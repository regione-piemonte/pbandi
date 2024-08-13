/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebbo.integration.vo;

import java.io.Serializable;
import java.util.Date;

public class NaturaCipeVO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long idNaturaCipe;
	private String codNaturaCipe;
	private String descNaturaCipe;
	private Date dtInizioValidita;
	private Date dtFineValidita;
	private Long idTipoOperazione;

	public Long getIdNaturaCipe() {
		return idNaturaCipe;
	}

	public void setIdNaturaCipe(Long idNaturaCipe) {
		this.idNaturaCipe = idNaturaCipe;
	}

	public String getCodNaturaCipe() {
		return codNaturaCipe;
	}

	public void setCodNaturaCipe(String codNaturaCipe) {
		this.codNaturaCipe = codNaturaCipe;
	}

	public String getDescNaturaCipe() {
		return descNaturaCipe;
	}

	public void setDescNaturaCipe(String descNaturaCipe) {
		this.descNaturaCipe = descNaturaCipe;
	}

	public Date getDtInizioValidita() {
		return dtInizioValidita;
	}

	public void setDtInizioValidita(Date dtInizioValidita) {
		this.dtInizioValidita = dtInizioValidita;
	}

	public Date getDtFineValidita() {
		return dtFineValidita;
	}

	public void setDtFineValidita(Date dtFineValidita) {
		this.dtFineValidita = dtFineValidita;
	}

	public Long getIdTipoOperazione() {
		return idTipoOperazione;
	}

	public void setIdTipoOperazione(Long idTipoOperazione) {
		this.idTipoOperazione = idTipoOperazione;
	}

}
