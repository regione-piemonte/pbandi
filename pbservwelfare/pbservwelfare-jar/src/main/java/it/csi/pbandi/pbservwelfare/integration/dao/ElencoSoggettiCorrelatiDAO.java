/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservwelfare.integration.dao;

import java.util.HashMap;
import java.util.List;

import it.csi.pbandi.pbservwelfare.dto.IdentificativoBeneficiario;
import it.csi.pbandi.pbservwelfare.dto.IdentificativoSoggettoCorrelato;
import it.csi.pbandi.pbservwelfare.dto.SoggettiCorrelati;

public interface ElencoSoggettiCorrelatiDAO {
	
	List<IdentificativoBeneficiario> getIdentificativoBeneficiario(String numeroDomanda);
	
	List<IdentificativoSoggettoCorrelato> getIdentificativoSoggettoCorrelato(Integer idSoggettoBeneficiario);
	
	List<SoggettiCorrelati> getSoggettiCorrelati(Integer progrSoggettiCorrelati, Integer idProgetto);
	
	HashMap<String, String> getInfoAnagrafiche(Integer idPersonaFisica);
	
	HashMap<String, String> getInfoComune(String idComuneItalianoNascita);
	
	HashMap<String, String> getInfoComuneEstero(String idComuneEsteroNascita);
	
	HashMap<String, String> getInfoRecapiti(Integer idRecapitiPersonaFisica);
	
	HashMap<String, String> getInfoIndirizzo(Integer idIndirizzoPersonaFisica);

}
