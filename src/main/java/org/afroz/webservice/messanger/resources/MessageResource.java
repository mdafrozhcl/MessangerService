package org.afroz.webservice.messanger.resources;

import java.net.URI;
import java.util.List;
import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import org.afroz.webservice.messanger.model.Message;
import org.afroz.webservice.messanger.resources.beans.MessageFilterBean;
import org.afroz.webservice.messanger.service.MessageService;

@Path("/messages")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
// @Produces(value={MediaType.APPLICATION_JSON, MediaType.TEXT_XML})
public class MessageResource {

	MessageService messageService = new MessageService();

	@GET
	public List<Message> getJsonMessages(
			@BeanParam MessageFilterBean messageFilterBean) {
		System.out.println("Json method called");
		if (messageFilterBean.getYear() > 0) {
			return messageService.getAllMessagesForYear(messageFilterBean
					.getYear());
		}
		/*
		 * if (messageFilterBean.getStart()>= 0 && messageFilterBean.getSize()
		 * >= 0) { return
		 * messageService.getAllPaginatedMessages(messageFilterBean.getStart(),
		 * messageFilterBean.getSize()); }
		 */
		return messageService.getAllMessages();
	}

	@GET
	@Produces(MediaType.TEXT_XML)
	public List<Message> getXmlMessages(
			@BeanParam MessageFilterBean messageFilterBean) {
		System.out.println("XML method called");
		if (messageFilterBean.getYear() > 0) {
			return messageService.getAllMessagesForYear(messageFilterBean
					.getYear());
		}
		/*
		 * if (messageFilterBean.getStart()>= 0 && messageFilterBean.getSize()
		 * >= 0) { return
		 * messageService.getAllPaginatedMessages(messageFilterBean.getStart(),
		 * messageFilterBean.getSize()); }
		 */
		return messageService.getAllMessages();
	}

	/*
	 * @GET public List<Message> getMessage(@QueryParam("year") int year,
	 * 
	 * @QueryParam("start") int start, @QueryParam("size") int size) { if (year
	 * > 0) { return messageService.getAllMessagesForYear(year); } if (start >=
	 * 0 && size >= 0) { return messageService.getAllPaginatedMessages(start,
	 * size); } return messageService.getAllMessages(); }
	 */

	@POST
	public Response addJsonMessage(Message message, @Context UriInfo uriInfo) {
		Message newMessage = messageService.addMessage(message);
		/*
		 * return Response.status(Status.CREATED) .entity(newMessage) .build();
		 */
		String newId = String.valueOf(newMessage.getId());
		URI uri = uriInfo.getAbsolutePathBuilder().path(newId).build();
		return Response.created(uri).entity(newMessage).build();
	}

	@POST
	// @Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_XML)
	public Response addXmlMessage(Message message, @Context UriInfo uriInfo) {
		System.out.println("post method consumes json and produces xml");
		Message newMessage = messageService.addMessage(message);
		/*
		 * return Response.status(Status.CREATED) .entity(newMessage) .build();
		 */
		String newId = String.valueOf(newMessage.getId());
		URI uri = uriInfo.getAbsolutePathBuilder().path(newId).build();
		return Response.created(uri).entity(newMessage).build();
	}

	@PUT
	@Path("/{messageId}")
	public Message updateMessage(@PathParam("messageId") long id,
			Message message) {
		message.setId(id);
		return messageService.updateMessage(message);
	}

	@DELETE
	@Path("/{messageId}")
	public void deleteMessage(@PathParam("messageId") long id) {
		messageService.deleteMessage(id);
	}

	@DELETE
	public void deleteAllMessage() {
		messageService.deleteAllMessages();
	}

	@GET
	@Path("/{messageId}")
	public Message getMessage(@PathParam("messageId") Long id,
			@Context UriInfo uriInfo) {
		Message message = messageService.getMessage(id);
		message.addLink(getUriForSelf(uriInfo, message), "self");
		message.addLink(getUriForProfile(uriInfo, message), "profile");
		message.addLink(getUriForComments(uriInfo, message), "comments");
		return message;
	}

	private String getUriForComments(UriInfo uriInfo, Message message) {
		String uri = uriInfo.getBaseUriBuilder().path(MessageResource.class)
				.path(MessageResource.class, "getCommentResource")
				.resolveTemplate("messageId", message.getId())
				.path(CommentResource.class).build().toString();
		return uri;
	}

	private String getUriForProfile(UriInfo uriInfo, Message message) {
		String uri = uriInfo.getBaseUriBuilder().path(ProfileResource.class)
				.path(message.getAuthor()).build().toString();
		return uri;
	}

	private String getUriForSelf(UriInfo uriInfo, Message message) {
		String uri = uriInfo.getBaseUriBuilder().path(MessageResource.class)
				.path(String.valueOf(message.getId())).build().toString();
		return uri;
	}

	@Path("/{messageId}/comments")
	public CommentResource getCommentResource() {
		return new CommentResource();
	}
}
