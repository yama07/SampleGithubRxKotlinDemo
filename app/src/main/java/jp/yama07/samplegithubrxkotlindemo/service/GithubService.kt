package jp.yama07.samplegithubrxkotlindemo.service

import io.reactivex.Observable
import jp.yama07.samplegithubrxkotlindemo.model.Github
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface GithubService {

    @GET("/users/{username}")
    fun getUserRx(
            @Path("username") username: String,
            @Header("Authorization") credentials: String = ""
    ): Observable<Github.User>

    @GET("/users/{login}")
    fun getUser(
            @Path("login") login: String,
            @Header("Authorization") credentials: String = ""
    ): Github.User

    @GET("/repos/{owner}/{repo}/git/trees/{sha}")
    fun getTreeRx(
            @Path("owner") owner: String,
            @Path("repo") repo: String,
            @Path("sha") sha: String,
            @Header("Authorization") credentials: String = ""
    ): Observable<Github.Trees>

    @GET("/users/{owner}/repos")
    fun getUserReposRx(
            @Path("owner") owner: String,
            @Header("Authorization") credentials: String = ""
    ): Observable<List<Github.Repo>>

    @GET("/users/{owner}/{repo}")
    fun getReposRx(
            @Path("owner") login: String,
            @Path("repo") repo: String,
            @Header("Authorization") credentials: String = ""
    ): Observable<Github.Repo>

    companion object {
        val SERVICE_ENDPOINT: String = "https://api.github.com"
    }
}
