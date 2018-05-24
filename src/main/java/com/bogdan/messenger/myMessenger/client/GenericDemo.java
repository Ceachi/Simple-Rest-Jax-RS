package com.bogdan.messenger.myMessenger.client;

/*
 * https://www.youtube.com/watch?v=fxDFpVLUDFY&list=PLqq-6Pq4lTTY40IcG584ynNqibMc1heIa&index=19
 */

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import com.bogdan.messenger.myMessenger.model.*;
public class GenericDemo {
	public static void main(String args[]) {
	
		Client client = ClientBuilder.newClient();
		
		List<Message> messages = client.target("http://localhost:8080/myMessenger/webapi")
									 .path("messages")
									// .queryParam("year", 2015)
									 .request(MediaType.APPLICATION_JSON)
									 .get(new GenericType<List<Message>>() {});
		System.out.println(messages.toString());
	}
}
