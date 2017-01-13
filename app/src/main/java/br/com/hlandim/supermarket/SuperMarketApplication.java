package br.com.hlandim.supermarket;

import android.app.Application;
import android.content.Context;

import rx.Scheduler;
import rx.schedulers.Schedulers;

/**
 * Created by hlandim on 12/01/17.
 */

public class SuperMarketApplication extends Application {

    private Scheduler mScheduler;


    private static SuperMarketApplication get(Context context) {
        return (SuperMarketApplication) context.getApplicationContext();
    }

    public static SuperMarketApplication create(Context context) {
        return SuperMarketApplication.get(context);
    }

    public Scheduler getmScheduler() {
        if (mScheduler == null) {
            mScheduler = Schedulers.io();
        }
        return mScheduler;
    }
}
