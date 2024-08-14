/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebcert.integration.request;

import java.util.List;

import it.csi.pbandi.pbservizit.security.UserInfoSec;

public class PropostaCertifLineaRequest {
	private UserInfoSec userInfo;
	private List<Long> idLineeIntervento;

	public UserInfoSec getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(UserInfoSec userInfo) {
		this.userInfo = userInfo;
	}

	public List<Long> getIdLineeIntervento() {
		return idLineeIntervento;
	}

	public void setIdLineeIntervento(List<Long> idLineeIntervento) {
		this.idLineeIntervento = idLineeIntervento;
	}

}
