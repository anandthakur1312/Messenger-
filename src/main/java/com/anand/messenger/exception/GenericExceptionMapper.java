package com.anand.messenger.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.anand.messenger.model.ErrorMessage;

//Commenting @Provider to disable this catch all exveptionmapper handler class.
//@Provider
public class GenericExceptionMapper implements ExceptionMapper<Throwable> {

	@Override
	public Response toResponse(Throwable ex) {
		ErrorMessage errorMessage = new ErrorMessage(ex.getMessage(), "500", "google.com");
		return Response.status(Status.INTERNAL_SERVER_ERROR)
						.entity(errorMessage)
						.build();
	}

}
