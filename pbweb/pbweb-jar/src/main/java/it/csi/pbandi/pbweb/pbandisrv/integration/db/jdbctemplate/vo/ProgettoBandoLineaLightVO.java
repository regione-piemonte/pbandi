/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo;

import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiTProgettoVO;

public class ProgettoBandoLineaLightVO extends PbandiTProgettoVO {
	
	private Long idBandoLinea;
	private String descrizioneBando;
	private String flag_sif;
	public Long getIdBandoLinea() {
		return idBandoLinea;
	}
	public void setIdBandoLinea(Long idBandoLinea) {
		this.idBandoLinea = idBandoLinea;
	}
	public String getDescrizioneBando() {
		return descrizioneBando;
	}
	public void setDescrizioneBando(String descrizioneBando) {
		this.descrizioneBando = descrizioneBando;
	}
	public String getFlag_sif() {
		return flag_sif;
	}
	public void setFlag_sif(String flag_sif) {
		this.flag_sif = flag_sif;
	}

}
