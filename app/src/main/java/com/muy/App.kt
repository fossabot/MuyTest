package com.muy

import android.app.Application
import android.content.Context
import com.muy.di.Injection

class App: Application(){

    init {
        instance = this
    }

    private fun createInjection(): Injection {
        return Injection(this)
    }

    override fun onCreate() {
        super.onCreate()
        injection = createInjection()
    }


    companion object {
        private  var instance: App? = null
        private lateinit var injection: Injection
        fun getInjection(): Injection? {
            return injection
        }

        fun applicationContext() : Context {
            return instance!!.applicationContext
        }
    }

}