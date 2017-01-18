package br.com.hlandim.supermarket.data.service;

import br.com.hlandim.supermarket.data.service.response.CreateResponse;
import br.com.hlandim.supermarket.data.service.response.SignInResponse;
import br.com.hlandim.supermarket.page.main.signup.model.SignUp;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by hlandim on 10/01/17.
 */

public interface SessionService {

    @POST("users")
    Observable<CreateResponse> createUser(@Body SignUp signUp);

    @FormUrlEncoded
    @POST("oauth/token")
    Observable<SignInResponse> signIn(@Field("username") String username, @Field("password") String password, @Field("grant_type") String grantType);

}
