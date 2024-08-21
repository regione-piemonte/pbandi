/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo;

import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.GenericVO;

import java.math.BigDecimal;

public class DocumentoDiSpesaVO extends GenericVO{
	
	

	static final long serialVersionUID = 1;


	private java.lang.String codiceFiscaleFornitore = null;
	private java.lang.String codiceProgetto = null;
	private String 	descBreveTipoDocSpesa;
	private Long idStatoDocumentoDiSpesa = null;
	private BigDecimal importoTotaleValidato;
	private BigDecimal idDichiarazioneSpesa;
	private BigDecimal importoTotaleDocumentoIvato;
	private String task;
	private String taskIdProgetto;
	private String tipoInvio;
	
	
	public Long getIdStatoDocumentoDiSpesa() {
		return idStatoDocumentoDiSpesa;
	}

	public void setIdStatoDocumentoDiSpesa(Long idStatoDocumentoDiSpesa) {
		this.idStatoDocumentoDiSpesa = idStatoDocumentoDiSpesa;
	}

	public void setCodiceFiscaleFornitore(java.lang.String val) {
		codiceFiscaleFornitore = val;
	}

	
	public java.lang.String getCodiceFiscaleFornitore() {
		return codiceFiscaleFornitore;
	}

	

	public void setCodiceProgetto(java.lang.String val) {
		codiceProgetto = val;
	}

	
	public java.lang.String getCodiceProgetto() {
		return codiceProgetto;
	}

	
	private java.lang.String cognomeFornitore = null;

	
	public void setCognomeFornitore(java.lang.String val) {
		cognomeFornitore = val;
	}


	public java.lang.String getCognomeFornitore() {
		return cognomeFornitore;
	}

	
	private java.util.Date dataDocumentoDiSpesa = null;

	
	public void setDataDocumentoDiSpesa(java.util.Date val) {
		dataDocumentoDiSpesa = val;
	}

	public java.util.Date getDataDocumentoDiSpesa() {
		return dataDocumentoDiSpesa;
	}

 
	
	private java.lang.String destinazioneTrasferta = null;

	
	public void setDestinazioneTrasferta(java.lang.String val) {
		destinazioneTrasferta = val;
	}

	public java.lang.String getDestinazioneTrasferta() {
		return destinazioneTrasferta;
	}

	
	private java.lang.String denominazioneFornitore = null;

	
	public void setDenominazioneFornitore(java.lang.String val) {
		denominazioneFornitore = val;
	}

	public java.lang.String getDenominazioneFornitore() {
		return denominazioneFornitore;
	}

	
	private java.lang.String descTipoDocumentoDiSpesa = null;


	
	private java.lang.Double durataTrasferta = null;

	
	public void setDurataTrasferta(java.lang.Double val) {
		durataTrasferta = val;
	}

	public java.lang.Double getDurataTrasferta() {
		return durataTrasferta;
	}

	
 

	
	private java.lang.Long idDocRiferimento = null;

	
	public void setIdDocRiferimento(java.lang.Long val) {
		idDocRiferimento = val;
	}

	
	public java.lang.Long getIdDocRiferimento() {
		return idDocRiferimento;
	}

	/**
	 * @generated
	 */
	private java.lang.Long idDocumentoDiSpesa = null;

	/**
	 * @generated
	 */
	public void setIdDocumentoDiSpesa(java.lang.Long val) {
		idDocumentoDiSpesa = val;
	}

	public java.lang.Long getIdDocumentoDiSpesa() {
		return idDocumentoDiSpesa;
	}

	/**
	 * @generated
	 */
	private java.lang.Long idFornitore = null;

	/**
	 * @generated
	 */
	public void setIdFornitore(java.lang.Long val) {
		idFornitore = val;
	}


	public java.lang.Long getIdFornitore() {
		return idFornitore;
	}

	/**
	 * @generated
	 */
	private java.lang.Long idProgetto = null;

	/**
	 * @generated
	 */
	public void setIdProgetto(java.lang.Long val) {
		idProgetto = val;
	}


	public java.lang.Long getIdProgetto() {
		return idProgetto;
	}

	/**
	 * @generated
	 */
	private java.lang.Long idSoggetto = null;

	/**
	 * @generated
	 */
	public void setIdSoggetto(java.lang.Long val) {
		idSoggetto = val;
	}


	public java.lang.Long getIdSoggetto() {
		return idSoggetto;
	}

	/**
	 * @generated
	 */
	private java.lang.Long idTipoDocumentoDiSpesa = null;

	/**
	 * @generated
	 */
	public void setIdTipoDocumentoDiSpesa(java.lang.Long val) {
		idTipoDocumentoDiSpesa = val;
	}

	
	public java.lang.Long getIdTipoDocumentoDiSpesa() {
		return idTipoDocumentoDiSpesa;
	}

