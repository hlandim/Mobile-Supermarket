package br.com.hlandim.supermarket.page.main.signin;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.basgeekball.awesomevalidation.AwesomeValidation;

import br.com.hlandim.supermarket.R;
import br.com.hlandim.supermarket.databinding.FragmentSignInBinding;
import br.com.hlandim.supermarket.page.main.MainBaseFragment;
import br.com.hlandim.supermarket.page.main.signin.viewmodel.SignInViewModel;
import br.com.hlandim.supermarket.page.main.signin.viewmodel.SignInViewModelListener;

import static com.basgeekball.awesomevalidation.ValidationStyle.TEXT_INPUT_LAYOUT;

/**
 * Created by hlandim on 13/01/17.
 */

public class SignInFragment extends MainBaseFragment implements SignInViewModelListener {

    private AwesomeValidation mAwesomeValidation;
    private SignInViewModel mLoginViewModel;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FragmentSignInBinding mFragmentSignInBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_sign_in, container, false);
        configureFieldValidations(mFragmentSignInBinding);
        if (mLoginViewModel == null) {
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
    public boolean validateFields() {
        return mAwesomeValidation.validate();
    }
}
