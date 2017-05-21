package com.anand.messenger.resources;
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

@Path("/allotherparams")
@Produces(MediaType.TEXT_PLAIN)
@Consumes(MediaType.TEXT_PLAIN)
public class AllOtherParamsResource {
	
	@GET
	@Path("/annotations")
	public String getAllOtherParams(@MatrixParam("matrix") String matrix,
									@HeaderParam("header") String header,
									@CookieParam("cookie") String cookie){
		return "hello " + matrix + "Header Value " + header + "Cookie Value " + cookie;
	}
	
	
	@GET
	@Path("/contextannotation")
	public String getParamsUSingContext(@Context UriInfo uriInfo, HttpHeaders httpHeaders){
		String path = uriInfo.getAbsolutePath().toString();
		
		//It will throw a nullPointer b,coz we dont have any cookie value.
		//String cookie = httpHeaders.getCookies().toString();
		
		return "Path : " + path ;
		
	}

}
