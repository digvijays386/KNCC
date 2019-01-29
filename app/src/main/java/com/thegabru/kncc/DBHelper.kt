package com.thegabru.kncc

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import org.jetbrains.anko.db.*

class DBHelper(context : Context) : ManagedSQLiteOpenHelper(context,"AppDatabase.db", null,1){
    override fun onCreate(db: SQLiteDatabase?) {

        db?.createTable("SentModels", true,
            "id" to INTEGER + PRIMARY_KEY,
            "fname" to TEXT,
            "lname" to TEXT,
            "contact_no" to TEXT,
            "time" to TEXT,
            "otp" to TEXT
        )

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        //To change body of created functions use File | Settings | File Templates.
    }
}