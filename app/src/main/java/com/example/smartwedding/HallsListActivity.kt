package com.example.smartwedding

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.smartwedding.databinding.ActivityHallsListBinding
import com.example.smartwedding.models.Hall
import com.example.smartwedding.util.*
import org.json.JSONArray

class HallsListActivity : AppCompatActivity() {

    private val halls = ArrayList<Hall>()
    private lateinit var binding: ActivityHallsListBinding
    private val HALLS_FILE_NAME="halls.json"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_halls_list)
        getHallsData()
        bindHallsList()
        binding.halls.setOnItemClickListener(){ adapterView, _, position, _ ->
            hallItemsOnClickListener(adapterView.getItemAtPosition(position))
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
       intent.extras?.let {
           val hallPrice= it.getInt(HALL_PRICE)
           val hallCapacity=it.getInt(HALL_CAPACITY)
           val childrenAllowed=it.getBoolean(CHILDREN_ALLOWED)
           val hallCity=it.get(HALL_CITY).toString()
           var imageIds = ArrayList<Int>()
           var hallNames=ArrayList<String>()
           halls.forEach {hall->
               if (hallPrice <=hall.price  && hall.capacity>= hallCapacity &&hall.location==hallCity&&
                   hall.childrenAllowed==childrenAllowed) {
                   hallNames.add(hall.name)
                   imageIds.add(resources.getIdentifier(hall.imageId, "drawable", packageName))
               }
           }
           binding.halls.adapter = MyListAdapter(this,hallNames,imageIds)
       }
    }

   private fun getHallsData(){
        var hallsArray = JSONArray(applicationContext.assets.open(HALLS_FILE_NAME).bufferedReader().use { it.readText() })
        for (jsonIndex in 0 until hallsArray.length()) {
            var hall=Hall()
            hall.apply {
                name = hallsArray.getJSONObject(jsonIndex).getString(HALL_NAME)
                phone = hallsArray.getJSONObject(jsonIndex).getString(HALL_PHONE)
                location = hallsArray.getJSONObject(jsonIndex).getString(HALL_CITY)
                capacity = hallsArray.getJSONObject(jsonIndex).getInt(HALL_CAPACITY)
                price = hallsArray.getJSONObject(jsonIndex).getInt(HALL_PRICE)
                childrenAllowed = hallsArray.getJSONObject(jsonIndex).getBoolean(CHILDREN_ALLOWED)
                imageId=hallsArray.getJSONObject(jsonIndex).getString(HALL_IMAGE_ID)
                halls.add(hall)
            }
        }
    }

   private fun hallItemsOnClickListener( itemAtPosition:Any){
       val hall= (halls.filter { it.name==itemAtPosition })[0]
       val intent=Intent(this,HallDescriptionActivity::class.java)
       intent.putExtra(HALL_NAME,hall.name)
       intent.putExtra(HALL_CITY,hall.location)
       intent.putExtra(HALL_CAPACITY,hall.capacity)
       intent.putExtra(HALL_PRICE,hall.price)
       intent.putExtra(CHILDREN_ALLOWED,hall.childrenAllowed)
       intent.putExtra(HALL_IMAGE_ID,hall.imageId)
       intent.putExtra(HALL_PHONE,hall.phone)
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

