package it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo;

import java.math.BigDecimal;

public class DettaglioSoggettoConTipoAnagraficaVO extends DettaglioSoggettoConTipoAnagraficaSenzaBeneficiarioVO {
	private BigDecimal idRelazioneBeneficiario;
	private BigDecimal idEnteOperatore;
	private BigDecimal idEnteProgetto;
	private BigDecimal progettiValidi;
	private BigDecimal progettiNonValidi;
	private BigDecimal idUtente;
	
	public BigDecimal getIdUtente() {
		return idUtente;
	}
	public void setIdUtente(BigDecimal idUtente) {
		this.idUtente = idUtente;
	}
	public BigDecimal getProgettiValidi() {
		return progettiValidi;
	}
	public void setProgettiValidi(BigDecimal progettiValidi) {
		this.progettiValidi = progettiValidi;
	}
	public BigDecimal getProgettiNonValidi() {
		return progettiNonValidi;
	}
	public void setProgettiNonValidi(BigDecimal progettiNonValidi) {
		this.progettiNonValidi = progettiNonValidi;
	}
	public BigDecimal getIdRelazioneBeneficiario() {
		return idRelazioneBeneficiario;
	}
	public void setIdRelazioneBeneficiario(BigDecimal idRelazioneBeneficiario) {
		this.idRelazioneBeneficiario = idRelazioneBeneficiario;
	}
	public BigDecimal getIdEnteOperatore() {
		return idEnteOperatore;
	}
	public void setIdEnteOperatore(BigDecimal idEnteOperatore) {
		this.idEnteOperatore = idEnteOperatore;
	}
	public BigDecimal getIdEnteProgetto() {
		return idEnteProgetto;
	}
	public void setIdEnteProgetto(BigDecimal idEnteProgetto) {
		this.idEnteProgetto = idEnteProgetto;
	}
}
