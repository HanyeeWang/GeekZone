package com.hanyee.geekzone.di.component;

import com.hanyee.geekzone.app.GeekZoneApplication;
import com.hanyee.geekzone.di.module.AppModule;
import com.hanyee.geekzone.model.source.local.LocalDataSourceManager;
import com.hanyee.geekzone.model.source.remote.RemoteDataSourceManager;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Hanyee on 16/10/10.
 */
@Singleton
@Component(modules = AppModule.class)
public interface AppComponent {

    GeekZoneApplication getApplication();

    LocalDataSourceManager getLocalDataSourceManager();

    RemoteDataSourceManager getRemoteDataSourceManager();
}
