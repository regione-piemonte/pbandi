/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebrce.integration.vo.affidamenti;

import java.math.BigDecimal;

import it.csi.pbandi.pbwebrce.pbandisrv.integration.db.vo.PbandiTChecklistVO;


public class CheckListAppaltoVO extends PbandiTChecklistVO  {
	private BigDecimal idAppalto;
	
	public CheckListAppaltoVO(){
		super();
	}

	public BigDecimal getIdAppalto() {
		return idAppalto;
	}

	public void setIdAppalto(BigDecimal idAppalto) {
		this.idAppalto = idAppalto;
	}
}
