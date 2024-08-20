/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebfin.business.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.csi.pbandi.pbservizit.pbandisrv.business.impegnobilancio.ImpegnoBilancioBusinessImpl;
import it.csi.pbandi.pbservizit.pbandisrv.dto.impegnobilancio.BandolineaImpegnoDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.impegnobilancio.CapitoloDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.impegnobilancio.EnteCompetenzaDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.impegnobilancio.EsitoOperazioneAggiornaImpegnoDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.impegnobilancio.EsitoOperazioneAssociaImpegnoDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.impegnobilancio.EsitoOperazioneDisassociaDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.impegnobilancio.ImpegnoDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.impegnobilancio.ProgettoImpegnoDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.impegnobilancio.ProvvedimentoDTO;
import it.csi.pbandi.pbservizit.security.UserInfoSec;
import it.csi.pbandi.pbwebfin.business.service.GestionaleFinanziamentiService;
import it.csi.pbandi.pbwebfin.dto.beneficiario.ProgettiBeneficiario;
import it.csi.pbandi.pbwebfin.dto.gestioneImpegni.DettaglioAttoDTO;
import it.csi.pbandi.pbwebfin.dto.impegni.GestioneImpegniDTO;
import it.csi.pbandi.pbwebfin.dto.utils.ResponseCodeMessage;
import it.csi.pbandi.pbwebfin.util.Constants;
import it.csi.pbandi.pbwebfin.util.TraduttoreMessaggiPbandisrv;

@Service
public class GestionaleFinanziamentiServiceImpl implements GestionaleFinanziamentiService {
	
	private Logger LOG = Logger.getLogger(Constants.COMPONENT_NAME);
	
	@Autowired
	private ImpegnoBilancioBusinessImpl impegnoBilancioBusinessImpl;

	@Override
	public Response cercaDirezioneRicercaImpegni(Long idU, Long idSoggetto, HttpServletRequest req) throws Exception {
		
		String prf = "[GestionaleFinanziamentiServiceImpl::cercaDirezioneRicercaImpegni]";
		LOG.debug(prf + " BEGIN");
		
		LOG.debug(prf + "Parametri in input -> idU = "+idU+", idSoggetto ="+idSoggetto);
		
		EnteCompetenzaDTO[] enteCompetenzaDTO = null;
		UserInfoSec userInfo = null;
		HttpSession session = req.getSession();
		userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
		LOG.debug(prf + "userInfo = " + userInfo);
		
		try {
			enteCompetenzaDTO = impegnoBilancioBusinessImpl.findDirezioniProvvedimento(idU, userInfo.getIdIride(), idSoggetto);
		}catch (Exception e) {
			LOG.error(prf + e);
			ResponseCodeMessage resp = new ResponseCodeMessage();
			resp.setCode("ERRORE");
			resp.setMessage("Errore: " +e);
			return Response.ok().entity(resp).build();
		}
		
		LOG.debug(prf + " END");
		
		return Response.ok().entity(enteCompetenzaDTO).build();
		
	}

	@Override
	public Response cercaAnniEsercizioValidi(Long idU, HttpServletRequest req) throws Exception {
		
		String prf = "[GestionaleFinanziamentiServiceImpl::cercaAnniEsercizioValidi]";
		LOG.debug(prf + " BEGIN");
		
		LOG.debug(prf + "Parametri in input -> idU = "+idU);
		
		String[] anniEsercizioValidi = null;
		UserInfoSec userInfo = null;
		HttpSession session = req.getSession();
		userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
		LOG.debug(prf + "userInfo = " + userInfo);
		
		try {
			anniEsercizioValidi = impegnoBilancioBusinessImpl.findAnniEsercizioValidi(idU, userInfo.getIdIride());
		}catch (Exception e) {
			LOG.error(prf + e);
			ResponseCodeMessage resp = new ResponseCodeMessage();
			resp.setCode("ERRORE");
			resp.setMessage("Errore: " +e);
			return Response.ok().entity(resp).build();
		}
		
		LOG.debug(prf + " END");
		
		return Response.ok().entity(anniEsercizioValidi).build();
		
	}

