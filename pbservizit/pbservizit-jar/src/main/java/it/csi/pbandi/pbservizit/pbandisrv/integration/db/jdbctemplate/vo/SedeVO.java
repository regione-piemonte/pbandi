/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo;

import java.math.BigDecimal;

public class SedeVO extends GenericVO {
	
	
	private BigDecimal idProgetto;
	private BigDecimal idSoggettoBeneficiario;
	private BigDecimal idSede;
	private BigDecimal idTipoSede;
	private String descBreveTipoSede;
	private String descTipoSede;
	private BigDecimal idRegione;
	private String descRegione;
	private BigDecimal idProvincia;
	private String descProvincia;
	private String siglaProvincia;
	private BigDecimal idComune;
	private String descComune;
	private BigDecimal idIndirizzo;
	private String descIndirizzo;
	private String civico;
	private String cap;
	private String partitaIva;
	
	
	public BigDecimal getIdProgetto() {
		return idProgetto;
	}
	public void setIdProgetto(BigDecimal idProgetto) {
		this.idProgetto = idProgetto;
	}
	public BigDecimal getIdSoggettoBeneficiario() {
		return idSoggettoBeneficiario;
	}
	public void setIdSoggettoBeneficiario(BigDecimal idSoggettoBeneficiario) {
		this.idSoggettoBeneficiario = idSoggettoBeneficiario;
	}
	public BigDecimal getIdSede() {
		return idSede;
	}
	public void setIdSede(BigDecimal idSede) {
		this.idSede = idSede;
	}
	public BigDecimal getIdTipoSede() {
		return idTipoSede;
	}
	public void setIdTipoSede(BigDecimal idTipoSede) {
		this.idTipoSede = idTipoSede;
	}
	public String getDescBreveTipoSede() {
		return descBreveTipoSede;
	}
	public void setDescBreveTipoSede(String descBreveTipoSede) {
		this.descBreveTipoSede = descBreveTipoSede;
	}
	public String getDescTipoSede() {
		return descTipoSede;
	}
	public void setDescTipoSede(String descTipoSede) {
		this.descTipoSede = descTipoSede;
	}
	public BigDecimal getIdRegione() {
		return idRegione;
	}
	public void setIdRegione(BigDecimal idRegione) {
		this.idRegione = idRegione;
	}
	public String getDescRegione() {
		return descRegione;
	}
	public void setDescRegione(String descRegione) {
		this.descRegione = descRegione;
	}
	public BigDecimal getIdProvincia() {
		return idProvincia;
	}
	public void setIdProvincia(BigDecimal idProvincia) {
		this.idProvincia = idProvincia;
	}
	public String getDescProvincia() {
		return descProvincia;
	}
	public void setDescProvincia(String descProvincia) {
		this.descProvincia = descProvincia;
	}
	public BigDecimal getIdComune() {
		return idComune;
	}
	public void setIdComune(BigDecimal idComune) {
		this.idComune = idComune;
	}
	public String getDescComune() {
		return descComune;
	}
	public void setDescComune(String descComune) {
		this.descComune = descComune;
	}
	public BigDecimal getIdIndirizzo() {
		return idIndirizzo;
	}
	public void setIdIndirizzo(BigDecimal idIndirizzo) {
		this.idIndirizzo = idIndirizzo;
	}
	public String getDescIndirizzo() {
		return descIndirizzo;
	}
	public void setDescIndirizzo(String descIndirizzo) {
		this.descIndirizzo = descIndirizzo;
	}
	public String getCap() {
		return cap;
	}
	public void setCap(String cap) {
		this.cap = cap;
	}
	public void setCivico(String civico) {
		this.civico = civico;
	}
	public String getCivico() {
		return civico;
	}
	public void setSiglaProvincia(String siglaProvincia) {
		this.siglaProvincia = siglaProvincia;
	}
	public String getSiglaProvincia() {
		return siglaProvincia;
	}
	public void setPartitaIva(String partitaIva) {
		this.partitaIva = partitaIva;
	}
	public String getPartitaIva() {
		return partitaIva;
	}

}
