package com.example.fleetech.base

import android.app.Activity
import android.app.Dialog
import android.graphics.PorterDuff
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import android.widget.ProgressBar
import com.example.fleetech.R

open class BaseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setSoftInputMode(
            WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN
                    or WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
    }

    // progress bar handling
    open fun showProgress(activity: Activity): Dialog? {
        val dialog = Dialog(activity)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.getWindow()?.setBackgroundDrawable(
            ColorDrawable(0)
        )
        dialog.setContentView(R.layout.dialog_progress)
        val progressBar: ProgressBar = dialog.findViewById(R.id.progressBar)
        progressBar.indeterminateDrawable.setColorFilter(
            activity.resources.getColor(R.color.black),
            PorterDuff.Mode.MULTIPLY
        )
        dialog.setCancelable(false)
        dialog.show()
        return dialog
    }

}