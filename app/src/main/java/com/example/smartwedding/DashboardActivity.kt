package com.example.smartwedding

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.smartwedding.databinding.ActivityDashboardBinding


class DashboardActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDashboardBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_dashboard)

        setDashboardCardViewTexts()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.actionbar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        startActivity(Intent(this,CartActivity::class.java))
        return super.onOptionsItemSelected(item)
    }

    private fun setDashboardCardViewTexts() {
        val texts = resources.getStringArray(R.array.dashboard_items)
        binding.hallsTextView.text = texts[0]
        binding.framsTextView.text = texts[1]
        binding.plannerTextView.text = texts[2]
        binding.putteyTextView.text = texts[3]
        binding.sweetTextView.text = texts[4]
        binding.photographyTextView.text = texts[5]
    }

    fun hallsCardViewOnClick(view: View) {
        startActivity(Intent(this, HallsActivity::class.java))
    }

}