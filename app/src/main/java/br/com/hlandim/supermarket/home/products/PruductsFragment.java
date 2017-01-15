package br.com.hlandim.supermarket.home.products;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import br.com.hlandim.supermarket.R;
import br.com.hlandim.supermarket.data.service.response.Error;
import br.com.hlandim.supermarket.databinding.FragmentProductsBinding;
import br.com.hlandim.supermarket.home.products.viewmodel.ProductsViewModel;
import br.com.hlandim.supermarket.home.products.viewmodel.ProductsViewModelListener;

/**
 * Created by hlandim on 14/01/17.
 */

public class PruductsFragment extends Fragment implements ProductsViewModelListener {

    private FragmentProductsBinding mBinding;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.setContentView(getActivity(), R.layout.fragment_products);
        ProductsViewModel viewModel = new ProductsViewModel(getActivity(), this);
        mBinding.setProductsViewModel(viewModel);

        return mBinding.getRoot();
    }

    @Override
    public void onGotError(Error error) {

    }
}
