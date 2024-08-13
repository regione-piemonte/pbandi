/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.pbandisrv.exception.manager;

public class XmlDataExtractionException extends Exception {

	public XmlDataExtractionException(String message, Exception e) {
		super(message, e);
	}

}
