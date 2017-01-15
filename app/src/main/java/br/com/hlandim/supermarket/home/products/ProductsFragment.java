package br.com.hlandim.supermarket.home.products;

import android.databinding.DataBindingUtil;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

import br.com.hlandim.supermarket.R;
import br.com.hlandim.supermarket.data.service.response.Error;
import br.com.hlandim.supermarket.data.service.response.ProductResponse;
import br.com.hlandim.supermarket.databinding.FragmentProductsBinding;
import br.com.hlandim.supermarket.home.HomeActivity;
import br.com.hlandim.supermarket.home.products.viewmodel.ProductsViewModel;
import br.com.hlandim.supermarket.home.products.viewmodel.ProductsViewModelListener;

/**
 * Created by hlandim on 14/01/17.
 */
public class ProductsFragment extends Fragment implements ProductsViewModelListener {

    private FragmentProductsBinding mBinding;
    private ProductsViewModel mViewModel;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_products, container, false);
        mBinding.rvProducts.setHasFixedSize(true);
        mBinding.rvProducts.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        SpacesItemDecoration decoration = new SpacesItemDecoration(5);
        mBinding.rvProducts.addItemDecoration(decoration);
        mViewModel = new ProductsViewModel(getActivity(), this);
        mViewModel.fetchProducts();

        return mBinding.getRoot();
    }

    @Override
    public void onGotProducts(List<ProductResponse> list) {
        mBinding.setProductsViewModel(mViewModel);
        if (list != null) {
            ProductsAdapter productsAdapter = new ProductsAdapter(list);
            productsAdapter.setListListener(mViewModel);
            mBinding.rvProducts.setAdapter(productsAdapter);
        }
    }

    @Override
    public void onGotError(List<Error> errors) {

    }

    @Override
    public void onProductClicked(ProductResponse productResponse, ImageView imageView) {
        ((HomeActivity) getActivity()).showProducDetails(imageView, "product_img");
    }

    public class SpacesItemDecoration extends RecyclerView.ItemDecoration {
        private final int mSpace;
        public SpacesItemDecoration(int space) {
            this.mSpace = space;
        }
        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            outRect.left = mSpace;
            outRect.right = mSpace;
            outRect.bottom = mSpace;
            // Add top margin only for the first item to avoid double space between items
            if (parent.getChildAdapterPosition(view) == 0)
                outRect.top = mSpace;
        }
    }
}
