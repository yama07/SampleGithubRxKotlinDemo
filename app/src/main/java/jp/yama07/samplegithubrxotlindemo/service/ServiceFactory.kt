package jp.yama07.samplegithubrxotlindemo.service

import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

/**
 * Created by yamamoto on 2018/01/01.
 */

object ServiceFactory {
    fun <T> createRetrofitService(tClass: Class<T>, endPoint: String): T {
        return Retrofit.Builder()
                .baseUrl(endPoint)
                .addConverterFactory(MoshiConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(tClass)
    }
}
