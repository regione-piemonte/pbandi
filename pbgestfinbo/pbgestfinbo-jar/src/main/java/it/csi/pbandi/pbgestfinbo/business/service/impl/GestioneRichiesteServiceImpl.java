/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.business.service.impl;

import java.math.BigDecimal;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.csi.pbandi.pbgestfinbo.business.service.GestioneRichiesteService;
import it.csi.pbandi.pbgestfinbo.dto.EsitoDTO;
import it.csi.pbandi.pbgestfinbo.exception.DaoException;
import it.csi.pbandi.pbgestfinbo.exception.ErroreGestitoException;
import it.csi.pbandi.pbgestfinbo.exception.RecordNotFoundException;
import it.csi.pbandi.pbgestfinbo.integration.dao.AnagraficaBeneficiarioDAO;
import it.csi.pbandi.pbgestfinbo.integration.dao.GestioneRichiesteDAO;
import it.csi.pbandi.pbgestfinbo.integration.vo.BloccoVO;
import it.csi.pbandi.pbgestfinbo.integration.vo.search.ElaboraRichiestaVO;
import it.csi.pbandi.pbgestfinbo.integration.vo.search.GestioneRichiesteVO;
import it.csi.pbandi.pbgestfinbo.integration.vo.search.NuovaRichiestaVO;
import it.csi.pbandi.pbgestfinbo.integration.vo.search.StoricoRichiestaVO;
import it.csi.pbandi.pbgestfinbo.integration.vo.search.SuggestionRichiesteVO;
import it.csi.pbandi.pbgestfinbo.util.Constants;
import it.csi.pbandi.pbservizit.dto.archivioFile.Esito;
import it.csi.pbandi.pbservizit.security.UserInfoSec;

@Service
public class GestioneRichiesteServiceImpl implements GestioneRichiesteService{

	private Logger LOG = Logger.getLogger(Constants.COMPONENT_NAME);

	@Autowired
	protected GestioneRichiesteDAO gestioneRichiesteDAO;
	
	@Autowired
	AnagraficaBeneficiarioDAO anaDao ; 
	

	@Override
	public Response getRichieste(HttpServletRequest req) throws DaoException {
		String prf = "[GestioneRichiesteServiceImpl::getRichieste]";		
		LOG.info(prf + " BEGIN");
		LOG.debug(prf + " BEGIN");

		List<GestioneRichiesteVO> response = gestioneRichiesteDAO.getRichieste(req);

		LOG.info(prf + " response prova: " + response.toString());
		LOG.info("[GestioneRichiesteServiceImpl::getRichieste] END");
		LOG.debug(prf + " END");

		return Response.ok().entity(response).build();
	}



	@Override
	public Response findRichieste(BigDecimal tipoRichiesta, BigDecimal statoRichiesta, String numeroDomanda, String codiceFondo,
			String richiedente, String ordinamento, String colonna, HttpServletRequest req) throws DaoException {
		String prf = "[GestioneRichiesteServiceImpl::findRichieste]";		
		LOG.info(prf + " BEGIN");
		LOG.debug(prf + " BEGIN");

		List<GestioneRichiesteVO> response = gestioneRichiesteDAO.findRichieste(tipoRichiesta, statoRichiesta, numeroDomanda, codiceFondo, richiedente, ordinamento, colonna, req);

		LOG.info(prf + " response prova: " + response.toString());
		LOG.info("[GestioneRichiesteServiceImpl::findRichieste] END");
		LOG.debug(prf + " END");

		return Response.ok().entity(response).build();
	}

	@Override
	public Response getDomandaNuovaRichiesta(String domandaNuovaRichiesta, HttpServletRequest req) throws DaoException {
		String prf = "[GestioneRichiesteServiceImpl::getDomandaNuovaRichiesta]";		
		LOG.info(prf + " BEGIN");
		LOG.debug(prf + " BEGIN");
		
		List<SuggestionRichiesteVO> response = gestioneRichiesteDAO.getDomandaNuovaRichiesta(domandaNuovaRichiesta, req);
		
		LOG.info(prf + " response prova: " + response.toString());
		LOG.info("[GestioneRichiesteServiceImpl::getDomandaNuovaRichiesta] END");
		LOG.debug(prf + " END");
		
		return Response.ok().entity(response).build();
	}



