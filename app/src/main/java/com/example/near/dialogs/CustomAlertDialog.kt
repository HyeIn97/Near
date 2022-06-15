package com.example.near.dialogs

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.databinding.DataBindingUtil
import com.example.near.R
import com.example.near.databinding.CustomAlertDialogBinding

class CustomAlertDialog (val mContext:Context){
    val dialog = Dialog(mContext)
    lateinit var binding : CustomAlertDialogBinding

    fun myDialog(){
        binding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.custom_alert_dialog, null, false)
        dialog.setContentView(binding.root)

        dialog.window?.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)

        dialog.setCancelable(true)

        dialog.show()
    }
}