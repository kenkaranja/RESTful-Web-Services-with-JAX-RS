package resources;

import Bean.MessageFilterBean;
import Model.Comment;
import Model.Message;
import Service.MessageService;
import org.jboss.logging.Logger;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@Path("/messages")
@Consumes(MediaType.APPLICATION_JSON)
//for content negotiation -based on the content- header requested by the client
@Produces(value = {MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML,MediaType.TEXT_PLAIN,MediaType.TEXT_XML})
public class MessageResource {
    Logger logger = Logger.getLogger(MessageResource.class);
    MessageService messageService = new MessageService();

    @GET
    public List<Message> getMessages(@BeanParam MessageFilterBean messageFilterBean)

    {
        if (messageFilterBean.getYear() > 0) {
            return messageService.getAllMessagesForYear(messageFilterBean.getYear());
        }
        if (messageFilterBean.getStart() >= 0 && messageFilterBean.getSize() > 0) {
            return messageService.getAllMessagesPaginated(messageFilterBean.getStart(), messageFilterBean.getSize());
        }
        return messageService.getAllMessages();
    }


    @POST
    public Response addMessage(Message message, @Context UriInfo uriInfo) throws URISyntaxException {
        Message newMessage = messageService.addMessage(message);
        String newid = String.valueOf(newMessage.getId());
        URI uri = uriInfo.getAbsolutePathBuilder().path(newid).build();
        return Response.created(uri).entity(newMessage).build();

    }

    @GET
    @Path("/{messageId}")
    public Message getMessage(@PathParam("messageId") long id, @Context UriInfo uriInfo) {
        Message message = messageService.getMessage(id);
        //HATEOAS links
        message.addLink(getUriForSelf(uriInfo, message), "self");
        message.addLink(getUriForProfile(uriInfo, message), "Profile");
        message.addLink(getUriForComments(uriInfo, message), "Comments");
        return message;
    }


    @PUT
    @Path("/{messageId}")
    public Message updateMessage(@PathParam("messageId") long id, Message message) {
        message.setId(id);
        return messageService.updateMessage(message);
    }

    @DELETE
    @Path("/{messageId}")
    public void deleteMessage(@PathParam("messageId") long id) {
        messageService.removeMessage(id);
    }

    //This creates a SubResources
    @Path("/{messageId}/comments")
    public CommentResource getCommentresource() {
        return new CommentResource();
    }

    //HATEOAS ----Links to other Resources that Message Resource can Map to

    private String getUriForComments(UriInfo uriInfo, Message message) {
        String uri = uriInfo.getBaseUriBuilder()
                .path(MessageResource.class)  //Super class resource
                .path(MessageResource.class, "getCommentresource") //method to sub resource
                .path(CommentResource.class) //sub resourcce uri
                .resolveTemplate("messageId", message.getId())  //variable you want to replace
                .build().toString();
        return uri;
    }

    private String getUriForProfile(UriInfo uriInfo, Message message) {
        return uriInfo.getBaseUriBuilder().path(ProfileResource.class).path(message.getAuthor()).build().toString();
    }

    private String getUriForSelf(UriInfo uriInfo, Message message) {
        return uriInfo.getBaseUriBuilder().path(MessageResource.class).path(String.valueOf(message.getId())).build().toString();
    }


}
