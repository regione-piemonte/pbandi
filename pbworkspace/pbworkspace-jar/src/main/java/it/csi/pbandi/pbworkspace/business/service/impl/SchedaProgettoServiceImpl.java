/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbworkspace.business.service.impl;

import java.security.InvalidParameterException;


import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.csi.pbandi.pbservizit.security.UserInfoSec;
import it.csi.pbandi.pbworkspace.business.service.LineeDiFinanziamentoService;
import it.csi.pbandi.pbworkspace.business.service.SchedaProgettoService;
import it.csi.pbandi.pbworkspace.dto.schedaProgetto.SchedaProgetto;
import it.csi.pbandi.pbworkspace.integration.dao.LineeDiFinanziamentoDAO;
import it.csi.pbandi.pbworkspace.integration.dao.SchedaProgettoDAO;
import it.csi.pbandi.pbworkspace.integration.request.AvviaProgettiRequest;
import it.csi.pbandi.pbworkspace.integration.request.RicercaProgettiRequest;
import it.csi.pbandi.pbworkspace.integration.request.SalvaSchedaProgettoRequest;
import it.csi.pbandi.pbworkspace.util.Constants;


@Service
public class SchedaProgettoServiceImpl implements SchedaProgettoService {

	@Autowired
	SchedaProgettoDAO schedaProgettoDAO;
	
