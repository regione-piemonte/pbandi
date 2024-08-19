/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweberog.business.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.csi.pbandi.pbservizit.exception.UtenteException;
import it.csi.pbandi.pbservizit.pbandisrv.dto.trasferimenti.CausaleTrasferimentoDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.trasferimenti.EsitoSalvaTrasferimentoDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.trasferimenti.SoggettoTrasferimentoDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.trasferimenti.TrasferimentoDTO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiDLineaDiInterventoVO;
import it.csi.pbandi.pbservizit.security.UserInfoSec;
import it.csi.pbandi.pbweberog.business.service.TrasferimentiService;
import it.csi.pbandi.pbweberog.dto.trasferimenti.CausaleTrasferimento;
import it.csi.pbandi.pbweberog.dto.trasferimenti.FiltroTrasferimento;
import it.csi.pbandi.pbweberog.dto.trasferimenti.SoggettoTrasferimento;
import it.csi.pbandi.pbweberog.dto.trasferimenti.TrasferimentiItem;
import it.csi.pbandi.pbweberog.dto.trasferimenti.Trasferimento;
import it.csi.pbandi.pbweberog.integration.dao.TrasferimentiDAO;
import it.csi.pbandi.pbweberog.util.BeanUtil;
import it.csi.pbandi.pbweberog.util.Constants;


@Service
public class TrasferimentiServiceImpl implements TrasferimentiService {
	
	@Autowired
	protected BeanUtil beanUtil;
	private Logger LOG = Logger.getLogger(Constants.COMPONENT_NAME);
	
	@Autowired
	TrasferimentiDAO trasferimentiDAO;
	
	@Override
	public Response getSoggettiTrasferimento(HttpServletRequest req) throws UtenteException, Exception {
		String prf = "[TrasferimentiServiceImpl::getSoggettiTrasferimento]";
		LOG.debug(prf + " BEGIN");
		try {
			HttpSession session = req.getSession();
			UserInfoSec userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
			Long idUtente = userInfo.getIdUtente();
			String idIride = userInfo.getIdIride();
			
			List<SoggettoTrasferimento> comboBeneficiari = new ArrayList<SoggettoTrasferimento>();
			
			SoggettoTrasferimentoDTO [] soggetti = trasferimentiDAO.findSoggettiTrasferimento(idUtente, idIride);

			if (soggetti != null) {
				for (SoggettoTrasferimentoDTO dto : soggetti) {
					SoggettoTrasferimento ben = new SoggettoTrasferimento();
					ben.setIdSoggettoBeneficiario(dto.getIdSoggettoBeneficiario());
//					ben.setDenominazioneBeneficiario(dto.getDenominazioneBeneficiario());
					ben.setDenominazioneBeneficiario(dto.getDenominazioneBeneficiario()+" - "+dto.getCfBeneficiario());

					
					ben.setCfBeneficiario(dto.getCfBeneficiario());
					comboBeneficiari.add(ben);
				}
			}
			
			LOG.debug(prf + " END");
			return Response.ok().entity(comboBeneficiari).build();
		} catch(Exception e) {
			throw e;
		}
	}

	@Override
	public Response getCausaliTrasferimento(HttpServletRequest req) throws UtenteException, Exception {
		String prf = "[TrasferimentiServiceImpl::getCausaliTrasferimento]";
		LOG.debug(prf + " BEGIN");
		try {
			HttpSession session = req.getSession();
			UserInfoSec userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
			Long idUtente = userInfo.getIdUtente();
			String idIride = userInfo.getIdIride();
			
			CausaleTrasferimentoDTO [] causali = trasferimentiDAO.findElencoCausaliTrasferimenti(idUtente, idIride);
			List<CausaleTrasferimento> comboCausaleTrasferimento = new ArrayList<CausaleTrasferimento>();
			if (causali != null) {
				for (CausaleTrasferimentoDTO dto : causali) {
					CausaleTrasferimento ben = new CausaleTrasferimento();
					ben.setIdCausaleTrasferimento(dto.getIdCausaleTrasferimento());
					ben.setDescCausaleTrasferimento(dto.getDescCausaleTrasferimento());
				
					comboCausaleTrasferimento.add(ben);
				}
			}
			LOG.debug(prf + " END");
			return Response.ok().entity(causali).build();
		} catch(Exception e) {
			throw e;
		}
	}
	
