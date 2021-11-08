using System;
using System.Collections.Generic;
using System.Linq;
using CloudContacts.models;
using Microsoft.AspNetCore.Mvc;
using Microsoft.Extensions.Logging;

namespace CloudContacts.Controllers
{
    [ApiController]
    [Route("[controller]")]
    public class ClientController  : ControllerBase
    {
        
        
        // GET /Client     
        [HttpGet]
        public IEnumerable<Client> Get()
        {
            return  Client.GetAllItems();
        }

        // GET /Client/{id}
        [HttpGet("{id}")]
        public Client Get(string id)
        {
            return Client.GetItem(id);
        }
    
      
        // POST  /Client
        [HttpPost]
        public string Post([FromBody]Newtonsoft.Json.Linq.JObject value)
        {
            Client client = Client.ParseJson(value);
            return Client.Create(client);
        }

        // PUT  /Client/5
        [HttpPut("{id}")]
        public void Put(string id, [FromBody]Newtonsoft.Json.Linq.JObject value)
        {
            Client client = Client.ParseJson(value);
            Client.Update(client,id);
        }
        
        // DELETE  /Client
        [HttpDelete("{id}")]
        public void Delete(string id)
        {
            Client.Delete(id);
        }


        
        
    }
}