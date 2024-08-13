/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.pbandisrv.business.neoflux;

import it.csi.csi.wrapper.CSIException;
import it.csi.csi.wrapper.SystemException;
import it.csi.csi.wrapper.UnrecoverableException;
import it.csi.pbandi.pbweb.pbandisrv.business.BusinessImpl;
import it.csi.pbandi.pbweb.pbandisrv.business.manager.ConfigurationManager;
import it.csi.pbandi.pbweb.pbandisrv.dto.neoflux.BandoDTO;
import it.csi.pbandi.pbweb.pbandisrv.dto.neoflux.NotificaDTO;
import it.csi.pbandi.pbweb.pbandisrv.dto.neoflux.ProgettoDTO;
import it.csi.pbandi.pbweb.pbandisrv.dto.neoflux.TaskDTO;
import it.csi.pbandi.pbweb.pbandisrv.exception.neoflux.NeoFluxException;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.dao.GenericDAO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.BandoVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiRBandoLineaInterventVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiTBandoVO;
import it.csi.pbandi.pbweb.pbandisrv.interfacecsi.neoflux.NeofluxSrv;
import it.csi.pbandi.pbweb.pbandisrv.util.validation.ValidatorInput;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import static it.csi.pbandi.pbweb.pbandisrv.business.neoflux.MiniApp.*;
import static it.csi.pbandi.pbweb.pbandisrv.business.neoflux.Task.*;
 
public class NeofluxBusinessImpl extends BusinessImpl implements NeofluxSrv {
 
	@Autowired
	private ConfigurationManager configurationManager;
	
	@Autowired
	private GenericDAO genericDAO;
	
	public ConfigurationManager getConfigurationManager() {
		return configurationManager;
	}


	public void setConfigurationManager(ConfigurationManager configurationManager) {
		this.configurationManager = configurationManager;
	}


	public GenericDAO getGenericDAO() {
		return genericDAO;
	}


	public void setGenericDAO(GenericDAO genericDAO) {
		this.genericDAO = genericDAO;
	}
   
