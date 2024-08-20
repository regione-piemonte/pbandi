/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebfin.business.service.impl;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.csi.csi.wrapper.CSIException;
import it.csi.csi.wrapper.SystemException;
import it.csi.csi.wrapper.UnrecoverableException;
import it.csi.pbandi.pbservizit.business.intf.ErrorConstants;
import it.csi.pbandi.pbservizit.pbandisrv.business.erogazione.ErogazioneBusinessImpl;
import it.csi.pbandi.pbservizit.pbandisrv.business.gestionedatididominio.GestioneDatiDiDominioBusinessImpl;
import it.csi.pbandi.pbservizit.pbandisrv.business.gestionedatigenerali.GestioneDatiGeneraliBusinessImpl;
import it.csi.pbandi.pbservizit.pbandisrv.business.impegnobilancio.ImpegnoBilancioBusinessImpl;
import it.csi.pbandi.pbservizit.pbandisrv.business.liquidazionebilancio.LiquidazioneBilancioBusinessImpl;
import it.csi.pbandi.pbservizit.pbandisrv.dto.erogazione.CodiceDescrizioneDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.gestionedatigenerali.DatiGeneraliDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.impegnobilancio.EsitoOperazioneAggiornaImpegnoDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.impegnobilancio.EsitoOperazioneConsultaImpegniDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.impegnobilancio.ImpegnoDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.liquidazionebilancio.AliquotaRitenutaAttoDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.liquidazionebilancio.BeneficiarioBilancioDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.liquidazionebilancio.DatiIntegrativiDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.liquidazionebilancio.DettaglioBeneficiarioBilancioDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.liquidazionebilancio.EnteCompetenzaDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.liquidazionebilancio.EsitoAggiornaLiquidazioneDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.liquidazionebilancio.EsitoAggiornaRiepilogoFondiDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.liquidazionebilancio.EsitoCreaAttoDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.liquidazionebilancio.EsitoDatiProgettoDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.liquidazionebilancio.EsitoInfoCreaAttoDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.liquidazionebilancio.EsitoLeggiAttoLiquidazioneDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.liquidazionebilancio.EsitoLeggiRiepilogoFondiDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.liquidazionebilancio.EsitoLeggiStatoElaborazioneDocDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.liquidazionebilancio.EsitoRitenuteDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.liquidazionebilancio.ImpegnoAllineatoDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.liquidazionebilancio.InputLeggiStatoElaborazioneDocDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.liquidazionebilancio.LiquidazioneDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.liquidazionebilancio.ModAgevolazioneContributoDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.liquidazionebilancio.ModalitaAgevolazioneLiquidazioneDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.liquidazionebilancio.RipartizioneImpegniLiquidazioneDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.liquidazionebilancio.RitenutaDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.liquidazionebilancio.SettoreEnteDTO;
import it.csi.pbandi.pbservizit.security.UserInfoSec;
import it.csi.pbandi.pbwebfin.business.service.LiquidazioneContributiService;
import it.csi.pbandi.pbwebfin.dto.beneficiario.AliquotaRienutaDTO;
import it.csi.pbandi.pbwebfin.dto.beneficiario.Beneficiario;
import it.csi.pbandi.pbwebfin.dto.beneficiario.DatiIntegrativi;
import it.csi.pbandi.pbwebfin.dto.beneficiario.DettaglioBeneficiario;
import it.csi.pbandi.pbwebfin.dto.beneficiario.ImpegniLiquidazioneDTO;
import it.csi.pbandi.pbwebfin.dto.beneficiario.Liquidazione;
import it.csi.pbandi.pbwebfin.dto.utils.ResponseCodeMessage;
import it.csi.pbandi.pbwebfin.util.Constants;
import it.csi.pbandi.pbwebfin.util.TraduttoreMessaggiPbandisrv;


@Service
public class LiquidazioneContributiServiceImpl implements LiquidazioneContributiService {

	private Logger LOG = Logger.getLogger(Constants.COMPONENT_NAME);
	
	@Autowired
	private GestioneDatiGeneraliBusinessImpl gestioneDatiGeneraliBusinessImpl;
	
	@Autowired
	private LiquidazioneBilancioBusinessImpl liquidazioneBilancioBusinessImpl;
	
	@Autowired
	private ErogazioneBusinessImpl erogazioneBusinessImpl;
	
	@Autowired
	ImpegnoBilancioBusinessImpl impegnoBilancioBusinessImpl;
	
	@Autowired
	GestioneDatiDiDominioBusinessImpl gestioneDatiDiDominioBusinessImpl;

