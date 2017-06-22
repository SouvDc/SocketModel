package com.souv.socket.constant;

/**
 * 描述：网络指令协议
 * 作者：dc on 2016/3/14 18:35
 * 邮箱：597210600@qq.com
 */
public class TokenCommon {

    /*********************  家居2 的指令集**********************************************/

    /**
     * 不同设备的不同波特率及串口path
     */
    //主控
    public final static int ROBOT_SERIALPORT_BAUDRATE = 115200;
    public final static String ROBOT_SERIALPORT_PATH = "/dev/ttyS3";


    /* 用户获取服务器列表，参数为'username password' */
    public static final int BUF_ACTION_USER_QUERY_SERVER_LIST = 0x1;
    /* 设备获取服务器列表 */
    public static final int BUF_ACTION_DEVICE_QUERY_SERVER_LIST = 0x2;
    /* 配置导入,参数为'config_table' */
    public static final int BUF_ACTION_CONFIG_IMPORT = 0x3;
    /* 配置更新,参数为'config_table' */
    public static final int BUF_ACTION_CONFIG_UPDATE = 0x4;
    /* 用户登录，参数为'username password' */
    public static final int BUF_ACTION_USER_LOGIN = 0x5;
    /* 设备登录 */
    public static final int BUF_ACTION_DEVICE_LOGIN = 0x6;
    /* 用户退出 */
    public static final int BUF_ACTION_USER_LOGOUT = 0x7;
    /* 设备退出 */
    public static final int BUF_ACTION_DEVICE_LOGOUT = 0x8;
    /* 配置查询 */
    public static final int BUF_ACTION_CONFIG_QUERY = 0x9;
    /* 状态请求开始  */
    public static final int BUF_ACTION_STATUS_START = 0x10;
    /* 状态请求结束  */
    public static final int BUF_ACTION_STATUS_STOP = 0x11;
    /* 配置变更通知，参数无 */
    public static final int BUF_ACTION_CONFIG_CHANGE_NOTIFY = 0xA;
    /* “后台确认一键呼救 */
    public static final int BUF_ACTION_SOS_CANCEL = 0xB;
    /* 修改音量,参数为'volume_value' */
    public static final int BUF_ACTION_ADJUST_VOL = 0xC;
    /* 一键呼救 */
    public static final int BUF_ACTION_SOS_NOTIFY = 0xD;
    /* 状态上报,参数为'status_list' */
    public static final int BUF_ACTION_STATUS_REPORT = 0xE;
    /* 障碍上报，参数为'barrier_value'，其中超声/跌落/碰撞分别为1/2/3 */
    public static final int BUF_ACTION_BARRIER_NOTIFY = 0xF;

    /* 开始录像 */
    public static final int BUF_ACTION_REC_START = 0x20;
    /* 结束录像 */
    public static final int BUF_ACTION_REC_STOP = 0x21;
    /* 拷贝录像 */
    public static final int BUF_ACTION_REC_FILES_COPY = 0x22;
    /* 灯带控制，参数为 'value flash_mode' */
    public static final int BUF_ACTION_LED_FLASH = 0x23;
    /* 机器人头的水平绝对角度 */
    public static final int BUF_ACTION_ROBOT_HEAD_TURN_LEFT = 0x24;
    /* 机器人头的水平相对角度 */
    public static final int BUF_ACTION_ROBOT_HEAD_TURN_RIGHT = 0x25;
    /* 打开手臂 */
    public static final int BUF_ACTION_ROBOT_ARM_OPEN = 0x26;
    /* 关闭手臂 */
    public static final int BUF_ACTION_ROBOT_ARM_CLOSE = 0x27;

    /* 身份证刷卡结果通知 */
    public static final int BUF_ACTION_ID_CARD_RESULT_NOTIFY = 0x30;
    /* 语音广播 */
    public static final int BUF_ACTION_VOICE_BROADCAST = 0x31;


    /// 空闲状态. 范围: 0x40 ~ 0x4F


    /// 遥控状态. 范围: 0x50 ~ 0x5F

    /* 往前,参数为'speed' */
    public static final int BUF_ACTION_FORWARD = 0x50;
    /* 往后,参数为'speed' */
    public static final int BUF_ACTION_BACK = 0x51;
    /* 往左,参数为'speed' */
    public static final int BUF_ACTION_LEFT = 0x52;
    /* 往右,参数为'speed' */
    public static final int BUF_ACTION_RIGHT = 0x53;
    /* 针对所有设备关机或重新开机，参数为'mode' */
    public static final int BUF_ACTION_SHUTDOWN = 0x54;


    ///导航. 范围: 0x60~0x6F

