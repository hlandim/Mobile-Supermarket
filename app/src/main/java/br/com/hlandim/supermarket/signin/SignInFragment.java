package br.com.hlandim.supermarket.signin;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import br.com.hlandim.supermarket.R;
import br.com.hlandim.supermarket.databinding.FragmentSignInBinding;
import br.com.hlandim.supermarket.signin.viewmodel.SignInViewModel;
import br.com.hlandim.supermarket.signin.viewmodel.SignInViewModelContract;

/**
 * Created by hlandim on 13/01/17.
 */

public class SignInFragment extends Fragment implements SignInViewModelContract {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FragmentSignInBinding fragmentSignInBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_sign_in, container, false);
        fragmentSignInBinding.emailSignUpButton.setMovementMethod(LinkMovementMethod.getInstance());
        SignInViewModel loginViewModel = new SignInViewModel(getActivity(), this);
        fragmentSignInBinding.setUser(loginViewModel);
        return fragmentSignInBinding.getRoot();
    }

}
