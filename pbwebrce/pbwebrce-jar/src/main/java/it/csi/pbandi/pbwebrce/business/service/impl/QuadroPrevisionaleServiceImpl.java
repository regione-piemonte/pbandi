/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebrce.business.service.impl;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.csi.pbandi.pbservizit.exception.UtenteException;
import it.csi.pbandi.pbservizit.security.UserInfoSec;
import it.csi.pbandi.pbwebrce.business.service.QuadroPrevisionaleService;
import it.csi.pbandi.pbwebrce.dto.EsitoOperazioni;
import it.csi.pbandi.pbwebrce.dto.ExecResults;
import it.csi.pbandi.pbwebrce.dto.quadroprevisionale.EsitoFindQuadroPrevisionaleDTO;
import it.csi.pbandi.pbwebrce.dto.quadroprevisionale.EsitoSalvaQuadroPrevisionaleDTO;
import it.csi.pbandi.pbwebrce.dto.quadroprevisionale.QuadroPrevisionale;
import it.csi.pbandi.pbwebrce.dto.quadroprevisionale.QuadroPrevisionaleComplessivoDTO;
import it.csi.pbandi.pbwebrce.dto.quadroprevisionale.QuadroPrevisionaleComplessivoItem;
import it.csi.pbandi.pbwebrce.dto.quadroprevisionale.QuadroPrevisionaleDTO;
import it.csi.pbandi.pbwebrce.dto.quadroprevisionale.QuadroPrevisionaleItem;
import it.csi.pbandi.pbwebrce.dto.quadroprevisionale.QuadroPrevisionaleModel;
import it.csi.pbandi.pbwebrce.dto.quadroprevisionale.ResponseGetQuadroPrevisionale;
import it.csi.pbandi.pbwebrce.integration.dao.AffidamentiDAO;
import it.csi.pbandi.pbwebrce.integration.dao.QuadroPrevisionaleDAO;
import it.csi.pbandi.pbwebrce.integration.request.ValidazioneDatiQuadroProvisionaleRequest;
import it.csi.pbandi.pbwebrce.pbandiutil.common.ObjectUtil;
import it.csi.pbandi.pbwebrce.util.BeanUtil;
import it.csi.pbandi.pbwebrce.util.Constants;
import it.csi.pbandi.pbwebrce.util.DateUtil;
import it.csi.pbandi.pbwebrce.util.MessageConstants;
import it.csi.pbandi.pbwebrce.util.NumberUtil;

@Service
public class QuadroPrevisionaleServiceImpl implements QuadroPrevisionaleService{
	
	@Autowired
	protected BeanUtil beanUtil;
	
	private Logger LOG = Logger.getLogger(Constants.COMPONENT_NAME);

	@Autowired
	QuadroPrevisionaleDAO quadroPrevisionaleDAO;
	
	@Autowired
	AffidamentiDAO affidamentiDAO;
	
	@Override
	public Response getCodiceProgetto(HttpServletRequest req, Long idProgetto) throws UtenteException, Exception {
		String prf = "[QuadroPrevisionaleServiceImpl::getCodiceProgetto]";
		LOG.debug(prf + " BEGIN");
		try {
			if(idProgetto == null) { return inviaErroreBadRequest("Parametro mancato: ?[idProgetto]"); }
			String codiceProgetto = affidamentiDAO.findCodiceProgetto(idProgetto);

			LOG.debug(prf + " END");
			return Response.ok().entity(codiceProgetto).build();
		} catch(Exception e) {
			throw e;
		}
	}
	
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	
	///////////////////////////////////////// SERVIZIO GET QUADRO PREVISIONALE ////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	@Override
	public Response getQuadroPrevisionale(HttpServletRequest req, Long idProgetto) throws UtenteException, Exception {
		String prf = "[QuadroPrevisionaleServiceImpl::getQuadroPrevisionale]";
		LOG.info(prf + " BEGIN");
		
		try {
			HttpSession session = req.getSession();
			UserInfoSec userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
			Long idUtente = userInfo.getIdUtente();
			String idIride = userInfo.getIdIride();
			
			// 1) recupera i dati
			EsitoFindQuadroPrevisionaleDTO esitoFind  = quadroPrevisionaleDAO.findQuadroPrevisionale(idUtente, idIride, idProgetto);
			QuadroPrevisionaleDTO quadroPrevisionaleServer = esitoFind.getQuadroPrevisionale();
			QuadroPrevisionaleDTO[] vociServer = quadroPrevisionaleServer.getFigli();
			
			QuadroPrevisionale quadroPrevisionalePresentation = new QuadroPrevisionale();
			quadroPrevisionalePresentation.setIsVociVisibili(esitoFind.isVociVisibili());
			quadroPrevisionalePresentation.setIdQuadro(esitoFind.getQuadroPrevisionale().getIdQuadro());
			quadroPrevisionalePresentation.setControlloNuovoImportoBloccante(esitoFind.isControlloNuovoImportoBloccante());
			
			// 2) mappa voci
			ArrayList<QuadroPrevisionaleItem> vociWeb = mappaVociDiSpesaPerVisualizzazione(vociServer, vociServer, esitoFind.isVociVisibili());
			
			Double sommaRealizzato = new Double("0");
			Double sommaDaRealizze = new Double("0");
			Double sommaUltimoProventivo = new Double("0");
			for(QuadroPrevisionaleItem quadroPrevisionaleItem: vociWeb) {
				
				if(quadroPrevisionaleItem.getIsPeriodo()) {
					
					if(quadroPrevisionaleItem.getRealizzato() != null) {
						Double realizzato = NumberUtil.toNullableDoubleItalianFormat(quadroPrevisionaleItem.getRealizzato());
						sommaRealizzato = NumberUtil.sum(sommaRealizzato,realizzato);
					}
					
					if(quadroPrevisionaleItem.getDaRealizzare() != null) {
						Double daRealizze = NumberUtil.toNullableDoubleItalianFormat(quadroPrevisionaleItem.getDaRealizzare());
						sommaDaRealizze = NumberUtil.sum(sommaDaRealizze,daRealizze);
					}
					
					if(quadroPrevisionaleItem.getUltimoPreventivo() != null) {
						Double ultimoPrventivo = NumberUtil.toNullableDoubleItalianFormat(quadroPrevisionaleItem.getUltimoPreventivo());
						sommaUltimoProventivo = NumberUtil.sum(sommaUltimoProventivo,ultimoPrventivo);
					}
					
					
				}
				
			}
			
			LOG.info("sommaRealizzato = "+sommaRealizzato);
			LOG.info("sommaDaRealizze = "+sommaDaRealizze);
			LOG.info("sommaDaUltimoProventivo = "+sommaUltimoProventivo);
			
			QuadroPrevisionaleComplessivoDTO[] quadroComplessivoServer = esitoFind.getQuadroPrevisionaleComplessivo();
			ArrayList<QuadroPrevisionaleComplessivoItem> listQuadroComplessivo = mappaQuadroComplessivoPerVisualizzazione(quadroComplessivoServer, quadroComplessivoServer, esitoFind.isVociVisibili());
			
			// 3) aggiungi righe in visualizzazione
			aggiungiRigaDate(esitoFind.getDataUltimoPreventivo(), vociWeb);
			if (!esitoFind.isVociVisibili()) {
				vociWeb.add(1, getRigaTotaleVociNonVisibili(vociWeb));
			}
			aggiungiRigaDateQuadroComplessivo(esitoFind.getDataUltimaSpesaAmmessa(), esitoFind.getDataUltimoPreventivo(), listQuadroComplessivo);
			
			
			for(int i  = 0; i < vociWeb.size(); i++) {
				
				if(vociWeb.get(i).getIsRigaTotale()) {
					QuadroPrevisionaleItem item = new QuadroPrevisionaleItem();
					item.setHaFigli(false);
					item.setLabel("Totale");
					item.setModificabile(false);
					item.setIsRigaDate(false);
					item.setIsMacroVoce(false);
					item.setIsVociVisibili(false);
					item.setIsRigaTotale(true);
					item.setIsError(false);
					item.setRealizzato(NumberUtil.getStringValueItalianFormat(sommaRealizzato));
					item.setDaRealizzare(NumberUtil.getStringValueItalianFormat(sommaDaRealizze));
					item.setUltimoPreventivo(NumberUtil.getStringValueItalianFormat(sommaUltimoProventivo));
					vociWeb.set(i, item);
				}
				
			}
			
			
			// 4) mette i dati nel response
			ResponseGetQuadroPrevisionale response = new ResponseGetQuadroPrevisionale();
			response.setVociWeb(vociWeb);
			response.setQuadroPrevisionale(quadroPrevisionalePresentation);
			response.setQuadroComplessivoList(listQuadroComplessivo);
			response.setNote(quadroPrevisionaleServer.getNote());
			response.setIdProgetto(idProgetto);
			LOG.info(prf + " END");
			return Response.ok().entity(response).build();
		} catch(Exception e) {
			throw e;
		}
	}
	
