/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebrce.business.service.impl;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.csi.pbandi.pbservizit.dto.archivioFile.EsitoLeggiFile;
import it.csi.pbandi.pbservizit.integration.dao.request.AssociaFilesRequest;
import it.csi.pbandi.pbservizit.pbandisrv.business.gestionedatigenerali.GestioneDatiGeneraliBusinessImpl;
import it.csi.pbandi.pbservizit.pbandisrv.dto.gestionedatigenerali.TipoAllegatoDTO;
import it.csi.pbandi.pbservizit.security.UserInfoSec;
import it.csi.pbandi.pbwebrce.util.Constants;
import it.csi.pbandi.pbwebrce.business.service.ContoEconomicoService;
import it.csi.pbandi.pbwebrce.integration.dao.ContoEconomicoDAO;
import it.csi.pbandi.pbwebrce.integration.request.InizializzaConcludiPropostaRimodulazioneRequest;
import it.csi.pbandi.pbwebrce.integration.request.InviaPropostaRimodulazioneRequest;
import it.csi.pbandi.pbwebrce.integration.request.SalvaPropostaRimodulazioneConfermataRequest;
import it.csi.pbandi.pbwebrce.integration.request.SalvaPropostaRimodulazioneRequest;
import it.csi.pbandi.pbwebrce.integration.request.SalvaRichiestaContoEconomicoRequest;
import it.csi.pbandi.pbwebrce.integration.request.SalvaRimodulazioneConfermataRequest;
import it.csi.pbandi.pbwebrce.integration.request.SalvaRimodulazioneRequest;
import it.csi.pbandi.pbwebrce.integration.request.ValidaRimodulazioneConfermataRequest;


@Service
public class ContoEconomicoServiceImpl implements ContoEconomicoService {
	
	@Autowired
	ContoEconomicoDAO contoEconomicoDAO;
	
	@Autowired
	GestioneDatiGeneraliBusinessImpl gestioneDatiGeneraliBusinessImpl;
	
