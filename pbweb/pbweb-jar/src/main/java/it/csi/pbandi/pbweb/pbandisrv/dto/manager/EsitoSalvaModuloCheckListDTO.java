/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.pbandisrv.dto.manager;

import java.math.BigDecimal;

public class EsitoSalvaModuloCheckListDTO  implements java.io.Serializable {

	private byte[] byteModulo = null;

	public void setByteModulo(byte[] val) {
		byteModulo = val;
	}

	public byte[] getByteModulo() {
		return byteModulo;
	}

	private boolean esito = true;

	public void setEsito(boolean val) {
		esito = val;
	}

	public boolean getEsito() {
		return esito;
	}

	private java.lang.String message = null;

	public void setMessage(java.lang.String val) {
		message = val;
	}

	public java.lang.String getMessage() {
		return message;
	}

	private java.lang.String[] params = null;

	public void setParams(java.lang.String[] val) {
		params = val;
	}


	public java.lang.String[] getParams() {
		return params;
	}

	private BigDecimal idDocumentoIndex;
	

	public BigDecimal getIdDocumentoIndex() {
		return idDocumentoIndex;
	}

	public void setIdDocumentoIndex(BigDecimal idDocumentoIndex) {
		this.idDocumentoIndex = idDocumentoIndex;
	}

	public void setIdChecklist(BigDecimal idChecklist) {
		this.idChecklist = idChecklist;
	}

	public BigDecimal getIdChecklist() {
		return idChecklist;
	}

	private BigDecimal idChecklist;
	
}
