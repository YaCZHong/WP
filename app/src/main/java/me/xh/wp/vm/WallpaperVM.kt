package me.xh.wp.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import kotlinx.coroutines.flow.Flow
import me.xh.wp.http.model.Wallpaper
import me.xh.wp.http.repo.WallpaperRepo

class WallpaperVM : ViewModel() {

    private val repo = WallpaperRepo()

    fun loadWallpaperList(): Flow<PagingData<Wallpaper>> {
        return repo.loadWallpaperList().cachedIn(viewModelScope)
    }

    fun search(searchKey: String): Flow<PagingData<Wallpaper>> {
        return repo.search(searchKey).cachedIn(viewModelScope)
    }
}