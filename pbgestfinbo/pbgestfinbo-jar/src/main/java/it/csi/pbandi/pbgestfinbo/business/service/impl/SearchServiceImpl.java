/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.business.service.impl;

import java.math.BigDecimal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.csi.pbandi.pbgestfinbo.business.service.SearchService;
import it.csi.pbandi.pbgestfinbo.exception.DaoException;
import it.csi.pbandi.pbgestfinbo.integration.dao.SearchDAO;
import it.csi.pbandi.pbgestfinbo.integration.vo.search.SearchVO;
import it.csi.pbandi.pbgestfinbo.util.Constants;

@Service
public class SearchServiceImpl implements SearchService{

	@Autowired
	protected SearchDAO searchDao;

	private Logger LOG = Logger.getLogger(Constants.COMPONENT_NAME);

	//	@Override
	//	public Response cercaBeneficiario(Optional<String> cf, Optional<Long> idSoggetto, Long pIva, Optional<String> denominazione, Optional<Long> idDomanda, Optional<Long> idProgetto, HttpServletRequest req) throws DaoException {
	//			LOG.debug("[SuggestionServiceImpl::cercaBeneficiario] BEGIN");
	//			
	//			List<String> beneficiari = this.searchDao.cercaBeneficiario(cf, idSoggetto, pIva, denominazione, idDomanda, idProgetto, req);
	//			
	//			LOG.debug("[SuggestionServiceImpl::cercaBeneficiario] END");
	//			
	//			return Response.ok().entity(beneficiari).build();
	//	}



//	@Override
//	public Response cercaBeneficiario(String cf, Long idSoggetto, String pIva, String denominazione,
//			String numeroDomanda, String codProgetto, String nome, String cognome, HttpServletRequest req)
//					throws DaoException {
//		LOG.debug("[SuggestionServiceImpl::cercaBeneficiario] BEGIN");
//
//		List<SearchVO> beneficiari = this.searchDao.cercaBeneficiario(cf, idSoggetto, pIva, denominazione, numeroDomanda, codProgetto, nome, cognome, req);
//
//		LOG.debug("[SuggestionServiceImpl::cercaBeneficiario] END");
//
//		return Response.ok().entity(beneficiari).build();
//	}

	@Override
	public Response cercaBeneficiarioEg(String cf, Long idSoggetto, String pIva, String denominazione,
			String numeroDomanda, BigDecimal codProgetto, HttpServletRequest req) throws DaoException {
		LOG.debug("[SuggestionServiceImpl::cercaBeneficiarioEg] BEGIN");

		List<SearchVO> beneficiari = this.searchDao.cercaBeneficiarioEg(cf, idSoggetto, pIva, denominazione, numeroDomanda, codProgetto, req);

		LOG.debug("[SuggestionServiceImpl::cercaBeneficiarioEg] END");

		return Response.ok().entity(beneficiari).build();
	}

	@Override
	public Response cercaBeneficiarioPf(String cf, Long idSoggetto, String numeroDomanda, BigDecimal codProgetto,
			String nome, String cognome, HttpServletRequest req) throws DaoException {
		LOG.debug("[SuggestionServiceImpl::cercaBeneficiarioPf] BEGIN");

		List<SearchVO> beneficiari = this.searchDao.cercaBeneficiarioPf(cf, idSoggetto, numeroDomanda, codProgetto, nome, cognome, req);

		LOG.debug("[SuggestionServiceImpl::cercaBeneficiarioPf] END");

		return Response.ok().entity(beneficiari).build();
	}}
