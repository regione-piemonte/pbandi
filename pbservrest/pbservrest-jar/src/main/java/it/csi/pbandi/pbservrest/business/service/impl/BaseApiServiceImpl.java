/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservrest.business.service.impl;

import java.util.ArrayList;
import java.util.List;

import it.csi.pbandi.pbservrest.dto.Esito;
import it.csi.pbandi.pbservrest.util.Constants;


public class BaseApiServiceImpl{

	/* - setDatiBeneficiario */
	public List<Esito> setErrore007(){
		List<Esito> dati = new ArrayList<>();
		Esito item = new Esito();
		item.setCodiceErrore(007);
		item.setDescErrore(Constants.MESSAGGI.ERRORE.CODICE_FISCALE_ERRATO);
		item.setEsitoServizio(Constants.ESITO.KO);
		dati.add(item);
		return dati;
	}
	
	/* - setDatiBeneficiario */
	public List<Esito> setErrore005(){
		List<Esito> dati = new ArrayList<>();
		Esito item = new Esito();
		item.setCodiceErrore(005);
		item.setDescErrore(Constants.MESSAGGI.ERRORE.PARAMETRI_INVALIDI);
		item.setEsitoServizio(Constants.ESITO.KO);
		dati.add(item);
		return dati;
	}
	
	/* - setDatiBeneficiario */
	public List<Esito> setEsitoOK(){
		List<Esito> dati = new ArrayList<>();
		Esito item = new Esito();
		item.setEsitoServizio(Constants.ESITO.OK);
		dati.add(item);
		return dati;
	}
	
	/* - setDatiBeneficiario */
	public List<Esito> setErrore006(){
		List<Esito> dati = new ArrayList<>();
		Esito item = new Esito();
		item.setCodiceErrore(006);
		item.setDescErrore(Constants.MESSAGGI.ERRORE.ERRORE_GENERICO);
		item.setEsitoServizio(Constants.ESITO.KO);
		dati.add(item);
		return dati;
	}
	
	/* - setDatiBeneficiario */
	public List<Esito> setDatiEsistenti(){
		List<Esito> dati = new ArrayList<>();
		Esito item = new Esito();
		item.setCodiceErrore(20);
		item.setDescErrore(Constants.MESSAGGI.ERRORE.DATI_ESISTENTI);
		item.setEsitoServizio(Constants.ESITO.KO);
		dati.add(item);
		return dati;
	}
	
} 
