package br.com.hlandim.supermarket.data.service;

import java.util.List;

import br.com.hlandim.supermarket.data.service.response.Product;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by hlandim on 13/01/17.
 */

public interface ProductService {

    @GET("products")
    Observable<List<Product>> list(@Header("Authorization") String authorization);

    @GET("products")
    Observable<List<Product>> listWithFilter(@Header("Authorization") String authorization, @Query(value = "filter", encoded = true) String filter);


}
