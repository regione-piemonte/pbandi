/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo;

import java.math.BigDecimal;

import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiRDetPropCerQpDocVO;

public class DetPropCerQpDocDettPropVO extends PbandiRDetPropCerQpDocVO {
	private BigDecimal idPropostaCertificaz;

	public void setIdPropostaCertificaz(BigDecimal idPropostaCertificaz) {
		this.idPropostaCertificaz = idPropostaCertificaz;
	}

	public BigDecimal getIdPropostaCertificaz() {
		return idPropostaCertificaz;
	}

}
