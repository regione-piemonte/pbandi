/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.pbandisrv.business;

import javax.ejb.SessionContext;

public class EjbSessionContextBean {
	
	private SessionContext ejbSession;

	public SessionContext getEjbSession() {
		return ejbSession;
	}

	public void setEjbSession(SessionContext ejbSession) {
		this.ejbSession = ejbSession;
	}
	
	
}
