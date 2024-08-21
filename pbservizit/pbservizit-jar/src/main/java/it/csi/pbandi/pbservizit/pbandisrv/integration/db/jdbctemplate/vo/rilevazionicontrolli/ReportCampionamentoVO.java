/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.rilevazionicontrolli;


import java.math.BigDecimal;

import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiTCampionamentoVO;

public class ReportCampionamentoVO extends PbandiTCampionamentoVO{
	
	private BigDecimal idDocumentoIndex;
	private String	nomeFile;
	private BigDecimal idNormativa;
	private String normativa;
	private String descPeriodoVisualizzata;
	
	
	public BigDecimal getIdDocumentoIndex() {
		return idDocumentoIndex;
	}
	public void setIdDocumentoIndex(BigDecimal idDocumentoIndex) {
		this.idDocumentoIndex = idDocumentoIndex;
	}
	public String getNomeFile() {
		return nomeFile;
	}
	public void setNomeFile(String nomeFile) {
		this.nomeFile = nomeFile;
	}
	public String getNormativa() {
		return normativa;
	}
	public void setNormativa(String normativa) {
		this.normativa = normativa;
	}
	public String getDescPeriodoVisualizzata() {
		return descPeriodoVisualizzata;
	}
	public void setDescPeriodoVisualizzata(String descPeriodoVisualizzata) {
		this.descPeriodoVisualizzata = descPeriodoVisualizzata;
	}
	public BigDecimal getIdNormativa() {
		return idNormativa;
	}
	public void setIdNormativa(BigDecimal idNormativa) {
		this.idNormativa = idNormativa;
	}
}
