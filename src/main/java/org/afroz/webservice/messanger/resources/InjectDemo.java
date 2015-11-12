package org.afroz.webservice.messanger.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.CookieParam;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.MatrixParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

@Path("/Injectdemo")
@Consumes(MediaType.TEXT_PLAIN)
@Produces(MediaType.TEXT_PLAIN)
public class InjectDemo {

	@GET
	@Path("anno")
	public String getParamsUsingAnnotations(@MatrixParam("mparam") String mparam, @HeaderParam("username") String name, @CookieParam("pass") String pass){
		return "matrix: "+mparam + " headerparam: "+name + "Cookie: "+pass;
	}
	
	@GET
	@Path("context")
	public String getContextUsingAnnotation(@Context UriInfo uriInfo, @Context HttpHeaders headerInfo){
		//String path = uriInfo.getPath();
		String path = uriInfo.getAbsolutePath().toString();
		String cookies = headerInfo.getCookies().toString();
		return "Path: "+path+" cookies: "+cookies;
	}
}
