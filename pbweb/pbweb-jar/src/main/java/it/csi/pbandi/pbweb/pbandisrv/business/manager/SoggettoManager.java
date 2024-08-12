package it.csi.pbandi.pbweb.pbandisrv.business.manager;

import static it.csi.pbandi.pbweb.pbandiutil.common.BeanUtil.ACTIVITY_ASSIGNMENT_OPERATOR;
import static it.csi.pbandi.pbweb.pbandiutil.common.BeanUtil.ACTIVITY_TOKEN_SEPARATOR;
import it.csi.pbandi.pbweb.pbandisrv.dto.manager.DecodificaDTO;
import it.csi.pbandi.pbweb.pbandisrv.dto.manager.EstremiBancariDTO;
import it.csi.pbandi.pbweb.pbandisrv.dto.manager.RappresentanteLegaleDTO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.dao.GenericDAO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.dao.GenericDAO.Pair;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.BeneficiarioEnteGiuridicoVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.BeneficiarioSoggettoRuoloVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.DettaglioSoggettoVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.ProgettoSoggettoRuoloVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.SoggettoPermessoTipoAnagraficaVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.gestionedocumentazione.ProgettoUtenteVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.profilazione.BeneficiarioUtenteVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.profilazione.TipoAnagraficaVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.util.AndCondition;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.util.Condition;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.util.FilterCondition;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.util.NotCondition;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.GenericVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiDBancaVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiDTipoAnagraficaVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiDTipoSoggettoVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiRSoggTipoAnagraficaVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiRSoggettoProgettoVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiTAgenziaVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiTEstremiBancariVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiTSoggettoVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiTUtenteVO;
import it.csi.pbandi.pbweb.pbandisrv.interfacecsi.gestionedatididominio.GestioneDatiDiDominioSrv;
import it.csi.pbandi.pbweb.pbandisrv.util.Constants;
import it.csi.pbandi.pbweb.pbandiutil.common.BeanUtil;
import it.csi.pbandi.pbweb.pbandiutil.common.LoggerUtil;
import it.csi.pbandi.pbweb.pbandiutil.common.NumberUtil;
import it.csi.pbandi.pbweb.pbandiutil.common.ObjectUtil;
import it.csi.pbandi.pbweb.pbandiutil.common.StringUtil;
import it.csi.pbandi.pbweb.pbandiutil.common.dto.ContestoIdentificativoDTO;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

public class SoggettoManager {
	public final static String PERSONA_FISICA = "PERSONA_FISICA";
	public final static String ENTE_GIURIDICO = "ENTE_GIURIDICO";
	private static Map<String, String> mapTipoSoggetto = new HashMap<String, String>();
	static {
		mapTipoSoggetto.put("PERSONA_FISICA", "PF");
		mapTipoSoggetto.put("ENTE_GIURIDICO", "EG");
	}

	@Autowired
	private LoggerUtil logger;
	
	@Autowired
	private BeanUtil beanUtil;
	
	@Autowired
	private RappresentanteLegaleManager rappresentanteLegaleManager;

	public void setRappresentanteLegaleManager(
			RappresentanteLegaleManager rappresentanteLegaleManager) {
		this.rappresentanteLegaleManager = rappresentanteLegaleManager;
	}

	public RappresentanteLegaleManager getRappresentanteLegaleManager() {
		return rappresentanteLegaleManager;
	}

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

	@Autowired
	private DecodificheManager decodificheManager;

	public void setDecodificheManager(DecodificheManager decodificheManager) {
		this.decodificheManager = decodificheManager;
	}

	public DecodificheManager getDecodificheManager() {
		return decodificheManager;
	}

	@Autowired
	private GenericDAO genericDAO;

	public void setGenericDAO(GenericDAO genericDAO) {
		this.genericDAO = genericDAO;
	}

	public GenericDAO getGenericDAO() {
		return genericDAO;
	}

	public boolean isPersonaFisica(String tipoSoggetto) {

		if ((mapTipoSoggetto.get(PERSONA_FISICA)).equals(tipoSoggetto))
			return true;
		else
			return false;
	}

