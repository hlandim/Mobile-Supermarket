package br.com.hlandim.supermarket.page.home.products.viewmodel;

import java.util.List;

import br.com.hlandim.supermarket.data.service.response.Error;
import br.com.hlandim.supermarket.data.service.response.Product;

/**
 * Created by hlandim on 17/01/17.
 */

public interface ProductDetailsViewModelListener {

    void addToCartFromDetails(Product product);

    void onGotError(List<Error> errors);

    void showProgress(boolean show, String text);

    void showProgress(boolean show);
}
