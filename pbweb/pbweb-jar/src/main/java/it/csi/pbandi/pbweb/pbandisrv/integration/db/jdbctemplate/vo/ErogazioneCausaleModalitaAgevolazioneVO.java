/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo;

import java.math.BigDecimal;
import java.sql.Date;


public class ErogazioneCausaleModalitaAgevolazioneVO extends GenericVO {
	
 	private BigDecimal idModalitaErogazione;
  	
  	private BigDecimal idCausaleErogazione;
  	
  	private BigDecimal importoErogazione;
  	
  	private String noteErogazione;
  	
  	private BigDecimal idEnteCompetenza;
  	
  	private String codRiferimentoErogazione;
  	
  	private BigDecimal idModalitaAgevolazione;
  	
  	private BigDecimal idProgetto;
  	
  	private Date dtContabile;
  	
  	private BigDecimal idErogazione;
  	
  	private String descBreveCausale;
  	
  	private String descCausale;
  	
  	private String descBreveModalitaAgevolaz;
  	
  	private String descModalitaAgevolazione;

	public BigDecimal getIdModalitaErogazione() {
		return idModalitaErogazione;
	}

	public void setIdModalitaErogazione(BigDecimal idModalitaErogazione) {
		this.idModalitaErogazione = idModalitaErogazione;
	}

	public BigDecimal getIdCausaleErogazione() {
		return idCausaleErogazione;
	}

	public void setIdCausaleErogazione(BigDecimal idCausaleErogazione) {
		this.idCausaleErogazione = idCausaleErogazione;
	}

	public BigDecimal getImportoErogazione() {
		return importoErogazione;
	}

	public void setImportoErogazione(BigDecimal importoErogazione) {
		this.importoErogazione = importoErogazione;
	}

	public String getNoteErogazione() {
		return noteErogazione;
	}

	public void setNoteErogazione(String noteErogazione) {
		this.noteErogazione = noteErogazione;
	}

	public BigDecimal getIdEnteCompetenza() {
		return idEnteCompetenza;
	}

	public void setIdEnteCompetenza(BigDecimal idEnteCompetenza) {
		this.idEnteCompetenza = idEnteCompetenza;
	}

	public String getCodRiferimentoErogazione() {
		return codRiferimentoErogazione;
	}

	public void setCodRiferimentoErogazione(String codRiferimentoErogazione) {
		this.codRiferimentoErogazione = codRiferimentoErogazione;
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

	public Date getDtContabile() {
		return dtContabile;
	}

	public void setDtContabile(Date dtContabile) {
		this.dtContabile = dtContabile;
	}

	public BigDecimal getIdErogazione() {
		return idErogazione;
	}

	public void setIdErogazione(BigDecimal idErogazione) {
		this.idErogazione = idErogazione;
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
  	

}
