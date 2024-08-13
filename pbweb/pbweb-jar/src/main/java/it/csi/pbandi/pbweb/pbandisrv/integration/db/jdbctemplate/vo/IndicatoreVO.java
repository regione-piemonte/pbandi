/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo;


import java.math.BigDecimal;

public class IndicatoreVO extends GenericVO {
	
	String cod_igrue;
	String desc_tipo_indicatore;
	String desc_indicatore;
	String desc_unita_misura;
	String flag_monit;
	String flag_obbligatorio;
	BigDecimal id_tipo_indicatore;
    BigDecimal id_indicatori;
    BigDecimal id_bando;
    /**
	 * @return the id_tipo_indicatore
	 */
	public BigDecimal getId_tipo_indicatore() {
		return id_tipo_indicatore;
	}
	/**
	 * @param idTipoIndicatore the id_tipo_indicatore to set
	 */
	public void setId_tipo_indicatore(BigDecimal idTipoIndicatore) {
		id_tipo_indicatore = idTipoIndicatore;
	}
	/**
	 * @return the desc_tipo_indicatore
	 */
	public String getDesc_tipo_indicatore() {
		return desc_tipo_indicatore;
	}
	/**
	 * @param descTipoIndicatore the desc_tipo_indicatore to set
	 */
	public void setDesc_tipo_indicatore(String descTipoIndicatore) {
		desc_tipo_indicatore = descTipoIndicatore;
	}
	/**
	 * @return the id_indicatori
	 */
	public BigDecimal getId_indicatori() {
		return id_indicatori;
	}
	/**
	 * @param idIndicatori the id_indicatori to set
	 */
	public void setId_indicatori(BigDecimal idIndicatori) {
		id_indicatori = idIndicatori;
	}
	/**
	 * @return the desc_indicatore
	 */
	public String getDesc_indicatore() {
		return desc_indicatore;
	}
	/**
	 * @param descIndicatore the desc_indicatore to set
	 */
	public void setDesc_indicatore(String descIndicatore) {
		desc_indicatore = descIndicatore;
	}
	/**
	 * @return the cod_igrue
	 */
	public String getCod_igrue() {
		return cod_igrue;
	}
	/**
	 * @param codIgrue the cod_igrue to set
	 */
	public void setCod_igrue(String codIgrue) {
		cod_igrue = codIgrue;
	}

	/**
	 * @return the flag_monit
	 */
	public String getFlag_monit() {
		return flag_monit;
	}
	/**
	 * @param flagMonit the flag_monit to set
	 */
	public void setFlag_monit(String flagMonit) {
		flag_monit = flagMonit;
	}


	/**
	 * @return the id_bando
	 */
	public BigDecimal getId_bando() {
		return id_bando;
	}
	/**
	 * @param idBando the id_bando to set
	 */
	public void setId_bando(BigDecimal idBando) {
		id_bando = idBando;
	}

	public void setDesc_unita_misura(String desc_unita_misura) {
		this.desc_unita_misura = desc_unita_misura;
	}
	public String getDesc_unita_misura() {
		return desc_unita_misura;
	}
	public void setFlag_obbligatorio(String flag_obbligatorio) {
		this.flag_obbligatorio = flag_obbligatorio;
	}
	public String getFlag_obbligatorio() {
		return flag_obbligatorio;
	}
	
	
}
