package com.example.smartwedding

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.smartwedding.databinding.ActivityHallsFilterBinding
import com.example.smartwedding.util.*

class HallsFilterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHallsFilterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_halls_filter)
        bindCitiesSpinner()
        binding.priceSeekBar.setOnSeekBarChangeListener(object :
            SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged( seek: SeekBar, progress: Int, fromUser: Boolean) {
                binding.price.text = seek.progress.toString() + ".00JOD"
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })
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

    private fun bindCitiesSpinner() {
        val jordanCites = resources.getStringArray(R.array.jordan_cites)
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, jordanCites)
        binding.citiesSpinner.adapter = adapter
    }

    fun applyButtonOnClick(view: View) {
        val intent = Intent(this, HallsListActivity::class.java)
        intent.putExtra(HALL_CITY, binding.citiesSpinner.selectedItemId)
        intent.putExtra(HALL_PRICE, binding.priceSeekBar.progress)
        intent.putExtra(HALL_CAPACITY, binding.capacity.text)
        intent.putExtra(CHILDREN_ALLOWED, binding.childrenAllowed.isChecked)
        startActivity(intent)
    }

}

