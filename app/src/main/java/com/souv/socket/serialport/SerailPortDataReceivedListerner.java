package com.souv.socket.serialport;

/**
 * 描述：接收串口数据接口
 * 作者：dc on 2017/4/21 10:04
 * 邮箱：597210600@qq.com
 */
public interface SerailPortDataReceivedListerner {

    /**
     * @descriptoin	接收串口数据
     * @author	dc
     * @param value 数据内容
     *              @param code 数据类型
     *                          -1  其他指令
     *                          0  状态信息
     *                          1  wifi配置
     *                          2  按钮事件
     *                          3  开始体温测量
     *                          4  体温数据上传
     *                          5  开始血压测量
     *                          6  血压数据上传
     *                          7  开始心电测量
     *                          8  心电数据上传
     *                          100  结束测量
     * @date 2017/4/21 10:06
     * @return
     */
    void onDataReceived(String value, int code);
}
