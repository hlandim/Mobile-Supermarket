package br.com.hlandim.supermarket.page.checkout.viewmodel;

import java.util.List;

import br.com.hlandim.supermarket.data.service.response.CartItem;
import br.com.hlandim.supermarket.data.service.response.Error;

/**
 * Created by hlandim on 17/01/17.
 */

public interface CheckoutViewModelListener {

    void onGotCartItems(List<CartItem> list);

    void onGotError(List<Error> errors);

    void onRemovedItem(CartItem cartItem);

    void showProgress(boolean show);

    void showProgress(boolean show, String text);
}
