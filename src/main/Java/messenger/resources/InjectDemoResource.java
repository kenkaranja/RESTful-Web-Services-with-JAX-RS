package resources;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

@Path("/injectDemo")
@Consumes(MediaType.TEXT_PLAIN)
@Produces(MediaType.TEXT_PLAIN)
public class InjectDemoResource {


    //matrixParam uses ;
    @GET
    @Path("/annotations")
    public String getParamsUsingAnnotations(@MatrixParam("param") String matrixParam,
                                            @HeaderParam("customHeaderParam") String header,
                                            @CookieParam("JSESSIONID") String cookie) {
        return "Matrix param: " + matrixParam + " Header param: " + header + " Cookie: " + cookie;
    }

    @GET
    @Path("/context")
    public String getParamsUsingContext(@Context UriInfo uriInfo, @Context HttpHeaders httpHeaders) {
        String path = uriInfo.getAbsolutePath().toString();
        String cookies = httpHeaders.getCookies().toString();
        return "Path: " + path + " Cookies: " + cookies;
    }
}
