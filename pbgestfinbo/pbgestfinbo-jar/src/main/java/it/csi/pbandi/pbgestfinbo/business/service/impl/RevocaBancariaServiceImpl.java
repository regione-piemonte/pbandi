/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.business.service.impl;

import java.security.InvalidParameterException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.csi.pbandi.pbgestfinbo.business.service.RevocaBancariaService;
import it.csi.pbandi.pbgestfinbo.dto.profilazione.RevocaBancariaDTO;
import it.csi.pbandi.pbgestfinbo.dto.profilazione.StoricoRevocaDTO;
import it.csi.pbandi.pbgestfinbo.exception.DaoException;
import it.csi.pbandi.pbgestfinbo.integration.dao.RevocaBancariaDAO;

@Service
public class RevocaBancariaServiceImpl implements RevocaBancariaService {
	
	@Autowired
	RevocaBancariaDAO revocaBancariaDAO; 

	@Override
	public Response getRevocheBancarie(Long idUtente, Long idProgetto, int idModalitaAgev, HttpServletRequest req)
			throws DaoException {
		
		return Response.ok().entity(revocaBancariaDAO.getListRevoche(idUtente, idProgetto, idModalitaAgev )).build();
	}

//	@Override
//	public Response salvaRevocaBancaria(
//			RevocaBancariaDTO revocaBancariaDTO,
//			long idUtente, long idProgetto,
//			HttpServletRequest request) throws InvalidParameterException, Exception {
//		revocaBancariaDAO.salvaRevoca(revocaBancariaDTO, idUtente, idProgetto); 
//		return Response.ok().entity(revocaBancariaDAO.salvaRevoca(revocaBancariaDTO, idUtente, idProgetto)).build(); 
//	}
	
	@Override
	public Response salvaRevocaBancaria(
			RevocaBancariaDTO revocaBancariaDTO, long idUtente, long idProgetto, int idModalitaAgev,HttpServletRequest request)
					throws InvalidParameterException, Exception {
		return Response.ok().entity(revocaBancariaDAO.salvaRevoca(revocaBancariaDTO, idUtente, idProgetto, idModalitaAgev)).build();
	}

	@Override
	public ArrayList<StoricoRevocaDTO> getStorico(Long idUtente, Long idProgetto, int idModalitaAgev, HttpServletRequest req) throws DaoException {
			
			ArrayList<StoricoRevocaDTO> lista= new ArrayList<StoricoRevocaDTO>(); 
			lista = revocaBancariaDAO.getStoricoRevoca(idUtente, idProgetto, idModalitaAgev);
		return lista; 
	}

	@Override
	public RevocaBancariaDTO getRevocaBancaria(Long idUtente, Long idProgetto,int idModalitaAgev, HttpServletRequest req)
			throws DaoException {
		RevocaBancariaDTO revoca = new RevocaBancariaDTO(); 
		revoca = revocaBancariaDAO.getRevoca(idUtente, idProgetto, idModalitaAgev); 
		return revoca ;
	}

	
	

}
