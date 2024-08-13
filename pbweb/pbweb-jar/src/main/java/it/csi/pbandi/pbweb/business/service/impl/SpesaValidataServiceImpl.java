/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.business.service.impl;

import java.security.InvalidParameterException;
import java.util.List;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.csi.pbandi.pbservizit.security.UserInfoSec;
import it.csi.pbandi.pbweb.business.service.SpesaValidataService;
import it.csi.pbandi.pbweb.dto.EsitoDTO;
import it.csi.pbandi.pbweb.dto.archivioFile.EsitoLeggiFile;
import it.csi.pbandi.pbweb.dto.spesaValidata.MensilitaProgettoDTO;
import it.csi.pbandi.pbweb.integration.dao.SpesaValidataDAO;
import it.csi.pbandi.pbweb.integration.dao.ValidazioneRendicontazioneDAO;
import it.csi.pbandi.pbweb.integration.dao.request.CercaDocumentiSpesaValidataRequest;
import it.csi.pbandi.pbweb.integration.dao.request.ConfermaSalvaSpesaValidataRequest;
import it.csi.pbandi.pbweb.integration.dao.request.SalvaSpesaValidataRequest;
import it.csi.pbandi.pbweb.util.Constants;


@Service
public class SpesaValidataServiceImpl implements SpesaValidataService{
	
	@Autowired
	SpesaValidataDAO spesaValidataDAO;
	
	@Autowired
	ValidazioneRendicontazioneDAO validazioneRendicontazioneDAO;
	
