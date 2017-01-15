package br.com.hlandim.supermarket.data.service;

import java.util.List;

import br.com.hlandim.supermarket.data.service.response.ProductResponse;
import retrofit2.http.GET;
import retrofit2.http.Header;
import rx.Observable;

/**
 * Created by hlandim on 13/01/17.
 */

public interface ProductService {

    @GET("products")
    Observable<List<ProductResponse>> list(@Header("Authorization") String authorization);
}