    // 导航模式设置，参数是 mode(单点、多点和禁入区)
    public static final int BUF_ACTION_NAVI_MODE = 0x60;
    // 地图上点击响应，参数是 goal_result(验证结果)
    public static final int BUF_ACTION_NAVI_POINT_CLK_RESPONSE = 0x61;
    // 导航（单点和多点）运行后的结果汇报,参数 nav_result(导航结果)
    public static final int BUF_ACTION_NAVI_RESULT_RESPONSE = 0x62;
    // 导航暂停，参数无
    public static final int BUF_ACTION_NAVI_FREEZE = 0x63;
    // 导航继续，参数无
    public static final int BUF_ACTION_NAVI_CONTINUE = 0x64;
    // 导航取消，参数无
    public static final int BUF_ACTION_NAVI_CANCEL = 0x65;
    // 导航开始，参数为 time_peroid(导航时长)
    public static final int BUF_ACTION_NAVI_START = 0x66;
    // 导航事件上传和修改. 参数为 'name time_start period point_list'
    public static final int BUF_ACTION_NAVI_EVENT_UPDATE = 0x67;
    // 导航事件删除，参数为'core_id name'
    public static final int BUF_ACTION_NAVI_EVENT_DELETE = 0x68;
    // 导航事件查询，参数无
    public static final int BUF_ACTION_NAVI_EVENT_QUERY = 0x69;
    // 导航变更
    public static final int BUF_ACTION_NAVI_NOTIFITION = 0x6a;

    /* 目标点请求，参数为 'x y z'
            * 逻辑：手机点击某点，通过此命令发到主控，主控再通过此命令返回结果 */
    public static final int BUF_ACTION_NAVI_DEST_POS_REQUEST = 0x6E;
    /* 目标点更新，参数为'index x,y,z' */
    public static final int BUF_ACTION_NAVI_DEST_POS_UPDATE = 0x6F;

    /// 充电. 范围: 0x70 ~ 0x7F

    /* 自动充电 */
    public static final int BUF_ACTION_AUTO_CHARGE = 0x70;
    /* 手动充电 */
    public static final int BUF_ACTION_MANUAL_CHARGE = 0x71;

    /* 充电子状态上报 */
    // 参数为 charging_state(值 0x01/0x02/0x03/0x04/0x05/0x06
    // 分别为寻充/正在充电/充电完毕/充电失败/找不到充电器/充电过程被中断)
    public static final int BUF_ACTION_CHARGING_SUBSTATE_REPORT = 0x72;


    /// 建图. 范围: 0x80 ~ 0x8F

    /* GPS位置上报 */
    public static final int BUF_ACTION_GPS_POSITION_REPORT = 0x80;
    /* GPS位置请求开始 */
    public static final int BUF_ACTION_GPS_POSITION_GET_START = 0x81;
    /* GPS位置请求结束 */
    public static final int BUF_ACTION_GPS_POSITION_GET_STOP = 0x82;
    /* 开始建图 */
    public static final int BUF_ACTION_MAKE_MAP_START = 0x83;
    /* 保存地图 */
    public static final int BUF_ACTION_MAKE_MAP_SAVE = 0x84;
    /* 取消建图 */
    public static final int BUF_ACTION_MAKE_MAP_CANCEL = 0x85;


    /// 新加数据. 范围: 0x90 ~ 0x9F
    public static final int BUF_ACTION_SET_ROBOT_WIFI = 0x90;

    /// 制暴状态. 范围: 0xA0 ~ 0xAF

    /* 开启制暴模式 */
    public static final int BUF_ACTION_AT_START = 0xA0;
    /* 打开叉子 */
    public static final int BUF_ACTION_SHOW_CRACK_FORK = 0xA1;
    /* 关闭叉子 */
    public static final int BUF_ACTION_HIDE_CRACK_FORK = 0xA2;
    /* 放电 */
    public static final int BUF_ACTION_TOUCH_ELE_STICK = 0xA3;
    /* 打开警报 */
    public static final int BUF_ACTION_TURN_ON_ALARM = 0xA4;
    /* 关闭警报 */
    public static final int BUF_ACTION_TURN_OFF_ALARM = 0xA5;
    /* 关闭制暴模式 */
    public static final int BUF_ACTION_AT_STOP = 0xA6;
    /* 打开追随  */
    public static final int BUF_ACTION_ON_FOLLOW = 0xA7;
    /* 关闭追随  */
    public static final int BUF_ACTION_OFF_FOLLOW = 0xA8;
    /// 交互. 范围: 0xB0 ~ 0xBF

