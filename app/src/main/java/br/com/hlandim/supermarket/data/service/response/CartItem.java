package br.com.hlandim.supermarket.data.service.response;

import java.util.List;

/**
 * Created by hlandim on 17/01/17.
 */

public class CartItem {

    private String productTitle;
    private String productFilename;
    private String id;
    private String productId;
    private String userId;
    private double productPrice;
    private List<Error> errors;

    public CartItem(Product product, String userId) {
        this.productTitle = product.getTitle();
        this.productFilename = product.getFilename();
        this.productId = product.getId();
        this.userId = userId;
        this.productPrice = product.getPrice();
    }

    public CartItem(String productTitle, String productFilename, String id, String productId, String userId, double productPrice, List<Error> errors) {
        this.productTitle = productTitle;
        this.productFilename = productFilename;
        this.id = id;
        this.productId = productId;
        this.userId = userId;
        this.productPrice = productPrice;
        this.errors = errors;
    }

    public List<Error> getErrors() {
        return errors;
    }

    public void setErrors(List<Error> errors) {
        this.errors = errors;
    }

    public String getProductTitle() {
        return productTitle;
    }

    public void setProductTitle(String productTitle) {
        this.productTitle = productTitle;
    }

    public String getProductFilename() {
        return productFilename;
    }

    public void setProductFilename(String productFilename) {
        this.productFilename = productFilename;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductPriceString() {
        return "$" + productPrice;
    }
}
