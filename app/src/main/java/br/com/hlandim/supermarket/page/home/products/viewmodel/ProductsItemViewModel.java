package br.com.hlandim.supermarket.page.home.products.viewmodel;

import br.com.hlandim.supermarket.data.service.response.Product;

/**
 * Created by hlandim on 14/01/17.
 */

public class ProductsItemViewModel {

    private Product product;

    public ProductsItemViewModel(Product product) {
        this.product = product;
    }

    public String getImageName() {
        return product.getFilename();
    }

    public String getTitle() {
        return product.getTitle();
    }

    public String getPrice() {
        return "$" + product.getPrice();
    }
}
