/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweberog.business.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.csi.pbandi.pbservizit.exception.UtenteException;
import it.csi.pbandi.pbservizit.pbandisrv.business.neoflux.NeofluxBusinessImpl;
import it.csi.pbandi.pbservizit.pbandisrv.dto.recupero.CausaleErogazioneDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.recupero.DettaglioRecuperoDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.recupero.EsitoSalvaRecuperoDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.recupero.MessaggioDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.recupero.ModalitaAgevolazioneDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.recupero.MotivoRevocaDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.recupero.RecuperoDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.recupero.RevocaDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.recupero.TipologiaRecuperoDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.revoca.PeriodoDTO;
import it.csi.pbandi.pbservizit.pbandiutil.commonweb.UserInfoHelper;
import it.csi.pbandi.pbservizit.security.UserInfoSec;
import it.csi.pbandi.pbweberog.business.service.RecuperiService;
import it.csi.pbandi.pbweberog.dto.CodiceDescrizione;
import it.csi.pbandi.pbweberog.dto.EsitoOperazioni;
import it.csi.pbandi.pbweberog.dto.disimpegni.RigaModificaRevocaItem;
import it.csi.pbandi.pbweberog.dto.recupero.DettaglioRecupero;
import it.csi.pbandi.pbweberog.dto.recupero.RigaModificaRecuperoItem;
import it.csi.pbandi.pbweberog.dto.recupero.RigaRecuperoItem;
import it.csi.pbandi.pbweberog.dto.recupero.Soppressione;
import it.csi.pbandi.pbweberog.integration.dao.RecuperiDAO;
import it.csi.pbandi.pbweberog.integration.request.RequestModificaRecupero;
import it.csi.pbandi.pbweberog.integration.request.RequestSalvaRecuperi;
import it.csi.pbandi.pbweberog.util.BeanUtil;
import it.csi.pbandi.pbweberog.util.Constants;
import it.csi.pbandi.pbweberog.util.DateUtil;
import it.csi.pbandi.pbweberog.util.ErrorMessages;
import it.csi.pbandi.pbweberog.util.MessageConstants;
import it.csi.pbandi.pbweberog.util.NumberUtil;

@Service
public class RecuperiServiceImpl implements RecuperiService {
	@Autowired
	protected BeanUtil beanUtil;
	private Logger LOG = Logger.getLogger(Constants.COMPONENT_NAME);
	
	@Autowired
	RecuperiDAO recuperiDAO;
	
	@Autowired
	NeofluxBusinessImpl neofluxSrv;
	
	@Autowired
	UserInfoHelper userInfoHelper;
	
