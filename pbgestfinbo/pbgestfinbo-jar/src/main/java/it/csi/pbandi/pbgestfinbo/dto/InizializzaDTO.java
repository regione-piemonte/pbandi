/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.dto;

import it.csi.pbandi.pbservizit.security.UserInfoSec;

public class InizializzaDTO implements java.io.Serializable {

	static final long serialVersionUID = 1;
	
	private UserInfoSec userInfoSec = null;

	public UserInfoSec getUserInfoSec() {
		return userInfoSec;
	}

	public void setUserInfoSec(UserInfoSec userInfoSec) {
		this.userInfoSec = userInfoSec;
	}

}
