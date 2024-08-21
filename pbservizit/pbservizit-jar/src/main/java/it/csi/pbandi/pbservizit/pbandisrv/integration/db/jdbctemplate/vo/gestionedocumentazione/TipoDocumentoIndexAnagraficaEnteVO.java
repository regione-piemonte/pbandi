/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.gestionedocumentazione;

import java.math.BigDecimal;

import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.GenericVO;

public class TipoDocumentoIndexAnagraficaEnteVO extends GenericVO {
	
	
	private BigDecimal idTipoDocumentoIndex;
	private BigDecimal idTipoAnagrafica;
	private String descBreveTipoDocIndex;
	private String descTipoDocIndex;
	private String descBreveTipoAnagrafica;
	private BigDecimal idEnteCompetenza;
	private BigDecimal idSoggetto;
	private BigDecimal progrBandoLineaIntervento;
	
	public BigDecimal getIdTipoDocumentoIndex() {
		return idTipoDocumentoIndex;
	}
	public void setIdTipoDocumentoIndex(BigDecimal idTipoDocumentoIndex) {
		this.idTipoDocumentoIndex = idTipoDocumentoIndex;
	}
	public BigDecimal getIdTipoAnagrafica() {
		return idTipoAnagrafica;
	}
	public void setIdTipoAnagrafica(BigDecimal idTipoAnagrafica) {
		this.idTipoAnagrafica = idTipoAnagrafica;
	}
	public String getDescBreveTipoDocIndex() {
		return descBreveTipoDocIndex;
	}
	public void setDescBreveTipoDocIndex(String descBreveTipoDocIndex) {
		this.descBreveTipoDocIndex = descBreveTipoDocIndex;
	}
	public String getDescTipoDocIndex() {
		return descTipoDocIndex;
	}
	public void setDescTipoDocIndex(String descTipoDocIndex) {
		this.descTipoDocIndex = descTipoDocIndex;
	}
	public String getDescBreveTipoAnagrafica() {
		return descBreveTipoAnagrafica;
	}
	public void setDescBreveTipoAnagrafica(String descBreveTipoAnagrafica) {
		this.descBreveTipoAnagrafica = descBreveTipoAnagrafica;
	}
	public BigDecimal getIdEnteCompetenza() {
		return idEnteCompetenza;
	}
	public void setIdEnteCompetenza(BigDecimal idEnteCompetenza) {
		this.idEnteCompetenza = idEnteCompetenza;
	}
	public BigDecimal getIdSoggetto() {
		return idSoggetto;
	}
	public void setIdSoggetto(BigDecimal idSoggetto) {
		this.idSoggetto = idSoggetto;
	}
	public void setProgrBandoLineaIntervento(BigDecimal progrBandoLineaIntervento) {
		this.progrBandoLineaIntervento = progrBandoLineaIntervento;
	}
	public BigDecimal getProgrBandoLineaIntervento() {
		return progrBandoLineaIntervento;
	}

}
