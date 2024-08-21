/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.business.gestioneaffidamenti;

import it.csi.csi.wrapper.CSIException;
import it.csi.csi.wrapper.SystemException;
import it.csi.csi.wrapper.UnrecoverableException;
import it.csi.pbandi.pbservizit.pbandisrv.business.BusinessImpl;
import it.csi.pbandi.pbservizit.pbandisrv.business.archivio.ArchivioBusinessImpl;
import it.csi.pbandi.pbservizit.pbandisrv.business.checklist.CheckListBusinessImpl;
import it.csi.pbandi.pbservizit.pbandisrv.business.checklisthtml.ChecklistHtmlBusinessImpl;
import it.csi.pbandi.pbservizit.pbandisrv.business.manager.DocumentoManager;
import it.csi.pbandi.pbservizit.pbandisrv.business.neoflux.Notification;
import it.csi.pbandi.pbservizit.pbandisrv.dto.archivio.FileDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.gestioneaffidamenti.AffidamentoDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.gestioneaffidamenti.EsitoGestioneAffidamenti;
import it.csi.pbandi.pbservizit.pbandisrv.dto.gestioneaffidamenti.FornitoreAffidamentoDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.gestioneaffidamenti.ParamInviaInVerificaDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.gestioneaffidamenti.ProceduraAggiudicazioneDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.gestioneaffidamenti.VarianteAffidamentoDTO;
import it.csi.pbandi.pbservizit.pbandisrv.exception.gestioneaffidamenti.GestioneAffidamentiException;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.dao.GenericDAO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.dao.PbandiArchivioFileDAOImpl;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.AffidamentoVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.ArchivioFileVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.CheckListAppaltoVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.DocumentoIndexMaxVersioneDefinitivoVO;
//import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.DocumentoIndexMaxVersioneVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.VariantiAffidamentoDescrizioneVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.FornitoreAffidamentoVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.neoflux.MetaDataVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.util.AndCondition;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.util.Condition;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.util.FilterCondition;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.util.NullCondition;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiCCostantiVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiCTipoDocumentoIndexVO;
//import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiDMicroSezioneDinVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiDStatoAffidamentoVO;
//import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiRBandoLineaInterventVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiRFornitoreAffidamentoVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiRProgettiAppaltiVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiTAffidamentoChecklistVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiTAppaltoChecklistVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiTAppaltoVO;
//import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiTDocumentoIndexVO;
//import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiTDomandaVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiTEsitiNoteAffidamentVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiTFileEntitaVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiTProceduraAggiudicazVO;
//import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiTProgettoVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiTVariantiAffidamentiVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.services.mail.MailDAO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.services.mail.vo.MailVO;
//import it.csi.pbandi.pbservizit.pbandisrv.interfacecsi.archivio.ArchivioSrv;
import it.csi.pbandi.pbservizit.pbandisrv.interfacecsi.gestioneaffidamenti.GestioneAffidamentiSrv;
import it.csi.pbandi.pbservizit.pbandisrv.util.Constants;
import it.csi.pbandi.pbservizit.pbandisrv.util.validation.ValidatorInput;
import it.csi.pbandi.pbservizit.pbandiutil.common.DateUtil;
import it.csi.pbandi.pbservizit.pbandiutil.common.NumberUtil;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.jsoup.helper.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;

public class GestioneAffidamentiBusinessImpl extends BusinessImpl implements GestioneAffidamentiSrv {
	
	// Jira PBANDI-2776
	// Restituisce la somma degli importi rendicontabili 
	//  - dell'affidamento in input
	//  - e di tutti gli altri in stato 2,3 o 4 relativi allo stesso progetto.
	public Double findSommaImportiRendicontabili(Long idUtente, String identitaIride, Long idAppalto)
	throws CSIException, SystemException, UnrecoverableException, GestioneAffidamentiException {
		String[] nameParameter = { "idUtente", "identitaIride", "idAppalto" };
		ValidatorInput.verifyNullValue(nameParameter, idUtente, identitaIride, idAppalto);
		
		// Recupera l'id progetto dell'affidamento in input.
		PbandiRProgettiAppaltiVO filtro = new PbandiRProgettiAppaltiVO();
		filtro.setIdAppalto(new BigDecimal(idAppalto));
		filtro.setIdTipoDocumentoIndex(new BigDecimal(Constants.ID_TIPO_DOCUMENTO_INDEX_DICHIARAZIONE_AFFIDAMENTO));
		filtro = genericDAO.findSingleWhere(filtro);
		BigDecimal idProgetto = filtro.getIdProgetto();
		
		// Recupera gli affidamenti associati al progetto.
		PbandiRProgettiAppaltiVO filtro2 = new PbandiRProgettiAppaltiVO();
		filtro2.setIdProgetto(idProgetto);
		filtro2.setIdTipoDocumentoIndex(new BigDecimal(Constants.ID_TIPO_DOCUMENTO_INDEX_DICHIARAZIONE_AFFIDAMENTO));
		ArrayList<PbandiRProgettiAppaltiVO> lista = (ArrayList<PbandiRProgettiAppaltiVO>) genericDAO.findListWhere(filtro2);
		
		// Somma gli importi rendicontabili.
		Double somma = new Double(0);
		for (PbandiRProgettiAppaltiVO r : lista) {
			PbandiTAppaltoVO appalto = new PbandiTAppaltoVO();
			appalto.setIdAppalto(r.getIdAppalto());
			appalto = genericDAO.findSingleOrNoneWhere(appalto);
			if (appalto.getIdAppalto().intValue() == idAppalto.intValue() ||
				appalto.getIdStatoAffidamento().intValue() == 2 ||
				appalto.getIdStatoAffidamento().intValue() == 3 ||
				appalto.getIdStatoAffidamento().intValue() == 4) {
				if (appalto.getImpRendicontabile() != null) {
					somma = somma + appalto.getImpRendicontabile().doubleValue();
				}
			}
		}
		
		return somma;
	}
	
