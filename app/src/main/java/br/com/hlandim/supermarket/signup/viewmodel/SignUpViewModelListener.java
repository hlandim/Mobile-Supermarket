package br.com.hlandim.supermarket.signup.viewmodel;

import java.util.List;

import br.com.hlandim.supermarket.data.service.response.Error;

/**
 * Created by hlandim on 11/01/17.
 */

public interface SignUpViewModelListener {
    void showProgress(boolean show);
    boolean validateFields();
    void onCreate(List<Error> errors);
}
