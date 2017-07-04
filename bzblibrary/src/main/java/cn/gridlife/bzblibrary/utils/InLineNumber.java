package cn.gridlife.bzblibrary.utils;

import android.content.Context;

/**
 * ProjectName SuperScreen
 * PackageName cn.gridlife.bzblibrary.utils
 * Created by damaren_bzb on 2017/7/4.
 */

public class InLineNumber {
    int x1;//点1 x值
    int y1;//点1 y值
    int x2;//点2 x值
    int y2;//点2 y值
    int d;//方向
    double angle;
    Context context;

    public InLineNumber(Context context) {
        this.context = context;
        new InLineNumber(context,0,0, 0,0, 0);
    }

    public InLineNumber(Context context,int x1, int y1, int angle) {
        this.context=context;
        this.x1 = x1;
        this.y1 = y1;
        this.angle = angle;
    }

    public InLineNumber(Context context, int x1, int y1, int x2, int y2, int d) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.d = d;
        this.context = context;
    }

    /**
     * 获取下一个点的X值
     * @param speed 整数 ，数值越大 越快
     * @return
     */
    public int getNextPointX(int speed){

        if(d==0){
            if(speed<=0)
                speed=1;

        }
        return 0;
    }
}