	@Override
	public Response isRecuperoAccessibile(HttpServletRequest req, Long idProgetto) throws UtenteException, Exception {
		String prf = "[RecuperiServiceImpl::isRecuperoAccessibile]";
		LOG.info(prf + " BEGIN");
		try {
			HttpSession session = req.getSession();
			UserInfoSec userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
			Long idUtente = userInfo.getIdUtente();
			String idIride = userInfo.getIdIride();
			boolean isAccessibile = recuperiDAO.isRecuperoAccessibile(idUtente, idIride, idProgetto);
			EsitoOperazioni esito = new EsitoOperazioni();
			esito.setEsito(isAccessibile);
			if(!isAccessibile)
				esito.setMsg(MessageConstants.WARN_RECUPERO_FUNZIONALITA_NON_ABILITATA);
			LOG.info(prf + " END");
			return Response.ok().entity(esito).build();
		} catch(Exception e) {
			throw e;
		}
	}
	@Override
	public Response getTipologieRecuperi(HttpServletRequest req) throws UtenteException, Exception {
		String prf = "[RecuperiServiceImpl::getTipologieRecuperi]";
		LOG.info(prf + " BEGIN");
		try {
			HttpSession session = req.getSession();
			UserInfoSec userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
			Long idUtente = userInfo.getIdUtente();
			String idIride = userInfo.getIdIride();
			String codiceRuolo = userInfo.getCodiceRuolo();
			TipologiaRecuperoDTO[] tipologieRecupero = recuperiDAO.findTipologieRecuperi(idUtente, idIride, codiceRuolo);

			ArrayList<CodiceDescrizione> result = new ArrayList<CodiceDescrizione>();
			if (tipologieRecupero != null) {
				for (TipologiaRecuperoDTO dto : tipologieRecupero) {
					CodiceDescrizione tipologia = new CodiceDescrizione();
					tipologia.setCodice(NumberUtil.getStringValue(dto.getIdTipoRecupero()));
					tipologia.setDescrizione(dto.getDescTipoRecupero());
					result.add(tipologia);
				}
			}

			LOG.info(prf + " END");
			return Response.ok().entity(result).build();
		} catch(Exception e) {
			throw e;
		}
	}
	@Override
	public Response getAnnoContabili(HttpServletRequest req) throws UtenteException, Exception {
		String prf = "[RecuperiServiceImpl::getAnnoContabili]";
		LOG.info(prf + " BEGIN");
		try {
			HttpSession session = req.getSession();
			UserInfoSec userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
			Long idUtente = userInfo.getIdUtente();
			String idIride = userInfo.getIdIride();
			PeriodoDTO[] periodi =  recuperiDAO.findPeriodi(idUtente, idIride);
			
			Map <String,String>mapProps=new HashMap<String,String>();
			mapProps.put("idPeriodo", "codice");
			mapProps.put("descPeriodoVisualizzata", "descrizione");
			ArrayList<CodiceDescrizione> result = beanUtil.transformToArrayList(periodi, CodiceDescrizione.class,mapProps);

			LOG.info(prf + " END");
			return Response.ok().entity(result).build();
		} catch(Exception e) {
			throw e;
		}
	}
	@Override
	public Response getProcesso(HttpServletRequest req, Long idProgetto) throws UtenteException, Exception {
		String prf = "[RecuperiServiceImpl::getProcesso]";
		LOG.info(prf + " BEGIN");
		try {
			HttpSession session = req.getSession();
			UserInfoSec userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
			Long idUtente = userInfo.getIdUtente();
			String idIride = userInfo.getIdIride();
			Long processo = neofluxSrv.getProcesso(idUtente, idIride, idProgetto);

			LOG.info(prf + " END");
			return Response.ok().entity(processo).build();
		} catch(Exception e) {
			throw e;
		}
	}
	@Override
	public Response getRecuperi(HttpServletRequest req, Long idProgetto) throws UtenteException, Exception {
		String prf = "[RecuperiServiceImpl::getRecuperi]";
		LOG.info(prf + " BEGIN");
		try {
			HttpSession session = req.getSession();
			UserInfoSec userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
			Long idUtente = userInfo.getIdUtente();
			String idIride = userInfo.getIdIride();
			RecuperoDTO[] recuperi  = recuperiDAO.findRecuperi(idUtente , idIride , idProgetto);
			ArrayList<RigaRecuperoItem> result = new ArrayList<RigaRecuperoItem>();
			RigaRecuperoItem rigaTotale = new RigaRecuperoItem();
			rigaTotale.setIsRigaModalita(Boolean.FALSE);
			rigaTotale.setDtRevoca(null);
			rigaTotale.setEstremiRevoca(null);
			rigaTotale.setIdModalitaAgevolazione(null);
			rigaTotale.setImportoAgevolato(0D);
			rigaTotale.setImportoErogato(0D);
			rigaTotale.setImportoTotaleRecuperato(0D);
			rigaTotale.setImportoTotaleRevoche(0D);
			rigaTotale.setIsRigaTotale(Boolean.TRUE);
			rigaTotale.setLabel("Totale");
			if (recuperi != null) {
				for (RecuperoDTO dto : recuperi) {
					RigaRecuperoItem rigaModalita = new RigaRecuperoItem();
					rigaModalita.setIsRigaModalita(Boolean.TRUE);
					rigaModalita.setDtRevoca(DateUtil.formatDate(dto.getDtUltimaRevoca()));
					rigaModalita.setEstremiRevoca(dto.getEstremiUltimaRevoca());
					rigaModalita.setIdModalitaAgevolazione(dto.getIdModalitaAgevolazione());
					rigaModalita.setImportoAgevolato(dto.getQuotaImportoAgevolato() == null ? 0D : dto.getQuotaImportoAgevolato());
					rigaModalita.setImportoErogato(dto.getImportoErogazioni() == null ? 0D : dto.getImportoErogazioni());
					rigaModalita.setImportoTotaleRecuperato(dto.getTotaleImportoRecuperato() == null ? 0D : dto.getTotaleImportoRecuperato());
					rigaModalita.setImportoTotaleRevoche(dto.getTotaleImportoRevocato() == null ? 0D : dto.getTotaleImportoRevocato());
					rigaModalita.setIsRigaTotale(Boolean.FALSE);
					rigaModalita.setLabel(dto.getDescModalitaAgevolazione());
					rigaModalita.setImportoRecupero(null);

					/* * Sommo gli importi della riga totale*/
					rigaTotale.setImportoAgevolato(NumberUtil.sum(NumberUtil.getDoubleValue(rigaModalita.getImportoAgevolato()), rigaTotale.getImportoAgevolato()));
					rigaTotale.setImportoErogato(NumberUtil.sum(NumberUtil.getDoubleValue(rigaModalita.getImportoErogato()), rigaTotale.getImportoErogato()));
					rigaTotale.setImportoTotaleRecuperato(NumberUtil.sum(NumberUtil.getDoubleValue(rigaModalita.getImportoTotaleRecuperato()), rigaTotale.getImportoTotaleRecuperato()));
					rigaTotale.setImportoTotaleRevoche(NumberUtil.sum(NumberUtil.getDoubleValue(rigaModalita.getImportoTotaleRevoche()), rigaTotale.getImportoTotaleRevoche()));

					result.add(rigaModalita);
					if (dto.getMotiviRevoche() != null && dto.getMotiviRevoche().length > 0) {
						rigaModalita.setHasRevoche(Boolean.TRUE);
						for (MotivoRevocaDTO motivo : dto.getMotiviRevoche()) {
							RigaRecuperoItem rigaMotivoRevoca = new RigaRecuperoItem();
							rigaMotivoRevoca.setIsRigaModalita(Boolean.FALSE);
							rigaMotivoRevoca.setDtRevoca(DateUtil.formatDate(motivo.getDtUltimaRevoca()));
							rigaMotivoRevoca.setEstremiRevoca(motivo.getEstremiUltimaRevoca());
							rigaMotivoRevoca.setIdModalitaAgevolazione(dto.getIdModalitaAgevolazione());
							rigaMotivoRevoca.setImportoAgevolato(null);
							rigaMotivoRevoca.setImportoErogato(null);
							rigaMotivoRevoca.setImportoTotaleRecuperato(null);
							rigaMotivoRevoca.setImportoTotaleRevoche(motivo.getTotaleImportoRevocato());
							rigaMotivoRevoca.setIsRigaTotale(Boolean.FALSE);
							rigaMotivoRevoca.setLabel(motivo.getDescMotivoRevoca());
							
							rigaMotivoRevoca.setIdRecupero(dto.getIdTipoRecupero());
							
							result.add(rigaMotivoRevoca);
						}
					} else {
						rigaModalita.setHasRevoche(Boolean.FALSE);
					}
				}
				result.add(rigaTotale);
			}
			LOG.info(prf + " END");
			return Response.ok().entity(result).build();
		} catch(Exception e) {
			throw e;
		}
	}
	@Override
	public Response checkRecuperi(HttpServletRequest req, RequestSalvaRecuperi salvaRequest)
			throws UtenteException, Exception {
		String prf = "[RecuperiServiceImpl::checkRecuperi]";
		LOG.info(prf + " BEGIN");
		try {
			HttpSession session = req.getSession();
			UserInfoSec userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
			Long idUtente = userInfo.getIdUtente();
			String idIride = userInfo.getIdIride();
			
			Long idProgetto = salvaRequest.getIdProgetto();
			Long idTipologiaRecupero = salvaRequest.getIdTipologiaRecupero();
			Long idAnnoContabile = salvaRequest.getIdAnnoContabile();
			String note = salvaRequest.getNoteRecupero();
			String estremiRecupero = salvaRequest.getEstremiRecupero();
			List<RigaRecuperoItem> recuperi = salvaRequest.getRecuperi();
			String dtRecupero = salvaRequest.getDtRecupero();
			
			// VALIDATE
			if(idProgetto == null) return inviaErroreBadRequest("parametro mancato nel body: ?{RequestSalvaRecuperi.idProgetto}");
			if(idTipologiaRecupero == null) return inviaErroreBadRequest("parametro mancato nel body: ?{RequestSalvaRecuperi.idTipologiaRecupero}");
			if(dtRecupero == null) return inviaErroreBadRequest("parametro mancato nel body: ?{RequestSalvaRecuperi.dtRecupero}");
			if (!DateUtil.isValidDate(dtRecupero)) return inviaErroreBadRequest("formatto data non valido nel body: ?{RequestSalvaRecuperi.dtRecupero}");
			if ( DateUtil.parseDate(dtRecupero).after(DateUtil.getDataOdierna()) ) return inviaErroreBadRequest(ErrorMessages.WRN_RECUPERO_DATA_RECUPERO);
			if(estremiRecupero != null && estremiRecupero.length() > 255) return inviaErroreBadRequest("{RequestSalvaRecuperi.estremiRecupero}  non può contenere più di 255 caratteri.");
			if(note != null && note.length() > 4000) return inviaErroreBadRequest("{RequestSalvaRecuperi.note}  non può contenere più di 4000 caratteri.");
			
			//SALVA
			EsitoSalvaRecuperoDTO esito = new EsitoSalvaRecuperoDTO();
			if (recuperi != null) {
				List<RecuperoDTO> listRecuperi = popolaRecuperi(recuperi, idProgetto, dtRecupero, idTipologiaRecupero, note, estremiRecupero, idAnnoContabile);
				if (!listRecuperi.isEmpty()) {
					try {
						esito = recuperiDAO.checkRecuperi(idUtente, idIride, beanUtil.transform(listRecuperi,RecuperoDTO.class));
					} catch (Exception re) {
						esito = new EsitoSalvaRecuperoDTO();
						esito.setEsito(Boolean.FALSE);
						MessaggioDTO error = new MessaggioDTO();
						error.setMsgKey(re.getMessage());
						esito.setMsgs(new MessaggioDTO[] { error });
					}
				} else {
					esito = new EsitoSalvaRecuperoDTO();
					esito.setEsito(Boolean.FALSE);
					MessaggioDTO error = new MessaggioDTO();
					error.setMsgKey(MessageConstants.MSG_RECUPERO_NESSUN_RECUPERO_MODIFICATO);
					esito.setMsgs(new MessaggioDTO[] { error });
				}
			} else {
				esito = new EsitoSalvaRecuperoDTO();
				esito.setEsito(Boolean.FALSE);
				MessaggioDTO error = new MessaggioDTO();
				error.setMsgKey(MessageConstants.MSG_RECUPERO_NESSUN_RECUPERO_MODIFICATO);
				esito.setMsgs(new MessaggioDTO[] { error });
			}
			LOG.info(prf + " END");
			return Response.ok().entity(esito).build();
		} catch(Exception e) {
			throw e;
		}
	}	
	
