import com.google.gson.Gson;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.util.ArrayList;
import java.util.List;

@Path("/book")
public class BookGet {
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/viewBooks")
    public Response viewBooks(){
        List<Book> bookList = new ArrayList<Book>();
        Book book = new Book();
        book.setAuthor("wilfred");
        book.setName("Mike and Dave");
        book.setPrice(2000);
        for(int i=0;i<100;i++){
            bookList.add(book);
        }
        return Response.ok().entity(new Gson().toJson(bookList)).build();
    }
}
