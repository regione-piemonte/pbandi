/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.vo.search;

import java.sql.Date;

public class VersioniVO {
	
	private Long idVersione;
	private Date dataRiferimento;
	private String nomeUtente; 
	private String cognomeUtente; 
	private String denom; 
	private Long progSoggettoProgetto; 
	private Long progSoggettoDomanda; 
	private Long idProgetto; 
	private Long idDomanda; 

	
	
	public Long getIdProgetto() {
		return idProgetto;
	}
	public void setIdProgetto(Long idProgetto) {
		this.idProgetto = idProgetto;
	}
	public Long getIdDomanda() {
		return idDomanda;
	}
	public void setIdDomanda(Long idDomanda) {
		this.idDomanda = idDomanda;
	}
	public Date getDataRiferimento() {
		return dataRiferimento;
	}
	public void setDataRiferimento(Date dataRiferimento) {
		this.dataRiferimento = dataRiferimento;
	}
	public Long getProgSoggettoProgetto() {
		return progSoggettoProgetto;
	}
	public void setProgSoggettoProgetto(Long progSoggettoProgetto) {
		this.progSoggettoProgetto = progSoggettoProgetto;
	}
	public Long getProgSoggettoDomanda() {
		return progSoggettoDomanda;
	}
	public void setProgSoggettoDomanda(Long progSoggettoDomanda) {
		this.progSoggettoDomanda = progSoggettoDomanda;
	}
	public String getDenom() {
		return denom;
	}
	public void setDenom(String denom) {
		this.denom = denom;
	}
	public String getNomeUtente() {
		return nomeUtente;
	}
	public void setNomeUtente(String nomeUtente) {
		this.nomeUtente = nomeUtente;
	}
	public String getCognomeUtente() {
		return cognomeUtente;
	}
	public void setCognomeUtente(String cognomeUtente) {
		this.cognomeUtente = cognomeUtente;
	}
	public Long getIdVersione() {
		return idVersione;
	}
	public void setIdVersione(Long idVersione) {
		this.idVersione = idVersione;
	}


	
	
	

}
