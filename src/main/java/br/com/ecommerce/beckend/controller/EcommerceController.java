package br.com.ecommerce.beckend.controller;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import br.com.ecommerce.beckend.data.EcommerceDAO;
import br.com.ecommerce.beckend.data.Produto;
import br.com.ecommerce.beckend.data.Status;


@Path("ecommerce")
public class EcommerceController {
	 @GET
	    @Produces(MediaType.APPLICATION_JSON)
	    @Path("/")
	    public List<Produto> listProdutos() {
	        try {
	            EcommerceDAO ecommerceDAO = new EcommerceDAO();
	            return ecommerceDAO.listar();
	        } catch (SQLException | ClassNotFoundException ex) {
	            Logger.getLogger(EcommerceController.class.getName()).log(Level.SEVERE, null, ex);
	            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
	        }
	    }

	    @GET
	    @Produces(MediaType.APPLICATION_JSON)
	    @Path("{id}/")
	    public Produto getProduto(@PathParam("id") long id) {
	        try {
	            EcommerceDAO ecommerceDAO = new EcommerceDAO();
	            return ecommerceDAO.selecionar(id);
	        } catch (SQLException | ClassNotFoundException ex) {
	            Logger.getLogger(EcommerceController.class.getName()).log(Level.SEVERE, null, ex);
	            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
	        }
	    }

	    @POST
	    @Consumes(MediaType.APPLICATION_JSON)
	    @Path("/")
	    public Response create(Produto produto) {
	        try {
	            produto.setStatus(Status.NOVO);

	            EcommerceDAO ecommerceDAO = new EcommerceDAO();
	            ecommerceDAO.inserir(produto);
	            return Response.status(Response.Status.OK).build();
	        } catch (SQLException | ClassNotFoundException ex) {
	            Logger.getLogger(EcommerceController.class.getName()).log(Level.SEVERE, null, ex);
	            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
	        }
	    }

	    @PUT
	    @Consumes(MediaType.APPLICATION_JSON)
	    @Path("/")
	    public Response update(Produto produto) {
	        try {
	            produto.setStatus(Status.PENDENTE);

	            EcommerceDAO ecommerceDAO = new EcommerceDAO();
	            ecommerceDAO.alterar(produto);
	            return Response.status(Response.Status.OK).build();
	        } catch (SQLException | ClassNotFoundException ex) {
	            Logger.getLogger(EcommerceController.class.getName()).log(Level.SEVERE, null, ex);
	            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
	        }
	    }

	    @DELETE
	    @Path("{id}/")
	    public Response delete(@PathParam("id") long id) {
	        try {
	        	EcommerceDAO ecommerceDAO = new EcommerceDAO();
	            ecommerceDAO.excluir(id);
	            return Response.status(Response.Status.OK).build();
	        } catch (SQLException | ClassNotFoundException ex) {
	            Logger.getLogger(EcommerceController.class.getName()).log(Level.SEVERE, null, ex);
	            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
	        }
	    }

	    @PUT
	    @Path("{id}/")
	    public Response concluir(@PathParam("id") long id) {
	        try {
	        	EcommerceDAO ecommerceDAO = new EcommerceDAO();

	            Produto p = ecommerceDAO.selecionar(id);
	            p.setStatus(Status.FECHADO);

	            ecommerceDAO.alterar(p);
	            return Response.status(Response.Status.OK).build();
	        } catch (SQLException | ClassNotFoundException ex) {
	            Logger.getLogger(EcommerceController.class.getName()).log(Level.SEVERE, null, ex);
	            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
	        }
	    }
	}

