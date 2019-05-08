package com.mark.greendaodemo.db.utils;

import android.content.Context;
import android.os.Environment;
import android.util.Log;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;

public class DbUtils {

    static final String TAG = "DbUtils";


    /***
     * 数据库 导入
     * @param context
     * @param dbDir
     * @param dbName
     */
    public static void importDB(Context context, String dbDir, String dbName) {
        try {
            File sd = new File(Environment.getExternalStorageDirectory(),dbDir);
            if (!sd.exists()) {
                Log.e(TAG,"importDB  -- 1  " + sd.getPath());
                sd.mkdirs();
            }
            File data = Environment.getDataDirectory();
            if (sd.canWrite()) {
                String currentDBPath = "//data//" + context.getPackageName()
                        + "//databases//" + dbName;
                String backupDBPath = dbName; // From SD directory.
                File backupDB = new File(data, currentDBPath);
                File currentDB = new File(sd, backupDBPath);

                FileChannel src = new FileInputStream(currentDB).getChannel();
                FileChannel dst = new FileOutputStream(backupDB).getChannel();
                dst.transferFrom(src, 0, src.size());
                src.close();
                dst.close();

                Log.e(TAG,"restore "+backupDB.getAbsolutePath()
                        + " current "+currentDB.getAbsolutePath());

                //Toast.makeText(getApplicationContext(), "Import Successful!", Toast.LENGTH_SHORT).show();

            }
        } catch (Exception e) {
            Log.e(TAG, "restore e " + e.getMessage());
            //Toast.makeText(getApplicationContext(), "Import Failed!", Toast.LENGTH_SHORT).show();
        }
    }


    /**
     * 数据库 备份
     * @param context
     * @param dbDir
     * @param dbName
     */
    public static void exportDB(Context context, String dbDir, String dbName) {
        try {
            File sd = new File(Environment.getExternalStorageDirectory(),dbDir);
            if (!sd.exists()) {
                sd.mkdirs();
                Log.e(TAG,"exportDB  -- 1  " + sd.getPath());
            }
            File data = Environment.getDataDirectory();

            if (sd.canWrite()) {
                String currentDBPath = "//data//" + context.getPackageName()
                        + "//databases//" + dbName;
                File currentDB = new File(data, currentDBPath);
                File backupDB = new File(sd, dbName);

                FileChannel src = new FileInputStream(currentDB).getChannel();
                FileChannel dst = new FileOutputStream(backupDB).getChannel();
                dst.transferFrom(src, 0, src.size());
                src.close();
                dst.close();


                Log.e(TAG,"backup "+backupDB.getAbsolutePath()
                        + " current "+currentDB.getAbsolutePath());

                //Toast.makeText(getApplicationContext(), "Backup Successful!", Toast.LENGTH_SHORT).show();

            }
        } catch (Exception e) {
            Log.e(TAG, "backup e " + e.getMessage());
            //Toast.makeText(getApplicationContext(), "Backup Failed!", Toast.LENGTH_SHORT).show();

        }
    }
}
