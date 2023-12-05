package com.krakedev.inventarios.entidades;

import java.math.BigDecimal;

public class DetalleVenta {
	private int codigo;
	private Venta cabecera;
	private Producto producto;
	private int cantidad;
	private BigDecimal subtotal;
	private BigDecimal subtotalConIva;
	
	public DetalleVenta() {
		
	}
	
	public DetalleVenta(int codigo, Venta cabecera, Producto producto, int cantidad, BigDecimal subtotal,
			BigDecimal subtotalConIva) {
		super();
		this.codigo = codigo;
		this.cabecera = cabecera;
		this.producto = producto;
		this.cantidad = cantidad;
		this.subtotal = subtotal;
		this.subtotalConIva = subtotalConIva;
	}
	
	public int getCodigo() {
		return codigo;
	}
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}
	public Venta getCabecera() {
		return cabecera;
	}
	public void setCabecera(Venta cabecera) {
		this.cabecera = cabecera;
	}
	public Producto getProducto() {
		return producto;
	}
	public void setProducto(Producto producto) {
		this.producto = producto;
	}
	public int getCantidad() {
		return cantidad;
	}
	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}
	public BigDecimal getSubtotal() {
		return subtotal;
	}
	public void setSubtotal(BigDecimal subtotal) {
		this.subtotal = subtotal;
	}
	public BigDecimal getSubtotalConIva() {
		return subtotalConIva;
	}
	public void setSubtotalConIva(BigDecimal subtotalConIva) {
		this.subtotalConIva = subtotalConIva;
	}
	@Override
	public String toString() {
		return "DetalleVenta [codigo=" + codigo + ", cabecera=" + cabecera + ", producto=" + producto + ", cantidad="
				+ cantidad + ", subtotal=" + subtotal + ", subtotalConIva=" + subtotalConIva + "]";
	}
	
	
}
