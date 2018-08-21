package br.com.ecommerce.beckend;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.server.ResourceConfig;

@ApplicationPath("main")
public class Main extends ResourceConfig{
	public Main() {
		// TODO Auto-generated constructor stub
		packages("br.com.ecommerce.beckend.controller");
	}
}
