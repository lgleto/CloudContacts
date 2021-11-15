package ipca.example.clientcontacts

import org.json.JSONObject

class Client {

    var id      : String? = null
    var name    : String? = null
    var address : String? = null
    var email   : String? = null
    var phone   : String? = null

    fun toJson() : JSONObject{
        val jsonObject = JSONObject()
        jsonObject.put("id"     , id     )
        jsonObject.put("name"   , name   )
        jsonObject.put("address", address)
        jsonObject.put("email"  , email  )
        jsonObject.put("phone"  , phone  )

        return jsonObject
    }

    companion object{
        fun fromJson( jsonObject: JSONObject) : Client {
            val client = Client()
            client.id      = jsonObject.getString("id"      )
            client.name    = jsonObject.getString("name"    )
            client.address = jsonObject.getString("address" )
            client.email   = jsonObject.getString("email"   )
            client.phone   = jsonObject.getString("phone"   )

            return client
        }
    }
}