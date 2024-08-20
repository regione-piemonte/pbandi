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

import it.csi.pbandi.pbgestfinbo.business.service.StoricoBeneficiario;
import it.csi.pbandi.pbgestfinbo.exception.DaoException;
import it.csi.pbandi.pbgestfinbo.integration.dao.StoricoBeneficiarioDAO;
import it.csi.pbandi.pbgestfinbo.integration.vo.search.StoricoBeneficiarioVO;
import it.csi.pbandi.pbgestfinbo.integration.vo.search.VersioniVO;

@Service
public class StoricoBeneficiarioImpl implements StoricoBeneficiario {

	@Autowired
	protected StoricoBeneficiarioDAO storicoBeneficiarioDao;

	@Override
	public Response getStorico(Long idSoggetto, HttpServletRequest req) throws DaoException {

		List<StoricoBeneficiarioVO> storico = this.storicoBeneficiarioDao.getStorico(idSoggetto, req);

		return Response.ok().entity(storico).build();
	}

	@Override
	public Response getStoricoFisico(Long idSoggetto, HttpServletRequest req) throws DaoException {
		List<StoricoBeneficiarioVO> storico = this.storicoBeneficiarioDao.getStoricoFisico(idSoggetto,  req);
		return Response.ok().entity(storico).build();
	}

	@Override
	public Response getVersioni(Long idSoggetto, HttpServletRequest req) throws DaoException {
		List<VersioniVO> versioni = this.storicoBeneficiarioDao.getVersioni(idSoggetto,req);
		return Response.ok().entity(versioni).build();
	}





}
