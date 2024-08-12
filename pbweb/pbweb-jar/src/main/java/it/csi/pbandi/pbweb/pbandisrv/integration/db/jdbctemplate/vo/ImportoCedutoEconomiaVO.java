package it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo;

import java.math.BigDecimal;

public class ImportoCedutoEconomiaVO extends GenericVO{
	private Double importoCeduto;
	private BigDecimal idProgettoCedente;
	
	public Double getImportoCeduto() {
		return importoCeduto;
	}
	public void setImportoCeduto(Double importoCeduto) {
		this.importoCeduto = importoCeduto;
	}
	public BigDecimal getIdProgettoCedente() {
		return idProgettoCedente;
	}
	public void setIdProgettoCedente(BigDecimal idProgettoCedente) {
		this.idProgettoCedente = idProgettoCedente;
	}
}
