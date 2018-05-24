package com.bogdan.messenger.myMessenger.implementingFiltersExample;

import java.io.IOException;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.ext.Provider;

/*https://www.youtube.com/watch?v=nDW6DQSNrIY&list=PLqq-6Pq4lTTY40IcG584ynNqibMc1heIa&index=21
 * Acesta este un exemplu separat
 * avem 2 tipuri de filtere
 * 
 *  -> ContainerRequestFilter ---->  |Resource|
 * 								     |        |
 *   <- CointainerResponseFilter --->|___     |
 *   
 *  ContainerRequestFilter = filter ce se executa
 *  	inaintea un request
 *   ContainerResponseFilter =  dupa ce raspunsul este
 *   pregatit si trimit catre client
 *   
 *   In acest exemplu vreau sa schimb raspunsul,
 *   sa aibe un header.
 *   
 *   Pt asta implementam interfata ContainerResponseFilter
 */

/*
 * Asta este un filter separat aplicat asupra oricarui request
 * chiar daca nu exista
 */
@Provider
public class PoweredByResponseFilter implements ContainerResponseFilter {

	// aici putem modifica raspunsul
	@Override
	public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext)
			throws IOException {
		
		responseContext.getHeaders().add("X-Powered-By", "Bogdan Ceachi");
	}

}

/*
 * Daca dupa treaba asta faci un request sa zicem:
 * http://localhost:8080/myMessenger/webapi/messages
 * si intri la headers,
 * ai sa vezi acolo:
 * x-powered-by →Bogdan Ceachi
 * */
