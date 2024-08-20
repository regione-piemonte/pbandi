/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.vo;

import java.math.BigDecimal;
import java.util.Date;

public class Riassicurazione_SoggettiCorrelatiVO {
	
	private Long idProgetto;
	private Long idSoggetto;
	private String ndg;
	private Long idEnte;
	private Long progrSoggettiCorrelati;
	private Long progrSoggettoDomanda;
	private Long idRiassicurazione;

	private String nome1;
	private String nome2;
	private String codiceFiscale;
	private String statoProgetto;
	private Date dataEscussione;
	private Date dataScarico;
	private BigDecimal importoRichiesto;
	private BigDecimal importoAmmesso;
	
	
	
	
	public Riassicurazione_SoggettiCorrelatiVO(Long idProgetto, Long idSoggetto, String ndg, Long idEnte,
			Long progrSoggettiCorrelati, Long progrSoggettoDomanda, Long idRiassicurazione, String nome1, String nome2,
			String codiceFiscale, String statoProgetto, Date dataEscussione, Date dataScarico,
			BigDecimal importoRichiesto, BigDecimal importoAmmesso) {
		this.idProgetto = idProgetto;
		this.idSoggetto = idSoggetto;
		this.ndg = ndg;
		this.idEnte = idEnte;
		this.progrSoggettiCorrelati = progrSoggettiCorrelati;
		this.progrSoggettoDomanda = progrSoggettoDomanda;
		this.idRiassicurazione = idRiassicurazione;
		this.nome1 = nome1;
		this.nome2 = nome2;
		this.codiceFiscale = codiceFiscale;
		this.statoProgetto = statoProgetto;
		this.dataEscussione = dataEscussione;
		this.dataScarico = dataScarico;
		this.importoRichiesto = importoRichiesto;
		this.importoAmmesso = importoAmmesso;
	}




	public Riassicurazione_SoggettiCorrelatiVO() {
	}




	public Long getIdProgetto() {
		return idProgetto;
	}




	public void setIdProgetto(Long idProgetto) {
		this.idProgetto = idProgetto;
	}




	public Long getIdSoggetto() {
		return idSoggetto;
	}




	public void setIdSoggetto(Long idSoggetto) {
		this.idSoggetto = idSoggetto;
	}




	public String getNdg() {
		return ndg;
	}




	public void setNdg(String ndg) {
		this.ndg = ndg;
	}




	public Long getIdEnte() {
		return idEnte;
	}




	public void setIdEnte(Long idEnte) {
		this.idEnte = idEnte;
	}




	public Long getProgrSoggettiCorrelati() {
		return progrSoggettiCorrelati;
	}




	public void setProgrSoggettiCorrelati(Long progrSoggettiCorrelati) {
		this.progrSoggettiCorrelati = progrSoggettiCorrelati;
	}




	public Long getProgrSoggettoDomanda() {
		return progrSoggettoDomanda;
	}




	public void setProgrSoggettoDomanda(Long progrSoggettoDomanda) {
		this.progrSoggettoDomanda = progrSoggettoDomanda;
	}




	public Long getIdRiassicurazione() {
		return idRiassicurazione;
	}




	public void setIdRiassicurazione(Long idRiassicurazione) {
		this.idRiassicurazione = idRiassicurazione;
	}




	public String getNome1() {
		return nome1;
	}




	public void setNome1(String nome1) {
		this.nome1 = nome1;
	}




	public String getNome2() {
		return nome2;
	}




	public void setNome2(String nome2) {
		this.nome2 = nome2;
	}




	public String getCodiceFiscale() {
		return codiceFiscale;
	}




	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}




	public String getStatoProgetto() {
		return statoProgetto;
	}




	public void setStatoProgetto(String statoProgetto) {
		this.statoProgetto = statoProgetto;
	}




	public Date getDataEscussione() {
		return dataEscussione;
	}




	public void setDataEscussione(Date dataEscussione) {
		this.dataEscussione = dataEscussione;
	}




	public Date getDataScarico() {
		return dataScarico;
	}




	public void setDataScarico(Date dataScarico) {
		this.dataScarico = dataScarico;
	}




	public BigDecimal getImportoRichiesto() {
		return importoRichiesto;
	}




	public void setImportoRichiesto(BigDecimal importoRichiesto) {
		this.importoRichiesto = importoRichiesto;
	}




	public BigDecimal getImportoAmmesso() {
		return importoAmmesso;
	}




	public void setImportoAmmesso(BigDecimal importoAmmesso) {
		this.importoAmmesso = importoAmmesso;
	}




	@Override
	public String toString() {
		return "Riassicurazione_SoggettiCorrelatiVO [idProgetto=" + idProgetto + ", idSoggetto=" + idSoggetto + ", ndg="
				+ ndg + ", idEnte=" + idEnte + ", progrSoggettiCorrelati=" + progrSoggettiCorrelati
				+ ", progrSoggettoDomanda=" + progrSoggettoDomanda + ", idRiassicurazione=" + idRiassicurazione
				+ ", nome1=" + nome1 + ", nome2=" + nome2 + ", codiceFiscale=" + codiceFiscale + ", statoProgetto="
				+ statoProgetto + ", dataEscussione=" + dataEscussione + ", dataScarico=" + dataScarico
				+ ", importoRichiesto=" + importoRichiesto + ", importoAmmesso=" + importoAmmesso + "]";
	}
	



	
	
	
}
