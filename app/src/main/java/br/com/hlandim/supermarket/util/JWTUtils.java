package br.com.hlandim.supermarket.util;

import android.util.Base64;

import com.google.gson.Gson;

import java.io.UnsupportedEncodingException;

/**
 * Created by hlandim on 16/01/17.
 */

public class JWTUtils {

    public static JwtToken decoded(String JWTEncoded) throws Exception {
        String[] split = JWTEncoded.split("\\.");
        return new Gson().fromJson(getJson(split[1]), JwtToken.class);
    }

    private static String getJson(String strEncoded) throws UnsupportedEncodingException {
        byte[] decodedBytes = Base64.decode(strEncoded, Base64.URL_SAFE);
        return new String(decodedBytes, "UTF-8");
    }
}
