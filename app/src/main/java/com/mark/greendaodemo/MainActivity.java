package com.mark.greendaodemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.mark.greendaodemo.db.User;
import com.mark.greendaodemo.db.utils.DbUtils;
import com.mark.greendaodemo.utils.PermissionHelper;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    static final int STORAGE_PERMISSION_REQUEST_CODE = 101;
    int count = 0;


    TextView tvDetail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvDetail = findViewById(R.id.tv_detail);

        if (!PermissionHelper.checkStoragePermission(this)){
            PermissionHelper.requestStoragePermission(this,STORAGE_PERMISSION_REQUEST_CODE);
        }

    }


    public void onMainClick(View view){
        switch (view.getId()){
            case R.id.bt_add:
                User user = new User();
                user.setName("test " + count);
                count++;
                App.daoMaster.newSession().getUserDao().insert(user);
                break;

            case R.id.bt_backup:
                DbUtils.exportDB(this,App.dbDir,App.dbName);
                break;

            case R.id.bt_restore:
                DbUtils.importDB(this,App.dbDir,App.dbName);
                break;

            case R.id.bt_show:
                tvDetail.setText(loadData());
                break;

        }
    }



    private String loadData() {


        StringBuilder builder = new StringBuilder();
        List<User> users = App.daoMaster.newSession().getUserDao().loadAll();

        for (User user : users) {
            builder.append("id : ").append(user.getId()).append(" name : ").append(user.getName()).append("\n");
        }
        return builder.toString();
    }

}
