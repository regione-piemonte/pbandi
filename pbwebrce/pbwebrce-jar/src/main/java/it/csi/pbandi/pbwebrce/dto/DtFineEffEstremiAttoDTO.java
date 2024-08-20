/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebrce.dto;

import java.util.Date;

public class DtFineEffEstremiAttoDTO implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Date dtFineEffettiva;
	private String estremiAttoAmministrativo;

	public Date getDtFineEffettiva() {
		return dtFineEffettiva;
	}

	public void setDtFineEffettiva(Date dtFineEffettiva) {
		this.dtFineEffettiva = dtFineEffettiva;
	}

	public String getEstremiAttoAmministrativo() {
		return estremiAttoAmministrativo;
	}

	public void setEstremiAttoAmministrativo(String estremiAttoAmministrativo) {
		this.estremiAttoAmministrativo = estremiAttoAmministrativo;
	}

}