	private List<RecuperoDTO> popolaRecuperi(List<RigaRecuperoItem> recuperiItem, Long idProgetto, String dtRecupero,
			Long idTipologiaRecupero, String note, String estremiRecupero, Long idAnnoContabile) {
		String prf = "[RecuperiServiceImpl::popolaRecuperi]";
		LOG.info(prf + " START");		
		List<RecuperoDTO> listRecuperi = new ArrayList<RecuperoDTO>();
		for (RigaRecuperoItem item : recuperiItem) {
			if (item.getIsRigaModalita() && !item.getIsRigaTotale()) {
				/** Inserisco solo i recuperi che sono stati modificati */
				if (item.getImportoRecupero()  != null) {
					RecuperoDTO recupero = new RecuperoDTO();
					recupero.setDtRecupero(DateUtil.getDate(dtRecupero));
					recupero.setEstremiRecupero(estremiRecupero);
					recupero.setIdModalitaAgevolazione(new Long(item.getIdModalitaAgevolazione()));
					recupero.setIdProgetto(idProgetto);
					recupero.setIdTipoRecupero(idTipologiaRecupero);
					recupero.setImportoRecupero(item.getImportoRecupero());
					recupero.setNoteRecupero(note);
					recupero.setImportoErogazioni(item.getImportoErogato());
					recupero.setQuotaImportoAgevolato(item.getImportoAgevolato());
					recupero.setTotaleImportoRecuperato(item.getImportoTotaleRecuperato());
					recupero.setTotaleImportoRevocato(item.getImportoTotaleRevoche());					
					recupero.setIdPeriodo(idAnnoContabile);
					
					/** Inserisco solo i recuperi che sono stati modificati  */
					if (item.getImportoRecupero() != null) {
						if (NumberUtil.compare(item.getImportoRecupero(), NumberUtil.sum(item.getImportoRecupero(), item.getImportoRecupero())) != 0)
							listRecuperi.add(recupero);
					} else {
						listRecuperi.add(recupero);
					}
				}
			}
		}
		LOG.info(prf + " END");
		return listRecuperi;	
	}
	