	@Override
	public Response getCodiceProgetto(String codiceProgetto, HttpServletRequest req) throws DaoException {
		String prf = "[GestioneRichiesteServiceImpl::getCodiceProgetto]";		
		LOG.info(prf + " BEGIN");
		LOG.debug(prf + " BEGIN");
		
		List<SuggestionRichiesteVO> response = gestioneRichiesteDAO.getCodiceProgetto(codiceProgetto, req);
		
		LOG.info(prf + " response prova: " + response.toString());
		LOG.info("[GestioneRichiesteServiceImpl::getCodiceProgetto] END");
		LOG.debug(prf + " END");
		
		return Response.ok().entity(response).build();
	}



	@Override
	public Response insertNuovaRichiesta(NuovaRichiestaVO nuovaRichiesta, HttpServletRequest req)
			throws ErroreGestitoException, RecordNotFoundException, DaoException {
		String prf = "[GestioneRichiesteServiceImpl::insertNuovaRichiesta]";		
		LOG.info(prf + " BEGIN");
		LOG.debug(prf + " BEGIN");
		
		// PRIMA DI INSERIRE UNA NUOVA RICHIESTA
		
		// CONTROLLO IL TIPO DI RICHIESTA :  SE E' DURC O DSAN IL FUNZIONAMENTO E' LO STESSO. 
		
//		In caso sia presente un DURC regolare (e trovo richieste in stato COMPLETATE o ANNULLATE) 
//		non deve essere possibile generare una nuova richiesta DURC e DSAN per qualsiasi domanda. 
//		non deve essere possibile inserire una nuova richiesta di DURC o DSAN neanche se esistono già richieste in fase DA ELABORARE o IN ELABORAZIONE
//
//
//		Posso inserire una nuova richiesta DSAN o DURC solo se non esistono richieste in stato DA ELABORARE o IN ELABORAZIONE. 
//		Nel caso in cui lo stato richiesta sia COMPLETATA, posso inserire una nuova richiesta solo se l'ESITO è regolare.
//
//		Posso inserire una nuova richiesta DSAN o DURC solo se non esistono richieste in stato DA ELABORARE o IN ELABORAZIONE.
//		Nel caso in cui lo stato richiesta sia COMPLETATA, posso inserire una nuova richiesta solo se l'ESITO è regolare e non è scaduto.
//
//		Posso inserire sempre una nuova richiesta se lo stato è COMPLETATO e il DURC ha esito negativo
//
//		Se esiste una richiesta in stato ANNULLATA posso inserire una nuova richiesta
//
//		INSERIMENTO NUOVA RICHIESTA DI TIPO DURC o DSAN:
//		Se esiste una richiesta ti tipo DURC o DSAN in stato da ELABORARE o IN ELABORAZIONE non posso inserire una nuova richiesta
//		se esistiono richieste di tipo DURC o DSAN in stato COMPLETATA o ANNULATA: non posso inserire una nuova richiesta se: lo stato è regolare e non è scaduto
		
		
		// NEL CASO DI ANTIMAFIA 
		
//		ANTIMAFIA
//
//		CR
//		1) se la richiesta per antimafia è in stato DA ELABORARE o IN ELABORAZIONE non deve essere possibile inserire nuove richieste per la stessa domanda
//		2) se la richiesta per antimafia è in stato IN ISTRUTTORIA BDNA non deve essere possibile inserire nuove richieste per la stessa domanda.
//		In caso in cui cambi la compagine societaria, riprendo sempre la stessa richiesta e la ripasso IN ELABORAZIONE (o posso lasciarla IN ISTRUTTORIA BDNA) 
//		e risalvare un nuovo documento e una nuova scadenza. su ALTRI DATI nella sezione anagrafica-domanda dovrò sempre prendere l'ultima inserita.
//		3) Se la richiesta è in stato COMPLETATA ed esiste una liberatoria antimafia con esito "NON SUSSISTONO IMPEDIMENTI" 
//		il sistema deve chiedere all'utente "ESISTE GIA' UNA LIBERATORIA ANTIMAFIA CON ESITO NON SUSSISTONO IMPEDIMENTI" 
//		sei sicura di volerla annullare e inserire una nuova richiesta? 
//		Se SI, il sistema deve andare a valorizzare la data fine validità della liberatoria antimafia ed inserire una nuova richiesta
//
//		1) se la richiesta per antimafia è in stato DA ELABORARE o IN ELABORAZIONE o IN ISTRUTTORIA BDNA 
//		non deve essere possibile inserire nuove richieste per la stessa domanda
//
//		2) Se la richiesta è in stato COMPLETATA ed esiste una liberatoria antimafia con esito "NON SUSSISTONO IMPEDIMENTI" 
//		il sistema deve chiedere all'utente "ESISTE GIA' UNA LIBERATORIA ANTIMAFIA CON ESITO NON SUSSISTONO IMPEDIMENTI" 
//		sei sicura di volerla annullare e inserire una nuova richiesta? Se SI, il sistema deve andare a valorizzare la data 
//		fine validità della liberatoria antimafia ed inserire una nuova richiesta
		
		
		
		// recupero dentro la tabella t_richiesta tutte le richieste 
		
		List<ElaboraRichiestaVO> elencoRichieste = gestioneRichiesteDAO.getRichiesteSoggetto(nuovaRichiesta.getCodiceFiscale(), nuovaRichiesta.getIdTipoRichiesta());
		// per ogni di queste richieste se esiste solo una in stato da elaborare o in elaborazione "non si può inserire una nuova richiesta"
	
		
		
		switch (nuovaRichiesta.getIdTipoRichiesta().intValue()) {
//		DURC or DSAN 
		case 1:
		case 2:
			// recuperare l'ultima richiesta durc per questo soggetto
			for (ElaboraRichiestaVO richiesta : elencoRichieste) {
				if(richiesta.getIdStatoRichiesta().trim().equals("1") || richiesta.getIdStatoRichiesta().trim().equals("2") ) {
					return inviaErroreBadRequest("Ci sono richieste "+ richiesta.getIdTipoRichiesta()+" in stato da elaborare o in elaborazione da completare");
				} 
			}

			ElaboraRichiestaVO ultimaRichiesta = new ElaboraRichiestaVO(); 
			ultimaRichiesta = gestioneRichiesteDAO.getRichiestaUltimaRichiestaDurc(nuovaRichiesta.getCodiceFiscale());
			if(ultimaRichiesta!=null) {				
				if(ultimaRichiesta.getEsito().trim().equals("1")) {
					// se il durc non è ancora scaduto ovvero dtscadenza  non posso inserire un nuovo!
					 if(ultimaRichiesta.getDataScadenza()!=null  &&  ultimaRichiesta.getDataScadenza()!=null &&  ultimaRichiesta.getDataScadenza().after(new Date()))
						return inviaErroreBadRequest("Non è possibile inserire una nuova richiesta perché il DURC non è scaduto!");
				}
			}
				ElaboraRichiestaVO ultimaRichiestaDsan = new ElaboraRichiestaVO(); 
			if(ultimaRichiestaDsan!=null) {					
					ultimaRichiestaDsan = gestioneRichiesteDAO.getRichiestaUltimaRichiestaDSan(nuovaRichiesta.getCodiceFiscale());
				if(ultimaRichiestaDsan!=null && ultimaRichiestaDsan.getDataScadenza()!=null &&  ultimaRichiestaDsan.getDataScadenza().after(new Date())){
					return inviaErroreBadRequest("Non è possibile inserire una nuova richiesta perché il DSAN non è scaduto!");
				}
			}
			break;

//		ANTIMAFIA
		case 3:
			for (ElaboraRichiestaVO richiesta : elencoRichieste) {
			
				if(richiesta.getIdStatoRichiesta().trim().equals("1") || richiesta.getIdStatoRichiesta().trim().equals("2") ) {
					return inviaErroreBadRequest("Ci sono richieste "+ richiesta.getIdTipoRichiesta()+" in stato da elaborare o in elaborazione da completare");
				} 
				if(richiesta.getIdStatoRichiesta().trim().equals("3")) {
					return inviaErroreBadRequest("Ci sono richieste "+ richiesta.getIdTipoRichiesta()+" in IN ISTRUTTORIA BDNA!");
				}
				if(richiesta.getIdStatoRichiesta().trim().equals("4")) {
					ElaboraRichiestaVO richiestaAntimafia = gestioneRichiesteDAO.getRichiesteAntimafia(nuovaRichiesta.getNumeroDomanda());
				
					if(richiestaAntimafia !=null ) {						
						if(richiestaAntimafia.getEsito().trim().equals("3") || richiestaAntimafia.getEsito().trim().equals("4") ) {
							EsitoDTO esito = new EsitoDTO(); 
							esito.setId(Long.parseLong(richiestaAntimafia.getIdRichiesta()));
							esito.setEsito(false);
							esito.setMessaggio("ESISTE GIA' UNA LIBERATORIA ANTIMAFIA CON ESITO NON SUSSISTONO IMPEDIMENTI / SUSSISTONO IMPEDIMENTI: sei sicura di volerla annullare e inserire una nuova richiesta?");
							return Response.ok(esito).build();
						}
					}
					
				}
			}
			break;

		default:
			break;
		}
		
		int response = gestioneRichiesteDAO.insertNuovaRichiesta(nuovaRichiesta, req);
		
		LOG.info(prf + " response prova: " + response);
		LOG.info("[GestioneRichiesteServiceImpl::insertNuovaRichiesta] END");
		LOG.debug(prf + " END");
		
		EsitoDTO esito = new EsitoDTO(); 
		esito.setId((long)(response));
		esito.setEsito(true);
		esito.setMessaggio("Richiesta inserita con successo!");

		
		return Response.ok().entity(esito).build();
	}



