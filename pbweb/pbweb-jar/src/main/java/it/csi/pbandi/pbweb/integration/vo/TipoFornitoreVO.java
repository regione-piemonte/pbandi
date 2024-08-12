package it.csi.pbandi.pbweb.integration.vo;

import java.math.BigDecimal;

public class TipoFornitoreVO {
	private String descTipoSoggetto;
	private BigDecimal idTipoSoggetto;
	private String descBreveTipoSoggetto;
	
	public String getDescTipoSoggetto() {
		return descTipoSoggetto;
	}
	public void setDescTipoSoggetto(String descTipoSoggetto) {
		this.descTipoSoggetto = descTipoSoggetto;
	}
	public BigDecimal getIdTipoSoggetto() {
		return idTipoSoggetto;
	}
	public void setIdTipoSoggetto(BigDecimal idTipoSoggetto) {
		this.idTipoSoggetto = idTipoSoggetto;
	}
	public String getDescBreveTipoSoggetto() {
		return descBreveTipoSoggetto;
	}
	public void setDescBreveTipoSoggetto(String descBreveTipoSoggetto) {
		this.descBreveTipoSoggetto = descBreveTipoSoggetto;
	}
}
