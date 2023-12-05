package com.krakedev.inventarios.bdd;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.krakedev.inventarios.entidades.Categoria;

import com.krakedev.inventarios.excepciones.KrakedevException;
import com.krakedev.inventarios.utils.ConexionBDD;

public class CategoriaBDD {
	public void insertarCategoria(Categoria categoria) throws KrakedevException {
		Connection con = null;
		PreparedStatement ps = null;
		try {
			con = ConexionBDD.obtenerConexion();
			ps = con.prepareStatement("insert into categorias (nombre, categoria_padre) VALUES (?, ?)");
			
			ps.setString(1, categoria.getNombre());
			if(categoria.getCategoriaPadre() != null) {
				ps.setInt(2, categoria.getCategoriaPadre().getCodigo());
			}else {
				ps.setNull(2, java.sql.Types.INTEGER);
			}

			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new KrakedevException("Error al insertar el cliente. Detalle : " + e.getMessage());
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
	
	public void actualizarCategoria(Categoria categoria) throws KrakedevException {
		Connection con = null;
		PreparedStatement ps = null;
		try {
			con = ConexionBDD.obtenerConexion();
			ps = con.prepareStatement("update  categorias set nombre=?, categoria_padre=? where codigo_cat=?");
			
			ps.setString(1, categoria.getNombre());
			if(categoria.getCategoriaPadre() != null) {
				ps.setInt(2, categoria.getCategoriaPadre().getCodigo());
				System.out.println("categoria_padre--- " + categoria.getCategoriaPadre().getCodigo());
			}else {
				ps.setNull(2, java.sql.Types.INTEGER);
			}
			
			ps.setInt(3, categoria.getCodigo());
			System.out.println("categoria codigo---");

			ps.executeUpdate();
			System.out.println(ps);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new KrakedevException("Error al insertar el cliente. Detalle : " + e.getMessage());
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
	
	public ArrayList<Categoria>recuperarTodos() throws KrakedevException{
		ArrayList<Categoria> categorias = new ArrayList<Categoria>();
		Connection con = null;
		PreparedStatement ps=null;
		ResultSet rs = null;
		Categoria cat = null;
		
		try {
			con = ConexionBDD.obtenerConexion();
			ps = con.prepareStatement("select c.codigo_cat, c.nombre,  cp.codigo_cat as codigoPadre, cp.nombre as nombreCatPadre from categorias c , categorias cp where c.categoria_padre = cp.codigo_cat ");
			rs = ps.executeQuery();
			
			while(rs.next()) {
				int codigo = rs.getInt("codigo_cat");
				String nombre = rs.getString("nombre");
				String nombre_categoria_padre = rs.getString("nombreCatPadre");
				int categoria_Padre = rs.getInt("codigoPadre");
				
				
				Categoria cat_padre = new Categoria(); 
				cat_padre.setCodigo(categoria_Padre);
				cat_padre.setNombre(nombre_categoria_padre);
				
				
				
				cat = new Categoria();
				cat.setCodigo(codigo);
				cat.setNombre(nombre);
				cat.setCategoriaPadre(cat_padre);
				categorias.add(cat);
				
			}
			
		} catch (KrakedevException e) {
			e.printStackTrace();
			throw e;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new KrakedevException("Error al consultar. Detalle : "+ e.getMessage());
		}
		
		return categorias;
	}
}
