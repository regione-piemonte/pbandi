/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebrce.pbandisrv.dto.gestioneaffidamenti;

import java.util.Date;

public class SubcontrattoAffidDTO {

	private Long idSubcontrattoAffidamento;
	private Long idFornitore;
	private Long idAppalto;
	private Long idSubcontraente;
	private Date dtSubcontratto;
	private String riferimentoSubcontratto;
	private Double importoSubcontratto;
	private String denominazioneSubcontraente;
	private String cfPivaSubcontraente;

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

	public Double getImportoSubcontratto() {
		return importoSubcontratto;
	}

	public void setImportoSubcontratto(Double importoSubcontratto) {
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
