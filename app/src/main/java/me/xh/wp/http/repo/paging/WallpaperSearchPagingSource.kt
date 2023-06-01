package me.xh.wp.http.repo.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import me.xh.wp.http.api.WallpaperApi
import me.xh.wp.http.model.Wallpaper

class WallpaperSearchPagingSource(
    private val wallpaperApi: WallpaperApi,
    private val searchKey: String
) : PagingSource<Int, Wallpaper>() {

    override fun getRefreshKey(state: PagingState<Int, Wallpaper>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Wallpaper> {
        return try {
            val pageIndex = params.key ?: 1
            val result = wallpaperApi.search(page = pageIndex, q = searchKey)
            val prev = if (pageIndex > 1) pageIndex - 1 else null
            val next = if (result.data.isNotEmpty()) pageIndex + 1 else null
            LoadResult.Page(result.data, prev, next)
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}