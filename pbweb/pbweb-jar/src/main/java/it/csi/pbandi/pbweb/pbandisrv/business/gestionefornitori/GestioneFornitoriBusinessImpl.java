package it.csi.pbandi.pbweb.pbandisrv.business.gestionefornitori;

import it.csi.pbandi.pbweb.pbandisrv.business.BusinessImpl;
import it.csi.pbandi.pbweb.pbandisrv.interfacecsi.gestionefornitori.GestioneFornitoriSrv;


import it.csi.csi.wrapper.CSIException;
import it.csi.csi.wrapper.SystemException;
import it.csi.csi.wrapper.UnrecoverableException;
import it.csi.pbandi.pbweb.pbandisrv.business.manager.ConfigurationManager;
import it.csi.pbandi.pbweb.pbandisrv.business.manager.SoggettoManager;
import it.csi.pbandi.pbweb.pbandisrv.dto.gestionefornitori.EsitoOperazioneElimina;
import it.csi.pbandi.pbweb.pbandisrv.dto.gestionefornitori.EsitoOperazioneInserimento;
import it.csi.pbandi.pbweb.pbandisrv.dto.gestionefornitori.EsitoOperazioneModifica;
import it.csi.pbandi.pbweb.pbandisrv.dto.gestionefornitori.FornitoreDTO;
import it.csi.pbandi.pbweb.pbandisrv.dto.gestionefornitori.ProgettoAssociatoDTO;
import it.csi.pbandi.pbweb.pbandisrv.dto.gestionefornitori.QualificaDTO;
import it.csi.pbandi.pbweb.pbandisrv.exception.FormalParameterException;
import it.csi.pbandi.pbweb.pbandisrv.exception.gestionefornitori.GestioneFornitoriException;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.dao.GenericDAO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.dao.GenericDAO.Pair;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.dao.PbandiDocumentiDiSpesaDAOImpl;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.dao.PbandiFornitoreQualificaDAOImpl;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.dao.PbandiFornitoriDAOImpl;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.FornitoreQualificaFilterFornitoreVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.ProgettiAssociatiFornitoreVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.QualificaVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.gestionefornitori.FornitoreVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.util.AndCondition;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.util.Condition;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.util.FilterCondition;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.util.NotCondition;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.util.NullCondition;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.GenericVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiCEntitaVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiDNazioneVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiDTipoAnagraficaVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiRFornitoreAffidamentoVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiRFornitoreQualificaVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiRSoggettoProgettoVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiTDocumentoDiSpesaVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiTFileEntitaVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiTFornitoreVO;
import it.csi.pbandi.pbweb.pbandisrv.util.Constants;
import it.csi.pbandi.pbweb.pbandisrv.util.validation.ValidatorInput;
import it.csi.pbandi.pbweb.util.DateUtil;
import it.csi.pbandi.pbweb.util.ErrorMessages;
import it.csi.pbandi.pbweb.util.NumberUtil;
import it.csi.pbandi.pbweb.pbandiutil.common.ObjectUtil;
import it.csi.pbandi.pbweb.util.StringUtil;
import it.csi.pbandi.pbweb.pbandiutil.common.ErrorConstants;
import it.csi.pbandi.pbweb.pbandiutil.common.messages.MessaggiConstants;
import it.csi.pbandi.pbweb.util.ValidatorPartitaIva;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.BidiMap;
import org.apache.commons.collections.bidimap.TreeBidiMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

//import weblogic.auddi.util.Logger;

public class GestioneFornitoriBusinessImpl extends BusinessImpl implements GestioneFornitoriSrv{
	
	
	
	static BidiMap fromFornitoreQualificaVO2FornitoreDTO = new TreeBidiMap();
	static {
		fromFornitoreQualificaVO2FornitoreDTO.put("codiceFiscaleFornitore", "codiceFiscaleFornitore");
		fromFornitoreQualificaVO2FornitoreDTO.put("cognomeFornitore", "cognomeFornitore");
		fromFornitoreQualificaVO2FornitoreDTO.put("denominazioneFornitore", "denominazioneFornitore");
		fromFornitoreQualificaVO2FornitoreDTO.put("descTipoSoggetto", "descTipoSoggetto");
		fromFornitoreQualificaVO2FornitoreDTO.put("idFornitore", "idFornitore");
		fromFornitoreQualificaVO2FornitoreDTO.put("idSoggettoFornitore", "idSoggettoFornitore");
		fromFornitoreQualificaVO2FornitoreDTO.put("idTipoSoggetto", "idTipoSoggetto");
		fromFornitoreQualificaVO2FornitoreDTO.put("nomeFornitore", "nomeFornitore");
		fromFornitoreQualificaVO2FornitoreDTO.put("partitaIvaFornitore", "partitaIvaFornitore");
		fromFornitoreQualificaVO2FornitoreDTO.put("dtFineValiditaFornitore", "dtFineValidita");
		fromFornitoreQualificaVO2FornitoreDTO.put("descBreveTipoSoggetto", "descBreveTipoSoggetto");
		fromFornitoreQualificaVO2FornitoreDTO.put("flagHasQualifica", "flagHasQualifica");
		fromFornitoreQualificaVO2FornitoreDTO.put("idFormaGiuridica", "idFormaGiuridica");
	}
	
	static BidiMap fromFornitoreDTO2FornitoreQualificaVO = fromFornitoreQualificaVO2FornitoreDTO.inverseBidiMap();

	 

	// dao iniettati con spring
	
	@Autowired
	PbandiFornitoriDAOImpl pbandifornitoriDAO;

	@Autowired
	PbandiDocumentiDiSpesaDAOImpl pbandiDocumentiDiSpesaDAO;

	@Autowired
	PbandiFornitoreQualificaDAOImpl pbandiRFornitoreQualificaDAO;
	
	@Autowired
	private GenericDAO genericDAO;

	@Autowired
	private SoggettoManager soggettoManager;
	
	@Autowired
	private ConfigurationManager configurationManager;

	public ConfigurationManager getConfigurationManager() {
		return configurationManager;
	}

	public void setConfigurationManager(ConfigurationManager configurationManager) {
		this.configurationManager = configurationManager;
	}

	public PbandiFornitoriDAOImpl getPbandifornitoriDAO() {
		return pbandifornitoriDAO;
	}

	public void setPbandifornitoriDAO(PbandiFornitoriDAOImpl pbandifornitoriDAO) {
		this.pbandifornitoriDAO = pbandifornitoriDAO;
	}

	public PbandiFornitoreQualificaDAOImpl getPbandiRFornitoreQualificaDAO() {
		return pbandiRFornitoreQualificaDAO;
	}

	public void setPbandiRFornitoreQualificaDAO(
			PbandiFornitoreQualificaDAOImpl pbandiRFornitoreQualificaDAO) {
		this.pbandiRFornitoreQualificaDAO = pbandiRFornitoreQualificaDAO;
	}


	public PbandiDocumentiDiSpesaDAOImpl getPbandiDocumentiDiSpesaDAO() {
		return pbandiDocumentiDiSpesaDAO;
	}

