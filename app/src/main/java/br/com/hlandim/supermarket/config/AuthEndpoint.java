package br.com.hlandim.supermarket.config;

/**
 * Created by hlandim on 10/01/17.
 */

public enum AuthEndpoint {
    CREATE_USER("user"),
    LOGIN("oauth/token");

    private String endpoint;

    AuthEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getUtl() {
        return "auth.mobilesupermarket.wedeploy.io/" + endpoint;
    }
}
