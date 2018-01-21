package jp.yama07.samplegithubrxkotlindemo.activity

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import jp.yama07.samplegithubrxkotlindemo.R
import jp.yama07.samplegithubrxkotlindemo.adapter.CardAdapter
import jp.yama07.samplegithubrxkotlindemo.service.GithubService
import jp.yama07.samplegithubrxkotlindemo.service.ServiceFactory
import kotlinx.android.synthetic.main.activity_main.*
import timber.log.Timber

class MainActivity : AppCompatActivity() {

    private lateinit var service: GithubService
    private lateinit var mCardAdapter: CardAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        service = ServiceFactory.createRetrofitService(
                GithubService::class.java, GithubService.SERVICE_ENDPOINT
        )

        mCardAdapter = CardAdapter()

        recycler_view.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = mCardAdapter
        }

        button_clear.setOnClickListener { mCardAdapter.clear() }
        button_fetch.setOnClickListener {
            mCardAdapter.clear()
            searchAndUpdate(find_edit_text.text.toString())
        }

        setDefaultUsers()
    }

    private fun setDefaultUsers() = resources.getStringArray(R.array.default_github_logins)
            .forEach { searchAndUpdate(it) }

    private fun searchAndUpdate(query: String) {
        Timber.d("Query: $query")
        service.getUserRx(query)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .cache()
                .subscribe({ response ->
                    Timber.d("Response: ${response.login}")
                    mCardAdapter.apply {
                        addData(response)
                    }
                }, { ex ->
                    Timber.e(ex)
                    Snackbar.make(base_layout, "An error occurred: ${ex.message}", Snackbar.LENGTH_LONG)
                            .show()
                })
    }

}
