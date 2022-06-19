package com.example.near

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(mContext: Context, name : String?, factory: SQLiteDatabase.CursorFactory?, version:Int)
    : SQLiteOpenHelper(mContext, name, factory, version){
    override fun onCreate(db: SQLiteDatabase) {
        var sql = "CREATE TABLE if not exists mytable(" +
                "id integer primary key autoincrement," +
                "search text);"
        db.execSQL(sql)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        val sql : String = "DROP TABLE if exists mytable"

        db.execSQL(sql)
        onCreate(db)
    }
}