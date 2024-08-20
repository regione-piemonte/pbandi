/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.business.service.impl;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.csi.pbandi.pbgestfinbo.business.service.SegnalazioneCorteContiService;
import it.csi.pbandi.pbgestfinbo.integration.dao.SegnalazioneCorteContiDAO;
import it.csi.pbandi.pbgestfinbo.integration.vo.SegnalazioneCorteContiVO;

@Service
public class SegnalazioneCorteContiServiceImpl implements SegnalazioneCorteContiService {
	
	@Autowired
	private SegnalazioneCorteContiDAO segnalazioneDAO;

	@Override
	public Response getSegnalazioneCorteConti(Long idSegnalazioneCorteConti, HttpServletRequest req) {
		return Response.ok(segnalazioneDAO.getSegnalazione(idSegnalazioneCorteConti)).build();
	}

	@Override
	public Response insertSegnalazioneCorteConti(SegnalazioneCorteContiVO segnalazioneCorteContiVO, int idModalitaAgev) {
		return Response.ok(segnalazioneDAO.insertSegnalazione(segnalazioneCorteContiVO,idModalitaAgev)).build();
	}

	@Override
	public Response modifcaSegnalazioneCorteConti(SegnalazioneCorteContiVO segnalazioneCorteContiVO,
			Long idSegnalazioneCorteConti, int idModalitaAgev) {
		return Response.ok(segnalazioneDAO.modificaSegnalazione(segnalazioneCorteContiVO, idSegnalazioneCorteConti, idModalitaAgev)).build();
	}

	@Override
	public Response getStoricoSegnalazioni(Long idProgetto, int idModalitaAgev, @Context HttpServletRequest req) {
		return Response.ok(segnalazioneDAO.getSotricoSegnalazioni(idProgetto, idModalitaAgev)).build();
	}

	@Override
	public Response getStorico(Long idProgetto, int idModalitaAgev, @Context HttpServletRequest req) {
		return Response.ok(segnalazioneDAO.getStorico(idProgetto, idModalitaAgev)).build();
	}

}