	public boolean isPersonaFisica(Long idTipoSoggetto) {

		DecodificaDTO decodificaVO = decodificheManager.findDecodifica(
				GestioneDatiDiDominioSrv.TIPO_SOGGETTO, idTipoSoggetto);
		if (decodificaVO != null
				&& (mapTipoSoggetto.get(PERSONA_FISICA)).equals(decodificaVO
						.getDescrizioneBreve()))
			return true;
		else
			return false;
	}

	public boolean isEnteGiuridico(String tipoSoggetto) {
		if ((mapTipoSoggetto.get(ENTE_GIURIDICO)).equals(tipoSoggetto))
			return true;
		else
			return false;
	}

	public boolean isEnteGiuridico(Long idTipoSoggetto) {
		DecodificaDTO decodificaVO = decodificheManager.findDecodifica(
				GestioneDatiDiDominioSrv.TIPO_SOGGETTO, idTipoSoggetto);
		if (decodificaVO != null
				&& (mapTipoSoggetto.get(ENTE_GIURIDICO)).equals(decodificaVO
						.getDescrizioneBreve()))
			return true;
		else
			return false;
	}

	// NB: usare il primo se ne trova tanti
	public List<RappresentanteLegaleDTO> findRappresentantiLegali(
			Long idProgetto, Long idSoggettoRappresentante) {

		return rappresentanteLegaleManager.findRappresentantiLegali(idProgetto,
				idSoggettoRappresentante);

	}

	public List<BeneficiarioUtenteVO> findBeneficiariBySoggettoRuolo(
			BeneficiarioSoggettoRuoloVO filtro) {
		List<BeneficiarioUtenteVO> beneficiariVO = getGenericDAO()
				.findListWhereGroupBy(
						new FilterCondition<BeneficiarioSoggettoRuoloVO>(filtro),
						BeneficiarioUtenteVO.class);

		return beneficiariVO;

	}

	public List<ProgettoUtenteVO> findProgettiBySoggettoRuolo(
			ProgettoSoggettoRuoloVO filtro) {
		List<ProgettoUtenteVO> progettiVO = genericDAO.findListWhereGroupBy(
				new FilterCondition<ProgettoSoggettoRuoloVO>(filtro),
				ProgettoUtenteVO.class);
		return progettiVO;
	}

	public String getDenominazioneSoggetto(BigDecimal idSoggetto) {
		DettaglioSoggettoVO soggetto = new DettaglioSoggettoVO();
		soggetto.setIdSoggetto(idSoggetto);
		long start=System.currentTimeMillis();
		soggetto = genericDAO.findSingleWhere(soggetto);
		logger.info("*****  dettaglioSoggettoVO executed in ms:  "+(System.currentTimeMillis()-start));
		String denominazione = soggetto.getDenominazione();
		if (StringUtil.isEmpty(denominazione)) {
			logger.warn("L'utente "
					+ soggetto.getIdSoggetto()
					+ " non � un ente giuridico n� una persona fisica: verr� identificato col codice fiscale.");
			denominazione = soggetto.getCodiceFiscaleSoggetto();
		}
		return denominazione;
	}

	public String getDenominazioneSoggettoCorrente(Long idUtente,
			String identitaIride) {
		String denominazioneSoggetto = "Soggetto sconosciuto";
		ContestoIdentificativoDTO contestoIdentificativo = caricaContestoIdentificativo(
				idUtente, identitaIride);
		if (contestoIdentificativo != null) {
			denominazioneSoggetto = getDenominazioneSoggetto(contestoIdentificativo
					.getIdSoggetto());
		}
		return denominazioneSoggetto;
	}

	public PbandiTUtenteVO findUtente(String codiceFiscale) {
		PbandiTUtenteVO filtro = new PbandiTUtenteVO();
		filtro.setCodiceUtente(codiceFiscale);
		filtro.setDescendentOrder("idUtente");
		PbandiTUtenteVO utenteVO = genericDAO.where(filtro).selectFirst();
		return utenteVO;
	}

