/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.liquidazionebilancio;

import java.math.BigDecimal;
import java.sql.Date;

import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.GenericVO;

public class ModalitaAgevolazioneLiquidazioneVO extends GenericVO {
	
	private BigDecimal idProgetto;
	private BigDecimal idAttoLiquidazione;
	private BigDecimal idModalitaAgevolazione;
	private String descModalitaAgevolazione;
	private String descBreveModalitaAgevolaz;
	private BigDecimal quotaImportoAgevolato;
	private BigDecimal idCausaleErogazione;
	private String descCausale;
	private String descBreveCausale;
	private Date dtEmissioneAtto;
	private String numeroAtto;
	private BigDecimal importoLiquidatoAtto;
	
	public BigDecimal getIdProgetto() {
		return idProgetto;
	}
	public void setIdProgetto(BigDecimal idProgetto) {
		this.idProgetto = idProgetto;
	}
	public BigDecimal getIdAttoLiquidazione() {
		return idAttoLiquidazione;
	}
	public void setIdAttoLiquidazione(BigDecimal idAttoLiquidazione) {
		this.idAttoLiquidazione = idAttoLiquidazione;
	}
	public String getDescModalitaAgevolazione() {
		return descModalitaAgevolazione;
	}
	public void setDescModalitaAgevolazione(String descModalitaAgevolazione) {
		this.descModalitaAgevolazione = descModalitaAgevolazione;
	}
	public String getDescBreveModalitaAgevolaz() {
		return descBreveModalitaAgevolaz;
	}
	public void setDescBreveModalitaAgevolaz(String descBreveModalitaAgevolaz) {
		this.descBreveModalitaAgevolaz = descBreveModalitaAgevolaz;
	}
	public BigDecimal getQuotaImportoAgevolato() {
		return quotaImportoAgevolato;
	}
	public void setQuotaImportoAgevolato(BigDecimal quotaImportoAgevolato) {
		this.quotaImportoAgevolato = quotaImportoAgevolato;
	}
	public String getDescCausale() {
		return descCausale;
	}
	public void setDescCausale(String descCausale) {
		this.descCausale = descCausale;
	}
	public String getDescBreveCausale() {
		return descBreveCausale;
	}
	public void setDescBreveCausale(String descBreveCausale) {
		this.descBreveCausale = descBreveCausale;
	}
	public Date getDtEmissioneAtto() {
		return dtEmissioneAtto;
	}
	public void setDtEmissioneAtto(Date dtEmissioneAtto) {
		this.dtEmissioneAtto = dtEmissioneAtto;
	}
	public String getNumeroAtto() {
		return numeroAtto;
	}
	public void setNumeroAtto(String numeroAtto) {
		this.numeroAtto = numeroAtto;
	}
	public BigDecimal getImportoLiquidatoAtto() {
		return importoLiquidatoAtto;
	}
	public void setImportoLiquidatoAtto(BigDecimal importoLiquidatoAtto) {
		this.importoLiquidatoAtto = importoLiquidatoAtto;
	}
	public void setIdModalitaAgevolazione(BigDecimal idModalitaAgevolazione) {
		this.idModalitaAgevolazione = idModalitaAgevolazione;
	}
	public BigDecimal getIdModalitaAgevolazione() {
		return idModalitaAgevolazione;
	}
	public void setIdCausaleErogazione(BigDecimal idCausaleErogazione) {
		this.idCausaleErogazione = idCausaleErogazione;
	}
	public BigDecimal getIdCausaleErogazione() {
		return idCausaleErogazione;
	}
	


}
