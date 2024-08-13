/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo;


public class MaxProgrOrdinamentoVoceDiSpesaVO extends GenericVO{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6536184190618679234L;
	private Long idBando;
	private Long maxProgrOrdinamento;

	public Long getIdBando() {
		return idBando;
	}
	public void setIdBando(Long idBando) {
		this.idBando = idBando;
	}
	public Long getMaxProgrOrdinamento() {
		return maxProgrOrdinamento;
	}
	public void setMaxProgrOrdinamento(Long maxProgrOrdinamento) {
		this.maxProgrOrdinamento = maxProgrOrdinamento;
	}
	
	public MaxProgrOrdinamentoVoceDiSpesaVO(Long idBando) {
		setIdBando(idBando);
	}
	
	public MaxProgrOrdinamentoVoceDiSpesaVO() {
	}
}