	@Override
	public Response cercaImpegni(Long idU, Long annoEsercizio, Long annoImpegno, Long annoProvvedimento, String numeroProvvedimento, String direzioneProvvedimento, Long idDirezioneProvvedimento, 
			                     Long numeroCapitolo, String ragsoc, Long numeroImpegno, Long idSoggetto, Boolean isImpegniReimputati, HttpServletRequest req) throws Exception {
		
		String prf = "[GestionaleFinanziamentiServiceImpl::cercaImpegni]";
		LOG.debug(prf + " BEGIN");
		
		LOG.debug(prf + "Parametri in input -> idU = "+idU+", annoEsercizio = "+annoEsercizio+", annoProvvedimento = "+annoProvvedimento+", numeroProvvedimento = "+numeroProvvedimento+
				  ", direzioneProvvedimento = "+direzioneProvvedimento+", idDirezioneProvvedimento = "+idDirezioneProvvedimento+", numeroCapitolo = "+numeroCapitolo+
				  ", ragione sociale = "+ragsoc+", numeroImpegno = "+numeroImpegno+", idSoggetto = "+idSoggetto+", isImpegniReimputati = "+isImpegniReimputati);
		
		ImpegnoDTO[] impegnoDTO = null;
		UserInfoSec userInfo = null;
		HttpSession session = req.getSession();
		userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
		LOG.debug(prf + "userInfo = " + userInfo);
		
		try {
			
			ImpegnoDTO filtro = new ImpegnoDTO();
			filtro.setAnnoEsercizio(annoEsercizio);
			filtro.setAnnoImpegno(annoImpegno);
			filtro.setNumeroImpegno(numeroImpegno);
			ProvvedimentoDTO provvedimentoDTO = new ProvvedimentoDTO();
			provvedimentoDTO.setAnnoProvvedimento(annoProvvedimento);
			provvedimentoDTO.setNumeroProvvedimento(numeroProvvedimento);
//			provvedimentoDTO.setIdProvvedimento(idDirezioneProvvedimento);
			filtro.setProvvedimento(provvedimentoDTO);
			CapitoloDTO capitoloDTO = new CapitoloDTO();
			capitoloDTO.setNumeroCapitolo(numeroCapitolo);
			EnteCompetenzaDTO enteCompetenzaDTO = new EnteCompetenzaDTO();
			enteCompetenzaDTO.setIdTipoEnte(idDirezioneProvvedimento);
			capitoloDTO.setEnteCompetenza(enteCompetenzaDTO);
			filtro.setCapitolo(capitoloDTO);
			if(ragsoc == null) {
				ragsoc="";
			}
			filtro.setRagsoc(ragsoc);
			
			impegnoDTO = impegnoBilancioBusinessImpl.findImpegni(idU, userInfo.getIdIride(), filtro, idSoggetto);
			LOG.debug(prf + "Numero impegni trovati  = " + impegnoDTO.length);
			
			// Se a video è stato selezionato il check 'Impegni Reimputati'
			// considero solo gli impegni con anno perente valorizzato.
			if(isImpegniReimputati != null && isImpegniReimputati) {
				List<ImpegnoDTO> impegniList = new ArrayList<ImpegnoDTO>();
				for(int i = 0; i < impegnoDTO.length; i++) {
					if(impegnoDTO[i].getAnnoPerente() != null)
						impegniList.add(impegnoDTO[i]);
						
				}
				LOG.debug(prf + "Numero impegni trovati con check 'Impegni Reimputati' selezionato  = " + impegniList.size());
				return Response.ok().entity(impegniList).build();
			}
			
		}catch (Exception e) {
			LOG.error(prf + e);
			ResponseCodeMessage resp = new ResponseCodeMessage();
			resp.setCode("ERRORE");
			resp.setMessage("Errore: " +e);
			return Response.ok().entity(resp).build();
		}
		
		LOG.debug(prf + " END");
		
		return Response.ok().entity(impegnoDTO).build();
		
	}
	
	
	@Override
	public Response cercaDettaglioImpegno(Long idU, Long idSoggetto, Long idImpegno, HttpServletRequest req) throws Exception {
		
		String prf = "[GestionaleFinanziamentiServiceImpl::cercaDettaglioAtto]";
		LOG.debug(prf + " BEGIN");
		
		LOG.debug(prf + "Parametri in input -> idU = "+idU+", idSoggetto = "+idSoggetto+", idImpegno = "+idImpegno);
		
		DettaglioAttoDTO dettaglioAttoDTO = new DettaglioAttoDTO();
		ResponseCodeMessage resp = new ResponseCodeMessage();
		ImpegnoDTO impegnoDTO = null;
		EsitoOperazioneAggiornaImpegnoDTO esito = null;
		UserInfoSec userInfo = null;
		HttpSession session = req.getSession();
		userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
		LOG.debug(prf + "userInfo = " + userInfo);
		
		try {
			impegnoDTO = impegnoBilancioBusinessImpl.dettaglioImpengo(idU, userInfo.getIdIride(), idSoggetto, idImpegno);
			LOG.debug(prf + "impegnoDTO  = " +impegnoDTO.toString());
			dettaglioAttoDTO.setImpegnoDTO(impegnoDTO);
			
			esito = impegnoBilancioBusinessImpl.consultaImpegno(idU, userInfo.getIdIride(),impegnoDTO);
			LOG.debug(prf + "Esito consultaImpegno  = " +esito.toString());
			
			if(esito != null && esito.getImpegno() != null) {
				impegnoDTO = new ImpegnoDTO();
				impegnoDTO = esito.getImpegno();
			}
				
			esito = impegnoBilancioBusinessImpl.aggiornaImpegno(idU, userInfo.getIdIride(),impegnoDTO);
			LOG.debug(prf + "Esito aggiornaImpegno  = " +esito.toString());
			if(esito != null && esito.getMsg() != null) {
				resp.setCode(esito.getMsg().getKey());
				resp.setMessage(TraduttoreMessaggiPbandisrv.traduci(esito.getMsg().getKey(), esito.getMsg().getParams() ));
			}
			dettaglioAttoDTO.setResponseCodeMessage(resp);
			
			impegnoDTO = impegnoBilancioBusinessImpl.dettaglioImpengo(idU, userInfo.getIdIride(), idSoggetto, idImpegno);
			LOG.debug(prf + "impegnoDTO  = " +impegnoDTO.toString());
			dettaglioAttoDTO.setImpegnoDTO(impegnoDTO);			
						
		}catch (Exception e) {
			LOG.error(prf + e);
			resp.setCode("ERRORE");
			resp.setMessage("Errore: " +e);
			dettaglioAttoDTO.setResponseCodeMessage(resp);
		}
		
		LOG.debug(prf + " END");
		
		return Response.ok().entity(dettaglioAttoDTO).build();
		
	}

