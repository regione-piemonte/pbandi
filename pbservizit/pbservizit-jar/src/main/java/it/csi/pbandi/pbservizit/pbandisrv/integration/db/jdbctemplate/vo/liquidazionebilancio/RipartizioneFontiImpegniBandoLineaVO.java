/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.liquidazionebilancio;

import java.math.BigDecimal;
import java.sql.Date;

import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.GenericVO;

public class RipartizioneFontiImpegniBandoLineaVO extends GenericVO {
	private BigDecimal idImpegno;
	private BigDecimal progrBandoLineaIntervento;
	private String descImpegno;
	private String annoImpegno;
	private String annoEsercizio;
	private BigDecimal numeroImpegno;
	private BigDecimal disponibilitaLiquidare;
	private BigDecimal importoAttualeImpegno;
	private BigDecimal idTipoFondo;
	private String  descTipoFondo;
	private Date dtProvvedimento;
	private BigDecimal idCapitolo;
	private BigDecimal numeroCapitolo;
	private BigDecimal idSoggettoFinanziatore;
	private String descSoggFinanziatore;
	private String cig;
	private String annoPerente;
	private BigDecimal numeroPerente;
	
	public String getAnnoPerente() {
		return annoPerente;
	}
	public void setAnnoPerente(String annoPerente) {
		this.annoPerente = annoPerente;
	}
	public BigDecimal getNumeroPerente() {
		return numeroPerente;
	}
	public void setNumeroPerente(BigDecimal numeroPerente) {
		this.numeroPerente = numeroPerente;
	}
	public BigDecimal getIdCapitolo() {
		return idCapitolo;
	}
	public void setIdCapitolo(BigDecimal idCapitolo) {
		this.idCapitolo = idCapitolo;
	}

	public BigDecimal getNumeroCapitolo() {
		return numeroCapitolo;
	}
	public void setNumeroCapitolo(BigDecimal numeroCapitolo) {
		this.numeroCapitolo = numeroCapitolo;
	}
	public BigDecimal getIdSoggettoFinanziatore() {
		return idSoggettoFinanziatore;
	}
	public void setIdSoggettoFinanziatore(BigDecimal idSoggettoFinanziatore) {
		this.idSoggettoFinanziatore = idSoggettoFinanziatore;
	}

	public String getCig() {
		return cig;
	}
	public void setCig(String cig) {
		this.cig = cig;
	}

	
	public BigDecimal getIdImpegno() {
		return idImpegno;
	}
	public void setIdImpegno(BigDecimal idImpegno) {
		this.idImpegno = idImpegno;
	}
	public BigDecimal getProgrBandoLineaIntervento() {
		return progrBandoLineaIntervento;
	}
	public void setProgrBandoLineaIntervento(BigDecimal progrBandoLineaIntervento) {
		this.progrBandoLineaIntervento = progrBandoLineaIntervento;
	}
	public String getDescImpegno() {
		return descImpegno;
	}
	public void setDescImpegno(String descImpegno) {
		this.descImpegno = descImpegno;
	}
	public String getAnnoImpegno() {
		return annoImpegno;
	}
	public void setAnnoImpegno(String annoImpegno) {
		this.annoImpegno = annoImpegno;
	}
	public String getAnnoEsercizio() {
		return annoEsercizio;
	}
	public void setAnnoEsercizio(String annoEsercizio) {
		this.annoEsercizio = annoEsercizio;
	}
	public BigDecimal getNumeroImpegno() {
		return numeroImpegno;
	}
	public void setNumeroImpegno(BigDecimal numeroImpegno) {
		this.numeroImpegno = numeroImpegno;
	}
	public BigDecimal getDisponibilitaLiquidare() {
		return disponibilitaLiquidare;
	}
	public void setDisponibilitaLiquidare(BigDecimal disponibilitaLiquidare) {
		this.disponibilitaLiquidare = disponibilitaLiquidare;
	}
	public BigDecimal getImportoAttualeImpegno() {
		return importoAttualeImpegno;
	}
	public void setImportoAttualeImpegno(BigDecimal importoAttualeImpegno) {
		this.importoAttualeImpegno = importoAttualeImpegno;
	}
	public BigDecimal getIdTipoFondo() {
		return idTipoFondo;
	}
	public void setIdTipoFondo(BigDecimal idTipoFondo) {
		this.idTipoFondo = idTipoFondo;
	}
	public String getDescTipoFondo() {
		return descTipoFondo;
	}
	public void setDescTipoFondo(String descTipoFondo) {
		this.descTipoFondo = descTipoFondo;
	}
	public String getDescSoggFinanziatore() {
		return descSoggFinanziatore;
	}
	public void setDescSoggFinanziatore(String descSoggFinanziatore) {
		this.descSoggFinanziatore = descSoggFinanziatore;
	}
	public Date getDtProvvedimento() {
		return dtProvvedimento;
	}
	public void setDtProvvedimento(Date dtProvvedimento) {
		this.dtProvvedimento = dtProvvedimento;
	}

}
