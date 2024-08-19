/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweberog.business.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.csi.pbandi.pbservizit.exception.UtenteException;
import it.csi.pbandi.pbservizit.pbandisrv.business.gestionedatididominio.GestioneDatiDiDominioBusinessImpl;
import it.csi.pbandi.pbservizit.pbandisrv.business.neoflux.NeofluxBusinessImpl;
import it.csi.pbandi.pbservizit.pbandisrv.dto.gestionedatididominio.Decodifica;
import it.csi.pbandi.pbservizit.pbandisrv.dto.revoca.EsitoSalvaRevocaDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.revoca.MancatoRecuperoDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.revoca.MessaggioDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.revoca.ModalitaAgevolazioneDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.revoca.QuotaParteVoceDiSpesaDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.revoca.RevocaModalitaAgevolazioneDTO;
import it.csi.pbandi.pbservizit.pbandisrv.interfacecsi.gestionedatididominio.GestioneDatiDiDominioSrv;
import it.csi.pbandi.pbservizit.security.UserInfoSec;
import it.csi.pbandi.pbweberog.business.service.GestioneDisimpegniService;
import it.csi.pbandi.pbweberog.dto.CodiceDescrizione;
import it.csi.pbandi.pbweberog.dto.EsitoOperazioni;
import it.csi.pbandi.pbweberog.dto.disimpegni.CausaleErogazioneDTO;
import it.csi.pbandi.pbweberog.dto.disimpegni.DettaglioRevocaIrregolarita;
import it.csi.pbandi.pbweberog.dto.disimpegni.DettaglioRevocaIrregolaritaDTO;
import it.csi.pbandi.pbweberog.dto.disimpegni.DsDettaglioRevocaIrregolarita;
import it.csi.pbandi.pbweberog.dto.disimpegni.DsDettaglioRevocaIrregolaritaDTO;
import it.csi.pbandi.pbweberog.dto.disimpegni.RecuperoDTO;
import it.csi.pbandi.pbweberog.dto.disimpegni.Revoca;
import it.csi.pbandi.pbweberog.dto.disimpegni.RevocaDTO;
import it.csi.pbandi.pbweberog.dto.disimpegni.RevocaIrregolarita;
import it.csi.pbandi.pbweberog.dto.disimpegni.RevocaIrregolaritaDTO;
import it.csi.pbandi.pbweberog.dto.disimpegni.RigaModificaRevocaItem;
import it.csi.pbandi.pbweberog.dto.revoca.DettaglioRevoca;
import it.csi.pbandi.pbweberog.dto.revoca.RigaRevocaItem;
import it.csi.pbandi.pbweberog.integration.dao.GestioneDisimpegniDAO;
import it.csi.pbandi.pbweberog.integration.request.RequestAssociaIrregolarita;
import it.csi.pbandi.pbweberog.integration.request.RequestFindIrregolarita;
import it.csi.pbandi.pbweberog.integration.request.RequestModificaDisimpegno;
import it.csi.pbandi.pbweberog.integration.request.RequestSalvaRevoche;
import it.csi.pbandi.pbweberog.util.BeanUtil;
import it.csi.pbandi.pbweberog.util.Constants;
import it.csi.pbandi.pbweberog.util.DateUtil;
import it.csi.pbandi.pbweberog.util.ErrorMessages;
import it.csi.pbandi.pbweberog.util.MessageConstants;
import it.csi.pbandi.pbweberog.util.NumberUtil;
import it.csi.pbandi.pbweberog.pbandiutil.common.ObjectUtil;

@Service
public class GestioneDisimpegniServiceImpl implements GestioneDisimpegniService {
	@Autowired
	protected BeanUtil beanUtil;
	private Logger LOG = Logger.getLogger(Constants.COMPONENT_NAME);
	
	//SecurityHelper springSecurityHelper;
	
	@Autowired
	GestioneDisimpegniDAO gestioneDisimpegniDAO;

	@Autowired
	GestioneDatiDiDominioBusinessImpl gestioneDatiDiDominioBusiness;
	
	@Autowired
	NeofluxBusinessImpl neofluxBusinessImpl;
	
	@Override
	public Response checkPropostaCertificazione(HttpServletRequest req, Long idProgetto)
			throws UtenteException, Exception {
		String prf = "[GestioneDisimpegniServiceImpl::checkPropostaCertificazione]";
		LOG.debug(prf + " BEGIN");
		try {
			HttpSession session = req.getSession();
			UserInfoSec userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
			Long idUtente = userInfo.getIdUtente();
			String idIride = userInfo.getIdIride();
			EsitoOperazioni esito = gestioneDisimpegniDAO.checkPropostaCertificazione(idUtente, idIride, idProgetto);
			LOG.debug(prf + " END");
			return Response.ok().entity(esito).build();
		} catch(Exception e) {
			throw e;
		}
	}

	@Override
	public Response getModalitaAgevolazione(HttpServletRequest req, Long idProgetto) throws UtenteException, Exception {
		String prf = "[GestioneDisimpegniServiceImpl::getModalitaAgevolazione]";
		LOG.debug(prf + " BEGIN");
		try {
			HttpSession session = req.getSession();
			UserInfoSec userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
			Long idUtente = userInfo.getIdUtente();
			String idIride = userInfo.getIdIride();
			RevocaDTO filtro = new RevocaDTO();
			filtro.setIdProgetto(idProgetto);
			
			it.csi.pbandi.pbweberog.dto.revoca.ModalitaAgevolazioneDTO[] modalita =  gestioneDisimpegniDAO.findRiepilogoRevoche(idUtente, idIride, filtro);
			
			LOG.debug(prf + " END");
			return Response.ok().entity(modalita).build();
		} catch(Exception e) {
			throw e;
		}
	}

