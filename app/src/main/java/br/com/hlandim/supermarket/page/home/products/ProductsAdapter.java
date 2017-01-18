package br.com.hlandim.supermarket.page.home.products;

import android.databinding.DataBindingUtil;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

import br.com.hlandim.supermarket.R;
import br.com.hlandim.supermarket.data.service.response.Product;
import br.com.hlandim.supermarket.databinding.ProductListRowBinding;
import br.com.hlandim.supermarket.page.home.products.viewmodel.ProductsItemViewModel;

/**
 * Created by hlandim on 14/01/17.
 */

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ProductHolder> {

    private List<Product> mProductList;
    private ProductsListListener mListListener;

    public ProductsAdapter(List<Product> mProductList) {
        this.mProductList = mProductList;
    }

    @Override
    public ProductHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_list_row, parent, false);
        return new ProductHolder(view);
    }

    @Override
    public void onBindViewHolder(ProductHolder holder, int position) {
        Product product = mProductList.get(position);
        ViewCompat.setTransitionName(holder.getBinding().productImage, String.valueOf(position) + "_image");
        ProductListRowBinding binding = holder.getBinding();
        binding.setItemViewModel(new ProductsItemViewModel(product));
        binding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return mProductList.size();
    }

    public void setListListener(ProductsListListener listListener) {
        this.mListListener = listListener;
    }

    public void setProductList(List<Product> productList) {
        this.mProductList = productList;
    }

    public interface ProductsListListener {
        void onProductClicked(Product product, ImageView sharedImg);

        void onAddToCartClicked(Product product);
    }

    class ProductHolder extends RecyclerView.ViewHolder {

        private ProductListRowBinding mBinding;

        ProductHolder(View itemView) {
            super(itemView);
            mBinding = DataBindingUtil.bind(itemView);

            mBinding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mListListener != null) {
                        int position = getLayoutPosition();
                        Product product = mProductList.get(position);
                        mListListener.onProductClicked(product, mBinding.productImage);
                    }
                }
            });
            mBinding.btnAddToCart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mListListener != null) {
                        int position = getLayoutPosition();
                        Product product = mProductList.get(position);
                        mListListener.onAddToCartClicked(product);
                    }
                }
            });
        }

        public ProductListRowBinding getBinding() {
            return mBinding;
        }
    }
}
