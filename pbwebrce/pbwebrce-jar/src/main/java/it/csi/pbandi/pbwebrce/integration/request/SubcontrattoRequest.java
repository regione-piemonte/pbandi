/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebrce.integration.request;

import java.util.Date;

public class SubcontrattoRequest {

	private Long idSubcontrattoAffidamento;
	private Long idFornitore;
	private Long idAppalto;
	private Long idSubcontraente;
	private String riferimentoSubcontratto;
	private Date dtSubcontratto;
	private Double importoSubcontratto;

	public Long getIdSubcontrattoAffidamento() {
		return idSubcontrattoAffidamento;
	}

	public void setIdSubcontrattoAffidamento(Long idSubcontrattoAffidamento) {
		this.idSubcontrattoAffidamento = idSubcontrattoAffidamento;
	}

	public Long getIdFornitore() {
		return idFornitore;
	}

	public void setIdFornitore(Long idFornitore) {
		this.idFornitore = idFornitore;
	}

	public Long getIdAppalto() {
		return idAppalto;
	}

	public void setIdAppalto(Long idAppalto) {
		this.idAppalto = idAppalto;
	}

	public Long getIdSubcontraente() {
		return idSubcontraente;
	}

	public void setIdSubcontraente(Long idSubcontraente) {
		this.idSubcontraente = idSubcontraente;
	}

	public String getRiferimentoSubcontratto() {
		return riferimentoSubcontratto;
	}

	public void setRiferimentoSubcontratto(String riferimentoSubcontratto) {
		this.riferimentoSubcontratto = riferimentoSubcontratto;
	}

	public Date getDtSubcontratto() {
		return dtSubcontratto;
	}

	public void setDtSubcontratto(Date dtSubcontratto) {
		this.dtSubcontratto = dtSubcontratto;
	}

	public Double getImportoSubcontratto() {
		return importoSubcontratto;
	}

	public void setImportoSubcontratto(Double importoSubcontratto) {
		this.importoSubcontratto = importoSubcontratto;
	}

}
