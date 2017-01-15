package br.com.hlandim.supermarket.home.products.viewmodel;

import android.content.Context;
import android.content.ContextWrapper;
import android.databinding.BindingAdapter;
import android.support.v7.widget.RecyclerView;

import java.util.List;

import br.com.hlandim.supermarket.config.Endpoint;
import br.com.hlandim.supermarket.data.service.ProductService;
import br.com.hlandim.supermarket.data.service.response.Error;
import br.com.hlandim.supermarket.data.service.response.ProductResponse;
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

    public ProductsViewModel(Context base, ProductsViewModelListener listener) {
        super(base);
        this.listener = listener;
        service = ServerUtil.getService(ProductService.class, Endpoint.SERVER_DATA);
    }

    @BindingAdapter("items")
    public static void setItems(RecyclerView view, List<ProductResponse> list) {
        ProductsAdapter productsAdapter = new ProductsAdapter(list);
        view.setAdapter(productsAdapter);
    }

    private void fetchProducts() {
        if (listener != null) {
            service.list(SessionManager.getInstance().getmToken())
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Action1<List<ProductResponse>>() {
                        @Override
                        public void call(List<ProductResponse> products) {
                            setProducts(products);
                        }
                    }, new Action1<Throwable>() {
                        @Override
                        public void call(Throwable throwable) {
                            Error error = new Error(null, throwable.getMessage());
                            listener.onGotError(error);
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
