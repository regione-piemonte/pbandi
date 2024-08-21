/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.integration.dao;

import java.security.InvalidParameterException;

import it.csi.pbandi.pbservizit.dto.archivioFile.FileDTO;
import it.csi.pbandi.pbservizit.integration.vo.PbandiTDocumentoIndexVO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.archivio.UserSpaceDTO;

public interface GestioneFileDAO {

	UserSpaceDTO spazioDisco(long idSoggettoBeneficiario) throws InvalidParameterException, Exception;
	
	PbandiTDocumentoIndexVO salvaFileCL(Long idUtente, FileDTO fileDTO, String codTipoDocIndex ) throws InvalidParameterException, Exception;
}
