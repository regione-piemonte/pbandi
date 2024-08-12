package it.csi.pbandi.pbweb.dto;

import java.util.ArrayList;

public class EsitoRicercaFornitori implements java.io.Serializable {

	static final long serialVersionUID = 1;

	private java.lang.Boolean esito = null;
	
	private java.lang.String messaggio = null;
	
	private ArrayList<Fornitore> fornitori= null;

	public java.lang.Boolean getEsito() {
		return esito;
	}

	public void setEsito(java.lang.Boolean esito) {
		this.esito = esito;
	}

	public java.lang.String getMessaggio() {
		return messaggio;
	}

	public void setMessaggio(java.lang.String messaggio) {
		this.messaggio = messaggio;
	}

	public ArrayList<Fornitore> getFornitori() {
		return fornitori;
	}

	public void setFornitori(ArrayList<Fornitore> fornitori) {
		this.fornitori = fornitori;
	}

}
