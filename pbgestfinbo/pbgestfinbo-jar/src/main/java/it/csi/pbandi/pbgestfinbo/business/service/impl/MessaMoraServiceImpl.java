/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.business.service.impl;

import java.security.InvalidParameterException;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.csi.pbandi.pbgestfinbo.business.service.MessaMoraService;
import it.csi.pbandi.pbgestfinbo.exception.DaoException;
import it.csi.pbandi.pbgestfinbo.integration.dao.MessaMoraDAO;
import it.csi.pbandi.pbgestfinbo.integration.vo.MessaMoraVO;


@Service
public class MessaMoraServiceImpl implements MessaMoraService {
	
	@Autowired
	MessaMoraDAO messaMoraDao; 

	@Override
	public Response getMessaMora(Long idMessaMora, int idModalitaAgev,  HttpServletRequest req) throws DaoException {
		// TODO Auto-generated method stub
		return Response.ok(messaMoraDao.getMessaMora(idMessaMora, idModalitaAgev)).build();
	}

	@Override
	public Response insertMessaMora(MessaMoraVO messaMora,int idModalitaAgev, HttpServletRequest request)
			throws InvalidParameterException, Exception {
		// TODO Auto-generated method stub
		return Response.ok(messaMoraDao.insertMessaMora(messaMora, idModalitaAgev)).build();
	}

	@Override
	public Response modificaMessaMora(MessaMoraVO messaMoraVO, Long idMessaMora, int idModalitaAgev, HttpServletRequest request)
			throws InvalidParameterException, Exception {
		// TODO Auto-generated method stub
		return Response.ok(messaMoraDao.modificaMessaMora(messaMoraVO, idMessaMora, idModalitaAgev)).build();
	}

	@Override
	public Response getStorico(Long idProgetto,int idModalitaAgev, HttpServletRequest req) throws DaoException {
		// TODO Auto-generated method stub
		return Response.ok(messaMoraDao.getStorico(idProgetto, idModalitaAgev)).build();
	}

	@Override
	public Response getStoricoMessaMora(Long idProgetto,int idModalitaAgev,  HttpServletRequest req) throws DaoException {
		// TODO Auto-generated method stub
		return Response.ok(messaMoraDao.getStoricoMessaMora(idProgetto, idModalitaAgev)).build();
	}

	@Override
	public Response getListaAttivitaMessaMora() throws DaoException {
		// TODO Auto-generated method stub
		return Response.ok(messaMoraDao.getAttivitaMessaMora()).build();
	}

	@Override
	public Response getListaAttivitaRecupero() throws DaoException {
		// TODO Auto-generated method stub
		return  Response.ok(messaMoraDao.getAttivitaRecupero()).build();
	}

}