	// Jira PBANDI-2773
	public EsitoGestioneAffidamenti respingiAffidamento(Long idUtente, String identitaIride,Long idAppalto, String note)
		throws CSIException, SystemException, UnrecoverableException, GestioneAffidamentiException {
		
		String[] nameParameter = { "idUtente", "identitaIride", "idAppalto" };
		ValidatorInput.verifyNullValue(nameParameter, idUtente, identitaIride, idAppalto);
		
		EsitoGestioneAffidamenti esito = new EsitoGestioneAffidamenti();
		esito.setEsito(Boolean.TRUE);
		
		// Cancella eventuale bozza associata all'affidamento.
		try {
			checkListBusiness.eliminaBozzaCheckListDocumentaleAffidamento(idUtente, identitaIride, idAppalto);
		} catch (Exception e) {
			String msg = "Errore nella cancellazione della checklist in bozza.";
			logger.error(msg, e);
			throw new GestioneAffidamentiException(msg, e);
		}
		
		// Aggiorna lo stato dell'appalto in DA_INVIARE (id 1).
		try {
			PbandiTAppaltoVO appalto = new PbandiTAppaltoVO();
			appalto.setIdAppalto(new BigDecimal(idAppalto));
			appalto.setIdStatoAffidamento(new BigDecimal(Constants.ID_STATO_AFFIDAMENTO_DAINVIARE));
			appalto.setDtAggiornamento(DateUtil.getSysdate());
			appalto.setIdUtenteAgg(new BigDecimal(idUtente));		
			genericDAO.update(appalto);
		} catch (Exception e) {
			String msg = "Errore nella modifica dello stato dell'affidamento.";
			logger.error(msg, e);
			throw new GestioneAffidamentiException(msg, e);
		}
		
		// Tramite Appalto -> Proc Aggiudicazione, recupera l'id progetto.
		PbandiTAppaltoVO appaltoVO = new PbandiTAppaltoVO();
		appaltoVO.setIdAppalto(new BigDecimal(idAppalto));
		appaltoVO = genericDAO.findSingleWhere(appaltoVO);
		
		PbandiTProceduraAggiudicazVO proc = new PbandiTProceduraAggiudicazVO();
		proc.setIdProceduraAggiudicaz(appaltoVO.getIdProceduraAggiudicaz());
		proc = genericDAO.findSingleWhere(proc);
		
		// Invia una notifica al beneficiario.
		String importo = NumberUtil.getStringValue(appaltoVO.getImportoContratto());
		List<MetaDataVO>metaDatas = creaParametriNotificaRespingi(appaltoVO.getOggettoAppalto(), importo, note);
		
		logger.info("calling genericDAO.callProcedure().putNotificationMetadata....");
		genericDAO.callProcedure().putNotificationMetadata(metaDatas);
		
		logger.info("calling genericDAO.callProcedure().sendNotificationMessage....");
		String descrBreveTemplateNotifica=Notification.NOTIFICA_RESPINGI_AFFIDAMENTO;
		genericDAO.callProcedure().sendNotificationMessage2(proc.getIdProgetto(),descrBreveTemplateNotifica,Notification.BENEFICIARIO,idUtente,idAppalto);
		
		return esito;
	}
	
	private List<MetaDataVO> creaParametriNotificaRespingi(String oggetto, String importo, String note) {
		
		List<MetaDataVO> metaDatas = new ArrayList<MetaDataVO>();
		
		MetaDataVO metadata1 = new MetaDataVO(); 
		metadata1.setNome(Notification.DATA_RESPINGI_AFFIDAMENTO);
		metadata1.setValore(DateUtil.getDate(new Date()));
		metaDatas.add(metadata1);
		
		MetaDataVO metadata2 = new MetaDataVO(); 
		metadata2.setNome(Notification.OGGETTO_AFFIDAMENTO);
		metadata2.setValore(oggetto);
		metaDatas.add(metadata2);
		
		MetaDataVO metadata3 = new MetaDataVO(); 
		metadata3.setNome(Notification.IMP_AGGIUDICATO_AFFIDAMENTO);
		metadata3.setValore(importo);
		metaDatas.add(metadata3);
		
		MetaDataVO metadata4 = new MetaDataVO(); 
		metadata4.setNome(Notification.NOTE_RESPINGI_AFFIDAMENTO);
		metadata4.setValore(note);
		metaDatas.add(metadata4);
		
		return metaDatas;
	}

	public EsitoGestioneAffidamenti inviaInVerifica(Long idUtente,String identitaIride, ParamInviaInVerificaDTO paramInviaInVerificaDTO) throws CSIException,
			SystemException, UnrecoverableException,
			GestioneAffidamentiException {
		String[] nameParameter = { "idUtente", "identitaIride", "paramInviaInVerificaDTO" };
		ValidatorInput.verifyNullValue(nameParameter, idUtente, identitaIride, paramInviaInVerificaDTO);
		
		EsitoGestioneAffidamenti esito = new EsitoGestioneAffidamenti();
		esito.setEsito(Boolean.TRUE);
		
		logger.info("inviaInVerifica(): inizio");
		try {
			java.sql.Date oggi = DateUtil.getSysdate();
			String flagInviato = "S";
			Long idAppalto = paramInviaInVerificaDTO.getIdAppalto();
			
			// Aggiorna lo stato dell'appalto in IN_VERIFICA (id 2).
			logger.info("inviaInVerifica(): stato affidamento = IN_VERIFICA");
			PbandiTAppaltoVO appalto = new PbandiTAppaltoVO();
			appalto.setIdAppalto(new BigDecimal(idAppalto));			
			appalto.setIdStatoAffidamento(new BigDecimal(Constants.ID_STATO_AFFIDAMENTO_INVERIFICA));
			appalto.setDtAggiornamento(oggi);
			appalto.setIdUtenteAgg(new BigDecimal(idUtente));
			genericDAO.update(appalto);
			
			// Marca come inviati le varianti.
			logger.info("inviaInVerifica(): marca le varianti");
			PbandiTVariantiAffidamentiVO affidamentoVO = new PbandiTVariantiAffidamentiVO();
			affidamentoVO.setIdAppalto(new BigDecimal(idAppalto));
			List<PbandiTVariantiAffidamentiVO> affidamenti = genericDAO.findListWhere(affidamentoVO);
			for (PbandiTVariantiAffidamentiVO item : affidamenti) {
				if (nonAncoraInviato(item.getFlgInvioVerificaAffidamento())) {
					PbandiTVariantiAffidamentiVO vo = new PbandiTVariantiAffidamentiVO(item.getIdVariante());
					vo.setFlgInvioVerificaAffidamento(flagInviato);
					vo.setDtInvioVerificaAffidamento(oggi);
					genericDAO.update(vo);
				}
			}
			
			// Marca come inviati gli allegati.
			logger.info("inviaInVerifica(): marca gli allegati");
			List<ArchivioFileVO> allegati = getPbandiArchivioFileDAOImpl().getFilesAssociatedAffidamento(new BigDecimal(idAppalto));			
			for (ArchivioFileVO item : allegati) {				
				// Cerco il record PBANDI_T_FILE_ENTITA dove salvare il flag per invio verifica.
				PbandiTFileEntitaVO vo = new PbandiTFileEntitaVO();
				vo.setIdFileEntita(item.getIdFileEntita());
				vo = genericDAO.findSingleWhere(vo);
				// Setto i campi da salvare e aggiorno il record.				
				if (nonAncoraInviato(vo.getFlagEntita())) {
					vo.setFlagEntita(flagInviato);
					vo.setDtEntita(oggi);
					genericDAO.update(vo);
				}
			}
			
			// Marca come inviati i fornitori.
			logger.info("inviaInVerifica(): marca i fornitori");
			FornitoreAffidamentoVO fornitoreVO = new FornitoreAffidamentoVO();
			fornitoreVO.setIdAppalto(new BigDecimal(idAppalto));
			List<FornitoreAffidamentoVO> fornitori = genericDAO.findListWhere(fornitoreVO);
			for (FornitoreAffidamentoVO item : fornitori) {
				if (nonAncoraInviato(item.getFlgInvioVerificaAffidamento())) {
					FornitoreAffidamentoVO vo = new FornitoreAffidamentoVO();
					vo.setIdFornitore(item.getIdFornitore());
					vo.setIdAppalto(item.getIdAppalto());
					vo.setIdTipoPercettore(item.getIdTipoPercettore());
					vo.setFlgInvioVerificaAffidamento(flagInviato);
					vo.setDtInvioVerificaAffidamento(oggi);
					genericDAO.update(vo);
				}
			}
			
			// Invia una mail di notifica all'Istruttore affidamenti.
			this.inviaMail(paramInviaInVerificaDTO);
			
		} catch(Exception ex){
			String msg = "inviaInVerifica(): errore nel salvataggio dei dati. ";
			logger.error(msg, ex);
			throw new GestioneAffidamentiException(msg, ex);
		}	
		logger.info("inviaInVerifica(): fine");
		return esito;
	}
	
