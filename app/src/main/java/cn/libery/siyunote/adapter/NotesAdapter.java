package cn.libery.siyunote.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import cn.libery.siyunote.R;
import cn.libery.siyunote.db.EventRecord;

/**
 * Created by Libery on 2015/12/3.
 * Email:libery.szq@qq.com
 */
public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.ItemViewHolder> {

    private List<EventRecord> records;
    private OnItemClickListener onItemClickListener;

    public NotesAdapter(List<EventRecord> records) {
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
        if ("1".equals(records.get(position).getType())) {
            holder.imageView.setBackgroundResource(R.color.colorAccent);
        } else {
            holder.imageView.setBackgroundResource(R.color.colorPrimary);
        }
        holder.title.setText(records.get(position).getContent());
        holder.menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return records.size();
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public ImageView imageView;
        public TextView title;
        public TextView menu;
        private OnItemClickListener onItemClickListener;

        public ItemViewHolder(View itemView, OnItemClickListener onItemClickListener) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.note_type);
            title = (TextView) itemView.findViewById(R.id.note_title);
            menu = (TextView) itemView.findViewById(R.id.note_menu);
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
