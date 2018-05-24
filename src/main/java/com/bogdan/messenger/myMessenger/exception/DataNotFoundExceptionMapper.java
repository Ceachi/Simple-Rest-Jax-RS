package com.bogdan.messenger.myMessenger.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.bogdan.messenger.myMessenger.model.ErrorMessage;

/*
 * ExceptionMapper este de tip generic
 * trebuie sa ii pui Exceptia catre care Mapeaza
 */

// clasa trebuie notata cu @Provider
// aceasta inregistreaza clasa in Jax-RS ca sa stie de ea
// un fel de equal

@Provider
public class DataNotFoundExceptionMapper implements ExceptionMapper<DataNotFoundException> {

	@Override
	public Response toResponse(DataNotFoundException ex) {
		// create your custom response here: acum o sa dea err 404, in loc de 500
		ErrorMessage errorMessage = new ErrorMessage(ex.getMessage(), 404, "http://javabrains.koushik.org");
		return Response.status(Status.NOT_FOUND)
				.entity(errorMessage)
				.build();
	}

}
