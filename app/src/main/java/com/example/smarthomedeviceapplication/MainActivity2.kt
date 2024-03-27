package com.example.smarthomedeviceapplication

import android.os.Bundle
import android.widget.TextView
import androidx.activity.ComponentActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MainActivity2 : ComponentActivity() {
    private val databaseReference: DatabaseReference by lazy {
        FirebaseDatabase.getInstance("https://smart-home-31620-default-rtdb.firebaseio.com").reference
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_temperature_view)

        val textView: TextView = findViewById(R.id.textView)
        observeTemperature(textView)
    }

    private fun observeTemperature(textView: TextView) {
        val temperatureRef = databaseReference.child("sensors").child("temperature").child("value")

        temperatureRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val value = snapshot.getValue(String::class.java)
                textView.text = value ?: "N/A"
                textView.text = "$value°C" // Adding "°C" at the end
            }

            override fun onCancelled(error: DatabaseError) {
                textView.text = "Failed to fetch temperature"
            }
        })
    }
}
