
package com.example.smartwedding

import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.smartwedding.databinding.ActivityHallBinding

enum class HallActionMode {
    CANCEL, APPLY
}

class HallActivity : AppCompatActivity() {
    private lateinit var hallName: String
    private lateinit var binding: ActivityHallBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_hall)

        hallName = intent.extras?.get("hallName").toString()
        val hallPrice = intent.extras?.getInt("hallPrice")
        val hallCapacity = intent.extras?.getInt("hallCapacity")
        val hallChildrenAllowed = intent.extras?.getBoolean("hallChildrenAllowed")
        val hallLocation = intent.extras?.get("hallLocation").toString()
        val hallImageId = intent.extras?.get("hallImageId").toString()
        val hallPhone = intent.extras?.get("hallPhone").toString()
        val description =
            "Hall Name:$hallName\nHall Price:$hallPrice\nHall Capacity:$hallCapacity\nChildren Allowed:" +
                    "$hallChildrenAllowed\nHall Location:$hallLocation\nHall Phone:$hallPhone"

        binding.hallImg.setImageResource(
            resources.getIdentifier(
                hallImageId,
                "drawable",
                packageName
            )
        )
        binding.detailsTextView.text = description

    }

    fun cancelBtnOnClick(view: View) {
        showDialog(
            R.string.alert,
            R.string.are_you_sure_you_want_to_cancel,
            android.R.drawable.ic_dialog_alert,
            HallActionMode.CANCEL
        )
    }

    fun bookHallOnClick(view: View) {
        showDialog(
            R.string.alert,
            R.string.are_you_sure_you_want_to_book,
            android.R.drawable.ic_dialog_info,
            HallActionMode.APPLY
        )
    }

    private fun showDialog(title: Int, message: Int, iconId: Int, mode: HallActionMode) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(title)
        builder.setMessage(message)
        builder.setIcon(iconId)

        builder.setPositiveButton(R.string.yes) { _, _ ->
            if (mode == HallActionMode.CANCEL) finish() else saveAppliedHall()
        }

        builder.setNeutralButton(R.string.cancel) { _, _ ->
        }

        builder.setNegativeButton(R.string.no) { _, _ ->
        }

        val alertDialog: AlertDialog = builder.create()
        alertDialog.show()
    }

    private fun saveAppliedHall() {
        val sharedPreferences: SharedPreferences = getSharedPreferences("HALL", MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.putString("selectedHall", hallName+"_100")
        editor.apply()
        finish()
    }

}