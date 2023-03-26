package com.atifqamar.androidwebserver

import android.annotation.SuppressLint
import android.net.wifi.WifiManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView

@SuppressLint("LongLogTag")
class MainActivity : AppCompatActivity() {
    private lateinit var tvIpAddress : TextView
    private lateinit var btStartServer : Button
    private lateinit var btStopServer : Button
    private val TAG : String ="com.atifqamar.androidwebserver.MainActivity"
    private var androidWebServer: AndroidWebServer? = null
    private val portNumber = 9988
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initUI()
        action()
    }

    private fun initUI() {
        tvIpAddress = findViewById(R.id.tvIpAddress)
        btStartServer = findViewById(R.id.btStartServer)
        btStopServer = findViewById(R.id.btStopServer)
    }

    private fun action() {
        btStartServer.setOnClickListener {
            startAndroidWebServer()
        }

        btStopServer.setOnClickListener {
            stopAndroidWebServer()
        }

    }

    private fun startAndroidWebServer() {
        try {
            androidWebServer = AndroidWebServer(portNumber, this@MainActivity)
            androidWebServer?.start()
            tvIpAddress?.text = getIpAccess()
        } catch (e: Exception) {
            Log.e(TAG, e.message, e)
            e.printStackTrace()
        }
    }

    private fun stopAndroidWebServer() {
        try {
            if (androidWebServer != null) {
                androidWebServer?.stop()
            }
        } catch (e: Exception) {
            Log.e(TAG, e.message, e)
            e.printStackTrace()
        }
    }

    fun getIpAccess(): String? {
        val wifiManager = applicationContext.getSystemService(WIFI_SERVICE) as WifiManager?
        val ipAddress = wifiManager!!.connectionInfo.ipAddress
        val formatedIpAddress = String.format(
            "%d.%d.%d.%d",
            ipAddress and 0xff,
            ipAddress shr 8 and 0xff,
            ipAddress shr 16 and 0xff,
            ipAddress shr 24 and 0xff
        )
        return "http://$formatedIpAddress:9988"
    }


    override fun onResume() {
        super.onResume()
        Log.i(TAG, "onResume called")
        if (androidWebServer != null) {
            if (!androidWebServer?.isAlive!!) {
                androidWebServer?.start()
                Log.d(TAG, "onResume() server restarted")
            }
        }

    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause() ")
        if (androidWebServer != null) {
            if (!androidWebServer?.isAlive!!) {
                androidWebServer?.start()
                Log.d(TAG, "onPause() server restarted")
            }
        }

    }

    override fun onDestroy() {
        super.onDestroy()

        if (androidWebServer != null) {
            androidWebServer?.stop()
        }

        Log.d(TAG, "onDestroy() server stopped")
    }

    override fun onRestart() {
        super.onRestart()
        if (androidWebServer != null) {
            if (!androidWebServer?.isAlive!!) {
                androidWebServer?.start()
                Log.d(TAG, "onRestart() server restarted")

            }
        }
    }
}