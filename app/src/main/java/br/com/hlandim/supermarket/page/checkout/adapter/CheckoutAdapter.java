package br.com.hlandim.supermarket.page.checkout.adapter;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import br.com.hlandim.supermarket.R;
import br.com.hlandim.supermarket.data.service.response.CartItem;
import br.com.hlandim.supermarket.databinding.CartListRowBinding;

/**
 * Created by hlandim on 17/01/17.
 */

public class CheckoutAdapter extends RecyclerView.Adapter<CheckoutAdapter.CheckoutHolder> {

    private CheckoutListInterface mListener;
    private List<CartItem> mCartItems;

    public CheckoutAdapter(List<CartItem> mCartItems) {
        this.mCartItems = mCartItems;
    }

    public void setListener(CheckoutListInterface listListener) {
        this.mListener = listListener;
    }

    public void setCartItems(List<CartItem> cartItems) {
        this.mCartItems = cartItems;
    }

    @Override
    public CheckoutHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_list_row, parent, false);
        return new CheckoutAdapter.CheckoutHolder(view);
    }

    @Override
    public void onBindViewHolder(CheckoutHolder holder, int position) {
        CartItem cartItem = mCartItems.get(position);
        holder.mBinding.setCartItem(cartItem);
    }

    @Override
    public int getItemCount() {
        return mCartItems.size();
    }

    public interface CheckoutListInterface {
        void onDeleteCartItemClicked(CartItem cartItem);
    }

    class CheckoutHolder extends RecyclerView.ViewHolder {

        private CartListRowBinding mBinding;

        CheckoutHolder(View itemView) {
            super(itemView);
            mBinding = DataBindingUtil.bind(itemView);

            mBinding.btnDelete.setOnClickListener(v -> {
                        if (mListener != null) {
                            int position = getLayoutPosition();
                            CartItem cartItem = mCartItems.get(position);
                            mListener.onDeleteCartItemClicked(cartItem);
                        }
                    }
            );
        }
    }
}
