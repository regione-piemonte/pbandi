/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebrce.integration.dao;

import java.util.List;

import it.csi.pbandi.pbservizit.pbandisrv.exception.FormalParameterException;
import it.csi.pbandi.pbservizit.security.UserInfoSec;
import it.csi.pbandi.pbwebrce.dto.EsitoOperazioni;
import it.csi.pbandi.pbwebrce.dto.affidamenti.DocumentoAllegato;

public interface ArchivioFileDAO {

	List<DocumentoAllegato> findAllegati(Long idAppalto) throws Exception;

	EsitoOperazioni dissociateFile(Long idUtente, String idIride, Long idDocumentoIndex, Long idEntita, Long idTarget, Long idProgetto2) throws FormalParameterException, Exception;

	EsitoOperazioni associateFile(UserInfoSec userInfo, Long idTarget, Long idEntita, long long1, Long idProgetto) throws Exception;

	DocumentoAllegato[] getChecklistAssociatedAffidamento(UserInfoSec userInfo, Long idAppalto);

	
}
