/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo;

import java.math.BigDecimal;
import java.sql.Date;

public class RichiestaErogazioneCausaleVO extends GenericVO {
	
	private BigDecimal idProgetto;
	private BigDecimal idRichiestaErogazione;
	private BigDecimal idCausaleErogazione;
	private String descBreveCausale;
	private String descCausaleErogazione;
	private String numeroRichiestaErogazione;
	private Date dataRichiestaErogazione;
	private BigDecimal importoRichiestaErogazione;
	
	
	public BigDecimal getIdProgetto() {
		return idProgetto;
	}
	public void setIdProgetto(BigDecimal idProgetto) {
		this.idProgetto = idProgetto;
	}
	public BigDecimal getIdRichiestaErogazione() {
		return idRichiestaErogazione;
	}
	public void setIdRichiestaErogazione(BigDecimal idRichiestaErogazione) {
		this.idRichiestaErogazione = idRichiestaErogazione;
	}
	public BigDecimal getIdCausaleErogazione() {
		return idCausaleErogazione;
	}
	public void setIdCausaleErogazione(BigDecimal idCausaleErogazione) {
		this.idCausaleErogazione = idCausaleErogazione;
	}
	public String getDescBreveCausale() {
		return descBreveCausale;
	}
	public void setDescBreveCausale(String descBreveCausale) {
		this.descBreveCausale = descBreveCausale;
	}
	public String getDescCausaleErogazione() {
		return descCausaleErogazione;
	}
	public void setDescCausaleErogazione(String descCausaleErogazione) {
		this.descCausaleErogazione = descCausaleErogazione;
	}
	public String getNumeroRichiestaErogazione() {
		return numeroRichiestaErogazione;
	}
	public void setNumeroRichiestaErogazione(String numeroRichiestaErogazione) {
		this.numeroRichiestaErogazione = numeroRichiestaErogazione;
	}
	public Date getDataRichiestaErogazione() {
		return dataRichiestaErogazione;
	}
	public void setDataRichiestaErogazione(Date dataRichiestaErogazione) {
		this.dataRichiestaErogazione = dataRichiestaErogazione;
	}
	public BigDecimal getImportoRichiestaErogazione() {
		return importoRichiestaErogazione;
	}
	public void setImportoRichiestaErogazione(BigDecimal importoRichiestaErogazione) {
		this.importoRichiestaErogazione = importoRichiestaErogazione;
	}
	
	
	

}
