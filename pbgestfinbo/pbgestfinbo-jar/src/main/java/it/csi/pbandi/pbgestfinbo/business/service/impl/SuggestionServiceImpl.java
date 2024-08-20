/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.business.service.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.csi.pbandi.pbgestfinbo.exception.DaoException;
import it.csi.pbandi.pbgestfinbo.integration.dao.SuggestionDAO;
import it.csi.pbandi.pbgestfinbo.integration.vo.suggestion.AutofillVO;
import it.csi.pbandi.pbgestfinbo.integration.vo.suggestion.CodiceFiscaleVO;
import it.csi.pbandi.pbgestfinbo.integration.vo.suggestion.CognomeVO;
import it.csi.pbandi.pbgestfinbo.integration.vo.suggestion.DenominazioneVO;
import it.csi.pbandi.pbgestfinbo.integration.vo.suggestion.IdDomandaVO;
import it.csi.pbandi.pbgestfinbo.integration.vo.suggestion.IdProgettoVO;
import it.csi.pbandi.pbgestfinbo.integration.vo.suggestion.IdSoggettoVO;
import it.csi.pbandi.pbgestfinbo.integration.vo.suggestion.NomeVO;
import it.csi.pbandi.pbgestfinbo.integration.vo.suggestion.PartitaIvaVO;
import it.csi.pbandi.pbgestfinbo.util.Constants;


@Service
public class SuggestionServiceImpl implements it.csi.pbandi.pbgestfinbo.business.service.SuggestionService {

	@Autowired
	protected SuggestionDAO suggestionDao;

	private Logger LOG = Logger.getLogger(Constants.COMPONENT_NAME);


	@Override
	public Response suggerimentoIdSoggetto(@QueryParam("idSoggetto") String idSoggetto, HttpServletRequest req) throws DaoException {

		LOG.debug("[SuggestionServiceImpl::suggerimentoIdSoggetto] BEGIN");

		List<IdSoggettoVO> idSoggestoResponse = this.suggestionDao.suggestionIdSoggetto(idSoggetto, req);

		LOG.debug("[SuggestionServiceImpl::suggerimentoIdSoggetto] END");

		return Response.ok().entity(idSoggestoResponse).build();
	}


	@Override
	public Response suggerimentoCodiceFiscale(String codiceFiscale, Long idSoggetto, HttpServletRequest req)
			throws DaoException {
		LOG.debug("[SuggestionServiceImpl::suggerimentoCodiceFiscale] BEGIN");

		List<CodiceFiscaleVO> cfResponse = this.suggestionDao.suggestionCodiceFiscale(codiceFiscale, idSoggetto, req);

		LOG.debug("[SuggestionServiceImpl::suggerimentoCodiceFiscale] END");

		return Response.ok().entity(cfResponse).build();
	}


	@Override
	public Response suggerimentoPartitaIva(String partitaIva, Long idSoggetto, HttpServletRequest req)
			throws DaoException {
		LOG.debug("[SuggestionServiceImpl::suggerimentoPartitaIva] BEGIN");

		List<PartitaIvaVO> pIvaResponse = this.suggestionDao.suggestionPartitaIva(partitaIva, idSoggetto, req);

		LOG.debug("[SuggestionServiceImpl::suggerimentoPartitaIva] END");

		return Response.ok().entity(pIvaResponse).build();
	}


	@Override
	public Response suggerimentoDenominazione(String denominazione, Long idSoggetto, HttpServletRequest req)
			throws DaoException {
		LOG.debug("[SuggestionServiceImpl::suggerimentoDenominazione] BEGIN");

		List<DenominazioneVO> denominazioneResponse = this.suggestionDao.suggestionDenominazione(denominazione, idSoggetto, req);

		LOG.debug("[SuggestionServiceImpl::suggerimentoDenominazione] END");

		return Response.ok().entity(denominazioneResponse).build();
	}


	@Override
	public Response suggerimentoIdDomanda(String numeroDomanda, Long idSoggetto, HttpServletRequest req)
			throws DaoException {
		LOG.debug("[SuggestionServiceImpl::suggerimentoIdDomanda] BEGIN");

		List<IdDomandaVO> idDomandaResponse = this.suggestionDao.suggestionIdDomanda(numeroDomanda, idSoggetto, req);

		LOG.debug("[SuggestionServiceImpl::suggerimentoIdDomanda] END");

		return Response.ok().entity(idDomandaResponse).build();
	}


	@Override
	public Response suggerimentoIdProgetto(String codProgetto, Long idSoggetto, HttpServletRequest req)
			throws DaoException {
		LOG.debug("[SuggestionServiceImpl::suggerimentoIdProgetto] BEGIN");

		List<IdProgettoVO> idProgettoResponse = this.suggestionDao.suggestionIdProgetto(codProgetto, idSoggetto, req);

		LOG.debug("[SuggestionServiceImpl::suggerimentoIdProgetto] END");

		return Response.ok().entity(idProgettoResponse).build();
	}


	@Override
	public Response getNome(String nome, Long idSoggetto, HttpServletRequest req) throws DaoException {
		LOG.debug("[SuggestionServiceImpl::getNome] BEGIN");

		List<NomeVO> nomiList = this.suggestionDao.getNome(nome, idSoggetto, req);

		LOG.debug("[SuggestionServiceImpl::getNome] END");

		return Response.ok().entity(nomiList).build();
	}


	@Override
	public Response getCognome(String cognome, Long idSoggetto, HttpServletRequest req) throws DaoException {
		LOG.debug("[SuggestionServiceImpl::getNome] BEGIN");

		List<CognomeVO> cognomiList = this.suggestionDao.getCognome(cognome, idSoggetto, req);

		LOG.debug("[SuggestionServiceImpl::getNome] END");

		return Response.ok().entity(cognomiList).build();
	}


	@Override
	public Response getAutofill(Long idSoggetto, String tipoSoggetto, Long idDomanda, Long idProgetto, HttpServletRequest req) throws DaoException {
		LOG.debug("[SuggestionServiceImpl::getAutofill] BEGIN");

		AutofillVO autofill = this.suggestionDao.getAutofill(idSoggetto, tipoSoggetto, idDomanda, idProgetto,  req);

		LOG.debug("[SuggestionServiceImpl::getAutofill] END");

		return Response.ok().entity(autofill).build();
	}










}