	@Override
	public Response cercaDirezioneConImpegno(Long idU, HttpServletRequest req) throws Exception {
		
		String prf = "[GestionaleFinanziamentiServiceImpl::cercaDirezioneSenzaImpegno]";
		LOG.debug(prf + " BEGIN");
		
		LOG.debug(prf + "Parametri in input -> idU = "+idU);
		
		EnteCompetenzaDTO[] enteCompetenzaDTO = null;
		UserInfoSec userInfo = null;
		HttpSession session = req.getSession();
		userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
		LOG.debug(prf + "userInfo = " + userInfo);
		
		try {
			enteCompetenzaDTO = impegnoBilancioBusinessImpl.findDirezioniConRuoloBilancio(idU, userInfo.getIdIride());
			LOG.debug(prf + "Numero direzione impegni trovati  = " + enteCompetenzaDTO.length);
		}catch (Exception e) {
			LOG.error(prf + e);
			ResponseCodeMessage resp = new ResponseCodeMessage();
			resp.setCode("ERRORE");
			resp.setMessage("Errore: " +e);
			return Response.ok().entity(resp).build();
		}
		
		LOG.debug(prf + " END");
		
		return Response.ok().entity(enteCompetenzaDTO).build();
		
	}

	@Override
	public Response cercaBandolineaConDirezione(Long idU, Long[] idImpegni, Long idEnteCompetenza, HttpServletRequest req) throws Exception {
		
		String prf = "[GestionaleFinanziamentiServiceImpl::cercaBandolineaConDirezione]";
		LOG.debug(prf + " BEGIN");
		
		LOG.debug(prf + "Parametri in input -> idU = "+idU+", idImpegni = "+Arrays.toString(idImpegni)+", idEnteCompetenza = "+idEnteCompetenza);
		
		BandolineaImpegnoDTO[] bandolineaImpegnoDTO = null;
		UserInfoSec userInfo = null;
		HttpSession session = req.getSession();
		userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
		LOG.debug(prf + "userInfo = " + userInfo);
		
		try {
			bandolineaImpegnoDTO = impegnoBilancioBusinessImpl.findListBandolinea(idU, userInfo.getIdIride(), idImpegni, idEnteCompetenza);
			LOG.debug(prf + "Numero bando linea associati trovati  = " + bandolineaImpegnoDTO.length);
		}catch (Exception e) {
			LOG.error(prf + e);
			ResponseCodeMessage resp = new ResponseCodeMessage();
			resp.setCode("ERRORE");
			resp.setMessage("Errore: " +e);
			return Response.ok().entity(resp).build();
		}
		
		LOG.debug(prf + " END");
		
		return Response.ok().entity(bandolineaImpegnoDTO).build();
		
	}

