package com.example.smartwedding

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.smartwedding.databinding.ActivityHallsListBinding
import com.example.smartwedding.models.Hall
import com.example.smartwedding.util.*
import org.json.JSONException
import com.example.smartwedding.util.VolleyCallBack
import org.json.JSONObject

class HallsListActivity : AppCompatActivity() {

    private val halls = ArrayList<Hall>()
    private lateinit var binding: ActivityHallsListBinding
    private val hallsDataURL = "https://api.npoint.io/6045ed449416133135c1"
    private var requestQueue: RequestQueue? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_halls_list)
        requestQueue = Volley.newRequestQueue(this)
        getHallsData(object : VolleyCallBack {
            override fun onSuccess(response: JSONObject) {
                val hallsArray = response.getJSONArray("halls")
                for (i in 0 until hallsArray.length()) {
                    var hall = Hall()
                    val hallObject = hallsArray.getJSONObject(i)
                    hall.apply {
                        name = hallObject.getString(HALL_NAME)
                        phone = hallObject.getString(HALL_PHONE)
                        location = hallObject.getString(HALL_CITY)
                        capacity = hallObject.getInt(HALL_CAPACITY)
                        price = hallObject.getInt(HALL_PRICE)
                        childrenAllowed = hallObject.getBoolean(CHILDREN_ALLOWED)
                        imageId = hallObject.getString(HALL_IMAGE_ID)
                        halls.add(hall)
                    }
                }
                binding.progressBar.visibility = ProgressBar.INVISIBLE
                bindHallsList()
            }
        })
        binding.halls.setOnItemClickListener() { adapterView, _, position, _ ->
            hallItemsOnClickListener(adapterView.getItemAtPosition(position))
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.actionbar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        startActivity(Intent(this, CartActivity::class.java))
        return super.onOptionsItemSelected(item)
    }

    private fun bindHallsList() {
        intent.extras?.let {
            val hallPrice = it.getInt(HALL_PRICE)
            val hallCapacity = it.getInt(HALL_CAPACITY)
            val childrenAllowed = it.getBoolean(CHILDREN_ALLOWED)
            val hallCity = it.get(HALL_CITY).toString()
            val imageIds = ArrayList<Int>()
            val hallNames = ArrayList<String>()
            halls.forEach { hall ->
                if (hallPrice <= hall.price && hall.capacity >= hallCapacity && hall.location == hallCity &&
                    hall.childrenAllowed == childrenAllowed) {
                    hallNames.add(hall.name)
                    imageIds.add(resources.getIdentifier(hall.imageId, "drawable", packageName))
                }
            }
            binding.halls.adapter = MyListAdapter(this, hallNames, imageIds)
        }
    }

    private fun getHallsData(callBack: VolleyCallBack) {
        val request = JsonObjectRequest(Request.Method.GET, hallsDataURL, null, { response ->
            try {
                callBack.onSuccess(response)
            } catch (e: JSONException) {
                e.printStackTrace()
            }
        }, { error -> error.printStackTrace() })
        requestQueue?.add(request)
    }

    private fun hallItemsOnClickListener(itemAtPosition: Any) {
        val hall = (halls.filter { it.name == itemAtPosition })[0]
        val intent = Intent(this, HallDescriptionActivity::class.java)
        intent.putExtra(HALL_NAME, hall.name)
        intent.putExtra(HALL_CITY, hall.location)
        intent.putExtra(HALL_CAPACITY, hall.capacity)
        intent.putExtra(HALL_PRICE, hall.price)
        intent.putExtra(CHILDREN_ALLOWED, hall.childrenAllowed)
        intent.putExtra(HALL_IMAGE_ID, hall.imageId)
        intent.putExtra(HALL_PHONE, hall.phone)
        startActivity(intent)
    }
}

class MyListAdapter(
    private val context: Activity, private val title: ArrayList<String>,
    private val imgid: ArrayList<Int>
) : ArrayAdapter<String>(context, R.layout.activity_listview, title) {
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

