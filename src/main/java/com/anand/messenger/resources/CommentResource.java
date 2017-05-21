package com.anand.messenger.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import com.anand.messenger.model.Message;

@Path("/")
public class CommentResource {
	
	@GET
	@Path("/{commentId}")
	public String test(@PathParam("commentId") long commentId, @PathParam("messageId") long messageId) {
    	
		return "test reached to subresource. and the messageId is: " + messageId + "Comment Id: " + commentId ;
    }

}
