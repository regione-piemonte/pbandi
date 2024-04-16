/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbworkspace.integration.vo;

import java.util.List;

public class NotificheFrequenzeVO {
	private List<FrequenzaVO> frequenze;
	private List<NotificaAlertVO> notificheAlert;

	public List<FrequenzaVO> getFrequenze() {
		return frequenze;
	}

	public void setFrequenze(List<FrequenzaVO> frequenze) {
		this.frequenze = frequenze;
	}

	public List<NotificaAlertVO> getNotificheAlert() {
		return notificheAlert;
	}

	public void setNotificheAlert(List<NotificaAlertVO> notificheAlert) {
		this.notificheAlert = notificheAlert;
	}
}
