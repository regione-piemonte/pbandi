/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebfin.business.service.impl;

import java.math.BigDecimal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.csi.pbandi.pbservizit.pbandisrv.business.consultazioneattibilancio.ConsultazioneAttiBilancioBusinessImpl;
import it.csi.pbandi.pbservizit.pbandisrv.business.liquidazionebilancio.LiquidazioneBilancioBusinessImpl;
import it.csi.pbandi.pbservizit.pbandisrv.dto.consultazioneattibilancio.AttoDiLiquidazioneDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.consultazioneattibilancio.CodiceDescrizioneDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.consultazioneattibilancio.DettaglioAttoDiLiquidazioneDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.consultazioneattibilancio.RiepilogoAttoDiLiquidazionePerProgetto;
import it.csi.pbandi.pbservizit.pbandisrv.dto.liquidazionebilancio.EsitoRitenuteDTO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiDAliquotaRitenutaVO;
import it.csi.pbandi.pbservizit.security.UserInfoSec;
import it.csi.pbandi.pbwebfin.business.service.AttiDiLiquidazioneService;
import it.csi.pbandi.pbwebfin.dto.utils.ResponseCodeMessage;
import it.csi.pbandi.pbwebfin.util.Constants;

@Service
public class AttiDiLiquidazioneServiceImpl implements AttiDiLiquidazioneService {

	private Logger LOG = Logger.getLogger(Constants.COMPONENT_NAME);
	
	@Autowired
	private ConsultazioneAttiBilancioBusinessImpl consultazioneAttiBilancioBusinessImpl;
	
	@Autowired
	private LiquidazioneBilancioBusinessImpl liquidazioneBilancioBusinessImpl;

	@Override
	public Response caricaBeneficiariByDenomOrIdBen(String denominazione, Long idBeneficiario,
			@Context HttpServletRequest req) throws  Exception {
		
		String prf = "[AttiDiLiquidazioneServiceImpl::caricaBeneficiariByDenomOrIdBen]";
		LOG.info(prf + " BEGIN");
		LOG.info(prf + "Parametri in input -> idBeneficiario = "+idBeneficiario+", denominazione = "+ denominazione);
		
		CodiceDescrizioneDTO[] codiceDescrizioneDTO = null;
		UserInfoSec userInfo = null;
		HttpSession session = req.getSession();
		userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
		LOG.info(prf + "userInfo = " + userInfo);
		
		try {
			codiceDescrizioneDTO = consultazioneAttiBilancioBusinessImpl.getBeneficiariConAttiDiLiquidazioneByDenomOrIdBen(denominazione, idBeneficiario, userInfo.getIdUtente(), userInfo.getIdIride());
		}catch (Exception e) {
			LOG.error(prf + e);
			ResponseCodeMessage resp = new ResponseCodeMessage();
			resp.setCode("ERRORE");
			resp.setMessage("Errore: " +e);
			return Response.ok().entity(resp).build();
		}
		
		LOG.info(prf + " END");
		
		return Response.ok().entity(codiceDescrizioneDTO).build();
		
	}

	@Override
	public Response caricaProgettiByIdBen(Long idBeneficiario, HttpServletRequest req) throws Exception {
		
		String prf = "[AttiDiLiquidazioneServiceImpl::caricaProgetti]";
		LOG.info(prf + " BEGIN");
		
		LOG.info(prf + "Parametri in input -> idBeneficiario = "+idBeneficiario);
		
		CodiceDescrizioneDTO[] codiceDescrizioneDTO = null;
		UserInfoSec userInfo = null;
		HttpSession session = req.getSession();
		userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
		LOG.info(prf + "userInfo = " + userInfo);
		
		try {
			codiceDescrizioneDTO = consultazioneAttiBilancioBusinessImpl.getProgettiConAttiDiLiquidazioneByIdBen(idBeneficiario, userInfo.getIdUtente(), userInfo.getIdIride());
		}catch (Exception e) {
			LOG.error(prf + e);
			ResponseCodeMessage resp = new ResponseCodeMessage();
			resp.setCode("ERRORE");
			resp.setMessage("Errore: " +e);
			return Response.ok().entity(resp).build();
		}
		
		LOG.info(prf + " END");
		
		return Response.ok().entity(codiceDescrizioneDTO).build();
		
	}

