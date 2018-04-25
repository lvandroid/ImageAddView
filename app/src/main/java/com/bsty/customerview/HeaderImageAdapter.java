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

    public void leftMove(int position) {

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
            itemListener.setBtnColor(position);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (itemListener != null) {
                        itemListener.showBigImg(data.get(position));
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
