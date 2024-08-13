/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo;

import java.math.BigDecimal;

import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiTAppaltoVO;;
/**
 *
 */
public class AppaltoVO extends PbandiTAppaltoVO {
	private String codProcAgg;
	private String descProcAgg;
	private BigDecimal idProgetto;
	private String descTipologiaAggiudicazione;
	private String codVisualizzatoProcAgg;
	private String descTipologiaAppalto;
	private BigDecimal importo;
	private BigDecimal iva;
	private BigDecimal importoRibassoAsta;
	private BigDecimal percentualeRibassoAsta;

	public void setCodProcAgg(String codProcAgg) {
		this.codProcAgg = codProcAgg;
	}

	public String getCodProcAgg() {
		return codProcAgg;
	}

	public void setIdProgetto(BigDecimal idProgetto) {
		this.idProgetto = idProgetto;
	}

	public BigDecimal getIdProgetto() {
		return idProgetto;
	}

	public void setDescProcAgg(String descProcAgg) {
		this.descProcAgg = descProcAgg;
	}

	public String getDescProcAgg() {
		return descProcAgg;
	}

	public void setDescTipologiaAggiudicazione(
			String descTipologiaAggiudicazione) {
		this.descTipologiaAggiudicazione = descTipologiaAggiudicazione;
	}

	public String getDescTipologiaAggiudicazione() {
		return descTipologiaAggiudicazione;
	}

	public String getCodVisualizzatoProcAgg() {
		return codVisualizzatoProcAgg;
	}

	public void setCodVisualizzatoProcAgg(String codVisualizzatoProcAgg) {
		this.codVisualizzatoProcAgg = codVisualizzatoProcAgg;
	}

	public void setDescTipologiaAppalto(String descTipologiaAppalto) {
		this.descTipologiaAppalto = descTipologiaAppalto;
	}

	public String getDescTipologiaAppalto() {
		return descTipologiaAppalto;
	}

	public void setImporto(BigDecimal importo) {
		this.importo = importo;
	}

	public BigDecimal getImporto() {
		return importo;
	}

	public void setIva(BigDecimal iva) {
		this.iva = iva;
	}

	public BigDecimal getIva() {
		return iva;
	}

	public BigDecimal getImportoRibassoAsta() {
		return importoRibassoAsta;
	}

	public void setImportoRibassoAsta(BigDecimal importoRibassoAsta) {
		this.importoRibassoAsta = importoRibassoAsta;
	}

	public BigDecimal getPercentualeRibassoAsta() {
		return percentualeRibassoAsta;
	}

	public void setPercentualeRibassoAsta(BigDecimal percentualeRibassoAsta) {
		this.percentualeRibassoAsta = percentualeRibassoAsta;
	}



}
