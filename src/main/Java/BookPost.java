import com.google.gson.Gson;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


@Path("/book")
public class BookPost {
    @Consumes(MediaType.APPLICATION_JSON)
    @POST
    @Path("/send")
    public Response send() {
        Book book = new Book();
        book.setName("Learn java");
        book.setAuthor("ken");
        book.setPrice(1000.0);
        return Response.status(200).entity(new Gson().toJson(book)).build();
    }
}
