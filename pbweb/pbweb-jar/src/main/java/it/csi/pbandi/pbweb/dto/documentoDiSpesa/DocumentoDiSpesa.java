/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.dto.documentoDiSpesa;

import java.sql.Date;

public class DocumentoDiSpesa implements java.io.Serializable {
	
	// Ex it.csi.pbandi.pbandiweb.dto.DocumentoDiSpesa

	private java.lang.String codiceFiscaleFornitore = null;
	private java.lang.String numeroDocumento = null;
	private java.lang.String dataDocumento = null;
	private java.lang.String partitaIvaFornitore = null;
	private java.lang.String cognomeFornitore = null;
	private java.lang.String nomeFornitore = null;
	private java.lang.String denominazioneFornitore = null;
	private java.lang.String codTipologiaFornitore = null;
	private java.lang.Long id = null;
	private java.lang.Long idProgetto = null;
	private java.lang.Double importoIva = null;
	private java.lang.String stato = null;
	private java.lang.String codTipologiaDocumento = null;
	private java.lang.Double durataTrasferta = null;
	private java.lang.String destinazioneTrasferta = null;
	private java.lang.String codOggettoAttivita = null;
	private java.lang.Double imponibile = null;
	private java.lang.String descrizioneTipologiaDocumento = null;
	private java.lang.String descrizioneTipologiaFornitore = null;
	private java.lang.String descrizioneDocumento = null;
	private java.lang.String partner = null;
	private java.lang.String radioRendicontazione = null;
	private java.lang.String radioDocumentoDiSpesa = null;
	private java.lang.Long idSoggetto = null;
	private java.lang.Boolean isGestitiNelProgetto = null;
	private java.lang.Boolean isRicercaPerCapofila = null;
	private java.lang.Boolean isRicercaPerTutti = null;
	private java.lang.Boolean isRicercaPerPartners = null;
	private java.lang.Long idDocRiferimento = null;
	private java.lang.String dataDocumentoForfettaria = null;
	private java.lang.String numeroDocumentoRiferimento = null;
	private java.lang.String dataDocumentoRiferimento = null;
	private java.lang.String codiceProgetto = null;
	private java.lang.Long idTipoDocumentoDiSpesa = null;
	private java.lang.Long idFornitore = null;
	private java.lang.Double costoOrario = null;
	private java.lang.String chiaveComplessa = null;
	private java.lang.String motivazione = null;
	private java.lang.String descrizioneStato = null;
	private java.lang.String descBreveTipoDocumentoDiSpesa = null;
	private java.util.ArrayList<java.lang.String> statiDocumento = new java.util.ArrayList<java.lang.String>();
	private java.lang.String task = null;
	private java.lang.Long idContoEcomonico = null;
	private java.lang.Long idVoce = null;
	private java.lang.Long idTipoFornitore = null;
	private java.lang.Long idDichiarazioneDiSpesa = null;
	private java.lang.Double importoIvaACosto = null;
	private java.lang.Double importoRendicontabile = null;
	private java.lang.Double importoRendicontabileQuietanzato = null;
	private java.lang.Double importoResiduoQuietanzabile = null;
	private java.lang.Double importoResiduoRendicontabileDocumento = null;
	private java.lang.Double importoRitenutaAcconto = null;
	private java.lang.Double importoSpesaForfettaria = null;
	private java.lang.Double importoTotaleDocumento = null;
	private java.lang.Double importoTotaleRendicontato = null;
	private java.lang.Double importoTotaleNoteDiCredito = null;
	private java.lang.Double importoTotaleQuietanzato = null;
	private java.lang.Double importoTotaleValidato = null;
	private java.lang.Double oreLavorate = null;
	private java.lang.Long idStatoDocumentoSpesa = null;
	private java.lang.String progrFornitoreQualifica = null;
	private java.lang.Long idTipoOggettoAttivita = null;
	private java.lang.String noteValidazione = null;
	private java.lang.String descBreveStatoDocumentoSpesa = null;
	private java.lang.String linkValida = null;
	private java.lang.Boolean isRettificato = null;
	private java.lang.String tipoInvio = null;
	private java.lang.String flagElettronico = null;
	private java.lang.Boolean isValidabile = null;
	private java.lang.Boolean isModificabile = null;
	private java.lang.String msgNonValidabile = null;
	private java.lang.String numDichiarazione = null;
	private java.util.ArrayList<java.lang.String> numDichiarazioni = new java.util.ArrayList<java.lang.String>();
	private java.util.ArrayList<java.lang.Double> importiValidati = new java.util.ArrayList<java.lang.Double>();
	private java.util.ArrayList<java.lang.Boolean> rettificatos = new java.util.ArrayList<java.lang.Boolean>();
	private java.lang.Boolean isAllegatiPresenti = null;
	private java.lang.Long idAppalto = null;
	private java.lang.String descrizioneAppalto = null;
	private java.lang.String flagPubblicoPrivatoFornitore = null;
	private java.lang.Boolean flagFatturaElettronica = null;
	private java.lang.String codUniIpa = null;
	private java.lang.Long codTipologiaFormaGiuridica = null;
	private java.lang.String flagElettXml = null;
	private String rilievoContabile;
	private Date dtRilievoContabile;
	
