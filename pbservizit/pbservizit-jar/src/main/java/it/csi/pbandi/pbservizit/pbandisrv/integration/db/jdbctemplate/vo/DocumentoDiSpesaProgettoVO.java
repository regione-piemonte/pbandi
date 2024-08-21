/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo;

import java.math.BigDecimal;
import java.sql.Date;

public class DocumentoDiSpesaProgettoVO extends GenericVO {
	
	
	private BigDecimal idProgetto;
	private BigDecimal idDocumentoDiSpesa;
	private String noteValidazione;
	private BigDecimal idDocRiferimento;
	private BigDecimal idTipoDocumentoSpesa;
	private BigDecimal idStatoDocumentoSpesa;
	private BigDecimal idFornitore;
	private BigDecimal idTipoFornitore;
	private String descTipoDocumentoSpesa;
	private String descBreveTipoDocSpesa;
	private String descDocumento;
	private String numeroDocumento;
	private Date dtEmissioneDocumento;
	private String task;
	private String denominazioneFornitore;
	private String nomeFornitore;
	private String cognomeFornitore;
	private String codiceFiscaleFornitore;
	private String partitaIvaFornitore;
	private BigDecimal importoTotaleDocumento;
	private String descStatoDocumentoSpesa;
	private String descBreveStatoDocSpesa;
	private BigDecimal importoRendicontazione;
	private BigDecimal importoTotaleRendicontato;
	private BigDecimal progrFornitoreQualifica;
	private BigDecimal imponibile;
	private BigDecimal importoIva;
	private BigDecimal importoIvaCosto;
	private BigDecimal importoTotaleQuietanzato;
	private BigDecimal idSoggetto;
	private BigDecimal idTipoOggettoAttivita;
	private BigDecimal rendicontabileQuietanzato;
	private String destinazioneTrasferta;
	private BigDecimal durataTrasferta;
	private String tipoInvio;
	private String flagElettronico;
	private BigDecimal idAppalto;
	private String descrizioneAppalto;
	private String flagElettXml;
	private BigDecimal idParametroCompenso;
	private BigDecimal ggLavorabiliMese;
	private BigDecimal sospBrevi;
	private BigDecimal sospLungheGgTot;
	private BigDecimal sospLungheGgLav;
	private BigDecimal oreMeseLavorate;
	private BigDecimal mese;
	private BigDecimal anno;