	@Override
	public Response getErogazioni(HttpServletRequest req, it.csi.pbandi.pbweberog.dto.revoca.ModalitaAgevolazioneDTO[] modalitaAgevolazione) throws UtenteException, Exception {
		String prf = "[GestioneDisimpegniServiceImpl::getErogazioni]";
		LOG.debug(prf + " BEGIN");
		try {
			HttpSession session = req.getSession();
			UserInfoSec userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
			Long idUtente = userInfo.getIdUtente();
			String idIride = userInfo.getIdIride();
			ArrayList<RigaModificaRevocaItem> result = new ArrayList<RigaModificaRevocaItem>();
			 
			if (modalitaAgevolazione != null && modalitaAgevolazione.length > 0) {
				// inizializzo la riga del totale
				RigaModificaRevocaItem rigaTotale = new RigaModificaRevocaItem();
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
				
				// ciclo sulle modalita' di agevolazione
				for (it.csi.pbandi.pbweberog.dto.revoca.ModalitaAgevolazioneDTO modalita : modalitaAgevolazione) {
					RigaModificaRevocaItem rigaModalita = new RigaModificaRevocaItem();
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

					/*
					 * Sommo gli importi per la riga totale
					 */
					rigaTotale.setImportoAgevolato(NumberUtil.sum(NumberUtil.getDoubleValue(rigaModalita.getImportoAgevolato()), rigaTotale.getImportoAgevolato()));
					rigaTotale.setImportoErogato(NumberUtil.sum(NumberUtil.getDoubleValue(rigaModalita.getImportoErogato()),rigaTotale.getImportoErogato()));
					rigaTotale.setImportoRecuperato(NumberUtil.sum(NumberUtil.getDoubleValue(rigaModalita.getImportoRecuperato()),rigaTotale.getImportoRecuperato()));
					rigaTotale.setImportoRevocato(NumberUtil.sum(NumberUtil.getDoubleValue(rigaModalita.getImportoRevocato()),rigaTotale.getImportoRevocato()));				
					
					/*
					 * causali
					 */
					if (modalita.getCausaliErogazioni() != null && modalita.getCausaliErogazioni().length > 0) {
						rigaModalita.setHasCausaliErogazione(Boolean.TRUE);
						for (CausaleErogazioneDTO causale : modalita.getCausaliErogazioni()) {
						//	logger.shallowDump(causale,"info");
							RigaModificaRevocaItem rigaCausale = new RigaModificaRevocaItem();
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

			 

					/*
					 * recuperi
					 */
					if (modalita.getRecuperi() != null && modalita.getRecuperi().length > 0) {
						for (RecuperoDTO recupero : modalita.getRecuperi()) {
							LOG.info(prf + "++++++++++ recupero ++++++++++");
							RigaModificaRevocaItem rigaRecupero = new RigaModificaRevocaItem();
							rigaRecupero.setIsRigaModalita(Boolean.FALSE);
							rigaRecupero.setIsRigaCausale(Boolean.FALSE);
							rigaRecupero.setIsRigaRevoca(Boolean.FALSE);
							rigaRecupero.setIsRigaRecupero(Boolean.TRUE);
							rigaRecupero.setIsRigaTotale(Boolean.FALSE);
							rigaRecupero.setLabel(recupero.getDescTipoRecupero());
							rigaRecupero.setImportoRecuperato(recupero.getImportoRecupero());
							rigaRecupero.setData(DateUtil.formatDate(recupero.getDtRecupero()));
							rigaRecupero.setRiferimento(recupero.getEstremiRecupero());						
							result.add(rigaRecupero);
						}					
					}
				}
				
				// aggiungo per ultima la riga del totale
				//result.add(rigaTotale);
			}
			LOG.debug(prf + " END");
			return Response.ok().entity(result).build();
		} catch(Exception e) {
			throw e;
		}
	}

	
	@Override
	public Response getDisimpegni(HttpServletRequest req,
			it.csi.pbandi.pbweberog.dto.revoca.ModalitaAgevolazioneDTO[] modalitaAgev) throws UtenteException, Exception {
		String prf = "[GestioneDisimpegniServiceImpl::getDisimpegni]";
		LOG.debug(prf + " BEGIN");
		try {
//			HttpSession session = req.getSession();
//			boolean isAbilitatoModifica = springSecurityHelper.verifyCurrentUserForUC((Map) session.getValue(Constants.USERINFO_SESSIONATTR),
//							UseCaseConstants.UC_OPEDISIMP01);
//			
			ArrayList<RigaModificaRevocaItem> result = new ArrayList<RigaModificaRevocaItem>();		
			
			if (modalitaAgev != null && modalitaAgev.length > 0) {
				// inizializzo la riga del totale
				RigaModificaRevocaItem rigaTotale = new RigaModificaRevocaItem();
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
				
				// ciclo sulle modalita' di agevolazione
				for (it.csi.pbandi.pbweberog.dto.revoca.ModalitaAgevolazioneDTO modalita : modalitaAgev) {

					/*
					 * revoche
					 */
					if (modalita.getRevoche() != null && modalita.getRevoche().length > 0) {
						for (it.csi.pbandi.pbweberog.dto.revoca.RevocaDTO revoca : modalita.getRevoche()) {
							//&& modalita.getRevoche()[0].getCodCausaleDisimpegno()
							
							RigaModificaRevocaItem rigaRevoca = new RigaModificaRevocaItem();
							rigaRevoca.setIsRigaModalita(Boolean.FALSE);
							rigaRevoca.setIsRigaCausale(Boolean.FALSE);
							if(revoca.getCodCausaleDisimpegno().equals("REV")) {
								rigaRevoca.setIsRigaRevoca(Boolean.TRUE);
								rigaRevoca.setData(DateUtil.formatDate(revoca.getDtRevoca()));
							}
							
							rigaRevoca.setIsRigaRecupero(Boolean.FALSE);
							rigaRevoca.setIsRigaTotale(Boolean.FALSE);
							rigaRevoca.setIdRevoca(NumberUtil.getStringValue(revoca.getIdRevoca()));
							rigaRevoca.setLabel(revoca.getDescMotivoRevoca());
							rigaRevoca.setCausaleDisimpegno(revoca.getCausaleDisimpegno());
							rigaRevoca.setMotivazione(revoca.getDescMotivoRevoca());
							rigaRevoca.setImportoRevocato(revoca.getImporto());		
							rigaRevoca.setRiferimento(revoca.getEstremi());
							rigaRevoca.setIdMotivoRevoca(NumberUtil.getStringValue(revoca.getIdMotivoRevoca()));
							//M.R./////////////
							rigaRevoca.setOrdineRecupero(revoca.getFlagOrdineRecupero());
							rigaRevoca.setIdModalitaRecupero(revoca.getIdMancatoREcupero());
							rigaRevoca.setDescModalitaRecupero(revoca.getDescMancatoRecupero());
				
							// DETT_REVOCA_IRREG: codice che sostituisce il precedente, popolando anche
							// i dettagli revoca irregolarita e le dich. spesa associate.
							ArrayList<RevocaIrregolarita> irregolarita = new ArrayList<RevocaIrregolarita>();
							if (revoca.getRevocaIrregolarita() != null) {
								for (RevocaIrregolaritaDTO revIrregDTO : revoca.getRevocaIrregolarita()) {
									
									RevocaIrregolarita revIrreg = beanUtil.transform(revIrregDTO, RevocaIrregolarita.class);								
									
									// Somma l'importo in rigaRevoca.
									Double quotaIrreg = (revIrreg.getQuotaIrreg() == null) ? 0d : revIrreg.getQuotaIrreg();
									Double importoIrreg = (rigaRevoca.getImportoIrregolarita() == null) ? 0d : rigaRevoca.getImportoIrregolarita();
									rigaRevoca.setImportoIrregolarita(NumberUtil.sum(importoIrreg, quotaIrreg));
									// Popola i dettagli revoca irregolarita.
									ArrayList<DettaglioRevocaIrregolarita> dettagli = new ArrayList<DettaglioRevocaIrregolarita>();
									for (DettaglioRevocaIrregolaritaDTO dettaglioDTO : revIrregDTO.getDettagliRevocaIrregolarita()) {
										DettaglioRevocaIrregolarita dettaglio = beanUtil.transform(dettaglioDTO, DettaglioRevocaIrregolarita.class);
										// Popola le DS associate al dettaglio
										ArrayList<DsDettaglioRevocaIrregolarita> dsDettagli = new ArrayList<DsDettaglioRevocaIrregolarita>();
										for (DsDettaglioRevocaIrregolaritaDTO dsDettaglioDTO : dettaglioDTO.getDsDettagliRevocaIrregolarita()) {
											DsDettaglioRevocaIrregolarita dsDettaglio = beanUtil.transform(dsDettaglioDTO, DsDettaglioRevocaIrregolarita.class);
											dsDettagli.add(dsDettaglio);
										}
										dettaglio.setDsDettagliRevocaIrregolarita(dsDettagli);
										dettagli.add(dettaglio);
									}
									revIrreg.setDettagliRevocaIrregolarita(dettagli);
									irregolarita.add(revIrreg);
								}
							}
							// DETT_REVOCA_IRREG: fine.
					
							rigaRevoca.setIrregolarita(irregolarita );
							rigaRevoca.setHasIrregolarita(true);
							//rigaRevoca.setIsDeletable(isAbilitatoModifica);
							//rigaRevoca.setIsUpdatable(isAbilitatoModifica);
							setTotaleImportoIrregolarita(rigaTotale, rigaRevoca);
							if(rigaRevoca.getIdRevoca() != null)
									result.add(rigaRevoca);
						}
					}

					 
				}
				
				// aggiungo per ultima la riga del totale
				LOG.info("\n\n ############ trovati disimpegni: "+result.size());
				if(!result.isEmpty())
					result.add(rigaTotale);
			}

			//logIrregolarita(modalitaAgev);
			logRigaRegola(result);
			LOG.debug(prf + " END");
			return Response.ok().entity(result).build();
		} catch(Exception e) {
			throw e;
		}
	}


	@Override
	public Response getDettaglioRevoca(HttpServletRequest req, Long idProgetto, Long idRevoca)
			throws UtenteException, Exception {
		String prf = "[GestioneDisimpegniServiceImpl::getDettaglioRevoca]";
		LOG.debug(prf + " BEGIN");
		
		try {
			HttpSession session = req.getSession();
			UserInfoSec userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
			Long idUtente = userInfo.getIdUtente();
			String idIride = userInfo.getIdIride();
			
			RevocaDTO filtro = new RevocaDTO();
			filtro.setIdProgetto(idProgetto);
			filtro.setIdRevoca(idRevoca);
			it.csi.pbandi.pbweberog.dto.revoca.ModalitaAgevolazioneDTO[] listaRevoche = gestioneDisimpegniDAO.findRiepilogoRevoche(idUtente, idIride, filtro);		
			// compongo l'oggetto "piatto" di ritorno
			DettaglioRevoca result = new DettaglioRevoca();
			if (listaRevoche != null) {
				// dati modalita' (sola lettura)
				
				
				// 01/02/2021: non so perchè, ma qui prende sempre la prima modalità di agevolazione,
				// ma la revoca potrebbe essere definita su una delle altre.
				// Quindi scorro l'elenco finchè non trovo una modalità con il campo Revoche valorizzato.
				// Codice originale commentato.
				//ModalitaAgevolazioneDTO modalita = listaRevoche[0];
				// Codice nuovo.
				it.csi.pbandi.pbweberog.dto.revoca.ModalitaAgevolazioneDTO modalita = new it.csi.pbandi.pbweberog.dto.revoca.ModalitaAgevolazioneDTO(); 
				for (it.csi.pbandi.pbweberog.dto.revoca.ModalitaAgevolazioneDTO modAgev : listaRevoche)  {
					it.csi.pbandi.pbweberog.dto.revoca.RevocaDTO[] revoche = modAgev.getRevoche();
					if (revoche != null && revoche[0] != null) {
						modalita = modAgev;
					}
				}
				
				result.setModalitaAgevolazione(modalita.getDescModalitaAgevolazione());
				result.setImportoAgevolato(modalita.getQuotaImportoAgevolato() == null ? 0D : modalita.getQuotaImportoAgevolato());
				result.setImportoErogato(modalita.getImportoErogazioni() == null ? 0D : modalita.getImportoErogazioni());
				result.setImportoRevocato(modalita.getTotaleImportoRevocato() == null ? 0D : modalita.getTotaleImportoRevocato());
				result.setImportoRecuperato(modalita.getTotaleImportoRecuperato() == null ? 0D : modalita.getTotaleImportoRecuperato());
				
				// dati revoca (in modifica)
				it.csi.pbandi.pbweberog.dto.revoca.RevocaDTO[] revoche = modalita.getRevoche();
				if (revoche != null && revoche[0] != null) {
					it.csi.pbandi.pbweberog.dto.revoca.RevocaDTO revocaSelezionata = revoche[0];
					//logger.shallowDump(revocaSelezionata, "info");
					result.setIdRevoca(revocaSelezionata.getIdRevoca());
					result.setIdMotivoRevoca(revocaSelezionata.getIdMotivoRevoca());
					result.setIdAnnoContabile(NumberUtil.getStringValue(revocaSelezionata.getIdPeriodo()));
					result.setCodiceCausaleDisimpegno(revocaSelezionata.getCodCausaleDisimpegno());
					result.setDataRevoca(DateUtil.formatDate(revocaSelezionata.getDtRevoca()));
					result.setEstremiDeterminaRevoca(revocaSelezionata.getEstremi());
					result.setNoteRevoca(revocaSelezionata.getNote());
					result.setImportoRevoca(revocaSelezionata.getImporto());
					//M.R.//////////
					result.setOrdineRecupero(revocaSelezionata.getFlagOrdineRecupero());
					result.setIdModalitaRecupero(revocaSelezionata.getIdMancatoREcupero());
					////////////////
				}
			}
			LOG.debug(prf + " END");
			return Response.ok().entity(result).build();
		} catch(Exception e) {
			throw e;
		}
	}
	
	
	@Override
	public Response getRevocaConIrregolarita(HttpServletRequest req, Long idRevoca) throws Exception {
		
		String prf = "[GestioneDisimpegniServiceImpl::getRevocaConIrregolarita]";
		LOG.debug(prf + " BEGIN");
		
		
		EsitoOperazioni esito = new EsitoOperazioni();
		try {
			HttpSession session = req.getSession();
			UserInfoSec userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
			Long idUtente = userInfo.getIdUtente();
			String idIride = userInfo.getIdIride();
			boolean revocaConIrregolarita = false;
			
			revocaConIrregolarita = gestioneDisimpegniDAO.revocaConIrregolarita(idUtente, idIride, idRevoca);
			if(revocaConIrregolarita) {
				esito.setEsito(true);
				esito.setMsg("Non e' possibile modificare l'importo della revoca perche' e' associato ad una irregolarita'.");
			}else {
				esito.setEsito(false);
				esito.setMsg(null);
			}
			
		}catch (Exception e) {
			throw e;
		}
		
		LOG.debug(prf + " END");
		
		return Response.ok().entity(esito).build();
		
	}


	@Override
	public Response modificaDisimpegno(HttpServletRequest req, RequestModificaDisimpegno modificaRequest) throws UtenteException, Exception {
		String prf = "[GestioneDisimpegniServiceImpl::modificaDisimpegno]";
		LOG.debug(prf + " BEGIN");
		try {
			HttpSession session = req.getSession();
			UserInfoSec userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
			Long idUtente = userInfo.getIdUtente();
			String idIride = userInfo.getIdIride();
			DettaglioRevoca dettaglio = modificaRequest.getDettaglioRevoca();
			Long idProgetto = modificaRequest.getIdProgetto();
			
			EsitoSalvaRevocaDTO esito = new EsitoSalvaRevocaDTO();
			
			//VALIDATE
			if (dettaglio.getIdMotivoRevoca() == null && dettaglio.getCodiceCausaleDisimpegno().equals("REV")) 
				  return inviaErroreBadRequest("parametro mancato: ? {dettaglioRevoca.idMotivoRevoca}");	
			if (!DateUtil.isValidDate(dettaglio.getDataRevoca()))
				  return inviaErroreBadRequest("parametro mancato: ? {dettaglioRevoca.dataRevoca}");
			if (!DateUtil.isValidDate(dettaglio.getDataRevoca())) {
				  return inviaErroreBadRequest("parametro mancato: ? {dettaglioRevoca.dataRevoca}");
			} else {
				if (DateUtil.parseDate(dettaglio.getDataRevoca()).after(DateUtil.getDataOdierna())) {
					return inviaErroreBadRequest("La data della revoca non può essere successiva alla data odierna.");
				}
			}
			if (dettaglio.getEstremiDeterminaRevoca() != null && dettaglio.getEstremiDeterminaRevoca().length() > 250)
				return inviaErroreBadRequest("Il campo Estremi determina non può contenere più di 255 caratteri.");
			if (dettaglio.getNoteRevoca() != null && dettaglio.getNoteRevoca().length() > Constants.MAX_LENGHT_CAMPO_NOTE_4000)
				return inviaErroreBadRequest("Il campo note non pu\\u00F2 contenere pi\\u00F9 di 4000 caratteri.");
			if (dettaglio.getImportoRevoca() == null || dettaglio.getImportoRevoca().doubleValue()<0)
				return inviaErroreBadRequest("parametro mancato: ? {dettaglioRevoca.importoRevoca}");
			
			if (dettaglio.getImportoRevoca() != null && dettaglio.getImportoRevoca().doubleValue()<0) {
				return inviaErroreBadRequest("Valore negativo non ammesso. - {dettaglioRevoca.importoRevoca}");
			} else {
			
				Double importoRecuperato=dettaglio.getImportoRecuperato();
				if(importoRecuperato==null) importoRecuperato=0d;
				
				if(dettaglio.getImportoRevoca() != null && dettaglio.getImportoRevoca().doubleValue()<importoRecuperato){
					return inviaErroreBadRequest("Per le modalità di agevolazione, il totale recuperato è superiore al totale revocato.");
				}
			}
			
			//SALVA
			it.csi.pbandi.pbweberog.dto.revoca.ModalitaAgevolazioneDTO modalitaAgevolazione = new it.csi.pbandi.pbweberog.dto.revoca.ModalitaAgevolazioneDTO();
			modalitaAgevolazione.setIdProgetto(idProgetto);
			it.csi.pbandi.pbweberog.dto.revoca.RevocaDTO revoca = new it.csi.pbandi.pbweberog.dto.revoca.RevocaDTO();
			revoca.setDtRevoca(DateUtil.getDate(dettaglio.getDataRevoca()));
			revoca.setEstremi(dettaglio.getEstremiDeterminaRevoca());

			revoca.setCodCausaleDisimpegno(dettaglio.getCodiceCausaleDisimpegno());
			revoca.setIdRevoca(dettaglio.getIdRevoca());
			if (dettaglio.getCodiceCausaleDisimpegno() != null
					&& !dettaglio
							.getCodiceCausaleDisimpegno()
							.equalsIgnoreCase(
									Constants.COD_TIPO_CAUSALE_DISIMPEGNO_REVOCA)) {
				revoca.setIdMotivoRevoca(null);
				//M.R.////////////
				revoca.setFlagOrdineRecupero(null);
				revoca.setIdMancatoREcupero(null);
				/////////////////
			} else {
				revoca.setIdMotivoRevoca(dettaglio.getIdMotivoRevoca());
				//M.R.////////////
				revoca.setFlagOrdineRecupero(dettaglio.getOrdineRecupero());
				revoca.setIdMancatoREcupero(dettaglio
						.getIdModalitaRecupero());
				/////////////////
			}

			revoca.setIdPeriodo(NumberUtil.toNullableLong(dettaglio
					.getIdAnnoContabile()));
			revoca.setImporto(dettaglio.getImportoRevoca());
			revoca.setNote(dettaglio.getNoteRevoca());
			it.csi.pbandi.pbweberog.dto.revoca.RevocaDTO[] revoche = new it.csi.pbandi.pbweberog.dto.revoca.RevocaDTO[]{revoca};
			modalitaAgevolazione.setRevoche(revoche);

			Double importoErogazioni = dettaglio.getImportoErogato();
			LOG.info("importoErogazioni: " + importoErogazioni);
			LOG.info("dettaglio revoca: " + dettaglio);
			if ((importoErogazioni == null || importoErogazioni == 0d)
					&& dettaglio
							.getCodiceCausaleDisimpegno()
							.equalsIgnoreCase(
									Constants.COD_TIPO_CAUSALE_DISIMPEGNO_REVOCA)) {
				esito.setEsito(false);
				MessaggioDTO error = new MessaggioDTO();
				error.setId(dettaglio.getIdRevoca());
				error.setMsgKey(ErrorMessages.ERROR_DISIMPEGNO_IMPORTO_REVOCA_NEGATIVO);
				esito.setMsgs(new MessaggioDTO[] { error });
				return Response.ok().entity(esito).build();
			} else {
				esito = gestioneDisimpegniDAO.modificaDisimpegno(idUtente, idIride, modalitaAgevolazione);
			}
			
			
			LOG.debug(prf + " END");
			return Response.ok().entity(esito).build();
		} catch(Exception e) {
			throw e;
		}
	}

	
	private void logRigaRegola(ArrayList<RigaModificaRevocaItem> lista) {
		String prf = "[GestioneDisimpegniServiceImpl::logRigaRegola]";
		LOG.debug(prf + " START");
		String s = "";
		if (lista != null) {
			for (RigaModificaRevocaItem riga : lista) {
				s = s + "\nRigaModificaRevocaItem: idRevoca = "+riga.getIdRevoca()+"; CausaleDisimpegno = "+riga.getCausaleDisimpegno()+"; importoErogato = "+riga.getImportoErogato();
				if (riga.getIrregolarita() != null) {
					for (RevocaIrregolarita i : riga.getIrregolarita()) {						
						s = s + "\n     RevocaIrregolarita: idIrregolarita = "+i.getIdIrregolarita()+"; tipoIrregolarita = "+i.getTipoIrregolarita()+"; quota = "+i.getQuotaIrreg()+"; DtFineValidita() = "+i.getDtFineValidita();
						if (i.getDettagliRevocaIrregolarita() != null) {
							for (DettaglioRevocaIrregolarita d : i.getDettagliRevocaIrregolarita()) {																
								s = s + "\n          Dettaglio: IdDettRevocaIrreg = "+d.getIdDettRevocaIrreg()+"; idClassRevocaIrreg = "+d.getIdClassRevocaIrreg()+"; importo = ; "+d.getImporto();
								for (DsDettaglioRevocaIrregolarita ds : d.getDsDettagliRevocaIrregolarita()) {
									s = s + "\n               DS associate: IdDichiarazioneSpesa = "+ds.getIdDichiarazioneSpesa()+"; importoIrregolareDs = "+ds.getImportoIrregolareDs();
								}
							}
						}  
					}
				} 
			}
		}
		LOG.info(s);
		LOG.debug(prf + " END");
		
	}

	private void setTotaleImportoIrregolarita(RigaModificaRevocaItem rigaTotale, RigaModificaRevocaItem rigaRevoca) {
		String prf = "[GestioneDisimpegniServiceImpl::setTotaleImportoIrregolarita]";
		LOG.debug(prf + " START");
		Double importoIrregolarita = rigaTotale.getImportoIrregolarita();
		if(importoIrregolarita==null){
			 importoIrregolarita=0d;
		}
		if(rigaRevoca.getImportoIrregolarita()!=null){
			rigaTotale.setImportoIrregolarita(NumberUtil.sum(importoIrregolarita, rigaRevoca.getImportoIrregolarita()));
		}
		Double importoRevocato= rigaTotale.getImportoRevocato();
		if(importoRevocato==null){
			importoRevocato=0d;
		}
		if(rigaRevoca.getImportoRevocato()!=null){
			rigaTotale.setImportoRevocato(NumberUtil.sum(importoRevocato, rigaRevoca.getImportoRevocato()));
		}
		rigaTotale.setImportoRevocato(NumberUtil.sum(importoRevocato, rigaRevoca.getImportoRevocato()));
		LOG.debug(prf + " END");
	}

	@Override
	public Response getMotivazioni(HttpServletRequest req, Long idProgetto) throws UtenteException, Exception {
		String prf = "[GestioneDisimpegniServiceImpl::getMotivazioni]";
		LOG.debug(prf + " START");
		HttpSession session = req.getSession();
		UserInfoSec userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
		Long idUtente = userInfo.getIdUtente();
		String idIride = userInfo.getIdIride();
		
		Decodifica[] motivazioni = gestioneDatiDiDominioBusiness.findDecodifiche(idUtente, idIride,
				GestioneDatiDiDominioSrv.MOTIVO_REVOCA);
		
		ArrayList<CodiceDescrizione> comboMotivazioni = new ArrayList<CodiceDescrizione>();

		Long processo = neofluxBusinessImpl.getProcesso(idUtente, idIride, idProgetto);

		if (motivazioni != null) {
			for (Decodifica motivazione : motivazioni) {
				CodiceDescrizione motivo = new CodiceDescrizione();

				if(processo !=null && ( processo.compareTo(Constants.ID_PROCESSO_PROGRAMMAZIONE_2014_20) != 0)){
					if(DateUtil.before(motivazione.getDataInizioValidita(), DateUtil.parseDate("01/01/2014"))){
						motivo.setCodice(NumberUtil.getStringValue(motivazione.getId()));
						motivo.setDescrizione(motivazione.getDescrizione());
						comboMotivazioni.add(motivo);
					}
				}
				else{
					if(!DateUtil.before(motivazione.getDataInizioValidita(), DateUtil.parseDate("01/01/2014"))){
						motivo
						.setCodice(NumberUtil.getStringValue(motivazione
								.getId()));
						motivo.setDescrizione(motivazione.getDescrizione());
						comboMotivazioni.add(motivo);
					}
				}

			}
		}
		LOG.debug(prf + " END");
		return Response.ok().entity(comboMotivazioni).build();
	}

	@Override
	public Response getCausaleDisimpegno(HttpServletRequest req) throws UtenteException, Exception {
		String prf = "[GestioneDisimpegniServiceImpl::getCausaleDisimpegno]";
		LOG.debug(prf + " START");
		HttpSession session = req.getSession();
		UserInfoSec userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
		Long idUtente = userInfo.getIdUtente();
		String idIride = userInfo.getIdIride();
		
		Decodifica[] causaliDisimpegno = gestioneDatiDiDominioBusiness
				.findDecodifiche(idUtente, idIride, GestioneDatiDiDominioSrv.CAUSALE_DISIMPEGNO);

		ArrayList<CodiceDescrizione> combo = new ArrayList<CodiceDescrizione>();
		if (causaliDisimpegno != null) {
			for (Decodifica decodifica : causaliDisimpegno) {
				CodiceDescrizione causale = new CodiceDescrizione();
				causale.setCodice(decodifica.getDescrizioneBreve());
				causale.setDescrizione(decodifica.getDescrizione());
				combo.add(causale);
			}
		}
		
		LOG.debug(prf + " END");
		return Response.ok().entity(combo).build();
	}

	@Override
	public Response getAnnoContabile(HttpServletRequest req) throws UtenteException, Exception {
		String prf = "[GestioneDisimpegniServiceImpl::getAnnoContabile]";
		LOG.debug(prf + " START");
		HttpSession session = req.getSession();
		UserInfoSec userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
		Long idUtente = userInfo.getIdUtente();
		String idIride = userInfo.getIdIride();
		
	
		ArrayList<CodiceDescrizione> periodi = gestioneDisimpegniDAO.findPeriodi(idUtente, idIride);
		LOG.debug(prf + " END");
		return Response.ok().entity(periodi).build();
	}

	@Override
	public Response getModalitaRecupero(HttpServletRequest req) throws UtenteException, Exception {
		String prf = "[GestioneDisimpegniServiceImpl::getModalitaRecupero]";
		LOG.debug(prf + " START");
		
		HttpSession session = req.getSession();
		UserInfoSec userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
		Long idUtente = userInfo.getIdUtente();
		String idIride = userInfo.getIdIride();
		
		
		MancatoRecuperoDTO[] recuperi = gestioneDisimpegniDAO.findModalitaRecupero(idUtente, idIride);
		 
		Map <String,String>mapProps=new HashMap<String,String>();
		mapProps.put("idMancatoRecupero", "codice");
		mapProps.put("descMancatoRecupero", "descrizione");
		
		ArrayList<CodiceDescrizione> result = beanUtil.transformToArrayList(recuperi, CodiceDescrizione.class, mapProps);
		LOG.debug(prf + " END");
		return Response.ok().entity(result).build();
	}

	@Override
	public Response getImportoValidatoTotale(HttpServletRequest req, Long idProgetto)
			throws UtenteException, Exception {
		String prf = "[GestioneDisimpegniServiceImpl::getImportoValidatoTotale]";
		LOG.debug(prf + " START");
		
		HttpSession session = req.getSession();
		UserInfoSec userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
		Long idUtente = userInfo.getIdUtente();
		String idIride = userInfo.getIdIride();
		Long idSoggetto = userInfo.getIdSoggetto();
		
		QuotaParteVoceDiSpesaDTO filtro = new QuotaParteVoceDiSpesaDTO();
		filtro.setIdProgetto(idProgetto);
		
		QuotaParteVoceDiSpesaDTO[] quoteParte = gestioneDisimpegniDAO.findQuotePartePerRevoca(idSoggetto, idIride, filtro);
		
		BigDecimal totaleSpesaValidato = new BigDecimal(0);
		for(QuotaParteVoceDiSpesaDTO quotaParteVoceDiSpesaDTO: quoteParte) {
			totaleSpesaValidato = totaleSpesaValidato.add( new BigDecimal(quotaParteVoceDiSpesaDTO.getImportoValidato() ) );
		}
		
		totaleSpesaValidato = totaleSpesaValidato.setScale(2, RoundingMode.CEILING);
		
		LOG.debug(prf + " END");
		return Response.ok().entity(totaleSpesaValidato).build();
	}

	@Override
	public Response getRevoche(HttpServletRequest req, Long idProgetto) throws UtenteException, Exception {
		String prf = "[GestioneDisimpegniServiceImpl::getRevoche]";
		LOG.debug(prf + " START");
		HttpSession session = req.getSession();
		UserInfoSec userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
		Long idUtente = userInfo.getIdUtente();
		String idIride = userInfo.getIdIride();
		
		RevocaModalitaAgevolazioneDTO filtro = new RevocaModalitaAgevolazioneDTO();
		filtro.setIdProgetto(idProgetto);
		RevocaModalitaAgevolazioneDTO[] revoche = gestioneDisimpegniDAO.findRevoche(idUtente, idIride, filtro);
		ArrayList<RigaRevocaItem> result = new ArrayList<RigaRevocaItem>();
		RigaRevocaItem rigaTotale = new RigaRevocaItem();
		rigaTotale.setLabel("Totale");
		rigaTotale.setIsRigaModalita(Boolean.FALSE);
		rigaTotale.setIsRigaTotale(Boolean.TRUE);
		rigaTotale.setHasCausaliErogazione(Boolean.FALSE);
		rigaTotale.setImportoAgevolato(0D);
		rigaTotale.setImportoErogato(0D);
		rigaTotale.setImportoRecuperato(0D);
		rigaTotale.setImportoRevocato(0D);
		if (revoche != null) {
			for (RevocaModalitaAgevolazioneDTO revoca : revoche) {
				
				RigaRevocaItem rigaModalita = new RigaRevocaItem();
				rigaModalita.setIdModalitaAgevolazione(NumberUtil.getStringValue(revoca.getIdModalitaAgevolazione()));
				rigaModalita.setLabel(revoca.getDescModalitaAgevolazione());
				rigaModalita.setImportoAgevolato(revoca.getQuotaImportoAgevolato() == null ? 0D : revoca.getQuotaImportoAgevolato());
				rigaModalita.setImportoErogato(revoca.getImportoErogazioni() == null ? 0D : revoca.getImportoErogazioni());
				rigaModalita.setImportoRevocato(revoca.getTotaleImportoRevocato() == null ? 0D : revoca.getTotaleImportoRevocato());
				rigaModalita.setImportoRecuperato(revoca.getTotaleImportoRecuperato() == null ? 0D : revoca.getTotaleImportoRecuperato());
				rigaModalita.setIsRigaModalita(Boolean.TRUE);
				rigaModalita.setIsRigaTotale(Boolean.FALSE);							
				result.add(rigaModalita);
				
				/*
				 * Sommo gli importi per la riga totale
				 */
				rigaTotale.setImportoAgevolato(NumberUtil.sum(NumberUtil.getDoubleValue(rigaModalita.getImportoAgevolato()), rigaTotale.getImportoAgevolato()));
				rigaTotale.setImportoErogato(NumberUtil.sum(NumberUtil.getDoubleValue(rigaModalita.getImportoErogato()),rigaTotale.getImportoErogato()));
				rigaTotale.setImportoRecuperato(NumberUtil.sum(NumberUtil.getDoubleValue(rigaModalita.getImportoRecuperato()),rigaTotale.getImportoRecuperato()));
				rigaTotale.setImportoRevocato(NumberUtil.sum(NumberUtil.getDoubleValue(rigaModalita.getImportoRevocato()),rigaTotale.getImportoRevocato()));
				
				
				
				if (revoca.getCausaliErogazioni() != null && revoca.getCausaliErogazioni().length > 0) {
					rigaModalita.setHasCausaliErogazione(Boolean.TRUE);
					for (it.csi.pbandi.pbservizit.pbandisrv.dto.revoca.CausaleErogazioneDTO causale : revoca.getCausaliErogazioni()) {
						RigaRevocaItem rigaCausale = new RigaRevocaItem();
						rigaCausale.setIdModalitaAgevolazione(NumberUtil.getStringValue(revoca.getIdModalitaAgevolazione()));
						rigaCausale.setLabel(causale.getDescCausale());
						rigaCausale.setImportoErogato(causale.getImportoErogazione() == null ? 0D : causale.getImportoErogazione());
						rigaCausale.setIsRigaModalita(Boolean.FALSE);
						rigaCausale.setIsRigaTotale(Boolean.FALSE);
						rigaCausale.setHasCausaliErogazione(Boolean.FALSE);
						rigaCausale.setData(DateUtil.formatDate(causale.getDtContabile()));
						result.add(rigaCausale);
					}
				} else {
					rigaModalita.setHasCausaliErogazione(Boolean.FALSE);
				}
			}
			result.add(rigaTotale);
		}
		LOG.debug(prf + " END");
		return Response.ok().entity(result).build();
	}

	@Override
	public Response salvaDisimpegni(HttpServletRequest req, RequestSalvaRevoche requestSalva)
			throws UtenteException, Exception {
		String prf = "[GestioneDisimpegniServiceImpl::salvaRevoche]";
		LOG.debug(prf + " START");
		HttpSession session = req.getSession();
		UserInfoSec userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
		Long idUtente = userInfo.getIdUtente();
		String idIride = userInfo.getIdIride();
		
		Long idProgetto = requestSalva.getIdProgetto();
		Long idMotivoRevoca = requestSalva.getIdMotivoRevoca();
		String dtRevoca = requestSalva.getDtRevoca();
		String note = requestSalva.getNote();
		String estremi = requestSalva.getEstremi();
		String codCausale = requestSalva.getCodCausaleDisimpegno();
		String ordineRecupero = requestSalva.getOrdineRecupero();
		Long idModalitaRecupero = requestSalva.getIdModalitaRecupero();
		Long idAnnoContabile = requestSalva.getIdAnnoContabile();
		List<RigaModificaRevocaItem> disimpegni = requestSalva.getDisimpegni();
		
		List<RigaRevocaItem> revoche = requestSalva.getRevoche();
		// PASSO 1: VALIDATE
		LOG.info(prf  + " PASSO 1: VALIDATE");
		if (idMotivoRevoca == null && codCausale.equals("REV")) {
			return inviaErroreBadRequest("parametro mancato nel body: ?{RequestSalvaRevoche.idMotivoRevoca}");
		}		
		/* Controllo che sia stata inserita una data valida */
		if (!DateUtil.isValidDate(dtRevoca)) {
			return inviaErroreBadRequest("parametro mancato nel body: ?{RequestSalvaRevoche.dtRevoca}");
			
		} else {
			if (DateUtil.parseDate(dtRevoca).after(DateUtil.getDataOdierna())) {
				return inviaErroreBadRequest(ErrorMessages.DATA_REVOCA);
			}
		}
		
		/** Verifico la lunghezza del campo nota 4000 */
		if (note != null && note.length() > Constants.MAX_LENGHT_CAMPO_NOTE_4000) {
			return inviaErroreBadRequest(ErrorMessages.MSG_REVOCA_LUNGHEZZA_NOTE);
		}
		
		/** Controllo la lunghezza del campo estremi 250 */
		if (estremi != null && estremi.length() > 250) {
			return inviaErroreBadRequest(ErrorMessages.MSG_REVOCA_LUNGHEZZA_NOTE);
		}
		// PASSO 2: check dispimpegno		
		LOG.info(prf  + " PASSO 2: CHECK DISIMPEGNI");
		EsitoSalvaRevocaDTO result = new EsitoSalvaRevocaDTO();
		result.setEsito(true);
		List<RevocaModalitaAgevolazioneDTO> revocheDTO = popolaRevoche(revoche, idProgetto, idMotivoRevoca, note, estremi, DateUtil.parseDate(dtRevoca), codCausale ,null, ordineRecupero, idModalitaRecupero);
		if (!revocheDTO.isEmpty()) {
				if(ObjectUtil.isEmpty(codCausale) || codCausale.equalsIgnoreCase(Constants.COD_TIPO_CAUSALE_DISIMPEGNO_REVOCA)){
					LOG.info(prf + " REVOCHE\n");
					result = this.checkRevocheNew(beanUtil.transform(revocheDTO, RevocaModalitaAgevolazioneDTO.class), disimpegni);
				} else{
					LOG.info(prf + " DISIMPEGNI\n");
					result = gestioneDisimpegniDAO.checkDisimpegni(idUtente, idIride, beanUtil.transform(revocheDTO, RevocaModalitaAgevolazioneDTO.class));
				}							
		} else {
				MessaggioDTO msg = new MessaggioDTO();
				msg.setMsgKey(MessageConstants.MSG_DISIMPEGNO_NESSUNA_REVOCA_MODIFICATA);
				result.setMsgs(new MessaggioDTO[]{msg});
				result.setEsito(false);
		}		
		if (result.getEsito() != null) {
			if (!result.getEsito()) {
				return Response.ok().entity(result).build();
			}
		}
		// PASSO 3: salva disimpegni
		LOG.info(prf  + " PASSO 3: SALVA DISIMPEGNI");
		result = gestioneDisimpegniDAO.salvaDisimpegni(idUtente, idIride,  beanUtil.transform(revocheDTO, RevocaModalitaAgevolazioneDTO.class));
		
		LOG.debug(prf + " END");
		return Response.ok().entity(result).build();
	}



	
	private EsitoSalvaRevocaDTO checkRevocheNew(RevocaModalitaAgevolazioneDTO[] revoche,
			List<RigaModificaRevocaItem> disimpegni) {
		String prf = "[GestioneDisimpegniServiceImpl::checkRevocheNew]";
		LOG.debug(prf + " START");

		EsitoSalvaRevocaDTO esito = new EsitoSalvaRevocaDTO();
		esito.setEsito(Boolean.FALSE);
		List<MessaggioDTO> errors = new ArrayList<MessaggioDTO>();
		if (revoche != null) {
			for (RevocaModalitaAgevolazioneDTO dto : revoche) {
				boolean hasError = false;
				/*
				 * Controllo che l'importo della revoca sia maggiore o
				 * uguale a 0
				 */
				if (NumberUtil.compare(dto.getImportoRevoca(), 0D) < 0) {
					hasError = true;
					MessaggioDTO error = new MessaggioDTO();
					error.setId(dto.getIdModalitaAgevolazione());
					error.setMsgKey(ErrorMessages.ERROR_REVOCA_IMPORTO_REVOCA_NEGATIVO);
					errors.add(error);
				}

				
				if (!hasError && (dto.getImportoErogazioni() == null || NumberUtil.compare(dto.getImportoErogazioni(), 0D) == 0)) {
					hasError = true;
					MessaggioDTO error = new MessaggioDTO();
					error.setId(dto.getIdModalitaAgevolazione());
					error.setMsgKey(ErrorMessages.ERROR_REVOCA_IMPORTO_GIA_EROGATO_NON_PRESENTE);
					errors.add(error);
				}

				if (!hasError && dto.getImportoRevoca() != null) {

					/*
					 * Controllo che la somma dei recuperi non sia superiore
					 * al totale degli importi revocati
					 */

					// Totale di tutti i disimpegni.
					Double totaleRevocato = NumberUtil.sum(NumberUtil.getDoubleValue(dto.getTotaleImportoRevocato()), dto.getImportoRevoca());
					
					
					// Totale dei soli disimpegni di tipo revoca.
					Double totaleDisimpegniTipoRevoca = new Double(0);
					for (RigaModificaRevocaItem d : disimpegni){
						// verifico la descrizione poichè l'id non è valorizzato.
						if ("Revoca".equalsIgnoreCase(d.getCausaleDisimpegno())) {
							totaleDisimpegniTipoRevoca = NumberUtil.sum(totaleDisimpegniTipoRevoca, d.getImportoRevocato());
							LOG.info("sommo "+d.getImportoRevocato()+" e ottengo "+totaleDisimpegniTipoRevoca);
						}
					}
					// Aggiungo l'importo del disimpegno di tipo revoca inserito\modificato.
					totaleDisimpegniTipoRevoca = NumberUtil.sum(totaleDisimpegniTipoRevoca, dto.getImportoRevoca());
					LOG.info("checkRevocheNew(): sommo nuovo importo "+dto.getImportoRevoca()+" e ottengo totaleDisimpegniTipoRevoca = "+totaleDisimpegniTipoRevoca);
					
					
					if (NumberUtil.compare(dto.getTotaleImportoRecuperato(), totaleRevocato) > 0) {
						hasError = true;
						MessaggioDTO error = new MessaggioDTO();
						error.setId(dto.getIdModalitaAgevolazione());
						error.setMsgKey(ErrorMessages.ERROR_REVOCA_RECUPERATO_MAGGIORE_REVOCATO);
						errors.add(error);
					}

					/*
					 * Controllo che la somma tra il nuovo importo della
					 * revoca ed il totale gia' revocato sia minore o uguale
					 * al totale delle erogazioni per la modalita'
					 */
					//if (!hasError && NumberUtil.compare(totaleRevocato, dto.getImportoErogazioni()) > 0) {
					if (!hasError && NumberUtil.compare(totaleDisimpegniTipoRevoca, dto.getImportoErogazioni()) > 0) {
						hasError = true;
						MessaggioDTO error = new MessaggioDTO();
						error.setId(dto.getIdModalitaAgevolazione());
						error.setMsgKey(ErrorMessages.ERROR_REVOCA_TOTALE_REVOCATO_SUPERIORE_EROGAZIONI);						
						errors.add(error);

					}
					// nuovo controllo su agevolato: la somma dei disimpegni non deve superare l'importo agevolato.
					LOG.info("checkRevocheNew(): totaleRevocato = "+totaleRevocato+"; getQuotaImportoAgevolato = "+dto.getQuotaImportoAgevolato());
					if (!hasError && NumberUtil.compare(totaleRevocato, dto.getQuotaImportoAgevolato()) > 0) {
						hasError = true;
						MessaggioDTO error = new MessaggioDTO();
						error.setId(dto.getIdModalitaAgevolazione());
						error.setMsgKey(ErrorMessages.ERROR_REVOCA_TOTALE_REVOCATO_SUPERIORE_AGEVOLATO);						                
						errors.add(error);
					}
					
				}
			}
		}
		if (errors.isEmpty()) {
			esito.setEsito(Boolean.TRUE);
			MessaggioDTO msg = new MessaggioDTO();
			msg.setMsgKey(Constants.WARN_CONFERMA_SALVATAGGIO);
			esito.setMsgs(new MessaggioDTO[] { msg });
		} else {
			esito.setMsgs(beanUtil.transform(errors, MessaggioDTO.class));
		}
		LOG.debug(prf + " END");
		return esito;
	}

	private List<RevocaModalitaAgevolazioneDTO> popolaRevoche(List<RigaRevocaItem> revoche, Long idProgetto,
			Long idMotivoRevoca, String note, String estremi, Date dtRevoca, String codCausaleDisimpegno, String idAnnoContabile, 
			String ordineRecupero, Long idModalitaRecupero) {
		String prf = "[GestioneDisimpegniServiceImpl::popolaRevoche]";
		LOG.debug(prf + " START");
		List<RevocaModalitaAgevolazioneDTO> result = new ArrayList<RevocaModalitaAgevolazioneDTO>();
		if (revoche != null) {
			for (RigaRevocaItem item : revoche) {
				if (item.getIsRigaModalita()) {
					RevocaModalitaAgevolazioneDTO dto = new RevocaModalitaAgevolazioneDTO();
				
					dto.setDtRevoca(dtRevoca);
					dto.setEstremi(estremi);
					dto.setIdModalitaAgevolazione(NumberUtil.toNullableLong(item.getIdModalitaAgevolazione()));
					dto.setCodCausaleDisimpegno(codCausaleDisimpegno);
				 	dto.setIdMotivoRevoca(idMotivoRevoca);
				 	dto.setIdPeriodo(NumberUtil.toNullableLong(idAnnoContabile));
				 	//M.R.////////////
				 	dto.setFlagOrdineRecupero(ordineRecupero);
					dto.setIdMancatoRecupero(idModalitaRecupero);
				 	//////////////////
					
					dto.setIdProgetto(idProgetto);
					dto.setImportoErogazioni(item.getImportoErogato());
					dto.setImportoRevoca(item.getImportoRevocaNew());
					dto.setNote(note);
					dto.setQuotaImportoAgevolato(item.getImportoAgevolato());
					dto.setTotaleImportoRecuperato(item.getImportoRecuperato());
					dto.setTotaleImportoRevocato(item.getImportoRevocato());
										
					/*
					 * Inserisco solo le revoche che sono state modificate
					 */
					if (item.getImportoRevocato() != null && item.getImportoRevocaNew() != null) {
						if (NumberUtil.compare(item.getImportoRevocato(),NumberUtil.sum(item.getImportoRevocato(),item.getImportoRevocaNew())) != 0) {
							result.add(dto);
						}
					} else {
						if (item.getImportoRevocaNew() != null)
							result.add(dto);
					}
				}
			}
		}
		LOG.debug(prf + " END");
		return result;
	}


	@Override
	public Response eliminaRevoca(HttpServletRequest req, Long idRevoca, Long idProgetto) throws UtenteException, Exception {
		String prf = "[GestioneDisimpegniServiceImpl::eliminaRevoca]";
		LOG.debug(prf + " BEGIN");
		try {
			HttpSession session = req.getSession();
			UserInfoSec userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
			Long idUtente = userInfo.getIdUtente();
			String idIride = userInfo.getIdIride();
			if(idRevoca == null) return inviaErroreBadRequest("parametro mancato: ?[idRevoca]");
			
			
			EsitoSalvaRevocaDTO esito = gestioneDisimpegniDAO.eliminaRevoca(idUtente, idIride, idRevoca, idProgetto);
			
			LOG.debug(prf + " END");
			return Response.ok().entity(esito).build();
		} catch (Exception e) {
			 EsitoSalvaRevocaDTO result = new EsitoSalvaRevocaDTO();
			 result.setEsito(Boolean.FALSE);
			 MessaggioDTO msg = new MessaggioDTO();
			 msg.setMsgKey(e.getMessage());
			 result.setMsgs(new MessaggioDTO []{msg});
			 return Response.ok().entity(result).build();
		}
		
			
	}

	//ASSOCIAZIONE TRA REVOCA E IRREGOLARELARITA
	
	@Override
	public Response findDsIrregolarita(HttpServletRequest req, Long idProgetto) throws UtenteException, Exception {
		String prf = "[GestioneDisimpegniServiceImpl::findDsIrregolarita]";
		LOG.debug(prf + " BEGIN");
		try {
			HttpSession session = req.getSession();
			UserInfoSec userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
			Long idUtente = userInfo.getIdUtente();
			String idIride = userInfo.getIdIride();			
			
			Long[] lista = gestioneDisimpegniDAO.findDsIrregolarita(idUtente, idIride, idProgetto);
			
			LOG.debug(prf + " END");
			return Response.ok().entity(lista).build();
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public Response findClassRevocaIrreg(HttpServletRequest req, Long idProgetto) throws UtenteException, Exception {
		String prf = "[GestioneDisimpegniServiceImpl::findClassRevocaIrreg]";
		LOG.debug(prf + " BEGIN");
		try {
			HttpSession session = req.getSession();
			UserInfoSec userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
			Long idUtente = userInfo.getIdUtente();
			String idIride = userInfo.getIdIride();			
			Decodifica[] decodifiche = gestioneDatiDiDominioBusiness
					.findDecodifiche(idUtente, idIride, GestioneDatiDiDominioSrv.CLASS_REVOCA_IRREG);
			LOG.debug(prf + " END");
			return Response.ok().entity(decodifiche).build();
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public Response findIrregolarita(HttpServletRequest req,  RequestFindIrregolarita requestFindIrregolarita) throws UtenteException, Exception {
		String prf = "[GestioneDisimpegniServiceImpl::findIrregolarita]";
		LOG.debug(prf + " BEGIN");
		try {
			HttpSession session = req.getSession();
			UserInfoSec userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
			Long idUtente = userInfo.getIdUtente();
			String idIride = userInfo.getIdIride();			
			ArrayList<RigaModificaRevocaItem> revoche = requestFindIrregolarita.getRevoche();
			Long idRevoca = requestFindIrregolarita.getIdRevoca();
			
			ArrayList<RevocaIrregolarita> res = new ArrayList<RevocaIrregolarita>();
			Revoca revoca=new Revoca();
			
			for (RigaModificaRevocaItem rigaModificaRevocaItem : revoche) {

				if(rigaModificaRevocaItem.getIdRevoca()!=null && rigaModificaRevocaItem.getIdRevoca().toString().equals(idRevoca.toString())){
					ArrayList<RevocaIrregolarita> irregolarita = rigaModificaRevocaItem.getIrregolarita();			
					for (RevocaIrregolarita ri : irregolarita) {			
						if (ri.getDtFineValidita() == null && (ri.getIdRevoca() == null || NumberUtil.toLong(rigaModificaRevocaItem.getIdRevoca()) == ri.getIdRevoca().longValue())) {
							res.add(ri);
							LOG.info(prf + "  irregolarita inclusa : IdIrregolarita = "+ri.getIdIrregolarita()+"; IdRevoca = "+ri.getIdRevoca()+"; DtFineValidita = "+ri.getDtFineValidita());
						} else {
							LOG.info(prf + "  irregolarita scartata: IdIrregolarita = "+ri.getIdIrregolarita()+"; IdRevoca = "+ri.getIdRevoca()+"; DtFineValidita = "+ri.getDtFineValidita());
						}
					}
					revoca.setIdRevoca(Long.valueOf(idRevoca));
					if (!StringUtils.isBlank(rigaModificaRevocaItem.getIdMotivoRevoca())) {
						revoca.setIdMotivoRevoca(Long.valueOf(rigaModificaRevocaItem.getIdMotivoRevoca()));
					}
					revoca.setImportoRevocato(rigaModificaRevocaItem.getImportoRevocato());
				   
					
				}
			}
			revoca.setIrregolarita(res);
			LOG.debug(prf + " END");
			return Response.ok().entity(revoca).build();
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public Response salvaIrregolarita(HttpServletRequest req, RequestAssociaIrregolarita requestSalva)
			throws UtenteException, Exception {
		String prf = "[GestioneDisimpegniServiceImpl::salvaIrregolarita]";
		LOG.debug(prf + " BEGIN");
		try {
			HttpSession session = req.getSession();
			UserInfoSec userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
			Long idUtente = userInfo.getIdUtente();
			String idIride = userInfo.getIdIride();			
			
			EsitoSalvaRevocaDTO esito = gestioneDisimpegniDAO.saveIrregolarita(idUtente, idIride, requestSalva);


			LOG.debug(prf + " END");
			return Response.ok().entity(esito).build();
		} catch (Exception e) {
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

	

}
