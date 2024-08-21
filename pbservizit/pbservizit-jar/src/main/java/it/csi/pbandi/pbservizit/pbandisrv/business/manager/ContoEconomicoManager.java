/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.business.manager;

import it.csi.pbandi.pbservizit.pbandisrv.dto.contoeconomico.*;
import it.csi.pbandi.pbservizit.pbandisrv.dto.manager.ContoEconomicoDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.manager.ContoEconomicoItemDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.manager.*;
import it.csi.pbandi.pbservizit.pbandisrv.exception.manager.ContoEconomicoAggiornamentoStatoFallitoException;
import it.csi.pbandi.pbservizit.pbandisrv.exception.manager.ContoEconomicoCopiaFallitaException;
import it.csi.pbandi.pbservizit.pbandisrv.exception.manager.ContoEconomicoNonTrovatoException;
import it.csi.pbandi.pbservizit.pbandisrv.exception.manager.ContoEconomicoRibaltamentoFallitoException;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.dao.GenericDAO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.*;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.util.AndCondition;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.util.Condition;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.util.FilterCondition;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.*;
import it.csi.pbandi.pbservizit.pbandisrv.interfacecsi.gestionedatididominio.GestioneDatiDiDominioSrv;
import it.csi.pbandi.pbservizit.pbandisrv.util.Constants;
import it.csi.pbandi.pbservizit.pbandiutil.common.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.*;

public class ContoEconomicoManager {
	
	@Autowired
	private BeanUtil beanUtil;
	@Autowired
	private DecodificheManager decodificheManager;
	@Autowired
	private GenericDAO genericDAO;
	@Autowired
	private LoggerUtil logger;
	@Autowired
	private ProgettoManager progettoManager;
	
	
	public void setLogger(LoggerUtil logger) {
		this.logger = logger;
	}

	public LoggerUtil getLogger() {
		return logger;
	}

	public void setBeanUtil(BeanUtil beanUtil) {
		this.beanUtil = beanUtil;
	}

	public BeanUtil getBeanUtil() {
		return beanUtil;
	}


	public void setGenericDAO(GenericDAO genericDAO) {
		this.genericDAO = genericDAO;
	}

	public GenericDAO getGenericDAO() {
		return genericDAO;
	}
	

	public void setDecodificheManager(DecodificheManager decodificheManager) {
		this.decodificheManager = decodificheManager;
	}

	public DecodificheManager getDecodificheManager() {
		return decodificheManager;
	}
	
	public ProgettoManager getProgettoManager() {
		return progettoManager;
	}

	public void setProgettoManager(ProgettoManager progettoManager) {
		this.progettoManager = progettoManager;
	}



	private static final String TOTALE = "Totale";
	private static final String SUBTOTALE_FINANZIAMENTI = "Subtotale finanziamenti";

	public ContoEconomicoDTO findContoEconomicoMasterPotato(
			BigDecimal idProgetto) throws ContoEconomicoNonTrovatoException {
		logger.debug("idProgetto="+idProgetto);
		ContoEconomicoDTO contoEconomico = findContoEconomicoMaster(idProgetto);
		return potaVociTerminaliSenzaRigoAssociato(contoEconomico);
	}

	public ContoEconomicoDTO findContoEconomicoMaster(BigDecimal idProgetto)
			throws ContoEconomicoNonTrovatoException {
		ContoEconomicoConStatoVO c = findContoEconomicoMasterConStato(idProgetto);

		ContoEconomicoDTO contoEconomico = loadContoEconomico(c
				.getIdContoEconomico());
		return contoEconomico;
	}

	public PbandiTContoEconomicoVO findContoEconomicoMainApertoOChiuso(
			BigDecimal idProgetto) throws ContoEconomicoNonTrovatoException {
		return findContoEconomicoChiusoConStato(idProgetto,
				Constants.TIPOLOGIA_CONTO_ECONOMICO_MAIN);
	}

	private ContoEconomicoConStatoVO findContoEconomicoMasterConStato(
			BigDecimal idProgetto) throws ContoEconomicoNonTrovatoException {
		return findContoEconomicoConStato(idProgetto,
				Constants.TIPOLOGIA_CONTO_ECONOMICO_MASTER);
	}

	private ContoEconomicoConStatoVO findContoEconomicoChiusoConStato(
			BigDecimal idProgetto, String tipologiaContoEconomico)
			throws ContoEconomicoNonTrovatoException {
		return findContoEconomicoConStatoValidDate(idProgetto,
				tipologiaContoEconomico, false);
	}

	private ContoEconomicoConStatoVO findContoEconomicoConStato(
			BigDecimal idProgetto, String tipologiaContoEconomico)
			throws ContoEconomicoNonTrovatoException {
		return findContoEconomicoConStatoValidDate(idProgetto,
				tipologiaContoEconomico, true);
	}

	
	private ContoEconomicoConStatoVO findContoEconomicoConStatoValidDate(
			BigDecimal idProgetto, String tipologiaContoEconomico,
			boolean validDateOnly) throws ContoEconomicoNonTrovatoException {

		ContoEconomicoConStatoVO contoEconomicoConStatoVO = new ContoEconomicoConStatoVO();
		contoEconomicoConStatoVO.setIdProgetto(idProgetto);
		contoEconomicoConStatoVO
				.setDescBreveTipologiaContoEco(tipologiaContoEconomico);
		contoEconomicoConStatoVO.setDescendentOrder("idContoEconomico");
		Condition<ContoEconomicoConStatoVO> condition;
		if (validDateOnly) {
			condition = new AndCondition<ContoEconomicoConStatoVO>(
					new FilterCondition<ContoEconomicoConStatoVO>(
							contoEconomicoConStatoVO), Condition
							.validOnly(ContoEconomicoConStatoVO.class));
		} else {
			condition = new FilterCondition<ContoEconomicoConStatoVO>(
					contoEconomicoConStatoVO);
		}

		//todo aggiungere order by desc idContoEconomico
		List<ContoEconomicoConStatoVO> list = genericDAO
				.findListWhere(condition);

		if (list == null || list.size() < 1) {
			throw new ContoEconomicoNonTrovatoException(
					"Nessun conto economico trovato per il progetto "
							+ idProgetto);
		}

		return list.get(0);
	}

	private ContoEconomicoDTO potaVociTerminaliSenzaRigoAssociato(
			ContoEconomicoDTO contoEconomico) {
		rimuoviVociNonAssociateARigo(contoEconomico.getFigli());
		return contoEconomico;
	}

	private void rimuoviVociNonAssociateARigo(List<RigoContoEconomicoDTO> list) {
		if (list != null) {
			Iterator<RigoContoEconomicoDTO> iterator = list.iterator();
			while (iterator.hasNext()) {
				RigoContoEconomicoDTO rigoContoEconomicoDTO = iterator.next();
				if (rigoContoEconomicoDTO.isVoceAssociataARigo()) {
					rimuoviVociNonAssociateARigo(rigoContoEconomicoDTO
							.getFigli());
				} else {
					iterator.remove();
				}
			}
		}
	}

	public List<SoggettiFinanziatoriPerProgettoVO> getSoggettiFinanziatoriPrivati(
			BigDecimal idProgetto) {
		List<SoggettiFinanziatoriPerProgettoVO> listSoggettiFinanziatoriPrivati = genericDAO
				.findListWhere(createFiltroSoggettiFinanziatori(idProgetto,
						Condition.filterBy(getSoggettiFinanziatoriPrivati())));
		return listSoggettiFinanziatoriPrivati;
	}

	private Condition<SoggettiFinanziatoriPerProgettoVO> createFiltroSoggettiFinanziatori(
			BigDecimal idProgetto,
			Condition<SoggettiFinanziatoriPerProgettoVO> condition) {
		SoggettiFinanziatoriPerProgettoVO soggettiFinanziatoriPerProgettoVO = new SoggettiFinanziatoriPerProgettoVO();
		soggettiFinanziatoriPerProgettoVO.setIdProgetto(idProgetto);

		Condition<SoggettiFinanziatoriPerProgettoVO> filtro = Condition
				.filterBy(soggettiFinanziatoriPerProgettoVO).and(condition);
		return filtro;
	}

	// subtotale : S
	public List<SoggettiFinanziatoriPerProgettoVO> getSoggettiFinanziatori(
			BigDecimal idProgetto,String flagAgevolazione) {
		
		BigDecimal idPeriodo = getIdPeriodoDaLineaIntervento(idProgetto);
		SoggettiFinanziatoriPerProgettoVO filtro=new SoggettiFinanziatoriPerProgettoVO();
		filtro.setIdProgetto(idProgetto);
		filtro.setFlagAgevolato(flagAgevolazione);
		filtro.setIdPeriodo(idPeriodo);
		List<SoggettiFinanziatoriPerProgettoVO> listSoggettiFinanziatori= genericDAO
		.findListWhere(filtro);
		
		return listSoggettiFinanziatori;
	}
	
	public List<SoggettiFinanziatoriPerBandoVO> getSoggettiFinanziatoriBando(
			BigDecimal idBando,String flagAgevolazione) {

		SoggettiFinanziatoriPerBandoVO filtro=new SoggettiFinanziatoriPerBandoVO();
		filtro.setProgrBandoLineaIntervento(idBando);
		filtro.setFlagAgevolato(flagAgevolazione);
		List<SoggettiFinanziatoriPerBandoVO> listSoggettiFinanziatori= genericDAO
		.findListWhere(filtro);
		
		return listSoggettiFinanziatori;
	}
	
	/**
	 * Resituisce idPeriodo sulla base della linea di intervento 
	 * se il progetto � nuova programmazione allora restituisce id Periodo 23 altrimenti 11 per la vecchia programmazione.
	 * @param idProgetto
	 * @return 23 per nuova programmazione, 11 per vecchia programmazione
	 */
	
	private BigDecimal getIdPeriodoDaLineaIntervento(BigDecimal idProgetto) {
//		25-09-2023 idPeriodo non serve più, mettere 11 a tutte le linee
//		BigDecimal idLineaIntervento = progettoManager.getLineaDiInterventoNormativa(idProgetto.longValue()).getIdLineaDiIntervento();
		BigDecimal idPeriodo = Constants.ID_PERIODO_VECCHIA_PROGRAMMAZIONE;
//		if(idLineaIntervento.compareTo(Constants.ID_LINEA_DI_INTERVENTO_POR_FESR_14_20) == 0
//				|| idLineaIntervento.compareTo(Constants.ID_LINEA_DI_INTERVENTO_PNRR) == 0){
//			idPeriodo = Constants.ID_PERIODO_POR_FESR_14_20;
//		}
		return idPeriodo;
	}

	// subtotale : S
	public List<SoggettiFinanziatoriPerProgettoVO> getSoggettiFinanziatori(
			BigDecimal idProgetto,String flagAgevolazione, BigDecimal idPeriodo) {
		SoggettiFinanziatoriPerProgettoVO filtro=new SoggettiFinanziatoriPerProgettoVO();
		filtro.setIdProgetto(idProgetto);
		filtro.setFlagAgevolato(flagAgevolazione);
		filtro.setIdPeriodo(idPeriodo);
		List<SoggettiFinanziatoriPerProgettoVO> listSoggettiFinanziatori= genericDAO
		.findListWhere(filtro);
		
		return listSoggettiFinanziatori;
	}
	
	public List<SoggettiFinanziatoriPerProgettoVO> getSoggettiFinanziatoriNonPrivati(
			BigDecimal idProgetto) {
		List<SoggettiFinanziatoriPerProgettoVO> listSoggettiFinanziatoriNonPrivati = genericDAO
				.findListWhere(createFiltroSoggettiFinanziatori(idProgetto,
						Condition.not(Condition
								.filterBy(getSoggettiFinanziatoriPrivati()))));
		return listSoggettiFinanziatoriNonPrivati;
	}

	public List<SoggettiFinanziatoriPerProgettoVO> getSoggettiFinanziatoriNonPrivatiAnnoCorrente(
			BigDecimal idProgetto) {
		String annoCorrente = String.valueOf(DateUtil.getAnno());
		SoggettiFinanziatoriPerProgettoVO annoCorrenteVO = new SoggettiFinanziatoriPerProgettoVO();
		annoCorrenteVO.setDescPeriodo(annoCorrente);
		SoggettiFinanziatoriPerProgettoVO periodoVO = new SoggettiFinanziatoriPerProgettoVO();
		periodoVO.setDescPeriodo("9999"); // TODO
		
		Condition<SoggettiFinanziatoriPerProgettoVO> periodoCondition = Condition
				.filterBy(annoCorrenteVO).or(Condition.filterBy(periodoVO));

		List<SoggettiFinanziatoriPerProgettoVO> listSoggettiFinanziatoriNonPrivati = genericDAO
				.findListWhere(createFiltroSoggettiFinanziatori(
						idProgetto,
						new AndCondition<SoggettiFinanziatoriPerProgettoVO>(
								Condition
										.not(Condition
												.filterBy(getSoggettiFinanziatoriPrivati())),
								periodoCondition)));
		return listSoggettiFinanziatoriNonPrivati;
	}

	private List<SoggettiFinanziatoriPerProgettoVO> getSoggettiFinanziatoriPrivati() {
		SoggettiFinanziatoriPerProgettoVO f1VO = new SoggettiFinanziatoriPerProgettoVO();
		/*
		 * FIX PBandi-985
		 */
		// f1VO.setDescBreveSoggFinanziatore("Privato");
		f1VO
				.setDescBreveTipoSoggFinanziat(it.csi.pbandi.pbservizit.pbandiutil.common.Constants.CODICE_TIPO_SOGG_FINANZIATORE_COFPOR);
		SoggettiFinanziatoriPerProgettoVO f2VO = new SoggettiFinanziatoriPerProgettoVO();
		/*
		 * FIX PBandi-985
		 */
		// f2VO.setDescBreveSoggFinanziatore("Privato FESR");
		f2VO
				.setDescBreveTipoSoggFinanziat(it.csi.pbandi.pbservizit.pbandiutil.common.Constants.CODICE_TIPO_SOGG_FINANZIATORE_OTHFIN);

		List<SoggettiFinanziatoriPerProgettoVO> filtroPrivati = new ArrayList<SoggettiFinanziatoriPerProgettoVO>();
		filtroPrivati.add(f1VO);
		filtroPrivati.add(f2VO);
		return filtroPrivati;
	}

	public Map<String, ContoEconomicoDTO> findContiEconomici(
			BigDecimal idProgetto, String tipologiaContoEconomicoCopy)
			throws ContoEconomicoNonTrovatoException {
		Map<String, ContoEconomicoDTO> contiEconomiciRisultanti = new HashMap<String, ContoEconomicoDTO>();

		ContoEconomicoConStatoVO filtro = new ContoEconomicoConStatoVO();
		filtro.setIdProgetto(idProgetto);
		filtro
				.setDescBreveTipologiaContoEco(Constants.TIPOLOGIA_CONTO_ECONOMICO_MASTER);
		List<ContoEconomicoConStatoVO> listaMaster = genericDAO
				.findListWhere(filtro);

		filtro
				.setDescBreveTipologiaContoEco(Constants.TIPOLOGIA_CONTO_ECONOMICO_MAIN);
		List<ContoEconomicoConStatoVO> listaMain = genericDAO
				.findListWhere(filtro);

		boolean mainDaCaricare = false;
		try {
			ContoEconomicoConStatoVO vo = findContoEconomicoMasterConStato(idProgetto);
			ContoEconomicoDTO contoEconomicoMaster = loadContoEconomico(vo
					.getIdContoEconomico());
			contiEconomiciRisultanti.put(
					Constants.TIPOLOGIA_CONTO_ECONOMICO_MASTER,
					contoEconomicoMaster);

			// cerco una eventuale copia
			List<ContoEconomicoConStatoVO> listaCopia = cercaCopiaDiConto(
					tipologiaContoEconomicoCopy, filtro);
			if (listaCopia.size() > 0) {
				contiEconomiciRisultanti.put(tipologiaContoEconomicoCopy,
						loadContoEconomico(listaCopia.get(0)
								.getIdContoEconomico()));
			}

			// c'� il master, allora il main deve essere chiuso
			if (listaMain.size() != 0
					&& listaMain.get(0).getDtFineValidita() != null) {
				mainDaCaricare = true;
			}
		} catch (ContoEconomicoNonTrovatoException e) {
			// non c'� il master, carico il main per aperto o no che sia
			mainDaCaricare = true;
		}

		if (mainDaCaricare || listaMain.size() != 0) {
			contiEconomiciRisultanti.put(
					Constants.TIPOLOGIA_CONTO_ECONOMICO_MAIN,
					loadContoEconomico(listaMain.get(0).getIdContoEconomico()));
		} else if (listaMaster.size() == 0) {
			/*
			 * no main e no master... no conto economico?
			 */
			throw new ContoEconomicoNonTrovatoException(
					"Nessun conto economico trovato per il progetto "
							+ idProgetto);
		}
		return contiEconomiciRisultanti;
	}

