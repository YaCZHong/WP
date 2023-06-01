package me.xh.wp.utils

import android.widget.Toast
import androidx.annotation.StringRes
import me.xh.wp.App

fun toast(msg: String) {
    Toast.makeText(App.sInstance.applicationContext, msg, Toast.LENGTH_SHORT).show()
}

fun toast(@StringRes resId: Int) {
    Toast.makeText(App.sInstance.applicationContext, resId, Toast.LENGTH_SHORT).show()
}