	public void setPbandiDocumentiDiSpesaDAO(
			PbandiDocumentiDiSpesaDAOImpl pbandiDocumentiDiSpesaDAO) {
		this.pbandiDocumentiDiSpesaDAO = pbandiDocumentiDiSpesaDAO;
	}

	public void setGenericDAO(GenericDAO genericDAO) {
		this.genericDAO = genericDAO;
	}

	public GenericDAO getGenericDAO() {
		return genericDAO;
	}

	public it.csi.pbandi.pbweb.pbandisrv.dto.gestionefornitori.FornitoreDTO[] findFornitori(
			java.lang.Long idUtente,

			java.lang.String identitaDigitale,

			it.csi.pbandi.pbweb.pbandisrv.dto.gestionefornitori.FornitoreDTO filtroFornitore

	)
			throws it.csi.pbandi.pbweb.pbandisrv.exception.gestionefornitori.GestioneFornitoriException,
			FormalParameterException

	{
		
		String pr ="[GestioneFornitoriBusinessImpl::findFornitori] ";
		logger.debug(pr + "BEGIN");
		
		FornitoreDTO fornitoreDTO[] = null;


			String[] nameParameter = { "idUtente", "identitaDigitale",
					"filtroFornitore" };
			
			
			ValidatorInput.verifyNullValue(nameParameter, idUtente,
					identitaDigitale, filtroFornitore);
           
			logger.shallowDump(filtroFornitore,"info");
			
			java.util.List<FornitoreVO> listaVO = new ArrayList<FornitoreVO>();
			

			listaVO = pbandifornitoriDAO.findFornitori(filtroFornitore);
			
			
			
			 // Controllo che il numero di record restituiti non superi
			 // il limite definito nella tabella PBandi_C_Costanti
			 
			Long maxNumFornitori = 0L;
			try {
				 maxNumFornitori = configurationManager.getCurrentConfiguration().getMaxNumFornitoriRicerca();
				 logger.debug(pr + "maxNumFornitori="+maxNumFornitori);
				 
			} catch (Exception e) {
				logger.error("Errore nella lettura della configurazione del ConfigurationManager.", e);
			}
			
			if (maxNumFornitori > 0 && listaVO.size() > maxNumFornitori) {
				GestioneFornitoriException gfe = new GestioneFornitoriException(ErrorConstants.ERRORE_FORNITORI_TROVAI_TROPPI_RISULTATI);
				throw gfe;
			}
			
			fornitoreDTO = new FornitoreDTO[listaVO.size()];

			getBeanUtil().valueCopy(listaVO.toArray(), fornitoreDTO);
 
			logger.debug(pr + "END");
		return fornitoreDTO;
	}

	

	
	 // Cerco il codiceFiscale del fornitore per idfornitore. Se non coincide con
	 //quello in arrivo dal client vuol dire che � stato modificato. In tal caso
	 //controllo che non ci fosse gi� un fornitore con quel codice fiscale
	 
	private void checkUnivocitaFornitorePerModifica(
			it.csi.pbandi.pbweb.pbandisrv.dto.gestionefornitori.FornitoreDTO fornitore)
			throws GestioneFornitoriException {
		 
		
		PbandiTFornitoreVO filtro = new PbandiTFornitoreVO();
		
		filtro.setCodiceFiscaleFornitore((fornitore.getCodiceFiscaleFornitore().toUpperCase()));
		filtro.setIdSoggettoFornitore(NumberUtil.toBigDecimal(fornitore.getIdSoggettoFornitore()));
		
		PbandiTFornitoreVO ris[] = genericDAO.findWhere(filtro);
		
		if (ris != null && ris.length > 0) {
			for (int i = 0; i < ris.length; i++) {
				if (ris[i].getIdFornitore().longValue() != (fornitore
						.getIdFornitore().longValue()) && isNull(ris[i].getDtFineValidita()))
					throw new GestioneFornitoriException(
							ERRORE_CODICE_FISCALE_FORNITORE_GIA_PRESENTE);
			}
		}
	}


	private boolean controlliValidazioneFornitore(FornitoreDTO fornitore)
			throws GestioneFornitoriException {
		 
		boolean ret=true;
	 
			if (isNull(fornitore.getIdTipoSoggetto()))
				throw new GestioneFornitoriException(ERRORE_CAMPO_NON_VALORIZZATO);
	
			if (soggettoManager.isPersonaFisica(fornitore.getIdTipoSoggetto())) {
				if (isEmpty(fornitore.getCodiceFiscaleFornitore()))
					throw new GestioneFornitoriException(
							ERRORE_CAMPO_NON_VALORIZZATO);
				
				if ((fornitore.getCodiceFiscaleFornitore().length()>32))
					throw new GestioneFornitoriException(
							ERRORE_CODICE_FISCALE_TROPPO_LUNGO);
				
				if (isEmpty(fornitore.getNomeFornitore()))
					throw new GestioneFornitoriException(
							ERRORE_CAMPO_NON_VALORIZZATO);
				if (isEmpty(fornitore.getCognomeFornitore()))
					throw new GestioneFornitoriException(
							ERRORE_CAMPO_NON_VALORIZZATO);
				if (isNull(fornitore.getIdQualifica()))
					throw new GestioneFornitoriException(
							ERRORE_CAMPO_NON_VALORIZZATO);
			} else if (soggettoManager.isEnteGiuridico(fornitore
					.getIdTipoSoggetto())) {
	
				if (isEmpty(fornitore.getDenominazioneFornitore()))
					throw new GestioneFornitoriException(
							ERRORE_CAMPO_NON_VALORIZZATO);
	
				if (isEmpty(fornitore.getCodiceFiscaleFornitore()))
					throw new GestioneFornitoriException(
							ERRORE_CAMPO_NON_VALORIZZATO);
				
				if ((fornitore.getCodiceFiscaleFornitore().length()>32))
					throw new GestioneFornitoriException(
							ERRORE_CODICE_FISCALE_TROPPO_LUNGO);
	
				if (!isEmpty(fornitore.getPartitaIvaFornitore())) {
					// FIXME ritornare codice corretto
					if (!ValidatorPartitaIva.isPartitaIvaValid(fornitore
							.getPartitaIvaFornitore()))
						throw new GestioneFornitoriException(
								"Partita iva non corretta");
				}
			} else {
				GestioneFornitoriException gex = new GestioneFornitoriException(
						ERRORE_SERVER);
				logger.fatal("Tipo soggetto " + fornitore.getIdTipoSoggetto()
						+ " non riconosciuto", gex);
				throw gex;
			}
			return ret;
		 
	}

	public void setSoggettoManager(SoggettoManager soggettoManager) {
		this.soggettoManager = soggettoManager;
	}

	public SoggettoManager getSoggettoManager() {
		return soggettoManager;
	}

