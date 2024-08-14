/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebcert.integration.request;

import java.util.List;

import it.csi.pbandi.pbservizit.security.UserInfoSec;

public class ProposteCertificazioneRequest {

	private UserInfoSec userInfo;
	private List<String> stati;

	public UserInfoSec getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(UserInfoSec userInfo) {
		this.userInfo = userInfo;
	}

	public List<String> getStati() {
		return stati;
	}

	public void setStati(List<String> stati) {
		this.stati = stati;
	}

	@Override
	public String toString() {
		return "ProposteCertificazioneRequest [userInfo=" + userInfo + ", stati=" + stati + "]";
	}

}
