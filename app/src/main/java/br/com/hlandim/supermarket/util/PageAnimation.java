package br.com.hlandim.supermarket.util;

import br.com.hlandim.supermarket.R;

/**
 * Created by hlandim on 13/01/17.
 */

public enum PageAnimation {

    SLIDE_LEFT_TO_RIGHT(R.animator.slide_in_right, R.animator.slide_out_right),
    SLIDE_RIGHT_TO_LEFT(R.animator.slide_in_left, R.animator.slide_out_left),
    SLIDE_BOTTOM_TO_UP(R.animator.slide_in_bottom, R.animator.slide_out_top),
    SLIDE_UP_TO_BOTTOM(R.animator.slide_out_top, R.animator.slide_out_bottom),
    STATIONARY(R.animator.stationary, R.animator.stationary);

    private int inTransition;
    private int outTransition;

    PageAnimation(int inTransition, int outTransition) {
        this.inTransition = inTransition;
        this.outTransition = outTransition;
    }

    public int getInTransition() {
        return inTransition;
    }

    public int getOutTransition() {
        return outTransition;
    }
}