	//PBANDI_D_TASK
	private static final Map<String,String> taskMiniApp=new HashMap<String,String> ();
	static {
		
		taskMiniApp.put(CHIUSURA_PROGETTO, PBANDIWEBEROGSRV);//Chiusura_del_progetto
		taskMiniApp.put(CHIUS_PROG_RINUNCIA, PBANDIWEBEROGSRV);//Chiusura_del_progetto_per_rinuncia
		taskMiniApp.put(CHIUS_UFF_PROG, PBANDIWEBEROGSRV);//Chiusura_d_ufficio_del_progetto
		taskMiniApp.put(COMUNIC_FINE_PROG, PBANDIWEBEROGSRV);//Comunicazione_di_fine_progetto
		taskMiniApp.put(COMUNIC_RINUNCIA, PBANDIWEBEROGSRV);//Comunicazione_di_rinuncia
		taskMiniApp.put(DATI_PROG, PBANDIWEBEROGSRV);//Dati_del_progetto
		taskMiniApp.put(GEST_FIDEIUSSIONI, PBANDIWEBEROGSRV);//Gestione_fideiussioni
		taskMiniApp.put(EROGAZIONE, PBANDIWEBEROGSRV);//Erogazione
		taskMiniApp.put(MOD_EROG, PBANDIWEBEROGSRV);//Modifica_erogazione
		taskMiniApp.put(MOD_RECUPERO, PBANDIWEBEROGSRV);//Modifica_recuperi
		taskMiniApp.put(MOD_REVOCA, PBANDIWEBEROGSRV);//Modifica_revoche
		taskMiniApp.put(REVOCA, PBANDIWEBEROGSRV);//Revoca
		taskMiniApp.put(RECUPERO, PBANDIWEBEROGSRV);//Recupero
		taskMiniApp.put(RICH_EROG_CALC_CAU, PBANDIWEBEROGSRV);//Richiesta_di_erogazione_acconto
		taskMiniApp.put(RILEV_IRREGOLAR, PBANDIWEBEROGSRV);//Rilevazione_irregolarita
		taskMiniApp.put(SOPPRESSIONE, PBANDIWEBEROGSRV);//Soppressione
		
		taskMiniApp.put(CONSULTA_LIQUID, PBANDIWEBFINSRV);//Consulta_atti_liquidazione
		taskMiniApp.put(LIQUIDAZIONE, PBANDIWEBFINSRV);//Liquidazione_contributo

		taskMiniApp.put(CARICAM_INDIC_PROG, PBANDIWEBRCESRV);// Caricamento_indicatori_di_progetto
		taskMiniApp.put(CARICAM_INDIC_AVVIO, PBANDIWEBRCESRV);// Caricamento_indicatori_di_progetto_di_avvio
		taskMiniApp.put(CRONOPROGRAMMA, PBANDIWEBRCESRV);//Cronoprogramma
		taskMiniApp.put(CRONOPROG_AVVIO, PBANDIWEBRCESRV);//Cronoprogramma_di_avvio
		taskMiniApp.put(GEST_APPALTI, PBANDIWEBRCESRV);//Gestione_appalti
		//taskMiniApp.put(GEST_APPALTI_CONT_INCARICHI, PBANDIWEBRCESRV);//Gestione_appalti_contratti_incarichi
		taskMiniApp.put(PROP_RIM_CE, PBANDIWEBRCESRV);//Proposta_di_rimodulazione_del_conto_economico
		taskMiniApp.put(QUADRO_PREVISIO, PBANDIWEBRCESRV);//Quadro_previsionale
		taskMiniApp.put(RET_CRONOPROG, PBANDIWEBRCESRV);//Rettifica_cronoprogramma
		taskMiniApp.put(RET_INDIC_PROG , PBANDIWEBRCESRV);//Rettifica_indicatori_di_progetto
		taskMiniApp.put(RICH_CE_DOM , PBANDIWEBRCESRV);//Richiesta_del_conto_economico_in_domanda
		taskMiniApp.put(RIM_CE , PBANDIWEBRCESRV);//Rimodulazione_del_conto_economico
		taskMiniApp.put(RIM_CE_ISTR , PBANDIWEBRCESRV);//Rimodulazione_del_conto_economico_in_istruttoria
		
		taskMiniApp.put(DICH_DI_SPESA, PBANDIWEBSRV);//Dichiarazione_di_spesa
		taskMiniApp.put(DICH_SPESA_INTEGRATIVA, PBANDIWEBSRV);//Dichiarazione_di_spesa_integrativa
		taskMiniApp.put(GEST_CHECKLIST, PBANDIWEBSRV);//Gestione_checklist_del_progetto
		taskMiniApp.put(GEST_SPESA_VALID, PBANDIWEBSRV);//Gestione_spesa_validata
		taskMiniApp.put(VALID_DICH_SPESA, PBANDIWEBSRV);
		taskMiniApp.put(VALID_DICH_SPESA_FINALE, PBANDIWEBSRV);//Validazione_della_dichiarazione_di_spesa_finale
		
		//taskMiniApp.put("Richiesta_di_erogazione_primo_anticipo", "pbandiweberogsrv");// not present in neoflux
		//taskMiniApp.put("Richiesta_di_erogazione_saldo", "pbandiweberogsrv");//not present in neoflux
		//taskMiniApp.put("Richiesta_di_erogazione_ulteriore_acconto", "pbandiweberogsrv");//not present in neoflux
		
	}
	
	 
	
	public void cancelNotificationMessage(

			java.lang.Long idUtente,

			java.lang.String identitaDigitale,

			java.lang.Long idNotifica

			)throws CSIException,
					SystemException, UnrecoverableException, NeoFluxException {

		long startTime= System.currentTimeMillis();
		String[] nameParameter = { "idUtente", "identitaDigitale","idNotifica"};
		ValidatorInput.verifyNullValue(nameParameter,idUtente,identitaDigitale, idNotifica);
		logger.warn("cancelNotificationMessage  idUtente: "+idUtente+" idNotifica: "+idNotifica);
		
		int ret = genericDAO.callProcedure().cancelNotificationMessage(BigDecimal.valueOf(idNotifica),
				 BigDecimal.valueOf(idUtente));
		
		logger.warn("ret code---> : "+ret);
		
	 
		
		logger.warn("method executed in ms: "+(System.currentTimeMillis()-startTime));
		
	}
		
		
		
		
		
