/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo;


import java.math.BigDecimal;

public class IndicatoreDomandaVO extends GenericVO {

    BigDecimal id_domanda;
    BigDecimal valore_prog_iniziale;
    BigDecimal valore_concluso;
    BigDecimal valore_prog_agg;
	String cod_igrue;
	String desc_tipo_indicatore;
	String desc_indicatore;
	String desc_unita_misura;
	String flag_monit;
	String flag_obbligatorio;
	String flag_non_applicabile;
	BigDecimal id_tipo_indicatore;
    BigDecimal id_indicatori;
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
	 * @return the desc_unita_misura
	 */
	public String getDesc_unita_misura() {
		return desc_unita_misura;
	}
	/**
	 * @param descUnitaMisura the desc_unita_misura to set
	 */
	public void setDesc_unita_misura(String descUnitaMisura) {
		desc_unita_misura = descUnitaMisura;
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
	 * @return the flag_obbligatorio
	 */
	public String getFlag_obbligatorio() {
		return flag_obbligatorio;
	}
	/**
	 * @param flagObbligatorio the flag_obbligatorio to set
	 */
	public void setFlag_obbligatorio(String flagObbligatorio) {
		flag_obbligatorio = flagObbligatorio;
	}
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
	 * @return the valore_prog_iniziale
	 */
	public BigDecimal getValore_prog_iniziale() {
		return valore_prog_iniziale;
	}
	/**
	 * @param valoreProgIniziale the valore_prog_iniziale to set
	 */
	public void setValore_prog_iniziale(BigDecimal valoreProgIniziale) {
		valore_prog_iniziale = valoreProgIniziale;
	}
	/**
	 * @return the valore_concluso
	 */
	public BigDecimal getValore_concluso() {
		return valore_concluso;
	}
	/**
	 * @param valoreConcluso the valore_concluso to set
	 */
	public void setValore_concluso(BigDecimal valoreConcluso) {
		valore_concluso = valoreConcluso;
	}
	/**
	 * @return the valore_prog_agg
	 */
	public BigDecimal getValore_prog_agg() {
		return valore_prog_agg;
	}
	/**
	 * @param valoreProgAgg the valore_prog_agg to set
	 */
	public void setValore_prog_agg(BigDecimal valoreProgAgg) {
		valore_prog_agg = valoreProgAgg;
	}
	
	/**
	 * @return the id_domanda
	 */
	public BigDecimal getId_domanda() {
		return id_domanda;
	}
	/**
	 * @param idDomanda the id_domanda to set
	 */
	public void setId_domanda(BigDecimal idDomanda) {
		id_domanda = idDomanda;
	}
	public void setFlag_non_applicabile(String flag_non_applicabile) {
		this.flag_non_applicabile = flag_non_applicabile;
	}
	public String getFlag_non_applicabile() {
		return flag_non_applicabile;
	}

	
	
	
}
