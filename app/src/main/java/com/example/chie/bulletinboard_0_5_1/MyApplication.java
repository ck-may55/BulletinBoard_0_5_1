package com.example.chie.bulletinboard_0_5_1;

import android.app.Application;
import android.util.Log;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by chie on 2017/03/29.
 */

public class MyApplication extends Application {

    @Override
    public void onCreate(){
        super.onCreate();
        //Realm.init(this);
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder(this).build();

        Realm.setDefaultConfiguration(realmConfiguration);
        Log.d("myapp","debugsetdefault");
    }
}
