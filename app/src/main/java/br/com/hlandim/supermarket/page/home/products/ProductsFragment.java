package br.com.hlandim.supermarket.page.home.products;

import android.app.AlertDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.res.Configuration;
import android.databinding.DataBindingUtil;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import br.com.hlandim.supermarket.R;
import br.com.hlandim.supermarket.data.service.response.CartItem;
import br.com.hlandim.supermarket.data.service.response.Error;
import br.com.hlandim.supermarket.data.service.response.Product;
import br.com.hlandim.supermarket.databinding.FragmentProductsBinding;
import br.com.hlandim.supermarket.page.home.HomeActivity;
import br.com.hlandim.supermarket.page.home.HomeBaseFragment;
import br.com.hlandim.supermarket.page.home.products.viewmodel.ProductDetailsViewModelListener;
import br.com.hlandim.supermarket.page.home.products.viewmodel.ProductsViewModel;
import br.com.hlandim.supermarket.page.home.products.viewmodel.ProductsViewModelListener;
import br.com.hlandim.supermarket.util.Util;

/**
 * Created by hlandim on 14/01/17.
 */
public class ProductsFragment extends HomeBaseFragment implements ProductsViewModelListener, ProductDetailsViewModelListener {

    private FragmentProductsBinding mBinding;
    private ProductsViewModel mViewModel;
    private ProductsAdapter mProductsAdapter;
    private AlertDialog mFilterDialog;
    private List<Product> mProducts;
    private List<CartItem> mCartItems;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_products, container, false);

        configureRecycleView();

        mViewModel = new ProductsViewModel(getActivity(), this);

        if (mProducts == null) {
            fetchProductsByTytpe(null);
        } else {
            mProductsAdapter = null;
            mBinding.setProductsViewModel(mViewModel);
            updateProducts(mProducts);
        }

        if (mCartItems != null) {
            mBinding.fabCart.setCount(mCartItems.size());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, ProductFilter.getFilters(getContext()));
        mFilterDialog = new AlertDialog.Builder(getActivity())
                .setTitle("Selecione o tipo")
                .setSingleChoiceItems(adapter, 0, (dialogInterface, i) -> {
                            ProductFilter productFilter = ProductFilter.getFilter(i);
                            if (productFilter != null) {
                                String filter = productFilter.name().toLowerCase();
                                fetchProductsByTytpe(filter);
                            }
                            dialogInterface.dismiss();
                        }
                )
                .create();


        setRetainInstance(true);
        setHasOptionsMenu(true);
        ((HomeActivity) getActivity()).setHideMenu(false);
        return mBinding.getRoot();
    }

    private void fetchProductsByTytpe(String filter) {
        showProgress(true, getString(R.string.loading_products_list));
        if (TextUtils.isEmpty(filter) || filter.equals(ProductFilter.ALL.name().toLowerCase())) {
            mViewModel.fetchProductsByType();
        } else {
            mViewModel.fetchProductsByType(filter);
        }
    }

    public void fetchProductsByTitle(String title) {
        showProgress(true, getString(R.string.loading_products_list));
        if (TextUtils.isEmpty(title)) {
            mViewModel.fetchProductsByType();
        } else {
            mViewModel.fetchProductsByTitle(title);
        }
    }

    private void configureRecycleView() {
        mBinding.rvProducts.setHasFixedSize(true);

        int columnCount = 2;
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            columnCount = 4;
        }
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(columnCount, StaggeredGridLayoutManager.VERTICAL);
        mBinding.rvProducts.setLayoutManager(staggeredGridLayoutManager);
        SpacesItemDecoration decoration = new SpacesItemDecoration(5);
        mBinding.rvProducts.addItemDecoration(decoration);
    }


/*
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.home, menu);

        // Associate searchable configuration with the SearchView
        SearchManager searchManager =
                (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getActivity().getComponentName()));

    }*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_filter:
                mFilterDialog.show();
                break;
            case R.id.action_logout:
                mViewModel.logout();
                break;

        }

        return false;
    }

    @Override
    public void onGotProducts(List<Product> list) {
        mProducts = list;
        mBinding.setProductsViewModel(mViewModel);
        updateProducts(list);
        mViewModel.fetchCartItens();
//        showProgress(false);
    }

    private void updateProducts(List<Product> list) {
        if (list != null) {
            if (mProductsAdapter == null) {
                mProductsAdapter = new ProductsAdapter(list);
                mProductsAdapter.setListListener(mViewModel);
                mBinding.rvProducts.setAdapter(mProductsAdapter);
            } else {
                mProductsAdapter.setProductList(list);
                mProductsAdapter.notifyDataSetChanged();
            }
        }
    }


    @Override
    public void addToCartFromDetails(Product product) {
        addedToCart(product);
        showProgress(false);
    }

    @Override

    public void onGotError(List<Error> errors) {
        for (Error error : errors) {
            Util.getErrorSnackbar(mBinding.fabCart, error.getMessage()).show();
        }
    }

    @Override
    public void onProductClicked(Product product, ImageView imageView) {

        ((HomeActivity) getActivity()).goToDetailsFragment(this, product, imageView);
    }

    @Override
    public void addedToCart(Product product) {
        mBinding.fabCart.increase();
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentByTag("tag_details");
        if (fragment != null) {
            fragmentManager
                    .beginTransaction().remove(fragment).commit();
        }

    }

    @Override
    public void updateCart(List<CartItem> items) {
        mBinding.fabCart.setCount(items.size());
    }

    @Override
    public void goToCheckout() {
        ((HomeActivity) getActivity()).goToCheckout(this, mCartItems);
    }


    private enum ProductFilter {

        ALL(R.string.filter_all),
        BAKERY(R.string.filter_bakery),
        DAIRY(R.string.filter_dairy),
        FRUIT(R.string.filter_fruit),
        VEGETABLE(R.string.filter_vegetable),
        MEAT(R.string.filter_meat);

        private int id;

        ProductFilter(int id) {
            this.id = id;
        }

        private static List<String> getFilters(Context context) {
            List<String> strings = new ArrayList<>();
            for (ProductFilter filter : ProductFilter.values()) {
                strings.add(context.getString(filter.id));
            }
            return strings;
        }

        private static ProductFilter getFilter(int position) {
            for (ProductFilter filter : ProductFilter.values()) {
                if (filter.ordinal() == position) {
                    return filter;
                }
            }
            return null;
        }
    }

    public class SpacesItemDecoration extends RecyclerView.ItemDecoration {
        private final int mSpace;

        SpacesItemDecoration(int space) {
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
