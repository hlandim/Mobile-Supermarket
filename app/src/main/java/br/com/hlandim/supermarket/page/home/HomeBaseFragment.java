package br.com.hlandim.supermarket.page.home;

import android.support.v4.app.Fragment;

/**
 * Created by hlandim on 17/01/17.
 */

public class HomeBaseFragment extends Fragment {

    protected void showProgress(boolean show, String text) {
        ((HomeActivity) getActivity()).showLoadingOverlay(show, text);
    }

    protected void showProgress(boolean show) {
        ((HomeActivity) getActivity()).showLoadingOverlay(show);
    }
}
