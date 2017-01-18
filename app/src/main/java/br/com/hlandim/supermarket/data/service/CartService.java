package br.com.hlandim.supermarket.data.service;

import java.util.List;

import br.com.hlandim.supermarket.data.service.request.AddToCartRequest;
import br.com.hlandim.supermarket.data.service.response.AddToCartResponse;
import br.com.hlandim.supermarket.data.service.response.CartItem;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by hlandim on 16/01/17.
 */

public interface CartService {

    @POST("cart")
    Observable<AddToCartResponse> addToCart(@Header("Authorization") String authorization, @Body AddToCartRequest addToCartRequest);

    @GET("cart")
    Observable<List<CartItem>> listItens(@Header("Authorization") String authorization);

    @DELETE("cart/{id}")
    Observable<String> deleteItem(@Header("Authorization") String authorization, @Path("id") String id);
}
