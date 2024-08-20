/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.business.service.impl;

import it.csi.pbandi.pbgestfinbo.business.service.RicercaProposteErogazioneService;
import it.csi.pbandi.pbgestfinbo.integration.dao.RicercaProposteErogazioneDAO;
import it.csi.pbandi.pbgestfinbo.util.Constants;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Response;

@Service
public class RicercaProposteErogazioneServiceImpl implements RicercaProposteErogazioneService {
	private Logger LOG = Logger.getLogger(Constants.COMPONENT_NAME);

	@Autowired
	protected RicercaProposteErogazioneDAO ricercaProposteErogazioneDao;

	@Override
	public Response getProposteErogazione(Long progrBandoLinea, Long idModalitaAgevolazione, Long idSoggetto, Long idProgetto, String contrPreErogazione, HttpServletRequest req) throws Exception {
		return Response.ok().entity(ricercaProposteErogazioneDao.getProposteErogazione(progrBandoLinea, idModalitaAgevolazione, idSoggetto, idProgetto, contrPreErogazione)).build();
	}

	@Override
	public Response getSuggestion(String value, String id, HttpServletRequest req) throws Exception {
		return Response.ok().entity(ricercaProposteErogazioneDao.getSuggestion(value, id)).build();
	}


}
