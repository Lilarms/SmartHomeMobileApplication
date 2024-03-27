package com.example.smarthomedeviceapplication

import android.os.Bundle
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MainActivity3 : ComponentActivity() {
    private val databaseReference: DatabaseReference by lazy {
        FirebaseDatabase.getInstance("https://smart-home-31620-default-rtdb.firebaseio.com").reference
    }


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContent {
            setContentView(R.layout.activity_irrigation_view)
            val humidity = remember { mutableStateOf("Loading...") }
            observeTemperature(humidity)
           // textView = (TextView) findViewByID(text_temperature_details);

            val textView: TextView = findViewById(R.id.text_temperature_details);
            textView.setText(humidity.value)
            System.out.println("the temp is:" + humidity.value)

        }

    }
    private fun observeTemperature(temperature: MutableState<String>) {
        val temperatureRef = databaseReference.child("sensors").child("temperature").child("value")

        temperatureRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val value = snapshot.getValue(String::class.java)
                temperature.value = value ?: "N/A"
            }

            override fun onCancelled(error: DatabaseError) {
                temperature.value = "Failed to fetch temperature"
            }
        })
        System.out.println("Observed temp: $temperature" + temperature.value)
    }
}







