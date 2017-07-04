package gridlife.cn.superscreen.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import cn.gridlife.bzblibrary.utils.BzbToast;
import gridlife.cn.superscreen.R;
import gridlife.cn.superscreen.activity.base.BaseActivity;
import gridlife.cn.superscreen.service.FloatWindowService;

public class MainActivity extends BaseActivity {
    private boolean serviceFlag = false;
    public int getLayout(){
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        serviceFlag= ServiceUtils.isServiceRunning("FloatWindowService");
        if (serviceFlag) {
            fab.setImageResource(R.drawable.vector_drawable_switch_open);
        } else {
            fab.setImageResource(R.drawable.vector_drawable_switch_close);
        }

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                if (serviceFlag == false) {
                    BzbToast.showToast(MainActivity.this, "open");
                    openFloatView();
                    serviceFlag=true;
                    fab.setImageResource(R.drawable.vector_drawable_switch_open);
//                    finish();

                } else {
                    BzbToast.showToast(MainActivity.this, "close");
                    stopFloatView();
                    serviceFlag = false;
                    fab.setImageResource(R.drawable.vector_drawable_switch_close);
//                    finish();
                }
            }
        });
    }
    @Override
    protected void initData() {

    }
    public void openFloatView(){
        Intent intent = new Intent(MainActivity.this, FloatWindowService.class);
        startService(intent);
    }
    public void stopFloatView(){
        Intent intent = new Intent(MainActivity.this, FloatWindowService.class);
        stopService(intent);
    }
    public void toImage(View v){
        startActivity(new Intent(this,TestTransformMatrixActivity.class));
    }
    public boolean getLocalData() {
        SharedPreferences share= getSharedPreferences("serviceFlagFile", Context.MODE_PRIVATE);
        boolean b=share.getBoolean("serviceFlag", false);
        return b;
    }

    public void saveData(boolean b) {
        SharedPreferences sharedPreferences = getSharedPreferences("serviceFlagFile", Context.MODE_PRIVATE); //私有数据
        SharedPreferences.Editor editor = sharedPreferences.edit();//获取编辑器
        editor.putBoolean("serviceFlag", b);
        editor.commit();//提交修改
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add:
                BzbToast.showToast(this, "add");
                break;
//            case R.id.remove_item:
//                adapter.removeData(1);
//                break;
        }
        return true;
    }
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
}
