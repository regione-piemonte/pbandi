/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo;

import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiRProgettoFaseMonitVO;

public class ProgettoFaseMonitVO extends PbandiRProgettoFaseMonitVO{

	private Long idIter; 
	private String codIgrueT35;
	private String estremiAttoAmministrativo;
	
	public Long getIdIter() {
		return idIter;
	}
	public void setIdIter(Long idIter) {
		this.idIter = idIter;
	}
	public String getCodIgrueT35() {
		return codIgrueT35;
	}
	public void setCodIgrueT35(String codIgrueT35) {
		this.codIgrueT35 = codIgrueT35;
	}
	public String getEstremiAttoAmministrativo() {
		return estremiAttoAmministrativo;
	}
	public void setEstremiAttoAmministrativo(String estremiAttoAmministrativo) {
		this.estremiAttoAmministrativo = estremiAttoAmministrativo;
	}
	
}
