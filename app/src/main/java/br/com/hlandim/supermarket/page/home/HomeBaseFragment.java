package br.com.hlandim.supermarket.page.home;

import android.support.v4.app.Fragment;

/**
 * Created by hlandim on 17/01/17.
 */

public class HomeBaseFragment extends Fragment {

    public void showProgress(boolean show, String text) {
        ((HomeActivity) getActivity()).showLoadingOverlay(show, text);
    }

    public void showProgress(boolean show) {
        ((HomeActivity) getActivity()).showLoadingOverlay(show);
    }
}
