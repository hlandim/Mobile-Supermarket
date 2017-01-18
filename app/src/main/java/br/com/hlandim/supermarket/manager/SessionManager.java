package br.com.hlandim.supermarket.manager;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.SharedPreferences;
import android.text.TextUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import br.com.hlandim.supermarket.R;
import br.com.hlandim.supermarket.config.Endpoint;
import br.com.hlandim.supermarket.data.service.SessionService;
import br.com.hlandim.supermarket.data.service.response.CreateResponse;
import br.com.hlandim.supermarket.data.service.response.Error;
import br.com.hlandim.supermarket.data.service.response.SignInResponse;
import br.com.hlandim.supermarket.exception.RetrofitException;
import br.com.hlandim.supermarket.page.main.signin.model.SignIn;
import br.com.hlandim.supermarket.page.main.signup.model.SignUp;
import br.com.hlandim.supermarket.util.JWTUtils;
import br.com.hlandim.supermarket.util.JwtToken;
import br.com.hlandim.supermarket.util.ServerUtil;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by hlandim on 14/01/17.
 * This class is responsible to create and manage the user session
 */

public class SessionManager extends ContextWrapper {

    private static final String SHARED_PREFERENCES_TOKEN_KEY = "auth_token";
    private static final String SHARED_PREFERENCES_EMAIL_KEY = "auth_email";
    private static final String SHARED_PREFERENCES_PASSWORD_KEY = "auth_password";
    private static final String STATUS_WITHOUT_CREDENTIAIS = "without_credentiais";
    private static SessionManager instance;
    private SessionService mService;
    private String mToken;
    private JwtToken mJwtToken;
    private SharedPreferences mSharedPreferences;

    private SessionManager(Context base) {
        super(base);
        mService = ServerUtil.getService(SessionService.class, Endpoint.SERVER_AUTH);
        mSharedPreferences = base.getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE);


    }

    public static SessionManager getInstance(Activity base) {
        if (instance == null) {
            instance = new SessionManager(base);
        }
        return instance;
    }

    public void signInWithSavedCrendentials(SignInCallback signInCallback) {
        String email = mSharedPreferences.getString(SHARED_PREFERENCES_EMAIL_KEY, null);
        String password = mSharedPreferences.getString(SHARED_PREFERENCES_PASSWORD_KEY, null);
        if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)) {
            SignIn signIn = new SignIn(email, password);
            signIn(signIn, signInCallback);
        } else {
            signInCallback.onSignInResponse(STATUS_WITHOUT_CREDENTIAIS);
        }
    }

    public void signIn(final SignIn signIn, final SignInCallback signInCallback) {
        if (signInCallback != null) {
            mService.signIn(signIn.getEmail(), signIn.getPassword(), signIn.getGrantType())
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(signInResponse -> {
                        try {
                            setToken(signInResponse.getAccessToken());
                            saveUserCredentiais(signIn.getEmail(), signIn.getPassword());
                            signInCallback.onSignInResponse(signInResponse.getError());
                        } catch (Exception e) {
                            e.printStackTrace();
                            signInCallback.onSignInResponse(e.getMessage());
                        }
                    }, throwable -> {
                        try {
                            RetrofitException error = (RetrofitException) throwable;
                            SignInResponse response = error.getErrorBodyAs(SignInResponse.class);
                            signInCallback.onSignInResponse(response.getErrorDescription());
                        } catch (IOException e1) {
                            signInCallback.onSignInResponse(throwable.getMessage());
                        }
                    });
        }
    }

    public void logout() {
        mSharedPreferences.edit().clear().apply();
    }

    public void create(SignUp signUp, final SignUpCallback signUpCallback) {
        if (signUpCallback != null) {
            mService.createUser(signUp)
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(createResponse -> signUpCallback.onSignUpResponse(createResponse.getErrors())
                            , throwable -> {
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
                            });
        }
    }

    private void saveUserCredentiais(String email, String password) {
        mSharedPreferences.edit()
                .putString(SHARED_PREFERENCES_EMAIL_KEY, email)
                .putString(SHARED_PREFERENCES_PASSWORD_KEY, password)
                .apply();
    }

    public String getToken() {
        return mToken;
    }

    private void setToken(String token) throws Exception {
        mSharedPreferences.edit().putString(SHARED_PREFERENCES_TOKEN_KEY, token).apply();
        this.mToken = token;
        this.mJwtToken = JWTUtils.decoded(mToken);
    }

    public JwtToken getJwtToken() {
        return mJwtToken;
    }

    public interface SignInCallback {
        void onSignInResponse(String error);
    }

    public interface SignUpCallback {
        void onSignUpResponse(List<Error> errors);
    }


}
