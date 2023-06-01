package me.xh.wp.http.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class WallpaperList(
    val data: List<Wallpaper>
) : Parcelable
