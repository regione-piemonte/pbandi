/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebrce.dto.cronoprogramma;

import java.util.Date;

public class CronoprogrammaItem implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	
	private Long idFaseMonit;
	private String descFaseMonit;
	private String codIgrue;
	private String dtInizioPrevista;
	private String dtFinePrevista;
	private String dtInizioEffettiva;
	private String dtFineEffettiva;
	private Long idMotivoScostamento;
	private boolean flagObbligatorio;
	private Long idIter;
	private boolean dtInizioPrevistaEditable;
	private boolean dtFinePrevistaEditable;
	private boolean dtInizioEffettivaEditable;
	private boolean dtFineEffettivaEditable;
	private boolean motivoScostamentoEditable;
	
	public Long getIdFaseMonit() {
		return idFaseMonit;
	}

	public void setIdFaseMonit(Long idFaseMonit) {
		this.idFaseMonit = idFaseMonit;
	}

	public String getDescFaseMonit() {
		return descFaseMonit;
	}

	public void setDescFaseMonit(String descFaseMonit) {
		this.descFaseMonit = descFaseMonit;
	}

	public String getCodIgrue() {
		return codIgrue;
	}

	public void setCodIgrue(String codIgrue) {
		this.codIgrue = codIgrue;
	}	

	public String getDtInizioPrevista() {
		return dtInizioPrevista;
	}

	public void setDtInizioPrevista(String dtInizioPrevista) {
		this.dtInizioPrevista = dtInizioPrevista;
	}

	public String getDtFinePrevista() {
		return dtFinePrevista;
	}

	public void setDtFinePrevista(String dtFinePrevista) {
		this.dtFinePrevista = dtFinePrevista;
	}

	public String getDtInizioEffettiva() {
		return dtInizioEffettiva;
	}

	public void setDtInizioEffettiva(String dtInizioEffettiva) {
		this.dtInizioEffettiva = dtInizioEffettiva;
	}

	public String getDtFineEffettiva() {
		return dtFineEffettiva;
	}

	public void setDtFineEffettiva(String dtFineEffettiva) {
		this.dtFineEffettiva = dtFineEffettiva;
	}

	public Long getIdMotivoScostamento() {
		return idMotivoScostamento;
	}

	public void setIdMotivoScostamento(Long idMotivoScostamento) {
		this.idMotivoScostamento = idMotivoScostamento;
	}

	public boolean getFlagObbligatorio() {
		return flagObbligatorio;
	}

	public void setFlagObbligatorio(boolean flagObbligatorio) {
		this.flagObbligatorio = flagObbligatorio;
	}

	public Long getIdIter() {
		return idIter;
	}

	public void setIdIter(Long idIter) {
		this.idIter = idIter;
	}

	public boolean isDtInizioPrevistaEditable() {
		return dtInizioPrevistaEditable;
	}

	public void setDtInizioPrevistaEditable(boolean dtInizioPrevistaEditable) {
		this.dtInizioPrevistaEditable = dtInizioPrevistaEditable;
	}

	public boolean isDtFinePrevistaEditable() {
		return dtFinePrevistaEditable;
	}

	public void setDtFinePrevistaEditable(boolean dtFinePrevistaEditable) {
		this.dtFinePrevistaEditable = dtFinePrevistaEditable;
	}

	public boolean isDtInizioEffettivaEditable() {
		return dtInizioEffettivaEditable;
	}

	public void setDtInizioEffettivaEditable(boolean dtInizioEffettivaEditable) {
		this.dtInizioEffettivaEditable = dtInizioEffettivaEditable;
	}

	public boolean isDtFineEffettivaEditable() {
		return dtFineEffettivaEditable;
	}

	public void setDtFineEffettivaEditable(boolean dtFineEffettivaEditable) {
		this.dtFineEffettivaEditable = dtFineEffettivaEditable;
	}

	public boolean isMotivoScostamentoEditable() {
		return motivoScostamentoEditable;
	}

	public void setMotivoScostamentoEditable(boolean motivoScostamentoEditable) {
		this.motivoScostamentoEditable = motivoScostamentoEditable;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public CronoprogrammaItem() {
		super();

	}

	public String toString() {
		return super.toString();
	}
}