	// Invia una mail di notifica all'Istruttore affidamenti.
	private void inviaMail (ParamInviaInVerificaDTO paramInviaInVerificaDTO) throws Exception {
		
		String mittente = leggiCostante(Constants.COSTANTE_INDIRIZZO_EMAIL_MITTENTE);
		String destinatari = leggiCostante(Constants.COSTANTE_INDIRIZZO_EMAIL_AFFIDAMENTI);  // più mail separate da virgola
		
		// Passa da una stringa di mail separate da virgola ad una lista di stringhe.
		List<String> separatedMailAddresses = new ArrayList<String>();
		for (String mailAddress : destinatari.split(MailDAO.MAIL_ADDRESSES_SEPARATOR)) {
			separatedMailAddresses.add(mailAddress.trim());
		}
		
		//PbandiTProgettoVO progetto = getProgettoByIdAppalto(BigDecimal.valueOf(idAppalto));
		//PbandiRBandoLineaInterventVO bandoLinea = getBandoLineaByIdProgetto(progetto.getIdProgetto());
		PbandiTAppaltoVO appalto = new PbandiTAppaltoVO(new BigDecimal(paramInviaInVerificaDTO.getIdAppalto()));
		appalto = genericDAO.findSingleWhere(appalto);
		
		String oggi = DateUtil.getData();
		
		String oggetto = "Verifica Affidamento - "+paramInviaInVerificaDTO.getNomeBandoLinea()+" - Cod. Progetto "+paramInviaInVerificaDTO.getCodiceProgettoVisualizzato();
		//oggetto = oggetto.replace('“', '"');
		
		String testo = "Con la presente si notifica che in data "+oggi+" il beneficiario "+paramInviaInVerificaDTO.getBeneficiario()+" ha inviato in verifica l'affidamento con oggetto '"+appalto.getOggettoAppalto()+"' per il progetto '"+paramInviaInVerificaDTO.getTitoloProgetto()+"' bando '"+paramInviaInVerificaDTO.getNomeBandoLinea()+"'.\n\nI dettagli dell'affidamento sono consultabili sul Gestionale Finanziamenti \nall'indirizzo http://www.sistemapiemonte.it/finanziamenti/bandi/";
		//testo = testo.replace('“', '"');
		
		MailVO mailVO = new MailVO();
		mailVO.setToAddresses(separatedMailAddresses);
		mailVO.setFromAddress(mittente);
		mailVO.setSubject(oggetto);
		mailVO.setContent(testo);
		logger.shallowDump(mailVO, "info");
		mailDAO.send(mailVO);
	}
	
	private String leggiCostante(String attributo) throws Exception {
		String valore = null;
		try {
			PbandiCCostantiVO c = new PbandiCCostantiVO();
			c.setAttributo(attributo);
			c = genericDAO.findSingleWhere(c);
			valore = c.getValore();
			logger.info("Valore della costante "+attributo+" = "+valore);
			if (StringUtil.isBlank(valore)) {
				throw new Exception("Costante "+attributo+" non valorizzata.");
			}
		} catch (Exception e) {
			logger.error("ERRORE in leggiCostante(): "+e);
			throw new Exception("Costante "+attributo+" non valorizzata.");
		}
		return valore;
	}
	/*
	private PbandiTProgettoVO getProgettoByIdAppalto (BigDecimal idAppalto) throws Exception {
		PbandiTAppaltoVO appalto = new PbandiTAppaltoVO(idAppalto);
		appalto = genericDAO.findSingleWhere(appalto);
		
		PbandiTProceduraAggiudicazVO proc = new PbandiTProceduraAggiudicazVO(appalto.getIdProceduraAggiudicaz());
		proc = genericDAO.findSingleWhere(proc);
		
		PbandiTProgettoVO progetto = new PbandiTProgettoVO(proc.getIdProgetto());
		progetto = genericDAO.findSingleWhere(progetto);
		
		return progetto;
	}
	
	private PbandiRBandoLineaInterventVO getBandoLineaByIdProgetto (BigDecimal idProgetto) throws Exception {
		PbandiTDomandaVO domanda = new PbandiTDomandaVO(idProgetto);
		domanda = genericDAO.findSingleWhere(domanda);
		
		PbandiRBandoLineaInterventVO bandoLinea = new PbandiRBandoLineaInterventVO(domanda.getProgrBandoLineaIntervento());
		bandoLinea = genericDAO.findSingleWhere(bandoLinea);
		
		return bandoLinea;
	}
	*/
	private boolean nonAncoraInviato (String flag) {
		return (StringUtil.isBlank(flag) || "N".equalsIgnoreCase(flag));
	}
	
	// Verifica se l'affidamento è presente nella PBANDI_T_AFFIDAMENTO_CHECKLIST.
	private boolean affidamentoChecklistMancante(AffidamentoDTO affidamentoDTO) {		
		
		PbandiTAffidamentoChecklistVO affChecklist = new PbandiTAffidamentoChecklistVO();
		if (affidamentoDTO.getIdNorma() != null)
			affChecklist.setIdNorma(new BigDecimal(affidamentoDTO.getIdNorma()));
		if (affidamentoDTO.getIdTipoAffidamento() != null)
			affChecklist.setIdTipoAffidamento(new BigDecimal(affidamentoDTO.getIdTipoAffidamento()));
		if (affidamentoDTO.getIdTipologiaAppalto() != null)
			affChecklist.setIdTipoAppalto(new BigDecimal(affidamentoDTO.getIdTipologiaAppalto()));
		if (affidamentoDTO.getProceduraAggiudicazioneDTO() != null &&
			affidamentoDTO.getProceduraAggiudicazioneDTO().getIdTipologiaAggiudicaz() != null)
			affChecklist.setIdTipologiaAggiudicaz(new BigDecimal(affidamentoDTO.getProceduraAggiudicazioneDTO().getIdTipologiaAggiudicaz()));
		affChecklist.setSopraSoglia(affidamentoDTO.getSopraSoglia());
		
		List<PbandiTAffidamentoChecklistVO> lista = null;
		if (StringUtil.isBlank(affChecklist.getSopraSoglia())) {
			NullCondition<PbandiTAffidamentoChecklistVO> nullIdLineaCondition = new NullCondition<PbandiTAffidamentoChecklistVO>(
					PbandiTAffidamentoChecklistVO.class,"sopraSoglia"); 
			AndCondition<PbandiTAffidamentoChecklistVO> filtro = new AndCondition<PbandiTAffidamentoChecklistVO>
			(new FilterCondition<PbandiTAffidamentoChecklistVO>(affChecklist),nullIdLineaCondition);
			lista = genericDAO.findListWhere(filtro);
		} else {
			lista = genericDAO.findListWhere(affChecklist);
		}
		
		return (lista.size() == 0);	
	}
	
