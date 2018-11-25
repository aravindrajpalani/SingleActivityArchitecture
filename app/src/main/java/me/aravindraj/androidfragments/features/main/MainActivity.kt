package me.aravindraj.androidfragments.features.main

import android.os.Bundle
import android.view.MenuItem
import me.aravindraj.androidfragments.R
import me.aravindraj.androidfragments.features.base.BaseActivity


class MainActivity : BaseActivity(), MainMvpView {

    lateinit var mainPresenter: MainPresenter

    override fun layoutId() = R.layout.activity_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainPresenter = MainPresenter()
        mainPresenter.attachView(this)
    }

    override fun onResume() {
        super.onResume()
        mainPresenter.onViewReady()
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                if (supportFragmentManager.backStackEntryCount > 0) {
                    supportFragmentManager.popBackStack()
                } else {
                    onBackPressed()
                }
                return true
            }

            else -> return super.onOptionsItemSelected(item)
        }

    }
}
