/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo;

import java.math.BigDecimal;
import java.util.List;

public class PbandiTCeAltriCostiVO extends GenericVO {

	static final long serialVersionUID = 1;
	
	//PBANDI_T_CE_ALTRI_COSTI
	private Long idTCeAltriCosti;
	private Long idProgetto;
	private Long idDCeAltriCosti;
	private Double impCeApprovato;
	private Double impCdPropmod;
	
	//PBANDI_D_CE_ALTRI_COSTI
	private String descBreveCeAltriCosti;
	private String descCeAltriCosti;
	
	private Double delta;
	
	public String getDescBreveCeAltriCosti() {
		return descBreveCeAltriCosti;
	}
	public void setDescBreveCeAltriCosti(String descBreveCeAltriCosti) {
		this.descBreveCeAltriCosti = descBreveCeAltriCosti;
	}
	public String getDescCeAltriCosti() {
		return descCeAltriCosti;
	}
	public void setDescCeAltriCosti(String descCeAltriCosti) {
		this.descCeAltriCosti = descCeAltriCosti;
	}
	public Long getIdTCeAltriCosti() {
		return idTCeAltriCosti;
	}
	public void setIdTCeAltriCosti(Long idTCeAltriCosti) {
		this.idTCeAltriCosti = idTCeAltriCosti;
	}
	public Long getIdProgetto() {
		return idProgetto;
	}
	public void setIdProgetto(Long idProgetto) {
		this.idProgetto = idProgetto;
	}
	public Long getIdDCeAltriCosti() {
		return idDCeAltriCosti;
	}
	public void setIdDCeAltriCosti(Long idDCeAltriCosti) {
		this.idDCeAltriCosti = idDCeAltriCosti;
	}
	public Double getImpCeApprovato() {
		return impCeApprovato;
	}
	public void setImpCeApprovato(Double impCeApprovato) {
		this.impCeApprovato = impCeApprovato;
	}
	public Double getImpCdPropmod() {
		return impCdPropmod;
	}
	public void setImpCdPropmod(Double impCdPropmod) {
		this.impCdPropmod = impCdPropmod;
	}
	public Double getDelta() {
		return delta;
	}
	public void setDelta(Double delta) {
		this.delta = delta;
	}
	
	
	@Override
	public boolean isPKValid() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public List getPK() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public String toString() {
		return "PbandiTCeAltriCostiVO [idTCeAltriCosti=" + idTCeAltriCosti + ", idProgetto=" + idProgetto
				+ ", idDCeAltriCosti=" + idDCeAltriCosti + ", impCeApprovato=" + impCeApprovato + ", impCdPropmod="
				+ impCdPropmod + ", descBreveCeAltriCosti=" + descBreveCeAltriCosti + ", descCeAltriCosti="
				+ descCeAltriCosti + ", delta=" + delta+ "]";
	}
	
}