	@Override
	public Response getStoricoRichieste(String idRichiesta, HttpServletRequest req) throws DaoException {
		String prf = "[GestioneRichiesteServiceImpl::getStoricoRichieste]";		
		LOG.info(prf + " BEGIN");
		LOG.debug(prf + " BEGIN");

		List<StoricoRichiestaVO> response = gestioneRichiesteDAO.getStoricoRichiesta(idRichiesta,req);

		LOG.info(prf + " response prova: " + response.toString());
		LOG.info("[GestioneRichiesteServiceImpl::getStoricoRichieste] END");
		LOG.debug(prf + " END");

		return Response.ok().entity(response).build();
	}



	@Override
	public Response getRichiesta(String idRichiesta, HttpServletRequest req) throws DaoException {
		String prf = "[GestioneRichiesteServiceImpl::getRichiesta]";		
		LOG.info(prf + " BEGIN");
		LOG.debug(prf + " BEGIN");

		List<ElaboraRichiestaVO> response = gestioneRichiesteDAO.getRichiesta(idRichiesta,req);

		LOG.info(prf + " response prova: " + response.toString());
		LOG.info("[GestioneRichiesteServiceImpl::getRichiesta] END");
		LOG.debug(prf + " END");

		return Response.ok().entity(response).build();
	}

