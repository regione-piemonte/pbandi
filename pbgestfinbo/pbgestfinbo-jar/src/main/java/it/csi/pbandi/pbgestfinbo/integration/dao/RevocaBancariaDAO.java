/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.dao;


import java.util.ArrayList;
import java.util.List;

import it.csi.pbandi.pbgestfinbo.dto.profilazione.RevocaBancariaDTO;
import it.csi.pbandi.pbgestfinbo.dto.profilazione.StoricoRevocaDTO;
import it.csi.pbandi.pbgestfinbo.exception.DaoException;

public interface RevocaBancariaDAO {
	Boolean salvaRevoca(RevocaBancariaDTO revocaBancariaDTO, Long idUtente, Long idProgetto, int idModalitaAgev);
	List<RevocaBancariaDTO> getListRevoche(Long idUtente, Long idProgetto, int idModalitaAgev) throws DaoException;
	ArrayList<StoricoRevocaDTO>getStoricoRevoca(Long idUtente, Long idProgetto, int idModalitaAgev) throws DaoException;
	RevocaBancariaDTO getRevoca(Long idUtente, Long idProgetto, int idModalitaAgev);
	
	
	
}
