package br.com.hlandim.supermarket.page.home.products;

import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import br.com.hlandim.supermarket.R;
import br.com.hlandim.supermarket.data.service.response.Product;
import br.com.hlandim.supermarket.databinding.FragmentProductDetailsBinding;
import br.com.hlandim.supermarket.page.home.HomeBaseFragment;
import br.com.hlandim.supermarket.page.home.products.viewmodel.ProductDetailsViewModel;
import br.com.hlandim.supermarket.page.home.products.viewmodel.ProductDetailsViewModelListener;

/**
 * Created by hlandim on 15/01/17.
 */

public class ProductDetailsFragment extends HomeBaseFragment {

    private FragmentProductDetailsBinding mBinding;
    private Product mProduct;
    private ProductDetailsViewModelListener mProductsViewModelListener;
    private Bitmap bitmap;

    public static ProductDetailsFragment newInstance(Product product, ProductDetailsViewModelListener productsViewModelListener, Bitmap bitmap) {
        ProductDetailsFragment fragment = new ProductDetailsFragment();
        fragment.mProduct = product;
        fragment.mProductsViewModelListener = productsViewModelListener;
        fragment.bitmap = bitmap;
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_product_details, container, false);
        ProductDetailsViewModel productDetailsViewModel = new ProductDetailsViewModel(getActivity(), mProduct, mProductsViewModelListener);
        mBinding.setProduct(productDetailsViewModel);
        setRetainInstance(true);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (bitmap != null) {
            mBinding.productImage.setImageBitmap(bitmap);
        }
    }
}