	@Override
	public Response cercaListaProgetti(Long idU, Long[] idImpegni, Long progrBandolinea, HttpServletRequest req) throws Exception {
		
		String prf = "[GestionaleFinanziamentiServiceImpl::cercaListaProgetti]";
		LOG.debug(prf + " BEGIN");
		
		LOG.debug(prf + "Parametri in input -> idU = "+idU+", progrBandolinea = "+progrBandolinea+", idImpegni = "+Arrays.toString(idImpegni));
		
		ProgettoImpegnoDTO[] progettoImpegnoDTO = null;
		UserInfoSec userInfo = null;
		HttpSession session = req.getSession();
		userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
		LOG.debug(prf + "userInfo = " + userInfo);
		
		try {
			progettoImpegnoDTO = impegnoBilancioBusinessImpl.findListProgetti(idU, userInfo.getIdIride(), idImpegni, progrBandolinea);
			LOG.debug(prf + "Trovata lista progetti con  = " + progettoImpegnoDTO.length+" elementi");
		}catch (Exception e) {
			LOG.error(prf + e);
			ResponseCodeMessage resp = new ResponseCodeMessage();
			resp.setCode("ERRORE");
			resp.setMessage("Errore: " +e);
			return Response.ok().entity(resp).build();
		}
		
		LOG.debug(prf + " END");
		
		return Response.ok().entity(progettoImpegnoDTO).build();
		
	}

	@Override
	public Response cercaProgettiDaAssociare(Long idU, Long[] idImpegni, Long progrBandolinea, Long idProgetto, HttpServletRequest req) throws Exception {
		
		String prf = "[GestionaleFinanziamentiServiceImpl::cercaProgettiDaAssociare]";
		LOG.debug(prf + " BEGIN");
		
		LOG.debug(prf + "Parametri in input -> idU = "+idU+", progrBandolinea = "+progrBandolinea+", idImpegni = "+Arrays.toString(idImpegni)+"idProgetto = "+idProgetto);
		
		ProgettoImpegnoDTO[] progettoImpegnoDTO = null;
		UserInfoSec userInfo = null;
		HttpSession session = req.getSession();
		userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
		LOG.debug(prf + "userInfo = " + userInfo);
		
		try {
			progettoImpegnoDTO = impegnoBilancioBusinessImpl.findProgettiDaAssociare(idU, userInfo.getIdIride(), idImpegni, progrBandolinea, idProgetto);
			LOG.debug(prf + "Numero progetti da associare trovati = " + progettoImpegnoDTO.length);
		}catch (Exception e) {
			LOG.error(prf + e);
			ResponseCodeMessage resp = new ResponseCodeMessage();
			resp.setCode("ERRORE");
			resp.setMessage("Errore: " +e);
			return Response.ok().entity(resp).build();
		}
		
		LOG.debug(prf + " END");
		
		return Response.ok().entity(progettoImpegnoDTO).build();
		
	}

	@Override
	public Response ceraBandolineaDaAssociare(Long idU, Long[] idImpegni, Long idEnteCompetenza, Long progrBandolinea, HttpServletRequest req) throws Exception {
		
		String prf = "[GestionaleFinanziamentiServiceImpl::ceraBandolineaDaAssociare]";
		LOG.debug(prf + " BEGIN");
		
		LOG.debug(prf + "Parametri in input -> idU = "+idU+", progrBandolinea = "+progrBandolinea+", idImpegni = "+Arrays.toString(idImpegni)+"idEnteCompetenza = "+idEnteCompetenza);
		
		BandolineaImpegnoDTO[] bandolineaImpegnoDTO = null;
		UserInfoSec userInfo = null;
		HttpSession session = req.getSession();
		userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
		LOG.debug(prf + "userInfo = " + userInfo);
		
		try {
			bandolineaImpegnoDTO = impegnoBilancioBusinessImpl.findBandolineaDaAssociare(idU, userInfo.getIdIride(), idImpegni, idEnteCompetenza, progrBandolinea);
			LOG.debug(prf + "Numero bando linea da associare trovati = " + bandolineaImpegnoDTO.length);
		}catch (Exception e) {
			LOG.error(prf + e);
			ResponseCodeMessage resp = new ResponseCodeMessage();
			resp.setCode("ERRORE");
			resp.setMessage("Errore: " +e);
			return Response.ok().entity(resp).build();
		}
		
		LOG.debug(prf + " END");
		
		return Response.ok().entity(bandolineaImpegnoDTO).build();
		
	}

