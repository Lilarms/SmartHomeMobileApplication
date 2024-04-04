package com.example.smarthomedeviceapplication

import android.os.Bundle
import android.widget.Switch
import android.widget.TextView
import android.widget.Toast
import android.widget.ToggleButton
import androidx.activity.ComponentActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MainActivity4 : ComponentActivity() {
    private val toggleDatabaseReference: DatabaseReference by lazy {
        FirebaseDatabase.getInstance("https://smart-home-31620-default-rtdb.firebaseio.com").reference.child("control").child("blinds_open")
    }

    private val modeDatabaseReference: DatabaseReference by lazy {
        FirebaseDatabase.getInstance("https://smart-home-31620-default-rtdb.firebaseio.com").reference.child("control").child("mode")
    }

    private val blindsStatusDatabaseReference: DatabaseReference by lazy {
        FirebaseDatabase.getInstance("https://smart-home-31620-default-rtdb.firebaseio.com").reference.child("sensors").child("arduino2").child("blinds_status").child("blinds")
    }

    private val lightLevelDatabaseReference: DatabaseReference by lazy {
        FirebaseDatabase.getInstance("https://smart-home-31620-default-rtdb.firebaseio.com").reference.child("sensors").child("arduino2").child("blinds_status").child("lightLevel")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lighting_view)

        val toggleButton = findViewById<ToggleButton>(R.id.toggleButton)
        val switch1 = findViewById<Switch>(R.id.switch1)
        val blindsStatusTextView = findViewById<TextView>(R.id.blindsStatus)
        val lightLevelTextView = findViewById<TextView>(R.id.lightLevel1)

        // Add ValueEventListener to update the toggle state based on Firebase value
        toggleDatabaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val value = dataSnapshot.getValue(Boolean::class.java) ?: false
                toggleButton.isChecked = value
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@MainActivity4, "Failed to read value for toggle.", Toast.LENGTH_SHORT).show()
            }
        })

        // Add OnClickListener to update Firebase value based on toggle state
        toggleButton.setOnClickListener {
            val newValue = toggleButton.isChecked
            toggleDatabaseReference.setValue(newValue)
        }

        // Add ValueEventListener to update the switch state based on Firebase value
        modeDatabaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val value = dataSnapshot.getValue(String::class.java) ?: "manual"
                switch1.isChecked = value == "automatic"
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@MainActivity4, "Failed to read value for mode.", Toast.LENGTH_SHORT).show()
            }
        })

        // Add ValueEventListener to update the blinds status TextView based on Firebase value
        blindsStatusDatabaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val value = dataSnapshot.getValue(String::class.java) ?: "Unknown"
                blindsStatusTextView.text = value
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@MainActivity4, "Failed to read value for blinds.", Toast.LENGTH_SHORT).show()
            }
        })

        // Add ValueEventListener to update the light level TextView based on Firebase value
        lightLevelDatabaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val value = dataSnapshot.getValue(Int::class.java) ?: 0
                lightLevelTextView.text = value.toString()
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@MainActivity4, "Failed to read value for light level.", Toast.LENGTH_SHORT).show()
            }
        })

        // Add OnCheckedChangeListener to update Firebase value based on switch state
        switch1.setOnCheckedChangeListener { _, isChecked ->
            val mode = if (isChecked) "automatic" else "manual"
            modeDatabaseReference.setValue(mode)
        }

        toggleButton.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                // Do something when toggle is on
                System.out.println("blinds are opening")
            } else {
                // Do something when toggle is off
                System.out.println("blinds are closing")
            }
        }
    }
}
