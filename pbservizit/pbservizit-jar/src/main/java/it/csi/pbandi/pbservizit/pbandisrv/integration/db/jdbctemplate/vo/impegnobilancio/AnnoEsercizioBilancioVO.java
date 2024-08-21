/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.impegnobilancio;

import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.GenericVO;

public class AnnoEsercizioBilancioVO extends GenericVO {
	private String annoEsercizio;

	public void setAnnoEsercizio(String annoEsercizio) {
		this.annoEsercizio = annoEsercizio;
	}

	public String getAnnoEsercizio() {
		return annoEsercizio;
	}

}
