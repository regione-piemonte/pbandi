/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservwelfare.integration.dao;

import java.util.HashMap;

public interface TrasmissioneDocumentazioneContestazioniDAO {
	
	Integer getInfoDocContestazioni(Integer identificativoContestazione);
	
	Integer getIdEntitaContestazione();
	
	HashMap<String, String> getInfoSoggettoProgetto(Integer identificativoContestazione);

	Integer getIdFolderContestazioni(Integer idFolderPadre, String nomeFolder, String idSoggetto, String idProgetto);

	Integer getIdFolderProgettoPadre(String idSoggetto, String idProgetto);
	
	Integer getIdFolder();
	
	Integer getIdFolderSoggettoPadre(String idSoggetto);
	
	void insertFolder(Integer idFolder, Integer idFolderPadre, String nomeFolder, String idSoggetto, String idProgetto);
	
	Integer getIdDocIndex();
	
	void insertDocumentoIndex(Integer idDocIndex, Integer idFolder, String nomeDocumento, String idProgetto,
			String idSoggetto);
	
	Integer getIdFile();
	
	void insertFile(Integer idFile, Integer idFolder, Integer idDocumentoIndex, String nomeDocumento, int sizeFile);
	
	void insertFileEntita(Integer idFile, Integer idEntitaContestazione, Integer idTarget, String idProgetto);
	
	void updateStatoAttivita(Integer identificativoContestazione);

}
