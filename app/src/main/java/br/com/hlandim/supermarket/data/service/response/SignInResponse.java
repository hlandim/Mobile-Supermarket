package br.com.hlandim.supermarket.data.service.response;

import android.text.TextUtils;

/**
 * Created by hlandim on 14/01/17.
 */

public class SignInResponse {

    private String access_token;
    private String error_description;
    private String error;

    public SignInResponse(String access_token) {
        this.access_token = access_token;
    }

    public SignInResponse(String error_description, String error) {
        this.error_description = error_description;
        this.error = error;
    }

    public String getErrorDescription() {
        return error_description;
    }

    public void setErrorDescription(String error_description) {
        this.error_description = error_description;
    }

    public String getError() {
        if (!TextUtils.isEmpty(error_description) && !TextUtils.isEmpty(error)) {
            return error_description + "\n" + error;
        }
        return null;
    }


    public void setError(String error) {
        this.error = error;
    }

    public String getAccessToken() {
        return access_token;
    }

    public void setAccessToken(String access_token) {
        this.access_token = access_token;
    }
}
