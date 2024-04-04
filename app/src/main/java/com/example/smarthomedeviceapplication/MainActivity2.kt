package com.example.smarthomedeviceapplication

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
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

        val textViewCurrentTemp: TextView = findViewById(R.id.textViewCurrentTemp)
        val textViewDesiredTemp: TextView = findViewById(R.id.textViewDesiredTemp)
        val btnPlus: Button = findViewById(R.id.btnPlus)
        val btnMinus: Button = findViewById(R.id.btnMinus)
        val humidityTextView: TextView = findViewById(R.id.text_humidity)

        observeCurrentTemperature(textViewCurrentTemp)
        observeDesiredTemperature(textViewDesiredTemp)
        observeHumidity(humidityTextView)
        setupButtonClickListeners(textViewDesiredTemp, btnPlus, btnMinus)
    }

    private fun observeDesiredTemperature(textViewDesiredTemp: TextView) {
        val temperatureRef = databaseReference.child("control").child("desired_temp").child("value")

        temperatureRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val value = snapshot.getValue(String::class.java)?.toIntOrNull() ?: 0
                textViewDesiredTemp.text = "$value°C"
            }

            override fun onCancelled(error: DatabaseError) {
                textViewDesiredTemp.text = "Failed to fetch temperature"
            }
        })
    }

    private fun observeCurrentTemperature(textViewCurrentTemp: TextView) {
        val temperatureRef = databaseReference.child("sensors").child("temperature").child("value")

        temperatureRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val value = snapshot.getValue(String::class.java)?.toIntOrNull() ?: 0
                textViewCurrentTemp.text = "$value°C"
            }

            override fun onCancelled(error: DatabaseError) {
                textViewCurrentTemp.text = "Failed to fetch temperature"
            }
        })
    }

    private fun observeHumidity(humidityTextView: TextView) {
        val humidityReference = databaseReference.child("sensors").child("arduino1").child("humidity").child("value")

        humidityReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val value = dataSnapshot.getValue(String::class.java) ?: "N/A"
                humidityTextView.text = "Current Humidity: $value"
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@MainActivity2, "Failed to read humidity value.", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun setupButtonClickListeners(
        textViewDesiredTemp: TextView,
        btnPlus: Button,
        btnMinus: Button
    ) {
        btnPlus.setOnClickListener {
            incrementTemperature(textViewDesiredTemp)
        }

        btnMinus.setOnClickListener {
            decrementTemperature(textViewDesiredTemp)
        }
    }

    private fun incrementTemperature(textViewDesiredTemp: TextView) {
        val currentTemperature = textViewDesiredTemp.text.toString().replace("°C", "").toInt()
        val newTemperature = currentTemperature + 1
        textViewDesiredTemp.text = "$newTemperature°C"
        updateTemperatureInDatabase(newTemperature.toString())
    }

    private fun decrementTemperature(textViewDesiredTemp: TextView) {
        val currentTemperature = textViewDesiredTemp.text.toString().replace("°C", "").toInt()
        val newTemperature = currentTemperature - 1
        textViewDesiredTemp.text = "$newTemperature°C"
        updateTemperatureInDatabase(newTemperature.toString())
    }

    private fun updateTemperatureInDatabase(temperature: String) {
        databaseReference.child("control").child("desired_temp").child("value").setValue(temperature)
    }
}
