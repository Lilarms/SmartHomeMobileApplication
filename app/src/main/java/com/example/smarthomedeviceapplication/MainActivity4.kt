package com.example.smarthomedeviceapplication

import android.os.Bundle
import android.widget.Toast
import android.widget.ToggleButton
import androidx.activity.ComponentActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MainActivity4 : ComponentActivity() {
    private val databaseReference: DatabaseReference by lazy {
        FirebaseDatabase.getInstance("https://smart-home-31620-default-rtdb.firebaseio.com").reference.child("control").child("blinds_open")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lighting_view)

        val toggleButton = findViewById<ToggleButton>(R.id.toggleButton)

        // Add ValueEventListener to update the toggle state based on Firebase value
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val value = dataSnapshot.getValue(Boolean::class.java) ?: false
                toggleButton.isChecked = value
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@MainActivity4, "Failed to read value.", Toast.LENGTH_SHORT).show()
            }
        })

        // Add OnClickListener to update Firebase value based on toggle state
        toggleButton.setOnClickListener {
            val newValue = toggleButton.isChecked
            databaseReference.setValue(newValue)
        }

        toggleButton.setOnCheckedChangeListener { buttonView, isChecked ->
            // isChecked is true if the toggle is in the "on" state, false otherwise
            if (isChecked) {
                // Do something when toggle is on
                System.out.println("blinds are opening");
            } else {
                // Do something when toggle is off
                System.out.println("blinds are closing");
            }
        }
    }
}
