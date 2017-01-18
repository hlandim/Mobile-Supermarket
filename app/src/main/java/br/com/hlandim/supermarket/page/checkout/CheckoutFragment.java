package br.com.hlandim.supermarket.page.checkout;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import br.com.hlandim.supermarket.R;
import br.com.hlandim.supermarket.data.service.response.CartItem;
import br.com.hlandim.supermarket.data.service.response.Error;
import br.com.hlandim.supermarket.databinding.FragmentCheckoutBinding;
import br.com.hlandim.supermarket.page.checkout.adapter.CheckoutAdapter;
import br.com.hlandim.supermarket.page.checkout.viewmodel.CheckoutViewModel;
import br.com.hlandim.supermarket.page.checkout.viewmodel.CheckoutViewModelListener;
import br.com.hlandim.supermarket.page.home.HomeBaseFragment;
import br.com.hlandim.supermarket.page.home.products.viewmodel.ProductsViewModelListener;
import br.com.hlandim.supermarket.util.Util;

/**
 * Created by hlandim on 16/01/17.
 */

public class CheckoutFragment extends HomeBaseFragment implements CheckoutViewModelListener {

    private FragmentCheckoutBinding mBinding;
    private CheckoutViewModel mCheckoutViewModel;
    private CheckoutAdapter mAdapter;
    private List<CartItem> mCartItems;
    private ProductsViewModelListener mProductsViewModelListener;

    public static CheckoutFragment newInstance(ProductsViewModelListener productsViewModelListener, List<CartItem> list) {
        CheckoutFragment fragment = new CheckoutFragment();
        fragment.mProductsViewModelListener = productsViewModelListener;
        fragment.mCartItems = list;
        return fragment;

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_checkout, container, false);
        mCheckoutViewModel = new CheckoutViewModel(getActivity(), this);
        mBinding.rvCartList.setLayoutManager(new LinearLayoutManager(getContext()));
        showProgress(true, getString(R.string.loading_fetch_cart_items));
        if (mCartItems == null) {
            mCheckoutViewModel.listCartItems();
        } else {
            mAdapter = null;
            mCheckoutViewModel.updateList(mCartItems);
        }
        setRetainInstance(true);
        return mBinding.getRoot();
    }

    @Override
    public void onGotCartItems(List<CartItem> list) {
        mBinding.setCheckoutViewModel(mCheckoutViewModel);
        mCartItems = list;
        if (list != null) {
            if (mAdapter == null) {
                mAdapter = new CheckoutAdapter(list);
                mAdapter.setListener(mCheckoutViewModel);
                mBinding.rvCartList.setAdapter(mAdapter);
            } else {
                mAdapter.setCartItems(list);
                mAdapter.notifyDataSetChanged();
            }
        }
        showProgress(false);
    }

    @Override
    public void onGotError(List<Error> errors) {
        for (Error error : errors) {
            Util.getErrorSnackbar(mBinding.rvCartList, error.getMessage()).show();
        }
    }

    @Override
    public void onRemovedItem(CartItem cartItem) {
        mCartItems.remove(cartItem);
        mCheckoutViewModel.updateList(mCartItems);
        mProductsViewModelListener.updateCart(mCartItems);
        mAdapter.notifyDataSetChanged();
        if (mCartItems.size() == 0) {
            getActivity().onBackPressed();
        }
    }
}