	@Override
	public Response associaBandolineaImpegni(Long idU, Long idSoggetto, GestioneImpegniDTO gestioneImpegniDTO, HttpServletRequest req) throws Exception {
		
		String prf = "[GestionaleFinanziamentiServiceImpl::associaBandolineaImpegni]";
		LOG.debug(prf + " BEGIN");
		
		LOG.debug(prf + "Parametri in input -> idU = "+idU+", idSoggetto = "+idSoggetto+", gestioneImpegniDTO = "+gestioneImpegniDTO.toString() );
		
		EsitoOperazioneAssociaImpegnoDTO esitoOperazioneAssociaImpegnoDTO = null;
		UserInfoSec userInfo = null;
		HttpSession session = req.getSession();
		userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
		LOG.debug(prf + "userInfo = " + userInfo);
		
		try {
			esitoOperazioneAssociaImpegnoDTO = impegnoBilancioBusinessImpl.associaBandolineaImpegnoGestAss(idU, userInfo.getIdIride(), idSoggetto, 
																						                   gestioneImpegniDTO.getIdImpegniSelizioanti(), gestioneImpegniDTO.getBandoLineaSelezionati());
			
			LOG.debug(prf + "Esito = " + esitoOperazioneAssociaImpegnoDTO.getEsito()+", Messaggio = "+esitoOperazioneAssociaImpegnoDTO.getMsg());
		}catch (Exception e) {
			LOG.error(prf + e);
			ResponseCodeMessage resp = new ResponseCodeMessage();
			resp.setCode("ERRORE");
			resp.setMessage("Errore: " +e);
			return Response.ok().entity(resp).build();
		}
		
		LOG.debug(prf + " END");
		
		return Response.ok().entity(esitoOperazioneAssociaImpegnoDTO).build();
		
	}
	
	
	@Override
	public Response cercaBandolineaAssociati(Long idU, Long idSoggetto, Long[] listaIdImpegno, HttpServletRequest req) throws Exception {
		
		String prf = "[GestionaleFinanziamentiServiceImpl::cercaBandolineaAssociati]";
		LOG.debug(prf + " BEGIN");
		
		LOG.debug(prf + "Parametri in input -> idU = "+idU+", idSoggetto = "+idSoggetto+", listaIdImpegno = "+Arrays.toString(listaIdImpegno) );
		
		BandolineaImpegnoDTO[] bandolineaImpegnoDTO = null;
		UserInfoSec userInfo = null;
		HttpSession session = req.getSession();
		userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
		LOG.debug(prf + "userInfo = " + userInfo);
		
		try {
			bandolineaImpegnoDTO = impegnoBilancioBusinessImpl.findBandolineaAssociati(idU, userInfo.getIdIride(), idSoggetto, listaIdImpegno);
			LOG.debug(prf + "Numero bando linea associati trovati = " + bandolineaImpegnoDTO.length);
		}catch (Exception e) {
			LOG.error(prf + e);
			ResponseCodeMessage resp = new ResponseCodeMessage();
			resp.setCode("ERRORE");
			resp.setMessage("Errore: " +e);
			return Response.ok().entity(resp).build();
		}
		
		LOG.debug(prf + " END");
		
		return Response.ok().entity(bandolineaImpegnoDTO).build();
		
	}
	
	
	@Override
	public Response cercaProgettiAssociati(Long idU, Long idSoggetto, Long[] listaIdImpegno, Long annoEsercizio, HttpServletRequest req) throws Exception {
		
		String prf = "[GestionaleFinanziamentiServiceImpl::cercaProgettiAssociati]";
		LOG.debug(prf + " BEGIN");
		
		LOG.debug(prf + "Parametri in input -> idU = "+idU+", idSoggetto = "+idSoggetto+", listaIdImpegno = "+Arrays.toString(listaIdImpegno)+", annoEsercizio = "+annoEsercizio );
		
		ProgettoImpegnoDTO[] progettoImpegnoDTO = null;
		UserInfoSec userInfo = null;
		HttpSession session = req.getSession();
		userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
		LOG.debug(prf + "userInfo = " + userInfo);
		
		try {
			progettoImpegnoDTO = impegnoBilancioBusinessImpl.findProgettiAssociati(idU, userInfo.getIdIride(), idSoggetto, listaIdImpegno, annoEsercizio);
			LOG.debug(prf + "Numero progetti associati trovati = " + progettoImpegnoDTO.length);
		}catch (Exception e) {
			LOG.error(prf + e);
			ResponseCodeMessage resp = new ResponseCodeMessage();
			resp.setCode("ERRORE");
			resp.setMessage("Errore: " +e);
			return Response.ok().entity(resp).build();
		}
		
		LOG.debug(prf + " END");
		
		return Response.ok().entity(progettoImpegnoDTO).build();
		
	}
	