	public Map<String, ContoEconomicoDTO> findContiEconomiciVociDiEntrata(
			BigDecimal idProgetto, String tipologiaContoEconomicoCopy)
			throws ContoEconomicoNonTrovatoException {
		Map<String, ContoEconomicoDTO> contiEconomiciRisultanti = new HashMap<String, ContoEconomicoDTO>();

		ContoEconomicoConStatoVO filtro = new ContoEconomicoConStatoVO();
		filtro.setIdProgetto(idProgetto);
		filtro
				.setDescBreveTipologiaContoEco(Constants.TIPOLOGIA_CONTO_ECONOMICO_MASTER);
		List<ContoEconomicoConStatoVO> listaMaster = genericDAO
				.findListWhere(filtro);

		filtro
				.setDescBreveTipologiaContoEco(Constants.TIPOLOGIA_CONTO_ECONOMICO_MAIN);
		List<ContoEconomicoConStatoVO> listaMain = genericDAO
				.findListWhere(filtro);

		boolean mainDaCaricare = false;
		try {
			ContoEconomicoConStatoVO vo = findContoEconomicoMasterConStato(idProgetto);
			ContoEconomicoDTO contoEconomicoMaster = loadContoVociDiEntrata(vo
					.getIdContoEconomico());
			contiEconomiciRisultanti.put(
					Constants.TIPOLOGIA_CONTO_ECONOMICO_MASTER,
					contoEconomicoMaster);

			// cerco una eventuale copia
			List<ContoEconomicoConStatoVO> listaCopia = cercaCopiaDiConto(
					tipologiaContoEconomicoCopy, filtro);
			if (listaCopia.size() > 0) {
				contiEconomiciRisultanti.put(tipologiaContoEconomicoCopy,
						loadContoVociDiEntrata(listaCopia.get(0)
								.getIdContoEconomico()));
			}

			// c'� il master, allora il main deve essere chiuso
			if (listaMain.size() != 0
					&& listaMain.get(0).getDtFineValidita() != null) {
				mainDaCaricare = true;
			}
		} catch (ContoEconomicoNonTrovatoException e) {
			// non c'� il master, carico il main per aperto o no che sia
			mainDaCaricare = true;
		}

		if (mainDaCaricare || listaMain.size() != 0) {
			contiEconomiciRisultanti.put(
					Constants.TIPOLOGIA_CONTO_ECONOMICO_MAIN,
					loadContoVociDiEntrata(listaMain.get(0).getIdContoEconomico()));
		} else if (listaMaster.size() == 0) {
			/*
			 * no main e no master... no conto economico?
			 */
			throw new ContoEconomicoNonTrovatoException(
					"Nessun conto economico trovato per il progetto "
							+ idProgetto);
		}
		return contiEconomiciRisultanti;
	}

	public List<ContoEconomicoConStatoVO> cercaCopiaDiConto(
			String tipologiaContoEconomicoCopy, ContoEconomicoConStatoVO filtro)
			throws ContoEconomicoNonTrovatoException {
		filtro.setDescBreveTipologiaContoEco(tipologiaContoEconomicoCopy);
		// TNT fix 13/9/11 fix cercaCopiaDiConto
		filtro.setDescendentOrder("idContoEconomico");
		List<ContoEconomicoConStatoVO> listaCopia = genericDAO
				.findListWhere(new AndCondition<ContoEconomicoConStatoVO>(
						new FilterCondition<ContoEconomicoConStatoVO>(filtro),
						Condition.validOnly(ContoEconomicoConStatoVO.class)));

		//FIXME per derrico ,modificare la query order by descent id conto
		// non rilanciare l'eccezione 
//		if (listaCopia.size() > 1) {
//			throw new ContoEconomicoNonTrovatoException(
//					"Trovata pi� di un conto copia di tipo "
//							+ tipologiaContoEconomicoCopy);
//		}

		return listaCopia;
	}

	/**
	 * carica un conto economico completo di righi associati e calcola i totali
	 * di ammesso, richiesto (definiti per ogni conto) e di rendicontato,
	 * quietanzato e validato (definiti solo per i conti master)
	 * 
	 * @param idContoEconomico
	 * @return
	 * @throws ContoEconomicoNonTrovatoException
	 * @throws Exception
	 */
	public ContoEconomicoDTO loadContoEconomico(BigDecimal idContoEconomico)
			throws ContoEconomicoNonTrovatoException {
		/*
		 * carico tutto il conto da db, compresi i righi e le voci non ancora
		 * associate a righi
		 */
		ContoEconomicoConRighiEVociVO filtro = new ContoEconomicoConRighiEVociVO();
		filtro.setIdContoEconomico(idContoEconomico);
		List<ContoEconomicoConRighiEVociVO> list = genericDAO
				.findListWhere(filtro);

		if (list == null || list.size() == 0) {
			throw new ContoEconomicoNonTrovatoException(
					"Conto economico non trovato: " + idContoEconomico);
		}

		Map<BigDecimal, List<RigoContoEconomicoDTO>> indexVociFiglie = new HashMap<BigDecimal, List<RigoContoEconomicoDTO>>();
		Map<BigDecimal, RigoContoEconomicoDTO> indexVoci = new HashMap<BigDecimal, RigoContoEconomicoDTO>();
		List<RigoContoEconomicoDTO> vociRadice = new ArrayList<RigoContoEconomicoDTO>();

		for (ContoEconomicoConRighiEVociVO contoEconomicoConRighiEVociVO : list) {
			RigoContoEconomicoDTO rigo = getBeanUtil().transform(
					contoEconomicoConRighiEVociVO, RigoContoEconomicoDTO.class);
			indexVoci.put(contoEconomicoConRighiEVociVO.getIdVoceDiSpesa(),
					rigo);
			BigDecimal idVoceDiSpesaPadre = contoEconomicoConRighiEVociVO
					.getIdVoceDiSpesaPadre();
			if (idVoceDiSpesaPadre == null) {
				vociRadice.add(rigo);
			} else {
				List<RigoContoEconomicoDTO> vociFiglie = indexVociFiglie
						.get(idVoceDiSpesaPadre);
				if (vociFiglie == null) {
					vociFiglie = new ArrayList<RigoContoEconomicoDTO>();
					indexVociFiglie.put(idVoceDiSpesaPadre, vociFiglie);
				}
				vociFiglie.add(rigo);
			}
		}

		// collego i figli ai padri
		for (BigDecimal idVoceCorrente : indexVoci.keySet()) {
			List<RigoContoEconomicoDTO> vociFiglie = indexVociFiglie
					.get(idVoceCorrente);
			if (vociFiglie != null) {
				RigoContoEconomicoDTO rigoContoEconomicoDTO = indexVoci
						.get(idVoceCorrente);
				rigoContoEconomicoDTO.setFigli(vociFiglie);
			}
		}

		ContoEconomicoConRighiEVociVO contoEconomicoConRighiEVociVO = list
				.get(0);

		ContoEconomicoDTO contoRisultante = new ContoEconomicoDTO();
		contoRisultante.setIdContoEconomico(contoEconomicoConRighiEVociVO
				.getIdContoEconomico());
		contoRisultante.setDtInizioValidita(contoEconomicoConRighiEVociVO
				.getDtInizioValidita());
		contoRisultante.setDtFineValidita(contoEconomicoConRighiEVociVO
				.getDtFineValidita());
		contoRisultante.setImportoImpegnoVincolante(contoEconomicoConRighiEVociVO.getImportoImpegnoVincolante());
		contoRisultante.setFigli(vociRadice);

		PbandiTDomandaVO domandaVO = new PbandiTDomandaVO();
		domandaVO.setIdDomanda(contoEconomicoConRighiEVociVO.getIdDomanda());
		domandaVO = genericDAO.findSingleWhere(domandaVO);

		contoRisultante.setDataPresentazioneDomanda(domandaVO
				.getDtPresentazioneDomanda());

		/*
		 * VN: Ribasso d' asta Per ogni conto economico puo' esistere solo un
		 * ribasso d' asta o nessuno. Nel caso in cui c'e' ne sono piu' di uno
		 * lanciamo un' eccezione.
		 */
		PbandiTRibassoAstaVO ribassoAstaVO = new PbandiTRibassoAstaVO();
		ribassoAstaVO.setIdContoEconomico(contoEconomicoConRighiEVociVO
				.getIdContoEconomico());
		List<PbandiTRibassoAstaVO> ribassi = genericDAO
				.findListWhere(ribassoAstaVO);
		if (ribassi != null && !ribassi.isEmpty() && ribassi.size() > 1) {
			ContoEconomicoNonTrovatoException e = new ContoEconomicoNonTrovatoException(
					"Errore trovati piu' di un ribasso associato al conto economino("
							+ contoEconomicoConRighiEVociVO
									.getIdContoEconomico() + ")");
			throw e;
		} else if (ribassi == null || ribassi.isEmpty()) {
			contoRisultante
					.setFlagRibassoAsta(it.csi.pbandi.pbservizit.pbandiutil.common.Constants.FLAG_FALSE);
		} else {
			ribassoAstaVO = ribassi.get(0);
			contoRisultante
					.setFlagRibassoAsta(it.csi.pbandi.pbservizit.pbandiutil.common.Constants.FLAG_TRUE);
			contoRisultante.setPercRibassoAsta(ribassoAstaVO.getPercentuale());
			contoRisultante.setImportoRibassoAsta(ribassoAstaVO.getImporto());
		}

		calcolaSommeAggregateDaiFigliEVerificaAssociazioniARighi(contoRisultante);

		contoRisultante.setDescBreveStatoContoEconom(decodificheManager
				.findDescBreve(PbandiDStatoContoEconomicoVO.class,
						contoEconomicoConRighiEVociVO
								.getIdStatoContoEconomico()));

		return contoRisultante;
	}

	public ContoEconomicoDTO loadContoVociDiEntrata(BigDecimal idContoEconomico)
			throws ContoEconomicoNonTrovatoException {
		/*
		 * carico tutto il conto da db, compresi i righi e le voci non ancora
		 * associate a righi
		 */
		CEVociDiEntrataCulturaVO filtro = new CEVociDiEntrataCulturaVO();
		filtro.setIdContoEconomico(idContoEconomico);
		List<CEVociDiEntrataCulturaVO> list = genericDAO
				.findListWhere(filtro);

		if (list == null || list.size() == 0) {
			throw new ContoEconomicoNonTrovatoException(
					"Conto economico non trovato: " + idContoEconomico);
		}

		Map<BigDecimal, List<RigoContoEconomicoDTO>> indexVociFiglie = new HashMap<BigDecimal, List<RigoContoEconomicoDTO>>();
		Map<BigDecimal, RigoContoEconomicoDTO> indexVoci = new HashMap<BigDecimal, RigoContoEconomicoDTO>();
		List<RigoContoEconomicoDTO> vociRadice = new ArrayList<RigoContoEconomicoDTO>();

		for (CEVociDiEntrataCulturaVO CEVociDiEntrataCulturaVO : list) {
			RigoContoEconomicoDTO rigo = getBeanUtil().transform(
					CEVociDiEntrataCulturaVO, RigoContoEconomicoDTO.class);
			indexVoci.put(CEVociDiEntrataCulturaVO.getIdVoceDiSpesa(),
					rigo);
			BigDecimal idVoceDiSpesaPadre = CEVociDiEntrataCulturaVO
					.getIdVoceDiSpesaPadre();
			if (idVoceDiSpesaPadre == null) {
				vociRadice.add(rigo);
			} else {
				List<RigoContoEconomicoDTO> vociFiglie = indexVociFiglie
						.get(idVoceDiSpesaPadre);
				if (vociFiglie == null) {
					vociFiglie = new ArrayList<RigoContoEconomicoDTO>();
					indexVociFiglie.put(idVoceDiSpesaPadre, vociFiglie);
				}
				vociFiglie.add(rigo);
			}
		}

		// collego i figli ai padri
		for (BigDecimal idVoceCorrente : indexVoci.keySet()) {
			List<RigoContoEconomicoDTO> vociFiglie = indexVociFiglie
					.get(idVoceCorrente);
			if (vociFiglie != null) {
				RigoContoEconomicoDTO rigoContoEconomicoDTO = indexVoci
						.get(idVoceCorrente);
				rigoContoEconomicoDTO.setFigli(vociFiglie);
			}
		}

		CEVociDiEntrataCulturaVO CEVociDiEntrataCulturaVO = list
				.get(0);

		ContoEconomicoDTO contoRisultante = new ContoEconomicoDTO();
		contoRisultante.setIdContoEconomico(CEVociDiEntrataCulturaVO
				.getIdContoEconomico());
		contoRisultante.setDtInizioValidita(CEVociDiEntrataCulturaVO
				.getDtInizioValidita());
		contoRisultante.setDtFineValidita(CEVociDiEntrataCulturaVO
				.getDtFineValidita());
		contoRisultante.setImportoImpegnoVincolante(CEVociDiEntrataCulturaVO.getImportoImpegnoVincolante());
		contoRisultante.setFigli(vociRadice);

		PbandiTDomandaVO domandaVO = new PbandiTDomandaVO();
		domandaVO.setIdDomanda(CEVociDiEntrataCulturaVO.getIdDomanda());
		domandaVO = genericDAO.findSingleWhere(domandaVO);

		contoRisultante.setDataPresentazioneDomanda(domandaVO
				.getDtPresentazioneDomanda());

		/*
		 * VN: Ribasso d' asta Per ogni conto economico puo' esistere solo un
		 * ribasso d' asta o nessuno. Nel caso in cui c'e' ne sono piu' di uno
		 * lanciamo un' eccezione.
		 */
		PbandiTRibassoAstaVO ribassoAstaVO = new PbandiTRibassoAstaVO();
		ribassoAstaVO.setIdContoEconomico(CEVociDiEntrataCulturaVO
				.getIdContoEconomico());
		List<PbandiTRibassoAstaVO> ribassi = genericDAO
				.findListWhere(ribassoAstaVO);
		if (!ribassi.isEmpty() && ribassi.size() > 1) {
			ContoEconomicoNonTrovatoException e = new ContoEconomicoNonTrovatoException(
					"Errore trovati piu' di un ribasso associato al conto economino("
							+ CEVociDiEntrataCulturaVO
							.getIdContoEconomico() + ")");
			throw e;
		} else if (ribassi.isEmpty()) {
			contoRisultante
					.setFlagRibassoAsta(it.csi.pbandi.pbservizit.pbandiutil.common.Constants.FLAG_FALSE);
		} else {
			ribassoAstaVO = ribassi.get(0);
			contoRisultante
					.setFlagRibassoAsta(it.csi.pbandi.pbservizit.pbandiutil.common.Constants.FLAG_TRUE);
			contoRisultante.setPercRibassoAsta(ribassoAstaVO.getPercentuale());
			contoRisultante.setImportoRibassoAsta(ribassoAstaVO.getImporto());
		}

		calcolaSommeAggregateDaiFigliEVerificaAssociazioniARighi(contoRisultante);

		contoRisultante.setDescBreveStatoContoEconom(decodificheManager
				.findDescBreve(PbandiDStatoContoEconomicoVO.class,
						CEVociDiEntrataCulturaVO
								.getIdStatoContoEconomico()));

		return contoRisultante;
	}
	private void calcolaSommeAggregateDaiFigliEVerificaAssociazioniARighi(
			RigoContoEconomicoDTO rigo) {
		Stack<RigoContoEconomicoDTO> stackItem = new Stack<RigoContoEconomicoDTO>();
		Stack<Iterator<RigoContoEconomicoDTO>> stackIterator = new Stack<Iterator<RigoContoEconomicoDTO>>();

		RigoContoEconomicoDTO currentItem = rigo;
		Iterator<RigoContoEconomicoDTO> currentIterator = currentItem
				.getFigli() == null ? null : currentItem.getFigli().iterator();

		if (currentIterator != null) {
			prepareItem(currentItem);
		}

		boolean notFinished = true;

		while (notFinished) {
			RigoContoEconomicoDTO nextItem = null;

			if (currentIterator != null && currentIterator.hasNext()) {
				nextItem = currentIterator.next();
				nextItem.setVoceAssociataARigo(nextItem
						.getIdRigoContoEconomico() != null);

				List<RigoContoEconomicoDTO> figli = nextItem.getFigli();

				if (figli != null && figli.size() > 0) {
					stackItem.add(currentItem);
					currentItem = nextItem;
					nextItem = null;
					stackIterator.add(currentIterator);
					currentIterator = figli.iterator();

					prepareItem(currentItem);
				}
			} else {
				if (!stackIterator.isEmpty()) {
					currentIterator = stackIterator.pop();
					nextItem = currentItem;
					currentItem = stackItem.pop();
				} else {
					notFinished = false;
				}
			}

			if (nextItem != null) {
				reduceChilToParent(currentItem, nextItem);
			}
		}
	}

