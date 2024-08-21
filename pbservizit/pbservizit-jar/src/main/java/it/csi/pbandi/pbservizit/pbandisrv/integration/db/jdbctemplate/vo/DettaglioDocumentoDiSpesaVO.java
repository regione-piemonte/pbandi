/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo;

import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiTDocumentoDiSpesaVO;

import java.math.BigDecimal;

public class DettaglioDocumentoDiSpesaVO extends PbandiTDocumentoDiSpesaVO {
	private String codiceFiscaleFornitore;
	private BigDecimal idProgetto;
	private String codiceVisualizzato;
	private BigDecimal idTipoSoggetto;
	private String descStatoDocumentoSpesa;
	private String descTipoDocumentoSpesa;
	private String descBreveTipoDocSpesa;
	private BigDecimal importoRendicontazione;
	private String descTipologiaFornitore;
	private String partitaIvaFornitore;
	private String task;
	private String noteValidazione;
	private String tipoInvio;
	private BigDecimal idAppalto;
	
	public BigDecimal getIdAppalto() {
		return idAppalto;
	}
	public void setIdAppalto(BigDecimal idAppalto) {
		this.idAppalto = idAppalto;
	}
	public String getCodiceFiscaleFornitore() {
		return codiceFiscaleFornitore;
	}
	public void setCodiceFiscaleFornitore(String codiceFiscaleFornitore) {
		this.codiceFiscaleFornitore = codiceFiscaleFornitore;
	}
	public BigDecimal getIdProgetto() {
		return idProgetto;
	}
	public void setIdProgetto(BigDecimal idProgetto) {
		this.idProgetto = idProgetto;
	}
	public String getCodiceVisualizzato() {
		return codiceVisualizzato;
	}
	public void setCodiceVisualizzato(String codiceVisualizzato) {
		this.codiceVisualizzato = codiceVisualizzato;
	}
	public BigDecimal getIdTipoSoggetto() {
		return idTipoSoggetto;
	}
	public void setIdTipoSoggetto(BigDecimal idTipoSoggetto) {
		this.idTipoSoggetto = idTipoSoggetto;
	}
	public String getDescStatoDocumentoSpesa() {
		return descStatoDocumentoSpesa;
	}
	public void setDescStatoDocumentoSpesa(String descStatoDocumentoSpesa) {
		this.descStatoDocumentoSpesa = descStatoDocumentoSpesa;
	}
	public String getDescTipoDocumentoSpesa() {
		return descTipoDocumentoSpesa;
	}
	public void setDescTipoDocumentoSpesa(String descTipoDocumentoSpesa) {
		this.descTipoDocumentoSpesa = descTipoDocumentoSpesa;
	}
	public String getDescBreveTipoDocSpesa() {
		return descBreveTipoDocSpesa;
	}
	public void setDescBreveTipoDocSpesa(String descBreveTipoDocSpesa) {
		this.descBreveTipoDocSpesa = descBreveTipoDocSpesa;
	}
	public BigDecimal getImportoRendicontazione() {
		return importoRendicontazione;
	}
	public void setImportoRendicontazione(BigDecimal importoRendicontazione) {
		this.importoRendicontazione = importoRendicontazione;
	}
	public String getDescTipologiaFornitore() {
		return descTipologiaFornitore;
	}
	public void setDescTipologiaFornitore(String descTipologiaFornitore) {
		this.descTipologiaFornitore = descTipologiaFornitore;
	}
	public String getPartitaIvaFornitore() {
		return partitaIvaFornitore;
	}
	public void setPartitaIvaFornitore(String partitaIvaFornitore) {
		this.partitaIvaFornitore = partitaIvaFornitore;
	}
	public String getTask() {
		return task;
	}
	public void setTask(String task) {
		this.task = task;
	}
	public String getNoteValidazione() {
		return noteValidazione;
	}
	public void setNoteValidazione(String noteValidazione) {
		this.noteValidazione = noteValidazione;
	}
	public String getTipoInvio() {
		return tipoInvio;
	}
	public void setTipoInvio(String tipoInvio) {
		this.tipoInvio = tipoInvio;
	}
}
