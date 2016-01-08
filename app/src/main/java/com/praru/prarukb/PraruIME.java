package com.praru.prarukb;

import android.app.Dialog;
import android.inputmethodservice.InputMethodService;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.media.AudioManager;
import android.os.IBinder;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by prady on 1/7/16.
 */
public class PraruIME extends InputMethodService implements KeyboardView.OnKeyboardActionListener{

    public static boolean isNumpad = false;

    public static final int KB_QWERTY = 1;
    public static final int KB_NUMPAD = 2;

    private boolean caps = false;

    private int lastDisplayWidth;

    private String wordSeparators;

    private KeyboardView keyboardView;
    private Keyboard qwertyKB;
    private Keyboard numpadKB;

    private InputMethodManager inputMethodManager;

    @Override
    public void onCreate(){
        super.onCreate();
        inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        wordSeparators = getResources().getString(R.string.word_separators);
    }

    @Override
    public void onPress(int primaryCode) {

    }

    @Override
    public void onRelease(int primaryCode) {

    }

    @Override
    public void onKey(int primaryCode, int[] keyCodes) {
        InputConnection inputConnection = getCurrentInputConnection();
//        Toast.makeText(this, "Primary Code " + primaryCode, Toast.LENGTH_LONG).show();
        keySound(primaryCode);
        switch (primaryCode){
            case -2:
                if (!isNumpad){
                    changeKeyboard(numpadKB, KB_NUMPAD);
                    isNumpad = true;
                }else{
                    changeKeyboard(qwertyKB, KB_QWERTY);
                    isNumpad = false;
                }
                break;
            case Keyboard.KEYCODE_DELETE:
                inputConnection.deleteSurroundingText(1,0);
                break;
            case Keyboard.KEYCODE_SHIFT:
                caps = !caps;
                qwertyKB.setShifted(caps);
                keyboardView.invalidateAllKeys();
                break;
            case Keyboard.KEYCODE_DONE:
                inputConnection.sendKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_ENTER));
                break;
            default:
                char code = (char) primaryCode;
                if (Character.isLetter(code) && caps){
                    code = Character.toUpperCase(code);
                }
                inputConnection.commitText(String.valueOf(code), 1);
        }
    }

    @Override
    public void onText(CharSequence text) {

    }

    @Override
    public void swipeLeft() {

    }

    @Override
    public void swipeRight() {

    }

    @Override
    public void swipeDown() {

    }

    @Override
    public void swipeUp() {

    }

    @Override
    public void onInitializeInterface(){
        if (qwertyKB != null){
            int displayWidth = getMaxWidth();
            if (displayWidth == lastDisplayWidth)
                return;
            lastDisplayWidth = displayWidth;
        }
        qwertyKB = new Keyboard(this, R.xml.qwerty);
        numpadKB = new Keyboard(this, R.xml.numpad);
    }

    @Override
    public View onCreateInputView(){
        isNumpad = false;
        keyboardView = (KeyboardView) getLayoutInflater().inflate(R.layout.keyboard, null);
        changeKeyboard(qwertyKB, KB_QWERTY);
        keyboardView.setOnKeyboardActionListener(this);
        return keyboardView;
    }

    private void changeKeyboard(Keyboard newKeyboard, int selectedKeyboard){
//        final boolean shouldSupportLanguageSwitchKey = inputMethodManager.shouldOfferSwitchingToNextInputMethod(getToken());
//        newKeyboard.setLanguageSwitchKeyVisibility(shouldSupportLanguageSwitchKey);
        switch (selectedKeyboard){
            case KB_NUMPAD:
                numpadKB = new Keyboard(this, R.xml.numpad);
                keyboardView.setKeyboard(numpadKB);
                break;
            default:
                qwertyKB = new Keyboard(this, R.xml.qwerty);
                keyboardView.setKeyboard(qwertyKB);
        }
    }

    private void keySound(int keycode){
        AudioManager audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
        switch (keycode){
            case 32:
                audioManager.playSoundEffect(AudioManager.FX_KEYPRESS_SPACEBAR);
                break;
            case Keyboard.KEYCODE_DONE:
                audioManager.playSoundEffect(AudioManager.FX_KEY_CLICK);
            case 10:
                audioManager.playSoundEffect(AudioManager.FX_KEYPRESS_RETURN);
                break;
            case Keyboard.KEYCODE_DELETE:
                audioManager.playSoundEffect(AudioManager.FX_KEYPRESS_DELETE);
                break;
            default:
                audioManager.playSoundEffect(AudioManager.FX_KEYPRESS_STANDARD);
        }
    }

    private IBinder getToken() {
        final Dialog dialog = getWindow();
        if (dialog == null) {
            return null;
        }
        final Window window = dialog.getWindow();
        if (window == null) {
            return null;
        }
        return window.getAttributes().token;
    }
}
