package com.example.smartwedding

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.smartwedding.databinding.ActivityHallsBinding
import kotlin.math.log

class HallsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHallsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_halls)

        bindCitiesSpinner()
        binding.priceseekBar.setOnSeekBarChangeListener(object :
            SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(
                seek: SeekBar,
                progress: Int, fromUser: Boolean
            ) {
                binding.seekBarValue.text = seek.progress.toString() + ".00JOD"
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
        if (binding.citiesSpinner != null) {
            val adapter = ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item, jordanCites
            )
            binding.citiesSpinner.adapter = adapter
        }
    }

    fun applyButtonOnClick(view: View) {
        val intent = Intent(this, HallsViewActivity::class.java)
        intent.putExtra("city", binding.citiesSpinner.selectedItemId)
        intent.putExtra("price", binding.priceseekBar.progress)
        intent.putExtra("capacity", binding.capacityTextNumber.text)
        intent.putExtra("childrenAllowed", binding.childrenAllowedSwitch.isChecked)
        startActivity(intent)
    }
}

