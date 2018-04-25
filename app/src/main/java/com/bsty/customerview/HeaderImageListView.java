package com.bsty.customerview;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

public class HeaderImageListView extends LinearLayout implements ItemListener {
    HeaderImageAdapter adapter;
    private Context context;
    private SimpleDraweeView bigImgView;
    private RecyclerView recyclerView;
    LayoutInflater layoutInflater;

    public HeaderImageListView(Context context) {
        this(context, null, 0);
    }

    public HeaderImageListView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HeaderImageListView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init();
    }

    private void init() {
        layoutInflater = LayoutInflater.from(context);
        View mainView = layoutInflater.inflate(R.layout.view_image_add, null, false);
        addView(mainView);
        bigImgView = mainView.findViewById(R.id.iv_display);
        recyclerView = mainView.findViewById(R.id.rv_image_list);

        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(HORIZONTAL);
        adapter = new HeaderImageAdapter(new ArrayList<Uri>(), context);
        adapter.setItemListener(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    public void setData(List<Uri> data) {
        if (adapter != null) {
            adapter.setData(data);
            showBigImg(data.get(0));
        }
    }

    public void addItem(Uri uri) {
        if (adapter != null) {
            adapter.addItem(uri);
            showBigImg(uri);
        }
    }

    @Override
    public void showBigImg(Uri uri) {
        if (bigImgView != null) {
            bigImgView.setImageURI(uri);
        }
    }

    @Override
    public void addImg() {
        Toast.makeText(context, "添加图片", Toast.LENGTH_SHORT).show();
        addItem(Uri.parse("http://img.taopic.com/uploads/allimg/120707/201807-120FH3415789.jpg"));
    }
}
