/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweberog.business.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import it.csi.pbandi.pbservizit.exception.UtenteException;
import it.csi.pbandi.pbservizit.pbandisrv.business.gestionedatididominio.GestioneDatiDiDominioBusinessImpl;
import it.csi.pbandi.pbservizit.pbandisrv.business.neoflux.NeofluxBusinessImpl;
import it.csi.pbandi.pbservizit.pbandisrv.dto.gestionedatididominio.Decodifica;
import it.csi.pbandi.pbservizit.pbandisrv.dto.revoca.CausaleErogazioneDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.revoca.EsitoSalvaRevocaDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.revoca.MessaggioDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.revoca.QuotaParteVoceDiSpesaDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.revoca.RevocaDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.revoca.RevocaModalitaAgevolazioneDTO;
import it.csi.pbandi.pbservizit.pbandisrv.interfacecsi.gestionedatididominio.GestioneDatiDiDominioSrv;
import it.csi.pbandi.pbservizit.pbandiutil.common.ObjectUtil;
import it.csi.pbandi.pbservizit.security.UserInfoSec;
import it.csi.pbandi.pbweberog.business.service.RevocheService;
import it.csi.pbandi.pbweberog.dto.CodiceDescrizione;
import it.csi.pbandi.pbweberog.dto.EsitoOperazioni;
import it.csi.pbandi.pbweberog.dto.disimpegni.RevocaIrregolarita;
import it.csi.pbandi.pbweberog.dto.disimpegni.RigaModificaRevocaItem;
import it.csi.pbandi.pbweberog.dto.revoca.DettaglioRevoca;
import it.csi.pbandi.pbweberog.dto.revoca.ModalitaAgevolazioneDTO;
import it.csi.pbandi.pbweberog.dto.revoca.RigaRevocaItem;
import it.csi.pbandi.pbweberog.integration.dao.RevocaDAO;
import it.csi.pbandi.pbweberog.integration.request.RequestModificaRevoca;
import it.csi.pbandi.pbweberog.integration.request.RequestSalvaRevoche;
import it.csi.pbandi.pbweberog.util.BeanUtil;
import it.csi.pbandi.pbweberog.util.Constants;
import it.csi.pbandi.pbweberog.util.DateUtil;
import it.csi.pbandi.pbweberog.util.ErrorMessages;
import it.csi.pbandi.pbweberog.util.MessageConstants;
import it.csi.pbandi.pbweberog.util.NumberUtil;

@Service
public class RevocheServiceImpl  implements RevocheService {
	@Autowired
	protected BeanUtil beanUtil;
	private Logger LOG = Logger.getLogger(Constants.COMPONENT_NAME);
	
	@Autowired
	RevocaDAO revocaDAO;
	
	@Autowired
	GestioneDatiDiDominioBusinessImpl datiDiDominioBusinessImpl;
	
	@Autowired
	NeofluxBusinessImpl neofluxSrv;
	
