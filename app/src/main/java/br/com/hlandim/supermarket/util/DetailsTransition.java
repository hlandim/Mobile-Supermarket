package br.com.hlandim.supermarket.util;

import android.annotation.TargetApi;
import android.os.Build;
import android.transition.ChangeBounds;
import android.transition.ChangeImageTransform;
import android.transition.ChangeTransform;
import android.transition.TransitionSet;

/**
 * Created by hlandim on 15/01/17.
 */

@TargetApi(Build.VERSION_CODES.KITKAT)
public class DetailsTransition extends TransitionSet {
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public DetailsTransition() {
        setOrdering(ORDERING_TOGETHER);
        addTransition(new ChangeBounds()).
                addTransition(new ChangeTransform()).
                addTransition(new ChangeImageTransform());
    }
}
