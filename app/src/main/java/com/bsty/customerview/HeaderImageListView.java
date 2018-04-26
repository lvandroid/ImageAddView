package com.bsty.customerview;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

public class HeaderImageListView extends LinearLayout implements ItemListener, View.OnClickListener {
    HeaderImageAdapter adapter;
    private Context context;
    private SimpleDraweeView bigImgView;
    private RecyclerView recyclerView;
    LayoutInflater layoutInflater;
    private int focusPosition;

    private TextView btnDel;
    private TextView btnRMove;
    private TextView btnLMove;
    private TextView btnReplace;
    private TextView tvImgDes;

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


        btnDel = mainView.findViewById(R.id.tv_delete);
        btnLMove = mainView.findViewById(R.id.tv_left_move);
        btnRMove = mainView.findViewById(R.id.tv_right_move);
        btnReplace = mainView.findViewById(R.id.tv_replace);
        tvImgDes = mainView.findViewById(R.id.tv_img_des);

        btnDel.setOnClickListener(this);
        btnLMove.setOnClickListener(this);
        btnRMove.setOnClickListener(this);
        btnReplace.setOnClickListener(this);
    }

    public void setData(List<Uri> data) {
        if (adapter != null) {
            adapter.setData(data);
            focusPosition = 0;
            showBigImg(data.get(0), focusPosition);
        }
    }

    public void addItem(Uri uri) {
        if (adapter != null) {
            adapter.addItem(uri);
            focusPosition = adapter.getDataCount() - 1;
            showBigImg(uri, focusPosition);
        }
    }

    @Override
    public void showBigImg(Uri uri, int focus) {
        if (bigImgView != null) {
            bigImgView.setImageURI(uri);
            focusPosition = focus;
        }
        setBtnColor(focus);
        setBigImgDes();
    }


    @Override
    public void addImg() {
        Toast.makeText(context, "添加图片", Toast.LENGTH_SHORT).show();
        addItem(Uri.parse("http://img.taopic.com/uploads/allimg/120707/201807-120FH3415789.jpg"));
    }

    /**
     * 设置操作按钮字体颜色
     *
     * @param focus
     */
    @Override
    public void setBtnColor(int focus) {
        //设置btn颜色
        if (adapter.getDataCount() == 0) {
            btnDel.setTextColor(Color.GRAY);
            btnReplace.setTextColor(Color.GRAY);
            btnLMove.setTextColor(Color.GRAY);
            btnRMove.setTextColor(Color.GRAY);
        } else {
            btnDel.setTextColor(Color.RED);
            btnReplace.setTextColor(Color.BLUE);
            if (focus == 0) {
                btnLMove.setTextColor(Color.GRAY);
                btnRMove.setTextColor(Color.BLUE);
            } else if (focus == adapter.getDataCount() - 1) {
                btnRMove.setTextColor(Color.GRAY);
                btnLMove.setTextColor(Color.BLUE);
            } else {
                btnLMove.setTextColor(Color.BLUE);
                btnRMove.setTextColor(Color.BLUE);
            }
        }
    }

    /**
     * 设置大图描述信息
     */
    @Override
    public void setBigImgDes() {
        if (adapter.getDataCount() == 0) {
            tvImgDes.setVisibility(GONE);
        } else {
            tvImgDes.setVisibility(VISIBLE);
        }
        tvImgDes.setText(String.format("共%d张， 当前第%d张", adapter.getDataCount(), focusPosition + 1));
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_delete:
                adapter.remove(focusPosition);
                break;
            case R.id.tv_replace:
                adapter.replace(null, focusPosition);
                break;
            case R.id.tv_left_move:
                adapter.leftMove(focusPosition);
                break;
            case R.id.tv_right_move:
                adapter.rightMove(focusPosition);
                break;
        }
    }
}