	public EsitoOperazioneInserimento inserisciFornitoreSemplificazione(
			Long idUtente, String identitaDigitale, FornitoreDTO fornitore,Boolean generaCF)
			throws CSIException, SystemException, UnrecoverableException,
			GestioneFornitoriException {
		
		logger.info("BEGIN");
		try {
			EsitoOperazioneInserimento esitoOperazioneInserimento = new EsitoOperazioneInserimento();
			esitoOperazioneInserimento.setEsito(Boolean.FALSE);
			List<String> messaggi = new ArrayList<String>();
	
			String[] nameParameter = { "idUtente", "identitaDigitale", "fornitore"};
	
			ValidatorInput.verifyNullValue(nameParameter, idUtente,identitaDigitale, fornitore);
			logger.shallowDump(fornitore, "info");
			
			PbandiTFornitoreVO pbandiTFornitoreVO = new PbandiTFornitoreVO();
			pbandiTFornitoreVO.setCodiceFiscaleFornitore(fornitore.getCodiceFiscaleFornitore().toUpperCase());
			pbandiTFornitoreVO.setIdSoggettoFornitore(NumberUtil.toBigDecimal(fornitore.getIdSoggettoFornitore()));
			//Jira PBANDI-2838 - aggiunto IdTipoSoggetto per consentire di salvare lo stesso fornitore come PF e come PG.
			pbandiTFornitoreVO.setIdTipoSoggetto(BigDecimal.valueOf(fornitore.getIdTipoSoggetto()));
			NullCondition<PbandiTFornitoreVO> dtFineValiditaNullCondition = new NullCondition<PbandiTFornitoreVO>(PbandiTFornitoreVO.class, "dtFineValidita");
			List<PbandiTFornitoreVO> fornitoreEsistente = genericDAO.findListWhere(
					new AndCondition<PbandiTFornitoreVO>(
							dtFineValiditaNullCondition, 
							Condition.filterBy(pbandiTFornitoreVO)
					)
			);
			if (!ObjectUtil.isEmpty(fornitoreEsistente)) {
				
				 // Se trovo un fornitore con la data fine validita' non valorizzato
				 // allora non posso inserire il fornitore
				 
				messaggi.add(ErrorMessages.ERRORE_FORNITORE_GIA_USATO);
				
			} else {
				if(generaCF){
					fornitore.setCodiceFiscaleFornitore(" ");
				}
				
				fornitore.setAltroCodice(StringUtil.toUpperCase(fornitore.getAltroCodice()));
				
				Long idFornitore = pbandifornitoriDAO.inserisciFornitore(fornitore,idUtente);
				logger.info("inserisciFornitoreSemplificazione(): idFornitore appena inserito: "+idFornitore);
				fornitore.setIdFornitore(idFornitore);
				logger.info("generaCF : "+generaCF);
				if(generaCF){
					pbandiTFornitoreVO=new PbandiTFornitoreVO();
					pbandiTFornitoreVO.setIdFornitore(BigDecimal.valueOf(idFornitore));
					pbandiTFornitoreVO= genericDAO.findSingleWhere(pbandiTFornitoreVO);
					
					String codiceFiscaleFornitore="PBAN_"+idFornitore;
					while(codiceFiscaleFornitore.length()<15){
						codiceFiscaleFornitore+="0";
					}
					codiceFiscaleFornitore+="*";
					logger.info("aggiorno fornitore con codice fiscale autogenerato: "+codiceFiscaleFornitore);
					pbandiTFornitoreVO.setCodiceFiscaleFornitore(codiceFiscaleFornitore);
					genericDAO.updateNullables(pbandiTFornitoreVO);
					logger.info("aggiornamento ok");
				}
				esitoOperazioneInserimento.setFornitore(fornitore);
				messaggi.add(ErrorMessages.SALVATAGGIO_AVVENUTO_CON_SUCCESSO);
				esitoOperazioneInserimento.setEsito(Boolean.TRUE);
			}
			esitoOperazioneInserimento.setMessaggi(messaggi.toArray(new String[] {}));
			
			logger.info("END");
			return esitoOperazioneInserimento;
		} catch (Exception e ) {
			logger.error("Errore durante l' inserimento di un fornitore",e);
			throw new GestioneFornitoriException(ErrorMessages.ERRORE_GENERICO);
		}  
	}

