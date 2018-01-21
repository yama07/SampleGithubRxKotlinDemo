package jp.yama07.samplegithubrxkotlindemo.service

import io.reactivex.Observable
import jp.yama07.samplegithubrxkotlindemo.model.Github
import retrofit2.http.GET
import retrofit2.http.Path

interface GithubService {

    @GET("/users/{login}")
    fun getUserRx(@Path("login") login: String): Observable<Github>

    @GET("/users/{login}")
    fun getUser(@Path("login") login: String): Github

    companion object {
        val SERVICE_ENDPOINT: String = "https://api.github.com"
    }
}
