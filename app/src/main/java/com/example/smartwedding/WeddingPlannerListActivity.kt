package com.example.smartwedding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.smartwedding.models.WeddingPlanner
import java.util.*

class WeddingPlannerListActivity : AppCompatActivity() {

    private lateinit var weddingList: RecyclerView
    private lateinit var weddingPlanners: ArrayList<WeddingPlanner>
    lateinit var imgId: Array<Int>
    lateinit var description: Array<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wedding_planner_list)
        imgId = arrayOf(
            R.drawable.ic_hall1,
            R.drawable.ic_hall10,
            R.drawable.ic_hall12,
            R.drawable.ic_hall9,
            R.drawable.ic_hall13
        )
        description = arrayOf(
            getString(R.string.Wedding_Planner_one),
            getString(R.string.Wedding_Planner_two),
            getString(R.string.Wedding_Planner_three),
            getString(R.string.Wedding_Planner_four),
            getString(R.string.Wedding_Planner_five)
        )
        weddingList = findViewById(R.id.weddingList)
        weddingList.layoutManager = LinearLayoutManager(this)
        weddingList.setHasFixedSize(true)
        weddingPlanners = arrayListOf()
        getWeddingsData()
    }

    private fun getWeddingsData() {
        for (i in imgId.indices) {
            val news = WeddingPlanner(imgId[i], description[i])
            weddingPlanners.add(news)
        }
        var adapter=MyAdapter(weddingPlanners)
        weddingList.adapter = adapter
        adapter.setOnItemClickListener(object :MyAdapter.OnItemClickListener{
            override fun onItemClick(position: Int) {
                Toast.makeText(this@WeddingPlannerListActivity,"You clicked on item $position",Toast.LENGTH_SHORT).show()
            }
        })
    }

}

class MyAdapter(private val newsList: ArrayList<WeddingPlanner>) :
    RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

    private lateinit var listener:OnItemClickListener

    interface OnItemClickListener{

        fun onItemClick(position:Int)
    }

    fun setOnItemClickListener(listener:OnItemClickListener){
        this.listener=listener

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.activity_listview,
                            parent, false),listener)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = newsList[position]
        holder.img.setImageResource(currentItem.imageTitle)
        holder.heading.text = currentItem.description
    }

    override fun getItemCount(): Int {
        return newsList.size
    }

    class MyViewHolder(itemView: View, listener:OnItemClickListener) : RecyclerView.ViewHolder(itemView) {
        val img = itemView.findViewById(R.id.icon) as ImageView
        val heading = itemView.findViewById(R.id.title) as TextView
        init {
            itemView.setOnClickListener{
                listener.onItemClick(adapterPosition)
            }
        }
    }

}