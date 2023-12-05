package com.krakedev.inventarios.servicios;

import java.util.ArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import com.krakedev.inventarios.bdd.ProductosBDD;
import com.krakedev.inventarios.entidades.Producto;
import com.krakedev.inventarios.excepciones.KrakedevException;

@Path("productos")
public class ServiciosProductos {
	@Path("buscar/{subcadena}")
	@GET
	@Produces("application/json")
	public Response buscar(@PathParam("subcadena")  String subcadena){
		ProductosBDD prodBDD = new ProductosBDD();
		ArrayList<Producto> productos= null;
		try {
			productos = prodBDD.buscarProductos(subcadena);
			//si todo esta correcto envia status 200
			return Response.ok(productos).build();
		} catch (KrakedevException e) {
			e.printStackTrace();
			//si algo falla devuelve el status 500 
			return Response.serverError().build();
		}
	}
	
	@Path("crear")
	@POST
	@Consumes("application/json")
	public Response crear(Producto producto) {
		ProductosBDD prodBDD = new ProductosBDD();
		
		try {
			prodBDD.insertar(producto);
			return Response.ok().build();
		} catch (KrakedevException e) {
			e.printStackTrace();
			return Response.serverError().build();
		}
		
	}
	
	@Path("actualizar")
	@PUT
	@Consumes("application/json")
	public Response actualizar(Producto producto) {
		ProductosBDD productoBDD = new ProductosBDD();
		try {
			productoBDD.actualizarProducto(producto);
			//si todo esta correcto envia status 200
			return Response.ok().build();
		} catch (KrakedevException e) {
			e.printStackTrace();
			//si algo falla devuelve el status 500 
			return Response.serverError().build();
		}
	}
	
	@Path("buscarProductoCodigo/{id}")
	@GET
	@Produces("application/json")
	public Response buscarPorIdentificador(@PathParam("id") int id) {
	    ProductosBDD prodBDD = new ProductosBDD();
	    Producto prod = null;
	    try {
	        prod = prodBDD.buscarProductoCodigo(id);
	        return Response.ok(prod).build();
	    } 
	    
	catch (KrakedevException e) {
	        e.printStackTrace();
	        return Response.serverError().build();
	    }
	}
}
