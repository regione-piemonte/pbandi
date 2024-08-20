/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.business.service.impl;

import java.math.BigDecimal;
import java.security.InvalidParameterException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.csi.pbandi.pbgestfinbo.business.service.ProposteVariazioniStatoCreditoService;
import it.csi.pbandi.pbgestfinbo.exception.DaoException;
import it.csi.pbandi.pbgestfinbo.integration.dao.ProposteVariazioneStatoCreditoDAO;
import it.csi.pbandi.pbgestfinbo.integration.vo.CercaPropostaVarazioneStatoCreditoSearchVO;
import it.csi.pbandi.pbgestfinbo.integration.vo.SalvaVariazioneStatoCreditoVO;

@Service
public class ProposteVariazioneStatoCreditoServiceImpl implements ProposteVariazioniStatoCreditoService {
	
	@Autowired
	ProposteVariazioneStatoCreditoDAO propostaDao; 


	@Override
	public Response getElencoProposte(CercaPropostaVarazioneStatoCreditoSearchVO statoCreditoSearchDTO, HttpServletRequest request) throws InvalidParameterException, Exception {
		return Response.ok(propostaDao.getElencoProposte(statoCreditoSearchDTO)).build();
	}


	@Override
	public Response getListaSugg(String stringa, int id, HttpServletRequest req) throws DaoException {
		
		return Response.ok(propostaDao.getSugggest(id, stringa)).build();
	}

	@Override
	public Response getListaAgev(HttpServletRequest req) throws DaoException {
		return Response.ok(propostaDao.listaTipoAgevolazione()).build();
	}

	@Override
	public Response getListaProp(HttpServletRequest req) throws DaoException {
		return Response.ok(propostaDao.listaStatoProposta()).build();
	}

	@Override
	public Response salvaStatoCredito(SalvaVariazioneStatoCreditoVO salvaVariazioneStatoCreditoVO,
			HttpServletRequest request) throws InvalidParameterException, Exception {
		
		
		return Response.ok(propostaDao.salvaStatoProposta(salvaVariazioneStatoCreditoVO, request)).build();
	}


	@Override
	public Response statoCredito(HttpServletRequest req) throws DaoException {
		return Response.ok(propostaDao.statoCredito()).build();
	}


	@Override
	public Response rifiutaAccettaMassivaStatoCredito(List<Long> proposteDaConfermare, String flagConferma,
			HttpServletRequest request) throws InvalidParameterException, Exception {
		return Response.ok(propostaDao.rifiutaAccettaMassivaStatoCredito(proposteDaConfermare, flagConferma, request)).build();
	}

}
