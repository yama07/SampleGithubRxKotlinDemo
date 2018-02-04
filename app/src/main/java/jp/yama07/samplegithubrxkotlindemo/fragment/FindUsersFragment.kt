package jp.yama07.samplegithubrxkotlindemo.fragment

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import jp.yama07.samplegithubrxkotlindemo.R
import jp.yama07.samplegithubrxkotlindemo.adapter.UserCardAdapter
import jp.yama07.samplegithubrxkotlindemo.model.Github
import jp.yama07.samplegithubrxkotlindemo.service.GithubService
import jp.yama07.samplegithubrxkotlindemo.service.ServiceFactory
import kotlinx.android.synthetic.main.find_users.*
import timber.log.Timber

public class FindUsersFragment : Fragment() {
    private lateinit var service: GithubService
    private val mUserCardAdapter = UserCardAdapter()

    companion object {
        fun newInstance(): FindUsersFragment {
            return FindUsersFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater?.inflate(R.layout.find_users, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        service = ServiceFactory.createRetrofitService(
                GithubService::class.java, GithubService.SERVICE_ENDPOINT
        )

        mUserCardAdapter.onItemClickListener = object : UserCardAdapter.OnItemClickListener {
            override fun onItemClick(user: Github.User) {
                Timber.d("${user.login}")
            }
        }

        recycler_view.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(activity)
            adapter = mUserCardAdapter
        }

        button_clear.setOnClickListener { mUserCardAdapter.clear() }
        button_fetch.setOnClickListener {
            mUserCardAdapter.clear()
            fetchAndUpdate(find_edit_text.text.toString())
        }

        setDefaultUsers()
    }

    private fun setDefaultUsers() = resources.getStringArray(R.array.default_github_usernames)
            .forEach { fetchAndUpdate(it) }

    private fun fetchAndUpdate(query: String) {
        Timber.d("Query: $query")
        service.getUserRx(query)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .cache()
                .subscribe({ response ->
                    Timber.d("Response: ${response.login}")
                    mUserCardAdapter.addData(response)
                }, { ex ->
                    Timber.e(ex)
                    Snackbar.make(base_layout, "An error occurred: ${ex.message}", Snackbar.LENGTH_LONG)
                            .show()

                })
    }
}