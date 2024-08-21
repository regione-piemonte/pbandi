/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo;

import java.math.BigDecimal;

import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiTDocumentoDiSpesaVO;

public class DocumentoDiSpesaDichiarazioneTotalePagamentiVO extends PbandiTDocumentoDiSpesaVO {
	private BigDecimal idDichiarazioneSpesa;
	private BigDecimal totalePagamenti;
	private String task;
	private BigDecimal importoRendicontazione;
	private String descTipoDocumentoSpesa;
	private String codiceFiscaleFornitore;
	
	public BigDecimal getIdDichiarazioneSpesa() {
		return idDichiarazioneSpesa;
	}
	public void setIdDichiarazioneSpesa(BigDecimal idDichiarazioneSpesa) {
		this.idDichiarazioneSpesa = idDichiarazioneSpesa;
	}
	public BigDecimal getTotalePagamenti() {
		return totalePagamenti;
	}
	public void setTotalePagamenti(BigDecimal totalePagamenti) {
		this.totalePagamenti = totalePagamenti;
	}
	public String getTask() {
		return task;
	}
	public void setTask(String task) {
		this.task = task;
	}
	public BigDecimal getImportoRendicontazione() {
		return importoRendicontazione;
	}
	public void setImportoRendicontazione(BigDecimal importoRendicontazione) {
		this.importoRendicontazione = importoRendicontazione;
	}
	public void setDescTipoDocumentoSpesa(String descTipoDocumentoSpesa) {
		this.descTipoDocumentoSpesa = descTipoDocumentoSpesa;
	}
	public String getDescTipoDocumentoSpesa() {
		return descTipoDocumentoSpesa;
	}
	public void setCodiceFiscaleFornitore(String codiceFiscaleFornitore) {
		this.codiceFiscaleFornitore = codiceFiscaleFornitore;
	}
	public String getCodiceFiscaleFornitore() {
		return codiceFiscaleFornitore;
	}

}
