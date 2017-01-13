package br.com.hlandim.supermarket.signup;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.TextView;

import java.util.List;

import br.com.hlandim.supermarket.R;
import br.com.hlandim.supermarket.databinding.ActivitySignUpBinding;
import br.com.hlandim.supermarket.service.response.Error;
import br.com.hlandim.supermarket.signup.viewmodel.SignUpViewModel;
import br.com.hlandim.supermarket.signup.viewmodel.SignUpViewModelContract;

public class SignUpActivity extends AppCompatActivity implements SignUpViewModelContract {

    private ActivitySignUpBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_sign_up);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_sign_up);
        SignUpViewModel signUpViewModel = new SignUpViewModel(getBaseContext(), this);
        mBinding.setSignUp(signUpViewModel);


    }

    @Override
    public void showProgress(boolean show) {

    }

    @Override
    public void callHomeScreen() {

    }

    @Override
    public void callLoginScreen() {
        finish();
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
