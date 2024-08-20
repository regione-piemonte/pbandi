/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.business.service.impl;

import it.csi.pbandi.pbgestfinbo.business.service.GestioneControdeduzioniService;
import it.csi.pbandi.pbgestfinbo.integration.dao.GestioneControdeduzioniDAO;
import it.csi.pbandi.pbgestfinbo.integration.vo.controdeduzioni.AllegatiControdeduzioniVO;
import it.csi.pbandi.pbgestfinbo.integration.vo.controdeduzioni.RichiestaProrogaVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Response;
import java.util.List;

@Service
public class GestioneControdeduzioniServiceImpl implements GestioneControdeduzioniService {
	@Autowired
	protected GestioneControdeduzioniDAO gestioneControdeduzioniDao;

	@Override
	public Response getAllegati(Long idControdeduz, HttpServletRequest req) {
		return Response.ok(gestioneControdeduzioniDao.getAllegati(idControdeduz)).build();
	}

	@Override
	public Response inserisciAllegati(Long idControdeduz, List<AllegatiControdeduzioniVO> allegati, HttpServletRequest req) {
		return Response.ok(gestioneControdeduzioniDao.inserisciAllegati(idControdeduz, allegati)).build();
	}
	
	@Override
	public Response deleteAllegato(Long idFileEntita, HttpServletRequest req) {
		return Response.ok(gestioneControdeduzioniDao.deleteAllegato(idFileEntita)).build();
	}

	//NEW
	@Override
	public Response getIntestazioneControdeduzioni(Long idProgetto, HttpServletRequest req) {
		return Response.ok().entity(gestioneControdeduzioniDao.getIntestazioneControdeduzioni(idProgetto)).build();
	}

	@Override
	public Response getControdeduzioni(Long idProgetto, HttpServletRequest req) {
		return Response.ok().entity(gestioneControdeduzioniDao.getControdeduzioni(idProgetto)).build();
	}

	@Override
	public Response insertControdeduz(Long idGestioneRevoca, HttpServletRequest req) {
		return Response.ok().entity(gestioneControdeduzioniDao.insertControdeduz(idGestioneRevoca, req)).build();
	}

	@Override
	public Response richiediAccessoAtti(Long idControdeduz, HttpServletRequest req) {
		return Response.ok().entity(gestioneControdeduzioniDao.richiediAccessoAtti(idControdeduz, req)).build();
	}

	@Override
	public Response inviaControdeduz(Long idControdeduz, HttpServletRequest req) {
		return Response.ok().entity(gestioneControdeduzioniDao.inviaControdeduz(idControdeduz, req)).build();
	}

	@Override
	public Response deleteControdeduz(Long idControdeduz, HttpServletRequest req) {
		return Response.ok().entity(gestioneControdeduzioniDao.deleteControdeduz(idControdeduz, req)).build();
	}

	@Override
	public Response getRichiestaProroga(Long idControdeduz, HttpServletRequest req) {
		return Response.ok().entity(gestioneControdeduzioniDao.getRichiestaProroga(idControdeduz)).build();
	}

	@Override
	public Response richiediProroga(Long idControdeduz, RichiestaProrogaVO richiestaProrogaVO, HttpServletRequest req) {
		return Response.ok().entity(gestioneControdeduzioniDao.richiediProroga(idControdeduz, richiestaProrogaVO, req)).build();
	}

}
