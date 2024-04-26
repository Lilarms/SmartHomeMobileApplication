package com.example.smarthomedeviceapplication

import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import android.widget.ToggleButton
import androidx.activity.ComponentActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MainActivity3 : ComponentActivity() {
    private val databaseReference: DatabaseReference by lazy {
        FirebaseDatabase.getInstance("https://smart-home-new-4cf7a-default-rtdb.firebaseio.com/").reference
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_irrigation_view)

        val irrigationToggleButton = findViewById<ToggleButton>(R.id.irrigation_automation_button)
        val waterPumpToggleButton = findViewById<ToggleButton>(R.id.water_pump_on_button)

        val soilMoistureTextView = findViewById<TextView>(R.id.text_moisture_level)

        // Add ValueEventListener to update the toggle state based on Firebase value for irrigation
        val irrigationReference = databaseReference.child("control").child("irrigation_automation")
        irrigationReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val value = dataSnapshot.getValue(Boolean::class.java) ?: false
                irrigationToggleButton.isChecked = value
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@MainActivity3, "Failed to read irrigation value.", Toast.LENGTH_SHORT).show()
            }
        })

        // Add ValueEventListener to update the toggle state based on Firebase value for water pump
        val waterPumpReference = databaseReference.child("control").child("irrigation_active")
        waterPumpReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val value = dataSnapshot.getValue(Boolean::class.java) ?: false
                waterPumpToggleButton.isChecked = value
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@MainActivity3, "Failed to read water pump value.", Toast.LENGTH_SHORT).show()
            }
        })

        // Add ValueEventListener to update the humidity value

        // Add ValueEventListener to update the soil moisture value
        val soilMoistureReference = databaseReference.child("sensors").child("arduino1").child("soilMoisture").child("value")
        soilMoistureReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val value = dataSnapshot.getValue(String::class.java) ?: "N/A"
                soilMoistureTextView.text = "Current Soil Moisture: $value"
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@MainActivity3, "Failed to read soil moisture value.", Toast.LENGTH_SHORT).show()
            }
        })

        // Add OnClickListener to update Firebase value based on toggle state for irrigation
        irrigationToggleButton.setOnClickListener {
            val newValue = irrigationToggleButton.isChecked
            irrigationReference.setValue(newValue)
        }

        // Add OnClickListener to update Firebase value based on toggle state for water pump
        waterPumpToggleButton.setOnClickListener {
            val newValue = waterPumpToggleButton.isChecked
            waterPumpReference.setValue(newValue)
        }
    }
}
