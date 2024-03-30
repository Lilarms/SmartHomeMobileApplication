package com.example.smarthomedeviceapplication

import android.os.Bundle
import android.widget.Switch
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

    private val switchDatabaseReference: DatabaseReference by lazy {
        FirebaseDatabase.getInstance("https://smart-home-31620-default-rtdb.firebaseio.com").reference.child("control").child("blinds_auto")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lighting_view)

        val toggleButton = findViewById<ToggleButton>(R.id.toggleButton)
        val switch1 = findViewById<Switch>(R.id.switch1)

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
        switchDatabaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val value = dataSnapshot.getValue(Boolean::class.java) ?: false
                switch1.isChecked = value
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@MainActivity4, "Failed to read value for switch.", Toast.LENGTH_SHORT).show()
            }
        })

        // Add OnCheckedChangeListener to update Firebase value based on switch state
        switch1.setOnCheckedChangeListener { _, isChecked ->
            switchDatabaseReference.setValue(isChecked)
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
