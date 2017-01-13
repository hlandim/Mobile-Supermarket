package br.com.hlandim.supermarket.signin.model;

/**
 * Created by hlandim on 10/01/17.
 */

public class SignIn {

    private String email;
    private String password;

    public SignIn() {
    }

    public SignIn(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
