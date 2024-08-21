/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo;

import java.math.BigDecimal;
import java.util.Date;


public class InfoFileAssociatedPropostaRimodVO extends InfoFileVO {
	

	private BigDecimal idContoEconomico;	 
	private Date dtProposta;

	public BigDecimal getIdContoEconomico() {
		return idContoEconomico;
	}

	public void setIdContoEconomico(BigDecimal idContoEconomico) {
		this.idContoEconomico = idContoEconomico;
	}

	public Date getDtProposta() {
		return dtProposta;
	}

	public void setDtProposta(Date dtProposta) {
		this.dtProposta = dtProposta;
	}

	 

 

}
