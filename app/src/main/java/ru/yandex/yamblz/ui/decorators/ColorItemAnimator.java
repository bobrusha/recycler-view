package ru.yandex.yamblz.ui.decorators;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ru.yandex.yamblz.ui.fragments.ContentAdapter;

/**
 * Created by Aleksandra on 30/07/16.
 */
public class ColorItemAnimator extends DefaultItemAnimator {
    public static final String DEBUG_TAG = ColorItemAnimator.class.getName();

    private final Map<RecyclerView.ViewHolder, Animator> animatorsMap = new HashMap<>();

    @Override
    public boolean animateChange(@NonNull RecyclerView.ViewHolder oldHolder,
                                 @NonNull final RecyclerView.ViewHolder newHolder,
                                 @NonNull ItemHolderInfo preInfo,
                                 @NonNull ItemHolderInfo postInfo) {
        final ContentAdapter.ContentHolder holder = (ContentAdapter.ContentHolder) newHolder;
        final ColorTextInfo preColorTextInfo = (ColorTextInfo) preInfo;
        final ColorTextInfo postColorTextInfo = (ColorTextInfo) postInfo;

        // setting animation
        ObjectAnimator oldTextRotate = ObjectAnimator.ofFloat(holder.itemView, View.ROTATION_X, 0, 90);
        oldTextRotate.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                holder.itemView.setBackgroundColor(preColorTextInfo.color);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                holder.itemView.setBackgroundColor(postColorTextInfo.color);

            }
        });
        oldTextRotate.setDuration(getChangeDuration() / 2);

        ObjectAnimator newTextRotate = ObjectAnimator.ofFloat(holder.itemView, View.ROTATION_X, 270, 360);
        newTextRotate.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                dispatchAnimationFinished(holder);
            }
        });
        newTextRotate.setDuration(getChangeDuration() / 2);

        AnimatorSet set = new AnimatorSet();
        set.play(oldTextRotate).before(newTextRotate);
        set.start();

        return super.animateChange(oldHolder, newHolder, preInfo, postInfo);
    }


    @Override
    public boolean canReuseUpdatedViewHolder(RecyclerView.ViewHolder viewHolder) {
        return true;
    }

    @NonNull
    @Override
    public ItemHolderInfo recordPreLayoutInformation(@NonNull RecyclerView.State state,
                                                     @NonNull RecyclerView.ViewHolder viewHolder,
                                                     int changeFlags,
                                                     @NonNull List<Object> payloads) {
        ColorTextInfo colorTextInfo = new ColorTextInfo();
        colorTextInfo.setFrom(viewHolder);
        return colorTextInfo;
    }

    @NonNull
    @Override
    public ItemHolderInfo recordPostLayoutInformation(@NonNull RecyclerView.State state, @NonNull RecyclerView.ViewHolder viewHolder) {
        ColorTextInfo colorTextInfo = new ColorTextInfo();
        colorTextInfo.setFrom(viewHolder);
        return colorTextInfo;
    }

    private class ColorTextInfo extends ItemHolderInfo {
        int color;

        @Override
        public ItemHolderInfo setFrom(RecyclerView.ViewHolder holder) {
            if (holder instanceof ContentAdapter.ContentHolder) {
                ContentAdapter.ContentHolder colorViewHolder = (ContentAdapter.ContentHolder) holder;
                color = ((ColorDrawable) colorViewHolder.itemView.getBackground()).getColor();
            }
            return super.setFrom(holder);
        }
    }
}
