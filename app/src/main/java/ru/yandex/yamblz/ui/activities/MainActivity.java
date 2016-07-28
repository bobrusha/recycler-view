package ru.yandex.yamblz.ui.activities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;

import javax.inject.Inject;
import javax.inject.Named;

import ru.yandex.yamblz.App;
import ru.yandex.yamblz.R;
import ru.yandex.yamblz.developer_settings.DeveloperSettingsModule;
import ru.yandex.yamblz.ui.fragments.ContentFragment;
import ru.yandex.yamblz.ui.other.ViewModifier;

public class MainActivity extends BaseActivity {

    @Inject
    @Named(DeveloperSettingsModule.MAIN_ACTIVITY_VIEW_MODIFIER)
    ViewModifier viewModifier;

    @SuppressLint("InflateParams") // It's okay in our case.
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App.get(this).applicationComponent().inject(this);

        setContentView(viewModifier.modify(getLayoutInflater().inflate(R.layout.activity_main, null)));

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.main_frame_layout, new ContentFragment(), "qq")
                    .commit();
        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        ContentFragment fragment = (ContentFragment) getSupportFragmentManager().findFragmentByTag("qq");
        if (fragment == null) {
            return super.onKeyDown(keyCode, event);
        }
        switch (event.getKeyCode()) {
            case KeyEvent.KEYCODE_VOLUME_UP:
                fragment.incrementNumberOfColumns();
                return true;
            case KeyEvent.KEYCODE_VOLUME_DOWN:
                fragment.decrementNumberOfColumns();
                return true;
            default:
                return super.onKeyDown(keyCode, event);
        }
    }
}
