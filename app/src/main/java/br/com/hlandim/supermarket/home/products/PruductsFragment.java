package br.com.hlandim.supermarket.home.products;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

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
    private ProductsViewModel mViewModel;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_products, container, false);
        mBinding.rvProducts.setHasFixedSize(true);
        mBinding.rvProducts.setLayoutManager(new StaggeredGridLayoutManager(2, 1));
        mViewModel = new ProductsViewModel(getActivity(), this);
        mViewModel.fetchProducts();

        return mBinding.getRoot();
    }

    @Override
    public void onGotProducts() {
        mBinding.setProductsViewModel(mViewModel);
    }

    @Override
    public void onGotError(List<Error> errors) {

    }
}