	public PbandiTSoggettoVO findSoggetto(String codiceFiscale) {
		PbandiDTipoSoggettoVO tipoSoggettoVO = new PbandiDTipoSoggettoVO();
		tipoSoggettoVO
				.setDescBreveTipoSoggetto(Constants.DESCRIZIONE_BREVE_TIPO_SOGG_PERSONA_FISICA);

		PbandiTSoggettoVO soggettoVO = new PbandiTSoggettoVO();
		soggettoVO.setCodiceFiscaleSoggetto(codiceFiscale);
		soggettoVO.setDescendentOrder("idSoggetto");
		soggettoVO.setIdTipoSoggetto(genericDAO.where(tipoSoggettoVO)
				.selectSingle().getIdTipoSoggetto());
		return genericDAO.where(soggettoVO).selectFirst();
	}

	public boolean hasPermesso(ContestoIdentificativoDTO identita,
			String descBreveTipoAnagrafica, String descBrevePermesso) {
		return hasPermesso(identita.getIdSoggetto(), descBreveTipoAnagrafica,
				descBrevePermesso);
	}

	private boolean hasPermesso(BigDecimal idSoggetto,
			String descBreveTipoAnagrafica, String descBrevePermesso) {
		SoggettoPermessoTipoAnagraficaVO permessoTipoAnagraficaVO = new SoggettoPermessoTipoAnagraficaVO();
		permessoTipoAnagraficaVO.setIdSoggetto(idSoggetto);
		permessoTipoAnagraficaVO.setDescBrevePermesso(descBrevePermesso);
		permessoTipoAnagraficaVO
				.setDescBreveTipoAnagrafica(descBreveTipoAnagrafica);
		List<SoggettoPermessoTipoAnagraficaVO> result = genericDAO
				.findListWhere(permessoTipoAnagraficaVO);
		return (result != null && result.size() >= 1);
	}

	public boolean hasPermessoUtenteCorrente(Long idUtente,
			String identitaIride, String descBrevePermesso) {
		boolean autorizzato = false;

		ContestoIdentificativoDTO contestoIdentificativo = caricaContestoIdentificativo(
				idUtente, identitaIride);

		if (contestoIdentificativo != null) {
			logger.warn("contestoIdentificativo.getIdSoggetto() : "+contestoIdentificativo.getIdSoggetto()+
					"\tcontestoIdentificativo.getDescBreveTipoAnagrafica(): "+contestoIdentificativo.getDescBreveTipoAnagrafica());
			autorizzato = hasPermesso(contestoIdentificativo.getIdSoggetto(),
					contestoIdentificativo.getDescBreveTipoAnagrafica(),
					descBrevePermesso);
		}
		return autorizzato;
	}

	public ContestoIdentificativoDTO caricaContestoIdentificativo(
			Long idUtente, String identitaDigitale) {
		Object[] params = new Object[2];
		params[0] = idUtente;
		params[1] = identitaDigitale;
		return caricaContestoIdentificativoNelContestoSpring(false, params);
	}

	/**
	 * invocato ad ogni chiamata di servizio: e` importante che sia VELOCE (ma
	 * ora non lo �)
	 * 
	 * @param idUtente
	 * @param contestoIdentificativoCodificato
	 * @return
	 */
	@SuppressWarnings("static-access")
	public ContestoIdentificativoDTO caricaContestoIdentificativoNelContestoSpring(
			boolean anonymous, Object[] params) {
		ContestoIdentificativoDTO contestoIdentificativo = new ContestoIdentificativoDTO();
		Long idUtente = null;
		if (anonymous) {
			contestoIdentificativo
					.setIdUtente(it.csi.pbandi.pbweb.pbandiutil.common.Constants.ID_UTENTE_FITTIZIO);
		} else {
			idUtente = (Long) params[0];
			contestoIdentificativo.setIdUtente(idUtente);
			if (params.length > 1) {
				ContestoIdentificativoDTO newCtx = beanUtil.transform(beanUtil
						.decodeMap((String) params[1],
								ACTIVITY_TOKEN_SEPARATOR,
								ACTIVITY_ASSIGNMENT_OPERATOR),
						ContestoIdentificativoDTO.class);

				getBeanUtil().valueCopy(newCtx, contestoIdentificativo);
			}
			logger.info("\n### CONTESTO IDENTIFICATIVO ###\n" + "idUtente="
					+ contestoIdentificativo.getIdUtente() + "\n"
					+ "idSoggetto=" + contestoIdentificativo.getIdSoggetto()
					+ "\n" + "idIstanzaAttivitaProcesso="
					+ contestoIdentificativo.getIdIstanzaAttivitaProcesso()
					+ "\n" + "nome=" + contestoIdentificativo.getNome() + "\n");
		}

		if (it.csi.pbandi.pbweb.pbandiutil.common.Constants.ID_UTENTE_FITTIZIO != idUtente
				&& contestoIdentificativo.getIdSoggetto() == null) {
			logger.info("idSoggetto null, lo cerco dal codice fiscale "
					+ contestoIdentificativo.getCodFiscale());
			contestoIdentificativo.setIdSoggetto(findSoggetto(
					contestoIdentificativo.getCodFiscale()).getIdSoggetto());
		}
		return contestoIdentificativo;
	}

