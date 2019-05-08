package com.mark.greendaodemo;

import android.app.Application;

import com.mark.greendaodemo.db.DaoMaster;

public class App extends Application {



    public static DaoMaster daoMaster;
    public static String dbDir = "db";
    public static String dbName = "user.db";

    @Override
    public void onCreate() {
        super.onCreate();
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this,dbName);
        daoMaster = new DaoMaster(helper.getWritableDb());

    }
}
