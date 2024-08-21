/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.business.profilazione;

import it.csi.csi.wrapper.CSIException;
import it.csi.csi.wrapper.SystemException;
import it.csi.csi.wrapper.UnrecoverableException;
import it.csi.iride2.policy.entity.Identita;
import it.csi.pbandi.pbservizit.exception.DaoException;
import it.csi.pbandi.pbservizit.pbandisrv.business.BusinessImpl;
import it.csi.pbandi.pbservizit.pbandisrv.business.manager.ConfigurationManager;
import it.csi.pbandi.pbservizit.pbandisrv.business.manager.ProgettoManager;
import it.csi.pbandi.pbservizit.pbandisrv.business.manager.SoggettoManager;
import it.csi.pbandi.pbservizit.pbandisrv.dto.profilazione.Beneficiario;
import it.csi.pbandi.pbservizit.pbandisrv.dto.profilazione.ConsensoInvioComunicazioneDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.profilazione.EsitoControlloAccessoDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.profilazione.FiltroBeneficiarioProgettoSoggettoRuoloDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.profilazione.ProgettoDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.profilazione.Ruolo;
import it.csi.pbandi.pbservizit.pbandisrv.dto.profilazione.UserInfoDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.profilazione.UserParamsDTO;
import it.csi.pbandi.pbservizit.pbandisrv.exception.profilazione.ProfilazioneException;
import it.csi.pbandi.pbservizit.pbandisrv.exception.profilazione.UtenteException;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.dao.GenericDAO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.exception.RecordNotFoundException;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.dao.PbandiProfilazioneDAOImpl;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.BeneficiarioProgettoBandolineaVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.BeneficiarioSoggettoRuoloVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.BeneficiarioVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.PersonaFisicaVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.ProgettoSoggettoRuoloVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.RegolaAssociataBandoLineaVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.gestionedocumentazione.ProgettoUtenteVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.gestioneincarichi.RappresentanteLegaleGestioneIncarichiVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.profilazione.BeneficiarioUtenteVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.profilazione.TipoAnagraficaVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.util.AndCondition;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.util.Condition;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiDPermessoVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiDRuoloHelpVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiDTipoAccessoVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiDTipoAnagraficaVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiRPermessoTipoAnagrafVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiTAccessoIrideVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiTAccessoUppVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiTSoggettoVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiTStoricoAccessoVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiTUtenteVO;
import it.csi.pbandi.pbservizit.pbandisrv.interfacecsi.profilazione.ProfilazioneSrv;
import it.csi.pbandi.pbservizit.pbandisrv.util.validation.ValidatorInput;
import it.csi.pbandi.pbservizit.pbandiutil.common.DateUtil;
import it.csi.pbandi.pbservizit.pbandiutil.common.NumberUtil;
import it.csi.pbandi.pbservizit.pbandiutil.common.ObjectUtil;
import it.csi.pbandi.pbservizit.pbandiutil.common.dto.ContestoIdentificativoDTO;
import it.csi.pbandi.pbservizit.pbandiutil.common.messages.ErrorConstants;
import it.csi.pbandi.pbservizit.util.Constants;
import it.csi.pbandi.pbservizit.util.UseCaseConstants;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProfilazioneBusinessImpl extends BusinessImpl implements ProfilazioneSrv {
	private static final Map<String, String> TIPO_ANAGRAFICA_VO_TO_DTO = new HashMap<String, String>();
	private static final Map<String, String> BENEFICIARIO_VO_TO_DTO = new HashMap<String, String>();
	static {
		TIPO_ANAGRAFICA_VO_TO_DTO.put("idTipoAnagrafica", "id");
		TIPO_ANAGRAFICA_VO_TO_DTO.put("descBreveTipoAnagrafica", "descrizioneBreve");
		TIPO_ANAGRAFICA_VO_TO_DTO.put("descRuoloHelp", "ruoloHelp");
		TIPO_ANAGRAFICA_VO_TO_DTO.put("descTipoAnagrafica", "descrizione");
		BENEFICIARIO_VO_TO_DTO.put("idSoggettoBeneficiario", "id_soggetto");
		BENEFICIARIO_VO_TO_DTO.put("codiceFiscaleBeneficiario", "codiceFiscale");
		BENEFICIARIO_VO_TO_DTO.put("denominazioneBeneficiario", "descrizione");
	}

	private Logger LOG = Logger.getLogger(Constants.COMPONENT_NAME);

	private ProgettoManager progettoManager;

	@Autowired
	private PbandiProfilazioneDAOImpl pbandiProfilazioneDAO;

	@Autowired
	private GenericDAO genericDAO;

	@Autowired
	private SoggettoManager soggettoManager;

	@Autowired
	private ConfigurationManager configurationManager;

	public PbandiProfilazioneDAOImpl getPbandiProfilazioneDAO() {
		return pbandiProfilazioneDAO;
	}

	public void setPbandiProfilazioneDAO(PbandiProfilazioneDAOImpl pbandiProfilazioneDAO) {
		this.pbandiProfilazioneDAO = pbandiProfilazioneDAO;
	}

	public void setProgettoManager(ProgettoManager progettoManager) {
		this.progettoManager = progettoManager;
	}

	public ProgettoManager getProgettoManager() {
		return progettoManager;
	}

	public void setGenericDAO(GenericDAO genericDAO) {
		this.genericDAO = genericDAO;
	}

	public GenericDAO getGenericDAO() {
		return genericDAO;
	}

	public void setSoggettoManager(SoggettoManager soggettoManager) {
		this.soggettoManager = soggettoManager;
	}

	public SoggettoManager getSoggettoManager() {
		return soggettoManager;
	}

	public void setConfigurationManager(ConfigurationManager configurationManager) {
		this.configurationManager = configurationManager;
	}

	public ConfigurationManager getConfigurationManager() {
		return configurationManager;
	}

	public java.lang.Boolean hasPermesso(Long idUtente, String identitaDigitale, String descBreveRuolo,
			String descBrevePermesso) throws CSIException, SystemException, UnrecoverableException, UtenteException {
		logger.begin();
		logger.debug("idUtente=" + idUtente + ", identitaDigitale=" + identitaDigitale);
		ContestoIdentificativoDTO contestoIdentificativo = soggettoManager.caricaContestoIdentificativo(idUtente,
				identitaDigitale);
		boolean autorizzato = soggettoManager.hasPermesso(contestoIdentificativo, descBreveRuolo, descBrevePermesso);
		logger.debug("autorizzato=" + autorizzato);
		logger.end();
		return autorizzato;
	}

	public Boolean isRegolaApplicabileForBandoLinea(java.lang.Long idUtente, java.lang.String identitaDigitale,
			Long idBandoLinea, String codiceRegola) throws CSIException, SystemException, UnrecoverableException {
		boolean result = false;

		result = regolaManager.isRegolaApplicabileForBandoLinea(idBandoLinea, codiceRegola);

		return result;
	}

	public Boolean isRegolaApplicabileForProgetto(java.lang.Long idUtente, java.lang.String identitaDigitale,
			Long idProgetto, String codiceRegola) throws CSIException, SystemException, UnrecoverableException {
		boolean result = false;

		result = regolaManager.isRegolaApplicabileForProgetto(idProgetto, codiceRegola);

		return result;
	}

	public UserInfoDTO getInfoUtente(Long idUtente, String identitaDigitale) throws CSIException {

		UserInfoDTO userInfo = new UserInfoDTO();

		ContestoIdentificativoDTO identita = soggettoManager.caricaContestoIdentificativo(idUtente, identitaDigitale);
		logger.info("\nidentitaDigitale: " + identitaDigitale);
		eseguiControlliComuni(userInfo, identita);

		userInfo.setCognome(identita.getCognome());
		userInfo.setNome(identita.getNome());
		userInfo.setCodiceFiscale(identita.getCodFiscale());
		userInfo.setIsIncaricato(Boolean.FALSE);
		userInfo.setRuoloHelp(identita.getRuoloHelp());
		logger.info("identita.getRuoloHelp()  : " + identita.getRuoloHelp());
		return userInfo;

	}

	public UserInfoDTO getMiniAppInfoUtente(Long idUtenteCorrente, String identitaDigitaleCorrente,
			UserParamsDTO userParams)
			throws CSIException, SystemException, UnrecoverableException, ProfilazioneException {

		Long idUtente = userParams.getIdUtente();

		try {
			UserInfoDTO userInfo = new UserInfoDTO();

			ContestoIdentificativoDTO identita = soggettoManager.caricaContestoIdentificativo(idUtente,
					identitaDigitaleCorrente);
			/*
			 * logger.info("\n\n\nidentita.getDescBreveTipoAnagrafica(): "+identita.
			 * getDescBreveTipoAnagrafica());
			 * logger.info("identita.getRuoloHelp() 1: "+identita.getRuoloHelp());
			 * logger.info("identitaDigitaleCorrente:"+identitaDigitaleCorrente);
			 * logger.info("userParams.getIdentitaIride():"+userParams.getIdentitaIride());
			 */

			PbandiTSoggettoVO soggetto = eseguiControlliComuni(userInfo, identita);

			/*
			 * 2) Controllo che l' idSoggetto restituito sia uguale all' idSoggetto di
			 * userParams. Questo controllo vale anche in caso di accesso di utente
			 * incaricato poiche' sia l' idSoggetto che l' identita iride devono essere
			 * relativi al soggetto delegante.
			 */
			if (NumberUtil.compare(soggetto.getIdSoggetto(), new BigDecimal(userParams.getIdSoggetto())) != 0) {
				logger.warn("Soggetto[" + userParams.getIdSoggetto() + " - " + identita.getCodFiscale()
						+ "] non presente in PBandi_T_Soggetto");
				UtenteException ue = new UtenteException(ErrorConstants.ERRORE_UTENTE_NON_AUTORIZZATO);
				throw ue;
			}

			/*
			 * 3) Nel caso di accesso di utente incaricato verifico che il delegante
			 * (idSoggetto di userParams) sia Rappresentante Legale per l' azienda
			 * (idSoggettoBeneficiario) Altrimenti verifico che esiste l' utente in
			 * PBandi_t_utente con idUtente e codice fiscale dell' identita iride
			 */
			if (userParams.getFlagIncaricato() != null && userParams.getFlagIncaricato()) {
				RappresentanteLegaleGestioneIncarichiVO rappLegaleVO = new RappresentanteLegaleGestioneIncarichiVO();
				rappLegaleVO.setIdSoggettoDelegante(new BigDecimal(userParams.getIdSoggetto()));
				rappLegaleVO.setIdSoggettoBeneficiario(new BigDecimal(userParams.getIdSoggettoBeneficiario()));
				List<RappresentanteLegaleGestioneIncarichiVO> listRapp = genericDAO.findListWhere(rappLegaleVO);
				if (listRapp == null || listRapp.size() == 0) {
					logger.warn("Delegate[" + identita.getCodFiscale()
							+ "] non presente come rappresentante legale dell'azienda ["
							+ userParams.getIdSoggettoBeneficiario() + "]");
					UtenteException ue = new UtenteException(ErrorConstants.ERRORE_UTENTE_NON_AUTORIZZATO);
					throw ue;
				}
			} else {
				PbandiTUtenteVO utenteVO = new PbandiTUtenteVO();
				utenteVO.setIdUtente(new BigDecimal(idUtente));
				utenteVO.setCodiceUtente(identita.getCodFiscale());
				utenteVO = genericDAO.findSingleWhere(utenteVO);
				if (utenteVO == null) {
					logger.warn("Utente[" + idUtente + " - " + identita.getCodFiscale() + "] non censito in PBANDI");
					UtenteException ue = new UtenteException(ErrorConstants.ERRORE_UTENTE_NON_AUTORIZZATO);
					throw ue;
				}
			}

			/*
			 * Nel caso di incaricato: - nome, cognome e codice fiscale sono quelli dell'
			 * incaricato - idSoggetto e' quello del delegante
			 */
			userInfo.setIdSoggetto(userParams.getIdSoggetto());
			userInfo.setIdUtente(idUtente);

			if (userParams.getFlagIncaricato() != null && userParams.getFlagIncaricato()) {
				PbandiTUtenteVO utenteIncaricatoVO = new PbandiTUtenteVO();
				utenteIncaricatoVO.setIdUtente(new BigDecimal(idUtente));
				utenteIncaricatoVO = genericDAO.findSingleWhere(utenteIncaricatoVO);
				PersonaFisicaVO personaFisicaVO = new PersonaFisicaVO();
				personaFisicaVO.setIdSoggetto(utenteIncaricatoVO.getIdSoggetto());
				personaFisicaVO = genericDAO.findSingleWhere(personaFisicaVO);
				userInfo.setCognome(personaFisicaVO.getCognome());
				userInfo.setNome(personaFisicaVO.getNome());
				userInfo.setCodiceFiscale(personaFisicaVO.getCodiceFiscale());
				userInfo.setIsIncaricato(Boolean.TRUE);
			} else {
				userInfo.setCognome(identita.getCognome());
				userInfo.setNome(identita.getNome());
				userInfo.setCodiceFiscale(identita.getCodFiscale());
				userInfo.setIsIncaricato(Boolean.FALSE);
			}
			String codTipoAnagrafica = userParams.getCodTipoAnagrafica();
			if (codTipoAnagrafica != null) {
				PbandiDTipoAnagraficaVO anagraficaVO = new PbandiDTipoAnagraficaVO();
				anagraficaVO.setDescBreveTipoAnagrafica(codTipoAnagrafica);
				anagraficaVO = genericDAO.findSingleWhere(anagraficaVO);
				if (anagraficaVO.getIdRuoloHelp() != null) {
					PbandiDRuoloHelpVO dRuoloHelpVO = new PbandiDRuoloHelpVO();
					dRuoloHelpVO.setIdRuoloHelp(anagraficaVO.getIdRuoloHelp());
					dRuoloHelpVO = genericDAO.findSingleWhere(dRuoloHelpVO);
					userInfo.setRuoloHelp(dRuoloHelpVO.getDescRuoloHelp());
				}
			}

			logger.warn("\n\n\nuserInfo.getRuoloHelp()  ---> " + userInfo.getRuoloHelp());

			return userInfo;
		} finally {
		}

	}

	private PbandiTSoggettoVO eseguiControlliComuni(UserInfoDTO userInfo, ContestoIdentificativoDTO identita)
			throws UtenteException {
		boolean autorizzato = true;

		PbandiTSoggettoVO soggetto = null;
		PbandiTUtenteVO utente = null;
		// OLD,TO REMOVE
		// List<PbandiDTipoAnagraficaVO> tipiAnagrafica = null;
		// OLD,TO REMOVE
		List<TipoAnagraficaVO> tipiAnagrafica = null;
		try {
			if (identita.getCodFiscale() == null) {
				UtenteException e = new UtenteException("Codice fiscale null letto dal contesto identificativo.");
				logger.error("Codice fiscale non valorizzato nel contesto identificativo.", e);
				throw e;
			}
			soggetto = soggettoManager.findSoggetto(identita.getCodFiscale());

			// OLD, TO REMOVE
			// tipiAnagrafica = soggettoManager.findTipiAnagrafica(soggetto
			// .getIdSoggetto());
			// OLD,TO REMOVE

			tipiAnagrafica = soggettoManager.findTipiAnagrafica(soggetto.getIdSoggetto());
			try {
				utente = soggettoManager.findUtente(identita.getCodFiscale());

				// ruoloHelp???

				if (isEmpty(tipiAnagrafica)) {
					logger.warn("Utente[" + identita.getCodFiscale() + "] non possiede ruoli, quindi non pu� accedere");
					autorizzato = false;
				}

			} catch (RecordNotFoundException e) {
				logger.warn("Utente[" + identita.getCodFiscale() + "] non censito in PBandi_T_utente");
				autorizzato = false;
			}
		} catch (RecordNotFoundException e) {
			logger.warn("Utente[" + identita.getCodFiscale() + "] non presente in PBandi_T_Soggetto");
			autorizzato = false;
		}

		if (!autorizzato) {
			throw new UtenteException(ErrorConstants.ERRORE_UTENTE_NON_AUTORIZZATO);
		} else {
			userInfo.setIdSoggetto(NumberUtil.toLong(soggetto.getIdSoggetto()));

			userInfo.setRuoli(beanUtil.transform(tipiAnagrafica, Ruolo.class, TIPO_ANAGRAFICA_VO_TO_DTO));

			userInfo.setIdUtente(beanUtil.transform(utente.getIdUtente(), Long.class));
		}

		return soggetto;
	}

	public Beneficiario[] findBeneficiari(Long idUtente, String identitaDigitale, String codiceFiscale,
			String ruoloIride) throws CSIException, SystemException, UnrecoverableException, ProfilazioneException {
		PbandiTSoggettoVO soggettoVO = soggettoManager.findSoggetto(codiceFiscale);

		BeneficiarioSoggettoRuoloVO filtro = new BeneficiarioSoggettoRuoloVO();
		filtro.setIdSoggetto(soggettoVO.getIdSoggetto());
		filtro.setDescBreveTipoAnagrafica(ruoloIride);
		List<String> orderByList = new ArrayList<String>();
		orderByList.add("denominazioneBeneficiario");
		orderByList.add("codiceFiscaleBeneficiario");
		filtro.setOrderPropertyNamesList(orderByList);
		List<BeneficiarioUtenteVO> beneficiariVO = getSoggettoManager().findBeneficiariBySoggettoRuolo(filtro);

		return beanUtil.transform(beneficiariVO, Beneficiario.class, BENEFICIARIO_VO_TO_DTO);

	}

	public Beneficiario[] findBeneficiariProgetto(Long idUtente, String identitaDigitale, Long idProgetto)
			throws CSIException, SystemException, UnrecoverableException, ProfilazioneException {
		// FIXME dal business il beneficiario sar� solo uno, ma non lo
		// modifico perch� altrimenti dovrei adeguare anche il servizio e
		// tutti i client che lo richiamano
		return progettoManager.getBeneficiari(idProgetto);
	}

	public Beneficiario[] findBeneficiario(Long idUtente, String identitaDigitale, Long idSoggettoBeneficiario)
			throws CSIException, SystemException, UnrecoverableException, ProfilazioneException {

		String[] nameParameter = { "idUtente", "identitaDigitale", "idSoggettoBeneficiario" };
		ValidatorInput.verifyNullValue(nameParameter, idUtente, identitaDigitale, idSoggettoBeneficiario);
		List<BeneficiarioVO> listBeneficiari = pbandiProfilazioneDAO.findBeneficiario(idSoggettoBeneficiario);
		return beanUtil.transform(listBeneficiari, Beneficiario.class);

	}

	public Beneficiario[] findBeneficiariByProgettoSoggettoRuolo(Long idUtente, String identitaDigitale,
			FiltroBeneficiarioProgettoSoggettoRuoloDTO filtro)
			throws CSIException, SystemException, UnrecoverableException, ProfilazioneException {
		String[] nameParameter = { "idUtente", "identitaDigitale", "filtro" };
		ValidatorInput.verifyNullValue(nameParameter, idUtente, identitaDigitale, filtro);
		ValidatorInput.verifyAtLeastOneNotNullValue(filtro);
		Map<String, String> trsMap = new HashMap<String, String>() {
			{
				put("idSoggetto", "idSoggetto");
				put("codiceRuolo", "descBreveTipoAnagrafica");
				put("idSoggettoBeneficiario", "idSoggettoBeneficiario");
				put("idProgetto", "idProgetto");
				put("idBando", "progrBandoLineaIntervento");
			}
		};
		BeneficiarioSoggettoRuoloVO filter = beanUtil.transform(filtro, BeneficiarioSoggettoRuoloVO.class, trsMap);

		List<String> orderByList = new ArrayList<String>();
		orderByList.add("denominazioneBeneficiario");
		orderByList.add("codiceFiscaleBeneficiario");
		filter.setOrderPropertyNamesList(orderByList);

		List<BeneficiarioUtenteVO> beneficiariVO = getSoggettoManager().findBeneficiariBySoggettoRuolo(filter);

		return beanUtil.transform(beneficiariVO, Beneficiario.class, BENEFICIARIO_VO_TO_DTO);
	}

	@Override
	public Beneficiario[] findBeneficiariByProgettoSoggettoRuoloDenominazioneOrIdBen(Long idUtente,
			String identitaDigitale, FiltroBeneficiarioProgettoSoggettoRuoloDTO filtro, String denominazione)
			throws CSIException, SystemException, UnrecoverableException, ProfilazioneException, DaoException {
		String prf = "[ProfilazioneDAOImpl::findBeneficiariByProgettoSoggettoRuoloDenominazioneOrIdBen] ";
		LOG.info(prf + " BEGIN");
		String[] nameParameter = { "idUtente", "identitaDigitale", "filtro" };
		ValidatorInput.verifyNullValue(nameParameter, idUtente, identitaDigitale, filtro);
		ValidatorInput.verifyAtLeastOneNotNullValue(filtro);

		List<BeneficiarioUtenteVO> beneficiariVO = new ArrayList<>();
		try {
			beneficiariVO = pbandiProfilazioneDAO.findBeneficiariDocProgBySoggettoRuoloDenominazioneOrIdBen(
					new BigDecimal(filtro.getIdSoggetto()), filtro.getCodiceRuolo(), denominazione,
					filtro.getIdSoggettoBeneficiario());
		} catch (DaoException e) {
			LOG.error(prf + "Errore nel caricamento dei beneficiari di documenti di progetto" + e);
			throw e;
		} finally {
			LOG.info(prf + " END");
		}

		return beanUtil.transform(beneficiariVO, Beneficiario.class, BENEFICIARIO_VO_TO_DTO);
	}

	@Override
	public ProgettoDTO[] findProgettiByBeneficiarioSoggettoRuolo(Long idUtente, String identitaDigitale,
			FiltroBeneficiarioProgettoSoggettoRuoloDTO filtro)
			throws CSIException, SystemException, UnrecoverableException, UtenteException {
		String[] nameParameter = { "idUtente", "identitaDigitale", "filtro" };
		ValidatorInput.verifyNullValue(nameParameter, idUtente, identitaDigitale, filtro);
		ValidatorInput.verifyAtLeastOneNotNullValue(filtro);
		Map<String, String> trsMap = new HashMap<String, String>() {
			{
				put("idSoggetto", "idSoggetto");
				put("codiceRuolo", "descBreveTipoAnagrafica");
				put("idSoggettoBeneficiario", "idSoggettoBeneficiario");
				put("idProgetto", "idProgetto");
				put("idBando", "progrBandoLineaIntervento");
			}
		};

		ProgettoSoggettoRuoloVO filter = beanUtil.transform(filtro, ProgettoSoggettoRuoloVO.class, trsMap);
		filter.setAscendentOrder("codiceVisualizzatoProgetto");

		List<ProgettoUtenteVO> progetti = getSoggettoManager().findProgettiBySoggettoRuolo(filter);

		return beanUtil.transform(progetti, ProgettoDTO.class);
	}

	public EsitoControlloAccessoDTO controllaStoricizzaAccesso(Long idUtente, String identitaDigitale,
			Long codTipoAccesso, Boolean isSpid)
			throws CSIException, SystemException, UnrecoverableException, UtenteException {

		String[] nameParameter = { "idUtente", "identitaDigitale", "codTipoAccesso", "isSpid" };
		ValidatorInput.verifyNullValue(nameParameter, idUtente, identitaDigitale, codTipoAccesso, isSpid);

		EsitoControlloAccessoDTO result = new EsitoControlloAccessoDTO();
		result.setIsAccessoBloccato(Boolean.FALSE);

		/*
		 * Data odierna
		 */
		Date currentDate = DateUtil.getSysdate();

		/*
		 * Ricerco il tipo di accesso
		 */
		PbandiDTipoAccessoVO tipoAccessoVO = new PbandiDTipoAccessoVO();
		tipoAccessoVO.setCodTipoAccesso(beanUtil.transform(codTipoAccesso, String.class));
		try {
			tipoAccessoVO = genericDAO.findSingleWhere(tipoAccessoVO);
		} catch (RecordNotFoundException rnfe) {
			logger.warn("Errore durante la ricerca del tipo di accesso (" + codTipoAccesso + ")" + rnfe.getMessage());
			throw rnfe;
		}

		/*
		 * Storicizzo l' accesso dell'utente
		 */
		PbandiTStoricoAccessoVO storicoVO = new PbandiTStoricoAccessoVO();
		storicoVO.setIdUtente(beanUtil.transform(idUtente, BigDecimal.class));
		storicoVO.setIdTipoAccesso(tipoAccessoVO.getIdTipoAccesso());
		storicoVO.setDtAccesso(DateUtil.utilToSqlDate(currentDate));
		try {
			genericDAO.insert(storicoVO);
		} catch (Exception e) {
			logger.error("Errore durante insert in PBANDI_T_STORICO_ACCESSO [idUtente=" + idUtente, e);
			throw new UtenteException("Errore durante la storicizzazione dell' accesso.", e);
		}

		/*
		 * ATTENZIONE: VALE SOLO PER SHIBBOLETH, NON PER SPID. (Alex 17/7/2018) Se
		 * l'utente accede con il livello di autenticazione User/Psw/Pin devo: 1) al
		 * primo accesso: - inserisco il record nella PBANDI_T_ACCESSO_UPP - calcolo la
		 * possibile data di blocco dell' accesso, aggiugendo 30 gg alla data odierna.
		 * Se questa data e' antecedente alla data di blocco di tutti gli accessi con
		 * usr/psw/pin allora la setto come data di blocco accesso per l' utente, mentre
		 * se la data e' successiva alla data di blocco di tutti gli accessi con
		 * usr/psw/pin allora setto quest'ultima come data di blocco accesso per
		 * l'utente.
		 * 
		 * 2) dal secondo accesso: - controllo la data odierna con la data di blocco
		 * accesso per l' utente, se e' valida restituisco il msg con la data di blocco,
		 * altrimenti restituisco il messggio che le credenziali non sono piu' valide.
		 */

		/*
		 * Verifico il livello di accesso
		 */
		if (codTipoAccesso == Identita.AUTENTICAZIONE_USERNAME_PASSWORD_PIN && !isSpid) {
			/*
			 * Data di blocco di tutti gli accessi con user/psw/pin
			 */
			Date dtBloccoAccessiUPP = configurationManager.getConfiguration().getDtBloccoAccessoUPP();

			/*
			 * Numero di gg per il calcolo della data di blocco
			 */
			Long gg = configurationManager.getConfiguration().getGgCalcoloDtBloccoAccessoUPP();

			PbandiTAccessoUppVO accessoVO = new PbandiTAccessoUppVO();
			accessoVO.setIdUtente(beanUtil.transform(idUtente, BigDecimal.class));
			List<PbandiTAccessoUppVO> accessiUPP = genericDAO.findListWhere(accessoVO);

			if (ObjectUtil.isEmpty(accessiUPP)) {
				accessoVO.setDtPrimoAccesso(DateUtil.getSysdate());
				/*
				 * Calcolo la data di blocco dell' accesso dell' utente con user/psw/pin come la
				 * data minima tra la (dataOdierna+30) e la data di blocco di tutti gli accessi
				 * con user/psw/pin.
				 */
				Date dtBloccoUtente = DateUtil.addGiorni(currentDate, beanUtil.transform(gg, Integer.class));
				try {
					if (DateUtil.after(dtBloccoUtente, dtBloccoAccessiUPP))
						accessoVO.setDtBloccoAccesso(DateUtil.utilToSqlDate(dtBloccoAccessiUPP));
					else
						accessoVO.setDtBloccoAccesso(DateUtil.utilToSqlDate(dtBloccoUtente));
				} catch (Exception e1) {
					logger.error(e1.getMessage(), e1);
				}
				try {
					accessoVO = genericDAO.insert(accessoVO);
				} catch (Exception e) {
					logger.error("Errore durante insert in PBANDI_T_ACCESSO_UPP [idUtente=" + idUtente, e);
					throw new UtenteException("Errore durante l' inserimento dell' accesso con usr/pin/psw.", e);
				}
			} else {
				/*
				 * Recupero le informazioni relative alla data di blocco
				 */
				accessoVO = accessiUPP.get(0);
			}

			/*
			 * Se la data odierna e' successiva alla data minima tra la data di blocco dell'
			 * utente e la data di blocco di tutti gli accessi con user/psw/pin allora
			 * blocco l'utente.
			 */
			try {
				Date dataMinimaBlocco = null;
				if (DateUtil.before(accessoVO.getDtBloccoAccesso(), dtBloccoAccessiUPP))
					dataMinimaBlocco = accessoVO.getDtBloccoAccesso();
				else
					dataMinimaBlocco = dtBloccoAccessiUPP;
				String msgAvviso = new String();
				if (DateUtil.before(dataMinimaBlocco, currentDate)) {
					result.setIsAccessoBloccato(Boolean.TRUE);
					msgAvviso = configurationManager.getConfiguration().getMsgAccessoBloccato();
					msgAvviso = msgAvviso.replace("${dtBlocco}", DateUtil.getData(dataMinimaBlocco.getTime()));
				} else {
					msgAvviso = configurationManager.getConfiguration().getMsgDataBloccoAccesso();
					msgAvviso = msgAvviso.replace("${dtBlocco}", DateUtil.getData(dataMinimaBlocco.getTime()));
				}
				result.setMsgAvviso(msgAvviso);
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		}
		return result;

	}

	public void memorizzaIride(Long idUtente, String identitaDigitale, String idShib, String idIride, String sessionId,
			String msg) throws CSIException, SystemException, UnrecoverableException, UtenteException {
		PbandiTAccessoIrideVO vo = new PbandiTAccessoIrideVO();
		vo.setAccessDate(DateUtil.getSysdate());
		vo.setIrideId(idIride == null ? null : idIride.substring(0, Math.min(244, idIride.length())));
		vo.setMsg(msg == null ? null : msg.substring(0, Math.min(244, msg.length())));
		vo.setShibId(idShib == null ? null : idShib.substring(0, Math.min(244, idShib.length())));
		vo.setSessionId(sessionId == null ? null : sessionId.substring(0, Math.min(244, sessionId.length())));
		try {
			genericDAO.insert(vo);
		} catch (Exception e) {
			/*
			 * Attenzione. In caso di errore il sistema deve continuare a funzionare.
			 */
			logger.error("Errore durante inserimento su PBandi_t_Accesso_Iride", e);
			logger.dump(vo);
		}
	}

	/*
	 * 
	 * (non-Javadoc)
	 * 
	 * @see
	 * it.csi.pbandi.pbservizit.pbandisrv.interfacecsi.profilazione.ProfilazioneSrv#
	 * tracciaControlloCookie(java.lang.Long, java.lang.String, java.lang.String,
	 * java.lang.String)
	 */
	public void tracciaControlloCookie(Long idUtente, String identitaDigitale, String msg)
			throws CSIException, SystemException, UnrecoverableException, UtenteException {
		UtenteException e = new UtenteException(msg);
		throw e;

	}

	public Boolean existBandolineaWithRegolaForBeneficiario(Long idUtente, String identitaDigitale,
			Long idSoggettoBeneficiario, String codiceRegola)
			throws CSIException, SystemException, UnrecoverableException, UtenteException {
		boolean result = false;

		String[] nameParameter = { "idUtente", "identitaDigitale", "idSoggettoBeneficiario", "codiceRegola" };
		ValidatorInput.verifyNullValue(nameParameter, idUtente, identitaDigitale, idSoggettoBeneficiario, codiceRegola);

		BeneficiarioProgettoBandolineaVO filter = new BeneficiarioProgettoBandolineaVO();
		filter.setIdSoggetto(NumberUtil.toBigDecimal(idSoggettoBeneficiario));
		List<BeneficiarioProgettoBandolineaVO> bandolinea = genericDAO.findListWhere(filter);
		if (!ObjectUtil.isEmpty(bandolinea)) {
			HashMap<String, String> map = new HashMap<String, String>();

			map.put("progrBandoLineaIntervento", "progrBandoLineaIntervento");
			List<RegolaAssociataBandoLineaVO> bandolineaFilter = beanUtil.transformList(bandolinea,
					RegolaAssociataBandoLineaVO.class, map);
			RegolaAssociataBandoLineaVO regolaFilter = new RegolaAssociataBandoLineaVO();
			regolaFilter.setDescBreveRegola(codiceRegola);

			AndCondition<RegolaAssociataBandoLineaVO> andCondition = new AndCondition<RegolaAssociataBandoLineaVO>(
					Condition.filterBy(bandolineaFilter), Condition.filterBy(regolaFilter));

			List<RegolaAssociataBandoLineaVO> bandolineaRegola = genericDAO.findListWhere(andCondition);
			if (!ObjectUtil.isEmpty(bandolineaRegola))
				result = true;
		}
		return result;

	}

	public ConsensoInvioComunicazioneDTO findConsensoInvioComunicazione(Long idUtente, String identitaDigitale)
			throws CSIException, SystemException, UnrecoverableException, UtenteException {

		String[] nameParameter = { "idUtente", "identitaDigitale" };
		ValidatorInput.verifyNullValue(nameParameter, idUtente, identitaDigitale);

		PbandiTUtenteVO utente = new PbandiTUtenteVO();
		utente.setIdUtente(new BigDecimal(idUtente));
		utente = genericDAO.findSingleWhere(utente);

		ConsensoInvioComunicazioneDTO dto = new ConsensoInvioComunicazioneDTO();
		dto.setEmailConsenso(utente.getEmailConsenso());
		dto.setFlagConsensoMail(utente.getFlagConsensoMail());
		return dto;
	}

	public Boolean salvaConsensoInvioComunicazione(Long idUtente, String identitaDigitale,
			ConsensoInvioComunicazioneDTO consensoInvioComunicazioneDTO)
			throws CSIException, SystemException, UnrecoverableException, UtenteException {
		try {

			String[] nameParameter = { "idUtente", "identitaDigitale", "consensoInvioComunicazioneDTO" };
			ValidatorInput.verifyNullValue(nameParameter, idUtente, identitaDigitale, consensoInvioComunicazioneDTO);

			PbandiTUtenteVO utente = new PbandiTUtenteVO();
			utente.setIdUtente(new BigDecimal(idUtente));
			utente = genericDAO.findSingleWhere(utente);

			utente.setEmailConsenso(consensoInvioComunicazioneDTO.getEmailConsenso());
			utente.setFlagConsensoMail(consensoInvioComunicazioneDTO.getFlagConsensoMail());
			utente.setDtAggiornamento(DateUtil.getSysdate());
			genericDAO.update(utente);

		} catch (Exception e) {
			logger.error("salvaConsensoInvioComunicazione(): ERRORE: " + e);
			return false;
		}
		return true;
	}

	public Boolean verifyCurrentUserForUC(String codiceRuolo, String useCase) {
		PbandiDPermessoVO pbandiDPermessoVO = new PbandiDPermessoVO();
		pbandiDPermessoVO.setDescBrevePermesso(useCase);
		List<PbandiDPermessoVO> permessi = genericDAO.findListWhere(pbandiDPermessoVO);
		if (permessi != null && permessi.size() > 0) {
			pbandiDPermessoVO = permessi.get(0);
			PbandiDTipoAnagraficaVO pbandiDTipoAnagraficaVO = new PbandiDTipoAnagraficaVO();
			pbandiDTipoAnagraficaVO.setDescBreveTipoAnagrafica(codiceRuolo);
			List<PbandiDTipoAnagraficaVO> tipi = genericDAO.findListWhere(pbandiDTipoAnagraficaVO);
			if (tipi != null && tipi.size() > 0) {
				pbandiDTipoAnagraficaVO = tipi.get(0);
				PbandiRPermessoTipoAnagrafVO pbandiRPermessoTipoAnagrafVO = new PbandiRPermessoTipoAnagrafVO();
				pbandiRPermessoTipoAnagrafVO.setIdPermesso(pbandiDPermessoVO.getIdPermesso());
				pbandiRPermessoTipoAnagrafVO.setIdTipoAnagrafica(pbandiDTipoAnagraficaVO.getIdTipoAnagrafica());
				pbandiRPermessoTipoAnagrafVO = genericDAO.findSingleOrNoneWhere(pbandiRPermessoTipoAnagrafVO);
				if (pbandiRPermessoTipoAnagrafVO != null) {
					return Boolean.TRUE;
				}
			}
		}
		return Boolean.FALSE;
	}
}