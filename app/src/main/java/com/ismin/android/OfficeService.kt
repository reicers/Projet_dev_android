package com.ismin.android

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface OfficeService {
    @GET("offices")
    fun findOffice(): Call<List<Office>>
    @POST("offices")
    fun createBook(@Body office : Office): Call<Office>
}