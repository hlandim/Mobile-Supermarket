package br.com.hlandim.supermarket.page.home;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.transition.Fade;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import br.com.hlandim.supermarket.R;
import br.com.hlandim.supermarket.data.service.response.CartItem;
import br.com.hlandim.supermarket.data.service.response.Product;
import br.com.hlandim.supermarket.page.checkout.CheckoutFragment;
import br.com.hlandim.supermarket.page.home.products.ProductDetailsFragment;
import br.com.hlandim.supermarket.page.home.products.ProductsFragment;
import br.com.hlandim.supermarket.page.home.products.viewmodel.ProductsViewModelListener;
import br.com.hlandim.supermarket.util.DetailsTransition;
import br.com.hlandim.supermarket.util.LoadingAnimation;
import br.com.hlandim.supermarket.util.PageAnimation;

public class HomeActivity extends AppCompatActivity {

    private static final String TAG_FRAGMENT = "my_fragment_tag";
    private LoadingAnimation mLoadingAnimation;
    private TextView mLoadingText;
    private HomeBaseFragment mSecondaryFragment;
    private ProductsFragment mProductsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        if (savedInstanceState == null) {
            mProductsFragment = new ProductsFragment();
            changeFragment(mProductsFragment, null, R.id.container_home_fragment);
        }

        mLoadingAnimation = new LoadingAnimation(findViewById(R.id.container_loading));
        mLoadingText = (TextView) findViewById(R.id.tv_loading);

        handleIntent(getIntent());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.home, menu);

        // Associate searchable configuration with the SearchView
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));
        // Get the search close button image view
        ImageView closeButton = (ImageView) searchView.findViewById(R.id.search_close_btn);
        if (closeButton != null) {
            closeButton.setOnClickListener(v -> {
                if (mProductsFragment != null) {
                    mProductsFragment.fetchProductsByTitle(null);
                    searchView.setIconified(true);
                }
            });
        }


        return true;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {

        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            if (mProductsFragment != null) {
                mProductsFragment.fetchProductsByTitle(query);
            }
        }
    }

    private void changeFragment(Fragment fragment, PageAnimation pageAnimation, int containerId) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

        //Configure animation
        if (pageAnimation != null) {
            int enter = pageAnimation.getInTransition();
            int exit = pageAnimation.getOutTransition();
            if (enter > 0 && exit > 0) {
                fragmentTransaction.setCustomAnimations(enter, exit);
            }
        }

        fragmentTransaction.replace(containerId, fragment, TAG_FRAGMENT).commit();
    }

    public void goToCheckout(ProductsViewModelListener listener, List<CartItem> cartItems) {
        mSecondaryFragment = CheckoutFragment.newInstance(listener, cartItems);
        changeFragment(mSecondaryFragment, PageAnimation.SLIDE_RIGHT_TO_LEFT, R.id.container_fragment_2);
    }

    public void showLoadingOverlay(boolean show) {
        showLoadingOverlay(show, null);
    }

    public void showLoadingOverlay(boolean show, String text) {
        mLoadingText.setText(text);
        mLoadingAnimation.showLoadingOverlay(show);
    }

    @Override
    public void onBackPressed() {
        if (mSecondaryFragment != null) {
            removeSecondaryFragment();
        } else {
            super.onBackPressed();
        }
    }

    public void goToDetailsFragment(ProductsFragment productsFragment, Product product, ImageView imageView) {

        ProductDetailsFragment productDetailsFragment = ProductDetailsFragment.newInstance(product, productsFragment, ((BitmapDrawable) imageView.getDrawable()).getBitmap());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            productDetailsFragment.setSharedElementEnterTransition(new DetailsTransition());
            productDetailsFragment.setEnterTransition(new Fade());
            productsFragment.setExitTransition(new Fade());
            productDetailsFragment.setSharedElementReturnTransition(new DetailsTransition());
        }

        getSupportFragmentManager()
                .beginTransaction()
                .addSharedElement(imageView, getString(R.string.product_img_transaction))
                .replace(R.id.container_fragment_2, productDetailsFragment, "tag_details")
                .addToBackStack(null)
                .commit();

        mSecondaryFragment = productDetailsFragment;
    }

    private void removeSecondaryFragment() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.remove(mSecondaryFragment).commit();
        showLoadingOverlay(false);
    }
}
