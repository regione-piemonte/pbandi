/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.vo;

import java.sql.Date;
import java.util.List;

import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;
import org.springframework.web.multipart.MultipartFile;

import it.csi.pbandi.pbgestfinbo.dto.DocumentoIndexVO;
import it.csi.pbandi.pbgestfinbo.dto.GestioneAllegatiVO;

public class NoteGeneraliVO {
	
	private Long idAnnotazione; 
	private Long idProgetto; 
	private String note; 
	private Date dataInizioValidita; 		
	private Date dataFineValidita;			
	private Date dataInserimento;				
	private Date dataAggiornamento;	
	private Long idUtenteIns;				
	private Long idUtenteAgg;
	private String nomeUtente; 
	private String cognomeUtente;
	private String nomeUtenteAgg; 
	private String cognomeUtenteAgg;
	
	private List<GestioneAllegatiVO> allegatiPresenti;
	private List<GestioneAllegatiVO> nuoviAllegati;
	
	
	
	public String getNomeUtente() {
		return nomeUtente;
	}
	public void setNomeUtente(String nomeUtente) {
		this.nomeUtente = nomeUtente;
	}
	public String getCognomeUtente() {
		return cognomeUtente;
	}
	public void setCognomeUtente(String cognomeUtente) {
		this.cognomeUtente = cognomeUtente;
	}

	public Long getIdAnnotazione() {
		return idAnnotazione;
	}
	public void setIdAnnotazione(Long idAnnotazione) {
		this.idAnnotazione = idAnnotazione;
	}
	public Long getIdProgetto() {
		return idProgetto;
	}
	public void setIdProgetto(Long idProgetto) {
		this.idProgetto = idProgetto;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public Date getDataInizioValidita() {
		return dataInizioValidita;
	}
	public void setDataInizioValidita(Date dataInizioValidita) {
		this.dataInizioValidita = dataInizioValidita;
	}
	public Date getDataFineValidita() {
		return dataFineValidita;
	}
	public void setDataFineValidita(Date dataFineValidita) {
		this.dataFineValidita = dataFineValidita;
	}
	public Date getDataInserimento() {
		return dataInserimento;
	}
	public void setDataInserimento(Date dataInserimento) {
		this.dataInserimento = dataInserimento;
	}
	public Date getDataAggiornamento() {
		return dataAggiornamento;
	}
	public void setDataAggiornamento(Date dataAggiornamento) {
		this.dataAggiornamento = dataAggiornamento;
	}
	public Long getIdUtenteIns() {
		return idUtenteIns;
	}
	public void setIdUtenteIns(Long idUtenteIns) {
		this.idUtenteIns = idUtenteIns;
	}
	public Long getIdUtenteAgg() {
		return idUtenteAgg;
	}
	public void setIdUtenteAgg(Long idUtenteAgg) {
		this.idUtenteAgg = idUtenteAgg;
	}
	public List<GestioneAllegatiVO> getAllegatiPresenti() {
		return allegatiPresenti;
	}
	public void setAllegatiPresenti(List<GestioneAllegatiVO> allegatiPresenti) {
		this.allegatiPresenti = allegatiPresenti;
	}
	public List<GestioneAllegatiVO> getNuoviAllegati() {
		return nuoviAllegati;
	}
	public void setNuoviAllegati(List<GestioneAllegatiVO> nuoviAllegati) {
		this.nuoviAllegati = nuoviAllegati;
	}
	public String getNomeUtenteAgg() {
		return nomeUtenteAgg;
	}
	public void setNomeUtenteAgg(String nomeUtenteAgg) {
		this.nomeUtenteAgg = nomeUtenteAgg;
	}
	public String getCognomeUtenteAgg() {
		return cognomeUtenteAgg;
	}
	public void setCognomeUtenteAgg(String cognomeUtenteAgg) {
		this.cognomeUtenteAgg = cognomeUtenteAgg;
	}
	@Override
	public String toString() {
		return "NoteGeneraliVO [idAnnotazione=" + idAnnotazione + ", idProgetto=" + idProgetto + ", note=" + note
				+ ", dataInizioValidita=" + dataInizioValidita + ", dataFineValidita=" + dataFineValidita
				+ ", dataInserimento=" + dataInserimento + ", dataAggiornamento=" + dataAggiornamento + ", idUtenteIns="
				+ idUtenteIns + ", idUtenteAgg=" + idUtenteAgg + ", nomeUtente=" + nomeUtente + ", cognomeUtente="
				+ cognomeUtente + ", nomeUtenteAgg=" + nomeUtenteAgg + ", cognomeUtenteAgg=" + cognomeUtenteAgg
				+ ", allegatiPresenti=" + allegatiPresenti + ", nuoviAllegati=" + nuoviAllegati + "]";
	}
	


}
