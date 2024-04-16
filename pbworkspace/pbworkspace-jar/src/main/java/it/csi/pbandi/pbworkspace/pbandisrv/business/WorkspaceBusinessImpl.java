/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbworkspace.pbandisrv.business;

/*
import static it.csi.pbandi.pbworkspace.pbandisrv.integration.db.util.Condition.filterBy;
import static it.csi.pbandi.pbworkspace.pbandisrv.integration.db.util.Condition.isFieldNull;
import static it.csi.pbandi.pbworkspace.pbandisrv.integration.db.util.Condition.not;
import static it.csi.pbandi.pbworkspace.pbandisrv.integration.db.util.Condition.validOnly;
import it.csi.csi.wrapper.CSIException;
import it.csi.csi.wrapper.SystemException;
import it.csi.csi.wrapper.UnrecoverableException;
import it.csi.pbandi.pbworkspace.pbandisrv.business.BusinessImpl;
import it.csi.pbandi.pbworkspace.pbandisrv.business.manager.ConfigurationManager;
import it.csi.pbandi.pbworkspace.pbandisrv.business.manager.ProgettoManager;
import it.csi.pbandi.pbworkspace.pbandisrv.business.manager.RuoliProcessoManager;
import it.csi.pbandi.pbworkspace.pbandisrv.dto.workspace.BandoProcessoDTO;
import it.csi.pbandi.pbworkspace.pbandisrv.dto.workspace.DefinizioneProcessoDTO;
import it.csi.pbandi.pbworkspace.pbandisrv.dto.workspace.ElencoIstanzeProcessiEBandiAssociatiDTO;
import it.csi.pbandi.pbworkspace.pbandisrv.dto.workspace.EsitoCaricaUrlMiniAppDTO;
import it.csi.pbandi.pbworkspace.pbandisrv.dto.workspace.EsitoRicercaAttivitaDTO;
import it.csi.pbandi.pbworkspace.pbandisrv.dto.workspace.FiltroIstanzaProcessoDTO;
import it.csi.pbandi.pbworkspace.pbandisrv.dto.workspace.FiltroRicercaAttivitaDTO;
import it.csi.pbandi.pbworkspace.pbandisrv.dto.workspace.IstanzaAttivitaDTO;
import it.csi.pbandi.pbworkspace.pbandisrv.dto.workspace.IstanzaProcessoDTO;
import it.csi.pbandi.pbworkspace.pbandisrv.dto.workspace.StatoAttivitaDTO;
import it.csi.pbandi.pbworkspace.pbandisrv.dto.workspace.UrlDTO;
import it.csi.pbandi.pbworkspace.pbandisrv.exception.workspace.WorkspaceException;
import it.csi.pbandi.pbworkspace.pbandisrv.integration.db.dao.GenericDAO;
import it.csi.pbandi.pbworkspace.pbandisrv.integration.db.jdbctemplate.vo.BandoLineaCodUtenteVO;
import it.csi.pbandi.pbworkspace.pbandisrv.integration.db.jdbctemplate.vo.BandoLineaVO;
import it.csi.pbandi.pbworkspace.pbandisrv.integration.db.jdbctemplate.vo.ProgettoBandoLineaLightVO;
import it.csi.pbandi.pbworkspace.pbandisrv.integration.db.jdbctemplate.vo.ProgettoBandoLineaVO;
import it.csi.pbandi.pbworkspace.pbandisrv.integration.db.jdbctemplate.vo.SoggettoProgettoProcessoNonAggiornatoSuFluxVO;
import it.csi.pbandi.pbworkspace.pbandisrv.integration.db.jdbctemplate.vo.SoggettoProgettoRuoloProcessoVO;
import it.csi.pbandi.pbworkspace.pbandisrv.integration.db.util.AndCondition;
import it.csi.pbandi.pbworkspace.pbandisrv.integration.db.util.Condition;
import it.csi.pbandi.pbworkspace.pbandisrv.integration.db.util.FilterCondition;
import it.csi.pbandi.pbworkspace.pbandisrv.integration.db.util.LikeContainsCondition;
import it.csi.pbandi.pbworkspace.pbandisrv.integration.db.util.LikeStartsWithCondition;
import it.csi.pbandi.pbworkspace.pbandisrv.integration.db.vo.PbandiCDefinizioneProcessoVO;
import it.csi.pbandi.pbworkspace.pbandisrv.integration.db.vo.PbandiTProgettoVO;
import it.csi.pbandi.pbworkspace.pbandisrv.integration.db.vo.PbandiVSoggettoProgettoVO;
import it.csi.pbandi.pbworkspace.pbandisrv.interfacecsi.workspace.WorkspaceSrv;
import it.csi.pbandi.pbworkspace.pbandisrv.util.validation.ValidatorInput;
import it.csi.pbandi.pbandiutil.common.Constants;
import it.csi.pbandi.pbandiutil.common.StringUtil;
import it.csi.pbandi.pbandiutil.common.messages.MessaggiConstants;
*/

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import it.csi.csi.wrapper.CSIException;
import it.csi.csi.wrapper.SystemException;
import it.csi.csi.wrapper.UnrecoverableException;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.dao.GenericDAO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.BandoLineaCodUtenteVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.util.Condition;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.util.LikeContainsCondition;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiVSoggettoProgettoVO;
import it.csi.pbandi.pbservizit.pbandisrv.util.validation.ValidatorInput;
import it.csi.pbandi.pbworkspace.pbandisrv.BusinessImpl;
import it.csi.pbandi.pbworkspace.pbandisrv.dto.workspace.DefinizioneProcessoDTO;
import it.csi.pbandi.pbworkspace.pbandisrv.exception.WorkspaceException;