	@Override
	public Response caricaDatiGenerali(Long idU, Long idSoggettoBeneficiario, Long idBandoLinea, Long idProgetto, HttpServletRequest req) throws  Exception {
		
		String prf = "[LiquidazioneContributiServiceImpl::caricaDatiGenerali]";
		LOG.debug(prf + " BEGIN");
		
		LOG.debug(prf + "Parametri in input -> idU = "+idU+", idProgetto = "+idProgetto+", idSoggettoBeneficiario = "+idSoggettoBeneficiario+", idBandoLinea = "+idBandoLinea);
		
		DatiGeneraliDTO datiGeneraliDTO = null;
		Liquidazione liquidazione = new Liquidazione();
		ResponseCodeMessage resp = new ResponseCodeMessage();
		
		try {
			datiGeneraliDTO = gestioneDatiGeneraliBusinessImpl.caricaDatiGenerali(idU, getIride(req), idProgetto, idSoggettoBeneficiario);
			
			ModAgevolazioneContributoDTO modAgevolazioneContributoDTO = liquidazioneBilancioBusinessImpl.getModAgevolazioneContributo(idU, prf, idProgetto);
			if(modAgevolazioneContributoDTO == null || modAgevolazioneContributoDTO.getIdModAgevolazioneContributo() == null || 
					modAgevolazioneContributoDTO.getDescBreveModAgevolazioneContributo() == null || modAgevolazioneContributoDTO.getDescBreveModAgevolazioneContributo().length() == 0) {
				
				resp.setCode("Errore");
				resp.setMessage("ATTENZIONE! Non &egrave; stata individuata la modalit&agrave; di agevolazione da utilizzare per la liquidazione.");
				liquidazione.setResp(resp);
			}
			
			ModalitaAgevolazioneLiquidazioneDTO[]  modalitaAgevolazioneLiquidazioneDTO = liquidazioneBilancioBusinessImpl.findModalitaAgevolazioneLiquidazione(idU, getIride(req), idProgetto);
			LOG.debug(prf + " modalitaAgevolazioneLiquidazioneDTO = "+modalitaAgevolazioneLiquidazioneDTO.toString());
			
			EsitoDatiProgettoDTO esitoDatiProgettoDTO = liquidazioneBilancioBusinessImpl.findDatiProgetto(idU, getIride(req), idProgetto);
			LOG.debug(prf + " esitoDatiProgettoDTO = "+esitoDatiProgettoDTO.toString());
			
			EsitoLeggiAttoLiquidazioneDTO esitoLeggiAttoLiquidazioneDTO = liquidazioneBilancioBusinessImpl.getAttoLiquidazioneContributi(idU, getIride(req), idProgetto, 
					                                                               datiGeneraliDTO.getIdFormaGiuridica(), datiGeneraliDTO.getIdDimensioneImpresa(), idBandoLinea);
			LOG.debug(prf + " esitoLeggiAttoLiquidazioneDTO = "+esitoLeggiAttoLiquidazioneDTO.toString());

			CodiceDescrizioneDTO[] codiceDescrizioneErogazioneDTO = erogazioneBusinessImpl.findCausaliErogazione(idU, getIride(req), idBandoLinea, 
																									   datiGeneraliDTO.getIdFormaGiuridica(), datiGeneraliDTO.getIdDimensioneImpresa());
			
			LOG.debug(prf + "Trovati codiceDescrizioneErogazioneDTO = "+codiceDescrizioneErogazioneDTO.length);

			it.csi.pbandi.pbservizit.pbandisrv.dto.liquidazionebilancio.CodiceDescrizioneDTO[] codiceDescrizioneDTO = liquidazioneBilancioBusinessImpl.findCIGProgetto(idU, getIride(req), idProgetto);
			LOG.debug(prf + "Trovati codiceDescrizioneDTO = "+codiceDescrizioneDTO.length);
			
			
			liquidazione.setCodiceDescrizioneDTO(codiceDescrizioneDTO);
			liquidazione.setCodiceDescrizioneErogazioneDTO(codiceDescrizioneErogazioneDTO);
			liquidazione.setDatiGeneraliDTO(datiGeneraliDTO);
			liquidazione.setEsitoDatiProgettoDTO(esitoDatiProgettoDTO);
			liquidazione.setEsitoLeggiAttoLiquidazioneDTO(esitoLeggiAttoLiquidazioneDTO);
			liquidazione.setModalitaAgevolazioneLiquidazioneDTO(modalitaAgevolazioneLiquidazioneDTO);
			
		}catch (Exception e) {
			LOG.error(prf + e);
			resp.setCode("ERRORE");
			resp.setMessage("Errore: " +e);
			liquidazione.setResp(resp);
		}
		
		
		LOG.debug(prf + " END");
		
		return Response.ok().entity(liquidazione).build();
		
	}

