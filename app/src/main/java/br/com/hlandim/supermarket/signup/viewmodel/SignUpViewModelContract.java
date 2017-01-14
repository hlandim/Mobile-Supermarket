package br.com.hlandim.supermarket.signup.viewmodel;

import java.util.List;

import br.com.hlandim.supermarket.service.response.Error;

/**
 * Created by hlandim on 11/01/17.
 */

public interface SignUpViewModelContract {
    void showProgress(boolean show);
    void onCreateError(List<Error> errors);
    boolean validateFields();
}
