/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweberog.pbandisrv.integration.db.jdbctemplate.vo;

import java.util.List;

public abstract class GenericVO extends it.csi.pbandi.pbweberog.pbandisrv.integration.db.vo.GenericVO {
	private static final long serialVersionUID = 1L;
	
	@Override
	public boolean isPKValid() {
		return false;
	}
	
	
	@Override
	public List getPK() {
		return null;
	}
	
}
