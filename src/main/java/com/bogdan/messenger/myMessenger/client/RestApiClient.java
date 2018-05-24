package com.bogdan.messenger.myMessenger.client;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.bogdan.messenger.myMessenger.model.Message;

/*
 * Client = interfata
 */
public class RestApiClient {
	
	public static void main(String args[]) {
		/*//Response response = client.target("http://localhost:8080/myMessenger/webapi/messages/2")
			//					  .request(MediaType.APPLICATION_JSON).get();
		 * 
		 * WebTarget target = client.target("http://localhost:8080/myMessenger/webapi/messages/2");
		
		Builder builder = target.request();
		Response response = builder.get();
									//.post, .delete
		 */
		
		
		
		// ii spunem sa ne dea un obiect client
		Client client = ClientBuilder.newClient();
		
		// trebuie sa ii spunem catre cine sa pointeze si ii spunem sa faca si eun request	
		Message message = client.target("http://localhost:8080/myMessenger/webapi/messages/2")
								  .request(MediaType.APPLICATION_JSON).get(Message.class);
		//get(x) x = tipul raspunsului tau
		// after response we need to unWrapp cu readEntity(clasa ce o primesti)
		// in exemplu asta il avem in acelasi proiect
	//	Message message = response.readEntity(Message.class);
		
		
		
		System.out.println(message.getMessage());
	}
	
}
