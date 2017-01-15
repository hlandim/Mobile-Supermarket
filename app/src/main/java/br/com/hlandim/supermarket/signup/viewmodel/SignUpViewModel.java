package br.com.hlandim.supermarket.signup.viewmodel;

import android.app.Activity;
import android.content.ContextWrapper;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;

import java.util.List;

import br.com.hlandim.supermarket.data.service.response.Error;
import br.com.hlandim.supermarket.home.HomeActivity;
import br.com.hlandim.supermarket.manager.SessionManager;
import br.com.hlandim.supermarket.signin.model.SignIn;
import br.com.hlandim.supermarket.signup.model.SignUp;
import br.com.hlandim.supermarket.util.Util;

/**
 * Created by hlandim on 11/01/17.
 */

public class SignUpViewModel extends ContextWrapper {

    private static final String TAG = SignUpViewModel.class.getSimpleName();
    private SignUp mSignUp;
    private SignUpViewModelListener mContract;


    public SignUpViewModel(Activity base, SignUpViewModelListener contract) {
        super(base);
        mSignUp = new SignUp();
        this.mContract = contract;
    }

    public String getEmail() {
        return mSignUp.getEmail();
    }

    public void setEmail(String email) {
        mSignUp.setEmail(email);
    }

    public String getPassword() {
        return mSignUp.getPassword();
    }

    public void setPassword(String password) {
        mSignUp.setPassword(password);
    }

    public String getConfirmPassword() {
        return mSignUp.getConfirmPassword();
    }

    public void setConfirmPassword(String confirmPassword) {
        mSignUp.setConfirmPassword(confirmPassword);
    }

    public String getName() {
        return mSignUp.getName();
    }

    public void setName(String name) {
        mSignUp.setName(name);
    }

    private void signIn(String email, String password) {
        if (mContract != null && mContract.validateFields()) {
            SignIn signIn = new SignIn(email, password);
            SessionManager.getInstance().signIn(signIn, new SessionManager.SignInCallback() {
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

    public void onBtnCreateClicked(View v) {
        if (mContract != null && mContract.validateFields()) {
            Util.hideKeyboard((Activity) getBaseContext());
            mContract.showProgress(true);
            SessionManager.getInstance().create(mSignUp, new SessionManager.SignUpCallback() {
                @Override
                public void onSignUpResponse(List<Error> errors) {
                    if (errors == null) {
                        signIn(mSignUp.getEmail(), mSignUp.getPassword());
                    } else {
                        mContract.showProgress(false);
                        mContract.onCreate(errors);
                    }
                }
            });
        }
    }
}