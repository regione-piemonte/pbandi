/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo;

import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiTPropostaCertificazVO;

public class PropostaCertificazApertaByIdLineaInterventoVO extends PbandiTPropostaCertificazVO {
	private String descBreveStatoPropostaCert;
	private String descStatoPropostaCertif;
    private Long idLineaDiIntervento;

	public Long getIdLineaDiIntervento() {
		return idLineaDiIntervento;
	}

	public void setIdLineaDiIntervento(Long idLineaDiIntervento) {
		this.idLineaDiIntervento = idLineaDiIntervento;
	}

	public void setDescBreveStatoPropostaCert(String descBreveStatoPropostaCert) {
		this.descBreveStatoPropostaCert = descBreveStatoPropostaCert;
	}

	public String getDescBreveStatoPropostaCert() {
		return descBreveStatoPropostaCert;
	}

	public void setDescStatoPropostaCertif(String descStatoPropostaCertif) {
		this.descStatoPropostaCertif = descStatoPropostaCertif;
	}

	public String getDescStatoPropostaCertif() {
		return descStatoPropostaCertif;
	}

}
