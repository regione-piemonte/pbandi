/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.dto.gestionecronoprogramma;

import java.util.Date;

public class FaseMonitoraggioDTO implements java.io.Serializable {

	static final long serialVersionUID = 1;

	private Long idFaseMonit;
	private Long idIter;
	private Long idMotivoScostamento;
	private String descFaseMonit;
	private String codIgrueT35;
	private boolean obbligatorio = true;
	private boolean controlloIndicatori = true;
	private Date dtInizioPrevista;
	private Date dtFinePrevista;
	private Date dtInizioEffettiva;
	private Date dtFineEffettiva;
	
	
	
	public Long getIdFaseMonit() {
		return idFaseMonit;
	}
	public void setIdFaseMonit(Long idFaseMonit) {
		this.idFaseMonit = idFaseMonit;
	}
	public Long getIdMotivoScostamento() {
		return idMotivoScostamento;
	}
	public void setIdMotivoScostamento(Long idMotivoScostamento) {
		this.idMotivoScostamento = idMotivoScostamento;
	}
	public String getDescFaseMonit() {
		return descFaseMonit;
	}
	public void setDescFaseMonit(String descFaseMonit) {
		this.descFaseMonit = descFaseMonit;
	}
	public String getCodIgrueT35() {
		return codIgrueT35;
	}
	public void setCodIgrueT35(String codIgrueT35) {
		this.codIgrueT35 = codIgrueT35;
	}
	public boolean getObbligatorio() {
		return obbligatorio;
	}
	public void setObbligatorio(boolean obbligatorio) {
		this.obbligatorio = obbligatorio;
	}
	public boolean isControlloIndicatori() {
		return controlloIndicatori;
	}
	public void setControlloIndicatori(boolean controlloIndicatori) {
		this.controlloIndicatori = controlloIndicatori;
	}
	public Date getDtInizioPrevista() {
		return dtInizioPrevista;
	}
	public void setDtInizioPrevista(Date dtInizioPrevista) {
		this.dtInizioPrevista = dtInizioPrevista;
	}
	public Date getDtFinePrevista() {
		return dtFinePrevista;
	}
	public void setDtFinePrevista(Date dtFinePrevista) {
		this.dtFinePrevista = dtFinePrevista;
	}
	public Date getDtInizioEffettiva() {
		return dtInizioEffettiva;
	}
	public void setDtInizioEffettiva(Date dtInizioEffettiva) {
		this.dtInizioEffettiva = dtInizioEffettiva;
	}
	public Date getDtFineEffettiva() {
		return dtFineEffettiva;
	}
	public void setDtFineEffettiva(Date dtFineEffettiva) {
		this.dtFineEffettiva = dtFineEffettiva;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public Long getIdIter() {
		return idIter;
	}
	public void setIdIter(Long idIter) {
		this.idIter = idIter;
	}

	
	
}
