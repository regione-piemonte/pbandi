/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.dto;

public class AffidamentoValidazione {
	
	private Long idAppalto;
	private String oggetto;
	private String codProcAgg;
	private String cigProcAgg;
	// Jira PBANDI-2775
	private Long idStatoAffidamento;
	private String descStatoAffidamento;
	// Jira PBANDI-2829
	private String esitoIntermedio;
	private String flagRettificaIntermedio;
	private String esitoDefinitivo;
	private String flagRettificaDefinitivo;
	// Jira PBANDI-2849.
	private String nomeFile;
	private Long idDocIndex;
	
	public Long getIdDocIndex() {
		return idDocIndex;
	}
	public void setIdDocIndex(Long idDocIndex) {
		this.idDocIndex = idDocIndex;
	}
	public String getNomeFile() {
		return nomeFile;
	}
	public void setNomeFile(String nomeFile) {
		this.nomeFile = nomeFile;
	}
	public String getEsitoIntermedio() {
		return esitoIntermedio;
	}
	public void setEsitoIntermedio(String esitoIntermedio) {
		this.esitoIntermedio = esitoIntermedio;
	}
	public String getFlagRettificaIntermedio() {
		return flagRettificaIntermedio;
	}
	public void setFlagRettificaIntermedio(String flagRettificaIntermedio) {
		this.flagRettificaIntermedio = flagRettificaIntermedio;
	}
	public String getEsitoDefinitivo() {
		return esitoDefinitivo;
	}
	public void setEsitoDefinitivo(String esitoDefinitivo) {
		this.esitoDefinitivo = esitoDefinitivo;
	}
	public String getFlagRettificaDefinitivo() {
		return flagRettificaDefinitivo;
	}
	public void setFlagRettificaDefinitivo(String flagRettificaDefinitivo) {
		this.flagRettificaDefinitivo = flagRettificaDefinitivo;
	}
	public String getDescStatoAffidamento() {
		return descStatoAffidamento;
	}
	public void setDescStatoAffidamento(String descStatoAffidamento) {
		this.descStatoAffidamento = descStatoAffidamento;
	}
	public Long getIdStatoAffidamento() {
		return idStatoAffidamento;
	}
	public void setIdStatoAffidamento(Long idStatoAffidamento) {
		this.idStatoAffidamento = idStatoAffidamento;
	}
	public Long getIdAppalto() {
		return idAppalto;
	}
	public void setIdAppalto(Long idAppalto) {
		this.idAppalto = idAppalto;
	}
	public String getOggetto() {
		return oggetto;
	}
	public void setOggetto(String oggetto) {
		this.oggetto = oggetto;
	}
	public String getCodProcAgg() {
		return codProcAgg;
	}
	public void setCodProcAgg(String codProcAgg) {
		this.codProcAgg = codProcAgg;
	}
	public String getCigProcAgg() {
		return cigProcAgg;
	}
	public void setCigProcAgg(String cigProcAgg) {
		this.cigProcAgg = cigProcAgg;
	}
	
	
}
