/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.vo;


import java.util.Date;

public class VisualizzaAzioniRecuperoBancaVO {
	
	
	private String descrizioneAttivita; 
	private Date dataAzione; 
	private String nome;
	private String cognome;
	private String numProtocollo;
	private String note;
	
	
	public VisualizzaAzioniRecuperoBancaVO() {
		super();
		//TODO Auto-generated constructor stub
	}


	public VisualizzaAzioniRecuperoBancaVO(String azione, Date dataAzione, String nome, String cognome, String numProtocollo,
			String note) {
		super();
		this.descrizioneAttivita = azione;
		this.dataAzione = dataAzione;
		this.nome = nome;
		this.cognome = cognome;
		this.numProtocollo = numProtocollo;
		this.note = note;
	}


	public String getDescrizioneAttivita() {
		return descrizioneAttivita;
	}


	public void setDescrizioneAttivita(String descrizioneAttivita) {
		this.descrizioneAttivita = descrizioneAttivita;
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


	public String getNumProtocollo() {
		return numProtocollo;
	}


	public void setNumProtocollo(String numProtocollo) {
		this.numProtocollo = numProtocollo;
	}


	public String getNote() {
		return note;
	}


	public void setNote(String note) {
		this.note = note;
	}
	
	
}