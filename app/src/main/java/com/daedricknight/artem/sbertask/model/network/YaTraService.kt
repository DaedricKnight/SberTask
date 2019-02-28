package com.daedricknight.artem.sbertask.model.network

import com.daedricknight.artem.sbertask.model.entities.Translate
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface YaTraService {
    @GET("translate")
    fun getTranslate(@Query("key") key: String,
                 @Query("text") text: String,
                 @Query("lang") lang: String): Deferred<Response<Translate>>
}