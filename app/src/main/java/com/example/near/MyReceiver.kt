package com.example.near

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.example.near.ui.MainActivity

class MyReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if(MainAction.equals(intent.action)){
            val boolean = true
            val myIntent = Intent(context, MainActivity::class.java)
            myIntent.putExtra("homeBoolean", boolean)
            myIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
            context.startActivity(myIntent)
        }
        if(CartAction.equals(intent.action)){
            val boolean = true
            val myIntent = Intent(context, MainActivity::class.java)
            myIntent.putExtra("cartBoolean", boolean)
            myIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
            context.startActivity(myIntent)
        }
    }

    companion object {
        const val MainAction : String = "com.example.near.MainAction"
        const val CartAction : String = "com.example.near.CartAction"
    }
}