	private void reduceChilToParent(RigoContoEconomicoDTO currentItem,
			RigoContoEconomicoDTO nextItem) {
		currentItem.setVoceAssociataARigo(currentItem.isVoceAssociataARigo()
				|| nextItem.isVoceAssociataARigo());

		currentItem.setImportoRichiesto(NumberUtil.sum(currentItem
				.getImportoRichiesto(), NumberUtil
				.createScaledBigDecimal(nextItem.getImportoRichiesto())));
		currentItem.setImportoAmmesso(NumberUtil.sum(currentItem
				.getImportoAmmesso(), NumberUtil
				.createScaledBigDecimal(nextItem.getImportoAmmesso())));
		currentItem.setImportoQuietanzato(NumberUtil.sum(currentItem
				.getImportoQuietanzato(), NumberUtil
				.createScaledBigDecimal(nextItem.getImportoQuietanzato())));
		currentItem.setImportoRendicontato(NumberUtil.sum(currentItem
				.getImportoRendicontato(), NumberUtil
				.createScaledBigDecimal(nextItem.getImportoRendicontato())));
		currentItem.setImportoValidato(NumberUtil.sum(currentItem
				.getImportoValidato(), NumberUtil
				.createScaledBigDecimal(nextItem.getImportoValidato())));
	}

	private void prepareItem(RigoContoEconomicoDTO currentItem) {
		currentItem.setImportoRichiesto(new BigDecimal(0));
		currentItem.setImportoAmmesso(new BigDecimal(0));
		currentItem.setImportoQuietanzato(new BigDecimal(0));
		currentItem.setImportoRendicontato(new BigDecimal(0));
		currentItem.setImportoValidato(new BigDecimal(0));
		currentItem.setVoceAssociataARigo(false);
	}

	public Double getTotaleSpesaAmmmessaInRendicontazioneDouble(
			BigDecimal idProgetto) {
		BigDecimal importoAmmesso = getTotaleSpesaAmmmessaInRendicontazione(idProgetto);
		return beanUtil.transform(importoAmmesso, Double.class);
	}

	public BigDecimal getTotaleSpesaAmmmessaInRendicontazione(
			BigDecimal idProgetto) {
		logger.debug("idProgetto="+idProgetto);
		BigDecimal importoAmmesso = null;
		try {
			importoAmmesso = findContoEconomicoMasterPotato(idProgetto)
					.getImportoAmmesso();
		} catch (ContoEconomicoNonTrovatoException e) {
			logger.warn(e.getMessage());
		}
		return importoAmmesso;
	}

	/**
	 * 
	 * @param idContoEconomico
	 *            id della copia o del conto sorgente se la copia non esiste
	 * @param tipologiaContoEconomicoCopy
	 * @param idUtente
	 * @return
	 * @throws ContoEconomicoNonTrovatoException
	 * @throws ContoEconomicoCopiaFallitaException
	 * @throws ContoEconomicoRibaltamentoFallitoException
	 */
	public ContoEconomicoDTO creaCopia(BigDecimal idContoEconomico,
			String tipologiaContoEconomicoCopy, BigDecimal idUtente,
			String statoContoEconomicoSorgente,
			String statoContoEconomicoDestinazione)
			throws ContoEconomicoNonTrovatoException,
			ContoEconomicoCopiaFallitaException,
			ContoEconomicoRibaltamentoFallitoException {
		ContoEconomicoDTO contoEconomicoCopia;

		// cerco una eventuale copia
		ContoEconomicoConStatoVO filtro = new ContoEconomicoConStatoVO();
		filtro.setIdContoEconomico(idContoEconomico);
		List<ContoEconomicoConStatoVO> listaCopia = cercaCopiaDiConto(
				tipologiaContoEconomicoCopy, filtro);

		if (listaCopia != null && listaCopia.size() > 0) {
			// trovata copia gi� presente
			contoEconomicoCopia = loadContoEconomico(idContoEconomico);
		} else {
			// copia non presente, la creo dal conto sorgente
			ContoEconomicoDTO contoSorgente = loadContoEconomico(idContoEconomico);
			contoEconomicoCopia = copia(contoSorgente,
					statoContoEconomicoSorgente, idUtente);
			PbandiTContoEconomicoVO contoEconomicoVO = new PbandiTContoEconomicoVO();
			contoEconomicoVO.setIdContoEconomico(contoSorgente
					.getIdContoEconomico());
			contoEconomicoVO.setIdUtenteAgg(idUtente);
			contoEconomicoVO
					.setIdStatoContoEconomico(decodeStatoContoEconomico(statoContoEconomicoDestinazione));
			try {
				genericDAO.update(contoEconomicoVO);
			} catch (Exception e) {
				logger.warn(e.getMessage());
				throw new ContoEconomicoCopiaFallitaException(e.getMessage(), e);
			}
		}

		return contoEconomicoCopia;
	}

	public String calcolaStatoContoMasterDalTipoCopia(
			String tipologiaContoEconomicoCopy) {
		String res = null;

		if (tipologiaContoEconomicoCopy
				.equals(Constants.TIPOLOGIA_CONTO_ECONOMICO_COPY_BEN)) {
			res = Constants.STATO_CONTO_ECONOMICO_IN_PROPOSTA_PER_GESTIONE_OPERATIVA;
		} else if (tipologiaContoEconomicoCopy
				.equals(Constants.TIPOLOGIA_CONTO_ECONOMICO_COPY_IST)) {
			res = Constants.STATO_CONTO_ECONOMICO_IN_RIMODULAZIONE;
		}
		return res;
	}

	public String calcolaStatoInzialeDallaCopiaDalTipo(
			String tipologiaContoEconomicoCopy) {
		String res = null;

		if (tipologiaContoEconomicoCopy
				.equals(Constants.TIPOLOGIA_CONTO_ECONOMICO_COPY_BEN)) {
			res = Constants.STATO_CONTO_ECONOMICO_NUOVA_PROPOSTA;
		} else if (tipologiaContoEconomicoCopy
				.equals(Constants.TIPOLOGIA_CONTO_ECONOMICO_COPY_IST)) {
			res = Constants.STATO_CONTO_ECONOMICO_NUOVA_RIMODULAZIONE;
		}
		return res;
	}

	private ContoEconomicoDTO copia(ContoEconomicoDTO contoMaster,
			String descBreveStatoContoEconom, BigDecimal idUtente)
			throws ContoEconomicoCopiaFallitaException,
			ContoEconomicoNonTrovatoException,
			ContoEconomicoRibaltamentoFallitoException {
		// creo una copia sulla t_conto

		PbandiTContoEconomicoVO vo = genericDAO.findSingleWhere(Condition
				.filterByKeyOf(getBeanUtil().transform(contoMaster,
						PbandiTContoEconomicoVO.class)));
		vo.setIdContoEconomico(null);
		vo.setIdUtenteIns(idUtente);
		vo.setIdUtenteAgg(null);
		vo.setDtInizioValidita(DateUtil.getSysdate());
		vo
				.setIdStatoContoEconomico(decodeStatoContoEconomico(descBreveStatoContoEconom));
		PbandiTContoEconomicoVO contoCopiaVO;
		try {
			contoCopiaVO = genericDAO.insert(vo);
		} catch (Exception e) {
			logger.warn(e.getMessage());
			throw new ContoEconomicoCopiaFallitaException(e.getMessage(), e);
		}

		Map<String, String> mapCampiDaRibaltare = new HashMap<String, String>();
		mapCampiDaRibaltare
				.put("importoAmmesso", "importoAmmessoFinanziamento");
		mapCampiDaRibaltare.put("importoRichiesto", "importoSpesa");

		ribaltaRighiContoDaA(contoMaster, loadContoEconomico(contoCopiaVO
				.getIdContoEconomico()), contoCopiaVO, mapCampiDaRibaltare,
				idUtente, false);
		return loadContoEconomico(contoCopiaVO.getIdContoEconomico());

	}

	private BigDecimal decodeStatoContoEconomico(
			String descBreveStatoContoEconom) {
		PbandiDStatoContoEconomicoVO statoContoEconomicoVO = new PbandiDStatoContoEconomicoVO();
		statoContoEconomicoVO
				.setDescBreveStatoContoEconom(descBreveStatoContoEconom);
		BigDecimal idStatoContoEconomico = genericDAO.findSingleWhere(
				statoContoEconomicoVO).getIdStatoContoEconomico();
		return idStatoContoEconomico;
	}

	private void ribaltaRighiContoDaA(RigoContoEconomicoDTO rigoOrigine,
			RigoContoEconomicoDTO rigoDestinazione,
			PbandiTContoEconomicoVO contoDestinazioneVO,
			Map<String, String> mapCampiDaRibaltare, BigDecimal idUtente,
			boolean richiestaConto)
			throws ContoEconomicoRibaltamentoFallitoException {
		try {
			if (rigoOrigine != null) {
				if (rigoOrigine.getFigli() != null) {
					// ha figli, scendo di livello
					Map<? extends Object, RigoContoEconomicoDTO> indiceFigliDestinazione = rigoDestinazione != null
							&& rigoDestinazione.getFigli() != null ? getBeanUtil()
							.index(rigoDestinazione.getFigli(), "idVoceDiSpesa")
							: new HashMap<BigDecimal, RigoContoEconomicoDTO>();

					for (RigoContoEconomicoDTO rigoFiglioOrigine : rigoOrigine
							.getFigli()) {
						RigoContoEconomicoDTO destinazione = indiceFigliDestinazione
								.remove(rigoFiglioOrigine.getIdVoceDiSpesa());
						ribaltaRighiContoDaA(rigoFiglioOrigine, destinazione,
								contoDestinazioneVO, mapCampiDaRibaltare,
								idUtente, richiestaConto);
					}

					for (RigoContoEconomicoDTO rigoSuMasterMaNonSuCopy : indiceFigliDestinazione
							.values()) {
						logger
								.warn("Trovato rigo sul master che non � presente sulla copia per la voce: "
										+ rigoSuMasterMaNonSuCopy
												.getIdVoceDiSpesa()
										+ " - "
										+ rigoSuMasterMaNonSuCopy
												.getDescVoceDiSpesa());
					}
				} else {
					Map<String, String> map = new HashMap<String, String>();
					map.putAll(mapCampiDaRibaltare);
					map.put("idVoceDiSpesa", "idVoceDiSpesa");
					PbandiTRigoContoEconomicoVO vo = getBeanUtil()
							.transform(rigoOrigine,
									PbandiTRigoContoEconomicoVO.class, map);
					vo.setIdContoEconomico(contoDestinazioneVO
							.getIdContoEconomico());

					if (rigoDestinazione == null
							|| rigoDestinazione.getIdRigoContoEconomico() == null) {
						// non ha figli e non ha un corrispondente sulla
						// destinazione
						if (!getBeanUtil().propertiesNull(rigoOrigine,
								mapCampiDaRibaltare.keySet())) {

							vo.setDtInizioValidita(contoDestinazioneVO
									.getDtInizioValidita());

							vo.setIdUtenteIns(contoDestinazioneVO
									.getIdUtenteIns());
							vo.setIdRigoContoEconomico(null);
							// TNT
							if (richiestaConto
									&& (vo.getImportoSpesa() == null || vo
											.getImportoSpesa().equals(
													new BigDecimal(0))))
								logger.debug("non inserisco: importo 0");
							else
								genericDAO.insert(vo);
						}
					} else {
						// non ha figli ma ha un corrispondente sull
						// destinazione da
						// aggiornare
						if (NumberUtil.compare(rigoDestinazione
								.getImportoAmmesso(), rigoOrigine
								.getImportoAmmesso()) != 0
								|| NumberUtil.compare(rigoDestinazione
										.getImportoRichiesto(), rigoOrigine
										.getImportoRichiesto()) != 0) {

							vo.setIdRigoContoEconomico(rigoDestinazione
									.getIdRigoContoEconomico());
							vo.setIdUtenteAgg(idUtente);

							genericDAO.update(vo);
						}
					}
				}
			}
		} catch (Exception e) {
			logger.warn(e.getMessage());
			throw new ContoEconomicoRibaltamentoFallitoException(
					e.getMessage(), e);
		}
	}

	public BigDecimal findIdProgetto(BigDecimal idContoEconomico) {
		ContoEconomicoConStatoVO filtro = new ContoEconomicoConStatoVO();
		filtro.setIdContoEconomico(idContoEconomico);
		return genericDAO.findSingleWhere(filtro).getIdProgetto();
	}

	public void aggiornaStoricizzandoRighiContoEconomico(
			RigoContoEconomicoDTO c, BigDecimal idUtente) throws Exception {
		List<RigoContoEconomicoDTO> figli = c.getFigli();
		if (figli != null && figli.size() > 0) {
			for (RigoContoEconomicoDTO rigoContoEconomicoDTO : figli) {
				aggiornaStoricizzandoRighiContoEconomico(rigoContoEconomicoDTO,
						idUtente);
			}
		} else {
			if (c.getImportoAmmesso() != null
					|| c.getImportoRichiesto() != null) {
				PbandiTRigoContoEconomicoVO rigo;
				boolean inserisciNuovoRigo = false;

				if (c.getIdRigoContoEconomico() != null
						&& !c.getIdRigoContoEconomico().equals(
								new BigDecimal(0))) {
					PbandiTRigoContoEconomicoVO rigoPrec = new PbandiTRigoContoEconomicoVO();
					rigoPrec.setIdRigoContoEconomico(c
							.getIdRigoContoEconomico());
					rigoPrec = genericDAO.findSingleWhere(rigoPrec);

					if (NumberUtil.compare(rigoPrec
							.getImportoAmmessoFinanziamento(), c
							.getImportoAmmesso()) != 0
							|| NumberUtil.compare(rigoPrec.getImportoSpesa(), c
									.getImportoRichiesto()) != 0) {
						rigo = new PbandiTRigoContoEconomicoVO();
						rigo.setIdRigoContoEconomico(c
								.getIdRigoContoEconomico());
						rigo.setDtFineValidita(DateUtil.getSysdate());
						rigo.setIdUtenteAgg(idUtente);
						genericDAO.update(rigo);
						c.setIdRigoContoEconomico(null);

						inserisciNuovoRigo = true;
					}
				} else {
					if (NumberUtil.compare(c.getImportoAmmesso(),
							new BigDecimal(0L)) > 0
							|| NumberUtil.compare(c.getImportoRichiesto(),
									new BigDecimal(0L)) > 0) {
						inserisciNuovoRigo = true;
					}
				}

				if (inserisciNuovoRigo) {
					// questa logica � molto simile al ribalta
					rigo = getBeanUtil().transform(c,
							PbandiTRigoContoEconomicoVO.class);
					rigo.setIdContoEconomico(c.getIdContoEconomico());
					rigo.setImportoSpesa(c.getImportoRichiesto());
					rigo.setImportoAmmessoFinanziamento(c.getImportoAmmesso());
					rigo.setIdUtenteIns(idUtente);
					rigo.setDtFineValidita(null);
					rigo.setDtInizioValidita(DateUtil.getSysdate());
					genericDAO.insert(rigo);
				}
			}
		}
	}

