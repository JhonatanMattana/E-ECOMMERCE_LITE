package br.com.ecommerce.beckend.controller;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

@Path("teste")
public class TesteController {
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String getMsg(@QueryParam("usuario") String usuario) {
		return "Teste controller: " + usuario;
	}
	
	@GET
	@Produces
	@Path("usuarios/{id}")
	private String  getUsuarios(@PathParam("id") long id) {
		return "Recuperando usuario com ID: " + id;
	}
}
