package com.krakedev.inventarios.entidades;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;

public class Venta {
	private int codigo;
	private Date fecha;
	private BigDecimal totalSinIva;
	private BigDecimal iva;
	private BigDecimal total;
	
	private ArrayList<DetalleVenta>detallesVenta;

	public Venta() {
		
	}
	
	public Venta(int codigo, Date fecha, BigDecimal totalSinIva, BigDecimal iva, BigDecimal total,
			ArrayList<DetalleVenta> detallesVenta) {
		super();
		this.codigo = codigo;
		this.fecha = fecha;
		this.totalSinIva = totalSinIva;
		this.iva = iva;
		this.total = total;
		this.detallesVenta = detallesVenta;
	}

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public BigDecimal getTotalSinIva() {
		return totalSinIva;
	}

	public void setTotalSinIva(BigDecimal totalSinIva) {
		this.totalSinIva = totalSinIva;
	}

	public BigDecimal getIva() {
		return iva;
	}

	public void setIva(BigDecimal iva) {
		this.iva = iva;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	public ArrayList<DetalleVenta> getDetallesVenta() {
		return detallesVenta;
	}

	public void setDetallesVenta(ArrayList<DetalleVenta> detallesVenta) {
		this.detallesVenta = detallesVenta;
	}

	@Override
	public String toString() {
		return "Venta [codigo=" + codigo + ", fecha=" + fecha + ", totalSinIva=" + totalSinIva + ", iva=" + iva
				+ ", total=" + total + ", detallesVenta=" + detallesVenta + "]";
	}
	
}
