/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.recupero;

import java.math.BigDecimal;

import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.GenericVO;


public class TipoRecuperoTipoAnagraficaVO extends GenericVO {
	
	private BigDecimal idTipoRecupero;
	private BigDecimal idTipoAnagrafica;
	private String descBreveTipoRecupero;
	private String descTipoRecupero;
	private String descBreveTipoAnagrafica;
	private String descTipoAnagrafica;
	
	public BigDecimal getIdTipoRecupero() {
		return idTipoRecupero;
	}
	public void setIdTipoRecupero(BigDecimal idTipoRecupero) {
		this.idTipoRecupero = idTipoRecupero;
	}
	public BigDecimal getIdTipoAnagrafica() {
		return idTipoAnagrafica;
	}
	public void setIdTipoAnagrafica(BigDecimal idTipoAnagrafica) {
		this.idTipoAnagrafica = idTipoAnagrafica;
	}
	public String getDescBreveTipoRecupero() {
		return descBreveTipoRecupero;
	}
	public void setDescBreveTipoRecupero(String descBreveTipoRecupero) {
		this.descBreveTipoRecupero = descBreveTipoRecupero;
	}
	public String getDescTipoRecupero() {
		return descTipoRecupero;
	}
	public void setDescTipoRecupero(String descTipoRecupero) {
		this.descTipoRecupero = descTipoRecupero;
	}
	public String getDescBreveTipoAnagrafica() {
		return descBreveTipoAnagrafica;
	}
	public void setDescBreveTipoAnagrafica(String descBreveTipoAnagrafica) {
		this.descBreveTipoAnagrafica = descBreveTipoAnagrafica;
	}
	public String getDescTipoAnagrafica() {
		return descTipoAnagrafica;
	}
	public void setDescTipoAnagrafica(String descTipoAnagrafica) {
		this.descTipoAnagrafica = descTipoAnagrafica;
	}
	
	

}
