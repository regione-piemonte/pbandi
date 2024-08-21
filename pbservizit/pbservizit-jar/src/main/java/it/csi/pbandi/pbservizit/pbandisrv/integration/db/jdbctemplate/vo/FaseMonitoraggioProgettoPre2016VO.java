/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo;

import java.sql.Date;

public class FaseMonitoraggioProgettoPre2016VO extends GenericVO {

	private String descMotivoScostamento;
	private String descTipoOperazione;
	private String descIter;

	private Long idFaseMonit;
	private Long idProgetto;
	private Long idIter;
	private Long idMotivoScostamento;
	private String codIgrueT35;
	private String descFaseMonit;
	private String flagObbligatorio;
	private String flagControlloIndicat;
	private Date dtInizioPrevista;
	private Date dtFinePrevista;
	private Date dtInizioEffettiva;
	private Date dtFineEffettiva;
	private Date dtAggiornamento;

	/**
	 * @return the idFaseMonit
	 */
	public Long getIdFaseMonit() {
		return idFaseMonit;
	}

	/**
	 * @param idFaseMonit
	 *            the idFaseMonit to set
	 */
	public void setIdFaseMonit(Long idFaseMonit) {
		this.idFaseMonit = idFaseMonit;
	}

	/**
	 * @return the idProgetto
	 */
	public Long getIdProgetto() {
		return idProgetto;
	}

	/**
	 * @param idProgetto
	 *            the idProgetto to set
	 */
	public void setIdProgetto(Long idProgetto) {
		this.idProgetto = idProgetto;
	}

	/**
	 * @return the idIter
	 */
	public Long getIdIter() {
		return idIter;
	}

	/**
	 * @param idIter
	 *            the idIter to set
	 */
	public void setIdIter(Long idIter) {
		this.idIter = idIter;
	}

	/**
	 * @return the idMotivoScostamento
	 */
	public Long getIdMotivoScostamento() {
		return idMotivoScostamento;
	}

	/**
	 * @param idMotivoScostamento
	 *            the idMotivoScostamento to set
	 */
	public void setIdMotivoScostamento(Long idMotivoScostamento) {
		this.idMotivoScostamento = idMotivoScostamento;
	}

	/**
	 * @return the codIgrueT35
	 */
	public String getCodIgrueT35() {
		return codIgrueT35;
	}

	/**
	 * @param codIgrueT35
	 *            the codIgrueT35 to set
	 */
	public void setCodIgrueT35(String codIgrueT35) {
		this.codIgrueT35 = codIgrueT35;
	}

	/**
	 * @return the descFaseMonit
	 */
	public String getDescFaseMonit() {
		return descFaseMonit;
	}

	/**
	 * @param descFaseMonit
	 *            the descFaseMonit to set
	 */
	public void setDescFaseMonit(String descFaseMonit) {
		this.descFaseMonit = descFaseMonit;
	}

	/**
	 * @return the flagObbligatorio
	 */
	public String getFlagObbligatorio() {
		return flagObbligatorio;
	}

	/**
	 * @param flagObbligatorio
	 *            the flagObbligatorio to set
	 */
	public void setFlagObbligatorio(String flagObbligatorio) {
		this.flagObbligatorio = flagObbligatorio;
	}

	/**
	 * @return the flagControlloIndicat
	 */
	public String getFlagControlloIndicat() {
		return flagControlloIndicat;
	}

	/**
	 * @param flagControlloIndicat
	 *            the flagControlloIndicat to set
	 */
	public void setFlagControlloIndicat(String flagControlloIndicat) {
		this.flagControlloIndicat = flagControlloIndicat;
	}

	/**
	 * @return the dtInizioPrevista
	 */
	public Date getDtInizioPrevista() {
		return dtInizioPrevista;
	}

	/**
	 * @param dtInizioPrevista
	 *            the dtInizioPrevista to set
	 */
	public void setDtInizioPrevista(Date dtInizioPrevista) {
		this.dtInizioPrevista = dtInizioPrevista;
	}

	/**
	 * @return the dtFinePrevista
	 */
	public Date getDtFinePrevista() {
		return dtFinePrevista;
	}

	/**
	 * @param dtFinePrevista
	 *            the dtFinePrevista to set
	 */
	public void setDtFinePrevista(Date dtFinePrevista) {
		this.dtFinePrevista = dtFinePrevista;
	}

	/**
	 * @return the dtInizioEffettiva
	 */
	public Date getDtInizioEffettiva() {
		return dtInizioEffettiva;
	}

	/**
	 * @param dtInizioEffettiva
	 *            the dtInizioEffettiva to set
	 */
	public void setDtInizioEffettiva(Date dtInizioEffettiva) {
		this.dtInizioEffettiva = dtInizioEffettiva;
	}

	/**
	 * @return the dtFineEffettiva
	 */
	public Date getDtFineEffettiva() {
		return dtFineEffettiva;
	}

	/**
	 * @param dtFineEffettiva
	 *            the dtFineEffettiva to set
	 */
	public void setDtFineEffettiva(Date dtFineEffettiva) {
		this.dtFineEffettiva = dtFineEffettiva;
	}

	public void setDescTipoOperazione(String descTipoOperazione) {
		this.descTipoOperazione = descTipoOperazione;
	}

	public String getDescTipoOperazione() {
		return descTipoOperazione;
	}

	public void setDescIter(String descIter) {
		this.descIter = descIter;
	}

	public String getDescIter() {
		return descIter;
	}

	public void setDescMotivoScostamento(String descMotivoScostamento) {
		this.descMotivoScostamento = descMotivoScostamento;
	}

	public String getDescMotivoScostamento() {
		return descMotivoScostamento;
	}

	public void setDtAggiornamento(Date dtAggiornamento) {
		this.dtAggiornamento = dtAggiornamento;
	}

	public Date getDtAggiornamento() {
		return dtAggiornamento;
	}

}
