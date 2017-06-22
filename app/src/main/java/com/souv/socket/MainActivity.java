package com.souv.socket;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;

import com.souv.socket.constant.TokenCommon;
import com.souv.socket.serialport.SerailPortDataReceivedListerner;
import com.souv.socket.serialport.SerialPortUtil;
import com.souv.socket.service.SocketProgressService;
import com.souv.socket.util.AppContextUtil;

public class MainActivity extends AppCompatActivity implements SerailPortDataReceivedListerner {
    private final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.e(TAG, "onCreate");

        /**  启动socket服务  **/
        startService(new Intent(AppContextUtil.getInstance(), SocketProgressService.class));

        initSerialPort();
    }

    /**
     * @descriptoin 初始化串口工具
     * @author dc
     * @date 2016/9/5 16:15
     */
    private void initSerialPort() {
        //判断主控串口是否打开
        boolean isOpen = SerialPortUtil.openSerailPort(TokenCommon.ROBOT_SERIALPORT_BAUDRATE, TokenCommon.ROBOT_SERIALPORT_PATH);
        if (!isOpen) {
            //打开串口失败
            Log.e(TAG, "打开主控状态串口失败");
        } else {
            Log.e(TAG, "打开主控状态串口成功");
            //注册接收串口数据的监听接口
            SerialPortUtil.setDataReceivedListener(this);
        }
    }


    @Override
    protected void onStart() {
        super.onStart();
        Log.e(TAG, "onStart");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.e(TAG, "onRestart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e(TAG, "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e(TAG, "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e(TAG, "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e(TAG, "onDestroy");
        stopService(new Intent(AppContextUtil.getInstance(), SocketProgressService.class));
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        Log.e(TAG, "onSaveInstanceState");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.e(TAG, "onRestoreInstanceState");
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            moveTaskToBack(false);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onDataReceived(String value, int code) {

    }
}
