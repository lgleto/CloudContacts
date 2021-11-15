package ipca.example.clientcontacts

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONArray
import org.json.JSONObject

object Backend {

    const val BASE_API = "http://192.168.33.189:5001/"

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

    fun createClient(client: Client, callback: (Boolean)->Unit ) {
        GlobalScope.launch (Dispatchers.IO) {

            val postBody = client.toJson().toString()
            val okHttpClient = OkHttpClient()
            val request = Request.Builder()
                .url(BASE_API + "Client")
                .post(postBody.toRequestBody("application/json; charset=utf-8".toMediaType()))
                .build()
            okHttpClient.newCall(request).execute().use { response ->
                if (response.isSuccessful){
                    val result = response.body!!.string()
                    Log.d("clientcontacts", result)
                    GlobalScope.launch (Dispatchers.Main){
                        callback.invoke(true)
                    }
                }else {
                    GlobalScope.launch (Dispatchers.Main){
                        callback.invoke(false)
                    }
                }
            }
        }
    }

    fun updateClient(client: Client, callback: (Boolean)->Unit ) {
        GlobalScope.launch (Dispatchers.IO) {

            val postBody = client.toJson().toString()
            val okHttpClient = OkHttpClient()
            val request = Request.Builder()
                .url(BASE_API + "Client/${client.id}")
                .put(postBody.toRequestBody("application/json; charset=utf-8".toMediaType()))
                .build()
            okHttpClient.newCall(request).execute().use { response ->
                if (response.isSuccessful){
                    val result = response.body!!.string()
                    Log.d("clientcontacts", result)
                    GlobalScope.launch (Dispatchers.Main){
                        callback.invoke(true)
                    }
                }else {
                    GlobalScope.launch (Dispatchers.Main){
                        callback.invoke(false)
                    }
                }
            }
        }
    }

    fun deleteClient(clientID: String, callback: (Boolean)->Unit ) {
        GlobalScope.launch (Dispatchers.IO) {

            val okHttpClient = OkHttpClient()
            val request = Request.Builder()
                .url(BASE_API + "Client/$clientID")
                .delete()
                .build()
            okHttpClient.newCall(request).execute().use { response ->
                if (response.isSuccessful){
                    val result = response.body!!.string()
                    Log.d("clientcontacts", result)
                    GlobalScope.launch (Dispatchers.Main){
                        callback.invoke(true)
                    }
                }else {
                    GlobalScope.launch (Dispatchers.Main){
                        callback.invoke(false)
                    }
                }
            }
        }
    }
}