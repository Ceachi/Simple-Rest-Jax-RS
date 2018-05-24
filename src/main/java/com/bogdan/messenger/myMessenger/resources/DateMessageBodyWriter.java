package com.bogdan.messenger.myMessenger.resources;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.Date;

import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;

/*
 * Ne facem propriul nostru MessageBodyWriter pt un Date object.
 * Cand JAx vrea sa converteasca ceva, se uita la @Providerii care ii are
 * 
 */

// deci daca tu vrei vreodata sa intorci un Date in Text_Plain
// Jax vine in Providerul asta
@Provider
@Produces(MediaType.TEXT_PLAIN)
public class DateMessageBodyWriter implements MessageBodyWriter<Date> {

	@Override
	public boolean isWriteable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
		// trb sa verificam daca type este Date aici	
		return Date.class.isAssignableFrom(type);
	}

	@Override
	public void writeTo(Date date, Class<?> type, 
						Type genericType, 
						Annotation[] annotations, 
						MediaType mediaType,
						MultivaluedMap<String, Object> httpHeaders, 
						OutputStream out) throws IOException, WebApplicationException {
		
		out.write(date.toString().getBytes());
	}	

}

