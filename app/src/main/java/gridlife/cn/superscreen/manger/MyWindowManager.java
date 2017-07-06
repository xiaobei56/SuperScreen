package gridlife.cn.superscreen.manger;

import android.app.ActivityManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PixelFormat;
import android.media.Image;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import cn.gridlife.bzblibrary.utils.BzbToast;
import cn.gridlife.bzblibrary.utils.LogUtils;
import gridlife.cn.superscreen.R;
import gridlife.cn.superscreen.bean.MoveType;
import gridlife.cn.superscreen.bean.ShowType;
import gridlife.cn.superscreen.bean.SmallViewParameter;
import gridlife.cn.superscreen.view.FloatWindowSmallView;

/**
 * cn.gridlife.superscreen
 * SuperScreen
 * Created by BEI on 2017/7/3.
 */


public class MyWindowManager {

    /**
     * 小悬浮窗View的实例
     */
    private static FloatWindowSmallView smallWindow;

    /**
     * 大悬浮窗View的实例
     */

    /**
     * 小悬浮窗View的参数
     */
    private static WindowManager.LayoutParams smallWindowParams;

    /**
     * 大悬浮窗View的参数
     */
    private static WindowManager.LayoutParams bigWindowParams;

    /**
     * 用于控制在屏幕上添加或移除悬浮窗
     */
    private static WindowManager mWindowManager;

    /**
     * 用于获取手机可用内存
     */
    private static ActivityManager mActivityManager;
    private static int screenWidth = 1;
    private static int screenHeight = 1;
    private static Bitmap bitmap;

    /**
     * 创建一个小悬浮窗。初始位置为屏幕的右部中间位置。
     *
     * @param context 必须为应用程序的Context.
     */
    public static void createSmallWindow(Context context) {
        WindowManager windowManager = getWindowManager(context);
        screenWidth = windowManager.getDefaultDisplay().getWidth();
        screenHeight = windowManager.getDefaultDisplay().getHeight();
        if (smallWindow == null) {
            smallWindow = new FloatWindowSmallView(context);
            if (smallWindowParams == null) {
                smallWindowParams = new WindowManager.LayoutParams();
                smallWindowParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY;
                smallWindowParams.format = PixelFormat.RGBA_8888;
                smallWindowParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                        | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
                smallWindowParams.gravity = Gravity.LEFT | Gravity.TOP;
                smallWindowParams.width = FloatWindowSmallView.viewWidth;
                smallWindowParams.height = FloatWindowSmallView.viewHeight;
                smallWindowParams.x = screenWidth;
                smallWindowParams.y = screenHeight / 2;
            }
            smallWindow.setParams(smallWindowParams);
            windowManager.addView(smallWindow, smallWindowParams);
        }
    }

    /**
     * 将小悬浮窗从屏幕上移除。
     *
     * @param context 必须为应用程序的Context.
     */
    public static void removeSmallWindow(Context context) {
        if (smallWindow != null) {
            WindowManager windowManager = getWindowManager(context);
            windowManager.removeView(smallWindow);
            smallWindow = null;
        }
    }

//	/**
//	 * 创建一个大悬浮窗。位置为屏幕正中间。
//	 *
//	 * @param context
//	 *            必须为应用程序的Context.
//	 */
//	public static void createBigWindow(Context context) {
//		WindowManager windowManager = getWindowManager(context);
//		int screenWidth = windowManager.getDefaultDisplay().getWidth();
//		int screenHeight = windowManager.getDefaultDisplay().getHeight();
//		if (bigWindow == null) {
//			bigWindow = new FloatWindowBigView(context);
//			if (bigWindowParams == null) {
//				bigWindowParams = new WindowManager.LayoutParams();
//				bigWindowParams.x = screenWidth / 2 - FloatWindowBigView.viewWidth / 2;
//				bigWindowParams.y = screenHeight / 2 - FloatWindowBigView.viewHeight / 2;
//				bigWindowParams.type = WindowManager.LayoutParams.TYPE_PHONE;
//				bigWindowParams.format = PixelFormat.RGBA_8888;
//				bigWindowParams.gravity = Gravity.LEFT | Gravity.TOP;
//				bigWindowParams.width = FloatWindowBigView.viewWidth;
//				bigWindowParams.height = FloatWindowBigView.viewHeight;
//			}
//			windowManager.addView(bigWindow, bigWindowParams);
//		}
//	}

    /**
     * 将大悬浮窗从屏幕上移除。
     *
     * @param context
     *            必须为应用程序的Context.
     */
//	public static void removeBigWindow(Context context) {
//		if (bigWindow != null) {
//			WindowManager windowManager = getWindowManager(context);
//			windowManager.removeView(bigWindow);
//			bigWindow = null;
//		}
//	}

    /**
     * 更新小悬浮窗的TextView上的数据，显示内存使用的百分比。
     *
     * @param context 可传入应用程序上下文。
     */
    public static void updateViewContent(Context context, String s) {
        if (smallWindow != null) {
            TextView percentView = smallWindow.findViewById(R.id.percent);
            percentView.setText(s);

        }
    }

