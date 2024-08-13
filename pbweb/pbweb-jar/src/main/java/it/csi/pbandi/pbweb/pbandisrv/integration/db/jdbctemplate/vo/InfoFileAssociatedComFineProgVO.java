/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo;

import java.math.BigDecimal;
import java.util.Date;


public class InfoFileAssociatedComFineProgVO extends InfoFileVO {
	
 
	private Date dtComunicazione;
	private BigDecimal idComunicazione;	 

	public BigDecimal getIdComunicazione() {
		return idComunicazione;
	}

	public void setIdComunicazione(BigDecimal idComunicazione) {
		this.idComunicazione = idComunicazione;
	}

	public Date getDtComunicazione() {
		return dtComunicazione;
	}

	public void setDtComunicazione(Date dtComunicazione) {
		this.dtComunicazione = dtComunicazione;
	}

 

}
