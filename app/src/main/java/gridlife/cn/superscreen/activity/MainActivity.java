package gridlife.cn.superscreen.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.IdRes;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.gridlife.bzblibrary.utils.BzbToast;
import cn.gridlife.bzblibrary.utils.ServiceUtils;
import cn.gridlife.bzblibrary.utils.Utils;
import gridlife.cn.superscreen.R;
import gridlife.cn.superscreen.activity.base.BaseActivity;
import gridlife.cn.superscreen.bean.MoveType;
import gridlife.cn.superscreen.bean.ShowType;
import gridlife.cn.superscreen.bean.SmallViewParameter;
import gridlife.cn.superscreen.manger.MyWindowManager;
import gridlife.cn.superscreen.service.FloatWindowService;

public class MainActivity extends BaseActivity implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {
    private boolean serviceFlag = false;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.et_show_text)
    EditText etShowText;
    @BindView(R.id.rb_static)
    RadioButton rbStatic;

    @BindView(R.id.rb_show_text)
    RadioButton rbShowText;
    @BindView(R.id.rb_show_image)
    RadioButton rbShowImage;
    @BindView(R.id.iv_show_image)
    ImageView ivShowImage;

    @BindView(R.id.rg_select_content)
    RadioGroup rgSelectContent;

    @BindView(R.id.rb_show_time)
    RadioButton rbShowTime;

    @BindView(R.id.rb_show_memory)
    RadioButton rbShowMemory;

    @BindView(R.id.rg_move_type)
    RadioGroup rgMoveType;

    @BindView(R.id.rb_horizon_move)
    RadioButton rbHorizonMove;

    @BindView(R.id.rb_vertical_move)
    RadioButton rbVerticalMove;

    @BindView(R.id.rb_random_line_move)
    RadioButton rbRandomLineMove;

    @BindView(R.id.rb_random_dot_move)
    RadioButton rbRandomDotMove;

    @BindView(R.id.rb_can_be_touch)
    RadioButton rbCanBeTouch;
    private String sShowText;
    private static int screenWidth;
    private static int screenHeight;
    private SmallViewParameter parameter;
    public int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//        serviceFlag= ServiceUtils.isServiceRunning("FloatWindowService");
        if (serviceFlag) {
            fab.setImageResource(R.drawable.vector_drawable_switch_open);
        } else {
            fab.setImageResource(R.drawable.vector_drawable_switch_close);
        }
        fab.setOnClickListener(this);
        rgSelectContent.setOnCheckedChangeListener(this);
        rgMoveType.setOnCheckedChangeListener(this);
    }

    @Override
    protected void initData() {
        Utils.init(getApplicationContext());
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        screenHeight = displayMetrics.heightPixels;
        screenWidth = displayMetrics.widthPixels;
        parameter = new SmallViewParameter();
        ButterKnife.bind(this);

        sShowText = etShowText.getText() == null ? "null" : etShowText.getText().toString();
    }

    public void openFloatView(View view) {

        Intent intent = new Intent(MainActivity.this, FloatWindowService.class);
        intent.putExtra("windowParameter",parameter);
        startService(intent);
        ((FloatingActionButton) view).setImageResource(R.drawable.vector_drawable_switch_open);
    }

    public void stopFloatView(View view) {
        Intent intent = new Intent(MainActivity.this, FloatWindowService.class);
        stopService(intent);
        ((FloatingActionButton) view).setImageResource(R.drawable.vector_drawable_switch_close);
    }

    public void toImage(View v) {
        startActivity(new Intent(this, TestTransformMatrixActivity.class));
    }

    public boolean getLocalData() {
        SharedPreferences share = getSharedPreferences("serviceFlagFile", Context.MODE_PRIVATE);
        return share.getBoolean("serviceFlag", false);
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
        }
        return true;
    }

    public static int getRandomWidthNum() {
        return Math.abs(new Random().nextInt() % screenWidth);
    }

    public static int getRandomHeightNum() {
        return Math.abs(new Random().nextInt() % screenWidth);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fab:
                if (serviceFlag == false) {
                    BzbToast.showToast(MainActivity.this, "open");
                    openFloatView(fab);
                    serviceFlag = true;
                } else {
                    BzbToast.showToast(MainActivity.this, "close");
                    stopFloatView(fab);
                    serviceFlag = false;
                }
                break;
            case R.id.iv_show_image:
                BzbToast.showToast(this, "选择照片");
                break;
        }
    }

    MoveType moveType = MoveType.PERMEMENTMOVE;
    ShowType showType=ShowType.TEXT;
    @Override
    public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
        if(ServiceUtils.isServiceRunning("FloatWindowService")){
            stopFloatView(fab);
        }
        switch (radioGroup.getId()) {
            case R.id.rg_select_content:
                if (rbShowText.isChecked()) {
                    showType=ShowType.TEXT;
                    sShowText = etShowText.getText() == null ? "null" : etShowText.getText().toString();
                } else if (rbShowTime.isChecked()) {
                    showType=ShowType.TIME;
                    sShowText = MyWindowManager.getSystemTime();
                } else if (rbShowMemory.isChecked()) {
                    showType=ShowType.MEMORY;
                    sShowText = MyWindowManager.getUsedPercentValue(getApplicationContext());
                } else {
                    showType=ShowType.IMAGE;
                    BzbToast.showToast(this, "Image");
                }
                parameter.setShowType(showType);
                parameter.setShowText(sShowText);
                break;
            case R.id.rg_move_type:

                if (rbStatic.isChecked()) {
                    BzbToast.showToast(MainActivity.this, "Static");
                    moveType = MoveType.PERMEMENTMOVE;

                } else if (rbHorizonMove.isChecked()) {
                    moveType = MoveType.HORIZONMOVE;
                    BzbToast.showToast(this, "Horizon");
                } else if (rbVerticalMove.isChecked()) {
                    moveType = MoveType.VERTICALMOVE;
                    BzbToast.showToast(this, "Horizon");

                } else if (rbRandomDotMove.isChecked()) {
                    moveType = MoveType.RANDOMDOTMOVE;
                    BzbToast.showToast(this, "RandomDot");
                } else if (rbRandomLineMove.isChecked()) {
                    moveType = MoveType.RANDOMLINEMOVE;
                    BzbToast.showToast(this, "RandomLine");
                } else if (rbCanBeTouch.isChecked()) {
                    moveType = MoveType.CANBETOUCHED;
                    BzbToast.showToast(this, "CanBeTouched");
                }
                parameter.setMoveType(moveType);
                break;

        }
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
