package com.souv.socket.serialport;

/**
 * 描述：
 * 作者：dc on 2016/9/5 17:59
 * 邮箱：597210600@qq.com
 */
public class SerailPortStatus {
    private static boolean ROBOT_SERIALPORT_START = false; //主控串口是否打开


    public static boolean isRobotSerialportStart() {
        return ROBOT_SERIALPORT_START;
    }

    public static void setRobotSerialportStart(boolean robotSerialportStart) {
        ROBOT_SERIALPORT_START = robotSerialportStart;
    }

}
