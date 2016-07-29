package ru.yandex.yamblz.ui.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ru.yandex.yamblz.R;

public class ContentFragment extends BaseFragment {
    public static final String DEBUG_TAG = ContentFragment.class.getName();
    private static final String NUMBER_OF_COLUMNS_KEY = "NUMBER_OF_COLUMNS_KEY";
    private static final String STYLE_KEY = "STYLE_KEY";
    private static final int DEFAULT_COLUMNS_NUMBER = 1;

    private GridLayoutManager layoutManager;

    private RecyclerView.ItemDecoration decoration;

    private int numberOfColumns = DEFAULT_COLUMNS_NUMBER;

    private boolean bordersIsShown = false;

    private ContentAdapter adapter;

    @BindView(R.id.rv)
    RecyclerView rv;

    @BindView(R.id.fab)
    FloatingActionButton fab;

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
            bordersIsShown = savedInstanceState.getBoolean(STYLE_KEY, false);
        }

        layoutManager = new GridLayoutManager(getContext(), numberOfColumns);
        rv.setLayoutManager(layoutManager);

        adapter = new ContentAdapter();
        rv.setAdapter(adapter);

        ItemTouchHelper.Callback callback =
                new MyItemTouchHelperCallback(adapter);
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(rv);

        decoration = new BorderItemDecorator();
        if (bordersIsShown) {
            rv.addItemDecoration(decoration);
        }
        setImageToFab();
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
        outState.putBoolean(STYLE_KEY, bordersIsShown);
    }


    @OnClick(R.id.fab)
    public void onClick() {
        Log.v(DEBUG_TAG, "in onClick");
        if (bordersIsShown) {
            bordersIsShown = false;
            rv.removeItemDecoration(decoration);
        } else {
            bordersIsShown = true;
            rv.addItemDecoration(decoration);
        }
        setImageToFab();
    }

    public void setImageToFab() {
        if (bordersIsShown) {
            fab.setImageDrawable(getResources().getDrawable(R.drawable.ic_border_clear_white_24dp));
        } else {
            fab.setImageDrawable(getResources().getDrawable(R.drawable.ic_border_outer_white_24dp));
        }
    }


}
