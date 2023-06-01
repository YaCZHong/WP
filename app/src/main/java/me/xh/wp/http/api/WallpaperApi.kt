package me.xh.wp.http.api

import me.xh.wp.http.model.WallpaperList
import retrofit2.http.GET
import retrofit2.http.Query

interface WallpaperApi {

    @GET("search?purity=100")
    suspend fun defaultList(
        @Query("page") page: Int
    ): WallpaperList

    @GET("search?purity=100")
    suspend fun search(
        @Query("page") page: Int,
        @Query("q") q: String
    ): WallpaperList
}