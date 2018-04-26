package com.bsty.customerview;

import android.net.Uri;

public class BottomItemVo {
    private int index;
    private Uri uri;

    public BottomItemVo(int index, Uri uri) {
        this.index = index;
        this.uri = uri;
    }

    @Override
    public String toString() {
        return "BottomItemVo{" +
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
