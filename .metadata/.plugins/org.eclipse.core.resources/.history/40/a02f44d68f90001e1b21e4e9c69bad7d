package com.krakedev.inventarios.servicios;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
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
}
