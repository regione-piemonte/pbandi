/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.dto.manager.report;

import java.math.BigDecimal;

import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiTAppaltoVO;;
/**
 *
 */
public class AppaltoDTO implements java.io.Serializable {
	static final long serialVersionUID = 1;
	
	
	private String descTipologiaAppalto;	
	private Double importo;
	private Double iva; 
	private Double totale;
	private String impresaAppaltatrice;
	private String interventoPisu;
	
	
	
	/**
	 * @return the totale
	 */
	public Double getTotale() {
		return totale;
	}

	/**
	 * @param totale the totale to set
	 */
	public void setTotale(Double totale) {
		this.totale = totale;
	}

	/**
	 * @return the impresaAppaltatrice
	 */
	public String getImpresaAppaltatrice() {
		return impresaAppaltatrice;
	}

	/**
	 * @param impresaAppaltatrice the impresaAppaltatrice to set
	 */
	public void setImpresaAppaltatrice(String impresaAppaltatrice) {
		this.impresaAppaltatrice = impresaAppaltatrice;
	}

	/**
	 * @return the interventoPisu
	 */
	public String getInterventoPisu() {
		return interventoPisu;
	}

	/**
	 * @param interventoPisu the interventoPisu to set
	 */
	public void setInterventoPisu(String interventoPisu) {
		this.interventoPisu = interventoPisu;
	}

	public void setDescTipologiaAppalto(String descTipologiaAppalto) {
		this.descTipologiaAppalto = descTipologiaAppalto;
	}

	public String getDescTipologiaAppalto() {
		return descTipologiaAppalto;
	}

	public void setImporto(Double importo) {
		this.importo = importo;
	}

	public Double getImporto() {
		return importo;
	}

	public void setIva(Double iva) {
		this.iva = iva;
	}

	public Double getIva() {
		return iva;
	}



}
