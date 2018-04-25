package com.bsty.customerview;

import android.net.Uri;

public interface ItemListener {
    void showBigImg(Uri uri);
    void addImg();
    void setBtnColor(int position);
}
