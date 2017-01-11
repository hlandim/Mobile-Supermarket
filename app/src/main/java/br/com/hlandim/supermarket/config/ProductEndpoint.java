package br.com.hlandim.supermarket.config;

/**
 * Created by hlandim on 10/01/17.
 */

public enum ProductEndpoint {
    LIST_PRODUCTS("products"),
    LOGIN("oauth/token");

    private String endpoint;

    ProductEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getUtl() {
        return "data.mobilesupermarket.wedeploy.io/" + endpoint;
    }
}
