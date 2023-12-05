package com.krakedev.inventarios.bdd;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.krakedev.inventarios.entidades.Proveedor;
import com.krakedev.inventarios.entidades.TipoDocumentos;
import com.krakedev.inventarios.excepciones.KrakedevException;
import com.krakedev.inventarios.utils.ConexionBDD;

public class ProveedoresBDD {
	
	//buscar proveedores por subcadena
	public ArrayList<Proveedor>buscar(String subcadena) throws KrakedevException{
		ArrayList<Proveedor> proveedores = new ArrayList<Proveedor>();
		Connection con = null;
		PreparedStatement ps=null;
		ResultSet rs = null;
		Proveedor proveedor = null;
		
		try {
			con = ConexionBDD.obtenerConexion();
			ps = con.prepareStatement("select prov.identificador,prov.codigo_tipo_documento,td.descripcion,prov.nombre_proveedor,prov.telefono_proveedor,prov.correo_proveedor,prov.direccion_proveedor "
					+ "from proveedores prov, tipo_documentos td "
					+ "where prov.codigo_tipo_documento = td.codigo_tipo_documento "
					+ "and upper(nombre_proveedor) like ?");
			ps.setString(1,"%"+subcadena.toUpperCase()+"%");
			rs = ps.executeQuery();
			
			while(rs.next()) {
				String identificador = rs.getString("identificador");
				String codigoTipoDocumento = rs.getString("codigo_tipo_documento");
				String descripcionTipoDocumento = rs.getString("descripcion");
				String nombre = rs.getString("nombre_proveedor");
				String telefono = rs.getString("telefono_proveedor");
				String correo = rs.getString("correo_proveedor");
				String direccion = rs.getString("direccion_proveedor");
				TipoDocumentos td = new TipoDocumentos(codigoTipoDocumento, descripcionTipoDocumento);
				
				proveedor = new Proveedor(identificador, td, nombre, telefono, correo, direccion);
				proveedores.add(proveedor);
				
			}
			
		} catch (KrakedevException e) {
			e.printStackTrace();
			throw e;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new KrakedevException("Error al consultar. Detalle : "+ e.getMessage());
		}
		
		return proveedores;
	}
	
	//recuperar todos los tipos de documentos
	public ArrayList<TipoDocumentos>recuperarTodos() throws KrakedevException{
		ArrayList<TipoDocumentos> tipoDocumentos = new ArrayList<TipoDocumentos>();
		Connection con = null;
		PreparedStatement ps=null;
		ResultSet rs = null;
		TipoDocumentos tipdoc = null;
		
		try {
			con = ConexionBDD.obtenerConexion();
			ps = con.prepareStatement("select codigo_tipo_documento, descripcion from tipo_documentos ");
			rs = ps.executeQuery();
			
			while(rs.next()) {
				String codigo = rs.getString("codigo_tipo_documento");
				String descripcion = rs.getString("descripcion");
				
				
				tipdoc = new TipoDocumentos(codigo, descripcion);
				tipoDocumentos.add(tipdoc);
				
			}
			
		} catch (KrakedevException e) {
			e.printStackTrace();
			throw e;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new KrakedevException("Error al consultar. Detalle : "+ e.getMessage());
		}
		
		return tipoDocumentos;
	}
	
	
	public void insertar(Proveedor proveedor) throws KrakedevException {
		Connection con = null;
		PreparedStatement ps = null;
		try {
			con = ConexionBDD.obtenerConexion();
			ps = con.prepareStatement("insert into proveedores(identificador,codigo_tipo_documento,nombre_proveedor,telefono_proveedor,correo_proveedor,direccion_proveedor)" 
					+ "values(?,?,?,?,?,?);");
			
			ps.setString(1, proveedor.getIdentificador());
			ps.setString(2, proveedor.getTipoDocumento().getCodigo());
			ps.setString(3, proveedor.getNombre());
			ps.setString(4, proveedor.getTelefono() );
			ps.setString(5, proveedor.getCorreo());
			ps.setString(6, proveedor.getDireccion());

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
	
	//buscar proveedor por identificador 
	
	public Proveedor buscarPorIdentificador(String id) throws KrakedevException{
		
		Connection con = null;
		PreparedStatement ps=null;
		ResultSet rs = null;
		Proveedor proveedor = null;
		
		try {
			con = ConexionBDD.obtenerConexion();
			ps = con.prepareStatement("select prov.identificador,prov.codigo_tipo_documento,td.descripcion,prov.nombre_proveedor,prov.telefono_proveedor,prov.correo_proveedor,prov.direccion_proveedor "
					+ "from proveedores prov, tipo_documentos td "
					+ "where prov.identificador = ?");
			ps.setString(1,id);
			rs = ps.executeQuery();
			
			if(rs.next()) {
				String identificador = rs.getString("identificador");
				String codigoTipoDocumento = rs.getString("codigo_tipo_documento");
				String descripcionTipoDocumento = rs.getString("descripcion");
				String nombre = rs.getString("nombre_proveedor");
				String telefono = rs.getString("telefono_proveedor");
				String correo = rs.getString("correo_proveedor");
				String direccion = rs.getString("direccion_proveedor");
				TipoDocumentos td = new TipoDocumentos(codigoTipoDocumento, descripcionTipoDocumento);
				
				proveedor = new Proveedor(identificador, td, nombre, telefono, correo, direccion);
				
			}
			
		} catch (KrakedevException e) {
			e.printStackTrace();
			throw e;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new KrakedevException("Error al consultar. Detalle : "+ e.getMessage());
		}
		
		return proveedor;
	}
}
