package jp.yama07.samplegithubrxotlindemo

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import com.jakewharton.rxbinding2.widget.RxTextView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import jp.yama07.samplegithubrxotlindemo.adapter.CardAdapter
import jp.yama07.samplegithubrxotlindemo.model.Github
import jp.yama07.samplegithubrxotlindemo.service.GithubService
import jp.yama07.samplegithubrxotlindemo.service.ServiceFactory
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private val LOG = "yama"

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
        button_fetch.setOnClickListener { setDefaultUsers() }

        setDefaultUsers()
        searchObservable()
    }

    private fun searchObservable() = RxTextView.textChangeEvents(find_edit_text)
            .filter {
                Log.d(LOG, "Text is: ${it.text()}")
                find_edit_text.text.length > 3
            }
            .flatMap {
                Log.d(LOG, "FilteredText is: ${it.text().toString()}")
//                service.getUserRx(it.text().toString())
                service.getUserRx("tumblr")
            }
//            .retry()
            .cache()
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ github ->
                Log.d(LOG, "Response: ${github.login}")
                mCardAdapter.apply {
                    clear()
                    notifyDataSetChanged()
                    addData(github)
                }
            }, { e ->
                Log.d(LOG, "Error : ${e.message}")
            })

    private fun setDefaultUsers() = Data.githubList.forEach {
        service.getUserRx(it)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .cache()
                .subscribe({ response ->
                    mCardAdapter.addData(response)
                }, { e ->
                    Log.e(LOG, "ErrorRX: ${e.message}")
                })
    }

}
