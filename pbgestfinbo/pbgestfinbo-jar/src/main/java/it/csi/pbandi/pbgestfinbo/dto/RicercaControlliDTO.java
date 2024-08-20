/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.dto;

import java.math.BigDecimal;
import java.sql.Date;

public class RicercaControlliDTO {
	
	private BigDecimal idSoggetto; 
	private BigDecimal progrBandoLinea;
	private BigDecimal idProgetto; 
	private BigDecimal idStatoControllo; 
	private BigDecimal numeroCampionamento; 
	private BigDecimal importoDaControllareDa; 
	private BigDecimal importoDaControllareA; 
	private BigDecimal importoIrregolareDa; 
	private BigDecimal importoIrregolareA;
	private String numProtocollo; 
	private int idAutoritaControllante;
	private String tipoControllo;
	private Date dataInizio; 
	private Date dataFine; 
	private int idStatoIter; 
	private String value; 
	
	
	
	

	
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public int getIdStatoIter() {
		return idStatoIter;
	}
	public void setIdStatoIter(int idStatoIter) {
		this.idStatoIter = idStatoIter;
	}
	public Date getDataInizio() {
		return dataInizio;
	}
	public void setDataInizio(Date dataInizio) {
		this.dataInizio = dataInizio;
	}
	public Date getDataFine() {
		return dataFine;
	}
	public void setDataFine(Date dataFine) {
		this.dataFine = dataFine;
	}
	
	public String getNumProtocollo() {
		return numProtocollo;
	}
	public void setNumProtocollo(String numProtocollo) {
		this.numProtocollo = numProtocollo;
	}
	public int getIdAutoritaControllante() {
		return idAutoritaControllante;
	}
	public void setIdAutoritaControllante(int idAutoritaControllante) {
		this.idAutoritaControllante = idAutoritaControllante;
	}
	public String getTipoControllo() {
		return tipoControllo;
	}
	public void setTipoControllo(String tipoControllo) {
		this.tipoControllo = tipoControllo;
	}
	public BigDecimal getIdSoggetto() {
		return idSoggetto;
	}
	public void setIdSoggetto(BigDecimal idSoggetto) {
		this.idSoggetto = idSoggetto;
	}
	public BigDecimal getProgrBandoLinea() {
		return progrBandoLinea;
	}
	public void setProgrBandoLinea(BigDecimal progrBandoLinea) {
		this.progrBandoLinea = progrBandoLinea;
	}
	public BigDecimal getIdProgetto() {
		return idProgetto;
	}
	public void setIdProgetto(BigDecimal idProgetto) {
		this.idProgetto = idProgetto;
	}
	public BigDecimal getIdStatoControllo() {
		return idStatoControllo;
	}
	public void setIdStatoControllo(BigDecimal idStatoControllo) {
		this.idStatoControllo = idStatoControllo;
	}
	public BigDecimal getNumeroCampionamento() {
		return numeroCampionamento;
	}
	public void setNumeroCampionamento(BigDecimal numeroCampionamento) {
		this.numeroCampionamento = numeroCampionamento;
	}
	public BigDecimal getImportoDaControllareDa() {
		return importoDaControllareDa;
	}
	public void setImportoDaControllareDa(BigDecimal importoDaControllareDa) {
		this.importoDaControllareDa = importoDaControllareDa;
	}
	public BigDecimal getImportoDaControllareA() {
		return importoDaControllareA;
	}
	public void setImportoDaControllareA(BigDecimal importoDaControllareA) {
		this.importoDaControllareA = importoDaControllareA;
	}
	public BigDecimal getImportoIrregolareDa() {
		return importoIrregolareDa;
	}
	public void setImportoIrregolareDa(BigDecimal importoIrregolareDa) {
		this.importoIrregolareDa = importoIrregolareDa;
	}
	public BigDecimal getImportoIrregolareA() {
		return importoIrregolareA;
	}
	public void setImportoIrregolareA(BigDecimal importoIrregolareA) {
		this.importoIrregolareA = importoIrregolareA;
	} 
	
	
}
