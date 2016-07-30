package ru.yandex.yamblz.ui.decorators;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

/**
 * Created by Aleksandra on 29/07/16.
 */
public class LastTwoDecorator extends RecyclerView.ItemDecoration {
    public static final String DEBUG_TAG = LastTwoDecorator.class.getName();

    private Paint paint;
    private int positionFrom = -1;
    private int positionTo = -1;

    public LastTwoDecorator() {
        paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.FILL);
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        if (positionTo != -1 && positionFrom != -1) {
            RecyclerView.ViewHolder to = parent.findViewHolderForLayoutPosition(positionTo);
            if (to != null) {
                drawMark(c, to.itemView, parent);
            }

            RecyclerView.ViewHolder from = parent.findViewHolderForLayoutPosition(positionFrom);
            if (from != null) {
                drawMark(c, from.itemView, parent);
            }
        }

    }

    public void drawMark(Canvas c, View child, RecyclerView parent) {
        Log.v(DEBUG_TAG, "on Draw Mark");
        RecyclerView.LayoutManager lm = parent.getLayoutManager();
        RectF rect = new RectF(
                lm.getDecoratedLeft(child),
                lm.getDecoratedTop(child),
                lm.getDecoratedRight(child),
                lm.getDecoratedBottom(child)
        );
        Log.d(DEBUG_TAG, rect.toString());
        c.drawOval(rect, paint);
    }


    public void setPositionFrom(int positionFrom) {
        this.positionFrom = positionFrom;
    }

    public void setPositionTo(int positionTo) {
        this.positionTo = positionTo;
    }
}
