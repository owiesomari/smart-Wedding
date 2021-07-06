package com.example.smartwedding

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

class FarmsListActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_farms_list)
        val fm: FragmentManager =supportFragmentManager
        var fragment: Fragment? =fm.findFragmentById(R.id.container1)
        if(fragment==null) {
            fragment = ListviewFragment()
            fm.beginTransaction().add(R.id.container1,fragment).commit()
        }
    }
}