	private ArrayList<QuadroPrevisionaleComplessivoItem> mappaQuadroComplessivoPerVisualizzazione(
			QuadroPrevisionaleComplessivoDTO[] quadroComplessivoServer,
			QuadroPrevisionaleComplessivoDTO[] oldQuadroComplessivoServer,
			boolean isVociVisibili) {
		String prf = "[QuadroPrevisionaleServiceImpl::mappaQuadroComplessivoPerVisualizzazione]";
		LOG.info(prf + " START");
		// serve per la prima riga in visualizzazione
		QuadroPrevisionaleComplessivoItem quadroComplessivoItemTotale = new QuadroPrevisionaleComplessivoItem();
		quadroComplessivoItemTotale.setIsTotaleComplessivo(true);
		quadroComplessivoItemTotale.setIsVociVisibili(isVociVisibili);
		quadroComplessivoItemTotale.setLabel("Totale complessivo");

		Map<String, String> mapPropsToCopy = new HashMap<String, String>();
		mapPropsToCopy.put("descVoce", "label");
		mapPropsToCopy.put("idVoce", "idVoce");
		mapPropsToCopy.put("idVocePadre", "idPadre");
		mapPropsToCopy.put("preventivo", "nuovoPreventivo");
		mapPropsToCopy.put("spesaAmmessa", "ultimaSpesaAmmessa");
		mapPropsToCopy.put("realizzato", "realizzato");
		mapPropsToCopy.put("daRealizzare", "daRealizzare");
		mapPropsToCopy.put("macroVoce", "isMacroVoce");

		QuadroPrevisionaleComplessivoItem[] quadroComplessivoItem = new QuadroPrevisionaleComplessivoItem[quadroComplessivoServer.length];
		for (int i = 0; i < quadroComplessivoServer.length; i++) {
			quadroComplessivoItem[i] = new QuadroPrevisionaleComplessivoItem();
			beanUtil.valueCopy(quadroComplessivoServer[i], quadroComplessivoItem[i], mapPropsToCopy);
			// TNT MODIFICA
			quadroComplessivoItem[i].setUltimoPreventivo(NumberUtil.getStringValueItalianFormat(oldQuadroComplessivoServer[i].getPreventivo()));
			quadroComplessivoItem[i].setHaFigli(quadroComplessivoServer[i].getHaFigli());
			quadroComplessivoItem[i].setIsVociVisibili(isVociVisibili);
			if (quadroComplessivoServer[i].getMacroVoce()) {
				Double newRealizzato = quadroComplessivoServer[i].getRealizzato();
				Double newPreventivo = quadroComplessivoServer[i].getPreventivo();
				Double oldPreventivo = oldQuadroComplessivoServer[i].getPreventivo();
				Double newSpesaAmmessa = quadroComplessivoServer[i].getSpesaAmmessa();
				Double newDaRealizzare = quadroComplessivoServer[i].getDaRealizzare();
				Double realizzato = NumberUtil.toNullableDoubleItalianFormat(quadroComplessivoItemTotale.getRealizzato());
				Double nPreventivo = NumberUtil.toNullableDoubleItalianFormat(quadroComplessivoItemTotale.getNuovoPreventivo());
				Double oPreventivo = NumberUtil.toNullableDoubleItalianFormat(quadroComplessivoItemTotale.getUltimoPreventivo());
				Double spesaAmmessa = NumberUtil.toNullableDoubleItalianFormat(quadroComplessivoItemTotale.getUltimaSpesaAmmessa());
				Double daRealizzare = NumberUtil.toNullableDoubleItalianFormat(quadroComplessivoItemTotale.getDaRealizzare());
				if (newRealizzato == null) newRealizzato = 0d;
				if (newPreventivo == null) newPreventivo = 0d;
				if (oldPreventivo == null) oldPreventivo = 0d;
				if (newSpesaAmmessa == null) newSpesaAmmessa = 0d;
				if (newDaRealizzare == null) newDaRealizzare = 0d;
				if (realizzato == null) realizzato = 0d;
				if (nPreventivo == null) nPreventivo = 0d;
				if (oPreventivo == null) oPreventivo = 0d;
				if (spesaAmmessa == null) spesaAmmessa = 0d;
				if (daRealizzare == null) daRealizzare = 0d;

				realizzato = NumberUtil.sum(realizzato, newRealizzato);
				nPreventivo = NumberUtil.sum(nPreventivo, newPreventivo);
				oPreventivo = NumberUtil.sum(oPreventivo, oldPreventivo);
				spesaAmmessa = NumberUtil.sum(spesaAmmessa, newSpesaAmmessa);
				daRealizzare = NumberUtil.sum(daRealizzare, newDaRealizzare);

				quadroComplessivoItemTotale.setRealizzato(NumberUtil.getStringValueItalianFormat(realizzato));
				quadroComplessivoItemTotale.setUltimaSpesaAmmessa(NumberUtil.getStringValueItalianFormat(spesaAmmessa));
				quadroComplessivoItemTotale.setUltimoPreventivo(NumberUtil.getStringValueItalianFormat(oPreventivo));
				quadroComplessivoItemTotale.setNuovoPreventivo(NumberUtil.getStringValueItalianFormat(nPreventivo));
				quadroComplessivoItemTotale.setDaRealizzare(NumberUtil.getStringValueItalianFormat(daRealizzare));
			}
		}
		ArrayList<QuadroPrevisionaleComplessivoItem> list = new ArrayList<QuadroPrevisionaleComplessivoItem>();
		if (isVociVisibili) {
			list = new ArrayList<QuadroPrevisionaleComplessivoItem>(Arrays.asList(quadroComplessivoItem));
		}
		list.add(0, quadroComplessivoItemTotale);
		
		LOG.info(prf + " END");
		return list;
	}
	
