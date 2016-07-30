package ru.yandex.yamblz.ui.fragments;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;

import ru.yandex.yamblz.ui.decorators.LastTwoDecorator;

/**
 * Created by Aleksandra on 29/07/16.
 */
public class MyItemTouchHelperCallback extends ItemTouchHelper.Callback {
    private static final String DEBUG_TAG = MyItemTouchHelperCallback.class.getName();
    private final LastTwoDecorator lastTwoDecorator;
    private final ItemTouchHelperAdapter adapter;
    private final Paint paint;

    private int from = -1;
    private int to = -1;


    public MyItemTouchHelperCallback(ItemTouchHelperAdapter adapter, LastTwoDecorator lastTwoDecorator) {
        this.adapter = adapter;
        paint = new Paint();
        paint.setColor(Color.RED);

        this.lastTwoDecorator = lastTwoDecorator;
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
    public void onMoved(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, int fromPos, RecyclerView.ViewHolder target, int toPos, int x, int y) {
        super.onMoved(recyclerView, viewHolder, fromPos, target, toPos, x, y);

        if (from == -1) {
            from = fromPos;
        }

        to = toPos;
        lastTwoDecorator.setPositionFrom(from);
        lastTwoDecorator.setPositionTo(to);
    }

    @Override
    public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        super.clearView(recyclerView, viewHolder);
        from = -1;
        to = -1;
    }


    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        lastTwoDecorator.setPositionFrom(-1);
        lastTwoDecorator.setPositionTo(-1);
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
