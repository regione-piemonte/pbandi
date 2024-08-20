/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.dto.profilazione;

import java.sql.Date;

public class StoricoAzioneRecuperoBancaDTO {
	
	private Date dataInserimento;
	private Date dataAggiornamento; 
	private Date dataAzione; 
	private String nome;
	private String cognome;
	private String descrizioneAttivita;
	private String note; 
	private String numProtocollo; 
	private Long idAzioneRecuperoBanca; 
	

	public Long getIdAzioneRecuperoBanca() {
		return idAzioneRecuperoBanca;
	}
	public void setIdAzioneRecuperoBanca(Long idAzioneRecuperoBanca) {
		this.idAzioneRecuperoBanca = idAzioneRecuperoBanca;
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
	public Date getDataAzione() {
		return dataAzione;
	}
	public void setDataAzione(Date dataAzione) {
		this.dataAzione = dataAzione;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getCognome() {
		return cognome;
	}
	public void setCognome(String cognome) {
		this.cognome = cognome;
	}
	public String getDescrizioneAttivita() {
		return descrizioneAttivita;
	}
	public void setDescrizioneAttivita(String descrizioneAttivita) {
		this.descrizioneAttivita = descrizioneAttivita;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public String getNumProtocollo() {
		return numProtocollo;
	}
	public void setNumProtocollo(String numProtocollo) {
		this.numProtocollo = numProtocollo;
	}
	
	
	
	
}