	private ArrayList<QuadroPrevisionaleItem> mappaVociDiSpesaPerVisualizzazione(
			QuadroPrevisionaleDTO[] vociDB, QuadroPrevisionaleDTO[] oldVociDB, boolean vociVisibili) {
		String prf = "[QuadroPrevisionaleServiceImpl::mappaVociDiSpesaPerVisualizzazione]";
		LOG.info(prf + " START");
		ArrayList<QuadroPrevisionaleItem> vociWeb = new ArrayList<QuadroPrevisionaleItem>();
		ArrayList<QuadroPrevisionaleItem> allVociWeb = new ArrayList<QuadroPrevisionaleItem>();
		Map<String, String> mapPropsToCopy = new HashMap<String, String>();

		mapPropsToCopy.put("descVoce", "label"); mapPropsToCopy.put("idVoce", "idVoce");
		mapPropsToCopy.put("idVocePadre", "idPadre"); mapPropsToCopy.put("idRigoQuadro", "idRigoQuadro");
		mapPropsToCopy.put("importoRealizzato", "realizzato"); mapPropsToCopy.put("importoPreventivo", "nuovoPreventivo");
		mapPropsToCopy.put("importoDaRealizzare", "daRealizzare"); mapPropsToCopy.put("importoSpesaAmmessa", "ultimaSpesaAmmessa");
		mapPropsToCopy.put("macroVoce", "isMacroVoce"); mapPropsToCopy.put("periodo", "periodo");

		for (QuadroPrevisionaleDTO voceDB : vociDB) {
			QuadroPrevisionaleItem voceWeb = new QuadroPrevisionaleItem();
			voceWeb.setHaFigli(voceDB.getHaFigli());
			beanUtil.valueCopy(voceDB, voceWeb, mapPropsToCopy);
			voceWeb.setUltimaSpesaAmmessa(NumberUtil.getStringValueItalianFormat(voceDB.getImportoSpesaAmmessa()));
			
			voceWeb.setIdVoce(null);
			//new
			voceWeb.setRealizzato( NumberUtil.getStringValueItalianFormat(voceDB.getImportoRealizzato()) );
			voceWeb.setDaRealizzare( NumberUtil.getStringValueItalianFormat(voceDB.getImportoDaRealizzare()) );
			//voceWeb.setNuovoPreventivo( NumberUtil.getStringValueItalianFormat(voceDB.get) );
			//voceWeb.setNuovoPreventivo( NumberUtil.getStringValueItalianFormat(NumberUtil.toNullableDouble(voceWeb.getNuovoPreventivo()) ) );
			voceWeb.setIsVociVisibili(vociVisibili);
			if (voceDB.getIsPeriodo()) {
				voceWeb.setIsPeriodo(true);// bug del beanutil: devo
				// fare il ciclo e il set a mano sul boolean
				voceWeb.setPeriodo(voceWeb.getLabel());
				if (vociVisibili) voceWeb.setModificabile(false);
				else voceWeb.setModificabile(true);
			} else if (voceDB.getMacroVoce()) {
				voceWeb.setIsMacroVoce(true);
				if (voceDB.getHaFigli()) voceWeb.setModificabile(false);
				else voceWeb.setModificabile(true);
			} else
				voceWeb.setModificabile(true);

			if (voceWeb.getUltimoPreventivo() == null) voceWeb.setUltimoPreventivo("");
			if (vociVisibili || voceDB.getIsPeriodo()) vociWeb.add(voceWeb);
			allVociWeb.add(voceWeb);

		}
		int i = 0;
		for (QuadroPrevisionaleItem voceWeb : allVociWeb) {
			voceWeb.setUltimoPreventivo(NumberUtil.getStringValueItalianFormat(oldVociDB[i++].getImportoPreventivo()));
		}
//		theModel.setAppDatavociNonVisibili(allVociWeb);
		LOG.info(prf + " END");
		return allVociWeb;
	}

	private void aggiungiRigaDate(Date dataUltimoPreventivo,
			ArrayList<QuadroPrevisionaleItem> figli) {
		String prf = "[QuadroPrevisionaleServiceImpl::aggiungiRigaDate]";
		LOG.info(prf + " START");
		QuadroPrevisionaleItem rigaDate = new QuadroPrevisionaleItem();
		if (dataUltimoPreventivo != null)
			rigaDate.setDataUltimaPreventivo(new SimpleDateFormat("dd/MM/yyyy").format(dataUltimoPreventivo));
		else
			rigaDate.setDataUltimaPreventivo("");
		rigaDate.setIsRigaDate(true);
		rigaDate.setModificabile(false);
		figli.add(0, rigaDate);
		LOG.info(prf + " END");
	}

	private void aggiungiRigaDateQuadroComplessivo(Date ultimaSpesaAmmessa, Date ultimoPreventivo, ArrayList<QuadroPrevisionaleComplessivoItem> figli) {
		String prf = "[QuadroPrevisionaleServiceImpl::aggiungiRigaDateQuadroComplessivo]";
		LOG.info(prf + " START");
		QuadroPrevisionaleComplessivoItem rigaDate = new QuadroPrevisionaleComplessivoItem();
		if (ultimaSpesaAmmessa != null)
			rigaDate.setDataUltimaSpesaAmmessa(new SimpleDateFormat("dd/MM/yyyy").format(ultimaSpesaAmmessa));
		else
			rigaDate.setDataUltimaSpesaAmmessa("");

		if (ultimoPreventivo != null)
			rigaDate.setDataUltimaPreventivo(new SimpleDateFormat("dd/MM/yyyy").format(ultimoPreventivo));
		else
			rigaDate.setDataUltimaPreventivo("");

		rigaDate.setIsRigaDate(true);
		figli.add(0, rigaDate);
		LOG.info(prf + " END");
	}
	
	private QuadroPrevisionaleItem getRigaTotaleVociNonVisibili(ArrayList<QuadroPrevisionaleItem> vociPeriodi) {
		String prf = "[QuadroPrevisionaleServiceImpl::getRigaTotaleVociNonVisibili]";
		LOG.info(prf + " START");
		QuadroPrevisionaleItem rigaTotale = new QuadroPrevisionaleItem();
		Double daRealizzare= 0d, nuovoPreventivo= 0d, realizzato= 0d,  ultimoPreventivo= 0d;
		for (QuadroPrevisionaleItem quadroPrevisionaleItem : vociPeriodi) {
			if (quadroPrevisionaleItem.getDaRealizzare() != null)
				daRealizzare = NumberUtil.sum(daRealizzare, NumberUtil.toNullableDoubleItalianFormat(quadroPrevisionaleItem.getDaRealizzare()));
			if (quadroPrevisionaleItem.getNuovoPreventivo() != null)
				nuovoPreventivo = NumberUtil.sum(nuovoPreventivo, NumberUtil.toNullableDoubleItalianFormat(quadroPrevisionaleItem.getNuovoPreventivo()));
			if (quadroPrevisionaleItem.getRealizzato() != null)
				realizzato = NumberUtil.sum(realizzato, NumberUtil.toNullableDoubleItalianFormat(quadroPrevisionaleItem.getRealizzato()));
			if (quadroPrevisionaleItem.getUltimoPreventivo() != null)
				ultimoPreventivo = NumberUtil.sum(ultimoPreventivo, NumberUtil.toNullableDoubleItalianFormat(quadroPrevisionaleItem.getUltimoPreventivo()));
		}
		rigaTotale.setDaRealizzare(NumberUtil.getStringValueItalianFormat(daRealizzare));
		rigaTotale.setNuovoPreventivo(NumberUtil.getStringValueItalianFormat(nuovoPreventivo));
		rigaTotale.setRealizzato(NumberUtil.getStringValueItalianFormat(realizzato));
		rigaTotale.setUltimoPreventivo(NumberUtil.getStringValueItalianFormat(ultimoPreventivo));

		rigaTotale.setIsMacroVoce(false);
		rigaTotale.setIsRigaDate(false);
		rigaTotale.setIsRigaRibaltamento(false);
		rigaTotale.setModificabile(false);
		rigaTotale.setIsRigaTotale(true);
		rigaTotale.setLabel("Totale");
		LOG.info(prf + " END");
		return rigaTotale;
	}
	
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	
	/////////////////////////////////// SERVIZIO VALIDAZIONE DATI PER SALVATAGGIO /////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	@Override
	public Response controllaDatiPerSalvataggio(HttpServletRequest req, ValidazioneDatiQuadroProvisionaleRequest vaProvisionaleRequest) throws UtenteException, Exception {
		String prf = "[QuadroPrevisionaleServiceImpl::controllaDatiPerSalvataggio]";
		LOG.info(prf + " BEGIN");
		
		try {			
			ArrayList<QuadroPrevisionaleItem> voci = vaProvisionaleRequest.getVoci();
			ArrayList<QuadroPrevisionaleComplessivoItem> vociQuadroComplessivo = vaProvisionaleRequest.getVociQuadroComplessivo();
			QuadroPrevisionale quadroPrevisionale = vaProvisionaleRequest.getQuadroPrevisionale();
			String noteQuadroPrevisionale = vaProvisionaleRequest.getNoteQuadroPrevisionale();
			
			boolean isError = false;
			Map<String, String> errors = new HashMap<String, String>();
			ExecResults result = new ExecResults();
			Set<String> setMessaggi = new HashSet<String>();
			Set<String> globalMessages = new HashSet<String>();
			
			if (noteQuadroPrevisionale != null && noteQuadroPrevisionale.length() > Constants.MAX_LUNGHEZZA_NOTE) {
				errors.put("noteQuadroPrevisionale", MessageConstants.ERRORE_MAX_LUNGHEZZA_CAMPO_NOTE);
				globalMessages.add(MessageConstants.ERRORE_MAX_LUNGHEZZA_CAMPO_NOTE);
				result.setFldErrors(errors);
				result.setGlobalErrors(globalMessages);
				result.setResultCode(Constants.CONTROLLADATIPERSALVATAGGIO_OUTCOME_CODE__ERROR);
				return Response.ok().entity(result).build();
			}
			
			Double totaleUltimaSpesaAmmessa = 0d;
			Map<Long, Double> mapTotaleUltimaSpesaAmmessa = new HashMap<Long, Double>();
			for (QuadroPrevisionaleComplessivoItem quadroPrevisionaleComplessivoItem : vociQuadroComplessivo) {
				if (quadroPrevisionaleComplessivoItem.getIsTotaleComplessivo()) {
					totaleUltimaSpesaAmmessa = NumberUtil.toNullableDoubleItalianFormat(quadroPrevisionaleComplessivoItem.getUltimaSpesaAmmessa());
				} else {
					String ultimaSpesaAmmessa = quadroPrevisionaleComplessivoItem.getUltimaSpesaAmmessa();
					Double dUltimaSpesaAmmessa = NumberUtil.toNullableDoubleItalianFormat(ultimaSpesaAmmessa);
					if (dUltimaSpesaAmmessa == null) {
						dUltimaSpesaAmmessa = 0d;
					}
					mapTotaleUltimaSpesaAmmessa.put(quadroPrevisionaleComplessivoItem.getIdVoce(), dUltimaSpesaAmmessa);
				}
			}
			
			if (quadroPrevisionale.getIsVociVisibili()) {
				isError = checkImporti(voci, setMessaggi, quadroPrevisionale.getControlloNuovoImportoBloccante(), totaleUltimaSpesaAmmessa, mapTotaleUltimaSpesaAmmessa);
			} else {
				isError = checkImportiVociNonVisibili(voci, setMessaggi, quadroPrevisionale.getControlloNuovoImportoBloccante(), totaleUltimaSpesaAmmessa, mapTotaleUltimaSpesaAmmessa);
			}			
			
			voci = ricalcolaVociPerVisualizzazione(voci);
			vociQuadroComplessivo = ricalcolaVociComplessivePerVisualizzazione(voci, vociQuadroComplessivo);
			checkImportiComplessivi(vociQuadroComplessivo, setMessaggi);
			Collection<String> messaggi = new ArrayList<String>(setMessaggi);

			if (isError) {
				result.setGlobalErrors(messaggi);	
				result.setResultCode(Constants.CONTROLLADATIPERSALVATAGGIO_OUTCOME_CODE__ERROR);
				result.setModel(messaggi);
			} else {
				Collection<String> msgSuccess = new ArrayList<String>(setMessaggi);
				msgSuccess.add(Constants.CONFERMA_SALVATAGGIO);
				result.setResultCode(Constants.CONTROLLADATIPERSALVATAGGIO_OUTCOME_CODE__OK);
				result.setGlobalMessages(msgSuccess);
			}	
			QuadroPrevisionaleModel model = new QuadroPrevisionaleModel();
			model.setVoci(voci);
			model.setVociQuadroComplessivo(vociQuadroComplessivo);
			result.setModel(model);
			
			LOG.info(prf + " END");
			return Response.ok().entity(result).build();
		} catch(Exception e) {
			throw e;
		}
	}
	
