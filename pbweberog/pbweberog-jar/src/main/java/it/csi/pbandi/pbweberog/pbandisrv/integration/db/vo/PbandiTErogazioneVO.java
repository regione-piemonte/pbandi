/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


package it.csi.pbandi.pbweberog.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;



public class PbandiTErogazioneVO extends GenericVO {

  	
  	private BigDecimal idModalitaErogazione;
  	
  	private BigDecimal idUtenteAgg;
  	
  	private BigDecimal idCausaleErogazione;
  	
  	private BigDecimal importoErogazione;
  	
  	private Date dtAggiornamento;
  	
  	private Date dtInserimento;
  	
  	private String noteErogazione;
  	
  	private BigDecimal idEnteCompetenza;
  	
  	private String codRiferimentoErogazione;
  	
  	private BigDecimal idModalitaAgevolazione;
  	
  	private BigDecimal idProgetto;
  	
  	private Date dtContabile;
  	
  	private BigDecimal idErogazione;
  	
  	private BigDecimal idUtenteIns;
  	
	public PbandiTErogazioneVO() {}
  	
	public PbandiTErogazioneVO (BigDecimal idErogazione) {
	
		this. idErogazione =  idErogazione;
	}
  	
	public PbandiTErogazioneVO (BigDecimal idModalitaErogazione, BigDecimal idUtenteAgg, BigDecimal idCausaleErogazione, BigDecimal importoErogazione, Date dtAggiornamento, Date dtInserimento, String noteErogazione, BigDecimal idEnteCompetenza, String codRiferimentoErogazione, BigDecimal idModalitaAgevolazione, BigDecimal idProgetto, Date dtContabile, BigDecimal idErogazione, BigDecimal idUtenteIns) {
	
		this. idModalitaErogazione =  idModalitaErogazione;
		this. idUtenteAgg =  idUtenteAgg;
		this. idCausaleErogazione =  idCausaleErogazione;
		this. importoErogazione =  importoErogazione;
		this. dtAggiornamento =  dtAggiornamento;
		this. dtInserimento =  dtInserimento;
		this. noteErogazione =  noteErogazione;
		this. idEnteCompetenza =  idEnteCompetenza;
		this. codRiferimentoErogazione =  codRiferimentoErogazione;
		this. idModalitaAgevolazione =  idModalitaAgevolazione;
		this. idProgetto =  idProgetto;
		this. dtContabile =  dtContabile;
		this. idErogazione =  idErogazione;
		this. idUtenteIns =  idUtenteIns;
	}
  	
  	
	public BigDecimal getIdModalitaErogazione() {
		return idModalitaErogazione;
	}
	
	public void setIdModalitaErogazione(BigDecimal idModalitaErogazione) {
		this.idModalitaErogazione = idModalitaErogazione;
	}
	
	public BigDecimal getIdUtenteAgg() {
		return idUtenteAgg;
	}
	
	public void setIdUtenteAgg(BigDecimal idUtenteAgg) {
		this.idUtenteAgg = idUtenteAgg;
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
	
	public Date getDtAggiornamento() {
		return dtAggiornamento;
	}
	
	public void setDtAggiornamento(Date dtAggiornamento) {
		this.dtAggiornamento = dtAggiornamento;
	}
	
	public Date getDtInserimento() {
		return dtInserimento;
	}
	
	public void setDtInserimento(Date dtInserimento) {
		this.dtInserimento = dtInserimento;
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
	
	public BigDecimal getIdUtenteIns() {
		return idUtenteIns;
	}
	
	public void setIdUtenteIns(BigDecimal idUtenteIns) {
		this.idUtenteIns = idUtenteIns;
	}
	
	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid() && idModalitaErogazione != null && idCausaleErogazione != null && importoErogazione != null && dtInserimento != null && idModalitaAgevolazione != null && idProgetto != null && dtContabile != null && idUtenteIns != null) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idErogazione != null ) {
   			isPkValid = true;
   		}

   		return isPkValid;
	}
	
	
	public java.util.List<String> getPK() {
		java.util.List<String> pk=new java.util.ArrayList<String>();
		
			pk.add("idErogazione");
		
	    return pk;
	}
	
	
}
