/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebrce.integration.dao;

import it.csi.csi.wrapper.UnrecoverableException;
import it.csi.pbandi.pbservizit.pbandisrv.exception.FormalParameterException;
import it.csi.pbandi.pbwebrce.dto.quadroprevisionale.EsitoFindQuadroPrevisionaleDTO;
import it.csi.pbandi.pbwebrce.dto.quadroprevisionale.EsitoSalvaQuadroPrevisionaleDTO;
import it.csi.pbandi.pbwebrce.dto.quadroprevisionale.QuadroPrevisionaleDTO;

public interface QuadroPrevisionaleDAO {

	EsitoFindQuadroPrevisionaleDTO findQuadroPrevisionale(Long idUtente, String idIride, Long idProgetto) throws Exception;

	EsitoSalvaQuadroPrevisionaleDTO salvaQuadroPrevisionale(Long idUtente, String idIride,
			QuadroPrevisionaleDTO quadroPrevisionale, Long idProgetto) throws UnrecoverableException, FormalParameterException;

}