	/*
	EndAttivita (p_id_progetto       IN  PBANDI_T_PROGETTO.ID_PROGETTO%TYPE,
	         p_descr_breve_task  IN  PBANDI_D_TASK.DESCR_BREVE_TASK%TYPE,
	         p_id_utente         IN  PBANDI_T_UTENTE.ID_UTENTE%TYPE,
	         p_flag_forzatura    IN VARCHAR2 DEFAULT NULL)
	*/
		public void endAttivita(Long idUtente, String identitaDigitale,
				Long idProgetto, String descBreveTask) throws CSIException,
				SystemException, UnrecoverableException, NeoFluxException {
			
			long startTime= System.currentTimeMillis();
			
			String[] nameParameter = { "idUtente", "identitaDigitale","idProgetto", "descBreveTask"};
			ValidatorInput.verifyNullValue(nameParameter,idUtente,identitaDigitale, idProgetto,descBreveTask);
			
			logger.info("ending task: "+descBreveTask+" for idProgetto: "+idProgetto+ " , idUtente: "+idUtente);
			
			int ret = genericDAO.callProcedure().endAttivita(BigDecimal.valueOf(idProgetto),
					descBreveTask,BigDecimal.valueOf(idUtente));
			
			logger.warn("ret code---> : "+ret);
			
			/*
			 *  v_ritorno := 0; -- TUTTO OK
			 *  v_ritorno := 1; -- Task lockato da altro utente
			 *  v_ritorno := 2; -- Attivit� non ancora istanziata per il progetto
			 *  v_ritorno := 3; -- Non processato per dati del businness con cogruenti con la chiusura 
			 *  v_ritorno := 3; -- Si � verificato un errore nel calcolo delle economie del progetto, pertanto non � stato possibile chiudere il progetto. Contattare l'Assistenza.
			 *  
			 *  */
			
			if(ret==1)
				throw new NeoFluxException("Attivita' usata da un altro utente");
			else if(ret==2)
				throw new NeoFluxException("Attivita' non ancora istanziata per il progetto");
			else if(ret==3)
				throw new NeoFluxException("Non processato per dati del businness non cogruenti con la chiusura ");
			else if(ret==4)
				throw new NeoFluxException("Si e' verificato un errore nel calcolo delle economie del progetto, pertanto non e' stato possibile chiudere il progetto. Contattare l'Assistenza.");
			
			logger.info("method executed in ms: "+(System.currentTimeMillis()-startTime));
		}

		
		// Simile ad endAttivita(), ma chiude tutti insieme indicatori, cronoprogramma, ds finale e CFP.
			public void endAttivitaDsFinale(Long idUtente, String identitaDigitale, Long idProgetto) throws CSIException,
					SystemException, UnrecoverableException, NeoFluxException {
				
				long startTime= System.currentTimeMillis();
				
				String[] nameParameter = { "idUtente", "identitaDigitale","idProgetto"};
				ValidatorInput.verifyNullValue(nameParameter,idUtente,identitaDigitale, idProgetto);
				
				logger.info("endAttivitaDsFinale(): idProgetto: "+idProgetto+ "; idUtente: "+idUtente);
				
				int ret = genericDAO.callProcedure().endAttivitaDsFinale(BigDecimal.valueOf(idProgetto),BigDecimal.valueOf(idUtente));
				
				logger.info("endAttivitaDsFinale(): ret code---> : "+ret);
				
				//  v_ritorno := 0; -- TUTTO OK
				//  v_ritorno := 1; -- errore
				
				if(ret==1)
					throw new NeoFluxException("Si e' verificato un errore nella chiusura della attivita'");

				logger.info("method executed in ms: "+(System.currentTimeMillis()-startTime));
			}

