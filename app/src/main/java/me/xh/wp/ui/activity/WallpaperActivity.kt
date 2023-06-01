package me.xh.wp.ui.activity

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.drawable.toBitmap
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import coil.request.ErrorResult
import coil.request.SuccessResult
import kotlinx.coroutines.launch
import me.xh.wp.R
import me.xh.wp.databinding.ActivityWallpaperBinding
import me.xh.wp.http.model.Wallpaper
import me.xh.wp.utils.SetMode
import me.xh.wp.utils.loadImage
import me.xh.wp.utils.setAsWallpaper
import me.xh.wp.utils.toast

class WallpaperActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWallpaperBinding
    private lateinit var wallpaper: Wallpaper

    private var bitmap: Bitmap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        hideSystemUI()
        binding = ActivityWallpaperBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initParam()
        initUI()
    }

    private fun initParam() {
        wallpaper = intent.getParcelableExtra(KEY_WALLPAPER)!!
    }

    private fun initUI() {
        binding.btnSetAsWallpaper.setOnClickListener { setAsWallpaper() }
        lifecycleScope.launch {
            val thumbResult = loadImage(wallpaper.thumbs.original)
            if (thumbResult is SuccessResult) {
                binding.tiv.setImageBitmap(thumbResult.drawable.toBitmap())
            }
            val originalResult = loadImage(wallpaper.path)
            if (originalResult is SuccessResult) {
                binding.pb.isVisible = false
                bitmap = originalResult.drawable.toBitmap()
                binding.tiv.setImageBitmap(bitmap)
            } else {
                val errorMsg = (originalResult as ErrorResult).throwable.message
                    ?: getString(R.string.load_error)
                toast(errorMsg)
                finish()
            }
        }
    }

    private fun setAsWallpaper() {
        lifecycleScope.launch {
            bitmap?.let {
                binding.pb.isVisible = true
                val rectF = binding.tiv.zoomedRect
                val isSuccess = setAsWallpaper(bitmap!!, rectF, SetMode.Both)
                if (isSuccess) {
                    toast(R.string.set_as_wallpaper_success)
                } else {
                    toast(R.string.set_as_wallpaper_failure)
                }
                binding.pb.isVisible = false
            } ?: toast(R.string.waiting_for_load_completed)
        }
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) {
            hideSystemUI()
        }
    }

    private fun hideSystemUI() {
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_FULLSCREEN)
    }

    companion object {
        private const val KEY_WALLPAPER = "wallpaper"

        fun actionStart(context: Context, wallpaper: Wallpaper) {
            val intent = Intent(context, WallpaperActivity::class.java).apply {
                putExtra(KEY_WALLPAPER, wallpaper)
            }
            context.startActivity(intent)
        }
    }
}