package com.example.smarthomedeviceapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.ComponentActivity

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnNext = findViewById<Button>(R.id.button4)
        btnNext.setOnClickListener {
            val intent = Intent(this, MainActivity2::class.java)
            startActivity(intent)
        }

        val btnNext1 = findViewById<Button>(R.id.button5)
        btnNext1.setOnClickListener {
            val intent2 = Intent(this, MainActivity3::class.java)
            startActivity(intent2)
        }

        val btnNext2 = findViewById<Button>(R.id.button6)
        btnNext2.setOnClickListener {
            val intent3 = Intent(this, MainActivity4::class.java)
            startActivity(intent3)
        }

        val btnNext8 = findViewById<Button>(R.id.button7)
        btnNext8.setOnClickListener {
            val intent8 = Intent(this, MainActivity5::class.java)
            startActivity(intent8)
        }



    }
}
