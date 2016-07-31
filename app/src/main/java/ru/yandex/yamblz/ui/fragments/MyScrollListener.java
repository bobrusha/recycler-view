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
        oldFirst = gridLayoutManager.findFirstVisibleItemPosition();
        oldLast = gridLayoutManager.findLastVisibleItemPosition();
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        Log.d(DEBUG_TAG, "dx " + dx + " dy" + dy);

        int newFirst = gridLayoutManager.findFirstVisibleItemPosition();
        int newLast = gridLayoutManager.findLastVisibleItemPosition();
        int spanCount = gridLayoutManager.getSpanCount();

        // dy < – up, dy > 0 - down
        if (dy < 0) {
            if (oldFirst != newFirst) {
                for (int adapterPos = newFirst; adapterPos < newFirst + spanCount; adapterPos++) {
                    recyclerView.getAdapter().notifyItemRemoved(adapterPos);
                    recyclerView.getAdapter().notifyItemInserted(adapterPos);
                }
            }
        } else {
            if (oldLast != newLast) {
                for (int adapterPos = newLast - spanCount + 1; adapterPos <= newLast; adapterPos++) {
                    recyclerView.getAdapter().notifyItemRemoved(adapterPos);
                    recyclerView.getAdapter().notifyItemInserted(adapterPos);
                }
            }
        }
        oldFirst = newFirst;
        oldLast = newLast;
    }

}
