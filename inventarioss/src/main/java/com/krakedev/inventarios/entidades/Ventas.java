
package com.krakedev.inventarios.entidades;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;

public class Ventas {
	private int codigoCV;
	private Date fecha;
	private BigDecimal totalSinIVA;
	private BigDecimal iva;
	private BigDecimal total;

	private ArrayList<DetalleVentas> detalles;

	public Ventas() {
		super();
	}

	public Ventas(int codigoCV, Date fecha, BigDecimal totalSinIVA, BigDecimal iva, BigDecimal total,
			ArrayList<DetalleVentas> detalles) {
		super();
		this.codigoCV = codigoCV;
		this.fecha = fecha;
		this.totalSinIVA = totalSinIVA;
		this.iva = iva;
		this.total = total;
		this.detalles = detalles;
	}

	public int getCodigoCV() {
		return codigoCV;
	}

	public void setCodigoCV(int codigoCV) {
		this.codigoCV = codigoCV;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public BigDecimal getTotalSinIVA() {
		return totalSinIVA;
	}

	public void setTotalSinIVA(BigDecimal totalSinIVA) {
		this.totalSinIVA = totalSinIVA;
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

	public ArrayList<DetalleVentas> getDetalles() {
		return detalles;
	}

	public void setDetalles(ArrayList<DetalleVentas> detalles) {
		this.detalles = detalles;
	}

	@Override
	public String toString() {
		return "Ventas [codigoCV=" + codigoCV + ", fecha=" + fecha + ", totalSinIVA=" + totalSinIVA + ", iva=" + iva
				+ ", total=" + total + ", detalles=" + detalles + "]";
	}

}
