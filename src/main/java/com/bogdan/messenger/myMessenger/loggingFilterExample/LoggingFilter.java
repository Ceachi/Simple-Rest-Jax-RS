package com.bogdan.messenger.myMessenger.loggingFilterExample;

import java.io.IOException;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.ext.Provider;

/*
 * Exemplu de implementare pt filtere
 * 
 */
@Provider
public class LoggingFilter  implements ContainerResponseFilter, ContainerRequestFilter{

	@Override
	public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext)
			throws IOException {
		System.out.println("Response filter");
		System.out.println(responseContext.getHeaders());
	}

	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {
		System.out.println("Request filter : ");
		System.out.println("Headers : " + requestContext.getHeaders());
	}

}

// Afiseaza : la orice request
//Request filter : 
//Headers : {host=[localhost:8080], connection=[keep-alive], cache-control=[no-cache], content-type=[application/json], user-agent=[Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/66.0.3359.181 Safari/537.36], postman-token=[34987dcf-cfd1-9c65-1517-3e858afc4e11], accept=[*/*], accept-encoding=[gzip, deflate, br], accept-language=[ro-RO,ro;q=0.9,en-US;q=0.8,en;q=0.7]}
//Response filter
//{Content-Type=[application/json]}

