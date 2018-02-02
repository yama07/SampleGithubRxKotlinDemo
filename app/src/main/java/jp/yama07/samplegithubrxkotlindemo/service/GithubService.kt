package jp.yama07.samplegithubrxkotlindemo.service

import io.reactivex.Observable
import jp.yama07.samplegithubrxkotlindemo.model.Github
import retrofit2.http.GET
import retrofit2.http.Path

interface GithubService {

    @GET("/users/{username}")
    fun getUserRx(@Path("username") username: String): Observable<Github.Users>

    @GET("/users/{username}")
    fun getUser(@Path("username") login: String): Github.Users

    @GET("/repos/{owner}/{repo}/git/trees/{sha}")
    fun getTreeRx(@Path("owner") owner: String,
                  @Path("repo") repo: String,
                  @Path("sha") sha: String
    ): Observable<Github.Trees>

    @GET("/users/{owner}/repos")
    fun getUserReposRx(@Path("owner") owner: String): Observable<List<Github.Repo>>

    @GET("/users/{owner}/{repo}")
    fun getReposRx(@Path("owner") login: String, @Path("repo") repo: String): Observable<Github.Repo>

    companion object {
        val SERVICE_ENDPOINT: String = "https://api.github.com"
    }
}
