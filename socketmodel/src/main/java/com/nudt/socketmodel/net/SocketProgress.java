package com.nudt.socketmodel.net;

import android.text.TextUtils;
import android.util.Log;

import com.nudt.socketmodel.bean.SocketConfig;
import com.nudt.socketmodel.constant.SocketConstant;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.net.Socket;

/**
 * 描述：TCP连接的工具类
 * 作者：dc on 2017/3/21 15:11
 * 邮箱：597210600@qq.com
 */
public class SocketProgress {
    private static final String TAG = "SocketProgress";

    /**  类对象定义  **/
    private static Socket serviceSocket = null;  //服务器的socket

    private static Writer serviceWriter = null;  //服务器的writer
    private static Reader serviceReader = null;  //服务器的reader

    /**   属性定义  **/
    private static String service_ip;
    private static int service_port;
    private static int SOCKET_TIME_OUT;
    private static boolean serviceConnect;
    private static int RECONNECT_COUNT;
    private static int SOCKET_CONNECT_FIAL_SLEEP;
    static {
        service_ip = SocketConfig.getServiceIp();
        service_port = SocketConfig.getServicePort();
        SOCKET_TIME_OUT = SocketConfig.getSocketTime();
        serviceConnect = SocketConfig.isReConnectStatu();
        RECONNECT_COUNT = SocketConfig.getReConnectCount();
        SOCKET_CONNECT_FIAL_SLEEP = SocketConfig.getReconnectFailSleep();
    }


    /**
     * @descriptoin	socket连接
     * @author	dc
     * @date 2017/3/21 15:36
     * @return 是否连接成功
     */
    public static boolean socketConnect(){
        try {
            Log.e(TAG, "进行socket连接");
            serviceSocket = new Socket(service_ip, service_port);
            serviceSocket.setSoTimeout(SOCKET_TIME_OUT);
            serviceSocket.setTcpNoDelay(true);
//                serviceSocket.setKeepAlive(true);  //启动默认的tcp心跳包  默认2小时发送5次
            serviceWriter = new OutputStreamWriter(serviceSocket.getOutputStream());
            serviceReader = new InputStreamReader(serviceSocket.getInputStream());
            Log.e(TAG, "socket连接成功");

            SocketConfig.setReConnectStatu(true);
            return serviceConnect = true;
        } catch (IOException e) {
            serviceConnect = false;
            e.printStackTrace();
        }
        return false;
    }

    /**
     * @descriptoin	根据不同的ip来断开相应的socket
     * @author	dc
     * @date 2017/3/21 15:41
     * @return
     */
    public static void disConnect(){
        try {
            if (serviceSocket != null)
                serviceSocket.close();
            if (serviceWriter != null)
                serviceWriter.close();
            if (serviceReader != null)
                serviceReader.close();
            Log.e(TAG, "service 服务器断开成功");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @descriptoin	根据ip获取socket连接状态
     * @author	dc
     * @param
     * @date 2017/3/21 15:43
     * @return
     */
    public static boolean isConnect(){
        if(serviceSocket.isConnected()){
            return true;
        }
        return serviceConnect;
    }


    /**
     * @descriptoin	重连RECONNECT_COUNT次
     * @author	dc
     * @date 2017/3/21 16:21
     * @return
     */
    public static boolean reConnect(){
        boolean isReconnect = false;
        for (int i = 0; i < RECONNECT_COUNT; i++) {
            isReconnect = socketConnect();
            if(isReconnect)
                break;
            else {
                try {
                    Thread.sleep(SOCKET_CONNECT_FIAL_SLEEP);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        return isReconnect;
    }


    /**
     * @descriptoin	根据不同的ip得到不同的socket中的数据
     * @author	dc
     * @param
     * @date 2017/3/21 15:59
     * @return length 数据长度
     */
    public static int readMsg(char buf[] ,int offset, int maxLength){
        int length = 0;
        //判断连接状态  如果未连接  直接返回false
        if (!isConnect()){
            length =  -1;
        }
        if(serviceReader != null){
            try {
                length = serviceReader.read(buf, offset, maxLength);
                Log.e(TAG,"length" + length);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return length;
    }

    /**
     * @descriptoin	根据ip向不同的socket发送数据
     * @author	dc
     * @param
     * @date 2017/3/21 16:03
     */
    public static void sendMsg(String msg){
        String sendMsg = msg + SocketConstant.BUF_INSTRUCTION_SPLIT_SYMBOL;  //发送加上结束符
        //判断数据是否为null或""
        if (!TextUtils.isEmpty(sendMsg)){
            Log.e(TAG,"服务器需要发送的数据 = "+sendMsg);
            try {
                if(null != serviceWriter){
                    serviceWriter.write(sendMsg, 0, sendMsg.length());
                    serviceWriter.flush();
                }
            } catch (IOException e) {
                Log.e(TAG, "发送数据异常");
                e.printStackTrace();
            }
        }
    }
}
