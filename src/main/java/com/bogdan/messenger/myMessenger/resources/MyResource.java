package com.bogdan.messenger.myMessenger.resources;

import java.util.Calendar;
import java.util.Date;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/testDate")

public class MyResource {

	@GET
	// alegem care din mediatype create sa produca
	// avem MessageBodyWriter pt conversii, pt fiecare
	@Produces(value = {MediaType.TEXT_PLAIN, "text/shortdate"})
	public Date testMethod() {
		return Calendar.getInstance().getTime();
	}
}