	public EsitoGestioneAffidamenti salvaAffidamento(Long idUtente,
			String identitaIride, AffidamentoDTO affidamentoDTO) throws CSIException,
			SystemException, UnrecoverableException,
			GestioneAffidamentiException {
		
		String[] nameParameter = { "idUtente", "identitaIride", "affidamentoDTO" };
		ValidatorInput.verifyNullValue(nameParameter, idUtente, identitaIride, affidamentoDTO);
		
		EsitoGestioneAffidamenti esito = new EsitoGestioneAffidamenti();
		esito.setEsito(Boolean.TRUE);
		
		logger.info("salvaAffidamento(): inizio");
		try {			
				
			// Verifica se l'affidamento è presente nella PBANDI_T_AFFIDAMENTO_CHECKLIST.
			if (this.affidamentoChecklistMancante(affidamentoDTO)) {
				logger.error("ERRORE: l'affidamento non ha alcuna checklist associata.");
				esito.setEsito(false);
				//esito.setMessage("L&rsquo;affidamento non ha alcuna checklist associata.");
				esito.setMessage("Salvataggio non effettuato!");
				return esito;
			}	
			
			boolean nuovoAppalto = (affidamentoDTO.getIdAppalto() == null);
			
			// Salva la procedura di aggiudicazione in PBANDI_T_PROCEDURA_AGGIUDICAZ.
			// NOTA: va salvata prima prima la proc di agg poichè il suo ID va inserito poi in PBANDI_T_APPALTO.
			BigDecimal idProcAgg = this.salvaProceduraAggiudicazione(idUtente, affidamentoDTO.getProceduraAggiudicazioneDTO());
			affidamentoDTO.setIdProceduraAggiudicaz(idProcAgg.longValue());
			affidamentoDTO.getProceduraAggiudicazioneDTO().setIdProceduraAggiudicaz(idProcAgg.longValue());
			
			// Salva l'affidamento in PBANDI_T_APPALTO.
			BigDecimal idAppalto = this.salvaAppalto(idUtente, affidamentoDTO);
			affidamentoDTO.setIdAppalto(idAppalto.longValue());
			
			if (nuovoAppalto) {
				// Insert in PBANDI_R_PROGETTI_APPALTI.
				PbandiRProgettiAppaltiVO progAppaltiVO = new PbandiRProgettiAppaltiVO();
				progAppaltiVO.setIdAppalto(idAppalto);
				progAppaltiVO.setIdProgetto(new BigDecimal(affidamentoDTO.getIdProgetto()));
				progAppaltiVO.setIdTipoDocumentoIndex(new BigDecimal(Constants.ID_TIPO_DOCUMENTO_INDEX_DICHIARAZIONE_AFFIDAMENTO));
				genericDAO.insert(progAppaltiVO);
			}
			
			// Restituisce in output l'id dell'affidamento e della procedura di aggiudicazione.
			esito.setAffidamentoDTO(affidamentoDTO);
			
		} catch(Exception ex){
			String msg = "salvaAffidamento(): errore nel salvataggio dell'affidamento. ";
			logger.error(msg, ex);
			throw new GestioneAffidamentiException(msg, ex);
		}	
		logger.info("salvaAffidamento(): fine");
		return esito;
	}
	
	// Salva l'affidamento in PBANDI_T_APPALTO.
	private BigDecimal salvaAppalto(Long idUtente, AffidamentoDTO affidamentoDTO) throws Exception {
		java.sql.Date oggi = DateUtil.getSysdate();
		// Salva l'affidamento in PBANDI_T_APPALTO.
		PbandiTAppaltoVO appaltoVO = new PbandiTAppaltoVO();
		appaltoVO = beanUtil.transform(affidamentoDTO, PbandiTAppaltoVO.class);
		logger.shallowDump(affidamentoDTO, "info");
		logger.shallowDump(appaltoVO, "info");
		if (appaltoVO.getIdAppalto() == null) {
			// Inserimento.
			appaltoVO.setDtInserimento(oggi);
			appaltoVO.setIdUtenteIns(new BigDecimal(idUtente));
			appaltoVO = genericDAO.insert(appaltoVO);
			if (appaltoVO.getIdAppalto() == null) {
				throw new Exception("L'inserimento non ha restituito alcun IdAppalto.");
			} else {
				logger.info("salvaAppalto(): inserito record con id "+appaltoVO.getIdAppalto());
			}
		} else {
			// Modifica.
			appaltoVO.setDtAggiornamento(oggi);
			appaltoVO.setIdUtenteAgg(new BigDecimal(idUtente));
			genericDAO.updateNullables(appaltoVO);
		}
		return appaltoVO.getIdAppalto();
	}
	
	// Salva la procedura di aggiudicazione in PBANDI_T_PROCEDURA_AGGIUDICAZ.
	private BigDecimal salvaProceduraAggiudicazione(Long idUtente, ProceduraAggiudicazioneDTO procAggDTO) throws Exception {
		java.sql.Date oggi = DateUtil.getSysdate();
		PbandiTProceduraAggiudicazVO procAggVO = new PbandiTProceduraAggiudicazVO(); 
		procAggVO = beanUtil.transform(procAggDTO, PbandiTProceduraAggiudicazVO.class);
		//logger.shallowDump(procAggDTO, "info");
		//logger.shallowDump(procAggVO, "info");
		if (procAggVO.getIdProceduraAggiudicaz() == null) {
			// Inserimento.
			procAggVO.setDtInizioValidita(oggi);
			procAggVO.setIdUtenteIns(new BigDecimal(idUtente));
			procAggVO = genericDAO.insert(procAggVO);
			if (procAggVO.getIdProceduraAggiudicaz() == null) {
				throw new Exception("L'inserimento non ha restituito alcun IdProceduraAggiudicaz.");
			} else {
				logger.info("salvaProceduraAggiudicazione(): inserito record con id "+procAggVO.getIdProceduraAggiudicaz());
			}
		} else {
			// Modifica.
			procAggVO.setIdUtenteAgg(new BigDecimal(idUtente));
			genericDAO.updateNullables(procAggVO);
		}
		return procAggVO.getIdProceduraAggiudicaz();
	}
	
