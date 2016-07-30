package ru.yandex.yamblz.ui.fragments;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import ru.yandex.yamblz.R;

public class ContentAdapter extends RecyclerView.Adapter<ContentAdapter.ContentHolder> implements ItemTouchHelperAdapter {
    private final Random rnd = new Random();
    private final List<Integer> colors = new ArrayList<>();

    @Override
    public ContentHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.content_item, parent, false);
        ContentHolder h = new ContentHolder(v);
        v.setOnClickListener(v1 -> {
            int position = h.getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                colors.set(position, Color.rgb(rnd.nextInt(255), rnd.nextInt(255), rnd.nextInt(255)));
                notifyItemChanged(position);
            }
        });
        return h;
    }

    @Override
    public void onBindViewHolder(ContentHolder holder, int position) {
        holder.bind(createColorForPosition(position));
    }

    @Override
    public int getItemCount() {
        return Integer.MAX_VALUE;
    }

    private Integer createColorForPosition(int position) {
        if (position >= colors.size()) {
            colors.add(Color.rgb(rnd.nextInt(255), rnd.nextInt(255), rnd.nextInt(255)));
        }
        return colors.get(position);
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {

        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; ++i) {
                Collections.swap(colors, i, i + 1);
            }
        } else {
            for (int i = fromPosition; i > toPosition; --i) {
                Collections.swap(colors, i, i - 1);
            }
        }
        notifyItemMoved(fromPosition, toPosition);
        return true;
    }

    @Override
    public void onItemDismiss(int position) {
        colors.remove(position);
        notifyItemRemoved(position);
    }

    public static class ContentHolder extends RecyclerView.ViewHolder {
        ContentHolder(View itemView) {
            super(itemView);
        }

        void bind(Integer color) {
            itemView.setBackgroundColor(color);
            ((TextView) itemView).setText("#".concat(Integer.toHexString(color).substring(2)));
        }
    }
}
