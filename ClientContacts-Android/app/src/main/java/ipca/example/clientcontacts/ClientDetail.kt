package ipca.example.clientcontacts

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import java.util.*

class ClientDetail : AppCompatActivity() {

    lateinit var editTextName :EditText
    lateinit var editTextTextAddress :EditText
    lateinit var editTextEmail :EditText
    lateinit var editTextPhone :EditText

    var client : Client? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_client_detail)

        val clientId = intent.getStringExtra("client_id")
        clientId?.let {
            Backend.fetchClient(it){
                client = it

                editTextName.setText(it.name)
                editTextTextAddress.setText(it.address)
                editTextEmail.setText(it.email)
                editTextPhone.setText(it.phone)
            }
        }


        editTextName        = findViewById<EditText>(R.id.editTextName)
        editTextTextAddress = findViewById<EditText>(R.id.editTextTextAddress)
        editTextEmail       = findViewById<EditText>(R.id.editTextEmail)
        editTextPhone       = findViewById<EditText>(R.id.editTextPhone)



        val buttonSave = findViewById<Button>(R.id.buttonSave)
        buttonSave.setOnClickListener {

            client?.let{
                it.name = editTextName.text.toString()
                it.address = editTextTextAddress.text.toString()
                it.email = editTextEmail.text.toString()
                it.phone = editTextPhone.text.toString()

                Backend.updateClient(it){
                    if (it){
                        finish()
                    }else{
                        Toast.makeText(this@ClientDetail, "Erro de escrito no servidor", Toast.LENGTH_LONG).show()
                    }
                }
            }?: run {
                val client = Client()
                client.name = editTextName.text.toString()
                client.address = editTextTextAddress.text.toString()
                client.email = editTextEmail.text.toString()
                client.phone = editTextPhone.text.toString()
                client.id = UUID.randomUUID().toString()

                Backend.createClient(client){
                    if (it){
                        finish()
                    }else{
                        Toast.makeText(this@ClientDetail, "Erro de escrito no servidor", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }

        val buttonDelete = findViewById<Button>(R.id.buttonDelete)
        buttonDelete.setOnClickListener {
            client?.id?.let{
                Backend.deleteClient(it){
                    if (it){
                        finish()
                    }else{
                        Toast.makeText(this@ClientDetail, "Erro de escrito no servidor", Toast.LENGTH_LONG).show()
                    }
                }
            }

        }

        if (clientId == null){
            buttonDelete.visibility = View.GONE
        }


    }

}