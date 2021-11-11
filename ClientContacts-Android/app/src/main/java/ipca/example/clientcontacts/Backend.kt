package ipca.example.clientcontacts

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONArray
import org.json.JSONObject

object Backend {

    const val BASE_API = "http://172.20.10.3:5001/"

    fun fetchAllClients( callback: (List<Client>)->Unit ) {
        GlobalScope.launch (Dispatchers.IO){
            val client = OkHttpClient()
            val request = Request.Builder()
                .url(BASE_API+ "Client")
                .build()
            client.newCall(request).execute().use { response ->
                if (response.isSuccessful){
                    val result = response.body!!.string()
                    val clientJsonArray = JSONArray(result)
                    val clients = arrayListOf<Client>()
                    for( index in 0 until clientJsonArray.length()){
                        val client = Client.fromJson(clientJsonArray[index] as JSONObject)
                        clients.add(client)
                    }

                    GlobalScope.launch (Dispatchers.Main){
                        callback.invoke(clients)
                    }
                }
            }
        }
    }

    fun fetchClient(id:String, callback: (Client)->Unit ) {
        GlobalScope.launch (Dispatchers.IO){
            val client = OkHttpClient()
            val request = Request.Builder()
                .url(BASE_API+ "Client/$id")
                .build()
            client.newCall(request).execute().use { response ->
                if (response.isSuccessful){
                    val result = response.body!!.string()
                    Log.d("clientcontacts", result)
                    val client = Client.fromJson(JSONObject(result))
                    Log.d("clientcontacts", client.name!!)
                    GlobalScope.launch (Dispatchers.Main){
                        callback.invoke(client)
                    }
                }
            }
        }
    }
}