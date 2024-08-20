/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebrce.dto.affidamenti;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;

public class MotiveAssenzaDTO implements Serializable {
	private Long idMotivoAssenzaCig;
  	
  	private Date dtFineValidita;
  	
  	private Date dtInizioValidita;
  	
  	private String descBreveMotivoAssenzaCig;
  	
  	private String descMotivoAssenzaCig;
  	
  	private String tc22;

	public Long getIdMotivoAssenzaCig() {
		return idMotivoAssenzaCig;
	}

	public void setIdMotivoAssenzaCig(Long idMotivoAssenzaCig) {
		this.idMotivoAssenzaCig = idMotivoAssenzaCig;
	}

	public Date getDtFineValidita() {
		return dtFineValidita;
	}

	public void setDtFineValidita(Date dtFineValidita) {
		this.dtFineValidita = dtFineValidita;
	}

	public Date getDtInizioValidita() {
		return dtInizioValidita;
	}

	public void setDtInizioValidita(Date dtInizioValidita) {
		this.dtInizioValidita = dtInizioValidita;
	}

	public String getDescBreveMotivoAssenzaCig() {
		return descBreveMotivoAssenzaCig;
	}

	public void setDescBreveMotivoAssenzaCig(String descBreveMotivoAssenzaCig) {
		this.descBreveMotivoAssenzaCig = descBreveMotivoAssenzaCig;
	}

	public String getDescMotivoAssenzaCig() {
		return descMotivoAssenzaCig;
	}

	public void setDescMotivoAssenzaCig(String descMotivoAssenzaCig) {
		this.descMotivoAssenzaCig = descMotivoAssenzaCig;
	}

	public String getTc22() {
		return tc22;
	}

	public void setTc22(String tc22) {
		this.tc22 = tc22;
	}
  	
  	
  	
}
