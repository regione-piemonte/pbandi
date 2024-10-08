/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.integration.dao;

import it.csi.pbandi.pbservizit.dto.EsitoOperazioneDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.checklisthtml.FileDTO;

public interface ChecklistCommonDAO {

	EsitoOperazioneDTO allegaFilesChecklist(Long idUtente, String idIride, Long idProgetto, String codTipoDocumento,
			String idDocumentoIndexChecklist, FileDTO[] allegati) throws Exception;
}
