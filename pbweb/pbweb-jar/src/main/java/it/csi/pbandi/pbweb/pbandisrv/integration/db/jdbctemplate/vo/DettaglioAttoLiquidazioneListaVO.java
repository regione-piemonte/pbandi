package it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo;

import java.math.BigDecimal;

public class DettaglioAttoLiquidazioneListaVO extends GenericVO {
  	private BigDecimal idAttoLiquidazione;
	private String codiceVisualizzatoProgetto;
	private String denominazioneBeneficiarioBil;
	private String estremiAtto;
	private String descStatoAtto;
	private String descBreveStatoAtto;	

	public String getDescBreveStatoAtto() {
		return descBreveStatoAtto;
	}

	public void setDescBreveStatoAtto(String descBreveStatoAtto) {
		this.descBreveStatoAtto = descBreveStatoAtto;
	}

	public DettaglioAttoLiquidazioneListaVO() {
		super();
	}
	
	public String getCodiceVisualizzatoProgetto() {
		return codiceVisualizzatoProgetto;
	}
	public void setCodiceVisualizzatoProgetto(String codiceVisualizzatoProgetto) {
		this.codiceVisualizzatoProgetto = codiceVisualizzatoProgetto;
	}
	public String getDenominazioneBeneficiarioBil() {
		return denominazioneBeneficiarioBil;
	}
	public void setDenominazioneBeneficiarioBil(String denominazioneBeneficiarioBil) {
		this.denominazioneBeneficiarioBil = denominazioneBeneficiarioBil;
	}
	public String getEstremiAtto() {
		return estremiAtto;
	}
	public void setEstremiAtto(String estremiAtto) {
		this.estremiAtto = estremiAtto;
	}
	public String getDescStatoAtto() {
		return descStatoAtto;
	}
	public void setDescStatoAtto(String descStatoAtto) {
		this.descStatoAtto = descStatoAtto;
	}

	public BigDecimal getIdAttoLiquidazione() {
		return idAttoLiquidazione;
	}

	public void setIdAttoLiquidazione(BigDecimal idAttoLiquidazione) {
		this.idAttoLiquidazione = idAttoLiquidazione;
	}
}