	@Override
	public Response salvaRecuperi(HttpServletRequest req, RequestSalvaRecuperi salvaRequest)
			throws UtenteException, Exception {
		String prf = "[RecuperiServiceImpl::salvaRecuperi]";
		LOG.info(prf + " BEGIN");
		try {
			HttpSession session = req.getSession();
			UserInfoSec userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
			Long idUtente = userInfo.getIdUtente();
			String idIride = userInfo.getIdIride();
			
			Long idProgetto = salvaRequest.getIdProgetto();
			Long idTipologiaRecupero = salvaRequest.getIdTipologiaRecupero();
			Long idAnnoContabile = salvaRequest.getIdAnnoContabile();
			String note = salvaRequest.getNoteRecupero();
			String estremiRecupero = salvaRequest.getEstremiRecupero();
			List<RigaRecuperoItem> recuperi = salvaRequest.getRecuperi();
			String dtRecupero = salvaRequest.getDtRecupero();
			
			EsitoSalvaRecuperoDTO esito = new EsitoSalvaRecuperoDTO();
			if (recuperi != null) {
				List<RecuperoDTO> listRecuperi = popolaRecuperi(recuperi, idProgetto, dtRecupero, idTipologiaRecupero, note,
						estremiRecupero,idAnnoContabile);
				if (!listRecuperi.isEmpty()) {
					try {
						//IstanzaAttivitaDTO istanza = new IstanzaAttivitaDTO();
						//istanza.setId(istanzaAttivitaCorrente.getTaskIdentity());
						//istanza.setCodUtente(userInfoHelper.getCodUtenteFlux(user));
						esito = recuperiDAO.salvaRecuperi(idUtente, idIride, beanUtil.transform(listRecuperi, RecuperoDTO.class));
					} catch (Exception re) {
						esito = new EsitoSalvaRecuperoDTO();
						esito.setEsito(Boolean.FALSE);
						MessaggioDTO error = new MessaggioDTO();
						error.setMsgKey(re.getMessage());
					}
				} else {
					esito = new EsitoSalvaRecuperoDTO();
					esito.setEsito(Boolean.FALSE);
					MessaggioDTO error = new MessaggioDTO();
					error.setMsgKey(MessageConstants.MSG_RECUPERO_NESSUN_RECUPERO_MODIFICATO);
				}
			}
			

			LOG.info(prf + " END");
			return Response.ok().entity(esito).build();
		} catch(Exception e) {
			throw e;
		}
	}

	
	//////////////////////////////////////MODIFICA RECUPERO //////////////////////////////////////////////
	/////////////////////////////////////////////////////////////////////////////////////////////////////
	@Override
	public Response getRecuperiPerModifica(HttpServletRequest req, Long idProgetto) throws UtenteException, Exception {
		String prf = "[RecuperiServiceImpl::getRecuperiPerModifica]";
		LOG.info(prf + " BEGIN");
		try {
			HttpSession session = req.getSession();
			UserInfoSec userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
			Long idUtente = userInfo.getIdUtente();
			String idIride = userInfo.getIdIride();

			DettaglioRecuperoDTO filtro = new DettaglioRecuperoDTO();
			filtro.setIdProgetto(idProgetto);

			ModalitaAgevolazioneDTO[] modalitaAgevolazioni = recuperiDAO.findRiepilogoRecuperi(idUtente, idIride, filtro);
			LOG.info(prf + " modalita size : " + modalitaAgevolazioni.length);
			List<RigaModificaRecuperoItem> result = new ArrayList<RigaModificaRecuperoItem>();

			if (modalitaAgevolazioni != null && modalitaAgevolazioni.length > 0) {
				LOG.info(prf + " modalita size > 0: \n");
				/** Riga totali */
				RigaModificaRecuperoItem rigaTotale = new RigaModificaRecuperoItem();
				rigaTotale.setLabel("Totale");
				rigaTotale.setIsRigaModalita(Boolean.FALSE);
				rigaTotale.setIsRigaCausale(Boolean.FALSE);
				rigaTotale.setIsRigaRevoca(Boolean.FALSE);
				rigaTotale.setIsRigaRecupero(Boolean.FALSE);
				rigaTotale.setIsRigaTotale(Boolean.TRUE);
				rigaTotale.setHasCausaliErogazione(Boolean.FALSE);
				rigaTotale.setImportoAgevolato(0D);
				rigaTotale.setImportoErogato(0D);
				rigaTotale.setImportoRecuperato(0D);
				rigaTotale.setImportoRevocato(0D);

				/** Ciclo sulle modalita' di agevolazioni */
				for (ModalitaAgevolazioneDTO modalita : modalitaAgevolazioni) {

					RigaModificaRecuperoItem rigaModalita = new RigaModificaRecuperoItem();
					rigaModalita.setIsRigaModalita(Boolean.TRUE);
					rigaModalita.setIsRigaCausale(Boolean.FALSE);
					rigaModalita.setIsRigaRevoca(Boolean.FALSE);
					rigaModalita.setIsRigaRecupero(Boolean.FALSE);
					rigaModalita.setIsRigaTotale(Boolean.FALSE);
					rigaModalita.setHasCausaliErogazione(Boolean.FALSE);
					rigaModalita.setIdModalitaAgevolazione(NumberUtil.getStringValue(modalita.getIdModalitaAgevolazione()));
					rigaModalita.setLabel(modalita.getDescModalitaAgevolazione());
					rigaModalita.setImportoAgevolato(modalita.getQuotaImportoAgevolato() == null ? 0D : modalita.getQuotaImportoAgevolato());
					rigaModalita.setImportoErogato(modalita.getImportoErogazioni() == null ? 0D : modalita.getImportoErogazioni());
					rigaModalita.setImportoRevocato(modalita.getTotaleImportoRevocato() == null ? 0D : modalita.getTotaleImportoRevocato());
					rigaModalita.setImportoRecuperato(modalita.getTotaleImportoRecuperato() == null ? 0D : modalita.getTotaleImportoRecuperato());
					result.add(rigaModalita);

					/** Sommo gli importi per la riga totale */
					rigaTotale.setImportoAgevolato(NumberUtil.sum(NumberUtil.getDoubleValue(rigaModalita.getImportoAgevolato()), rigaTotale.getImportoAgevolato()));
					rigaTotale.setImportoErogato(NumberUtil.sum(NumberUtil.getDoubleValue(rigaModalita.getImportoErogato()), rigaTotale.getImportoErogato()));
					rigaTotale.setImportoRecuperato(NumberUtil.sum(NumberUtil.getDoubleValue(rigaModalita.getImportoRecuperato()), rigaTotale.getImportoRecuperato()));
					rigaTotale.setImportoRevocato(NumberUtil.sum(NumberUtil.getDoubleValue(rigaModalita.getImportoRevocato()), rigaTotale.getImportoRevocato()));

					/** Per ogni modalita' di agevolazione ciclo sulle causali */
					if (modalita.getCausaliErogazioni() != null
							&& modalita.getCausaliErogazioni().length > 0) {
						rigaModalita.setHasCausaliErogazione(Boolean.TRUE);
						for (CausaleErogazioneDTO causale : modalita.getCausaliErogazioni()) {
							RigaModificaRecuperoItem rigaCausale = new RigaModificaRecuperoItem();
							rigaCausale.setIsRigaModalita(Boolean.FALSE);
							rigaCausale.setIsRigaCausale(Boolean.TRUE);
							rigaCausale.setIsRigaRevoca(Boolean.FALSE);
							rigaCausale.setIsRigaRecupero(Boolean.FALSE);
							rigaCausale.setIsRigaTotale(Boolean.FALSE);
							rigaCausale.setLabel(causale.getDescCausale());
							rigaCausale.setImportoErogato(causale.getImportoErogazione());
							rigaCausale.setData(DateUtil.formatDate(causale.getDtContabile()));
							rigaCausale.setRiferimento(causale.getCodRiferimentoErogazione());
							result.add(rigaCausale);
						}
					}

					/** Per ogni modalita' di agevolazione ciclo sulle revoche */
					if (modalita.getRevoche() != null
							&& modalita.getRevoche().length > 0) {
						for (RevocaDTO revoca : modalita.getRevoche()) {
							RigaModificaRecuperoItem rigaRevoca = new RigaModificaRecuperoItem();
							rigaRevoca.setIsRigaModalita(Boolean.FALSE);
							rigaRevoca.setIsRigaCausale(Boolean.FALSE);
							rigaRevoca.setIsRigaRevoca(Boolean.TRUE);
							rigaRevoca.setIsRigaRecupero(Boolean.FALSE);
							rigaRevoca.setIsRigaTotale(Boolean.FALSE);
							rigaRevoca.setLabel(revoca.getDescMotivoRevoca());
							rigaRevoca.setImportoRevocato(revoca.getImporto());
							rigaRevoca.setData(DateUtil.formatDate(revoca.getDtRevoca()));
							rigaRevoca.setRiferimento(revoca.getEstremi());
							result.add(rigaRevoca);
						}
					}

					/** Per ogni modalita' di agevolazione ciclo sui recuperi */
					if (modalita.getRecuperi() != null && modalita.getRecuperi().length > 0) {
						for (DettaglioRecuperoDTO recupero : modalita.getRecuperi()) {
							RigaModificaRecuperoItem rigaRecupero = new RigaModificaRecuperoItem();
							rigaRecupero.setIsRigaModalita(Boolean.FALSE);
							rigaRecupero.setIsRigaCausale(Boolean.FALSE);
							rigaRecupero.setIsRigaRevoca(Boolean.FALSE);
							rigaRecupero.setIsRigaRecupero(Boolean.TRUE);
							rigaRecupero.setIsRigaTotale(Boolean.FALSE);
							rigaRecupero.setLabel(recupero.getDescTipoRecupero());
							rigaRecupero.setImportoRecuperato(recupero.getImportoRecupero());
							rigaRecupero.setData(DateUtil.formatDate(recupero.getDtRecupero()));
							rigaRecupero.setRiferimento(recupero.getEstremiRecupero());
							rigaRecupero.setIdRecupero(NumberUtil.getStringValue(recupero.getIdRecupero()));
							rigaRecupero.setIdAnnoContabile(recupero.getIdPeriodo());
							LOG.info("\nkkkk recupero.getIdPeriodo():"+recupero.getIdPeriodo());
							result.add(rigaRecupero);
						}
					}
				}
				result.add(rigaTotale);
			}
			LOG.info(prf + " END");
			return Response.ok().entity(result).build();
		} catch(Exception e) {
			throw e;
		}
	}

