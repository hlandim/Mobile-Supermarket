package br.com.hlandim.supermarket.signup;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.google.common.collect.Range;

import java.util.List;

import br.com.hlandim.supermarket.MainActivity;
import br.com.hlandim.supermarket.R;
import br.com.hlandim.supermarket.data.service.response.Error;
import br.com.hlandim.supermarket.data.service.response.ProductResponse;
import br.com.hlandim.supermarket.databinding.FragmentSignUpBinding;
import br.com.hlandim.supermarket.signup.viewmodel.SignUpViewModel;
import br.com.hlandim.supermarket.signup.viewmodel.SignUpViewModelListener;
import br.com.hlandim.supermarket.util.Util;

import static com.basgeekball.awesomevalidation.ValidationStyle.TEXT_INPUT_LAYOUT;

public class SignUpFragment extends Fragment implements SignUpViewModelListener {

    private FragmentSignUpBinding mBinding;
    private AwesomeValidation mAwesomeValidation;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_sign_up, container, false);
        SignUpViewModel signUpViewModel = new SignUpViewModel(getActivity(), this);

        mBinding.setSignUp(signUpViewModel);
        configureFieldValidations(mBinding);
        return mBinding.getRoot();
    }

    private void configureFieldValidations(FragmentSignUpBinding fragmentSignUpBinding) {
        mAwesomeValidation = new AwesomeValidation(TEXT_INPUT_LAYOUT);
        mAwesomeValidation.addValidation(fragmentSignUpBinding.tilEmail, android.util.Patterns.EMAIL_ADDRESS, getString(R.string.invalid_email));
        mAwesomeValidation.addValidation(fragmentSignUpBinding.tilPassword, "\\w{6,}", getString(R.string.password_size));
        mAwesomeValidation.addValidation(fragmentSignUpBinding.tilConfirmPassword, "\\w{6,}", getString(R.string.password_size));
        mAwesomeValidation.addValidation(fragmentSignUpBinding.tilName, "\\w{6,}", getString(R.string.required_field));
    }


    @Override
    public void showProgress(boolean show) {
        ((MainActivity) getActivity()).showLoadingOverlay(show);
    }

    @Override
    public void onCreate(List<Error> errors) {
        if (errors == null) {

        } else {
            handleErrors(errors);
        }
    }

    private void handleErrors(List<Error> errors) {
        for (Error error : errors) {
            if (ProductResponse.VALIDATION_ERROR_REASON.equals(error.getReason())
                    && ProductResponse.VALIDATION_ERROR_EMAIL.equals(error.getMessage())) {
                Util.getErrorSnackbar(mBinding.getRoot(), getString(R.string.sign_up_error_email)).show();
                break;
            }
        }
    }


    @Override
    public boolean validateFields() {

        if (mAwesomeValidation.validate()) {
            String password = mBinding.password.getText().toString();
            String confirmPassword = mBinding.confirmPassword.getText().toString();
            if (password.equals(confirmPassword)) {
                return true;
            } else {
                mBinding.tilConfirmPassword.setError(getString(R.string.password_do_not_match));
                return false;
            }
        }
        return false;
    }
}