/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.rettifica;

import java.math.BigDecimal;
import java.sql.Date;

import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.GenericVO;

public class DocumentoDiSpesaRettificaVO extends GenericVO {
	
	private String destinazioneTrasferta;
  	
  	private BigDecimal idStatoDocumentoSpesa;
  	
  	private BigDecimal idTipoOggettoAttivita;
  	
  	private BigDecimal progrFornitoreQualifica;
  	
  	private BigDecimal imponibile;
  	
  	private BigDecimal importoTotaleDocumento;
  	
  	private BigDecimal idUtenteIns;
  	
  	private BigDecimal idFornitore;
  	
  	private Date dtEmissioneDocumento;
  	
  	private BigDecimal idDocRiferimento;
  	
  	private String numeroDocumento;
  	
  	private BigDecimal idTipoDocumentoSpesa;
  	
  	private BigDecimal idSoggetto;
  	
  	private BigDecimal importoIvaCosto;
  	
  	private BigDecimal durataTrasferta;
  	
  	private BigDecimal idUtenteAgg;
  	
  	private String descDocumento;
  	
  	private BigDecimal importoIva;
  	
  	private BigDecimal idDocumentoDiSpesa;
  	
  	private BigDecimal totaleImportoNote;
  	
  	private BigDecimal idProgetto;
  	
  	private BigDecimal importoRendicontazione;
  	
  	private BigDecimal totaleRendicontabileNote;	
  	
  	private BigDecimal dichAperteProgetto;
  	
	private BigDecimal dichAperteTotale;

	public String getDestinazioneTrasferta() {
		return destinazioneTrasferta;
	}

	public void setDestinazioneTrasferta(String destinazioneTrasferta) {
		this.destinazioneTrasferta = destinazioneTrasferta;
	}

	public BigDecimal getIdStatoDocumentoSpesa() {
		return idStatoDocumentoSpesa;
	}

	public void setIdStatoDocumentoSpesa(BigDecimal idStatoDocumentoSpesa) {
		this.idStatoDocumentoSpesa = idStatoDocumentoSpesa;
	}

	public BigDecimal getIdTipoOggettoAttivita() {
		return idTipoOggettoAttivita;
	}

	public void setIdTipoOggettoAttivita(BigDecimal idTipoOggettoAttivita) {
		this.idTipoOggettoAttivita = idTipoOggettoAttivita;
	}

	public BigDecimal getProgrFornitoreQualifica() {
		return progrFornitoreQualifica;
	}

	public void setProgrFornitoreQualifica(BigDecimal progrFornitoreQualifica) {
		this.progrFornitoreQualifica = progrFornitoreQualifica;
	}

	public BigDecimal getImponibile() {
		return imponibile;
	}

	public void setImponibile(BigDecimal imponibile) {
		this.imponibile = imponibile;
	}

	public BigDecimal getImportoTotaleDocumento() {
		return importoTotaleDocumento;
	}

	public void setImportoTotaleDocumento(BigDecimal importoTotaleDocumento) {
		this.importoTotaleDocumento = importoTotaleDocumento;
	}

	public BigDecimal getIdUtenteIns() {
		return idUtenteIns;
	}

	public void setIdUtenteIns(BigDecimal idUtenteIns) {
		this.idUtenteIns = idUtenteIns;
	}

	public BigDecimal getIdFornitore() {
		return idFornitore;
	}

	public void setIdFornitore(BigDecimal idFornitore) {
		this.idFornitore = idFornitore;
	}

	public Date getDtEmissioneDocumento() {
		return dtEmissioneDocumento;
	}

	public void setDtEmissioneDocumento(Date dtEmissioneDocumento) {
		this.dtEmissioneDocumento = dtEmissioneDocumento;
	}

	public BigDecimal getIdDocRiferimento() {
		return idDocRiferimento;
	}

	public void setIdDocRiferimento(BigDecimal idDocRiferimento) {
		this.idDocRiferimento = idDocRiferimento;
	}

	public String getNumeroDocumento() {
		return numeroDocumento;
	}

	public void setNumeroDocumento(String numeroDocumento) {
		this.numeroDocumento = numeroDocumento;
	}

	public BigDecimal getIdTipoDocumentoSpesa() {
		return idTipoDocumentoSpesa;
	}

	public void setIdTipoDocumentoSpesa(BigDecimal idTipoDocumentoSpesa) {
		this.idTipoDocumentoSpesa = idTipoDocumentoSpesa;
	}

	public BigDecimal getIdSoggetto() {
		return idSoggetto;
	}

	public void setIdSoggetto(BigDecimal idSoggetto) {
		this.idSoggetto = idSoggetto;
	}

	public BigDecimal getImportoIvaCosto() {
		return importoIvaCosto;
	}

	public void setImportoIvaCosto(BigDecimal importoIvaCosto) {
		this.importoIvaCosto = importoIvaCosto;
	}

	public BigDecimal getDurataTrasferta() {
		return durataTrasferta;
	}

	public void setDurataTrasferta(BigDecimal durataTrasferta) {
		this.durataTrasferta = durataTrasferta;
	}

	public BigDecimal getIdUtenteAgg() {
		return idUtenteAgg;
	}

	public void setIdUtenteAgg(BigDecimal idUtenteAgg) {
		this.idUtenteAgg = idUtenteAgg;
	}

	public String getDescDocumento() {
		return descDocumento;
	}

	public void setDescDocumento(String descDocumento) {
		this.descDocumento = descDocumento;
	}

	public BigDecimal getImportoIva() {
		return importoIva;
	}

	public void setImportoIva(BigDecimal importoIva) {
		this.importoIva = importoIva;
	}

	public BigDecimal getIdDocumentoDiSpesa() {
		return idDocumentoDiSpesa;
	}

	public void setIdDocumentoDiSpesa(BigDecimal idDocumentoDiSpesa) {
		this.idDocumentoDiSpesa = idDocumentoDiSpesa;
	}

	public BigDecimal getTotaleImportoNote() {
		return totaleImportoNote;
	}

	public void setTotaleImportoNote(BigDecimal totaleImportoNote) {
		this.totaleImportoNote = totaleImportoNote;
	}

	public BigDecimal getIdProgetto() {
		return idProgetto;
	}

	public void setIdProgetto(BigDecimal idProgetto) {
		this.idProgetto = idProgetto;
	}

	public void setImportoRendicontazione(BigDecimal importoRendicontazione) {
		this.importoRendicontazione = importoRendicontazione;
	}

	public BigDecimal getImportoRendicontazione() {
		return importoRendicontazione;
	}

	public void setTotaleRendicontabileNote(BigDecimal totaleRendicontabileNote) {
		this.totaleRendicontabileNote = totaleRendicontabileNote;
	}

	public BigDecimal getTotaleRendicontabileNote() {
		return totaleRendicontabileNote;
	}

	public void setDichAperteProgetto(BigDecimal dichAperteProgetto) {
		this.dichAperteProgetto = dichAperteProgetto;
	}

	public BigDecimal getDichAperteProgetto() {
		return dichAperteProgetto;
	}

	public void setDichAperteTotale(BigDecimal dichAperteTotale) {
		this.dichAperteTotale = dichAperteTotale;
	}

	public BigDecimal getDichAperteTotale() {
		return dichAperteTotale;
	}

}