	@Override
	public Response checkPropostaCertificazioneProgetto(HttpServletRequest req, Long idProgetto)
			throws UtenteException, Exception {
		String prf = "[RecuperiServiceImpl::checkPropostaCertificazioneProgetto]";
		LOG.info(prf + " BEGIN");
		try {
			HttpSession session = req.getSession();
			UserInfoSec userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
			Long idUtente = userInfo.getIdUtente();
			String idIride = userInfo.getIdIride();

			Boolean esito = recuperiDAO.checkPropostaCertificazione(idUtente, idIride, idProgetto);
			LOG.info(prf + " END");
			return Response.ok().entity(esito).build();
		} catch(Exception e) {
			throw e;
		}
	}

	@Override
	public Response getDettaglioRecupero(HttpServletRequest req, Long idProgetto, Long idRecupero)
			throws UtenteException, Exception {
		String prf = "[RecuperiServiceImpl::getDettaglioRecupero]";
		LOG.info(prf + " BEGIN");
		try {
			HttpSession session = req.getSession();
			UserInfoSec userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
			Long idUtente = userInfo.getIdUtente();
			String idIride = userInfo.getIdIride();
	
			DettaglioRecuperoDTO filtro = new DettaglioRecuperoDTO();
			filtro.setIdProgetto(idProgetto);
			filtro.setIdRecupero(idRecupero);

			DettaglioRecupero dettaglio = null;

			/* Il metodo restituisce una sola modalita di agevolazione con un solo * recupero ( quello selezionato) */
			ModalitaAgevolazioneDTO[] listModalita = recuperiDAO.findRiepilogoRecuperi(idUtente, idIride, filtro);
			LOG.info(prf + " size " + listModalita.length);
			if (listModalita != null && listModalita.length == 1 && listModalita[0] != null
					&& listModalita[0].getRecuperi() != null && listModalita[0].getRecuperi().length == 1
					&& listModalita[0].getRecuperi()[0] != null) {

				ModalitaAgevolazioneDTO modalita = listModalita[0];
				DettaglioRecuperoDTO recupero = modalita.getRecuperi()[0];

				dettaglio = new DettaglioRecupero();
				dettaglio.setDataRecupero(DateUtil.formatDate(recupero.getDtRecupero()));
				dettaglio.setEstremiDeterminaRecupero(recupero.getEstremiRecupero());
				dettaglio.setIdRecupero(recupero.getIdRecupero());
				dettaglio.setIdTipoRecupero(recupero.getIdTipoRecupero());
				dettaglio.setImportoAgevolato(modalita.getQuotaImportoAgevolato());
				dettaglio.setImportoErogato(modalita.getImportoErogazioni());
				dettaglio.setImportoRecuperato(modalita.getTotaleImportoRecuperato());
				dettaglio.setImportoRevocato(modalita.getTotaleImportoRevocato());
				dettaglio.setImportoRecupero(recupero.getImportoRecupero());
				dettaglio.setModalitaAgevolazione(modalita.getDescModalitaAgevolazione());
				dettaglio.setIdAnnoContabile(recupero.getIdPeriodo());
				dettaglio.setNoteRecupero(recupero.getNote());
			}

			LOG.info(prf + " END");
			return Response.ok().entity(dettaglio).build();
		} catch(Exception e) {
			throw e;
		}
	}

