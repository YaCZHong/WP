package me.xh.wp.http.repo

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import me.xh.wp.http.HttpManager
import me.xh.wp.http.api.WallpaperApi
import me.xh.wp.http.model.Wallpaper
import me.xh.wp.http.repo.paging.WallpaperListPagingSource
import me.xh.wp.http.repo.paging.WallpaperSearchPagingSource

class WallpaperRepo {

    private val wallpaperApiService = HttpManager.wallpaperRetrofit.create(WallpaperApi::class.java)

    fun loadWallpaperList(): Flow<PagingData<Wallpaper>> {
        return Pager(
            config = PagingConfig(PAGE_SIZE),
            pagingSourceFactory = { WallpaperListPagingSource(wallpaperApiService) }
        ).flow
    }

    fun search(searchKey: String): Flow<PagingData<Wallpaper>> {
        return Pager(
            config = PagingConfig(PAGE_SIZE),
            pagingSourceFactory = { WallpaperSearchPagingSource(wallpaperApiService, searchKey) }
        ).flow
    }

    companion object {
        private const val PAGE_SIZE = 20
    }
}