	public java.lang.String getCodiceFiscaleFornitore() {
		return codiceFiscaleFornitore;
	}

	public void setCodiceFiscaleFornitore(java.lang.String codiceFiscaleFornitore) {
		this.codiceFiscaleFornitore = codiceFiscaleFornitore;
	}

	public java.lang.String getNumeroDocumento() {
		return numeroDocumento;
	}

	public void setNumeroDocumento(java.lang.String numeroDocumento) {
		this.numeroDocumento = numeroDocumento;
	}

	public java.lang.String getDataDocumento() {
		return dataDocumento;
	}

	public void setDataDocumento(java.lang.String dataDocumento) {
		this.dataDocumento = dataDocumento;
	}

	public java.lang.String getPartitaIvaFornitore() {
		return partitaIvaFornitore;
	}

	public void setPartitaIvaFornitore(java.lang.String partitaIvaFornitore) {
		this.partitaIvaFornitore = partitaIvaFornitore;
	}

	public java.lang.String getCognomeFornitore() {
		return cognomeFornitore;
	}

	public void setCognomeFornitore(java.lang.String cognomeFornitore) {
		this.cognomeFornitore = cognomeFornitore;
	}

	public java.lang.String getNomeFornitore() {
		return nomeFornitore;
	}

	public void setNomeFornitore(java.lang.String nomeFornitore) {
		this.nomeFornitore = nomeFornitore;
	}

	public java.lang.String getDenominazioneFornitore() {
		return denominazioneFornitore;
	}

	public void setDenominazioneFornitore(java.lang.String denominazioneFornitore) {
		this.denominazioneFornitore = denominazioneFornitore;
	}

	public java.lang.String getCodTipologiaFornitore() {
		return codTipologiaFornitore;
	}

	public void setCodTipologiaFornitore(java.lang.String codTipologiaFornitore) {
		this.codTipologiaFornitore = codTipologiaFornitore;
	}

	public java.lang.Long getId() {
		return id;
	}

	public void setId(java.lang.Long id) {
		this.id = id;
	}

	public java.lang.Long getIdProgetto() {
		return idProgetto;
	}

	public void setIdProgetto(java.lang.Long idProgetto) {
		this.idProgetto = idProgetto;
	}

	public java.lang.Double getImportoIva() {
		return importoIva;
	}

	public void setImportoIva(java.lang.Double importoIva) {
		this.importoIva = importoIva;
	}

	public java.lang.String getStato() {
		return stato;
	}

	public void setStato(java.lang.String stato) {
		this.stato = stato;
	}

	public java.lang.String getCodTipologiaDocumento() {
		return codTipologiaDocumento;
	}

