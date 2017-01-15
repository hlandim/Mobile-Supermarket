package br.com.hlandim.supermarket.signin;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.google.common.collect.Range;

import br.com.hlandim.supermarket.MainActivity;
import br.com.hlandim.supermarket.R;
import br.com.hlandim.supermarket.databinding.FragmentSignInBinding;
import br.com.hlandim.supermarket.signin.viewmodel.SignInViewModel;
import br.com.hlandim.supermarket.signin.viewmodel.SignInViewModelListener;

import static com.basgeekball.awesomevalidation.ValidationStyle.TEXT_INPUT_LAYOUT;

/**
 * Created by hlandim on 13/01/17.
 */

public class SignInFragment extends Fragment implements SignInViewModelListener {

    private AwesomeValidation mAwesomeValidation;
    private SignInViewModel mLoginViewModel;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FragmentSignInBinding mFragmentSignInBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_sign_in, container, false);
        configureFieldValidations(mFragmentSignInBinding);
        if( mLoginViewModel == null) {
            mLoginViewModel = new SignInViewModel(getActivity(), this);
        } else {
            mLoginViewModel.setListener(this);
        }
        mFragmentSignInBinding.setViewModel(mLoginViewModel);

        setRetainInstance(true);
        return mFragmentSignInBinding.getRoot();
    }

    private void configureFieldValidations(FragmentSignInBinding mFragmentSignInBinding) {
        mAwesomeValidation = new AwesomeValidation(TEXT_INPUT_LAYOUT);
        mAwesomeValidation.addValidation(mFragmentSignInBinding.tilEmail, android.util.Patterns.EMAIL_ADDRESS, getString(R.string.invalid_email));
        mAwesomeValidation.addValidation(mFragmentSignInBinding.tilPassword, "\\w{6,}", getString(R.string.password_size));
    }

    @Override
    public void showProgress(boolean show) {
        ((MainActivity) getActivity()).showLoadingOverlay(show);
    }

    @Override
    public boolean validateFields() {
        return mAwesomeValidation.validate();
    }
}