	@Override
	public Response getNormativa(HttpServletRequest req) throws UtenteException, Exception {
		
		String prf = "[TrasferimentiServiceImpl::getNormativa]";
		LOG.debug(prf + " BEGIN");

		List<PbandiDLineaDiInterventoVO> lineaDiInterventoVO = new ArrayList<PbandiDLineaDiInterventoVO>();
		lineaDiInterventoVO = trasferimentiDAO.findLineaDiIntervento();
		
		LOG.debug(prf + " END");
		
		return Response.ok().entity(lineaDiInterventoVO).build();
	}

	@Override
	public Response ricercaTrasferimenti(HttpServletRequest req, FiltroTrasferimento filtro)
			throws UtenteException, Exception {
		String prf = "[TrasferimentiServiceImpl::ricercaTrasferimenti]";
		LOG.debug(prf + " BEGIN");
		try {
			HttpSession session = req.getSession();
			UserInfoSec userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
			Long idUtente = userInfo.getIdUtente();
			String idIride = userInfo.getIdIride();
						
			TrasferimentoDTO[] elencoTrasferimento = trasferimentiDAO.ricercaTrasferimenti(idUtente, idIride, filtro);
			ArrayList<TrasferimentiItem> listaTrasferimento = new ArrayList<TrasferimentiItem>();
			if (elencoTrasferimento != null) {
				SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
				
				for (TrasferimentoDTO irr : elencoTrasferimento) {
					TrasferimentiItem trasferimento = new TrasferimentiItem();

					try {
						String dataTrasferimento = format.format(irr.getDtTrasferimento());	
						trasferimento.setDtTrasferimento(dataTrasferimento);
					} catch (Exception e) {
					}
					
					trasferimento.setIdTrasferimento(irr.getIdTrasferimento());
					trasferimento.setCfBeneficiario(irr.getCfBeneficiario());
					trasferimento.setCodiceTrasferimento(irr.getCodiceTrasferimento());
					trasferimento.setDescCausaleTrasferimento(irr.getDescCausaleTrasferimento());
					if(irr.getFlagPubblicoPrivato()!=null) {
						trasferimento.setFlagPubblicoPrivato(irr.getFlagPubblicoPrivato());
						trasferimento.setDescPubblicoPrivato(irr.getFlagPubblicoPrivato().equals(Constants.COD_TIPO_TRASFERIMENTO_PRIVATO) ? "PRIVATO" : "PUBBLICO");
					}
						
					trasferimento.setIdCausaleTrasferimento(irr.getIdCausaleTrasferimento());
					trasferimento.setIdSoggettoBeneficiario(irr.getIdSoggettoBeneficiario());
					trasferimento.setImportoTrasferimento(irr.getImportoTrasferimento());
					trasferimento.setDenominazioneBeneficiario(irr.getDenominazioneBeneficiario());
					
					trasferimento.setIdLineaDiIntervento(irr.getIdLineaDiIntervento());

					trasferimento.setIsEliminabile(true);
					trasferimento.setIsModificabile(true);
					
					listaTrasferimento.add(trasferimento);
				}
			}
			LOG.debug(prf + " END");
			return Response.ok().entity(listaTrasferimento).build();
		} catch(Exception e) {
			throw e;
		}
	}

	@Override
	public Response modificaTrasferimento(HttpServletRequest req, Trasferimento trasferimento)
			throws UtenteException, Exception {
		String prf = "[TrasferimentiServiceImpl::modificaTrasferimento]";
		LOG.debug(prf + " BEGIN");
		try {
			HttpSession session = req.getSession();
			UserInfoSec userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
			Long idUtente = userInfo.getIdUtente();
			String idIride = userInfo.getIdIride();
			TrasferimentoDTO dto = popolaTrasferimentoDto(trasferimento);
			EsitoSalvaTrasferimentoDTO esito = trasferimentiDAO.modificaTrasferimento(idUtente, idIride, dto);	
			LOG.debug(prf + " END");
			return Response.ok().entity(esito).build();
		} catch(Exception e) {
			throw e;
		}
	}

	private TrasferimentoDTO popolaTrasferimentoDto(Trasferimento irr) {
		TrasferimentoDTO dto = new TrasferimentoDTO();
		
		if (irr != null) {

			SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
			try {
				Date dataTrasferimento = format.parse(irr.getDtTrasferimento());	
				dto.setDtTrasferimento(dataTrasferimento);
			} catch (Exception e) {
			}
			
			dto.setIdTrasferimento(irr.getIdTrasferimento());
			dto.setCfBeneficiario(irr.getCfBeneficiario());
			dto.setCodiceTrasferimento(irr.getCodiceTrasferimento());
			dto.setDescCausaleTrasferimento(irr.getDescCausaleTrasferimento());
			dto.setFlagPubblicoPrivato(irr.getFlagPubblicoPrivato());
			dto.setIdCausaleTrasferimento(irr.getIdCausaleTrasferimento());
			dto.setIdSoggettoBeneficiario(irr.getIdSoggettoBeneficiario());
			dto.setImportoTrasferimento(irr.getImportoTrasferimento());
			dto.setDenominazioneBeneficiario(irr.getDenominazioneBeneficiario());
			dto.setIdLineaDiIntervento(irr.getIdLineaDiIntervento());
		}
		return dto;
	}

