/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweberog.pbandisrv.integration.db.exception;

public class RecordNotFoundException extends RuntimeException {

	public RecordNotFoundException(String string) {
		super(string);
	}

}
