package com.example.deber_02

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.ViewModelProvider.NewInstanceFactory.Companion.instance
import java.text.SimpleDateFormat

class EBaseDatos {
    companion object{
        var BDatos: ESqliteHelper?=null
        fun getInstance(context: Context): ESqliteHelper {
            if (instance == null) {
                BDatos = ESqliteHelper(context.applicationContext)
            }
            return BDatos!!
        }
    }
}