/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.impegnobilancio;

import java.math.BigDecimal;

import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.GenericVO;

public class ProgettoVO extends GenericVO {
	
	 private BigDecimal idProgetto;
	 private String codiceVisualizzato;
	 private String titoloProgetto;
	 private BigDecimal quotaImportoAgevolato;
//	 private BigDecimal impTotImpegniProgetto; 
//	 private BigDecimal numTotImpegniProgetto;
	 private BigDecimal progrBandolineaIntervento;
	
	 
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
/*
	public void setImpTotImpegniProgetto(BigDecimal impTotImpegniProgetto) {
		this.impTotImpegniProgetto = impTotImpegniProgetto;
	}
	public BigDecimal getImpTotImpegniProgetto() {
		return impTotImpegniProgetto;
	}
*/
/*
	public void setNumTotImpegniProgetto(BigDecimal numTotImpegniProgetto) {
		this.numTotImpegniProgetto = numTotImpegniProgetto;
	}
	public BigDecimal getNumTotImpegniProgetto() {
		return numTotImpegniProgetto;
	}
*/
	public void setProgrBandolineaIntervento(BigDecimal progrBandolineaIntervento) {
		this.progrBandolineaIntervento = progrBandolineaIntervento;
	}
	public BigDecimal getProgrBandolineaIntervento() {
		return progrBandolineaIntervento;
	}

}