	@Override
	public Response elaboraRichiesta(ElaboraRichiestaVO elaboraRichiesta, HttpServletRequest req) throws DaoException {

		String prf = "[GestioneRichiesteServiceImpl::elaboraRichiesta]";		
		LOG.info(prf + " BEGIN");
		LOG.debug(prf + " BEGIN");
		
		int response = gestioneRichiesteDAO.elaboraRichiesta(elaboraRichiesta, req);
		
		LOG.info(prf + " response prova: " + response);
		LOG.info("[GestioneRichiesteServiceImpl::elaboraRichiesta] END");
		LOG.debug(prf + " END");
		
		return Response.ok().entity(response).build();
	}



	@Override
	public Response elaboraDurc(ElaboraRichiestaVO elaboraDurc,  boolean isDocumento, HttpServletRequest req) throws DaoException {
		String prf = "[GestioneRichiesteServiceImpl::elaboraDurc]";		
		LOG.info(prf + " BEGIN");
		LOG.debug(prf + " BEGIN");
		UserInfoSec userInfoSec = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);
		// recupero lo stato precedente della richiesta
		String  idPreviousState = gestioneRichiesteDAO.getPreviousState(elaboraDurc.getIdRichiesta()); 
		
		BigDecimal response = gestioneRichiesteDAO.elaboraDurc(elaboraDurc, req);

