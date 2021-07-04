
package com.example.smartwedding

import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.smartwedding.databinding.ActivityHallDescriptionBinding
import com.example.smartwedding.util.*

enum class HallActionMode {
    CANCEL, APPLY
}

class HallDescriptionActivity : AppCompatActivity() {

    private lateinit var hallName: String
    private lateinit var binding: ActivityHallDescriptionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_hall_description)
        intent.extras?.let {
            hallName = it.get(HALL_NAME).toString()
            val hallPrice = it.getInt(HALL_PRICE)
            val hallCapacity = it.getInt(HALL_CAPACITY)
            val hallChildrenAllowed = it.getBoolean(CHILDREN_ALLOWED)
            val hallLocation = it.get(HALL_CITY).toString()
            val hallImageId = it.get(HALL_IMAGE_ID).toString()
            val hallPhone = it.get(HALL_PHONE).toString()
            val description =
                "Hall Name:$hallName\nHall Price:$hallPrice\nHall Capacity:$hallCapacity\nChildren Allowed:" +
                        "$hallChildrenAllowed\nHall Location:$hallLocation\nHall Phone:$hallPhone"
            binding.apply {
                hallImg.setImageResource(resources.getIdentifier(hallImageId, "drawable", packageName))
                details.text = description
            }
        }
    }

    fun cancelBtnOnClick(view: View) {
        showDialog( R.string.alert, R.string.are_you_sure_you_want_to_cancel, android.R.drawable.ic_dialog_alert,
            HallActionMode.CANCEL)
    }

    fun bookHallOnClick(view: View) {
        showDialog(R.string.alert, R.string.are_you_sure_you_want_to_book, android.R.drawable.ic_dialog_info,
            HallActionMode.APPLY)
    }

    private fun showDialog(title: Int, message: Int, iconId: Int, mode: HallActionMode) {
        val builder = AlertDialog.Builder(this)
        builder.apply {
            setTitle(title)
            setMessage(message)
            setIcon(iconId)
            setPositiveButton(R.string.yes) { _, _ ->
                if (mode == HallActionMode.CANCEL) finish() else saveAppliedHall()
            }

            setNeutralButton(R.string.cancel) { _, _ ->}
            setNegativeButton(R.string.no) { _, _ -> }
        }

        val alertDialog: AlertDialog = builder.create()
        alertDialog.show()
    }

    private fun saveAppliedHall() {
        val sharedPreferences: SharedPreferences = getSharedPreferences(HALL_DATA, MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.putString(SELECTED_HALL, hallName+"_100")
        editor.apply()
        finish()
    }

}