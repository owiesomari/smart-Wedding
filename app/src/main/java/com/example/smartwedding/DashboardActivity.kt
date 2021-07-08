package com.example.smartwedding

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.smartwedding.databinding.ActivityDashboardBinding

enum class DashboardTextsIndices(val value: Int) {
    HALLS(0),
    FARMS(1),
    PLANNERS(2),
    PUTTY(3),
    SWEET(4),
    PHOTOGRAPHY(5);
}

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
        val dashboardTexts = resources.getStringArray(R.array.dashboard_items)
        binding.apply {
            hallsText.text = dashboardTexts[DashboardTextsIndices.HALLS.value]
            farmsText.text = dashboardTexts[DashboardTextsIndices.FARMS.value]
            plannerText.text = dashboardTexts[DashboardTextsIndices.PLANNERS.value]
            putteyText.text = dashboardTexts[DashboardTextsIndices.PUTTY.value]
            sweetText.text = dashboardTexts[DashboardTextsIndices.SWEET.value]
            photographyText.text = dashboardTexts[DashboardTextsIndices.PHOTOGRAPHY.value]
        }
    }

    fun hallsCardViewOnClick(view: View) {
        startActivity(Intent(this, HallsFilterActivity::class.java))
    }

    fun farmCardViewOnClick(view: View) {
        startActivity(Intent(this, FarmsListActivity::class.java))

    }

    fun weddingOnclick(view: View) {
        startActivity(Intent(this,WeddingPlannerListActivity::class.java))
    }

}