	@Override
	public Response associaProgettiImpegni(Long idU, GestioneImpegniDTO gestioneImpegniDTO, HttpServletRequest req) throws Exception {

		String prf = "[GestionaleFinanziamentiServiceImpl::associaProgettiImpegni]";
		LOG.debug(prf + " BEGIN");
		
		LOG.debug(prf + "Parametri in input -> idU = "+idU+", gestioneImpegniDTO = "+gestioneImpegniDTO.toString() );
		
		EsitoOperazioneAssociaImpegnoDTO esitoOperazioneAssociaImpegnoDTO = null;
		UserInfoSec userInfo = null;
		HttpSession session = req.getSession();
		userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
		LOG.debug(prf + "userInfo = " + userInfo);
		
		try {
			esitoOperazioneAssociaImpegnoDTO = impegnoBilancioBusinessImpl.associaProgettiImpegnoGestAss(idU, userInfo.getIdIride(),
					                                                                                     gestioneImpegniDTO.getIdImpegniSelizioanti(), gestioneImpegniDTO.getIdProgettiSelezionati());
			LOG.debug(prf + "Esito = " + esitoOperazioneAssociaImpegnoDTO.getEsito()+", Messaggio = "+esitoOperazioneAssociaImpegnoDTO.getMsg());
		}catch (Exception e) {
			LOG.error(prf + e);
			ResponseCodeMessage resp = new ResponseCodeMessage();
			resp.setCode("ERRORE");
			resp.setMessage("Errore: " +e);
			return Response.ok().entity(resp).build();
		}
		
		LOG.debug(prf + " END");
		
		return Response.ok().entity(esitoOperazioneAssociaImpegnoDTO).build();
		
	}

//	@Override
//	public Response disassociaBandolinea(Long idU, Long idImpegno, Long[] progrBandolineaSelezionati, HttpServletRequest req) throws Exception {
//		
//		String prf = "[GestionaleFinanziamentiServiceImpl::disassociaBandolinea]";
//		LOG.debug(prf + " BEGIN");
//		
//		LOG.debug(prf + "Parametri in input -> idU = "+idU+", idImpegno = "+idImpegno+", progrBandolineaSelezionati = "+Arrays.toString(progrBandolineaSelezionati) );
//		
//		EsitoOperazioneDisassociaDTO esitoOperazioneDisassociaDTO = null;
//		UserInfoSec userInfo = null;
//		HttpSession session = req.getSession();
//		userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
//		LOG.debug(prf + "userInfo = " + userInfo);
//		
//		try {
//			esitoOperazioneDisassociaDTO = impegnoBilancioBusinessImpl.disassociaBandolinea(idU, userInfo.getIdIride(), idImpegno, progrBandolineaSelezionati);
//			LOG.debug(prf + "Esito = " + esitoOperazioneDisassociaDTO.getEsito()+", Messaggio = "+esitoOperazioneDisassociaDTO.getMsg());
//		}catch (Exception e) {
//			LOG.error(prf + e);
//			ResponseCodeMessage resp = new ResponseCodeMessage();
//			resp.setCode("ERRORE");
//			resp.setMessage("Errore: " +e);
//			return Response.ok().entity(resp).build();
//		}
//		
//		LOG.debug(prf + " END");
//		
//		return Response.ok().entity(esitoOperazioneDisassociaDTO).build();
//		
//	}
//
//	@Override
//	public Response disassociaProgetti(Long idU, Long idImpegno, Long[] progettiSelezionati, HttpServletRequest req) throws Exception {
//		
//		String prf = "[GestionaleFinanziamentiServiceImpl::disassociaProgetti]";
//		LOG.debug(prf + " BEGIN");
//		
//		LOG.debug(prf + "Parametri in input -> idU = "+idU+", idImpegno = "+idImpegno+", progettiSelezionati = "+Arrays.toString(progettiSelezionati) );
//		
//		EsitoOperazioneDisassociaDTO esitoOperazioneDisassociaDTO = null;
//		UserInfoSec userInfo = null;
//		HttpSession session = req.getSession();
//		userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
//		LOG.debug(prf + "userInfo = " + userInfo);
//		
//		try {
//			esitoOperazioneDisassociaDTO = impegnoBilancioBusinessImpl.disassociaProgetti(idU, userInfo.getIdIride(), idImpegno, progettiSelezionati);
//			LOG.debug(prf + "Esito = " + esitoOperazioneDisassociaDTO.getEsito()+", Messaggio = "+esitoOperazioneDisassociaDTO.getMsg());
//		}catch (Exception e) {
//			LOG.error(prf + e);
//			ResponseCodeMessage resp = new ResponseCodeMessage();
//			resp.setCode("ERRORE");
//			resp.setMessage("Errore: " +e);
//			return Response.ok().entity(resp).build();
//		}
//		
//		LOG.debug(prf + " END");
//		
//		return Response.ok().entity(esitoOperazioneDisassociaDTO).build();
//		
//	}