	public TaskDTO[] getAttivita(Long idUtente, String identitaDigitale,
			Long idProgetto, String descBreveTipoAnagrafica) throws CSIException,
			SystemException, UnrecoverableException, NeoFluxException {
		long startTime= System.currentTimeMillis();
		String[] nameParameter = { "idUtente", "identitaDigitale", "idProgetto","descBreveTipoAnagrafica" };
		ValidatorInput.verifyNullValue(nameParameter, idProgetto,descBreveTipoAnagrafica);
		
		
		logger.warn("method executed in ms: "+(System.currentTimeMillis()-startTime));
		
		return null;
	}
	
 
	public TaskDTO[] getAttivitaBEN(Long idUtente, String identitaDigitale,
			 String descBreveTipoAnagrafica,
			 Long idSoggettoBen, 
			 Long progrBandoLineaIntervento, 
			 Long idProgetto,
		 	 String descrTask ) throws CSIException,
			 SystemException, UnrecoverableException, NeoFluxException {
		long startTime= System.currentTimeMillis();
		String[] nameParameter = { "idUtente", "identitaDigitale","descBreveTipoAnagrafica", "idSoggettoBen","progrBandoLineaIntervento" };
		ValidatorInput.verifyNullValue(nameParameter,idUtente,identitaDigitale,descBreveTipoAnagrafica, idSoggettoBen,progrBandoLineaIntervento);
		
		TaskDTO[] tasks=null;
		logger.warn("\n\n\ncalling genericDAO.callProcedure().countProgettiBEN with progrBandoLineaIntervento: "+progrBandoLineaIntervento
				+ " ,  idSoggettoBen: "+idSoggettoBen+"\tidUtente ---> "+idUtente);
		int countProgettiBEN = genericDAO.callProcedure().countProgettiBEN(BigDecimal.valueOf(progrBandoLineaIntervento),
				BigDecimal.valueOf(idSoggettoBen),BigDecimal.valueOf(idUtente));
		logger.warn("countProgettiBEN ---> : "+countProgettiBEN);
		//if(countProgettiBEN>20){
		//	throw new NeoFluxException("Ci sono troppe attivit�. Selezionare un progetto e rieseguire la ricerca");
		//}
		
		logger.warn("\n\n\ncalling genericDAO.callProcedure().getAttivitaBEN with progrBandoLineaIntervento ---> "
				+ progrBandoLineaIntervento
				+ "\nidSoggettoBen: "+idSoggettoBen+"\nidUtente: "+idUtente
				+"\ndescBreveTipoAnagrafica:"+descBreveTipoAnagrafica+"\nidProgetto:"+idProgetto+"\ndescrTask "+ descrTask);
		BigDecimal idPrj=null;
		if(idProgetto!=null)
			idPrj=	BigDecimal.valueOf(idProgetto);
		tasks   = genericDAO.callProcedure().getAttivitaBEN(BigDecimal.valueOf(progrBandoLineaIntervento),
				BigDecimal.valueOf(idSoggettoBen),
				BigDecimal.valueOf(idUtente),
				descBreveTipoAnagrafica,
				BigDecimal.valueOf(1),
				idPrj,
				descrTask
				);
		logger.warn("\nfound tasks --> "+tasks.length);
		for (TaskDTO  taskDTO: tasks) {
			logger.shallowDump(taskDTO, "info");
		}
		logger.warn("method executed in ms: "+(System.currentTimeMillis()-startTime));
		return tasks;
	}
	
	
	public TaskDTO[] getAttivitaBL(Long idUtente, String identitaDigitale,
			Long progrBandoLineaIntervento,  String descBreveTipoAnagrafica ) throws CSIException,
			SystemException, UnrecoverableException, NeoFluxException {
		long startTime= System.currentTimeMillis();
		String[] nameParameter = { "idUtente", "identitaDigitale", "progrBandoLineaIntervento","descBreveTipoAnagrafica" };
		ValidatorInput.verifyNullValue(nameParameter, progrBandoLineaIntervento,descBreveTipoAnagrafica);
		
		logger.warn("\n\n\ncalling genericDAO.callProcedure().getAttivitaBL with progrBandoLineaIntervento: "+progrBandoLineaIntervento);
		TaskDTO[] tasks=null;
		try{
			tasks   = genericDAO.callProcedure().getAttivitaBL(BigDecimal.valueOf(progrBandoLineaIntervento),BigDecimal.valueOf(idUtente),
															  descBreveTipoAnagrafica,BigDecimal.valueOf(1));
		}catch(Exception ex){
			logger.error("Error: "+ex.getMessage()+"\n\n", ex);
		}
		logger.warn("method executed in ms: "+(System.currentTimeMillis()-startTime));
		return tasks;
	}


	
	
