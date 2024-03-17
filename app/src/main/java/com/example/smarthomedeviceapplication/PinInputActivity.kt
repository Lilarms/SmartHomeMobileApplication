import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.ComponentActivity
import com.example.smarthomedeviceapplication.MainActivity
import com.example.smarthomedeviceapplication.R

class PinInputActivity : ComponentActivity() { // Changed to AppCompatActivity
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pin_input)

        val pinDigit1 = findViewById<EditText>(R.id.pin_digit1)
        val pinDigit2 = findViewById<EditText>(R.id.pin_digit2)
        val pinDigit3 = findViewById<EditText>(R.id.pin_digit3)
        val pinDigit4 = findViewById<EditText>(R.id.pin_digit4)

        val submitButton = findViewById<Button>(R.id.submit_button)
        submitButton.setOnClickListener {
            val enteredPin = "${pinDigit1.text}${pinDigit2.text}${pinDigit3.text}${pinDigit4.text}"
            if (enteredPin == "1234") { // Change to your desired PIN
                startActivity(Intent(this, MainActivity::class.java))
                finish() // Finish this activity to prevent going back to it when pressing back
            } else {
                // Display error message or handle incorrect PIN
                Toast.makeText(this, "Incorrect PIN", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
