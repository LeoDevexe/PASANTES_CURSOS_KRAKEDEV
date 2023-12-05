package com.krakedev.inventarios.bdd;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.krakedev.inventarios.entidades.Categoria;
import com.krakedev.inventarios.entidades.Producto;
import com.krakedev.inventarios.entidades.UnidadDeMedida;
import com.krakedev.inventarios.excepciones.KrakedevException;
import com.krakedev.inventarios.utils.ConexionBDD;

public class ProductosBDD {
	//buscar productos por subcadena 
	public ArrayList<Producto>buscarProductos(String subcadena) throws KrakedevException{
		ArrayList<Producto> productos = new ArrayList<Producto>();
		Connection con = null;
		PreparedStatement ps=null;
		ResultSet rs = null;
		Producto producto = null;
		
		try {
			con = ConexionBDD.obtenerConexion();
			ps = con.prepareStatement("select prod.codigo_producto, prod.nombre_producto, udm.codigo_udm as nombre_udm,udm.descripcion as descripcion_udm, "
					+ "cast(prod.precio_venta as decimal(6,2)), prod.tiene_iva, cast(prod.coste as decimal(5,4)), prod.codigo_cat as categoria, cat.nombre as nombre_categoria,prod.stock "
					+ "from  productos prod, unidades_medida udm, categorias cat "
					+ "where prod.codigo_udm = udm.codigo_udm "
					+ "and prod.codigo_cat = cat.codigo_cat "
					+ "and upper(nombre_producto) like ?");
			ps.setString(1,"%"+subcadena.toUpperCase()+"%");
			rs = ps.executeQuery();
			
			while(rs.next()) {
				int codigoProducto = rs.getInt("codigo_producto");
				String nombreProducto  = rs.getString("nombre_producto");
				String nombreUnidadMedida  = rs.getString("nombre_udm");
				String descripcionUnidadMedida  = rs.getString("descripcion_udm");
				BigDecimal precioVenta = rs.getBigDecimal("precio_venta");
				boolean tieneIva = rs.getBoolean("tiene_iva");
				BigDecimal coste = rs.getBigDecimal("coste");
				int codigoCategoria = rs.getInt("categoria");
				String nombreCategoria = rs.getString("nombre_categoria");
				int stock = rs.getInt("stock");
				
				UnidadDeMedida udm = new UnidadDeMedida();
				udm.setNombre(nombreUnidadMedida);
				udm.setDescripcion(descripcionUnidadMedida);
				
				Categoria categoria = new Categoria();
				categoria.setCodigo(codigoCategoria);
				categoria.setNombre(nombreCategoria);
				
				producto = new Producto();
				producto.setCodigo(codigoProducto);
				producto.setNombre(nombreProducto);
				producto.setUnidadMedida(udm);
				producto.setPrecioVenta(precioVenta);
				producto.setTieneIva(tieneIva);
				producto.setCoste(coste);
				producto.setCategoria(categoria);
				producto.setStock(stock);
				
				
				productos.add(producto);
				
			}
			
		} catch (KrakedevException e) {
			e.printStackTrace();
			throw e;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new KrakedevException("Error al consultar. Detalle : "+ e.getMessage());
		}
		
		return productos;
	}
	
