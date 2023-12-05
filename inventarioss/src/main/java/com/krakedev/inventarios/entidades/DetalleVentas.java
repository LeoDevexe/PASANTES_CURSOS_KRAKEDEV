package com.krakedev.inventarios.entidades;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)

public class DetalleVentas {
	private int codigoDetalleVentas;
	private Ventas cabeceraVentas;
	private Producto producto;
	private int cantidad;
	private BigDecimal precioVenta;
	private BigDecimal subtotal;
	private BigDecimal subtotalConIva;

	public DetalleVentas() {
		super();
	}

	public DetalleVentas(int codigoDetalleVentas, Ventas cabeceraVentas, Producto producto, int cantidad,
			BigDecimal precioVenta, BigDecimal subtotal, BigDecimal subtotalConIva) {
		super();
		this.codigoDetalleVentas = codigoDetalleVentas;
		this.cabeceraVentas = cabeceraVentas;
		this.producto = producto;
		this.cantidad = cantidad;
		this.precioVenta = precioVenta;
		this.subtotal = subtotal;
		this.subtotalConIva = subtotalConIva;
	}

	public int getCodigoDetalleVentas() {
		return codigoDetalleVentas;
	}

	public void setCodigoDetalleVentas(int codigoDetalleVentas) {
		this.codigoDetalleVentas = codigoDetalleVentas;
	}

	public Ventas getCabeceraVentas() {
		return cabeceraVentas;
	}

	public void setCabeceraVentas(Ventas cabeceraVentas) {
		this.cabeceraVentas = cabeceraVentas;
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

	public BigDecimal getPrecioVenta() {
		return precioVenta;
	}

	public void setPrecioVenta(BigDecimal precioVenta) {
		this.precioVenta = precioVenta;
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
		return "DetalleVentas [codigoDetalleVentas=" + codigoDetalleVentas + ", cabeceraVentas=" + cabeceraVentas
				+ ", producto=" + producto + ", cantidad=" + cantidad + ", precioVenta=" + precioVenta + ", subtotal="
				+ subtotal + ", subtotalConIva=" + subtotalConIva + "]";
	}

}