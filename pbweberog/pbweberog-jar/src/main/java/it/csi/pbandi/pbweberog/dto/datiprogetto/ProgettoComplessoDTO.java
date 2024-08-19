/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweberog.dto.datiprogetto;

public class ProgettoComplessoDTO implements java.io.Serializable {

	static final long serialVersionUID = 1;
	private Long idProgettoComplesso;
	private String descProgettoComplesso;
	public Long getIdProgettoComplesso() {
		return idProgettoComplesso;
	}
	public void setIdProgettoComplesso(Long idProgettoComplesso) {
		this.idProgettoComplesso = idProgettoComplesso;
	}
	public String getDescProgettoComplesso() {
		return descProgettoComplesso;
	}
	public void setDescProgettoComplesso(String descProgettoComplesso) {
		this.descProgettoComplesso = descProgettoComplesso;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}


}
