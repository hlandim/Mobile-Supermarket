package br.com.hlandim.supermarket.data.service.response;

import java.util.List;

/**
 * Created by hlandim on 11/01/17.
 */

public class CreateResponse {

    private String id;
    private String createdAt;
    private String password;
    private String email;
    private List<Error> errors;


    public CreateResponse(String id, String createdAt, String password, String email, List<Error> errors) {
        this.id = id;
        this.createdAt = createdAt;
        this.password = password;
        this.email = email;
        this.errors = errors;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Error> getErrors() {
        return errors;
    }

    public void setErrors(List<Error> errors) {
        this.errors = errors;
    }

    public boolean isSuccess() {
        return errors == null || errors.isEmpty();
    }
}
