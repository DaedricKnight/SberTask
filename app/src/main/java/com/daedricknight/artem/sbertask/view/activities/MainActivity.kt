package com.daedricknight.artem.sbertask.view.activities

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import com.daedricknight.artem.sbertask.R
import com.daedricknight.artem.sbertask.model.persistence.WordRoomDatabase
import com.daedricknight.artem.sbertask.model.persistence.dao.WordDao
import com.daedricknight.artem.sbertask.view.fragments.TranslateFragment
import com.daedricknight.artem.sbertask.view.fragments.WordsFragment


class MainActivity : AppCompatActivity() {
    private lateinit var mDb: WordRoomDatabase
    private lateinit var mWordDao: WordDao

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                addFragmentToActivity(
                    getSupportFragmentManager(), TranslateFragment(), R.id.fragment_container)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_dashboard -> {
                addFragmentToActivity(
                    getSupportFragmentManager(), WordsFragment(), R.id.fragment_container)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_notifications -> {
                addFragmentToActivity(
                    getSupportFragmentManager(), TranslateFragment(), R.id.fragment_container)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    fun toast(text: String){
        Toast.makeText(this@MainActivity,text,Toast.LENGTH_LONG).show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        addFragmentToActivity(supportFragmentManager, TranslateFragment(), R.id.fragment_container)
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }

    fun addFragmentToActivity(manager: FragmentManager, fragment: Fragment, frameId: Int) {
        manager.beginTransaction()
        .replace(frameId, fragment)
        .addToBackStack(null)
        .commit()
    }
}
