package me.xh.wp.http.model

import android.graphics.Point
import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize
import me.xh.wp.utils.dp2px
import me.xh.wp.utils.screenWidth

@Parcelize
data class Wallpaper(
    @Json(name = "category")
    val category: String,
    @Json(name = "dimension_x")
    val dimensionX: Int,
    @Json(name = "dimension_y")
    val dimensionY: Int,
    @Json(name = "file_size")
    val fileSize: Int,
    @Json(name = "id")
    val id: String,
    @Json(name = "path")
    val path: String,
    @Json(name = "purity")
    val purity: String,
    @Json(name = "ratio")
    val ratio: String,
    @Json(name = "resolution")
    val resolution: String,
    @Json(name = "source")
    val source: String,
    @Json(name = "thumbs")
    val thumbs: Thumbs,
    @Json(name = "url")
    val url: String
) : Parcelable {

    @Parcelize
    data class Thumbs(
        @Json(name = "large")
        val large: String,
        @Json(name = "original")
        val original: String,
        @Json(name = "small")
        val small: String
    ) : Parcelable

//    @IgnoredOnParcel
//    private var compatWH: Point? = null

    fun getCompatWH(): Point {
//        return compatWH ?: calcCompatWH().also { compatWH = it }
        return calcCompatWH()
    }

    private fun calcCompatWH(): Point {
        val width = (screenWidth - paddingHorizontal * 6) / 2
        val height = (width / getRatio()).toInt()
        return Point(width, height)
    }

    private fun getRatio(): Float {
        var fixRatio = 1f
        try {
            fixRatio = ratio.toFloat()
        } catch (e: Exception) {
            /** ignore */
        }
        return fixRatio
    }

    companion object {
        private val paddingHorizontal = dp2px(3)
    }
}