	private boolean checkImporti(
			ArrayList<QuadroPrevisionaleItem> quadroPrevisionaleItems,
			Set<String> messaggi, boolean controlloNuovoImportoBloccante,
			Double totaleUltimaSpesaAmmessa,
			Map<Long, Double> mapUltimaSpesaAmmessaPerVoceComplessivo) {
		boolean error = false;
		Iterator<QuadroPrevisionaleItem> vociWeb = quadroPrevisionaleItems.iterator();

		Double totaleNuovoPreventivo = 0d;
		if (totaleUltimaSpesaAmmessa == null)
			totaleUltimaSpesaAmmessa = 0d;
		Double totaleRealizzato = 0d;

		Map<Long, Double> mapTotaleRealizzato = new HashMap<Long, Double>();
		Map<Long, Double> mapTotaleNuovoPreventivo = new HashMap<Long, Double>();
		int currentAnno = DateUtil.getAnno();
		while (vociWeb.hasNext()) {

			QuadroPrevisionaleItem quadroPrevisionaleItem = (QuadroPrevisionaleItem) vociWeb.next();
			quadroPrevisionaleItem.setCodiceErrore(null);
			boolean isWarning = false;
			if (quadroPrevisionaleItem.getModificabile()) {
				String periodo = quadroPrevisionaleItem.getPeriodo();
				int currentPeriodo = Integer.parseInt(periodo);

				String nuovoPreventivo = quadroPrevisionaleItem.getNuovoPreventivo();
				Double dNuovoPreventivo = NumberUtil.toNullableDoubleItalianFormat(nuovoPreventivo);

				String realizzato = quadroPrevisionaleItem.getRealizzato();
				Double dRealizzato = NumberUtil.toNullableDoubleItalianFormat(realizzato);

				String ultimoPreventivo = quadroPrevisionaleItem.getUltimoPreventivo();
				Double dUltimoPreventivo = NumberUtil.toNullableDoubleItalianFormat(ultimoPreventivo);

				if (ObjectUtil.isNull(dNuovoPreventivo) || dNuovoPreventivo < 0) {
					quadroPrevisionaleItem.setCodiceErrore("X");
					quadroPrevisionaleItem.setIsError(true);
					messaggi.clear();
					messaggi.add(MessageConstants.IMPORTI_NON_VALIDI);
					error = true;
				} else if (dNuovoPreventivo.doubleValue() > Constants.MAX_IMPORTO_AMMMESSO) {
					quadroPrevisionaleItem.setCodiceErrore("X");
					messaggi.clear();
					messaggi.add(MessageConstants.IMPORTI_NON_VALIDI);
					quadroPrevisionaleItem.setIsError(true);
					error = true;
				} else {

					totaleNuovoPreventivo = NumberUtil.sum(totaleNuovoPreventivo, dNuovoPreventivo);
					Double preventivo = dNuovoPreventivo;
					if (mapTotaleNuovoPreventivo.containsKey(quadroPrevisionaleItem.getIdVoce())) {
						Double oldNuovoPreventivo = mapTotaleNuovoPreventivo.get(quadroPrevisionaleItem.getIdVoce());
						preventivo = NumberUtil.sum(oldNuovoPreventivo, preventivo);
					}
					mapTotaleNuovoPreventivo.put(quadroPrevisionaleItem.getIdVoce(), preventivo);
					if (dRealizzato != null) {
						if (!error && dNuovoPreventivo.doubleValue() < dRealizzato.doubleValue()) {
							quadroPrevisionaleItem.setCodiceErrore("a");
							messaggi.add(MessageConstants.KEY_MSG_INFERIORE_REALIZZATO);
							isWarning = true;
						}
						totaleRealizzato = NumberUtil.sum(totaleRealizzato, dRealizzato);
						Double oldRealizzato = dRealizzato;
						if (mapTotaleRealizzato.containsKey(quadroPrevisionaleItem.getIdVoce())) {
							oldRealizzato = mapTotaleRealizzato.get(quadroPrevisionaleItem.getIdVoce());
							oldRealizzato = NumberUtil.sum(oldRealizzato, dRealizzato);
						}
						mapTotaleRealizzato.put(quadroPrevisionaleItem.getIdVoce(), oldRealizzato);

					}

					if (dUltimoPreventivo != null) {
						if (!error && dNuovoPreventivo.doubleValue() > dUltimoPreventivo.doubleValue()) {
							if (!isWarning) {
								quadroPrevisionaleItem.setCodiceErrore("b");
								messaggi.add(MessageConstants.KEY_MSG_SUPERIORE_ULTIMO_PREVENTIVO);
								isWarning = true;
							}
						} else if (!error && dNuovoPreventivo.doubleValue() < dUltimoPreventivo.doubleValue()) {
							if (!isWarning) {
								quadroPrevisionaleItem.setCodiceErrore("c");
								messaggi.add(MessageConstants.KEY_MSG_INFERIORE_ULTIMO_PREVENTIVO);
								isWarning = true;
							}
						}
					}

					if (dNuovoPreventivo == 0d)
						quadroPrevisionaleItem.setNuovoPreventivo("0,00");
					else
						quadroPrevisionaleItem.setNuovoPreventivo(NumberUtil.getStringValueItalianFormat(dNuovoPreventivo));
				}

				if ((currentPeriodo < currentAnno) && dNuovoPreventivo != null && dRealizzato != null) {
					if (dNuovoPreventivo.doubleValue() != dRealizzato.doubleValue()) {
						if (controlloNuovoImportoBloccante) {
							messaggi.clear();
							messaggi.add(MessageConstants.KEY_MSG_NUOVO_PREVENTIVO_DIVERSO_DAL_REALIZZATO);
							quadroPrevisionaleItem.setCodiceErrore("g");
							quadroPrevisionaleItem.setIsError(true);
							error = true;
							// return error;
						} else {
							if (!messaggi.contains(MessageConstants.KEY_MSG_INFERIORE_REALIZZATO)) {
								if (!error && !isWarning) {
									messaggi.add(MessageConstants.KEY_MSG_NUOVO_PREVENTIVO_DIVERSO_DAL_REALIZZATO);
									quadroPrevisionaleItem.setCodiceErrore("g");
									isWarning = true;
								}
							}
						}
					}
				}
			}
		}

		if (!error) {
			if (totaleNuovoPreventivo.doubleValue() > totaleUltimaSpesaAmmessa.doubleValue()) {
				messaggi.add(MessageConstants.KEY_MSG_NUOVO_PREVENTIVO_SUPERIORE_ULTIMA_SPESA_AMMESSA);
				if (controlloNuovoImportoBloccante) {
					messaggi.clear();
					messaggi.add(MessageConstants.KEY_MSG_NUOVO_PREVENTIVO_SUPERIORE_ULTIMA_SPESA_AMMESSA);
					error = true;
					return error;
				}
			}
			if (totaleNuovoPreventivo.doubleValue() < totaleUltimaSpesaAmmessa.doubleValue()) {
				messaggi.add(MessageConstants.KEY_MSG_NUOVO_PREVENTIVO_INFERIORE_ULTIMA_SPESA_AMMESSA);
				if (controlloNuovoImportoBloccante) {
					messaggi.clear();
					messaggi.add(MessageConstants.KEY_MSG_NUOVO_PREVENTIVO_INFERIORE_ULTIMA_SPESA_AMMESSA);
					error = true;
					return error;
				}
			}
			if (totaleNuovoPreventivo.doubleValue() < totaleRealizzato.doubleValue()) {
				messaggi.add(MessageConstants.KEY_MSG_TOTALE_NUOVO_PREVENTIVO_INFERIORE_TOTALE_REALIZZATO);
			}
		}

		return error;
	}

