package br.com.hlandim.supermarket.home.products;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import br.com.hlandim.supermarket.R;
import br.com.hlandim.supermarket.data.service.response.ProductResponse;
import br.com.hlandim.supermarket.databinding.ProductListRowBinding;
import br.com.hlandim.supermarket.home.products.viewmodel.ProductsItemViewModel;

/**
 * Created by hlandim on 14/01/17.
 */

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ProductHolder> {

    private List<ProductResponse> mProductList;

    public ProductsAdapter(List<ProductResponse> mProductList) {
        this.mProductList = mProductList;
    }

    @Override
    public ProductHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_list_row, parent, false);
        return new ProductHolder(view);
    }

    @Override
    public void onBindViewHolder(ProductHolder holder, int position) {
        ProductResponse productResponse = mProductList.get(position);
        ProductListRowBinding binding = holder.getBinding();
        binding.setItemViewModel(new ProductsItemViewModel(productResponse));
        binding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return mProductList.size();
    }

    class ProductHolder extends RecyclerView.ViewHolder {

        private ProductListRowBinding binding;

        ProductHolder(View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
        }

        public ProductListRowBinding getBinding() {
            return binding;
        }
    }
}
