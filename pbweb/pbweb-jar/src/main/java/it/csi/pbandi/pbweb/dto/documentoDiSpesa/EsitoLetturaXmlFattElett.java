/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.dto.documentoDiSpesa;

import it.csi.pbandi.pbweb.dto.FornitoreFormDTO;

import java.io.Serializable;

public class EsitoLetturaXmlFattElett implements Serializable {

	private DatiFatturaElettronica datiFatturaElettronica;
	private FornitoreFormDTO fornitoreDb;
	private String esito;
	
	public String getEsito() {
		return esito;
	}
	public void setEsito(String esito) {
		this.esito = esito;
	}
	public DatiFatturaElettronica getDatiFatturaElettronica() {
		return datiFatturaElettronica;
	}
	public void setDatiFatturaElettronica(DatiFatturaElettronica datiFatturaElettronica) {
		this.datiFatturaElettronica = datiFatturaElettronica;
	}
	public FornitoreFormDTO getFornitoreDb() {
		return fornitoreDb;
	}
	public void setFornitoreDb(FornitoreFormDTO fornitoreDb) {
		this.fornitoreDb = fornitoreDb;
	}

}
