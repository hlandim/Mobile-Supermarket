package br.com.hlandim.supermarket.util;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by hlandim on 26/01/17.
 */

public class HttpInterceptor implements Interceptor {
    private static final String TAG = HttpInterceptor.class.getSimpleName();
    public static final String NO_CONNECTION_INTENT_ACTION = NoConnectivityException.class.getCanonicalName() + "Receiver";
    private ConnectivityManager mCmc;
    private Context mContext;

    public HttpInterceptor(Context context) {
        this.mContext = context;
        mCmc = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
    }

    @Override
    public Response intercept(Interceptor.Chain chain)
            throws IOException {
        Request request = chain.request();
        /*request = request.newBuilder()
                .addHeader("appid", "hello")
                .addHeader("deviceplatform", "android")
                .removeHeader("User-Agent")
                .addHeader("User-Agent", "Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:38.0) Gecko/20100101 Firefox/38.0")
                .build();*/

        if (!checkConnection()) {
            Log.d(TAG, "Without connection");
            Intent intent = new Intent();
            intent.setAction(NO_CONNECTION_INTENT_ACTION);
            mContext.sendBroadcast(intent);
            throw new NoConnectivityException();
        }

        Response response = chain.proceed(request);
        return response;
    }

    private boolean checkConnection() {
        NetworkInfo activeNetworkInfo = mCmc.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }

    public class NoConnectivityException extends IOException {

        @Override
        public String getMessage() {
            return "No network available, please check your WiFi or Data connection";
        }
    }
}