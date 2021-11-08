using System;
using System.Data;
using Microsoft.VisualBasic.CompilerServices;
using MySql.Data.MySqlClient;

namespace CloudContacts
{
    public class Database
    {
        MySqlConnection conn;

        public Database()
        {
        }

        public Boolean OpenConnection()
        {
            string myConnectionString;
            myConnectionString = "server=127.0.0.1;uid=root;" +
                                 "pwd=12345678;database=cloud_clients_db";
            try
            {
                conn = new MySql.Data.MySqlClient.MySqlConnection();
                conn.ConnectionString = myConnectionString;
                conn.Open();
                return true;
            }
            catch (MySql.Data.MySqlClient.MySqlException ex)
            {
                System.Console.WriteLine(ex.Message);
            }
            return false;
        }

        public void CloseConnection()
        {
            conn.Close();
        }

        public MySqlDataReader DbQuery(String query)
        {
            try
            {
         
                MySqlCommand cmd = new MySqlCommand(query, conn);
                MySqlDataReader rdr = cmd.ExecuteReader();
                
                return rdr;
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.ToString());
            }

    
            return null;
        }

        public int DbNonQuery(String query)
        {
            try {
                MySqlCommand cmd = new MySqlCommand(query, conn);
                return cmd.ExecuteNonQuery();
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.ToString());
            }

            return 0;
        }
        
    }

}