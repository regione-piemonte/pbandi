/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.vo;

import java.util.Date;

public class LavorazioneBoxListVO {

	private String nomeAttivita;
	private Date dataInizio;
	private Date dataUltimaModifica;

	

	public LavorazioneBoxListVO() {
	}


	public LavorazioneBoxListVO(String nomeAttivita, Date dataInizio, Date dataUltimaModifica) {
		this.nomeAttivita = nomeAttivita;
		this.dataInizio = dataInizio;
		this.dataUltimaModifica = dataUltimaModifica;
	}


	public String getNomeAttivita() {
		return nomeAttivita;
	}


	public void setNomeAttivita(String nomeAttivita) {
		this.nomeAttivita = nomeAttivita;
	}


	public Date getDataInizio() {
		return dataInizio;
	}


	public void setDataInizio(Date dataInizio) {
		this.dataInizio = dataInizio;
	}


	public Date getDataUltimaModifica() {
		return dataUltimaModifica;
	}


	public void setDataUltimaModifica(Date dataUltimaModifica) {
		this.dataUltimaModifica = dataUltimaModifica;
	}


	@Override
	public String toString() {
		return "LavorazioneBoxListVO [nomeAttivita=" + nomeAttivita + ", dataInizio=" + dataInizio
				+ ", dataUltimaModifica=" + dataUltimaModifica + "]";
	}


}
