/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.business.api.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.csi.csi.wrapper.UnrecoverableException;
import it.csi.pbandi.pbservizit.exception.UtenteException;
import it.csi.pbandi.pbservizit.pbandisrv.business.gestionedatigenerali.GestioneDatiGeneraliBusinessImpl;
import it.csi.pbandi.pbservizit.pbandisrv.dto.gestioneindicatori.IndicatoreDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.gestioneindicatori.TipoIndicatoreDTO;
import it.csi.pbandi.pbservizit.pbandisrv.exception.FormalParameterException;
import it.csi.pbandi.pbservizit.pbandiutil.commonweb.business.impl.EsecuzioneAttivitaBusinessImpl;
import it.csi.pbandi.pbservizit.security.UserInfoSec;
import it.csi.pbandi.pbservizit.business.api.IndicatoriApi;
import it.csi.pbandi.pbservizit.dto.EsitoOperazioni;
import it.csi.pbandi.pbservizit.dto.ExecResults;
import it.csi.pbandi.pbservizit.dto.indicatori.EsitoSaveIndicatori;
import it.csi.pbandi.pbservizit.dto.indicatori.IndicatoreItem;
import it.csi.pbandi.pbservizit.dto.indicatori.InfoIndicatore;
import it.csi.pbandi.pbservizit.integration.dao.IndicatoriDAO;
//import it.csi.pbandi.pbservizit.integration.dao.InizializzazioneDAO;
import it.csi.pbandi.pbservizit.integration.request.SalvaIndicatoriRequest;
import it.csi.pbandi.pbservizit.integration.request.ValidazioneDatiIndicatoriRequest;
import it.csi.pbandi.pbservizit.integration.response.ResponseGetIndicatori;
import it.csi.pbandi.pbservizit.integration.response.ResponseInizializzaIndicatori;
import it.csi.pbandi.pbservizit.pbandiutil.common.ObjectUtil;
import it.csi.pbandi.pbservizit.util.BeanUtil;
import it.csi.pbandi.pbservizit.util.Constants;
import it.csi.pbandi.pbservizit.util.ErrorMessages;
import it.csi.pbandi.pbservizit.util.MessageConstants;
import it.csi.pbandi.pbservizit.util.NumberUtil;

@Service
public class IndicatoriApiServiceImpl implements IndicatoriApi {

	@Autowired
	protected BeanUtil beanUtil;
	private Logger LOG = Logger.getLogger(Constants.COMPONENT_NAME);

//	@Autowired
//	InizializzazioneDAO inizializzazioneDAO;

	@Autowired
	GestioneDatiGeneraliBusinessImpl gestioneDatiGeneraliBusinessImpl;

	@Autowired
	private IndicatoriDAO indicatoriDAO;

	@Autowired
	EsecuzioneAttivitaBusinessImpl esecuzioneAttivitaBusiness;

	private static String OPERAZIONE_GESTIONE_OPERATIVA = "GESTIONE";
	private static final String OPERAZIONE_AVVIO = "AVVIO";
	private static final String OPERAZIONE_RETTIFICA = "RETTIFICA";
	private static final int MAX_SCALE_INDICATORI_MONIT = 11;
	private static final String N_A = "NA";

	@Override
	public Response inizializzaIndicatori(HttpServletRequest req, Long idProgetto) throws UtenteException, Exception {
		String prf = "[IndicatoriServiceImpl::inizializzaIndicatori]";
		LOG.debug(prf + " BEGIN");
		try {
			if (idProgetto == null) {
				return inviaErroreBadRequest("Parametro mancato: ?[idProgetto]");
			}

			HttpSession session = req.getSession();
			UserInfoSec userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);

			Boolean esisteCFP = indicatoriDAO.esisteCFP(userInfo, idProgetto);

			Boolean esisteDsFinale = indicatoriDAO.esisteDsFinale(userInfo, idProgetto);

			ResponseInizializzaIndicatori res = new ResponseInizializzaIndicatori();
			res.setEsisteCFP(esisteCFP);
			res.setEsisteDsFinale(esisteDsFinale);

			LOG.debug(prf + " END");
			return Response.ok().entity(res).build();
		} catch (Exception e) {
			throw e;
		}

	}

	@Override
	public Response getCodiceProgetto(HttpServletRequest req, Long idProgetto) throws UtenteException, Exception {
		String prf = "[IndicatoriServiceImpl::getCodiceProgetto]";
		LOG.debug(prf + " BEGIN");
		try {
			if (idProgetto == null) {
				return inviaErroreBadRequest("Parametro mancato: ?[idProgetto]");
			}
			String codiceProgetto = indicatoriDAO.findCodiceProgetto(idProgetto);

			LOG.debug(prf + " END");
			return Response.ok().entity(codiceProgetto).build();
		} catch (Exception e) {
			throw e;
		}

	}

	@Override
	public Response getIndicatori(HttpServletRequest req, Long idProgetto, Boolean isBandoSif)
			throws UtenteException, Exception {
		String prf = "[IndicatoriServiceImpl::getIndicatori]";
		LOG.debug(prf + " BEGIN");

		try {
			HttpSession session = req.getSession();
			UserInfoSec userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
			Long idUtente = userInfo.getIdUtente();
			String idIride = userInfo.getIdIride();

//			boolean isRettifica = esecuzioneAttivitaBusiness.checkCurrentActivity(Constants.TASKNAME_RETTIFICA_INDICATORI);
//
			ArrayList<IndicatoreItem> indicatoriMonitoraggio;
			ArrayList<IndicatoreItem> altriIndicatori;
//			if (isRettifica) {
//				
//				indicatoriMonitoraggio = this.findIndicatoriRettifica(idUtente, idIride, idProgetto, true);
//				altriIndicatori = this.findIndicatoriRettifica(idUtente, idIride, idProgetto, false);
//			} else {
			indicatoriMonitoraggio = this.findIndicatoriGestione(idUtente, idIride, idProgetto, true, isBandoSif);
			altriIndicatori = this.findIndicatoriGestione(idUtente, idIride, idProgetto, false, isBandoSif);
			// }

			ResponseGetIndicatori res = new ResponseGetIndicatori();
			res.setIndicatoriMonitoraggio(indicatoriMonitoraggio);
			res.setAltriIndicatori(altriIndicatori);

			LOG.debug(prf + " END");
			return Response.ok().entity(res).build();
		} catch (Exception e) {
			throw e;
		}
	}

	private ArrayList<IndicatoreItem> findIndicatoriGestione(Long idUtente, String idIride, Long idProgetto,
			boolean isMonitoraggio, Boolean isBandoSif) throws FormalParameterException, UnrecoverableException {
		String prf = "[IndicatoriServiceImpl::findIndicatoriGestione]";
		LOG.debug(prf + " BEGIN");
		if (isBandoSif != null && isBandoSif.equals(Boolean.TRUE)) {
			ArrayList<IndicatoreItem> listaIndicatori = indicatoriDAO.findIndicatoriGestioneSif(idUtente, idIride,
					idProgetto, isMonitoraggio);
			LOG.debug(prf + " END");
			return listaIndicatori;
		} else {
			TipoIndicatoreDTO[] listaIndicatori = indicatoriDAO.findIndicatoriGestione(idUtente, idIride, idProgetto,
					isMonitoraggio);
			LOG.debug(prf + " END");
			return trasformaIndicatoriPerVisualizzazione(listaIndicatori, OPERAZIONE_GESTIONE_OPERATIVA,
					isMonitoraggio);
		}

	}

	@Override
	public Response getIndicatoriAvvio(HttpServletRequest req, Long idProgetto) throws UtenteException, Exception {
		String prf = "[IndicatoriServiceImpl::getIndicatoriAvvio]";
		LOG.debug(prf + " BEGIN");

		try {
			HttpSession session = req.getSession();
			UserInfoSec userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
			Long idUtente = userInfo.getIdUtente();
			String idIride = userInfo.getIdIride();

//			boolean isRettifica = esecuzioneAttivitaBusiness.checkCurrentActivity(Constants.TASKNAME_RETTIFICA_INDICATORI);
//
			ArrayList<IndicatoreItem> indicatoriMonitoraggio;
			ArrayList<IndicatoreItem> altriIndicatori;
//			if (isRettifica) {
//				
//				indicatoriMonitoraggio = this.findIndicatoriRettifica(idUtente, idIride, idProgetto, true);
//				altriIndicatori = this.findIndicatoriRettifica(idUtente, idIride, idProgetto, false);
//			} else {
			indicatoriMonitoraggio = this.findIndicatoriAvvio(idUtente, idIride, idProgetto, true);
			altriIndicatori = this.findIndicatoriAvvio(idUtente, idIride, idProgetto, false);
			// }

			ResponseGetIndicatori res = new ResponseGetIndicatori();
			res.setIndicatoriMonitoraggio(indicatoriMonitoraggio);
			res.setAltriIndicatori(altriIndicatori);

			LOG.debug(prf + " END");
			return Response.ok().entity(res).build();
		} catch (Exception e) {
			throw e;
		}
	}

	private ArrayList<IndicatoreItem> findIndicatoriAvvio(Long idUtente, String idIride, Long idProgetto,
			boolean isMonitoraggio) throws FormalParameterException, UnrecoverableException {
		String prf = "[IndicatoriServiceImpl::findIndicatoriAvvio]";
		LOG.debug(prf + " START");
		TipoIndicatoreDTO[] listaIndicatori = indicatoriDAO.findIndicatoriAvvio(idUtente, idIride, idProgetto,
				isMonitoraggio);
		LOG.debug(prf + " END");
		return trasformaIndicatoriPerVisualizzazione(listaIndicatori, OPERAZIONE_AVVIO, isMonitoraggio);
	}