	@Override
	public Response avantiTabLiquidazione(Long idU, LiquidazioneDTO liquidazione, Long idSoggetto, Long idSoggettoBeneficiario, Long idBandoLinea, HttpServletRequest req) throws Exception {
		
		String prf = "[LiquidazioneContributiServiceImpl::riepilogoFondi]";
		LOG.debug(prf + " BEGIN");
		
		LOG.debug(prf + "Parametri in input -> idU = "+idU+", liquidazione = "+liquidazione.toString()+", idSoggetto = "+idSoggetto+ 
					    ", idSoggettoBeneficiario = "+idSoggettoBeneficiario+", idBandoLinea = "+idBandoLinea);

		EsitoAggiornaLiquidazioneDTO esitoAggiornaLiquidazioneDTO;
		EsitoLeggiRiepilogoFondiDTO esitoLeggiRiepilogoFondiDTO;
		EsitoOperazioneConsultaImpegniDTO esitoOperazioneConsultaImpegniDTO;
		EsitoOperazioneAggiornaImpegnoDTO esitoOperazioneAggiornaImpegnoDTO;
		UserInfoSec userInfo = null;
		HttpSession session = req.getSession();
		userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
		LOG.debug(prf + "userInfo = " + userInfo);
		
		try {
			
			esitoAggiornaLiquidazioneDTO = liquidazioneBilancioBusinessImpl.aggiornaLiquidazioneContributi(idU, userInfo.getIdIride(), idSoggetto, liquidazione, idSoggettoBeneficiario);
			LOG.debug(prf + "esitoAggiornaLiquidazioneDTO = "+esitoAggiornaLiquidazioneDTO.toString());
			
			esitoLeggiRiepilogoFondiDTO = liquidazioneBilancioBusinessImpl.getRiepilogoFondi(idU, userInfo.getIdIride(), esitoAggiornaLiquidazioneDTO.getLiquidazione().getIdProgetto(), 
																							 esitoAggiornaLiquidazioneDTO.getLiquidazione().getIdAttoLiquidazione(), idBandoLinea);
			LOG.debug(prf + "TesitoLeggiRiepilogoFondiDTO= "+esitoLeggiRiepilogoFondiDTO.toString());
			
			RipartizioneImpegniLiquidazioneDTO[] ripartizioneImpegniLiquidazioneDTO= esitoLeggiRiepilogoFondiDTO.getRipartizioneImpegniLiquidazione();
			ImpegnoDTO[] filtro = new ImpegnoDTO[ripartizioneImpegniLiquidazioneDTO.length];
			Long[] idImpegni = new Long[ripartizioneImpegniLiquidazioneDTO.length];
			for(int i = 0; i < ripartizioneImpegniLiquidazioneDTO.length; i++) {
				filtro[i] = new ImpegnoDTO();
				filtro[i].setAnnoEsercizio(ripartizioneImpegniLiquidazioneDTO[i].getAnnoEsercizio());
				filtro[i].setAnnoImpegno(ripartizioneImpegniLiquidazioneDTO[i].getAnnoImpegno());
				filtro[i].setNumeroImpegno(ripartizioneImpegniLiquidazioneDTO[i].getNumeroImpegno());
				idImpegni[i] = ripartizioneImpegniLiquidazioneDTO[i].getIdImpegno();
			}
			
			esitoOperazioneConsultaImpegniDTO = impegnoBilancioBusinessImpl.consultaImpegni(idU, userInfo.getIdIride(), filtro);
			LOG.debug(prf + "esitoOperazioneConsultaImpegniDTO= "+esitoOperazioneConsultaImpegniDTO.toString());
			//Se i ws di Contabilia non sono raggiungibili il messaggio da visualizzare qa video viene ricalcolato in "W.MB07"
			if(esitoOperazioneConsultaImpegniDTO.getMsg() != null && esitoOperazioneConsultaImpegniDTO.getMsg().getKey().equals(ErrorConstants.ERRORE_IMPEGNI_IMPOSSIBILE_REPERIRE_DATI_IMPEGNO) ){
				esitoOperazioneConsultaImpegniDTO.getMsg().setKey("W.MB07");
				esitoLeggiRiepilogoFondiDTO.setMessage( TraduttoreMessaggiPbandisrv.traduci(esitoOperazioneConsultaImpegniDTO.getMsg().getKey(), esitoOperazioneConsultaImpegniDTO.getMsg().getParams()) );
			}
			
			
			if(esitoOperazioneConsultaImpegniDTO.getImpegno() != null && esitoOperazioneConsultaImpegniDTO.getImpegno().length > 0) {
				
				for(int i = 0; i < esitoOperazioneConsultaImpegniDTO.getImpegno().length; i++) {
					esitoOperazioneAggiornaImpegnoDTO = impegnoBilancioBusinessImpl.aggiornaImpegno(idU, userInfo.getIdIride(), esitoOperazioneConsultaImpegniDTO.getImpegno()[i]);
					LOG.debug(prf + "esitoOperazioneAggiornaImpegnoDTO = "+esitoOperazioneAggiornaImpegnoDTO.toString());
				}
				
			}
			
			
			ImpegnoAllineatoDTO[] impegnoAllineatoDTO = liquidazioneBilancioBusinessImpl.getInfoImpegniPostAllineamento(idU, userInfo.getIdIride(), idImpegni);
			if(impegnoAllineatoDTO != null) {
				for(ImpegnoAllineatoDTO impegnoAllineato: impegnoAllineatoDTO)
				LOG.debug(prf + "impegnoAllineato = "+impegnoAllineato.toString());
			}
			
			
			EsitoLeggiRiepilogoFondiDTO esitoLeggiRiepilogoFondi = liquidazioneBilancioBusinessImpl.getRiepilogoFondiPostAllineamentoImpegni(idU, userInfo.getIdIride(), liquidazione.getIdProgetto(),
					liquidazione.getIdAttoLiquidazione(), esitoLeggiRiepilogoFondiDTO.getFontiFinanziarie(), ripartizioneImpegniLiquidazioneDTO);
			LOG.debug(prf + "esitoLeggiRiepilogoFondi = "+esitoLeggiRiepilogoFondi.toString());
			
		}catch (Exception e) {
			LOG.error(prf + e);
			ResponseCodeMessage resp = new ResponseCodeMessage();
			resp.setCode("ERRORE");
			resp.setMessage("Errore: " +e);
			return Response.ok().entity(resp).build();
		}
		
		LOG.debug(prf + " END");
		
		return Response.ok().entity(esitoLeggiRiepilogoFondiDTO).build();
		
	}

