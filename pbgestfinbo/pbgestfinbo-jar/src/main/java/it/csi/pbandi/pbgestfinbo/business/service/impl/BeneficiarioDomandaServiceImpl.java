/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.business.service.impl;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.csi.pbandi.pbgestfinbo.business.service.BeneficiarioDomandaService;
import it.csi.pbandi.pbgestfinbo.exception.DaoException;
import it.csi.pbandi.pbgestfinbo.integration.dao.BeneficiarioDomandaDAO;
import it.csi.pbandi.pbgestfinbo.integration.vo.search.BeneficiarioDomandaVO;

@Service
public class BeneficiarioDomandaServiceImpl implements BeneficiarioDomandaService{
	
	@Autowired
	protected BeneficiarioDomandaDAO beneDomandaDAO;

	@Override
	public Response getLegaleRappr(Long idSoggetto, Long idDomanda, HttpServletRequest req) throws DaoException {
		BeneficiarioDomandaVO beneDomanda = this.beneDomandaDAO.getLegaleRappr(idSoggetto, idDomanda, req);
		return Response.ok().entity(beneDomanda).build();
	}

	@Override
	public Response getSedeAmm(Long idSoggetto, Long idDomanda, HttpServletRequest req) throws DaoException {
		BeneficiarioDomandaVO beneDomanda = this.beneDomandaDAO.getSedeAmm(idSoggetto, idDomanda, req);
		return Response.ok().entity(beneDomanda).build();
	}

	@Override
	public Response getRecapiti(Long idSoggetto, Long idDomanda, HttpServletRequest req) throws DaoException {
		BeneficiarioDomandaVO beneDomanda = this.beneDomandaDAO.getRecapiti(idSoggetto, idDomanda, req);
		return Response.ok().entity(beneDomanda).build();
	}

	@Override
	public Response getConto(Long idSoggetto, Long idDomanda, HttpServletRequest req) throws DaoException {
		BeneficiarioDomandaVO beneDomanda = this.beneDomandaDAO.getConto(idSoggetto, idDomanda, req);
		return Response.ok().entity(beneDomanda).build();
	}

	@Override
	public Response getDelegati(Long idDomanda, HttpServletRequest req) throws DaoException {
		BeneficiarioDomandaVO beneDomanda = this.beneDomandaDAO.getDelegati(idDomanda, req);
		return Response.ok().entity(beneDomanda).build();
	}

	@Override
	public Response getConsulenti(Long idDomanda, HttpServletRequest req) throws DaoException {
		BeneficiarioDomandaVO beneDomanda = this.beneDomandaDAO.getConsulenti(idDomanda, req);
		return Response.ok().entity(beneDomanda).build();
	}

	

	

	

}
