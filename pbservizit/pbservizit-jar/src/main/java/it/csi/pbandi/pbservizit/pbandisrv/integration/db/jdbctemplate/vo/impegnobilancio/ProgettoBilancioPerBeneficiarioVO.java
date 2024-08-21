/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.impegnobilancio;

import java.math.BigDecimal;

import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.GenericVO;

public class ProgettoBilancioPerBeneficiarioVO extends GenericVO {
	
	private BigDecimal idProgetto;
	private String codiceVisualizzato;
    private String titoloProgetto;
    private BigDecimal idSoggetto;
    private String denominazioneBeneficiario;
    private BigDecimal quotaImportoAgevolato;
    private String nomeBandoLinea;
    
	public String getNomeBandoLinea() {
		return nomeBandoLinea;
	}
	public void setNomeBandoLinea(String nomeBandoLinea) {
		this.nomeBandoLinea = nomeBandoLinea;
	}
	public BigDecimal getIdSoggetto() {
		return idSoggetto;
	}
	public void setIdSoggetto(BigDecimal idSoggetto) {
		this.idSoggetto = idSoggetto;
	}
	public BigDecimal getIdProgetto() {
		return idProgetto;
	}
	public void setIdProgetto(BigDecimal idProgetto) {
		this.idProgetto = idProgetto;
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
	public String getDenominazioneBeneficiario() {
		return denominazioneBeneficiario;
	}
	public void setDenominazioneBeneficiario(String denominazioneBeneficiario) {
		this.denominazioneBeneficiario = denominazioneBeneficiario;
	}
	
	/*
    private BigDecimal idImpegno;
    private BigDecimal idEnteCap;
    private BigDecimal idEnteProvv;
    private BigDecimal idProgetto;
    private BigDecimal progrBandolineaIntervento;
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
    */

}
