package br.com.hlandim.supermarket.manager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import br.com.hlandim.supermarket.config.Endpoint;
import br.com.hlandim.supermarket.data.service.SessionService;
import br.com.hlandim.supermarket.data.service.response.CreateResponse;
import br.com.hlandim.supermarket.data.service.response.Error;
import br.com.hlandim.supermarket.data.service.response.SignInResponse;
import br.com.hlandim.supermarket.exception.RetrofitException;
import br.com.hlandim.supermarket.signin.model.SignIn;
import br.com.hlandim.supermarket.signup.model.SignUp;
import br.com.hlandim.supermarket.util.ServerUtil;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by hlandim on 14/01/17.
 * This class is responsable to manager the user session
 */

public class SessionManager {

    private static SessionManager instance;
    private SessionService service;
    private String mToken;

    private SessionManager() {
        service = ServerUtil.getService(SessionService.class, Endpoint.SERVER_AUTH);
    }

    public static SessionManager getInstance() {
        if (instance == null) {
            instance = new SessionManager();
        }

        return instance;
    }

    public void signIn(SignIn signIn, final SignInCallback signInCallback) {
        if (signInCallback != null) {
            service.signIn(signIn.getEmail(), signIn.getPassword(), signIn.getGrantType())
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Action1<SignInResponse>() {
                        @Override
                        public void call(SignInResponse signInResponse) {
                            setToken(signInResponse.getAccessToken());
                            signInCallback.onSignInResponse(signInResponse.getError());
                        }
                    }, new Action1<Throwable>() {
                        @Override
                        public void call(Throwable throwable) {
                            try {
                                RetrofitException error = (RetrofitException) throwable;
                                SignInResponse response = error.getErrorBodyAs(SignInResponse.class);
                                signInCallback.onSignInResponse(response.getError());
                            } catch (IOException e1) {
                                signInCallback.onSignInResponse(throwable.getMessage());
                            }

                        }
                    });
        }
    }

    public void create(SignUp signUp, final SignUpCallback signUpCallback) {
        if (signUpCallback != null) {
            service.createUser(signUp)
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Action1<CreateResponse>() {
                        @Override
                        public void call(CreateResponse createResponse) {
                            signUpCallback.onSignUpResponse(createResponse.getErrors());
                        }
                    }, new Action1<Throwable>() {
                        @Override
                        public void call(Throwable throwable) {
                            try {
                                RetrofitException error = (RetrofitException) throwable;
                                CreateResponse response = error.getErrorBodyAs(CreateResponse.class);
                                signUpCallback.onSignUpResponse(response.getErrors());
                            } catch (IOException e1) {
                                Error error = new Error("", throwable.getMessage());
                                List<Error> errors = new ArrayList<>();
                                errors.add(error);
                                signUpCallback.onSignUpResponse(errors);
                                e1.printStackTrace();
                            }
                        }
                    });
        }
    }

    private void setToken(String token) {
        this.mToken = token;
    }

    public String getmToken() {
        return mToken;
    }

    public interface SignInCallback {
        void onSignInResponse(String error);
    }

    public interface SignUpCallback {
        void onSignUpResponse(List<Error> errors);
    }
}
