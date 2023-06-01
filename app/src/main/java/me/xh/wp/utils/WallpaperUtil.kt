package me.xh.wp.utils

import android.app.WallpaperManager
import android.graphics.Bitmap
import android.graphics.RectF
import android.os.Build
import coil.imageLoader
import coil.request.ImageRequest
import coil.request.ImageResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import me.xh.wp.App

enum class SetMode {
    HomeScreen, LockScreen, Both
}

suspend fun loadImage(imageUrl: String): ImageResult {
    return withContext(Dispatchers.IO) {
        val request = ImageRequest.Builder(App.sInstance)
            .data(imageUrl)
            .build()
        App.sInstance.imageLoader.execute(request)
    }
}

suspend fun setAsWallpaper(sourceBitmap: Bitmap, rectF: RectF, setMode: SetMode): Boolean {
    return withContext(Dispatchers.IO) {
        val wallpaperBitmap = handleBitmap(sourceBitmap, rectF)
        val isSuccess = setWallpaper(wallpaperBitmap, setMode)
        isSuccess
    }
}

private suspend fun handleBitmap(sourceBitmap: Bitmap, rectF: RectF): Bitmap {
    return withContext(Dispatchers.IO) {
        val cropBitmap = Bitmap.createBitmap(
            sourceBitmap,
            (sourceBitmap.width * rectF.left).toInt(),
            (sourceBitmap.height * rectF.top).toInt(),
            (sourceBitmap.width * (rectF.right - rectF.left)).toInt(),
            (sourceBitmap.height * (rectF.bottom - rectF.top)).toInt()
        )
        val scaleBitmap = Bitmap.createScaledBitmap(
            cropBitmap,
            screenWidth,
            screenHeight,
            true
        )
        scaleBitmap
    }
}

private suspend fun setWallpaper(bitmap: Bitmap, setMode: SetMode): Boolean {
    return withContext(Dispatchers.IO) {
        try {
            val wpm = WallpaperManager.getInstance(App.sInstance)
            wpm.suggestDesiredDimensions(screenWidth, screenHeight)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                when (setMode) {
                    SetMode.HomeScreen -> {
                        wpm.setBitmap(bitmap, null, true, WallpaperManager.FLAG_SYSTEM)
                    }

                    SetMode.LockScreen -> {
                        wpm.setBitmap(bitmap, null, true, WallpaperManager.FLAG_LOCK)
                    }

                    SetMode.Both -> {
                        // 同时设置无效
//                        wpm.setBitmap(bitmap, null, true, WallpaperManager.FLAG_SYSTEM or WallpaperManager.FLAG_LOCK)
                        wpm.setBitmap(bitmap, null, true, WallpaperManager.FLAG_SYSTEM)
                        wpm.setBitmap(bitmap, null, true, WallpaperManager.FLAG_LOCK)
                    }
                }
            } else {
                wpm.setBitmap(bitmap)
            }
        } catch (e: Exception) {
            return@withContext false
        }
        true
    }
}