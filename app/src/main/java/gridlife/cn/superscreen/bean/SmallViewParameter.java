package gridlife.cn.superscreen.bean;

import android.graphics.Bitmap;
import android.media.Image;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * ProjectName SuperScreen
 * PackageName gridlife.cn.superscreen.bean
 * Created by damaren_bzb on 2017/7/6.
 */

public class SmallViewParameter implements Serializable {
    private String  contentType;
    private String  showText;
    private ShowType showType;
    private byte[] showImage;
    private MoveType moveType;

    public String getContentType() {
        return contentType;
    }

    public ShowType getShowType() {
        return showType;
    }

    public void setShowType(ShowType showType) {
        this.showType = showType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public byte[] getShowImage() {
        return showImage;
    }

    public void setShowImage(byte[] showImage) {
        this.showImage = showImage;
    }

    public String getShowText() {
        return showText;
    }

    public void setShowText(String showText) {
        this.showText = showText;
    }



    public MoveType getMoveType() {
        return moveType;
    }

    public void setMoveType(MoveType moveType) {
        this.moveType = moveType;
    }

}
