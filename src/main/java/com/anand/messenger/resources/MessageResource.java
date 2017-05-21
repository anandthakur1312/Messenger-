package com.anand.messenger.resources;

import java.net.URI;
import java.util.List;
import java.util.zip.DataFormatException;

import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import com.anand.messenger.model.Message;
import com.anand.messenger.service.MessageService;

@Path("/messages")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MessageResource {
	
	MessageService messageService = new MessageService();

    @GET
    
    /*We are using @BeanParam insted of below in the method argument
     * 
     * @QueryParam("year") int year;
	@QueryParam("start") int start;
	@QueryParam("size") int size;*/
    
    public List<Message> getMessages(@BeanParam MessageFilterBean messageFilterBean) {
    	
    	
    	
    	if(messageFilterBean.getYear()>0){
    		return messageService.getAllMessagesForYear(messageFilterBean.getYear());
    	}
    	
    	if(messageFilterBean.getStart() > 0 && messageFilterBean.getSize()>0){
    		return messageService.getAllMessagesPaginated(messageFilterBean.getStart(), messageFilterBean.getSize());
    	}
    	
    	return messageService.getAllMessages(); 
    }
    
    
    @GET
	@Path("/{messageId}")
	public Message getMessage(@PathParam("messageId") long messageId, @Context UriInfo uriInfo) {
    	
		Message message = messageService.getMessage(messageId);
		String uriForSelf = getUriForSelf(uriInfo, message);
		String uriForProfiles = getUriForProfile(uriInfo, message);
		String uriForComments = getUriForComments(uriInfo, message);
		message.addLink(uriForSelf, "rel");	
		message.addLink(uriForProfiles, "profile");
		message.addLink(uriForComments, "comments");
		return message;
    }

	private String getUriForSelf(UriInfo uriInfo, Message message) {
		String uriForSelf = uriInfo.getBaseUriBuilder()
						.path(MessageResource.class)
						.path(String.valueOf(message.getId()))
						.build()
						.toString(); 
		return uriForSelf;	
	}
	
	private String getUriForProfile(UriInfo uriInfo, Message message) {
		String uriForProfile = uriInfo.getBaseUriBuilder()
							.path(ProfileResource.class)
							.path(message.getAuthor())
							.build()
							.toString(); 
		return uriForProfile;
		
	}
	
	private String getUriForComments(UriInfo uriInfo, Message message) {
		String uriForComments = uriInfo.getBaseUriBuilder()
							.path(MessageResource.class)
							.path(MessageResource.class, "getCommentResource")
							.resolveTemplate("messageId", message.getId())
							.build()
							.toString(); 
		return uriForComments;
	}
	
    
    
    
    @POST
	public Response addMessage(Message message, @Context UriInfo uriInfo) {
    	
		Message newMessage = messageService.addMessage(message);
		
		String newId = String.valueOf(newMessage.getId());
    	URI uri = uriInfo.getAbsolutePathBuilder().path(newId).build();
		return Response.created(uri)
						.entity(newMessage)
						.build();
    }
    
    @PUT
	@Path("/{messageId}")
	public Message updateMessage(@PathParam("messageId") long messageId, Message message) {
    	message.setId(messageId);
		Message updatedMessage = messageService.updateMessage(message);
		return updatedMessage;
    }
    
    @DELETE
	@Path("/{messageId}")
	public Message deleteMessage(@PathParam("messageId") long messageId) {
    	
	return messageService.deleteMessage(messageId);
		
    }
    
   
    @Path("/{messageId}/comments")
    public CommentResource getCommentResource(){
		return new CommentResource();
    	
    }
     
}

