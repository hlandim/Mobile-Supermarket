package br.com.hlandim.supermarket;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import br.com.hlandim.supermarket.databinding.ActivityLoginBinding;
import br.com.hlandim.supermarket.login.viewModel.LoginViewModel;
import br.com.hlandim.supermarket.login.viewModel.LoginViewModelContract;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity implements LoginViewModelContract {


    // UI references.
    private ActivityLoginBinding mActivityLoginBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set up the login form.
        mActivityLoginBinding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        LoginViewModel loginViewModel = new LoginViewModel(this, this);
        mActivityLoginBinding.setUser(loginViewModel);


//        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
//        mEmailSignInButton.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                //attemptLogin();
//
//                showProgress(true);
//            }
//        });

    }


    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    public void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mActivityLoginBinding.loginForm.setVisibility(show ? View.GONE : View.VISIBLE);
            mActivityLoginBinding.loginForm.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mActivityLoginBinding.loginForm.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mActivityLoginBinding.loginProgress.setVisibility(show ? View.VISIBLE : View.GONE);
            mActivityLoginBinding.loginProgress.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mActivityLoginBinding.loginProgress.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mActivityLoginBinding.loginProgress.setVisibility(show ? View.VISIBLE : View.GONE);
            mActivityLoginBinding.loginForm.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }
}