	public EsitoOperazioneModifica modificaFornitoreSemplificazione(
			Long idUtente, String identitaDigitale, FornitoreDTO fornitore,java.lang.Boolean generaCF)
			throws CSIException, SystemException, UnrecoverableException,
			GestioneFornitoriException {
	 
			List<String> messaggi = new ArrayList<String>();
			
			String[] nameParameter = { "idUtente", "identitaDigitale", "fornitore", "idFornitore"};
	
			ValidatorInput.verifyNullValue(nameParameter, idUtente,identitaDigitale, fornitore, fornitore.getIdFornitore());
			EsitoOperazioneModifica esito = new EsitoOperazioneModifica();
			esito.setEsito(Boolean.FALSE);
			logger.info("\n\n\n\n\n modificaFornitoreSemplificazione with id: "+fornitore.getIdFornitore());
			logger.shallowDump(fornitore,"info");
			
			if(generaCF){
				String codiceFiscaleFornitore="PBAN_"+fornitore.getIdFornitore();
				while(codiceFiscaleFornitore.length()<15){
					codiceFiscaleFornitore+="0";
				}
				codiceFiscaleFornitore+="*";
				logger.info("aggirono fornitore con codice fiscale autogenerato: "+codiceFiscaleFornitore);
				fornitore.setCodiceFiscaleFornitore(codiceFiscaleFornitore);
			}
			
			boolean isAssociatoADocumento = isAssociatoDocumentoDiSpesa(fornitore.getIdFornitore());
			
			
			//	se il fornitore non e' associato a nessun documento allora eseguo l' update direttamente
			 //	della PBandiTFornitore, altrimenti si storicizza il vecchio fornitore (dtFineValidita), si
			 //	storicizzano le relazioni tra il vecchio fornitore e le qualifiche, si crea il nuovo fornitore
			 //	e si copiano le stesse relazioni con le qualifiche del vecchio fornitore. 
			 
			if (!isAssociatoADocumento) {
				
				 // Ricerco prima i dati precedenti del fornitore e poi li sovrascrivo con
				 // quelli del web per non perdere gli eventuali dati non null.
				 
				logger.info("modificaFornitoreSemplificazione(): modifico.");
				PbandiTFornitoreVO fornitoreVO = new PbandiTFornitoreVO();
				fornitoreVO.setIdFornitore(NumberUtil.toBigDecimal(fornitore.getIdFornitore()));
				
				fornitoreVO = genericDAO.findSingleWhere(fornitoreVO);
				if(!fornitoreVO.getCodiceFiscaleFornitore().endsWith("*"))
					fornitoreVO.setCodiceFiscaleFornitore(StringUtil.toUpperCase(fornitore.getCodiceFiscaleFornitore()));
				fornitoreVO.setCognomeFornitore(StringUtil.toUpperCase(fornitore.getCognomeFornitore()));
				fornitoreVO.setDenominazioneFornitore(StringUtil.toUpperCase(fornitore.getDenominazioneFornitore()));
				if(fornitore.getIdAttivitaAteco()!=null)
					fornitoreVO.setIdAttivitaAteco(NumberUtil.toBigDecimal(fornitore.getIdAttivitaAteco()));
				if(fornitore.getIdFormaGiuridica()!=null)
					fornitoreVO.setIdFormaGiuridica(NumberUtil.toBigDecimal(fornitore.getIdFormaGiuridica()));
				if(fornitore.getIdNazione()!=null){
					fornitoreVO.setIdNazione(NumberUtil.toBigDecimal(fornitore.getIdNazione()));
					PbandiDNazioneVO pbandiDNazioneVO=new PbandiDNazioneVO(NumberUtil.toBigDecimal(fornitore.getIdNazione()));
					pbandiDNazioneVO=genericDAO.findSingleWhere(pbandiDNazioneVO);
					if( isEmpty(pbandiDNazioneVO.getFlagAppartenenzaUe())|| pbandiDNazioneVO.getFlagAppartenenzaUe().equals("N")){
						fornitoreVO.setIdAttivitaAteco(null);
					}
				}
				
				fornitoreVO.setIdSoggettoFornitore(NumberUtil.toBigDecimal(fornitore.getIdSoggettoFornitore()));
				fornitoreVO.setIdTipoSoggetto(NumberUtil.toBigDecimal(fornitore.getIdTipoSoggetto()));
				fornitoreVO.setNomeFornitore(StringUtil.toUpperCase(fornitore.getNomeFornitore()));
				fornitoreVO.setPartitaIvaFornitore(StringUtil.toUpperCase(fornitore.getPartitaIvaFornitore()));
				fornitoreVO.setIdUtenteAgg(new BigDecimal(idUtente));
				fornitoreVO.setAltroCodice(StringUtil.toUpperCase(fornitore.getAltroCodice()));
				fornitoreVO.setCodUniIpa(fornitore.getCodUniIpa());
				fornitoreVO.setFlagPubblicoPrivato(fornitore.getFlagPubblicoPrivato());
				
				try {
					genericDAO.updateNullables(fornitoreVO);
					esito.setEsito(Boolean.TRUE);
					esito.setIdFornitore(fornitoreVO.getIdFornitore().longValue());
					messaggi.add("I dati del fornitore sono stati correttamente aggiornati.");
				} catch (Exception e) {
					GestioneFornitoriException gfe = new GestioneFornitoriException("Errore durante la modifica del fornitore["+fornitore.getIdFornitore()+"]", e);
					throw gfe;
				}
			}  else {
				try {
					
					 // Storicizzo il vecchio fornitore
					 
					logger.info("modificaFornitoreSemplificazione(): storicizzo.");
					PbandiTFornitoreVO oldFornitoreVO = new PbandiTFornitoreVO();
					oldFornitoreVO.setIdFornitore(NumberUtil.toBigDecimal(fornitore.getIdFornitore()));
					oldFornitoreVO.setDtFineValidita(DateUtil.getSysdate());
					oldFornitoreVO.setIdUtenteAgg(new BigDecimal(idUtente));
					genericDAO.update(oldFornitoreVO);
					
					
					 // Creo il nuovo fornitore
					 
					PbandiTFornitoreVO fornitoreNewVO = new PbandiTFornitoreVO();
					fornitoreNewVO.setCodiceFiscaleFornitore(StringUtil.toUpperCase(fornitore.getCodiceFiscaleFornitore()));
					fornitoreNewVO.setCognomeFornitore(StringUtil.toUpperCase(fornitore.getCognomeFornitore()));
					fornitoreNewVO.setDenominazioneFornitore(StringUtil.toUpperCase(fornitore.getDenominazioneFornitore()));
					fornitoreNewVO.setIdSoggettoFornitore(NumberUtil.toBigDecimal(fornitore.getIdSoggettoFornitore()));
					fornitoreNewVO.setIdTipoSoggetto(NumberUtil.toBigDecimal(fornitore.getIdTipoSoggetto()));
					fornitoreNewVO.setNomeFornitore(StringUtil.toUpperCase(fornitore.getNomeFornitore()));
					fornitoreNewVO.setPartitaIvaFornitore(StringUtil.toUpperCase(fornitore.getPartitaIvaFornitore()));
					if(fornitore.getIdAttivitaAteco()!=null)
						fornitoreNewVO.setIdAttivitaAteco(NumberUtil.toBigDecimal(fornitore.getIdAttivitaAteco()));
					if(fornitore.getIdFormaGiuridica()!=null)
						fornitoreNewVO.setIdFormaGiuridica(NumberUtil.toBigDecimal(fornitore.getIdFormaGiuridica()));
					if(fornitore.getIdNazione()!=null)
						fornitoreNewVO.setIdNazione(NumberUtil.toBigDecimal(fornitore.getIdNazione()));
					fornitoreNewVO.setIdUtenteIns(new BigDecimal(idUtente));
					fornitoreNewVO.setAltroCodice(fornitore.getAltroCodice());
					fornitoreNewVO.setCodUniIpa(fornitore.getCodUniIpa());
					fornitoreNewVO.setFlagPubblicoPrivato(fornitore.getFlagPubblicoPrivato());
					fornitoreNewVO = genericDAO.insert(fornitoreNewVO);
					
					
					 // Ricerco le qualifiche associate al vecchio fornitore
					 
					PbandiRFornitoreQualificaVO filtroQualificheFornitoreOldVO = new PbandiRFornitoreQualificaVO();
					filtroQualificheFornitoreOldVO.setIdFornitore(NumberUtil.toBigDecimal(fornitore.getIdFornitore()));
					
					List<PbandiRFornitoreQualificaVO> qualificheFornitoreOld = genericDAO.findListWhere(filtroQualificheFornitoreOldVO);
					if (!ObjectUtil.isEmpty(qualificheFornitoreOld)) {
						
						
						 // storicizzazione del legame con il vecchio fornitore.
						 
						PbandiRFornitoreQualificaVO qualificaFornitoreOldVO = new PbandiRFornitoreQualificaVO();
						qualificaFornitoreOldVO.setDtFineValidita(DateUtil.getSysdate());
						genericDAO.update(filtroQualificheFornitoreOldVO,qualificaFornitoreOldVO);
						
						
						 //Associo le qualifiche del vecchio fornitore al nuovo fornitore e poi le storicizzo. 
						 
						for (PbandiRFornitoreQualificaVO vo: qualificheFornitoreOld) {
							PbandiRFornitoreQualificaVO qualificaFornitoreNewVO = new PbandiRFornitoreQualificaVO();
							qualificaFornitoreNewVO.setIdFornitore(fornitoreNewVO.getIdFornitore());
							qualificaFornitoreNewVO.setIdQualifica(vo.getIdQualifica());
							qualificaFornitoreNewVO.setCostoOrario(vo.getCostoOrario());
							qualificaFornitoreNewVO.setNoteQualifica(vo.getNoteQualifica());
							
							
							 // inserimento del nuovo legame con il fornitore
							 
							genericDAO.insert(qualificaFornitoreNewVO);

							
						}
					}
					
					
					 // Associo i documenti del vecchio fornitore anche al nuovo (PBANDI-2871).
					 
					PbandiCEntitaVO entitaFornitore = new PbandiCEntitaVO();
					entitaFornitore.setNomeEntita("PBANDI_T_FORNITORE");
					entitaFornitore = genericDAO.findSingleWhere(entitaFornitore);
					
					PbandiTFileEntitaVO filtroFileEntita = new PbandiTFileEntitaVO();
					filtroFileEntita.setIdEntita(entitaFornitore.getIdEntita());
					filtroFileEntita.setIdTarget(oldFornitoreVO.getIdFornitore());
					List<PbandiTFileEntitaVO> elenco = genericDAO.findListWhere(filtroFileEntita);					
					
					if (elenco != null) {
						logger.info("modificaFornitoreSemplificazione(): trovati "+elenco.size()+" documenti allegati da duplicare.");
						for (PbandiTFileEntitaVO vecchio : elenco) {
							PbandiTFileEntitaVO nuovo = new PbandiTFileEntitaVO();
							nuovo.setIdEntita(vecchio.getIdEntita());
							nuovo.setIdTarget(fornitoreNewVO.getIdFornitore());
							nuovo.setIdFile(vecchio.getIdFile());
							nuovo.setIdProgetto(vecchio.getIdProgetto());
							nuovo.setDtEntita(vecchio.getDtEntita());
							nuovo.setFlagEntita(vecchio.getFlagEntita());
							genericDAO.insert(nuovo);
						}
					}
					
					esito.setEsito(Boolean.TRUE);
					esito.setIdFornitore(fornitoreNewVO.getIdFornitore().longValue());
					messaggi.add("I dati del fornitore sono stati correttamente aggiornati.");
				} catch (Exception e) {
					GestioneFornitoriException gfe = new GestioneFornitoriException("Errore durante la modifica del fornitore["+fornitore.getIdFornitore()+"]", e);
					throw gfe;
				}
			}
			esito.setMessaggi(messaggi.toArray(new String[] {}));
			return esito;
			
		 
	}

