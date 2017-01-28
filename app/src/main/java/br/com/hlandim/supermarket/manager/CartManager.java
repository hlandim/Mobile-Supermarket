package br.com.hlandim.supermarket.manager;

import android.app.Activity;
import android.content.ContextWrapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import br.com.hlandim.supermarket.config.Endpoint;
import br.com.hlandim.supermarket.data.service.CartService;
import br.com.hlandim.supermarket.data.service.request.AddToCartRequest;
import br.com.hlandim.supermarket.data.service.response.AddToCartResponse;
import br.com.hlandim.supermarket.data.service.response.CartItem;
import br.com.hlandim.supermarket.data.service.response.Error;
import br.com.hlandim.supermarket.data.service.response.Product;
import br.com.hlandim.supermarket.exception.RetrofitException;
import br.com.hlandim.supermarket.util.ServerUtil;
import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by hlandim on 16/01/17.
 */

public class CartManager extends ContextWrapper {

    private static CartManager instance;
    private final CartService service;
    private final SessionManager mSessionManager;

    private CartManager(Activity base) {
        super(base);
        service = ServerUtil.getService(CartService.class, Endpoint.SERVER_DATA, base);
        mSessionManager = SessionManager.getInstance(base);
    }

    public static CartManager getInstance(Activity base) {
        if (instance == null) {
            instance = new CartManager(base);
        }

        return instance;
    }

    public void addToCart(Product product, final AddToCartCallback callback) {

        if (callback != null) {
            AddToCartRequest addToCartRequest = new AddToCartRequest(product.getTitle(),
                    product.getFilename(),
                    product.getId(),
                    mSessionManager.getJwtToken().getSub(),
                    product.getPrice());

            Scheduler scheduler = Schedulers.newThread();
            service.addToCart(Endpoint.SUFIX_KEY_AUTH + mSessionManager.getToken(), addToCartRequest)
                    .subscribeOn(scheduler)
                    .unsubscribeOn(scheduler)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(addToCartResponse ->
                                    callback.onAddToCartResponse(addToCartResponse.getErrors())

                            , throwable -> {
                                try {
                                    RetrofitException error = (RetrofitException) throwable;
                                    AddToCartResponse response = error.getErrorBodyAs(AddToCartResponse.class);
                                    callback.onAddToCartResponse(response.getErrors());
                                } catch (IOException e1) {
                                    Error error = new Error("", throwable.getMessage());
                                    List<Error> errors = new ArrayList<>();
                                    errors.add(error);
                                    callback.onAddToCartResponse(errors);
                                    e1.printStackTrace();
                                }
                            });
        }

    }

    public void listItems(final ListItemsCallback callback) {
        if (callback != null) {

            Scheduler scheduler = Schedulers.newThread();
            service.listItens(Endpoint.SUFIX_KEY_AUTH + mSessionManager.getToken())
                    .subscribeOn(scheduler)
                    .unsubscribeOn(scheduler)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(list -> callback.onGotItems(list)
                            , throwable -> {
                                try {
                                    RetrofitException error = (RetrofitException) throwable;
                                    CartItem response = error.getErrorBodyAs(CartItem.class);
                                    callback.onGotErrors(response.getErrors());
                                } catch (IOException e1) {
                                    Error error = new Error("", throwable.getMessage());
                                    List<Error> errors = new ArrayList<>();
                                    errors.add(error);
                                    callback.onGotErrors(errors);
                                    e1.printStackTrace();
                                }
                            });
        }
    }

    public void removeItem(CartItem cartItem, final RemoveItemCallback callback) {
        if (callback != null) {
            Scheduler scheduler = Schedulers.newThread();
            service.deleteItem(Endpoint.SUFIX_KEY_AUTH + mSessionManager.getToken(), cartItem.getId())
                    .subscribeOn(scheduler)
                    .unsubscribeOn(scheduler)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(response ->
                                    callback.onRemoveSuccess()
                            , throwable -> {
                                try {
                                    RetrofitException error = (RetrofitException) throwable;
                                    CartItem response = error.getErrorBodyAs(CartItem.class);
                                    callback.onGoError(response.getErrors());
                                } catch (IOException e1) {
                                    Error error = new Error("", throwable.getMessage());
                                    List<Error> errors = new ArrayList<>();
                                    errors.add(error);
                                    callback.onGoError(errors);
                                    e1.printStackTrace();
                                }
                            }
                    );
        }
    }

    public interface RemoveItemCallback {
        void onRemoveSuccess();

        void onGoError(List<Error> errors);
    }

    public interface AddToCartCallback {
        void onAddToCartResponse(List<Error> errors);
    }

    public interface ListItemsCallback {
        void onGotItems(List<CartItem> list);

        void onGotErrors(List<Error> errors);
    }
}
