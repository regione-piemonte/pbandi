/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.gestionepagamenti;

import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.GenericVO;

public class PDocumentoDiSpesaVO extends GenericVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -941659350892396640L;
	
	private java.sql.Date dataEmissione = null;
	private Double sommaPagamenti = null;
	private Double importoTotaleNetto = null;
	private Long idTipoDocDiSpesa= null;
	
	
	public java.sql.Date getDataEmissione() {
		return dataEmissione;
	}
	public void setDataEmissione(java.sql.Date dataEmissione) {
		this.dataEmissione = dataEmissione;
	}
	public Double getSommaPagamenti() {
		return sommaPagamenti;
	}
	public void setSommaPagamenti(Double sommaPagamenti) {
		this.sommaPagamenti = sommaPagamenti;
	}
	public Double getImportoTotaleNetto() {
		return importoTotaleNetto;
	}
	public void setImportoTotaleNetto(Double importoTotaleNetto) {
		this.importoTotaleNetto = importoTotaleNetto;
	}
	public void setIdTipoDocDiSpesa(Long idTipoDocDiSpesa) {
		this.idTipoDocDiSpesa = idTipoDocDiSpesa;
	}
	public Long getIdTipoDocDiSpesa() {
		return idTipoDocDiSpesa;
	}
	
}
