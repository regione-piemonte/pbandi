package it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo;

import java.math.BigDecimal;

public class ReportDettaglioDocumentiSpesaVO extends GenericVO {

	
	private String codiceFiscaleFornitore;
	private String descTipologiaFornitore;
	private String cognomeFornitore;
	private String denominazioneFornitore;
	private String descBreveTipoDocSpesa;
	private String descDocumento;
	private String descFornitore;
	private String descStatoDocumentoSpesa;
	private String descTipoDocumentoSpesa;
	private String destinazioneTrasferta;
	private java.sql.Date dtDichSpesa;
	private java.sql.Date dtEmissioneDocumento;
	private java.sql.Date dtValidazione;
	private BigDecimal durataTrasferta;
	private Long idDichiarazioneSpesa;
	private BigDecimal idDocRiferimento;
	private BigDecimal idDocumentoDiSpesa;
	private BigDecimal idFornitore;
	private BigDecimal idRigoContoEconomico;
	private BigDecimal idSoggetto;
	private BigDecimal idTipoDocumentoSpesa;
	private String idTipoFornitore;
	private BigDecimal idTipoOggettoAttivita;
	private BigDecimal idUtenteAgg;
	private BigDecimal idUtenteIns;
	private BigDecimal imponibile;
	private BigDecimal importoIva;
	private BigDecimal importoIvaCosto;
	private BigDecimal importoQuietanzatoVoce;
	private BigDecimal importoTotaleDocumento;
	private BigDecimal importoValidatoDoc;
	private BigDecimal importoValidatoVoce;
	private String nomeFornitore;
	private String noteValidazioneDoc;
	private String noteValidazioneVds;
	private String numeroDocumento;
	private String task;
	private String voceDiSpesa;


	/**
	 * @return the dtDichSpesa
	 */
	public java.sql.Date getDtDichSpesa() {
		return dtDichSpesa;
	}

	/**
	 * @param dtDichSpesa the dtDichSpesa to set
	 */
	public void setDtDichSpesa(java.sql.Date dtDichSpesa) {
		this.dtDichSpesa = dtDichSpesa;
	}

	/**
	 * @return the dtValidazione
	 */
	public java.sql.Date getDtValidazione() {
		return dtValidazione;
	}

	/**
	 * @param dtValidazione the dtValidazione to set
	 */
	public void setDtValidazione(java.sql.Date dtValidazione) {
		this.dtValidazione = dtValidazione;
	}
	public String getCodiceFiscaleFornitore() {
		return codiceFiscaleFornitore;
	}

	public void setCodiceFiscaleFornitore(String codiceFiscaleFornitore) {
		this.codiceFiscaleFornitore = codiceFiscaleFornitore;
	}

	public String getDescTipologiaFornitore() {
		return descTipologiaFornitore;
	}

	public void setDescTipologiaFornitore(String descTipologiaFornitore) {
		this.descTipologiaFornitore = descTipologiaFornitore;
	}

	public String getIdTipoFornitore() {
		return idTipoFornitore;
	}

