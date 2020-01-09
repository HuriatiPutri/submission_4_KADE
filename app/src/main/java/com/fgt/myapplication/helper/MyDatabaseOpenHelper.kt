package com.fgt.myapplication.helper

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import org.jetbrains.anko.db.*

class MyDatabaseOpenHelper(ctx: Context) :
    ManagedSQLiteOpenHelper(ctx, "FootballApp.db",null,1){

    companion object {
        private  var instance: MyDatabaseOpenHelper? = null

        fun getInstance(ctx: Context): MyDatabaseOpenHelper{
            if (instance == null) {
                instance = MyDatabaseOpenHelper(ctx.applicationContext)
            }
            return instance as MyDatabaseOpenHelper
        }
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.createTable(Favorite.TABLE_FAV_MATCH,true,
            Favorite.ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
            Favorite.ID_EVENT to TEXT + UNIQUE,
            Favorite.DATE_EVENT to TEXT,
            Favorite.HOME_TEAM to TEXT,
            Favorite.AWAY_TEAM to TEXT,
            Favorite.HOME_SCORE to TEXT,
            Favorite.AWAY_SCORE to TEXT,
            Favorite.HOME_GOAL_DETAIL to TEXT,
            Favorite.AWAY_GOAL_DETAIL to TEXT)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.dropTable(Favorite.TABLE_FAV_MATCH,true)
    }

}
// Akses properti database untuk context
val Context.database: MyDatabaseOpenHelper
    get() = MyDatabaseOpenHelper.getInstance(applicationContext)