	public boolean isContoEconomicoMasterApprovato(BigDecimal idProgetto) {
		boolean result = false;

		try {
			ContoEconomicoConStatoVO ce = findContoEconomicoMasterConStato(idProgetto);
			result = NumberUtil
					.compare(
							ce.getIdStatoContoEconomico(),
							decodeStatoContoEconomico(Constants.STATO_CONTO_ECONOMICO_DA_APPROVARE)) != 0;
		} catch (ContoEconomicoNonTrovatoException e) {
			logger.warn("Nessun master per il progetto: " + idProgetto
					+ " (assumo che non sia approvato).");
		}

		return result;
	}

	public BigDecimal getQuotaImportoAgevolato(BigDecimal idProgetto,
			String codModalitaAgevolazione)
			throws ContoEconomicoNonTrovatoException {

		// CALCOLO IMPORTO DEL CONTO ECONOMICO
		ModalitaDiAgevolazioneContoEconomicoVO modalitaDiAgevolazioneContoEconomicoVO = new ModalitaDiAgevolazioneContoEconomicoVO();
		modalitaDiAgevolazioneContoEconomicoVO
				.setIdContoEconomico(getIdContoMaster(idProgetto));
		modalitaDiAgevolazioneContoEconomicoVO
				.setDescBreveModalitaAgevolaz(codModalitaAgevolazione);
		modalitaDiAgevolazioneContoEconomicoVO
				.setFlagLvlprj(it.csi.pbandi.pbservizit.pbandiutil.common.Constants.FLAG_TRUE);
		modalitaDiAgevolazioneContoEconomicoVO = genericDAO
				.findSingleWhere(modalitaDiAgevolazioneContoEconomicoVO);
		return modalitaDiAgevolazioneContoEconomicoVO
				.getQuotaImportoAgevolato();
	}

	public BigDecimal getIdContoMaster(BigDecimal idProgetto)
			throws ContoEconomicoNonTrovatoException {
		return findContoEconomicoMasterConStato(idProgetto)
				.getIdContoEconomico();
	}

	public List<ModalitaDiAgevolazioneContoEconomicoVO> caricaModalitaAgevolazioneContoChiuso(
			BigDecimal idProgetto, String tipologiaContoEconomico)
			throws ContoEconomicoNonTrovatoException {
		ModalitaDiAgevolazioneContoEconomicoVO modalitaDiAgevolazioneContoEconomicoVO = new ModalitaDiAgevolazioneContoEconomicoVO();
		modalitaDiAgevolazioneContoEconomicoVO
				.setIdContoEconomico(findContoEconomicoChiusoConStato(
						idProgetto, tipologiaContoEconomico)
						.getIdContoEconomico());

		return genericDAO.findListWhere(modalitaDiAgevolazioneContoEconomicoVO);
	}

	public List<ModalitaDiAgevolazioneContoEconomicoVO> caricaModalitaAgevolazione(
			BigDecimal idProgetto, String tipologiaContoEconomico)
			throws ContoEconomicoNonTrovatoException {
		ModalitaDiAgevolazioneContoEconomicoVO modalitaDiAgevolazioneContoEconomicoVO = new ModalitaDiAgevolazioneContoEconomicoVO();
		modalitaDiAgevolazioneContoEconomicoVO
				.setIdContoEconomico(findContoEconomicoConStato(idProgetto,
						tipologiaContoEconomico).getIdContoEconomico());

		return genericDAO.findListWhere(modalitaDiAgevolazioneContoEconomicoVO);
	}

	public List<ModalitaDiAgevolazioneContoEconomicoPerBilancioVO> caricaModalitaAgevolazionePerBilancio(
			BigDecimal idProgetto, String tipologiaContoEconomico)
			throws ContoEconomicoNonTrovatoException {
		//FIXME rifattorizzare con il metodo  caricaModalitaAgevolazione
		ModalitaDiAgevolazioneContoEconomicoPerBilancioVO modalitaDiAgevolazioneContoEconomicoVO = new ModalitaDiAgevolazioneContoEconomicoPerBilancioVO();
		modalitaDiAgevolazioneContoEconomicoVO
				.setIdContoEconomico(findContoEconomicoConStato(idProgetto,
						tipologiaContoEconomico).getIdContoEconomico());
		return genericDAO.findListWhere(modalitaDiAgevolazioneContoEconomicoVO);
	}

	public List<ModalitaDiAgevolazioneContoEconomicoVO> caricaModalitaAgevolazione(
			BigDecimal idContoEconomico)
			throws ContoEconomicoNonTrovatoException {
		ModalitaDiAgevolazioneContoEconomicoVO modalitaDiAgevolazioneContoEconomicoVO = new ModalitaDiAgevolazioneContoEconomicoVO();
		modalitaDiAgevolazioneContoEconomicoVO
				.setIdContoEconomico(idContoEconomico);

		return genericDAO.findListWhere(modalitaDiAgevolazioneContoEconomicoVO);
	}

	public Double findImportoAgevolato(Long idProgetto) {
		try {
			return NumberUtil.toDouble(NumberUtil.accumulate(
					caricaModalitaAgevolazione(new BigDecimal(idProgetto),
							Constants.TIPOLOGIA_CONTO_ECONOMICO_MASTER),
					"quotaImportoAgevolato").getQuotaImportoAgevolato());
		} catch (ContoEconomicoNonTrovatoException e) {
			logger.warn(e.getMessage());
			return null;
		} catch (RuntimeException e) {
			logger.error(e.getMessage(), e);
			return null;
		}
	}

	public Double findImportoErogatoNettoDiRecuperi(Long idProgetto) {
		PbandiTErogazioneVO erogazioni = new PbandiTErogazioneVO();
		PbandiTRecuperoVO recuperi = new PbandiTRecuperoVO();
		erogazioni.setIdProgetto(new BigDecimal(idProgetto));
		recuperi.setIdProgetto(new BigDecimal(idProgetto));
		Double count = (genericDAO.where(erogazioni).sum("importoErogazione") - genericDAO
				.sum(recuperi, "importoRecupero"));
		return count;
	}

	public Long chiudiCopiaRimodulazione(BigDecimal idProgetto,
			BigDecimal idUtente, String note, String contrattiDaStipulare, String riferimento,
			Double importoFinanziamentoBanca,
			Double importoGiuridicoVincolante) throws Exception {

		Map<String, String> mapCampiDaRibaltare = new HashMap<String, String>();
		mapCampiDaRibaltare
				.put("importoAmmesso", "importoAmmessoFinanziamento");

		PbandiTContoEconomicoVO contoEconomicoVO = new PbandiTContoEconomicoVO();
		contoEconomicoVO.setImportoFinanziamentoBanca(BeanUtil
				.transformToBigDecimal(importoFinanziamentoBanca));
		contoEconomicoVO.setIdContoEconomico(findContoEconomicoMasterConStato(
				idProgetto).getIdContoEconomico());
		contoEconomicoVO.setIdUtenteAgg(idUtente);
		genericDAO.update(contoEconomicoVO);

		contoEconomicoVO = new PbandiTContoEconomicoVO();
		contoEconomicoVO.setRiferimento(riferimento);
		contoEconomicoVO.setNoteContoEconomico(note);
		contoEconomicoVO.setContrattiDaStipulare(contrattiDaStipulare);
		contoEconomicoVO.setImportoFinanziamentoBanca(BeanUtil
				.transformToBigDecimal(importoFinanziamentoBanca));

		
		
		BigDecimal idContoEconomicoCopyIst = findContoEconomicoConStato(idProgetto,
				Constants.TIPOLOGIA_CONTO_ECONOMICO_COPY_IST)
				.getIdContoEconomico();
		
		contoEconomicoVO.setIdContoEconomico(idContoEconomicoCopyIst);
		contoEconomicoVO.setIdUtenteAgg(idUtente);
		genericDAO.update(contoEconomicoVO);

		chiudiCopiaContoEconomico(idProgetto, idUtente, mapCampiDaRibaltare,
				Constants.TIPOLOGIA_CONTO_ECONOMICO_COPY_IST,
				Constants.STATO_CONTO_ECONOMICO_APPROVATO,
				Constants.STATO_CONTO_ECONOMICO_NUOVA_RIMODULAZIONE_CONCLUSA,
				importoGiuridicoVincolante);
		return NumberUtil.toLong(idContoEconomicoCopyIst);
	}

	public Long chiudiInvioPropostaRimodulazione(BigDecimal idProgetto,
			BigDecimal idUtente, String noteContoEconomico,
			Double importoFinanzBancaRichiesto) throws Exception {
		Map<String, String> mapCampiDaRibaltare = new HashMap<String, String>();
		mapCampiDaRibaltare.put("importoRichiesto", "importoSpesa");

		PbandiTContoEconomicoVO contoEconomicoVO = new PbandiTContoEconomicoVO();
		contoEconomicoVO.setNoteContoEconomico(noteContoEconomico);
		contoEconomicoVO.setImportoFinanzBancaRichiesto(BeanUtil
				.transformToBigDecimal(importoFinanzBancaRichiesto));
		ContoEconomicoConStatoVO master = findContoEconomicoMasterConStato(idProgetto);
		contoEconomicoVO.setIdContoEconomico(master.getIdContoEconomico());
		contoEconomicoVO.setIdUtenteAgg(idUtente);
		genericDAO.update(contoEconomicoVO);

		contoEconomicoVO = new PbandiTContoEconomicoVO();
		contoEconomicoVO.setNoteContoEconomico(noteContoEconomico);
		BigDecimal idContoEconomico;
		try{
			idContoEconomico = findContoEconomicoConStato(idProgetto, Constants.TIPOLOGIA_CONTO_ECONOMICO_COPY_BEN).getIdContoEconomico();
			contoEconomicoVO.setIdContoEconomico(idContoEconomico);
			contoEconomicoVO.setIdUtenteAgg(idUtente);
			genericDAO.update(contoEconomicoVO);
		}
		catch(ContoEconomicoNonTrovatoException e){
			//ne inserisco uno nuovo se non esiste
			contoEconomicoVO.setIdContoEconomico(null);
			contoEconomicoVO.setIdUtenteIns(idUtente);
			contoEconomicoVO.setDtInizioValidita(DateUtil.getSysdate());
			contoEconomicoVO.setIdStatoContoEconomico(decodeStatoContoEconomico(Constants.STATO_CONTO_ECONOMICO_NUOVA_PROPOSTA_INVIATA));
			contoEconomicoVO.setIdDomanda(master.getIdDomanda());
			contoEconomicoVO = genericDAO.insert(contoEconomicoVO);
			idContoEconomico = contoEconomicoVO.getIdContoEconomico();
		}


		chiudiCopiaContoEconomico(idProgetto, idUtente, mapCampiDaRibaltare,
				Constants.TIPOLOGIA_CONTO_ECONOMICO_COPY_BEN,
				Constants.STATO_CONTO_ECONOMICO_DA_APPROVARE,
				Constants.STATO_CONTO_ECONOMICO_NUOVA_PROPOSTA_INVIATA,null);
		return NumberUtil.toLong(idContoEconomico);
	}

	private void chiudiCopiaContoEconomico(BigDecimal idProgetto,
			BigDecimal idUtente, Map<String, String> mapCampiDaRibaltare,
			String tipoCopia, String nuovoStatoMaster, String nuovoStatoCopia,
			Double importoGiuridicoVincolante)
			throws Exception {

		RigoContoEconomicoDTO contoCopiaDTO = loadContoEconomico(findContoEconomicoConStato(
				idProgetto, tipoCopia).getIdContoEconomico());

		PbandiTContoEconomicoVO contoMasterVO = new PbandiTContoEconomicoVO();
		contoMasterVO.setIdContoEconomico(getIdContoMaster(idProgetto));
		contoMasterVO
				.setIdStatoContoEconomico(decodeStatoContoEconomico(nuovoStatoMaster));

		//TNT 20 giugno 2011
		if(importoGiuridicoVincolante!=null)
			contoMasterVO.setImportoImpegnoVincolante(BeanUtil
				.transformToBigDecimal(importoGiuridicoVincolante));
		//TNT 20 giugno 2011
		contoMasterVO.setIdUtenteAgg(idUtente);
		genericDAO.update(contoMasterVO);

		contoMasterVO.setDtInizioValidita(DateUtil.getSysdate());
		contoMasterVO.setIdUtenteIns(idUtente);

		ribaltaRighiContoDaA(contoCopiaDTO, loadContoEconomico(contoMasterVO
				.getIdContoEconomico()), contoMasterVO, mapCampiDaRibaltare,
				idUtente, false);

		PbandiTContoEconomicoVO contoCopiaVO = new PbandiTContoEconomicoVO();
		contoCopiaVO.setIdContoEconomico(contoCopiaDTO.getIdContoEconomico());
		contoCopiaVO
				.setIdStatoContoEconomico(decodeStatoContoEconomico(nuovoStatoCopia));
		contoCopiaVO.setDtFineValidita(DateUtil.getSysdate());

		contoCopiaVO.setIdUtenteAgg(idUtente);
		genericDAO.update(contoCopiaVO);
	}

	// TNT

	private static final String IMPORTO = "IMPORTO";

	public List<ContoEconomicoItemDTO> mappaContoEconomicoPerReport(
			ContoEconomicoRimodulazioneDTO contoEconomicoRimodulazione,
			boolean isRimodulazione, boolean isCopiaPresente,
			boolean isModifica, java.util.Date date, boolean inIstruttoria) {

		ArrayList<ContoEconomicoItemDTO> contoEconomicoItemList = new ArrayList<ContoEconomicoItemDTO>();
		Double importo = new Double(0);
		Map<String, Double> mapImporto = new HashMap<String, Double>();
		mapImporto.put(IMPORTO, importo);

		if (!ObjectUtil.isNull(contoEconomicoRimodulazione)) {

			ContoEconomicoRimodulazioneDTO contoEconomicoDTOarray[] = contoEconomicoRimodulazione
					.getFigli();

			if (!ObjectUtil.isEmpty(contoEconomicoDTOarray)) {
				int level = 1;
				mappaContiEconomiciFigliPerVisualizzazione(
						contoEconomicoItemList, contoEconomicoDTOarray, level,
						isCopiaPresente, isRimodulazione, mapImporto, null,
						isModifica, date, inIstruttoria);
			}
		}

		importo = calcolaImportoPerVisualizzazione(contoEconomicoItemList,	isRimodulazione);
		mapImporto.put(IMPORTO, importo);

		mappaPrimeRighePerVisualizzazione(contoEconomicoRimodulazione,
				contoEconomicoItemList, mapImporto, isModifica);

		calcolaImportiMacroVociPerVisualizzazione(contoEconomicoItemList, false);

		return contoEconomicoItemList;

	}