    public static void updateViewContent(Context context, Bitmap b) {
        if (smallWindow != null) {
            smallWindow.findViewById(R.id.percent).setVisibility(View.GONE);
            ImageView imageView = smallWindow.findViewById(R.id.image);
            imageView.setVisibility(View.VISIBLE);
            imageView.setImageBitmap(b);
        }
    }

    public void updateLoaction(Context context) {

    }

    public static String getSystemTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日    HH:mm:ss     ");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        String str = formatter.format(curDate);
        return str;
    }

    /**
     * 是否有悬浮窗(包括小悬浮窗和大悬浮窗)显示在屏幕上。
     *
     * @return 有悬浮窗显示在桌面上返回true，没有的话返回false。
     */
    public static boolean isWindowShowing() {
        return smallWindow != null;
    }

    /**
     * 如果WindowManager还未创建，则创建一个新的WindowManager返回。否则返回当前已创建的WindowManager。
     *
     * @param context 必须为应用程序的Context.
     * @return WindowManager的实例，用于控制在屏幕上添加或移除悬浮窗。
     */
    private static WindowManager getWindowManager(Context context) {
        if (mWindowManager == null) {
            mWindowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        }
        return mWindowManager;
    }

    /**
     * 如果ActivityManager还未创建，则创建一个新的ActivityManager返回。否则返回当前已创建的ActivityManager。
     *
     * @param context 可传入应用程序上下文。
     * @return ActivityManager的实例，用于获取手机可用内存。
     */
    private static ActivityManager getActivityManager(Context context) {
        if (mActivityManager == null) {
            mActivityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        }
        return mActivityManager;
    }

    /**
     * 计算已使用内存的百分比，并返回。
     *
     * @param context 可传入应用程序上下文。
     * @return 已使用内存的百分比，以字符串形式返回。
     */
    public static String getUsedPercentValue(Context context) {
        String dir = "/proc/meminfo";
        try {
            FileReader fr = new FileReader(dir);
            BufferedReader br = new BufferedReader(fr, 2048);
            String memoryLine = br.readLine();
            String subMemoryLine = memoryLine.substring(memoryLine.indexOf("MemTotal:"));
            br.close();
            long totalMemorySize = Integer.parseInt(subMemoryLine.replaceAll("\\D+", ""));
            long availableSize = getAvailableMemory(context) / 1024;
            int percent = (int) ((totalMemorySize - availableSize) / (float) totalMemorySize * 100);
            return "内存使用：" + percent + "%";
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "悬浮窗";
    }

    /**
     * 获取当前可用内存，返回数据以字节为单位。
     *
     * @param context 可传入应用程序上下文。
     * @return 当前可用内存。
     */
    private static long getAvailableMemory(Context context) {
        ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
        getActivityManager(context).getMemoryInfo(mi);
        return mi.availMem;
    }

    static int y = getRandomHeightNum();
    static int x = getRandomWidthNum();

    public static Bitmap getBitmap(byte[] data) {
        return BitmapFactory.decodeByteArray(data, 0, data.length);//从字节数组解码位图
    }

    public static void updateMove(Context context, SmallViewParameter smallViewParameter) {
        MoveType moveType;
        String showText;
        ShowType showType;
        if (smallViewParameter != null) {
            moveType = smallViewParameter.getMoveType();
            showText = smallViewParameter.getShowText();
            showType = smallViewParameter.getShowType();
            bitmap = getBitmap(smallViewParameter.getShowImage());
        } else {
            moveType = MoveType.RANDOMDOTMOVE;
            showText = "null";
            showType = ShowType.IMAGE;
        }
        if (moveType.equals(MoveType.RANDOMDOTMOVE)) {

            smallWindowParams.x = getRandomWidthNum();
            Log.i("x:==", smallWindowParams.x + "");
            smallWindowParams.y = getRandomHeightNum();
            LogUtils.i("y:==", smallWindowParams.y + "");
            smallWindow.setParams(smallWindowParams);
            if (!showType.equals(ShowType.IMAGE)) {
                BzbToast.showToast(context,"showText");
                updateViewContent(context, showText);
            } else {
                BzbToast.showToast(context,"showBitmap");
                updateViewContent(context, bitmap);
            }
        } else if (moveType.equals(MoveType.HORIZONMOVE)) {
            smallWindowParams.x = x++;
            Log.i("x:==", (x++) + "");
            smallWindowParams.y = y;
            Log.i("y:==", y + "");
            smallWindow.setParams(smallWindowParams);
            if (!showType.equals(ShowType.IMAGE)) {
                updateViewContent(context, showText);
            } else {
                updateViewContent(context, bitmap);
            }
        }
        Log.e("showText", showText);
        WindowManager windowManager = getWindowManager(context);
        windowManager.updateViewLayout(smallWindow, smallWindowParams);

    }

    public static int getRandomWidthNum() {
        return Math.abs(new Random().nextInt() % screenWidth);
    }

    public static int getRandomHeightNum() {
        return Math.abs(new Random().nextInt() % screenWidth);
    }
}
