package zzg.com.nfc.util;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

/**
 * Toast统一管理类
 * 
 */
public class ToastUtils
{
	private static ToastUtils instance;
//	private ToastUtils() {
//
//	}
//
//	public synchronized static ToastUtils getInstance() {
//		if (instance == null) {
//			instance = new ToastUtils();
//		}
//		return instance;
//	}

	private static Toast toast;

	/**
	 * 短时间显示Toast
	 */
	public static void showToast(Context context, CharSequence message) {
		if (null == toast) {
			toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
			toast.setGravity(Gravity.CENTER,0,40);
			// toast.setGravity(Gravity.CENTER, 0, 0);
		} else {
			toast.setText(message);
		}
		toast.show();
	}

	/**
	 * 短时间显示Toast
	 */
	public static void showShort(Context context, CharSequence message) {
		if (null == toast) {
			toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
			toast.setGravity(Gravity.CENTER,0,40);
			// toast.setGravity(Gravity.CENTER, 0, 0);
		} else {
			toast.setText(message);
		}
		toast.show();
	}

	/**
	 * 长时间显示Toast
	 */
	public static void showLong(Context context, CharSequence message) {
		if (null == toast) {
			toast = Toast.makeText(context, message, Toast.LENGTH_LONG);
			// toast.setGravity(Gravity.CENTER, 0, 0);
			toast.setGravity(Gravity.CENTER,0,40);
		} else {
			toast.setText(message);
		}
		toast.show();
	}

	/**
	 * 长时间显示Toast
	 */
	public static void showLong(Context context, int message) {
		if (null == toast) {
			toast = Toast.makeText(context, message, Toast.LENGTH_LONG);
			// toast.setGravity(Gravity.CENTER, 0, 0);
			toast.setGravity(Gravity.CENTER,0,40);
		} else {
			toast.setText(message);
		}
		toast.show();
	}

	/**
	 * 自定义显示Toast时间
	 */
	public static void show(Context context, CharSequence message, int duration) {
		if (null == toast) {
			toast = Toast.makeText(context, message, duration);
			toast.setGravity(Gravity.CENTER,0,40);
			// toast.setGravity(Gravity.CENTER, 0, 0);
		} else {
			toast.setText(message);
		}
		toast.show();
	}

	/**
	 * 自定义显示Toast时间
	 */
	public static void showToast(Context context, CharSequence message, int duration) {
		if (null == toast) {
			toast = Toast.makeText(context, message, duration);
			toast.setGravity(Gravity.CENTER,0,40);
			// toast.setGravity(Gravity.CENTER, 0, 0);
		} else {
			toast.setText(message);
		}
		toast.show();
	}

}