	public void setIdTipoFornitore(String idTipoFornitore) {
		this.idTipoFornitore = idTipoFornitore;
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

	public String getDenominazioneFornitore() {
		return denominazioneFornitore;
	}

	public void setDenominazioneFornitore(String denominazioneFornitore) {
		this.denominazioneFornitore = denominazioneFornitore;
	}

	public String getDescFornitore() {
		return descFornitore;
	}

	public void setDescFornitore(String descFornitore) {
		this.descFornitore = descFornitore;
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

	public java.sql.Date getDtEmissioneDocumento() {
		return dtEmissioneDocumento;
	}

	public void setDtEmissioneDocumento(java.sql.Date dtEmissioneDocumento) {
		this.dtEmissioneDocumento = dtEmissioneDocumento;
	}

	public String getDescDocumento() {
		return descDocumento;
	}

	public void setDescDocumento(String descDocumento) {
		this.descDocumento = descDocumento;
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

	public BigDecimal getIdDocRiferimento() {
		return idDocRiferimento;
	}

	public void setIdDocRiferimento(BigDecimal idDocRiferimento) {
		this.idDocRiferimento = idDocRiferimento;
	}

	public BigDecimal getIdDocumentoDiSpesa() {
		return idDocumentoDiSpesa;
	}

	public void setIdDocumentoDiSpesa(BigDecimal idDocumentoDiSpesa) {
		this.idDocumentoDiSpesa = idDocumentoDiSpesa;
	}

	public BigDecimal getIdFornitore() {
		return idFornitore;
	}

	public void setIdFornitore(BigDecimal idFornitore) {
		this.idFornitore = idFornitore;
	}

	public BigDecimal getIdSoggetto() {
		return idSoggetto;
	}

	public void setIdSoggetto(BigDecimal idSoggetto) {
		this.idSoggetto = idSoggetto;
	}

	public BigDecimal getIdTipoDocumentoSpesa() {
		return idTipoDocumentoSpesa;
	}

	public void setIdTipoDocumentoSpesa(BigDecimal idTipoDocumentoSpesa) {
		this.idTipoDocumentoSpesa = idTipoDocumentoSpesa;
	}

	public BigDecimal getIdTipoOggettoAttivita() {
		return idTipoOggettoAttivita;
	}

	public void setIdTipoOggettoAttivita(BigDecimal idTipoOggettoAttivita) {
		this.idTipoOggettoAttivita = idTipoOggettoAttivita;
	}

	public BigDecimal getIdUtenteAgg() {
		return idUtenteAgg;
	}

	public void setIdUtenteAgg(BigDecimal idUtenteAgg) {
		this.idUtenteAgg = idUtenteAgg;
	}

	public BigDecimal getIdUtenteIns() {
		return idUtenteIns;
	}

	public void setIdUtenteIns(BigDecimal idUtenteIns) {
		this.idUtenteIns = idUtenteIns;
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

	public BigDecimal getImportoTotaleDocumento() {
		return importoTotaleDocumento;
	}

	public void setImportoTotaleDocumento(BigDecimal importoTotaleDocumento) {
		this.importoTotaleDocumento = importoTotaleDocumento;
	}

	public String getNumeroDocumento() {
		return numeroDocumento;
	}

	public void setNumeroDocumento(String numeroDocumento) {
		this.numeroDocumento = numeroDocumento;
	}

	public Long getIdDichiarazioneSpesa() {
		return idDichiarazioneSpesa;
	}

	public void setIdDichiarazioneSpesa(Long idDichiarazioneSpesa) {
		this.idDichiarazioneSpesa = idDichiarazioneSpesa;
	}

	public String getTask() {
		return task;
	}

	public void setTask(String task) {
		this.task = task;
	}

	public BigDecimal getIdRigoContoEconomico() {
		return idRigoContoEconomico;
	}

	public void setIdRigoContoEconomico(BigDecimal idRigoContoEconomico) {
		this.idRigoContoEconomico = idRigoContoEconomico;
	}

	public String getVoceDiSpesa() {
		return voceDiSpesa;
	}

	public void setVoceDiSpesa(String voceDiSpesa) {
		this.voceDiSpesa = voceDiSpesa;
	}

	public BigDecimal getImportoValidatoDoc() {
		return importoValidatoDoc;
	}

	public void setImportoValidatoDoc(BigDecimal importoValidatoDoc) {
		this.importoValidatoDoc = importoValidatoDoc;
	}

	public BigDecimal getImportoValidatoVoce() {
		return importoValidatoVoce;
	}

	public void setImportoValidatoVoce(BigDecimal importoValidatoVoce) {
		this.importoValidatoVoce = importoValidatoVoce;
	}

	public BigDecimal getImportoQuietanzatoVoce() {
		return importoQuietanzatoVoce;
	}

	public void setImportoQuietanzatoVoce(BigDecimal importoQuietanzatoVoce) {
		this.importoQuietanzatoVoce = importoQuietanzatoVoce;
	}

	public String getNoteValidazioneDoc() {
		return noteValidazioneDoc;
	}

	public void setNoteValidazioneDoc(String noteValidazioneDoc) {
		this.noteValidazioneDoc = noteValidazioneDoc;
	}

	public String getNoteValidazioneVds() {
		return noteValidazioneVds;
	}

	public void setNoteValidazioneVds(String noteValidazioneVds) {
		this.noteValidazioneVds = noteValidazioneVds;
	}

}
