package me.xh.wp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import me.xh.wp.databinding.ItemWallpaperBinding
import me.xh.wp.http.model.Wallpaper
import me.xh.wp.ui.activity.WallpaperActivity

class WallpaperAdapter : PagingDataAdapter<Wallpaper, WallpaperAdapter.ViewHolder>(COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemWallpaperBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    class ViewHolder(private val binding: ItemWallpaperBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private lateinit var current: Wallpaper

        init {
            itemView.setOnClickListener {
                WallpaperActivity.actionStart(itemView.context, current)
            }
        }

        fun bind(wallpaper: Wallpaper) {
            this.current = wallpaper
            binding.apply {
                val size = wallpaper.getCompatWH()

                val lp = ivWallpaper.layoutParams
                lp.width = size.x
                lp.height = size.y
                ivWallpaper.layoutParams = lp

                ivWallpaper.load(wallpaper.thumbs.original) {
                    crossfade(true)
                }
            }
        }
    }

    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<Wallpaper>() {
            override fun areItemsTheSame(oldItem: Wallpaper, newItem: Wallpaper): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Wallpaper, newItem: Wallpaper): Boolean {
                return oldItem == newItem
            }
        }
    }
}