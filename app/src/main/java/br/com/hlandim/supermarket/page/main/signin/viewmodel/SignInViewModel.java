package br.com.hlandim.supermarket.page.main.signin.viewmodel;

import android.app.Activity;
import android.content.ContextWrapper;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;

import br.com.hlandim.supermarket.R;
import br.com.hlandim.supermarket.manager.SessionManager;
import br.com.hlandim.supermarket.page.home.HomeActivity;
import br.com.hlandim.supermarket.page.main.signin.model.SignIn;
import br.com.hlandim.supermarket.util.Util;

/**
 * Created by hlandim on 10/01/17.
 */

public class SignInViewModel extends ContextWrapper {


    private SignIn mSignIn;
    private SignInViewModelListener mListener;
    private SessionManager mSessionManager;

    public SignInViewModel(Activity base, SignInViewModelListener signInViewModelListener) {
        super(base);
        this.mListener = signInViewModelListener;
        this.mSignIn = new SignIn();
        mSessionManager = SessionManager.getInstance(base);

    }

    public String getEmail() {
        return mSignIn.getEmail();
    }

    public void setEmail(String email) {
        mSignIn.setEmail(email);
    }

    public String getPassword() {
        return mSignIn.getPassword();
    }

    public void setPassword(String password) {
        mSignIn.setPassword(password);
    }

    public void onBtnSignInClicked(View v) {
        if (mListener != null) {
            if (mListener.validateFields()) {
                Util.hideKeyboard((Activity) getBaseContext());
                mListener.showProgress(true, getString(R.string.loading_sign_in_user));
                mSessionManager.signIn(mSignIn, error -> {
                            if (TextUtils.isEmpty(error)) {
                                Intent intent = new Intent(getBaseContext(), HomeActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                            } else {

                                Util.getErrorSnackbar(v, error).show();
                                mListener.showProgress(false);

                            }
                        }
                );
            }
        }
    }

    public void setListener(SignInViewModelListener listener) {
        this.mListener = listener;
    }
}
