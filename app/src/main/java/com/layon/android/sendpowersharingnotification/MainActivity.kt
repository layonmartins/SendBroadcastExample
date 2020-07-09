package com.layon.android.sendpowersharingnotification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    //name of the intents
    var intentPowerSharingOn = "com.layonf.broadcast.POWER_SHARING_ON"
    var intentPowerSharingOff = "com.layonf.broadcast.POWER_SHARING_OFF"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //get button reference and set onclickListener
        val btn_powersharing_on = findViewById(R.id.powersharing_on) as Button
        btn_powersharing_on.setOnClickListener {
            sendBroadcast(intentPowerSharingOn)
        }

        //get button reference and set onclickListener
        val btn_powersharing_off = findViewById(R.id.powersharing_off) as Button
        btn_powersharing_off.setOnClickListener {
            sendBroadcast(intentPowerSharingOff)
        }

        //create a broadcast receiver object
        val broadCastReceiver = object : BroadcastReceiver() {
            override fun onReceive(contxt: Context?, intent: Intent?) {
                //get textview reference
                val txt_last_broadcast = findViewById(R.id.last_broadcast) as TextView
                //update the last broadcast text with the last sended broadcast
                when (intent?.action) {
                    intentPowerSharingOn -> txt_last_broadcast.setText(intentPowerSharingOn)
                    intentPowerSharingOff -> txt_last_broadcast.setText(intentPowerSharingOff)
                }
            }
        }


        //add intent to register broadcast
        val filter = IntentFilter().apply {
            addAction(intentPowerSharingOn)
            addAction(intentPowerSharingOff)
        }

        //register brodcast receiver
        registerReceiver(broadCastReceiver, filter)
    }

    //send the broadcast
    fun sendBroadcast(intentName: String){
        Log.d("layonf", "sendbroadst = " + intentName)
        Intent().also { intent ->
            intent.setAction(intentName)
            intent.putExtra("extra", intentName)
            sendBroadcast(intent)
        }
    }


}
