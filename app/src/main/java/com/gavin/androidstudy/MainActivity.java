package com.gavin.androidstudy;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.SearchEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityEvent;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Example of a call to a native method
        TextView tv = (TextView) findViewById(R.id.sample_text);
        tv.setText(stringFromJNI());

        getWindow().setCallback(new Window.Callback() {
            @Override
            public boolean dispatchKeyEvent(KeyEvent event) {
                return false;
            }

            @Override
            public boolean dispatchKeyShortcutEvent(KeyEvent event) {
                return false;
            }

            @Override
            public boolean dispatchTouchEvent(MotionEvent event) {

                return false;
            }

            @Override
            public boolean dispatchTrackballEvent(MotionEvent event) {
                return false;
            }

            @Override
            public boolean dispatchGenericMotionEvent(MotionEvent event) {
                return false;
            }

            @Override
            public boolean dispatchPopulateAccessibilityEvent(AccessibilityEvent event) {
                return false;
            }

            @Nullable
            @Override
            public View onCreatePanelView(int featureId) {
                return null;
            }

            @Override
            public boolean onCreatePanelMenu(int featureId, Menu menu) {
                return false;
            }

            @Override
            public boolean onPreparePanel(int featureId, View view, Menu menu) {
                return false;
            }

            @Override
            public boolean onMenuOpened(int featureId, Menu menu) {
                return false;
            }

            @Override
            public boolean onMenuItemSelected(int featureId, MenuItem item) {
                return false;
            }

            @Override
            public void onWindowAttributesChanged(WindowManager.LayoutParams attrs) {

            }

            @Override
            public void onContentChanged() {

            }

            @Override
            public void onWindowFocusChanged(boolean hasFocus) {

            }

            @Override
            public void onAttachedToWindow() {

            }

            @Override
            public void onDetachedFromWindow() {

            }

            @Override
            public void onPanelClosed(int featureId, Menu menu) {

            }

            @Override
            public boolean onSearchRequested() {
                return false;
            }

            @Override
            public boolean onSearchRequested(SearchEvent searchEvent) {
                return false;
            }

            @Nullable
            @Override
            public ActionMode onWindowStartingActionMode(ActionMode.Callback callback) {
                return null;
            }

            @Nullable
            @Override
            public ActionMode onWindowStartingActionMode(ActionMode.Callback callback, int type) {
                return null;
            }

            @Override
            public void onActionModeStarted(ActionMode mode) {

            }

            @Override
            public void onActionModeFinished(ActionMode mode) {

            }
        });

    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }
}