	@Deprecated
	public List<PbandiDTipoAnagraficaVO> findTipiAnagraficaOld(
			BigDecimal idSoggetto) {
		PbandiRSoggTipoAnagraficaVO rSoggTipoAnagrafica = new PbandiRSoggTipoAnagraficaVO();
		rSoggTipoAnagrafica.setIdSoggetto(idSoggetto);

		PbandiDTipoAnagraficaVO tipoAnagraficaVO = new PbandiDTipoAnagraficaVO();
		tipoAnagraficaVO.setAscendentOrder("descTipoAnagrafica");

		List<Pair<GenericVO, PbandiRSoggTipoAnagraficaVO, PbandiDTipoAnagraficaVO>> list = genericDAO
				.join(Condition.validOnly(PbandiRSoggTipoAnagraficaVO.class)
						.and(Condition.filterBy(rSoggTipoAnagrafica)),
						Condition.validOnly(PbandiDTipoAnagraficaVO.class).and(
								Condition.filterBy(tipoAnagraficaVO)))
				.by("idTipoAnagrafica")
				.orderBy(GenericDAO.Order.ascendent("descTipoAnagrafica", 1))
				.select();

		List<PbandiDTipoAnagraficaVO> listTipiAnagrafica = new ArrayList<PbandiDTipoAnagraficaVO>();
		for (Pair<GenericVO, PbandiRSoggTipoAnagraficaVO, PbandiDTipoAnagraficaVO> pair : list) {
			listTipiAnagrafica.add(pair.getSecond());
		}

		return listTipiAnagrafica;
	}

	public List<TipoAnagraficaVO> findTipiAnagrafica(BigDecimal idSoggetto) {

		TipoAnagraficaVO tipoAnagraficaVO = new TipoAnagraficaVO();
		tipoAnagraficaVO.setAscendentOrder("descTipoAnagrafica");
		tipoAnagraficaVO.setIdSoggetto(idSoggetto);

		List<TipoAnagraficaVO> listTipiAnagrafica = new ArrayList<TipoAnagraficaVO>();
		listTipiAnagrafica = genericDAO.findListWhere(tipoAnagraficaVO);

		return listTipiAnagrafica;
	}

