/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebrce.pbandisrv.integration.db.exception;

public class TooMuchRecordFoundException extends RuntimeException {

	public TooMuchRecordFoundException(String string) {
		super(string);
	}

}
