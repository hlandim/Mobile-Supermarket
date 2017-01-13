package br.com.hlandim.supermarket.service;

import br.com.hlandim.supermarket.service.response.CreateResponse;
import br.com.hlandim.supermarket.signup.model.SignUp;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.POST;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by hlandim on 10/01/17.
 */

public interface SessionService {

    @POST("users")
    Observable<CreateResponse> createUser(@Body SignUp signUp);

}
