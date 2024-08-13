/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.dichiarazionedispesa;

/*
 * FIXME questo VO si chiama come un VO generato, bisogna chiarire che differenza c'� tra i due rinominando questo o utilizzando quello generato se possibile
 */
public class DichiarazioneDiSpesaVO {

	static final long serialVersionUID = 1;

	private java.lang.String codiceFiscaleBeneficiario = null;
	private java.lang.String codiceProgetto = null;
	private java.util.Date dtChiusuraValidazione= null;
	private java.util.Date dataDichiarazioneDiSpesa = null;
	private java.util.Date dataInizioRendicontazione = null;
	private java.lang.Long idBandoLinea = null;
	private java.lang.Long idDichiarazione = null;
	private java.lang.Long idProgetto=null;
	private java.lang.Long idSoggetto = null;
	private java.lang.Long idUtente = null;
	private java.lang.String noteChiusuraValidazione= null;
	private java.lang.Long idTipoDichiarazSpesa = null; 
	private java.lang.String tipoInvioDs = null;
	
	


	public java.lang.String getTipoInvioDs() {
		return tipoInvioDs;
	}

	public void setTipoInvioDs(java.lang.String tipoInvioDs) {
		this.tipoInvioDs = tipoInvioDs;
	}

	/**
	 * @return the dtChiusuraValidazione
	 */
	public java.util.Date getDtChiusuraValidazione() {
		return dtChiusuraValidazione;
	}

	/**
	 * @param dtChiusuraValidazione the dtChiusuraValidazione to set
	 */
	public void setDtChiusuraValidazione(java.util.Date dtChiusuraValidazione) {
		this.dtChiusuraValidazione = dtChiusuraValidazione;
	}



	
	public void setIdUtente(java.lang.Long idUtente) {
		this.idUtente = idUtente;
	}

	public java.lang.Long getIdUtente() {
		return idUtente;
	}


	public void setDataDichiarazioneDiSpesa(
			java.util.Date dataDichiarazioneDiSpesa) {
		this.dataDichiarazioneDiSpesa = dataDichiarazioneDiSpesa;
	}

	public java.util.Date getDataDichiarazioneDiSpesa() {
		return dataDichiarazioneDiSpesa;
	}

	private java.lang.Boolean isRicercaPerCapofila = null;

	/**
	 * @generated
	 */
	public void setIsRicercaPerCapofila(java.lang.Boolean val) {
		isRicercaPerCapofila = val;
	}

	public java.lang.Boolean getIsRicercaPerCapofila() {
		return isRicercaPerCapofila;
	}

	/**
	 * @generated
	 */
	private java.lang.Boolean isRicercaPePartners = null;

	/**
	 * @generated
	 */
	public void setIsRicercaPePartners(java.lang.Boolean val) {
		isRicercaPePartners = val;
	}

	public java.lang.Boolean getIsRicercaPePartners() {
		return isRicercaPePartners;
	}

	/**
	 * @generated
	 */

	/**
	 * @generated
	 */
	public void setDataInizioRendicontazione(java.util.Date val) {
		dataInizioRendicontazione = val;
	}

	public java.util.Date getDataInizioRendicontazione() {
		return dataInizioRendicontazione;
	}

	/**
	 * @generated
	 */
	private java.util.Date dataFineRendicontazione = null;

	/**
	 * @generated
	 */
	public void setDataFineRendicontazione(java.util.Date val) {
		dataFineRendicontazione = val;
	}

	public java.util.Date getDataFineRendicontazione() {
		return dataFineRendicontazione;
	}


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

	/**
	 * @generated
	 */
	public void setIdBandoLinea(java.lang.Long val) {
		idBandoLinea = val;
	}

	public java.lang.Long getIdBandoLinea() {
		return idBandoLinea;
	}

	public void setCodiceFiscaleBeneficiario(
			java.lang.String codiceFiscaleBeneficiario) {
		this.codiceFiscaleBeneficiario = codiceFiscaleBeneficiario;
	}

	public java.lang.String getCodiceFiscaleBeneficiario() {
		return codiceFiscaleBeneficiario;
	}

	public void setIdDichiarazione(java.lang.Long idDichiarazione) {
		this.idDichiarazione = idDichiarazione;
	}

	public java.lang.Long getIdDichiarazione() {
		return idDichiarazione;
	}

	public void setCodiceProgetto(java.lang.String codiceProgetto) {
		this.codiceProgetto = codiceProgetto;
	}

	public java.lang.String getCodiceProgetto() {
		return codiceProgetto;
	}

	public void setNoteChiusuraValidazione(java.lang.String noteChiusuraValidazione) {
		this.noteChiusuraValidazione = noteChiusuraValidazione;
	}

	public java.lang.String getNoteChiusuraValidazione() {
		return noteChiusuraValidazione;
	}

	public void setIdTipoDichiarazSpesa(java.lang.Long idTipoDichiarazSpesa) {
		this.idTipoDichiarazSpesa = idTipoDichiarazSpesa;
	}

	public java.lang.Long getIdTipoDichiarazSpesa() {
		return idTipoDichiarazSpesa;
	}



}
