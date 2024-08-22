/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservwelfare.integration.dao;

import java.util.HashMap;

public interface TrasmissioneDocumentazioniPerControdeduzioniDAO {

	Integer getInfoDocumentazioneControdeduzioni(Integer identificativoControdeduzione);

	Integer getIdEntitaControdeduzione();

	HashMap<String, String> getInfoSoggettoProgetto(Integer identificativoControdeduzione);

	Integer getIdFolderControdeduzioni(Integer idFolderPadre, String nomeFolder, String idSoggetto, String idProgetto);

	Integer getIdFolderProgettoPadre(String idSoggetto, String idProgetto);

	Integer getIdFolder();

	Integer getIdFolderSoggettoPadre(String idSoggetto);

	void insertFolder(Integer idFolder, Integer idFolderPadre, String nomeFolder, String idSoggetto, String idProgetto);

	Integer getIdDocIndex();

	void insertDocumentoIndex(Integer idDocIndex, Integer idFolder, String nomeDocumento, String idProgetto,
			String idSoggetto);

	void insertFile(Integer idFolder, Integer idDocumentoIndex, String nomeDocumento, int sizeFile);

	Integer getIdFile(Integer idFolder, Integer idDocumentoIndex, String nomeDocumento);

	void insertFileEntita(Integer idFile, Integer idEntitaControdeduz, Integer idTarget, String idProgetto);

	void updateStatoAttivita(Integer identificativoControdeduzione);
}