	public NotificaDTO getNotificationMessage(Long idUtente, String identitaDigitale,
			 Long idNotifica
		 	     ) throws CSIException,
			 SystemException, UnrecoverableException, NeoFluxException {
		long startTime= System.currentTimeMillis();
		String[] nameParameter = { "idUtente", "identitaDigitale","idNotifica"  };
		ValidatorInput.verifyNullValue(nameParameter,idUtente,identitaDigitale,idNotifica);
		
		NotificaDTO notificaDTO=null;
		logger.warn("\n\n\n\ncalling genericDAO.callProcedure().getNotificationMessage with idNotifica ---> "
				+ idNotifica);
		notificaDTO   = genericDAO.callProcedure().getNotificationMessage(BigDecimal.valueOf(idNotifica) );
		logger.shallowDump(notificaDTO, "info");
		logger.warn("method executed in ms: "+(System.currentTimeMillis()-startTime)+"\n\n");
		return notificaDTO;
	}
	
	
	public Long getProcesso(Long idUtente, String identitaDigitale,
			Long idProgetto) throws CSIException, SystemException,
			UnrecoverableException, NeoFluxException {
		long startTime= System.currentTimeMillis();
	 
		String[] nameParameter = { "idUtente", "identitaDigitale", "idProgetto" };
		ValidatorInput.verifyNullValue(nameParameter, idProgetto);
		
		logger.warn("\n\n\ncalling genericDAO.callProcedure().getProcesso with idProgetto: "+idProgetto);
		int processo = -1;
		try{
			processo   = genericDAO.callProcedure().getProcesso(BigDecimal.valueOf(idProgetto));
		}catch(Exception ex){
			logger.error("Error: "+ex.getMessage()+"\n\n", ex);
		}
		logger.warn("method executed in ms: "+(System.currentTimeMillis()-startTime));
		if(processo!=-1)
			return Long.valueOf(processo);
		else
			return null;
	}

	
	public Long getProcessoBL(Long idUtente, String identitaDigitale,
			Long progrBandoLineaIntervento) throws CSIException,
			SystemException, UnrecoverableException, NeoFluxException {
		long startTime= System.currentTimeMillis();
		String[] nameParameter = { "idUtente", "identitaDigitale", "progrBandoLineaIntervento" };
		ValidatorInput.verifyNullValue(nameParameter, progrBandoLineaIntervento);
		logger.warn("\n\n\ncalling genericDAO.callProcedure().getProcessoBL with progrBandoLineaIntervento: "+progrBandoLineaIntervento);
		int processo = -1;
		try{
			processo   = genericDAO.callProcedure().getProcessoBL(BigDecimal.valueOf(progrBandoLineaIntervento));
		}catch(Exception ex){
			logger.error("Error: "+ex.getMessage()+"\n\n", ex);
		}
		logger.warn("method executed in ms: "+(System.currentTimeMillis()-startTime));
		if(processo!=-1)
			return Long.valueOf(processo);
		else
			return null;
	}


	public BandoDTO[] getBandi(Long idUtente, String identitaDigitale,
			Long idSoggetto, String ruoloUtente, Long idSoggettoBen)
			throws CSIException, SystemException, UnrecoverableException,
			NeoFluxException {
		long startTime= System.currentTimeMillis();
		String[] nameParameter = { "idUtente", "identitaDigitale", "idSoggetto","ruoloUtente","idSoggettoBen" };
		ValidatorInput.verifyNullValue(nameParameter, idSoggetto, ruoloUtente, idSoggettoBen);
		
		logger.warn("\n\n\n############################\ngetBandi with idSoggetto: "+idSoggetto+" , ruoloUtente: "+ruoloUtente+" ,idSoggettoBen: "+idSoggettoBen);
		try{
			BandoVO filter= new BandoVO();
			filter.setAscendentOrder("nomeBandoLinea");
			filter.setDescBreveTipoAnagrafica(ruoloUtente);
			filter.setIdSoggetto(BigDecimal.valueOf(idSoggetto));
			filter.setIdSoggettoBeneficiario(BigDecimal.valueOf(idSoggettoBen));
			List<BandoVO> bandi = genericDAO.findListWhere(filter);
			logger.warn("found List<BandoVO> bandi : "+bandi.size());
			
			// E' stato richiesto di elencare prima i bandolinea nuova programmazione
			// e poi quelli vecchia programmazione (6/4/2017).
			// (nota: BandoVO.nomeBandoLinea � il nome del bandolinea, non del bando)
			List<BandoVO> bandiOrdinati = this.ordinaBandi(bandi);
			
			BandoDTO[] bandoDTOs = beanUtil.transform(bandiOrdinati, BandoDTO.class);
			return bandoDTOs;
		}catch(Exception ex){
			logger.error("Error: "+ex.getMessage()+"\n\n", ex);
			throw new NeoFluxException(ex.getMessage(), ex );
		}finally{
			logger.warn("method executed in ms: "+(System.currentTimeMillis()-startTime));
		}

	}

