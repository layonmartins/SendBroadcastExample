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
import android.widget.Toast
import androidx.appcompat.app.AlertDialog

class MainActivity : AppCompatActivity() {

    //Broadcast receiver reference
    private val myBroadcastReceiver by lazy { makeBroadcastReceiver() }

    //Intent filter reference
    lateinit var filter: IntentFilter

    //State of the power share
    var mState = "-1"

    //define int states powersharing
    val WIRELESS_POWER_SHARE_STATE_OFF = 0
    val WIRELESS_POWER_SHARE_STATE_PEER_WAITING = 1
    val WIRELESS_POWER_SHARE_STATE_PEER_CONNECTED = 2
    val WIRELESS_POWER_SHARE_STATE_POWER_TRANSER = 3

    //define String states
    val STATE_OFF = "WIRELESS_POWER_SHARE_STATE_OFF"
    val STATE_PEER_WAITING = "WIRELESS_POWER_SHARE_STATE_PEER_WAITING"
    val STATE_PEER_CONNECTED = "WIRELESS_POWER_SHARE_STATE_PEER_CONNECTED"
    val STATE_POWER_TRANSER = "WIRELESS_POWER_SHARE_STATE_POWER_TRANSER"

    //name of the intents
    var intentPowerSharingChanged = "com.layonf.broadcast.POWER_SHARING_Changed"
    var intentPowerSharingOn = "com.layonf.broadcast.POWER_SHARING_OnAvailable"
    var intentPowerSharingOff = "com.layonf.broadcast.POWER_SHARING_OnUnavailable"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //get button reference and set onclickListener
        val btn_powersharing_changed = findViewById(R.id.powersharing_changed) as Button
        btn_powersharing_changed.setOnClickListener {
            showAlertDialogList()
        }

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

        //add intent to register broadcast
        filter = IntentFilter().apply {
            addAction(intentPowerSharingChanged)
            addAction(intentPowerSharingOn)
            addAction(intentPowerSharingOff)
        }

        //register brodcast receiver
        registerReceiver(myBroadcastReceiver, filter)
    }

    //show the dialog with a list user options
    fun showAlertDialogList() {
        val items = arrayOf(STATE_OFF, STATE_PEER_WAITING,
            STATE_PEER_CONNECTED, STATE_POWER_TRANSER)
        val builder = AlertDialog.Builder(this)
        with(builder) {
            setTitle("Select the POWER_SHARE_STATE:")
            setItems(items) { dialog, which ->
                //Toast.makeText(applicationContext, items[which] + " is clicked", Toast.LENGTH_SHORT).show()
                //get the correct value
                mState = getState(items[which])
                sendBroadcast(intentPowerSharingChanged)
            }
            show()
        }
    }

    //convert power_share_state
    private fun getState(state: String): String {
        return when (state) {
            STATE_OFF -> "0"
            STATE_PEER_WAITING -> "1"
            STATE_PEER_CONNECTED -> "2"
            STATE_POWER_TRANSER -> "3"
            else -> "-1"
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        //unregister broadcast
        unregisterReceiver(myBroadcastReceiver)
    }


    //send the broadcast
    fun sendBroadcast(intentName: String){

        Intent().also { intent ->
            intent.setAction(intentName)
            //Log.d("layonfapp", "sendbroadst = " + intentName + "state = " + mState)
            intent.putExtra("state", mState)
            sendBroadcast(intent)
        }
    }

    //fun to initialize myBroadcastReceiver by lazy
    private fun makeBroadcastReceiver(): BroadcastReceiver {
        return object : BroadcastReceiver() {
            override fun onReceive(contxt: Context?, intent: Intent?) {
                //get textview reference
                val txt_last_broadcast = findViewById(R.id.last_broadcast) as TextView
                //update the last broadcast text with the last sended broadcast
                when (intent?.action) {
                    intentPowerSharingChanged -> txt_last_broadcast.setText(intentPowerSharingChanged + "= " + mState)
                    intentPowerSharingOn -> txt_last_broadcast.setText(intentPowerSharingOn)
                    intentPowerSharingOff -> txt_last_broadcast.setText(intentPowerSharingOff)
                }
            }
        }
    }

}
