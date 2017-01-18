package br.com.hlandim.supermarket.page.main;

import android.support.v4.app.Fragment;

import br.com.hlandim.supermarket.MainActivity;

/**
 * Created by hlandim on 17/01/17.
 */

public class MainBaseFragment extends Fragment {

    public void showProgress(boolean show, String text) {
        ((MainActivity) getActivity()).showLoadingOverlay(show, text);
    }

    public void showProgress(boolean show) {
        ((MainActivity) getActivity()).showLoadingOverlay(show);
    }
}
