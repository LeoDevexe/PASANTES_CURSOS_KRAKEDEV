package com.krakedev.inventarios.servicios;

import java.util.ArrayList;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import com.krakedev.inventarios.bdd.ProveedoresBDD;
import com.krakedev.inventarios.entidades.TipoDocumentos;
import com.krakedev.inventarios.excepciones.KrakedevException;

@Path("tiposdocumento")
public class ServiciosTipoDocumentos {
	
	@Path("recuperar")
	@GET
	@Produces("application/json")
	public Response obtenerTiposDocumentos(){
		ProveedoresBDD prov = new ProveedoresBDD();
		ArrayList<TipoDocumentos> tiposDocumentos= null;
		try {
			tiposDocumentos = prov.recuperarTodos();
			//si todo esta correcto envia status 200
			return Response.ok(tiposDocumentos).build();
		} catch (KrakedevException e) {
			e.printStackTrace();
			//si algo falla devuelve el status 500 
			return Response.serverError().build();
		}
	}
}
