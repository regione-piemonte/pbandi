/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.security;

import it.csi.pbandi.pbweb.integration.vo.ProgettoBandoLineaVO;

public class UserInfoSecEsteso extends it.csi.pbandi.pbservizit.security.UserInfoSec {
	
	private static final long serialVersionUID = 1L;
	
	private ProgettoBandoLineaVO rogettoBandoLinea = null;

	public ProgettoBandoLineaVO getRogettoBandoLinea() {
		return rogettoBandoLinea;
	}

	public void setRogettoBandoLinea(ProgettoBandoLineaVO rogettoBandoLinea) {
		this.rogettoBandoLinea = rogettoBandoLinea;
	}

}