	private void mappaContiEconomiciFigliPerVisualizzazione(
			ArrayList<ContoEconomicoItemDTO> contoEconomicoItemList,
			ContoEconomicoRimodulazioneDTO[] contoEconomicoDTOarray, int level,
			boolean isCopiaPresente, boolean isRimodulazione,
			Map<String, Double> mapImporto, String idPadre, boolean isModifica,
			java.util.Date date, Boolean inIstruttoria) {

		for (ContoEconomicoRimodulazioneDTO contoEconomicoDTO : contoEconomicoDTOarray) {
			ContoEconomicoItemDTO contoEconomicoItem = new ContoEconomicoItemDTO();
			beanUtil.valueCopy(contoEconomicoDTO, contoEconomicoItem);

			contoEconomicoItem.setIdPadre(idPadre);

			// VALORI DI DEFAULT
			Double zero = new Double(0);
			Double spesaAmmessaUltima = contoEconomicoDTO
					.getImportoSpesaAmmessaUltima() != null ? contoEconomicoDTO
					.getImportoSpesaAmmessaUltima() : zero;
			Double ultimaProposta = contoEconomicoDTO
					.getImportoRichiestoUltimaProposta() != null ? contoEconomicoDTO
					.getImportoRichiestoUltimaProposta()
					: zero;

			Double value = null;

			if (isRimodulazione) {
				value = spesaAmmessaUltima;
				contoEconomicoItem
						.setImportoSpesaAmmessaRimodulazione(spesaAmmessaUltima);
			} else {
				value = ultimaProposta;
				contoEconomicoItem
						.setImportoRichiestoNuovaProposta(ultimaProposta);
			}

			mapImporto.put(IMPORTO, value);

			// VALORI DI DEFAULT
			contoEconomicoItem.setLevel(level);
			contoEconomicoItem.setId(contoEconomicoDTO.getIdVoce().toString());

			if (!ObjectUtil.isEmpty(contoEconomicoDTO.getFigli())) {
				contoEconomicoItem.setHaFigli(true);
				contoEconomicoItem.setModificabile(false);

				contoEconomicoItemList.add(contoEconomicoItem);
				ContoEconomicoRimodulazioneDTO contoEconomicoFigli[] = contoEconomicoDTO
						.getFigli();

				String idVocePadre = contoEconomicoDTO.getIdVoce() != null ? contoEconomicoDTO
						.getIdVoce().toString()
						: null;

				mappaContiEconomiciFigliPerVisualizzazione(
						contoEconomicoItemList, contoEconomicoFigli, level + 1,
						isCopiaPresente, isRimodulazione, mapImporto,
						idVocePadre, isModifica, date, inIstruttoria);
				// non modificabile
			} else {
				// modificabile
				contoEconomicoItem.setModificabile(true);
				contoEconomicoItemList.add(contoEconomicoItem);
			}
		}
	}

	private void calcolaImportiMacroVociPerVisualizzazione(
			ArrayList<ContoEconomicoItemDTO> contoEconomicoItemList,
			boolean isRimodulazione) {

		logger.begin();
		Map<String, Double> mapMacroVoci = new HashMap<String, Double>();
		for (ContoEconomicoItemDTO contoEconomicoItem : contoEconomicoItemList) {

			String idPadre = contoEconomicoItem.getIdPadre();
			Long idVoce = contoEconomicoItem.getIdVoce();
			Double importo = mapMacroVoci.get(idPadre);
			if (importo == null) {
				importo = 0D;
			}
			if (isRimodulazione) {
				importo = NumberUtil.sum(importo, contoEconomicoItem
						.getImportoSpesaAmmessaRimodulazione());
			} else {
				importo = NumberUtil.sum(importo, contoEconomicoItem
						.getImportoRichiestoNuovaProposta());
			}

			if (idPadre != null && importo != null) {
				mapMacroVoci.put(idPadre, importo);
			} else if (!contoEconomicoItem.getHaFigli() && importo != null) {
				mapMacroVoci.put(idVoce.toString(), importo);
			}
		}

		for (ContoEconomicoItemDTO contoEconomicoItem : contoEconomicoItemList) {
			if (contoEconomicoItem.getIdVoce() != null) {
				if (mapMacroVoci.containsKey(contoEconomicoItem.getIdVoce().toString())) {
					Double importo = mapMacroVoci.get(contoEconomicoItem.getIdVoce().toString());
					
					logger.info("level=["+ contoEconomicoItem.getLevel() + "], idPadre=["+contoEconomicoItem.getIdPadre()+
							"], ImportoRichiestoInDomanda=[" +contoEconomicoItem.getImportoRichiestoInDomanda() +
							"], ImportoRichiestoNuovaProposta=[" +contoEconomicoItem.getImportoRichiestoNuovaProposta() + 
							"], ImportoRichiestoUltimaProposta=[" + contoEconomicoItem.getImportoRichiestoUltimaProposta()+
							"], ImportoSpesaAmmessaInDetermina=[" +contoEconomicoItem.getImportoSpesaAmmessaInDetermina() +
							"], ImportoSpesaAmmessaRimodulazione=[" + contoEconomicoItem.getImportoSpesaAmmessaRimodulazione()+
							"], ImportoSpesaAmmessaUltima=[" +contoEconomicoItem.getImportoSpesaAmmessaUltima() +
							"], ImportoSpesaQuietanziata=[" +contoEconomicoItem.getImportoSpesaQuietanziata() +
							"], ImportoSpesaRendicontata=[" +contoEconomicoItem.getImportoSpesaRendicontata() +
							"], ImportoSpesaValidataTotale=[" +contoEconomicoItem.getImportoSpesaValidataTotale() +
							"], label=[" + contoEconomicoItem.getLabel() +	"]");
							
					
					// PK :calcolo la differenza tra ImportoSpesaAmmessaRimodulazione e ImportoRichiestoUltimaProposta
					// Questo valore serve solo per il bando Architetture Rurali (template Rimodulazione_ContoEconomico_ArchRurali.jrxml)
										
					Double irup = 0D;
					Double isar = 0D;
					if(contoEconomicoItem.getImportoRichiestoUltimaProposta()!=null) {
						irup = contoEconomicoItem.getImportoRichiestoUltimaProposta();
					}
					
					if(contoEconomicoItem.getImportoSpesaAmmessaUltima()!=null) {
						isar =contoEconomicoItem.getImportoSpesaAmmessaUltima();
					}
					
					Double delta = isar - irup;
					logger.info("isar=["+isar+"] - irup=["+irup+"] = delta=["+delta+"]");
					
					if(delta!=0) {
						logger.info("1 settato delta=["+delta+"]");
						contoEconomicoItem.setDelta(delta);
					}
					
					if (isRimodulazione) {
						contoEconomicoItem
								.setImportoSpesaAmmessaRimodulazione(importo);
					} else {
						contoEconomicoItem
								.setImportoRichiestoNuovaProposta(importo);
					}
				}
			}
			
			if ("Conto Economico".equals(contoEconomicoItem.getLabel())) {
				Double irup = 0D;
				Double isar = 0D;
				if(contoEconomicoItem.getImportoRichiestoUltimaProposta()!=null) {
					irup = contoEconomicoItem.getImportoRichiestoUltimaProposta();
				}
				
				if(contoEconomicoItem.getImportoSpesaAmmessaRimodulazione()!=null) {
					isar =contoEconomicoItem.getImportoSpesaAmmessaRimodulazione();
				}
				
				Double delta = isar - irup;
				logger.info("Conto Economico isar=["+isar+"] - irup=["+irup+"] = delta=["+delta+"]");
				
				if(delta!=0) {
					logger.info("2 settato delta=["+delta+"]");
					contoEconomicoItem.setDelta(delta);
				}
			}
		}
		logger.end();
	
	}

	private Double calcolaImportoPerVisualizzazione(
			ArrayList<ContoEconomicoItemDTO> contoEconomicoItemList,
			boolean isRimodulazione) {

		Double importo = new Double(0);
		for (ContoEconomicoItemDTO contoEconomicoItem : contoEconomicoItemList) {

			if (contoEconomicoItem.getModificabile()) {
				if (isRimodulazione) {
					if (contoEconomicoItem
							.getImportoSpesaAmmessaRimodulazione() != null
							&& contoEconomicoItem
									.getImportoSpesaAmmessaRimodulazione() > 0) {

						importo = NumberUtil.sum(importo, contoEconomicoItem
								.getImportoSpesaAmmessaRimodulazione());
					}

				} else {
					if (contoEconomicoItem.getImportoRichiestoNuovaProposta() != null
							&& contoEconomicoItem
									.getImportoRichiestoNuovaProposta() > 0) {
						importo = NumberUtil.sum(importo, contoEconomicoItem
								.getImportoRichiestoNuovaProposta());
					}

				}
			}
		}
		return importo;
	}

	private void mappaPrimeRighePerVisualizzazione(
			ContoEconomicoRimodulazioneDTO contoEconomicoRimodulazione,
			ArrayList<ContoEconomicoItemDTO> contoEconomicoItemList,
			Map<String, Double> mapImporto, boolean isModifica) {

		ContoEconomicoItemDTO contoEconomicoItemTotali = new ContoEconomicoItemDTO();

		contoEconomicoItemTotali
				.setImportoRichiestoInDomanda(contoEconomicoRimodulazione
						.getImportoRichiestoInDomanda());

		contoEconomicoItemTotali
				.setImportoRichiestoUltimaProposta(contoEconomicoRimodulazione
						.getImportoRichiestoUltimaProposta());

		Double importo = mapImporto.get(IMPORTO);

		if (importo == null)
			importo = 0D;

		contoEconomicoItemTotali.setLabel("Conto Economico");

		contoEconomicoItemTotali.setImportoSpesaAmmessaRimodulazione(importo);

		contoEconomicoItemTotali.setImportoRichiestoNuovaProposta(importo);

		contoEconomicoItemTotali
				.setImportoSpesaAmmessaInDetermina(contoEconomicoRimodulazione
						.getImportoSpesaAmmessaInDetermina());
		contoEconomicoItemTotali
				.setImportoSpesaAmmessaUltima(contoEconomicoRimodulazione
						.getImportoSpesaAmmessaUltima());
		contoEconomicoItemTotali
				.setImportoSpesaRendicontata(contoEconomicoRimodulazione
						.getImportoSpesaRendicontata());
		contoEconomicoItemTotali
				.setImportoSpesaQuietanziata(contoEconomicoRimodulazione
						.getImportoSpesaQuietanziata());
		contoEconomicoItemTotali
				.setImportoSpesaValidataTotale(contoEconomicoRimodulazione
						.getImportoSpesaValidataTotale());
		contoEconomicoItemTotali
				.setPercSpesaQuietanziataSuAmmessa(contoEconomicoRimodulazione
						.getPercSpesaQuietanziataSuAmmessa());
		contoEconomicoItemTotali
				.setPercSpesaValidataSuAmmessa(contoEconomicoRimodulazione
						.getPercSpesaValidataSuAmmessa());
		contoEconomicoItemTotali.setModificabile(false);
		contoEconomicoItemTotali.setHaFigli(true);
		contoEconomicoItemTotali.setId("0");
		contoEconomicoItemTotali.setLevel(0);

		contoEconomicoItemList.add(0, contoEconomicoItemTotali);

	}

	public List<ModalitaAgevolazione> mappaModalitaDiAgevolazionePerReport(
			DatiPerInvioPropostaRimodulazioneDTO datiDTO) {
		List<ModalitaAgevolazione> listModalita = new ArrayList<ModalitaAgevolazione>();
		ModalitaDiAgevolazioneDTO[] modalitaDTO = datiDTO
				.getModalitaDiAgevolazione();
		ModalitaDiAgevolazioneDTO totaleModalitaDTO = datiDTO
				.getTotaliModalitaDiAgevolazione();

		if (datiDTO != null) {

			if (modalitaDTO != null && modalitaDTO.length > 0)
				for (ModalitaDiAgevolazioneDTO dto : modalitaDTO) {

					ModalitaAgevolazione modalita = new ModalitaAgevolazione();
					modalita.setIdModalitaAgevolazione(dto
							.getIdModalitaDiAgevolazione());
					modalita.setDescModalita(dto.getDescrizione());
					modalita.setImportoMassimoAgevolato(dto
							.getImportoMassimoAgevolato());
					modalita.setPercentualeMassimoAgevolato(dto
							.getPercImportoMassimoAgevolato());
					// }L{ PBANDI-2027 was dto.getImportoRichiestoNuovo()
					modalita.setUltimoImportoRichiesto(dto
							.getImportoRichiestoUltimo());
					modalita.setImportoRichiesto(NumberUtil.getDoubleValue(dto
							.getImportoRichiestoNuovo()));
					modalita
							.setPercentualeImportoRichiesto(NumberUtil
									.getDoubleValue(dto
											.getPercImportoRichiestoNuovo()));
					modalita.setImportoErogato(dto.getImportoErogato());
					modalita.setUltimoImportoAgevolato(dto
							.getImportoAgevolatoUltimo());
					modalita.setPercentualeUltimoImportoAgevolato(dto
							.getPercImportoAgevolatoUltimo());
					modalita.setModificabile(true);
					listModalita.add(modalita);
				}

			if (totaleModalitaDTO != null) {
				ModalitaAgevolazione totaliModalita = new ModalitaAgevolazione();
				totaliModalita.setIsModalitaTotale(true);
				totaliModalita.setDescModalita(TOTALE);
				totaliModalita.setImportoMassimoAgevolato(totaleModalitaDTO
						.getImportoMassimoAgevolato());
				totaliModalita.setPercentualeMassimoAgevolato(totaleModalitaDTO
						.getPercImportoMassimoAgevolato());
				totaliModalita.setUltimoImportoRichiesto(totaleModalitaDTO
						.getImportoRichiestoUltimo());
				totaliModalita.setImportoRichiesto(totaleModalitaDTO
						.getImportoRichiestoNuovo());

				totaliModalita.setPercentualeImportoRichiesto(totaleModalitaDTO
						.getPercImportoRichiestoNuovo());
				totaliModalita.setImportoErogato(totaleModalitaDTO
						.getImportoErogato());
				totaliModalita.setUltimoImportoAgevolato(totaleModalitaDTO
						.getImportoAgevolatoUltimo());
				totaliModalita
						.setPercentualeUltimoImportoAgevolato(totaleModalitaDTO
								.getPercImportoAgevolatoUltimo());
				totaliModalita.setModificabile(false);

				listModalita.add(listModalita.size(), totaliModalita);

			}

		}
		return listModalita;
	}

