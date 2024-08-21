/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.integration.services.bilancio.vo;

public abstract class BaseVO {
	
	private ResultVO result;

	public ResultVO getResult() {
		return result;
	}

	public void setResult(ResultVO result) {
		this.result = result;
	}
	
}
