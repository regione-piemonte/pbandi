/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.vo;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;

public class VisualizzaStoricoEscussioneVO {
	
	private Date dtRichEscussione;
	private String numProtocolloRich;
	private String descTipoEscussione;
	private String descStatoEscussione;
	private Date dtInizioValidita;
	private Date dtNotifica;
	private String numProtocolloNotif;
	private BigDecimal impApprovato;
	private BigDecimal impRichiesto;
	private String causaleBonifico;
	private String ibanBonifico;
	private String descBanca;
	private String note;
	private String nome;
	private String cognome;
	//solo per insert
	private int idProgetto;
	private int idUtente;
	private int idEscussione;
	private int idSoggProgBancaBen;
	private int idBanca;
	private int ProgrSoggettoProgetto;
	private ArrayList<Integer> listaAllegatiPresenti;
	//per gli errori
	private String Messaggio;
	
	
	public VisualizzaStoricoEscussioneVO() {
	}


	


	public VisualizzaStoricoEscussioneVO(Date dtRichEscussione, String numProtocolloRich, String descTipoEscussione,
			String descStatoEscussione, Date dtInizioValidita, Date dtNotifica, String numProtocolloNotif,
			BigDecimal impApprovato, BigDecimal impRichiesto, String causaleBonifico, String ibanBonifico,
			String descBanca, String note, String nome, String cognome, int idProgetto, int idUtente, int idEscussione,
			int idSoggProgBancaBen, int idBanca, int progrSoggettoProgetto, ArrayList<Integer> listaAllegatiPresenti,
			String messaggio) {
		this.dtRichEscussione = dtRichEscussione;
		this.numProtocolloRich = numProtocolloRich;
		this.descTipoEscussione = descTipoEscussione;
		this.descStatoEscussione = descStatoEscussione;
		this.dtInizioValidita = dtInizioValidita;
		this.dtNotifica = dtNotifica;
		this.numProtocolloNotif = numProtocolloNotif;
		this.impApprovato = impApprovato;
		this.impRichiesto = impRichiesto;
		this.causaleBonifico = causaleBonifico;
		this.ibanBonifico = ibanBonifico;
		this.descBanca = descBanca;
		this.note = note;
		this.nome = nome;
		this.cognome = cognome;
		this.idProgetto = idProgetto;
		this.idUtente = idUtente;
		this.idEscussione = idEscussione;
		this.idSoggProgBancaBen = idSoggProgBancaBen;
		this.idBanca = idBanca;
		ProgrSoggettoProgetto = progrSoggettoProgetto;
		this.listaAllegatiPresenti = listaAllegatiPresenti;
		Messaggio = messaggio;
	}





	public Date getDtRichEscussione() {
		return dtRichEscussione;
	}


	public void setDtRichEscussione(Date dtRichEscussione) {
		this.dtRichEscussione = dtRichEscussione;
	}


	public String getNumProtocolloRich() {
		return numProtocolloRich;
	}


	public void setNumProtocolloRich(String numProtocolloRich) {
		this.numProtocolloRich = numProtocolloRich;
	}


	public String getDescTipoEscussione() {
		return descTipoEscussione;
	}


	public void setDescTipoEscussione(String descTipoEscussione) {
		this.descTipoEscussione = descTipoEscussione;
	}


	public String getDescStatoEscussione() {
		return descStatoEscussione;
	}


	public void setDescStatoEscussione(String descStatoEscussione) {
		this.descStatoEscussione = descStatoEscussione;
	}


	public Date getDtInizioValidita() {
		return dtInizioValidita;
	}


	public void setDtInizioValidita(Date dtInizioValidita) {
		this.dtInizioValidita = dtInizioValidita;
	}


	public Date getDtNotifica() {
		return dtNotifica;
	}


	public void setDtNotifica(Date dtNotifica) {
		this.dtNotifica = dtNotifica;
	}


	public String getNumProtocolloNotif() {
		return numProtocolloNotif;
	}


	public void setNumProtocolloNotif(String numProtocolloNotif) {
		this.numProtocolloNotif = numProtocolloNotif;
	}


	public BigDecimal getImpApprovato() {
		return impApprovato;
	}


	public void setImpApprovato(BigDecimal impApprovato) {
		this.impApprovato = impApprovato;
	}


	public BigDecimal getImpRichiesto() {
		return impRichiesto;
	}


	public void setImpRichiesto(BigDecimal impRichiesto) {
		this.impRichiesto = impRichiesto;
	}


	public String getCausaleBonifico() {
		return causaleBonifico;
	}


	public void setCausaleBonifico(String causaleBonifico) {
		this.causaleBonifico = causaleBonifico;
	}


	public String getIbanBonifico() {
		return ibanBonifico;
	}


	public void setIbanBonifico(String ibanBonifico) {
		this.ibanBonifico = ibanBonifico;
	}


	public String getDescBanca() {
		return descBanca;
	}


	public void setDescBanca(String descBanca) {
		this.descBanca = descBanca;
	}


	public String getNote() {
		return note;
	}


	public void setNote(String note) {
		this.note = note;
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
	

	public int getIdProgetto() {
		return idProgetto;
	}


	public void setIdProgetto(int idProgetto) {
		this.idProgetto = idProgetto;
	}
	

	public int getIdUtente() {
		return idUtente;
	}


	public void setIdUtente(int idUtente) {
		this.idUtente = idUtente;
	}


	public int getIdEscussione() {
		return idEscussione;
	}


	public void setIdEscussione(int idEscussione) {
		this.idEscussione = idEscussione;
	}


	public String getMessaggio() {
		return Messaggio;
	}


	public void setMessaggio(String messaggio) {
		Messaggio = messaggio;
	}



	public int getIdSoggProgBancaBen() {
		return idSoggProgBancaBen;
	}



	public void setIdSoggProgBancaBen(int idSoggProgBancaBen) {
		this.idSoggProgBancaBen = idSoggProgBancaBen;
	}






	public ArrayList<Integer> getListaAllegatiPresenti() {
		return listaAllegatiPresenti;
	}





	public void setListaAllegatiPresenti(ArrayList<Integer> listaAllegatiPresenti) {
		this.listaAllegatiPresenti = listaAllegatiPresenti;
	}





	public int getIdBanca() {
		return idBanca;
	}






	public void setIdBanca(int idBanca) {
		this.idBanca = idBanca;
	}






	public int getProgrSoggettoProgetto() {
		return ProgrSoggettoProgetto;
	}




	public void setProgrSoggettoProgetto(int progrSoggettoProgetto) {
		ProgrSoggettoProgetto = progrSoggettoProgetto;
	}
	
	
}	