	@Override
	public Response cercaProgettiAssociatiPerBeneficiario(Long idU, Long idSoggetto, Long[] listaIdImpegno, Long annoEsercizio,  HttpServletRequest req) throws Exception {
		
		String prf = "[GestionaleFinanziamentiServiceImpl::cercaProgettiAssociatiPerBeneficiario]";
		LOG.debug(prf + " BEGIN");
		
		LOG.debug(prf + "Parametri in input -> idU = "+idU+", idSoggetto = "+idSoggetto+", listaIdImpegno = "+Arrays.toString(listaIdImpegno)+", annoEsercizio = "+annoEsercizio);
		
		ProgettoImpegnoDTO[] progettoImpegnoDTO = null;
		UserInfoSec userInfo = null;
		HttpSession session = req.getSession();
		userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
		LOG.debug(prf + "userInfo = " + userInfo);
		
		try {
			progettoImpegnoDTO = impegnoBilancioBusinessImpl.findProgettiAssociatiPerBeneficiario(idU, userInfo.getIdIride(), idSoggetto, listaIdImpegno, annoEsercizio);
			LOG.debug(prf + "Numero progetti associati trovati = " + progettoImpegnoDTO.length);
		}catch (Exception e) {
			LOG.error(prf + e);
			ResponseCodeMessage resp = new ResponseCodeMessage();
			resp.setCode("ERRORE");
			resp.setMessage("Errore: " +e);
			return Response.ok().entity(resp).build();
		}
		
		LOG.debug(prf + " END");
		
		return Response.ok().entity(progettoImpegnoDTO).build();
		
	}

	@Override
	public Response disassociaProgettiGestAss(Long idU, ProgettiBeneficiario progettiBeneficiario, HttpServletRequest req) throws Exception {
		
		String prf = "[GestionaleFinanziamentiServiceImpl::disassociaProgettiGestAss]";
		LOG.debug(prf + " BEGIN");
		
		LOG.debug(prf + "Parametri in input -> idU = "+idU+", beneficiario = "+progettiBeneficiario.toString());
		
		EsitoOperazioneDisassociaDTO esitoOperazioneDisassociaDTO = null;
		UserInfoSec userInfo = null;
		HttpSession session = req.getSession();
		userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
		LOG.debug(prf + "userInfo = " + userInfo);
		
		try {
			esitoOperazioneDisassociaDTO = impegnoBilancioBusinessImpl.disassociaProgettiGestAss(idU, userInfo.getIdIride(), progettiBeneficiario.getIdImpegno(), progettiBeneficiario.getProgetti());
			LOG.debug(prf + "Esito = " + esitoOperazioneDisassociaDTO.getEsito()+", Messaggio = "+esitoOperazioneDisassociaDTO.getMsg());
		}catch (Exception e) {
			LOG.error(prf + e);
			ResponseCodeMessage resp = new ResponseCodeMessage();
			resp.setCode("ERRORE");
			resp.setMessage("Errore: " +e);
			return Response.ok().entity(resp).build();
		}
		
		LOG.debug(prf + " END");
		
		return Response.ok().entity(esitoOperazioneDisassociaDTO).build();
		
	}

