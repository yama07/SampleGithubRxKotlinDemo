package jp.yama07.samplegithubrxkotlindemo.util

import android.app.Activity

/**
 * Created by yamamoto on 2018/01/01.
 */

class ScreenSize(private val activity: Activity) {
    fun getScreenHeight(decrease: Float = 1f): Int {
        var displayMetrics = activity.resources.displayMetrics
        return Math.round(displayMetrics.heightPixels / displayMetrics.density / decrease)
    }

    fun getScreenWidth(decrease: Float): Int {
        var displayMetrics = activity.resources.displayMetrics
        return Math.round(displayMetrics.widthPixels / displayMetrics.density / decrease)
    }
}
