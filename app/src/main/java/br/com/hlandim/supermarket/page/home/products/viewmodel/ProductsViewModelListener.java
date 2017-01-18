package br.com.hlandim.supermarket.page.home.products.viewmodel;

import android.widget.ImageView;

import java.util.List;

import br.com.hlandim.supermarket.data.service.response.CartItem;
import br.com.hlandim.supermarket.data.service.response.Error;
import br.com.hlandim.supermarket.data.service.response.Product;

/**
 * Created by hlandim on 14/01/17.
 */

public interface ProductsViewModelListener {
    void onGotProducts(List<Product> list);

    void onGotError(List<Error> errors);

    void onProductClicked(Product product, ImageView imageView);

    void addedToCart(Product product);

    void updateCart(List<CartItem> cartItems);

    void goToCheckout();

    void showProgress(boolean show, String text);

    void showProgress(boolean show);
}
