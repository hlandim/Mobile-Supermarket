package br.com.hlandim.supermarket.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import br.com.hlandim.supermarket.R;

/**
 * Created by hlandim on 14/01/17.
 */

public class Util {

    public static void hideKeyboard(Activity context) {
        // Check if no view has focus:
        View view = context.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public static Snackbar getErrorSnackbar(View v, String text) {
        Snackbar snackbar = Snackbar
                .make(v, text, Snackbar.LENGTH_LONG);
        TextView tv = (TextView) snackbar.getView().findViewById(android.support.design.R.id.snackbar_text);
        tv.setTextColor(Color.RED);
        return snackbar;
    }

    public static AlertDialog getGeneralErrorDialog(Context context, String body) {
        return new AlertDialog.Builder(context)
                .setTitle(context.getString(R.string.general_error_dialog_title))
                .setMessage(body).create();
    }
}
