package br.com.hlandim.supermarket.util;

/**
 * Created by hlandim on 16/01/17.
 */

public class JwtToken {
    private String sub;

    public JwtToken(String sub) {
        this.sub = sub;
    }

    public String getSub() {
        return sub;
    }

    public void setSub(String sub) {
        this.sub = sub;
    }
}
