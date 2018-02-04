package jp.yama07.samplegithubrxkotlindemo.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import jp.yama07.samplegithubrxkotlindemo.R
import jp.yama07.samplegithubrxkotlindemo.model.Github

import kotlinx.android.synthetic.main.user_recycler_view.view.*

class UserCardAdapter : RecyclerView.Adapter<UserCardAdapter.ViewHolder>() {
    private val mItems = mutableListOf<Github.User>()
    public var onItemClickListener: OnItemClickListener? = null

    fun addData(githubUser: Github.User) {
        mItems.add(githubUser)
        notifyDataSetChanged()
    }

    fun clear() {
        mItems.clear()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {
        val v = LayoutInflater.from(viewGroup.context)
                .inflate(R.layout.user_recycler_view, viewGroup, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, i: Int) {
        val (login, blog, publicRepos) = mItems[i]
        viewHolder.login.text = login
        viewHolder.repos.text = "repos: $publicRepos"
        viewHolder.blog.text = "blog: $blog"

        viewHolder.itemView.setOnClickListener {
            val position = viewHolder.adapterPosition
            onItemClickListener?.onItemClick(mItems[position])
        }
    }

    override fun getItemCount(): Int = mItems.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var login: TextView = itemView.login
        var repos: TextView = itemView.repos
        var blog: TextView = itemView.blog
    }

    interface OnItemClickListener {
        public fun onItemClick(user: Github.User)
    }
}