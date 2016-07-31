package ru.yandex.yamblz.ui.fragments;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

/**
 * Created by Aleksandra on 31/07/16.
 */
public class MyScrollListener extends RecyclerView.OnScrollListener {
    private static final String DEBUG_TAG = MyScrollListener.class.getName();

    private final GridLayoutManager gridLayoutManager;
    private int oldFirst;
    private int oldLast;


    public MyScrollListener(GridLayoutManager gridLayoutManager) {
        this.gridLayoutManager = gridLayoutManager;
    }

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        Log.d(DEBUG_TAG, "x " + dx + " dy" + dy);
        recyclerView.getAdapter().notifyItemInserted(gridLayoutManager.findLastVisibleItemPosition());
    }

}
