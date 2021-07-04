package com.example.smartwedding

import android.content.SharedPreferences
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.smartwedding.util.*

class CartActivity:AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)
        val cartValue:TextView=findViewById(R.id.cartValue)
        val getSharedPrefs: SharedPreferences =getSharedPreferences(HALL_DATA, MODE_PRIVATE)
        var value: String? =getSharedPrefs.getString(SELECTED_HALL,"Nothing")
        cartValue.text="Hall Name: "+value.toString().split("_")[0]+"\n"+"Price:"+
                value.toString().split("_")[1]
    }

}