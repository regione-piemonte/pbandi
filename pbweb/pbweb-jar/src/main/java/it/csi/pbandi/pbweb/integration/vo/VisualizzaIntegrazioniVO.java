/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.integration.vo;


import java.util.Date;

public class VisualizzaIntegrazioniVO { 
	
	private int numeroRevoca;
	private Date dtRich;
	private Date dtNotifica;
	private Date dtScadenza;
	private Date dtInvio;
	private int idStatoRich;
	private int idRichIntegrazione;
	
	//per gli allegati:
	
	private String flagEntita;
	private int idDocumentoIndex;
	private String nomeFile;
	private int idFileEntita;
	private String desc;
	
	//per l'update:
	
	private int idUtente;
	
	
	public VisualizzaIntegrazioniVO() {
		super();
		//TODO Auto-generated constructor stub
	}


	public VisualizzaIntegrazioniVO(int numeroRevoca, Date dtRich, Date dtNotifica, Date dtScadenza, Date dtInvio,
			int idStatoRich, int idRichIntegrazione, String nomeFile, int idUtente, String flagEntita, int idDocumentoIndex, int idFileEntita, String desc) {
		super();
		this.numeroRevoca = numeroRevoca;
		this.dtRich = dtRich;
		this.dtNotifica = dtNotifica;
		this.dtScadenza = dtScadenza;
		this.dtInvio = dtInvio;
		this.idStatoRich = idStatoRich;
		this.idRichIntegrazione = idRichIntegrazione;
		this.idUtente = idUtente;
		this.flagEntita = flagEntita;
		this.idDocumentoIndex = idDocumentoIndex;
		this.nomeFile = nomeFile;
		this.idFileEntita = idFileEntita;
		this.desc = desc;
		
	}


	public int getNumeroRevoca() {
		return numeroRevoca;
	}


	public void setNumeroRevoca(int numeroRevoca) {
		this.numeroRevoca = numeroRevoca;
	}


	public Date getDtRich() {
		return dtRich;
	}


	public String getNomeFile() {
		return nomeFile;
	}


	public void setNomeFile(String nomeFile) {
		this.nomeFile = nomeFile;
	}


	public int getIdFileEntita() {
		return idFileEntita;
	}


	public void setIdFileEntita(int idFileEntita) {
		this.idFileEntita = idFileEntita;
	}


	public String getDesc() {
		return desc;
	}


	public void setDesc(String desc) {
		this.desc = desc;
	}


	public void setDtRich(Date dtRich) {
		this.dtRich = dtRich;
	}


	public Date getDtNotifica() {
		return dtNotifica;
	}


	public void setDtNotifica(Date dtNotifica) {
		this.dtNotifica = dtNotifica;
	}


	public Date getDtScadenza() {
		return dtScadenza;
	}


	public void setDtScadenza(Date dtScadenza) {
		this.dtScadenza = dtScadenza;
	}


	public Date getDtInvio() {
		return dtInvio;
	}


	public void setDtInvio(Date dtInvio) {
		this.dtInvio = dtInvio;
	}


	public int getIdStatoRich() {
		return idStatoRich;
	}


	public void setIdStatoRich(int idStatoRich) {
		this.idStatoRich = idStatoRich;
	}


	public int getIdRichIntegrazione() {
		return idRichIntegrazione;
	}


	public void setIdRichIntegrazione(int idRichIntegrazione) {
		this.idRichIntegrazione = idRichIntegrazione;
	}





	public String getFlagEntita() {
		return flagEntita;
	}


	public void setFlagEntita(String flagEntita) {
		this.flagEntita = flagEntita;
	}


	public int getIdDocumentoIndex() {
		return idDocumentoIndex;
	}


	public void setIdDocumentoIndex(int idDocumentoIndex) {
		this.idDocumentoIndex = idDocumentoIndex;
	}


	public int getIdUtente() {
		return idUtente;
	}


	public void setIdUtente(int idUtente) {
		this.idUtente = idUtente;
	}
	
	
	
	
}	