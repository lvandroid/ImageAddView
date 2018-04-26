package com.bsty.customerview;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

public class HeaderImageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_CONTENT = 1;
    private static final int TYPE_FOOTER = 2;


    private static final int MAX = 5;

    private List<Uri> data;


    private ItemListener itemListener;
    private Context context;
    private LayoutInflater layoutInflater;

    public void setItemListener(ItemListener itemListener) {
        this.itemListener = itemListener;
    }

    public int getDataCount() {
        if (data == null) return 0;
        return data.size();
    }

    private boolean hasFooter() {
        if (getDataCount() >= MAX) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (hasFooter() && position == getDataCount()) {
            return TYPE_FOOTER;
        } else {
            return TYPE_CONTENT;
        }
    }

    public HeaderImageAdapter(List<Uri> data, Context context) {
        this.data = data;
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
    }

    public void setData(List<Uri> data) {
        this.data.addAll(data);
        notifyDataSetChanged();
    }

    public void addItem(Uri uri) {
        this.data.add(uri);
        notifyDataSetChanged();
    }

    /**
     * 向左移动，第一张不可移动
     *
     * @param position
     */
    public void leftMove(int position) {
        if (position == 0) {
            return;
        }
        Uri tmp = data.get(position - 1);
        data.set(position - 1, data.get(position));
        data.set(position, tmp);
        itemListener.showBigImg(data.get(position), position);
        notifyDataSetChanged();
    }

    /**
     * 向右移动图片，只有一张或者最后一张不可移动
     *
     * @param position
     */
    public void rightMove(int position) {
        if (getDataCount()<=1)return;
        if ( position == getDataCount() - 1) {
            return;
        }
        Uri tmp = data.get(position + 1);
        data.set(position + 1, data.get(position));
        data.set(position, tmp);
        itemListener.showBigImg(data.get(position), position);
        notifyDataSetChanged();
    }

    public void replace(Uri uri, int position) {
        if (data == null) {
            return;
        }
        data.set(position, uri);
        notifyItemChanged(position);
    }

    public void remove(int position) {
        if (data.size() == 0)
            return;
        data.remove(position);
        //删除第一个位置时，焦点变为第二个

//        notifyItemRemoved(position);
        notifyDataSetChanged();
        if (itemListener == null) {
            return;
        }
        //如果删除后列表回空，清除大图片显示
        if (data.size() == 0) {
            itemListener.showBigImg(null, 0);
        } else if (position == data.size()) { //若果删除最后一张，则大图显示上一张图片
            itemListener.showBigImg(data.get(position - 1), position - 1);
        } else { //其他情况大图展示下一张图片
            itemListener.showBigImg(data.get(position), position);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TYPE_CONTENT) {
            return new ContentViewHolder(layoutInflater.inflate(R.layout.item_img, parent, false));
        } else if (viewType == TYPE_FOOTER) {
            return new FooterViewHolder(layoutInflater.inflate(R.layout.item_footer, parent, false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof ContentViewHolder) {
            SimpleDraweeView simpleDraweeView = ((ContentViewHolder) holder).imageView;
            simpleDraweeView.setImageURI(data.get(position));
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (itemListener != null) {
                        itemListener.showBigImg(data.get(position), position);
                    }
                }
            });
        } else if (holder instanceof FooterViewHolder) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (itemListener != null) {
                        itemListener.addImg();
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return hasFooter() ? getDataCount() + 1 : getDataCount();
    }

    public static class ContentViewHolder extends RecyclerView.ViewHolder {
        public SimpleDraweeView imageView;

        public ContentViewHolder(View itemView) {
            super(itemView);
            this.imageView = itemView.findViewById(R.id.iv_item);
        }
    }

    public static class FooterViewHolder extends RecyclerView.ViewHolder {

        public FooterViewHolder(View itemView) {
            super(itemView);
        }
    }
}
