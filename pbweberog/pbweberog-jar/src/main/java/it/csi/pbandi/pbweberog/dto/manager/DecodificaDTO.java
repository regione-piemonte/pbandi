/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweberog.dto.manager;

import java.util.Date;

public class DecodificaDTO {
	private Long id;
	private String descrizione;
	private String descrizioneBreve;
	private Date dataInizioValidita;
	private Date dataFineValidita;
	private String pkColumnName;
	
	public void setId(Long val) {
		id = val;
	}
	public Long getId() {
		return id;
	}
	public void setDescrizione(String val) {
		descrizione = val;
	}
	public String getDescrizione() {
		return descrizione;
	}
	public void setDescrizioneBreve(String val) {
		descrizioneBreve = val;
	}
	public String getDescrizioneBreve() {
		return descrizioneBreve;
	}
	public void setDataInizioValidita(Date val) {
		dataInizioValidita = val;
	}
	public Date getDataInizioValidita() {
		return dataInizioValidita;
	}
	public void setDataFineValidita(Date val) {
		dataFineValidita = val;
	}
	public Date getDataFineValidita() {
		return dataFineValidita;
	}
	public void setPkColumnName(String pkColumnName) {
		this.pkColumnName = pkColumnName;
	}
	public String getPkColumnName() {
		return pkColumnName;
	}
}