public class WorkspaceBusinessImpl extends BusinessImpl implements WorkspaceSrv {
	
	@Autowired
	private GenericDAO genericDAO;
	
	public void setGenericDAO(GenericDAO genericDAO) {
		this.genericDAO = genericDAO;
	}

	public GenericDAO getGenericDAO() {
		return genericDAO;
	}
	
	/*
	private ConfigurationManager configurationManager;
	private ProgettoManager progettoManager;
	private RuoliProcessoManager ruoliProcessoManager;

	public ConfigurationManager getConfigurationManager() {
		return configurationManager;
	}

	public void setConfigurationManager(
			ConfigurationManager configurationManager) {
		this.configurationManager = configurationManager;
	}

	public void setGenericDAO(GenericDAO genericDAO) {
		this.genericDAO = genericDAO;
	}

	public GenericDAO getGenericDAO() {
		return genericDAO;
	}

	public void setProgettoManager(ProgettoManager progettoManager) {
		this.progettoManager = progettoManager;
	}

	public ProgettoManager getProgettoManager() {
		return progettoManager;
	}
	public void setRuoliProcessoManager(
			RuoliProcessoManager ruoliProcessoManager) {
		this.ruoliProcessoManager = ruoliProcessoManager;
	}

	public RuoliProcessoManager getRuoliProcessoManager() {
		return ruoliProcessoManager;
	}
	
	*/
	
