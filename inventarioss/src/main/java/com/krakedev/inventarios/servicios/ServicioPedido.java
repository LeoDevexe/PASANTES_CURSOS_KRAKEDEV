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

import com.krakedev.inventarios.bdd.PedidosBDD;
import com.krakedev.inventarios.entidades.Pedido;
import com.krakedev.inventarios.excepciones.KrakedevException;


@Path("pedidos")
public class ServicioPedido {
	@Path("registrar")
	@POST
	@Consumes("application/json")
	public Response crear(Pedido pedido) {
		PedidosBDD pedidoBDD = new PedidosBDD();
		
		try {
			pedidoBDD.insertar(pedido);
			return Response.ok().build();
		} catch (KrakedevException e) {
			e.printStackTrace();
			return Response.serverError().build();
		}
		
	}
	
	@Path("recibir")
	@PUT
	@Consumes("application/json")
	public Response actualizar(Pedido pedido) {
		PedidosBDD pedidoBDD = new PedidosBDD();
		try {
			pedidoBDD.actualizar(pedido);
			//si todo esta correcto envia status 200
			return Response.ok().build();
		} catch (KrakedevException e) {
			e.printStackTrace();
			//si algo falla devuelve el status 500 
			return Response.serverError().build();
		}
	}
	
	@Path("buscarIdentificador/{id}")
	@GET
	@Produces("application/json")
	public Response buscarPorIdentificador(@PathParam("id")  String id){
		PedidosBDD pedidoBDD = new PedidosBDD();
		ArrayList<Pedido> pedidos= null;
		try {
			pedidos = pedidoBDD.buscarPedidosPorProveedor(id);
		
			//si todo esta correcto envia status 200
			return Response.ok(pedidos).build();
		} catch (KrakedevException e) {
			e.printStackTrace();
			//si algo falla devuelve el status 500 
			return Response.serverError().build();
		}
	}
}
