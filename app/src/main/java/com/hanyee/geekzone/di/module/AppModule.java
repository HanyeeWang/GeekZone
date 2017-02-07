package com.hanyee.geekzone.di.module;

import com.hanyee.geekzone.app.GeekZoneApplication;
import com.hanyee.geekzone.model.source.local.LocalDataSourceManager;
import com.hanyee.geekzone.model.source.local.db.RealmDBHelper;
import com.hanyee.geekzone.model.source.local.xml.XmlCacheHelper;
import com.hanyee.geekzone.model.source.remote.RemoteDataSourceManager;
import com.hanyee.geekzone.model.source.remote.http.RetrofitHelper;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Hanyee on 16/10/10.
 */
@Module
public class AppModule {

    private final GeekZoneApplication application;

    public AppModule(GeekZoneApplication application) {
        this.application = application;
    }

    @Singleton
    @Provides
    public GeekZoneApplication provideApplication() {
        return application;
    }

    @Singleton
    @Provides
    public LocalDataSourceManager providerLocalDataSourceManager(
            XmlCacheHelper xmlCacheHelper, RealmDBHelper realmDBHelper) {
        return new LocalDataSourceManager(xmlCacheHelper, realmDBHelper);
    }

    @Singleton
    @Provides
    public RemoteDataSourceManager providerRemoteDataSourceManager(
            RetrofitHelper retrofitHelper) {
        return new RemoteDataSourceManager(retrofitHelper, retrofitHelper, retrofitHelper);
    }
}