	@Override 
	public Response inizializzaPropostaRimodulazione (Long idProgetto, HttpServletRequest req) throws InvalidParameterException, Exception {
		UserInfoSec userInfo = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);		
		return Response.ok().entity(contoEconomicoDAO.inizializzaPropostaRimodulazione(idProgetto, userInfo.getIdUtente(),userInfo.getIdIride())).build();
	}
	
	@Override 
	public Response inizializzaPropostaRimodulazioneInDomanda (Long idProgetto, HttpServletRequest req) throws InvalidParameterException, Exception {
		UserInfoSec userInfo = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);		
		return Response.ok().entity(contoEconomicoDAO.inizializzaPropostaRimodulazioneInDomanda(idProgetto, userInfo.getIdUtente(),userInfo.getIdIride())).build();
	}
	
	@Override 
	public Response contoEconomicoLocked (Long idProgetto, HttpServletRequest req) throws InvalidParameterException, Exception {
		UserInfoSec userInfo = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);		
		return Response.ok().entity(contoEconomicoDAO.contoEconomicoLocked(idProgetto, userInfo.getIdUtente(),userInfo.getIdIride())).build();
	}
	
	@Override 
	public Response salvaPropostaRimodulazione (SalvaPropostaRimodulazioneRequest salvaPropostaRimodulazioneRequest, HttpServletRequest req) throws InvalidParameterException, Exception {
		UserInfoSec userInfo = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);		
		return Response.ok().entity(contoEconomicoDAO.salvaPropostaRimodulazione(salvaPropostaRimodulazioneRequest, userInfo.getIdUtente(),userInfo.getIdIride())).build();
	}
	
	@Override 
	public Response salvaPropostaRimodulazioneConfermata (SalvaPropostaRimodulazioneRequest salvaPropostaRimodulazioneRequest, HttpServletRequest req) throws InvalidParameterException, Exception {
		UserInfoSec userInfo = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);		
		return Response.ok().entity(contoEconomicoDAO.salvaPropostaRimodulazioneConfermata(salvaPropostaRimodulazioneRequest, userInfo.getIdUtente(),userInfo.getIdIride())).build();
	}
	
	@Override 
	public Response inizializzaConcludiPropostaRimodulazione (Long idProgetto, HttpServletRequest req) throws InvalidParameterException, Exception {
		UserInfoSec userInfo = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);		
		return Response.ok().entity(contoEconomicoDAO.inizializzaConcludiPropostaRimodulazione(idProgetto, userInfo.getIdUtente(),userInfo.getIdIride())).build();
	}
	
	@Override
	public Response associaAllegatiAPropostaRimodulazione(AssociaFilesRequest associaFilesRequest, HttpServletRequest req) throws InvalidParameterException, Exception {
		UserInfoSec userInfo = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);
		return Response.ok().entity(contoEconomicoDAO.associaAllegatiAPropostaRimodulazione(associaFilesRequest, userInfo.getIdUtente())).build();
	}
	
	@Override
	public Response disassociaAllegatoPropostaRimodulazione(Long idDocumentoIndex, Long idProgetto, HttpServletRequest req) throws InvalidParameterException, Exception {
		UserInfoSec userInfo = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);
		return Response.ok().entity(contoEconomicoDAO.disassociaAllegatoPropostaRimodulazione(idDocumentoIndex, idProgetto, userInfo.getIdUtente())).build();
	}
	
	@Override
	public Response allegatiPropostaRimodulazione(Long idProgetto, HttpServletRequest req) throws InvalidParameterException, Exception {
		UserInfoSec userInfo = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);
		return Response.ok().entity(contoEconomicoDAO.allegatiPropostaRimodulazione(idProgetto, userInfo.getIdUtente())).build();
	}
	
	@Override 
	public Response inviaPropostaRimodulazione(InviaPropostaRimodulazioneRequest inviaPropostaRimodulazioneRequest, HttpServletRequest req) throws InvalidParameterException, Exception {
		UserInfoSec userInfo = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);		
		return Response.ok().entity(contoEconomicoDAO.inviaPropostaRimodulazione(inviaPropostaRimodulazioneRequest, userInfo.getIdUtente(),userInfo.getIdIride())).build();
	}
	
	@Override 
	public Response inizializzaUploadFileFirmato(Long idProgetto, HttpServletRequest req) throws InvalidParameterException, Exception {
		UserInfoSec userInfo = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);		
		return Response.ok().entity(contoEconomicoDAO.inizializzaUploadFileFirmato(idProgetto, userInfo.getIdUtente(),userInfo.getIdIride())).build();
	}
	
	@Override
	public Response salvaFileFirmato(MultipartFormDataInput multipartFormData, HttpServletRequest req) throws InvalidParameterException, Exception {		
		UserInfoSec userInfo = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);
		return Response.ok().entity(contoEconomicoDAO.salvaFileFirmato(multipartFormData, userInfo.getIdUtente(), userInfo.getIdIride())).build();
	}
	
	@Override
	public Response verificaFirmaDigitale(Long idDocumentoIndex, HttpServletRequest req) throws InvalidParameterException, Exception {		
		UserInfoSec userInfo = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);
		return Response.ok().entity(contoEconomicoDAO.verificaFirmaDigitale(idDocumentoIndex, userInfo.getIdUtente(), userInfo.getIdIride())).build();
	}
	
	@Override
	public Response salvaInvioCartaceo(Boolean invioCartaceo, Long idDocumentoIndex, HttpServletRequest req) throws InvalidParameterException, Exception {		
		UserInfoSec userInfo = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);
		return Response.ok().entity(contoEconomicoDAO.salvaInvioCartaceo(invioCartaceo, idDocumentoIndex, userInfo.getIdUtente(), userInfo.getIdIride())).build();
	}
	
	@Override 
	public Response inizializzaRimodulazione (Long idProgetto, HttpServletRequest req) throws InvalidParameterException, Exception {
		UserInfoSec userInfo = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);		
		return Response.ok().entity(contoEconomicoDAO.inizializzaRimodulazione(idProgetto, userInfo.getIdUtente(),userInfo.getIdIride())).build();
	}
	
	@Override 
	public Response salvaRimodulazione(SalvaRimodulazioneRequest salvaRimodulazioneRequest, HttpServletRequest req) throws InvalidParameterException, Exception {
		UserInfoSec userInfo = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);		
		return Response.ok().entity(contoEconomicoDAO.salvaRimodulazione(salvaRimodulazioneRequest, userInfo.getIdUtente(),userInfo.getIdIride())).build();
	}
	
	@Override 
	public Response inizializzaConcludiRimodulazione (Long idProgetto, Long idBandoLinea, HttpServletRequest req) throws InvalidParameterException, Exception {
		UserInfoSec userInfo = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);		
		return Response.ok().entity(contoEconomicoDAO.inizializzaConcludiRimodulazione(idProgetto, idBandoLinea, userInfo.getIdUtente(),userInfo.getIdIride())).build();
	}
	
	@Override 
	public Response salvaRimodulazioneConfermata (SalvaRimodulazioneConfermataRequest salvaRimodulazioneConfermataRequest, HttpServletRequest req) throws InvalidParameterException, Exception {
		UserInfoSec userInfo = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);		
		return Response.ok().entity(contoEconomicoDAO.salvaRimodulazioneConfermata(salvaRimodulazioneConfermataRequest, userInfo.getIdUtente(),userInfo.getIdIride())).build();
	}
	
	@Override 
	public Response validaRimodulazioneConfermata (ValidaRimodulazioneConfermataRequest validaRimodulazioneConfermataRequest, HttpServletRequest req) throws InvalidParameterException, Exception {
		UserInfoSec userInfo = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);		
		return Response.ok().entity(contoEconomicoDAO.validaRimodulazioneConfermata(validaRimodulazioneConfermataRequest, userInfo.getIdUtente(),userInfo.getIdIride())).build();
	}
	
	@Override 
	public Response scaricaRimodulazione (Long idProgetto, Long idSoggettoBeneficiario, Long idContoEconomico, HttpServletRequest req) throws InvalidParameterException, Exception {
		UserInfoSec userInfo = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);
		EsitoLeggiFile esito = contoEconomicoDAO.scaricaRimodulazione(idProgetto, idSoggettoBeneficiario, idContoEconomico, userInfo.getIdUtente(),userInfo.getIdIride());
		return Response.ok().header("header-nome-file", esito.getNomeFile()).entity(esito.getBytes()).build();
	}
	
	@Override 
	public Response inizializzaRimodulazioneIstruttoria (Long idProgetto, HttpServletRequest req) throws InvalidParameterException, Exception {
		UserInfoSec userInfo = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);		
		return Response.ok().entity(contoEconomicoDAO.inizializzaRimodulazioneIstruttoria(idProgetto, userInfo.getIdUtente(),userInfo.getIdIride())).build();
	}
	
	@Override 
	public Response validaRimodulazioneIstruttoria(SalvaRimodulazioneRequest salvaRimodulazioneRequest, HttpServletRequest req) throws InvalidParameterException, Exception {
		UserInfoSec userInfo = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);		
		return Response.ok().entity(contoEconomicoDAO.validaRimodulazioneIstruttoria(salvaRimodulazioneRequest, userInfo.getIdUtente(),userInfo.getIdIride())).build();
	}
	
	@Override 
	public Response salvaRimodulazioneIstruttoria(SalvaRimodulazioneRequest salvaRimodulazioneRequest, HttpServletRequest req) throws InvalidParameterException, Exception {
		UserInfoSec userInfo = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);		
		return Response.ok().entity(contoEconomicoDAO.salvaRimodulazioneIstruttoria(salvaRimodulazioneRequest, userInfo.getIdUtente(),userInfo.getIdIride())).build();
	}
	
	@Override 
	public Response inizializzaConcludiRimodulazioneIstruttoria (Long idProgetto, HttpServletRequest req) throws InvalidParameterException, Exception {
		UserInfoSec userInfo = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);		
		return Response.ok().entity(contoEconomicoDAO.inizializzaConcludiRimodulazioneIstruttoria(idProgetto, userInfo.getIdUtente(),userInfo.getIdIride())).build();
	}

	@Override 
	public Response salvaRimodulazioneIstruttoriaConfermata (SalvaRimodulazioneConfermataRequest salvaRimodulazioneConfermataRequest, HttpServletRequest req) throws InvalidParameterException, Exception {
		UserInfoSec userInfo = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);		
		return Response.ok().entity(contoEconomicoDAO.salvaRimodulazioneIstruttoriaConfermata(salvaRimodulazioneConfermataRequest, userInfo.getIdUtente(),userInfo.getIdIride())).build();
	}
	
	@Override 
	public Response validaPropostaRimodulazioneInDomanda (SalvaPropostaRimodulazioneRequest salvaPropostaRimodulazioneRequest, HttpServletRequest req) throws InvalidParameterException, Exception {
		UserInfoSec userInfo = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);		
		return Response.ok().entity(contoEconomicoDAO.validaPropostaRimodulazioneInDomanda(salvaPropostaRimodulazioneRequest, userInfo.getIdUtente(),userInfo.getIdIride())).build();
	}
	
	@Override 
	public Response salvaPropostaRimodulazioneInDomanda (SalvaPropostaRimodulazioneRequest salvaPropostaRimodulazioneRequest, HttpServletRequest req) throws InvalidParameterException, Exception {
		UserInfoSec userInfo = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);		
		return Response.ok().entity(contoEconomicoDAO.salvaPropostaRimodulazioneInDomanda(salvaPropostaRimodulazioneRequest, userInfo.getIdUtente(),userInfo.getIdIride())).build();
	}
	
	@Override 
	public Response inizializzaConcludiRichiestaContoEconomico (Long idProgetto, HttpServletRequest req) throws InvalidParameterException, Exception {
		UserInfoSec userInfo = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);		
		return Response.ok().entity(contoEconomicoDAO.inizializzaConcludiRichiestaContoEconomico(idProgetto, userInfo.getIdUtente(),userInfo.getIdIride())).build();
	}
	
	@Override 
	public Response salvaRichiestaContoEconomico (SalvaRichiestaContoEconomicoRequest salvaRichiestaContoEconomicoRequest, HttpServletRequest req) throws InvalidParameterException, Exception {
		UserInfoSec userInfo = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);		
		return Response.ok().entity(contoEconomicoDAO.salvaRichiestaContoEconomico(salvaRichiestaContoEconomicoRequest, userInfo.getIdUtente(),userInfo.getIdIride())).build();
	}

	@Override
	public Response getAltriCosti(Long idBandoLinea, Long idProgetto, HttpServletRequest req)
			throws InvalidParameterException, Exception {
		UserInfoSec userInfo = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);		
		return Response.ok().entity(contoEconomicoDAO.getAltriCosti(idBandoLinea, idProgetto, userInfo.getIdUtente(),userInfo.getIdIride())).build();
	}

	@Override
	public Response getTipiAllegatiProposta(Long idBandoLinea, Long idProgetto, HttpServletRequest req)
			throws InvalidParameterException, Exception {
		UserInfoSec userInfo = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);		
		TipoAllegatoDTO[] tipi =  gestioneDatiGeneraliBusinessImpl.findTipoAllegati(userInfo.getIdUtente(),userInfo.getIdIride(), idBandoLinea,
				Constants.COD_TIPO_DOCUMENTO_INDEX_PROPOSTA_RIMODULAZIONE, null, idProgetto );
		if (tipi != null) {
			for(TipoAllegatoDTO dto : tipi) {
				if (dto.getDescTipoAllegato().startsWith("<b>")) {
					dto.setFlagAllegato("S");
				}
			}
		}
		return Response.ok().entity(tipi).build();
	}
	
}