	private boolean checkImportiVociNonVisibili(
			ArrayList<QuadroPrevisionaleItem> quadroPrevisionaleItems,
			Set<String> messaggi, boolean controlloNuovoImportoBloccante,
			Double totaleUltimaSpesaAmmessa,
			Map<Long, Double> mapUltimaSpesaAmmessaPerVoceComplessivo) {
		boolean error = false;
		Iterator<QuadroPrevisionaleItem> vociWeb = quadroPrevisionaleItems.iterator();

		Double totaleNuovoPreventivo = 0d;
		if (totaleUltimaSpesaAmmessa == null)
			totaleUltimaSpesaAmmessa = 0d;
		Double totaleRealizzato = 0d;
		Map<String, Double> mapTotaleRealizzato = new HashMap<String, Double>();
		Map<String, Double> mapTotaleNuovoPreventivo = new HashMap<String, Double>();

		while (vociWeb.hasNext()) {
			QuadroPrevisionaleItem quadroPrevisionaleItem = (QuadroPrevisionaleItem) vociWeb.next();
			quadroPrevisionaleItem.setCodiceErrore(null);
			if (quadroPrevisionaleItem.getModificabile()) {
				String nuovoPreventivo = quadroPrevisionaleItem.getNuovoPreventivo();
				Double dNuovoPreventivo = NumberUtil.toNullableDoubleItalianFormat(nuovoPreventivo);
				if (ObjectUtil.isNull(dNuovoPreventivo) || dNuovoPreventivo < 0) {
					quadroPrevisionaleItem.setCodiceErrore("X");
					messaggi.add(MessageConstants.IMPORTI_NON_VALIDI);
					error = true;
				}
			}
		}
		vociWeb = quadroPrevisionaleItems.iterator();
		while (vociWeb.hasNext()) {
			
			QuadroPrevisionaleItem quadroPrevisionaleItem = (QuadroPrevisionaleItem) vociWeb.next();
			if (quadroPrevisionaleItem.getModificabile()) {

				String nuovoPreventivo = quadroPrevisionaleItem.getNuovoPreventivo();
				Double dNuovoPreventivo = NumberUtil.toNullableDoubleItalianFormat(nuovoPreventivo);

				String realizzato = quadroPrevisionaleItem.getRealizzato();
				Double dRealizzato = NumberUtil.toNullableDoubleItalianFormat(realizzato);

				String ultimoPreventivo = quadroPrevisionaleItem.getUltimoPreventivo();
				Double dUltimoPreventivo = NumberUtil.toNullableDoubleItalianFormat(ultimoPreventivo);

				totaleNuovoPreventivo = NumberUtil.sum(totaleNuovoPreventivo, dNuovoPreventivo);
				Double preventivo = dNuovoPreventivo;
				if (mapTotaleNuovoPreventivo.containsKey(quadroPrevisionaleItem.getPeriodo())) {
					Double oldNuovoPreventivo = mapTotaleNuovoPreventivo.get(quadroPrevisionaleItem.getPeriodo());
					preventivo = NumberUtil.sum(oldNuovoPreventivo, preventivo);
				}
				mapTotaleNuovoPreventivo.put(quadroPrevisionaleItem.getPeriodo(), preventivo);
				boolean isWarning = false;
				if (dRealizzato != null) {
					if (!error && dNuovoPreventivo.doubleValue() < dRealizzato.doubleValue()) {
						quadroPrevisionaleItem.setCodiceErrore("a");
						messaggi.add(MessageConstants.KEY_MSG_INFERIORE_REALIZZATO);
						isWarning = true;
					}
					totaleRealizzato = NumberUtil.sum(totaleRealizzato, dRealizzato);

					if (mapTotaleRealizzato.containsKey(quadroPrevisionaleItem.getPeriodo())) {
						Double oldRealizzato = mapTotaleRealizzato.get(quadroPrevisionaleItem.getPeriodo());
						dRealizzato = NumberUtil.sum(oldRealizzato, dRealizzato);
					}
					mapTotaleRealizzato.put(quadroPrevisionaleItem.getPeriodo(), dRealizzato);
				}

				if (dUltimoPreventivo != null) {
					if (!error && dNuovoPreventivo.doubleValue() > dUltimoPreventivo.doubleValue()) {
						if (!isWarning) {
							quadroPrevisionaleItem.setCodiceErrore("b");
							messaggi.add(MessageConstants.KEY_MSG_SUPERIORE_ULTIMO_PREVENTIVO);
							isWarning = true;
						}
					} else if (!error && dNuovoPreventivo.doubleValue() < dUltimoPreventivo.doubleValue()) {
						if (!isWarning) {
							quadroPrevisionaleItem.setCodiceErrore("c");
							messaggi.add(MessageConstants.KEY_MSG_INFERIORE_ULTIMO_PREVENTIVO);
							isWarning = true;
						}
					}
				}

				if (dNuovoPreventivo != null && dNuovoPreventivo == 0d)
					quadroPrevisionaleItem.setNuovoPreventivo("0,00");
				else
					quadroPrevisionaleItem.setNuovoPreventivo(NumberUtil.getStringValueItalianFormat(dNuovoPreventivo));
				
			}
			
		}

		if (!error) {
			// tofix
			if (totaleNuovoPreventivo.doubleValue() > totaleUltimaSpesaAmmessa.doubleValue()) {
				messaggi.add(MessageConstants.KEY_MSG_NUOVO_PREVENTIVO_SUPERIORE_ULTIMA_SPESA_AMMESSA);
				if (controlloNuovoImportoBloccante) {
					messaggi.clear();
					messaggi.add(MessageConstants.KEY_MSG_NUOVO_PREVENTIVO_SUPERIORE_ULTIMA_SPESA_AMMESSA);
					error = true;
					return error;
				}
			}
			if (totaleNuovoPreventivo.doubleValue() < totaleUltimaSpesaAmmessa.doubleValue()) {
				messaggi.add(MessageConstants.KEY_MSG_NUOVO_PREVENTIVO_INFERIORE_ULTIMA_SPESA_AMMESSA);
				if (controlloNuovoImportoBloccante) {
					messaggi.clear();
					messaggi.add(MessageConstants.KEY_MSG_NUOVO_PREVENTIVO_INFERIORE_ULTIMA_SPESA_AMMESSA);
					error = true;
					return error;
				}
			}
			// tofix

			if (totaleNuovoPreventivo.doubleValue() < totaleRealizzato.doubleValue())
				messaggi.add(MessageConstants.KEY_MSG_TOTALE_NUOVO_PREVENTIVO_INFERIORE_TOTALE_REALIZZATO);

			// RICICLO PER I TOTALI PER VoceDiSpesa
			vociWeb = quadroPrevisionaleItems.iterator();

			while (vociWeb.hasNext()) {
				QuadroPrevisionaleItem quadroPrevisionaleItem = (QuadroPrevisionaleItem) vociWeb.next();
				if (quadroPrevisionaleItem.getModificabile()) {
					String periodo = quadroPrevisionaleItem.getPeriodo();
					if (mapTotaleNuovoPreventivo.containsKey(periodo)) {
						Double totaleNuovoPreventivoVds = mapTotaleNuovoPreventivo.get(periodo);

						if (mapTotaleRealizzato.containsKey(periodo)) {
							Double totaleRealizzatoVds = mapTotaleNuovoPreventivo.get(periodo);
							if (totaleNuovoPreventivoVds.doubleValue() < totaleRealizzatoVds.doubleValue()) {
								if (quadroPrevisionaleItem.getCodiceErrore() == null) {
									messaggi.add(MessageConstants.KEY_MSG_TOTALI_NUOVO_PREVENTIVO_PER_VDS_INFERIORI_TOTALE_REALIZZATO);
									quadroPrevisionaleItem.setCodiceErrore("f");
								}
							}
						}
					}
				}
			}
		}
		return error;
	}
	