    /* 开启交互 */
    public static final int BUF_ACTION_CHAT_START = 0xB0;
    /* 结束交互 */
    public static final int BUF_ACTION_CHAT_STOP = 0xB1;
    /* 音视频通话的帐号更新，参数为'username password' */
    public static final int BUF_ACTION_CHAT_ACCOUNT_UPDATE = 0xB2;
    /* 请求通话 */
    public static final int BUF_ACTION_CHAT_ASK = 0xB3;
    /* 回复请求通话 */
    public static final int  BUF_ACTION_CHAT_RESPONSE_ASK = 0xB4;
    /* 音视频通话的帐号查询 */
    public static final int BUF_ACTION_CHAT_ACCOUNT_QUERY = 0xB5;
    /*  音视频通话挂掉  */
    public static final int BUF_ACTION_CHAT_RESPONSE_ASK_HANGUP = 0xB6;
    /* 接受通话  */
    public static final int BUF_ACTINO_CHAT_RESPONSE_ASK_REPLY_OK = 1;
    /*  拒绝通话  */
    public static final int BUF_ACTINO_CHAT_RESPONSE_ASK_REPLY_NO = 0;

    /// 急停. 范围: 0xC0 ~ 0xCF

    /* 急停按钮按下 */
    public static final int BUF_ACTION_HW_STOP_BTN_PRESS = 0xC0;
    /* 急停按钮恢复 */
    public static final int BUF_ACTION_HW_STOP_BTN_RELEASE = 0xC1;

    /// 未使用. 范围: 0xD0 ~ 0xEE

    /// 调试相关. 范围: 0xFA ~ 0xFF

    public static final int BUF_ACTION_BECKON_DATA = 0xf0; //召唤指令

    public static final int BUF_ACTION_TURN_DATA = 0xf1; //转过来

    public static final int BUF_ACTION_DEBUG_QUERY = 0xFD;

    //广播
    public static final int BUF_ACTION_DEMO_BROADCAST = 0xFC;

    /* API 版本查询 */
    public static final int BUF_ACTION_API_VER_QUERY = 0xFF;

    //人脸车牌识别
    public static final int BUF_ACTION_FACE_IPR = 0x91;

    //device_type
    public static final int DEVICE_TYPE_DISPLAY = 2;

    //主控
    public static final int DEVICE_TYPE_ROBOT= 1;

    //发送的数据标志位
    public static final int DEVICE_TYPE_SEND_TAG = 4;
    public static final int DEVICE_TYPE_SEND_TAG_0 = 0;
    public static final int DEVICE_TYPE_SEND_TAG_8 = 8;

    public static final String DESTINATION_ID = "0"; //  destination_id   为请求或返回过程中目标主机的ID 不传为0
    /// Common. 范围: 0x01 ~ 0x3F



    /* 指令的结束符号 */
    public final static byte BUF_STOP = (byte) 0x5c;

    /* 指令的结束符号 */
    public final static String BUF_INSTRUCTION_SPLIT_SYMBOL = "\\";

    public static final int DATA_MIN_LEN = 1;//数据最小长度

    /** 串口通讯协议 **/
    /*  android 发送给主控的协议头 */
    public static final byte ANDROIDSENDROBOT = (byte) 0x8a;

    /*  android 固定设备号  */
    public static final byte ANDROIDDEVICEID = (byte)0x02;
    public static final byte RESPONSEROUTESERVERADDRESS = (byte) 0x8c;

    /*************************  家居2 按照安保协议新加的指令集合  *************************/
    public static final int BUF_ACTION_START_BT_SENSOR  = 0x91; /* 开始体温测量 */
    public static final int BUF_ACTION_BT_SENSOR_DATA   = 0x92; /* 体温数据上报 */
    public static final int BUF_ACTION_START_RCG_SENSOR = 0x93; /* 开始心电数据测量 */
    public static final int BUF_ACTION_RCG_SENSOR_DATA  = 0x94; /* 上报心电数据 */
    public static final int BUF_ACTION_STOP_HEALTH_SENSOR  = 0x97; /* 停止数据测量 */
    public static final int BUF_ACTION_START_BLOOD_PRESSURE_SENSOR  = 0x95; /* 开始血压测量 */
    public static final int BUF_ACTION_BT_BLOOD_PRESSURE_SENSOR  = 0x96; /* 血压数据上报 */

    //心跳
    public static final int BUF_ACTION_HEARTBEAT_DATA = 0xfa; // 心跳

    /**
     * 按钮指令
     */
    public static final int BUF_ACTION_BUTTON_TOUCH_TOKEN = 0x98 ;

    public static final int BUF_ACTION_SET_ROBOTID = 0x99; //将robotid设置给主控


}
