package ru.yandex.yamblz.ui.fragments;

/**
 * Created by Aleksandra on 29/07/16.
 */
interface ItemTouchHelperAdapter {

    boolean onItemMove(int fromPosition, int toPosition);

    void onItemDismiss(int position);
}