	public void setCodTipologiaDocumento(java.lang.String codTipologiaDocumento) {
		this.codTipologiaDocumento = codTipologiaDocumento;
	}

	public java.lang.Double getDurataTrasferta() {
		return durataTrasferta;
	}

	public void setDurataTrasferta(java.lang.Double durataTrasferta) {
		this.durataTrasferta = durataTrasferta;
	}

	public java.lang.String getDestinazioneTrasferta() {
		return destinazioneTrasferta;
	}

	public void setDestinazioneTrasferta(java.lang.String destinazioneTrasferta) {
		this.destinazioneTrasferta = destinazioneTrasferta;
	}

	public java.lang.String getCodOggettoAttivita() {
		return codOggettoAttivita;
	}

	public void setCodOggettoAttivita(java.lang.String codOggettoAttivita) {
		this.codOggettoAttivita = codOggettoAttivita;
	}

	public java.lang.Double getImponibile() {
		return imponibile;
	}

	public void setImponibile(java.lang.Double imponibile) {
		this.imponibile = imponibile;
	}

	public java.lang.String getDescrizioneTipologiaDocumento() {
		return descrizioneTipologiaDocumento;
	}

	public void setDescrizioneTipologiaDocumento(java.lang.String descrizioneTipologiaDocumento) {
		this.descrizioneTipologiaDocumento = descrizioneTipologiaDocumento;
	}

	public java.lang.String getDescrizioneTipologiaFornitore() {
		return descrizioneTipologiaFornitore;
	}

	public void setDescrizioneTipologiaFornitore(java.lang.String descrizioneTipologiaFornitore) {
		this.descrizioneTipologiaFornitore = descrizioneTipologiaFornitore;
	}

	public java.lang.String getDescrizioneDocumento() {
		return descrizioneDocumento;
	}

	public void setDescrizioneDocumento(java.lang.String descrizioneDocumento) {
		this.descrizioneDocumento = descrizioneDocumento;
	}

	public java.lang.String getPartner() {
		return partner;
	}

	public void setPartner(java.lang.String partner) {
		this.partner = partner;
	}

	public java.lang.String getRadioRendicontazione() {
		return radioRendicontazione;
	}

	public void setRadioRendicontazione(java.lang.String radioRendicontazione) {
		this.radioRendicontazione = radioRendicontazione;
	}

	public java.lang.String getRadioDocumentoDiSpesa() {
		return radioDocumentoDiSpesa;
	}

	public void setRadioDocumentoDiSpesa(java.lang.String radioDocumentoDiSpesa) {
		this.radioDocumentoDiSpesa = radioDocumentoDiSpesa;
	}

	public java.lang.Long getIdSoggetto() {
		return idSoggetto;
	}

	public void setIdSoggetto(java.lang.Long idSoggetto) {
		this.idSoggetto = idSoggetto;
	}

	public java.lang.Boolean getIsGestitiNelProgetto() {
		return isGestitiNelProgetto;
	}

	public void setIsGestitiNelProgetto(java.lang.Boolean isGestitiNelProgetto) {
		this.isGestitiNelProgetto = isGestitiNelProgetto;
	}

	public java.lang.Boolean getIsRicercaPerCapofila() {
		return isRicercaPerCapofila;
	}

	public void setIsRicercaPerCapofila(java.lang.Boolean isRicercaPerCapofila) {
		this.isRicercaPerCapofila = isRicercaPerCapofila;
	}

	public java.lang.Boolean getIsRicercaPerTutti() {
		return isRicercaPerTutti;
	}

	public void setIsRicercaPerTutti(java.lang.Boolean isRicercaPerTutti) {
		this.isRicercaPerTutti = isRicercaPerTutti;
	}

	public java.lang.Boolean getIsRicercaPerPartners() {
		return isRicercaPerPartners;
	}

	public void setIsRicercaPerPartners(java.lang.Boolean isRicercaPerPartners) {
		this.isRicercaPerPartners = isRicercaPerPartners;
	}

