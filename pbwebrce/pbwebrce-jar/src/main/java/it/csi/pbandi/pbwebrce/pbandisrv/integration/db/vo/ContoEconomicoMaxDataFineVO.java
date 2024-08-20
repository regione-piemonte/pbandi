/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebrce.pbandisrv.integration.db.vo;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

public class ContoEconomicoMaxDataFineVO extends GenericVO {

	private BigDecimal idProgetto;
	private Date dtFineValidita;
	private String descBreveStatoContoEconom;
	private String descBreveTipologiaContoEco;
	private BigDecimal importoRibassoAsta;
	private BigDecimal percRibassoAsta;
	private BigDecimal idProceduraAggiudicaz;

	public ContoEconomicoMaxDataFineVO() {
	}

	public Date getDtFineValidita() {
		return dtFineValidita;
	}

	public void setDtFineValidita(Date dtFineValidita) {
		this.dtFineValidita = dtFineValidita;
	}

	public void setIdProgetto(BigDecimal idProgetto) {
		this.idProgetto = idProgetto;
	}

	public BigDecimal getIdProgetto() {
		return idProgetto;
	}

	public void setDescBreveStatoContoEconom(String descBreveStatoContoEconom) {
		this.descBreveStatoContoEconom = descBreveStatoContoEconom;
	}

	public String getDescBreveStatoContoEconom() {
		return descBreveStatoContoEconom;
	}

	public void setDescBreveTipologiaContoEco(String descBreveTipologiaContoEco) {
		this.descBreveTipologiaContoEco = descBreveTipologiaContoEco;
	}

	public String getDescBreveTipologiaContoEco() {
		return descBreveTipologiaContoEco;
	}

	@Override
	public List getPK() {
		return null;
	}

	@Override
	public boolean isPKValid() {
		return false;
	}

	public void setImportoRibassoAsta(BigDecimal importoRibassoAsta) {
		this.importoRibassoAsta = importoRibassoAsta;
	}

	public BigDecimal getImportoRibassoAsta() {
		return importoRibassoAsta;
	}

	public void setPercRibassoAsta(BigDecimal percRibassoAsta) {
		this.percRibassoAsta = percRibassoAsta;
	}

	public BigDecimal getPercRibassoAsta() {
		return percRibassoAsta;
	}

	public void setIdProceduraAggiudicaz(BigDecimal idProceduraAggiudicaz) {
		this.idProceduraAggiudicaz = idProceduraAggiudicaz;
	}

	public BigDecimal getIdProceduraAggiudicaz() {
		return idProceduraAggiudicaz;
	}

	
}
