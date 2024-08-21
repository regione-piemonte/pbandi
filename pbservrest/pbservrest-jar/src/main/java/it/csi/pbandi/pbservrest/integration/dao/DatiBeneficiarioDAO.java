/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservrest.integration.dao;

import java.util.Date;
import java.util.List;

import it.csi.pbandi.pbservrest.dto.DatiBeneficiario;
import it.csi.pbandi.pbservrest.model.SoggettoEProgressivoTmp;

public interface DatiBeneficiarioDAO {
	
	// q0: test connessione al database 
	public boolean testConnection();
	
	// q1: verifico cf presente a db ed associato a numero domanda
	Boolean isCorrect(String codiceFiscale, String numeroDomanda);
	
	// q2: recupero soggetto e progressivo by (numeroDomanda)
	List<SoggettoEProgressivoTmp> getSoggettoEProg(String numeroDomanda);
	
	// q3: recupero (cod_dimensione_impresa e dt_valutazione_esito)
	List<DatiBeneficiario> getDimensioneEValutazione(String codiceFiscale, String numeroDomanda);
	
	// q4:
	List<DatiBeneficiario> getDscrizioni(int prog);
	
	// q5:
	List<DatiBeneficiario> getProviderERating(String idDataBen, String numeroDomanda);
	
	// q6:
	List<DatiBeneficiario> getDescrClasseRischio(String idDataBen, String numeroDomanda);
	
	// q7:
	List<DatiBeneficiario> getDescrizioniSoggettoFisico(int prog, String idDataBeneficiario);
	
	// q8:
	void insertDimensioneEValutazione(int idSoggetto, int idDimImpresa, Date dtValutazioneEsito);
	
	// q9:
	int getIdSoggetto(String codiceFIscale);
	
	// q10:
	void updateEnteGiuridico(int idEnteGiuridico, int idSoggetto);
	
	// q11:
	int getIdRating(String codRating, int idProvider);
	
	// q12:
	int getVecchioIdRating(int idSoggetto);
	
	// q13:
	boolean idSoggettoExist(int idSoggetto);
	
	// q14:
	int getIdDimImpresa(String codDimImpresa);
	
	// q15:
	void updateDtFineValidita(int idSoggetto);
	
	// q16:
	void insertRatingESogg(int idRating, int idSoggetto, Date dtClassificazione);
	
	// q17:
	int getIdClasseRischio(String descClasseRischio);
	
	// q18:
	int getVecchioIdClasseRischio(int idSoggetto);
	
	// q19:
	void insertClasseESogg(int idSoggetto, int idClasseRischio);
	
	// q20:
	void updateDtFineValiditaClasse(int idSoggetto);
	
	// q21:
	int getIdEnteGiuridico(int idSoggetto);
	
	// q22:
	int getIdProvider(String descrizioneProvider);
	
	// q23:
	boolean trovatoRecord(int idDimImpresa, Date dtValutazioneEsito);
	
	// q24: recupero idIndirizzo
	int getIdIndirizzo(int progr);
	
	// q25: recupero campi indirizzo completo
	List<DatiBeneficiario> getIndirizzoCompleto(int idIndirizzo, String idDataBen);
	
}