	public void insertar(Producto producto) throws KrakedevException {
		Connection con = null;
		PreparedStatement ps = null;
		try {
			con = ConexionBDD.obtenerConexion();
			ps = con.prepareStatement("insert into productos(nombre_producto, codigo_udm, precio_venta,tiene_iva, coste,codigo_cat,stock)" 
					+ "values(?,?,?,?,?,?,?);");
			
			ps.setString(1, producto.getNombre());
			ps.setString(2, producto.getUnidadMedida().getNombre());
			ps.setBigDecimal(3, producto.getPrecioVenta());
			ps.setBoolean(4, producto.isTieneIva());
			ps.setBigDecimal(5, producto.getCoste());
			ps.setInt(6, producto.getCategoria().getCodigo());
			ps.setInt(7, producto.getStock());
			
			ps.executeUpdate();
			
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
	
	//
	
	public void actualizarProducto(Producto producto) throws KrakedevException {
		Connection con = null;
		PreparedStatement ps = null;
		
		try {
			con = ConexionBDD.obtenerConexion();
			//Statement.RETURN_GENERATED_KEYS sirve para poder recuperar las claves generadas en la cabecera de pedido 
			ps = con.prepareStatement("update productos set nombre_producto = ?, codigo_udm = ?, "
                    + "precio_venta = ?, tiene_iva = ?, coste = ?, codigo_cat = ?, stock = ? "
                    + "WHERE codigo_producto = ?");
			
			ps.setString(1, producto.getNombre());
			ps.setString(2,producto.getUnidadMedida().getNombre());
			ps.setBigDecimal(3, producto.getPrecioVenta());
			ps.setBoolean(4, producto.isTieneIva());
			ps.setBigDecimal(5, producto.getCoste());
			ps.setInt(6, producto.getCategoria().getCodigo());
			ps.setInt(7, producto.getStock());
			ps.setInt(8, producto.getCodigo());
			
			
			ps.executeUpdate();
			
			
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
	
	//buscar producto por codigo de producto
	public Producto buscarProductoCodigo(int codigo) throws KrakedevException{
		
		Connection con = null;
		PreparedStatement ps=null;
		ResultSet rs = null;
		Producto producto = null;
		
		try {
			con = ConexionBDD.obtenerConexion();
			ps = con.prepareStatement("select prod.codigo_producto, prod.nombre_producto, udm.codigo_udm as nombre_udm,udm.descripcion as descripcion_udm, "
					+ "cast(prod.precio_venta as decimal(6,2)), prod.tiene_iva, cast(prod.coste as decimal(5,4)), prod.codigo_cat as categoria, cat.nombre as nombre_categoria,prod.stock "
					+ "from  productos prod, unidades_medida udm, categorias cat "
					+ "where prod.codigo_producto = ? "
					+ "and prod.codigo_udm = udm.codigo_udm");
			ps.setInt(1,codigo);
			rs = ps.executeQuery();
			
			if(rs.next()) {
				int codigoProducto = rs.getInt("codigo_producto");
				String nombreProducto  = rs.getString("nombre_producto");
				String nombreUnidadMedida  = rs.getString("nombre_udm");
				String descripcionUnidadMedida  = rs.getString("descripcion_udm");
				BigDecimal precioVenta = rs.getBigDecimal("precio_venta");
				boolean tieneIva = rs.getBoolean("tiene_iva");
				BigDecimal coste = rs.getBigDecimal("coste");
				int codigoCategoria = rs.getInt("categoria");
				String nombreCategoria = rs.getString("nombre_categoria");
				int stock = rs.getInt("stock");
				
				UnidadDeMedida udm = new UnidadDeMedida();
				udm.setNombre(nombreUnidadMedida);
				udm.setDescripcion(descripcionUnidadMedida);
				
				Categoria categoria = new Categoria();
				categoria.setCodigo(codigoCategoria);
				categoria.setNombre(nombreCategoria);
				
				//producto = new Producto(codigoProducto, nombreProducto, udm, precioVenta, tieneIva, coste, categoria, stock);
				
				producto = new Producto();
				producto.setCodigo(codigoProducto);
				producto.setNombre(nombreProducto);
				producto.setUnidadMedida(udm);
				producto.setPrecioVenta(precioVenta);
				producto.setTieneIva(tieneIva);
				producto.setCoste(coste);
				producto.setCategoria(categoria);
				producto.setStock(stock);
				
				
				
			}
			
		} catch (KrakedevException e) {
			e.printStackTrace();
			throw e;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new KrakedevException("Error al consultar. Detalle : "+ e.getMessage());
		}
		
		return producto;
	}
	
}
