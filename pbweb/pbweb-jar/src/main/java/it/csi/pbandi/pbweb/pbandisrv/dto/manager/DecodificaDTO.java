/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.pbandisrv.dto.manager;


public class DecodificaDTO {
	private java.lang.Long _id = null;
	private java.lang.String _descrizione = null;
	private java.lang.String _descrizioneBreve = null;
	private java.util.Date _dataInizioValidita = null;
	private java.util.Date _dataFineValidita = null;
	private java.lang.String pkColumnName = null;
	
	public void setId(java.lang.Long val) {
		_id = val;
	}
	public java.lang.Long getId() {
		return _id;
	}
	public void setDescrizione(java.lang.String val) {
		_descrizione = val;
	}
	public java.lang.String getDescrizione() {
		return _descrizione;
	}
	public void setDescrizioneBreve(java.lang.String val) {
		_descrizioneBreve = val;
	}
	public java.lang.String getDescrizioneBreve() {
		return _descrizioneBreve;
	}
	public void setDataInizioValidita(java.util.Date val) {
		_dataInizioValidita = val;
	}
	public java.util.Date getDataInizioValidita() {
		return _dataInizioValidita;
	}
	public void setDataFineValidita(java.util.Date val) {
		_dataFineValidita = val;
	}
	public java.util.Date getDataFineValidita() {
		return _dataFineValidita;
	}
	public void setPkColumnName(java.lang.String pkColumnName) {
		this.pkColumnName = pkColumnName;
	}
	public java.lang.String getPkColumnName() {
		return pkColumnName;
	}
}