	public EstremiBancariDTO getEstremiBancari(Long idProgetto, Long idSoggetto) {

		logger.info("[SoggettoManager::getEstremiBancari] BEGIN");
		EstremiBancariDTO estremiBancariDTO = new EstremiBancariDTO();
		
		PbandiRSoggettoProgettoVO pbandiRSoggettoProgettoVO = new PbandiRSoggettoProgettoVO();
		pbandiRSoggettoProgettoVO.setIdProgetto(new BigDecimal(idProgetto));
		//pbandiRSoggettoProgettoVO.setIdSoggetto(new BigDecimal(idSoggetto));
		pbandiRSoggettoProgettoVO.setDtFineValidita(null);
		pbandiRSoggettoProgettoVO.setIdTipoAnagrafica(new BigDecimal(
						decodificheManager
								.findDecodifica(
										GestioneDatiDiDominioSrv.TIPO_ANAGRAFICA,
										Constants.DESCRIZIONE_BREVE_TIPO_ANAGRAFICA_BENEFICIARIO)
								.getId()));
		logger.info("[SoggettoManager::getEstremiBancari] popolato pbandiRSoggettoProgettoVO="+pbandiRSoggettoProgettoVO);
		
		PbandiRSoggettoProgettoVO filtro = new PbandiRSoggettoProgettoVO();
		filtro.setIdTipoBeneficiario(new BigDecimal(
				decodificheManager
						.findDecodifica(
								GestioneDatiDiDominioSrv.TIPO_BENEFICIARIO,
								Constants.DESCRIZIONE_BREVE_TIPO_BENEFICIARIO_BEN_ASSOCIATO)
						.getId()));
		logger.info("[SoggettoManager::getEstremiBancari] popolato filtro="+filtro);
		
		List<PbandiRSoggettoProgettoVO> listSoggettoProgetto = genericDAO
				.findListWhere(new AndCondition<PbandiRSoggettoProgettoVO>(
						new FilterCondition<PbandiRSoggettoProgettoVO>(	pbandiRSoggettoProgettoVO),
						new NotCondition<PbandiRSoggettoProgettoVO>(new FilterCondition<PbandiRSoggettoProgettoVO>(	filtro))));
		
		if (listSoggettoProgetto != null && !listSoggettoProgetto.isEmpty()) {
			
			logger.info("[SoggettoManager::getEstremiBancari] trovata lista di dimensione ="+listSoggettoProgetto.size());
			PbandiRSoggettoProgettoVO vo = listSoggettoProgetto.get(0);
			if (vo.getIdEstremiBancari() != null) {
				PbandiTEstremiBancariVO pbandiTEstremiBancariVO = new PbandiTEstremiBancariVO();
				pbandiTEstremiBancariVO.setIdEstremiBancari(vo.getIdEstremiBancari());
				pbandiTEstremiBancariVO = genericDAO.findSingleWhere(pbandiTEstremiBancariVO);
				getBeanUtil().valueCopy(pbandiTEstremiBancariVO,estremiBancariDTO);
				if (pbandiTEstremiBancariVO.getIdBanca() != null) {
					PbandiDBancaVO pbandiBancaVO = new PbandiDBancaVO();
					pbandiBancaVO.setIdBanca(pbandiTEstremiBancariVO
							.getIdBanca());
					pbandiBancaVO = genericDAO.findSingleWhere(pbandiBancaVO);
					estremiBancariDTO.setDescrizioneBanca(pbandiBancaVO
							.getDescBanca());
				}
				if (pbandiTEstremiBancariVO.getIdAgenzia() != null) {
					PbandiTAgenziaVO pbandiTAgenziaVO = new PbandiTAgenziaVO();
					pbandiTAgenziaVO.setIdAgenzia(pbandiTEstremiBancariVO
							.getIdAgenzia());
					pbandiTAgenziaVO = genericDAO
							.findSingleWhere(pbandiTAgenziaVO);
					estremiBancariDTO.setDescrizioneAgenzia(pbandiTAgenziaVO
							.getDescAgenzia());

				}
			}
		}else {
			logger.info("[SoggettoManager::getEstremiBancari] listSoggettoProgetto vuota");
		}
		logger.info("[SoggettoManager::getEstremiBancari] END");
		return (estremiBancariDTO);
	}

	public BeneficiarioEnteGiuridicoVO findEnteGiuridicoBeneficiario(
			Long idProgetto, Long idSoggettoBeneficiario) {
		BeneficiarioEnteGiuridicoVO filtroVO = new BeneficiarioEnteGiuridicoVO();
		filtroVO.setIdSoggetto(NumberUtil.toBigDecimal(idSoggettoBeneficiario));
		filtroVO.setIdProgetto(NumberUtil.toBigDecimal(idProgetto));

		List<BeneficiarioEnteGiuridicoVO> entiGiuridiciSoggetto = genericDAO
				.findListWhere(filtroVO);
		if (!ObjectUtil.isEmpty(entiGiuridiciSoggetto)) {
			return entiGiuridiciSoggetto.get(0);
		} else {
			return null;
		}
	}

}
