/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservwelfare.integration.dao;

import java.util.HashMap;

public interface TrasmissioneDocumentazioneIntegrativaDocSpesaDAO {

	HashMap<String, String> getInfoRichiestaIntegrazione(Integer identificativoRichiestaDiIntegrazione);

	Integer getIdEntitaDichSpesa();

	HashMap<String, String> getInfoSoggettoProgetto(String idDichiarazioneSpesa);

	Integer getIdFolderRichiestaIntegrazioneSpesa(Integer idFolderPadre, String nomeFolder, String idSoggetto, String idProgetto);

	Integer getIdFolderProgettoPadre(String idSoggetto, String idProgetto, String nomeFolder);

	Integer getIdFolderSoggettoPadre(String idSoggetto);

	Integer getIdFolder();

	void insertFolder(Integer idFolder, Integer idFolderPadre, String nomeFolder, String idSoggetto, String idProgetto);

	Integer getIdDocIndex();

	void insertDocumentoIndex(Integer idDocIndex, Integer idFolder, String nomeDocumento, String idProgetto,
			String idSoggetto);

	Integer getIdFile();

	void insertFile(Integer idFile, Integer idFolder, Integer idDocumentoIndex, String nomeDocumento, int sizeFile);

	void insertFileEntita(Integer idFile, Integer idEntitaDichSpesa, Integer idTarget, String idProgetto);
	
	void chiusuraRichiestaIntegrazione(Integer identificativoRichiestaDiIntegrazione);
	
	HashMap<String, String> getTemplateNotifica();
	
	void insertNotificaProcesso(String idProgetto, String compMessage, String idTemplateNotifica);

}
