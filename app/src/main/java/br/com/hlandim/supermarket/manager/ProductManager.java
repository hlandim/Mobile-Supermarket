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
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by hlandim on 16/01/17.
 */

public class ProductManager extends ContextWrapper {

    private static ProductManager instance;
    private ProductService service;
    private SessionManager mSessionManager;
    private ProductManagerListener mListener;
    private Action1<List<Product>> successAction1 = new Action1<List<Product>>() {
        @Override
        public void call(List<Product> products) {
            mListener.onGotProducts(products);
        }
    };
    private Action1<Throwable> errorAction1 = new Action1<Throwable>() {
        @Override
        public void call(Throwable throwable) {
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
    };

    private ProductManager(Activity base) {
        super(base);
        service = ServerUtil.getService(ProductService.class, Endpoint.SERVER_DATA);
        mSessionManager = SessionManager.getInstance(base);
    }

    public static ProductManager getInstance(Activity activity) {
        if (instance == null) {
            instance = new ProductManager(activity);
        }
        return instance;
    }

    public void fetchProducts() {
        if (mListener != null) {
            service.list(Endpoint.SUFIX_KEY_AUTH + mSessionManager.getToken())
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(successAction1, errorAction1);
        }
    }

    public void fetchProducts(String filter) {
        Filter filterObj = new Filter(filter);
        String filterJson = new Gson().toJson(filterObj);
        try {
            filterJson = URLEncoder.encode(filterJson, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        service.listWithFilter(Endpoint.SUFIX_KEY_AUTH + mSessionManager.getToken(), filterJson)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(successAction1, errorAction1);
    }

    public void setListener(ProductManagerListener listener) {
        this.mListener = listener;
    }

    public interface ProductManagerListener {
        void onGotProducts(List<Product> list);

        void onGotError(List<Error> errors);
    }

    public class Filter {
        private String type;

        public Filter(String type) {
            this.type = type;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }

}