	// Elimino il record in PBANDI_T_APPALTO con l'id in input.
	public EsitoGestioneAffidamenti eliminaAffidamento(Long idUtente,
			String identitaIride, Long idAppalto) throws CSIException,
			SystemException, UnrecoverableException,
			GestioneAffidamentiException {
		
		String[] nameParameter = { "idUtente", "identitaIride", "idAppalto" };
		ValidatorInput.verifyNullValue(nameParameter, idUtente, identitaIride, idAppalto);
		
		EsitoGestioneAffidamenti esito = new EsitoGestioneAffidamenti();
		esito.setEsito(true);
		try{
			logger.info("eliminaAffidamento(): idAppalto = "+idAppalto);
			
			// Cancello le varianti associate.
			PbandiTVariantiAffidamentiVO variante = new PbandiTVariantiAffidamentiVO();
			variante.setIdAppalto(new BigDecimal(idAppalto));
			genericDAO.deleteWhere(new FilterCondition<PbandiTVariantiAffidamentiVO>(variante));
			
			// Cancello gli allegati associati.
			PbandiTFileEntitaVO allegato = new PbandiTFileEntitaVO();
			allegato.setIdTarget(new BigDecimal(idAppalto));
			allegato.setIdEntita(new BigDecimal(Constants.ID_ENTITA_PBANDI_T_APPALTO));
			genericDAO.deleteWhere(new FilterCondition<PbandiTFileEntitaVO>(allegato));
			
			// Cancello i fornitori associati.
			PbandiRFornitoreAffidamentoVO fornitore = new PbandiRFornitoreAffidamentoVO();
			fornitore.setIdAppalto(new BigDecimal(idAppalto));
			genericDAO.deleteWhere(new FilterCondition<PbandiRFornitoreAffidamentoVO>(fornitore));
			
			// Leggo l'affidamento per ottenere l'id della procedura di aggiudicazione associata.
			PbandiTAppaltoVO appalto = new PbandiTAppaltoVO();
			appalto.setIdAppalto(new BigDecimal(idAppalto));
			appalto = genericDAO.findSingleWhere(appalto);
			if (appalto == null || appalto.getIdAppalto() == null)
				throw new GestioneAffidamentiException("Cancellazione affidamento fallita: Affidamento "+idAppalto+" non trovato.");
			if (appalto.getIdProceduraAggiudicaz() == null)
				throw new GestioneAffidamentiException("Cancellazione affidamento fallita: IdProceduraAggiudicaz non trovato.");
			
			// Leggo la procedura di aggiudicazione per recuperare l'id del progetto.
			PbandiTProceduraAggiudicazVO procAgg = new PbandiTProceduraAggiudicazVO();
			procAgg.setIdProceduraAggiudicaz(appalto.getIdProceduraAggiudicaz());
			procAgg = genericDAO.findSingleWhere(procAgg);
			if (procAgg == null || procAgg.getIdProceduraAggiudicaz() == null)
				throw new GestioneAffidamentiException("Cancellazione affidamento fallita: ProceduraAggiudicaz "+procAgg.getIdProceduraAggiudicaz()+" non trovata.");
			if (procAgg.getIdProgetto() == null)
				throw new GestioneAffidamentiException("Cancellazione affidamento fallita: IdProgetto non trovato.");
						
			// Cancello relazione in PBANDI_R_PROGETTI_APPALTI.
			PbandiRProgettiAppaltiVO pbandiRProgettiAppaltiVO = new PbandiRProgettiAppaltiVO();
			pbandiRProgettiAppaltiVO.setIdAppalto(appalto.getIdAppalto());
			pbandiRProgettiAppaltiVO.setIdProgetto(procAgg.getIdProgetto());
			pbandiRProgettiAppaltiVO = genericDAO.findSingleWhere(pbandiRProgettiAppaltiVO);
			if (!genericDAO.delete(pbandiRProgettiAppaltiVO))
				throw new GestioneAffidamentiException("Cancellazione relazione affidamento-progetto fallita."); 			
			
			// Cancello l'affidamento in PBANDI_T_APPALTO.
			PbandiTAppaltoVO vo1 = new PbandiTAppaltoVO();
			vo1.setIdAppalto(appalto.getIdAppalto());
			if (!genericDAO.delete(vo1))
				throw new GestioneAffidamentiException("Cancellazione affidamento fallita."); 
			
			// Cancello la procedura di aggiudicazione associata all'affidamento in PBANDI_T_PROCEDURA_AGGIUDICAZ.
			PbandiTProceduraAggiudicazVO vo2 = new PbandiTProceduraAggiudicazVO();
			vo2.setIdProceduraAggiudicaz(appalto.getIdProceduraAggiudicaz());
			if (!genericDAO.delete(vo2))
				throw new GestioneAffidamentiException("Cancellazione procedura aggiudicazione fallita."); 
		
		} catch(GestioneAffidamentiException ex) {
			throw ex;
		} catch(Exception ex) {
			String msg = "Errore nell'eliminazione dell'affidamento.";
			logger.error(msg, ex);
			throw new GestioneAffidamentiException(msg, ex);
		}		
		return esito;
	}
	
	// Recupera un affidamento con i dati correlati (proc. aggiudicaz., varianti, etc).
	public AffidamentoDTO findAffidamento(Long idUtente,
			String identitaIride, Long idAppalto) throws CSIException,
			SystemException, UnrecoverableException,
			GestioneAffidamentiException {
		
		String[] nameParameter = { "idUtente", "identitaIride", "idAppalto" };
		ValidatorInput.verifyNullValue(nameParameter, idUtente, identitaIride, idAppalto);
					
		try{
			logger.info("findAffidamento(): idAppalto = "+idAppalto);
			
			// PBANDI_T_APPALTO.
			PbandiTAppaltoVO appalto = new PbandiTAppaltoVO();
			appalto.setIdAppalto(new BigDecimal(idAppalto));
			appalto = genericDAO.findSingleWhere(appalto);
			if (appalto == null || appalto.getIdAppalto() == null)
				throw new GestioneAffidamentiException("Affidamento "+idAppalto+" non trovato.");
			
			// PBANDI_D_STATO_AFFIDAMENTO (aggiunto per la PBANDI-2775).
			String descStatoAffidamento = null;
			if (appalto.getIdStatoAffidamento() != null) {
				PbandiDStatoAffidamentoVO stato = new PbandiDStatoAffidamentoVO();
				stato.setIdStatoAffidamento(appalto.getIdStatoAffidamento());
				stato = genericDAO.findSingleWhere(stato);
				descStatoAffidamento = stato.getDescStatoAffidamento();
			}
			
			// PBANDI_T_PROCEDURA_AGGIUDICAZ.
			PbandiTProceduraAggiudicazVO procAgg = new PbandiTProceduraAggiudicazVO();
			procAgg.setIdProceduraAggiudicaz(appalto.getIdProceduraAggiudicaz());
			procAgg = genericDAO.findSingleWhere(procAgg);
			if (procAgg == null || procAgg.getIdProceduraAggiudicaz() == null)
				throw new GestioneAffidamentiException("Proceudra di aggiudicazione "+appalto.getIdProceduraAggiudicaz()+" non trovato.");
			
			// VARIANTI.
			VarianteAffidamentoDTO affdamento = new VarianteAffidamentoDTO(); 
			affdamento.setIdAppalto(idAppalto);
			VarianteAffidamentoDTO[] varianti = this.findVarianti(idUtente, identitaIride, affdamento);
			
			// FORNITORI.
			FornitoreAffidamentoDTO fornitore = new FornitoreAffidamentoDTO(); 
			fornitore.setIdAppalto(idAppalto);
			FornitoreAffidamentoDTO[] fornitori = this.findFornitoriAffidamento(idUtente, identitaIride, fornitore);
			
			// Trasforma i VO in DTO.			
			ProceduraAggiudicazioneDTO procAggDTO = new ProceduraAggiudicazioneDTO();
			procAggDTO = beanUtil.transform(procAgg, ProceduraAggiudicazioneDTO.class);
			
			AffidamentoDTO affDTO = new AffidamentoDTO();
			affDTO = beanUtil.transform(appalto, AffidamentoDTO.class);
			affDTO.setDescStatoAffidamento(descStatoAffidamento);
			affDTO.setProceduraAggiudicazioneDTO(procAggDTO);
			affDTO.setVarianti(varianti);
			affDTO.setFornitori(fornitori);
			// Jira PBANDI-2773
			affDTO.setRespingibile(this.isAffidamentoRespingibile(appalto.getIdAppalto()));
			
			//logger.shallowDump(affDTO, "info");
			
			return affDTO;
			
		} catch(GestioneAffidamentiException ex) {
			throw ex;
		} catch(Exception ex) {
			String msg = "Errore nell'eliminazione dell'affidamento.";
			logger.error(msg, ex);
			throw new GestioneAffidamentiException(msg, ex);
		}
	}
	