	@Override
	public Response modificaRecupero(HttpServletRequest req, RequestModificaRecupero modificaRequest)
			throws UtenteException, Exception {
		String prf = "[RecuperiServiceImpl::modificaRecuperos]";
		LOG.info(prf + " BEGIN");
		try {
			HttpSession session = req.getSession();
			UserInfoSec userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
			Long idUtente = userInfo.getIdUtente();
			String idIride = userInfo.getIdIride();
			DettaglioRecupero recupero = modificaRequest.getRecupero();
			
			// VALIDATE
			if(recupero.getIdTipoRecupero() == null)
				return inviaErroreBadRequest("parametro mancato nel body: ?{RequestModificaRecupero.recupero.idTipoRecupero}");
			if (!DateUtil.isValidDate(recupero.getDataRecupero())) 
				return inviaErroreBadRequest("parametro mancato nel body: ?{RequestModificaRecupero.recupero.dataRecupero}");
			if (DateUtil.parseDate(recupero.getDataRecupero()).after(DateUtil.getDataOdierna())) 
				return inviaErroreBadRequest("parametro non valido : La data del recupero non può essere successiva alla data odierna.");				
			if (recupero.getEstremiDeterminaRecupero() != null && recupero.getEstremiDeterminaRecupero().length() > 255) 
				return inviaErroreBadRequest("parametro non valido: Il campo Estremi determina non può contenere più di 255 caratteri.");
			if (recupero.getNoteRecupero() != null && recupero.getNoteRecupero().length() > 4000)
				return inviaErroreBadRequest("parametro non valido: Il campo note non può contenere più di 4000 caratteri.");
			if (recupero.getImportoRecupero() == null)
				return inviaErroreBadRequest("parametro mancato nel body: ?{RequestModificaRecupero.recupero.importoRecupero}");
			if (NumberUtil.compare(recupero.getImportoRecupero(), new Double(0)) < 0)
				return inviaErroreBadRequest("parametro non valido: ?{RequestModificaRecupero.recupero.importoRecupero}, Valore negativo non ammesso.");
			
			// TRASFORMA DTO
			ModalitaAgevolazioneDTO modalitaDTO = new ModalitaAgevolazioneDTO();
			DettaglioRecuperoDTO recuperoDTO = new DettaglioRecuperoDTO();
			recuperoDTO.setDtRecupero(DateUtil.getDate(recupero.getDataRecupero()));
			recuperoDTO.setEstremiRecupero(recupero.getEstremiDeterminaRecupero());
			recuperoDTO.setIdRecupero(recupero.getIdRecupero());
			recuperoDTO.setIdTipoRecupero(recupero.getIdTipoRecupero());
			recuperoDTO.setNote(recupero.getNoteRecupero());
			recuperoDTO.setImportoRecupero(recupero.getImportoRecupero());	 
			recuperoDTO.setIdPeriodo(recupero.getIdAnnoContabile());			
			modalitaDTO.setQuotaImportoAgevolato(recupero.getImportoAgevolato());
			modalitaDTO.setImportoErogazioni(recupero.getImportoErogato());
			modalitaDTO.setTotaleImportoRecuperato(recupero.getImportoRecuperato());
			modalitaDTO.setTotaleImportoRevocato(recupero.getImportoRevocato());
			modalitaDTO.setDescModalitaAgevolazione(recupero.getModalitaAgevolazione());
			modalitaDTO.setRecuperi(new DettaglioRecuperoDTO[] { recuperoDTO });
			// UPDATE
			EsitoSalvaRecuperoDTO esito = recuperiDAO.modificaRecupero(idUtente, idIride, modalitaDTO);
			LOG.info(prf + " END");
			return Response.ok().entity(esito).build();
		} catch(Exception e) {
			throw e;
		}
	}
	
