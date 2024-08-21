/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo;

import java.math.BigDecimal;

public class DettaglioAllSoggettiConTipoAnagraficaVO extends DettaglioSoggettoConTipoAnagraficaSenzaBeneficiarioVO {

	private BigDecimal idRelazioneBeneficiario;
	
	private BigDecimal progettiValidi;
	
	private BigDecimal progettiNonValidi;
	
	private BigDecimal idUtente;
	
	public BigDecimal getIdRelazioneBeneficiario() {
		return idRelazioneBeneficiario;
	}
	public void setIdRelazioneBeneficiario(BigDecimal idRelazioneBeneficiario) {
		this.idRelazioneBeneficiario = idRelazioneBeneficiario;
	}
	public BigDecimal getProgettiValidi() {
		return progettiValidi;
	}
	public void setProgettiValidi(BigDecimal progettiValidi) {
		this.progettiValidi = progettiValidi;
	}
	public BigDecimal getProgettiNonValidi() {
		return progettiNonValidi;
	}
	public void setProgettiNonValidi(BigDecimal progettiNonValidi) {
		this.progettiNonValidi = progettiNonValidi;
	}
	public BigDecimal getIdUtente() {
		return idUtente;
	}
	public void setIdUtente(BigDecimal idUtente) {
		this.idUtente = idUtente;
	}
	
}
