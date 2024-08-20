/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebrce.dto.affidamenti;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;

public class TipologiaAggiudicazioneDTO implements Serializable{
	private Date dtFineValidita;
  	
  	private Long codIgrueT47;
  	
  	private Date dtInizioValidita;
  	
  	private String descTipologiaAggiudicazione;
  	
  	private Long idTipologiaAggiudicaz;

	public Date getDtFineValidita() {
		return dtFineValidita;
	}

	public void setDtFineValidita(Date dtFineValidita) {
		this.dtFineValidita = dtFineValidita;
	}

	public Long getCodIgrueT47() {
		return codIgrueT47;
	}

	public void setCodIgrueT47(Long codIgrueT47) {
		this.codIgrueT47 = codIgrueT47;
	}

	public Date getDtInizioValidita() {
		return dtInizioValidita;
	}

	public void setDtInizioValidita(Date dtInizioValidita) {
		this.dtInizioValidita = dtInizioValidita;
	}

	public String getDescTipologiaAggiudicazione() {
		return descTipologiaAggiudicazione;
	}

	public void setDescTipologiaAggiudicazione(String descTipologiaAggiudicazione) {
		this.descTipologiaAggiudicazione = descTipologiaAggiudicazione;
	}

	public Long getIdTipologiaAggiudicaz() {
		return idTipologiaAggiudicaz;
	}

	public void setIdTipologiaAggiudicaz(Long idTipologiaAggiudicaz) {
		this.idTipologiaAggiudicaz = idTipologiaAggiudicaz;
	}
  	
  	
  	
}