	// Jira PBANDI-2773: restituisce true se l'affidamento può essere respinto dall'istruttore.
	private boolean isAffidamentoRespingibile(BigDecimal idAffidamento) {
		// Verifica che non esistano esiti associati all'affidamento.
		PbandiTAppaltoChecklistVO acVO = new PbandiTAppaltoChecklistVO();
		acVO.setIdAppalto(idAffidamento);
		ArrayList<PbandiTAppaltoChecklistVO> listaChecklist = null;
		listaChecklist = (ArrayList<PbandiTAppaltoChecklistVO>) genericDAO.findListWhere(acVO);				
		for (PbandiTAppaltoChecklistVO ac : listaChecklist) {
			PbandiTEsitiNoteAffidamentVO esitoVO = new PbandiTEsitiNoteAffidamentVO();
			esitoVO.setIdChecklist(ac.getIdChecklist());
			ArrayList<PbandiTEsitiNoteAffidamentVO> esiti = (ArrayList<PbandiTEsitiNoteAffidamentVO>) genericDAO.findListWhere(esitoVO);
			// Se esiste un esito, l'affidamento non può essere respinto.
			if (esiti.size() > 0)
				return false;
		}
		return true;
	}
	
	// Restituisce gli affidamenti di un progetto con i relativi fornitori.
	// E' atteso popolato solo FiltroRicercaAffidamentiDTO.idProgetto.
	public AffidamentoDTO[] findElencoAffidamentiConFornitori(
			java.lang.Long idUtente,
			java.lang.String identitaIride,
			it.csi.pbandi.pbservizit.pbandisrv.dto.gestioneaffidamenti.FiltroRicercaAffidamentiDTO filtro) 
	throws CSIException, SystemException, UnrecoverableException, GestioneAffidamentiException {
		
		String[] nameParameter = { "idUtente", "identitaIride", "filtro" };
		ValidatorInput.verifyNullValue(nameParameter, idUtente, identitaIride, filtro);
		logger.info("findElencoAffidamentiConFornitori(): idProgetto = "+filtro.getIdProgetto()+"; ruolo = "+filtro.getCodiceRuolo());
		
		AffidamentoDTO[] elenco = null;
		try {
			// Legge gli affidamenti del progetto.
			elenco = this.findElencoAffidamenti(idUtente, identitaIride, filtro);
			
			// Trovo i fornitori di ciascun affidamento.
			for (AffidamentoDTO affidamento : elenco) {
				FornitoreAffidamentoDTO filtroFornitore = new FornitoreAffidamentoDTO();
				filtroFornitore.setIdAppalto(affidamento.getIdAppalto());
				FornitoreAffidamentoDTO[] fornitori = this.findFornitoriAffidamento(idUtente, identitaIride, filtroFornitore);
				affidamento.setFornitori(fornitori);
			}
			
		} catch (Exception e) {
			logger.warn("findElencoAffidamentiConFornitori(): "+e);
		}		
		return elenco;
	}
	
	public AffidamentoDTO[] findElencoAffidamenti(
			java.lang.Long idUtente,
			java.lang.String identitaIride,
			it.csi.pbandi.pbservizit.pbandisrv.dto.gestioneaffidamenti.FiltroRicercaAffidamentiDTO filtro) 
	throws CSIException, SystemException, UnrecoverableException, GestioneAffidamentiException {
		
		String[] nameParameter = { "idUtente", "identitaIride", "filtro" };
		ValidatorInput.verifyNullValue(nameParameter, idUtente, identitaIride, filtro);
		logger.info("findElencoAffidamenti(): idProgetto = "+filtro.getIdProgetto()+"; ruolo = "+filtro.getCodiceRuolo());
		
		AffidamentoDTO[] elenco = null;
		try {
			// Legge gli affidamenti del progetto.
			List<AffidamentoVO> lista = null;
			AffidamentoVO vo = new AffidamentoVO();
			vo.setIdProgetto(new BigDecimal(filtro.getIdProgetto()));
			vo.setIdTipoDocumentoIndex(new BigDecimal(Constants.ID_TIPO_DOCUMENTO_INDEX_DICHIARAZIONE_AFFIDAMENTO));
			lista = genericDAO.findListWhere(vo);
			
			// Se ruolo valorizzato e diverso da beneficiario, vanno tolti gli affidamenti in stato DAINVIARE.
			if (!StringUtil.isBlank(filtro.getCodiceRuolo()) &&
				!Constants.DESCRIZIONE_BREVE_TIPO_ANAGRAFICA_BEN_MASTER.equalsIgnoreCase(filtro.getCodiceRuolo()) &&
				!Constants.DESCRIZIONE_BREVE_TIPO_ANAGRAFICA_PERSONA_FISICA.equalsIgnoreCase(filtro.getCodiceRuolo())) {
				this.selezionaAffidamentiPerIstruttore(lista);
			}
			
			if (lista != null) {
				elenco = beanUtil.transform(lista, AffidamentoDTO.class);

				// Jira PBANDI-2829: serve sotto.
				BigDecimal idTipoDocChecklistDocumentale = decodificheManager.decodeDescBreve(
						PbandiCTipoDocumentoIndexVO.class, Constants.COD_TIPO_CHEKCLIST_AFFIDAMENTO_DOCUMENTALE);	// Jira Pbandi-2859
				logger.info("getEsitoChecklistAffidamento(): idTipoDocChecklistDocumentale = "+idTipoDocChecklistDocumentale);
				
				// Popola i campi NumFornitoriAssociati, NumAllegatiAssociati e EsisteAllegatoNonInviato.
				for (AffidamentoDTO dto : elenco) {

					FornitoreAffidamentoDTO fornitore = new FornitoreAffidamentoDTO(); 
					fornitore.setIdAppalto(dto.getIdAppalto());
					FornitoreAffidamentoDTO[] fornitori = this.findFornitoriAffidamento(idUtente, identitaIride, fornitore);
					dto.setNumFornitoriAssociati(new Long(fornitori.length));
						
					FileDTO[] allegati = null;
					allegati = archivioBusiness.getFilesAssociatedAffidamento(idUtente, identitaIride, dto.getIdAppalto());					
					dto.setNumAllegatiAssociati(new Long(allegati.length));
					
					dto.setEsisteAllegatoNonInviato(false);
					for (FileDTO f : allegati) {
						if (f.getDtEntita() == null)
							dto.setEsisteAllegatoNonInviato(true);
					}
					
					// Jira PBANDI-2829: legge l'eventuale esito (positivo\negativo).									
					this.popolaEsitiChecklistAffidamento(dto, idTipoDocChecklistDocumentale);
					logger.info("popolaEsitiChecklistAffidamento(): IdAppalto = "+dto.getIdAppalto()+"; fase1Esito = "+dto.getFase1Esito()+"; fase1Rettifica = "+dto.getFase1Rettifica()+"; fase2Esito = "+dto.getFase2Esito()+"; fase2Rettifica = "+dto.getFase2Rettifica());
				}
			}			
			
		} catch (Exception e) {
			logger.warn("findElencoAffidamenti(): "+e);
		}
		
		return elenco;
	}
	
