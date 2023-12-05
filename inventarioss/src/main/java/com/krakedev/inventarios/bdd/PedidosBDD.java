package com.krakedev.inventarios.bdd;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

import com.krakedev.inventarios.entidades.DetallePedido;
import com.krakedev.inventarios.entidades.EstadoPedido;
import com.krakedev.inventarios.entidades.Pedido;
import com.krakedev.inventarios.entidades.Producto;
import com.krakedev.inventarios.entidades.Proveedor;
import com.krakedev.inventarios.excepciones.KrakedevException;
import com.krakedev.inventarios.utils.ConexionBDD;

public class PedidosBDD {
	public void insertar(Pedido pedido) throws KrakedevException {
		Connection con = null;
		PreparedStatement ps = null;
		PreparedStatement psDet = null;
		ResultSet rsClave = null;
		int codigoCabecera = 0;
		
		//recuperar un fecha en el paquete java.util
		Date fechaActual = new Date();
		
		///recuperamos la fecha en formato sql 
		java.sql.Date fechaSql = new java.sql.Date(fechaActual.getTime());
		
		
		try {
			con = ConexionBDD.obtenerConexion();
			//Statement.RETURN_GENERATED_KEYS sirve para poder recuperar las claves generadas en la cabecera de pedido 
			ps = con.prepareStatement("insert into cabecera_pedido(identificador,fecha,codigo_estado_pedido)" 
					+ "values(?,?,?);", Statement.RETURN_GENERATED_KEYS);
			
			ps.setString(1, pedido.getProveedor().getIdentificador());
			ps.setDate(2, fechaSql);
			ps.setString(3, "S");
			
			ps.executeUpdate();
			rsClave = ps.getGeneratedKeys();
			
			if(rsClave.next()) {
				codigoCabecera = rsClave.getInt(1);
			}
			
			ArrayList<DetallePedido>detallesPedido = pedido.getDetalles();
			DetallePedido det;
			
			for(int i=0 ;i < detallesPedido.size();i++) {
				det = detallesPedido.get(i);
				psDet = con.prepareStatement("insert into detalle_pedido(numero_cabecera_pedido,codigo_producto, cantidad_solicitada, subtotal, cantidad_recibida)"
						+ "values(?,?,?,?,?);");
				
				psDet.setInt(1, codigoCabecera);
				psDet.setInt(2, det.getProducto().getCodigo());
				psDet.setInt(3, det.getCantidadSolicitada());
				BigDecimal pv = det.getProducto().getPrecioVenta();
				BigDecimal cantidad = new BigDecimal(det.getCantidadSolicitada());
				BigDecimal subtotal =  pv.multiply(cantidad);
				psDet.setBigDecimal(4, subtotal);
				psDet.setInt(5, 0);
				
				psDet.executeUpdate();
				
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new KrakedevException("Error al insertar el producto. Detalle : " + e.getMessage());
		} catch (KrakedevException e) {
			throw e;
		} finally {
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}

		}
	}
	
