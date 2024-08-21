/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.integration.vo;

import java.math.BigDecimal;

public class SoggettoPermessoTipoAnagraficaVO extends PermessoTipoAnagraficaVO {

  	private BigDecimal idSoggetto;

	public BigDecimal getIdSoggetto() {
		return idSoggetto;
	}

	public void setIdSoggetto(BigDecimal idSoggetto) {
		this.idSoggetto = idSoggetto;
	}

	@Override
	public String toString() {
		return "SoggettoPermessoTipoAnagraficaVO [idSoggetto=" + idSoggetto + ",  " + super.toString() + "]";
	}
	
}
