/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.integration.vo;

import java.util.Date;

public class BeneficiarioVO {
	
	static final long serialVersionUID = 1;

	private Long id_soggetto = null;
	private Long id_progetto = null;
	private String codiceFiscale = null;
	private String cognome = null;
	private String nome = null;
	private String descrizione = null;
	private Integer id = null;
	private Date dataInizioValidita = null;
	private Long idFormaGiuridica;
	private Long idDimensioneImpresa;
	
	
	public Long getId_soggetto() {
		return id_soggetto;
	}
	public void setId_soggetto(Long id_soggetto) {
		this.id_soggetto = id_soggetto;
	}
	public Long getId_progetto() {
		return id_progetto;
	}
	public void setId_progetto(Long id_progetto) {
		this.id_progetto = id_progetto;
	}
	public String getCodiceFiscale() {
		return codiceFiscale;
	}
	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}
	public String getCognome() {
		return cognome;
	}
	public void setCognome(String cognome) {
		this.cognome = cognome;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getDescrizione() {
		return descrizione;
	}
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Date getDataInizioValidita() {
		return dataInizioValidita;
	}
	public void setDataInizioValidita(Date dataInizioValidita) {
		this.dataInizioValidita = dataInizioValidita;
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
	@Override
	public String toString() {
		return "BeneficiarioVO [id_soggetto=" + id_soggetto + ", id_progetto=" + id_progetto + ", codiceFiscale="
				+ codiceFiscale + ", cognome=" + cognome + ", nome=" + nome + ", descrizione=" + descrizione + ", id="
				+ id + ", dataInizioValidita=" + dataInizioValidita + ", idFormaGiuridica=" + idFormaGiuridica
				+ ", idDimensioneImpresa=" + idDimensioneImpresa + "]";
	}
	
	
}
