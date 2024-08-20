/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.vo.affidamenti;

import java.math.BigDecimal;
import java.sql.Date;

import it.csi.pbandi.pbgestfinbo.dto.AttivitaDTO;

public class DichiarazioneSpesaCampionamentoVO {
	
	private AttivitaDTO[] tipoDichiarazione ;
	private boolean flagValidita; 
	private BigDecimal imporRendicontatoInizio;
	private BigDecimal imporRendicontatoFine;
	private BigDecimal importoValidatoInizio;
	private BigDecimal importoValidatoFine;
	private Date dataRicezioneInizio;
	private Date dataRicezioneFine;
	private Date dataUltimoEsitoInizio;
	private Date dataUltimoEsitoFine;
	
	
	public AttivitaDTO[] getTipoDichiarazione() {
		return tipoDichiarazione;
	}
	public void setTipoDichiarazione(AttivitaDTO[] tipoDichiarazione) {
		this.tipoDichiarazione = tipoDichiarazione;
	}
	public boolean isFlagValidita() {
		return flagValidita;
	}
	public void setFlagValidita(boolean flagValidita) {
		this.flagValidita = flagValidita;
	}
	public BigDecimal getImporRendicontatoInizio() {
		return imporRendicontatoInizio;
	}
	public void setImporRendicontatoInizio(BigDecimal imporRendicontatoInizio) {
		this.imporRendicontatoInizio = imporRendicontatoInizio;
	}
	public BigDecimal getImporRendicontatoFine() {
		return imporRendicontatoFine;
	}
	public void setImporRendicontatoFine(BigDecimal imporRendicontatoFine) {
		this.imporRendicontatoFine = imporRendicontatoFine;
	}
	public BigDecimal getImportoValidatoInizio() {
		return importoValidatoInizio;
	}
	public void setImportoValidatoInizio(BigDecimal importoValidatoInizio) {
		this.importoValidatoInizio = importoValidatoInizio;
	}
	public BigDecimal getImportoValidatoFine() {
		return importoValidatoFine;
	}
	public void setImportoValidatoFine(BigDecimal importoValidatoFine) {
		this.importoValidatoFine = importoValidatoFine;
	}
	public Date getDataRicezioneInizio() {
		return dataRicezioneInizio;
	}
	public void setDataRicezioneInizio(Date dataRicezioneInizio) {
		this.dataRicezioneInizio = dataRicezioneInizio;
	}
	public Date getDataRicezioneFine() {
		return dataRicezioneFine;
	}
	public void setDataRicezioneFine(Date dataRicezioneFine) {
		this.dataRicezioneFine = dataRicezioneFine;
	}
	public Date getDataUltimoEsitoInizio() {
		return dataUltimoEsitoInizio;
	}
	public void setDataUltimoEsitoInizio(Date dataUltimoEsitoInizio) {
		this.dataUltimoEsitoInizio = dataUltimoEsitoInizio;
	}
	public Date getDataUltimoEsitoFine() {
		return dataUltimoEsitoFine;
	}
	public void setDataUltimoEsitoFine(Date dataUltimoEsitoFine) {
		this.dataUltimoEsitoFine = dataUltimoEsitoFine;
	}
	
	
}