	public java.lang.Long getIdDocRiferimento() {
		return idDocRiferimento;
	}

	public void setIdDocRiferimento(java.lang.Long idDocRiferimento) {
		this.idDocRiferimento = idDocRiferimento;
	}

	public java.lang.String getDataDocumentoForfettaria() {
		return dataDocumentoForfettaria;
	}

	public void setDataDocumentoForfettaria(java.lang.String dataDocumentoForfettaria) {
		this.dataDocumentoForfettaria = dataDocumentoForfettaria;
	}

	public java.lang.String getNumeroDocumentoRiferimento() {
		return numeroDocumentoRiferimento;
	}

	public void setNumeroDocumentoRiferimento(java.lang.String numeroDocumentoRiferimento) {
		this.numeroDocumentoRiferimento = numeroDocumentoRiferimento;
	}

	public java.lang.String getDataDocumentoRiferimento() {
		return dataDocumentoRiferimento;
	}

	public void setDataDocumentoRiferimento(java.lang.String dataDocumentoRiferimento) {
		this.dataDocumentoRiferimento = dataDocumentoRiferimento;
	}

	public java.lang.String getCodiceProgetto() {
		return codiceProgetto;
	}

	public void setCodiceProgetto(java.lang.String codiceProgetto) {
		this.codiceProgetto = codiceProgetto;
	}

	public java.lang.Long getIdTipoDocumentoDiSpesa() {
		return idTipoDocumentoDiSpesa;
	}

	public void setIdTipoDocumentoDiSpesa(java.lang.Long idTipoDocumentoDiSpesa) {
		this.idTipoDocumentoDiSpesa = idTipoDocumentoDiSpesa;
	}

	public java.lang.Long getIdFornitore() {
		return idFornitore;
	}

	public void setIdFornitore(java.lang.Long idFornitore) {
		this.idFornitore = idFornitore;
	}

	public java.lang.Double getCostoOrario() {
		return costoOrario;
	}

	public void setCostoOrario(java.lang.Double costoOrario) {
		this.costoOrario = costoOrario;
	}

	public java.lang.String getChiaveComplessa() {
		return chiaveComplessa;
	}

	public void setChiaveComplessa(java.lang.String chiaveComplessa) {
		this.chiaveComplessa = chiaveComplessa;
	}

	public java.lang.String getMotivazione() {
		return motivazione;
	}

	public void setMotivazione(java.lang.String motivazione) {
		this.motivazione = motivazione;
	}

	public java.lang.String getDescrizioneStato() {
		return descrizioneStato;
	}

	public void setDescrizioneStato(java.lang.String descrizioneStato) {
		this.descrizioneStato = descrizioneStato;
	}

	public java.lang.String getDescBreveTipoDocumentoDiSpesa() {
		return descBreveTipoDocumentoDiSpesa;
	}

	public void setDescBreveTipoDocumentoDiSpesa(java.lang.String descBreveTipoDocumentoDiSpesa) {
		this.descBreveTipoDocumentoDiSpesa = descBreveTipoDocumentoDiSpesa;
	}

	public java.util.ArrayList<java.lang.String> getStatiDocumento() {
		return statiDocumento;
	}

	public void setStatiDocumento(java.util.ArrayList<java.lang.String> statiDocumento) {
		this.statiDocumento = statiDocumento;
	}

	public java.lang.String getTask() {
		return task;
	}

	public void setTask(java.lang.String task) {
		this.task = task;
	}

	public java.lang.Long getIdContoEcomonico() {
		return idContoEcomonico;
	}

	public void setIdContoEcomonico(java.lang.Long idContoEcomonico) {
		this.idContoEcomonico = idContoEcomonico;
	}

	public java.lang.Long getIdVoce() {
		return idVoce;
	}

	public void setIdVoce(java.lang.Long idVoce) {
		this.idVoce = idVoce;
	}

	public java.lang.Long getIdTipoFornitore() {
		return idTipoFornitore;
	}

