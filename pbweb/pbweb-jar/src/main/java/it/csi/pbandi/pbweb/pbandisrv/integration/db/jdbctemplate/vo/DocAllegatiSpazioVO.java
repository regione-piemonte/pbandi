/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo;

import java.math.BigDecimal;

public class DocAllegatiSpazioVO extends GenericVO{
	
	private BigDecimal idProgetto;
	
	private BigDecimal idDichiarazioneSpesa;
	
	private BigDecimal numeroDocumentiSpesa;
	
	private BigDecimal sizeDocumentiSpesa;
	
	private BigDecimal numeroDocumentiPagamenti;
	
	private BigDecimal sizeDocumentiPagamenti;
	
	private BigDecimal numeroDocumentiDichSpesa;
	
	private BigDecimal sizeDocumentiDichSpesa;
	
	private BigDecimal numeroDocumentiIntegrazioni;
	
	private BigDecimal sizeDocumentiIntegrazioni;

	public BigDecimal getIdProgetto() {
		return idProgetto;
	}

	public void setIdProgetto(BigDecimal idProgetto) {
		this.idProgetto = idProgetto;
	}

	public BigDecimal getIdDichiarazioneSpesa() {
		return idDichiarazioneSpesa;
	}

	public void setIdDichiarazioneSpesa(BigDecimal idDichiarazioneSpesa) {
		this.idDichiarazioneSpesa = idDichiarazioneSpesa;
	}

	public BigDecimal getNumeroDocumentiSpesa() {
		return numeroDocumentiSpesa;
	}

	public void setNumeroDocumentiSpesa(BigDecimal numeroDocumentiSpesa) {
		this.numeroDocumentiSpesa = numeroDocumentiSpesa;
	}

	public BigDecimal getSizeDocumentiSpesa() {
		return sizeDocumentiSpesa;
	}

	public void setSizeDocumentiSpesa(BigDecimal sizeDocumentiSpesa) {
		this.sizeDocumentiSpesa = sizeDocumentiSpesa;
	}

	public BigDecimal getNumeroDocumentiPagamenti() {
		return numeroDocumentiPagamenti;
	}

	public void setNumeroDocumentiPagamenti(BigDecimal numeroDocumentiPagamenti) {
		this.numeroDocumentiPagamenti = numeroDocumentiPagamenti;
	}

	public BigDecimal getSizeDocumentiPagamenti() {
		return sizeDocumentiPagamenti;
	}

	public void setSizeDocumentiPagamenti(BigDecimal sizeDocumentiPagamenti) {
		this.sizeDocumentiPagamenti = sizeDocumentiPagamenti;
	}
	
	public BigDecimal getNumeroDocumentiIntegrazioni() {
		return numeroDocumentiIntegrazioni;
	}

	public void setNumeroDocumentiIntegrazioni(
			BigDecimal numeroDocumentiIntegrazioni) {
		this.numeroDocumentiIntegrazioni = numeroDocumentiIntegrazioni;
	}

	public BigDecimal getSizeDocumentiIntegrazioni() {
		return sizeDocumentiIntegrazioni;
	}

	public void setSizeDocumentiIntegrazioni(BigDecimal sizeDocumentiIntegrazioni) {
		this.sizeDocumentiIntegrazioni = sizeDocumentiIntegrazioni;
	}

	public BigDecimal getNumeroDocumentiDichSpesa() {
		return numeroDocumentiDichSpesa;
	}

	public void setNumeroDocumentiDichSpesa(BigDecimal numeroDocumentiDichSpesa) {
		this.numeroDocumentiDichSpesa = numeroDocumentiDichSpesa;
	}

	public BigDecimal getSizeDocumentiDichSpesa() {
		return sizeDocumentiDichSpesa;
	}

	public void setSizeDocumentiDichSpesa(BigDecimal sizeDocumentiDichSpesa) {
		this.sizeDocumentiDichSpesa = sizeDocumentiDichSpesa;
	}
}
