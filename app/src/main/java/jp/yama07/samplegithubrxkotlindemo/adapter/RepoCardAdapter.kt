package jp.yama07.samplegithubrxkotlindemo.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import jp.yama07.samplegithubrxkotlindemo.R
import jp.yama07.samplegithubrxkotlindemo.model.Github
import kotlinx.android.synthetic.main.repos_recycler_view.view.*

class RepoCardAdapter : RecyclerView.Adapter<RepoCardAdapter.ViewHolder>() {
    private val mItems = mutableListOf<Github.Repo>()

    fun addData(githubRepo: Github.Repo) {
        mItems.add(githubRepo)
        notifyDataSetChanged()
    }

    fun clear() {
        mItems.clear()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent?.context)
                .inflate(R.layout.repos_recycler_view, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int = mItems.size

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        val (name, description, forks_count, stargazers_count, created_at, updated_at) = mItems[position]
        holder?.name?.text = name
        holder?.description?.apply {
            text = description
            if (description == null) {
                visibility = View.GONE
            } else {
                visibility = View.VISIBLE
            }
        }
        holder?.forks_count?.text = "Fork: ${forks_count}"
        holder?.stargazers_count?.text = "Star: ${stargazers_count}"
        holder?.created_at?.text = "Created: ${created_at}"
        holder?.updated_at?.text = "Updated: ${updated_at}"
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var name: TextView = itemView.repos_name
        var description: TextView = itemView.repos_description
        var forks_count: TextView = itemView.repos_forks_count
        var stargazers_count: TextView = itemView.repos_stargazers_count
        var created_at: TextView = itemView.repos_created_at
        var updated_at: TextView = itemView.repos_updated_at
    }
}