	public DefinizioneProcessoDTO[] ricercaDefinizioneProcesso(Long idUtente,
			String identitaDigitale, String codUtente,
			DefinizioneProcessoDTO processoRicercato) throws CSIException,
			SystemException, UnrecoverableException, WorkspaceException {

		String nameParameter[] = { "idUtente", "identitaDigitale", "codUtente" };
		ValidatorInput.verifyNullValue(nameParameter, idUtente,
				identitaDigitale, codUtente);

		DefinizioneProcessoDTO[] result;

		try {
			PbandiVSoggettoProgettoVO bandoLineaRicercatoVO = new PbandiVSoggettoProgettoVO();
			bandoLineaRicercatoVO
					.setNomeBandoLinea(processoRicercato != null ? processoRicercato
							.getDescr() : null);
			bandoLineaRicercatoVO.setAscendentOrder("nomeBandoLinea");

			PbandiVSoggettoProgettoVO bandoLineaFiltroVO = new PbandiVSoggettoProgettoVO();
			bandoLineaFiltroVO.setCodUtente(codUtente);
			// }L{ 18/7/2012 se non introduco almeno una colonna non aggregata,
			// la query ci mette un eternita
			final int indexStart = codUtente.indexOf('_');
			final int indexEnd = codUtente.indexOf('@');
			if (indexStart > -1 && indexEnd > indexStart) {
				bandoLineaFiltroVO.setCodiceFiscaleSoggetto(codUtente
						.substring(indexStart + 1, indexEnd));
			}
			List<BandoLineaCodUtenteVO> bandoLineaVOs = genericDAO
				//	.where(new LikeStartsWithCondition<PbandiVSoggettoProgettoVO>( // modified 13/06/16 --> pnrc
					.where(new LikeContainsCondition<PbandiVSoggettoProgettoVO>(
							bandoLineaRicercatoVO)
							.and(Condition.filterBy(bandoLineaFiltroVO))
							.and(Condition
									.validOnly(PbandiVSoggettoProgettoVO.class)))
					.groupBy(BandoLineaCodUtenteVO.class).select();

			Map<String, String> map = new HashMap<String, String>();
			map.put("progrBandoLineaIntervento", "id");
			map.put("nomeBandoLinea", "descr");
			map.put("idProcesso", "idProcesso");

			result = getBeanUtil().transform(bandoLineaVOs,DefinizioneProcessoDTO.class, map);
		} catch (Exception e) {
			String message = "ricerca definizioni fallita: " + e.getMessage();
			logger.error(message, e);
			throw new UnrecoverableException(message, e);
		}  
		return result;
	}
	
