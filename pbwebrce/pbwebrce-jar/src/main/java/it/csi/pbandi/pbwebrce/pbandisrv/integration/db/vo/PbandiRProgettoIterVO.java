/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


package it.csi.pbandi.pbwebrce.pbandisrv.integration.db.vo;

import java.util.List;

public class PbandiRProgettoIterVO extends GenericVO {

	Long idProgettoIter;
	Long idProgetto;
	Long idIter;
	Long flagFaseChiusa;
	Long idDichiarazioneSpesa;

	public Long getIdProgettoIter() {
		return idProgettoIter;
	}

	public void setIdProgettoIter(Long idProgettoIter) {
		this.idProgettoIter = idProgettoIter;
	}

	public Long getIdProgetto() {
		return idProgetto;
	}

	public void setIdProgetto(Long idProgetto) {
		this.idProgetto = idProgetto;
	}

	public Long getIdIter() {
		return idIter;
	}

	public void setIdIter(Long idIter) {
		this.idIter = idIter;
	}

	public Long getFlagFaseChiusa() {
		return flagFaseChiusa;
	}

	public void setFlagFaseChiusa(Long flagFaseChiusa) {
		this.flagFaseChiusa = flagFaseChiusa;
	}

	public Long getIdDichiarazioneSpesa() {
		return idDichiarazioneSpesa;
	}

	public void setIdDichiarazioneSpesa(Long idDichiarazioneSpesa) {
		this.idDichiarazioneSpesa = idDichiarazioneSpesa;
	}

	@Override
	public List getPK() {
		return null;
	}

	@Override
	public boolean isPKValid() {
		return false;
	}

}
