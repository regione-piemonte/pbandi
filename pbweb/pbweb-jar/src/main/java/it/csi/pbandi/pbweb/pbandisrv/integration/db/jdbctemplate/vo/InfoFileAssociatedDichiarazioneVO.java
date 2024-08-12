package it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo;

import java.math.BigDecimal;
import java.util.Date;


public class InfoFileAssociatedDichiarazioneVO extends InfoFileVO {
	
	private String descTipoDichiarazioneSpesa;
	private Date dtDichiarazione;
	private BigDecimal idDichiarazioneSpesa;
 
	
	public Date getDtDichiarazione() {
		return dtDichiarazione;
	}
	public void setDtDichiarazione(Date dtDichiarazione) {
		this.dtDichiarazione = dtDichiarazione;
	}
	public BigDecimal getIdDichiarazioneSpesa() {
		return idDichiarazioneSpesa;
	}
	public void setIdDichiarazioneSpesa(BigDecimal idDichiarazioneSpesa) {
		this.idDichiarazioneSpesa = idDichiarazioneSpesa;
	}
 
	public String getDescTipoDichiarazioneSpesa() {
		return descTipoDichiarazioneSpesa;
	}
	public void setDescTipoDichiarazioneSpesa(String descTipoDichiarazioneSpesa) {
		this.descTipoDichiarazioneSpesa = descTipoDichiarazioneSpesa;
	}

 
	
	
 

}