	public EsitoOperazioneElimina cancellaFornitoreSemplificazione(
			Long idUtente, String identitaDigitale, Long idFornitore)
			throws CSIException, SystemException, UnrecoverableException,
			GestioneFornitoriException {
		 
		List<String> messaggi = new ArrayList<String>();
		
		String[] nameParameter = { "idUtente", "identitaDigitale", "idFornitore"};

		ValidatorInput.verifyNullValue(nameParameter, idUtente,identitaDigitale, idFornitore);
		
		EsitoOperazioneElimina esito = new EsitoOperazioneElimina();
		esito.setEsito(Boolean.FALSE);
	
		
		boolean isAssociatoADocumento = isAssociatoDocumentoDiSpesa(idFornitore);
		boolean isAssociatoAdAffidamento = isFornitoreAssociatoAdAffidamenti(idUtente, identitaDigitale, idFornitore);
		logger.info("[GestioneFornitoriBusinessImpl :: cancellaFornitoreSemplificazione()] - isAssociatoADocumento = "+ isAssociatoADocumento);
		logger.info("[GestioneFornitoriBusinessImpl :: cancellaFornitoreSemplificazione()] - isAssociatoAdAffidamento = "+ isAssociatoAdAffidamento);
		
		 // Se il fornitore non e' associato a nessun documento di spesa allora
		 // cancello fisicamente il fornitore e le relazioni con le qualifiche,
		 // altrimenti storicizzo il forniotre e le relazioni con le qualifiche
		 
		if (!ObjectUtil.isNull(idFornitore)) {
			if (!isAssociatoADocumento && !isAssociatoAdAffidamento) {
				logger.info("[GestioneFornitoriBusinessImpl :: cancellaFornitoreSemplificazione()] - Fornitore non � associato con documenti o affidamenti"
						+ " idFornitore = "+ idFornitore);
				
				 // Non vanno cancellate le associazioni con i file
				
	
				// Cancello fisicamente le relazioni con le qualifiche
				 
				PbandiRFornitoreQualificaVO filtroQualificheVO = new PbandiRFornitoreQualificaVO();
				filtroQualificheVO.setIdFornitore(NumberUtil.toBigDecimal(idFornitore));
				try {
					genericDAO.deleteWhere(Condition.filterBy(filtroQualificheVO));
				} catch (Exception e) {
					GestioneFornitoriException ge = new GestioneFornitoriException("Errore durante la cancellazione del legame tra il fornitore e le qualifiche",e);
					throw ge;
				}
				
				
				// Cancello fisicamente il fornitore
				 
				PbandiTFornitoreVO filtroFornitoreVO = new PbandiTFornitoreVO();
				filtroFornitoreVO.setIdFornitore(NumberUtil.toBigDecimal(idFornitore));
				try {
					genericDAO.deleteWhere(Condition.filterBy(filtroFornitoreVO));
				} catch (Exception e) {
					GestioneFornitoriException ge = new GestioneFornitoriException("Errore durante la cancellazione del fornitore.",e);
					throw ge;
				}
				messaggi.add(MessaggiConstants.FORNITORE_CANCELLATO);
				esito.setEsito(Boolean.TRUE);
				
			} else {
				logger.info("[GestioneFornitoriBusinessImpl :: cancellaFornitoreSemplificazione()] - Fornitore associato con documenti o affidamenti"
						+ " idFornitore = "+ idFornitore);
				
				// Storicizzo le relazioni tra il fornitore e le qualifiche
				 
				//				PbandiRFornitoreQualificaVO filtroQualificheVO = new PbandiRFornitoreQualificaVO();
				//				filtroQualificheVO.setIdFornitore(NumberUtil.toBigDecimal(idFornitore));
				//				PbandiRFornitoreQualificaVO vo = new PbandiRFornitoreQualificaVO();
				//				vo.setDtFineValidita(DateUtil.getSysdate());
				//
				//				try {
				//					genericDAO.update(filtroQualificheVO,vo);
				//				} catch (Exception e) {
				//					GestioneFornitoriException ge = new GestioneFornitoriException("Errore durante la storicizzazione del legame tra il fornitore e le qualifiche",e);
				//					throw ge;
				//				}
				//				
				//				
				//				 * Storicizzo il fornitore
				//				 
				//				PbandiTFornitoreVO filtroFornitoreVO = new PbandiTFornitoreVO();
				//				filtroFornitoreVO.setIdFornitore(NumberUtil.toBigDecimal(idFornitore));
				//				filtroFornitoreVO.setDtFineValidita(DateUtil.getSysdate());
				//				filtroFornitoreVO.setIdUtenteAgg(new BigDecimal(idUtente));
				//				try {
				//					genericDAO.update(filtroFornitoreVO);
				//				} catch (Exception e) {
				//					GestioneFornitoriException ge = new GestioneFornitoriException("Errore durante la storicizzazione del fornitore.",e);
				//					throw ge;
				//				}
				//JIRA PBANDI-2896
				if(isAssociatoADocumento && !isAssociatoAdAffidamento) {
					try {
						logger.info("[GestioneFornitoriBusinessImpl :: cancellaFornitoreSemplificazione()] - messagio= "+ ErrorConstants.ERRORE_DOCUMENTI_DI_SPESA_IN_USO_DAL_FORNITORE);
						//messaggi.add(ErrorConstants.ERRORE_FORNITORE_NON_ELIMINABILE);
						messaggi.add(ErrorConstants.ERRORE_DOCUMENTI_DI_SPESA_IN_USO_DAL_FORNITORE);
						esito.setEsito(Boolean.FALSE);
					} catch (Exception e) {
						GestioneFornitoriException ge = new GestioneFornitoriException("errore durante la ricezione del messaggio ERRORE_FORNITORE_NON_ELIMINABILE: ",e);
						throw ge;
					}
				}
				if(isAssociatoAdAffidamento && !isAssociatoADocumento) {
					try {
						logger.info("[GestioneFornitoriBusinessImpl :: cancellaFornitoreSemplificazione()] - messagio= "+ ErrorConstants.ERRORE_FORNITORE_AFFIDAMENTO_NON_ELIMINABILE);
						//messaggi.add(ErrorConstants.ERRORE_FORNITORE_NON_ELIMINABILE);
						messaggi.add(ErrorConstants.ERRORE_FORNITORE_AFFIDAMENTO_NON_ELIMINABILE);
						esito.setEsito(Boolean.FALSE);
					} catch (Exception e) {
						GestioneFornitoriException ge = new GestioneFornitoriException("errore durante la ricezione del messaggio ERRORE_FORNITORE_NON_ELIMINABILE: ",e);
						throw ge;
					}
				}
				if(isAssociatoADocumento && isAssociatoAdAffidamento) {
					try {
						logger.info("[GestioneFornitoriBusinessImpl :: cancellaFornitoreSemplificazione()] - messagio= "+ ErrorConstants.ERRORE_FORNITORE_AFFIDAMENTO_DOCUMENTI_DI_SPESA_NON_ELIMINABILE);
						//messaggi.add(ErrorConstants.ERRORE_FORNITORE_NON_ELIMINABILE);
						messaggi.add(ErrorConstants.ERRORE_FORNITORE_AFFIDAMENTO_DOCUMENTI_DI_SPESA_NON_ELIMINABILE);
						esito.setEsito(Boolean.FALSE);
					} catch (Exception e) {
						GestioneFornitoriException ge = new GestioneFornitoriException("errore durante la ricezione del messaggio ERRORE_FORNITORE_NON_ELIMINABILE: ",e);
						throw ge;
					}
				}
				
				
				
			}
			//esito.setEsito(Boolean.TRUE);
			
		}
		
		if (esito.getEsito() && messaggi.size() == 0)
			messaggi.add(MessaggiConstants.FORNITORE_CANCELLATO);
		
		esito.setMessaggi(messaggi.toArray(new String[] {}));
		return esito;
		
		 
	}
	
	
	private boolean isAssociatoDocumentoDiSpesa (Long idFornitore) {
		
		PbandiTDocumentoDiSpesaVO filtroVO = new PbandiTDocumentoDiSpesaVO();
		filtroVO.setIdFornitore(NumberUtil.toBigDecimal(idFornitore));
		
		 List<PbandiTDocumentoDiSpesaVO> documentiAssociatiFornitore = genericDAO.findListWhere(filtroVO);
		 
		 if (ObjectUtil.isEmpty(documentiAssociatiFornitore)) 
			 return false;
		 else 
			 return true;
	}



