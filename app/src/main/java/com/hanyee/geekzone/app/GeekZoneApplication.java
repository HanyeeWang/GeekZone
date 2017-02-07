package com.hanyee.geekzone.app;

import android.app.Application;

import com.github.moduth.blockcanary.BlockCanary;
import com.hanyee.geekzone.di.component.AppComponent;
import com.hanyee.geekzone.di.component.DaggerAppComponent;
import com.hanyee.geekzone.di.module.AppModule;
import com.hanyee.geekzone.widget.ApplicationBlockCanaryContext;
import com.orhanobut.logger.Logger;
import com.squareup.leakcanary.LeakCanary;
import com.tencent.smtt.sdk.QbSdk;
import com.tencent.smtt.sdk.TbsListener;

/**
 * Created by Hanyee on 2016/10/14.
 */
public class GeekZoneApplication extends Application {

    private static GeekZoneApplication sInstance;
    private AppComponent appComponent;

    public static GeekZoneApplication getInstance() {
        return sInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;

        //Initialize the log
        Logger.init(getPackageName()).hideThreadInfo();

        //Initialize memery leak checking
        LeakCanary.install(this);

        //Initialize ui block checking
        BlockCanary.install(this, new ApplicationBlockCanaryContext()).start();

        //Global dependencies graph is created here
        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();

        //Initialize TBS x5 webview
        QbSdk.setTbsListener(new TbsListener() {
            @Override
            public void onDownloadFinish(int i) {
                Logger.d("onDownloadFinish");
            }

            @Override
            public void onInstallFinish(int i) {
                Logger.d("onInstallFinish");
            }

            @Override
            public void onDownloadProgress(int i) {
                Logger.d("onDownloadProgress:"+i);
            }
        });
        QbSdk.initX5Environment(this, new QbSdk.PreInitCallback() {
            @Override
            public void onCoreInitFinished() {
            }

            @Override
            public void onViewInitFinished(boolean b) {
                Logger.d("onViewInitFinished is:"+b);
            }
        });
    }

    //Just a helper to provide appComponent to local components which depend on it
    public AppComponent getAppComponent() {
        return appComponent;
    }
}