	@Override
	public Response getDettaglioTrasferimento(HttpServletRequest req, Long idTrasferimento)
			throws UtenteException, Exception {
		String prf = "[TrasferimentiServiceImpl::getDettaglioTrasferimento]";
		LOG.debug(prf + " BEGIN");
		try {
			HttpSession session = req.getSession();
			UserInfoSec userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
			Long idUtente = userInfo.getIdUtente();
			String idIride = userInfo.getIdIride();

			TrasferimentoDTO irr = trasferimentiDAO.findDettaglioTrasferimento(idUtente, idIride, idTrasferimento);	
			Trasferimento dettaglio = popolaTrasferimentoWeb(irr);
			
			LOG.debug(prf + " END");
			return Response.ok().entity(dettaglio).build();
		} catch(Exception e) {
			throw e;
		}
	}

	private Trasferimento popolaTrasferimentoWeb(TrasferimentoDTO irr) {
		Trasferimento dettaglio = new Trasferimento();
		if (irr != null) {
			
			SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
			try {
				String dataTrasferimento = format.format(irr.getDtTrasferimento());	
				dettaglio.setDtTrasferimento(dataTrasferimento);
			} catch (Exception e) {
			}
			
			dettaglio.setIdTrasferimento(irr.getIdTrasferimento());
			dettaglio.setCfBeneficiario(irr.getCfBeneficiario());
			dettaglio.setCodiceTrasferimento(irr.getCodiceTrasferimento());
			dettaglio.setDescCausaleTrasferimento(irr.getDescCausaleTrasferimento());
			dettaglio.setFlagPubblicoPrivato(irr.getFlagPubblicoPrivato());
			dettaglio.setIdCausaleTrasferimento(irr.getIdCausaleTrasferimento());
			dettaglio.setIdSoggettoBeneficiario(irr.getIdSoggettoBeneficiario());
			dettaglio.setImportoTrasferimento(irr.getImportoTrasferimento());
			
			dettaglio.setDenominazioneBeneficiario(irr.getDenominazioneBeneficiario()+" - "+irr.getCfBeneficiario());
			dettaglio.setDescPubblicoPrivato(irr.getFlagPubblicoPrivato().equals(Constants.COD_TIPO_TRASFERIMENTO_PRIVATO) ? "PRIVATO" : "PUBBLICO");
			dettaglio.setIdLineaDiIntervento(irr.getIdLineaDiIntervento());
		
		}
		return dettaglio;
	}

	@Override
	public Response cancellaTrasferimento(HttpServletRequest req, Long idTrasferimento)
			throws UtenteException, Exception {
		String prf = "[TrasferimentiServiceImpl::cancellaTrasferimento]";
		LOG.debug(prf + " BEGIN");
		try {
			HttpSession session = req.getSession();
			UserInfoSec userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
			Long idUtente = userInfo.getIdUtente();
			String idIride = userInfo.getIdIride();

			EsitoSalvaTrasferimentoDTO esito = trasferimentiDAO.eliminaTrasferimento(idUtente, idIride, idTrasferimento);	
			
			LOG.debug(prf + " END");
			return Response.ok().entity(esito).build();
		} catch(Exception e) {
			throw e;
		}
	}

	@Override
	public Response inserisciTrasferimento(HttpServletRequest req, Trasferimento trasferimento)
			throws UtenteException, Exception {
		String prf = "[TrasferimentiServiceImpl::inserisciTrasferimento]";
		LOG.debug(prf + " BEGIN");
		try {
			HttpSession session = req.getSession();
			UserInfoSec userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
			Long idUtente = userInfo.getIdUtente();
			String idIride = userInfo.getIdIride();

			TrasferimentoDTO dto = popolaTrasferimentoDto(trasferimento);
			
			EsitoSalvaTrasferimentoDTO esito = trasferimentiDAO.creaTrasferimento(idUtente, idIride, dto);	
			
			LOG.debug(prf + " END");
			return Response.ok().entity(esito).build();
		} catch(Exception e) {
			throw e;
		}
	}

}
