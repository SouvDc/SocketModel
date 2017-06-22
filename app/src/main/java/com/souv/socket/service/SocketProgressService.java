package com.souv.socket.service;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

import com.nudt.socketmodel.bean.SocketConfig;
import com.nudt.socketmodel.constant.SocketConstant;
import com.nudt.socketmodel.net.SocketProgress;


/**
 * 描述：连接服务器、主控的socket类
 * 作者：dc on 2017/3/21 14:41
 * 邮箱：597210600@qq.com
 */
public class SocketProgressService extends Service {
    private static final String TAG = "SocketProgressService";

    /**  类对象定义  **/
    private ServiceSendThread serviceSendThread = null; //数据发送线程
    private ServiceSocketThread serviceSocketThread = null; //接收服务器socket数据线程
    private ServiceConnectSocketThread serviceConnectSocketThread = null; //anbot socket连接线程
    /**   属性定义  **/
    private boolean serviceConnect = false;  //服务器连接状态
    private int THREAD_SLEEP = 200;  //线程休眠时间

    /* 线程接收数据  */
    private int REPLY_MAX_LENGTH = 1024 * 10;       //socket数据接收最大长度
    private char buf[] = new char[REPLY_MAX_LENGTH]; //socket缓存buf

    private final int HANDLER_LOGIN_FAIL = 1;    //登录失败
    private final int HANDLER_LOGIN_SUCCESS = 2; //登录成功

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case HANDLER_LOGIN_FAIL:
                    Log.e(TAG, "登录失败");
//                    Toast.makeText(AppContextUtil.getInstance(), getString(R.string.tcp_socket_login_fail) , Toast.LENGTH_LONG);
                    break;
                case HANDLER_LOGIN_SUCCESS:
                    Log.e(TAG, "登录成功");
//                    Toast.makeText(AppContextUtil.getInstance(), getString(R.string.tcp_socket_login_success) , Toast.LENGTH_LONG);
//                    loginCount = 0;
                    break;
            }
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();

        Log.e(TAG,"SocketProgressService服务以及启动");
        SocketConfig.setServiceIp(SocketConstant.SERVICE_IP);
        SocketConfig.setServicePort(SocketConstant.SERVICE_PORT);
        SocketConfig.setSocketTime(8 * 1000);

        //服务器连接线程
        serviceConnectSocketThread = new ServiceConnectSocketThread();
        serviceConnectSocketThread.start();

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    /**
     * @descriptoin	服务器连接线程
     * @author	dc
     * @date 2017/3/23 10:30
     */
    public class ServiceConnectSocketThread extends Thread{
        @Override
        public void run() {
            //服务器连接
            serviceConnect = SocketProgress.socketConnect();
            if(serviceConnect){
                //启动服务器socket接收线程
                Log.e(TAG, "服务器连接成功，启动服务器socket接收线程");
                serviceSocketThread = new ServiceSocketThread();
                serviceSocketThread.start();

                serviceSendThread = new ServiceSendThread();
                serviceSendThread.start();
            } else {
                Log.e(TAG, "service重连");
                //重连
                serviceConnect = SocketProgress.reConnect();
                if(serviceConnect){
                    //重连成功
                } else {
                    //重连失败
                    Log.e(TAG, "service重连失败");
                }
            }
        }
    }

    /**
     * 接收服务器数据线程
     */
    public class ServiceSocketThread extends Thread{
        @Override
        public void run() {
            while (serviceConnect){
                String allString = "";
                String partString = "";
                String value = "";
                try {
                    if(SocketProgress.isConnect())
                    {
                        int length = SocketProgress.readMsg(buf, 0, REPLY_MAX_LENGTH - 1);
                        if (-1 == length) {
                            Log.e(TAG, "服务器断开，进行重连");
                            SocketProgress.reConnect();
                        } else if (0 < length) {
                            partString = new String(buf, 0, length);
                            allString += partString;

                            int aCommonEnd = allString.indexOf(SocketConstant.BUF_INSTRUCTION_SPLIT_SYMBOL);
                            while (aCommonEnd >= 0) {
                                value = allString.substring(0, aCommonEnd);
                                Log.e(TAG, "   接收到的完整数据：" + value);
                                if (!TextUtils.isEmpty(value)) {

                                    //判断是否为登录指令(登录指令无需返回到界面，直接toast提示）
                                    String rece[] = value.split(" ");
                                    String targetStr = rece[0].trim(); //目标地址
                                    String checkStr = rece[2].trim();
                                    String checkCode = (checkStr.substring(1, checkStr.length()));  //获取校验码  将  80D截取为0D

                                    /*//不根据指令来进行转发，而是根据目标地址进行转发
                                    if(targetStr.equals(Constant.robotId)){
                                        //转发给主控
                                        byte[] remoteControlByte = ByteUtil.stringToByte(value + " \\","utf-8");
//                                        SerialPortUtil.sendData(remoteControlByte);
//                                        String sendValue = "4EB6967DAE79 7451baef41de 453  ";
//                                        SocketProgress.sendMsg(Constant.serviceIp, Constant.servicePort, sendValue);
                                    } else {
                                        if (checkCode.equals(ByteUtil.byteToHexStr(ByteUtil.intToByte(TokenCommon.BUF_ACTION_DEVICE_LOGIN), "").toLowerCase())) {
                                            //设备登录成功
                                            String  resultCode = (rece[rece.length - 1].trim());  //获取checksum
                                            int HANDLER_MESSAGE_WATH = resultCode.equals("1000") ? HANDLER_LOGIN_SUCCESS : HANDLER_LOGIN_FAIL;
                                            Log.e(TAG,  HANDLER_MESSAGE_WATH + "  登录返回结果 = " + resultCode);
                                            handler.sendEmptyMessage(HANDLER_MESSAGE_WATH);
                                        }
                                    }*/
                                }
                                if (aCommonEnd + SocketConstant.BUF_INSTRUCTION_SPLIT_SYMBOL.length() >= allString.length()) {
                                    allString = "";
                                    aCommonEnd = 0;
                                    break;
                                }
                                allString = allString.substring(aCommonEnd + SocketConstant.BUF_INSTRUCTION_SPLIT_SYMBOL.length(), allString.length());
                                aCommonEnd = allString.indexOf(SocketConstant.BUF_INSTRUCTION_SPLIT_SYMBOL);
                            }
                        }

                    } else
                        Log.e(TAG, "连接断开");
                } catch (Exception e){
                    e.printStackTrace();
                }

                try {
                    Thread.sleep(THREAD_SLEEP);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public class ServiceSendThread extends Thread{
        @Override
        public void run() {
            while (true){
                try {
                    String str = "01 02 03 04";
                    SocketProgress.sendMsg(str);
                    Thread.sleep(2 * 1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        SocketProgress.disConnect();
        Log.e(TAG,"socket Service is finish");
    }
}
