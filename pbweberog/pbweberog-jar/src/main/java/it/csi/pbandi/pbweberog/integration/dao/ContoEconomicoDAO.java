/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweberog.integration.dao;

import it.csi.pbandi.pbweberog.dto.contoeconomico.ContoEconomicoDTO;

public interface ContoEconomicoDAO {

	ContoEconomicoDTO findDatiContoEconomico(Long idUtente, String idIride, Long idProgetto) throws Exception;

}
