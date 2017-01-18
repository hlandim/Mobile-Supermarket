package br.com.hlandim.supermarket;

import android.os.Bundle;
import android.os.Parcelable;
import android.os.PersistableBundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import br.com.hlandim.supermarket.page.main.signin.SignInFragment;
import br.com.hlandim.supermarket.page.main.signup.SignUpFragment;
import br.com.hlandim.supermarket.util.LoadingAnimation;
import br.com.hlandim.supermarket.util.PageAnimation;

public class MainActivity extends AppCompatActivity {

    private static final String TAG_FRAGMENT = "my_fragment_tag";
    private SignUpFragment mSignUpFragment;
    private SignInFragment mSignInFragment;
    private LoadingAnimation mLoadingAnimation;
    private TextView mTvLoading;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
//            mSignInFragment = (SignInFragment) savedInstanceState.get("sign_in_fragment");
//            mSignUpFragment = (SignUpFragment) savedInstanceState.get("sign_up_fragment");
//        } else {

            mSignInFragment = new SignInFragment();
            mSignUpFragment = new SignUpFragment();
        }


        setInitialFragment(savedInstanceState);

        mLoadingAnimation = new LoadingAnimation(findViewById(R.id.container_loading));
        mTvLoading = (TextView) findViewById(R.id.tv_loading);

    }

    /*@Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        outState.putParcelable("sign_in_fragment", (Parcelable) mSignInFragment);
        outState.putParcelable("sign_up_fragment", (Parcelable) mSignUpFragment);
        super.onSaveInstanceState(outState, outPersistentState);
    }
*/
    private void setInitialFragment(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            changeFragment(mSignInFragment, null);
        } else {
            Fragment fragment = getSupportFragmentManager().findFragmentByTag(TAG_FRAGMENT);
            changeFragment(fragment, null);
        }
    }

    public void goToSignUp(View v) {
        changeFragment(mSignUpFragment, PageAnimation.SLIDE_RIGHT_TO_LEFT);
    }

    private void goToSignIn(View v) {
        changeFragment(mSignInFragment, PageAnimation.SLIDE_LEFT_TO_RIGHT);
    }

    private void changeFragment(Fragment fragment, PageAnimation pageAnimation) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

        //Configure animation
        if (pageAnimation != null) {
            int enter = pageAnimation.getInTransition();
            int exit = pageAnimation.getOutTransition();
            if (enter > 0 && exit > 0) {
                fragmentTransaction.setCustomAnimations(enter, exit);
            }
        }

        fragmentTransaction.replace(R.id.container_fragment, fragment, TAG_FRAGMENT).commit();
    }

    @Override
    public void onBackPressed() {
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(TAG_FRAGMENT);
        if (fragment != null && fragment instanceof SignUpFragment) {
            goToSignIn(null);
        } else {
            super.onBackPressed();
        }
    }

    public void showLoadingOverlay(boolean show) {
        showLoadingOverlay(show, null);
    }

    public void showLoadingOverlay(boolean show, String text) {
        mTvLoading.setText(text);
        mLoadingAnimation.showLoadingOverlay(show);
    }
}