	@Override
	public Response inizializzaSchedaProgetto(Long progrBandoLineaIntervento, Long idProgetto, Long idSoggetto, String codiceRuolo, HttpServletRequest req) throws InvalidParameterException, Exception {
		UserInfoSec userInfo = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);		
		return Response.ok().entity(schedaProgettoDAO.inizializzaSchedaProgetto(progrBandoLineaIntervento, idProgetto, idSoggetto, codiceRuolo, userInfo.getIdUtente(), userInfo.getIdIride())).build();
	} 
	
	@Override
	public Response inizializzaCombo(Long progrBandoLineaIntervento, HttpServletRequest req) throws InvalidParameterException, Exception {
		UserInfoSec userInfo = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);		
		return Response.ok().entity(schedaProgettoDAO.inizializzaCombo(progrBandoLineaIntervento, userInfo.getIdUtente(), userInfo.getIdIride())).build();
	} 
	
	@Override
	public Response popolaComboAttivitaAteco(Long idSettoreAttivita, HttpServletRequest req) throws InvalidParameterException, Exception {
		UserInfoSec userInfo = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);		
		return Response.ok().entity(schedaProgettoDAO.popolaComboAttivitaAteco(userInfo.getIdUtente(), userInfo.getIdIride(), idSettoreAttivita)).build();
	}
	
	@Override
	public Response popolaComboObiettivoGeneraleQsn(Long idListaPrioritaQsn, HttpServletRequest req) throws InvalidParameterException, Exception {
		UserInfoSec userInfo = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);		
		return Response.ok().entity(schedaProgettoDAO.popolaComboObiettivoGeneraleQsn(userInfo.getIdUtente(), userInfo.getIdIride(), idListaPrioritaQsn)).build();
	}
	
	@Override
	public Response popolaComboObiettivoSpecificoQsn(Long idObiettivoGenerale, HttpServletRequest req) throws InvalidParameterException, Exception {
		UserInfoSec userInfo = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);		
		return Response.ok().entity(schedaProgettoDAO.popolaComboObiettivoSpecificoQsn(userInfo.getIdUtente(), userInfo.getIdIride(), idObiettivoGenerale)).build();
	}
	
	@Override
	public Response popolaComboClassificazioneRA(Long idObiettivoTematico, HttpServletRequest req) throws InvalidParameterException, Exception {
		UserInfoSec userInfo = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);		
		return Response.ok().entity(schedaProgettoDAO.popolaComboClassificazioneRA(userInfo.getIdUtente(), userInfo.getIdIride(), idObiettivoTematico)).build();
	}
	
	@Override
	public Response popolaComboSottoSettoreCipe(Long idSettoreCipe, HttpServletRequest req) throws InvalidParameterException, Exception {
		UserInfoSec userInfo = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);		
		return Response.ok().entity(schedaProgettoDAO.popolaComboSottoSettoreCipe(userInfo.getIdUtente(), userInfo.getIdIride(), idSettoreCipe)).build();
	}
	
	@Override
	public Response popolaComboCategoriaCipe(Long idSottoSettoreCipe, HttpServletRequest req) throws InvalidParameterException, Exception {
		UserInfoSec userInfo = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);		
		return Response.ok().entity(schedaProgettoDAO.popolaComboCategoriaCipe(userInfo.getIdUtente(), userInfo.getIdIride(), idSottoSettoreCipe)).build();
	}
	
	@Override
	public Response popolaComboTipologiaCipe(Long idNaturaCipe, HttpServletRequest req) throws InvalidParameterException, Exception {
		UserInfoSec userInfo = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);		
		return Response.ok().entity(schedaProgettoDAO.popolaComboTipologiaCipe(userInfo.getIdUtente(), userInfo.getIdIride(), idNaturaCipe)).build();
	}
	
	@Override
	public Response popolaComboRegione(HttpServletRequest req) throws InvalidParameterException, Exception {
		UserInfoSec userInfo = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);		
		return Response.ok().entity(schedaProgettoDAO.popolaComboRegione(userInfo.getIdUtente(), userInfo.getIdIride())).build();
	}
	
	@Override
	public Response popolaComboRegioneNascita(HttpServletRequest req) throws InvalidParameterException, Exception {
		UserInfoSec userInfo = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);		
		return Response.ok().entity(schedaProgettoDAO.popolaComboRegioneNascita(userInfo.getIdUtente(), userInfo.getIdIride())).build();
	}
	
	@Override
	public Response popolaComboProvincia(Long idRegione, HttpServletRequest req) throws InvalidParameterException, Exception {
		UserInfoSec userInfo = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);		
		return Response.ok().entity(schedaProgettoDAO.popolaComboProvincia(userInfo.getIdUtente(), userInfo.getIdIride(), idRegione)).build();
	}
	
	@Override
	public Response popolaComboProvinciaNascita(Long idRegione, HttpServletRequest req) throws InvalidParameterException, Exception {
		UserInfoSec userInfo = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);		
		return Response.ok().entity(schedaProgettoDAO.popolaComboProvinciaNascita(userInfo.getIdUtente(), userInfo.getIdIride(), idRegione)).build();
	}
	
	@Override
	public Response popolaComboComuneEstero(Long idNazione, HttpServletRequest req) throws InvalidParameterException, Exception {
		UserInfoSec userInfo = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);		
		return Response.ok().entity(schedaProgettoDAO.popolaComboComuneEstero(userInfo.getIdUtente(), userInfo.getIdIride(), idNazione)).build();
	}
	
	@Override
	public Response popolaComboComuneEsteroNascita(Long idNazione, HttpServletRequest req) throws InvalidParameterException, Exception {
		UserInfoSec userInfo = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);		
		return Response.ok().entity(schedaProgettoDAO.popolaComboComuneEsteroNascita(userInfo.getIdUtente(), userInfo.getIdIride(), idNazione)).build();
	}
	
	@Override
	public Response popolaComboComuneItaliano(Long idProvincia, HttpServletRequest req) throws InvalidParameterException, Exception {
		UserInfoSec userInfo = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);		
		return Response.ok().entity(schedaProgettoDAO.popolaComboComuneItaliano(userInfo.getIdUtente(), userInfo.getIdIride(), idProvincia)).build();
	}
	
	@Override
	public Response popolaComboComuneItalianoNascita(Long idProvincia, HttpServletRequest req) throws InvalidParameterException, Exception {
		UserInfoSec userInfo = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);		
		return Response.ok().entity(schedaProgettoDAO.popolaComboComuneItalianoNascita(userInfo.getIdUtente(), userInfo.getIdIride(), idProvincia)).build();
	}
	
	@Override
	public Response popolaComboDenominazioneSettori(Long idEnte, HttpServletRequest req) throws InvalidParameterException, Exception {
		UserInfoSec userInfo = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);		
		return Response.ok().entity(schedaProgettoDAO.popolaComboDenominazioneSettori(userInfo.getIdUtente(), userInfo.getIdIride(), idEnte)).build();
	}
	
	@Override
	public Response ricercaBeneficiarioCsp(String codiceFiscale, HttpServletRequest req) throws InvalidParameterException, Exception {
		UserInfoSec userInfo = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);		
		return Response.ok().entity(schedaProgettoDAO.ricercaBeneficiarioCsp(codiceFiscale, userInfo.getIdUtente(), userInfo.getIdIride())).build();
	}
	
	@Override
	public Response ricercaRapprLegaleCsp(String codiceFiscale, HttpServletRequest req) throws InvalidParameterException, Exception {
		UserInfoSec userInfo = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);		
		return Response.ok().entity(schedaProgettoDAO.ricercaRapprLegaleCsp(codiceFiscale, userInfo.getIdUtente(), userInfo.getIdIride())).build();
	}
	
	@Override
	public Response popolaComboDenominazioneEnteDipUni(Long idAteneo, HttpServletRequest req) throws InvalidParameterException, Exception {
		UserInfoSec userInfo = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);		
		return Response.ok().entity(schedaProgettoDAO.popolaComboDenominazioneEnteDipUni(userInfo.getIdUtente(), userInfo.getIdIride(), idAteneo)).build();
	}
	
	@Override
	public Response salvaSchedaProgetto(SalvaSchedaProgettoRequest salvaSchedaProgettoRequest, HttpServletRequest req) throws InvalidParameterException, Exception {
		UserInfoSec userInfo = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);		
		return Response.ok().entity(schedaProgettoDAO.salvaSchedaProgetto(salvaSchedaProgettoRequest, userInfo.getIdUtente(), userInfo.getIdIride())).build();
	}
	
	@Override
	public Response verificaNumeroDomandaUnico(String numDomanda, Long idProgetto, Long idBandoLinea, HttpServletRequest req) throws InvalidParameterException, Exception {		
		return Response.ok().entity(schedaProgettoDAO.verificaNumeroDomandaUnico(numDomanda, idProgetto, idBandoLinea)).build();
	}

	@Override
	public Response verificaCupUnico(String cup, Long idProgetto, HttpServletRequest req) throws InvalidParameterException, Exception {	
		return Response.ok().entity(schedaProgettoDAO.verificaCupUnico(cup, idProgetto)).build();
	}
	
	@Override
	public Response caricaInfoDirezioneRegionale(Long idEnteCompetenza, HttpServletRequest req) throws InvalidParameterException, Exception {
		UserInfoSec userInfo = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);		
		return Response.ok().entity(schedaProgettoDAO.caricaInfoDirezioneRegionale(idEnteCompetenza, userInfo.getIdUtente(), userInfo.getIdIride())).build();
	}
	
	@Override
	public Response caricaInfoPubblicaAmministrazione(Long idEnteCompetenza, HttpServletRequest req) throws InvalidParameterException, Exception {
		UserInfoSec userInfo = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);		
		return Response.ok().entity(schedaProgettoDAO.caricaInfoPubblicaAmministrazione(idEnteCompetenza, userInfo.getIdUtente(), userInfo.getIdIride())).build();
	}

}
