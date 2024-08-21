/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.integration.vo;

public class PermessoVO {
	private Long idPermesso;
	private String descMenu;
	private String descBrevePermesso;

	public Long getIdPermesso() {
		return idPermesso;
	}

	public void setIdPermesso(Long idPermesso) {
		this.idPermesso = idPermesso;
	}

	public String getDescMenu() {
		return descMenu;
	}

	public void setDescMenu(String descMenu) {
		this.descMenu = descMenu;
	}

	public String getDescBrevePermesso() {
		return descBrevePermesso;
	}

	public void setDescBrevePermesso(String descBrevePermesso) {
		this.descBrevePermesso = descBrevePermesso;
	}

	@Override
	public String toString() {
		return "PermessoVO [idPermesso=" + idPermesso + ", descMenu=" + descMenu + ", descBrevePermesso="
				+ descBrevePermesso + "]";
	}
}
