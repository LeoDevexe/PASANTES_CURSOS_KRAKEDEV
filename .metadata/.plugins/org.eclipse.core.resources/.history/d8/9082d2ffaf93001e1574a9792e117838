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

import com.krakedev.inventarios.entidades.DetalleVenta;
import com.krakedev.inventarios.entidades.Venta;
import com.krakedev.inventarios.excepciones.KrakedevException;
import com.krakedev.inventarios.utils.ConexionBDD;

public class VentasBDD {
	public void insertar(Venta venta) throws KrakedevException {
		Connection con = null;
		PreparedStatement psCabecera = null;
		PreparedStatement psDet = null;
		PreparedStatement psActualizarCabecera = null;
		PreparedStatement psHist = null;
		ResultSet rsClave = null;
		int codigoCabecera = 0;

		ArrayList<DetalleVenta> detallesVenta = venta.getDetallesVenta();
		DetalleVenta det = null;

		// recuperar un fecha en el paquete java.util
		Date fechaActual = new Date();
		/// recuperamos la fecha en formato sql

		Timestamp fechaHoraActual = new Timestamp(fechaActual.getTime());

		try {
			con = ConexionBDD.obtenerConexion();
			// Statement.RETURN_GENERATED_KEYS sirve para poder recuperar las claves
			// generadas en la cabecera de pedido
			psCabecera = con.prepareStatement(
					"insert into cabecera_ventas(fecha,total_sin_iva,iva,total)" + "values(?,0,0,0);",
					Statement.RETURN_GENERATED_KEYS);

			psCabecera.setTimestamp(1, fechaHoraActual);
			/*
			 * psCabecera.setBigDecimal(2, venta.getTotalSinIva());
			 * psCabecera.setBigDecimal(3, venta.getIva()); psCabecera.setBigDecimal(4,
			 * venta.getTotal());
			 */

			psCabecera.executeUpdate();

			System.out.println("cabecera_ventas insertado....");
			rsClave = psCabecera.getGeneratedKeys();

			if (rsClave.next()) {
				codigoCabecera = rsClave.getInt(1);
			}

			// insertar el detalle de ventas

			for (int i = 0; i < detallesVenta.size(); i++) {
				det = detallesVenta.get(i);

				psDet = con.prepareStatement(
						"insert into detalle_ventas(codigo_cabecera_ventas,codigo_producto,cantidad,precio_venta,subtotal,subtotal_con_iva)"
								+ "values(?,?,?,?,?,?);");

				psDet.setInt(1, codigoCabecera);
				psDet.setInt(2, det.getProducto().getCodigo());
				psDet.setInt(3, det.getCantidad());
				psDet.setBigDecimal(4, det.getProducto().getPrecioVenta());
				BigDecimal cantidad = new BigDecimal(det.getCantidad());
				BigDecimal subtotal = det.getProducto().getPrecioVenta().multiply(cantidad);
				System.out.println(subtotal);
				psDet.setBigDecimal(5, subtotal);
				//asignamos el valor del subtotal al subtotal de detalles ventas
				detallesVenta.get(i).setSubtotal(subtotal);
				// verificamos si el producto tiene IVA
				if (det.getProducto().isTieneIva()) {
					BigDecimal subtotalConIva = subtotal.multiply(BigDecimal.valueOf(1.12));
					psDet.setBigDecimal(6, subtotalConIva);
					detallesVenta.get(i).setSubtotalConIva(subtotalConIva);
				} else {
					// si no tiene iva le enviamos el valor del subtotal.
					psDet.setBigDecimal(6, subtotal);
				}
				
				psDet.executeUpdate();
				
				
				System.out.println("detalle_ventas insertado....");
				
			}
			System.out.println(detallesVenta);

			// actualizar cabecera_ventas
			System.out.println("Entrando a actualizar cabecera_ventas");
			BigDecimal totalSinIva = BigDecimal.ZERO;
			BigDecimal iva = BigDecimal.ZERO;

			// calculando valores que tienen y no tienen iva
			for (int i = 0; i < detallesVenta.size(); i++) {
				det = detallesVenta.get(i);
				/*System.out.println(det);
				System.out.println(det.getProducto());
				System.out.println(det.getSubtotal());
				System.out.println(det.getProducto().isTieneIva());
				*/
				if (det.getSubtotal() != null) {
					if (det.getProducto().isTieneIva()) {
						totalSinIva = totalSinIva.add(det.getSubtotal());
						iva = iva.add(det.getSubtotal().multiply(BigDecimal.valueOf(0.12)));
						//iva = iva.add(det.getSubtotal());
						System.out.println("iva " + iva);
					} else {
						totalSinIva = totalSinIva.add(det.getSubtotal());
						System.out.println("total sin iva "+totalSinIva);
					}
				}

				BigDecimal total = totalSinIva.add(iva);
				
				System.out.println("Valores con iva y sin iva asignados");

				psActualizarCabecera = con.prepareStatement(
						"update cabecera_ventas set total_sin_iva=?, iva=?, total=? where codigo_cabecera_ventas=?");

				psActualizarCabecera.setBigDecimal(1, totalSinIva);
				System.out.println(totalSinIva);
				psActualizarCabecera.setBigDecimal(2, iva);
				System.out.println(iva);
				psActualizarCabecera.setBigDecimal(3, total);
				System.out.println(total);
				psActualizarCabecera.setInt(4, codigoCabecera);
				System.out.println(codigoCabecera);

				psActualizarCabecera.executeUpdate();

				System.out.println("cabecera_ventas actualizado");

				// System.out.println("cabecera_ventas actualizado");

				// actualizar historial stock
				psHist = con.prepareStatement(
						"insert into historial_stock(fecha,referencia,codigo_producto,cantidad)" + "values(?,?,?,?);");

				psHist.setTimestamp(1, fechaHoraActual);
				psHist.setString(2, "VENTA " + codigoCabecera);
				psHist.setInt(3, det.getProducto().getCodigo());
				// Convertir la cantidad vendida en negativo
				psHist.setInt(4, det.getCantidad() * -1);

				psHist.executeUpdate();
				System.out.println("historial stock registrado....");

			}

			// Registrar en historial_stock
			/*
			 * psHist = con.
			 * prepareStatement("insert into historial_stock(fecha,referencia,codigo_producto,cantidad)"
			 * + "values(?,?,?,?);");
			 * 
			 * psHist.setTimestamp(1, fechaHoraActual); psHist.setString(2, "VENTA " +
			 * codigoCabecera); psHist.setInt(3, det.getProducto().getCodigo()); //
			 * Convertir la cantidad vendida en negativo psHist.setInt(4, det.getCantidad()
			 * * -1);
			 * 
			 * psHist.executeUpdate(); System.out.println("historial stock registrado....");
			 */

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

}