//	private ArrayList<IndicatoreItem> findIndicatoriRettifica(Long idUtente, String idIride, Long idProgetto,
//			boolean isMonitoraggio) throws FormalParameterException, UnrecoverableException {
//		String prf = "[IndicatoriServiceImpl::findIndicatoriRettifica]";
//		LOG.debug(prf + " BEGIN");
//		TipoIndicatoreDTO[] listaIndicatori = indicatoriDAO.findIndicatoriGestione(idUtente, idIride, idProgetto, isMonitoraggio);
//		LOG.debug(prf + " END");
//		return trasformaIndicatoriPerVisualizzazione(listaIndicatori, OPERAZIONE_RETTIFICA,isMonitoraggio);
//
//	}

	private ArrayList<IndicatoreItem> trasformaIndicatoriPerVisualizzazione(TipoIndicatoreDTO[] listaIndicatori,
			String operazione, boolean isMonitoraggio) {
		String prf = "[IndicatoriServiceImpl::trasformaIndicatoriPerVisualizzazione]";
		LOG.debug(prf + " START");
		ArrayList<IndicatoreItem> res = new ArrayList<IndicatoreItem>();
		if (listaIndicatori != null) {
			for (TipoIndicatoreDTO tipoDto : listaIndicatori) {
				// aggiungo la riga del tipo
				IndicatoreItem rigaTipo = new IndicatoreItem();
				rigaTipo.setIdTipoIndicatore(tipoDto.getIdTipoIndicatore());
				rigaTipo.setDescTipoIndicatore(tipoDto.getDescTipoIndicatore());
				rigaTipo.setIsTipoIndicatore(true);
				res.add(rigaTipo);

				// ciclo sugli indicatori del tipo
				if (tipoDto.getIndicatori() != null) {
					for (IndicatoreDTO dto : tipoDto.getIndicatori()) {
						IndicatoreItem rigaIndicatore = new IndicatoreItem();

						InfoIndicatore info = indicatoriDAO.getInfoIndicatore(dto.getIdBando(), dto.getIdIndicatore());

						rigaIndicatore.setInfoIniziale(info.getInfoIniziale());
						rigaIndicatore.setInfoFinale(info.getInfoFinale());

						rigaIndicatore.setIdBando(dto.getIdBando()); // Jira PBANDI-2654
						rigaIndicatore.setIdIndicatore(dto.getIdIndicatore());
						rigaIndicatore.setCodIgrue(dto.getCodIgrue());
						rigaIndicatore.setDescIndicatore(dto.getDescIndicatore());
						rigaIndicatore.setDescUnitaMisura(dto.getDescUnitaMisura());
						rigaIndicatore.setFlagObbligatorio(dto.getFlagObbligatorio());

						rigaIndicatore.setIdTipoIndicatore(tipoDto.getIdTipoIndicatore());
						rigaIndicatore.setIsTipoIndicatore(false);

						if (operazione.equals(OPERAZIONE_AVVIO)) {
							rigaIndicatore.setIsValoreInizialeEditable(true);
							rigaIndicatore.setIsValoreAggiornamentoEditable(false);
							rigaIndicatore.setIsValoreFinaleEditable(false);
							rigaIndicatore.setFlagNonApplicabile(dto.getFlagNonApplicabile());
							if (!isMonitoraggio) {
								rigaIndicatore.setFlagNonApplicabile(dto.getFlagNonApplicabile());
								rigaIndicatore.setIsFlagNonApplicabileEditabile(Boolean.TRUE);
							}
						} else {

							if (ObjectUtil.isTrue(dto.getFlagNonApplicabile())) {
								rigaIndicatore.setValoreIniziale(Constants.N_A);
								rigaIndicatore.setValoreAggiornamento(Constants.N_A);
								rigaIndicatore.setValoreFinale(Constants.N_A);
							} else {
								if (isMonitoraggio) {
									if (dto.getValoreIniziale() == null) {
										rigaIndicatore.setValoreIniziale(null);
									} else {
										rigaIndicatore.setValoreIniziale(NumberUtil.getStringValueItalianFormat(
												dto.getValoreIniziale(), Constants.MAX_SCALE_INDICATORI_MONIT));
									}
									if (dto.getValoreAggiornamento() == null) {
										rigaIndicatore.setValoreAggiornamento(null);
									} else {
										rigaIndicatore.setValoreAggiornamento(NumberUtil.getStringValueItalianFormat(
												dto.getValoreAggiornamento(), Constants.MAX_SCALE_INDICATORI_MONIT));
									}

									if (dto.getValoreFinale() == null) {
										rigaIndicatore.setValoreFinale(null);
									} else {
										rigaIndicatore.setValoreFinale(NumberUtil.getStringValueItalianFormat(
												dto.getValoreFinale(), Constants.MAX_SCALE_INDICATORI_MONIT));
									}

								} else {
//										rigaIndicatore.setValoreIniziale(dto.getValoreIniziale() );
//										rigaIndicatore.setValoreAggiornamento(dto.getValoreAggiornamento());
//										rigaIndicatore.setValoreFinale(dto.getValoreFinale());
									rigaIndicatore.setValoreIniziale(NumberUtil.getStringValueItalianFormat(
											dto.getValoreIniziale(), Constants.MAX_SCALE_ALTRI_INDICATORI));
									rigaIndicatore.setValoreAggiornamento(NumberUtil.getStringValueItalianFormat(
											dto.getValoreAggiornamento(), Constants.MAX_SCALE_ALTRI_INDICATORI));
									rigaIndicatore.setValoreFinale(NumberUtil.getStringValueItalianFormat(
											dto.getValoreFinale(), Constants.MAX_SCALE_ALTRI_INDICATORI));
								}
							}

							rigaIndicatore.setFlagNonApplicabile(dto.getFlagNonApplicabile());
							if (operazione.equals(OPERAZIONE_RETTIFICA)) {
								boolean editable = true;
								if (dto.getFlagNonApplicabile()) {
									editable = false;
								}
								rigaIndicatore.setIsValoreAggiornamentoEditable(editable);
								rigaIndicatore.setIsValoreFinaleEditable(editable);
								rigaIndicatore.setIsValoreInizialeEditable(editable);
								if (!isMonitoraggio) {
									rigaIndicatore.setIsFlagNonApplicabileEditabile(Boolean.TRUE);
								}

							} else {
								if (isMonitoraggio) {
									rigaIndicatore.setIsValoreInizialeEditable(false);
									rigaIndicatore.setIsValoreAggiornamentoEditable(true);
									rigaIndicatore.setIsValoreFinaleEditable(true);
								} else {
									rigaIndicatore.setFlagNonApplicabile(dto.getFlagNonApplicabile());
									rigaIndicatore.setIsFlagNonApplicabileEditabile(Boolean.FALSE);
									if (dto.getFlagNonApplicabile()) {
										rigaIndicatore.setIsValoreInizialeEditable(false);
										rigaIndicatore.setIsValoreAggiornamentoEditable(false);
										rigaIndicatore.setIsValoreFinaleEditable(false);
									} else {
										rigaIndicatore.setIsValoreInizialeEditable(false);
										rigaIndicatore.setIsValoreAggiornamentoEditable(true);
										rigaIndicatore.setIsValoreFinaleEditable(true);
									}
								}

							}
						}

						res.add(rigaIndicatore);
					}
				}
			}
		}
		LOG.debug(prf + " END");
		return res;
	}

	/***************************************************************
	 * VALIDAZIONI DATI GESTIONE
	 ***************************************************************/
	/*
	 * @Override public Response
	 * controllaDatiPerSalvataggioGestione(HttpServletRequest req,
	 * ValidazioneDatiIndicatoriRequest validazioneRequest) throws UtenteException,
	 * Exception { String prf =
	 * "[IndicatoriServiceImpl::controllaDatiPerSalvataggioGestione]"; LOG.debug(prf
	 * + " BEGIN");
	 * 
	 * try { HttpSession session = req.getSession(); UserInfoSec userInfo =
	 * (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR); Long
	 * idUtente = userInfo.getIdUtente(); String idIride = userInfo.getIdIride();
	 * LOG.info(prf + ": idUtente = "+ idUtente + " idIride = " + idIride);
	 * 
	 * ExecResults result = new ExecResults(); Set<String> globalErrors = new
	 * HashSet<String>(); Set<String> globalMessages = new HashSet<String>();
	 * Map<String, String> fieldErrors = new HashMap<String, String>();
	 * 
	 * // verifico indicatori monitoraggio boolean isErrorMonitoraggio =
	 * verificaIndicatoriMonitoraggioGestione(validazioneRequest.getIndicatori(),
	 * globalErrors, fieldErrors, false, validazioneRequest.getIdProgetto(),
	 * userInfo);
	 * 
	 * // verifico altri indicatori boolean isErrorAltri =
	 * verificaAltriIndicatori(validazioneRequest.getIndicatori(), globalErrors,
	 * fieldErrors, false);
	 * 
	 * if (isErrorMonitoraggio || isErrorAltri) { //
	 * MessageManager.setGlobalErrors(result, globalErrors.toArray(new String[]{}));
	 * result.setGlobalErrors(globalErrors); result.setFldErrors(fieldErrors);
	 * //result.setResultCode(codeError); } else { //
	 * MessageManager.setGlobalMessage(result, MessageKey.CONFERMA_SALVATAGGIO);
	 * globalMessages.add(MessageConstants.CONFERMA_SALVATAGGIO1);
	 * result.setGlobalMessages(globalMessages); //result.setResultCode(codeOk); }
	 * LOG.debug(prf + " END"); return Response.ok().entity(result).build(); }
	 * catch(Exception e) { throw e; } }
	 * 
	 * private boolean
	 * verificaIndicatoriMonitoraggioGestione(ArrayList<IndicatoreItem> indicatori,
	 * Set<String> globalErrors, Map<String, String> fieldErrors, boolean
	 * isChiusura, Long idProgetto, UserInfoSec userInfo) throws
	 * FormalParameterException, UnrecoverableException { String prf =
	 * "[IndicatoriServiceImpl::verificaIndicatoriMonitoraggioGestione]";
	 * LOG.debug(prf + " START");
	 * 
	 * boolean isError = false; String valoreInizialeField =
	 * "appDataelencoIndicatoriMonitoraggio[#].valoreIniziale"; String
	 * valoreAggiornamentoField =
	 * "appDataelencoIndicatoriMonitoraggio[#].valoreAggiornamento"; String
	 * valoreFinaleField = "appDataelencoIndicatoriMonitoraggio[#].valoreFinale";
	 * 
	 * // resetto eventuali messaggi di errore precedenti
	 * this.resettaErroriSuTipiMonitoraggio(indicatori);
	 * 
	 * // PBANDI-2814: alcuni controlli vanno fatti solo in alcuni casi. Boolean
	 * esisteCFP = indicatoriDAO.esisteCFP(userInfo, idProgetto); Boolean
	 * esisteDsFinale = indicatoriDAO.esisteDsFinale(userInfo, idProgetto); boolean
	 * isBeneficiario = this.isBeneficiario(userInfo);
	 * LOG.info("\n\nverificaIndicatoriMonitoraggio(): idProgetto = "
	 * +idProgetto+"; esisteCFP = "+esisteCFP+"; esisteDsFinale = "
	 * +esisteDsFinale+"; isBeneficiario = "+isBeneficiario+"\n\n");
	 * 
	 * // verifichiamo la coerenza dei valori iniziali/finali int i = 0; for
	 * (IndicatoreItem item : indicatori) { if (!item.getIsTipoIndicatore()) { //
	 * PBANDI-1573: verifico che il valore degli indicatori sia // strettamente
	 * maggiore di zero // PBANDI-1587: lunghezza indicatori LOG.debug(prf +
	 * " Verificando idIndicatore: " + item.getIdIndicatore() +
	 * ", descIndicatore: "+ item.getDescIndicatore());
	 * if(item.getValoreIniziale()!=null && item.getValoreAggiornamento()!=null) {
	 * LOG.debug(prf + " ********* valoreIniziale: " + item.getValoreIniziale() +
	 * " ********* "); if (!ObjectUtil.isEmpty(item.getValoreIniziale()) &&
	 * !item.getValoreAggiornamento().equalsIgnoreCase(N_A)) { Double val =
	 * NumberUtil.toNullableDoubleItalianFormat(item.getValoreIniziale()); if ((val
	 * != null && val <= 0) || !NumberUtil.isImporto(item.getValoreIniziale(),
	 * Constants.MAX_CIFRE_INTERE)) { fieldErrors.put(
	 * valoreInizialeField.replaceAll("#", String.valueOf(i)), "Valore programmato["
	 * + i + "] - " + ErrorMessages.ERRORE_VALORE_NON_AMMESSO); isError = true; }
	 * else if (!NumberUtil.isDecimalValid(item.getValoreIniziale(),
	 * MAX_SCALE_INDICATORI_MONIT)) {
	 * fieldErrors.put(valoreInizialeField.replaceAll("#",String.valueOf(i)),
	 * "Valore programmato[" + i + "] - " +ErrorMessages.ERRORE_VALORE_NON_AMMESSO);
	 * isError = true; } } }
	 * 
	 * if(item.getValoreAggiornamento()!=null) { LOG.debug(prf +
	 * " ********* valoreAggiornamento: " + item.getValoreAggiornamento() +
	 * " ********* "); if (!ObjectUtil.isEmpty(item.getValoreAggiornamento()) &&
	 * !item.getValoreAggiornamento().equalsIgnoreCase(N_A)) { if
	 * (!NumberUtil.isImporto(item.getValoreAggiornamento(),
	 * Constants.MAX_CIFRE_INTERE) ||
	 * NumberUtil.toNullableDoubleItalianFormat(item.getValoreAggiornamento()) <= 0)
	 * { fieldErrors.put( valoreAggiornamentoField.replaceAll( "#",
	 * String.valueOf(i)), "Valore aggiornamento[" + i + "] - " +
	 * ErrorMessages.ERRORE_VALORE_NON_AMMESSO); isError = true; } else if
	 * (!NumberUtil.isDecimalValid(item.getValoreAggiornamento(),
	 * MAX_SCALE_INDICATORI_MONIT)) { fieldErrors.put(
	 * valoreAggiornamentoField.replaceAll("#", String.valueOf(i)),
	 * "Valore aggiornamento[" + i + "] - " +
	 * ErrorMessages.ERRORE_VALORE_NON_AMMESSO); isError = true; } } }
	 * 
	 * if(item.getValoreFinale()!=null) { LOG.debug(prf +
	 * " ********* valoreFinale: " + item.getValoreFinale() + " ********* "); if
	 * (!ObjectUtil.isEmpty(item.getValoreFinale()) &&
	 * !item.getValoreFinale().equalsIgnoreCase(N_A)) { Double val =
	 * NumberUtil.toNullableDoubleItalianFormat(item.getValoreFinale()); if ((val !=
	 * null && val <= 0) || !NumberUtil.isImporto(item.getValoreFinale(),
	 * Constants.MAX_CIFRE_INTERE)) {
	 * fieldErrors.put(valoreFinaleField.replaceAll("#", String.valueOf(i)),
	 * "Valore finale[" + i + "] - " + ErrorMessages.ERRORE_VALORE_NON_AMMESSO);
	 * isError = true; } else if (!NumberUtil.isDecimalValid(item.getValoreFinale(),
	 * MAX_SCALE_INDICATORI_MONIT)) { fieldErrors.put(
	 * valoreFinaleField.replaceAll("#", String.valueOf(i)), "Valore finale[" + i +
	 * "] - " + ErrorMessages.ERRORE_VALORE_NON_AMMESSO); isError = true; } }
	 * 
	 * if (!ObjectUtil.isEmpty(item.getValoreFinale()) &&
	 * ObjectUtil.isEmpty(item.getValoreIniziale())) {
	 * fieldErrors.put(valoreInizialeField.replaceAll("#", String.valueOf(i)),
	 * "Valore programmato[" + i + "] - " + ErrorMessages
	 * .ERRORE_CAMPO_OBBLIGATORIO);
	 * globalErrors.add(ErrorMessages.KEY_ERR_VALORE_INIZIALE_MANCANTE_MON); isError
	 * = true; item.setIsValoreInizialeEditable(true); }
	 * 
	 * // Jira PBANDI-2814: deve esserci un valore finale dove c'è un valore
	 * iniziale. if ((isBeneficiario && esisteDsFinale) || (!isBeneficiario &&
	 * esisteCFP)) { if (ObjectUtil.isEmpty(item.getValoreFinale()) &&
	 * !ObjectUtil.isEmpty(item.getValoreIniziale())) {
	 * fieldErrors.put(valoreFinaleField.replaceAll("#", String.valueOf(i)),
	 * "Valore finale[" + i + "] - " + ErrorMessages.ERRORE_CAMPO_OBBLIGATORIO);
	 * globalErrors.add("[" + i + "] - " +
	 * ErrorMessages.KEY_ERR_VALORE_FINALE_MANCANTE_MON); isError = true; } }
	 * 
	 * }
	 * 
	 * } i++; }
	 * 
	 * // Jira PBANDI-2814: deve esserci almeno un valore iniziale (colonna 'valore
	 * programmato') // per ogni tipo di indicatore, sempre per tutti i progetti
	 * (cioè con o senza una comunicazione di fine progetto). // Raggruppo gli
	 * indicatori per tipo Map<Long, List<IndicatoreItem>> tmp1 =
	 * this.trasformaIndicatoriPerVerifica(indicatori); for (Iterator<Long> iterator
	 * = tmp1.keySet().iterator(); iterator.hasNext();) { Long idTipo =
	 * iterator.next(); List<IndicatoreItem> listaPerTipo = tmp1.get(idTipo);
	 * 
	 * boolean tipoValido = false; for (IndicatoreItem item : listaPerTipo) { if
	 * (item.getValoreIniziale() !=null) { tipoValido = true; break; } }
	 * 
	 * if (!tipoValido) { this.settaErroreSuTipoMonitoraggio( idTipo,
	 * ErrorMessages.KEY_ERR_VALORE_INIZIALE_MON, indicatori);
	 * globalErrors.add(ErrorMessages.KEY_ERR_VALORE_INIZIALE_MON); isError = true;
	 * } } // Fine jira PBANDI-2814. // [DM] PBANDI-1605: algoritmo 2.6.2, il
	 * controllo va fatto solo in chiusura if (isChiusura) {
	 * 
	 * // Deve esserci almeno un valore finale per ogni tipo di indicatore.
	 * 
	 * // ora raggruppo gli indicatori per tipo Map<Long, List<IndicatoreItem>> tmp
	 * = this.trasformaIndicatoriPerVerifica(indicatori);
	 * 
	 * // PBANDI-2814: il controllo sul valore finale viene ora eseguito // solo se
	 * esiste una comunicazione di fine progetto. if ((isBeneficiario &&
	 * esisteDsFinale) || (!isBeneficiario && esisteCFP)) { // ciclo sui tipi
	 * indicatore for (Iterator<Long> iterator = tmp.keySet().iterator();
	 * iterator.hasNext();) { Long idTipo = iterator.next(); List<IndicatoreItem>
	 * listaPerTipo = tmp.get(idTipo);
	 * 
	 * boolean tipoValido = false; for (IndicatoreItem item : listaPerTipo) { if
	 * (item.getValoreFinale() != null) { if
	 * (!ObjectUtil.isEmpty(item.getValoreFinale())) { // qui posso permettermi di
	 * controllare solo il valore // finale perché il controllo fatto in precedenza
	 * // mi garantisce che se un valoreFinale è non nullo, il // corrispettivo
	 * valoreIniziale // è sicuramente non nullo tipoValido = true; break; } }
	 * 
	 * }
	 * 
	 * if (!tipoValido) { this.settaErroreSuTipoMonitoraggio(idTipo,
	 * ErrorMessages.KEY_ERR_VALORE_FINALE_MON, indicatori);
	 * globalErrors.add(ErrorMessages.KEY_ERR_VALORE_FINALE_MON); isError = true; }
	 * } } } // TODO : PK : fieldErrors e globalErrors su questo servizio servono
	 * ???? LOG.debug(prf + " fieldErrors = "+fieldErrors); LOG.debug(prf +
	 * " END, isError = "+isError); return isError; }
	 * 
	 * private boolean verificaAltriIndicatori(ArrayList<IndicatoreItem> indicatori,
	 * Set<String> globalErrors, Map<String, String> fieldErrors, boolean
	 * isChiusura) { String prf =
	 * "[IndicatoriServiceImpl::verificaAltriIndicatori]"; LOG.debug(prf +
	 * " START"); boolean isError = false; String valoreInizialeField =
	 * "appDataelencoAltriIndicatori[#].valoreIniziale"; String
	 * valoreAggiornamentoField =
	 * "appDataelencoAltriIndicatori[#].valoreAggiornamento"; String
	 * valoreFinaleField = "appDataelencoAltriIndicatori[#].valoreFinale";
	 * 
	 * int i = 0; for (IndicatoreItem item : indicatori) { if
	 * (!item.getIsTipoIndicatore()) { //&&
	 * !ObjectUtil.isTrue(item.getFlagNonApplicabile())) {
	 * 
	 * if (ObjectUtil.isTrue(item.getFlagNonApplicabile())) {
	 * item.setValoreIniziale(N_A); item.setValoreAggiornamento(N_A);
	 * item.setValoreFinale(N_A); item.setIsValoreInizialeEditable(false);
	 * item.setIsValoreAggiornamentoEditable(false);
	 * item.setIsValoreFinaleEditable(false); } else { // PBANDI-1573: verifico che
	 * il valore degli indicatori sia strettamente maggiore di zero // PBANDI-1587:
	 * lunghezza indicatori if (!ObjectUtil.isEmpty(item.getValoreIniziale()) &&
	 * !item.getValoreIniziale().equals(N_A)) { if
	 * (!NumberUtil.isImportoNoDecimalDigitLimits(item.getValoreIniziale(),
	 * Constants.MAX_CIFRE_INTERE) ||
	 * NumberUtil.toNullableDoubleNoDecimalDigitLimits(item.getValoreIniziale()) <
	 * 0) { fieldErrors.put(valoreInizialeField.replaceAll("#", String.valueOf(i)),
	 * ErrorMessages.ERRORE_VALORE_NON_AMMESSO); isError = true;
	 * abilitaItemAltriIndicatori(item); } else if
	 * (!NumberUtil.isDecimalValid(item.getValoreIniziale(),
	 * Constants.MAX_SCALE_ALTRI_INDICATORI)) {
	 * fieldErrors.put(valoreInizialeField.replaceAll("#", String.valueOf(i)),
	 * ErrorMessages.ERRORE_VALORE_NON_AMMESSO); isError = true; //TODO commentare
	 * perchè abilitaItemAltriIndicatori(item); } }
	 * 
	 * if (!ObjectUtil.isEmpty(item.getValoreAggiornamento()) &&
	 * !item.getValoreAggiornamento().equals(N_A)) { if
	 * (!NumberUtil.isImportoNoDecimalDigitLimits(item.getValoreAggiornamento(),
	 * Constants.MAX_CIFRE_INTERE) ||
	 * NumberUtil.toNullableDoubleNoDecimalDigitLimits(item.getValoreAggiornamento()
	 * ) < 0) { fieldErrors.put(valoreAggiornamentoField.replaceAll("#",
	 * String.valueOf(i)), ErrorMessages.ERRORE_VALORE_NON_AMMESSO); isError = true;
	 * abilitaItemAltriIndicatori(item); } else if
	 * (!NumberUtil.isDecimalValid(item.getValoreAggiornamento(),
	 * Constants.MAX_SCALE_ALTRI_INDICATORI)) {
	 * fieldErrors.put(valoreAggiornamentoField.replaceAll("#", String.valueOf(i)),
	 * ErrorMessages.ERRORE_VALORE_NON_AMMESSO); isError = true;
	 * abilitaItemAltriIndicatori(item); }
	 * 
	 * }
	 * 
	 * if (!ObjectUtil.isEmpty(item.getValoreFinale()) &&
	 * !item.getValoreFinale().equals(N_A)) { Double val =
	 * NumberUtil.toNullableDoubleNoDecimalDigitLimits(item.getValoreFinale()); if
	 * (!NumberUtil.isImportoNoDecimalDigitLimits(item.getValoreFinale(),
	 * Constants.MAX_CIFRE_INTERE) || (val != null && val < 0)) {
	 * fieldErrors.put(valoreFinaleField.replaceAll("#", String.valueOf(i)),
	 * ErrorMessages.ERRORE_VALORE_NON_AMMESSO); isError = true;
	 * abilitaItemAltriIndicatori(item); } else if
	 * (!NumberUtil.isDecimalValid(item.getValoreFinale(),
	 * Constants.MAX_SCALE_ALTRI_INDICATORI)) { fieldErrors.put(
	 * valoreFinaleField.replaceAll("#", String.valueOf(i)),
	 * ErrorMessages.ERRORE_VALORE_NON_AMMESSO); isError = true;
	 * abilitaItemAltriIndicatori(item); }
	 * 
	 * }
	 * 
	 * if (!ObjectUtil.isEmpty(item.getValoreFinale()) &&
	 * ObjectUtil.isEmpty(item.getValoreIniziale())) { fieldErrors.put(
	 * valoreInizialeField.replaceAll("#", String.valueOf(i)),
	 * ErrorMessages.ERRORE_CAMPO_OBBLIGATORIO);
	 * globalErrors.add(ErrorMessages.KEY_ERR_VALORE_INIZIALE_MANCANTE_MON); isError
	 * = true; // TNT item.setIsValoreAggiornamentoEditable(true);
	 * item.setIsValoreFinaleEditable(true); item.setIsValoreInizialeEditable(true);
	 * }
	 * 
	 * // [DM] PBANDI-1605: algoritmo 2.6.2, il controllo va fatto solo // in
	 * chiusura if (isChiusura) { if (item.isFlagObbligatorio()) { // VN. Fix
	 * PBandi-1801 if (ObjectUtil.isEmpty(item.getValoreFinale())) {
	 * fieldErrors.put(valoreFinaleField.replaceAll("#", String.valueOf(i)),
	 * ErrorMessages.ERRORE_CAMPO_OBBLIGATORIO); isError = true;
	 * abilitaItemAltriIndicatori(item);
	 * 
	 * } if (ObjectUtil.isEmpty(item.getValoreIniziale())) { fieldErrors.put(
	 * valoreInizialeField.replaceAll( "#", String.valueOf(i)),
	 * ErrorMessages.ERRORE_CAMPO_OBBLIGATORIO); isError = true;
	 * abilitaItemAltriIndicatori(item); } } } } } i++; } LOG.debug(prf + " END");
	 * return isError; }
	 */

	private void settaErroreSuTipoMonitoraggio(Long idTipo, String key, ArrayList<IndicatoreItem> indicatori) {
		for (IndicatoreItem item : indicatori) {
			if (item.getIsTipoIndicatore() && item.getIdTipoIndicatore().equals(idTipo)) {
				item.setCodiceErrore(key);
			}
		}

	}

	private Map<Long, List<IndicatoreItem>> trasformaIndicatoriPerVerifica(ArrayList<IndicatoreItem> indicatori) {
		Map<Long, List<IndicatoreItem>> res = new LinkedHashMap<Long, List<IndicatoreItem>>();

		for (IndicatoreItem item : indicatori) {
			if (!item.getIsTipoIndicatore()) {
				if (res.containsKey(item.getIdTipoIndicatore())) {
					item.setCodiceErrore(null);
					res.get(item.getIdTipoIndicatore()).add(item);
				} else {
					List<IndicatoreItem> tmpList = new ArrayList<IndicatoreItem>();
					tmpList.add(item);
					res.put(item.getIdTipoIndicatore(), tmpList);
				}
			}
		}

		return res;
	}

	private boolean isBeneficiario(UserInfoSec userInfo) {
		String prf = "[IndicatoriServiceImpl::isBeneficiario]";
		LOG.info(prf + " START");
		String ruolo = userInfo.getCodiceRuolo();
		String ruoloBenMaster = Constants.RUOLO_BEN_MASTER;
		String ruoloBen = Constants.RUOLO_PERSONA_FISICA;
		boolean result = (ruoloBen.equalsIgnoreCase(ruolo) || ruoloBenMaster.equalsIgnoreCase(ruolo));
		LOG.info("isBeneficiario(): ruolo = " + ruolo + "; result = " + result);
		LOG.info(prf + " END");
		return result;
	}

	private void resettaErroriSuTipiMonitoraggio(ArrayList<IndicatoreItem> indicatori) {
		for (IndicatoreItem item : indicatori) {
			if (item.getIsTipoIndicatore()) {
				item.setCodiceErrore(null);
			}
		}

	}

	private void abilitaItemAltriIndicatori(IndicatoreItem item) {
		String prf = "[IndicatoriServiceImpl::abilitaItemAltriIndicatori]";
		LOG.debug(prf + " START");
		boolean isRettifica = false;
		item.setIsValoreAggiornamentoEditable(true);
		item.setIsValoreFinaleEditable(true);
		if (isRettifica)
			item.setIsValoreInizialeEditable(true);
		LOG.debug(prf + " END");

	}

	/***************************************************************
	 * VALIDAZIONI DATI AVVIO
	 ***************************************************************/
	@Override
	public Response controllaDatiPerSalvataggioAvvio(HttpServletRequest req,
			ValidazioneDatiIndicatoriRequest validazioneRequest) throws UtenteException, Exception {
		String prf = "[IndicatoriServiceImpl::controllaDatiPerSalvataggioAvvio]";
		LOG.debug(prf + " BEGIN");

		try {
			HttpSession session = req.getSession();
			UserInfoSec userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
			Long idUtente = userInfo.getIdUtente();
			String idIride = userInfo.getIdIride();
			LOG.info(prf + ": idUtente = " + idUtente + " idIride = " + idIride);

			ExecResults result = new ExecResults();
			Set<String> globalErrors = new HashSet<String>();
			Set<String> globalMessages = new HashSet<String>();
			Map<String, String> fieldErrors = new HashMap<String, String>();

			// verifico indicatori monitoraggio
			boolean isErrorMonitoraggio = verificaIndicatoriMonitoraggioAvvio(validazioneRequest.getIndicatori(),
					globalErrors, fieldErrors, false, validazioneRequest.getIdProgetto(), userInfo);

			// verifico altri indicatori
			boolean isErrorAltri = verificaAltriIndicatoriAvvio(validazioneRequest.getAltriIndicatori(), globalErrors,
					fieldErrors, false);

			if (isErrorMonitoraggio || isErrorAltri) {
				result.setGlobalErrors(globalErrors);
				result.setFldErrors(fieldErrors);
			} else {
				globalMessages.add(MessageConstants.CONFERMA_SALVATAGGIO1);
				result.setGlobalMessages(globalMessages);
			}
			LOG.debug(prf + " END");
			return Response.ok().entity(result).build();
		} catch (Exception e) {
			throw e;
		}
	}

	private boolean verificaAltriIndicatoriAvvio(ArrayList<IndicatoreItem> altriIndicatori, Set<String> globalErrors,
			Map<String, String> fieldErrors, boolean b) {
		String prf = "[IndicatoriServiceImpl::verificaAltriIndicatoriAvvio]";
		LOG.debug(prf + " START");
		boolean isError = false;
		String valoreInizialeField = "appDataelencoAltriIndicatori[#].valoreIniziale";

		int i = 0;
		for (IndicatoreItem item : altriIndicatori) {
			if (!item.getIsTipoIndicatore()) {
				if (ObjectUtil.isTrue(item.getFlagNonApplicabile())) {
					// FIXEM modificare item type
					item.setValoreIniziale(N_A);
					item.setValoreFinale(N_A);
					item.setValoreAggiornamento(N_A);
					item.setIsValoreAggiornamentoEditable(false);
					item.setIsValoreInizialeEditable(false);
					item.setIsValoreFinaleEditable(false);
				} else {
					if (item.isFlagObbligatorio()) {
						if (ObjectUtil.isEmpty(item.getValoreIniziale())) {
							fieldErrors.put(valoreInizialeField.replaceAll("#", String.valueOf(i)),
									"[" + i + "] - " + ErrorMessages.ERRORE_CAMPO_OBBLIGATORIO);
							item.setIsValoreInizialeEditable(true);
							isError = true;
						}
					}
					// PBANDI-1573: verifico che il valore degli indicatori sia
					// strettamente maggiore di zero
					// PBANDI-1587: lunghezza indicatori
					if (!ObjectUtil.isEmpty(item.getValoreIniziale())
							&& !item.getValoreIniziale().equalsIgnoreCase(N_A)) {
						if (NumberUtil.toNullableDoubleNoDecimalDigitLimits(item.getValoreIniziale()) < 0 || !NumberUtil
								.isImportoNoDecimalDigitLimits(item.getValoreIniziale(), Constants.MAX_CIFRE_INTERE)) {
							fieldErrors.put(valoreInizialeField.replaceAll("#", String.valueOf(i)),
									"[" + i + "] - " + ErrorMessages.ERRORE_VALORE_NON_AMMESSO);
							item.setIsValoreInizialeEditable(true);
							isError = true;
						} else if (!NumberUtil.isDecimalValid(item.getValoreIniziale(),
								Constants.MAX_SCALE_ALTRI_INDICATORI)) {
							fieldErrors.put(valoreInizialeField.replaceAll("#", String.valueOf(i)),
									"[" + i + "] - " + ErrorMessages.ERRORE_VALORE_NON_AMMESSO);
							isError = true;
						}
					}
				}
			}
			i++;
		}
		LOG.debug(prf + " END");
		return isError;
	}

	private boolean verificaIndicatoriMonitoraggioAvvio(ArrayList<IndicatoreItem> indicatori, Set<String> globalErrors,
			Map<String, String> fieldErrors, boolean isChiusura, Long idProgetto, UserInfoSec userInfo)
			throws FormalParameterException, UnrecoverableException {
		String prf = "[IndicatoriServiceImpl::verificaIndicatoriMonitoraggioAvvio]";
		LOG.info(prf + " START");
		boolean isError = false;
		// resetto eventuali messaggi di errore precedenti sui tipi indicatori
		resettaErroriSuTipiMonitoraggio(indicatori);
		// PBANDI-1573: verifico che il valore degli indicatori sia strettamente
		// maggiore di zero
		// PBANDI-1587: lunghezza indicatori
		String valoreInizialeField = "appDataelencoIndicatoriMonitoraggio[#].valoreIniziale";
		int i = 0;
		for (IndicatoreItem item : indicatori) {
			if (!item.getIsTipoIndicatore()) {
				if (item.getValoreIniziale() != null) {
					LOG.info(prf + " ValoreIniziale = "
							+ NumberUtil.toNullableDoubleItalianFormat(item.getValoreIniziale()));
					if (NumberUtil.toNullableDoubleItalianFormat(item.getValoreIniziale()) <= 0
							|| !NumberUtil.isImporto(item.getValoreIniziale(), Constants.MAX_CIFRE_INTERE)) {
						fieldErrors.put(valoreInizialeField.replaceAll("#", String.valueOf(i)),
								"Valore programmato[" + i + "] - " + ErrorMessages.ERRORE_VALORE_NON_AMMESSO);
						isError = true;
					} else if (!NumberUtil.isDecimalValid(item.getValoreIniziale(), MAX_SCALE_INDICATORI_MONIT)) {
						fieldErrors.put(valoreInizialeField.replaceAll("#", String.valueOf(i)),
								"Valore programmato[" + i + "] - " + ErrorMessages.ERRORE_VALORE_NON_AMMESSO);
						isError = true;
					}

				}
			}
			i++;
		}

		// raggruppo gli indicatori per tipo
		Map<Long, List<IndicatoreItem>> tmp = trasformaIndicatoriPerVerifica(indicatori);

		// ciclo sui tipi indicatore
		for (Iterator<Long> iterator = tmp.keySet().iterator(); iterator.hasNext();) {
			Long idTipo = iterator.next();
			List<IndicatoreItem> listaPerTipo = tmp.get(idTipo);

			boolean tipoValido = false;
			for (IndicatoreItem item : listaPerTipo) {
				if (item.getValoreIniziale() != null) {
					tipoValido = true;
					break;
				}
			}

			if (!tipoValido) {
				settaErroreSuTipoMonitoraggio(idTipo, ErrorMessages.KEY_ERR_VALORE_INIZIALE_MON, indicatori);
				globalErrors.add(ErrorMessages.KEY_ERR_VALORE_INIZIALE_MON);
				isError = true;
			}
		}

		return isError;
	}

	/***************************************************************
	 * SALVATAGGIO
	 ***************************************************************/
	@Override
	public Response saveIndicatoriGestione(HttpServletRequest req, SalvaIndicatoriRequest salvaRequest)
			throws UtenteException, Exception {
		String prf = "[IndicatoriServiceImpl::saveIndicatoriGestione]";
		LOG.debug(prf + " BEGIN");
		EsitoSaveIndicatori esito = new EsitoSaveIndicatori();
		try {
			ArrayList<IndicatoreItem> indicatoriMonitoraggio = salvaRequest.getIndicatoriMonitoraggio();
			ArrayList<IndicatoreItem> altriIndicatori = salvaRequest.getAltriIndicatori();
			Long idProgetto = salvaRequest.getIdProgetto();
			TipoIndicatoreDTO[] listaIndicatori = unificaListeIndicatori(indicatoriMonitoraggio, altriIndicatori,
					false);

			HttpSession session = req.getSession();
			UserInfoSec userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
			Long idUtente = userInfo.getIdUtente();
			String idIride = userInfo.getIdIride();

			esito = indicatoriDAO.saveIndicatoriGestione(idUtente, idIride, idProgetto, listaIndicatori);

			LOG.debug(prf + " END");
			return Response.ok().entity(esito).build();
		} catch (Exception e) {
			throw e;
		}

	}

	private TipoIndicatoreDTO[] unificaListeIndicatori(ArrayList<IndicatoreItem> indicatoriMonitoraggio,
			ArrayList<IndicatoreItem> altriIndicatori, boolean isAvvio) {
		ArrayList<IndicatoreItem> listaIndicatori = new ArrayList<IndicatoreItem>();
		listaIndicatori.addAll(indicatoriMonitoraggio);
		listaIndicatori.addAll(altriIndicatori);
		return trasformaIndicatoriPerSalvataggio(listaIndicatori, isAvvio);
	}

	private TipoIndicatoreDTO[] trasformaIndicatoriPerSalvataggio(ArrayList<IndicatoreItem> indicatori,
			boolean isAvvio) {
		List<TipoIndicatoreDTO> res = new ArrayList<TipoIndicatoreDTO>();

		// raggruppo gli indicatori per tipo
		Map<Long, List<IndicatoreItem>> mappaPerTipo = trasformaIndicatoriPerVerifica(indicatori);

		// ciclo sui tipi indicatore
		for (Iterator<Long> iterator = mappaPerTipo.keySet().iterator(); iterator.hasNext();) {
			Long idTipo = iterator.next();
			TipoIndicatoreDTO tipo = new TipoIndicatoreDTO();
			tipo.setIdTipoIndicatore(idTipo);

			List<IndicatoreDTO> listaDto = new ArrayList<IndicatoreDTO>();
			for (IndicatoreItem indicatore : mappaPerTipo.get(idTipo)) {

				if (indicatore.getValoreIniziale() != null || indicatore.getValoreFinale() != null
						|| indicatore.getValoreAggiornamento() != null) {
					IndicatoreDTO dto = new IndicatoreDTO();

					dto.setIdIndicatore(indicatore.getIdIndicatore());
					dto.setCodIgrue(indicatore.getCodIgrue());

					dto.setFlagNonApplicabile(indicatore.getFlagNonApplicabile());
					if (ObjectUtil.isTrue(indicatore.getFlagNonApplicabile())) {
						dto.setValoreIniziale(null);
						dto.setValoreFinale(null);
						dto.setValoreAggiornamento(null);
					} else {
						if (indicatore.getValoreIniziale() != null) {
							dto.setValoreIniziale(
									NumberUtil.toNullableDoubleItalianFormat(indicatore.getValoreIniziale()));
						}

						// dto.setValoreIniziale(indicatore.getValoreInizia.le());
						if (!isAvvio) {
							// dto.setValoreAggiornamento(indicatore.getValoreAggiornamento());
							// dto.setValoreFinale(indicatore.getValoreFinale());
							if (indicatore.getValoreAggiornamento() != null) {
								dto.setValoreAggiornamento(
										NumberUtil.toNullableDoubleItalianFormat(indicatore.getValoreAggiornamento()));
							}
							if (indicatore.getValoreFinale() != null) {
								dto.setValoreFinale(
										NumberUtil.toNullableDoubleItalianFormat(indicatore.getValoreFinale()));
							}

						}
					}
					listaDto.add(dto);
				}
			}

			if (!listaDto.isEmpty()) {
				tipo.setIndicatori(listaDto.toArray(new IndicatoreDTO[] {}));
				res.add(tipo);
			}

		}

		return res.toArray(new TipoIndicatoreDTO[] {});
	}

	@Override
	public Response saveIndicatoriAvvio(HttpServletRequest req, SalvaIndicatoriRequest salvaRequest)
			throws UtenteException, Exception {
		String prf = "[IndicatoriServiceImpl::saveIndicatoriAvvio]";
		LOG.debug(prf + " BEGIN");
		EsitoSaveIndicatori esito = new EsitoSaveIndicatori();
		try {
			ArrayList<IndicatoreItem> indicatoriMonitoraggio = salvaRequest.getIndicatoriMonitoraggio();
			ArrayList<IndicatoreItem> altriIndicatori = salvaRequest.getAltriIndicatori();
			Long idProgetto = salvaRequest.getIdProgetto();
			TipoIndicatoreDTO[] listaIndicatori = unificaListeIndicatori(indicatoriMonitoraggio, altriIndicatori,
					false);

			HttpSession session = req.getSession();
			UserInfoSec userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
			Long idUtente = userInfo.getIdUtente();
			String idIride = userInfo.getIdIride();

			// esito = indicatoriDAO.deleteDomandaIndicatori(idUtente, idIride, idProgetto,
			// listaIndicatori);
//			if(!esito.getSuccesso()) {
//				return inviaErroreBadRequest("Errore nel salvataggio.");
//			}
			esito = indicatoriDAO.saveIndicatoriAvvio(idUtente, idIride, idProgetto, listaIndicatori);

			LOG.debug(prf + " END");
			return Response.ok().entity(esito).build();
		} catch (Exception e) {
			throw e;
		}
	}

	private Double toNullableDoubleItalianFormat(String valore) {
		Double ret = null;

		if (valore != null) {
			BigDecimal bigdecimal = null;
			try {
				valore = valore.replace(".", "");
				valore = valore.replace(",", ".");
				bigdecimal = new BigDecimal(valore);
				ret = bigdecimal.doubleValue();
			} catch (Exception x) {
				return null;
			}
		}
		return ret;
	}

	private Response inviaErroreBadRequest(String msg) {
		EsitoOperazioni esito = new EsitoOperazioni();
		esito.setEsito(false);
		esito.setMsg(msg);
		return Response.status(Response.Status.BAD_REQUEST).entity(esito).type(MediaType.APPLICATION_JSON).build();
	}

	private Response inviaErroreInternalServer(String msg) {
		EsitoOperazioni esito = new EsitoOperazioni();
		esito.setEsito(false);
		esito.setMsg(msg);
		return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(esito).type(MediaType.APPLICATION_JSON)
				.build();
	}

}
