package br.com.hlandim.supermarket.util;

import br.com.hlandim.supermarket.exception.RxErrorHandlingCallAdapterFactory;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by hlandim on 11/01/17.
 */

public class ServerUtil {

    public static <T> T getService(Class<T> serviceClass, String url) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxErrorHandlingCallAdapterFactory.create())
                .build();
        return retrofit.create(serviceClass);
    }
}
