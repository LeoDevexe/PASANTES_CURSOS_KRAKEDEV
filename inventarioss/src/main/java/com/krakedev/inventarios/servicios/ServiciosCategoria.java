package com.krakedev.inventarios.servicios;

import java.util.ArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import com.krakedev.inventarios.bdd.CategoriaBDD;
import com.krakedev.inventarios.entidades.Categoria;
import com.krakedev.inventarios.excepciones.KrakedevException;

@Path("categoria")
public class ServiciosCategoria {
	@Path("crear")
	@POST
	@Consumes("application/json")
	public Response crear(Categoria categoria) {
		CategoriaBDD catBDD = new CategoriaBDD();
		
		try {
			catBDD.insertarCategoria(categoria);
			return Response.ok().build();
		} catch (KrakedevException e) {
			e.printStackTrace();
			return Response.serverError().build();
		}
		
	}
	
	@Path("actualizar")
	@PUT
	@Consumes("application/json")
	public Response actualizar(Categoria categoria) {
		CategoriaBDD catBDD = new CategoriaBDD();
		try {
			catBDD.actualizarCategoria(categoria);
			return Response.ok().build();
		} catch (KrakedevException e) {
			e.printStackTrace();
			return Response.serverError().build();
		}
		
	}
	
	@Path("consultar")
	@GET
	@Produces("application/json")
	public Response recuperarTodos() {
		CategoriaBDD catBDD = new CategoriaBDD();
		ArrayList<Categoria> categorias= null;
		try {
			categorias = catBDD.recuperarTodos();
			return Response.ok(categorias).build();
		} catch (KrakedevException e) {
			e.printStackTrace();
			return Response.serverError().build();
		}
		
	}
}
