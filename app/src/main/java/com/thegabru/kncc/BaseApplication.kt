package com.thegabru.kncc

import android.app.Application
import com.thegabru.kncc.repository.SentModelsRepository

class BaseApplication : Application() {

    private var dbHelper: DBHelper? = null
    private var sentModelsRepository: SentModelsRepository? = null
    var appManager: AppManager? = null
        private set

    override fun onCreate() {
        super.onCreate()

        dbHelper = DBHelper(applicationContext)
        sentModelsRepository = SentModelsRepository(dbHelper!!)
        appManager = AppManager( sentModelsRepository!!)

    }
}
