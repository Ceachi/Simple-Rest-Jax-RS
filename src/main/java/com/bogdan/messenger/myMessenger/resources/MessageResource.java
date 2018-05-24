package com.bogdan.messenger.myMessenger.resources;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.net.URI;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

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
import javax.ws.rs.ext.ParamConverter;
import javax.ws.rs.ext.ParamConverterProvider;

import com.bogdan.messenger.myMessenger.model.Message;
import com.bogdan.messenger.myMessenger.resources.beans.MessageFilterBean;
import com.bogdan.messenger.myMessenger.service.MessageService;

/*
 * De cate ori faci un apel asupra lui /messages
 * se creeaza o noua instanta al acestei clase
 * dar daca nu vrei sa se intample asta poti adauga anotarea
 * @Singleton, configurezi resursa sa fie creeata 1 singura data
 */
@Path("/messages")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class MessageResource {

	MessageService messageService = new MessageService();
	
	@GET//http://localhost:8080/myMessenger/webapi/messages/printSomething
	@Path("/printSomething")
	@Produces(MediaType.APPLICATION_XML)
	public List<Message> getText() {
		return messageService.getAllMessages();
	}
	
	
	@GET//http://localhost:8080/myMessenger/webapi/messages?year=2018
	// in loc sa folosesti prea multe QueryParam, faci o clasa MessageFilterBean
	public List<Message> getMessages(@BeanParam  MessageFilterBean filterBean) {
		
		if (filterBean.getYear() > 0) {
			return messageService.getAllMessagesForYear(filterBean.getYear());
		}
		if (filterBean.getStart() >= 0 && filterBean.getSize() > 0) {
			return messageService.getAllMessagesPaginated(filterBean.getStart(), filterBean.getSize());
		}
		return messageService.getAllMessages();
	}
	
	@GET //http://localhost:8080/myMessenger/webapi/messages/getAllMessages
	@Path("/getAllMessages")
	public Map<Long,Message> getAllMessages() {
		
		Map<Long, Message> messages = messageService.getAllMess();
		System.out.println("After put");
		
		for(Map.Entry<Long, Message> entry : messages.entrySet()) {
			Long key = entry.getKey();
			Message value = entry.getValue();
			System.out.println(entry);
		}
		
		return messageService.getAllMess();
	}
	
	// get Messages with QueryParam
	@GET//http://localhost:8080/myMessenger/webapi/messages/getMessagesByQuery?year=2018
		// sau poti si asa:
		//http://localhost:8080/myMessenger/webapi/messages/getMessagesByQuery?start=1&size=1
	@Path("/getMessagesByQuery")
	// mai simplu decat sa faci atatea anotari, folosesti BeanParam
	public List<Message> getMessages(@QueryParam("year") int year,
									 @QueryParam("start") int start,
									 @QueryParam("size") int size) {
		if(year > 0) {
			return messageService.getAllMessagesForYear(year);
		}
		if(start >= 0 && size > 0) {
			return messageService.getAllMessagesPaginated(start, size);
		}
		return messageService.getAllMessages();
	}

	@POST// dupa ce adaugam, vrem ca in header sa punem si linkul cum putem accesa
	// noua resursa adaugata
	public Response addMessage(Message message, @Context UriInfo uriInfo) {
		
		Message newMessage = messageService.addMessage(message);
		String newId = String.valueOf(newMessage.getId()); // get the id of the new message
		URI uri = uriInfo.getAbsolutePathBuilder().path(newId).build();
		// luam pathul absolut din URI-ul primit si adaugam id-ul mesajului
		// cu getAbsolutePathBuilder, poti adauga ceva catre un URI existent
		/*
		 * Response.status(Status.CREATED)
		 * .entity(newMessage)
		 * .build()
		 * daca vrei sa returnezi si statusul
		 */
		/*
		 * vrem sa trimitem si status si locatia(headerului) pt noau resursa
		 * return Response.created(new URI(/messenger/webapp/messages/" + newMessage.getId()	
		 * .entity(newMessage)
		 * .build()
		 * 	 */
		
		// vrem sa trimitem un URL pentru noua valoare creata
		return Response.created(uri)//adauga status+location(header) pt noua resursa
				.entity(newMessage)
				.build();
	}
	

	@PUT //ttp://localhost:8080/myMessenger/webapi/messages/1
	@Path("/{messageId}")
	public Message updateMessage(@PathParam("messageId") long id, Message message) {
		message.setId(id);
		messageService.updateMessage(message);
		 
		 System.out.println("In put method after update");
		 Map<Long, Message> messages = messageService.getAllMess();			
			for(Map.Entry<Long, Message> entry : messages.entrySet()) {
				Long key = entry.getKey();
				Message value = entry.getValue();
				System.out.println(entry);
			}
			
			
		return message;
	}
	
	@PUT //http://localhost:8080/myMessenger/webapi/messages/putMessage/1
	@Path("putMessage/{messageId}")
	public Message updateNewMessage(@PathParam("messageId") long id, Message message) {
		message.setId(id);
		return messageService.updateMessage(message);
	}
	
	
	@DELETE
	@Path("/{messageId}")
	public void deleteMessage(@PathParam("messageId") long id) {
		messageService.removeMessage(id);
	}
	
	
	@GET
	@Path("/{messageId}")
	public Message getMessage(@PathParam("messageId") long id, @Context UriInfo uriInfo) {
		Message message = messageService.getMessage(id);
		message.addLink(getUriForSelf(uriInfo, message), "self");//link catre self resource
		message.addLink(getUriForProfile(uriInfo, message), "profile");
		message.addLink(getUriForComments(uriInfo, message), "comments");
		
		return message;
		
	}
	@GET
	@Path("getMessage/{messageId}")
	public Message getMessage(@PathParam("messageId") long id) {
		return messageService.getMessage(id);
	}

	private String getUriForComments(UriInfo uriInfo, Message message) {
		URI uri = uriInfo.getBaseUriBuilder()
				.path(MessageResource.class)
	       		.path(MessageResource.class, "getCommentResource")
	       		.path(CommentResource.class)
	       		.resolveTemplate("messageId", message.getId())
	            .build();
	    return uri.toString();
	}

	private String getUriForProfile(UriInfo uriInfo, Message message) {
		URI uri = uriInfo.getBaseUriBuilder()
       		 .path(ProfileResource.class)
       		 .path(message.getAuthor())
             .build();
        return uri.toString();
	}

	private String getUriForSelf(UriInfo uriInfo, Message message) {
		String uri = uriInfo.getBaseUriBuilder()
		 .path(MessageResource.class)
		 .path(Long.toString(message.getId()))
		 .build()
		 .toString();
		return uri;
	}
	
	
	
	@GET
	@Path("/{messageId}/comments")
	public CommentResource getCommentResource() {
		return new CommentResource();
	}
	
}


/*
 * Mai exista si o alta intrebuintare a lui PathParam exemplu:
 * anotezi clasa cu : @Path("(pathParam)/messages")
 * @Path("(pathParam)/messages")
 * class MyResource {
 * 		@PathParam("pathParam") private String pathParamExample;
 * 		@Queryparam("query") private String queryParamsExample;
 * 
 * 		@GET
 * 		@Produces(MediaType.TEXT_PLAIN)
 * 		public String testMethod() {
 * 			return "Ceva" + queryParamsExample + " " + pathParamExample;			
 * 		}
 * 
 * 
 * Dar daca facem clasa @Singleton, ne da exceptia: ModelValidationException
 * pt ca practic cu @Singleton, variabilele alea sunt creeate inainte de request
 * */