	/**
	 * @generated
	 */
	private java.lang.Long idTipoFornitore = null;

	/**
	 * @generated
	 */
	public void setIdTipoFornitore(java.lang.Long val) {
		idTipoFornitore = val;
	}


	public java.lang.Long getIdTipoFornitore() {
		return idTipoFornitore;
	}

	/**
	 * @generated
	 */
	private java.lang.Long idTipoOggettoAttivita = null;

	/**
	 * @generated
	 */
	public void setIdTipoOggettoAttivita(java.lang.Long val) {
		idTipoOggettoAttivita = val;
	}

	public java.lang.Long getIdTipoOggettoAttivita() {
		return idTipoOggettoAttivita;
	}

	/**
	 * @generated
	 */
	private java.lang.Double imponibile = null;

	/**
	 * @generated
	 */
	public void setImponibile(java.lang.Double val) {
		imponibile = val;
	}


	public java.lang.Double getImponibile() {
		return imponibile;
	}

	/**
	 * @generated
	 */
	private java.lang.Double importoIva = null;

	/**
	 * @generated
	 */
	public void setImportoIva(java.lang.Double val) {
		importoIva = val;
	}


	public java.lang.Double getImportoIva() {
		return importoIva;
	}


	
	 private java.lang.Double importoIvaACosto = null;

	
	public void setImportoIvaACosto(java.lang.Double val) {
		importoIvaACosto = val;
	}


	public java.lang.Double getImportoIvaACosto() {
		return importoIvaACosto;
	}
 
	
	private java.lang.String nomeFornitore = null;

	
	public void setNomeFornitore(java.lang.String val) {
		nomeFornitore = val;
	}


	public java.lang.String getNomeFornitore() {
		return nomeFornitore;
	}

	
	private java.lang.String numeroDocumento = null;

	
	public void setNumeroDocumento(java.lang.String val) {
		numeroDocumento = val;
	}


	public java.lang.String getNumeroDocumento() {
		return numeroDocumento;
	}

	
	private java.lang.String partitaIvaFornitore = null;

	
	public void setPartitaIvaFornitore(java.lang.String val) {
		partitaIvaFornitore = val;
	}


	public java.lang.String getPartitaIvaFornitore() {
		return partitaIvaFornitore;
	}

		
	private java.lang.String descStatoDocumentoSpesa = null;

	
	public void setDescStatoDocumentoSpesa(java.lang.String val) {
		descStatoDocumentoSpesa = val;
	}


	public java.lang.String getDescStatoDocumentoSpesa() {
		return descStatoDocumentoSpesa;
	}

	
	private java.lang.String descTipologiaFornitore = null;

	
	public void setDescTipologiaFornitore(java.lang.String val) {
		descTipologiaFornitore = val;
	}


	public java.lang.String getDescTipologiaFornitore() {
		return descTipologiaFornitore;
	}


	

	public void setTask(String task) {
		this.task = task;
	}

	public String getTask() {
		return task;
	}

	public void setImportoTotaleValidato(BigDecimal importoTotaleValidato) {
		this.importoTotaleValidato = importoTotaleValidato;
	}

	public BigDecimal getImportoTotaleValidato() {
		return importoTotaleValidato;
	}

	public BigDecimal getIdDichiarazioneSpesa() {
		return idDichiarazioneSpesa;
	}

	public void setIdDichiarazioneSpesa(BigDecimal idDichiarazioneSpesa) {
		this.idDichiarazioneSpesa = idDichiarazioneSpesa;
	}

	public String getDescBreveTipoDocSpesa() {
		return descBreveTipoDocSpesa;
	}

	public void setDescBreveTipoDocSpesa(String descBreveTipoDocSpesa) {
		this.descBreveTipoDocSpesa = descBreveTipoDocSpesa;
	}

	public BigDecimal getImportoTotaleDocumentoIvato() {
		return importoTotaleDocumentoIvato;
	}

	public void setImportoTotaleDocumentoIvato(
			BigDecimal importoTotaleDocumentoIvato) {
		this.importoTotaleDocumentoIvato = importoTotaleDocumentoIvato;
	}

	public java.lang.String getDescTipoDocumentoDiSpesa() {
		return descTipoDocumentoDiSpesa;
	}

	public void setDescTipoDocumentoDiSpesa(java.lang.String descTipoDocumentoDiSpesa) {
		this.descTipoDocumentoDiSpesa = descTipoDocumentoDiSpesa;
	}
 
	public String getTaskIdProgetto() {
		return taskIdProgetto;
	}

	public void setTaskIdProgetto(String taskIdProgetto) {
		this.taskIdProgetto = taskIdProgetto;
	}

	public String getTipoInvio() {
		return tipoInvio;
	}

	public void setTipoInvio(String tipoInvio) {
		this.tipoInvio = tipoInvio;
	}

	
	
	

	

	
}
