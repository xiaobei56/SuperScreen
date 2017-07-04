package gridlife.cn.superscreen.activity.base;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import java.util.List;

import gridlife.cn.superscreen.service.FloatWindowService;
import pub.devrel.easypermissions.EasyPermissions;


/**
 * ProjectName SuperScreen
 * PackageName gridlife.cn.superscreen.activity.base
 * Created by damaren_bzb on 2017/7/4.
 */

public abstract class BaseActivity extends AppCompatActivity implements EasyPermissions.PermissionCallbacks {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestPermission();
        setContentView(getLayout());
        initData();
        initView();
    }

    /**
     * 获取布局
     *
     * @return R.layout
     */
    public abstract int getLayout();

    /**
     * 初始化数据
     */
    protected abstract void initData();

    /**
     * 初始化view组件
     */
    public abstract void initView();

    private void requestPermission() {
        if (Build.VERSION.SDK_INT >= 23 && !Settings.canDrawOverlays(this)) {
            //没有悬浮窗权限m,去开启悬浮窗权限
            showMissingPermissionDialog();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> list) {
        // Some permissions have been granted
        // ...
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> list) {
        // Some permissions have been denied
        // ...
        showMissingPermissionDialog();
    }

    /**
     * 权限方案
     */
    public static final String PACKAGE_URL_SCHEME = "package:";

    /**
     * 显示对话框提示用户缺少权限
     */
    public void showMissingPermissionDialog() {

        new AlertDialog.Builder(this)
                .setTitle("请开启悬浮窗权限")
                .setMessage("手动开启")
                .setPositiveButton("GO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        startAppSettings();
                    }
                })
                .show();
    }

    /**
     * 打开系统应用设置(ACTION_APPLICATION_DETAILS_SETTINGS:系统设置权限)
     */
    public void startAppSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse(PACKAGE_URL_SCHEME + getPackageName()));
        startActivity(intent);
    }
}