	public List<FonteFinanziaria> mappaFontiFinanziariePerReport(
			DatiPerConclusioneRimodulazioneDTO datiDTO) {

		FontiFinanziarieDTO[] fontiNonPrivateDTO = datiDTO
				.getFontiFinanziarieNonPrivate();
		fontiNonPrivateDTO = accumulatePerPeriodo(fontiNonPrivateDTO);
		FontiFinanziarieDTO[] fontiPrivateDTO = datiDTO
				.getFontiFinanziariePrivate();
		fontiPrivateDTO = accumulatePerPeriodo(fontiPrivateDTO);
		FontiFinanziarieDTO subtotaleFontiNonPrivateDTO = datiDTO
				.getSubTotaliFontiFinanziarieNonPrivate();
		FontiFinanziarieDTO totaleFontiDTO = datiDTO
				.getTotaliFontiFinanziarie();
		ArrayList<FonteFinanziaria> listFonti = new ArrayList<FonteFinanziaria>();
		if (fontiNonPrivateDTO != null && fontiNonPrivateDTO.length > 0) {
			for (FontiFinanziarieDTO dto : fontiNonPrivateDTO) {
				FonteFinanziaria fonte = new FonteFinanziaria();
				fonte.setIdFonteFinanziaria(dto.getIdFonteFinanziaria());
				fonte.setDescFonteFinanziaria(dto.getDescrizione());
				fonte.setImportoUltimaQuota(dto
						.getQuotaFonteFinanziariaUltima());
				fonte.setPercentualeUltimaQuota(dto
						.getPercQuotaFonteFinanziariaUltima());
				fonte.setImportoQuota(NumberUtil.getDoubleValue(dto
						.getQuotaFonteFinanziariaNuova()));
				fonte.setPercentualeQuota(NumberUtil.getDoubleValue(dto
						.getPercQuotaFonteFinanziariaNuova()));
				fonte.setModificabile(true);
				fonte.setIsFontePrivata(false);
				listFonti.add(fonte);
			}
		}
		if (subtotaleFontiNonPrivateDTO != null) {
			FonteFinanziaria subtotaleFonte = new FonteFinanziaria();
			subtotaleFonte.setIsSubTotale(true);
			subtotaleFonte.setDescFonteFinanziaria(SUBTOTALE_FINANZIAMENTI);
			subtotaleFonte.setImportoUltimaQuota(subtotaleFontiNonPrivateDTO
					.getQuotaFonteFinanziariaUltima());
			subtotaleFonte
					.setPercentualeUltimaQuota(subtotaleFontiNonPrivateDTO
							.getPercQuotaFonteFinanziariaUltima());
			subtotaleFonte.setImportoQuota(subtotaleFontiNonPrivateDTO
					.getQuotaFonteFinanziariaNuova());
			subtotaleFonte.setPercentualeQuota(subtotaleFontiNonPrivateDTO
					.getPercQuotaFonteFinanziariaNuova());
			listFonti.add(listFonti.size(), subtotaleFonte);
		}
		if (fontiPrivateDTO != null && fontiPrivateDTO.length > 0) {
			for (FontiFinanziarieDTO dto : fontiPrivateDTO) {
				FonteFinanziaria fonte = new FonteFinanziaria();
				fonte.setIdFonteFinanziaria(dto.getIdFonteFinanziaria());
				fonte.setDescFonteFinanziaria(dto.getDescrizione());
				fonte.setImportoUltimaQuota(dto
						.getQuotaFonteFinanziariaUltima());
				fonte.setPercentualeUltimaQuota(dto
						.getPercQuotaFonteFinanziariaUltima());
				fonte.setImportoQuota(dto.getQuotaFonteFinanziariaNuova());
				fonte.setPercentualeQuota(dto
						.getPercQuotaFonteFinanziariaNuova());
				fonte.setModificabile(true);
				fonte.setIsFontePrivata(true);
				listFonti.add(listFonti.size(), fonte);
			}
		}
		if (totaleFontiDTO != null) {
			FonteFinanziaria totaleFonte = new FonteFinanziaria();
			totaleFonte.setIsFonteTotale(true);
			totaleFonte.setDescFonteFinanziaria(TOTALE);
			totaleFonte.setImportoUltimaQuota(totaleFontiDTO
					.getQuotaFonteFinanziariaUltima());
			totaleFonte.setPercentualeUltimaQuota(totaleFontiDTO
					.getPercQuotaFonteFinanziariaUltima());
			totaleFonte.setImportoQuota(totaleFontiDTO
					.getQuotaFonteFinanziariaNuova());
			totaleFonte.setPercentualeQuota(totaleFontiDTO
					.getPercQuotaFonteFinanziariaNuova());
			listFonti.add(listFonti.size(), totaleFonte);
		}
		return listFonti;
	}

	/**
	 * Dato un array di FontiFinanziarieDTO, restituisce un array con le stesse
	 * fonti, ma con percentuali e importi accumulati per i diversi periodi
	 * 
	 * @param fonti
	 *            l'array con le diverse fonti per tutti i periodi
	 * @return l'array con solo un occorrenza per fonte
	 */
	private FontiFinanziarieDTO[] accumulatePerPeriodo(
			FontiFinanziarieDTO[] fonti) {
		Map<Long, ArrayList<FontiFinanziarieDTO>> fontiMap = new HashMap<Long, ArrayList<FontiFinanziarieDTO>>();
		for (FontiFinanziarieDTO fonte : fonti) {
			ArrayList<FontiFinanziarieDTO> fonteList = fontiMap.get(fonte
					.getIdFonteFinanziaria());
			if (fonteList == null) {
				fonteList = new ArrayList<FontiFinanziarieDTO>();
				fontiMap.put(fonte.getIdFonteFinanziaria(), fonteList);
			}
			fonteList.add(fonte);
		}
		Set<Long> idFonti = fontiMap.keySet();
		ArrayList<FontiFinanziarieDTO> result = new ArrayList<FontiFinanziarieDTO>();
		for (Long idFonte : idFonti) {
			ArrayList<FontiFinanziarieDTO> fonteList = fontiMap.get(idFonte);
			FontiFinanziarieDTO temp = beanUtil.transform(fonteList.get(0),
					FontiFinanziarieDTO.class);
			FontiFinanziarieDTO accumulato = NumberUtil
					.accumulate(fonteList, "percQuotaFonteFinanziariaNuova",
							"percQuotaFonteFinanziariaUltima",
							"quotaFonteFinanziariaNuova",
							"quotaFonteFinanziariaUltima");
			beanUtil.valueCopy(accumulato, temp);
			result.add(temp);
		}

		return result.toArray(new FontiFinanziarieDTO[result.size()]);
	}

	public List<ModalitaAgevolazione> mappaModalitaDiAgevolazionePerReport(
			DatiPerConclusioneRimodulazioneDTO datiDTO) {
		ModalitaDiAgevolazioneDTO[] modalitaDTO = datiDTO
				.getModalitaDiAgevolazione();
		ModalitaDiAgevolazioneDTO totaliModalitaDTO = datiDTO
				.getTotaliModalitaDiAgevolazione();

		/*
		 * Modalita di agevolazioni
		 */
		ArrayList<ModalitaAgevolazione> listModalita = new ArrayList<ModalitaAgevolazione>();
		if (modalitaDTO != null && modalitaDTO.length > 0) {
			for (ModalitaDiAgevolazioneDTO dto : modalitaDTO) {
				ModalitaAgevolazione modalita = new ModalitaAgevolazione();
				modalita.setIdModalitaAgevolazione(dto
						.getIdModalitaDiAgevolazione());
				modalita.setDescModalita(dto.getDescrizione());
				modalita.setImportoMassimoAgevolato(dto
						.getImportoMassimoAgevolato());
				modalita.setPercentualeMassimoAgevolato(dto
						.getPercImportoMassimoAgevolato());
				modalita.setUltimoImportoRichiesto(dto
						.getImportoRichiestoUltimo());
				modalita.setImportoErogato(dto.getImportoErogato());
				modalita.setUltimoImportoAgevolato(dto
						.getImportoAgevolatoUltimo());
				modalita.setPercentualeUltimoImportoAgevolato(dto
						.getPercImportoAgevolatoUltimo());
				modalita.setImportoAgevolato(NumberUtil.getDoubleValue(dto
						.getImportoAgevolatoNuovo()));
				modalita.setPercentualeImportoAgevolato(NumberUtil
						.getDoubleValue(dto.getPercImportoAgevolatoNuovo()));
				modalita.setModificabile(true);
				listModalita.add(modalita);
			}
		}
		if (totaliModalitaDTO != null) {
			ModalitaAgevolazione totaleModalita = new ModalitaAgevolazione();
			totaleModalita.setIsModalitaTotale(true);
			totaleModalita.setDescModalita(TOTALE);
			totaleModalita.setImportoMassimoAgevolato(totaliModalitaDTO
					.getImportoMassimoAgevolato());
			totaleModalita.setPercentualeMassimoAgevolato(totaliModalitaDTO
					.getPercImportoMassimoAgevolato());
			totaleModalita.setUltimoImportoRichiesto(totaliModalitaDTO
					.getImportoRichiestoUltimo());
			totaleModalita.setImportoErogato(totaliModalitaDTO
					.getImportoErogato());
			totaleModalita.setUltimoImportoAgevolato(totaliModalitaDTO
					.getImportoAgevolatoUltimo());
			totaleModalita
					.setPercentualeUltimoImportoAgevolato(totaliModalitaDTO
							.getPercImportoAgevolatoUltimo());
			totaleModalita.setImportoAgevolato(totaliModalitaDTO
					.getImportoAgevolatoNuovo());
			totaleModalita.setPercentualeImportoAgevolato(totaliModalitaDTO
					.getPercImportoAgevolatoNuovo());
			totaleModalita.setModificabile(false);
			listModalita.add(listModalita.size(), totaleModalita);
		}

		return listModalita;
	}

	public void aggiornaStatoContoEconomico(Long idContoEconomico,
			String codiceStato, BigDecimal idUtente)
			throws ContoEconomicoAggiornamentoStatoFallitoException {
		PbandiTContoEconomicoVO contoEconomicoVO = new PbandiTContoEconomicoVO();
		contoEconomicoVO.setIdContoEconomico(beanUtil.transform(
				idContoEconomico, BigDecimal.class));
		contoEconomicoVO
				.setIdStatoContoEconomico(decodeStatoContoEconomico(codiceStato));

		try {
			contoEconomicoVO.setIdUtenteAgg(idUtente);
			genericDAO.update(contoEconomicoVO);
		} catch (Exception e) {
			logger.warn(e.getMessage());
			throw new ContoEconomicoAggiornamentoStatoFallitoException(e
					.getMessage(), e);
		}
	}

	public void aggiornaContoEconomicoIstruttoria(Long idContoEconomico,
			String codiceStato, BigDecimal idUtente,
			Double importoFinanziamentoBancario)
			throws ContoEconomicoAggiornamentoStatoFallitoException {
		PbandiTContoEconomicoVO contoEconomicoVO = new PbandiTContoEconomicoVO();
		contoEconomicoVO.setIdContoEconomico(beanUtil.transform(
				idContoEconomico, BigDecimal.class));
		contoEconomicoVO
				.setIdStatoContoEconomico(decodeStatoContoEconomico(codiceStato));
		contoEconomicoVO.setImportoFinanziamentoBanca(BeanUtil
				.transformToBigDecimal(importoFinanziamentoBancario));
		try {
			contoEconomicoVO.setIdUtenteAgg(idUtente);
			genericDAO.update(contoEconomicoVO);
		} catch (Exception e) {
			logger.warn(e.getMessage());
			throw new ContoEconomicoAggiornamentoStatoFallitoException(e
					.getMessage(), e);
		}
	}

	public void aggiornaContoEconomicoBeneficiario(Long idContoEconomico,
			String codiceStato, BigDecimal idUtente,
			Double importoFinanzBancaRichiesto)
			throws ContoEconomicoAggiornamentoStatoFallitoException {
		PbandiTContoEconomicoVO contoEconomicoVO = new PbandiTContoEconomicoVO();
		contoEconomicoVO.setIdContoEconomico(beanUtil.transform(
				idContoEconomico, BigDecimal.class));
		try {
			contoEconomicoVO = genericDAO.findSingleWhere(contoEconomicoVO);
			DecodificaDTO decodifica = decodificheManager.findDecodifica(
					GestioneDatiDiDominioSrv.STATO_CONTO_ECONOMICO,
					NumberUtil.toLong(contoEconomicoVO
							.getIdStatoContoEconomico()));
			if (decodifica.getDescrizioneBreve().equals(
					Constants.STATO_CONTO_ECONOMICO_IN_PROPOSTA_PER_DOMANDA))
				contoEconomicoVO
						.setIdStatoContoEconomico(decodeStatoContoEconomico(codiceStato));
			contoEconomicoVO.setImportoFinanzBancaRichiesto(BeanUtil
					.transformToBigDecimal(importoFinanzBancaRichiesto));
			contoEconomicoVO.setIdUtenteAgg(idUtente);
			genericDAO.update(contoEconomicoVO);
		} catch (Exception e) {
			logger.warn(e.getMessage());
			throw new ContoEconomicoAggiornamentoStatoFallitoException(e
					.getMessage(), e);
		}
	}

	public ContoEconomicoDTO creaMaster(ContoEconomicoDTO contoMain,
			Long idUtente, String note, String riferimento,
			Double importoFinanziamentoBanca) throws Exception {
		Map<String, String> mapCampiDaRibaltare = new HashMap<String, String>();
		mapCampiDaRibaltare
				.put("importoAmmesso", "importoAmmessoFinanziamento");
		mapCampiDaRibaltare.put("importoRichiesto", "importoSpesa");

		PbandiTContoEconomicoVO contoDaChiudere = new PbandiTContoEconomicoVO();
		contoDaChiudere.setDtFineValidita(DateUtil.getSysdate());
		contoDaChiudere.setIdContoEconomico(contoMain.getIdContoEconomico());
		contoDaChiudere
				.setIdStatoContoEconomico(decodeStatoContoEconomico(Constants.STATO_CONTO_ECONOMICO_APPROVATO_IN_ISTRUTTORIA));
		contoDaChiudere.setNoteContoEconomico(note);
		contoDaChiudere.setRiferimento(riferimento);
		contoDaChiudere.setImportoFinanziamentoBanca(BeanUtil
				.transformToBigDecimal(importoFinanziamentoBanca));

		contoDaChiudere.setIdUtenteAgg(new BigDecimal(idUtente));
		genericDAO.update(contoDaChiudere);

		// Piccolo abominio su inserimento/ricerca con stringe vuote -> null
		contoDaChiudere = new PbandiTContoEconomicoVO();
		contoDaChiudere.setIdContoEconomico(contoMain.getIdContoEconomico());

		contoDaChiudere = genericDAO.findSingleWhere(contoDaChiudere);

		PbandiTContoEconomicoVO contoMasterVO = new PbandiTContoEconomicoVO();
		contoMasterVO
				.setIdStatoContoEconomico(decodeStatoContoEconomico(Constants.STATO_CONTO_ECONOMICO_APPROVATO));
		contoMasterVO.setIdUtenteIns(NumberUtil.toBigDecimal(idUtente));
		contoMasterVO.setDtInizioValidita(DateUtil.getSysdate());
		contoMasterVO.setIdDomanda(contoDaChiudere.getIdDomanda());
		contoMasterVO = genericDAO.insert(contoMasterVO);

		ContoEconomicoDTO contoMaster = loadContoEconomico(contoMasterVO
				.getIdContoEconomico());

		ribaltaRighiContoDaA(contoMain, contoMaster, contoMasterVO,
				mapCampiDaRibaltare, NumberUtil.toBigDecimal(idUtente), false);

		return contoMaster;
	}

	public ContoEconomicoDTO createMain(BigDecimal idProgetto, Long idUtente)
			throws Exception {

		PbandiTProgettoVO pbandiTProgettoVO = new PbandiTProgettoVO();
		pbandiTProgettoVO.setIdProgetto(idProgetto);
		pbandiTProgettoVO = genericDAO.findSingleWhere(pbandiTProgettoVO);

		PbandiTContoEconomicoVO contoDaCreareVO = new PbandiTContoEconomicoVO();
		contoDaCreareVO
				.setIdStatoContoEconomico(decodeStatoContoEconomico(Constants.STATO_CONTO_ECONOMICO_IN_PROPOSTA_PER_DOMANDA));
		contoDaCreareVO.setIdUtenteIns(NumberUtil.toBigDecimal(idUtente));
		contoDaCreareVO.setDtInizioValidita(DateUtil.getSysdate());
		contoDaCreareVO.setIdDomanda(pbandiTProgettoVO.getIdDomanda());
		contoDaCreareVO = genericDAO.insert(contoDaCreareVO);

		it.csi.pbandi.pbservizit.pbandisrv.dto.manager.ContoEconomicoDTO contoMain = loadContoEconomico(contoDaCreareVO
				.getIdContoEconomico());

		return contoMain;

	}

	public void aggiornaMain(ContoEconomicoDTO contoMainNew,
			ContoEconomicoDTO contoMainOld, Long idUtente) throws Exception {

		PbandiTContoEconomicoVO pbandiTContoEconomicoVO = new PbandiTContoEconomicoVO();
		pbandiTContoEconomicoVO.setIdContoEconomico(contoMainNew
				.getIdContoEconomico());
		pbandiTContoEconomicoVO.setIdUtenteAgg(new BigDecimal(idUtente));
		genericDAO.update(pbandiTContoEconomicoVO);
		pbandiTContoEconomicoVO = genericDAO
				.findSingleWhere(pbandiTContoEconomicoVO);
		Map<String, String> mapCampiDaRibaltare = new HashMap<String, String>();
		mapCampiDaRibaltare.put("importoRichiesto", "importoSpesa");

		aggiornaStoricizzandoRighiContoEconomico(contoMainNew, NumberUtil
				.toBigDecimal(idUtente));
		/*
		 * ribaltaRighiContoDaA(contoMainNew, contoMainOld,
		 * pbandiTContoEconomicoVO, mapCampiDaRibaltare,
		 * NumberUtil.toBigDecimal(idUtente), richiestaConto);
		 */
	}

