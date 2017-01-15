package br.com.hlandim.supermarket.signin.viewmodel;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;

import br.com.hlandim.supermarket.home.HomeActivity;
import br.com.hlandim.supermarket.manager.SessionManager;
import br.com.hlandim.supermarket.signin.model.SignIn;
import br.com.hlandim.supermarket.util.Util;

/**
 * Created by hlandim on 10/01/17.
 */

public class SignInViewModel extends ContextWrapper {

    private SignIn mSignIn;
    private SignInViewModelListener mContract;

    public SignInViewModel(Context base, SignInViewModelListener signInViewModelListener) {
        super(base);
        this.mContract = signInViewModelListener;
        this.mSignIn = new SignIn();


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
        if (mContract != null) {
            if (mContract.validateFields()) {
                mContract.showProgress(true);
                SessionManager.getInstance().signIn(mSignIn, new SessionManager.SignInCallback() {
                    @Override
                    public void onSignInResponse(String error) {
                        if (TextUtils.isEmpty(error)) {
                            Intent intent = new Intent(getBaseContext(), HomeActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                        } else {
                            mContract.showProgress(false);
                            Util.getGeneralErrorDialog(getBaseContext(), error).show();
                        }
                    }
                });
            }
        }
    }

    public void onBtnSignUpClicked(View v) {


    }


}
