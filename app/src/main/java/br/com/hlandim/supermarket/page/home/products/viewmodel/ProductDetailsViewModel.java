package br.com.hlandim.supermarket.page.home.products.viewmodel;

import android.app.Activity;
import android.content.ContextWrapper;
import android.view.View;

import java.util.List;

import br.com.hlandim.supermarket.R;
import br.com.hlandim.supermarket.data.service.response.Error;
import br.com.hlandim.supermarket.data.service.response.Product;
import br.com.hlandim.supermarket.manager.CartManager;

/**
 * Created by hlandim on 15/01/17.
 */

public class ProductDetailsViewModel extends ContextWrapper {
    private Product mProduct;
    private ProductDetailsViewModelListener listener;
    private CartManager mCartManager;


    public ProductDetailsViewModel(Activity base, Product response, ProductDetailsViewModelListener listener) {
        super(base);
        this.listener = listener;
        this.mProduct = response;
        mCartManager = CartManager.getInstance(base);
    }

    public String getDescription() {
        return mProduct.getDescription();
    }

    public int getRating() {
        return mProduct.getRating();
    }

    public String getTitle() {
        return mProduct.getTitle();
    }

    public String getPrice() {
        return "$" + mProduct.getPrice();
    }

    public void addToCart(View v) {
        listener.showProgress(true, getString(R.string.loading_adding_cart_item));
        mCartManager.addToCart(mProduct, new CartManager.AddToCartCallback() {
            @Override
            public void onAddToCartResponse(List<Error> errors) {
                listener.showProgress(true, getString(R.string.loading_adding_cart_item));
                if (errors != null) {
                    listener.onGotError(errors);
                } else {
                    listener.addToCartFromDetails(mProduct);
                }
            }
        });

    }


}
