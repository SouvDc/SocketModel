package com.nudt.socketmodel.bean;

/**
 * 描述：socket配置类
 * 作者：dc on 2017/6/20 14:27
 * 邮箱：597210600@qq.com
 */
public class SocketConfig {

    private static String serviceIp;  //服务器ip
    private static int servicePort; //服务器port
    private static int socketTime = 5 * 1000;  //socket 超时时间
    private static int reConnectCount = 10; // 连接失败之后的重连次数
    private static int loginCount = 10;  // 登录失败之后的连续登录次数
    private static int reconnectFailSleep = 10 * 1000; //socket连接失败之后的重复连接的休眠时间

    private static boolean reConnectStatu = false; // socket连接状态

    public static String getServiceIp() {
        return serviceIp;
    }

    public static void setServiceIp(String serviceIp) {
        SocketConfig.serviceIp = serviceIp;
    }

    public static int getServicePort() {
        return servicePort;
    }

    public static void setServicePort(int servicePort) {
        SocketConfig.servicePort = servicePort;
    }

    public static int getSocketTime() {
        return socketTime;
    }

    public static void setSocketTime(int socketTime) {
        SocketConfig.socketTime = socketTime;
    }

    public static int getReConnectCount() {
        return reConnectCount;
    }

    public static void setReConnectCount(int reConnectCount) {
        SocketConfig.reConnectCount = reConnectCount;
    }

    public static int getLoginCount() {
        return loginCount;
    }

    public static void setLoginCount(int loginCount) {
        SocketConfig.loginCount = loginCount;
    }

    public static int getReconnectFailSleep() {
        return reconnectFailSleep;
    }

    public static void setReconnectFailSleep(int reconnectFailSleep) {
        SocketConfig.reconnectFailSleep = reconnectFailSleep;
    }

    public static boolean isReConnectStatu() {
        return reConnectStatu;
    }

    public static void setReConnectStatu(boolean reConnectStatu) {
        SocketConfig.reConnectStatu = reConnectStatu;
    }
}
