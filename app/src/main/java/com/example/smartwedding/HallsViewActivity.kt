package com.example.smartwedding

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.smartwedding.databinding.ActivityHallsviewBinding
import com.example.smartwedding.models.Hall
import com.google.android.material.snackbar.Snackbar
import org.json.JSONArray

class HallsViewActivity : AppCompatActivity() {
    private val halls = ArrayList<Hall>()
    private lateinit var binding: ActivityHallsviewBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_hallsview)

        getHallsData()
        bindHallsList()
        binding.hallsListView.setOnItemClickListener(){ adapterView, _, position, _ ->
            val itemAtPos = adapterView.getItemAtPosition(position)
            val itemIdAtPos = adapterView.getItemIdAtPosition(position)
            hallItemsOnClickListener(itemAtPos,itemIdAtPos)
        }
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

   private fun bindHallsList() {
        val hallPrice= intent.extras?.getInt("price")
        val hallCapacity=intent.extras?.getInt("capacity")
        val childrenAllowed=intent.extras?.getBoolean("childrenAllowed")
        val hallCity=intent.extras?.get("city").toString()
        var imageIds = ArrayList<Int>()
        var hallNames=ArrayList<String>()
        halls.forEach {
            if (hallPrice!! <=it.price  && it.capacity>= hallCapacity!! &&it.location==hallCity&&
                it.childrenAllowed==childrenAllowed) {

                hallNames.add(it.name)
                imageIds.add(resources.getIdentifier(it.imageId, "drawable", packageName))
            }
        }

        val myListAdapter = MyListAdapter(this,hallNames,imageIds)
        binding.hallsListView.adapter = myListAdapter
    }

   private fun getHallsData(){
        val fileInString: String =
            applicationContext.assets.open("halls.json").bufferedReader().use { it.readText() }

        var jsonArray = JSONArray(fileInString)
        var hall: Hall
        for (jsonIndex in 0 until jsonArray.length()) {
            hall = Hall()
            hall.name = jsonArray.getJSONObject(jsonIndex).getString("name")
            hall.phone = jsonArray.getJSONObject(jsonIndex).getString("phone")
            hall.location = jsonArray.getJSONObject(jsonIndex).getString("location")
            hall.capacity = jsonArray.getJSONObject(jsonIndex).getInt("capacity")
            hall.price = jsonArray.getJSONObject(jsonIndex).getInt("price")
            hall.childrenAllowed = jsonArray.getJSONObject(jsonIndex).getBoolean("childrenAllowed")
            hall.imageId=jsonArray.getJSONObject(jsonIndex).getString("img")
            halls.add(hall)
        }
    }

   private fun hallItemsOnClickListener( itemAtPosition:Any, itemIdAtPos:Any){
       // Snackbar.make(binding.hallsListView,"Click on item at $itemAtPosition its item id " +
             //   "$itemIdAtPos",Snackbar.LENGTH_SHORT).show()
       val hall= (halls.filter { it.name==itemAtPosition })[0]
       val intent=Intent(this,HallActivity::class.java)
       intent.putExtra("hallName",hall.name)
       intent.putExtra("hallLocation",hall.location)
       intent.putExtra("hallCapacity",hall.capacity)
       intent.putExtra("hallPrice",hall.price)
       intent.putExtra("hallChildrenAllowed",hall.childrenAllowed)
       intent.putExtra("hallImageId",hall.imageId)
       intent.putExtra("hallPhone",hall.phone)
       startActivity(intent)
    }
}

class MyListAdapter(private val context: Activity, private val title: ArrayList<String>,
                    private val imgid: ArrayList<Int>)
    : ArrayAdapter<String>(context, R.layout.activity_listview, title) {

    override fun getView(position: Int, view: View?, parent: ViewGroup): View {
        val inflater = context.layoutInflater
        val rowView = inflater.inflate(R.layout.activity_listview, null, true)
        val titleText = rowView.findViewById(R.id.title) as TextView
        val imageView = rowView.findViewById(R.id.icon) as ImageView
        titleText.text = title[position]
        imageView.setImageResource(imgid[position])
        return rowView
    }
}

