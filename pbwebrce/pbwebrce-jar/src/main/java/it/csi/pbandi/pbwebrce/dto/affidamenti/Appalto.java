/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebrce.dto.affidamenti;

import java.sql.Date;

public class Appalto implements java.io.Serializable {
	private Long idAppalto;

	private Date dtPrevistaInizioLavori;
	private Date dtConsegnaLavori;
	private Date dtFirmaContratto;
	private Double importoContratto;
	private Double bilancioPreventivo;
	private String oggettoAppalto;
	private Long idProceduraAggiudicazione;
	private String proceduraAggiudicazione;
	private String descrizioneProceduraAggiudicazione;
	private Date dtPubGUUE;
	private Date dtPubGURI;
	private Date dtPubQuotidianiNazionali;
	private Date dtPubStazioneAppaltante;
	private Date dtPubLLPP;
	private Long idTipologiaAppalto;
	private String impresaAppaltatrice;
	private String identificativoIntervento;
	private Double importoRibassoAsta;
	private Double percentualeRibassoAsta;
	private static final long serialVersionUID = 1L;


	
	public Long getIdAppalto() {
		return idAppalto;
	}

	public void setIdAppalto(Long idAppalto) {
		this.idAppalto = idAppalto;
	}

	public Date getDtPrevistaInizioLavori() {
		return dtPrevistaInizioLavori;
	}

	public void setDtPrevistaInizioLavori(Date dtPrevistaInizioLavori) {
		this.dtPrevistaInizioLavori = dtPrevistaInizioLavori;
	}

	public Date getDtConsegnaLavori() {
		return dtConsegnaLavori;
	}

	public void setDtConsegnaLavori(Date dtConsegnaLavori) {
		this.dtConsegnaLavori = dtConsegnaLavori;
	}

	public Date getDtFirmaContratto() {
		return dtFirmaContratto;
	}

	public void setDtFirmaContratto(Date dtFirmaContratto) {
		this.dtFirmaContratto = dtFirmaContratto;
	}

	public Double getImportoContratto() {
		return importoContratto;
	}

	public void setImportoContratto(Double importoContratto) {
		this.importoContratto = importoContratto;
	}

	public Double getBilancioPreventivo() {
		return bilancioPreventivo;
	}

	public void setBilancioPreventivo(Double bilancioPreventivo) {
		this.bilancioPreventivo = bilancioPreventivo;
	}

	public String getOggettoAppalto() {
		return oggettoAppalto;
	}

	public void setOggettoAppalto(String oggettoAppalto) {
		this.oggettoAppalto = oggettoAppalto;
	}

	public Long getIdProceduraAggiudicazione() {
		return idProceduraAggiudicazione;
	}

	public void setIdProceduraAggiudicazione(Long idProceduraAggiudicazione) {
		this.idProceduraAggiudicazione = idProceduraAggiudicazione;
	}

	public String getProceduraAggiudicazione() {
		return proceduraAggiudicazione;
	}

	public void setProceduraAggiudicazione(String proceduraAggiudicazione) {
		this.proceduraAggiudicazione = proceduraAggiudicazione;
	}

	public String getDescrizioneProceduraAggiudicazione() {
		return descrizioneProceduraAggiudicazione;
	}

	public void setDescrizioneProceduraAggiudicazione(String descrizioneProceduraAggiudicazione) {
		this.descrizioneProceduraAggiudicazione = descrizioneProceduraAggiudicazione;
	}

	public Date getDtPubGUUE() {
		return dtPubGUUE;
	}

	public void setDtPubGUUE(Date dtPubGUUE) {
		this.dtPubGUUE = dtPubGUUE;
	}

	public Date getDtPubGURI() {
		return dtPubGURI;
	}

	public void setDtPubGURI(Date dtPubGURI) {
		this.dtPubGURI = dtPubGURI;
	}

	public Date getDtPubQuotidianiNazionali() {
		return dtPubQuotidianiNazionali;
	}

	public void setDtPubQuotidianiNazionali(Date dtPubQuotidianiNazionali) {
		this.dtPubQuotidianiNazionali = dtPubQuotidianiNazionali;
	}

	public Date getDtPubStazioneAppaltante() {
		return dtPubStazioneAppaltante;
	}

	public void setDtPubStazioneAppaltante(Date dtPubStazioneAppaltante) {
		this.dtPubStazioneAppaltante = dtPubStazioneAppaltante;
	}

	public Date getDtPubLLPP() {
		return dtPubLLPP;
	}

	public void setDtPubLLPP(Date dtPubLLPP) {
		this.dtPubLLPP = dtPubLLPP;
	}

	public Long getIdTipologiaAppalto() {
		return idTipologiaAppalto;
	}

	public void setIdTipologiaAppalto(Long idTipologiaAppalto) {
		this.idTipologiaAppalto = idTipologiaAppalto;
	}

	public String getImpresaAppaltatrice() {
		return impresaAppaltatrice;
	}

	public void setImpresaAppaltatrice(String impresaAppaltatrice) {
		this.impresaAppaltatrice = impresaAppaltatrice;
	}

	public String getIdentificativoIntervento() {
		return identificativoIntervento;
	}

	public void setIdentificativoIntervento(String identificativoIntervento) {
		this.identificativoIntervento = identificativoIntervento;
	}

	public Double getImportoRibassoAsta() {
		return importoRibassoAsta;
	}

	public void setImportoRibassoAsta(Double importoRibassoAsta) {
		this.importoRibassoAsta = importoRibassoAsta;
	}

	public Double getPercentualeRibassoAsta() {
		return percentualeRibassoAsta;
	}

	public void setPercentualeRibassoAsta(Double percentualeRibassoAsta) {
		this.percentualeRibassoAsta = percentualeRibassoAsta;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Appalto() {
		super();

	}

	public String toString() {
		return super.toString();
	}
}
