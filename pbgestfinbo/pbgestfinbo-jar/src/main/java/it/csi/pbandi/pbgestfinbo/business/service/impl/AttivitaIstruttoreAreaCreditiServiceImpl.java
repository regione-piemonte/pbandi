/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.business.service.impl;

import java.io.InputStream;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.csi.pbandi.pbgestfinbo.business.service.AttivitaIstruttoreAreaCreditiService;
import it.csi.pbandi.pbgestfinbo.business.service.RicercaGaranzieService;
import it.csi.pbandi.pbgestfinbo.dto.EsitoDTO;
import it.csi.pbandi.pbgestfinbo.dto.GestioneAllegatiVO;
import it.csi.pbandi.pbgestfinbo.dto.utils.ResponseCodeMessage;
import it.csi.pbandi.pbgestfinbo.exception.ErroreGestitoException;
import it.csi.pbandi.pbgestfinbo.exception.RecordNotFoundException;
import it.csi.pbandi.pbgestfinbo.integration.dao.AttivitaIstruttoreAreaCreditiDAO;
import it.csi.pbandi.pbgestfinbo.integration.dao.IterAutorizzativiDAO;
import it.csi.pbandi.pbgestfinbo.integration.dao.RicercaBeneficiariCreditiDAO;
import it.csi.pbandi.pbgestfinbo.integration.dao.RicercaGaranzieDAO;
import it.csi.pbandi.pbgestfinbo.integration.dao.impl.IterAutorizzativiDAOImpl;
import it.csi.pbandi.pbgestfinbo.integration.vo.IscrizioneRuoloVO;
import it.csi.pbandi.pbgestfinbo.integration.vo.LavorazioneBoxListVO;
import it.csi.pbandi.pbgestfinbo.integration.vo.NoteGeneraliVO;
import it.csi.pbandi.pbgestfinbo.integration.vo.RichiestaIntegrazioneVO;
import it.csi.pbandi.pbgestfinbo.integration.vo.VisualizzaAllegatiVO;
import it.csi.pbandi.pbgestfinbo.integration.vo.VisualizzaAzioniRecuperoBancaVO;
import it.csi.pbandi.pbgestfinbo.integration.vo.VisualizzaDatiAnagraficiVO;
import it.csi.pbandi.pbgestfinbo.integration.vo.VisualizzaDettagliGaranzieVO;
import it.csi.pbandi.pbgestfinbo.integration.vo.VisualizzaRevocaBancariaVO;
import it.csi.pbandi.pbgestfinbo.integration.vo.VisualizzaSaldoStralcioVO;
import it.csi.pbandi.pbgestfinbo.integration.vo.VisualizzaSezioneDettagliGaranziaVO;
import it.csi.pbandi.pbgestfinbo.integration.vo.VisualizzaStatoCreditoVO;
import it.csi.pbandi.pbgestfinbo.integration.vo.VisualizzaStoricoEscussioneVO;
import it.csi.pbandi.pbgestfinbo.util.Constants;
import it.csi.pbandi.pbservizit.security.UserInfoSec;

@Service
public class AttivitaIstruttoreAreaCreditiServiceImpl implements AttivitaIstruttoreAreaCreditiService {
	private Logger LOG = Logger.getLogger(Constants.COMPONENT_NAME);
	
	@Autowired
	protected AttivitaIstruttoreAreaCreditiDAO attivitaIstruttore;
	
	/*@Autowired
	protected RicercaBeneficiariCreditiDAO beneficiarioDao;*/
	
	
	
	@Override
	public Response getBoxList(Long idModalitaAgevolazione, Long idArea, HttpServletRequest req) throws Exception, InvalidParameterException {
		String prf = "[AttivitaIstruttoreAreaCreditiServiceImpl::getBoxList]";
		
		LOG.info(prf + " BEGIN");
		return Response.ok().entity(attivitaIstruttore.getBoxList(idModalitaAgevolazione, idArea)).build();
		//LOG.info(prf + " END");
		//return Response.ok().entity("Test ok").build();
	}
	
	
	@Override
	public Response getLavorazioneBox(Long idModalitaAgevolazione, Long idProgetto, HttpServletRequest req) throws Exception, InvalidParameterException {
		String prf = "[AttivitaIstruttoreAreaCreditiServiceImpl::getLavorazioneBox]";
		
		LOG.info(prf + " BEGIN");
		
		List<LavorazioneBoxListVO> lavorazione = attivitaIstruttore.getLavorazioneBox(idModalitaAgevolazione, idProgetto);
		
		LOG.info(prf + " END");
		
		return Response.ok().entity(lavorazione).build();
	}
	
	
	@Override
	public Response salvaAllegati(Long idEntita, Long idEscussione, MultipartFormDataInput multipartFormDataInput, HttpServletRequest req) throws Exception, RecordNotFoundException {
		String prf = "[AttivitaIstruttoreAreaCreditiServiceImpl::salvaAllegati]";
		LOG.info(prf + " BEGIN");
		
		// All.
		LOG.info(prf + " idEntita: " + idEntita);
		LOG.info(prf + " idEscussione: " + idEscussione);
		LOG.info(prf + " multipartFormDataInput: " + multipartFormDataInput);
		
		LOG.info(prf + " END");
		return Response.ok("Test OK.").build();
	}
	
	
	// Iscrizione a Ruolo //
	
