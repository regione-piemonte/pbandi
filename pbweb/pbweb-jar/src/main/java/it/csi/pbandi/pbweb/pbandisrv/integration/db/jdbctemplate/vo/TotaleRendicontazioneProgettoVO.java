/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo;

import java.math.BigDecimal;

public class TotaleRendicontazioneProgettoVO extends GenericVO {
	private BigDecimal idProgetto ;
	private BigDecimal importoRendicontazione;
	
	public void setIdProgetto(BigDecimal idProgetto) {
		this.idProgetto = idProgetto;
	}
	public BigDecimal getIdProgetto() {
		return idProgetto;
	}
	public void setImportoRendicontazione(BigDecimal importoRendicontazione) {
		this.importoRendicontazione = importoRendicontazione;
	}
	public BigDecimal getImportoRendicontazione() {
		return importoRendicontazione;
	}

}
