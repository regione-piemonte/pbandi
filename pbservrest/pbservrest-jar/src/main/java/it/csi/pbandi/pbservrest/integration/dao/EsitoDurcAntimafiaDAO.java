/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservrest.integration.dao;

import java.util.List;

import it.csi.pbandi.pbservrest.dto.EsitoDurcAntimafia;
import it.csi.pbandi.pbservrest.model.EsitoDurcAntimafiaInRest;
import it.csi.pbandi.pbservrest.model.IstruttoreAsfTmp;
import it.csi.pbandi.pbservrest.model.SoggettoDomandaTmp;
import it.csi.pbandi.pbservrest.model.SoggettoRichiestaTmp;

public interface EsitoDurcAntimafiaDAO {
	
	/* test connessione al database */ 
	public boolean testConnection();

	List<SoggettoDomandaTmp> getEsitoDurcAntimafia(String codFiscaleSoggetto, String numeroDomanda);
	int getIdSoggettoDurc(Integer idSoggetto);
	int getIdSoggettoAntimafia(Integer idDomanda);
	int getIdSoggettoAntimafiaBDNA(Integer idDomanda);
    int getIdSoggettoDsan(Integer idDomanda);
    
	List<EsitoDurcAntimafia> getDatiDurc(Integer idSoggettoDurc, Integer idDomanda, String numeroDomanda);
	List<EsitoDurcAntimafia> getDatiDsan(Integer idSoggettoDsan, Integer idDomanda, String numeroDomanda);
	List<EsitoDurcAntimafia> getDatiAntimafia(Integer idSoggettoAntimafia, Integer idDomanda, String numeroDomanda);
	List<EsitoDurcAntimafia> getDatiAntimafia_BDNA(Integer idSoggettoAntimafia, Integer idDomanda, String numeroDomanda);
	List<EsitoDurcAntimafia> getDatiRichiestiDurc(Integer idDomanda, String nnumeroDomanda);
	List<EsitoDurcAntimafia> getDatiRichiestiDsan(Integer idDomanda, String numeroDomanda);
	List<EsitoDurcAntimafia> getDatiRichiestiAntimafia(Integer idDomanda, String numeroDomanda);
	
	List<EsitoDurcAntimafia> getEsitoDurcAntimafiaDsan(String codFiscaleSoggetto, String numeroDomanda, String tipoRichiesta, String modalitaRichiesta, int codBando);
	public List<SoggettoRichiestaTmp> getDomandaRichiesta(Integer idSoggetto);
	public List<IstruttoreAsfTmp> getDatiIstruttoreASF(String cfIstruttore);
	public boolean recordExists(Integer idSoggetto);
	public Integer insertSoggetto(EsitoDurcAntimafiaInRest edain);
	public boolean insertRichiesta(EsitoDurcAntimafiaInRest datiInputRest, Integer idDomanda, Integer idIstruttore);
	public boolean insertUtente(EsitoDurcAntimafiaInRest datiInputRest, Integer id);
	public boolean recordBdnaExist(Integer idDomanda);
	public List<SoggettoRichiestaTmp> getDomandaRichiestaAntimafia(Integer idomanda);
	public List<SoggettoRichiestaTmp> getDomandaRichiestaDsan(Integer id_domanda);
	public List<SoggettoRichiestaTmp> getRichiestaDurc(Integer idSoggetto);
}