	public void actualizar(Pedido pedido) throws KrakedevException {
		Connection con = null;
		PreparedStatement ps = null;
		PreparedStatement psDet = null;
		PreparedStatement psHist = null;
		
		Date fechaActual = new Date();
		Timestamp fechaHoraActual= new Timestamp(fechaActual.getTime()); 
			
		try {
			con = ConexionBDD.obtenerConexion();
			//Statement.RETURN_GENERATED_KEYS sirve para poder recuperar las claves generadas en la cabecera de pedido 
			ps = con.prepareStatement("update cabecera_pedido set codigo_estado_pedido='R' where numero_cabecera_pedido=?");
			ps.setInt(1, pedido.getCodigo());
			
			
			ps.executeUpdate();
			
			ArrayList<DetallePedido>detallesPedido = pedido.getDetalles();
			DetallePedido det;
			
			for(int i=0 ;i < detallesPedido.size();i++) {
				det = detallesPedido.get(i);
				psDet = con.prepareStatement("update detalle_pedido set cantidad_recibida=?, subtotal=? WHERE codigo_detalle_pedido=?");
		
				
				psDet.setInt(1, det.getCantidadRecibida());
				BigDecimal pv = det.getProducto().getPrecioVenta();
				BigDecimal cantidad = new BigDecimal(det.getCantidadRecibida());
				BigDecimal subtotal =  pv.multiply(cantidad);
				psDet.setBigDecimal(2, subtotal);
				psDet.setInt(3, det.getCodigo());
				
				psDet.executeUpdate();
				
				
				psHist= con.prepareStatement("insert into historial_stock(fecha,referencia,codigo_producto,cantidad)"
						+ "values(?,?,?,?)");
				
				psHist.setTimestamp(1, fechaHoraActual);
				psHist.setString(2, "Pedido " + pedido.getCodigo());
				psHist.setInt(3, det.getProducto().getCodigo());
				psHist.setInt(4, det.getCantidadRecibida());
				
				psHist.executeUpdate();
				
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new KrakedevException("Error al insertar el producto. Detalle : " + e.getMessage());
		} catch (KrakedevException e) {
			throw e;
		} finally {
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}

		}
	}
	
	//buscar pedidos por proveedor
	public ArrayList<Pedido>buscarPedidosPorProveedor(String subcadena) throws KrakedevException{
		ArrayList<Pedido> pedidos = new ArrayList<Pedido>();
		Connection con = null;
		PreparedStatement ps=null;
		ResultSet rs = null;
		Pedido pedido = null;
		
		try {
			con = ConexionBDD.obtenerConexion();
			ps = con.prepareStatement("select cp.numero_cabecera_pedido, cp.identificador,cp.fecha, cp.codigo_estado_pedido,"
					+ "dp.codigo_detalle_pedido, dp.codigo_producto, p.nombre_producto, dp.cantidad_solicitada, "
					+ "cast(dp.subtotal as decimal(6,2)), dp.cantidad_recibida "
					+ "from cabecera_pedido cp, detalle_pedido dp, productos p "
					+ "where cp.numero_cabecera_pedido = dp.numero_cabecera_pedido "
					+ "and dp.codigo_producto = p.codigo_producto "
					+ "and cp.identificador like ?");
			ps.setString(1,subcadena);
			rs = ps.executeQuery();
			
			while(rs.next()) {
				int codigoCP = rs.getInt("numero_cabecera_pedido");
				String identificador = rs.getString("identificador");
				Date fecha = rs.getDate("fecha");
				String estadoPedido = rs.getString("codigo_estado_pedido");
				int codigoDP = rs.getInt("codigo_detalle_pedido");
				int codigoProducto = rs.getInt("codigo_producto");
				String nombreProducto = rs.getString("nombre_producto");
				int cantidaSolicitada = rs.getInt("cantidad_solicitada");
				BigDecimal subtotal = rs.getBigDecimal("subtotal");
				int cantidadRecibida = rs.getInt("cantidad_recibida");
				
				Proveedor proveedor = new Proveedor();
				proveedor.setIdentificador(identificador);
				
				EstadoPedido estado = new EstadoPedido();
				estado.setCodigo(estadoPedido);
				
				Producto p = new Producto();
				p.setCodigo(codigoProducto);
				p.setNombre(nombreProducto);
				
				ArrayList<DetallePedido>detPed = new ArrayList<DetallePedido>();
				DetallePedido dp = new DetallePedido();
				dp.setCodigo(codigoDP);
				dp.setProducto(p);
				dp.setCantidadSolicitada(cantidaSolicitada);
				dp.setSubtotal(subtotal);
				dp.setCantidadRecibida(cantidadRecibida);
				
				detPed.add(dp);
				
				pedido = new Pedido(codigoCP, proveedor, fecha, estado, detPed); 
				pedidos.add(pedido);
				
			}
			
		} catch (KrakedevException e) {
			e.printStackTrace();
			throw e;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new KrakedevException("Error al consultar. Detalle : "+ e.getMessage());
		}
		
		return pedidos;
	}
}
