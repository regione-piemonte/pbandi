/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebrce.pbandisrv.integration.db.vo;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

public class PbandiVPropAsseSifVO extends GenericVO {
	
	private BigDecimal idPropostaCertificaz;
	private BigDecimal idAsse;
	private String descBreveLinea;
	private String descLinea;
	private BigDecimal costoAmmesso;
	private BigDecimal contributoPubblicoConcesso;
	private BigDecimal importoErogazioni;

	@SuppressWarnings("unchecked")
	@Override
	public List getPK() {
		return null;
	}

	@Override
	public boolean isPKValid() {
		return false;
	}

	public String getDescBreveLinea() {
		return descBreveLinea;
	}

	public void setDescBreveLinea(String descBreveLinea) {
		this.descBreveLinea = descBreveLinea;
	}

	public BigDecimal getIdPropostaCertificaz() {
		return idPropostaCertificaz;
	}

	public void setIdPropostaCertificaz(BigDecimal idPropostaCertificaz) {
		this.idPropostaCertificaz = idPropostaCertificaz;
	}


	public String getDescLinea() {
		return descLinea;
	}

	public void setDescLinea(String descLinea) {
		this.descLinea = descLinea;
	}

	public BigDecimal getCostoAmmesso() {
		return costoAmmesso;
	}

	public void setCostoAmmesso(BigDecimal costoAmmesso) {
		this.costoAmmesso = costoAmmesso;
	}

	public BigDecimal getContributoPubblicoConcesso() {
		return contributoPubblicoConcesso;
	}

	public void setContributoPubblicoConcesso(BigDecimal contributoPubblicoConcesso) {
		this.contributoPubblicoConcesso = contributoPubblicoConcesso;
	}

	public BigDecimal getIdAsse() {
		return idAsse;
	}

	public void setIdAsse(BigDecimal idAsse) {
		this.idAsse = idAsse;
	}

	public BigDecimal getImportoErogazioni() {
		return importoErogazioni;
	}

	public void setImportoErogazioni(BigDecimal importoErogazioni) {
		this.importoErogazioni = importoErogazioni;
	}

}