	public QualificaDTO[] findQualificheSemplificazione(Long idUtente,
			String identitaDigitale, Long idFornitore) throws CSIException,
			SystemException, UnrecoverableException, GestioneFornitoriException {
		QualificaVO filtro=new QualificaVO();
		filtro.setIdFornitore(new BigDecimal(idFornitore));
		List<QualificaVO> list = genericDAO.findListWhere(filtro);		
		
		QualificaDTO[] ret=	beanUtil.transform(list, QualificaDTO.class);
		return ret;
	}
	
	public EsitoOperazioneInserimento inserisciQualificaSemplificazione(
			Long idUtente, String identitaDigitale, QualificaDTO qualifica)
			throws CSIException, SystemException, UnrecoverableException,
			GestioneFornitoriException {
		try {
			EsitoOperazioneInserimento esitoOperazioneInserimento = new EsitoOperazioneInserimento();
			esitoOperazioneInserimento.setEsito(Boolean.FALSE);
			List<String> messaggi = new ArrayList<String>();
	
			String[] nameParameter = { "idUtente", "identitaDigitale", "qualifica"};
	
			ValidatorInput.verifyNullValue(nameParameter, idUtente,identitaDigitale, qualifica);
			
			
			PbandiRFornitoreQualificaVO fornitoreQualificaVO =	beanUtil.transform(qualifica, PbandiRFornitoreQualificaVO.class);
			
	
			QualificaVO filtro=new QualificaVO();
			filtro.setIdFornitore(fornitoreQualificaVO.getIdFornitore());
			List<QualificaVO> qualificheEsistenti = genericDAO.findListWhere(filtro);	
			if(!isEmpty(qualificheEsistenti)){
				for (QualificaVO qualificaEsistente : qualificheEsistenti) {
					if(qualificaEsistente.getIdQualifica().intValue()==fornitoreQualificaVO.getIdQualifica().intValue()){
						PbandiRFornitoreQualificaVO oldFornitoreQualificaVO=new PbandiRFornitoreQualificaVO();
						oldFornitoreQualificaVO.setProgrFornitoreQualifica(qualificaEsistente.getProgrFornitoreQualifica());
						oldFornitoreQualificaVO.setDtFineValidita(DateUtil.getSysdate());
						genericDAO.update(oldFornitoreQualificaVO);						
					}
				}
			}
			
			fornitoreQualificaVO = genericDAO.insert(fornitoreQualificaVO);
			
			messaggi.add(SALVATAGGIO_AVVENUTO_CON_SUCCESSO);
			esitoOperazioneInserimento.setEsito(Boolean.TRUE);
			esitoOperazioneInserimento.setMessaggi(messaggi.toArray(new String[] {}));
			return esitoOperazioneInserimento;
		} catch (Exception e ) {
			logger.error("Errore durante l' inserimento di una qualifica",e);
			throw new GestioneFornitoriException(ERRORE_GENERICO);
		}

	}

	public EsitoOperazioneModifica modificaQualificaSemplificazione(
			Long idUtente, String identitaDigitale, QualificaDTO qualifica)
			throws CSIException, SystemException, UnrecoverableException,
			GestioneFornitoriException {
		try {
			EsitoOperazioneModifica esitoOperazioneModifica = new EsitoOperazioneModifica();
			esitoOperazioneModifica.setEsito(Boolean.FALSE);
			List<String> messaggi = new ArrayList<String>();
	
			String[] nameParameter = { "idUtente", "identitaDigitale", "qualifica"};
	
			ValidatorInput.verifyNullValue(nameParameter, idUtente,identitaDigitale, qualifica);
			
			
			PbandiRFornitoreQualificaVO fornitoreQualificaVO =	beanUtil.transform(qualifica, PbandiRFornitoreQualificaVO.class);

			if(isDocumentiAssociatiAQualificaFornitore(idUtente,identitaDigitale,qualifica.getProgrFornitoreQualifica())){
			//	aggiorna la tabella FornitoreQualifica ponendo la data di fine validit� uguale alla data del sistema (vecchio progr_fornitore_qualifica);
				PbandiRFornitoreQualificaVO oldFornitoreQualificaVO=new PbandiRFornitoreQualificaVO();
				oldFornitoreQualificaVO.setProgrFornitoreQualifica(new BigDecimal(qualifica.getProgrFornitoreQualifica()));
				oldFornitoreQualificaVO.setDtFineValidita(DateUtil.getSysdate());
				genericDAO.update(oldFornitoreQualificaVO);
				//	inserisce nella tabella FornitoreQualifica una nuova riga con i dati imputati dall�attore valorizzando al data di inizio validit� con la data del sistema (nuovo progr_fornitore_qualifica)
				fornitoreQualificaVO.setProgrFornitoreQualifica(null);
				genericDAO.insert(fornitoreQualificaVO);
			}else{
				genericDAO.update(fornitoreQualificaVO);				
			}
			
			
			messaggi.add(SALVATAGGIO_AVVENUTO_CON_SUCCESSO);
			esitoOperazioneModifica.setEsito(Boolean.TRUE);
			esitoOperazioneModifica.setMessaggi(messaggi.toArray(new String[] {}));
			return esitoOperazioneModifica;
		} catch (Exception e ) {
			logger.error("Errore durante la modifica di una qualifica",e);
			throw new GestioneFornitoriException(ERRORE_GENERICO);
		}
	}

