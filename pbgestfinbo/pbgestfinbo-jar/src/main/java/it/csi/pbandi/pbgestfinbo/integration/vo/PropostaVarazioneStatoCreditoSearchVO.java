/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.vo;

public class PropostaVarazioneStatoCreditoSearchVO {
	
	private String codiceFiscale; 
	private Long partitaIVA; 
	private String denominazione; 
	private String codiceProgetto; 
	private Long idAgevolazione; 
	private Long idBando; 
	private Long idStatoProposta;
	private Long idSoggetto; 
	private Long idSoggProg; 
	
	
	public Long getIdSoggProg() {
		return idSoggProg;
	}
	public void setIdSoggProg(Long idSoggProg) {
		this.idSoggProg = idSoggProg;
	}
	public Long getIdSoggetto() {
		return idSoggetto;
	}
	public void setIdSoggetto(Long idSoggetto) {
		this.idSoggetto = idSoggetto;
	}
	public String getCodiceFiscale() {
		return codiceFiscale;
	}
	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}
	public Long getPartitaIVA() {
		return partitaIVA;
	}
	public void setPartitaIVA(Long partitaIVA) {
		this.partitaIVA = partitaIVA;
	}
	public String getDenominazione() {
		return denominazione;
	}
	public void setDenominazione(String denominazione) {
		this.denominazione = denominazione;
	}
	public String getCodiceProgetto() {
		return codiceProgetto;
	}
	public void setCodiceProgetto(String codiceProgetto) {
		this.codiceProgetto = codiceProgetto;
	}
	public Long getIdAgevolazione() {
		return idAgevolazione;
	}
	public void setIdAgevolazione(Long idAgevolazione) {
		this.idAgevolazione = idAgevolazione;
	}
	public Long getIdBando() {
		return idBando;
	}
	public void setIdBando(Long idBando) {
		this.idBando = idBando;
	}
	public Long getIdStatoProposta() {
		return idStatoProposta;
	}
	public void setIdStatoProposta(Long idStatoProposta) {
		this.idStatoProposta = idStatoProposta;
	} 
	
	
	

}
