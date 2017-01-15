package br.com.hlandim.supermarket;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import br.com.hlandim.supermarket.signin.SignInFragment;
import br.com.hlandim.supermarket.signup.SignUpFragment;
import br.com.hlandim.supermarket.util.PageAnimation;

public class MainActivity extends AppCompatActivity {

    private static final String TAG_FRAGMENT = "my_fragment_tag";
    private SignUpFragment mSignUpFragment;
    private SignInFragment mSignInFragment;

    private LinearLayout mLoadingOverlay;
    private YoYo.YoYoString mLoadingAnimation;
    private ImageView mImgLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSignInFragment = new SignInFragment();
        mSignUpFragment = new SignUpFragment();

        setInitialFragment(savedInstanceState);

        configureLoadingAnimation();

    }

    private void configureLoadingAnimation() {
        mLoadingOverlay = (LinearLayout) findViewById(R.id.container_loading);
        mImgLoading = (ImageView) mLoadingOverlay.findViewById(R.id.img_loading);
    }

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

    public void goToSignIn(View v) {
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
        if (show) {
            if (mLoadingAnimation != null) {
                mLoadingAnimation.stop(false);
            }
        } else {
            mLoadingAnimation = YoYo.with(Techniques.Pulse).playOn(mImgLoading);
        }
        mLoadingOverlay.setVisibility(show ? View.VISIBLE : View.GONE);
    }
}

