/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebbo.integration.dao;

import java.security.InvalidParameterException;
import java.util.List;

import it.csi.pbandi.pbservizit.dto.archivioFile.FileDTO;
import it.csi.pbandi.pbservizit.security.UserInfoSec;
import it.csi.pbandi.pbwebbo.dto.CodiceDescrizioneDTO;
import it.csi.pbandi.pbwebbo.dto.utils.ResponseCodeMessage;
import it.csi.pbandi.pbwebbo.integration.vo.BoStorageDocumentoIndexDTO;
import it.csi.pbandi.pbwebbo.integration.vo.ProgettoSuggestVO;

public interface BoStorageDAO {

	List<CodiceDescrizioneDTO> getTipiDocIndex(UserInfoSec userInfo) throws InvalidParameterException, Exception;

	List<ProgettoSuggestVO> getProgettiByDesc(String descrizione, UserInfoSec userInfo)
			throws InvalidParameterException, Exception;

	List<BoStorageDocumentoIndexDTO> ricercaDocumenti(String nomeFile, Long idTipoDocumentoIndex, Long idProgetto,
			UserInfoSec userInfo) throws InvalidParameterException, Exception;

	ResponseCodeMessage sostituisciDocumento(FileDTO file, String descrizioneBreveTipoDocIndex, Long idDocumentoIndex,
			Boolean flagFirmato, Boolean flagMarcato, Boolean flagFirmaAutografa, UserInfoSec userInfo)
			throws InvalidParameterException, Exception;

}