	public void chiudiMain(ContoEconomicoDTO contoMain, Long idUtente,
			String note, String riferimento, Double importoFinanziamentoBanca)
			throws Exception {
		Map<String, String> mapCampiDaRibaltare = new HashMap<String, String>();
		mapCampiDaRibaltare
				.put("importoAmmesso", "importoAmmessoFinanziamento");
		mapCampiDaRibaltare.put("importoRichiesto", "importoSpesa");

		PbandiTContoEconomicoVO contoDaChiudere = new PbandiTContoEconomicoVO();
		contoDaChiudere.setDtFineValidita(DateUtil.getSysdate());
		contoDaChiudere.setIdContoEconomico(contoMain.getIdContoEconomico());
		contoDaChiudere
				.setIdStatoContoEconomico(decodeStatoContoEconomico(Constants.STATO_CONTO_ECONOMICO_APPROVATO_IN_ISTRUTTORIA));
		contoDaChiudere.setNoteContoEconomico(note);
		contoDaChiudere.setRiferimento(riferimento);
		contoDaChiudere.setImportoFinanziamentoBanca(BeanUtil
				.transformToBigDecimal(importoFinanziamentoBanca));

		contoDaChiudere.setIdUtenteAgg(NumberUtil.toBigDecimal(idUtente));
		genericDAO.update(contoDaChiudere);

	}

	
	
	private void mappaRigaContoEconomico(
			RigoContoEconomicoDTO rigoContoEconomicoDTO,
			ContoEconomicoItemDTO vo) {
		
		vo.setLabel(rigoContoEconomicoDTO.getDescVoceDiSpesa());
		vo.setImportoRichiestoInDomanda(NumberUtil
				.getDoubleValue(rigoContoEconomicoDTO.getImportoRichiesto()));
		vo.setImportoSpesaAmmessaInDetermina(NumberUtil
				.getDoubleValue(rigoContoEconomicoDTO.getImportoAmmesso()));
		vo.setImportoSpesaAmmessaRimodulazione(NumberUtil
				.getDoubleValue(rigoContoEconomicoDTO.getImportoAmmesso()));
		vo.setImportoSpesaAmmessaUltima(NumberUtil
				.getDoubleValue(rigoContoEconomicoDTO.getImportoAmmesso()));
		vo.setImportoSpesaQuietanziata(NumberUtil
				.getDoubleValue(rigoContoEconomicoDTO
						.getImportoQuietanzato()));
		vo.setImportoSpesaRendicontata(NumberUtil
				.getDoubleValue(rigoContoEconomicoDTO
						.getImportoRendicontato()));
		vo.setImportoSpesaValidataTotale(NumberUtil
				.getDoubleValue(rigoContoEconomicoDTO.getImportoValidato()));
		
		vo.setPercSpesaQuietanziataSuAmmessa(NumberUtil.getDoubleValue(
				NumberUtil.percentage(rigoContoEconomicoDTO.getImportoQuietanzato(),
				rigoContoEconomicoDTO.getImportoAmmesso())));
		vo.setPercSpesaValidataSuAmmessa(NumberUtil.getDoubleValue(
				NumberUtil.percentage(rigoContoEconomicoDTO.getImportoValidato(),
				rigoContoEconomicoDTO.getImportoAmmesso())));
	}
	
	public List<ContoEconomicoItemDTO> getContoEconomicoPerReport(
			RigoContoEconomicoDTO rigoContoEconomicoDTO,
			List<ContoEconomicoItemDTO> contoEconomicoReport) {
		List<RigoContoEconomicoDTO> figli = rigoContoEconomicoDTO.getFigli();
		boolean isFirst=true;
		if (ObjectUtil.isEmpty(figli)) {
			ContoEconomicoItemDTO vo = new ContoEconomicoItemDTO();
			//rigoContoEconomicoDTO.setDescVoceDiSpesa("    "+rigoContoEconomicoDTO.getDescVoceDiSpesa());
			mappaRigaContoEconomico(rigoContoEconomicoDTO, vo);
			contoEconomicoReport.add(vo);
		} else {
			if(isFirst){				
				isFirst=false;
				if(rigoContoEconomicoDTO.getDescVoceDiSpesa()==null)
					rigoContoEconomicoDTO.setDescVoceDiSpesa("CONTO ECONOMICO");
			}
			ContoEconomicoItemDTO vo = new ContoEconomicoItemDTO();
			rigoContoEconomicoDTO.setDescVoceDiSpesa(rigoContoEconomicoDTO.getDescVoceDiSpesa().toUpperCase());

			mappaRigaContoEconomico(rigoContoEconomicoDTO, vo);
			contoEconomicoReport.add(vo);
			for (RigoContoEconomicoDTO figlio : figli) {
				contoEconomicoReport = getContoEconomicoPerReport(figlio,
						contoEconomicoReport);
			}
			
		}
		return contoEconomicoReport;
	}
	public List<ModalitaDiAgevolazioneDTO> getModalitaAgevolazione(Long idProgetto,
			List<ModalitaDiAgevolazioneDTO> modalita) {
		Map<String, String> mapPropsToCopy = new HashMap<String, String>();
		mapPropsToCopy
				.put("idModalitaAgevolazione", "idModalitaDiAgevolazione");
		mapPropsToCopy.put("descModalitaAgevolazione", "descrizione");
		mapPropsToCopy
				.put("massimoImportoAgevolato", "importoMassimoAgevolato");
		mapPropsToCopy.put("importoErogazioni", "importoErogato");
		mapPropsToCopy.put("quotaImportoAgevolato", "importoAgevolatoNuovo");
		mapPropsToCopy.put("quotaImportoAgevolato", "importoAgevolatoUltimo");
		mapPropsToCopy.put("quotaImportoRichiesto", "importoRichiestoUltimo");
		mapPropsToCopy.put("percImportoAgevolatoBando",
				"percImportoMassimoAgevolato");
		try {
			modalita = getBeanUtil().transformList(

					caricaModalitaAgevolazione(
							new BigDecimal(idProgetto),
							Constants.TIPOLOGIA_CONTO_ECONOMICO_MASTER),
					ModalitaDiAgevolazioneDTO.class, mapPropsToCopy);
		} catch (Exception e) {
			logger.debug(
					"Errore nel recupero degli importi agevolati nel progetto "
							+ idProgetto + " per i dati generali ");
		}
		return modalita;
	}
	
	public ContoEconomicoConStatoVO findContoEconomicoLocalCopyIstr(
			BigDecimal idProgetto)
			throws ContoEconomicoNonTrovatoException {
		
		return findContoEconomicoConStatoValidDate(idProgetto,
				Constants.TIPOLOGIA_CONTO_ECONOMICO_COPY_IST, false);
	}
	
	public ContoEconomicoConStatoVO findContoEconomicoLocalCopyBen(
			BigDecimal idProgetto)
			throws ContoEconomicoNonTrovatoException {
		return findContoEconomicoConStatoValidDate(idProgetto,
				Constants.TIPOLOGIA_CONTO_ECONOMICO_COPY_BEN, false);
	}
	
	
	
	public List<ContoEconomicoItemDTO> estraiVociDiSpesaFoglieDaContoEconomicoMaster(BigDecimal idProgetto) throws Exception {
		ContoEconomicoDTO ceMaster = findContoEconomicoMasterPotato(idProgetto);
		List<ContoEconomicoItemDTO> voci = new ArrayList<ContoEconomicoItemDTO>();
		if (!ObjectUtil.isEmpty(ceMaster.getFigli())) {
			for (RigoContoEconomicoDTO padre : ceMaster.getFigli()) {
				if (!ObjectUtil.isEmpty(padre.getFigli())) {
					for (RigoContoEconomicoDTO figlio : padre.getFigli()) {
						String labelItem = padre.getDescVoceDiSpesa().toUpperCase()+" : "+figlio.getDescVoceDiSpesa();
						ContoEconomicoItemDTO item = popolaContoEconomicoItem(labelItem, figlio);
						voci.add(item);
					}
				} else {
					String labelItem = padre.getDescVoceDiSpesa();
					ContoEconomicoItemDTO item = popolaContoEconomicoItem(labelItem, padre);
					voci.add(item);
				}
			}
		}
		return voci;
	}
	
	private ContoEconomicoItemDTO popolaContoEconomicoItem (String label, RigoContoEconomicoDTO rigo) {
		ContoEconomicoItemDTO item = new ContoEconomicoItemDTO();
		item.setLabel(label);
		item.setImportoSpesaAmmessaUltima(NumberUtil.toDouble(rigo.getImportoAmmesso()));
		item.setImportoSpesaQuietanziata(NumberUtil.toDouble(rigo.getImportoQuietanzato()));
		item.setPercSpesaQuietanziataSuAmmessa(NumberUtil.toDouble(NumberUtil.percentage(rigo.getImportoQuietanzato(),rigo.getImportoAmmesso())));
		return item;
	}
	
	
	public VoceDiSpesaConAmmessoVO[] getVociDiSpesaConAmmesso(Long idProgetto) {
		VoceDiSpesaConAmmessoVO voceDiSpesaConAmmesso = new VoceDiSpesaConAmmessoVO();
		voceDiSpesaConAmmesso.setIdProgetto(new BigDecimal(idProgetto));
		VoceDiSpesaConAmmessoVO[] vociDiSpesaConAmmesso = genericDAO
				.findWhere(voceDiSpesaConAmmesso);
		return vociDiSpesaConAmmesso;
	}
	
	public List<EconomiaPerDatiGeneraliVO> findEconomiePerDatiGenerali(Long idProgetto) {
		BigDecimal id = new BigDecimal(idProgetto);
		String s = (String) genericDAO.callProcedure().isFESR(id);
		List<EconomiaPerDatiGeneraliVO> lista = null;
		if ("POR-FESR-2014-2020".equalsIgnoreCase(s)) {
			EconomiaPerDatiGeneraliVO filtro = new EconomiaPerDatiGeneraliVO();
			filtro.setIdProgettoCedente(id);
			lista = genericDAO.findListWhere(filtro);
		}
		return lista;
	}
	
	
	// ***********************************
	//  CULTURA - VOCI DI SPESA - inizio
	// ***********************************
	
	// Modifiche
	//   ContoEconomicoConRighiEVociVO: idTipologiaVoceDiSpesa, percSpGenFunz, completamento, flag_edit
	
	// Metodo creato sulla falsa riga di findContoEconomicoMasterPotato().
	public ContoEconomicoDTO findContoEconomicoMasterPotatoCultura(
			BigDecimal idProgetto) throws ContoEconomicoNonTrovatoException {
		ContoEconomicoDTO contoEconomico = findContoEconomicoMasterCultura(idProgetto);
		return potaVociTerminaliSenzaRigoAssociato(contoEconomico);
	}
	
	// Metodo creato sulla falsa riga di findContoEconomicoMaster().
	public ContoEconomicoDTO findContoEconomicoMasterCultura(BigDecimal idProgetto)
		throws ContoEconomicoNonTrovatoException {
		ContoEconomicoConStatoVO c = findContoEconomicoMasterConStato(idProgetto);
		ContoEconomicoDTO contoEconomico = loadContoEconomicoCultura(c.getIdContoEconomico());
		return contoEconomico;
	}
	
	/**
	 * carica un conto economico completo di righi associati e calcola i totali
	 * di ammesso, richiesto (definiti per ogni conto) e di rendicontato,
	 * quietanzato e validato (definiti solo per i conti master)
	 */
	// Metodo creato sulla falsa riga di loadContoEconomico().
	public ContoEconomicoDTO loadContoEconomicoCultura(BigDecimal idContoEconomico)
			throws ContoEconomicoNonTrovatoException {
		/*
		 * carico tutto il conto da db, compresi i righi e le voci non ancora
		 * associate a righi
		 */
		ContoEconomicoConRighiEVociVO filtro = new ContoEconomicoConRighiEVociVO();
		filtro.setIdContoEconomico(idContoEconomico);
		List<ContoEconomicoConRighiEVociVO> list = genericDAO
				.findListWhere(filtro);

		if (list == null || list.size() == 0) {
			throw new ContoEconomicoNonTrovatoException(
					"Conto economico non trovato: " + idContoEconomico);
		}
		
		Map<BigDecimal, List<RigoContoEconomicoDTO>> indexVociFiglie = new HashMap<BigDecimal, List<RigoContoEconomicoDTO>>();
		Map<BigDecimal, RigoContoEconomicoDTO> indexVoci = new HashMap<BigDecimal, RigoContoEconomicoDTO>();
		List<RigoContoEconomicoDTO> vociRadice = new ArrayList<RigoContoEconomicoDTO>();

		for (ContoEconomicoConRighiEVociVO contoEconomicoConRighiEVociVO : list) {
			RigoContoEconomicoDTO rigo = getBeanUtil().transform(
					contoEconomicoConRighiEVociVO, RigoContoEconomicoDTO.class);
			
			if ("S".equalsIgnoreCase(contoEconomicoConRighiEVociVO.getFlagEdit()) &&
				!StringUtil.isEmpty(contoEconomicoConRighiEVociVO.getCompletamento())) {
				rigo.setDescVoceDiSpesa(rigo.getDescVoceDiSpesa()+" "+contoEconomicoConRighiEVociVO.getCompletamento());
			}
			
			indexVoci.put(contoEconomicoConRighiEVociVO.getIdVoceDiSpesa(),
					rigo);
			BigDecimal idVoceDiSpesaPadre = contoEconomicoConRighiEVociVO
					.getIdVoceDiSpesaPadre();
			if (idVoceDiSpesaPadre == null) {
				vociRadice.add(rigo);
			} else {
				List<RigoContoEconomicoDTO> vociFiglie = indexVociFiglie
						.get(idVoceDiSpesaPadre);
				if (vociFiglie == null) {
					vociFiglie = new ArrayList<RigoContoEconomicoDTO>();
					indexVociFiglie.put(idVoceDiSpesaPadre, vociFiglie);
				}
				vociFiglie.add(rigo);
			}
		}

		// collego i figli ai padri
		for (BigDecimal idVoceCorrente : indexVoci.keySet()) {
			List<RigoContoEconomicoDTO> vociFiglie = indexVociFiglie
					.get(idVoceCorrente);
			if (vociFiglie != null) {
				RigoContoEconomicoDTO rigoContoEconomicoDTO = indexVoci
						.get(idVoceCorrente);
				rigoContoEconomicoDTO.setFigli(vociFiglie);
			}
		}

		ContoEconomicoConRighiEVociVO contoEconomicoConRighiEVociVO = list
				.get(0);

		ContoEconomicoDTO contoRisultante = new ContoEconomicoDTO();
		contoRisultante.setIdContoEconomico(contoEconomicoConRighiEVociVO
				.getIdContoEconomico());
		contoRisultante.setDtInizioValidita(contoEconomicoConRighiEVociVO
				.getDtInizioValidita());
		contoRisultante.setDtFineValidita(contoEconomicoConRighiEVociVO
				.getDtFineValidita());
		contoRisultante.setImportoImpegnoVincolante(contoEconomicoConRighiEVociVO.getImportoImpegnoVincolante());
		contoRisultante.setFigli(vociRadice);

		PbandiTDomandaVO domandaVO = new PbandiTDomandaVO();
		domandaVO.setIdDomanda(contoEconomicoConRighiEVociVO.getIdDomanda());
		domandaVO = genericDAO.findSingleWhere(domandaVO);

		contoRisultante.setDataPresentazioneDomanda(domandaVO
				.getDtPresentazioneDomanda());

		/*
		 * VN: Ribasso d' asta Per ogni conto economico puo' esistere solo un
		 * ribasso d' asta o nessuno. Nel caso in cui c'e' ne sono piu' di uno
		 * lanciamo un' eccezione.
		 */
		PbandiTRibassoAstaVO ribassoAstaVO = new PbandiTRibassoAstaVO();
		ribassoAstaVO.setIdContoEconomico(contoEconomicoConRighiEVociVO
				.getIdContoEconomico());
		List<PbandiTRibassoAstaVO> ribassi = genericDAO
				.findListWhere(ribassoAstaVO);
		if (ribassi != null && !ribassi.isEmpty() && ribassi.size() > 1) {
			ContoEconomicoNonTrovatoException e = new ContoEconomicoNonTrovatoException(
					"Errore trovati piu' di un ribasso associato al conto economino("
							+ contoEconomicoConRighiEVociVO
									.getIdContoEconomico() + ")");
			throw e;
		} else if (ribassi == null || ribassi.isEmpty()) {
			contoRisultante
					.setFlagRibassoAsta(it.csi.pbandi.pbservizit.pbandiutil.common.Constants.FLAG_FALSE);
		} else {
			ribassoAstaVO = ribassi.get(0);
			contoRisultante
					.setFlagRibassoAsta(it.csi.pbandi.pbservizit.pbandiutil.common.Constants.FLAG_TRUE);
			contoRisultante.setPercRibassoAsta(ribassoAstaVO.getPercentuale());
			contoRisultante.setImportoRibassoAsta(ribassoAstaVO.getImporto());
		}

		calcolaSommeAggregateDaiFigliEVerificaAssociazioniARighi(contoRisultante);

		contoRisultante.setDescBreveStatoContoEconom(decodificheManager
				.findDescBreve(PbandiDStatoContoEconomicoVO.class,
						contoEconomicoConRighiEVociVO
								.getIdStatoContoEconomico()));
		
		// Fin qui contoRisultante contiene un elenco di voci di spesa (macro voci) 
		// ognuna delle quali pu� contenere un elenco di sottovoci (micro voci)
		// in una struttura ripetuta del tipo 
		//   macro voce
		//     micro voce
		// Ogni macro voce contiene l'id della tipologia di spesa ad essa associata.
		// Cultura richiede che si raggruppino le macro voci per tipologia.
		// Quindi contoRisultante deve contenere una struttura ripetuta del tipo
		//   tipologia 
		//     macro voce
		//       micro voce
		
		raggruppaPerTipologia(contoRisultante);
		
		contoRisultante.setPercSpGenFunz(list.get(0).getPercSpGenFunz());

		return contoRisultante;
	}
	
