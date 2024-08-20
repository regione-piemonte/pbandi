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

import it.csi.pbandi.pbgestfinbo.business.service.AbbattimentoGaranzieService;
import it.csi.pbandi.pbgestfinbo.exception.DaoException;
import it.csi.pbandi.pbgestfinbo.integration.dao.AbbattimentoGaranzieDAO;
import it.csi.pbandi.pbgestfinbo.integration.vo.AbbattimentoGaranzieVO;

@Service
public class AbbattimentoGaranzieServiceImpl implements AbbattimentoGaranzieService {
	
	@Autowired
	AbbattimentoGaranzieDAO abbattimentoGaranzieDAO; 

	@Override
	public Response getAbbattimentoGaranzia(Long idUtente, Long idProgetto, Long idAbbattimentoGaranzia, int idModalitaAgev,
			HttpServletRequest req) throws DaoException {	
		return Response.ok(abbattimentoGaranzieDAO.getAbbatimento(idUtente, idProgetto, idAbbattimentoGaranzia, idModalitaAgev)).build();
	}

	@Override
	public Response insertAbbattimentoGaranzia(AbbattimentoGaranzieVO abbattimento, Long idUtente, Long idProgetto, int idModalitaAgev,
			HttpServletRequest request) throws InvalidParameterException, Exception {
		return Response.ok(abbattimentoGaranzieDAO.insertAbbattimento(abbattimento, idUtente, idProgetto, idModalitaAgev)).build();
	}

	@Override
	public Response modificaAbbattimentoGaranzia(AbbattimentoGaranzieVO abbattimento, Long idUtente, Long idProgetto,
			Long idAbbattimentoGaranzia, int idModalitaAgev, HttpServletRequest request) throws InvalidParameterException, Exception {
		return Response.ok(abbattimentoGaranzieDAO.modificaAbbattimento(abbattimento, idUtente, idProgetto, idAbbattimentoGaranzia, idModalitaAgev)).build();
	}

	@Override
	public Response getStoricoAbbattimenti(Long idUtente, Long idProgetto,int idModalitaAgev,  HttpServletRequest req) throws DaoException {
		return Response.ok(abbattimentoGaranzieDAO.storicoAbbattimenti(idUtente, idProgetto,idModalitaAgev)).build();
	}

	@Override
	public Response getStoricoAbbattimentoGaranzia(Long idUtente, Long idProgetto, int idModalitaAgev, HttpServletRequest req)
			throws DaoException {
		return Response.ok(abbattimentoGaranzieDAO.storicoAbbattimentoGaranzie(idUtente, idProgetto, idModalitaAgev)).build();
	}

	@Override
	public Response getListalistaAttivitaAbbattimento() throws DaoException {
		return Response.ok(abbattimentoGaranzieDAO.getListaAttivitaAbbattimento()).build();
	}

}
