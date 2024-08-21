/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservrest.business.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Propagation;
import it.csi.pbandi.pbservrest.business.service.CupApi;
import it.csi.pbandi.pbservrest.dto.Esito;
import it.csi.pbandi.pbservrest.dto.RequestCupVO;
import it.csi.pbandi.pbservrest.exception.RecordNotFoundException;
import it.csi.pbandi.pbservrest.integration.dao.CupDAO;
import it.csi.pbandi.pbservrest.util.Constants;

@Transactional(timeout = 240, propagation = Propagation.REQUIRES_NEW, rollbackFor = { Exception.class })
@Component
public class CupApiImpl extends BaseApiServiceImpl implements CupApi {

	private Logger LOG = Logger.getLogger(Constants.COMPONENT_NAME);
	
	@Autowired
	CupDAO cupDAO;
	
	@Override
	public Response acquisizioneCup(RequestCupVO requestCupVO, SecurityContext securityContext, HttpHeaders httpHeaders,
			HttpServletRequest httpRequest) {
		String prf = "[CupApiImpl::acquisizioneCup]";
		LOG.info(prf + " BEGIN");
		
		List<Esito> esitoList = new ArrayList<Esito>();
		
		try {
			
			LOG.info(prf + " CodiceCup="+requestCupVO.getCodiceCup());
			LOG.info(prf + " NumeroDomanda="+requestCupVO.getNumeroDomanda());
			
			if( requestCupVO.getCodiceCup()==null 
				|| requestCupVO.getNumeroDomanda()==null
				|| (requestCupVO.getCodiceCup()!=null && requestCupVO.getCodiceCup().isEmpty()) 
				|| (requestCupVO.getNumeroDomanda()!=null && requestCupVO.getNumeroDomanda().isEmpty())){
				LOG.error(prf + " [Errore bloccante: parametro obbligatorio mancante] ");
				Esito esito = new Esito();
				esito.setDescErrore(Constants.MESSAGGI.ERRORE.PARAMETRI_TUTTI_OBBLIGATORI);
				esito.setCodiceErrore(003);
				esito.setEsitoServizio(Constants.ESITO.KO);
				esitoList.add(esito);
				return Response.status(Response.Status.NOT_ACCEPTABLE).entity(esitoList).build();
			}
			
			/* eseguo test di connessione al database */
			if(!cupDAO.testConnection()) {
				LOG.error(prf + " [Errore sistemistico bloccante: 003] ");
				Esito esito = new Esito();
				esito.setDescErrore(Constants.MESSAGGI.ERRORE.ERRORE_CONESSIONE_DATABASE);
				esito.setCodiceErrore(003);
				esito.setEsitoServizio(Constants.ESITO.KO);
				esitoList.add(esito);
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(esitoList).build();
			}
			
			//verifico esistenza domanda
			if( cupDAO.domandaExist(requestCupVO.getNumeroDomanda()) ) {
				
				// se domanda esiste, aggiorno il codiceCUP
				boolean cupAggiornato = cupDAO.aggiornaCodiceCUP(requestCupVO.getCodiceCup(),requestCupVO.getNumeroDomanda());
				LOG.info(prf + " cupAggiornato="+cupAggiornato);
				if (!cupAggiornato) {
					LOG.warn(prf + " domanda non trovata sul DB");
					Esito esito = new Esito();
					esito.setDescErrore(Constants.MESSAGGI.ERRORE.AGGIORNAMENTO_CUP_FALLITO);
					esito.setCodiceErrore(003);
					esito.setEsitoServizio(Constants.ESITO.KO);
					esitoList.add(esito);
					return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(esitoList).build();
				}
				
			}else {
				LOG.warn(prf + " domanda non trovata sul DB");
				Esito esito = new Esito();
				esito.setDescErrore(Constants.MESSAGGI.ERRORE.DOMANDA_NON_TROVATA);
				esito.setCodiceErrore(003);
				esito.setEsitoServizio(Constants.ESITO.KO);
				esitoList.add(esito);
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(esitoList).build();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error(prf + "Exception while trying to read acquisizioneCup", e);
			esitoList = setErrore006();
			LOG.error(" err006= " + esitoList);
			
		} finally {
			LOG.info(prf + " END");
		}
		return Response.status(Response.Status.OK).entity(esitoList).build();
	}

}
