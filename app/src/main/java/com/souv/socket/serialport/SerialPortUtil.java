package com.souv.socket.serialport;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.nudt.serialport.utils.SerialPort;
import com.souv.socket.constant.TokenCommon;
import com.souv.socket.util.ByteUtil;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.InvalidParameterException;
import java.util.Arrays;


/**
 * 描述：串口调试工具类
 * 作者：dc on 2016/8/18 14:47
 * 邮箱：597210600@qq.com
 */
public class SerialPortUtil {
    private static final String TAG = SerialPortUtil.class.getSimpleName();

    private static Context context = null;

    /**  类对象定义  **/
    private static SerialPort mRobotSerialPort = null;  //主控串口对象
    private static SerailPortDataReceivedListerner serailPortDataReceivedListerner = null;

    /** 串口读取数据  **/
    protected static OutputStream mRobotOutputStream;
    private static InputStream mRobotInputStream;
    private static RobotReadThread robotReadThread;
    private static RobotSendThread robotSendThread;

    /**
     * @descriptoin	打开串口数据
     * @author	dc
     * @date 2016/9/5 16:07
     */
    public static boolean openSerailPort(int baudrate,String path){
        try {
            Log.e(TAG, "打开串口");
            getSerialPort(baudrate, path);

            return true;
        } catch (SecurityException e) {
            Log.e(TAG,"You do not have read/write permission to the serial port");
            return false;
        } catch (IOException e) {
            Log.e(TAG, "The serial port can not be opened for an unknown reason");
            return false;
        } catch (InvalidParameterException e) {
            Log.e(TAG, "Please configure your serial port first.");
            return false;
        }
    }


    /**
     * 接收主控串口数据
     */
    private static class RobotReadThread extends Thread {
        @Override
        public void run() {
            super.run();
            String allString = "";
            String partString = "";
            String value = "";
            while(!isInterrupted()) {
                int size;
                try {
                    byte[] buffer = new byte[512];
                    if (mRobotInputStream == null) return;
                    size = mRobotInputStream.read(buffer);
                    if(null != serailPortDataReceivedListerner){
                        //获取串口传过来的数据：机器人状态信息数据
                        if (size > 0) {
                            partString = new String(buffer, 0, size);
                            allString += partString;
                            int aCommonEnd =  allString.indexOf(TokenCommon.BUF_INSTRUCTION_SPLIT_SYMBOL);
                            while (aCommonEnd >= 0) {
                                value = allString.substring(0, aCommonEnd);
                                Log.e(TAG, "接收到的指令 = " + value);
                                if (!TextUtils.isEmpty(value)) {
                                    //判断是否为登录指令(登录指令无需返回到界面，直接toast提示）
                                    String rece[] = value.split(" ");
                                    String sourceStr = rece[1].trim();  //源地址
                                    String checkStr = rece[2].trim();  //命令字
                                    String checkCode = (checkStr.substring(1, checkStr.length()));  //获取校验码  将  80D截取为0D

                                }
                                if(aCommonEnd + TokenCommon.BUF_INSTRUCTION_SPLIT_SYMBOL.length()>=allString.length()){
                                    allString="";
                                    aCommonEnd=0;
                                    break;
                                }
                                allString = allString.substring(aCommonEnd + TokenCommon.BUF_INSTRUCTION_SPLIT_SYMBOL.length(), allString.length());
                                aCommonEnd = allString.indexOf(TokenCommon.BUF_INSTRUCTION_SPLIT_SYMBOL);
                            }
                        } else {
//                            Log.e(TAG,"无数据");
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    return;
                }
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
//                Log.e(TAG,"-------------------------------------");
            }
        }
    }

    public static class RobotSendThread extends Thread{
        @Override
        public void run() {
            while (true){
                try {
                    String str = "01 02 03 04 ";
                    byte[] sendByte = ByteUtil.stringToByte(str, "utf-8");
                    sendData(sendByte);
                    Thread.sleep(2 * 1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }



    /**
     * @descriptoin	注册串口接收数据接口
     * @author	dc
     * @param
     * @date 2016/9/5 16:02
     */
    public static void setDataReceivedListener(SerailPortDataReceivedListerner listener){
        context = (Context)listener;
        serailPortDataReceivedListerner = listener;
    }


    /**
     * @descriptoin	打开串口
     * @author	dc
     * @param
     * @date 2016/9/5 16:05
     * @return
     */
    public static void getSerialPort(int baudrate,String path) throws SecurityException, IOException, InvalidParameterException {

//        Log.e(TAG, "path=" + path + "     baudrate=" + baudrate);
        if(baudrate == TokenCommon.ROBOT_SERIALPORT_BAUDRATE && path.equals(TokenCommon.ROBOT_SERIALPORT_PATH)) {
            //打开主控串口
            if (mRobotSerialPort == null) {

			    /* Check parameters */
                if ((path.length() == 0) || (baudrate == -1)) {
                    throw new InvalidParameterException();
                }

			    /* Open the serial port */
                mRobotSerialPort = new SerialPort(new File(path), baudrate, 0);
//                mCurrentSerialPort = mRobotSerialPort;

                mRobotOutputStream = mRobotSerialPort.getOutputStream();
                mRobotInputStream = mRobotSerialPort.getInputStream();

                  /* Create a receiving thread */
                robotReadThread = new RobotReadThread();
                robotReadThread.start();


                robotSendThread = new RobotSendThread();
                robotSendThread.start();

                Log.e(TAG, "mRobotSerialPort =======path=" + path + "     baudrate=" + baudrate);
            }
        }


        //根据波特率和路径判断打开的是那个串口设备
        if(baudrate == TokenCommon.ROBOT_SERIALPORT_BAUDRATE && path == TokenCommon.ROBOT_SERIALPORT_PATH){
            SerailPortStatus.setRobotSerialportStart(true);
        }
    }

    /**
     * @descriptoin	关闭串口
     * @author	dc
     * @date 2016/9/5 16:05
     */
    public static void closeSerialPort() {

        if (mRobotSerialPort != null) {
            Log.e(TAG,"主控串口关闭");
            robotReadThread.interrupt();
            mRobotSerialPort.close();
            mRobotSerialPort = null;
            //根据不同的波特率也路径关闭对应的串口，则证明打开的是此串口
//            if(baudrate == TokenCommon.ROBOT_SERIALPORT_BAUDRATE && path.equals(TokenCommon.ROBOT_SERIALPORT_PATH)){
            SerailPortStatus.setRobotSerialportStart(false);
//            }
        }

    }

    /**
     * @descriptoin	发送串口数据
     * @author	dc
     * @param data byte数组
     * @date 2016/9/5 16:25
     */
    public static void sendData(byte[] data){
        Log.e(TAG,"发送数据" + Arrays.toString(data));
        try {
            if(null != mRobotOutputStream){
                mRobotOutputStream.write(data);
            } else {
                Log.e(TAG,"串口打开失败");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
