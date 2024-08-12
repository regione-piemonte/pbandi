package it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo;

import java.math.BigDecimal;

public class DettTipolAiutiAssociatoVO extends GenericVO {
	
	private BigDecimal idDettTipolAiuti;
	private BigDecimal progrBandoLineaIntervento;
	private String descrizione;
	private BigDecimal idTipoAiuto;
	private String link;
	private String codice;
	private String flagFittizio;
	private String descrizioneTipoAiuto;
	
	public String getDescrizioneTipoAiuto() {
		return descrizioneTipoAiuto;
	}
	public void setDescrizioneTipoAiuto(String descrizioneTipoAiuto) {
		this.descrizioneTipoAiuto = descrizioneTipoAiuto;
	}
	public BigDecimal getIdDettTipolAiuti() {
		return idDettTipolAiuti;
	}
	public void setIdDettTipolAiuti(BigDecimal idDettTipolAiuti) {
		this.idDettTipolAiuti = idDettTipolAiuti;
	}
	public BigDecimal getProgrBandoLineaIntervento() {
		return progrBandoLineaIntervento;
	}
	public void setProgrBandoLineaIntervento(BigDecimal progrBandoLineaIntervento) {
		this.progrBandoLineaIntervento = progrBandoLineaIntervento;
	}
	public String getDescrizione() {
		return descrizione;
	}
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	public BigDecimal getIdTipoAiuto() {
		return idTipoAiuto;
	}
	public void setIdTipoAiuto(BigDecimal idTipoAiuto) {
		this.idTipoAiuto = idTipoAiuto;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public String getCodice() {
		return codice;
	}
	public void setCodice(String codice) {
		this.codice = codice;
	}
	public String getFlagFittizio() {
		return flagFittizio;
	}
	public void setFlagFittizio(String flagFittizio) {
		this.flagFittizio = flagFittizio;
	}

}
