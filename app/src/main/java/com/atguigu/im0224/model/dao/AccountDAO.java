package com.atguigu.im0224.model.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import com.atguigu.im0224.model.bean.UserInfo;
import com.atguigu.im0224.model.db.AccountDB;
import com.atguigu.im0224.model.table.AccountTable;

/**
 * Created by Administrator on 2017/7/3.
 */

public class AccountDAO {

    private final AccountDB accountDB;

    public AccountDAO (Context context){
        accountDB = new AccountDB(context);
    }

     /*
    * 添加用户
    * */
    public void addAccount (UserInfo userInfo){
        if(userInfo == null) {
            throw new NullPointerException("对象不能为空");
        }

        SQLiteDatabase database = accountDB.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put(AccountTable.COL_HXID,userInfo.getHxid());
        contentValues.put(AccountTable.COL_NICK,userInfo.getNick());
        contentValues.put(AccountTable.COL_PHOTO,userInfo.getPhoto());
        contentValues.put(AccountTable.COL_USERNAME,userInfo.getUsername());

        database.replace(AccountTable.TABLE_NAME,null,contentValues);
    }

    /*
   * 根据hxid 获取对应的用户
   * */
    public UserInfo getUserInfo(String hxid) {

        //校验
        if (TextUtils.isEmpty(hxid)) {
            return null;
        }

        SQLiteDatabase database = accountDB.getWritableDatabase();

        String sql = "select * from " + AccountTable.TABLE_NAME
                + " where " + AccountTable.COL_HXID + "=?";

        Cursor cursor = database.rawQuery(sql, new String[]{hxid});

        UserInfo userInfo = new UserInfo();

        if(cursor.moveToNext()) {
            userInfo.setHxid(cursor.getString(cursor.getColumnIndex(AccountTable.COL_HXID)));
            userInfo.setNick(cursor.getString(cursor.getColumnIndex(AccountTable.COL_NICK)));
            userInfo.setPhoto(cursor.getString(cursor.getColumnIndex(AccountTable.COL_PHOTO)));
            userInfo.setUsername(cursor.getString(cursor.getColumnIndex(AccountTable.COL_USERNAME)));
        }

        cursor.close();

        return userInfo;

    }
}
