package com.bogdan.messenger.myMessenger.resources;

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


/*PARAM ANNOTATIONS
 * 
 * MatrixParam = asemanator cu Query, doar ca in loc de ? ai ;
 * HeaderParam = pt requesturile in care pui ceva in header gen(Key, value, Description)
 * CookieParam = daca adaugi si un cookie pe request
 * 
 * FormParam = daca vrei pt html forms (valori de texboxuri etc)
 * 
 * 
 * Vrei sa accesezi toate variabilele de mai sus, pt asta folosim un Context
 * 
 * @Context poate fi anotat pt anumite tipuri speciale
 * Exemplu UriInfo
 * - inject UriInfo (info despre Uri accesat)
 * HttpHeaders = ca sa iei ce primesti pe header
 * 
 * 
 * Daca nu vrem sa folosim atatea anotari, exista si metoda de a folosi Beanuri
 * - se creeaza un bean separat ce are acele anotari
 * - si le punem in XXXResource
 */
@Path("/injectdemo")
@Consumes(MediaType.TEXT_PLAIN)
@Produces(MediaType.TEXT_PLAIN)
public class InjectDemoResource {
	
	@GET //http://localhost:8080/myMessenger/webapi/injectdemo/annotations;param=value
		//sau: daca vrei si HeaderParam
	    // in POSTMAN la Headers faci un key = authSessionID si un Value = "ceva", si poti
		// folosi acelasi link ca mai sus
		// cookie la fel
	@Path("annotations")
	public String getParamsUsingAnnotations(@MatrixParam("param") String matrixParam,
											@HeaderParam("authSessionID") String header,
											@CookieParam("name") String cookie) {
		return "Matrix param: " + matrixParam + " Header param: " + header + " Cookie param: " + cookie;
	}
	
	@GET //http://localhost:8080/myMessenger/webapi/injectdemo/context
	@Path("context")
	public String getParamsUsingContext(@Context UriInfo uriInfo, @Context HttpHeaders headers) {
		
		String path = uriInfo.getAbsolutePath().toString(); // si mai are si alte metode pe langa Absolute Path
		// precum si getQueryParameters, sa vezi care au fost trimise etc, getPathParam...
		String cookies = headers.getCookies().toString(); // returneaza un Map
		return "Path : " + path + " Cookies: " + cookies;
	}
	
	
	

}