	@Override
	public Response avantiTabRiepilogoFondi(Long idU, ImpegniLiquidazioneDTO impegniLiquidazioneDTO, 
			                                Long idSoggetto, Long idSoggettoBeneficiario, Long idBandoLinea, HttpServletRequest req) throws Exception {
		
		String prf = "[LiquidazioneContributiServiceImpl::beneficiario]";
		LOG.debug(prf + " BEGIN");
		
		LOG.debug(prf + "Parametri in input -> idU = "+idU+", liquidazione = "+impegniLiquidazioneDTO.toString()+", idSoggetto = "+idSoggetto+ 
					    ", idSoggettoBeneficiario = "+idSoggettoBeneficiario+", idBandoLinea = "+idBandoLinea);

		BeneficiarioBilancioDTO beneficiarioBilancioDTO = null;
		DettaglioBeneficiarioBilancioDTO[] dettaglioBeneficiarioBilancioDTO = null;
		Beneficiario beneficiario = new Beneficiario();
		String iride = getIride(req);
		
		DatiGeneraliDTO datiGeneraliDTO = null;
		
		try {
			
			if(impegniLiquidazioneDTO.getLiquidazione().getIdAttoLiquidazione() == null || impegniLiquidazioneDTO.getLiquidazione().getIdAttoLiquidazione() == 0) {
				
				datiGeneraliDTO = gestioneDatiGeneraliBusinessImpl.caricaDatiGenerali(idU, getIride(req), impegniLiquidazioneDTO.getLiquidazione().getIdProgetto(), idSoggettoBeneficiario);
				EsitoLeggiAttoLiquidazioneDTO esitoLeggiAttoLiquidazioneDTO = liquidazioneBilancioBusinessImpl.getAttoLiquidazioneContributi(idU, getIride(req), 
						impegniLiquidazioneDTO.getLiquidazione().getIdProgetto(), datiGeneraliDTO.getIdFormaGiuridica(), datiGeneraliDTO.getIdDimensioneImpresa(), idBandoLinea);
				
				impegniLiquidazioneDTO.getLiquidazione().setIdAttoLiquidazione(esitoLeggiAttoLiquidazioneDTO.getIdAttoLiquidazione());
				
			}
			
			EsitoAggiornaRiepilogoFondiDTO esitoAggiornaRiepilogoFondiDTO = liquidazioneBilancioBusinessImpl.aggiornaRiepilogoFondiPostAllineamentoImpegni(idU, iride, 
					impegniLiquidazioneDTO.getLiquidazione().getIdProgetto(), impegniLiquidazioneDTO.getLiquidazione().getIdAttoLiquidazione(),impegniLiquidazioneDTO.getRipartizioneImpegniLiquidazioneDTO());
			LOG.debug(prf + "esitoAggiornaRiepilogoFondiDTO= "+esitoAggiornaRiepilogoFondiDTO.toString());
			
			beneficiarioBilancioDTO = liquidazioneBilancioBusinessImpl.findBeneficiario(idU, iride, impegniLiquidazioneDTO.getLiquidazione().getIdProgetto(), 
					                                                                    impegniLiquidazioneDTO.getLiquidazione().getIdAttoLiquidazione());
			LOG.debug(prf + "beneficiarioBilancioDTO= "+beneficiarioBilancioDTO.toString());
			
			dettaglioBeneficiarioBilancioDTO = liquidazioneBilancioBusinessImpl.elencoBeneficiariBilancio(idU, iride, impegniLiquidazioneDTO.getLiquidazione().getIdProgetto(), 
					impegniLiquidazioneDTO.getLiquidazione().getIdAttoLiquidazione(), beneficiarioBilancioDTO.getCodiceFiscaleBen(), beneficiarioBilancioDTO.getPartitaIva());
			
			LOG.debug(prf + "Trovati = "+dettaglioBeneficiarioBilancioDTO.length+" dettagli beneficiario bilancio");
			
		}catch (Exception e) {
			LOG.error(prf + e);
			ResponseCodeMessage resp = new ResponseCodeMessage();
			
			resp.setCode("ERRORE");
			resp.setMessage("Errore: " +e);
			
			if(e.getMessage()!= null ) {
				String message =  TraduttoreMessaggiPbandisrv.traduci(e.getMessage(), null );
				if(message != null && message.length() > 0) {
					resp.setCode(e.getMessage());
					resp.setMessage(message);
				}
				
			}
			
			beneficiario.setResponseCodeMessage(resp);
			
		}
		
		beneficiario.setIdAttoLiquidazione(impegniLiquidazioneDTO.getLiquidazione().getIdAttoLiquidazione());
		beneficiario.setBeneficiarioBilancioDTO(beneficiarioBilancioDTO);
		beneficiario.setDettaglioBeneficiarioBilancioDTO(dettaglioBeneficiarioBilancioDTO);
		
		LOG.debug(prf + " END");
		
		return Response.ok().entity(beneficiario).build();
		
	}
	
	
	@Override
	public Response apriDettaglioBeneficiario(Long idU, Long idProvincia, String iban, HttpServletRequest req) throws CSIException,SystemException, UnrecoverableException {
		
		String prf = "[LiquidazioneContributiServiceImpl::aggiornaBeneficiario]";
		LOG.debug(prf + " BEGIN");
		
		LOG.debug(prf + "Parametri in input -> idU = "+idU+", idProvincia = " +idProvincia+", iban = "+iban);
		
		String iride = getIride(req);
		DettaglioBeneficiario dettaglioBeneficiario = new DettaglioBeneficiario();
		
		try {
			
			dettaglioBeneficiario.setModalitaErogazione(gestioneDatiDiDominioBusinessImpl.findModalitaErogazioneFiltratePerBilancio(idU, iride));
			dettaglioBeneficiario.setNazioni(gestioneDatiDiDominioBusinessImpl.findNazioni(idU, iride));
			dettaglioBeneficiario.setProvince(gestioneDatiDiDominioBusinessImpl.findProvincie(idU, iride));
			dettaglioBeneficiario.setComuneDTO(gestioneDatiDiDominioBusinessImpl.findComuni(idU, iride, idProvincia));
			if(iban != null && iban.length() > 0) {
				dettaglioBeneficiario.setCoordinateBancarieDTO(gestioneDatiDiDominioBusinessImpl.checkIBAN(idU, iride, iban));
			}
			
			
		}catch (Exception e) {
			LOG.error(prf + e);
			ResponseCodeMessage resp = new ResponseCodeMessage();
			resp.setCode("ERRORE");
			resp.setMessage("Errore: " +e);
			return Response.ok().entity(resp).build();
		}
		
		LOG.debug(prf + " END");
		
		return Response.ok().entity(dettaglioBeneficiario).build();
		
	}
	
	
	@Override
	public Response aggiornaBeneficiario(Long idU, Long idProgetto, Long idAttoLiquidazione, DettaglioBeneficiarioBilancioDTO dettaglioBeneficiarioBilancioDTO, HttpServletRequest req) throws Exception {
		
		String prf = "[LiquidazioneContributiServiceImpl::aggiornaBeneficiario]";
		LOG.debug(prf + " BEGIN");
		
		LOG.debug(prf + "Parametri in input -> idU = "+idU+", idProgetto = "+idProgetto+", idAttoLiquidazione = "+idAttoLiquidazione+", "
				  + "dettaglioBeneficiarioBilancioDTO = "+dettaglioBeneficiarioBilancioDTO.toString());
		
		BeneficiarioBilancioDTO beneficiarioBilancioDTO;
		String iride = getIride(req);
		
		try {
			
			beneficiarioBilancioDTO = liquidazioneBilancioBusinessImpl.aggiornaBeneficiario(idU, iride, idProgetto, idAttoLiquidazione, dettaglioBeneficiarioBilancioDTO);
			
		}catch (Exception e) {
			LOG.error(prf + e);
			ResponseCodeMessage resp = new ResponseCodeMessage();
			resp.setCode("ERRORE");
			resp.setMessage("Errore: " +e);
			return Response.ok().entity(resp).build();
		}
		
		LOG.debug(prf + " END");
		
		return Response.ok().entity(beneficiarioBilancioDTO).build();
		
	}	
	