	public EsitoOperazioneElimina cancellaQualificaSemplificazione(
			Long idUtente, String identitaDigitale, Long progrFornitoreQualifica)
			throws CSIException, SystemException, UnrecoverableException,
			GestioneFornitoriException {
		try {
			EsitoOperazioneElimina esitoOperazioneElimina = new EsitoOperazioneElimina();
			esitoOperazioneElimina.setEsito(Boolean.FALSE);
			List<String> messaggi = new ArrayList<String>();
	
			String[] nameParameter = { "idUtente", "identitaDigitale", "progrFornitoreQualifica"};
	
			ValidatorInput.verifyNullValue(nameParameter, idUtente,identitaDigitale, progrFornitoreQualifica);
			
			// Non vanno cancellate le associazioni con i file panarace 10/06/2015
			
			if(isDocumentiAssociatiAQualificaFornitore(idUtente,identitaDigitale,progrFornitoreQualifica)){
				PbandiRFornitoreQualificaVO oldFornitoreQualificaVO=new PbandiRFornitoreQualificaVO();
				oldFornitoreQualificaVO.setProgrFornitoreQualifica(new BigDecimal(progrFornitoreQualifica));
				oldFornitoreQualificaVO.setDtFineValidita(DateUtil.getSysdate());
				genericDAO.update(oldFornitoreQualificaVO);			
				//messaggi.add(QUALIFICA_FORNITORE_DISATTIVATA);//Il costo orario della qualifica che si vuole eliminare � usata in uno o pi� documenti di spesa�;
			}else{
				PbandiRFornitoreQualificaVO fornitoreQualificaVO = new PbandiRFornitoreQualificaVO();			
				fornitoreQualificaVO.setProgrFornitoreQualifica(new BigDecimal(progrFornitoreQualifica));
				genericDAO.delete(fornitoreQualificaVO);		
			}
			messaggi.add(CANCELLAZIONE_AVVENUTA_CON_SUCCESSO);
			
			
		
			
			esitoOperazioneElimina.setEsito(Boolean.TRUE);
			esitoOperazioneElimina.setMessaggi(messaggi.toArray(new String[] {}));
			return esitoOperazioneElimina;
		} catch (Exception e ) {
			logger.error("Errore durante la cancellazione di una qualifica",e);
			throw new GestioneFornitoriException(ERRORE_GENERICO);
		}
	}

	public Boolean isDocumentiAssociatiAQualificaFornitore(Long idUtente, String identitaDigitale, Long progrFornitoreQualifica){
		List<PbandiTDocumentoDiSpesaVO> documentiAssociatiFornitore =findDocAssociatiAFornitore(progrFornitoreQualifica);
		if(isEmpty(documentiAssociatiFornitore)){
			return false;
		}
		else {
			return true;
		}
	}
	
	private List<PbandiTDocumentoDiSpesaVO> findDocAssociatiAFornitore(Long progrFornitoreQualifica){
		PbandiTDocumentoDiSpesaVO pbandiTDocumentoDiSpesaVO =new PbandiTDocumentoDiSpesaVO();
		pbandiTDocumentoDiSpesaVO.setProgrFornitoreQualifica(new BigDecimal(progrFornitoreQualifica));
	//	String cedolino=DocumentoDiSpesaManager.getCodiceTipoDocumentoDiSpesa(GestioneDocumentiDiSpesaSrv.TIPO_DOCUMENTO_DI_SPESA_CEDOLINO);
		
	//	DecodificaDTO decodifica = decodificheManager.findDecodifica(GestioneDatiDiDominioSrv.TIPO_DOCUMENTO_SPESA, cedolino);
	//	pbandiTDocumentoDiSpesaVO.setIdTipoDocumentoSpesa(new BigDecimal(decodifica.getId()));
		
		List<PbandiTDocumentoDiSpesaVO> documentiAssociatiFornitore = genericDAO.findListWhere(pbandiTDocumentoDiSpesaVO);
		return documentiAssociatiFornitore;
	}
	
	// Jira PBANDI-2766: modificati FornitoreQualificaFilterFornitoreVO e FornitoreQualificaVO
	//                   per far restituire dal metodo anche l'id forma giuridica.
	public it.csi.pbandi.pbweb.pbandisrv.dto.gestionefornitori.FornitoreDTO[] findFornitoriSemplificazione(
			java.lang.Long idUtente,
			java.lang.String identitaDigitale,
			it.csi.pbandi.pbweb.pbandisrv.dto.gestionefornitori.FornitoreDTO filtroFornitore,
			java.lang.Boolean isValidi) throws it.csi.pbandi.pbweb.pbandisrv.exception.gestionefornitori.GestioneFornitoriException,FormalParameterException {
		 
		 
			String[] nameParameter = { "idUtente", "identitaDigitale", "filtroFornitore"};
			ValidatorInput.verifyNullValue(nameParameter, idUtente,identitaDigitale, filtroFornitore);
			ValidatorInput.verifyAtLeastOneNotNullValue(filtroFornitore);
			
			
			
			List<FornitoreQualificaFilterFornitoreVO> listFornitori = null;
			
			it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.FornitoreQualificaVO filtroVO = beanUtil.transform(filtroFornitore, it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.FornitoreQualificaVO.class, fromFornitoreDTO2FornitoreQualificaVO);
			
			// Ordinamento
			 
			filtroVO.setAscendentOrder("cognomeFornitore","nomeFornitore","denominazioneFornitore");
			
			if (isValidi != null && !isValidi) {
				
				// Ricerco anche i fornitori con data fine validita' valorizzata
				 
			
				NotCondition<it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.FornitoreQualificaVO> notConditionDtFineValidita = new NotCondition<it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.FornitoreQualificaVO>(
						new NullCondition<it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.FornitoreQualificaVO>(it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.FornitoreQualificaVO.class, "dtFineValiditaFornitore")
				);
					
				listFornitori = genericDAO.findListWhereDistinct(Condition.filterBy(filtroVO).and(notConditionDtFineValidita), FornitoreQualificaFilterFornitoreVO.class);
				
			} else if (isValidi != null && isValidi) {
				
				// Ricerco i fornitori con data fine di validita' non valorizzata
				 

				NullCondition<it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.FornitoreQualificaVO> nullConditionDtFineValidita = new NullCondition<it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.FornitoreQualificaVO>(it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.FornitoreQualificaVO.class, "dtFineValiditaFornitore");
					
				listFornitori = genericDAO.findListWhereDistinct(Condition.filterBy(filtroVO).and(nullConditionDtFineValidita), FornitoreQualificaFilterFornitoreVO.class);
				
			} else {
				
				// Ricerco i fornitori tutti i fornitori
				 
				listFornitori = genericDAO.findListWhereDistinct(Condition.filterBy(filtroVO), FornitoreQualificaFilterFornitoreVO.class);
			}
			
						
			return beanUtil.transform(listFornitori, FornitoreDTO.class,fromFornitoreQualificaVO2FornitoreDTO);
			
			
	 
	}

