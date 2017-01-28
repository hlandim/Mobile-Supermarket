package br.com.hlandim.supermarket;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import br.com.hlandim.supermarket.util.HttpInterceptor;

/**
 * Created by hlandim on 26/01/17.
 */

public class BaseActivity extends AppCompatActivity {
    private static final String TAG = BaseActivity.class.getSimpleName();

    private BroadcastReceiver connectionReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            onNoConnectionReceived();
        }
    };

    private BroadcastReceiver mConnectionChangedReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            if (activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting()) {
                onConnectionRestored();
            } else {
                onNoConnectionReceived();
            }
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter intentFilter = new IntentFilter(HttpInterceptor.NO_CONNECTION_INTENT_ACTION);
        this.registerReceiver(connectionReceiver, intentFilter);

        IntentFilter intentFilter1 = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        this.registerReceiver(mConnectionChangedReceiver, intentFilter1);
    }

    @Override
    protected void onPause() {
        try {
            unregisterReceiver(connectionReceiver);
            unregisterReceiver(mConnectionChangedReceiver);
        } catch (Exception e) {
            Log.w(TAG, e.getMessage());
        }
        super.onPause();
    }

    public void onNoConnectionReceived() {

    }

    public void onConnectionRestored() {

    }
}
