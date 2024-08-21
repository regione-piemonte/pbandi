/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.impegnobilancio;

import java.math.BigDecimal;

import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.GenericVO;

public class BandolineaImpegnoSoggettoBilVO extends GenericVO {
	

    private BigDecimal idImpegno;
    private BigDecimal idEnteCap;
    private BigDecimal idEnteProvv;
    private String descImpegno;
    private BigDecimal progrBandolineaIntervento;
    private String nomeBandolinea;
    private BigDecimal dotazioneFinanziaria;
    private String annoImpegno;
    private String numeroImpegno;
    private BigDecimal importoAttualeImpegno;
    private BigDecimal disponibilitaLiquidare;
    private BigDecimal idTipoFondo;
    private String descTipoFondo;
    
   
	
	public BigDecimal getIdImpegno() {
		return idImpegno;
	}
	public void setIdImpegno(BigDecimal idImpegno) {
		this.idImpegno = idImpegno;
	}
	public String getDescImpegno() {
		return descImpegno;
	}
	public void setDescImpegno(String descImpegno) {
		this.descImpegno = descImpegno;
	}
	public BigDecimal getProgrBandolineaIntervento() {
		return progrBandolineaIntervento;
	}
	public void setProgrBandolineaIntervento(BigDecimal progrBandolineaIntervento) {
		this.progrBandolineaIntervento = progrBandolineaIntervento;
	}
	public String getNomeBandolinea() {
		return nomeBandolinea;
	}
	public void setNomeBandolinea(String nomeBandolinea) {
		this.nomeBandolinea = nomeBandolinea;
	}
	public BigDecimal getDotazioneFinanziaria() {
		return dotazioneFinanziaria;
	}
	public void setDotazioneFinanziaria(BigDecimal dotazioneFinanziaria) {
		this.dotazioneFinanziaria = dotazioneFinanziaria;
	}
	public String getAnnoImpegno() {
		return annoImpegno;
	}
	public void setAnnoImpegno(String annoImpegno) {
		this.annoImpegno = annoImpegno;
	}
	public String getNumeroImpegno() {
		return numeroImpegno;
	}
	public void setNumeroImpegno(String numeroImpegno) {
		this.numeroImpegno = numeroImpegno;
	}
	public BigDecimal getImportoAttualeImpegno() {
		return importoAttualeImpegno;
	}
	public void setImportoAttualeImpegno(BigDecimal importoAttualeImpegno) {
		this.importoAttualeImpegno = importoAttualeImpegno;
	}
	
	
	public void setIdTipoFondo(BigDecimal idTipoFondo) {
		this.idTipoFondo = idTipoFondo;
	}
	public BigDecimal getIdTipoFondo() {
		return idTipoFondo;
	}
	public void setDescTipoFondo(String descTipoFondo) {
		this.descTipoFondo = descTipoFondo;
	}
	public String getDescTipoFondo() {
		return descTipoFondo;
	}
	public void setIdEnteCap(BigDecimal idEnteCap) {
		this.idEnteCap = idEnteCap;
	}
	public BigDecimal getIdEnteCap() {
		return idEnteCap;
	}
	public void setIdEnteProvv(BigDecimal idEnteProvv) {
		this.idEnteProvv = idEnteProvv;
	}
	public BigDecimal getIdEnteProvv() {
		return idEnteProvv;
	}
	public void setDisponibilitaLiquidare(BigDecimal disponibilitaLiquidare) {
		this.disponibilitaLiquidare = disponibilitaLiquidare;
	}
	public BigDecimal getDisponibilitaLiquidare() {
		return disponibilitaLiquidare;
	}

}
