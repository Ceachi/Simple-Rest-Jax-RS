package com.bogdan.messenger.myMessenger.implementingRESTauthorizationExample;

import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.StringTokenizer;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

@Provider
public class SecurityFilter implements ContainerRequestFilter {
	private static final String AUTHORIZATION_HEADER  = "Authorization";
	private static final String AUTHORIZATION_HEADER_PREFIX = "Basic";
	private static final String SECURED_URL_PREFIXT  = "secured";
	
	
	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {
		List<String> authHeader = requestContext.getHeaders().get(AUTHORIZATION_HEADER);
		if(requestContext.getUriInfo().getPath().contains(SECURED_URL_PREFIXT)) {
			if(authHeader != null && authHeader.size() > 0) {
				String authToken = authHeader.get(0);
				authToken = authToken.replaceFirst(AUTHORIZATION_HEADER_PREFIX, "");
				byte[] decodedByte= Base64.getDecoder().decode(authToken);
				String decodeString = decodedByte.toString();
				
				StringTokenizer tokenizer = new StringTokenizer(decodeString, ":");
				
				String username = tokenizer.nextToken();
				String password = tokenizer.nextToken();
				
				if("user".equals(username) && "password".equals(password)) {
					return; // now JAX-RS is free to proceed the request
				}
				
				Response unauthorizedStatus = Response
											  .status(Response.Status.UNAUTHORIZED)
											  .entity("User cannot access the resource")
											  .build();
				// hey jax abord this request and pass a response:
				requestContext.abortWith(unauthorizedStatus);
			}
		}
	}

}
