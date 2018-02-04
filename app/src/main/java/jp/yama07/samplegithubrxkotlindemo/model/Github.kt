package jp.yama07.samplegithubrxkotlindemo.model

class Github {
    data class User(
            var login: String,
            var blog: String,
            var public_repos: Int
    )

    data class Tree(
            var path: String,
            var mode: String,
            var type: String,
            var size: Int,
            var sha: String,
            var url: String
    )

    data class Trees(
            var sha: String,
            var url: String,
            var tree: List<Github.Tree>
    )

    data class Repo(
            var name: String,
            var description: String,
            var forks_count: String,
            var stargazers_count: String,
            var created_at: String,
            var updated_at: String
    )

}