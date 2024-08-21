/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo;

import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiTProgettoVO;

public class ProgettoBandoLineaVO extends PbandiTProgettoVO {
	
	private Long idBandoLinea;
	private String versioneProcesso;
	private String descrizioneBando;
	private String titoloBando;
	private String beneficiario;
	
	public void setIdBandoLinea(Long idBandoLinea) {
		this.idBandoLinea = idBandoLinea;
	}

	public Long getIdBandoLinea() {
		return idBandoLinea;
	}

	public void setVersioneProcesso(String versioneProcesso) {
		this.versioneProcesso = versioneProcesso;
	}

	public String getVersioneProcesso() {
		return versioneProcesso;
	}

	public void setDescrizioneBando(String descrizioneBando) {
		this.descrizioneBando = descrizioneBando;
	}

	public String getDescrizioneBando() {
		return descrizioneBando;
	}

	public void setBeneficiario(String beneficiario) {
		this.beneficiario = beneficiario;
	}

	public String getBeneficiario() {
		return beneficiario;
	}

	public void setTitoloBando(String titoloBando) {
		this.titoloBando = titoloBando;
	}

	public String getTitoloBando() {
		return titoloBando;
	}

	

}
