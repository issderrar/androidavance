package com.example.advanced_android.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.advanced_android.R

class MainActivity : AppCompatActivity(), GamesListFragment.ViewDetails {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val transaction = supportFragmentManager.beginTransaction()
        val fragA = GamesListFragment()
        transaction.add(R.id.fragmentContainer, fragA)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    override fun viewDetails(id : Int) {
        val transaction = supportFragmentManager.beginTransaction()
        val fragB = GameDetailsFragment.newInstance(id)
        transaction.replace(R.id.fragmentContainer, fragB)
        transaction.addToBackStack(null)
        transaction.commit()


    }


}