		if(isDocumento==false) {			
			if(elaboraDurc.getIdStatoRichiesta().trim() != idPreviousState.trim() ) {
				// chiamata al servizio di finistr senza documento	
				boolean responseCall =	gestioneRichiesteDAO.callToFinistr(elaboraDurc, null, null);
				LOG.info(responseCall);
			}
		}
		
		if(elaboraDurc.getEsito().trim().equals("1")) {			
			// recupero tutti i blocchi 	
			List<BloccoVO> elencoBlocchi = new ArrayList<BloccoVO>();
			
			elencoBlocchi= anaDao.getElencoBlocchi(new BigDecimal(elaboraDurc.getNag()),false)
					.stream().filter( b -> b.getIdCausaleBlocco()==2)
					.collect(Collectors.toList());
			
			if(elencoBlocchi.size()>0) {
					boolean res =false;
					BloccoVO blocco = new BloccoVO(); 
					blocco = elencoBlocchi.get(0);
					blocco.setIdUtente(new BigDecimal(userInfoSec.getIdUtente()));
					res	 = anaDao.modificablocco(blocco);
					if(res) {
						LOG.info("Blocchi rimossi con successo!");
					} 
			}
		}
		
		LOG.info(prf + " response prova: " + response);
		LOG.info("[GestioneRichiesteServiceImpl::elaboraDurc] END");
		LOG.debug(prf + " END");
		