	@Override
	public Response avantiTabBeneficiario(Long idU, Long idAttoLiquidazione, Long idSoggetto, HttpServletRequest req) throws Exception {
		
		String prf = "[LiquidazioneContributiServiceImpl::datiIntegrativi]";
		LOG.debug(prf + " BEGIN");
		
		LOG.debug(prf + "Parametri in input -> idU = "+idU+", idAttoLiquidazione = "+idAttoLiquidazione+", idSoggetto = "+idSoggetto);
		
		DatiIntegrativiDTO datiIntegrativiDTO;
		EnteCompetenzaDTO[] enteCompetenzaDTO;
		SettoreEnteDTO[] settoreEnteDTO;
		ArrayList<SettoreEnteDTO> listSettoreEnte = new ArrayList<SettoreEnteDTO>();
		DatiIntegrativi datiIntegrativi;
		String iride = getIride(req);
		
		try {
			
			datiIntegrativiDTO = liquidazioneBilancioBusinessImpl.findDatiIntegrativi(idU, iride, idAttoLiquidazione);
			LOG.debug(prf + "liquidazioneBilancio = "+datiIntegrativiDTO.toString());
			
			enteCompetenzaDTO = liquidazioneBilancioBusinessImpl.findEntiCompetenza(idU, iride, idSoggetto);
			LOG.debug(prf + "enteCompetenzaDTO = "+enteCompetenzaDTO.toString());
			
			settoreEnteDTO = liquidazioneBilancioBusinessImpl.findSettoreEnte(idU, iride, idSoggetto, null);
			LOG.debug(prf + "Trovati = "+settoreEnteDTO.length+" settori");
			
			Long idEnteCompetenza = null;
			
			for(SettoreEnteDTO settore: settoreEnteDTO) {
				if(datiIntegrativiDTO.getIdSettoreEnte().equals(settore.getIdSettoreEnte()) ){
					idEnteCompetenza = settore.getIdEnteCompetenza();
					break;
				}
			}
			
			LOG.debug(prf + "idEnteCompetenza = "+idEnteCompetenza);
			
			for(SettoreEnteDTO settore: settoreEnteDTO) {
				if(settore.getIdEnteCompetenza().equals(idEnteCompetenza) ) {
					listSettoreEnte.add(settore);
				}
			}
			datiIntegrativi = new DatiIntegrativi();
			datiIntegrativi.setCausaleDiPagamento(datiIntegrativiDTO.getDescAtto());
			datiIntegrativi.setIdEnteCompetenza(idEnteCompetenza);
			datiIntegrativi.setIdSettoreDiAppartenenza(datiIntegrativiDTO.getIdSettoreEnte());
			datiIntegrativi.setListEnteDiCompetenza(enteCompetenzaDTO);
			datiIntegrativi.setListSettoreDiAppartenenza(listSettoreEnte);
			datiIntegrativi.setDatiIntegrativi(datiIntegrativiDTO);
			
		}catch (Exception e) {
			LOG.error(prf + e);
			ResponseCodeMessage resp = new ResponseCodeMessage();
			resp.setCode("ERRORE");
			resp.setMessage("Errore: " +e);
			return Response.ok().entity(resp).build();
		}
		
		LOG.debug(prf + " END");
		
		return Response.ok().entity(datiIntegrativi).build();
		
	}
	
	
	@Override
	public Response cercaSettoreAppartenenza(Long idU, Long idSoggetto, Long idEnte, HttpServletRequest req) throws Exception {
		
		String prf = "[LiquidazioneContributiServiceImpl::cercaSettoreAppartenenza]";
		LOG.debug(prf + " BEGIN");
		
		LOG.debug(prf + "Parametri in input -> idU = "+idU+", idSoggetto = "+idSoggetto+", idEnte = "+idEnte);
		
		String iride = getIride(req);
		SettoreEnteDTO[] settoreEnteDTO = null;
		
		try {
			
			settoreEnteDTO = liquidazioneBilancioBusinessImpl.findSettoreEnte(idU, iride,  idSoggetto, idEnte);
			LOG.debug(prf + " Trovati  = "+settoreEnteDTO.length+" Settori di appartenenza");
			
		}catch (NullPointerException e) {
			LOG.debug(prf + " Settori di appartenenza non trovati per l'Ente = "+idEnte);
		}catch (Exception e) {
			LOG.error(prf + e);
			throw e;
		}
		
		LOG.debug(prf + " END");
		
		return Response.ok().entity(settoreEnteDTO).build();
		
	}
	

