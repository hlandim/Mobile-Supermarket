package br.com.hlandim.supermarket;

import android.app.Application;

import br.com.hlandim.supermarket.manager.SessionManager;

/**
 * Created by hlandim on 12/01/17.
 */

public class SuperMarketApplication extends Application {

    private SessionManager mSessionManager;

    @Override
    public void onCreate() {
        super.onCreate();
        mSessionManager = new SessionManager(this);
    }

    public SessionManager getSessionManager() {
        return mSessionManager;
    }
}
