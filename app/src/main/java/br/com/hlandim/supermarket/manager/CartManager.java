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
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by hlandim on 16/01/17.
 */

public class CartManager extends ContextWrapper {

    private static CartManager instance;
    private CartService service;
    private SessionManager mSessionManager;

    private CartManager(Activity base) {
        super(base);
        service = ServerUtil.getService(CartService.class, Endpoint.SERVER_DATA);
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

            service.addToCart(Endpoint.SUFIX_KEY_AUTH + mSessionManager.getToken(), addToCartRequest)
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Action1<AddToCartResponse>() {
                        @Override
                        public void call(AddToCartResponse addToCartResponse) {
                            callback.onAddToCartResponse(addToCartResponse.getErrors());
                        }
                    }, new Action1<Throwable>() {
                        @Override
                        public void call(Throwable throwable) {
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
                        }
                    });
        }

    }

    public void listItems(final ListItemsCallback callback) {
        if (callback != null) {

            service.listItens(Endpoint.SUFIX_KEY_AUTH + mSessionManager.getToken())
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Action1<List<CartItem>>() {
                        @Override
                        public void call(List<CartItem> list) {
                            callback.onGotItems(list);
                        }
                    }, new Action1<Throwable>() {
                        @Override
                        public void call(Throwable throwable) {
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
                        }
                    });
        }
    }

    public void removeItem(CartItem cartItem, final RemoveItemCallback callback) {
        if (callback != null) {
            service.deleteItem(Endpoint.SUFIX_KEY_AUTH + mSessionManager.getToken(), cartItem.getId())
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Action1<String>() {
                        @Override
                        public void call(String sting) {
                            callback.onRemoveSuccess();
                        }
                    }, new Action1<Throwable>() {
                        @Override
                        public void call(Throwable throwable) {
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
                    });
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