		return Response.ok().entity(response).build();
	}


	@Override
	public Response elaboraBdna(ElaboraRichiestaVO elaboraBdna, boolean isDocumento, HttpServletRequest req) throws DaoException {
		String prf = "[GestioneRichiesteServiceImpl::elaboraBdna]";		
		LOG.info(prf + " BEGIN");
		LOG.debug(prf + " BEGIN");
		UserInfoSec userInfoSec = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);
		String  idPreviousState = gestioneRichiesteDAO.getPreviousState(elaboraBdna.getIdRichiesta()); 
		
		BigDecimal response = gestioneRichiesteDAO.elaboraBdna(elaboraBdna, req);
		
		// recupero tutti i blocchi 	
		List<BloccoVO> elencoBlocchi = new ArrayList<BloccoVO>();
		
		elencoBlocchi= anaDao.getElencoBlocchiSoggettoDomanda((elaboraBdna.getNag()), elaboraBdna.getNumeroDomanda(), false)
				.stream().filter( b -> b.getIdCausaleBlocco()==15)
				.collect(Collectors.toList());
		
		if(elencoBlocchi.size()>0) {
			for (BloccoVO bloccoVO : elencoBlocchi) {
				bloccoVO.setIdUtente(new BigDecimal(userInfoSec.getIdUtente()));
				boolean res =false;
				res	 = anaDao.updateBloccoDomanda(bloccoVO, elaboraBdna.getNumeroDomanda());
				if(res) {
					LOG.info("Blocchi rimossi con successo!");
					} 
			}
		}
		
	
		if(isDocumento==false) {			
			if(elaboraBdna.getIdStatoRichiesta().trim()!=idPreviousState.trim()) {
				// chiamata al servizio di finistr senza documento	
				boolean responseCall =	gestioneRichiesteDAO.callToFinistr(elaboraBdna, null, null);
				LOG.info(responseCall);
			}
		}
		LOG.info(prf + " response prova: " + response);
		LOG.info("[GestioneRichiesteServiceImpl::elaboraBdna] END");
		LOG.debug(prf + " END");
		
		return Response.ok().entity(response).build();
	}



	@Override
	public Response elaboraAntimafia(ElaboraRichiestaVO elaboraAntimafia,  boolean isDocumento, HttpServletRequest req) throws DaoException {
		String prf = "[GestioneRichiesteServiceImpl::elaboraAntimafia]";		
		LOG.info(prf + " BEGIN");
		LOG.debug(prf + " BEGIN");
		UserInfoSec userInfoSec = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);
		String  idPreviousState = gestioneRichiesteDAO.getPreviousState(elaboraAntimafia.getIdRichiesta()); 
		
		BigDecimal response = gestioneRichiesteDAO.elaboraAntimafia(elaboraAntimafia, req);
		
		if(elaboraAntimafia.getEsito().trim().equals("3")) {			
			// recupero tutti i blocchi 	
			List<BloccoVO> elencoBlocchi = new ArrayList<BloccoVO>();
			
			elencoBlocchi= anaDao.getElencoBlocchiSoggettoDomanda((elaboraAntimafia.getNag()), elaboraAntimafia.getNumeroDomanda(), false)
					.stream().filter( b -> b.getIdCausaleBlocco()==15)
					.collect(Collectors.toList());
			
			if(elencoBlocchi.size()>0) {
				for (BloccoVO bloccoVO : elencoBlocchi) {
					bloccoVO.setIdUtente(new BigDecimal(userInfoSec.getIdUtente()));
					boolean res =false;
					res	 = anaDao.updateBloccoDomanda(bloccoVO, elaboraAntimafia.getNumeroDomanda());
					if(res) {
						LOG.info("Blocchi rimossi con successo!");
					} 
				}
			}
		}
		
		LOG.info(prf + " response prova: " + response);
		LOG.info("[GestioneRichiesteServiceImpl::elaboraAntimafia] END");
		LOG.debug(prf + " END");
		
		if(isDocumento==false) {			
			if(elaboraAntimafia.getIdStatoRichiesta().trim () != idPreviousState.trim() ) {
				// chiamata al servizio di finistr senza documento	
				boolean responseCall =	gestioneRichiesteDAO.callToFinistr(elaboraAntimafia, null, null);
				LOG.info(responseCall);
			}
		}
	
		
		return Response.ok().entity(response).build();
	}



	@Override
	public Response getSuggestion(String value, String id, HttpServletRequest req) throws DaoException {
		String prf = "[GestioneRichiesteServiceImpl::getSuggestion]";		
		LOG.info(prf + " BEGIN");
		LOG.debug(prf + " BEGIN");

		List<String> response = gestioneRichiesteDAO.getSuggestion(value, id,req);

		LOG.info(prf + " response prova: " + response.toString());
		LOG.info("[GestioneRichiesteServiceImpl::getRichiesta] END");
		LOG.debug(prf + " END");

		return Response.ok().entity(response).build();
	}


	@Override
	public Response salvaUploadAntiMafia(MultipartFormDataInput multipartFormData, HttpServletRequest req)
			throws InvalidParameterException, Exception {
		return Response.ok(gestioneRichiesteDAO.salvaUploadAntiMafia(multipartFormData)).build();
	}



	@Override
	public Response salvaUploadDurc(MultipartFormDataInput multipartFormData, HttpServletRequest req)
			throws InvalidParameterException, Exception {
		return Response.ok(gestioneRichiesteDAO.salvaUploadDurc(multipartFormData)).build();
	}



	@Override
	public Response salvaUploadBdna(MultipartFormDataInput multipartFormData, HttpServletRequest req)
			throws InvalidParameterException, Exception {
		// TODO Auto-generated method stub
		return Response.ok(gestioneRichiesteDAO.salvaUploadBdna(multipartFormData)).build();
	}



	@Override
	public Response annullaRichiesta(ElaboraRichiestaVO elaboraRichiesta, HttpServletRequest req) throws DaoException {
		return Response.ok(gestioneRichiesteDAO.annullaRichiesta(elaboraRichiesta)).build();
	}



	@Override
	public Response getTipoRichiesta() throws DaoException {
		return Response.ok(gestioneRichiesteDAO.elencoTipoRichieste()).build();
	}


	private Response inviaErroreBadRequest(String msg) {
		EsitoDTO esito = new EsitoDTO();
		esito.setEsito(false);
		esito.setMessaggio(msg);
		return Response.ok(esito).type( MediaType.APPLICATION_JSON).build();
	}



	@Override
	public Response annullaRichiestaAntimafia(String idRichiesta, HttpServletRequest req) throws DaoException {
		
		Boolean result = gestioneRichiesteDAO.annullaRichiestaAntimafia(idRichiesta, req); 
		if(result == false) {
			return inviaErroreBadRequest("errore durante annullamento della richiesta antimafia!");
		}
		EsitoDTO esito = new EsitoDTO();
		esito.setEsito(true);
		esito.setMessaggio("Annullamento avvenuto con successo!");
		
		return  Response.ok(esito).build();
	}

}
