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
import java.util.List;

public class BottomMultiPicAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Uri> data = new ArrayList<>();
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

    public BottomMultiPicAdapter(Context context) {
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
        initData();
    }


    /**
     * head列表中是否包含数据(默认5个header）
     *
     * @param position
     * @return
     */
    public boolean headHasData(int position) {
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

    public void initData() {
        if (data.size() > 0) {
            data.clear();
        }
        for (int i = 0; i < INIT_NUM; i++) {
            headerContainer[i] = false;
            data.add(i, null);
        }
    }

    public void addAllData(List<BottomMultiPicItemVo> datas) {
        if (datas == null) return;
        initData();
        for (int i = 0; i < datas.size(); i++) {
            BottomMultiPicItemVo itemVo = datas.get(i);
            if (datas.size() <= INIT_NUM) {
                data.set(itemVo.getIndex(), itemVo.getUri());
                headerContainer[itemVo.getIndex()] = true;
            } else {
                if (i < INIT_NUM) {
                    headerContainer[i] = true;
                }
                data.add(itemVo.getUri());
            }
        }

        notifyDataSetChanged();
    }


    public void insertData(Uri uri, int position) {
        if (data == null) {
            return;
        }
        if (position < INIT_NUM) {
            headerContainer[position] = true;
            data.set(position, uri);
        } else {
            data.add(position, uri);
        }

        notifyDataSetChanged();
    }

    public void rm(int position) {
        if (getDataCount() > INIT_NUM) {
            data.remove(position);
            notifyDataSetChanged();
        } else {
            data.set(position, null);
            headerContainer[position] = false;
            notifyDataSetChanged();
        }

    }

    public void rep(int position, Uri uri) {
        data.set(position, uri);
        notifyItemChanged(position);
    }

    public void up(int position) {
        if (position == 0) {
            return;
        }

        Uri tmp = data.get(position - 1);
        boolean upBool = true;
        boolean downBool = true;
        if (position - 1 < INIT_NUM) {//如果小于5，记录下是否含有数据
            upBool = headerContainer[position - 1];
        }

        if (position < INIT_NUM) {
            downBool = headerContainer[position];
        }

        data.set(position - 1, data.get(position));
        if (position - 1 < INIT_NUM) {
            headerContainer[position - 1] = downBool;
        }
        data.set(position, tmp);
        if (position < INIT_NUM) {
            headerContainer[position] = upBool;
        }

        notifyDataSetChanged();

    }

    public void down(int position) {
        if (position == getDataCount() - 1) {
            return;
        }

        Uri tmp = data.get(position + 1);

        boolean upBool = true;
        boolean downBool = true;
        if (position < INIT_NUM) {//如果小于5，记录下是否含有数据
            upBool = headerContainer[position];
        }

        if (position + 1 < INIT_NUM) {
            downBool = headerContainer[position + 1];
        }

        data.set(position + 1, data.get(position));

        if (position + 1 < INIT_NUM) {
            headerContainer[position + 1] = upBool;
        }

        data.set(position, tmp);
        if (position < INIT_NUM) {
            headerContainer[position] = downBool;
        }

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
            ContentViewHolder contentViewHolder = (ContentViewHolder) holder;

            contentViewHolder.imageView.setImageURI(data.get(position));

            //设置按钮字体颜色
            setBtnStyle(contentViewHolder, position);

            contentViewHolder.setItemListener(new BottomMultiPicItemListener() {
                @Override
                public void delete() {
                    rm(position);
                }

                @Override
                public void replace() {
                    rep(position, null);
                }

                @Override
                public void upMove() {
                    up(position);
                }

                @Override
                public void downMove() {
                    down(position);
                }
            });

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


    private void setBtnStyle(ContentViewHolder holder, int position) {
        if (position == 0) {
            holder.tvUpMove.setTextColor(Color.GRAY);
            holder.tvDownMove.setTextColor(Color.BLUE);
        } else if (position == getDataCount() - 1) {
            holder.tvDownMove.setTextColor(Color.GRAY);
            holder.tvUpMove.setTextColor(Color.BLUE);
        } else {
            holder.tvDownMove.setTextColor(Color.BLUE);
            holder.tvUpMove.setTextColor(Color.BLUE);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (getDataCount() <= INIT_NUM && !headHasData(position)) {
            return TYPE_EMPTY;
        }

        if (getDataCount() > INIT_NUM && position == getDataCount()) {
            return TYPE_EMPTY;
        }

        if (getDataCount() == INIT_NUM && position == INIT_NUM) {
            return TYPE_EMPTY;
        }

        return TYPE_CONTENT;
    }

    @Override
    public int getItemCount() {
        if (hasHeaderEmpty()) {
            return INIT_NUM;
        }

        if (getDataCount() == MAX_NUM) {
            return MAX_NUM;
        }

        return getDataCount() + 1;

    }

    public static class ContentViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView tvDel;
        public TextView tvReplace;
        public TextView tvUpMove;
        public TextView tvDownMove;
        public SimpleDraweeView imageView;

        private BottomMultiPicItemListener itemListener;

        public void setItemListener(BottomMultiPicItemListener itemListener) {
            this.itemListener = itemListener;
        }

        public ContentViewHolder(View itemView) {
            super(itemView);
            tvDel = itemView.findViewById(R.id.tv_delete);
            tvReplace = itemView.findViewById(R.id.tv_replace);
            tvUpMove = itemView.findViewById(R.id.tv_up_move);
            tvDownMove = itemView.findViewById(R.id.tv_down_move);
            imageView = itemView.findViewById(R.id.iv_display);

            tvDel.setOnClickListener(this);
            tvReplace.setOnClickListener(this);
            tvUpMove.setOnClickListener(this);
            tvDownMove.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (itemListener == null) {
                return;
            }
            switch (v.getId()) {
                case R.id.tv_delete:
                    itemListener.delete();
                    break;
                case R.id.tv_replace:
                    itemListener.replace();
                    break;
                case R.id.tv_up_move:
                    itemListener.upMove();
                    break;
                case R.id.tv_down_move:
                    itemListener.downMove();
                    break;
            }
        }
    }

    public static class FooterViewHolder extends RecyclerView.ViewHolder {

        public FooterViewHolder(View itemView) {
            super(itemView);
        }
    }
}
