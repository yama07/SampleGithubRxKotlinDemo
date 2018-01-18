package jp.yama07.samplegithubrxotlindemo.service

import io.reactivex.Observable
import jp.yama07.samplegithubrxotlindemo.model.Github
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Created by yamamoto on 2018/01/01.
 */

interface GithubService{

    @GET("/users/{login}")
    fun getUserRx(@Path("login") login: String): Observable<Github>

    @GET("/users/{login}")
    fun getUser(@Path("login") login: String): Github

    companion object {
        val SERVICE_ENDPOINT: String = "https://api.github.com"
    }
}