	@Override
	public Response avantiTabDatiIntegrativi(Long idU, Long idProgetto, DatiIntegrativiDTO datiIntegrativiDTO, HttpServletRequest req) throws Exception {
		
		String prf = "[LiquidazioneContributiServiceImpl::ritenute]";
		LOG.debug(prf + " BEGIN");
		
		LOG.debug(prf + "Parametri in input -> idU = "+idU+", idProgetto = "+idProgetto+", datiIntegrativiDTO = "+datiIntegrativiDTO.toString() );
		
		EsitoRitenuteDTO esitoRitenuteDTO;
		AliquotaRienutaDTO aliquotaRienutaDTO = null;
		AliquotaRitenutaAttoDTO[] aliquotaRitenutaAttoDTO = null;
		Long idAliquotoRitenuta;
		String iride = getIride(req);
		
		try {
			
			liquidazioneBilancioBusinessImpl.salvaDatiIntegrativi(idU, iride, datiIntegrativiDTO);
			
			esitoRitenuteDTO = liquidazioneBilancioBusinessImpl.getRitenuta(idU, iride, datiIntegrativiDTO.getIdAttoLiquidazione());
			LOG.debug(prf + "esitoRitenuteDTO =  "+esitoRitenuteDTO.toString() );
			
			aliquotaRitenutaAttoDTO = liquidazioneBilancioBusinessImpl.getAliquotaRitenutaAtto(idU, iride);
			LOG.debug(prf + "aliquotaRitenutaAttoDTO =  "+aliquotaRitenutaAttoDTO.toString() );
			
			
			idAliquotoRitenuta = liquidazioneBilancioBusinessImpl.getIdAliquotaRitenuta(idU, iride, idProgetto);
			LOG.debug(prf + "idAliquotoRitenuta = "+idAliquotoRitenuta );
			
			if(idAliquotoRitenuta == null) {
				aliquotaRitenutaAttoDTO[0].setCodNaturaOnere("Nessuno");
			}
			
			esitoRitenuteDTO.getRitenuta().setIdAliquotaRitenuta(idAliquotoRitenuta);
			
		}catch (Exception e) {
			LOG.error(prf + e);
			esitoRitenuteDTO = new EsitoRitenuteDTO();
			esitoRitenuteDTO.setEsito(false);
			esitoRitenuteDTO.setMessage(e.getMessage());
		}
		
		aliquotaRienutaDTO = new AliquotaRienutaDTO();
		aliquotaRienutaDTO.setEsitoRitenuteDTO(esitoRitenuteDTO);
		aliquotaRienutaDTO.setAliquotaRitenutaAttoDTO(aliquotaRitenutaAttoDTO);
		
		LOG.debug(prf + " END");
		
		return Response.ok().entity(aliquotaRienutaDTO).build();
		
	}
	
