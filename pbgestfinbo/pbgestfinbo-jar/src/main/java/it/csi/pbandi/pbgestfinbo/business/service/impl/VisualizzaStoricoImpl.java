/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.business.service.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.csi.pbandi.pbgestfinbo.business.service.VisualizzaStorico;
import it.csi.pbandi.pbgestfinbo.exception.DaoException;
import it.csi.pbandi.pbgestfinbo.integration.dao.VisualizzaStoricoDAO;
import it.csi.pbandi.pbgestfinbo.integration.vo.search.VisualizzaStoricoPFVO;
import it.csi.pbandi.pbgestfinbo.integration.vo.search.VisualizzaStoricoVO;

@Service
public class VisualizzaStoricoImpl implements VisualizzaStorico{
	
	@Autowired
	protected VisualizzaStoricoDAO storicoDAO;

	@Override
	public Response getVistaStoricoDomanda(Long idSoggetto, Long idDomanda,
			HttpServletRequest req) throws DaoException {
		List<VisualizzaStoricoVO> storico = this.storicoDAO.getVistaStoricoDomanda(idSoggetto, idDomanda, req);
		return Response.ok().entity(storico).build();
	}

	@Override
	public Response getVistaStoricoProgetto(Long idSoggetto, Long idProgetto, HttpServletRequest req)
			throws DaoException {
		List<VisualizzaStoricoVO> storico = this.storicoDAO.getVistaStoricoProgetto(idSoggetto, idProgetto, req);
		return Response.ok().entity(storico).build();
	}

	@Override
	public Response getVistaStoricoDomandaPF(Long idSoggetto, Long idDomanda, HttpServletRequest req)
			throws DaoException {
		List<VisualizzaStoricoPFVO> storico = this.storicoDAO.getVistaStoricoDomandaPF(idSoggetto, idDomanda, req);
		return Response.ok().entity(storico).build();
	}

	@Override
	public Response getVistaStoricoProgettoPF(Long idSoggetto, Long idProgetto, HttpServletRequest req)
			throws DaoException {
		List<VisualizzaStoricoPFVO> storico = this.storicoDAO.getVistaStoricoProgettoPF(idSoggetto, idProgetto, req);
		return Response.ok().entity(storico).build();
	}

	

	

}
