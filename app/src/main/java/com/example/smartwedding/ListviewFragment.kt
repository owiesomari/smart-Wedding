package com.example.smartwedding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.ListFragment
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.smartwedding.models.Farm
import com.example.smartwedding.util.*
import org.json.JSONException
import org.json.JSONObject

class ListviewFragment : ListFragment() {

    private val farmsDataURL = "https://api.npoint.io/defc9bcea9b6a515ee38"
    private var requestQueue: RequestQueue? = null
    private val farms = ArrayList<Farm>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestQueue = Volley.newRequestQueue(layoutInflater.context)
        getFarmsData(object : VolleyCallBack {
            override fun onSuccess(response: JSONObject) {
                val farmsArray = response.getJSONArray("farms")
                for (i in 0 until farmsArray.length()) {
                    var farm = Farm()
                    val farmObject = farmsArray.getJSONObject(i)
                    farm.apply {
                        name = farmObject.getString(FARM_NAME)
                        phone = farmObject.getString(FARM_PHONE)
                        location = farmObject.getString(FARM_CITY)
                        capacity = farmObject.getInt(FARM_CAPACITY)
                        price = farmObject.getInt(FARM_PRICE)
                        childrenAllowed = farmObject.getBoolean(FARM_ALLOWED)
                        imageId = farmObject.getString(FARM_IMAGE_ID)
                        farms.add(farm)
                    }
                }
                bindFarmsList()
            }
        })
    }

    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?,  savedInstanceState: Bundle?  ): View? {
        return inflater.inflate(R.layout.fragment_listview, container, false)
    }

    private fun getFarmsData(callBack: VolleyCallBack) {
        val request = JsonObjectRequest(Request.Method.GET, farmsDataURL, null, { response ->
            try {
                callBack.onSuccess(response)
            } catch (e: JSONException) {
                e.printStackTrace()
            }
        }, { error -> error.printStackTrace() })
        requestQueue?.add(request)
    }

    private fun bindFarmsList() {
        val imageIds = ArrayList<Int>()
        val farmsNames = ArrayList<String>()
        farms.forEach {
            imageIds.add(resources.getIdentifier(it.imageId, "drawable", activity?.packageName))
            farmsNames.add(it.name)
        }
        listAdapter = MyListAdapter(requireActivity(), farmsNames, imageIds)
    }
}

