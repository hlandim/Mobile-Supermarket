package br.com.hlandim.supermarket.page.home.products.viewmodel;

import android.app.Activity;
import android.content.ContextWrapper;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import java.util.List;

import br.com.hlandim.supermarket.MainActivity;
import br.com.hlandim.supermarket.R;
import br.com.hlandim.supermarket.data.service.response.CartItem;
import br.com.hlandim.supermarket.data.service.response.Error;
import br.com.hlandim.supermarket.data.service.response.Product;
import br.com.hlandim.supermarket.manager.CartManager;
import br.com.hlandim.supermarket.manager.ProductManager;
import br.com.hlandim.supermarket.manager.SessionManager;
import br.com.hlandim.supermarket.page.home.products.ProductsAdapter;

/**
 * Created by hlandim on 14/01/17.
 */

public class ProductsViewModel extends ContextWrapper implements ProductsAdapter.ProductsListListener, ProductManager.ProductManagerListener {


    private ProductsViewModelListener mListener;
    private ProductManager mProductManager;
    private CartManager mCartManager;


    public ProductsViewModel(Activity base, ProductsViewModelListener listener) {
        super(base);
        this.mListener = listener;
        this.mProductManager = ProductManager.getInstance(base);
        mProductManager.setListener(this);
        this.mCartManager = CartManager.getInstance(base);
    }

    public void fetchProductsByType() {
        mProductManager.fetchProductsByType();
    }

    public void fetchCartItens() {
        mListener.showProgress(true, getString(R.string.loading_fetch_cart_items));
        mCartManager.listItems(new CartManager.ListItemsCallback() {
            @Override
            public void onGotItems(List<CartItem> list) {
                mListener.updateCart(list);
                mListener.showProgress(false);
            }

            @Override
            public void onGotErrors(List<Error> errors) {
                mListener.onGotError(errors);
                mListener.showProgress(false);
            }
        });
    }

    public void fetchProductsByType(String filter) {
        mProductManager.fetchProductsByType(filter);
    }

    public void fetchProductsByTitle(String title) {
        mProductManager.fetchProductsByTitle(title);
    }


    @Override
    public void onProductClicked(Product product, ImageView sharedImg) {
        if (mListener != null) {
            mListener.onProductClicked(product, sharedImg);
        }
    }

    @Override
    public void onAddToCartClicked(final Product product) {
        if (mListener != null) {
            mListener.showProgress(true, getString(R.string.loading_adding_cart_item));
            mCartManager.addToCart(product, new CartManager.AddToCartCallback() {
                @Override
                public void onAddToCartResponse(List<Error> errors) {
                    if (errors == null) {
                        mListener.addedToCart(product);
                    } else {
                        onGotError(errors);
                    }
                    mListener.showProgress(false);
                }
            });

        }
    }

    public void fabClicked(View v) {
        if (mListener != null) {
            mListener.goToCheckout();
        }

    }

    @Override
    public void onGotProducts(List<Product> list) {
        if (mListener != null) {
            mListener.onGotProducts(list);
        }
    }

    @Override
    public void onGotError(List<Error> errors) {
        if (mListener != null) {
            mListener.onGotError(errors);
        }
    }

    public void logout() {
        SessionManager.getInstance((Activity) getBaseContext()).logout();
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}