	@Override
	public Response getIscrizioneRuolo(String idProgetto, Long idModalitaAgevolazione, HttpServletRequest req) throws Exception {
		
		return Response.ok().entity(attivitaIstruttore.getIscrizioneRuolo(idProgetto, idModalitaAgevolazione)).build();
	}
	
	
	
	@Override
	public Response setIscrizioneRuolo(IscrizioneRuoloVO iscrizioneRuolo, int idModalitaAgevolazione, HttpServletRequest req) throws ErroreGestitoException, RecordNotFoundException {
		String prf = "[AttivitaIstruttoreAreaCreditiServiceImpl::setIscrizioneRuolo]";			
		LOG.info(prf + " BEGIN");

			boolean success = false;
			try {
				if(iscrizioneRuolo != null) {
					attivitaIstruttore.setIscrizioneRuolo(iscrizioneRuolo, idModalitaAgevolazione);
					success = true;
				}else {
					success = false;
				}

			} catch (Exception e) {
				throw e;
			} finally {
				LOG.info(prf + " END");
			}

			return Response.ok().entity(success).build();
	}
	
	
	// Note Generali //
	
	@Override
	public Response getNote(Long idProgetto, int idModalitaAgevolazione) {
		return Response.ok(attivitaIstruttore.getNoteGenerale(idProgetto, idModalitaAgevolazione)).build();
	}

	@Override
	public Response getStoricoNote(Long idProgetto, int idModalitaAgevolazione) {
		return Response.ok(attivitaIstruttore.getStoricoNote(idProgetto, idModalitaAgevolazione)).build();
	}
	
	@Override
	public Response salvaNota(NoteGeneraliVO nuovaNota, boolean isModifica, int idModalitaAgevolazione, HttpServletRequest req) throws Exception {
						
		//LOG.info(nuovaNota);
		
		Long idAnnotazione = attivitaIstruttore.salvaNote(nuovaNota, isModifica, idModalitaAgevolazione);
		if(idAnnotazione==null) {
			return Response.ok(false).build();
		}
		
		if(isModifica) {
			attivitaIstruttore.aggiornaAllegati(nuovaNota.getAllegatiPresenti(), idAnnotazione);
		}
		
		if(nuovaNota.getNuoviAllegati() != null) {
			for (GestioneAllegatiVO item : nuovaNota.getNuoviAllegati()) {
				byte[] decodedFile = Base64.getDecoder().decode(item.getNuovoAllegatoBase64().getBytes(StandardCharsets.UTF_8));
			
				attivitaIstruttore.salvaUploadAllegato(decodedFile, item.getNomeNuovoAllegato(), nuovaNota.getIdUtenteIns(), new BigDecimal(idAnnotazione), new BigDecimal(69), new BigDecimal(nuovaNota.getIdProgetto()), new BigDecimal(525));
			}
		}
		
		return Response.ok(true).build();
	}
	
	@Override
	public Response eliminaNota(int idAnnotazione, int idModalitaAgevolazione, HttpServletRequest req) throws Exception {		
				
		return Response.ok(attivitaIstruttore.eliminaNota(idAnnotazione, idModalitaAgevolazione)).build();
	}
	
	@Override
	public Response eliminaAllegato(int idAnnotazione, int idModalitaAgevolazione, HttpServletRequest req) throws Exception {
						
		//LOG.info(nuovaNota);
		
		/*Long idAnnotazione = attivitaIstruttore.salvaNote(nuovaNota, isModifica, idModalitaAgevolazione);
		if(idAnnotazione==null) {
			return Response.ok(false).build();
		}
		
		if(isModifica) {
			attivitaIstruttore.aggiornaAllegati(nuovaNota.getAllegatiPresenti(), idAnnotazione);
		}
		
		if(nuovaNota.getNuoviAllegati() != null) {
			for (GestioneAllegatiVO item : nuovaNota.getNuoviAllegati()) {
				byte[] decodedFile = Base64.getDecoder().decode(item.getNuovoAllegatoBase64().getBytes(StandardCharsets.UTF_8));
			
				attivitaIstruttore.salvaUploadAllegato(decodedFile, item.getNomeNuovoAllegato(), nuovaNota.getIdUtenteIns(), new BigDecimal(idAnnotazione), new BigDecimal(69), new BigDecimal(nuovaNota.getIdProgetto()), new BigDecimal(525));
			}
		}*/
		
		return Response.ok(true).build();
	}

}

