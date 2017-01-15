package br.com.hlandim.supermarket.home.products.viewmodel;

import br.com.hlandim.supermarket.data.service.response.Error;

/**
 * Created by hlandim on 14/01/17.
 */

public interface ProductsViewModelListener {
    void onGotError(Error error);
}
