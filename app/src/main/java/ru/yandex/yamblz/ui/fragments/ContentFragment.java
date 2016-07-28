package ru.yandex.yamblz.ui.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.yandex.yamblz.R;

public class ContentFragment extends BaseFragment {
    public static final String DEBUG_TAG = ContentFragment.class.getName();

    private static final String NUMBER_OF_COLUMNS_KEY = "NUMBER_OF_COLUMNS_KEY";
    private static final int DEFAULT_COLUMNS_NUMBER = 1;

    private GridLayoutManager layoutManager;
    private int numberOfColumns = DEFAULT_COLUMNS_NUMBER;

    private ContentAdapter adapter;

    @BindView(R.id.rv)
    RecyclerView rv;

    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_content, container, false);
        ButterKnife.bind(this, v);
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (savedInstanceState != null) {
            numberOfColumns = savedInstanceState.getInt(NUMBER_OF_COLUMNS_KEY, DEFAULT_COLUMNS_NUMBER);
        }

        layoutManager = new GridLayoutManager(getContext(), numberOfColumns);
        rv.setLayoutManager(layoutManager);

        adapter = new ContentAdapter();
        rv.setAdapter(adapter);
    }

    public void incrementNumberOfColumns() {
        Log.d(DEBUG_TAG, "in increment");
        ++numberOfColumns;
        layoutManager.setSpanCount(numberOfColumns);
        adapter.notifyDataSetChanged();
    }

    public void decrementNumberOfColumns() {
        Log.d(DEBUG_TAG, "in decrement");
        if (numberOfColumns > DEFAULT_COLUMNS_NUMBER) {
            --numberOfColumns;
            layoutManager.setSpanCount(numberOfColumns);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(NUMBER_OF_COLUMNS_KEY, numberOfColumns);
    }

}