	@Override
	public Response avantiTabRitenute(Long idU, Long idAttoLiquidazione, Double imponibile, Double sommeNonImponibili, Long idAliquotaRitenuta, HttpServletRequest req) throws Exception {
		
		String prf = "[LiquidazioneContributiServiceImpl::tabCreaAtto]";
		LOG.debug(prf + " BEGIN");
		
		LOG.debug(prf + "Parametri in input -> idU = "+idU+", idAttoLiquidazione = "+idAttoLiquidazione+", imponibile = "+imponibile+", sommeNonImponibili = "+sommeNonImponibili);
		
		EsitoRitenuteDTO esitoRitenuteDTO;
		EsitoInfoCreaAttoDTO esitoInfoCreaAttoDTO;
		String iride = getIride(req);
		
		try {
			
			RitenutaDTO ritenuta = new RitenutaDTO();
			ritenuta.setIdAttoLiquidazione(idAttoLiquidazione);
			ritenuta.setImponibile(imponibile);
			ritenuta.setSommeNonImponibili(sommeNonImponibili);
			ritenuta.setIdAliquotaRitenuta(idAliquotaRitenuta);
			esitoRitenuteDTO = liquidazioneBilancioBusinessImpl.aggiornaRitenuta(idU, iride, ritenuta);
			LOG.debug(prf + " esitoRitenuteDTO = "+esitoRitenuteDTO.toString());
			
			esitoInfoCreaAttoDTO = liquidazioneBilancioBusinessImpl.getInfoCreaAtto(idU, iride, idAttoLiquidazione);
			LOG.debug(prf + " esitoInfoCreaAttoDTO = "+esitoInfoCreaAttoDTO.toString());
			
		}catch (Exception e) {
			LOG.error(prf + e);
			throw e;
		}
		
		LOG.debug(prf + " END");
		
		return Response.ok().entity(esitoInfoCreaAttoDTO).build();
		
	}

