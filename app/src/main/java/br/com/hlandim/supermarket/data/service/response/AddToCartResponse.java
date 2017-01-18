package br.com.hlandim.supermarket.data.service.response;

import java.util.List;

/**
 * Created by hlandim on 16/01/17.
 */

public class AddToCartResponse {

    private List<Error> errors;

    public AddToCartResponse(List<Error> errors) {
        this.errors = errors;
    }

    public List<Error> getErrors() {
        return errors;
    }

    public void setErrors(List<Error> errors) {
        this.errors = errors;
    }
}
