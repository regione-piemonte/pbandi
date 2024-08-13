/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.business.service.impl;

import java.security.InvalidParameterException;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.csi.pbandi.pbservizit.security.UserInfoSec;
import it.csi.pbandi.pbweb.business.service.FornitoreService;
import it.csi.pbandi.pbweb.dto.FornitoreFormDTO;
import it.csi.pbandi.pbweb.integration.dao.FornitoreDAO;
//import it.csi.pbandi.pbweb.integration.dao.request.AssociaFilesRequest;
import it.csi.pbandi.pbservizit.integration.dao.request.AssociaFilesRequest;
import it.csi.pbandi.pbweb.integration.dao.request.SalvaQualificaRequest;
import it.csi.pbandi.pbweb.integration.dao.request.SalvaQualificheRequest;
import it.csi.pbandi.pbweb.pbandisrv.dto.gestionefornitori.FornitoreDTO;
import it.csi.pbandi.pbweb.util.Constants;

@Service
public class FornitoreServiceImpl implements FornitoreService {
	
	@Autowired
	private FornitoreDAO fornitoreDAO;
	
	@Override
	public Response testTransactional(HttpServletRequest request) throws RuntimeException, InvalidParameterException, Exception {	
		return Response.ok().entity(fornitoreDAO.testTransactional(request)).build();
	}
	
	@Override
	public Response alberoAttivitaAteco(
			@DefaultValue("0") @QueryParam("idUtente") long idUtente,
			@Context HttpServletRequest req) throws InvalidParameterException, Exception {
	
		return Response.ok().entity(fornitoreDAO.alberoAttivitaAteco(idUtente,req)).build();
	}
	
	@Override
	public Response attivitaAteco(
			@DefaultValue("0") @QueryParam("idUtente") long idUtente,
			@Context HttpServletRequest req) throws InvalidParameterException, Exception {
	
		return Response.ok().entity(fornitoreDAO.attivitaAteco(idUtente,req)).build();
	}

	@Override
	public Response salvaFornitore(
			FornitoreFormDTO fornitoreFormDTO,
			@DefaultValue("0") @QueryParam("idUtente") long idUtente,
			@DefaultValue("0") @QueryParam("idSoggettoBeneficiario") long idSoggettoBeneficiario,
			@Context HttpServletRequest request) throws InvalidParameterException, Exception {
	
		return Response.ok().entity(fornitoreDAO.salvaFornitore(fornitoreFormDTO,idUtente,idSoggettoBeneficiario,request)).build();
	}
	
	@Override
	public Response cercaFornitore(Long idFornitore, Long idSoggettoBeneficiario, Long idProgetto, HttpServletRequest request) throws InvalidParameterException, Exception {
		UserInfoSec userInfo = (UserInfoSec) request.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);
		return Response.ok().entity(fornitoreDAO.cercaFornitore(idFornitore,idSoggettoBeneficiario,idProgetto,userInfo.getIdUtente(),userInfo.getIdIride())).build();
	}
	
	@Override
	public Response qualificheFornitore(
			@DefaultValue("0") @QueryParam("idFornitore") long idFornitore,
			@DefaultValue("0") @QueryParam("idUtente") long idUtente,
			@Context HttpServletRequest request) throws InvalidParameterException, Exception {
	
		return Response.ok().entity(fornitoreDAO.qualificheFornitore(idFornitore,idUtente,request)).build();
	}
	
	@Override
	public Response salvaQualifica(
		SalvaQualificaRequest salvaQualificaRequest, HttpServletRequest request) throws InvalidParameterException, Exception {	
		return Response.ok().entity(fornitoreDAO.salvaQualifica(salvaQualificaRequest,request)).build();
	}
	
	@Override
	public Response salvaQualifiche(
		SalvaQualificheRequest salvaQualificheRequest, HttpServletRequest request) throws InvalidParameterException, Exception {
		UserInfoSec userInfo = (UserInfoSec) request.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);
		return Response.ok().entity(fornitoreDAO.salvaQualifiche(salvaQualificheRequest, userInfo.getIdUtente(), userInfo.getIdIride())).build();
	}
	
	@Override
	public Response fattureFornitore(long idFornitore, long idProgetto, HttpServletRequest request) throws RuntimeException, InvalidParameterException, Exception {
		UserInfoSec userInfo = (UserInfoSec) request.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);
		return Response.ok().entity(fornitoreDAO.fattureFornitore(idFornitore, idProgetto, userInfo.getIdUtente(), userInfo.getIdIride())).build();
	}
	
	@Override
	public Response nazioni(HttpServletRequest request) throws InvalidParameterException, Exception {	
		return Response.ok().entity(fornitoreDAO.nazioni(request)).build();
	}
	
	@Override
	public Response ricercaElencoFornitori(FornitoreDTO filtro, Long idSoggettoBeneficiario, Long idProgetto, HttpServletRequest request) throws InvalidParameterException, Exception {
		UserInfoSec userInfo = (UserInfoSec) request.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);
		return Response.ok().entity(fornitoreDAO.ricercaElencoFornitori(filtro, idSoggettoBeneficiario, idProgetto, userInfo.getIdUtente(), userInfo.getIdIride())).build();
	}
	
	@Override
	public Response disassociaAllegatoFornitore(Long idDocumentoIndex, Long idFornitore, Long idProgetto, HttpServletRequest request) throws InvalidParameterException, Exception {
		UserInfoSec userInfo = (UserInfoSec) request.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);
		return Response.ok().entity(fornitoreDAO.disassociaAllegatoFornitore(idDocumentoIndex, idFornitore, idProgetto, userInfo.getIdUtente(), userInfo.getIdIride())).build();
	}
	
	@Override
	public Response associaAllegatiAFornitore(AssociaFilesRequest associaFilesRequest, HttpServletRequest request) throws InvalidParameterException, Exception {
		UserInfoSec userInfo = (UserInfoSec) request.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);
		return Response.ok().entity(fornitoreDAO.associaAllegatiAFornitore(associaFilesRequest, userInfo.getIdUtente())).build();
	}
	
	@Override
	public Response eliminaFornitore(Long idFornitore, HttpServletRequest request) throws InvalidParameterException, Exception {
		UserInfoSec userInfo = (UserInfoSec) request.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);
		return Response.ok().entity(fornitoreDAO.eliminaFornitore(idFornitore, userInfo.getIdUtente(), userInfo.getIdIride())).build();
	}
	
	@Override
	public Response esistonoDocumentiAssociatiAQualificaFornitore(Long progrFornitoreQualifica, HttpServletRequest request) throws InvalidParameterException, Exception {
		UserInfoSec userInfo = (UserInfoSec) request.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);
		return Response.ok().entity(fornitoreDAO.esistonoDocumentiAssociatiAQualificaFornitore(progrFornitoreQualifica, userInfo.getIdUtente(), userInfo.getIdIride())).build();
	}
	
	@Override
	public Response eliminaQualifica(Long progrFornitoreQualifica, HttpServletRequest request) throws InvalidParameterException, Exception {
		UserInfoSec userInfo = (UserInfoSec) request.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);
		return Response.ok().entity(fornitoreDAO.eliminaQualifica(progrFornitoreQualifica, userInfo.getIdUtente(), userInfo.getIdIride())).build();
	}
	
}
