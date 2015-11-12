package org.afroz.webservice.messanger.service;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;

import org.afroz.webservice.messanger.database.DatabaseClass;
import org.afroz.webservice.messanger.exception.DataNotFoundException;
import org.afroz.webservice.messanger.model.Comment;
import org.afroz.webservice.messanger.model.Message;

public class MessageService {

	private Map<Long, Message> messages = DatabaseClass.getMessages();
	
	public MessageService(){
		Message m1 = new Message(1L, "Hello world!", "Afroz");
		Message m2 = new Message(2L, "Hello jersey!", "Afroz");
		Map<Long, Comment> comments = new HashMap<>();
		comments.put(1L, new Comment(1,"hi","Ravi", new Date()));
		comments.put(2L, new Comment(2,"hello","Afroz", new Date()));
		m1.setComments(comments);
		m2.setComments(comments);
		messages.put(1L, m1);
		messages.put(2L, m2);
	}
	
	public List<Message> getAllMessages(){
		return new ArrayList<Message>(messages.values());
	}
	
	public List<Message> getAllMessagesForYear(int year){
		List<Message> yearlist = new ArrayList<Message>();
		Calendar cal = Calendar.getInstance();
		for(Message message: messages.values()){
			cal.setTime(message.getCreated());
			if(cal.get(Calendar.YEAR) == year){
				yearlist.add(message);
			}
		}
		return yearlist;
	}
	
	public List<Message> getAllPaginatedMessages(int start, int end){
		List<Message> allMessages = new ArrayList<Message>(messages.values());
		if((start + end)> allMessages.size()) return new ArrayList<Message>();
		return allMessages.subList(start, start+end);
	}
	
	public Message getMessage(Long id){
		Message message = messages.get(id);
		if(message == null){
			throw new DataNotFoundException("Message with id "+id+" not found");
		}
		return message;
	}
	
	public Message addMessage(Message message){
		message.setId(messages.size()+1);
		messages.put(message.getId(), message);
		return message;
	}
	
	public Message updateMessage(Message message){
		if(message.getId() <=0){
			return null;
		}
		messages.put(message.getId(), message);
		return message;
	}
	
	public void deleteMessage(Long id){
		messages.remove(id);
	}
	
	public void deleteAllMessages(){
		messages.clear();
	}
}
