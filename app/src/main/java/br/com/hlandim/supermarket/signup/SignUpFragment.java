package br.com.hlandim.supermarket.signup;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import br.com.hlandim.supermarket.R;
import br.com.hlandim.supermarket.databinding.FragmentSignUpBinding;
import br.com.hlandim.supermarket.service.response.Error;
import br.com.hlandim.supermarket.signup.viewmodel.SignUpViewModel;
import br.com.hlandim.supermarket.signup.viewmodel.SignUpViewModelContract;

public class SignUpFragment extends Fragment implements SignUpViewModelContract {

    private FragmentSignUpBinding mBinding;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_sign_up, container, false);
        SignUpViewModel signUpViewModel = new SignUpViewModel(getActivity(), this);

        mBinding.setSignUp(signUpViewModel);

        return mBinding.getRoot();
    }

    @Override
    public void showProgress(boolean show) {

    }


    @Override
    public void onCreateError(List<Error> errors) {

    }

    @Override
    public boolean validateFields() {

        return validateEmptyField(mBinding.name) &&
                validateEmptyField(mBinding.email) &&
                validateEmptyField(mBinding.password) &&
                validateEmptyField(mBinding.confirmPassword);
    }

    private boolean validateEmptyField(TextView view) {
        return view != null && !TextUtils.isEmpty(view.getText());
    }

}
