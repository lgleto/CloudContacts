package ipca.example.clientcontacts

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val textViewHello = findViewById<TextView>(R.id.textViewHello)

        Backend.fetchClient("23r23", { textViewHello.text = it.name })

        textViewHello.text = "A carregar ..."
    }

}