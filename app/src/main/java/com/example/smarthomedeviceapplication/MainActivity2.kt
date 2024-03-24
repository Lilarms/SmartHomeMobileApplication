package com.example.smarthomedeviceapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
//import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.firebase.database.*

class MainActivity2 : ComponentActivity() {
    private val databaseReference: DatabaseReference by lazy {
        FirebaseDatabase.getInstance("https://smart-home-31620-default-rtdb.firebaseio.com").reference
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val temperature = remember { mutableStateOf("Loading...") }
            observeTemperature(temperature)
            TemperatureView(temperature.value)
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
    }
}

@Composable
fun TemperatureView(temperature: String) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text("Temperature: $temperature")
    }
}
