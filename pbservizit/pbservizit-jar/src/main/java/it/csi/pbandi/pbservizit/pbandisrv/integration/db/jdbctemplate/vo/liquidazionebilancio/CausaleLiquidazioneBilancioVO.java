/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.liquidazionebilancio;

import java.math.BigDecimal;
import java.sql.Date;

import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.GenericVO;

public class CausaleLiquidazioneBilancioVO extends GenericVO {
	
	 private BigDecimal idCausaleErogazione;
	 private BigDecimal idModalitaAgevolazione;
	 private BigDecimal idProgetto;
 	 private BigDecimal idAttoLiquidazione;
	 private String descBreveCausale;
	 private String descCausale;
     private BigDecimal importoAtto;

	 public BigDecimal getIdCausaleErogazione() {
		return idCausaleErogazione;
	}
	public void setIdCausaleErogazione(BigDecimal idCausaleErogazione) {
		this.idCausaleErogazione = idCausaleErogazione;
	}
	public BigDecimal getIdModalitaAgevolazione() {
		return idModalitaAgevolazione;
	}
	public void setIdModalitaAgevolazione(BigDecimal idModalitaAgevolazione) {
		this.idModalitaAgevolazione = idModalitaAgevolazione;
	}
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
	public String getDescBreveCausale() {
		return descBreveCausale;
	}
	public void setDescBreveCausale(String descBreveCausale) {
		this.descBreveCausale = descBreveCausale;
	}
	public String getDescCausale() {
		return descCausale;
	}
	public void setDescCausale(String descCausale) {
		this.descCausale = descCausale;
	}
	public BigDecimal getImportoAtto() {
		return importoAtto;
	}
	public void setImportoAtto(BigDecimal importoAtto) {
		this.importoAtto = importoAtto;
	}

}
