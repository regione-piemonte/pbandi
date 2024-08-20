/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.dao;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import it.csi.pbandi.pbgestfinbo.exception.DaoException;
import it.csi.pbandi.pbgestfinbo.integration.vo.search.VisualizzaStoricoPFVO;
import it.csi.pbandi.pbgestfinbo.integration.vo.search.VisualizzaStoricoVO;

public interface VisualizzaStoricoDAO {

	List<VisualizzaStoricoVO> getVistaStoricoDomanda(Long idSoggetto, Long idDomanda,  HttpServletRequest req)  throws DaoException;
	List<VisualizzaStoricoVO> getVistaStoricoProgetto(Long idSoggetto, Long idProgetto,  HttpServletRequest req)  throws DaoException;
	List<VisualizzaStoricoPFVO> getVistaStoricoDomandaPF(Long idSoggetto, Long idDomanda, HttpServletRequest req)
			throws DaoException;
	List<VisualizzaStoricoPFVO> getVistaStoricoProgettoPF(Long idSoggetto, Long idProgetto, HttpServletRequest req)
			throws DaoException;


}