	public String getDescrizioneAppalto() {
		return descrizioneAppalto;
	}
	public void setDescrizioneAppalto(String descrizioneAppalto) {
		this.descrizioneAppalto = descrizioneAppalto;
	}
	public BigDecimal getIdAppalto() {
		return idAppalto;
	}
	public void setIdAppalto(BigDecimal idAppalto) {
		this.idAppalto = idAppalto;
	}
	public BigDecimal getIdProgetto() {
		return idProgetto;
	}
	public void setIdProgetto(BigDecimal idProgetto) {
		this.idProgetto = idProgetto;
	}
	public BigDecimal getIdDocumentoDiSpesa() {
		return idDocumentoDiSpesa;
	}
	public void setIdDocumentoDiSpesa(BigDecimal idDocumentoDiSpesa) {
		this.idDocumentoDiSpesa = idDocumentoDiSpesa;
	}
	public BigDecimal getIdDocRiferimento() {
		return idDocRiferimento;
	}
	public void setIdDocRiferimento(BigDecimal idDocRiferimento) {
		this.idDocRiferimento = idDocRiferimento;
	}
	public BigDecimal getIdTipoDocumentoSpesa() {
		return idTipoDocumentoSpesa;
	}
	public void setIdTipoDocumentoSpesa(BigDecimal idTipoDocumentoSpesa) {
		this.idTipoDocumentoSpesa = idTipoDocumentoSpesa;
	}
	public BigDecimal getIdStatoDocumentoSpesa() {
		return idStatoDocumentoSpesa;
	}
	public void setIdStatoDocumentoSpesa(BigDecimal idStatoDocumentoSpesa) {
		this.idStatoDocumentoSpesa = idStatoDocumentoSpesa;
	}
	public BigDecimal getIdFornitore() {
		return idFornitore;
	}
	public void setIdFornitore(BigDecimal idFornitore) {
		this.idFornitore = idFornitore;
	}
	public BigDecimal getIdTipoFornitore() {
		return idTipoFornitore;
	}
	public void setIdTipoFornitore(BigDecimal idTipoFornitore) {
		this.idTipoFornitore = idTipoFornitore;
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
	public String getNumeroDocumento() {
		return numeroDocumento;
	}
	public void setNumeroDocumento(String numeroDocumento) {
		this.numeroDocumento = numeroDocumento;
	}
	public Date getDtEmissioneDocumento() {
		return dtEmissioneDocumento;
	}
	public void setDtEmissioneDocumento(Date dtEmissioneDocumento) {
		this.dtEmissioneDocumento = dtEmissioneDocumento;
	}
	public String getTask() {
		return task;
	}
	public void setTask(String task) {
		this.task = task;
	}
	public String getDenominazioneFornitore() {
		return denominazioneFornitore;
	}
	public void setDenominazioneFornitore(String denominazioneFornitore) {
		this.denominazioneFornitore = denominazioneFornitore;
	}
	public String getNomeFornitore() {
		return nomeFornitore;
	}
	public void setNomeFornitore(String nomeFornitore) {
		this.nomeFornitore = nomeFornitore;
	}
	public String getCognomeFornitore() {
		return cognomeFornitore;
	}
	public void setCognomeFornitore(String cognomeFornitore) {
		this.cognomeFornitore = cognomeFornitore;
	}
	public String getCodiceFiscaleFornitore() {
		return codiceFiscaleFornitore;
	}
	public void setCodiceFiscaleFornitore(String codiceFiscaleFornitore) {
		this.codiceFiscaleFornitore = codiceFiscaleFornitore;
	}
	public String getPartitaIvaFornitore() {
		return partitaIvaFornitore;
	}
	public void setPartitaIvaFornitore(String partitaIvaFornitore) {
		this.partitaIvaFornitore = partitaIvaFornitore;
	}
	public BigDecimal getImportoTotaleDocumento() {
		return importoTotaleDocumento;
	}
	public void setImportoTotaleDocumento(BigDecimal importoTotaleDocumento) {
		this.importoTotaleDocumento = importoTotaleDocumento;
	}
	public String getDescStatoDocumentoSpesa() {
		return descStatoDocumentoSpesa;
	}
	public void setDescStatoDocumentoSpesa(String descStatoDocumentoSpesa) {
		this.descStatoDocumentoSpesa = descStatoDocumentoSpesa;
	}
	public String getDescBreveStatoDocSpesa() {
		return descBreveStatoDocSpesa;
	}
	public void setDescBreveStatoDocSpesa(String descBreveStatoDocSpesa) {
		this.descBreveStatoDocSpesa = descBreveStatoDocSpesa;
	}
	public BigDecimal getImportoRendicontazione() {
		return importoRendicontazione;
	}
	public void setImportoRendicontazione(BigDecimal importoRendicontazione) {
		this.importoRendicontazione = importoRendicontazione;
	}
	public BigDecimal getImportoTotaleRendicontato() {
		return importoTotaleRendicontato;
	}
	public void setImportoTotaleRendicontato(BigDecimal importoTotaleRendicontato) {
		this.importoTotaleRendicontato = importoTotaleRendicontato;
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
	public BigDecimal getImportoIva() {
		return importoIva;
	}
	public void setImportoIva(BigDecimal importoIva) {
		this.importoIva = importoIva;
	}
	public BigDecimal getImportoIvaCosto() {
		return importoIvaCosto;
	}
	public void setImportoIvaCosto(BigDecimal importoIvaCosto) {
		this.importoIvaCosto = importoIvaCosto;
	}
	public BigDecimal getImportoTotaleQuietanzato() {
		return importoTotaleQuietanzato;
	}
	public void setImportoTotaleQuietanzato(BigDecimal importoTotaleQuietanzato) {
		this.importoTotaleQuietanzato = importoTotaleQuietanzato;
	}
	public BigDecimal getIdSoggetto() {
		return idSoggetto;
	}
	public void setIdSoggetto(BigDecimal idSoggetto) {
		this.idSoggetto = idSoggetto;
	}
	public BigDecimal getIdTipoOggettoAttivita() {
		return idTipoOggettoAttivita;
	}
	public void setIdTipoOggettoAttivita(BigDecimal idTipoOggettoAttivita) {
		this.idTipoOggettoAttivita = idTipoOggettoAttivita;
	}
	public String getDescDocumento() {
		return descDocumento;
	}
	public void setDescDocumento(String descDocumento) {
		this.descDocumento = descDocumento;
	}
	public BigDecimal getRendicontabileQuietanzato() {
		return rendicontabileQuietanzato;
	}
	public void setRendicontabileQuietanzato(BigDecimal rendicontabileQuietanzato) {
		this.rendicontabileQuietanzato = rendicontabileQuietanzato;
	}
	public String getNoteValidazione() {
		return noteValidazione;
	}
	public void setNoteValidazione(String noteValidazione) {
		this.noteValidazione = noteValidazione;
	}
	public String getDestinazioneTrasferta() {
		return destinazioneTrasferta;
	}
	public void setDestinazioneTrasferta(String destinazioneTrasferta) {
		this.destinazioneTrasferta = destinazioneTrasferta;
	}
	public BigDecimal getDurataTrasferta() {
		return durataTrasferta;
	}
	public void setDurataTrasferta(BigDecimal durataTrasferta) {
		this.durataTrasferta = durataTrasferta;
	}
	public String getTipoInvio() {
		return tipoInvio;
	}
	public void setTipoInvio(String tipoInvio) {
		this.tipoInvio = tipoInvio;
	}
	public String getFlagElettronico() {
		return flagElettronico;
	}
	public void setFlagElettronico(String flagElettronico) {
		this.flagElettronico = flagElettronico;
	}
	public String getFlagElettXml() {
		return flagElettXml;
	}
	public void setFlagElettXml(String flagElettXml) {
		this.flagElettXml = flagElettXml;
	}
	public BigDecimal getIdParametroCompenso() {
		return idParametroCompenso;
	}
	public void setIdParametroCompenso(BigDecimal idParametroCompenso) {
		this.idParametroCompenso = idParametroCompenso;
	}
	public BigDecimal getGgLavorabiliMese() {
		return ggLavorabiliMese;
	}
	public void setGgLavorabiliMese(BigDecimal ggLavorabiliMese) {
		this.ggLavorabiliMese = ggLavorabiliMese;
	}
	public BigDecimal getSospBrevi() {
		return sospBrevi;
	}
	public void setSospBrevi(BigDecimal sospBrevi) {
		this.sospBrevi = sospBrevi;
	}
	public BigDecimal getSospLungheGgTot() {
		return sospLungheGgTot;
	}
	public void setSospLungheGgTot(BigDecimal sospLungheGgTot) {
		this.sospLungheGgTot = sospLungheGgTot;
	}
	public BigDecimal getSospLungheGgLav() {
		return sospLungheGgLav;
	}
	public void setSospLungheGgLav(BigDecimal sospLungheGgLav) {
		this.sospLungheGgLav = sospLungheGgLav;
	}
	public BigDecimal getOreMeseLavorate() {
		return oreMeseLavorate;
	}
	public void setOreMeseLavorate(BigDecimal oreMeseLavorate) {
		this.oreMeseLavorate = oreMeseLavorate;
	}
	public BigDecimal getMese() {
		return mese;
	}
	public void setMese(BigDecimal mese) {
		this.mese = mese;
	}
	public BigDecimal getAnno() {
		return anno;
	}
	public void setAnno(BigDecimal anno) {
		this.anno = anno;
	}
}