	private ArrayList<QuadroPrevisionaleComplessivoItem> ricalcolaVociComplessivePerVisualizzazione(
			ArrayList<QuadroPrevisionaleItem> vociWeb,
			ArrayList<QuadroPrevisionaleComplessivoItem> vociQuadroComplessivoWeb) {

		Map<Long, Double> macroVoci = new HashMap<Long, Double>();
		Double totaleNuovoPreventivo = 0d;
		for (QuadroPrevisionaleItem voceWeb : vociWeb) {
			// calcolo nuovoimporto tramite Map
			Long idVoce = voceWeb.getIdVoce();
			if (idVoce != null) {
				Double nuovoPreventivo = NumberUtil.toNullableDoubleItalianFormat(voceWeb.getNuovoPreventivo());
				if (nuovoPreventivo == null)
					nuovoPreventivo = 0d;
				if (nuovoPreventivo.doubleValue() > 0) {
					if (macroVoci.containsKey(idVoce)) {
						// get value
						Double oldPreventivo = macroVoci.get(idVoce);
						// somma
						nuovoPreventivo = NumberUtil.sum(oldPreventivo, nuovoPreventivo);
						// put value
					}
					macroVoci.put(idVoce, nuovoPreventivo);
				}
			}
		}
		for (QuadroPrevisionaleItem voceWeb : vociWeb) {
			if (voceWeb.getIsPeriodo()) {
				Double nuovoPreventivo = NumberUtil.toNullableDoubleItalianFormat(voceWeb.getNuovoPreventivo());
				totaleNuovoPreventivo = NumberUtil.sum(totaleNuovoPreventivo, nuovoPreventivo);
			}
		}

		for (QuadroPrevisionaleComplessivoItem voceComplessiva : vociQuadroComplessivoWeb) {
			Double realizzato = NumberUtil.toNullableDoubleItalianFormat(voceComplessiva.getRealizzato());
			if (realizzato == null)
				realizzato = 0d;

			if (voceComplessiva.getIsTotaleComplessivo()) {
				voceComplessiva.setNuovoPreventivo(NumberUtil.getStringValueItalianFormat(totaleNuovoPreventivo));
				Double daRealizzare = totaleNuovoPreventivo - realizzato;
				voceComplessiva.setDaRealizzare(NumberUtil.getStringValueItalianFormat(daRealizzare));
			}

			Long idVoce = voceComplessiva.getIdVoce();
			if (idVoce != null) {
				Double nuovoPreventivo = macroVoci.get(idVoce);
				voceComplessiva.setNuovoPreventivo(NumberUtil.getStringValueItalianFormat(nuovoPreventivo));
				if (nuovoPreventivo == null)
					nuovoPreventivo = 0d;
				Double daRealizzare = nuovoPreventivo - realizzato;
				voceComplessiva.setDaRealizzare(NumberUtil.getStringValueItalianFormat(daRealizzare));
			}
			if(voceComplessiva.getIsRigaDate()) {
				Date today = new Date();
				DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");  
				String strToday = dateFormat.format(today);  
				voceComplessiva.setDataNuovoPreventivo(strToday);
			}
		}

		return vociQuadroComplessivoWeb;
	}

	private ArrayList<QuadroPrevisionaleItem> ricalcolaVociPerVisualizzazione(
			ArrayList<QuadroPrevisionaleItem> vociWeb) {
		ArrayList<QuadroPrevisionaleItem> vociWebRicalcolate = new ArrayList<QuadroPrevisionaleItem>( vociWeb);
		Map<String, Double> mapPeriodo = new HashMap<String, Double>(); // "2008","2345,34"
		Map<String, Map<Long, Double>> mapMacroVociPeriodo = new HashMap<String, Map<Long, Double>>();// "2008",idMacro+importo
		for (QuadroPrevisionaleItem voceWeb : vociWeb) {
			String periodo = voceWeb.getPeriodo();
			Double totalePeriodoNuovoPreventivo = 0d;
			Double parzialeNuovoPreventivo = 0d;
			Double totaleMacroVoce = 0d;

			Long id = NumberUtil.toNullableLong(voceWeb.getIdPadre());
			if (voceWeb.getModificabile() && id == null)
				id = voceWeb.getIdVoce();

			if (voceWeb.getModificabile()) {
				parzialeNuovoPreventivo = NumberUtil.toNullableDoubleItalianFormat(voceWeb.getNuovoPreventivo());
				if (parzialeNuovoPreventivo == null)
					parzialeNuovoPreventivo = 0d;
			}

			if (periodo != null) {
				if (mapPeriodo.containsKey(periodo)) {
					totalePeriodoNuovoPreventivo = mapPeriodo.get(periodo);
				}
				totalePeriodoNuovoPreventivo = NumberUtil.sum(totalePeriodoNuovoPreventivo, parzialeNuovoPreventivo);
				mapPeriodo.put(periodo, totalePeriodoNuovoPreventivo);

				if (id != null) {
					Map<Long, Double> mapMacroVoci = new HashMap<Long, Double>();
					if (mapMacroVociPeriodo.containsKey(periodo)) {
						mapMacroVoci = mapMacroVociPeriodo.get(periodo);
					}
					if (mapMacroVoci.containsKey(id)) {
						totaleMacroVoce = mapMacroVoci.get(id);
					}
					totaleMacroVoce = NumberUtil.sum(totaleMacroVoce, parzialeNuovoPreventivo);
					mapMacroVoci.put(id, totaleMacroVoce);
					mapMacroVociPeriodo.put(periodo, mapMacroVoci);
				}
			}
		}

		Double totaleNuovoPreventivo = 0d;
		for (QuadroPrevisionaleItem voceWeb : vociWebRicalcolate) {
			if (voceWeb.getIsPeriodo()) {
				if (mapPeriodo.containsKey(voceWeb.getPeriodo())) {
					if (voceWeb.getNuovoPreventivo() != null && !voceWeb.getNuovoPreventivo().equals("")) {
						Double importoPeriodo = mapPeriodo.get(voceWeb.getPeriodo());
						voceWeb.setNuovoPreventivo(NumberUtil.getStringValueItalianFormat(importoPeriodo));

						totaleNuovoPreventivo = NumberUtil.sum(totaleNuovoPreventivo, importoPeriodo);
					}
				}
			} else if (voceWeb.getIsMacroVoce()) {
				Map<Long, Double> mapMacroVoci = mapMacroVociPeriodo.get(voceWeb.getPeriodo());
				if (mapMacroVoci.containsKey(voceWeb.getIdVoce())) {
					Double importoNuovoPreventivoMacroVoce = mapMacroVoci.get(voceWeb.getIdVoce());
					voceWeb.setNuovoPreventivo(NumberUtil.getStringValueItalianFormat(importoNuovoPreventivoMacroVoce));
				}
			} else {

			}
		}
		for (QuadroPrevisionaleItem voceWeb : vociWebRicalcolate) {
			Double realizzato = NumberUtil.toNullableDoubleItalianFormat(voceWeb.getRealizzato());
			Double nuovoPreventivo = NumberUtil.toNullableDoubleItalianFormat(voceWeb.getNuovoPreventivo());
			if (voceWeb.getIsRigaTotale()) {
				voceWeb.setNuovoPreventivo(NumberUtil.getStringValueItalianFormat(totaleNuovoPreventivo));
				nuovoPreventivo = totaleNuovoPreventivo;
			}
			if (realizzato == null)
				realizzato = 0d;
			if (nuovoPreventivo == null)
				nuovoPreventivo = 0d;
			Double daRealizzare = nuovoPreventivo - realizzato;
			voceWeb.setDaRealizzare(NumberUtil.getStringValueItalianFormat(daRealizzare));
			if(voceWeb.getIsRigaDate()) {
				Date today = new Date();
				DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");  
				String strToday = dateFormat.format(today);  
				voceWeb.setDataNuovoPreventivo(strToday);
			}
		}
		return vociWebRicalcolate;

	}

