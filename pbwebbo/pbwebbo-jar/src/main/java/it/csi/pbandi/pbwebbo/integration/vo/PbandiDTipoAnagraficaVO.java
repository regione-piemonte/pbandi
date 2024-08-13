/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebbo.integration.vo;

import java.io.Serializable;
import java.sql.Date;

public class PbandiDTipoAnagraficaVO implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Long idTipoAnagrafica;
	private String descTipoAnagrafica;
	private String descBreveTipoAnagrafica;
	private Date dtInizioValidita;
	private Date dtFineValidita;
	private Long idRuoloHelp;
	private Long idCategAnagrafica;
	
	public Long getIdTipoAnagrafica() {
		return idTipoAnagrafica;
	}
	public void setIdTipoAnagrafica(Long idTipoAnagrafica) {
		this.idTipoAnagrafica = idTipoAnagrafica;
	}
	public String getDescTipoAnagrafica() {
		return descTipoAnagrafica;
	}
	public void setDescTipoAnagrafica(String descTipoAnagrafica) {
		this.descTipoAnagrafica = descTipoAnagrafica;
	}
	public String getDescBreveTipoAnagrafica() {
		return descBreveTipoAnagrafica;
	}
	public void setDescBreveTipoAnagrafica(String descBreveTipoAnagrafica) {
		this.descBreveTipoAnagrafica = descBreveTipoAnagrafica;
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
	public Long getIdRuoloHelp() {
		return idRuoloHelp;
	}
	public void setIdRuoloHelp(Long idRuoloHelp) {
		this.idRuoloHelp = idRuoloHelp;
	}
	public Long getIdCategAnagrafica() {
		return idCategAnagrafica;
	}
	public void setIdCategAnagrafica(Long idCategAnagrafica) {
		this.idCategAnagrafica = idCategAnagrafica;
	}
	
}