	// Dato un elenco di bandi restituiti da getBandi()
	// in modo da mettere prima quelli nuova programmazione e poi quella vecchia.
	private List<BandoVO> ordinaBandi (List<BandoVO> listaInput) {
		List<BandoVO> listaVecchiaProgr = new ArrayList<BandoVO>();
		List<BandoVO> listaNuovaProgr = new ArrayList<BandoVO>();
		try{
			if (listaInput != null) {
				// Divide i bandi in 2 elenchi in base alla programmazione.								
				for(BandoVO bando : listaInput) {					
					// Recupera il bando linea da PBANDI_R_BANDO_LINEA_INTERVENT.
					PbandiRBandoLineaInterventVO vo1 = new PbandiRBandoLineaInterventVO();
					vo1.setProgrBandoLineaIntervento(bando.getProgrBandoLineaIntervento());
					vo1 = genericDAO.findSingleWhere(vo1);			
					// Recupera il bando da PBANDI_T_BANDO.
					PbandiTBandoVO vo2 = new PbandiTBandoVO();
					vo2.setIdBando(vo1.getIdBando());
					vo2 = genericDAO.findSingleWhere(vo2);
					// Determina se il bando � vecchia o nuova programmazione.
					if (vo2.getIdLineaDiIntervento() == null) {
						// Vecchia programmazione
						listaVecchiaProgr.add(bando);
					} else {
						// Nuova programmazione
						listaNuovaProgr.add(bando);
					}
				}
				// Appende i bandi vecchia progr. al fondo di quelli nuova progr.
				for(BandoVO bando : listaVecchiaProgr) {
					listaNuovaProgr.add(bando);
				}
			}
		} catch(Exception ex){
			logger.error("ordinaBandi(): "+ex.getMessage()+"\n\n", ex);
			listaNuovaProgr = listaInput;
		}
		return listaNuovaProgr;
	}


	public ProgettoDTO[] getProgetti(Long idUtente, String identitaDigitale,
			Long idSoggettoBen, Long progrBandoLineaIntervento)
			throws CSIException, SystemException, UnrecoverableException,
			NeoFluxException {
		long startTime= System.currentTimeMillis();
		String[] nameParameter = { "idUtente", "identitaDigitale","idSoggettoBen", "progrBandoLineaIntervento"};
		ValidatorInput.verifyNullValue(nameParameter,idUtente,identitaDigitale, idSoggettoBen,progrBandoLineaIntervento);
		
		ProgettoDTO[] progetti=null;
		logger.warn("\n\ncalling genericDAO.callProcedure().getProgetti  with progrBandoLineaIntervento: "+progrBandoLineaIntervento
				+ " ,  idSoggettoBen: "+idSoggettoBen+" , idUtente ---> "+idUtente );
		progetti = genericDAO.callProcedure().getProgetti(BigDecimal.valueOf(progrBandoLineaIntervento),
				BigDecimal.valueOf(idSoggettoBen),BigDecimal.valueOf(idUtente));
		 
		logger.warn("\nfound progetti --> "+progetti.length+"\nmethod executed in ms: "+(System.currentTimeMillis()-startTime));
		return progetti;
	}


/*
	FUNCTION IsLocked      (p_id_progetto       IN  PBANDI_T_PROGETTO.ID_PROGETTO%TYPE,
            p_descr_breve_task  IN  PBANDI_D_TASK.DESCR_BREVE_TASK%TYPE,
            p_id_utente         IN  PBANDI_T_UTENTE.ID_UTENTE%TYPE) RETURN VARCHAR2
            */
	
