package jp.yama07.samplegithubrxkotlindemo.activity

import android.app.Fragment
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import jp.yama07.samplegithubrxkotlindemo.R
import jp.yama07.samplegithubrxkotlindemo.fragment.FindUsersFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

       if(savedInstanceState == null){
            supportFragmentManager
                    .beginTransaction()
                    .add(R.id.contents, FindUsersFragment())
                    .commit()
        }
    }



}
