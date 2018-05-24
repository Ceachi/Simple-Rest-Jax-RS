package com.bogdan.messenger.myMessenger.client;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.bogdan.messenger.myMessenger.model.Message;

/*
 * Client = interfata
 */
public class RestApiClient2 {
	
	public static void main(String args[]) {
		
		Client client = ClientBuilder.newClient();
		
		WebTarget baseTarget = client.target("http://localhost:8080/myMessenger/webapi/");
		WebTarget messagesTarget = baseTarget.path("messages");
		
		WebTarget singleMessageTarget  = messagesTarget.path("{messageId}");
		
		//GET
		Message message1 =  singleMessageTarget
							.resolveTemplate("messageId", "1")
							.request(MediaType.APPLICATION_JSON)
						    .get(Message.class);
		
		Message message2 =  singleMessageTarget
				.resolveTemplate("messageId", "2")
				.request(MediaType.APPLICATION_JSON)
			    .get(Message.class);
		
		
		
		System.out.println(message1.getMessage());
		System.out.println(message2.getMessage());
		
		//POST
		
		Message newMessage = new Message(4, "My new message from Jax-RS client","bogdan");
		Response postResponse = messagesTarget.request()
					  .post(Entity.json(newMessage));
		
		if(postResponse.getStatus() != 201) {
			System.out.println("Error");
		}
		System.out.println(postResponse.readEntity(Message.class).getMessage());
		
	}
	
}
