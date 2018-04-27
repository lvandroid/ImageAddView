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

import java.util.List;

public class BottomMultiPicView extends LinearLayout {
    private Context context;
    private LayoutInflater layoutInflater;
    private RecyclerView recyclerView;
    private BottomMultiPicAdapter adapter;
    private int initNum = 5;

    public BottomMultiPicView(Context context) {
        this(context, null);
    }

    public BottomMultiPicView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BottomMultiPicView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
        init();
    }

    private void init() {
        View mainView = layoutInflater.inflate(R.layout.view_bottom_list, null, false);
        recyclerView = mainView.findViewById(R.id.rv_bottom_img_list);

        adapter = new BottomMultiPicAdapter(context);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(VERTICAL);

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
        addView(mainView);
    }

    public void insertData(Uri uri, int position) {
        if (adapter == null) {
            return;
        }
        adapter.insertData(uri, position);
    }

    public void setData(List<BottomMultiPicItemVo> uriList) {
        if (adapter == null) {
            return;
        }
        adapter.addAllData(uriList);
    }

}
