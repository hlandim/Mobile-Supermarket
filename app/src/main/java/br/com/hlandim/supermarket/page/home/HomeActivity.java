package br.com.hlandim.supermarket.page.home;

import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.transition.Fade;
import android.view.Menu;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        changeFragment(new ProductsFragment(), null, R.id.container_home_fragment);

        mLoadingAnimation = new LoadingAnimation(findViewById(R.id.container_loading));
        mLoadingText = (TextView) findViewById(R.id.tv_loading);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
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