	@Override
	public Response creaAtto(Long idU, Long idAttoLiquidazione, Long idProgetto, HttpServletRequest req) throws Exception {
		
		String prf = "[LiquidazioneContributiServiceImpl::creaAtto]";
		LOG.debug(prf + " BEGIN");
		
		LOG.debug(prf + "Parametri in input -> idU = "+idU+", idAttoLiquidazione = "+idAttoLiquidazione+", idProgetto = "+idProgetto);
		
		EsitoCreaAttoDTO esitoCreaAttoDTO = null;
		EsitoLeggiStatoElaborazioneDocDTO esitoLeggiStatoElaborazioneDocDTO;
		String iride = getIride(req);
		
		try {
			
			esitoCreaAttoDTO = liquidazioneBilancioBusinessImpl.creaAtto(idU, iride, idAttoLiquidazione, idProgetto);
			LOG.debug(prf + "esitoCreaAttoDTO= "+esitoCreaAttoDTO.toString());
			
			InputLeggiStatoElaborazioneDocDTO input = new InputLeggiStatoElaborazioneDocDTO();
			if(esitoCreaAttoDTO != null && esitoCreaAttoDTO.getAnnoAtto() != null && esitoCreaAttoDTO.getAnnoAtto() != null && esitoCreaAttoDTO.getAnnoAtto().length() > 0) {
				input.setAnnoBilancio(Integer.valueOf(esitoCreaAttoDTO.getAnnoAtto() ));
			}
			input.setIdAttoLiquidazione(idAttoLiquidazione);
			input.setIdOperazioneAsincrona(Integer.valueOf(esitoCreaAttoDTO.getIdOperazioneAsincrona()));
			
			esitoLeggiStatoElaborazioneDocDTO = liquidazioneBilancioBusinessImpl.leggiStatoElaborazioneDoc(idU, iride, input);
			LOG.debug(prf + " esitoLeggiStatoElaborazioneDocDTO = "+esitoLeggiStatoElaborazioneDocDTO.toString());
			
			
		}catch (Exception e) {
			LOG.error(prf + e);
			ResponseCodeMessage resp = new ResponseCodeMessage();
			resp.setCode("ERRORE");
			resp.setMessage("Errore: " +e);
			return Response.ok().entity(resp).build();
		}
		
		LOG.debug(prf + " END");
		
		return Response.ok().entity(esitoCreaAttoDTO).build();
		
	}
	
	private String getIride(HttpServletRequest req) {
		UserInfoSec userInfo = null;
		HttpSession session = req.getSession();
		userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
		
		return userInfo.getIdIride();
	}

}
