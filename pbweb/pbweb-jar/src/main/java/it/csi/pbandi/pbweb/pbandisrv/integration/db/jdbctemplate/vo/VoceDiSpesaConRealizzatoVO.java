/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo;

import java.math.BigDecimal;
import java.sql.Date;

public class VoceDiSpesaConRealizzatoVO extends GenericVO {
	
/*
DT_PERIODO,
ID_RIGO_CONTO_ECONOMICO,
REALIZZATO 
ID_VOCE_DI_SPESA
DESC_VOCE_DI_SPESA
*/
	//BigDecimal idRigoContoEconomico; 
	private BigDecimal realizzato;
	private Long idVoceDiSpesa;
	private Long idVoceDiSpesaPadre;
	private Long idProgetto;
	private String descVoceDiSpesa;
	private String descVoceDiSpesaPadre;
	private String periodo;
	/**
	 * @return the idVoceDiSpesaPadre
	 */
	public Long getIdVoceDiSpesaPadre() {
		return idVoceDiSpesaPadre;
	}
	/**
	 * @param idVoceDiSpesaPadre the idVoceDiSpesaPadre to set
	 */
	public void setIdVoceDiSpesaPadre(Long idVoceDiSpesaPadre) {
		this.idVoceDiSpesaPadre = idVoceDiSpesaPadre;
	}
	/**
	 * @return the descVoceDiSpesaPadre
	 */
	public String getDescVoceDiSpesaPadre() {
		return descVoceDiSpesaPadre;
	}
	/**
	 * @param descVoceDiSpesaPadre the descVoceDiSpesaPadre to set
	 */
	public void setDescVoceDiSpesaPadre(String descVoceDiSpesaPadre) {
		this.descVoceDiSpesaPadre = descVoceDiSpesaPadre;
	}
	/**
	 * @return the realizzato
	 */
	public BigDecimal getRealizzato() {
		return realizzato;
	}
	/**
	 * @param realizzato the realizzato to set
	 */
	public void setRealizzato(BigDecimal realizzato) {
		this.realizzato = realizzato;
	}
	/**
	 * @return the idVoceDiSpesa
	 */
	public Long getIdVoceDiSpesa() {
		return idVoceDiSpesa;
	}
	/**
	 * @param idVoceDiSpesa the idVoceDiSpesa to set
	 */
	public void setIdVoceDiSpesa(Long idVoceDiSpesa) {
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

	public void setIdProgetto(Long idProgetto) {
		this.idProgetto = idProgetto;
	}
	public Long getIdProgetto() {
		return idProgetto;
	}
	public void setPeriodo(String periodo) {
		this.periodo = periodo;
	}
	public String getPeriodo() {
		return periodo;
	}
	
}
