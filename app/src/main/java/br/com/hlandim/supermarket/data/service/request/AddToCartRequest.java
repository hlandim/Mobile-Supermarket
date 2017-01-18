package br.com.hlandim.supermarket.data.service.request;

/**
 * Created by hlandim on 16/01/17.
 */

public class AddToCartRequest {

    private String productTitle;
    private String productFilename;
    private String productId;
    private String userId;
    private double productPrice;

    public AddToCartRequest(String productTitle, String productFilename, String productId, String userId, double productPrice) {
        this.productTitle = productTitle;
        this.productFilename = productFilename;
        this.productId = productId;
        this.userId = userId;
        this.productPrice = productPrice;
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
}
