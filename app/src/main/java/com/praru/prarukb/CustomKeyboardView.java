package com.praru.prarukb;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.NinePatchDrawable;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.util.AttributeSet;
import android.util.Log;

import java.util.List;

/**
 * Created by prady on 1/8/16.
 */
public class CustomKeyboardView extends KeyboardView {
    private static final String TAG = "CustomKeyboardView";
    public CustomKeyboardView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomKeyboardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * Requires API Level 21
     * @param context
     * @param attrs
     * @param defStyleAttr
     * @param defStyleRes
     */
    /*public CustomKeyboardView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }*/

    @Override
    public void onDraw(Canvas canvas){
        super.onDraw(canvas);
        Log.d(TAG, "Changing the color of specific keys");
        List<Keyboard.Key> keyList = getKeyboard().getKeys();
        for (Keyboard.Key key : keyList){
            if (key.codes[0] < 57 && key.codes[0] > 48){
                Drawable drawable = getContext().getResources().getDrawable(R.drawable.)
            }
        }
    }
}