	public String isAttivitaLocked(Long idUtente, String identitaDigitale,
			Long idProgetto, String descBreveTask) throws CSIException,
			SystemException, UnrecoverableException, NeoFluxException {
		String locker=null;
		long startTime= System.currentTimeMillis();
		
		String[] nameParameter = { "idUtente", "identitaDigitale","idProgetto", "descBreveTask"};
		ValidatorInput.verifyNullValue(nameParameter,idUtente,identitaDigitale, idProgetto,descBreveTask);
		logger.warn("\n\n\n\n################################\nisAttivitaLocked: "+descBreveTask+" for idProgetto: "+idProgetto+ " , idUtente: "+idUtente);
		
		String ret = genericDAO.callProcedure().isLocked(BigDecimal.valueOf(idProgetto),
				descBreveTask,BigDecimal.valueOf(idUtente));
		
		logger.warn("isAttivitaLocked ret  ---> : "+ret);
		if(!isEmpty(ret)){
			logger.warn("\nattivita is locked by "+ret+" !!!\n");
			locker=ret;
		}
		
		logger.warn( "method executed in ms: "+(System.currentTimeMillis()-startTime)+"\n\n\n");
		return locker;
	}
	
	
	
	/*StartAttivita (p_id_progetto       IN  PBANDI_T_PROGETTO.ID_PROGETTO%TYPE,
    p_descr_breve_task  IN  PBANDI_D_TASK.DESCR_BREVE_TASK%TYPE,
    p_id_utente         IN  PBANDI_T_UTENTE.ID_UTENTE%TYPE)
 RETURN INTEGER
*/
	public String startAttivita(Long idUtente, String identitaDigitale,
			Long idProgetto, String descBreveTask) throws CSIException,
			SystemException, UnrecoverableException, NeoFluxException {
		long startTime= System.currentTimeMillis();
		String[] nameParameter = { "idUtente", "identitaDigitale","idProgetto", "descBreveTask"};
		ValidatorInput.verifyNullValue(nameParameter,idUtente,identitaDigitale, idProgetto,descBreveTask);
		logger.warn("\n\n\n\n################################\nstarting task: "+descBreveTask+" for idProgetto: "+idProgetto+ " , idUtente: "+idUtente);
		
		int ret = genericDAO.callProcedure().startAttivita(BigDecimal.valueOf(idProgetto),
				descBreveTask,BigDecimal.valueOf(idUtente));
		
		logger.warn("startAttivita ret code---> : "+ret);
		// v_ritorno := 0; -- TUTTO OK
		// v_ritorno := 1; -- KO : Task lockato
		if(ret==1){
			logger.warn("attivita is locked, throw exception to client !!!");
			throw new NeoFluxException("Attivita' usata da un altro utente");
		}
		
		logger.warn( "method executed in ms: "+(System.currentTimeMillis()-startTime)+"\n\n\n");
		return null;
	}


	/*
	 FUNCTION UnlockAttivita (p_id_progetto       IN  PBANDI_T_PROGETTO.ID_PROGETTO%TYPE,
            p_descr_breve_task  IN  PBANDI_D_TASK.DESCR_BREVE_TASK%TYPE,
            p_id_utente         IN  PBANDI_T_UTENTE.ID_UTENTE%TYPE) 
	 */
	public void unlockAttivita(Long idUtente, String identitaDigitale,
			Long idProgetto, String descBreveTask) throws CSIException,
			SystemException, UnrecoverableException, NeoFluxException {
		long startTime= System.currentTimeMillis();
		String[] nameParameter = { "idUtente", "identitaDigitale","idProgetto", "descBreveTask"};
		ValidatorInput.verifyNullValue(nameParameter,idUtente,identitaDigitale, idProgetto,descBreveTask);
		logger.warn("unlock task: "+descBreveTask+" for idProgetto: "+idProgetto+ " , idUtente: "+idUtente);
		
		int ret = genericDAO.callProcedure().unlockAttivita(BigDecimal.valueOf(idProgetto),
				descBreveTask,BigDecimal.valueOf(idUtente));
		
		logger.warn("ret code---> : "+ret);
		// v_ritorno := 0; -- TUTTO OK
		// v_ritorno := 1; -- Task lockato da altro utente
		// v_ritorno := 2; -- Attivit� non ancora istanziata per il processo
		// v_ritorno := 3; -- Attivit� non lockata
		if(ret==1)
			throw new NeoFluxException("Attivita' usata da un altro utente");
		else if(ret==2)
			throw new NeoFluxException("Attivita' non ancora istanziata per il processo");
		
		logger.warn("method executed in ms: "+(System.currentTimeMillis()-startTime));
	}
	 
 

}
