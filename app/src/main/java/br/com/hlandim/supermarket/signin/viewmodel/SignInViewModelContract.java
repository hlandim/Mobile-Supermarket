package br.com.hlandim.supermarket.signin.viewmodel;

/**
 * Created by hlandim on 10/01/17.
 */

public interface SignInViewModelContract {
    void showProgress(boolean show);
    void callSignUpActivity();
    void callHomeActivity();
}
