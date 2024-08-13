/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo;

import java.math.BigDecimal;

import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiRFolderFileDocIndexVO;

public class FileAssociatoVO extends PbandiRFolderFileDocIndexVO {
	
	private BigDecimal idTarget;
	private BigDecimal idEntita;
	private String codiceVisualizzato;
	private BigDecimal idProgetto;
	
	public BigDecimal getIdTarget() {
		return idTarget;
	}
	public void setIdTarget(BigDecimal idTarget) {
		this.idTarget = idTarget;
	}
	public BigDecimal getIdEntita() {
		return idEntita;
	}
	public void setIdEntita(BigDecimal idEntita) {
		this.idEntita = idEntita;
	}
	public String getCodiceVisualizzato() {
		return codiceVisualizzato;
	}
	public void setCodiceVisualizzato(String codiceVisualizzato) {
		this.codiceVisualizzato = codiceVisualizzato;
	}
	public BigDecimal getIdProgetto() {
		return idProgetto;
	}
	public void setIdProgetto(BigDecimal idProgetto) {
		this.idProgetto = idProgetto;
	}
	
}
