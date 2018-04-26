package com.bsty.customerview;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class BottomImgAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Uri> data;
    private Context context;
    private LayoutInflater layoutInflater;
    private static final int MAX_NUM = 24; //最大的图片数
    private static final int INIT_NUM = 5; //初始化空位数

    private static final int TYPE_CONTENT = 1;
    private static final int TYPE_EMPTY = 2;
    //记录data前五个位置存储情况,默认全部置为空
    private boolean[] headerContainer = new boolean[]{false, false, false, false, false};

    public int getDataCount() {
        if (data == null) {
            return 0;
        }
        return data.size();
    }

    public BottomImgAdapter(List<Uri> data, Context context) {
        this.data = data;
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
    }

    /**
     * head列表中是否包含数据(默认5个header）
     *
     * @param position
     * @return
     */
    public boolean headHasData(int position) {
//        if (position > INIT_NUM - 1) {
//            throw new RuntimeException("超出预设的空位数量");
//        }
//        if (!hasHeaderEmpty()) {
//            return true;
//        }
        if (position < INIT_NUM) {
            return headerContainer[position];
        }
        return true;
    }

    /**
     * header是否包含空位
     *
     * @return
     */
    public boolean hasHeaderEmpty() {
        for (boolean b : headerContainer) {
            if (!b) return true;
        }
        return false;
    }

    public void setData(List<Uri> uris) {
        data.addAll(uris);
        for (int i = 0; i < uris.size(); i++) {
            headerContainer[i] = true;
        }
        notifyDataSetChanged();
    }

    public void insertData(Uri uri, int position) {
        if (data == null) {
            return;
        }
        if (position < INIT_NUM && position > getDataCount()) {
            for (int i = 0; i < position - getDataCount() + 1; i++) {
                data.add(getDataCount(), null);
            }
            headerContainer[position] = true;
        }
        data.add(position, uri);

        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case TYPE_CONTENT:
                return new ContentViewHolder(layoutInflater.inflate(R.layout.item_bottom_content,
                        parent, false));
            case TYPE_EMPTY:
                return new FooterViewHolder(layoutInflater.inflate(R.layout.item_bottom_footer,
                        parent, false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof ContentViewHolder) {
            ((ContentViewHolder) holder).imageView.setImageURI(data.get(position));
            if (position == 0) {
                ((ContentViewHolder) holder).tvUpMove.setTextColor(Color.GRAY);
            } else if (position == getDataCount() - 1) {
                ((ContentViewHolder) holder).tvDownMove.setTextColor(Color.GRAY);
            } else {
                ((ContentViewHolder) holder).tvDownMove.setTextColor(Color.BLUE);
                ((ContentViewHolder) holder).tvUpMove.setTextColor(Color.BLUE);
            }
        } else if (holder instanceof FooterViewHolder) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    insertData(Uri.parse("http://img.taopic.com/uploads/allimg/120707/201807-120FH3415789.jpg"),
                            position);
                    Toast.makeText(context, "添加图片 位置:" + position, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }


    @Override
    public int getItemViewType(int position) {
        if (getDataCount() <= INIT_NUM) {
            if (headHasData(position)) {
                return TYPE_CONTENT;
            } else {
                return TYPE_EMPTY;
            }
        } else if (getDataCount() < MAX_NUM && getDataCount() == position) {
            return TYPE_EMPTY;
        } else {
            return TYPE_CONTENT;
        }
    }

    @Override
    public int getItemCount() {
        if (getDataCount() < INIT_NUM) {
            return INIT_NUM;
        } else if (getDataCount() >= MAX_NUM) {
            return MAX_NUM;
        } else {
            return getDataCount() + 1;
        }
    }

    public static class ContentViewHolder extends RecyclerView.ViewHolder {
        public TextView tvDel;
        public TextView tvReplace;
        public TextView tvUpMove;
        public TextView tvDownMove;
        public SimpleDraweeView imageView;

        public ContentViewHolder(View itemView) {
            super(itemView);
            tvDel = itemView.findViewById(R.id.tv_delete);
            tvReplace = itemView.findViewById(R.id.tv_replace);
            tvUpMove = itemView.findViewById(R.id.tv_up_move);
            tvDownMove = itemView.findViewById(R.id.tv_down_move);
            imageView = itemView.findViewById(R.id.iv_display);
        }
    }

    public static class FooterViewHolder extends RecyclerView.ViewHolder {

        public FooterViewHolder(View itemView) {
            super(itemView);
        }
    }
}
