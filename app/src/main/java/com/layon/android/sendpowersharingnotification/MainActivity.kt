package com.layon.android.sendpowersharingnotification

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {

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
        btn_powersharing_on.setOnClickListener {
            sendBroadcast(intentPowerSharingOff)
        }
    }

    fun sendBroadcast(intentName: String){
        Intent().also { intent ->
            intent.setAction(intentName)
            intent.putExtra("extra", intentName)
            sendBroadcast(intent)
        }
    }
}
