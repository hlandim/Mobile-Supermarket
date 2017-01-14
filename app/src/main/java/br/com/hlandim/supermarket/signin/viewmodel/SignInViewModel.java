package br.com.hlandim.supermarket.signin.viewmodel;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.view.View;

import br.com.hlandim.supermarket.HomeActivity;
import br.com.hlandim.supermarket.signin.model.SignIn;
import br.com.hlandim.supermarket.signup.SignUpFragment;

/**
 * Created by hlandim on 10/01/17.
 */

public class SignInViewModel extends ContextWrapper {

    private SignIn mLogin;
    private SignInViewModelContract mContract;

    public SignInViewModel(Context base, SignInViewModelContract signInViewModelContract) {
        super(base);
        this.mContract = signInViewModelContract;
        this.mLogin = new SignIn();
    }

    public String getEmail() {
        return mLogin.getEmail();
    }

    public void setEmail(String email) {
        mLogin.setEmail(email);
    }

    public String getPassword() {
        return mLogin.getPassword();
    }

    public void setPassword(String password) {
        mLogin.setPassword(password);
    }

    public void onBtnSignInClicked(View v) {

    }

    public void onBtnSignUpClicked(View v) {


    }


}
