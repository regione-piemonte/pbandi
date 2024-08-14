/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebcert.integration.vo;

import java.math.BigDecimal;

public class ImportiAnticipoPropostaCertificazioneVO {
	private BigDecimal idPropostaCertificaz;
	private String descBreveLinea;
	private BigDecimal importoAnticipo;
	private BigDecimal idLineaDiIntervento;

	public BigDecimal getIdPropostaCertificaz() {
		return idPropostaCertificaz;
	}
	public void setIdPropostaCertificaz(BigDecimal idPropostaCertificaz) {
		this.idPropostaCertificaz = idPropostaCertificaz;
	}
	public String getDescBreveLinea() {
		return descBreveLinea;
	}
	public void setDescBreveLinea(String descBreveLinea) {
		this.descBreveLinea = descBreveLinea;
	}
	public BigDecimal getImportoAnticipo() {
		return importoAnticipo;
	}
	public void setImportoAnticipo(BigDecimal importoAnticipo) {
		this.importoAnticipo = importoAnticipo;
	}
	public BigDecimal getIdLineaDiIntervento() {
		return idLineaDiIntervento;
	}
	public void setIdLineaDiIntervento(BigDecimal idLineaDiIntervento) {
		this.idLineaDiIntervento = idLineaDiIntervento;
	}
}
