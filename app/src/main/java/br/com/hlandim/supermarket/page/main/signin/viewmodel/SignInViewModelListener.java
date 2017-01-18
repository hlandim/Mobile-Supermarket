package br.com.hlandim.supermarket.page.main.signin.viewmodel;

/**
 * Created by hlandim on 10/01/17.
 */

public interface SignInViewModelListener {
    void showProgress(boolean show, String text);

    void showProgress(boolean show);

    boolean validateFields();
}