	@Override
	public Response cancellaRecupero(HttpServletRequest req, Long idRecupero) throws UtenteException, Exception {
		String prf = "[RecuperiServiceImpl::cancellaRecupero]";
		LOG.info(prf + " BEGIN");
		try {
			HttpSession session = req.getSession();
			UserInfoSec userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
			Long idUtente = userInfo.getIdUtente();
			String idIride = userInfo.getIdIride();
			
			EsitoSalvaRecuperoDTO esito = recuperiDAO.eliminaRecupero(idUtente, idIride, idRecupero);
			LOG.info(prf + " END");
			return Response.ok().entity(esito).build();
		} catch(Exception e) {
			throw e;
		}
	}


	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	
	///////////////////////////////////////////// METODI DI RESPONSE HTTP /////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	private Response inviaErroreBadRequest(String msg) {
		EsitoOperazioni esito = new EsitoOperazioni();
		esito.setEsito(false);
		esito.setMsg(msg);
		return Response.status(Response.Status.BAD_REQUEST).entity(esito).type( MediaType.APPLICATION_JSON).build();
	}
	
	
	
	@Override
	public Response inizializzaSoppressioni(HttpServletRequest req, Long idProgetto) throws UtenteException, Exception {
		HttpSession session = req.getSession();
		UserInfoSec userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
		return Response.ok().entity(recuperiDAO.inizializzaSoppressioni(idProgetto, userInfo.getCodiceRuolo(), userInfo.getIdSoggetto(), userInfo.getIdUtente())).build();
	}
	
	@Override
	public Response salvaSoppressione(HttpServletRequest req, Soppressione soppressione) throws UtenteException, Exception {
		HttpSession session = req.getSession();
		UserInfoSec userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
		return Response.ok().entity(recuperiDAO.salvaSoppressione(soppressione, userInfo.getIdUtente())).build();
	}

}
