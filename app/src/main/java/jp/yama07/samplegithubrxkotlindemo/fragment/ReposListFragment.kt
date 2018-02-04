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
import jp.yama07.samplegithubrxkotlindemo.adapter.RepoCardAdapter
import jp.yama07.samplegithubrxkotlindemo.service.GithubService
import jp.yama07.samplegithubrxkotlindemo.service.ServiceFactory
import kotlinx.android.synthetic.main.list_repos.*
import timber.log.Timber

public class ReposListFragment : Fragment() {
    private lateinit var service: GithubService
    private val mRepoCardAdapter = RepoCardAdapter()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater?.inflate(R.layout.list_repos, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        service = ServiceFactory.createRetrofitService(
                GithubService::class.java, GithubService.SERVICE_ENDPOINT
        )

        repos_recycler_view.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(activity)
            adapter = mRepoCardAdapter
        }

        service.getUserReposRx("yama07")
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ response ->
                    Timber.d(response.toString())
                    response.forEach { mRepoCardAdapter.addData(it) }
                }, { ex ->
                    Timber.e(ex)
                    Snackbar.make(list_repos_base_layout, "An error occurred: ${ex.message}", Snackbar.LENGTH_LONG)
                            .show()

                })
    }
}
