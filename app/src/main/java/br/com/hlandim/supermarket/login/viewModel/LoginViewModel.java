package br.com.hlandim.supermarket.login.viewModel;

import android.content.Context;
import android.content.ContextWrapper;
import android.view.View;

import br.com.hlandim.supermarket.login.model.Login;

/**
 * Created by hlandim on 10/01/17.
 */

public class LoginViewModel extends ContextWrapper {

    private Login login;
    private LoginViewModelContract mLoginViewModelContract;

    public LoginViewModel(Context base, LoginViewModelContract loginViewModelContract) {
        super(base);
        this.mLoginViewModelContract = loginViewModelContract;
        this.login = new Login();
    }

    public String getEmail() {
        return login.getEmail();
    }

    public void setEmail(String email) {
        login.setEmail(email);
    }

    public String getPassword() {
        return login.getPassword();
    }

    public void setPassword(String password) {
        login.setPassword(password);
    }

    public void onBtnLoginClicked(View v) {
        if (mLoginViewModelContract != null) {
            mLoginViewModelContract.showProgress(true);
        }
    }


}
