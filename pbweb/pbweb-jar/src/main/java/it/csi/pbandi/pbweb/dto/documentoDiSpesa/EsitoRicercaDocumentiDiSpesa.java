/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.dto.documentoDiSpesa;

import java.util.ArrayList;
import java.util.List;

import it.csi.pbandi.pbweb.dto.ElencoDocumentiSpesaItem;

public class EsitoRicercaDocumentiDiSpesa implements java.io.Serializable {

	static final long serialVersionUID = 1;

	private java.lang.Boolean esito = null;
	
	private java.lang.String messaggio = null;
	
	private ArrayList<ElencoDocumentiSpesaItem> documenti= null;

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

	public ArrayList<ElencoDocumentiSpesaItem> getDocumenti() {
		return documenti;
	}

	public void setDocumenti(ArrayList<ElencoDocumentiSpesaItem> documenti) {
		this.documenti = documenti;
	}

}
