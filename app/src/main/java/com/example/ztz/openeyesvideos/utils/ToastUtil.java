package com.example.ztz.openeyesvideos.utils;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

/**
 * 去除了系统Toast的重叠问题
 * 
 * @author asus1
 * 
 */
public class ToastUtil {
	private static Toast toast = null;
    public static void showShortToast(Context context, String msg) {
		if (toast == null) {
			toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
		} else {
			toast.setText(msg);
			toast.setGravity(Gravity.CENTER,0,0);
		}
		toast.show();
    }  
    
    public static void showLongToast(Context context, String msg) {
		if (toast == null) {
			toast = Toast.makeText(context, msg, Toast.LENGTH_LONG);
		} else {
			toast.setText(msg);
		}
		toast.show();
    }
	public static void cancelToast() {
		if (toast != null) {
			toast.cancel();
		}
	}

}
