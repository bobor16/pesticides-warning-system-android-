package com.example.pesticides_android_client

import android.app.Activity
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView

class MainActivity : Activity(), SensorEventListener {

    /**
     * Variables for the compass
     */
    // define the display assembly compass picture
    private var image: ImageView? = null

    // record the compass picture angle turned
    private var currentDegree = 0f

    // device sensor manager
    private var mSensorManager: SensorManager? = null
    var tvHeading: TextView? = null

    /**
     * Variables for getting location
     */


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        image = findViewById(R.id.iv_compass)

        // TextView that will tell the user what degree is he heading
        tvHeading = findViewById(R.id.orientation)

        // initialize your android device sensor capabilities
        mSensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
    }

    override fun onResume() {
        super.onResume()
        // for the system's orientation sensor registered listeners
        mSensorManager!!.registerListener(this, mSensorManager!!.getDefaultSensor(Sensor.TYPE_ORIENTATION),
                SensorManager.SENSOR_DELAY_GAME)
    }

    override fun onPause() {
        super.onPause()

        // to stop the listener and save battery
        mSensorManager!!.unregisterListener(this)
    }

    override fun onSensorChanged(event: SensorEvent) {

        // get the angle around the z-axis rotated
        val degree = Math.round(event.values[0]).toFloat()
        val degrees = java.lang.Float.toString(degree)
        tvHeading!!.text = "Heading: $degrees degrees"

        // create a rotation animation (reverse turn degree degrees)
        val ra = RotateAnimation(
                currentDegree,
                -degree,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF,
                0.5f)

        // how long the animation will take place
        ra.duration = 210

        // set the animation after the end of the reservation status
        ra.fillAfter = true

        // Start the animation
        image!!.startAnimation(ra)
        currentDegree = -degree
    }

    override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {
        // not in use
    }
}