/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.impegnobilancio;

import java.math.BigDecimal;

import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.GenericVO;

public class ProgettoImpegnoSoggettoBilVO extends GenericVO {
	
	
    private BigDecimal idImpegno;
    private BigDecimal idEnteCap;
    private BigDecimal idEnteProvv;
    private BigDecimal idProgetto;
    private BigDecimal progrBandolineaIntervento;
    private String nomeBandoLinea;
    private String descImpegno;
    private String annoImpegno;
    private String numeroImpegno;
    private BigDecimal importoAttualeImpegno;
    private BigDecimal disponibilitaLiquidare;
    private BigDecimal idTipoFondo;
    private String descTipoFondo;
    private String codiceVisualizzato;
    private String titoloProgetto;
    private BigDecimal quotaImportoAgevolato;
    private String denominazioneBeneficiario;
    private String annoPerente;
    private String numeroPerente;
    private String annoEsercizio;
    
	public String getNomeBandoLinea() {
		return nomeBandoLinea;
	}
	public void setNomeBandoLinea(String nomeBandoLinea) {
		this.nomeBandoLinea = nomeBandoLinea;
	}
	public String getAnnoEsercizio() {
		return annoEsercizio;
	}
	public void setAnnoEsercizio(String annoEsercizio) {
		this.annoEsercizio = annoEsercizio;
	}
	public String getAnnoPerente() {
		return annoPerente;
	}
	public void setAnnoPerente(String annoPerente) {
		this.annoPerente = annoPerente;
	}
	public String getNumeroPerente() {
		return numeroPerente;
	}
	public void setNumeroPerente(String numeroPerente) {
		this.numeroPerente = numeroPerente;
	}
	public BigDecimal getIdImpegno() {
		return idImpegno;
	}
	public void setIdImpegno(BigDecimal idImpegno) {
		this.idImpegno = idImpegno;
	}
	public BigDecimal getIdProgetto() {
		return idProgetto;
	}
	public void setIdProgetto(BigDecimal idProgetto) {
		this.idProgetto = idProgetto;
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
	public String getCodiceVisualizzato() {
		return codiceVisualizzato;
	}
	public void setCodiceVisualizzato(String codiceVisualizzato) {
		this.codiceVisualizzato = codiceVisualizzato;
	}
	public String getTitoloProgetto() {
		return titoloProgetto;
	}
	public void setTitoloProgetto(String titoloProgetto) {
		this.titoloProgetto = titoloProgetto;
	}
	public BigDecimal getQuotaImportoAgevolato() {
		return quotaImportoAgevolato;
	}
	public void setQuotaImportoAgevolato(BigDecimal quotaImportoAgevolato) {
		this.quotaImportoAgevolato = quotaImportoAgevolato;
	}
	
	public void setProgrBandolineaIntervento(BigDecimal progrBandolineaIntervento) {
		this.progrBandolineaIntervento = progrBandolineaIntervento;
	}
	public BigDecimal getProgrBandolineaIntervento() {
		return progrBandolineaIntervento;
	}
	public void setDenominazioneBeneficiario(String denominazioneBeneficiario) {
		this.denominazioneBeneficiario = denominazioneBeneficiario;
	}
	public String getDenominazioneBeneficiario() {
		return denominazioneBeneficiario;
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
