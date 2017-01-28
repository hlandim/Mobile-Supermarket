package br.com.hlandim.supermarket.page.checkout.viewmodel;

import android.app.Activity;
import android.content.ContextWrapper;

import java.util.List;
import java.util.Locale;

import br.com.hlandim.supermarket.R;
import br.com.hlandim.supermarket.data.service.response.CartItem;
import br.com.hlandim.supermarket.data.service.response.Error;
import br.com.hlandim.supermarket.manager.CartManager;
import br.com.hlandim.supermarket.page.checkout.adapter.CheckoutAdapter;

/**
 * Created by hlandim on 16/01/17.
 */

public class CheckoutViewModel extends ContextWrapper implements CheckoutAdapter.CheckoutListInterface {

    private CartManager manager;
    private CheckoutViewModelListener mListener;
    private String mTotal;


    public CheckoutViewModel(Activity base, CheckoutViewModelListener listener) {
        super(base);
        manager = CartManager.getInstance(base);
        this.mListener = listener;
    }

    public void listCartItems() {
        if (mListener != null) {
            manager.listItems(new CartManager.ListItemsCallback() {
                @Override
                public void onGotItems(List<CartItem> list) {
                    updateList(list);
                }

                @Override
                public void onGotErrors(List<Error> errors) {
                    mListener.onGotError(errors);
                }
            });
        }
    }

    public void updateList(List<CartItem> list) {
        double total = 0;
        for (CartItem cartItem : list) {
            total += cartItem.getProductPrice();
        }
        String totalPrice = String.format(Locale.getDefault(), "%.2f", total);
        setTotalPrice("$" + totalPrice);
        mListener.onGotCartItems(list);
    }

    public String getTotalPrice() {
        return mTotal;
    }

    public void setTotalPrice(String totalPrice) {
        this.mTotal = totalPrice;
    }

    @Override
    public void onDeleteCartItemClicked(final CartItem cartItem) {
        if (mListener != null) {
            mListener.showProgress(true, getString(R.string.loading_removing_item));
            manager.removeItem(cartItem, new CartManager.RemoveItemCallback() {
                @Override
                public void onRemoveSuccess() {
                    mListener.onRemovedItem(cartItem);
                    mListener.showProgress(false);
                }

                @Override
                public void onGoError(List<Error> errors) {
                    mListener.onGotError(errors);
                    mListener.showProgress(false);
                }
            });
        }
    }
}
