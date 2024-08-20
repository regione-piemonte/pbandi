/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebrce.dto.affidamenti;

import java.io.Serializable;
import java.sql.Date;

public class TipologiaAppaltoDTO implements Serializable{

	static final long serialVersionUID = 1L;

	private Date dtFineValidita;
  	
  	private String descBreveAppalto;
  	
  	private Long idTipologiaAppalto;
  	
  	private String descTipologiaAppalto;
  	
  	private Date dtInizioValidita;

	public Date getDtFineValidita() {
		return dtFineValidita;
	}

	public void setDtFineValidita(Date dtFineValidita) {
		this.dtFineValidita = dtFineValidita;
	}

	public String getDescBreveAppalto() {
		return descBreveAppalto;
	}

	public void setDescBreveAppalto(String descBreveAppalto) {
		this.descBreveAppalto = descBreveAppalto;
	}

	public Long getIdTipologiaAppalto() {
		return idTipologiaAppalto;
	}

	public void setIdTipologiaAppalto(Long idTipologiaAppalto) {
		this.idTipologiaAppalto = idTipologiaAppalto;
	}

	public String getDescTipologiaAppalto() {
		return descTipologiaAppalto;
	}

	public void setDescTipologiaAppalto(String descTipologiaAppalto) {
		this.descTipologiaAppalto = descTipologiaAppalto;
	}

	public Date getDtInizioValidita() {
		return dtInizioValidita;
	}

	public void setDtInizioValidita(Date dtInizioValidita) {
		this.dtInizioValidita = dtInizioValidita;
	}
  	
  	
  	
}
