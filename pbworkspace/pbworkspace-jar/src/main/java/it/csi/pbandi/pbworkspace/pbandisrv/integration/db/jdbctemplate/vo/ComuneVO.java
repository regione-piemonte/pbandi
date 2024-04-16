/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbworkspace.pbandisrv.integration.db.jdbctemplate.vo;

import java.math.BigDecimal;
import java.util.List;

import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.GenericVO;

public class ComuneVO extends GenericVO {
	private String flagNazioneItaliana;
	private BigDecimal idComune;
	private BigDecimal idProvincia;
	private BigDecimal idRegione;
	private BigDecimal idNazione;
	private String descComune;
	private String siglaProvincia;
	private String codIstatComune;
	
	@Override
	public List getPK() {
		return null;
	}

	@Override
	public boolean isPKValid() {
		return false;
	}

	public String getFlagNazioneItaliana() {
		return flagNazioneItaliana;
	}

	public void setFlagNazioneItaliana(String flagNazioneItaliana) {
		this.flagNazioneItaliana = flagNazioneItaliana;
	}

	public BigDecimal getIdComune() {
		return idComune;
	}

	public void setIdComune(BigDecimal idComune) {
		this.idComune = idComune;
	}

	public BigDecimal getIdProvincia() {
		return idProvincia;
	}

	public void setIdProvincia(BigDecimal idProvincia) {
		this.idProvincia = idProvincia;
	}

	public BigDecimal getIdRegione() {
		return idRegione;
	}

	public void setIdRegione(BigDecimal idRegione) {
		this.idRegione = idRegione;
	}

	public BigDecimal getIdNazione() {
		return idNazione;
	}

	public void setIdNazione(BigDecimal idNazione) {
		this.idNazione = idNazione;
	}
	public String getDescComune() {
		return descComune;
	}

	public void setDescComune(String descComune) {
		this.descComune = descComune;
	}

	public String getCodIstatComune() {
		return codIstatComune;
	}

	public void setCodIstatComune(String codIstatComune) {
		this.codIstatComune = codIstatComune;
	}

	public String getSiglaProvincia() {
		return siglaProvincia;
	}

	public void setSiglaProvincia(String siglaProvincia) {
		this.siglaProvincia = siglaProvincia;
	}

	
}
