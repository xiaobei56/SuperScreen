package cn.gridlife.bzblibrary.utils;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import cn.gridlife.bzblibrary.R;

/**
 * cn.gridlife.bzblibrary.utils
 * TeleText
 * Created by BEI on 2017/6/21.
 */

public class BzbToast{
	/**
	 * 创建Toast类
	 */
	private static Toast mToast;
	/**
	 * 显示的时长
	 */
	public static final int LENGTH_SHORT = 0;
	public static final int LENGTH_LONG = 1;
	/**
	 * 常见Handler类用来调用Toast的cancel()方法
	 */
	private static Handler mHandler = new Handler();
	private static Runnable runnable = new Runnable() {
		@Override
		public void run() {
			mToast.cancel();
		}
	};
	/**
	 * 创建并设置显示信息
	 * @param context 上下文
	 * @param str 提示信息
	 * @param timeLength 时间长度
	 */
	private static void showInfo(Context context, String str, int timeLength) {
		try {
            /*
      自定义的布局
     */
			View v = LayoutInflater.from(context).inflate(R.layout.toast_bzb, null);
            /*
      自定义布局的提示信息组件
     */
			TextView tv = (TextView) v.findViewById(R.id.tv);
			mHandler.removeCallbacks(runnable);
			if(mToast!=null){
				tv.setText(str);
			}else {
				mToast = new Toast(context);
				tv.setText(str);

			}
			mToast.setDuration(timeLength);
			tv.setGravity(Gravity.CENTER);
			tv.setMaxLines(2);
			mToast.setView(v);
			mToast.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 *  显示自定义Toast的方法
	 * @param context 上下文
	 * @param msg 提示信息
	 */
	public static void showToast(Context context, String msg) {
		showInfo(context, msg, Toast.LENGTH_SHORT);
	}

	/**
	 *显示自定义Toast的方法
	 * @param ctx 上下文
	 * @param msg 提示信息
	 * @param timeLength 时间长度
	 */
	public static void showToast(final Activity ctx, final String msg, final int timeLength) {

		// 判断是否运行在主线程
		if ("main".equals(Thread.currentThread().getName())) {
			showInfo(ctx, msg, timeLength);
		} else {
			ctx.runOnUiThread(new Runnable() {
				@Override
				public void run() {
					showInfo(ctx, msg, timeLength);
				}
			});
		}
	}
}
