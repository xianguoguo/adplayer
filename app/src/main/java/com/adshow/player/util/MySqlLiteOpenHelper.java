package com.adshow.player.util;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.adshow.player.dao.AdvertisingDao;
import com.adshow.player.dao.DaoMaster;
import com.adshow.player.dao.HistoryDao;
import com.adshow.player.dao.ScheduleDao;

import org.greenrobot.greendao.database.Database;

public class MySqlLiteOpenHelper extends DaoMaster.OpenHelper{

    public MySqlLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory)
    {
        super(context, name, factory);
    }

    @Override
    public void onUpgrade(Database db, int oldVersion, int newVersion)
    {
        super.onUpgrade(db, oldVersion, newVersion);
        MyLog.e("MySqlLiteOpenHelper---version:"+oldVersion + "---先前和更新之后的版本---" + newVersion);
        if (oldVersion < newVersion)
        {
            MigrationHelper.getInstance().migrate(db, AdvertisingDao.class, HistoryDao.class, ScheduleDao.class);
            MyLog.e("MySqlLiteOpenHelper-----更新完成");
        }
    }
}
