package br.com.hlandim.supermarket.util;

import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import br.com.hlandim.supermarket.R;
import br.com.hlandim.supermarket.config.Endpoint;

/**
 * Created by hlandim on 15/01/17.
 */

public class BindAdapters {


    @BindingAdapter({"bind:imageName"})
    public static void imageName(ImageView view, String imageName) {
        imageName = Endpoint.SERVER_ASSETS_IMAGES + imageName;
        Picasso.with(view.getContext())
                .load(imageName)
                .fit()
                .centerCrop()
                .placeholder(R.drawable.apple)
                .into(view);
    }
}
