/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.columnfilter;

import java.math.BigDecimal;
import java.sql.Date;

import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.GenericVO;

public class DettaglioAttoLiquidazioneFilterByCausaleErogazionePerRiepilogoVO extends GenericVO{
	
	private String descCausale;
	private BigDecimal idAttoLiquidazione;
	private BigDecimal importoAtto;
	private String estremiAtto;
	private String descBreveStatoAtto;
	private Date dtEmissioneAtto;
	
	public Date getDtEmissioneAtto() {
		return dtEmissioneAtto;
	}
	public void setDtEmissioneAtto(Date dtEmissioneAtto) {
		this.dtEmissioneAtto = dtEmissioneAtto;
	}
	public String getDescBreveStatoAtto() {
		return descBreveStatoAtto;
	}
	public void setDescBreveStatoAtto(String descBreveStatoAtto) {
		this.descBreveStatoAtto = descBreveStatoAtto;
	}
	public String getDescCausale() {
		return descCausale;
	}
	public void setDescCausale(String descCausale) {
		this.descCausale = descCausale;
	}
	public BigDecimal getIdAttoLiquidazione() {
		return idAttoLiquidazione;
	}
	public void setIdAttoLiquidazione(BigDecimal idAttoLiquidazione) {
		this.idAttoLiquidazione = idAttoLiquidazione;
	}
	public BigDecimal getImportoAtto() {
		return importoAtto;
	}
	public void setImportoAtto(BigDecimal importoAtto) {
		this.importoAtto = importoAtto;
	}
	public String getEstremiAtto() {
		return estremiAtto;
	}
	public void setEstremiAtto(String estremiAtto) {
		this.estremiAtto = estremiAtto;
	}

}
