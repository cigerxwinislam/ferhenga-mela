// Bismillahirrahmannirrahim
// Elhamdulillahirabbulalemin
// Esselatu ve sselamu alâ Resulillah

package com.yekazad.yekazad.Utils.WhatsAppWidget;
import android.annotation.SuppressLint;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.widget.EditText;

import java.util.ArrayList;

/**
 * Created by Mohamed El Sayed
 */
public class WhatsAppEditText extends EditText {

    /**
     * List of {@link TextWatcher} listening to this {@link WhatsAppEditText}
     */
    private ArrayList<TextWatcher> mListeners;

    /*
     * Constructor
     */
    public WhatsAppEditText(Context context) {
        super(context);
        init();
    }

    /*
     * Constructor
     */
    public WhatsAppEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    /*
     * Constructor
     */
    public WhatsAppEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    /*
     * Constructor
     */
    @SuppressLint("NewApi")
    public WhatsAppEditText(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    /**
     * Initializer method to listen for text change events.
     */
    private void init() {
        addTextChangedListener(mEditTextWatcher);
    }

    /**
     * {@link TextWatcher} listener to do the formatting.
     */
    private TextWatcher mEditTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            sendBeforeTextChanged(s, start, count, after);
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            sendOnTextChanged(s, start, before, count);
        }

        @Override
        public void afterTextChanged(Editable s) {

            postDelayed(new Runnable() {
                @Override
                public void run() {
                    format();
                }
            }, 10);

        }
    };

    /**
     * Performs formatting.
     */
    private void format() {

        Editable text = getText();
        CharSequence formatted = WhatsappViewCompat.extractFlagsForEditText(text);
        removeTextChangedListener(mEditTextWatcher);
        int selectionEnd = getSelectionEnd();
        int selectionStart = getSelectionStart();
        setText(formatted);
        setSelection(selectionStart, selectionEnd);
        Editable formattedEditableText = getText();
        sendAfterTextChanged(formattedEditableText);
        addTextChangedListener(mEditTextWatcher);
    }

    /**
     * Send an before text change event to child listeners
     *
     * @see {@link TextWatcher#beforeTextChanged(CharSequence, int, int, int)}
     */
    private void sendBeforeTextChanged(CharSequence s, int start, int count, int after) {
        if (mListeners != null) {
            for (int i = 0; i < mListeners.size(); i++) {
                mListeners.get(i).beforeTextChanged(s, start, count, after);
            }
        }
    }

    /**
     * Send an on text change event to child listeners
     *
     * @see {@link TextWatcher#onTextChanged(CharSequence, int, int, int)}
     */
    private void sendOnTextChanged(CharSequence s, int start, int before, int count) {
        if (mListeners != null) {
            for (int i = 0; i < mListeners.size(); i++) {
                mListeners.get(i).onTextChanged(s, start, before, count);
            }
        }
    }

    /**
     * Send an after text change event to child listeners
     *
     * @see {@link TextWatcher#afterTextChanged(Editable)}
     */
    private void sendAfterTextChanged(Editable s) {
        if (mListeners != null) {
            for (int i = 0; i < mListeners.size(); i++) {
                mListeners.get(i).afterTextChanged(s);
            }
        }
    }

    /*
     * Overridden method.
     * Registers the callback for text change events.
     */
    public void addTextChangedListener(TextWatcher watcher) {

        if (watcher != mEditTextWatcher) {
            if (mListeners == null) {
                mListeners = new ArrayList<>();
            }

            mListeners.add(watcher);
        } else {
            super.addTextChangedListener(watcher);
        }
    }

    /*
     * Overridden method.
     * Unregisters the callback for text change events.
     */
    public void removeTextChangedListener(TextWatcher watcher) {
        if (watcher != mEditTextWatcher) {
            if (mListeners != null) {
                int i = mListeners.indexOf(watcher);

                if (i >= 0) {
                    mListeners.remove(i);
                }
            }
        } else {
            super.removeTextChangedListener(watcher);
        }
    }

}
