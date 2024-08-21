/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo;

import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiDDeliberaVO;

public class DeliberaVO extends PbandiDDeliberaVO {
	
	private String descDelibera;

	public void setDescDelibera(String descDelibera) {
		this.descDelibera = descDelibera;
	}

	public String getDescDelibera() {
		return descDelibera;
	}

}
