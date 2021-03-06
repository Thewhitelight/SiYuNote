package cn.libery.siyunote.adapter;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import cn.libery.siyunote.R;
import cn.libery.siyunote.db.EventRecord;
import cn.libery.siyunote.utils.ImageLoaderOptions;

/**
 * Created by Libery on 2015/12/3.
 * Email:libery.szq@qq.com
 */
public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.ItemViewHolder> {


    private List<EventRecord> records;
    private OnItemClickListener onItemClickListener;

    public NotesAdapter() {
    }

    public void setRecords(List<EventRecord> records) {
        this.records = records;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_note, parent, false);
        return new ItemViewHolder(view, onItemClickListener);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        if (records.get(position).getType() == 0) {
            holder.imageView.setBackgroundResource(R.color.colorAccent);
        } else if (records.get(position).getType() == 1) {
            holder.imageView.setBackgroundResource(R.color.colorPrimary);
        } else {
            holder.imageView.setBackgroundResource(R.color.colorPrimaryDark);
        }
        if (!TextUtils.isEmpty(records.get(position).getPictures())) {
            holder.pictures.setImageResource(R.drawable.ic_picture);
            ImageLoader.getInstance().displayImage(records.get(position).getPictures().split(",")[0], holder.pictures, ImageLoaderOptions.getOptions());
        } else {
            holder.pictures.setBackgroundDrawable(null);
        }
        holder.title.setText(records.get(position).getContent());
    }

    @Override
    public int getItemCount() {
        return records.size();
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public ImageView imageView;
        public TextView title;
        public ImageView pictures;
        private OnItemClickListener onItemClickListener;

        public ItemViewHolder(View itemView, OnItemClickListener onItemClickListener) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.note_type);
            title = (TextView) itemView.findViewById(R.id.note_title);
            pictures = (ImageView) itemView.findViewById(R.id.note_pictures);
            this.onItemClickListener = onItemClickListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(v, getAdapterPosition());
            }
        }
    }
}
