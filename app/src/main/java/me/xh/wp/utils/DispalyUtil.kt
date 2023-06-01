package me.xh.wp.utils

import android.content.Context
import android.graphics.Point
import android.util.TypedValue
import android.view.WindowManager
import me.xh.wp.App

val screenWidth: Int
    get() = screenSize.x

val screenHeight: Int
    get() = screenSize.y

private val screenSize: Point by lazy {
    val wm = App.sInstance.getSystemService(Context.WINDOW_SERVICE) as WindowManager
    val size = Point()
    wm.defaultDisplay.getRealSize(size)
    return@lazy size
}

fun dp2px(dp: Int): Int {
    return dp2px(dp.toFloat()).toInt()
}

fun dp2px(dp: Float): Float {
    val metrics = App.sInstance.resources.displayMetrics
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, metrics)
}