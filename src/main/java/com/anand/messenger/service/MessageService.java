package com.anand.messenger.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.zip.DataFormatException;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.anand.messenger.database.Database;
import com.anand.messenger.exception.DataNotFoundException;
import com.anand.messenger.model.ErrorMessage;
import com.anand.messenger.model.Message;


public class MessageService {
	
private Map<Long, Message> messages = Database.getMessages();
	
	public MessageService() {
		messages.put(1L, new Message(1, "Hello World", "koushik"));
		messages.put(2L, new Message(2, "Hello Jersey", "koushik"));
	}
	
	public List<Message> getAllMessagesForYear(int year) {
		List<Message> messagesForYear = new ArrayList<>();
		Calendar cal = Calendar.getInstance();
		for (Message message : messages.values()) {
			cal.setTime(message.getCreated());
			if (cal.get(Calendar.YEAR) == year) {
				messagesForYear.add(message);
			}
		}
		return messagesForYear;
	}
	
	public List<Message> getAllMessagesPaginated(int start, int size) {
		ArrayList<Message> list = new ArrayList<Message>(messages.values());
		if (start + size > list.size()) return new ArrayList<Message>();
		return list.subList(start, start + size); 
	}
	
	public List<Message> getAllMessages() {
		return new ArrayList<Message>(messages.values()); 
	}
	
	
	public Message getMessage(long messageId) {
		//messages is a Map messages.get(messageId)--key returns a message --Value 
		Message message = messages.get(messageId);
		
		if(message == null){
			
			throw new DataNotFoundException( "Message with id " + messageId + " Not found");
		}
		return message;
		
	}
	
	public Message addMessage(Message message){
		message.setId(messages.size()+1);
		messages.put(message.getId(), message);
		return message;	
	}
	
	public Message updateMessage(Message message){
		if(message.getId()<=0){
			return null;
		}
		messages.put(message.getId(), message);
		return message;
	}
	
	public Message deleteMessage(long id){
		ErrorMessage errorMessage = new ErrorMessage("Not Found", "404", "google.com");
		Response response = Response.status(Status.NOT_FOUND)
						.entity(errorMessage)
						.build();
		 Message message = messages.remove(id);
		 if(message == null){
				
			 	throw new WebApplicationException(response);
				//throw new NotFoundException(response);// This is the chile class of WebApplicationException
			}
		 return message;
	}
	
	
	
}
