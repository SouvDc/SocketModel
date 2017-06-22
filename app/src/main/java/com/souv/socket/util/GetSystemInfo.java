package com.souv.socket.util;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.util.Log;

/**
 * 获取系统信息
 * @author dc
 *
 */
public class GetSystemInfo {
	private static final String TAG = "GetSystemInfo";
	/**
	 * @descriptoin	获取mac地址
	 * @return	mac地址
	 * @author	dc
	 * @date 2015-9-7 下午3:19:39
	 */
	public static String getMac(){
		WifiManager wifi = (WifiManager) AppContextUtil.getInstance().getSystemService(Context.WIFI_SERVICE);
		WifiInfo info = wifi.getConnectionInfo();
		String mac = "e076d0f0b728";//info.getMacAddress().replace(":", ""); //"001122334455";// "e076d0f0bcbc"
		Log.e(TAG, "本机mac地址= " + mac);
		return mac;
	}
}
