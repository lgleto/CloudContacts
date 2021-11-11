package ipca.example.clientcontacts

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ListView
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    var clients = arrayListOf<Client>()
    var adapter : ClientsAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        adapter = ClientsAdapter()
        val listViewClients = findViewById<ListView>(R.id.listViewClients)
        listViewClients.adapter = adapter

        Backend.fetchAllClients {
            clients = it as ArrayList<Client>
            adapter?.notifyDataSetChanged()
        }
    }

    inner class ClientsAdapter : BaseAdapter(){
        override fun getCount(): Int {
            return clients.size
        }

        override fun getItem(position: Int): Any {
            return clients[position]
        }

        override fun getItemId(position: Int): Long {
            return 0
        }

        override fun getView(position: Int, parent: View?, viewGroup: ViewGroup?): View {
            val rowView = layoutInflater.inflate(R.layout.row_client,viewGroup, false)
            val textViewName = rowView.findViewById<TextView>(R.id.textViewName)
            val textViewPhone = rowView.findViewById<TextView>(R.id.textViewPhone)
            textViewName.text = clients[position].name
            textViewPhone.text = clients[position].phone
            return rowView
        }

    }
}