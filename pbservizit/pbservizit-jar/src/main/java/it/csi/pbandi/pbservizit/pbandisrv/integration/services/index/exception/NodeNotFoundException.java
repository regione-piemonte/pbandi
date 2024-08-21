/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.integration.services.index.exception;


public class NodeNotFoundException extends Exception {

	public NodeNotFoundException(String message) {
		super(message);
	}

	public NodeNotFoundException(String message, Exception e) {
		super(message, e);
	}

}
