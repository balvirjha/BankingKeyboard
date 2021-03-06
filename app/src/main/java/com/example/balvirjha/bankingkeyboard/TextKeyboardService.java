package com.example.balvirjha.bankingkeyboard;

import android.content.Context;
import android.inputmethodservice.InputMethodService;
import android.os.Build;
import android.os.IBinder;
import android.os.SystemClock;
import android.os.Vibrator;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.example.balvirjha.bankingkeyboard.view.TextKeyboardViewNew;
import com.klinker.android.emoji_keyboard_trial.R;

public class TextKeyboardService extends InputMethodService {

    private TextKeyboardViewNew emojiKeyboardView;

    private InputConnection inputConnection;

    private InputMethodManager previousInputMethodManager;
    private IBinder iBinder;

    private static Context staticApplicationContext;

    public static Context getStaticApplicationContext() {
        return staticApplicationContext;
    }

    public TextKeyboardService() {
        super();

        if (Build.VERSION.SDK_INT >= 17) {
            enableHardwareAcceleration();
        }
    }

    @Override
    public void onCreate() {
        staticApplicationContext = getApplicationContext();
        super.onCreate();
    }

    @Override
    public View onCreateInputView() {

        staticApplicationContext = getApplicationContext();

        previousInputMethodManager = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
        iBinder = this.getWindow().getWindow().getAttributes().token;

        emojiKeyboardView = (TextKeyboardViewNew) getLayoutInflater()
                .inflate(R.layout.text_keyboard_layout, null);

        return emojiKeyboardView.getView();
    }

    @Override
    public void onStartInput(EditorInfo attribute, boolean restarting) {
        super.onStartInputView(attribute, restarting);
        inputConnection = getCurrentInputConnection();
    }

    public void sendText(String text) {
        inputConnection.commitText(text, 1);
    }

    public void sendDownKeyEvent(int keyEventCode, int flags) {
        inputConnection.sendKeyEvent(
                new KeyEvent(
                        SystemClock.uptimeMillis(),
                        SystemClock.uptimeMillis(),
                        KeyEvent.ACTION_DOWN,
                        keyEventCode,
                        0,
                        flags
                )
        );
    }

    public void sendUpKeyEvent(int keyEventCode, int flags) {
        inputConnection.sendKeyEvent(
                new KeyEvent(
                        SystemClock.uptimeMillis(),
                        SystemClock.uptimeMillis(),
                        KeyEvent.ACTION_UP,
                        keyEventCode,
                        0,
                        flags
                )
        );
    }
    public void sendDownAndUpKeyEvent(int keyEventCode, int flags){
        sendDownKeyEvent(keyEventCode, flags);
        sendUpKeyEvent(keyEventCode, flags);
    }


    public void switchToPreviousInputMethod() {

        Vibrator vib = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        vib.vibrate(25);

        try {
            previousInputMethodManager.switchToLastInputMethod(iBinder);
        } catch (Throwable t) { // java.lang.NoSuchMethodError if API_level<11
            Context context = getApplicationContext();
            CharSequence text = "Unfortunately input method switching isn't supported in your version of Android! You will have to do it manually :(";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }
    }
}
