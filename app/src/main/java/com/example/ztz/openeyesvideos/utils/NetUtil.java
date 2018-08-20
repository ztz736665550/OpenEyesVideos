package com.example.ztz.openeyesvideos.utils;


import android.content.Context;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetUtil {
	/**
	 * 检查用户的网络:是否有网络
	 */
	public static boolean checkNet(Context context) {
		// 判断：WIFI链接
		boolean isWIFI = isWIFIConnection(context);
		// 判断：Mobile链接
		boolean isMOBILE = isMOBILEConnection(context);

		if (!isWIFI && !isMOBILE) {
			return false;
		}

		return true;
	}
	
	/**
	 * 检查gps是否打开和手机是否有网络:即是否能定位
	 */
	public static boolean canLocate(Context context) {
		// 判断：gps是否开启
		boolean isGPS = isGPSOpen(context);
		// 判断：WIFI链接
		boolean isWIFI = isWIFIConnection(context);
		// 判断：Mobile链接
		boolean isMOBILE = isMOBILEConnection(context);
		
		if (!isGPS && !isWIFI && !isMOBILE) {
			return false;
		}
		
		return true;
	}


	/**
	 * 判断：Mobile链接
	 * 
	 * @param context
	 * @return
	 */
	private static boolean isMOBILEConnection(Context context) {
		ConnectivityManager manager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);

		NetworkInfo networkInfo = manager
				.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
		if (networkInfo != null) {
			return networkInfo.isConnected();
		}
		return false;
	}

	/**
	 * 判断：WIFI链接
	 * 
	 * @param context
	 * @return
	 */
	private static boolean isWIFIConnection(Context context) {
		ConnectivityManager manager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);

		NetworkInfo networkInfo = manager
				.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		if (networkInfo != null) {
			return networkInfo.isConnected();
		}
		return false;
	}
	
	/**
	 * 判断：gps是否开启
	 * 
	 * @param context
	 * @return
	 */
	private static boolean isGPSOpen(Context context) {
		LocationManager manager = (LocationManager) context
				.getSystemService(Context.LOCATION_SERVICE);
		
		return manager.isProviderEnabled(LocationManager.GPS_PROVIDER);
	}
}
