package br.com.hlandim.supermarket.home.products.viewmodel;

import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import br.com.hlandim.supermarket.R;
import br.com.hlandim.supermarket.config.Endpoint;
import br.com.hlandim.supermarket.data.service.response.ProductResponse;

/**
 * Created by hlandim on 14/01/17.
 */

public class ProductsItemViewModel {

    private ProductResponse productResponse;

    public ProductsItemViewModel(ProductResponse productResponse) {
        this.productResponse = productResponse;
    }

    @BindingAdapter({"bind:imageName"})
    public static void imageName(ImageView view, String imageName) {
        imageName = Endpoint.SERVER_ASSETS_IMAGES + imageName;
        Picasso.with(view.getContext())
                .load(imageName)
                .placeholder(R.drawable.apple)
                .into(view);
    }

    public String getImageName() {
        return productResponse.getFilename();
    }

    public String getTitle() {
        return productResponse.getTitle();
    }

    public String getPrice() {
        return "$" + productResponse.getPrice();
    }
}
