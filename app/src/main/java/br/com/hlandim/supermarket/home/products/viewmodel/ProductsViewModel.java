package br.com.hlandim.supermarket.home.products.viewmodel;

import android.app.Activity;
import android.content.ContextWrapper;
import android.databinding.BindingAdapter;
import android.support.v7.widget.RecyclerView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import br.com.hlandim.supermarket.SuperMarketApplication;
import br.com.hlandim.supermarket.config.Endpoint;
import br.com.hlandim.supermarket.data.service.ProductService;
import br.com.hlandim.supermarket.data.service.response.Error;
import br.com.hlandim.supermarket.data.service.response.ProductResponse;
import br.com.hlandim.supermarket.exception.RetrofitException;
import br.com.hlandim.supermarket.home.products.ProductsAdapter;
import br.com.hlandim.supermarket.manager.SessionManager;
import br.com.hlandim.supermarket.util.ServerUtil;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by hlandim on 14/01/17.
 */

public class ProductsViewModel extends ContextWrapper {

    private List<ProductResponse> products;
    private ProductService service;
    private ProductsViewModelListener listener;
    private SessionManager mSessionManager;

    public ProductsViewModel(Activity base, ProductsViewModelListener listener) {
        super(base);
        this.listener = listener;
        service = ServerUtil.getService(ProductService.class, Endpoint.SERVER_DATA);
        mSessionManager = ((SuperMarketApplication) base.getApplication()).getSessionManager();
    }

    @BindingAdapter("items")
    public static void setItems(RecyclerView view, List<ProductResponse> list) {
        if (list != null) {
            ProductsAdapter productsAdapter = new ProductsAdapter(list);
            view.setAdapter(productsAdapter);
        }
    }

    public void fetchProducts() {
        if (listener != null) {
            service.list("Bearer " + mSessionManager.getmToken())
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Action1<List<ProductResponse>>() {
                        @Override
                        public void call(List<ProductResponse> products) {
                            setProducts(products);
                            listener.onGotProducts();
                        }
                    }, new Action1<Throwable>() {
                        @Override
                        public void call(Throwable throwable) {
                            try {
                                RetrofitException error = (RetrofitException) throwable;
                                ProductResponse response = error.getErrorBodyAs(ProductResponse.class);
                                listener.onGotError(response.getErrors());
                            } catch (IOException e1) {
                                Error error = new Error("", throwable.getMessage());
                                List<Error> errors = new ArrayList<>();
                                errors.add(error);
                                listener.onGotError(errors);
                                e1.printStackTrace();
                            }
                            Error error = new Error(null, throwable.getMessage());
                        }
                    });
        }
    }

    public List<ProductResponse> getProducts() {
        return products;
    }

    public void setProducts(List<ProductResponse> products) {
        this.products = products;
    }
}