	@Override
	public Response cercaDatiAttiDiLiquidazione(Long idU, Long idBeneficiario, Long idProgetto, String annoEsercizio, String annoImpegno, String numeroImpegno, 
			                                String annoAtto, String numeroAtto, HttpServletRequest req) throws java.lang.Exception {
		
		String prf = "[AttiDiLiquidazioneServiceImpl::cercaAttiDiLiquidazione]";
		LOG.debug(prf + " BEGIN");
		
		LOG.debug(prf + "Parametri in input -> idU = "+idU+", idBeneficiario = "+idBeneficiario+", idProgetto = "+idProgetto+", annoEsercizio = "+annoEsercizio+", annoImpegno = "+annoImpegno+
				        ", numeroImpegno = "+numeroImpegno+", annoAtto = "+annoAtto);
		
		AttoDiLiquidazioneDTO[] attoDiLiquidazioneDTO = null;
		UserInfoSec userInfo = null;
		HttpSession session = req.getSession();
		userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
		LOG.debug(prf + "userInfo = " + userInfo);
		
		AttoDiLiquidazioneDTO filtro = new AttoDiLiquidazioneDTO();
//		filtro.setIdBeneficiarioBilancio(idBeneficiario);
		filtro.setIdSoggetto(idBeneficiario);
		filtro.setIdProgetto(idProgetto);
		
		filtro.setAnnoEsercizioImpegno(annoEsercizio);
		filtro.setAnnoImpegno(annoImpegno);
		filtro.setNumeroImpegno(numeroImpegno);
		
		filtro.setAnnoAtto(annoAtto);
		filtro.setNumeroAtto(numeroAtto);
		try {
			attoDiLiquidazioneDTO = consultazioneAttiBilancioBusinessImpl.findAttiDiLiquidazione(idU, userInfo.getIdIride(), filtro);
		}catch (Exception e) {
			LOG.error(prf + e);
			ResponseCodeMessage resp = new ResponseCodeMessage();
			resp.setCode("ERRORE");
			resp.setMessage("Errore: " +e);
			return Response.ok().entity(resp).build();
		}
	
		LOG.debug(prf + " END");
		
		return Response.ok().entity(attoDiLiquidazioneDTO).build();
		
	}

	@Override
	public Response cercaDettaglioAttoDiLiquidazione(Long idU, Long idAttoDiLiquidazione, HttpServletRequest req) throws java.lang.Exception {
		
		String prf = "[AttiDiLiquidazioneServiceImpl::cercaDettaglioAttoDiLiquidazione]";
		LOG.debug(prf + " BEGIN");
		
		LOG.debug(prf + "Parametri in input -> idU = "+idU+", idAttoDiLiquidazione = "+idAttoDiLiquidazione);
		
		DettaglioAttoDiLiquidazioneDTO dettaglioAttoDiLiquidazioneDTO = null;
		EsitoRitenuteDTO  esitoRitenuteDTO = null;
		String naturaOnereCodiceTributo = null;
		UserInfoSec userInfo = null;
		HttpSession session = req.getSession();
		userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
		LOG.debug(prf + "userInfo = " + userInfo);
		
		try {
			
			dettaglioAttoDiLiquidazioneDTO = consultazioneAttiBilancioBusinessImpl.getDettaglioAttoDiLiquidazione(idU, userInfo.getIdIride(), idAttoDiLiquidazione);
			esitoRitenuteDTO = liquidazioneBilancioBusinessImpl.getRitenuta(idU, userInfo.getIdIride(), idAttoDiLiquidazione);
			dettaglioAttoDiLiquidazioneDTO.setDRImponibile(esitoRitenuteDTO.getRitenuta().getImponibile());
			
			//Popoliamo il campo "Natura Onere e Codice Tributo" del tab "Ritenute"
			PbandiDAliquotaRitenutaVO aliquotaVO = new PbandiDAliquotaRitenutaVO();
			if(esitoRitenuteDTO.getRitenuta().getIdAliquotaRitenuta() != null ) {
				aliquotaVO.setIdAliquotaRitenuta(new BigDecimal(esitoRitenuteDTO.getRitenuta().getIdAliquotaRitenuta()));
				PbandiDAliquotaRitenutaVO aliquotaRitenutaVO = liquidazioneBilancioBusinessImpl.getAliquota(aliquotaVO);
				LOG.debug(prf + "aliquotaRitenutaVO = " + aliquotaRitenutaVO);
				naturaOnereCodiceTributo = aliquotaRitenutaVO.getCodNaturaOnere() +"-"+ aliquotaRitenutaVO.getDescNaturaOnere() +"-"+ aliquotaRitenutaVO.getCodOnere() +"-"+ aliquotaRitenutaVO.getDescAliquota();
				dettaglioAttoDiLiquidazioneDTO.setDRAliquotaRitenuta(naturaOnereCodiceTributo);
			}
			
			
		}catch (Exception e) {
			LOG.error(prf + e);
			throw e;
		}
		
		LOG.debug(prf + " END");
		
		return Response.ok().entity(dettaglioAttoDiLiquidazioneDTO).build();
		
	}

	@Override
	public Response cercaRiepilogoAttiLiquidazione(Long idU, Long idProgetto, HttpServletRequest req) throws java.lang.Exception {
		

		String prf = "[AttiDiLiquidazioneServiceImpl::cercaRiepilogoAttiLiquidazione]";
		LOG.debug(prf + " BEGIN");
		
		LOG.debug(prf + "Parametri in input -> idU = "+idU+", idProgetto = "+idProgetto);
		
		RiepilogoAttoDiLiquidazionePerProgetto[] riepilogoAttoDiLiquidazionePerProgetto = null;
		UserInfoSec userInfo = null;
		HttpSession session = req.getSession();
		userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
		LOG.debug(prf + "userInfo = " + userInfo);
		
		try {
			riepilogoAttoDiLiquidazionePerProgetto = consultazioneAttiBilancioBusinessImpl.findRiepilogoAttiLiquidazione(idU, userInfo.getIdIride(), idProgetto);
		}catch (Exception e) {
			LOG.error(prf + e);
			ResponseCodeMessage resp = new ResponseCodeMessage();
			resp.setCode("ERRORE");
			resp.setMessage("Errore: " +e);
			return Response.ok().entity(resp).build();
		}
		
		LOG.debug(prf + " END");
		
		return Response.ok().entity(riepilogoAttoDiLiquidazionePerProgetto).build();
		
	}

}