	private void checkImportiComplessivi(
			ArrayList<QuadroPrevisionaleComplessivoItem> vociQuadroComplessivoWeb,
			Collection<String> messaggi) {

		for (QuadroPrevisionaleComplessivoItem quadroPrevisionaleComplessivoItem : vociQuadroComplessivoWeb) {
			if (!quadroPrevisionaleComplessivoItem.getIsRigaDate() && !quadroPrevisionaleComplessivoItem.getIsTotaleComplessivo() && !quadroPrevisionaleComplessivoItem.getHaFigli()) {

				Double nuovoPreventivo = NumberUtil.toNullableDoubleItalianFormat(quadroPrevisionaleComplessivoItem.getNuovoPreventivo());
				Double ultimaSpesaAmmessa = NumberUtil.toNullableDoubleItalianFormat(quadroPrevisionaleComplessivoItem.getUltimaSpesaAmmessa());
				Double realizzato = NumberUtil.toNullableDoubleItalianFormat(quadroPrevisionaleComplessivoItem.getRealizzato());
				if (nuovoPreventivo == null)
					nuovoPreventivo = 0d;
				if (ultimaSpesaAmmessa == null)
					ultimaSpesaAmmessa = 0d;
				if (realizzato == null)
					realizzato = 0d;
				if (nuovoPreventivo.doubleValue() > ultimaSpesaAmmessa.doubleValue()) {
					if (quadroPrevisionaleComplessivoItem.getCodiceErrore() == null) {
						messaggi.add(MessageConstants.KEY_MSG_TOTALI_NUOVO_PREVENTIVO_PER_VDS_SUPERIORI_ULTIMA_SPESA_AMMESSA);
						quadroPrevisionaleComplessivoItem.setCodiceErrore("d");
					}

				} else if (nuovoPreventivo.doubleValue() < ultimaSpesaAmmessa.doubleValue()) {
					if (quadroPrevisionaleComplessivoItem.getCodiceErrore() == null) {
						messaggi.add(MessageConstants.KEY_MSG_TOTALI_NUOVO_PREVENTIVO_PER_VDS_INFERIORI_ULTIMA_SPESA_AMMESSA);
						quadroPrevisionaleComplessivoItem.setCodiceErrore("e");
					}
				} else if (nuovoPreventivo.doubleValue() < realizzato.doubleValue()) {
					if (quadroPrevisionaleComplessivoItem.getCodiceErrore() == null) {
						messaggi.add(MessageConstants.KEY_MSG_TOTALI_NUOVO_PREVENTIVO_PER_VDS_INFERIORI_TOTALE_REALIZZATO);
						quadroPrevisionaleComplessivoItem.setCodiceErrore("f");
					}
				}
			}
		}
	}

	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	
	/////////////////////////////////////////////SERVIZIO SALVATAGGIO /////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	@Override
	public Response salvaQuadroPrevisionale(HttpServletRequest req,
			ResponseGetQuadroPrevisionale salvaQuadroPrevisionaleRequest) throws UtenteException, Exception {
		String prf = "[QuadroPrevisionaleServiceImpl::salvaQuadroPrevisionale]";
		LOG.info(prf + " BEGIN");
		
		try {
			HttpSession session = req.getSession();
			UserInfoSec userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
			Long idUtente = userInfo.getIdUtente();
			String idIride = userInfo.getIdIride();
			Long idProgetto = salvaQuadroPrevisionaleRequest.getIdProgetto();
			
			// 1) setto i dati generali del quadro
			QuadroPrevisionaleDTO quadroPrevisionaleDaSalvare = new QuadroPrevisionaleDTO();
			quadroPrevisionaleDaSalvare.setIdProgetto(salvaQuadroPrevisionaleRequest.getIdProgetto());
			quadroPrevisionaleDaSalvare.setIdQuadro(salvaQuadroPrevisionaleRequest.getQuadroPrevisionale().getIdQuadro());
			quadroPrevisionaleDaSalvare.setNote(salvaQuadroPrevisionaleRequest.getNote());
			
			// 2) rimappo i dati a video nel quadro da salvare
			ArrayList<QuadroPrevisionaleItem> vociWeb = salvaQuadroPrevisionaleRequest.getVociWeb();
			
			EsitoFindQuadroPrevisionaleDTO oldEsitoFind = findQuadroPrevisionale(idUtente, idIride, idProgetto);

			if (salvaQuadroPrevisionaleRequest.getQuadroPrevisionale().getIsVociVisibili()) {
				mappaQuadroPerSalvataggio(quadroPrevisionaleDaSalvare, vociWeb);
			} else {
				ArrayList<QuadroPrevisionaleComplessivoItem> vociQuadroComplessivo = salvaQuadroPrevisionaleRequest.getQuadroComplessivoList();
				//new
				EsitoFindQuadroPrevisionaleDTO esitoFind  = quadroPrevisionaleDAO.findQuadroPrevisionale(idUtente, idIride, idProgetto);
				QuadroPrevisionaleDTO quadroPrevisionaleServer = esitoFind.getQuadroPrevisionale();
				QuadroPrevisionaleDTO[] vociServer = quadroPrevisionaleServer.getFigli();
				
				ArrayList<QuadroPrevisionaleItem> voci = mappaVociDiSpesaPerVisualizzazione(vociServer, vociServer, esitoFind.isVociVisibili());
				ArrayList<QuadroPrevisionaleItem> vociNonVisibili = new ArrayList<>();
				for(QuadroPrevisionaleItem q: voci) {
					if(!q.getIsVociVisibili()) {
						vociNonVisibili.add(q);
					}
				}
				mappaQuadroPerSalvataggioVociNonVisibili( quadroPrevisionaleDaSalvare, vociWeb, vociNonVisibili, vociQuadroComplessivo, oldEsitoFind.getQuadroPrevisionaleComplessivo());

			}
			
			EsitoSalvaQuadroPrevisionaleDTO esito  = quadroPrevisionaleDAO.salvaQuadroPrevisionale(idUtente, idIride, quadroPrevisionaleDaSalvare, idProgetto);
			
			//rimappaDatiPerVisualizzazionePostSalvataggio(salvaQuadroPrevisionaleRequest, oldEsitoFind);
			LOG.info(prf + " END");
			return Response.ok().entity(esito).build();
		} catch(Exception e) {
			throw e;
		}
	}

	
	
