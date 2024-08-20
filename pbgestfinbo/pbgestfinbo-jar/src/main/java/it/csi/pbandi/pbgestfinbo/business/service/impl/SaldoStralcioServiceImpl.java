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
import org.springframework.transaction.annotation.Transactional;

import it.csi.pbandi.pbgestfinbo.business.service.SaldoStralcioService;
import it.csi.pbandi.pbgestfinbo.exception.DaoException;
import it.csi.pbandi.pbgestfinbo.integration.dao.SaldoStralcioDAO;
import it.csi.pbandi.pbgestfinbo.integration.vo.SaldoStralcioVO;

@Service
public class SaldoStralcioServiceImpl implements SaldoStralcioService {
	
	@Autowired
	SaldoStralcioDAO saldoStralcioDAO; 

	@Transactional
	@Override
	public Response getSaldoStralcio(Long idSaldoStralcio,int idModalitaAgev, HttpServletRequest req) throws DaoException {
		return Response.ok(saldoStralcioDAO.getSaldoStralcio(idSaldoStralcio,  idModalitaAgev)).build();
	}

	@Override
	public Response insertSaldoStralcio(SaldoStralcioVO saldoStralcio, Long idUtente, Long idProgetto, int idModalitaAgev,
			HttpServletRequest request) throws InvalidParameterException, Exception {
		return Response.ok(saldoStralcioDAO.insertSaldoStralcio(saldoStralcio, idUtente, idProgetto, idModalitaAgev)).build();
	}

	@Override
	public Response modificaSaldoStralcio(SaldoStralcioVO saldoStralcio, Long idUtente, Long idProgetto,
			Long idSaldoStralcio,int idModalitaAgev, HttpServletRequest request) throws InvalidParameterException, Exception {
		return Response.ok(saldoStralcioDAO.modificaSaldoStralcio(saldoStralcio, idUtente, idProgetto, idSaldoStralcio, idModalitaAgev)).build();
	}

	@Override
	public Response getStorico(Long idUtente, Long idProgetto,int idModalitaAgev, HttpServletRequest req) throws DaoException {
		return Response.ok(saldoStralcioDAO.getStorico(idProgetto, idModalitaAgev)).build();
	}

	@Override
	public Response getStoricoSaldoStralcio(Long idUtente, Long idProgetto,int idModalitaAgev, HttpServletRequest req)
			throws DaoException {
		return Response.ok(saldoStralcioDAO.getListaSaldoStralcio(idProgetto, idModalitaAgev)).build();
	}

	@Override
	public Response getListaAttivitaSaldoStralcio() throws DaoException {
		return Response.ok(saldoStralcioDAO.getAttivitaSaldoStralcio()).build();
	}

	@Override
	public Response getListaAttivitaEsito() throws DaoException {
		return Response.ok(saldoStralcioDAO.getAttivitaEsito()).build();
	}

	@Override
	public Response getListaAttivitaRecupero() throws DaoException {
		return Response.ok(saldoStralcioDAO.getAttivitaRecupero()).build();
	}

}
