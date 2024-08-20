/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebrce.integration.vo.affidamenti;

import java.math.BigDecimal;
import java.util.Date;

public class SubcontrattoAffidVO {

	private BigDecimal idSubcontrattoAffidamento;
	private BigDecimal idFornitore;
	private BigDecimal idAppalto;
	private BigDecimal idSubcontraente;
	private Date dtSubcontratto;
	private String riferimentoSubcontratto;
	private BigDecimal importoSubcontratto;
	private String denominazioneSubcontraente;
	private String cfPivaSubcontraente;

	public BigDecimal getIdSubcontrattoAffidamento() {
		return idSubcontrattoAffidamento;
	}

	public void setIdSubcontrattoAffidamento(BigDecimal idSubcontrattoAffidamento) {
		this.idSubcontrattoAffidamento = idSubcontrattoAffidamento;
	}

	public BigDecimal getIdFornitore() {
		return idFornitore;
	}

	public void setIdFornitore(BigDecimal idFornitore) {
		this.idFornitore = idFornitore;
	}

	public BigDecimal getIdAppalto() {
		return idAppalto;
	}

	public void setIdAppalto(BigDecimal idAppalto) {
		this.idAppalto = idAppalto;
	}

	public BigDecimal getIdSubcontraente() {
		return idSubcontraente;
	}

	public void setIdSubcontraente(BigDecimal idSubcontraente) {
		this.idSubcontraente = idSubcontraente;
	}

	public Date getDtSubcontratto() {
		return dtSubcontratto;
	}

	public void setDtSubcontratto(Date dtSubcontratto) {
		this.dtSubcontratto = dtSubcontratto;
	}

	public String getRiferimentoSubcontratto() {
		return riferimentoSubcontratto;
	}

	public void setRiferimentoSubcontratto(String riferimentoSubcontratto) {
		this.riferimentoSubcontratto = riferimentoSubcontratto;
	}

	public BigDecimal getImportoSubcontratto() {
		return importoSubcontratto;
	}

	public void setImportoSubcontratto(BigDecimal importoSubcontratto) {
		this.importoSubcontratto = importoSubcontratto;
	}

	public String getDenominazioneSubcontraente() {
		return denominazioneSubcontraente;
	}

	public void setDenominazioneSubcontraente(String denominazioneSubcontraente) {
		this.denominazioneSubcontraente = denominazioneSubcontraente;
	}

	public String getCfPivaSubcontraente() {
		return cfPivaSubcontraente;
	}

	public void setCfPivaSubcontraente(String cfPivaSubcontraente) {
		this.cfPivaSubcontraente = cfPivaSubcontraente;
	}

}
