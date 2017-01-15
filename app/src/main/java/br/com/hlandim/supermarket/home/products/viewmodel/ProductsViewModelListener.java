package br.com.hlandim.supermarket.home.products.viewmodel;

import java.util.List;

import br.com.hlandim.supermarket.data.service.response.Error;
import br.com.hlandim.supermarket.data.service.response.ProductResponse;

/**
 * Created by hlandim on 14/01/17.
 */

public interface ProductsViewModelListener {
    void onGotError(Error error);
}
