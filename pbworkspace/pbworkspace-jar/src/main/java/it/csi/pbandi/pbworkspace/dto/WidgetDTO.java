/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbworkspace.dto;

public class WidgetDTO implements java.io.Serializable {

	static final long serialVersionUID = 1;

	private Long idWidget;
	private String descBreveWIdget;
	private String descWidget;
	private String titoloWidget;
	private Boolean flagModifica;
	private Boolean flagWidgetAttivo;

	public Long getIdWidget() {
		return idWidget;
	}

	public void setIdWidget(Long idWidget) {
		this.idWidget = idWidget;
	}

	public String getDescBreveWIdget() {
		return descBreveWIdget;
	}

	public void setDescBreveWIdget(String descBreveWIdget) {
		this.descBreveWIdget = descBreveWIdget;
	}

	public String getDescWidget() {
		return descWidget;
	}

	public void setDescWidget(String descWidget) {
		this.descWidget = descWidget;
	}

	public String getTitoloWidget() {
		return titoloWidget;
	}

	public void setTitoloWidget(String titoloWidget) {
		this.titoloWidget = titoloWidget;
	}

	public Boolean getFlagModifica() {
		return flagModifica;
	}

	public void setFlagModifica(Boolean flagModifica) {
		this.flagModifica = flagModifica;
	}

	public Boolean getFlagWidgetAttivo() {
		return flagWidgetAttivo;
	}

	public void setFlagWidgetAttivo(Boolean flagWidgetAttivo) {
		this.flagWidgetAttivo = flagWidgetAttivo;
	}

}
