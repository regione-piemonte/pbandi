/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo;

import java.math.BigDecimal;

public class IndicatoreSifVO extends GenericVO {

	String cod_igrue;
	String desc_tipo_indicatore;
	String desc_indicatore;
	String desc_unita_misura;
	String flag_monit;
	String flag_obbligatorio;
	BigDecimal id_tipo_indicatore;
	BigDecimal id_indicatori;
	BigDecimal id_progetto;
	Double valoreIniziale;
	Double valoreFinale;
	Double valoreAggiornamento;

	public String getCod_igrue() {
		return cod_igrue;
	}

	public void setCod_igrue(String cod_igrue) {
		this.cod_igrue = cod_igrue;
	}

	public String getDesc_tipo_indicatore() {
		return desc_tipo_indicatore;
	}

	public void setDesc_tipo_indicatore(String desc_tipo_indicatore) {
		this.desc_tipo_indicatore = desc_tipo_indicatore;
	}

	public String getDesc_indicatore() {
		return desc_indicatore;
	}

	public void setDesc_indicatore(String desc_indicatore) {
		this.desc_indicatore = desc_indicatore;
	}

	public String getDesc_unita_misura() {
		return desc_unita_misura;
	}

	public void setDesc_unita_misura(String desc_unita_misura) {
		this.desc_unita_misura = desc_unita_misura;
	}

	public String getFlag_monit() {
		return flag_monit;
	}

	public void setFlag_monit(String flag_monit) {
		this.flag_monit = flag_monit;
	}

	public String getFlag_obbligatorio() {
		return flag_obbligatorio;
	}

	public void setFlag_obbligatorio(String flag_obbligatorio) {
		this.flag_obbligatorio = flag_obbligatorio;
	}

	public BigDecimal getId_tipo_indicatore() {
		return id_tipo_indicatore;
	}

	public void setId_tipo_indicatore(BigDecimal id_tipo_indicatore) {
		this.id_tipo_indicatore = id_tipo_indicatore;
	}

	public BigDecimal getId_indicatori() {
		return id_indicatori;
	}

	public void setId_indicatori(BigDecimal id_indicatori) {
		this.id_indicatori = id_indicatori;
	}

	public BigDecimal getId_progetto() {
		return id_progetto;
	}

	public void setId_progetto(BigDecimal id_progetto) {
		this.id_progetto = id_progetto;
	}

	public Double getValoreIniziale() {
		return valoreIniziale;
	}

	public void setValoreIniziale(Double valoreIniziale) {
		this.valoreIniziale = valoreIniziale;
	}

	public Double getValoreFinale() {
		return valoreFinale;
	}

	public void setValoreFinale(Double valoreFinale) {
		this.valoreFinale = valoreFinale;
	}

	public Double getValoreAggiornamento() {
		return valoreAggiornamento;
	}

	public void setValoreAggiornamento(Double valoreAggiornamento) {
		this.valoreAggiornamento = valoreAggiornamento;
	}

}