	// Jira PBANDI-2829: legge l'eventuale esito (positivo\negativo).
	private void popolaEsitiChecklistAffidamento(AffidamentoDTO dto, BigDecimal idTipoDocChecklistDocumentale) {
		logger.info("popolaEsitiChecklistAffidamento(): idTipoDocChecklistDocumentale = "+idTipoDocChecklistDocumentale+"; IdAppalto = "+dto.getIdAppalto()+"; getOggettoAppalto = "+dto.getOggettoAppalto());
		try {
			Long idAffidamento = dto.getIdAppalto();
			Long idProgetto = dto.getIdProgetto();
			
			// Jira PBANDI-2863: prima usava DocumentoIndexMaxVersioneVO, ora DocumentoIndexMaxVersioneDefinitivoVO.
			// poichè non trovava gli esiti se l'ultima checklist salvata era una bozza.
			DocumentoIndexMaxVersioneDefinitivoVO documentoIndexVO = new DocumentoIndexMaxVersioneDefinitivoVO();
			documentoIndexVO.setIdEntita(documentoManager.getIdEntita(PbandiTAppaltoVO.class));
			documentoIndexVO.setIdProgetto(BigDecimal.valueOf(idProgetto));
			documentoIndexVO.setIdTarget(BigDecimal.valueOf(idAffidamento));
			documentoIndexVO.setIdTipoDocumentoIndex(idTipoDocChecklistDocumentale);
			documentoIndexVO.setIdStatoTipoDocIndex(null);
			DocumentoIndexMaxVersioneDefinitivoVO docIndex = genericDAO.findSingleOrNoneWhere(documentoIndexVO) ;
			
			if (docIndex == null) {
				logger.info("popolaEsitiChecklistAffidamento(): idDocumentoIndex NON TROVATO.");
				return;
			}
			BigDecimal idDocumentoIndex = docIndex.getIdDocumentoIndex(); 
			logger.info("popolaEsitiChecklistAffidamento(): idDocumentoIndex = "+idDocumentoIndex);
			
			CheckListAppaltoVO checkListAppaltoVO = new CheckListAppaltoVO();
			checkListAppaltoVO.setIdAppalto(new BigDecimal(idAffidamento));
			checkListAppaltoVO.setIdDocumentoIndex(idDocumentoIndex);
			List<CheckListAppaltoVO> list = genericDAO.where(Condition.filterBy(checkListAppaltoVO)).select();
			
			BigDecimal idChecklist = null;
			if (list.size() == 1) {
				idChecklist = list.get(0).getIdChecklist();
			}
			logger.info("popolaEsitiChecklistAffidamento(): list.size() = "+list.size()+"; idChecklist = "+idChecklist);
			
			PbandiTEsitiNoteAffidamentVO filtroEsito = new PbandiTEsitiNoteAffidamentVO();
			filtroEsito.setIdChecklist(idChecklist);
			List<PbandiTEsitiNoteAffidamentVO> esiti = genericDAO.findListWhere(filtroEsito);
					
			dto.setFase1Esito("");
			dto.setFase1Rettifica("");
			dto.setFase2Esito("");
			dto.setFase2Rettifica("");
			for (PbandiTEsitiNoteAffidamentVO esito : esiti) {
				if (esito.getFase().intValue() == 1) {
					dto.setFase1Esito(esito.getEsito());
					dto.setFase1Rettifica(esito.getFlagRettifica());	// Jira PBANDI-2829.
				} else if (esito.getFase().intValue() == 2) {
					dto.setFase2Esito(esito.getEsito());
					dto.setFase2Rettifica(esito.getFlagRettifica());	// Jira PBANDI-2829.
				} 
			}
			logger.info("popolaEsitiChecklistAffidamento(): fase1Esito = "+dto.getFase1Esito()+"; fase1Rettifica = "+dto.getFase1Rettifica()+"; fase2Esito = "+dto.getFase2Esito()+"; fase2Rettifica = "+dto.getFase2Rettifica());
		} catch (Exception e){
			logger.error("popolaEsitiChecklistAffidamento(): ERRORE: "+e);
		}
	}
	
	private void selezionaAffidamentiPerIstruttore(List<AffidamentoVO> lista) {
		if (lista != null) {
			Iterator<AffidamentoVO> iterator = lista.iterator();
			while (iterator.hasNext()) {
				AffidamentoVO item = iterator.next();
				int idStato = item.getIdStatoAffidamento().intValue();
				if (idStato == Constants.ID_STATO_AFFIDAMENTO_DAINVIARE) {
					iterator.remove();	
				}
			}
		}
	}

	public EsitoGestioneAffidamenti creaVariante(Long idUtente,
			String identitaIride, VarianteAffidamentoDTO varianteDTO) throws CSIException,
			SystemException, UnrecoverableException,
			GestioneAffidamentiException {
		
		String[] nameParameter = { "idUtente", "identitaIride", "varianteDTO" };
		ValidatorInput.verifyNullValue(nameParameter, idUtente, identitaIride, varianteDTO);
		
		EsitoGestioneAffidamenti esito = new EsitoGestioneAffidamenti();
		esito.setEsito(Boolean.FALSE);		
		try{
			PbandiTVariantiAffidamentiVO newVariante = beanUtil.transform(varianteDTO, PbandiTVariantiAffidamentiVO.class);
			if(newVariante != null){
				if (newVariante.getIdVariante() == null)
					genericDAO.insert(newVariante);
				else 
					genericDAO.update(newVariante);
				esito.setEsito(Boolean.TRUE);
			}
		}catch(Exception ex){
			logger.error("[CreaVariante] errore nel salvataggio della Variante ", ex);
			throw new GestioneAffidamentiException("[CreaVariante] errore nel salvataggio della Variante ", ex);
		}		
		return esito;
	}

	public EsitoGestioneAffidamenti eliminaVariante(Long idUtente,
			String identitaIride, Long idVariante) throws CSIException,
			SystemException, UnrecoverableException,
			GestioneAffidamentiException {
		String[] nameParameter = { "idUtente", "identitaIride", "idVariante" };
		ValidatorInput.verifyNullValue(nameParameter, idUtente, identitaIride, idVariante);
		EsitoGestioneAffidamenti esito = new EsitoGestioneAffidamenti();
		esito.setEsito(Boolean.FALSE);
		
		try{
			PbandiTVariantiAffidamentiVO varianteToDelete = new PbandiTVariantiAffidamentiVO(new BigDecimal(idVariante));
			Boolean esitoOperatione = genericDAO.delete(varianteToDelete);
			esito.setEsito(esitoOperatione);
			
		}catch(Exception ex){
			logger.error("[eliminaVariante] errore nell'eliminazione della Variante ", ex);
			throw new GestioneAffidamentiException("[eliminaVariante] errore nell'eliminazione della Variante ", ex);
		}		
		return esito;
	}

