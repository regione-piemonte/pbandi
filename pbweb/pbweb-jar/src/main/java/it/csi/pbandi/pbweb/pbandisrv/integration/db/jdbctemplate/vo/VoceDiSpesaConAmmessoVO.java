/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo;

import java.math.BigDecimal;
import java.sql.Date;

public class VoceDiSpesaConAmmessoVO extends GenericVO {
	
	
	private BigDecimal idRigoContoEconomico; 
	private BigDecimal ultimaSpesaAmmessa;
	private BigDecimal idVoceDiSpesa;
	private BigDecimal idProgetto;
	private BigDecimal idVoceDiSpesaPadre;
	private String descVoceDiSpesa;
	private Date dtInizioValidita;
	/**
	 * @return the idVoceDiSpesaPadre
	 */
	public BigDecimal getIdVoceDiSpesaPadre() {
		return idVoceDiSpesaPadre;
	}
	/**
	 * @param idVoceDiSpesaPadre the idVoceDiSpesaPadre to set
	 */
	public void setIdVoceDiSpesaPadre(BigDecimal idVoceDiSpesaPadre) {
		this.idVoceDiSpesaPadre = idVoceDiSpesaPadre;
	}

	/**
	 * @return the idVoceDiSpesa
	 */
	public BigDecimal getIdVoceDiSpesa() {
		return idVoceDiSpesa;
	}
	/**
	 * @param idVoceDiSpesa the idVoceDiSpesa to set
	 */
	public void setIdVoceDiSpesa(BigDecimal idVoceDiSpesa) {
		this.idVoceDiSpesa = idVoceDiSpesa;
	}
	/**
	 * @return the descVoceDiSpesa
	 */
	public String getDescVoceDiSpesa() {
		return descVoceDiSpesa;
	}
	/**
	 * @param descVoceDiSpesa the descVoceDiSpesa to set
	 */
	public void setDescVoceDiSpesa(String descVoceDiSpesa) {
		this.descVoceDiSpesa = descVoceDiSpesa;
	}

	public void setUltimaSpesaAmmessa(BigDecimal ultimaSpesaAmmessa) {
		this.ultimaSpesaAmmessa = ultimaSpesaAmmessa;
	}
	public BigDecimal getUltimaSpesaAmmessa() {
		return ultimaSpesaAmmessa;
	}
	public void setIdRigoContoEconomico(BigDecimal idRigoContoEconomico) {
		this.idRigoContoEconomico = idRigoContoEconomico;
	}
	public BigDecimal getIdRigoContoEconomico() {
		return idRigoContoEconomico;
	}
	public void setIdProgetto(BigDecimal idProgetto) {
		this.idProgetto = idProgetto;
	}
	public BigDecimal getIdProgetto() {
		return idProgetto;
	}
	public void setDtInizioValidita(Date dtInizioValidita) {
		this.dtInizioValidita = dtInizioValidita;
	}
	public Date getDtInizioValidita() {
		return dtInizioValidita;
	}
	
}
