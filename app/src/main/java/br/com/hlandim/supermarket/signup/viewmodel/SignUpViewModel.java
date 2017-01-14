package br.com.hlandim.supermarket.signup.viewmodel;

import android.content.Context;
import android.content.ContextWrapper;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import br.com.hlandim.supermarket.SuperMarketApplication;
import br.com.hlandim.supermarket.config.Endpoint;
import br.com.hlandim.supermarket.config.ServerUtil;
import br.com.hlandim.supermarket.service.SessionService;
import br.com.hlandim.supermarket.service.response.CreateResponse;
import br.com.hlandim.supermarket.service.response.Error;
import br.com.hlandim.supermarket.signup.model.SignUp;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by hlandim on 11/01/17.
 */

public class SignUpViewModel extends ContextWrapper {

    private static final String TAG = SignUpViewModel.class.getSimpleName();
    private SignUp mSignUp;
    private SignUpViewModelContract mContract;
    private SessionService service;

    public SignUpViewModel(Context base, SignUpViewModelContract contract) {
        super(base);
        mSignUp = new SignUp();
        this.mContract = contract;
        service = ServerUtil.getService(SessionService.class, Endpoint.SERVER_AUTH);
    }

    public String getEmail() {
        return mSignUp.getEmail();
    }

    public void setEmail(String email) {
        mSignUp.setEmail(email);
    }

    public String getPassword() {
        return mSignUp.getPassword();
    }

    public void setPassword(String password) {
        mSignUp.setPassword(password);
    }

    public String getConfirmPassword() {
        return mSignUp.getConfirmPassword();
    }

    public void setConfirmPassword(String confirmPassword) {
        mSignUp.setConfirmPassword(confirmPassword);
    }

    public String getName() {
        return mSignUp.getName();
    }

    public void setName(String name) {
        mSignUp.setName(name);
    }

    public void onBtnSignInClicked(View v) {

    }

    public void onBtnCreateClicked(View v) {

        if (mContract != null && mContract.validateFields()) {
            SuperMarketApplication application = SuperMarketApplication.create(this);
            service.createUser(mSignUp)
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<CreateResponse>() {
                        @Override
                        public final void onCompleted() {
                            Log.i(TAG, "onCompleted");
                        }

                        @Override
                        public final void onError(Throwable e) {
                            Error error = new Error("", e.getMessage());
                            List<Error> errors = new ArrayList<>();
                            errors.add(error);
                            mContract.onCreateError(errors);
                        }

                        @Override
                        public final void onNext(CreateResponse createResponse) {
                            if (createResponse.isSuccess()) {
                                Log.i(TAG, "User created with success");
//                                mContract.callHomeScreen();
                            } else {
                                mContract.onCreateError(createResponse.getErrors());
                            }
                        }
                    });
        }
    }
}
