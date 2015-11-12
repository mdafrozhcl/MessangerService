package org.afroz.webservice.messanger.resources;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.afroz.webservice.messanger.model.Comment;
import org.afroz.webservice.messanger.service.CommentService;

@Path("/")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class CommentResource {
	
	private CommentService commentService = new CommentService();
	
	@GET
	public List<Comment> getAllComments(@PathParam("messageId") Long messageId){
		return commentService.getAllComments(messageId);
	}
	
	@GET
	@Path("/{commentId}")
	public Comment getComment(@PathParam("commentId") Long commentId,@PathParam("messageId") Long messageId){
		return commentService.getComment(messageId, commentId);
	}
	
	@POST
	public Comment addComment(@PathParam("messageId") Long messageId, Comment comment){
		System.out.print("ADD comments");
		return commentService.addComment(messageId, comment);
	}
	
	@PUT
	@Path("/{commentId}")
	public Comment updateComment(@PathParam("messageId") Long messageId, @PathParam("commentId") Long commentId, Comment comment){
		return commentService.updateComment(messageId, commentId, comment);
	}
	
	@DELETE
	@Path("/{commentId}")
	public void deleteComment(@PathParam("messageId") Long messageId, @PathParam("commentId") Long commentId){
		commentService.deleteComment(messageId, commentId);
	}

}
