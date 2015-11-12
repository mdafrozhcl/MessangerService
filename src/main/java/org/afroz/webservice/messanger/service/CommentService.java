package org.afroz.webservice.messanger.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.afroz.webservice.messanger.database.DatabaseClass;
import org.afroz.webservice.messanger.model.Comment;
import org.afroz.webservice.messanger.model.ErrorMessage;
import org.afroz.webservice.messanger.model.Message;

public class CommentService {

	private Map<Long, Message> messages = DatabaseClass.getMessages();
	
	public CommentService(){
		
	}

	public List<Comment> getAllComments(long messageId){
		Map<Long, Comment> comments = messages.get(messageId).getComments(); 
		return new ArrayList<Comment>(comments.values());
	}
	
	public List<Comment> getAllPaginatedMessages(Long messageId,int start, int end){
		Map<Long, Comment> commentMap = messages.get(messageId).getComments();
		List<Comment> allComments = new ArrayList<Comment>(commentMap.values());
		if((start + end)> allComments.size()) return new ArrayList<Comment>();
		return allComments.subList(start, start+end);
	}
	
	public Comment getComment(Long messageId, Long commentId){
		ErrorMessage errorMessage = new ErrorMessage("Not found", 404, "http//:www.website.com");
		Response response = Response.status(Status.NOT_FOUND).entity(errorMessage).build();
		Message message = messages.get(messageId);
		if(message == null){
			//throw new WebApplicationException(Status.NOT_FOUND);
			throw new WebApplicationException(response);
		}
		Map<Long, Comment> comments = messages.get(messageId).getComments();
		Comment comment = comments.get(commentId);
		if(comment == null){
			//throw new WebApplicationException(response);
			throw new javax.ws.rs.NotFoundException("Comment not found");
		}
		return comments.get(commentId);
	}
	
	public Comment addComment(Long messageId, Comment comment){
		Map<Long,Comment> comments = messages.get(messageId).getComments();
		comment.setId(comments.size()+1);
		comments.put(comment.getId(), comment);
		return comment;
	}
	
	public Comment updateComment(Long messageId, Long CommentId, Comment comment){
		Map<Long, Comment> comments = messages.get(messageId).getComments();
		if(comment.getId() <=0){
			return null;
		}
		comments.put(comment.getId(), comment);
		return comment;
	}
	
	public void deleteComment(Long messageId, Long commentId){
		Map<Long, Comment> comments = messages.get(messageId).getComments();
		comments.remove(commentId);
	}

}