	private void raggruppaPerTipologia(ContoEconomicoDTO conto) {
		if (conto == null)
			return;
		
		// Crea un elenco di RigoContoEconomicoDTO che contiene le tipologie dal db.
		List<RigoContoEconomicoDTO> tipologie = creaElencoTipologie();
		
		// Elenco di macro voci popolato precedentemente.
		List<RigoContoEconomicoDTO> vociMacro = conto.getFigli();
		
		// Abbina ogni macro voce alla relativa tipologia.
		for (RigoContoEconomicoDTO voceMacro : vociMacro) {
			assegnaVoceMacroATipologia(voceMacro, tipologie);
		}
		
		// Cancello le tipologie senza macro voci.
		Iterator<RigoContoEconomicoDTO> iterator = tipologie.iterator();
		while(iterator.hasNext()) {			
			RigoContoEconomicoDTO dto = iterator.next();
		    if (dto.getFigli().size() == 0) {
		        iterator.remove();
		    } 
		}
		
		conto.setFigli(tipologie);
	}
	
	// Crea un elenco di RigoContoEconomicoDTO che contiene le tipologie dal db.
	private List<RigoContoEconomicoDTO> creaElencoTipologie() {

		// Legge le tipologie di voce di spesa.
		PbandiDTipolVoceDiSpesaVO filtro = new PbandiDTipolVoceDiSpesaVO();
		filtro.setAscendentOrder("idTipologiaVoceDiSpesa");
		List<PbandiDTipolVoceDiSpesaVO> tipi = genericDAO.findListWhere(filtro);
				
		List<RigoContoEconomicoDTO> tipologie = new ArrayList<RigoContoEconomicoDTO>();
		for (PbandiDTipolVoceDiSpesaVO tipo : tipi) {
			RigoContoEconomicoDTO newRigo = new RigoContoEconomicoDTO();
			newRigo.setIdTipologiaVoceDiSpesa(tipo.getIdTipologiaVoceDiSpesa());
			newRigo.setDescVoceDiSpesa(tipo.getDescrizione());				
			newRigo.setFigli(new ArrayList<RigoContoEconomicoDTO>());		// conterr� le macro voci di questa tipologia
			tipologie.add(newRigo);
		}
		
		return tipologie;
	}
	
	private void assegnaVoceMacroATipologia(RigoContoEconomicoDTO voceMacro, List<RigoContoEconomicoDTO> tipologie) {
		if (voceMacro == null || voceMacro.getIdTipologiaVoceDiSpesa() == null)
			return;
		
		for (RigoContoEconomicoDTO tipol : tipologie) {
			if (tipol.getIdTipologiaVoceDiSpesa().intValue() == voceMacro.getIdTipologiaVoceDiSpesa().intValue()) {
				tipol.getFigli().add(voceMacro);
				tipol.setImportoRichiesto(somma(tipol.getImportoRichiesto(), voceMacro.getImportoRichiesto()));				
				tipol.setImportoAmmesso(somma(tipol.getImportoAmmesso(), voceMacro.getImportoAmmesso()));
				tipol.setImportoRendicontato(somma(tipol.getImportoRendicontato(), voceMacro.getImportoRendicontato()));
				tipol.setImportoQuietanzato(somma(tipol.getImportoQuietanzato(), voceMacro.getImportoQuietanzato()));
				tipol.setImportoValidato(somma(tipol.getImportoValidato(), voceMacro.getImportoValidato()));
			}
		}
	}
	
	private BigDecimal somma(BigDecimal a, BigDecimal b) {
		return NumberUtil.sum(NumberUtil.createScaledBigDecimal(a), NumberUtil.createScaledBigDecimal(b));
	}
	
	// ***********************************
	//  CULTURA - VOCI DI SPESA - fine
	// ***********************************
	
	
	
	// ***********************************
	//  CULTURA - VOCI DI ENTRATA - inizio
	// ***********************************
	
	 public RigaContoEconomicoCulturaDTO[] findVociDiEntrataCultura(BigDecimal idProgetto) 
	 		throws ContoEconomicoNonTrovatoException {
		 
		 // Recupara il conto master.
		 ContoEconomicoConStatoVO c = findContoEconomicoMasterConStato(idProgetto);
		 
		 // Recuper le voci di entrata del conto economico master.
		 RigaContoEconomicoCulturaDTO[] vde = loadVociDiEntrataCultura(c.getIdContoEconomico());
		 
		 return vde;
	 }
	 
	 private RigaContoEconomicoCulturaDTO[] loadVociDiEntrataCultura(BigDecimal idContoEconomico) {
		 
		 // Legge le voci di entrata del conto economico
		 // ordinate per descrizione della voce, id voce entrata, completamento.
		 VoceDiEntrataCulturaVO filtro = new VoceDiEntrataCulturaVO();
		 filtro.setIdContoEconomico(idContoEconomico);
		 List<VoceDiEntrataCulturaVO> voci = genericDAO.findListWhere(filtro);		 
		 //for (VoceDiEntrataCulturaVO vo : voci) {System.out.println("\n"+vo.toString());}
		 
		 // Crea la tabella da visualizzare a video.
		 ArrayList<RigaContoEconomicoCulturaDTO> tabella = creaTabellaVociDiEntrataCultura(voci);
		 for (RigaContoEconomicoCulturaDTO dto : tabella) {System.out.println("\n"+dto.toString());}
		 
		 return tabella.toArray(new RigaContoEconomicoCulturaDTO[tabella.size()]);
	 }
	 
	 // Crea la tabella delle voci di entrata da visualizzare a video.
	 // La tabella in output ha la seguente struttura:
	 //   CONTO ECONOMICO
	 //      VOCE DI SPESA 1
	 //         COMPLETAMENTO 1
	 //         COMPLETAMENTO 2
	 //      VOCE DI SPESA 2
	 //      VOCE DI SPESA 3
	 //      ........
	 private ArrayList<RigaContoEconomicoCulturaDTO> creaTabellaVociDiEntrataCultura(List<VoceDiEntrataCulturaVO> voci) {
		 
		 ArrayList<RigaContoEconomicoCulturaDTO> tabella = new ArrayList<RigaContoEconomicoCulturaDTO>();
		 
		 // Riga iniziale CONTO ECONOMICO.
		 RigaContoEconomicoCulturaDTO riga1 = new RigaContoEconomicoCulturaDTO();
		 riga1.setLabel("CONTO ECONOMICO");
		 riga1.setIdVoce(new Long(0));
		 riga1.setLevel(0);
		 riga1.setHaFigli(false);
		 riga1.setImportoSpesaAmmessa(totaleSpesaAmmessa(voci));
		 tabella.add(riga1);
		 
		 BigDecimal idVoceEntrataCorrente = new BigDecimal(0);		 
		 for (VoceDiEntrataCulturaVO voce : voci) {
			 if (idVoceEntrataCorrente.intValue() == voce.getIdVoceDiEntrata().intValue() &&
				 "S".equalsIgnoreCase(voce.getFlagEdit())) {
				 
				 // Nuovo completamento
				 RigaContoEconomicoCulturaDTO completamento = new RigaContoEconomicoCulturaDTO();
				 completamento.setLabel(voce.getCompletamento());
				 completamento.setIdVoce(voce.getIdVoceDiEntrata().longValue());
				 completamento.setLevel(2);
				 completamento.setHaFigli(false);
				 completamento.setImportoSpesaAmmessa(NumberUtil.toDouble(voce.getImportoAmmessoFinanziamento()));
				 tabella.add(completamento);
				 
			 } else {
				 
				 idVoceEntrataCorrente = voce.getIdVoceDiEntrata();
				 
				 // Nuova voce di spesa.
				 RigaContoEconomicoCulturaDTO vds = new RigaContoEconomicoCulturaDTO();
				 vds.setLabel(voce.getDescrizione());
				 vds.setIdVoce(voce.getIdVoceDiEntrata().longValue());
				 vds.setLevel(1);
				 vds.setHaFigli("S".equalsIgnoreCase(voce.getFlagEdit()));
				 vds.setImportoSpesaAmmessa(totaleSpesaAmmessaPerVoce(voce.getIdVoceDiEntrata(), voci));				 
				 tabella.add(vds);
				 
				 // Nuovo completamento
				 if ("S".equalsIgnoreCase(voce.getFlagEdit())) {
					 RigaContoEconomicoCulturaDTO completamento = new RigaContoEconomicoCulturaDTO();
					 completamento.setLabel(voce.getCompletamento());
					 completamento.setIdVoce(voce.getIdVoceDiEntrata().longValue());
					 completamento.setLevel(2);
					 completamento.setHaFigli(false);
					 completamento.setImportoSpesaAmmessa(NumberUtil.toDouble(voce.getImportoAmmessoFinanziamento()));
					 tabella.add(completamento);
				 }
			 }
		 }
		 		 
		 return tabella;
	 }
	 
	 private Double totaleSpesaAmmessa(List<VoceDiEntrataCulturaVO> voci) {
		 BigDecimal totale = new BigDecimal(0);
		 for (VoceDiEntrataCulturaVO voce : voci) {
			 totale = somma(totale, voce.getImportoAmmessoFinanziamento());
		 }
		 return totale.doubleValue();
	 }
	 
	 private Double totaleSpesaAmmessaPerVoce(BigDecimal idVoce, List<VoceDiEntrataCulturaVO> voci) {
		 BigDecimal totale = new BigDecimal(0);
		 for (VoceDiEntrataCulturaVO voce : voci) {
			 if (idVoce.intValue() == voce.getIdVoceDiEntrata().intValue()) {
				 totale = somma(totale, voce.getImportoAmmessoFinanziamento());
			 }
		 }
		 return totale.doubleValue();
	 }

	public ContoEconomicoConStatoContribVO findContoEconomicoMainImpContrib(BigDecimal idProgetto) throws ContoEconomicoNonTrovatoException {
		logger.begin();
		ContoEconomicoConStatoContribVO contoEco = new ContoEconomicoConStatoContribVO();
		contoEco.setIdProgetto(idProgetto);
		contoEco.setDescBreveTipologiaContoEco(Constants.TIPOLOGIA_CONTO_ECONOMICO_MAIN);
		contoEco.setDescendentOrder("idContoEconomico");
		Condition<ContoEconomicoConStatoContribVO> condition = new FilterCondition<ContoEconomicoConStatoContribVO>(
					contoEco);

		List<ContoEconomicoConStatoContribVO> list = genericDAO.findListWhere(condition);

		if (list == null || list.size() < 1) {
			throw new ContoEconomicoNonTrovatoException(
					"Nessun conto economico trovato per il progetto " + idProgetto);
		}
		logger.end();
		return list.get(0);
	}

	public ContoEconomicoConStatoVO findContoEconomicoProposta(BigDecimal idProgetto, Long idUtente) throws ContoEconomicoNonTrovatoException {

			ContoEconomicoConStatoVO contoEconomicoConStatoVO = new ContoEconomicoConStatoVO();
			contoEconomicoConStatoVO.setIdProgetto(idProgetto);
			contoEconomicoConStatoVO.setDescendentOrder("idContoEconomico");
			Condition<ContoEconomicoConStatoVO> condition;

			condition = new FilterCondition<>(contoEconomicoConStatoVO);

			List<ContoEconomicoConStatoVO> list = genericDAO.findListWhere(condition);
			//find the first with stato = proposta
			ContoEconomicoConStatoVO result = null;
			logger.info("findContoEconomicoProposta: list.size() = " + list.size());
			for (ContoEconomicoConStatoVO contoEconomicoConStatoVO2 : list) {
				if (contoEconomicoConStatoVO2.getDescBreveStatoContoEconom().equals(Constants.STATO_CONTO_ECONOMICO_NUOVA_PROPOSTA_INVIATA) &&
						contoEconomicoConStatoVO2.getDtFineValidita() == null){
					result = contoEconomicoConStatoVO2;
					logger.info("findContoEconomicoProposta: result = " + result.getIdContoEconomico());
					break;
				}
			}

			if (result == null) {
				if(list.isEmpty()){
					throw new ContoEconomicoNonTrovatoException("Nessun conto economico trovato per il progetto " + idProgetto);
				}
				logger.info("Creazione nuovo conto economico in proposta per il progetto cultura" + idProgetto);
				contoEconomicoConStatoVO.setDtInizioValidita(new java.sql.Date(new Date().getTime()));
				try {
					PbandiTContoEconomicoVO pbandiTContoEconomicoVO = new PbandiTContoEconomicoVO();
					ContoEconomicoConStatoVO temp = list.get(0);
					pbandiTContoEconomicoVO.setIdDomanda(temp.getIdDomanda());
					pbandiTContoEconomicoVO.setIdUtenteIns(BigDecimal.valueOf(idUtente));
					pbandiTContoEconomicoVO.setDtInizioValidita(new java.sql.Date(new Date().getTime()));
					pbandiTContoEconomicoVO.setIdStatoContoEconomico(decodeStatoContoEconomico(Constants.STATO_CONTO_ECONOMICO_NUOVA_PROPOSTA_INVIATA));
					genericDAO.insert(pbandiTContoEconomicoVO);
					return findContoEconomicoProposta(idProgetto, idUtente);
				} catch (Exception e) {
					logger.error("Errore durante la creazione del conto economico in proposta per il progetto cultura" + idProgetto);
					throw new RuntimeException(e);
				}
			}

			return result;
	}



	// ***********************************
	//  CULTURA - VOCI DI ENTRATA - fine
	// ***********************************
	
}