	public VarianteAffidamentoDTO[] findVarianti(Long idUtente, String identitaIride,
			VarianteAffidamentoDTO filtroVarianteDTO) throws CSIException,
			SystemException, UnrecoverableException,
			GestioneAffidamentiException {
		String[] nameParameter = { "idUtente", "identitaIride", "filtroVarianteDTO" };
		ValidatorInput.verifyNullValue(nameParameter, idUtente, identitaIride, filtroVarianteDTO);
		
		EsitoGestioneAffidamenti esito = new EsitoGestioneAffidamenti();		
		try{
			VariantiAffidamentoDescrizioneVO filtroSql = beanUtil.transform(filtroVarianteDTO, VariantiAffidamentoDescrizioneVO.class);
			return beanUtil.transform(genericDAO.findListWhere(filtroSql), VarianteAffidamentoDTO.class);
			
		}catch(Exception ex){
			logger.error("[findVarianti] errore nell'ottenimento delle varianti ", ex);
			throw new GestioneAffidamentiException("[findVarianti] errore nell'ottenimento delle varianti ", ex);
		}		
	}
	
	@Autowired
	private GenericDAO genericDAO;

	public void setGenericDAO(GenericDAO genericDAO) {
		this.genericDAO = genericDAO;
	}

	public GenericDAO getGenericDAO() {
		return genericDAO;
	}
	
	private MailDAO mailDAO;

	public MailDAO getMailDAO() {
		return mailDAO;
	}

	public void setMailDAO(MailDAO mailDAO) {
		this.mailDAO = mailDAO;
	}

	public EsitoGestioneAffidamenti creaFornitoreAffidamento(Long idUtente,
			String identitaIride,
			FornitoreAffidamentoDTO fornitoreAffidamentoDTO)
			throws CSIException, SystemException, UnrecoverableException,
			GestioneAffidamentiException {
		
		String[] nameParameter = { "idUtente", "identitaIride", "fornitoreAffidamentoDTO" };
		ValidatorInput.verifyNullValue(nameParameter, idUtente, identitaIride, fornitoreAffidamentoDTO);
		
		EsitoGestioneAffidamenti esito = new EsitoGestioneAffidamenti();
		esito.setEsito(Boolean.FALSE);		
		try{
			
			PbandiRFornitoreAffidamentoVO vo = beanUtil.transform(fornitoreAffidamentoDTO, PbandiRFornitoreAffidamentoVO.class);
			if(vo == null)
				throw new Exception("fornitore nullo.");
			
			vo = genericDAO.insert(vo);
			if(vo == null)
				throw new Exception("Fallita insert su pbandi_r_fornitore_affidamento.");
			
			esito.setEsito(Boolean.TRUE);
			
		}catch(Exception ex){
			logger.error("[creaFornitoreAffidamento] errore nel salvataggio del FornitoreAffidamento ", ex);
			throw new GestioneAffidamentiException("[creaFornitoreAffidamento] errore nel salvataggio del FornitoreAffidamento ", ex);
		}		
		return esito;
	}

	public EsitoGestioneAffidamenti eliminaFornitoreAffidamento(Long idUtente,
			String identitaIride, FornitoreAffidamentoDTO fornitoreAffidamentoDTO)
			throws CSIException, SystemException, UnrecoverableException,
			GestioneAffidamentiException {
		
		String[] nameParameter = { "idUtente", "identitaIride", "fornitoreAffidamentoDTO" };
		ValidatorInput.verifyNullValue(nameParameter, idUtente, identitaIride, fornitoreAffidamentoDTO);
		
		EsitoGestioneAffidamenti esito = new EsitoGestioneAffidamenti();
		esito.setEsito(Boolean.FALSE);		
		try{
			PbandiRFornitoreAffidamentoVO vo = new PbandiRFornitoreAffidamentoVO();
			vo.setIdFornitore(new BigDecimal(fornitoreAffidamentoDTO.getIdFornitore()));
			vo.setIdAppalto(new BigDecimal(fornitoreAffidamentoDTO.getIdAppalto()));
			vo.setIdTipoPercettore(new BigDecimal(fornitoreAffidamentoDTO.getIdTipoPercettore()));
			Boolean esitoOperatione = genericDAO.delete(vo);
			esito.setEsito(esitoOperatione);
			
		}catch(Exception ex){
			logger.error("[eliminaFornitoreAffidamento] errore nell'eliminazione del fornitore.", ex);
			throw new GestioneAffidamentiException("[eliminaVariante] errore nell'eliminazione del fornitore.", ex);
		}		
		return esito;
	}

	public FornitoreAffidamentoDTO[] findFornitoriAffidamento(Long idUtente,
			String identitaIride,
			FornitoreAffidamentoDTO filtroFornitoreAffidamentoDTO)
			throws CSIException, SystemException, UnrecoverableException,
			GestioneAffidamentiException {
		
		String[] nameParameter = { "idUtente", "identitaIride", "filtroFornitoreAffidamentoDTO" };
		ValidatorInput.verifyNullValue(nameParameter, idUtente, identitaIride, filtroFornitoreAffidamentoDTO);
		
		EsitoGestioneAffidamenti esito = new EsitoGestioneAffidamenti();		
		try{
			FornitoreAffidamentoVO filtroSql = beanUtil.transform(filtroFornitoreAffidamentoDTO, FornitoreAffidamentoVO.class);
			return beanUtil.transform(genericDAO.findListWhere(filtroSql), FornitoreAffidamentoDTO.class);
			
		}catch(Exception ex){
			logger.error("[findFornitoriAffidamento] errore nell'ottenimento dei fornitori.", ex);
			throw new GestioneAffidamentiException("[findFornitoriAffidamento] errore nell'ottenimento dei fornitori.", ex);
		}	
	}
	
	private PbandiArchivioFileDAOImpl pbandiArchivioFileDAOImpl;

	public PbandiArchivioFileDAOImpl getPbandiArchivioFileDAOImpl() {
		return pbandiArchivioFileDAOImpl;
	}

	public void setPbandiArchivioFileDAOImpl(
			PbandiArchivioFileDAOImpl pbandiArchivioFileDAOImpl) {
		this.pbandiArchivioFileDAOImpl = pbandiArchivioFileDAOImpl;
	}

	private ArchivioBusinessImpl archivioBusiness;

	public ArchivioBusinessImpl getArchivioBusiness() {
		return archivioBusiness;
	}

	public void setArchivioBusiness(ArchivioBusinessImpl archivioBusiness) {
		this.archivioBusiness = archivioBusiness;
	}
	
	private CheckListBusinessImpl checkListBusiness;

	public CheckListBusinessImpl getCheckListBusiness() {
		return checkListBusiness;
	}

	public void setCheckListBusiness(CheckListBusinessImpl checkListBusiness) {
		this.checkListBusiness = checkListBusiness;
	}

	private ChecklistHtmlBusinessImpl checklistHtmlBusiness;

	public ChecklistHtmlBusinessImpl getChecklistHtmlBusiness() {
		return checklistHtmlBusiness;
	}

	public void setChecklistHtmlBusiness(
			ChecklistHtmlBusinessImpl checklistHtmlBusiness) {
		this.checklistHtmlBusiness = checklistHtmlBusiness;
	}
	
	
	private DocumentoManager documentoManager;

	public DocumentoManager getDocumentoManager() {
		return documentoManager;
	}

	public void setDocumentoManager(DocumentoManager documentoManager) {
		this.documentoManager = documentoManager;
	}
}
