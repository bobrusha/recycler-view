package ru.yandex.yamblz.ui.fragments;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Aleksandra on 29/07/16.
 */
public class BorderItemDecorator extends RecyclerView.ItemDecoration {
    private static final String DEBUG_TAG = BorderItemDecorator.class.getName();
    private static final int BORDER_WIDTH = 16;
    private static final int ALPHA = 127;
    private final Paint paint;


    public BorderItemDecorator() {
        paint = new Paint();
        paint.setStrokeWidth(BORDER_WIDTH);
        paint.setAlpha(ALPHA);
        paint.setStyle(Paint.Style.STROKE);
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {

        final int childCount = parent.getChildCount();

        for (int i = 0; i < childCount; i = i + 2) {
            View child = parent.getChildAt(i);
            RecyclerView.LayoutManager lm = parent.getLayoutManager();

            final int top = lm.getDecoratedTop(child) + BORDER_WIDTH / 2;
            final int bottom = lm.getDecoratedBottom(child) - BORDER_WIDTH / 2;
            final int left = lm.getDecoratedLeft(child) + BORDER_WIDTH / 2;
            final int right = lm.getDecoratedRight(child) - BORDER_WIDTH / 2;

            c.drawRect(left, top, right, bottom, paint);
        }

    }
}