	@Override 
	public Response inizializzaSpesaValidata(Long idProgetto, String isFP, HttpServletRequest req) throws InvalidParameterException, Exception {
		UserInfoSec userInfo = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);	
		return Response.ok().entity(spesaValidataDAO.inizializzaSpesaValidata(idProgetto, Objects.equals("S", isFP), userInfo.getIdUtente(), userInfo.getIdIride(), userInfo.getIdSoggetto(), userInfo.getCodiceRuolo())).build();
	}
	
	@Override 
	public Response inizializzaModificaSpesaValidata(Long idDocumentoDiSpesa, String numDichiarazione, Long idProgetto, String codiceRuolo, Long idBandoLinea, HttpServletRequest req) throws InvalidParameterException, Exception {
		UserInfoSec userInfo = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);	
		return Response.ok().entity(spesaValidataDAO.inizializzaModificaSpesaValidata(idDocumentoDiSpesa, numDichiarazione, idProgetto, codiceRuolo, idBandoLinea, userInfo.getIdSoggetto(), userInfo.getIdUtente(), userInfo.getIdIride())).build();
	}
	
	@Override 
	public Response cercaDocumentiSpesaValidata (CercaDocumentiSpesaValidataRequest cercaDocumentiSpesaValidataRequest, HttpServletRequest req) throws InvalidParameterException, Exception {
		UserInfoSec userInfo = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);
		return Response.ok().entity(spesaValidataDAO.cercaDocumentiSpesaValidata(cercaDocumentiSpesaValidataRequest,userInfo.getIdUtente(),userInfo.getIdIride())).build();
	}
	
	@Override 
	public Response recuperaMensilitaProgetto (Long idProgetto, HttpServletRequest req) throws InvalidParameterException, Exception {
		UserInfoSec userInfo = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);
		return Response.ok().entity(spesaValidataDAO.recuperaMensilitaProgetto(idProgetto,userInfo.getIdUtente(),userInfo.getIdIride())).build();
	}
	
	@Override 
	public Response validaMensilitaProgetto (List<MensilitaProgettoDTO> listaMensilitaProgetto, HttpServletRequest req) throws InvalidParameterException, Exception {
		UserInfoSec userInfo = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);
		return Response.ok().entity(spesaValidataDAO.validaMensilitaProgetto(listaMensilitaProgetto,userInfo.getIdUtente(),userInfo.getIdIride())).build();
	}
	
	@Override 
	public Response reportDettaglioDocumentoDiSpesa (Long idProgetto, HttpServletRequest req) throws InvalidParameterException, Exception {
		UserInfoSec userInfo = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);
		EsitoLeggiFile esito = spesaValidataDAO.reportDettaglioDocumentoDiSpesa(idProgetto,userInfo.getIdUtente(),userInfo.getIdIride());
		return Response.ok().header("header-nome-file", esito.getNomeFile()).entity(esito.getBytes()).build();
	}
	
	@Override 
	public Response dichiarazioniDocumentoDiSpesa (Long idDocumentoDiSpesa, Long idProgetto, HttpServletRequest req) throws InvalidParameterException, Exception {
		UserInfoSec userInfo = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);
		return Response.ok().entity(spesaValidataDAO.dichiarazioniDocumentoDiSpesa(idDocumentoDiSpesa,idProgetto,userInfo.getIdUtente(),userInfo.getIdIride())).build();
	}
	
	@Override 
	public Response voceDiSpesa (Long idQuotaParte, Long idDocumentoDiSpesa, Long idProgetto, HttpServletRequest req) throws InvalidParameterException, Exception {
		UserInfoSec userInfo = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);
		return Response.ok().entity(spesaValidataDAO.voceDiSpesa(idQuotaParte,idDocumentoDiSpesa,idProgetto,userInfo.getIdUtente(),userInfo.getIdIride())).build();
	}
	
	@Override 
	public Response salvaSpesaValidata (SalvaSpesaValidataRequest salvaSpesaValidataRequest, HttpServletRequest req) throws InvalidParameterException, Exception {
		UserInfoSec userInfo = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);
		return Response.ok().entity(spesaValidataDAO.salvaSpesaValidata(salvaSpesaValidataRequest,userInfo.getIdUtente(),userInfo.getIdIride())).build();
	}
	
	@Override 
	public Response confermaSalvaSpesaValidata (ConfermaSalvaSpesaValidataRequest confermaSalvaSpesaValidataRequest, HttpServletRequest req) throws InvalidParameterException, Exception {
		UserInfoSec userInfo = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);
		return Response.ok().entity(spesaValidataDAO.confermaSalvaSpesaValidata(confermaSalvaSpesaValidataRequest,userInfo.getIdUtente(),userInfo.getIdIride())).build();
	}
	
	@Override 
	public Response dettaglioRettifiche (Long progQuotaParte, HttpServletRequest req) throws InvalidParameterException, Exception {
		UserInfoSec userInfo = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);
		return Response.ok().entity(spesaValidataDAO.dettaglioRettifiche(progQuotaParte,userInfo.getIdUtente(),userInfo.getIdIride())).build();
	}
	
	@Override
	public Response sospendiDocumentoSpesaValid(Long idDichiarazioneDiSpesa, Long idDocumentoDiSpesa, Long idProgetto,
			String noteValidazione, HttpServletRequest req) throws InvalidParameterException, Exception {
		UserInfoSec userInfo = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);
		EsitoDTO esito = new EsitoDTO();
		try {
			validazioneRendicontazioneDAO.sospendiDocumento(idDichiarazioneDiSpesa, idDocumentoDiSpesa, idProgetto,
					noteValidazione, userInfo.getIdUtente(), userInfo.getIdIride(), Boolean.FALSE);
			esito.setEsito(true);
			esito.setMessaggio("Documento sospeso correttamente.");
		} catch (Exception e) {
			esito.setEsito(false);
			esito.setMessaggio("Documento NON sospeso.");
		}
		return Response.ok().entity(esito).build();
	}

	@Override
	public Response salvaRilievoDS(Long idDichiarazioneDiSpesa, String note, HttpServletRequest req)
			throws InvalidParameterException, Exception {
		UserInfoSec userInfo = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);	
		return Response.ok().entity(spesaValidataDAO.salvaRilievoDS(idDichiarazioneDiSpesa,  note, userInfo.getIdUtente(), userInfo.getIdIride(), userInfo.getIdSoggetto(), userInfo.getCodiceRuolo())).build();
	}

	@Override
	public Response setNullaDaRilevare(Long idDichiarazioneDiSpesa, Long idProgetto, List<Long> idDocumentiConRilievo, HttpServletRequest req)
			throws InvalidParameterException, Exception {
		UserInfoSec userInfo = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);	
		return Response.ok().entity(spesaValidataDAO.setNullaDaRilevare(idDichiarazioneDiSpesa, idProgetto, idDocumentiConRilievo, userInfo.getIdUtente(), userInfo.getIdIride(), userInfo.getIdSoggetto(), userInfo.getCodiceRuolo())).build();
	}
	

	@Override
	public Response chiudiRilievi(Long idDichiarazioneDiSpesa, HttpServletRequest req)
			throws InvalidParameterException, Exception {
		UserInfoSec userInfo = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);	
		return Response.ok().entity(spesaValidataDAO.chiudiRilievi(idDichiarazioneDiSpesa, userInfo.getIdUtente(), userInfo.getIdIride(), userInfo.getIdSoggetto(), userInfo.getCodiceRuolo())).build();
	}

	@Override
	public Response getRilievoDocSpesa(Long idDocumentoDiSpesa, Long idProgetto, Long idDichiarazioneDiSpesa, HttpServletRequest req)
			throws InvalidParameterException, Exception {
		UserInfoSec userInfo = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);	
		return Response.ok().entity(spesaValidataDAO.getRilievoDocSpesa(idDocumentoDiSpesa, idProgetto, idDichiarazioneDiSpesa, userInfo.getIdUtente(), userInfo.getIdIride(), userInfo.getIdSoggetto(), userInfo.getCodiceRuolo())).build();
	}

	@Override
	public Response salvaRilievoDocSpesa(Long idDocumentoDiSpesa, Long idProgetto, String note, HttpServletRequest req)
			throws InvalidParameterException, Exception {
		UserInfoSec userInfo = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);	
		return Response.ok().entity(spesaValidataDAO.salvaRilievoDocSpesa(idDocumentoDiSpesa, idProgetto, note, userInfo.getIdUtente(), userInfo.getIdIride(), userInfo.getIdSoggetto(), userInfo.getCodiceRuolo())).build();
	}

	@Override
	public Response deleteRilievoDS(Long idDichiarazioneDiSpesa, HttpServletRequest req)
			throws InvalidParameterException, Exception {
		UserInfoSec userInfo = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);	
		return Response.ok().entity(spesaValidataDAO.deleteRilievoDS(idDichiarazioneDiSpesa, userInfo.getIdUtente(), userInfo.getIdIride(), userInfo.getIdSoggetto(), userInfo.getCodiceRuolo())).build();
	}

	@Override
	public Response deleteRilievoDocSpesa(Long idDocumentoDiSpesa, Long idProgetto, HttpServletRequest req)
			throws InvalidParameterException, Exception {
		UserInfoSec userInfo = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);	
		return Response.ok().entity(spesaValidataDAO.deleteRilievoDocSpesa(idDocumentoDiSpesa, idProgetto, userInfo.getIdUtente(), userInfo.getIdIride(), userInfo.getIdSoggetto(), userInfo.getCodiceRuolo())).build();
	}

	@Override
	public Response confermaValiditaRilievi(Long idDichiarazioneDiSpesa, HttpServletRequest req)
			throws InvalidParameterException, Exception {
		UserInfoSec userInfo = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);	
		return Response.ok().entity(spesaValidataDAO.confermaValiditaRilievi(idDichiarazioneDiSpesa, userInfo.getIdUtente(), userInfo.getIdIride(), userInfo.getIdSoggetto(), userInfo.getCodiceRuolo())).build();

	}
	
	@Override
	public Response annullaSospendiDocumentoSpesaValid(Long idDichiarazioneDiSpesa, Long idDocumentoDiSpesa, Long idProgetto,
			String noteValidazione, HttpServletRequest req) throws InvalidParameterException, Exception {
		UserInfoSec userInfo = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);
		EsitoDTO esito = new EsitoDTO();
		try {
			validazioneRendicontazioneDAO.annullaSospendiDocumentoSpesaValid(idDichiarazioneDiSpesa, idDocumentoDiSpesa, idProgetto,
					noteValidazione, userInfo.getIdUtente(), userInfo.getIdIride(), Boolean.FALSE);
			esito.setEsito(true);
			esito.setMessaggio("Sospensione documento annullata correttamente.");
		} catch (Exception e) {
			esito.setEsito(false);
			esito.setMessaggio("Sospensione documento NON annullata.");
		}
		return Response.ok().entity(esito).build();
	}
	
}
