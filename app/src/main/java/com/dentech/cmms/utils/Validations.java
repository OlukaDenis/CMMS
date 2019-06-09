package com.dentech.cmms.utils;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import com.rengwuxian.materialedittext.MaterialEditText;

public class Validations {
    private Context context;

    /**
     * constructor
     *
     * @param context
     */
    public Validations(Context context) {
        this.context = context;
    }

    /**
     * method to check InputEditText filled .
     *
     * @param materialEditText
     * @param message
     * @return
     */
    public boolean isInputEditTextFilled(MaterialEditText materialEditText, String message) {
        String value = materialEditText.getText().toString().trim();
        if (value.isEmpty()) {
            materialEditText.setError(message);
            hideKeyboardFrom(materialEditText);
            return false;
        }

        return true;
    }


    /**
     * method to check InputEditText has valid email .
     *
     * @param materialEditText
     * @param message
     * @return
     */
    public boolean isInputEditTextEmail(MaterialEditText materialEditText, String message) {
        String value = materialEditText.getText().toString().trim();
        if (value.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(value).matches()) {
            materialEditText.setError(message);
            hideKeyboardFrom(materialEditText);
            return false;
        }
        return true;
    }

    public boolean isInputEditTextMatches(MaterialEditText textInputEditText1, MaterialEditText textInputEditText2, String message) {
        String value1 = textInputEditText1.getText().toString().trim();
        String value2 = textInputEditText2.getText().toString().trim();
        if (!value1.contentEquals(value2)) {
            textInputEditText1.setError(message);
            textInputEditText2.setError(message);
            hideKeyboardFrom(textInputEditText2);
            return false;
        }
        return true;
    }

    /**
     * method to Hide keyboard
     *
     * @param view
     */
    private void hideKeyboardFrom(View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }
}