	/*

	public void avviaIstanzaProcesso(Long idUtente, String identitaDigitale,
			String codUtente, DefinizioneProcessoDTO processoDaAvviare)
			throws CSIException, SystemException, UnrecoverableException,
			WorkspaceException {
		 
		 
	}

	public IstanzaProcessoDTO[] caricaElencoIstanzeProcessi(Long idUtente,
			String identitaDigitale, String codUtente) throws CSIException,
			SystemException, UnrecoverableException, WorkspaceException {
		 
		return null;
	}

	public StatoAttivitaDTO[] caricaElencoStatiAttivita(Long idUtente,
			String identitaDigitale) throws CSIException, SystemException,
			UnrecoverableException, WorkspaceException {

		return null;
	}

	public EsitoCaricaUrlMiniAppDTO caricaUrlMiniAppAttivita(Long idUtente,
			String identitaDigitale, String codUtente,
			IstanzaAttivitaDTO istanzaAttivita) throws CSIException,
			SystemException, UnrecoverableException, WorkspaceException {
		String nameParameter[] = { "idUtente", "identitaDigitale", "codUtente",
				"istanzaAttivita", "istanzaAttivita.id" };
		ValidatorInput.verifyNullValue(nameParameter, idUtente,
				identitaDigitale, codUtente, istanzaAttivita,
				istanzaAttivita.getId());

		EsitoCaricaUrlMiniAppDTO esito = new EsitoCaricaUrlMiniAppDTO();
		esito.setEsito(false);
		return esito;
	}

	public UrlDTO caricaUrlMiniAppAttivitaPreliminare(Long idUtente,
			String identitaDigitale, String codUtente,
			DefinizioneProcessoDTO definizioneProcesso) throws CSIException,
			SystemException, UnrecoverableException, WorkspaceException {
         logger.error("\n\n\n\n\n\n\n\n\n This METHOD  caricaUrlMiniAppAttivitaPreliminare MUST NOT BE CALLED ANYMORE \n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
		BandoLineaVO valueObject = new BandoLineaVO();
		valueObject.setProgrBandoLineaIntervento(new BigDecimal(definizioneProcesso.getId()));
		String uuidProcesso = genericDAO.findSingleWhere(valueObject).getUuidProcesso();

		UrlDTO url = new UrlDTO();
		
		logger.info("\n\n\n\n\n\nFLUXXXXXXX\n#################################\n");
		logger.info("calling fluxDAO.getUrlMiniAppAttivitaPreliminareProcesso");
		//url.setUrl(fluxDAO.getUrlMiniAppAttivitaPreliminareProcesso(codUtente,uuidProcesso));
		
		return url;
	}

	public EsitoRicercaAttivitaDTO ricercaAttivita(Long idUtente,
			String identitaDigitale, FiltroRicercaAttivitaDTO filtro)
			throws CSIException, SystemException, UnrecoverableException,
			WorkspaceException {
		
		
        logger.error("\n\n\n\n\n\n\n\n\n This METHOD  ricercaAttivita  MUST NOT BE CALLED ANYMORE \n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
        
        
			logger.info("\n\n\n#######################\nricercaAttivita\n");
			logger.shallowDump(filtro, "info");
			logger.shallowDump(filtro.getAttivitaRicercata(), "info");
			EsitoRicercaAttivitaDTO esito = new EsitoRicercaAttivitaDTO();
			String nameParameter[] = { "idUtente", "identitaDigitale", "filtro" };
			ValidatorInput.verifyNullValue(nameParameter, idUtente,
					identitaDigitale, filtro);
			try {

			IstanzaAttivitaDTO attivitaRicercata = filtro.getAttivitaRicercata();
			String codUtente = filtro.getCodUtente();
			String[] statiSelezionati = filtro.getStatiSelezionati();

			SoggettoProgettoRuoloProcessoVO filtroVO = beanUtil.transform(filtro, SoggettoProgettoRuoloProcessoVO.class);
			logger.shallowDump(filtroVO, "info");


			boolean migrazioneInCorso = configurationManager.getConfiguration().isMigrazioneInCorso();
			List<Long> progettiNonMigrati = null;
			if (migrazioneInCorso) {
				progettiNonMigrati = findProgettiNonMigrati(filtroVO,attivitaRicercata);
			}
			logger.info("migrazioneInCorso :"+migrazioneInCorso);
			boolean aggiornaMappaturaPerSoggettoCompleta=aggiornaMappaturaPerSoggettoCompleta(idUtente, filtroVO);
			logger.info("aggiornaMappaturaPerSoggettoCompleta :"+aggiornaMappaturaPerSoggettoCompleta);
			
			if (!aggiornaMappaturaPerSoggettoCompleta) {
				esito.setEsito(false);
				esito.setMessage(MessaggiConstants.WRKSPC_ATTIVITA_NOT_REFRESHED);
			} else {
				Map<String, Object> variabiliDiProcesso = new HashMap<String, Object>();
				if (filtro.getIdSoggettoBeneficiario() != null) {
					variabiliDiProcesso.put(Constants.PROCESS_VARIABLE_ID_BENEFICIARIO,
							filtro.getIdSoggettoBeneficiario());
				}

				ArrayList<IstanzaAttivitaDTO> istanzeAttivitaDTO = eseguiRicercaAttivita(
						attivitaRicercata, codUtente, statiSelezionati,variabiliDiProcesso, progettiNonMigrati);

				esito.setEsito(true);
				if (migrazioneInCorso) {
					esito.setMessage(MessaggiConstants.WRKSPC_PROGETTI_IN_MIGRAZIONE);
				}
				esito.setAttivitaTrovate(istanzeAttivitaDTO
						.toArray(new IstanzaAttivitaDTO[istanzeAttivitaDTO
								.size()]));
			}
		} catch (Exception e) {
			logger.error("Ricerca attivit� fallita: " + e.getMessage(), e);
			throw new UnrecoverableException("Ricerca fallita: "
					+ e.getMessage(), e);
		}  
		return esito;
	}

	private List<Long> findProgettiNonMigrati(
			SoggettoProgettoRuoloProcessoVO filtroVO,
			IstanzaAttivitaDTO attivitaRicercata) {
		String processoAttuale = genericDAO.findSingleWhere(
				new PbandiCDefinizioneProcessoVO()).getUuidProcesso();
		String progetto = attivitaRicercata != null ? attivitaRicercata
				.getDescrIstanzaProcesso() : null;
		String bando = attivitaRicercata != null ? attivitaRicercata
				.getDescrDefinizioneProcesso() : null;
		PbandiTProgettoVO definizioneVO = new PbandiTProgettoVO();
		definizioneVO.setIdIstanzaProcesso(processoAttuale);
		if (!StringUtil.isEmpty(progetto)) {
			PbandiTProgettoVO progettoVO = new PbandiTProgettoVO();
			progettoVO.setCodiceVisualizzato(progetto);
			List<PbandiTProgettoVO> progettiNonMigrati = genericDAO
					.where(Condition
							.filterBy(progettoVO)
							.and(Condition
									.not(new LikeStartsWithCondition<PbandiTProgettoVO>(
											definizioneVO)),
									Condition.not(Condition.isFieldNull(
											PbandiTProgettoVO.class,
											"idIstanzaProcesso")))).select();
			return beanUtil.extractValues(progettiNonMigrati, "idProgetto",
					Long.class);
		} else if (!StringUtil.isEmpty(bando)) {
			ProgettoBandoLineaLightVO queryProgettiVO = new ProgettoBandoLineaLightVO();
			queryProgettiVO.setDescrizioneBando(bando);
			List<PbandiTProgettoVO> progettiVO = beanUtil.transformList(
					genericDAO.findListWhere(queryProgettiVO),
					PbandiTProgettoVO.class, new HashMap<String, String>() {
						{
							put("idProgetto", "idProgetto");
						}
					});
			List<PbandiTProgettoVO> progettiNonMigrati = genericDAO
					.where(Condition
							.filterBy(progettiVO)
							.and(Condition
									.not(new LikeStartsWithCondition<PbandiTProgettoVO>(
											definizioneVO)),
									Condition.not(Condition.isFieldNull(
											PbandiTProgettoVO.class,
											"idIstanzaProcesso")))).select();
			return beanUtil.extractValues(progettiNonMigrati, "idProgetto",
					Long.class);
		} else {
			SoggettoProgettoRuoloProcessoVO definizione = new SoggettoProgettoRuoloProcessoVO();
			definizione.setIdIstanzaProcesso(processoAttuale);

			List<SoggettoProgettoRuoloProcessoVO> progettiNonMigrati = genericDAO
					.where(Condition
							.filterBy(filtroVO)
							.and(Condition
									.not(new LikeStartsWithCondition<SoggettoProgettoRuoloProcessoVO>(
											definizione)),
									Condition.not(Condition
											.isFieldNull(
													SoggettoProgettoRuoloProcessoVO.class,
													"idIstanzaProcesso"))))
					.select();
			return beanUtil.extractValues(progettiNonMigrati, "idProgetto",
					Long.class);
		}
	}

	private ArrayList<IstanzaAttivitaDTO> eseguiRicercaAttivita(
			IstanzaAttivitaDTO attivitaRicercata, String codUtente,
			String[] statiSelezionati, Map<String, Object> variabiliDiProcesso,
			List<Long> idProgettoDaEscludere) throws UnrecoverableException,
			Exception {
		
		
		 logger.error("\n\n\n\n\n\n\n\n\n This METHOD  eseguiRicercaAttivita  MUST NOT BE CALLED ANYMORE \n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
 
		return null;
	}

 


	private boolean aggiornaMappaturaPerSoggettoCompleta(Long idUtente,
			SoggettoProgettoRuoloProcessoVO filtro)
			throws UnrecoverableException, Exception {
		 logger.error("\n\n\n\n\n\n\n\n\n This METHOD  aggiornaMappaturaPerSoggettoCompleta  MUST NOT BE CALLED ANYMORE \n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
		int maxIstanzeDaAggiornareSuFlux = configurationManager
				.getConfiguration().getMaxNumIstanzeFluxAggiornabili()
				.intValue();

		List<SoggettoProgettoProcessoNonAggiornatoSuFluxVO> istanzeNonAggiornateSoggettoProgetto = aggiornaRuoliNonTrasversali(
				idUtente, filtro, maxIstanzeDaAggiornareSuFlux);
		
		return !(istanzeNonAggiornateSoggettoProgetto.size() > maxIstanzeDaAggiornareSuFlux);
	}

	private List<SoggettoProgettoProcessoNonAggiornatoSuFluxVO> aggiornaRuoliNonTrasversali(
			Long idUtente, SoggettoProgettoRuoloProcessoVO filtro,
			int maxIstanzeDaAggiornare) throws UnrecoverableException {
		 logger.error("\n\n\n\n\n\n\n\n\n This METHOD  aggiornaRuoliNonTrasversali  MUST NOT BE CALLED ANYMORE \n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
		logger.info("\n\n\n################################\naggiornaRuoliNonTrasversali");
	
		// prima di tutto verifico se per il soggetto non esistano istanze non aggiornate su Flux
		filtro.setFlagAggiornatoFlux(Constants.FLAG_FALSE);
		
		logger.info("dump del filtro SoggettoProgettoRuoloProcessoVO per aggiornaRuoliNonTrasversali");
		logger.shallowDump(filtro,"warn");
		long start=System.currentTimeMillis();
		List<SoggettoProgettoProcessoNonAggiornatoSuFluxVO> istanzeNonAggiornateSoggettoProgetto = genericDAO
				.where(filterBy(filtro).and(
						not(isFieldNull(SoggettoProgettoRuoloProcessoVO.class,
								"idIstanzaProcesso"))).and(
						not(isFieldNull(SoggettoProgettoRuoloProcessoVO.class,
								"progrSoggettoProgetto"))))
				.groupBy(SoggettoProgettoProcessoNonAggiornatoSuFluxVO.class)
				.select();
		logger.info("\n\nsearching istanzeNonAggiornateSoggettoProgetto(con FlagAggiornatoFlux == false and ): "+istanzeNonAggiornateSoggettoProgetto.size()
				+"executed in ms : "+(System.currentTimeMillis()-start)+"\n\n");
      
		logger.info(" for each SoggettoProgettoProcessoNonAggiornatoSuFluxVO calling aggiornaMappaturaUtenti --> changeCandidate");
		
		for (int i = 0; i < maxIstanzeDaAggiornare && i < istanzeNonAggiornateSoggettoProgetto.size(); i++) {
			SoggettoProgettoProcessoNonAggiornatoSuFluxVO istanza = istanzeNonAggiornateSoggettoProgetto.get(i);
			IstanzaProcessoDTO processoDaAggiornare = new IstanzaProcessoDTO();
			processoDaAggiornare.setId(istanza.getIdIstanzaProcesso());
			ruoliProcessoManager
					.aggiornaMappaturaUtenti(
							idUtente,
							filtro.getCodUtente(),
							beanUtil.transform(processoDaAggiornare,
									it.csi.pbandi.pbworkspace.pbandisrv.dto.manager.IstanzaProcessoDTO.class));
		}
		
		return istanzeNonAggiornateSoggettoProgetto;
	}

	public ElencoIstanzeProcessiEBandiAssociatiDTO caricaElencoIstanzeProcessiEBandiAssociati(
			Long idUtente, String identitaDigitale,
			FiltroIstanzaProcessoDTO filtro) throws CSIException,
			SystemException, UnrecoverableException, WorkspaceException {
		try {
			ElencoIstanzeProcessiEBandiAssociatiDTO result = new ElencoIstanzeProcessiEBandiAssociatiDTO();

			SoggettoProgettoRuoloProcessoVO istanzeProcessoFilter = getBeanUtil()
					.transform(filtro, SoggettoProgettoRuoloProcessoVO.class);

			List<SoggettoProgettoRuoloProcessoVO> istanzeProcesso = genericDAO
					.where(filterBy(istanzeProcessoFilter).and(
							not(isFieldNull(
									SoggettoProgettoRuoloProcessoVO.class,
									"idIstanzaProcesso"))).and(
							validOnly(SoggettoProgettoRuoloProcessoVO.class)))
					.select();
			// trova i bandi associati
			HashMap<String, String> map = new HashMap<String, String>();
			map = new HashMap<String, String>();
			map.put("idIstanzaProcesso", "idIstanzaProcesso");
			List<ProgettoBandoLineaVO> progetti = getBeanUtil().transformList(istanzeProcesso, ProgettoBandoLineaVO.class, map);
			
			Map<?, ProgettoBandoLineaVO> bandoLineaRaggruppati = getBeanUtil()
					.index(genericDAO.findListWhere(progetti), "idBandoLinea");

			// mappa i bandi su una lista
			Collection<ProgettoBandoLineaVO> bandiLinea = bandoLineaRaggruppati.values();
			ArrayList<BandoProcessoDTO> bandiAssociati = new ArrayList<BandoProcessoDTO>();
			map = new HashMap<String, String>();
			map.put("idBandoLinea", "id");
			map.put("descrizioneBando", "descr");
			for (ProgettoBandoLineaVO bandoLinea : bandiLinea) {
				BandoProcessoDTO temp = new BandoProcessoDTO();
				getBeanUtil().valueCopy(bandoLinea, temp, map);
				bandiAssociati.add(temp);
			}
			result.setBandiAssociati(bandiAssociati.toArray(new BandoProcessoDTO[bandiAssociati.size()]));

			// filtra o meno i progetti in base al bando selezionato
			List<ProgettoBandoLineaVO> progettiFiltrati = null;
			if (progetti == null || progetti.size() == 0) {
				progettiFiltrati = progetti;
			} else {
				if (filtro.getDescBando() == null) {
					progettiFiltrati = genericDAO
							.findListWhere(new FilterCondition<ProgettoBandoLineaVO>(
									progetti));
				} else {
					ProgettoBandoLineaVO vo = new ProgettoBandoLineaVO();
					vo.setDescrizioneBando(filtro.getDescBando());
					progettiFiltrati = genericDAO
							.findListWhere(new AndCondition<ProgettoBandoLineaVO>(
									new FilterCondition<ProgettoBandoLineaVO>(
											progetti),
									new FilterCondition<ProgettoBandoLineaVO>(
											vo)));
				}
			}
			map = new HashMap<String, String>();
			map.put("codiceVisualizzato", "id");
			List<IstanzaProcessoDTO> istanzeProcessoFiltrate = getBeanUtil()
					.transformList(progettiFiltrati, IstanzaProcessoDTO.class,
							map);

			// }L{ PBANDI-2150
			Map<String, String> idProgetti = getBeanUtil().map(progettiFiltrati, "codiceVisualizzato", "idProgetto");
			for (IstanzaProcessoDTO istanzaProcessoDTO : istanzeProcessoFiltrate) {
				String acronimo = progettoManager.caricaAcronimo(getBeanUtil()
						.transform(idProgetti.get(istanzaProcessoDTO.getId()),
								Long.class));
				if (!StringUtil.isEmpty(acronimo)) {
					istanzaProcessoDTO.setDescr(acronimo + "-"
							+ istanzaProcessoDTO.getId());
				} else {
					istanzaProcessoDTO.setDescr(istanzaProcessoDTO.getId());
				}
			}

			Collections.sort(bandiAssociati,
					new Comparator<BandoProcessoDTO>() {
						public int compare(BandoProcessoDTO o1,
								BandoProcessoDTO o2) {
							return o1.getDescr().compareTo(o2.getDescr());
						}
					});
			result.setBandiAssociati(bandiAssociati.toArray(new BandoProcessoDTO[bandiAssociati.size()]));

			Collections.sort(istanzeProcessoFiltrate,
					new Comparator<IstanzaProcessoDTO>() {
						public int compare(IstanzaProcessoDTO o1,
								IstanzaProcessoDTO o2) {
							return o1.getDescr().compareToIgnoreCase(
									o2.getDescr());
						}
					});
			result.setIstanzeProcessi(istanzeProcessoFiltrate
					.toArray(new IstanzaProcessoDTO[istanzeProcessoFiltrate.size()]));

			return result;
		} catch (Exception e) {
			logger.error("Ricerca attivit� fallita: " + e.getMessage(), e);
			throw new UnrecoverableException("Ricerca fallita: "
					+ e.getMessage());
		}  
	}
	 */

}
