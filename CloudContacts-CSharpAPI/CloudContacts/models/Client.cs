using System;
using System.Collections.Generic;
using MySql.Data.MySqlClient;

namespace CloudContacts.models
{
    public class Client
    {

        public string Id       { get; set; }
        public string Name     { get; set; }
        public string Address  { get; set; }
        public string Email    { get; set; }
        public string Phone    { get; set; }


        public Client()
        {
            
        }

        public static Client ParseJson(Newtonsoft.Json.Linq.JObject value)
        {

            Client costumer = new Client();
            
            if (value.Property("id"      ) != null )costumer.Id         = (string) value["id"     ];
            if (value.Property("name"    ) != null )costumer.Name       = (string) value["name"   ];
            if (value.Property("address" ) != null )costumer.Address    = (string) value["address"];
            if (value.Property("email"   ) != null )costumer.Email      = (string) value["email"  ];
            if (value.Property("phone"   ) != null )costumer.Phone      = (string) value["phone"  ];
            
            return costumer;
        }
        
        
        public static List<Client> GetAllItems()
        {
            List<Client> clients = new List<Client>();

            Database dbc = new Database();
            if (dbc.OpenConnection())
            {
                string strSQL = "SELECT * FROM users";
                MySqlDataReader reader = dbc.DbQuery(strSQL);
                while (reader.HasRows)
                {
                    while (reader.Read())
                    {
                        Client client = new Client();

                        if (reader["id"      ] is string) client.Id       = (string) reader["id"      ];
                        if (reader["name"    ] is string) client.Name     = (string) reader["name"    ];
                        if (reader["address" ] is string) client.Address  = (string) reader["address" ];
                        if (reader["email"   ] is string) client.Email    = (string) reader["email"   ];
                        if (reader["phone"   ] is string) client.Phone    = (string) reader["phone"   ];

                        clients.Add(client);
                    }
                    reader.NextResult();
                }
                dbc.CloseConnection();
            }
            return clients;
        }


        public static Client GetItem(string codItem)
        {
            Client client = new Client();
            Database dbc = new Database();
            if (dbc.OpenConnection())
            {
                string strSQL = "SELECT * FROM users WHERE users.id = '" + codItem + "'";
                MySqlDataReader reader = dbc.DbQuery(strSQL);
                while (reader.Read())
                    {
                        if (reader["id"      ] is string) client.Id       = (string) reader["id"      ];
                        if (reader["name"    ] is string) client.Name     = (string) reader["name"    ];
                        if (reader["address" ] is string) client.Address  = (string) reader["address" ];
                        if (reader["email"   ] is string) client.Email    = (string) reader["email"   ];
                        if (reader["phone"   ] is string) client.Phone    = (string) reader["phone"   ];
                    }
                dbc.CloseConnection();
            }
            
            return client;
        }
   
        public static String Create(Client client)
        {

            Database dbc = new Database();
            if (dbc.OpenConnection())
            {
                String strQuery =
                    "INSERT into users (id, name, address, email, phone) VALUES ('" +
                    client.Id       + "', '" +
                    client.Name     + "', '" +
                    client.Address  + "', '" +
                    client.Email    + "', '" +
                    client.Phone    +
                    "')";
                
                int value = dbc.DbNonQuery(strQuery);
                
                dbc.CloseConnection();
                if (value > 0)
                {
                    return "{ \"status\" :\"ok\" }";
                }
                else
                {
                    return "{ \"status\" :\"error\" }";
                }
            }
            
            return "{ \"status\" :\"error\" }";;
        }
        
        public static String Update(Client client, String id)
        {

            Database dbc = new Database();
            if (dbc.OpenConnection())
            {
                String strQuery =
                    "UPDATE users SET " +
                    "name    = '" +client.Name     + "', " +
                    "address = '" +client.Address  + "', " +
                    "email   = '" +client.Email    + "', " +
                    "phone   = '" +client.Phone    + "' " +
                    "WHERE id ='"+id+"'";
                
                int value = dbc.DbNonQuery(strQuery);
                
                dbc.CloseConnection();
                if (value > 0)
                {
                    return "{ \"status\" :\"ok\" }";
                }
                else
                {
                    return "{ \"status\" :\"error\" }";
                }
            }
            
            return "{ \"status\" :\"error\" }";;
        }
        
        public static String Delete(String id)
        {

            Database dbc = new Database();
            if (dbc.OpenConnection())
            {
                String strQuery =
                    "DELETE FROM users WHERE id ='"+id+"'";
                int value = dbc.DbNonQuery(strQuery);
                dbc.CloseConnection();
                if (value > 0)
                {
                    return "{ \"status\" :\"ok\" }";
                }
                else
                {
                    return "{ \"status\" :\"error\" }";
                }
            }
            
            return "{ \"status\" :\"error\" }";;
        }
        
    }
}