	@Override
	public Response findAndFilterMotivazioni(HttpServletRequest req, Long idProgetto)
			throws UtenteException, Exception {
		String prf = "[RevocheServiceImpl::findAndFilterMotivazioni]";
		LOG.info(prf + " BEGIN");
		try {
			HttpSession session = req.getSession();
			UserInfoSec userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
			Long idUtente = userInfo.getIdUtente();
			String idIride = userInfo.getIdIride();
			
			Decodifica[] motivazioni = datiDiDominioBusinessImpl.findDecodifiche(idUtente, idIride, GestioneDatiDiDominioSrv.MOTIVO_REVOCA);
			ArrayList<CodiceDescrizione> comboMotivazioni = new ArrayList<CodiceDescrizione>();

			Long processo = neofluxSrv.getProcesso(idUtente, idIride, idProgetto);

			if (motivazioni != null) {
				HashMap<String, String> info = revocaDAO.getTitoloBandoENumeroDomandaValidazione(idProgetto, "BR69");
				String titoloBando = info != null && !info.isEmpty() ? info.get("titoloBando") : "";
				Long codRevocaBuonoDomRes[] = { 901L, 902L, 903L, 904L, 905L };
				for (Decodifica motivazione : motivazioni) {
					CodiceDescrizione motivo = new CodiceDescrizione();
					
					// Nel caso di buono dom/res carico solo alcune delle motivazioni, indicate sopra
					if (Constants.BUONO_DOMICILIARITA.equals(titoloBando) || Constants.BUONO_RESIDENZIALITA.equals(titoloBando)) {
						if (Arrays.asList(codRevocaBuonoDomRes).contains(motivazione.getId())) {
							motivo.setCodice(NumberUtil.getStringValue(motivazione.getId()));
							motivo.setDescrizione(motivazione.getDescrizione());
							comboMotivazioni.add(motivo);
						}
					} else {
						if(processo !=null && ( processo.compareTo(Constants.ID_PROCESSO_PROGRAMMAZIONE_2014_20) != 0)){
							if(DateUtil.before(motivazione.getDataInizioValidita(), DateUtil.parseDate("01/01/2014"))){
								motivo
								.setCodice(NumberUtil.getStringValue(motivazione
										.getId()));
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
			}
			LOG.info(prf + " END");
			return Response.ok().entity(comboMotivazioni).build();
		} catch(Exception e) {
			throw e;
		}
	}
	@Override
	public Response getImportoValidatoTotale(HttpServletRequest req, Long idProgetto)
			throws UtenteException, Exception {
		String prf = "[RevocheServiceImpl::getImportoValidatoTotale]";
		LOG.info(prf + " BEGIN");
		try {
			HttpSession session = req.getSession();
			UserInfoSec userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
			Long idUtente = userInfo.getIdUtente();
			String idIride = userInfo.getIdIride();
			QuotaParteVoceDiSpesaDTO filtro = new QuotaParteVoceDiSpesaDTO();
			filtro.setIdProgetto(idProgetto);
			QuotaParteVoceDiSpesaDTO[] quoteParte = revocaDAO.findQuotePartePerRevoca(idUtente, idIride, filtro);
			BigDecimal result = new BigDecimal(0D);
			if (quoteParte != null) {
				for (QuotaParteVoceDiSpesaDTO dto : quoteParte) {
					result = NumberUtil.sum(result, NumberUtil.createScaledBigDecimal(dto.getImportoValidato()));
				}
			}
			
			LOG.info(prf + " END");
			return Response.ok().entity(NumberUtil.toDouble(result)).build();
		} catch(Exception e) {
			throw e;
		}
	}
	@Override
	public Response getRevoche(HttpServletRequest req,  Long idProgetto)
			throws UtenteException, Exception {
		String prf = "[RevocheServiceImpl::getRevoche]";
		LOG.info(prf + " BEGIN");
		try {
			HttpSession session = req.getSession();
			UserInfoSec userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
			Long idUtente = userInfo.getIdUtente();
			String idIride = userInfo.getIdIride();
			
			RevocaModalitaAgevolazioneDTO filtro = new RevocaModalitaAgevolazioneDTO();
			filtro.setIdProgetto(idProgetto);
			RevocaModalitaAgevolazioneDTO[] revoche = revocaDAO.findRevoche(idUtente, idIride, filtro);
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
					
					/* Sommo gli importi per la riga totale */
					rigaTotale.setImportoAgevolato(NumberUtil.sum(NumberUtil.getDoubleValue(rigaModalita.getImportoAgevolato()), rigaTotale.getImportoAgevolato()));
					rigaTotale.setImportoErogato(NumberUtil.sum(NumberUtil.getDoubleValue(rigaModalita.getImportoErogato()),rigaTotale.getImportoErogato()));
					rigaTotale.setImportoRecuperato(NumberUtil.sum(NumberUtil.getDoubleValue(rigaModalita.getImportoRecuperato()),rigaTotale.getImportoRecuperato()));
					rigaTotale.setImportoRevocato(NumberUtil.sum(NumberUtil.getDoubleValue(rigaModalita.getImportoRevocato()),rigaTotale.getImportoRevocato()));
															
					if (revoca.getCausaliErogazioni() != null && revoca.getCausaliErogazioni().length > 0) {
						rigaModalita.setHasCausaliErogazione(Boolean.TRUE);
						for (CausaleErogazioneDTO causale : revoca.getCausaliErogazioni()) {
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
			LOG.info(prf + " END");
			return Response.ok().entity(result).build();
		} catch(Exception e) {
			throw e;
		}
	}
	
	@Override
	public Response checkRevoche(HttpServletRequest req, RequestSalvaRevoche salvaRequest)
			throws UtenteException, Exception {
		String prf = "[RevocheServiceImpl::checkRevoche]";
		LOG.info(prf + " BEGIN");
		try {
			HttpSession session = req.getSession();
			UserInfoSec userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
			Long idUtente = userInfo.getIdUtente();
			String idIride = userInfo.getIdIride();
			
			Long idProgetto = salvaRequest.getIdProgetto();
			Long idMotivo = salvaRequest.getIdMotivoRevoca();
			String note = salvaRequest.getNote();
			String estremi = salvaRequest.getEstremi();
			List<RigaRevocaItem> revoche = salvaRequest.getRevoche();
			String dtRevoca = salvaRequest.getDtRevoca();
			String dtDecorRevoca = salvaRequest.getDtDecorRevoca();
			String ordineRecupero =  salvaRequest.getOrdineRecupero();
			String codCausale = salvaRequest.getCodCausaleDisimpegno();
			Long idModalita = salvaRequest.getIdModalitaRecupero();
			// VALIDATE
			HashMap<String, String> info = revocaDAO.getTitoloBandoENumeroDomandaValidazione(idProgetto, "BR69");
			String titoloBando = info != null && !info.isEmpty() ? info.get("titoloBando") : "";
			
			if(idMotivo == null) return inviaErroreBadRequest("parametro mancato nel body: ?{RequestSalvaRecuperi.idMotivo}");
			if(idProgetto == null) return inviaErroreBadRequest("parametro mancato nel body: ?{RequestSalvaRecuperi.idProgetto}");
			if(dtRevoca == null) return inviaErroreBadRequest("parametro mancato nel body: ?{RequestSalvaRecuperi.dtRevoca}");
			if (!DateUtil.isValidDate(dtRevoca)) return inviaErroreBadRequest("formatto data non valido nel body: ?{RequestSalvaRecuperi.dtRevoca}");
			if ( DateUtil.parseDate(dtRevoca).after(DateUtil.getDataOdierna()) ) return inviaErroreBadRequest(ErrorMessages.WRN_REVOCA_DATA_RECUPERO);
			if(Constants.BUONO_DOMICILIARITA.equals(titoloBando) || Constants.BUONO_RESIDENZIALITA.equals(titoloBando)) {
				if(dtDecorRevoca == null) return inviaErroreBadRequest("parametro mancato nel body: ?{RequestSalvaRecuperi.dtDecorRevoca}");
				if (!DateUtil.isValidDate(dtDecorRevoca)) return inviaErroreBadRequest("formatto data non valido nel body: ?{RequestSalvaRecuperi.dtDecorRevoca}");
			}
			if(estremi!= null)
				if(estremi.length() > 255) return inviaErroreBadRequest("{RequestSalvaRecuperi.estremiRecupero}  non può contenere più di 255 caratteri.");
			if(note!= null)
				if(note.length() > 4000) return inviaErroreBadRequest("{RequestSalvaRecuperi.note}  non può contenere più di 4000 caratteri.");
						
			
			EsitoSalvaRevocaDTO result = new EsitoSalvaRevocaDTO();
			result.setEsito(true);
			List<RevocaModalitaAgevolazioneDTO> revocheDTO = popolaRevoche(revoche, idProgetto, idMotivo, note, estremi, DateUtil.parseDate(dtRevoca) , dtDecorRevoca, codCausale ,null, ordineRecupero, idModalita);
			if (!revocheDTO.isEmpty()) {
				result = revocaDAO.checkRevoche(idUtente, idIride, beanUtil.transform(revocheDTO, RevocaModalitaAgevolazioneDTO.class));
			} else {
				result = new EsitoSalvaRevocaDTO();
				result.setEsito(Boolean.FALSE);
				MessaggioDTO error = new MessaggioDTO();
				error.setMsgKey(MessageConstants.MSG_REVOCA_NESSUN_REVOCA_MODIFICATO);
				result.setMsgs(new MessaggioDTO[] { error });
			}
			
			LOG.info(prf + " END");
			return Response.ok().entity(result).build();
		} catch(Exception e) {
			throw e;
		}
	}

	
	
	
	@Override
	public Response revocaTutto(HttpServletRequest req, RequestSalvaRevoche salvaRequest)
			throws UtenteException, Exception {
		String prf = "[RevocheServiceImpl::revocaTutto]";
		LOG.info(prf + " BEGIN");
		try {
			
			EsitoSalvaRevocaDTO esito = new EsitoSalvaRevocaDTO();
			esito.setEsito(Boolean.TRUE);
			if (salvaRequest.getRevoche() != null) {
				for (RigaRevocaItem item : salvaRequest.getRevoche()) {
					if (item.getIsRigaModalita() && NumberUtil.compare(item.getImportoErogato(),0D) != 0) {
						BigDecimal importoRevocatoNew = new BigDecimal(0D);
						if (NumberUtil.compare(item.getImportoErogato(),item.getImportoAgevolato()) > 0) {
							importoRevocatoNew = NumberUtil.subtract(NumberUtil.createScaledBigDecimal(item.getImportoErogato()),NumberUtil.createScaledBigDecimal(item.getImportoAgevolato()));							
						} else {
							importoRevocatoNew = NumberUtil.createScaledBigDecimal(item.getImportoErogato());
						}
						importoRevocatoNew = NumberUtil.subtract(importoRevocatoNew, NumberUtil.createScaledBigDecimal(item.getImportoRevocato()));
						item.setImportoRevocaNew(NumberUtil.toDouble(importoRevocatoNew));
					}
				}
			}
			
			LOG.info(prf + " END");
			return Response.ok().entity(salvaRequest.getRevoche()).build();
		} catch(Exception e) {
			throw e;
		}
	}
	@Override
	public Response salvaRevoche(HttpServletRequest req, RequestSalvaRevoche salvaRequest)
			throws UtenteException, Exception {
		String prf = "[RevocheServiceImpl::salvaRevoche]";
		LOG.info(prf + " BEGIN");
		try {
			HttpSession session = req.getSession();
			UserInfoSec userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
			Long idUtente = userInfo.getIdUtente();
			String idIride = userInfo.getIdIride();
			Long idProgetto = salvaRequest.getIdProgetto();
			List<RigaRevocaItem> revoche = salvaRequest.getRevoche();
			Long idMotivoRevoca = salvaRequest.getIdMotivoRevoca();
			String note = salvaRequest.getNote();
			String estremi = salvaRequest.getEstremi();
			String dtRevoca = salvaRequest.getDtRevoca();
			String dtDecorrenza = salvaRequest.getDtDecorRevoca();
			String ordineRecupero = salvaRequest.getOrdineRecupero();
			Long idModalitaRecupero = salvaRequest.getIdModalitaRecupero();
			EsitoSalvaRevocaDTO result = new EsitoSalvaRevocaDTO();
			
			/**
			 * Buono Domiciliarità/Residenzialità - Chiamata al servizio di revoca
			 * 
			 */
			ResponseEntity<String> response = revocaDAO.invioNotificaDomRes(note, dtRevoca, dtDecorrenza, idProgetto,
					idMotivoRevoca, userInfo.getBeneficiarioSelezionato().getDenominazione());
			if(response == null || (response != null && "OK".equals(response.getBody()))) {
			
				List<RevocaModalitaAgevolazioneDTO> revocheDTO = popolaRevoche(revoche, idProgetto, idMotivoRevoca, note, estremi,
						DateUtil.parseDate(dtRevoca), dtDecorrenza, null, null, ordineRecupero, idModalitaRecupero);
	
				result = revocaDAO.salvaRevoche(idUtente, idIride, beanUtil.transform(revocheDTO, RevocaModalitaAgevolazioneDTO.class));	
					 
				LOG.info(prf + " END");
				return Response.ok().entity(result).build();
			} else {
				LOG.error("Errore nella chiamata al servizio di Scelta sociale " + response);
				result.setEsito(Boolean.FALSE);
				MessaggioDTO error = new MessaggioDTO();
				error.setMsgKey("Errore nella chiamata al servizio di Scelta Sociale");
				result.setMsgs(new MessaggioDTO[] { error });
				return Response.ok().entity(result).build();
			}
		} catch(Exception e) {
			 EsitoSalvaRevocaDTO result = new EsitoSalvaRevocaDTO();
			 result.setEsito(Boolean.FALSE);
			 MessaggioDTO msg = new MessaggioDTO();
			 msg.setMsgKey(e.getMessage());
			 result.setMsgs(new MessaggioDTO []{msg});
			throw e;
		}
	}
	private List<RevocaModalitaAgevolazioneDTO> popolaRevoche(List<RigaRevocaItem> revoche,
			Long idProgetto, Long idMotivoRevoca, String note, String estremi, Date dtRevoca, String dtDecorrenza, String codCausaleDisimpegno, String idAnnoContabile, 
			String ordineRecupero, Long idModalitaRecupero) {
		String prf = "[RevocheServiceImpl::popolaRevoche]";
		LOG.info(prf + " START");
		List<RevocaModalitaAgevolazioneDTO> result = new ArrayList<RevocaModalitaAgevolazioneDTO>();
		if (revoche != null) {
			for (RigaRevocaItem item : revoche) {
				if (item.getIsRigaModalita()) {
					RevocaModalitaAgevolazioneDTO dto = new RevocaModalitaAgevolazioneDTO();
				
					dto.setDtRevoca(dtRevoca);
					dto.setDtDecorRevoca(dtDecorrenza != null ? DateUtil.parseDate(dtDecorrenza) : null);
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
		LOG.info(prf + " END");
		return result;
	}
	
	
	
	//////////////////////////////////////MODIFICA REVOCA /////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////////////////////
	
	@Override
	public Response getRevochePerModifica(HttpServletRequest req, Long idProgetto) throws UtenteException, Exception {
		String prf = "[RevocheServiceImpl::getRevochePerModifica]";
		LOG.info(prf + " BEGIN");
		try {
			HttpSession session = req.getSession();
			UserInfoSec userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
			Long idUtente = userInfo.getIdUtente();
			String idIride = userInfo.getIdIride();
			
			RevocaDTO filtro = new RevocaDTO();
			filtro.setIdProgetto(idProgetto);
			ModalitaAgevolazioneDTO[] listaRevoche = revocaDAO.findRiepilogoRevoche(idUtente, idIride, filtro);
			LOG.info(prf + " listaRevoche length : " + listaRevoche.length);
			ArrayList<RigaModificaRevocaItem> result = new ArrayList<RigaModificaRevocaItem>();
			if (listaRevoche != null && listaRevoche.length > 0) {
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
				for (ModalitaAgevolazioneDTO modalita : listaRevoche) {
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
						for (it.csi.pbandi.pbweberog.dto.disimpegni.CausaleErogazioneDTO causale : modalita.getCausaliErogazioni()) {
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
					 * revoche
					 */
					if (modalita.getRevoche() != null && modalita.getRevoche().length > 0) {
						for (it.csi.pbandi.pbweberog.dto.revoca.RevocaDTO revoca : modalita.getRevoche()) {
							RigaModificaRevocaItem rigaRevoca = new RigaModificaRevocaItem();
							rigaRevoca.setIsRigaModalita(Boolean.FALSE);
							rigaRevoca.setIsRigaCausale(Boolean.FALSE);
							rigaRevoca.setIsRigaRevoca(Boolean.TRUE);
							rigaRevoca.setIsRigaRecupero(Boolean.FALSE);
							rigaRevoca.setIsRigaTotale(Boolean.FALSE);
							rigaRevoca.setIdRevoca(NumberUtil.getStringValue(revoca.getIdRevoca()));
							rigaRevoca.setLabel(revoca.getDescMotivoRevoca());
							rigaRevoca.setCausaleDisimpegno(revoca.getCausaleDisimpegno());//////
							rigaRevoca.setMotivazione(revoca.getDescMotivoRevoca());//////
							rigaRevoca.setImportoRevocato(revoca.getImporto());
							rigaRevoca.setData(DateUtil.formatDate(revoca.getDtRevoca()));
							rigaRevoca.setDtDecorRevoca(revoca.getDtDecorRevoca() != null ? DateUtil.formatDate(revoca.getDtDecorRevoca()) : "");
							rigaRevoca.setRiferimento(revoca.getEstremi());
							
						
							ArrayList<RevocaIrregolarita>irregolarita=new ArrayList<RevocaIrregolarita>();
							it.csi.pbandi.pbweberog.dto.disimpegni.RevocaIrregolaritaDTO[] revocaIrregolarita = revoca.getRevocaIrregolarita(); 
							if(!ObjectUtil.isEmpty(revocaIrregolarita)){
								LOG.info("\n\n trovate irregolarita "+revocaIrregolarita.length);
								irregolarita=beanUtil.transformToArrayList(revocaIrregolarita, RevocaIrregolarita.class);
								String idRevoca = rigaRevoca.getIdRevoca();
								if(idRevoca!=null ){
									 for (RevocaIrregolarita vo : irregolarita) {
										 //logger.shallowDump(vo, "info");
										 if(vo.getIdRevoca()!=null){
											 if(idRevoca.equals(vo.getIdRevoca().toString())){
												 Double quotaIrreg = vo.getQuotaIrreg();
												 if(quotaIrreg!=null){
													 Double importo=rigaRevoca.getImportoIrregolarita();
													 if(importo==null){
														 importo=0d;
													 }
													 rigaRevoca.setImportoIrregolarita(NumberUtil.sum(quotaIrreg, importo));
												 }
											 }
										 }
									}
							    }
							}
					
							rigaRevoca.setIrregolarita(irregolarita );
							rigaRevoca.setHasIrregolarita(true);
							
							setTotaleImportoIrregolarita(rigaTotale, rigaRevoca);
							if(!ObjectUtil.isEmpty(rigaRevoca.getIdRevoca()))
									result.add(rigaRevoca);
						}
					}

					/*
					 * recuperi
					 */
					if (modalita.getRecuperi() != null && modalita.getRecuperi().length > 0) {
						for (it.csi.pbandi.pbweberog.dto.disimpegni.RecuperoDTO recupero : modalita.getRecuperi()) {
							LOG.info("++++++++++ recupero ++++++++++");
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
				result.add(rigaTotale);
			}

			
			LOG.info(prf + " END");
			return Response.ok().entity(result).build();
		} catch(Exception e) {
			throw e;
		}
	}
	
	private void setTotaleImportoIrregolarita(RigaModificaRevocaItem rigaTotale, RigaModificaRevocaItem rigaRevoca) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public Response checkPropostaCertificazioneProgetto(HttpServletRequest req, Long idProgetto)
			throws UtenteException, Exception {
		String prf = "[RevocheServiceImpl::checkPropostaCertificazioneProgetto]";
		LOG.info(prf + " BEGIN");
		try {
			HttpSession session = req.getSession();
			UserInfoSec userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
			Long idUtente = userInfo.getIdUtente();
			String idIride = userInfo.getIdIride();
			EsitoOperazioni esito = new EsitoOperazioni();
			
			Boolean es = revocaDAO.checkPropostaCertificazione(idUtente, idIride, idProgetto);
			esito.setEsito(es);
			if(es) {				
				esito.setMsg(ErrorMessages.ERROR_ESISTE_PROP_CERTIF);
			}
			LOG.info(prf + " END");
			return Response.ok().entity(esito).build();
		} catch(Exception e) {
			throw e;
		}
	}
	
	@Override
	public Response getDettaglioRevoca(HttpServletRequest req, Long idRevoca, Long idProgetto)
			throws UtenteException, Exception {
		String prf = "[RevocheServiceImpl::getDettaglioRevoca]";
		LOG.info(prf + " BEGIN");
		try {
			HttpSession session = req.getSession();
			UserInfoSec userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
			Long idUtente = userInfo.getIdUtente();
			String idIride = userInfo.getIdIride();
			
			RevocaDTO filtro = new RevocaDTO();
			filtro.setIdProgetto(idProgetto);
			filtro.setIdRevoca(idRevoca);
			ModalitaAgevolazioneDTO[] listaRevoche = revocaDAO.findRiepilogoRevoche(idUtente, idIride, filtro);	
			LOG.info(prf + " size listRevoceh: " + listaRevoche.length);
			DettaglioRevoca result = new DettaglioRevoca();
			if (listaRevoche != null) {
				ModalitaAgevolazioneDTO modalita = new ModalitaAgevolazioneDTO(); 
				for (ModalitaAgevolazioneDTO modAgev : listaRevoche)  {
					it.csi.pbandi.pbweberog.dto.revoca.RevocaDTO[] revoche = modAgev.getRevoche();
					if (revoche != null && revoche[0] != null) {
						LOG.info(prf + " modAgev: " + modAgev.getDescModalitaAgevolazione());
						modalita = modAgev;
						LOG.info(prf + " modalita: " + modalita.getDescModalitaAgevolazione());
					}
					beanUtil.deepValueCopy(modAgev, modalita);
					LOG.info(prf + " modalita1: " + modalita.getDescModalitaAgevolazione());
				}
				LOG.info(prf + " modalita: " + modalita.getDescModalitaAgevolazione());
				LOG.info(prf + " importo agevolato: " +modalita.getQuotaImportoAgevolato());
				result.setModalitaAgevolazione(modalita.getDescModalitaAgevolazione());
				result.setImportoAgevolato(modalita.getQuotaImportoAgevolato() == null ? 0D : modalita.getQuotaImportoAgevolato());
				result.setImportoErogato(modalita.getImportoErogazioni() == null ? 0D : modalita.getImportoErogazioni());
				result.setImportoRevocato(modalita.getTotaleImportoRevocato() == null ? 0D : modalita.getTotaleImportoRevocato());
				result.setImportoRecuperato(modalita.getTotaleImportoRecuperato() == null ? 0D : modalita.getTotaleImportoRecuperato());
				
				// dati revoca (in modifica)
				it.csi.pbandi.pbweberog.dto.revoca.RevocaDTO[] revoche = modalita.getRevoche();
				//if (revoche != null && revoche[0] != null) {
					it.csi.pbandi.pbweberog.dto.revoca.RevocaDTO revocaSelezionata = revoche[0];
					result.setIdRevoca(revocaSelezionata.getIdRevoca());
					result.setIdMotivoRevoca(revocaSelezionata.getIdMotivoRevoca());
					result.setIdAnnoContabile(NumberUtil.getStringValue(revocaSelezionata.getIdPeriodo()));
					result.setCodiceCausaleDisimpegno(revocaSelezionata.getCodCausaleDisimpegno());
					result.setDataRevoca(DateUtil.formatDate(revocaSelezionata.getDtRevoca()));
					result.setDtDecorRevoca(revocaSelezionata.getDtDecorRevoca() != null ? DateUtil.formatDate(revocaSelezionata.getDtDecorRevoca()) : null);
					result.setEstremiDeterminaRevoca(revocaSelezionata.getEstremi());
					result.setNoteRevoca(revocaSelezionata.getNote());
					result.setImportoRevoca(revocaSelezionata.getImporto());
					result.setOrdineRecupero(revocaSelezionata.getFlagOrdineRecupero());
					result.setIdModalitaRecupero(revocaSelezionata.getIdMancatoREcupero());
					////////////////
				//}
			}
			LOG.info(prf + " END");

			return Response.ok().entity(result).build();
		} catch(Exception e) {
			throw e;
		}
	}
	
	@Override
	public Response modificaRevoca(HttpServletRequest req, RequestModificaRevoca modificaRequest)
			throws UtenteException, Exception {
		String prf = "[RevocheServiceImpl::modificaRevoca]";
		LOG.info(prf + " BEGIN");
		try {
			HttpSession session = req.getSession();
			UserInfoSec userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
			Long idUtente = userInfo.getIdUtente();
			String idIride = userInfo.getIdIride();
			Long idProgetto = modificaRequest.getIdProgetto();
			
			ModalitaAgevolazioneDTO modalitaAgevolazione = new ModalitaAgevolazioneDTO();
			modalitaAgevolazione.setIdProgetto(idProgetto);
			it.csi.pbandi.pbweberog.dto.revoca.RevocaDTO revoca = new it.csi.pbandi.pbweberog.dto.revoca.RevocaDTO();
			revoca.setIdRevoca(modificaRequest.getRevoca().getIdRevoca());
			revoca.setIdMotivoRevoca(modificaRequest.getRevoca().getIdMotivoRevoca());
			revoca.setDtRevoca(DateUtil.getDate(modificaRequest.getRevoca().getDataRevoca()));
			revoca.setDtDecorRevoca(modificaRequest.getRevoca().getDtDecorRevoca() != null ? DateUtil.getDate(modificaRequest.getRevoca().getDtDecorRevoca()) : null);
			revoca.setEstremi(modificaRequest.getRevoca().getEstremiDeterminaRevoca());
			revoca.setNote(modificaRequest.getRevoca().getNoteRevoca());
			revoca.setImporto(modificaRequest.getRevoca().getImportoRevoca());
			it.csi.pbandi.pbweberog.dto.revoca.RevocaDTO[] revoche = new it.csi.pbandi.pbweberog.dto.revoca.RevocaDTO[]{revoca};
			modalitaAgevolazione.setRevoche(revoche);
			
			
			EsitoSalvaRevocaDTO result = revocaDAO.modificaRevoca(idUtente, idIride, modalitaAgevolazione);
			LOG.info(prf + " END");
			return Response.ok().entity(result).build();
		} catch(Exception e) {
			throw e;
		}
	}
	@Override
	public Response cancellaRevoca(HttpServletRequest req, Long idRevoca) throws UtenteException, Exception {
		String prf = "[RevocheServiceImpl::cancellaRevoca]";
		LOG.info(prf + " BEGIN");
		try {
			HttpSession session = req.getSession();
			UserInfoSec userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
			Long idUtente = userInfo.getIdUtente();
			String idIride = userInfo.getIdIride();
			EsitoSalvaRevocaDTO result = revocaDAO.cancellaRevoca(idUtente, idIride, idRevoca);
			LOG.info(prf + " END");
			return Response.ok().entity(result).build();
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
	
	
	
	
}
