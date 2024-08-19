/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweberog.integration.vo;

import java.sql.Date;

public class BeneficiarioVO {

	static final long serialVersionUID = 1;
	
	private Long idSoggetto;
	private Long idProgetto;
	private String codiceFiscale;
//	private String cognome;
//	private String nome;
	private String descrizione;
	private Long idFormaGiuridica;
	private Long idDimensioneImpresa;
	private Date dataInizioValidita;
	
	public Long getIdSoggetto() {
		return idSoggetto;
	}
	public void setIdSoggetto(Long idSoggetto) {
		this.idSoggetto = idSoggetto;
	}
	public Long getIdProgetto() {
		return idProgetto;
	}
	public void setIdProgetto(Long idProgetto) {
		this.idProgetto = idProgetto;
	}
	public String getCodiceFiscale() {
		return codiceFiscale;
	}
	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}
//	public String getCognome() {
//		return cognome;
//	}
//	public void setCognome(String cognome) {
//		this.cognome = cognome;
//	}
//	public String getNome() {
//		return nome;
//	}
//	public void setNome(String nome) {
//		this.nome = nome;
//	}
	public String getDescrizione() {
		return descrizione;
	}
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	public Long getIdFormaGiuridica() {
		return idFormaGiuridica;
	}
	public void setIdFormaGiuridica(Long idFormaGiuridica) {
		this.idFormaGiuridica = idFormaGiuridica;
	}
	public Long getIdDimensioneImpresa() {
		return idDimensioneImpresa;
	}
	public void setIdDimensioneImpresa(Long idDimensioneImpresa) {
		this.idDimensioneImpresa = idDimensioneImpresa;
	}
	public Date getDataInizioValidita() {
		return dataInizioValidita;
	}
	public void setDataInizioValidita(Date dataInizioValidita) {
		this.dataInizioValidita = dataInizioValidita;
	}

	@Override
	public String toString() {
		return "BeneficiarioVO [idSoggetto=" + idSoggetto  
				 + ", idProgetto=" + idProgetto 
				+ ", codiceFiscale=" + codiceFiscale 
//				+ ", cognome=" + cognome 
//				+ ", nome=" + nome 
				+ ", descrizione=" + descrizione
				+ ", idFormaGiuridica=" + idFormaGiuridica 
				+ ", idDimensioneImpresa=" + idDimensioneImpresa
				+ ", dataInizioValidita=" + dataInizioValidita + "]";
	}
	
}
