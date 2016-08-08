package ru.yandex.yamblz.ui;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import java.util.HashMap;

/**
 * Created by Aleksandra on 08/08/16.
 */
public class MyGridLayoutManager extends GridLayoutManager {
    public static final String DEBUG_TAG = MyGridLayoutManager.class.getName();

    private int oldFirstPos;
    private int oldLastPos;

    private HashMap<Integer, Animator> animatorsMap = new HashMap<>();

    public MyGridLayoutManager(Context context, int spanCount) {
        super(context, spanCount);
    }

    @Override
    public int scrollVerticallyBy(int dy, RecyclerView.Recycler recycler, RecyclerView.State state) {
        int result = super.scrollVerticallyBy(dy, recycler, state);

        int newFirstPos = findFirstVisibleItemPosition();
        int newLastPos = findLastVisibleItemPosition();

        if (dy < 0) {
            if (oldFirstPos != newFirstPos) {
                for (int pos = oldFirstPos; pos >= newFirstPos; --pos) {
                    animate(pos);
                }
            }
        } else {
            if (oldLastPos != newLastPos) {
                for (int pos = oldLastPos; pos <= newLastPos; ++pos) {
                    animate(pos);
                }
            }
        }

        oldFirstPos = newFirstPos;
        oldLastPos = newLastPos;

        return result;
    }


    public void animate(int position) {
        View v = findViewByPosition(position);
        if (v != null) {
            // setting animation
            ObjectAnimator animator = ObjectAnimator.ofFloat(v, View.ROTATION_X, 180, 360);
            animator.setDuration(500);
            animatorsMap.put(position, animator);
            animator.start();
        } else {
            Log.d(DEBUG_TAG, "null");
        }
    }

    @Override
    public void removeAndRecycleViewAt(int index, RecyclerView.Recycler recycler) {
        View v = getChildAt(index);
        if (v != null) {
            //ends animation of item decorator
            endAnimation(v);
            int pos = getPosition(v);
            cancelAnimation(pos);
        }
        super.removeAndRecycleViewAt(index, recycler);
    }

    @Override
    public void removeAndRecycleView(View child, RecyclerView.Recycler recycler) {
        endAnimation(child);
        cancelAnimation(getPosition(child));
        super.removeAndRecycleView(child, recycler);
    }

    public void cancelAnimation(int position) {
        Animator animator = animatorsMap.get(position);
        if (animator != null && animator.isRunning()) {
            animator.cancel();
            animatorsMap.remove(position);
        }
    }
}
