package br.com.hlandim.supermarket;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.WindowManager;
import android.widget.ImageView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.nineoldandroids.animation.Animator;

import br.com.hlandim.supermarket.manager.SessionManager;
import br.com.hlandim.supermarket.page.home.HomeActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        startAnimation();

        new Handler().postDelayed(() -> verifyCredentials(), 1000);


    }

    private void startAnimation() {
        ImageView imageView = (ImageView) findViewById(R.id.img_loading);
        YoYo.with(Techniques.Pulse).withListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                startAnimation();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        }).delay(100).duration(1000).playOn(imageView);
    }

    private void verifyCredentials() {
        SessionManager sessionManager = SessionManager.getInstance(this);
        sessionManager.signInWithSavedCrendentials(error -> {

            Class aClass = MainActivity.class;
            if (TextUtils.isEmpty(error)) {
                aClass = HomeActivity.class;
            }

            Intent intent = new Intent(SplashActivity.this, aClass);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        });
    }
}
