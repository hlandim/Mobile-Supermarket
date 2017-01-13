package br.com.hlandim.supermarket;

import android.annotation.TargetApi;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.LinkMovementMethod;

import br.com.hlandim.supermarket.databinding.ActivityMainBinding;
import br.com.hlandim.supermarket.signin.viewmodel.SignInViewModel;
import br.com.hlandim.supermarket.signin.viewmodel.SignInViewModelContract;
import br.com.hlandim.supermarket.signup.SignUpActivity;

public class MainActivity extends AppCompatActivity implements SignInViewModelContract {


    // UI references.
    private ActivityMainBinding mActivitySignInBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set up the login form.
        mActivitySignInBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mActivitySignInBinding.emailSignUpButton.setMovementMethod(LinkMovementMethod.getInstance());
        SignInViewModel loginViewModel = new SignInViewModel(this, this);
        mActivitySignInBinding.setUser(loginViewModel);

    }


    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    public void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in

    }

    @Override
    public void callSignUpActivity() {
        Intent intent = new Intent(getBaseContext(), SignUpActivity.class);
        startActivity(intent);
    }

    @Override
    public void callHomeActivity() {
        Intent intent = new Intent(getBaseContext(), HomeActivity.class);
        startActivity(intent);
    }
}

