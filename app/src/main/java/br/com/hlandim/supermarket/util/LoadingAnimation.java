package br.com.hlandim.supermarket.util;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import br.com.hlandim.supermarket.R;

/**
 * Created by hlandim on 16/01/17.
 */

public class LoadingAnimation {

    private LinearLayout mLoadingOverlay;
    private YoYo.YoYoString mLoadingAnimation;
    private ImageView mImgLoading;
    private View mViewRoot;

    public LoadingAnimation(View mViewRoot) {
        this.mViewRoot = mViewRoot;
        configureLoadingAnimation(mViewRoot);
    }

    private void configureLoadingAnimation(View root) {
        mLoadingOverlay = (LinearLayout) root.findViewById(R.id.container_loading);
        mImgLoading = (ImageView) mLoadingOverlay.findViewById(R.id.img_loading);
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
