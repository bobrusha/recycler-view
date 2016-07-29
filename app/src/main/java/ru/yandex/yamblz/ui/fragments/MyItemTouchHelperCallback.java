package ru.yandex.yamblz.ui.fragments;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;

/**
 * Created by Aleksandra on 29/07/16.
 */
public class MyItemTouchHelperCallback extends ItemTouchHelper.Callback {
    private static final String DEBUG_TAG = MyItemTouchHelperCallback.class.getName();
    private final ItemTouchHelperAdapter adapter;
    private final Paint paint;

    public MyItemTouchHelperCallback(ItemTouchHelperAdapter adapter) {
        this.adapter = adapter;
        paint = new Paint();
        paint.setColor(Color.RED);
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.START | ItemTouchHelper.END;
        int swipeFlags = ItemTouchHelper.END;
        return makeMovementFlags(dragFlags, swipeFlags);
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        return adapter.onItemMove(viewHolder.getAdapterPosition(), target.getAdapterPosition());
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        adapter.onItemDismiss(viewHolder.getAdapterPosition());
    }


    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
            View iv = viewHolder.itemView;
            if (dX > 0) {
                Log.d(DEBUG_TAG, "in dx > 0");

                RecyclerView.LayoutManager lm = recyclerView.getLayoutManager();
                float left = lm.getDecoratedLeft(iv);
                float top = lm.getDecoratedTop(iv);
                float right = left + dX;
                float bottom = lm.getDecoratedBottom(iv);
                RectF background = new RectF(left, top, right, bottom);

                int alpha = (int) Math.min(255 * dX / iv.getWidth(), 255);
                paint.setAlpha(alpha);

                c.drawRect(background, paint);
            }
        }
    }
}
