package com.thegabru.kncc.activities

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import com.thegabru.kncc.R
import com.thegabru.kncc.fragments.DashBoardFragment
import com.thegabru.kncc.fragments.HomeFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val homeFragment : HomeFragment
    private val dashBoardFragment : DashBoardFragment

    init {
        homeFragment = HomeFragment()
        dashBoardFragment = DashBoardFragment()
    }

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->

        val transaction = supportFragmentManager.beginTransaction()

        when (item.itemId) {
            R.id.navigation_home -> {
                setTitle(R.string.title_home)
                transaction.replace(R.id.fragment_container, homeFragment)
            }
            R.id.navigation_dashboard -> {
                setTitle(R.string.title_dashboard)
                transaction.replace(R.id.fragment_container, dashBoardFragment)
            }
        }
        transaction.commit()
        true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        val transaction = supportFragmentManager.beginTransaction()
        transaction.add(R.id.fragment_container, homeFragment)
        transaction.commit()

    }
}
