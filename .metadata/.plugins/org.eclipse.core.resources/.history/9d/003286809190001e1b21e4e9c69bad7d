package com.krakedev.inventarios.servicios;

import java.util.ArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import com.krakedev.inventarios.bdd.ProveedoresBDD;
import com.krakedev.inventarios.entidades.Proveedor;
import com.krakedev.inventarios.excepciones.KrakedevException;

@Path("proveedores")
public class ServiciosProveedores {
	
	@Path("buscar/{subcadena}")
	@GET
	@Produces("application/json")
	public Response buscar(@PathParam("subcadena")  String subcadena){
		ProveedoresBDD provBDD = new ProveedoresBDD();
		ArrayList<Proveedor> proveedores= null;
		try {
			proveedores = provBDD.buscar(subcadena);
			//si todo esta correcto envia status 200
			return Response.ok(proveedores).build();
		} catch (KrakedevException e) {
			e.printStackTrace();
			//si algo falla devuelve el status 500 
			return Response.serverError().build();
		}
	}
	
	
	@Path("crear")
	@POST
	@Consumes("application/json")
	public Response crear(Proveedor proveedor) {
		ProveedoresBDD provBDD = new ProveedoresBDD();
		
		try {
			provBDD.insertar(proveedor);
			return Response.ok().build();
		} catch (KrakedevException e) {
			e.printStackTrace();
			return Response.serverError().build();
		}
		
	}
}
