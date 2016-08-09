package ru.yandex.yamblz.ui.decorators;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Aleksandra on 29/07/16.
 */
public class BorderItemDecoration extends RecyclerView.ItemDecoration {
    private static final String DEBUG_TAG = BorderItemDecoration.class.getName();
    private static final int ALPHA = 127;
    private final Paint paint;
    /** In pixels */
    private final int borderWidth;


    public BorderItemDecoration(int borderWidth) {
        this.borderWidth = borderWidth;

        paint = new Paint();
        paint.setAlpha(ALPHA);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(borderWidth);
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {

        final int childCount = parent.getChildCount();

        for (int i = 0; i < childCount; ++i) {
            View child = parent.getChildAt(i);
            int position = parent.getChildAdapterPosition(child);
            if (position % 2 == 0) {
                RecyclerView.LayoutManager lm = parent.getLayoutManager();

                final int top = lm.getDecoratedTop(child) + borderWidth / 2;
                final int bottom = lm.getDecoratedBottom(child) - borderWidth / 2;
                final int left = lm.getDecoratedLeft(child) + borderWidth / 2;
                final int right = lm.getDecoratedRight(child) - borderWidth / 2;

                c.drawRect(left, top, right, bottom, paint);
            }
        }

    }
}
