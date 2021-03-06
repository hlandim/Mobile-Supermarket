package br.com.hlandim.supermarket.page.main.signup.viewmodel;

import android.app.Activity;
import android.content.ContextWrapper;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;

import br.com.hlandim.supermarket.R;
import br.com.hlandim.supermarket.manager.SessionManager;
import br.com.hlandim.supermarket.page.home.HomeActivity;
import br.com.hlandim.supermarket.page.main.signin.model.SignIn;
import br.com.hlandim.supermarket.page.main.signup.model.SignUp;
import br.com.hlandim.supermarket.util.Util;

/**
 * Created by hlandim on 11/01/17.
 */

public class SignUpViewModel extends ContextWrapper {

    private static final String TAG = SignUpViewModel.class.getSimpleName();
    private SignUp mSignUp;
    private SignUpViewModelListener mContract;
    private SessionManager mSessionManager;


    public SignUpViewModel(Activity base, SignUpViewModelListener contract) {
        super(base);
        mSignUp = new SignUp();
        this.mContract = contract;
        mSessionManager = SessionManager.getInstance(base);
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
            mSessionManager.signIn(signIn, error -> {
                        if (TextUtils.isEmpty(error)) {
                            Intent intent = new Intent(getBaseContext(), HomeActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                        } else {
                            mContract.showProgress(false, null);
                            Util.getGeneralErrorDialog(getBaseContext(), error).show();
                        }
                    }
            );
        }
    }

    public void onBtnCreateClicked(View v) {
        if (mContract != null && mContract.validateFields()) {
            Util.hideKeyboard((Activity) getBaseContext());
            mContract.showProgress(true, getString(R.string.loading_creating_user));
            mSessionManager.create(mSignUp, errors -> {
                        if (errors == null) {
                            signIn(mSignUp.getEmail(), mSignUp.getPassword());
                        } else {
                            mContract.showProgress(false, null);
                            mContract.onCreate(errors);
                        }
                    }
            );
        }
    }
}