/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbworkspace.integration.request;

import java.util.List;

public class CambiaStatoNotificaRequest {
	private Long idNotifica;
	private String statoNotifica;
	private List<Long> idNotificheArray;

	public Long getIdNotifica() {
		return idNotifica;
	}

	public void setIdNotifica(Long idNotifica) {
		this.idNotifica = idNotifica;
	}

	public String getStatoNotifica() {
		return statoNotifica;
	}

	public void setStatoNotifica(String statoNotifica) {
		this.statoNotifica = statoNotifica;
	}

	public List<Long> getIdNotificheArray() {
		return idNotificheArray;
	}

	public void setIdNotificheArray(List<Long> idNotificheArray) {
		this.idNotificheArray = idNotificheArray;
	}
}
