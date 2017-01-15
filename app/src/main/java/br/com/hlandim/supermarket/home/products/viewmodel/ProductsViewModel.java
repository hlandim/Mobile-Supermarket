package br.com.hlandim.supermarket.home.products.viewmodel;

import android.app.Activity;
import android.content.ContextWrapper;
import android.widget.ImageView;

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

public class ProductsViewModel extends ContextWrapper implements ProductsAdapter.ProductsListListener {

    private ProductService service;
    private ProductsViewModelListener mListener;
    private SessionManager mSessionManager;

    public ProductsViewModel(Activity base, ProductsViewModelListener listener) {
        super(base);
        this.mListener = listener;
        service = ServerUtil.getService(ProductService.class, Endpoint.SERVER_DATA);
        mSessionManager = ((SuperMarketApplication) base.getApplication()).getSessionManager();
    }

  /*  @BindingAdapter("items")
    public static void setItems(RecyclerView view, List<ProductResponse> list) {
        if (list != null) {
            ProductsAdapter productsAdapter = new ProductsAdapter(list);
            view.setAdapter(productsAdapter);
        }
    }*/

    public void fetchProducts() {
        if (mListener != null) {
            service.list("Bearer " + mSessionManager.getmToken())
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Action1<List<ProductResponse>>() {
                        @Override
                        public void call(List<ProductResponse> products) {
                            mListener.onGotProducts(products);
                        }
                    }, new Action1<Throwable>() {
                        @Override
                        public void call(Throwable throwable) {
                            try {
                                RetrofitException error = (RetrofitException) throwable;
                                ProductResponse response = error.getErrorBodyAs(ProductResponse.class);
                                mListener.onGotError(response.getErrors());
                            } catch (IOException e1) {
                                Error error = new Error("", throwable.getMessage());
                                List<Error> errors = new ArrayList<>();
                                errors.add(error);
                                mListener.onGotError(errors);
                                e1.printStackTrace();
                            }
                            Error error = new Error(null, throwable.getMessage());
                        }
                    });
        }
    }

    @Override
    public void onProductClicked(ProductResponse product, ImageView sharedImg) {
        if (mListener != null) {
            mListener.onProductClicked(product, sharedImg);
        }
    }
}