	public FornitoreDTO[] findFornitoriConQualificaValidaSemplificazione(
			Long idUtente, String identitaDigitale, FornitoreDTO filtroFornitore)
			throws CSIException, SystemException, UnrecoverableException,
			GestioneFornitoriException {
		 
	 
			String[] nameParameter = { "idUtente", "identitaDigitale", "filtroFornitore"};
			ValidatorInput.verifyNullValue(nameParameter, idUtente,identitaDigitale, filtroFornitore);
			ValidatorInput.verifyAtLeastOneNotNullValue(filtroFornitore);
			
			it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.FornitoreQualificaValidaVO filtroVO = beanUtil.transform(filtroFornitore, it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.FornitoreQualificaValidaVO.class, fromFornitoreDTO2FornitoreQualificaVO);
			
			// Ordinamento
			
			filtroVO.setAscendentOrder("cognomeFornitore","nomeFornitore","denominazioneFornitore");
			
			List<FornitoreQualificaFilterFornitoreVO> listFornitori = genericDAO.findListWhereDistinct(Condition.filterBy(filtroVO), FornitoreQualificaFilterFornitoreVO.class);
			
			return beanUtil.transform(listFornitori, FornitoreDTO.class,fromFornitoreQualificaVO2FornitoreDTO);
			
		 
	}

	public ProgettoAssociatoDTO[] findProgettiAssociatiFornitoreBeneficiario(
			Long idUtente, String identitaDigitale, Long idFornitore,
			Long idBeneficiario) throws CSIException, SystemException,
			UnrecoverableException, GestioneFornitoriException {
		 
			String[] nameParameter = { "idUtente", "identitaDigitale",
					"idFornitore" };
			ValidatorInput.verifyNullValue(nameParameter, idUtente,
					identitaDigitale, idFornitore);
			
			ProgettiAssociatiFornitoreVO progettiAssociati = new ProgettiAssociatiFornitoreVO();
			progettiAssociati.setIdFornitore(new BigDecimal(idFornitore));
			
			Condition<ProgettiAssociatiFornitoreVO> query = new FilterCondition<ProgettiAssociatiFornitoreVO>(progettiAssociati);
			
			if(idBeneficiario != null) {
				PbandiRSoggettoProgettoVO rsp = new PbandiRSoggettoProgettoVO();
				rsp.setIdSoggetto(new BigDecimal(idBeneficiario));
				PbandiDTipoAnagraficaVO anagrafica = new PbandiDTipoAnagraficaVO();
				anagrafica.setDescBreveTipoAnagrafica(Constants.DESCRIZIONE_BREVE_TIPO_ANAGRAFICA_PERSONA_FISICA);
				List<Pair<GenericVO, PbandiRSoggettoProgettoVO, PbandiDTipoAnagraficaVO>> progettiBeneficiario = genericDAO.join(Condition.filterBy(rsp), Condition.filterBy(anagrafica)).by("idTipoAnagrafica").select();
				List<ProgettiAssociatiFornitoreVO> progettiAssociatiBeneficiario = new ArrayList<ProgettiAssociatiFornitoreVO>();
				for (Pair<GenericVO, PbandiRSoggettoProgettoVO, PbandiDTipoAnagraficaVO> pair : progettiBeneficiario) {
					ProgettiAssociatiFornitoreVO temp = new ProgettiAssociatiFornitoreVO();
					temp.setIdProgetto(pair.getFirst().getIdProgetto());
					progettiAssociatiBeneficiario.add(temp);
				}
				query = new AndCondition<ProgettiAssociatiFornitoreVO>(query, new FilterCondition<ProgettiAssociatiFornitoreVO>(progettiAssociatiBeneficiario));
			}
			List<ProgettiAssociatiFornitoreVO> result = genericDAO.where(query).orderBy("nomeBandoLinea","codiceVisualizzato").select();
			return beanUtil.transform(result, ProgettoAssociatoDTO.class);
	}
	
	// PBANDI-2760
	public Boolean isFornitoreAssociatoAdAffidamenti(Long idUtente, String identitaDigitale, Long idFornitore){
		if (idFornitore == null)
			return false;
		List<PbandiRFornitoreAffidamentoVO> lista = findAffidamentiAssociatiAFornitore(idFornitore);
		return (!isEmpty(lista));
	}
	
	private List<PbandiRFornitoreAffidamentoVO> findAffidamentiAssociatiAFornitore(Long idFornitore){
		PbandiRFornitoreAffidamentoVO pbandiRFornitoreAffidamentoVO =new PbandiRFornitoreAffidamentoVO();
		pbandiRFornitoreAffidamentoVO.setIdFornitore(new BigDecimal(idFornitore));
		List<PbandiRFornitoreAffidamentoVO> lista = genericDAO.findListWhere(pbandiRFornitoreAffidamentoVO);
		return lista;
	}
	
	
	// Verifica che il fornitore sia associato a documenti di spesa
	
	public Boolean isFornitoreAssociatoADocumentoSpesa(Long idUtente,
			String identitaDigitale, Long idFornitore) throws CSIException,
			SystemException, UnrecoverableException, GestioneFornitoriException {
				
		String[] nameParameter = { "idUtente", "identitaDigitale", "idFornitore"};

		ValidatorInput.verifyNullValue(nameParameter, idUtente,identitaDigitale, idFornitore);
		
		try{
			return isAssociatoDocumentoDiSpesa(idFornitore);
		}catch(Exception ex){
			throw new GestioneFornitoriException("Errore durante la chiamata del servizio isFornitoreAssociatoADocumentoSpesa", ex);
		} 
		
	}

	public Boolean isAllegatoFornitoreDisassociabile(Long idUtente,
			String identitaDigitale, Long idFornitore, Long idProgetto) throws CSIException,
			SystemException, UnrecoverableException, GestioneFornitoriException {
		
		String[] nameParameter = {"idFornitore","idProgetto"};
		ValidatorInput.verifyNullValue(nameParameter,idUtente,identitaDigitale, idFornitore, idProgetto);
		return pbandifornitoriDAO.isAllegatoFornitoreDisassociabile(idFornitore, idProgetto);
	}
	
	
}