	public void setIdTipoFornitore(java.lang.Long idTipoFornitore) {
		this.idTipoFornitore = idTipoFornitore;
	}

	public java.lang.Long getIdDichiarazioneDiSpesa() {
		return idDichiarazioneDiSpesa;
	}

	public void setIdDichiarazioneDiSpesa(java.lang.Long idDichiarazioneDiSpesa) {
		this.idDichiarazioneDiSpesa = idDichiarazioneDiSpesa;
	}

	public java.lang.Double getImportoIvaACosto() {
		return importoIvaACosto;
	}

	public void setImportoIvaACosto(java.lang.Double importoIvaACosto) {
		this.importoIvaACosto = importoIvaACosto;
	}

	public java.lang.Double getImportoRendicontabile() {
		return importoRendicontabile;
	}

	public void setImportoRendicontabile(java.lang.Double importoRendicontabile) {
		this.importoRendicontabile = importoRendicontabile;
	}

	public java.lang.Double getImportoRendicontabileQuietanzato() {
		return importoRendicontabileQuietanzato;
	}

	public void setImportoRendicontabileQuietanzato(java.lang.Double importoRendicontabileQuietanzato) {
		this.importoRendicontabileQuietanzato = importoRendicontabileQuietanzato;
	}

	public java.lang.Double getImportoResiduoQuietanzabile() {
		return importoResiduoQuietanzabile;
	}

	public void setImportoResiduoQuietanzabile(java.lang.Double importoResiduoQuietanzabile) {
		this.importoResiduoQuietanzabile = importoResiduoQuietanzabile;
	}

	public java.lang.Double getImportoResiduoRendicontabileDocumento() {
		return importoResiduoRendicontabileDocumento;
	}

	public void setImportoResiduoRendicontabileDocumento(java.lang.Double importoResiduoRendicontabileDocumento) {
		this.importoResiduoRendicontabileDocumento = importoResiduoRendicontabileDocumento;
	}

	public java.lang.Double getImportoRitenutaAcconto() {
		return importoRitenutaAcconto;
	}

	public void setImportoRitenutaAcconto(java.lang.Double importoRitenutaAcconto) {
		this.importoRitenutaAcconto = importoRitenutaAcconto;
	}

	public java.lang.Double getImportoSpesaForfettaria() {
		return importoSpesaForfettaria;
	}

	public void setImportoSpesaForfettaria(java.lang.Double importoSpesaForfettaria) {
		this.importoSpesaForfettaria = importoSpesaForfettaria;
	}

	public java.lang.Double getImportoTotaleDocumento() {
		return importoTotaleDocumento;
	}

	public void setImportoTotaleDocumento(java.lang.Double importoTotaleDocumento) {
		this.importoTotaleDocumento = importoTotaleDocumento;
	}

	public java.lang.Double getImportoTotaleRendicontato() {
		return importoTotaleRendicontato;
	}

	public void setImportoTotaleRendicontato(java.lang.Double importoTotaleRendicontato) {
		this.importoTotaleRendicontato = importoTotaleRendicontato;
	}

	public java.lang.Double getImportoTotaleNoteDiCredito() {
		return importoTotaleNoteDiCredito;
	}

	public void setImportoTotaleNoteDiCredito(java.lang.Double importoTotaleNoteDiCredito) {
		this.importoTotaleNoteDiCredito = importoTotaleNoteDiCredito;
	}

	public java.lang.Double getImportoTotaleQuietanzato() {
		return importoTotaleQuietanzato;
	}

	public void setImportoTotaleQuietanzato(java.lang.Double importoTotaleQuietanzato) {
		this.importoTotaleQuietanzato = importoTotaleQuietanzato;
	}

	public java.lang.Double getImportoTotaleValidato() {
		return importoTotaleValidato;
	}

	public void setImportoTotaleValidato(java.lang.Double importoTotaleValidato) {
		this.importoTotaleValidato = importoTotaleValidato;
	}

