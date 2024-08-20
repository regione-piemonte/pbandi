/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.vo;

public class CercaPropostaVarazioneStatoCreditoSearchVO {
	
	private String codiceFiscale; 
	private String partitaIVA; 
	private String denominazione; 
	private String codiceProgetto; 
	private Long idAgevolazione; 
	private Long idBando; 
	private Long idStatoProposta;
	private Long idSoggettoCF; 
	private Long idSoggettoDenom; 
	private Long idSoggProg; 
	private String titoloBando; 
	private Long percSconfinamentoDa;
	private Long percSconfinamentoA;
	private Long idStatoAttuale; 
	private Long idStatoCreditoProposto; 
	private String codiceFinpis;
	

	
	public Long getIdStatoCreditoProposto() {
		return idStatoCreditoProposto;
	}
	public void setIdStatoCreditoProposto(Long idStatoCreditoProposto) {
		this.idStatoCreditoProposto = idStatoCreditoProposto;
	}
	public Long getIdStatoAttuale() {
		return idStatoAttuale;
	}
	public void setIdStatoAttuale(Long idStatoAttuale) {
		this.idStatoAttuale = idStatoAttuale;
	}
	public Long getPercSconfinamentoDa() {
		return percSconfinamentoDa;
	}
	public void setPercSconfinamentoDa(Long percSconfinamentoDa) {
		this.percSconfinamentoDa = percSconfinamentoDa;
	}
	public Long getPercSconfinamentoA() {
		return percSconfinamentoA;
	}
	public void setPercSconfinamentoA(Long percSconfinamentoA) {
		this.percSconfinamentoA = percSconfinamentoA;
	}
	public void setTitoloBando(String titoloBando) {
		this.titoloBando = titoloBando;
	}
	public String getTitoloBando() {
		return titoloBando;
	}
	
	public Long getIdSoggProg() {
		return idSoggProg;
	}
	public void setIdSoggProg(Long idSoggProg) {
		this.idSoggProg = idSoggProg;
	}

	public Long getIdSoggettoCF() {
		return idSoggettoCF;
	}
	public void setIdSoggettoCF(Long idSoggettoCF) {
		this.idSoggettoCF = idSoggettoCF;
	}
	public Long getIdSoggettoDenom() {
		return idSoggettoDenom;
	}
	public void setIdSoggettoDenom(Long idSoggettoDenom) {
		this.idSoggettoDenom = idSoggettoDenom;
	}
	public String getCodiceFiscale() {
		return codiceFiscale;
	}
	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}

	public String getPartitaIVA() {
		return partitaIVA;
	}
	public void setPartitaIVA(String partitaIVA) {
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
	public String getCodiceFinpis() {
		return codiceFinpis;
	}
	public void setCodiceFinpis(String codiceFinpis) {
		this.codiceFinpis = codiceFinpis;
	} 
	
	
	

}
