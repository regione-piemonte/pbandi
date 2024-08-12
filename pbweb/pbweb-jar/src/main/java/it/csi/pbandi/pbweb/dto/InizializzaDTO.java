package it.csi.pbandi.pbweb.dto;

import it.csi.pbandi.pbservizit.security.UserInfoSec;
import it.csi.pbandi.pbweb.dto.DatiProgettoInizializzazioneDTO;

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
