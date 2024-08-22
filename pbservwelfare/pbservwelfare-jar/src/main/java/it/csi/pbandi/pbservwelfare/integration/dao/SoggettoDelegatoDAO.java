/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservwelfare.integration.dao;

import java.util.HashMap;

public interface SoggettoDelegatoDAO {
	
	HashMap<String, String> getSoggettoBeneficiario(String numeroDomanda);
	
	HashMap<String, String> isSoggettoPresente(String idSoggetto, String idProgetto, String codiceFiscale);
	
	Integer verificaEsistenzaIdentificativoSoggetto(String codiceFiscale);
	
	void insertSoggetto(String codiceFiscale);
	
	Integer verificaEsistenzaIdentificativoPersonaFisica(Integer idSoggettoDelegato);
	
	void insertPersonaFisica(Integer idSoggettoDelegato, String cognome, String nome, String dataNascita, String codiceComuneNascita, String descrizioneComuneEsteroNascita);
	
	void insertSoggettiCorrelati(Integer idSoggettoDelegato, String idSoggetto);
	
	Integer getProgrSoggettiCorrelati(Integer idSoggettoDelegato, String idSoggetto);
	
	void insertSoggettoProgetto(Integer idSoggettoDelegato, String idProgetto, Integer idPersonaFisica);
	
	Integer getProgrSoggettoProgetto(Integer idSoggettoDelegato, String idProgetto, Integer idPersonaFisica);
	
	void insertSoggProgSoggCorrel(Integer progrSoggettoProgettoDelegato, Integer progrSoggettiCorrelati);
	
	void updatePersonaFisica(Integer idSoggettoDelegato, String cognome, String nome, String dataNascita, String codiceComuneNascita, String descrizioneComuneEsteroNascita);
	
	HashMap<String, Integer> getIdIndirizzoERecapiti(String progrSoggProgettoDelegato);
	
	void updateIndirizzo(Integer idIndirizzo, String indirizzo, String cap, String codiceComuneNascita, String descrizioneComuneEsteroNascita);

	int createIndirizzo(String indirizzo, String cap, String codiceComuneNascita, String descrizioneComuneEsteroNascita, String progrSoggProgDelegato);

	void updateIdIndirizzo(String progrSoggettoProgetto, Integer idIndirizzo);
	void updateRecapiti(Integer idRecapiti, String mail, String telefono);
	int createRecapiti(String mail, String telefono, String progrSoggProgDelegato);

	void updateIdRecapiti(String progrSoggettoProgetto, Integer idRecapiti);
}