	public java.lang.Double getOreLavorate() {
		return oreLavorate;
	}

	public void setOreLavorate(java.lang.Double oreLavorate) {
		this.oreLavorate = oreLavorate;
	}

	public java.lang.Long getIdStatoDocumentoSpesa() {
		return idStatoDocumentoSpesa;
	}

	public void setIdStatoDocumentoSpesa(java.lang.Long idStatoDocumentoSpesa) {
		this.idStatoDocumentoSpesa = idStatoDocumentoSpesa;
	}

	public java.lang.String getProgrFornitoreQualifica() {
		return progrFornitoreQualifica;
	}

	public void setProgrFornitoreQualifica(java.lang.String progrFornitoreQualifica) {
		this.progrFornitoreQualifica = progrFornitoreQualifica;
	}

	public java.lang.Long getIdTipoOggettoAttivita() {
		return idTipoOggettoAttivita;
	}

	public void setIdTipoOggettoAttivita(java.lang.Long idTipoOggettoAttivita) {
		this.idTipoOggettoAttivita = idTipoOggettoAttivita;
	}

	public java.lang.String getNoteValidazione() {
		return noteValidazione;
	}

	public void setNoteValidazione(java.lang.String noteValidazione) {
		this.noteValidazione = noteValidazione;
	}

	public java.lang.String getDescBreveStatoDocumentoSpesa() {
		return descBreveStatoDocumentoSpesa;
	}

	public void setDescBreveStatoDocumentoSpesa(java.lang.String descBreveStatoDocumentoSpesa) {
		this.descBreveStatoDocumentoSpesa = descBreveStatoDocumentoSpesa;
	}

	public java.lang.String getLinkValida() {
		return linkValida;
	}

	public void setLinkValida(java.lang.String linkValida) {
		this.linkValida = linkValida;
	}

	public java.lang.Boolean getIsRettificato() {
		return isRettificato;
	}

	public void setIsRettificato(java.lang.Boolean isRettificato) {
		this.isRettificato = isRettificato;
	}

	public java.lang.String getTipoInvio() {
		return tipoInvio;
	}

	public void setTipoInvio(java.lang.String tipoInvio) {
		this.tipoInvio = tipoInvio;
	}

	public java.lang.String getFlagElettronico() {
		return flagElettronico;
	}

	public void setFlagElettronico(java.lang.String flagElettronico) {
		this.flagElettronico = flagElettronico;
	}

	public java.lang.Boolean getIsValidabile() {
		return isValidabile;
	}

	public void setIsValidabile(java.lang.Boolean isValidabile) {
		this.isValidabile = isValidabile;
	}

	public java.lang.Boolean getIsModificabile() {
		return isModificabile;
	}

	public void setIsModificabile(java.lang.Boolean isModificabile) {
		this.isModificabile = isModificabile;
	}

	public java.lang.String getMsgNonValidabile() {
		return msgNonValidabile;
	}

	public void setMsgNonValidabile(java.lang.String msgNonValidabile) {
		this.msgNonValidabile = msgNonValidabile;
	}

	public java.lang.String getNumDichiarazione() {
		return numDichiarazione;
	}

	public void setNumDichiarazione(java.lang.String numDichiarazione) {
		this.numDichiarazione = numDichiarazione;
	}

	public java.util.ArrayList<java.lang.String> getNumDichiarazioni() {
		return numDichiarazioni;
	}

	public void setNumDichiarazioni(java.util.ArrayList<java.lang.String> numDichiarazioni) {
		this.numDichiarazioni = numDichiarazioni;
	}

	public java.util.ArrayList<java.lang.Double> getImportiValidati() {
		return importiValidati;
	}

	public void setImportiValidati(java.util.ArrayList<java.lang.Double> importiValidati) {
		this.importiValidati = importiValidati;
	}

	public java.util.ArrayList<java.lang.Boolean> getRettificatos() {
		return rettificatos;
	}

