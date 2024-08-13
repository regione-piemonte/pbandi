/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.pbandiutil.common.util.json;

import java.io.Serializable;

public class GlobalErrorCustom implements Serializable {
	
	public GlobalErrorCustom(String msg) {
		msgError = msg;
	}
	
	private String msgError;

	public String getMsgError() {
		return msgError;
	}

	public void setMsgError(String msgError) {
		this.msgError = msgError;
	}
	

}