	@Override
	public Response disassociaBandolineaGestAss(Long idU, GestioneImpegniDTO impegni, HttpServletRequest req) throws Exception {
		
		String prf = "[GestionaleFinanziamentiServiceImpl::disassociaBandolineaGestAss]";
		LOG.debug(prf + " BEGIN");
		
		LOG.debug(prf + "Parametri in input -> idU = "+idU+", disassociaBandolineaGestAss = "+impegni.toString());
		
		EsitoOperazioneDisassociaDTO esitoOperazioneDisassociaDTO = null;
		UserInfoSec userInfo = null;
		HttpSession session = req.getSession();
		userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
		LOG.debug(prf + "userInfo = " + userInfo);
		
		try {
			esitoOperazioneDisassociaDTO = impegnoBilancioBusinessImpl.disassociaBandolineaGestAss(idU, userInfo.getIdIride(),impegni.getIdImpegniSelizioanti(), impegni.getBandoLineaSelezionati());
			LOG.debug(prf + "Esito = " + esitoOperazioneDisassociaDTO.getEsito()+", Messaggio = "+esitoOperazioneDisassociaDTO.getMsg());
		}catch (Exception e) {
			LOG.error(prf + e);
			ResponseCodeMessage resp = new ResponseCodeMessage();
			resp.setCode("ERRORE");
			resp.setMessage("Errore: " +e);
			return Response.ok().entity(resp).build();
		}
		
		LOG.debug(prf + " END");
		
		return Response.ok().entity(esitoOperazioneDisassociaDTO).build();
		
	}

	@Override
	public Response acquisisciImpegno(Long idU, Long annoEsercizio, Long annoImpegno, Long numeroImpegno, Long idSoggetto, HttpServletRequest req) throws Exception {
		
		String prf = "[GestionaleFinanziamentiServiceImpl::acquisisciImpegno]";
		LOG.debug(prf + " BEGIN");
		
		LOG.debug(prf + "Parametri in input -> idU = "+idU+", annoEsercizio = "+annoEsercizio+", annoImpegno = "+annoImpegno+", numeroImpegno = "+numeroImpegno+", idSoggetto = "+idSoggetto);
		
		EsitoOperazioneAggiornaImpegnoDTO esitoOperazioneAggiornaImpegnoDTO = null;
		UserInfoSec userInfo = null;
		HttpSession session = req.getSession();
		userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
		LOG.debug(prf + "userInfo = " + userInfo);
		
		try {
			
			ImpegnoDTO impegnoDTO = new ImpegnoDTO();
			impegnoDTO.setAnnoEsercizio(annoEsercizio);
			impegnoDTO.setAnnoImpegno(annoImpegno);
			impegnoDTO.setNumeroImpegno(numeroImpegno);
			
			esitoOperazioneAggiornaImpegnoDTO = impegnoBilancioBusinessImpl.consultaImpegno(idU, userInfo.getIdIride(), impegnoDTO);
			LOG.debug(prf + "ConsultaImpegno:: Esito = " + esitoOperazioneAggiornaImpegnoDTO.getEsito()+", Messaggio = "+esitoOperazioneAggiornaImpegnoDTO.getMsg());
			
			if(esitoOperazioneAggiornaImpegnoDTO != null && esitoOperazioneAggiornaImpegnoDTO.getEsito()) {
				ImpegnoDTO[] impegniDTO = null;
				impegniDTO = impegnoBilancioBusinessImpl.findImpegni(idU, userInfo.getIdIride(), impegnoDTO, idSoggetto);
				LOG.debug(prf + "Numero impegni trovati  = " + impegniDTO.length);
				
				if(impegniDTO != null && impegniDTO.length > 0) {
					esitoOperazioneAggiornaImpegnoDTO = impegnoBilancioBusinessImpl.aggiornaImpegno(idU, userInfo.getIdIride(), impegniDTO[0]);
				}else {
					esitoOperazioneAggiornaImpegnoDTO = impegnoBilancioBusinessImpl.aggiornaImpegno(idU, userInfo.getIdIride(), esitoOperazioneAggiornaImpegnoDTO.getImpegno());
				}
				
				LOG.debug(prf + "AggiornaImpegno:: Esito = " + esitoOperazioneAggiornaImpegnoDTO.getEsito()+", Messaggio = "+esitoOperazioneAggiornaImpegnoDTO.getMsg());
			}else if(esitoOperazioneAggiornaImpegnoDTO != null && !esitoOperazioneAggiornaImpegnoDTO.getEsito()) {
				esitoOperazioneAggiornaImpegnoDTO.getMsg().setKey(TraduttoreMessaggiPbandisrv.traduci(esitoOperazioneAggiornaImpegnoDTO.getMsg().getKey(), 
						                                                                              esitoOperazioneAggiornaImpegnoDTO.getMsg().getParams() ));
			}

			
		}catch (Exception e) {
			LOG.error(prf + e);
			ResponseCodeMessage resp = new ResponseCodeMessage();
			resp.setCode("ERRORE");
			resp.setMessage("Errore: " +e);
			return Response.ok().entity(resp).build();
		}
		
		LOG.debug(prf + " END");
		
		return Response.ok().entity(esitoOperazioneAggiornaImpegnoDTO).build();
		
	}


}
