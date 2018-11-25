package me.aravindraj.androidfragments.features.main


import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.view.View
import kotlinx.android.synthetic.main.fragment_main.*
import me.aravindraj.androidfragments.R
import me.aravindraj.androidfragments.features.base.BaseFragment
import me.aravindraj.androidfragments.features.favourite.FavouriteFragment
import me.aravindraj.androidfragments.features.home.HomeFragment
import me.aravindraj.androidfragments.features.profile.ProfileFragment
import me.aravindraj.androidfragments.util.Constants.Companion.TAB_FAVOURITE
import me.aravindraj.androidfragments.util.Constants.Companion.TAB_HOME
import me.aravindraj.androidfragments.util.Constants.Companion.TAB_PROFILE

import java.util.*


class MainFragment : BaseFragment() {


    private var mStacks: HashMap<String, Stack<Fragment>>? = null

    private var mCurrentTab: String? = null


    override fun layoutId() = R.layout.fragment_main


    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home-> {
                selectedTab(TAB_HOME);
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_favourite -> {
                selectedTab(TAB_FAVOURITE)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_profile -> {
                selectedTab(TAB_PROFILE)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    private fun selectedTab(tabId: String) {


        mCurrentTab = tabId
        if (mStacks!![tabId]!!.size == 0) {
            /*
   *    First time this tab is selected. So add first fragment of that tab.
   *    Dont need animation, so that argument is false.
   *    We are adding a new fragment which is not present in stack. So add to stack is true.
   */
            if (tabId.equals(TAB_HOME)) {
                pushFragments(tabId, HomeFragment(), true)
            } else if (tabId.equals(TAB_FAVOURITE)) {
                pushFragments(tabId, FavouriteFragment(), true)
            } else if (tabId.equals(TAB_PROFILE)) {
                pushFragments(tabId, ProfileFragment(), true)
            }
        } else {
            /*
   *    We are switching tabs, and target tab is already has atleast one fragment.
   *    No need of animation, no need of stack pushing. Just show the target fragment
   */
            pushFragments(tabId, mStacks!![tabId]!!.lastElement(), false)
        }

    }


    fun popFragments() {
        /*
   *    Select the second last fragment in current tab's stack..
   *    which will be shown after the fragment transaction given below
   */
        val fragment = mStacks!![mCurrentTab]!!.elementAt(mStacks!![mCurrentTab]!!.size - 2)
        /*pop current fragment from stack.. */
        mStacks!![mCurrentTab]!!.pop()

        /* We have the target fragment in hand.. Just show it.. Show a standard navigation animation*/
        val manager = activity!!.supportFragmentManager
        val ft = manager.beginTransaction()
        ft.replace(R.id.mainFragmentContainer, fragment)
        ft.commit()
    }

    fun onBackPressed() {
        if (mStacks!![mCurrentTab]!!.size == 1) {
            // We are already showing first fragment of current tab, so when back pressed, we will finish this activity..
            activity!!.finish()
            return
        }

        //* Goto previous fragment in navigation stack of this tab *//*
        popFragments()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        mStacks = HashMap()
        mStacks!![TAB_HOME] = Stack<Fragment>()
        mStacks!![TAB_FAVOURITE] = Stack<Fragment>()
        mStacks!![TAB_PROFILE] = Stack<Fragment>()
        bottomNavigationView.setItemIconTintList(null)
        bottomNavigationView.setSelectedItemId(R.id.navigation_home);
    }

    fun pushFragments(tag: String, fragment: Fragment, shouldAdd: Boolean) {
        if (shouldAdd)
            mStacks!![tag]!!.push(fragment)
        val manager = activity!!.supportFragmentManager
        val ft = manager.beginTransaction()
        ft.replace(R.id.mainFragmentContainer, fragment)
        ft.commit()
    }
}
