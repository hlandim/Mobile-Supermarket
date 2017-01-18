package br.com.hlandim.supermarket.page.main.signin.model;

/**
 * Created by hlandim on 10/01/17.
 */

public class SignIn {

    private static final String DEFAULT_GRANT_TYPE = "password";

    private String email;
    private String password;

    private String grantType;

    public SignIn() {
        this.grantType = DEFAULT_GRANT_TYPE;
    }

    public SignIn(String email, String password) {
        this.email = email;
        this.password = password;
        this.grantType = DEFAULT_GRANT_TYPE;
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

    public String getGrantType() {
        return grantType;
    }

    public void setGrantType(String grantType) {
        this.grantType = grantType;
    }
}
