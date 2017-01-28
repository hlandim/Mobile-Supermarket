package br.com.hlandim.supermarket.manager;

import android.app.Activity;
import android.content.ContextWrapper;

import com.google.gson.Gson;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import br.com.hlandim.supermarket.config.Endpoint;
import br.com.hlandim.supermarket.data.service.ProductService;
import br.com.hlandim.supermarket.data.service.response.Error;
import br.com.hlandim.supermarket.data.service.response.Product;
import br.com.hlandim.supermarket.exception.RetrofitException;
import br.com.hlandim.supermarket.util.ServerUtil;
import rx.Observer;
import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by hlandim on 16/01/17.
 */

public class ProductManager extends ContextWrapper {

    private static ProductManager instance;
    private ProductService service;
    private SessionManager mSessionManager;
    private ProductManagerListener mListener;

    private Observer<List<Product>> observerProduct = new Observer<List<Product>>() {
        @Override
        public void onCompleted() {

        }

        @Override
        public void onError(Throwable throwable) {
            try {
                RetrofitException error = (RetrofitException) throwable;
                Product response = error.getErrorBodyAs(Product.class);
                mListener.onGotError(response.getErrors());
            } catch (Exception e1) {
                Error error = new Error("", throwable.getMessage());
                List<Error> errors = new ArrayList<>();
                errors.add(error);
                mListener.onGotError(errors);
                e1.printStackTrace();
            }
        }

        @Override
        public void onNext(List<Product> products) {
            mListener.onGotProducts(products);
        }
    };

    private ProductManager(Activity base) {
        super(base);
        service = ServerUtil.getService(ProductService.class, Endpoint.SERVER_DATA, base);
        mSessionManager = SessionManager.getInstance(base);
    }

    public static ProductManager getInstance(Activity activity) {
        if (instance == null) {
            instance = new ProductManager(activity);
        }
        return instance;
    }

    public void fetchProductsByType() {
        if (mListener != null) {
            Scheduler scheduler = Schedulers.newThread();
            service.list(Endpoint.SUFIX_KEY_AUTH + mSessionManager.getToken())
                    .subscribeOn(scheduler)
                    .unsubscribeOn(scheduler)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(observerProduct);
        }
    }

    public void fetchProductsByType(String filter) {
        FilterType filterTypeObj = new FilterType(filter);
        String filterJson = new Gson().toJson(filterTypeObj);
        try {
            filterJson = URLEncoder.encode(filterJson, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Scheduler scheduler = Schedulers.newThread();
        service.listWithFilter(Endpoint.SUFIX_KEY_AUTH + mSessionManager.getToken(), filterJson)
                .subscribeOn(scheduler)
                .unsubscribeOn(scheduler)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observerProduct);
    }

    public void fetchProductsByTitle(String title) {
        FilterTitle filterTypeObj = new FilterTitle(title);
        String filterJson = new Gson().toJson(filterTypeObj);
        try {
            filterJson = URLEncoder.encode(filterJson, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Scheduler scheduler = Schedulers.newThread();
        service.listWithFilter(Endpoint.SUFIX_KEY_AUTH + mSessionManager.getToken(), filterJson)
                .subscribeOn(scheduler)
                .unsubscribeOn(scheduler)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observerProduct);
    }

    public void setListener(ProductManagerListener listener) {
        this.mListener = listener;
    }

    public interface ProductManagerListener {
        void onGotProducts(List<Product> list);

        void onGotError(List<Error> errors);
    }

    public class FilterType {
        private String type;

        public FilterType(String type) {
            this.type = type;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }

    public class FilterTitle {
        private String title;

        public FilterTitle(String title) {
            this.title = title;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }

}
