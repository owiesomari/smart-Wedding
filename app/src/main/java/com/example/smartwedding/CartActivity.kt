package com.example.smartwedding

import android.content.SharedPreferences
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class CartActivity:AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)
        val text1:TextView=findViewById(R.id.textView3)

        val getSharedPrefs: SharedPreferences =getSharedPreferences("HALL", MODE_PRIVATE)
        var value: String? =getSharedPrefs.getString("selectedHall","Nothing")

        text1.text="HallName: "+value.toString().split("_")[0]+"\n"+"Price:"+
                value.toString().split("_")[1]
    }
}