	private void rimappaDatiPerVisualizzazionePostSalvataggio(
			ResponseGetQuadroPrevisionale salvaQuadroPrevisionaleRequest, EsitoFindQuadroPrevisionaleDTO oldEsitoFind) {
		// TODO Auto-generated method stub
		
	}


	private void mappaQuadroPerSalvataggioVociNonVisibili(QuadroPrevisionaleDTO quadroPrevisionaleDaSalvare,
			ArrayList<QuadroPrevisionaleItem> vociPeriodo, ArrayList<QuadroPrevisionaleItem> allVociWeb,
			ArrayList<QuadroPrevisionaleComplessivoItem> vociQuadroComplessivo,
			QuadroPrevisionaleComplessivoDTO[] quadroPrevisionaleComplessivoDTO) {
		QuadroPrevisionaleComplessivoItem voceQuadroComplessivoTotale = null;
		for (QuadroPrevisionaleComplessivoItem item : vociQuadroComplessivo) {
			if (item.getIsTotaleComplessivo()) {
				voceQuadroComplessivoTotale = item;
				break;
			}
		}

		Double totaleSpesaAmmessaProgetto = NumberUtil
				.toNullableDoubleItalianFormat(voceQuadroComplessivoTotale
						.getUltimaSpesaAmmessa());

		Map<String, String> mapPropsToCopy = new HashMap<String, String>();
		mapPropsToCopy.put("nuovoPreventivo", "importoPreventivo");
		mapPropsToCopy.put("idVoce", "idVoce");
		mapPropsToCopy.put("periodo", "periodo");
		mapPropsToCopy.put("idRigoQuadro", "idRigoQuadro");
		mapPropsToCopy.put("haFigli", "haFigli");
		ArrayList<QuadroPrevisionaleDTO> righiDaSalvare = new ArrayList<QuadroPrevisionaleDTO>();
		for (QuadroPrevisionaleItem vocePeriodo : vociPeriodo) {
			Double lastValue = 0d;
			ArrayList<QuadroPrevisionaleDTO> righiDaSalvarePeriodo = new ArrayList<QuadroPrevisionaleDTO>();
			Double totalePreventivoAutomaticoPerPeriodo = 0d;
			Double nuovoPreventivo = NumberUtil
					.toNullableDoubleItalianFormat(vocePeriodo
							.getNuovoPreventivo());
			String periodo = vocePeriodo.getPeriodo();
			if (periodo != null && nuovoPreventivo != null) {
				for (QuadroPrevisionaleItem voceWeb : allVociWeb) {

					if (periodo.equals(voceWeb.getPeriodo())
							&& voceWeb.getIdVoce() != null
							&& !voceWeb.getHaFigli() && !voceWeb.getIsPeriodo()) {

						if (nuovoPreventivo.doubleValue() > 0
								|| voceWeb.getIdRigoQuadro() > 0) {

							Double ultimaSpesaAmmessaPerVoceDiSpesa = null;
							for (QuadroPrevisionaleComplessivoDTO quadroPrevisionaleDTO : quadroPrevisionaleComplessivoDTO) {
								if (voceWeb.getIdVoce().longValue() == quadroPrevisionaleDTO
										.getIdVoce().longValue()) {
									ultimaSpesaAmmessaPerVoceDiSpesa = (quadroPrevisionaleDTO
											.getSpesaAmmessa());
									break;
								}
							}

							Double preventivoAutomatico = 0d;
							if (ultimaSpesaAmmessaPerVoceDiSpesa != null
									&& ultimaSpesaAmmessaPerVoceDiSpesa
											.doubleValue() > 0) {

								preventivoAutomatico = NumberUtil
										.toRoundDouble(nuovoPreventivo
												* ultimaSpesaAmmessaPerVoceDiSpesa);

								preventivoAutomatico = Math
										.floor(preventivoAutomatico
												/ totaleSpesaAmmessaProgetto);

							}

							QuadroPrevisionaleDTO rigoDaSalvare = new QuadroPrevisionaleDTO();
							beanUtil.valueCopy(voceWeb, rigoDaSalvare,
									mapPropsToCopy);
							rigoDaSalvare
									.setImportoPreventivo(preventivoAutomatico);
							rigoDaSalvare
									.setIdProgetto(quadroPrevisionaleDaSalvare
											.getIdProgetto());
							if (0 == rigoDaSalvare.getIdRigoQuadro())
								rigoDaSalvare.setIdRigoQuadro(null);

							totalePreventivoAutomaticoPerPeriodo = NumberUtil
									.toRoundDouble(NumberUtil
											.sum(
													NumberUtil
															.toRoundDouble(totalePreventivoAutomaticoPerPeriodo),
													NumberUtil
															.toRoundDouble(rigoDaSalvare
																	.getImportoPreventivo())));
							righiDaSalvarePeriodo.add(rigoDaSalvare);

						}
					}
				}

			}
			vocePeriodo.setCodiceErrore(null);

			if (righiDaSalvarePeriodo.size() > 0) {

				QuadroPrevisionaleDTO rigo = righiDaSalvarePeriodo
						.get((righiDaSalvarePeriodo.size() - 1));
				lastValue = NumberUtil.toRoundDouble(nuovoPreventivo
						- totalePreventivoAutomaticoPerPeriodo);
				QuadroPrevisionaleDTO ultimoRigo = righiDaSalvarePeriodo
						.get(righiDaSalvarePeriodo.size() - 1);
				lastValue = lastValue + ultimoRigo.getImportoPreventivo();
				if (lastValue.doubleValue() > 0)
					rigo.setImportoPreventivo(lastValue);
				righiDaSalvare.addAll(righiDaSalvarePeriodo);
			}

		}

		quadroPrevisionaleDaSalvare.setFigli(righiDaSalvare
				.toArray(new QuadroPrevisionaleDTO[righiDaSalvare.size()]));
		
	}


	private void mappaQuadroPerSalvataggio(QuadroPrevisionaleDTO quadroPrevisionaleDaSalvare,
			ArrayList<QuadroPrevisionaleItem> vociWeb) {
		ArrayList<QuadroPrevisionaleDTO> righiDaSalvare = new ArrayList<QuadroPrevisionaleDTO>();
		Map<String, String> mapPropsToCopy = new HashMap<String, String>();
		mapPropsToCopy.put("nuovoPreventivo", "importoPreventivo");
		mapPropsToCopy.put("idVoce", "idVoce");
		mapPropsToCopy.put("periodo", "periodo");
		mapPropsToCopy.put("idRigoQuadro", "idRigoQuadro");
		mapPropsToCopy.put("haFigli", "haFigli");
		for (QuadroPrevisionaleItem quadroPrevisionaleItem : vociWeb) {
			if (quadroPrevisionaleItem.getIdVoce() != null && !quadroPrevisionaleItem.getHaFigli()) {
				QuadroPrevisionaleDTO rigoDaSalvare = new QuadroPrevisionaleDTO();
				beanUtil.valueCopy(quadroPrevisionaleItem, rigoDaSalvare, mapPropsToCopy);
				rigoDaSalvare.setIdProgetto(quadroPrevisionaleDaSalvare.getIdProgetto());
				if (0 == rigoDaSalvare.getIdRigoQuadro())  rigoDaSalvare.setIdRigoQuadro(null);
				if (rigoDaSalvare.getIdRigoQuadro() != null || rigoDaSalvare.getImportoPreventivo() > 0)
					righiDaSalvare.add(rigoDaSalvare);
			}
			quadroPrevisionaleItem.setCodiceErrore(null);
		}
		quadroPrevisionaleDaSalvare.setFigli(righiDaSalvare.toArray(new QuadroPrevisionaleDTO[righiDaSalvare.size()]));
		
	}


	private EsitoFindQuadroPrevisionaleDTO findQuadroPrevisionale(Long idUtente, String idIride, Long idProgetto) throws Exception {
		String prf = "[QuadroPrevisionaleServiceImpl::findQuadroPrevisionale]";
		LOG.info(prf + " START");
		
		try {
			EsitoFindQuadroPrevisionaleDTO esitoFind = quadroPrevisionaleDAO.findQuadroPrevisionale(idUtente, idIride, idProgetto);
			return esitoFind;
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
