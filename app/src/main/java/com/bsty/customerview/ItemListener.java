package com.bsty.customerview;

import android.net.Uri;
import android.view.View;

public interface ItemListener {
    void showBigImg(Uri uri,int focus);
    void addImg();
    void setBtnColor(int focus);
    void setBigImgDes();
}