	public void setRettificatos(java.util.ArrayList<java.lang.Boolean> rettificatos) {
		this.rettificatos = rettificatos;
	}

	public java.lang.Boolean getIsAllegatiPresenti() {
		return isAllegatiPresenti;
	}

	public void setIsAllegatiPresenti(java.lang.Boolean isAllegatiPresenti) {
		this.isAllegatiPresenti = isAllegatiPresenti;
	}

	public java.lang.Long getIdAppalto() {
		return idAppalto;
	}

	public void setIdAppalto(java.lang.Long idAppalto) {
		this.idAppalto = idAppalto;
	}

	public java.lang.String getDescrizioneAppalto() {
		return descrizioneAppalto;
	}

	public void setDescrizioneAppalto(java.lang.String descrizioneAppalto) {
		this.descrizioneAppalto = descrizioneAppalto;
	}

	public java.lang.String getFlagPubblicoPrivatoFornitore() {
		return flagPubblicoPrivatoFornitore;
	}

	public void setFlagPubblicoPrivatoFornitore(java.lang.String flagPubblicoPrivatoFornitore) {
		this.flagPubblicoPrivatoFornitore = flagPubblicoPrivatoFornitore;
	}

	public java.lang.Boolean getFlagFatturaElettronica() {
		return flagFatturaElettronica;
	}

	public void setFlagFatturaElettronica(java.lang.Boolean flagFatturaElettronica) {
		this.flagFatturaElettronica = flagFatturaElettronica;
	}

	public java.lang.String getCodUniIpa() {
		return codUniIpa;
	}

	public void setCodUniIpa(java.lang.String codUniIpa) {
		this.codUniIpa = codUniIpa;
	}

	public java.lang.Long getCodTipologiaFormaGiuridica() {
		return codTipologiaFormaGiuridica;
	}

	public void setCodTipologiaFormaGiuridica(java.lang.Long codTipologiaFormaGiuridica) {
		this.codTipologiaFormaGiuridica = codTipologiaFormaGiuridica;
	}

	public java.lang.String getFlagElettXml() {
		return flagElettXml;
	}

	public void setFlagElettXml(java.lang.String flagElettXml) {
		this.flagElettXml = flagElettXml;
	}

	public String getRilievoContabile() {
		return rilievoContabile;
	}

	public void setRilievoContabile(String rilievoContabile) {
		this.rilievoContabile = rilievoContabile;
	}

	public Date getDtRilievoContabile() {
		return dtRilievoContabile;
	}

	public void setDtRilievoContabile(Date dtRilievoContabile) {
		this.dtRilievoContabile = dtRilievoContabile;
	}

	public String toString() {
		String s =
			"\n NumeroDocumento: "+this.getNumeroDocumento()+
			"\n DataDocumento: "+this.getDataDocumento()+
			"\n DescrizioneDocumento: "+this.getDescrizioneDocumento()+
			"\n Imponibile: "+this.getImponibile()+
			"\n ImportoIva: "+this.getImportoIva()+
			"\n ImportoTotaleDocumento: "+this.getImportoTotaleDocumento()+
			"\n DenominazioneFornitore: "+this.getDenominazioneFornitore()+
			"\n CodiceFiscaleFornitore: "+this.getCodiceFiscaleFornitore()+
			"\n PartitaIvaFornitore: "+this.getPartitaIvaFornitore()+
			"\n FlagPubblicoPrivatoFornitore: "+this.getFlagPubblicoPrivatoFornitore()+
			"\n IdTipoFornitore: "+this.getIdTipoFornitore()+
			"\n CodTipologiaFornitore: "+this.getCodTipologiaFornitore()+
			"\n IdFornitore: "+this.getIdFornitore()+
			"\n FlagElettronico: "+this.getFlagElettronico()+
			"\n FlagElettXml: "+this.getFlagElettXml()+
			"\n Rilievo contabile: "+this.getRilievoContabile()+
			"\n Dt rilievo contabile: "+this.getDtRilievoContabile();			
		return s;
	}
}
