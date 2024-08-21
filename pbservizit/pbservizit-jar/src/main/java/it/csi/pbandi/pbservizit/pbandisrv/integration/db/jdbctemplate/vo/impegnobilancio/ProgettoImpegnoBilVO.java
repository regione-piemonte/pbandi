/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.impegnobilancio;

import java.math.BigDecimal;

import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.GenericVO;

public class ProgettoImpegnoBilVO extends GenericVO {
	private BigDecimal idProgetto;
	private BigDecimal progrBandolineaIntervento;
	private String codiceVisualizzato;
	private String titoloProgetto;
	private String denominazioneBeneficiario;
	private BigDecimal idImpegno;
	private String descImpegno;
	private String annoImpegno;
	private String numeroImpegno;
	private BigDecimal importoAttualeImpegno;
	private BigDecimal disponibilitaLiquidare;
	private BigDecimal idTipoFondo;
	private String descTipoFondo;
	private BigDecimal idModalitaAgevolazione;
	private String descBreveModalitaAgevolaz;
	private String descModalitaAgevolazione;
	private BigDecimal quotaImportoAgevolato;
	
	
	public BigDecimal getIdProgetto() {
		return idProgetto;
	}
	public void setIdProgetto(BigDecimal idProgetto) {
		this.idProgetto = idProgetto;
	}
	public BigDecimal getProgrBandolineaIntervento() {
		return progrBandolineaIntervento;
	}
	public void setProgrBandolineaIntervento(BigDecimal progrBandolineaIntervento) {
		this.progrBandolineaIntervento = progrBandolineaIntervento;
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
	public String getDenominazioneBeneficiario() {
		return denominazioneBeneficiario;
	}
	public void setDenominazioneBeneficiario(String denominazioneBeneficiario) {
		this.denominazioneBeneficiario = denominazioneBeneficiario;
	}
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
	public BigDecimal getIdModalitaAgevolazione() {
		return idModalitaAgevolazione;
	}
	public void setIdModalitaAgevolazione(BigDecimal idModalitaAgevolazione) {
		this.idModalitaAgevolazione = idModalitaAgevolazione;
	}
	public String getDescBreveModalitaAgevolaz() {
		return descBreveModalitaAgevolaz;
	}
	public void setDescBreveModalitaAgevolaz(String descBreveModalitaAgevolaz) {
		this.descBreveModalitaAgevolaz = descBreveModalitaAgevolaz;
	}
	public String getDescModalitaAgevolazione() {
		return descModalitaAgevolazione;
	}
	public void setDescModalitaAgevolazione(String descModalitaAgevolazione) {
		this.descModalitaAgevolazione = descModalitaAgevolazione;
	}
	public BigDecimal getQuotaImportoAgevolato() {
		return quotaImportoAgevolato;
	}
	public void setQuotaImportoAgevolato(BigDecimal quotaImportoAgevolato) {
		this.quotaImportoAgevolato = quotaImportoAgevolato;
	}
	public void setDisponibilitaLiquidare(BigDecimal disponibilitaLiquidare) {
		this.disponibilitaLiquidare = disponibilitaLiquidare;
	}
	public BigDecimal getDisponibilitaLiquidare() {
		return disponibilitaLiquidare;
	}

}
