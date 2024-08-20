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

import it.csi.pbandi.pbgestfinbo.business.service.AzioneRecuperoBancaService;
import it.csi.pbandi.pbgestfinbo.exception.DaoException;
import it.csi.pbandi.pbgestfinbo.integration.dao.AzioneRecuperoBancaDAO;
import it.csi.pbandi.pbgestfinbo.integration.vo.AzioneRecuperoBancaVO;


@Service
public class AzioneRecuperoBancaServiceImpl implements AzioneRecuperoBancaService {
	
	@Autowired
	AzioneRecuperoBancaDAO azioneRecuperoBancaDAO; 

	@Override
	public Response getAzioneRecuperoBanca(Long idUtente, Long idProgetto, Long idAzioneRecupero,int idModalitaAgev,  HttpServletRequest req) throws DaoException {
		
		return Response.ok(azioneRecuperoBancaDAO.getAzioneRecuperoBanca(idUtente, idProgetto, idAzioneRecupero, idModalitaAgev)).build();
	}
	
	@Override
	public Response modificaAzioneRecuperoBanca(AzioneRecuperoBancaVO azioneRecupBanca, Long idUtente, Long idProgetto,
			Long idAzioneRecupero,int idModalitaAgev, HttpServletRequest request) throws InvalidParameterException, Exception {
		
		return Response.ok(azioneRecuperoBancaDAO.modificaAzioneRecuperoBanca(azioneRecupBanca, idUtente, idProgetto, idAzioneRecupero, idModalitaAgev)).build();
	}


	@Override
	public Response insertAzioneRecuperoBanca(AzioneRecuperoBancaVO azioneRecupBanca, Long idUtente, Long idProgetto,
			int idModalitaAgev,HttpServletRequest request) throws InvalidParameterException, Exception {
		return Response.ok(azioneRecuperoBancaDAO.insertAzioneRecuperoBanca(azioneRecupBanca, idUtente, idProgetto, idModalitaAgev)).build();
	}

	@Override
	public Response getStoricoAzioni(Long idUtente, Long idProgetto,int idModalitaAgev, HttpServletRequest req) throws DaoException {
		return Response.ok(azioneRecuperoBancaDAO.getStoricoAzioni(idUtente, idProgetto, idModalitaAgev)).build();
	}
	

	@Override
	public Response getStoricoAzioneRecup(Long idUtente, Long idProgetto,int idModalitaAgev, HttpServletRequest req) throws DaoException {
		
		return Response.ok(azioneRecuperoBancaDAO.getStoricoAzioneRecupBanca(idUtente, idProgetto, idModalitaAgev)).build();
	}


	@Override
	public Response getListaAzioniRecuperoBanca() throws DaoException {
		return Response.ok(azioneRecuperoBancaDAO.getListaAttivitaAzione()).build();
	}



}
