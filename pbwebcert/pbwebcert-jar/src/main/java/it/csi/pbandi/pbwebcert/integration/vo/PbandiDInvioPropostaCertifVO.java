/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebcert.integration.vo;

import java.math.BigDecimal;
import java.sql.Date;

public class PbandiDInvioPropostaCertifVO {
	private BigDecimal idStatoPropostaCertif;
  	
  	private Date dtFineValidita;
  	
  	private String email;
  	
  	private BigDecimal idInvioPropostaCertif;
  	
  	private BigDecimal idLineaDiIntervento;
  	
  	private Date dtInizioValidita;
  	
	public PbandiDInvioPropostaCertifVO() {}
  	
	public PbandiDInvioPropostaCertifVO (BigDecimal idInvioPropostaCertif) {
	
		this. idInvioPropostaCertif =  idInvioPropostaCertif;
	}
  	
	public PbandiDInvioPropostaCertifVO (BigDecimal idStatoPropostaCertif, Date dtFineValidita, String email, BigDecimal idInvioPropostaCertif, BigDecimal idLineaDiIntervento, Date dtInizioValidita) {
	
		this. idStatoPropostaCertif =  idStatoPropostaCertif;
		this. dtFineValidita =  dtFineValidita;
		this. email =  email;
		this. idInvioPropostaCertif =  idInvioPropostaCertif;
		this. idLineaDiIntervento =  idLineaDiIntervento;
		this. dtInizioValidita =  dtInizioValidita;
	}
  	
  	
	public BigDecimal getIdStatoPropostaCertif() {
		return idStatoPropostaCertif;
	}
	
	public void setIdStatoPropostaCertif(BigDecimal idStatoPropostaCertif) {
		this.idStatoPropostaCertif = idStatoPropostaCertif;
	}
	
	public Date getDtFineValidita() {
		return dtFineValidita;
	}
	
	public void setDtFineValidita(Date dtFineValidita) {
		this.dtFineValidita = dtFineValidita;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public BigDecimal getIdInvioPropostaCertif() {
		return idInvioPropostaCertif;
	}
	
	public void setIdInvioPropostaCertif(BigDecimal idInvioPropostaCertif) {
		this.idInvioPropostaCertif = idInvioPropostaCertif;
	}
	
	public BigDecimal getIdLineaDiIntervento() {
		return idLineaDiIntervento;
	}
	
	public void setIdLineaDiIntervento(BigDecimal idLineaDiIntervento) {
		this.idLineaDiIntervento = idLineaDiIntervento;
	}
	
	public Date getDtInizioValidita() {
		return dtInizioValidita;
	}
	
	public void setDtInizioValidita(Date dtInizioValidita) {
		this.dtInizioValidita = dtInizioValidita;
	}
	
	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid() && idLineaDiIntervento != null && dtInizioValidita != null) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idInvioPropostaCertif != null ) {
   			isPkValid = true;
   		}

   		return isPkValid;
	}
}
