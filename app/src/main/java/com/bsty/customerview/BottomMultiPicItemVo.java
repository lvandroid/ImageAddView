package com.bsty.customerview;

import android.net.Uri;

public class BottomMultiPicItemVo {
    private int index;
    private Uri uri;

    public BottomMultiPicItemVo(int index, Uri uri) {
        this.index = index;
        this.uri = uri;
    }

    @Override
    public String toString() {
        return "BottomMultiPicItemVo{" +
                "index=" + index +
                ", uri=" + uri +
                '}';
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public Uri getUri() {
        return uri;
    }

    public void setUri(Uri uri) {
        this.uri = uri;
    }
}
