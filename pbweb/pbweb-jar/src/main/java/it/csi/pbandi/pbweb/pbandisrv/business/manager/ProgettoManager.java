package it.csi.pbandi.pbweb.pbandisrv.business.manager;

import it.csi.csi.wrapper.CSIException;
import it.csi.csi.wrapper.SystemException;
import it.csi.csi.wrapper.UnrecoverableException;
import it.csi.pbandi.pbweb.pbandisrv.dto.gestionedatigenerali.DatiPiuGreenDTO;
import it.csi.pbandi.pbweb.pbandisrv.dto.manager.BeneficiarioDTO;
import it.csi.pbandi.pbweb.pbandisrv.dto.profilazione.Beneficiario;
import it.csi.pbandi.pbweb.pbandisrv.exception.gestionedatigenerali.GestioneDatiGeneraliException;
import it.csi.pbandi.pbweb.pbandisrv.exception.manager.ContoEconomicoNonTrovatoException;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.dao.GenericDAO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.dao.PbandiDatiGeneraliDAOImpl;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.dao.PbandiDichiarazioneDiSpesaDAOImpl;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.dao.PbandiProfilazioneDAOImpl;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.AcronimoVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.AttivitaPregresseVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.BandoLineaProgettoVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.BeneficiarioProgettoVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.BeneficiarioVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.ErogazioneCausaleModalitaAgevolazioneVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.LineaDiInterventoPadreVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.LiquidazioneModalitaAgevolazioneVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.ProgettoBandoLineaLightVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.ProgettoBandoLineaVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.ProgettoGefoVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.RigoContoEconomicoProgettoCulturaVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.RigoContoEconomicoProgettoVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.SoggettoFinanziatoreProgettoVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.TotaleQuietanzatoValidatoProgettoVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.TotaleRendicontazioneProgettoVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.datigenerali.DatiGeneraliVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.dichiarazionedispesa.ProgettoVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.recupero.RecuperoProgettoVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.revoca.RevocaProgettoVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.util.Condition;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.util.FilterCondition;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.util.NotCondition;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.*;
import it.csi.pbandi.pbweb.pbandisrv.util.validation.ValidatorInput;
import it.csi.pbandi.pbweb.pbandiutil.common.BeanUtil;
import it.csi.pbandi.pbweb.pbandiutil.common.LoggerUtil;
import it.csi.pbandi.pbweb.pbandiutil.common.NumberUtil;
import it.csi.pbandi.pbweb.pbandiutil.common.ObjectUtil;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

public class ProgettoManager {

	@Autowired
	private BeanUtil beanUtil;
	private ContoEconomicoManager contoEconomicoManager;
	
	@Autowired
	private GenericDAO genericDAO;
	
	@Autowired
	private LoggerUtil logger;
	private SedeManager sedeManager;

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

	public ContoEconomicoManager getContoEconomicoManager() {
		return contoEconomicoManager;
	}

	public void setContoEconomicoManager(
			ContoEconomicoManager contoEconomicoManager) {
		this.contoEconomicoManager = contoEconomicoManager;
	}

	public void setSedeManager(SedeManager sedeManager) {
		this.sedeManager = sedeManager;
	}

	public SedeManager getSedeManager() {
		return sedeManager;
	}


	private Boolean isContoMasterPresente(BigDecimal idProgetto) {
		try {
			return (contoEconomicoManager.findContoEconomicoMaster(idProgetto) != null);
		} catch (ContoEconomicoNonTrovatoException e) {
			return false;
		}
	}

	private Boolean isSchedaProgetto(BigDecimal idProgetto) {
		PbandiTCspProgettoVO cspProgettoVO = new PbandiTCspProgettoVO();
		cspProgettoVO.setIdProgetto(idProgetto);

		return genericDAO.where(cspProgettoVO).select().size() >= 1;
	}

	public BigDecimal getIdBeneficiario(final BigDecimal idProgetto) {
		BeneficiarioProgettoVO beneficiarioProgettoVO = new BeneficiarioProgettoVO();
		beneficiarioProgettoVO.setIdProgetto(idProgetto);
		return genericDAO.findSingleWhere(
				Condition.filterBy(beneficiarioProgettoVO).and(
						Condition.validOnly(BeneficiarioProgettoVO.class)))
				.getIdSoggetto();
	}

	public PbandiTDomandaVO getDomandaByProgetto(Long idProgetto) {

		PbandiTProgettoVO pbandiTProgettoVO = getProgetto(idProgetto);

		PbandiTDomandaVO pbandiTDomandaVO = new PbandiTDomandaVO();
		pbandiTDomandaVO.setIdDomanda(pbandiTProgettoVO.getIdDomanda());
		PbandiTDomandaVO pbandiTDomanda = genericDAO
				.findSingleWhere(pbandiTDomandaVO);
		return pbandiTDomanda;
	}

	public void setGenericDAO(GenericDAO genericDAO) {
		this.genericDAO = genericDAO;
	}

	public GenericDAO getGenericDAO() {
		return genericDAO;
	}

	public BandoLineaProgettoVO findBandoLineaForProgetto(Long idProgetto) {
		BandoLineaProgettoVO filter = new BandoLineaProgettoVO();
		filter.setIdProgetto(NumberUtil.toBigDecimal(idProgetto));
		filter.setAscendentOrder("codiceVisualizzato");
		return genericDAO.findSingleWhere(filter);
	}

	@Deprecated
	public long getIdBandoLinea(Long idProgetto) {
		return findBandoLineaForProgetto(idProgetto)
				.getProgrBandoLineaIntervento().longValue();
	}

	public BigDecimal getIdProgettoByIstanzaProcesso(String idIstanzaProcesso) {
		PbandiTProgettoVO progettoVO = new PbandiTProgettoVO();
		progettoVO.setIdIstanzaProcesso(idIstanzaProcesso);

		return genericDAO.findSingleWhere(progettoVO).getIdProgetto();
	}

	public Long getIdBeneficiario(Long idProgetto) {
		return getIdBeneficiario(new BigDecimal(idProgetto)).longValue();
	}

	public PbandiDLineaDiInterventoVO getLineaDiInterventoAsse(
			BigDecimal progrBandoLineaIntervento) {
		String descTipoLinea = it.csi.pbandi.pbweb.pbandisrv.util.Constants.DESC_TIPO_LINEA_ASSE;

		PbandiDLineaDiInterventoVO linea = getLineaInterventoPerTipo(
				progrBandoLineaIntervento, descTipoLinea);
		return linea;
	}

	public PbandiDLineaDiInterventoVO getLineaDiInterventoNormativa(
			BigDecimal progrBandoLineaIntervento) {
		String descTipoLinea = it.csi.pbandi.pbweb.pbandisrv.util.Constants.DESC_TIPO_LINEA_NORMATIVA;

		PbandiDLineaDiInterventoVO linea = getLineaInterventoPerTipo(
				progrBandoLineaIntervento, descTipoLinea);

		return linea;
	}

	public List<LineaDiInterventoPadreVO> getGerarchiaLineeInterventoConEsclusione(
			BigDecimal progrBandoLineaIntervento, String descLineaEsclusa) {
		LineaDiInterventoPadreVO filtroLineeInterventoPerProgrBandoLinea = new LineaDiInterventoPadreVO();
		filtroLineeInterventoPerProgrBandoLinea
				.setProgrBandoLineaIntervento(progrBandoLineaIntervento);

		LineaDiInterventoPadreVO filtroLineaNormativa = new LineaDiInterventoPadreVO();
		filtroLineaNormativa.setDescBreveLinea(descLineaEsclusa);

		List<LineaDiInterventoPadreVO> listLineeIntervento = genericDAO
				.findListWhere(new FilterCondition<LineaDiInterventoPadreVO>(
						filtroLineeInterventoPerProgrBandoLinea)
						.and(new NotCondition<LineaDiInterventoPadreVO>(
								new FilterCondition<LineaDiInterventoPadreVO>(
										filtroLineaNormativa))));
		return listLineeIntervento;
	}

	private PbandiDLineaDiInterventoVO getLineaInterventoPerTipo(
			BigDecimal progrBandoLineaIntervento, String descTipoLinea) {
		LineaDiInterventoPadreVO lineaDiInterventoPadreVO = new LineaDiInterventoPadreVO();
		lineaDiInterventoPadreVO
				.setProgrBandoLineaIntervento(progrBandoLineaIntervento);
		lineaDiInterventoPadreVO.setDescTipoLinea(descTipoLinea);
		List<LineaDiInterventoPadreVO> list = genericDAO.where(
				lineaDiInterventoPadreVO).select();
		if (list.size() != 0) {
			lineaDiInterventoPadreVO = list.get(0);
		}
		return lineaDiInterventoPadreVO;
	}

	/**
	 * Verifico che il flag master del progetto sia uguale a S.
	 * 
	 * @param idProgetto
	 * @return
	 */
	public boolean isCapofila(BigDecimal idProgetto) {
		PbandiTProgettoVO progettoVO = new PbandiTProgettoVO();
		progettoVO.setIdProgetto(idProgetto);
		progettoVO = genericDAO.findSingleWhere(progettoVO);
		if (progettoVO.getFlagProgettoMaster() != null
				&& progettoVO.getFlagProgettoMaster().equalsIgnoreCase("S")) {
			return true;
		} else {
			return false;
		}

	}

	/**
	 * Verifica se il progetto e' gestito da GEFO
	 * 
	 * @param idProgetto
	 * @return
	 */
	public boolean isGestitoGEFO(BigDecimal idProgetto) {
		ProgettoGefoVO filtroVO = new ProgettoGefoVO();
		filtroVO.setIdProgetto(idProgetto);
		filtroVO = genericDAO.findSingleWhere(filtroVO);
		return beanUtil.transform(filtroVO.getFlagGEFO(), Boolean.class)
				.booleanValue();
	}

	/**
	 * Verifica se il cup esiste o no e se esiste verifica che sia unico e
	 * relativo al progetto indicato.
	 * 
	 * @param idProgetto
	 * @param cup
	 * @return restiture true se il cup non esiste o se e' unico e relativo al
	 *         progetto indicato.
	 */
	public boolean isCUPUnivoco(BigDecimal idProgetto, String cup) {
		PbandiTProgettoVO progettoVO = new PbandiTProgettoVO();
		progettoVO.setCup(cup);
		List<PbandiTProgettoVO> progettiSameCup = genericDAO.findListWhere(progettoVO);
		boolean result = false;
		if (progettiSameCup.isEmpty()) {
			result = true;
		} else if (progettiSameCup.size() > 0) {
			/*
			 * Verifico che il cup sia relativo al progetto indicato
			 */
			
			progettoVO.setIdProgetto(idProgetto);
			List<PbandiTProgettoVO> sameProjectSameCup = genericDAO.findListWhere(progettoVO);
			if(sameProjectSameCup.size() == 1){
				return true;
			} else{
				return false;
			}
			
//			if (NumberUtil.compare(progettiSameCup.get(0).getIdProgetto(), idProgetto) == 0)
//				result = true;
//			else
//				result = false;
		} else {
			result = false;
		}

		return result;
	}

	/**
	 * @deprecated
	 * @param idProgetto
	 * @return
	 */
	public Beneficiario[] getBeneficiari(Long idProgetto) {
		List<BeneficiarioVO> beneficiariVO = getPbandiProfilazioneDAO()
				.findBeneficiariProgetto(idProgetto);
		List<Beneficiario> beneficiariDTO = null;
		if (beneficiariVO != null) {
			beneficiariDTO = new ArrayList<Beneficiario>();
			StringBuilder codiciFiscaliInseriti = new StringBuilder("");
			String separator = "$$";
			int count = 1;
			for (BeneficiarioVO max_vo : beneficiariVO) {
				if (!codiciFiscaliInseriti.toString().contains(
						max_vo.getCodiceFiscale() + separator)) {
					for (BeneficiarioVO vo : beneficiariVO) {
						if (vo.getCodiceFiscale().equals(
								max_vo.getCodiceFiscale())) {
							/**
							 * A partita' di codice fiscale, considero il
							 * beneficiario con data di validita' maggiore
							 */
							if (vo.getDataIniziovalidita().after(
									max_vo.getDataIniziovalidita()))
								max_vo = vo;
						}
					}
					Beneficiario dto = new Beneficiario();
					beanUtil.valueCopy(max_vo, dto);
					dto.setId(new Integer(count));
					beneficiariDTO.add(dto);
					count++;
					codiciFiscaliInseriti.append(max_vo.getCodiceFiscale()
							+ separator);
				}

			}
		}
		return beneficiariDTO.toArray(new Beneficiario[] {});
	}

	public Beneficiario getBeneficiario(Long idProgetto) {
		return getBeneficiari(idProgetto)[0];
	}

	public BeneficiarioDTO findBeneficiario(Long idSoggetto, Long idProgetto) {
		// FIXME use genericDAO
		it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.dichiarazionedispesa.BeneficiarioVO beneficiarioVO = getPbandiDichiarazioneDiSpesaDAO()
				.findBeneficiario(idSoggetto, idProgetto);
		return beanUtil.transform(beneficiarioVO, BeneficiarioDTO.class);

	}

	public BeneficiarioProgettoVO findBeneficiario(
			BeneficiarioProgettoVO filterVO) {

		return genericDAO.findSingleWhere(filterVO);
	}

	// public BeneficiarioDTO findBeneficiario(Long idProgetto) {
	//
	// it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.dichiarazionedispesa.BeneficiarioVO
	// beneficiarioVO = getPbandiDichiarazioneDiSpesaDAO()
	// .findBeneficiario(getIdBeneficiario(idProgetto), idProgetto);
	// return beanUtil.transform(beneficiarioVO, BeneficiarioDTO.class);
	//
	// }

	public String getDenominazioneBeneficiario(BigDecimal idProgetto) {
		return getPbandiDatiGeneraliDAO().findDescrizioneBeneficiario(
				idProgetto.longValue(),
				getIdBeneficiario(idProgetto).longValue());
	}

	public String caricaSedeIntervento(Long idProgetto,
			Long idSoggettoBeneficiario) {
		DatiGeneraliVO datiGeneraliVO = pbandiDatiGeneraliDAO
				.findSedeIntervento(idProgetto, idSoggettoBeneficiario,
						SedeManager.getCodiceTipoSedeIntervento());
		return datiGeneraliVO != null ? datiGeneraliVO.getSedeIntervento()
				: null;
	}

	@Deprecated
	@Autowired
	private PbandiProfilazioneDAOImpl pbandiProfilazioneDAO;

	public void setPbandiProfilazioneDAO(
			PbandiProfilazioneDAOImpl pbandiProfilazioneDAO) {
		this.pbandiProfilazioneDAO = pbandiProfilazioneDAO;
	}

	public PbandiProfilazioneDAOImpl getPbandiProfilazioneDAO() {
		return pbandiProfilazioneDAO;
	}

	@Deprecated
	@Autowired
	private PbandiDichiarazioneDiSpesaDAOImpl pbandiDichiarazioneDiSpesaDAO;

	public void setPbandiDichiarazioneDiSpesaDAO(
			PbandiDichiarazioneDiSpesaDAOImpl pbandiDichiarazioneDiSpesaDAO) {
		this.pbandiDichiarazioneDiSpesaDAO = pbandiDichiarazioneDiSpesaDAO;
	}

	public PbandiDichiarazioneDiSpesaDAOImpl getPbandiDichiarazioneDiSpesaDAO() {
		return pbandiDichiarazioneDiSpesaDAO;
	}

	@Deprecated
	@Autowired
	private PbandiDatiGeneraliDAOImpl pbandiDatiGeneraliDAO;

	public void setPbandiDatiGeneraliDAO(
			PbandiDatiGeneraliDAOImpl pbandiDatiGeneraliDAO) {
		this.pbandiDatiGeneraliDAO = pbandiDatiGeneraliDAO;
	}

	public PbandiDatiGeneraliDAOImpl getPbandiDatiGeneraliDAO() {
		return pbandiDatiGeneraliDAO;
	}

	 

	private void ripristinaLegameIstanzaProcessoProgetto(
			String uuidVecchiaIstanzaProcesso,
			PbandiTProgettoVO progettoInMigrazioneVO, Exception e) {
		/*
		 * mi prendo la briga di correggere il progetto rimettendo l'istanza
		 * vecchia perch� la chiamata potrebbe non essere transazionale nel caso
		 * di migrazione multipla
		 */
		progettoInMigrazioneVO.setIdIstanzaProcesso(uuidVecchiaIstanzaProcesso);
		try {
			genericDAO.update(progettoInMigrazioneVO);
		} catch (Exception e1) {
			logger.error(
					"Migrazione fallita, fallito anche il ripristino dell'istanza alla versione precedente, per il processo: "
							+ uuidVecchiaIstanzaProcesso, e);
		}
	}

	public PbandiTProgettoVO getProgetto(Long idProgetto) {
		PbandiTProgettoVO pbandiTProgettoVO = new PbandiTProgettoVO();
		pbandiTProgettoVO.setIdProgetto(new BigDecimal(idProgetto));
		return pbandiTProgettoVO = genericDAO
				.findSingleWhere(pbandiTProgettoVO);
	}

	public String caricaSedeLegale(Long idProgetto) {
		DatiGeneraliVO datiGeneraliVO = pbandiDatiGeneraliDAO
				.findSedeIntervento(idProgetto, getIdBeneficiario(idProgetto),
						SedeManager.getCodiceTipoSedeIntervento());
		return datiGeneraliVO != null ? datiGeneraliVO.getSedeIntervento()
				: null;
	}

	public String caricaAcronimo(Long idProgetto) {

		AcronimoVO acronimoVO = new AcronimoVO();
		acronimoVO.setIdProgetto(new BigDecimal(idProgetto));
		List<AcronimoVO> list = genericDAO.findListWhere(acronimoVO);
		String result = null;
		if (!ObjectUtil.isEmpty(list)) {
			result = list.get(0).getAcronimoProgetto();
		}

		return result;
	}

 

	public it.csi.pbandi.pbweb.pbandisrv.dto.manager.report.ProgettoDTO getProgetto(
			Long idProgetto, java.util.Date dtDichiarazione,
			List<Long> idDocumentiValidi, Long idDicharazione) {
		PbandiTProgettoVO pbandiTProgettoVO = new PbandiTProgettoVO();
		pbandiTProgettoVO.setIdProgetto(new BigDecimal(idProgetto));
		pbandiTProgettoVO = genericDAO.findSingleWhere(pbandiTProgettoVO);
		it.csi.pbandi.pbweb.pbandisrv.dto.manager.report.ProgettoDTO progettoDTO = beanUtil
				.transform(
						pbandiTProgettoVO,
						it.csi.pbandi.pbweb.pbandisrv.dto.manager.report.ProgettoDTO.class);
		progettoDTO
				.setCodiceProgetto(pbandiTProgettoVO.getCodiceVisualizzato());
		ProgettoVO progettoVO = pbandiDichiarazioneDiSpesaDAO.findDatiProgetto(
				idProgetto, dtDichiarazione, idDocumentiValidi, 
				idDicharazione);

		if (progettoVO != null)
			progettoDTO.setTotaleQuietanzato(progettoVO.getTotaleQuietanzato());

		progettoDTO.setSogliaSpesaCalcErogazioni(NumberUtil
				.toDouble(pbandiTProgettoVO.getSogliaSpesaCalcErogazioni()));
		return progettoDTO;
	}

	public ProgettoBandoLineaVO getProgettoBandoLinea(Long idProgetto) {
		ProgettoBandoLineaVO progettoBandoLineaVO = new ProgettoBandoLineaVO();
		progettoBandoLineaVO.setIdProgetto(new BigDecimal(idProgetto));
		return progettoBandoLineaVO = genericDAO
				.findSingleWhere(progettoBandoLineaVO);
	}
	
	/**
	 * Restituisce il totale dei recuperi (tipoRecupeto = RE) per il progetto
	 * @param idProgetto
	 * @return
	 */
	public BigDecimal getTotaleRecuperi(Long idProgetto) {
		BigDecimal totaleRecuperi = getTotaleRecuperi(idProgetto, it.csi.pbandi.pbweb.pbandisrv.util.Constants.COD_FIDEIUSSIONI_TIPO_RECUPERO_RECUPERO);
		return totaleRecuperi;
	}
	

	
	/**
	 * Restituisce il totale dei recuperi di tipo RE raggruppati per modalita' di agevolazione
	 * @param idProgetto
	 * @return
	 */
	public List<RecuperoProgettoVO> getTotaleRecuperiPerModalitaAgevolazione(Long idProgetto) {
		List<RecuperoProgettoVO> result = getTotaleRecuperiPerModalitaAgevolazione(idProgetto, it.csi.pbandi.pbweb.pbandisrv.util.Constants.COD_FIDEIUSSIONI_TIPO_RECUPERO_RECUPERO);
		return result;
	}
	

	
	/**
	 * Restituisce il totale dei prerecuperi (tipoRecupeto = PR) per il progetto
	 * @param idProgetto
	 * @return
	 */
	public BigDecimal getTotalePreRecuperi(Long idProgetto) {
		BigDecimal totalePreRecuperi = getTotaleRecuperi(idProgetto, it.csi.pbandi.pbweb.pbandisrv.util.Constants.COD_FIDEIUSSIONI_TIPO_RECUPERO_PRERECUPERO);
		return totalePreRecuperi;
	}
	
	/**
	 * Restituisce il totale dei recuperi di tipo PR raggruppati per modalita' di agevolazione
	 * @param idProgetto
	 * @return
	 */
	public List<RecuperoProgettoVO>  getTotalePreRecuperiPerModalitaAgevolazione(Long idProgetto) {
		List<RecuperoProgettoVO> result = getTotaleRecuperiPerModalitaAgevolazione(idProgetto, it.csi.pbandi.pbweb.pbandisrv.util.Constants.COD_FIDEIUSSIONI_TIPO_RECUPERO_PRERECUPERO);
		return result;
	}
	
	
	/**
	 * Restituisce il totale delle soppressioni (tipoRecupeto = SO) per il progetto
	 * @param idProgetto
	 * @return
	 */
	public BigDecimal getTotaleSoppressioni(Long idProgetto) {
		BigDecimal totaleSoppressioni = getTotaleRecuperi(idProgetto, it.csi.pbandi.pbweb.pbandisrv.util.Constants.COD_FIDEIUSSIONI_TIPO_RECUPERO_SOPPRESSIONE); 
		return totaleSoppressioni;
	}
	
	/**
	 * Restituisce il totale dei recuperi di tipo SO raggruppati per modalita' di agevolazione
	 * @param idProgetto
	 * @return
	 */
	public List<RecuperoProgettoVO>  getTotaleSoppressioniPerModalitaAgevolazione(Long idProgetto) {
		List<RecuperoProgettoVO> result = getTotaleRecuperiPerModalitaAgevolazione(idProgetto, it.csi.pbandi.pbweb.pbandisrv.util.Constants.COD_FIDEIUSSIONI_TIPO_RECUPERO_SOPPRESSIONE);
		return result;
	}
	
	
	public List<RevocaProgettoVO> getTotaleRevochePerModalitaAgevolazione(Long idProgetto) {
		RevocaProgettoVO filtroVO = new RevocaProgettoVO();
		filtroVO.setIdProgetto(NumberUtil.toBigDecimal(idProgetto));
	    filtroVO.setAscendentOrder("idModalitaAgevolazione");
	    List<RevocaProgettoVO> revoche = genericDAO.findListWhere(filtroVO);
	    List<RevocaProgettoVO> result = new ArrayList<RevocaProgettoVO>();
	    if (!ObjectUtil.isEmpty(revoche)) {
	    	RevocaProgettoVO revocaPrec = null;
	    	BigDecimal totaleRevochePerModalitaAgevolazione = new BigDecimal(0D);
	    	for (RevocaProgettoVO rev : revoche) {
	    		if (revocaPrec == null) {
	    			revocaPrec = new RevocaProgettoVO();
	    			revocaPrec.setCodiceVisualizzato(rev.getCodiceVisualizzato());
	    			revocaPrec.setDescBreveModalitaAgevolaz(rev.getDescBreveModalitaAgevolaz());
	    			revocaPrec.setDescModalitaAgevolazione(rev.getDescModalitaAgevolazione());
	    			revocaPrec.setIdModalitaAgevolazione(rev.getIdModalitaAgevolazione());
	    			revocaPrec.setIdProgetto(rev.getIdProgetto());
	    			revocaPrec.setImporto(rev.getImporto());
	    		}
	    		
	    		if (revocaPrec.getIdModalitaAgevolazione().compareTo(rev.getIdModalitaAgevolazione()) == 0) {
	    			/*
	    			 * Revoca relativa alla stessa modalita' di agevolazione
	    			 */
	    			totaleRevochePerModalitaAgevolazione = NumberUtil.sum(totaleRevochePerModalitaAgevolazione,rev.getImporto());
	    		} else {
	    			/*
	    			 * Cambio di modalita' di agevolazione
	    			 */
	    			revocaPrec.setImporto(totaleRevochePerModalitaAgevolazione);
	    			result.add(revocaPrec);
	    			
	    			revocaPrec = new RevocaProgettoVO();
	    			revocaPrec.setCodiceVisualizzato(rev.getCodiceVisualizzato());
	    			revocaPrec.setDescBreveModalitaAgevolaz(rev.getDescBreveModalitaAgevolaz());
	    			revocaPrec.setDescModalitaAgevolazione(rev.getDescModalitaAgevolazione());
	    			revocaPrec.setIdModalitaAgevolazione(rev.getIdModalitaAgevolazione());
	    			revocaPrec.setIdProgetto(rev.getIdProgetto());
	    			revocaPrec.setImporto(new BigDecimal(0D));
	    			totaleRevochePerModalitaAgevolazione = rev.getImporto();
	    		}
	    	}
	    	/*
	    	 * Inserisco l'ultima revoca
	    	 */
	    	revocaPrec.setImporto(totaleRevochePerModalitaAgevolazione);
	    	result.add(revocaPrec);
	    }
	    return result;
	}
	
	
	/**
	 * Restituisce il totale delle erogazioni per il progetto.
	 * @param idProgetto
	 * @return
	 */
	public List<ErogazioneCausaleModalitaAgevolazioneVO> getTotaleErogazioniPerModalitaAgevolazione(Long idProgetto) {
		ErogazioneCausaleModalitaAgevolazioneVO filtroVO = new ErogazioneCausaleModalitaAgevolazioneVO();
		filtroVO.setIdProgetto(NumberUtil.toBigDecimal(idProgetto));
		filtroVO.setAscendentOrder("idModalitaAgevolazione");
		List<ErogazioneCausaleModalitaAgevolazioneVO> erogazioni = genericDAO.findListWhere(filtroVO);
		List<ErogazioneCausaleModalitaAgevolazioneVO> result = new  ArrayList<ErogazioneCausaleModalitaAgevolazioneVO>();
		if (!ObjectUtil.isEmpty(erogazioni)) {
			 ErogazioneCausaleModalitaAgevolazioneVO erogazionePrec = null;
			 BigDecimal totaleImportoErogazioneModalitaAgev = new BigDecimal(0D);
			 for (ErogazioneCausaleModalitaAgevolazioneVO erog : erogazioni) {
				 if (erogazionePrec == null ) {
					 erogazionePrec = new ErogazioneCausaleModalitaAgevolazioneVO();
					 erogazionePrec.setDescBreveModalitaAgevolaz(erog.getDescBreveModalitaAgevolaz());
					 erogazionePrec.setDescModalitaAgevolazione(erog.getDescModalitaAgevolazione());
					 erogazionePrec.setIdModalitaAgevolazione(erog.getIdModalitaAgevolazione());
					 erogazionePrec.setIdProgetto(erog.getIdProgetto());
					 erogazionePrec.setImportoErogazione(new BigDecimal(0D));
				 }
				 
				 if (erogazionePrec.getIdModalitaAgevolazione().compareTo(erog.getIdModalitaAgevolazione()) == 0) {
					 /*
					  * Stessa modalita di agevolazione
					  */
					 totaleImportoErogazioneModalitaAgev =  NumberUtil.sum(totaleImportoErogazioneModalitaAgev, erog.getImportoErogazione());
				 } else {
					 /*
					  * Cambio di modalita di agevolazione
					  */
					 erogazionePrec.setImportoErogazione(totaleImportoErogazioneModalitaAgev);
					 result.add(erogazionePrec);
					 
					 erogazionePrec = new ErogazioneCausaleModalitaAgevolazioneVO();
					 erogazionePrec.setDescBreveModalitaAgevolaz(erog.getDescBreveModalitaAgevolaz());
					 erogazionePrec.setDescModalitaAgevolazione(erog.getDescBreveModalitaAgevolaz());
					 erogazionePrec.setIdModalitaAgevolazione(erog.getIdModalitaAgevolazione());
					 erogazionePrec.setIdProgetto(erog.getIdProgetto());
					 erogazionePrec.setImportoErogazione(new BigDecimal(0D));
					 totaleImportoErogazioneModalitaAgev = erog.getImportoErogazione();
				 }
			 }
			 /*
			  * Aggiungo l' ultima erogazione
			  */
			 erogazionePrec.setImportoErogazione(totaleImportoErogazioneModalitaAgev);
			 result.add(erogazionePrec);
		 }
		 return result;
	}
	
	
	/**
	 * Restituisce il totale delle liquidazioni per il progetto.
	 * @param idProgetto
	 * @return
	 */
	public List<LiquidazioneModalitaAgevolazioneVO> getTotaleLiquidazioniPerModalitaAgevolazione(Long idProgetto) {
		LiquidazioneModalitaAgevolazioneVO filtroVO = new LiquidazioneModalitaAgevolazioneVO();
		filtroVO.setIdProgetto(NumberUtil.toBigDecimal(idProgetto));
		filtroVO.setAscendentOrder("idModalitaAgevolazione");
		List<LiquidazioneModalitaAgevolazioneVO> liquidazioni = genericDAO.findListWhere(filtroVO);
		List<LiquidazioneModalitaAgevolazioneVO> result = new  ArrayList<LiquidazioneModalitaAgevolazioneVO>();
		if (!ObjectUtil.isEmpty(liquidazioni)) {
			LiquidazioneModalitaAgevolazioneVO liquidazionePrec = null;
			 BigDecimal totaleImportoLiquidazioneModalitaAgev = new BigDecimal(0D);
			 for (LiquidazioneModalitaAgevolazioneVO liq : liquidazioni) {
				 if (liquidazionePrec == null ) {
					 liquidazionePrec = new LiquidazioneModalitaAgevolazioneVO();
					 liquidazionePrec.setDescBreveModalitaAgevolaz(liq.getDescBreveModalitaAgevolaz());
					 liquidazionePrec.setDescModalitaAgevolazione(liq.getDescModalitaAgevolazione());
					 liquidazionePrec.setIdModalitaAgevolazione(liq.getIdModalitaAgevolazione());
					 liquidazionePrec.setIdProgetto(liq.getIdProgetto());
					 liquidazionePrec.setImportoQuietanzato(new BigDecimal(0D));
				 }
				 
				 if (liquidazionePrec.getIdModalitaAgevolazione().compareTo(liq.getIdModalitaAgevolazione()) == 0) {
					 /*
					  * Stessa modalita di agevolazione
					  */
					 totaleImportoLiquidazioneModalitaAgev =  NumberUtil.sum(totaleImportoLiquidazioneModalitaAgev, liq.getImportoQuietanzato());
				 } else {
					 /*
					  * Cambio di modalita di agevolazione
					  */
					 liquidazionePrec.setImportoQuietanzato(totaleImportoLiquidazioneModalitaAgev);
					 result.add(liquidazionePrec);
					 
					 liquidazionePrec = new LiquidazioneModalitaAgevolazioneVO();
					 liquidazionePrec.setDescBreveModalitaAgevolaz(liq.getDescBreveModalitaAgevolaz());
					 liquidazionePrec.setDescModalitaAgevolazione(liq.getDescBreveModalitaAgevolaz());
					 liquidazionePrec.setIdModalitaAgevolazione(liq.getIdModalitaAgevolazione());
					 liquidazionePrec.setIdProgetto(liq.getIdProgetto());
					 liquidazionePrec.setImportoQuietanzato(new BigDecimal(0D));
					 totaleImportoLiquidazioneModalitaAgev = liq.getImportoQuietanzato();
				 }
			 }
			 /*
			  * Aggiungo l' ultima liquidazione
			  */
			 liquidazionePrec.setImportoQuietanzato(totaleImportoLiquidazioneModalitaAgev);
			 result.add(liquidazionePrec);
		 }
		
		return result;
	}
	
	/**
	 * Restituisce il totale rendicontato.
	 * @param idProgetto
	 * @return
	 */
	public BigDecimal getTotaleRendicontazione(Long idProgetto) {
		TotaleRendicontazioneProgettoVO filtroVO = new TotaleRendicontazioneProgettoVO();
		filtroVO.setIdProgetto(NumberUtil.toBigDecimal(idProgetto));
		BigDecimal result = new BigDecimal(0D);
		try {
			TotaleRendicontazioneProgettoVO totale = genericDAO.findSingleOrNoneWhere(filtroVO);
			if (totale != null)
				result = totale.getImportoRendicontazione();
		} catch (Exception e) {
			logger.error("Eccezione gestita.",e);
		}
		return result;
	}
	
	/**
	 * Restituisce il totale quietanzato ed il totale validato.
	 * @param idProgetto
	 * @return
	 */
	public TotaleQuietanzatoValidatoProgettoVO getTotaliQuietanzatoValidato(Long idProgetto) {
		TotaleQuietanzatoValidatoProgettoVO filtroVO = new TotaleQuietanzatoValidatoProgettoVO();
		filtroVO.setIdProgetto(NumberUtil.toBigDecimal(idProgetto));
		TotaleQuietanzatoValidatoProgettoVO result = new TotaleQuietanzatoValidatoProgettoVO();
		result.setImportoQuietanzato(new BigDecimal(0D));
		result.setImportoValidato(new BigDecimal(0D));
		try {
			TotaleQuietanzatoValidatoProgettoVO totale = genericDAO.findSingleOrNoneWhere(filtroVO);
			if (totale != null) {
				result.setImportoQuietanzato(totale.getImportoQuietanzato());
				result.setImportoValidato(totale.getImportoValidato());
			}
		} catch (Exception e) {
			logger.error("Eccezione gestita.",e);
		}
		
		return result;
	}
	
	
	public List<SoggettoFinanziatoreProgettoVO> findSoggettiFinanziatoriPrivati(Long idProgetto) {
		SoggettoFinanziatoreProgettoVO filter = new SoggettoFinanziatoreProgettoVO();
		filter.setIdProgetto(NumberUtil.toBigDecimal(idProgetto));
		
		SoggettoFinanziatoreProgettoVO soggettoPrivato = new SoggettoFinanziatoreProgettoVO();
		soggettoPrivato.setDescBreveSoggFinanziatore(it.csi.pbandi.pbweb.pbandisrv.util.Constants.COD_SOGGETTO_FINANZIATORE_PRIVATO);
		
		SoggettoFinanziatoreProgettoVO soggettoPrivatoFESR = new SoggettoFinanziatoreProgettoVO();
		soggettoPrivatoFESR.setDescBreveSoggFinanziatore(it.csi.pbandi.pbweb.pbandisrv.util.Constants.COD_SOGGETTO_FINANZIATORE_PRIVATO_FESR);
		
		List<SoggettoFinanziatoreProgettoVO> filterSoggettiPrivati = new ArrayList<SoggettoFinanziatoreProgettoVO>();
		filterSoggettiPrivati.add(soggettoPrivato);
		filterSoggettiPrivati.add(soggettoPrivatoFESR);
		
		return genericDAO.findListWhere(Condition.filterBy(filter).and(Condition.filterBy(filterSoggettiPrivati)));
	}
	
	
	public List<SoggettoFinanziatoreProgettoVO> findSoggettiFinanziatoriPubblici(Long idProgetto) {
		SoggettoFinanziatoreProgettoVO filter = new SoggettoFinanziatoreProgettoVO();
		filter.setIdProgetto(NumberUtil.toBigDecimal(idProgetto));
		
		SoggettoFinanziatoreProgettoVO soggettoPrivato = new SoggettoFinanziatoreProgettoVO();
		soggettoPrivato.setDescBreveSoggFinanziatore(it.csi.pbandi.pbweb.pbandisrv.util.Constants.COD_SOGGETTO_FINANZIATORE_PRIVATO);
		
		SoggettoFinanziatoreProgettoVO soggettoPrivatoFESR = new SoggettoFinanziatoreProgettoVO();
		soggettoPrivatoFESR.setDescBreveSoggFinanziatore(it.csi.pbandi.pbweb.pbandisrv.util.Constants.COD_SOGGETTO_FINANZIATORE_PRIVATO_FESR);
		
		List<SoggettoFinanziatoreProgettoVO> filterSoggettiPrivati = new ArrayList<SoggettoFinanziatoreProgettoVO>();
		filterSoggettiPrivati.add(soggettoPrivato);
		filterSoggettiPrivati.add(soggettoPrivatoFESR);
		
		return genericDAO.findListWhere(Condition.filterBy(filter).and(Condition.not(Condition.filterBy(filterSoggettiPrivati))));
	}
	
	/**
	 * Restituisce tutti i righi del conto economico MASTER del progetto
	 * @param idProgetto
	 * @return
	 */
	public  List<RigoContoEconomicoProgettoVO> getRigheContoEconomicoMaster(Long idProgetto) {
		return getRigheContoEconomico(idProgetto, it.csi.pbandi.pbweb.pbandisrv.util.Constants.TIPOLOGIA_CONTO_ECONOMICO_MASTER);
	}
	
	public  List<RigoContoEconomicoProgettoCulturaVO> getRigheContoEconomicoMasterCultura(Long idProgetto) {
		return getRigheContoEconomicoCultura(idProgetto, it.csi.pbandi.pbweb.pbandisrv.util.Constants.TIPOLOGIA_CONTO_ECONOMICO_MASTER);
	}

	/**
	 * Restituisce tutti i righi del conto economico COPY_IST del progetto
	 * @param idProgetto
	 * @return
	 */
	public  List<RigoContoEconomicoProgettoVO> getRigheContoEconomicoCopyIst(Long idProgetto) {
		return getRigheContoEconomico(idProgetto, it.csi.pbandi.pbweb.pbandisrv.util.Constants.TIPOLOGIA_CONTO_ECONOMICO_COPY_IST);
	}
	
	/**
	 * Restituisce tutti i righi del conto economico COPY_BEN del progetto
	 * @param idProgetto
	 * @return
	 */
	public  List<RigoContoEconomicoProgettoVO> getRigheContoEconomicoCopyBen(Long idProgetto) {
		return getRigheContoEconomico(idProgetto, it.csi.pbandi.pbweb.pbandisrv.util.Constants.TIPOLOGIA_CONTO_ECONOMICO_COPY_BEN);
	}
	
	
	
	public List<AttivitaPregresseVO> getAttivitaPregresse(Long idProgetto) {
		List<AttivitaPregresseVO> result = new ArrayList<AttivitaPregresseVO>();
		int idAttivitaPregresse = genericDAO.callProcedure().caricaAttivitaPregresse(NumberUtil.toBigDecimal(idProgetto));
		
		if (idAttivitaPregresse > 0) {
			AttivitaPregresseVO filtro = new AttivitaPregresseVO();
			filtro.setIdProgetto(NumberUtil.toBigDecimal(idProgetto));
			filtro.setIdAttivitaPregresse(new BigDecimal(idAttivitaPregresse));
			
			result = genericDAO.findListWhere(filtro);
		}
		return result;
	}
	
	
	
	
	
	private List<RigoContoEconomicoProgettoVO> getRigheContoEconomico(Long idProgetto, String tipoContoEconomico) {
		List<RigoContoEconomicoProgettoVO> result = new ArrayList<RigoContoEconomicoProgettoVO>();
		RigoContoEconomicoProgettoVO filterVO = new RigoContoEconomicoProgettoVO();
		filterVO.setIdProgetto(NumberUtil.toBigDecimal(idProgetto));
		filterVO.setDescBreveTipologiaContoEco(tipoContoEconomico);
		filterVO.setAscendentOrder("idContoEconomico");		
		result = genericDAO.findListWhere(filterVO);
		return result;		
	}

	private List<RigoContoEconomicoProgettoCulturaVO> getRigheContoEconomicoCultura(Long idProgetto, String tipoContoEconomico) {
		List<RigoContoEconomicoProgettoCulturaVO> result = new ArrayList<RigoContoEconomicoProgettoCulturaVO>();
		RigoContoEconomicoProgettoCulturaVO filterVO = new RigoContoEconomicoProgettoCulturaVO();
		filterVO.setIdProgetto(NumberUtil.toBigDecimal(idProgetto));
		filterVO.setDescBreveTipologiaContoEco(tipoContoEconomico);
		filterVO.setAscendentOrder("idContoEconomico");		
		result = genericDAO.findListWhere(filterVO);
		return result;		
	}
	
	/**
	 * Restituisce il totale del tipo dei recuperi raggruppati per modalita' di agevolazione
	 * @param idProgetto
	 * @param descBreveTipoRecupero: tipo di recupero Recupero(RE) - PreRecupero(PR) - Soppressione(SO)
	 * @return
	 */
	private List<RecuperoProgettoVO> getTotaleRecuperiPerModalitaAgevolazione(Long idProgetto, String descBreveTipoRecupero) {
		RecuperoProgettoVO filtroVO = new RecuperoProgettoVO();
		filtroVO.setIdProgetto(NumberUtil.toBigDecimal(idProgetto));
		filtroVO.setDescBreveTipoRecupero(descBreveTipoRecupero);
		filtroVO.setAscendentOrder("idModalitaAgevolazione");
		List<RecuperoProgettoVO> recuperi = genericDAO.findListWhere(filtroVO);
		List<RecuperoProgettoVO> result = new ArrayList<RecuperoProgettoVO>();
		if (!ObjectUtil.isEmpty(recuperi)) {
			 RecuperoProgettoVO recuperoPrec = null;
			 BigDecimal totaleImportoRecuperoModalitaAgev = new BigDecimal(0D);
			 BigDecimal totaleInteressiRecuperoModalitaAgev = new BigDecimal(0D);
			 for (RecuperoProgettoVO rec : recuperi) {
				 if (recuperoPrec == null) {
					 recuperoPrec = new RecuperoProgettoVO();
					 recuperoPrec.setCodiceVisualizzato(rec.getCodiceVisualizzato());
					 recuperoPrec.setDescBreveModalitaAgevolaz(rec.getDescBreveModalitaAgevolaz());
					 recuperoPrec.setDescModalitaAgevolazione(rec.getDescModalitaAgevolazione());
					 recuperoPrec.setDescBreveTipoRecupero(rec.getDescBreveTipoRecupero());
					 recuperoPrec.setDescTipoRecupero(rec.getDescTipoRecupero());
					 recuperoPrec.setIdModalitaAgevolazione(rec.getIdModalitaAgevolazione());
					 recuperoPrec.setIdProgetto(rec.getIdProgetto());
					 recuperoPrec.setImportoRecupero(new BigDecimal(0D));
					 recuperoPrec.setInteressiRecupero(new BigDecimal(0D));
				 }
				 if (recuperoPrec.getIdModalitaAgevolazione().compareTo(rec.getIdModalitaAgevolazione()) == 0) {
					 /*
					  * Stessa modalita' di agevolazione
					  */
					 totaleImportoRecuperoModalitaAgev = NumberUtil.sum(totaleImportoRecuperoModalitaAgev, rec.getImportoRecupero());
					 totaleInteressiRecuperoModalitaAgev = NumberUtil.sum(totaleInteressiRecuperoModalitaAgev, rec.getInteressiRecupero()); 
				 } else {
					 /*
					  * Cambio di modalita' di agevolazione
					  */
					 recuperoPrec.setImportoRecupero(totaleImportoRecuperoModalitaAgev);
					 recuperoPrec.setInteressiRecupero(totaleInteressiRecuperoModalitaAgev);
					 result.add(recuperoPrec);
					 
					 recuperoPrec = new RecuperoProgettoVO();
					 recuperoPrec.setCodiceVisualizzato(rec.getCodiceVisualizzato());
					 recuperoPrec.setDescBreveModalitaAgevolaz(rec.getDescBreveModalitaAgevolaz());
					 recuperoPrec.setDescModalitaAgevolazione(rec.getDescModalitaAgevolazione());
					 recuperoPrec.setDescBreveTipoRecupero(rec.getDescBreveTipoRecupero());
					 recuperoPrec.setDescTipoRecupero(rec.getDescTipoRecupero());
					 recuperoPrec.setIdModalitaAgevolazione(rec.getIdModalitaAgevolazione());
					 recuperoPrec.setIdProgetto(rec.getIdProgetto());
					 recuperoPrec.setImportoRecupero(new BigDecimal(0D));
					 recuperoPrec.setInteressiRecupero(new BigDecimal(0D));
					 totaleImportoRecuperoModalitaAgev = rec.getImportoRecupero();
					 totaleInteressiRecuperoModalitaAgev =  rec.getInteressiRecupero();
				 }
			 }
			 /*
			  * Inserisco l'ultimo recupero
			  */
			 recuperoPrec.setImportoRecupero(totaleImportoRecuperoModalitaAgev);
			 recuperoPrec.setInteressiRecupero(totaleInteressiRecuperoModalitaAgev);
			 result.add(recuperoPrec);
		 }
		return result;
	}
	
	/**
	 * Restituisce il totale del tipo di recuperi per il progetto
	 * @param idProgetto
	 * @param descBreveTipoRecupero
	 * @return
	 */
	private BigDecimal getTotaleRecuperi(Long idProgetto, String descBreveTipoRecupero){
		RecuperoProgettoVO filtroVO = new RecuperoProgettoVO();
		filtroVO.setIdProgetto(NumberUtil.toBigDecimal(idProgetto));
		filtroVO.setDescBreveTipoRecupero(descBreveTipoRecupero);
		BigDecimal totaleRecuperi = new BigDecimal(0D);
		 List<RecuperoProgettoVO> recuperi = genericDAO.findListWhere(filtroVO);
		 if (!ObjectUtil.isEmpty(recuperi)) {
			 for (RecuperoProgettoVO rec : recuperi) {
				 totaleRecuperi = NumberUtil.sum(totaleRecuperi, rec.getImportoRecupero());
			 }
		 }
		 return totaleRecuperi;
	}
 
	
	public PbandiDLineaDiInterventoVO getLineaDiInterventoNormativa(
			Long idProgetto) {
		ProgettoBandoLineaVO progettoBandoLineaVO = new ProgettoBandoLineaVO();
		progettoBandoLineaVO.setIdProgetto(new BigDecimal(idProgetto));
		progettoBandoLineaVO = genericDAO.findSingleWhere(progettoBandoLineaVO);
		PbandiDLineaDiInterventoVO pbandiDLineaDiInterventoVO=getLineaDiInterventoNormativa(new BigDecimal(progettoBandoLineaVO.getIdBandoLinea()));// idBandoLinea qui � il progrBandoLineaIntervento !!
		logger.info("descBreveLineaNormativa "+pbandiDLineaDiInterventoVO.getDescBreveLinea()+"peridProgetto "+idProgetto+"\n\n");
		return pbandiDLineaDiInterventoVO;
	}
	
	public boolean isProgettoSIF(long idProgetto){
		logger.begin();
		ProgettoBandoLineaLightVO vo = new ProgettoBandoLineaLightVO();
		vo.setIdProgetto(BigDecimal.valueOf(idProgetto));
		vo.setFlag_sif("S");
		int count = genericDAO.count(new FilterCondition<ProgettoBandoLineaLightVO>(vo));
		 
		if(count>0) return true;
		return false;
	}
	

	public Boolean isProgettoContributo(Long idProgetto) {

		PbandiTProgettoVO vo = new PbandiTProgettoVO();
		vo.setIdProgetto(new BigDecimal(idProgetto));
		vo = genericDAO.findSingleOrNoneWhere(vo);
		if (vo != null && vo.getIdProgettoFinanz() != null)
			return Boolean.TRUE;
			
		return Boolean.FALSE;
	}
	
	public BigDecimal findIdProgettoFinanziamento(Long idProgettoContributo){
		PbandiTProgettoVO vo = new PbandiTProgettoVO();
		vo.setIdProgetto(new BigDecimal(idProgettoContributo));
		vo = genericDAO.findSingleOrNoneWhere(vo);
		if (vo != null && vo.getIdProgettoFinanz() != null)
			return vo.getIdProgettoFinanz();
			
		return null;
	}
	
	
	public List caricaListaNotifiche(Long idBandoLineaIntervento, Long idSoggettoBeneficiario, long idProgetti[], String codiceRuolo){
		return pbandiDatiGeneraliDAO.caricaListaNotifiche(idBandoLineaIntervento, idSoggettoBeneficiario, idProgetti, codiceRuolo);
    }

	public Integer aggiornaStatoNotifiche(long elencoIdNotifiche[], String stato){
		return pbandiDatiGeneraliDAO.aggiornaStatoNotifiche(elencoIdNotifiche, stato);
	}

	public Boolean isProgettoAssociatoRegola(Long idProgetto, String codRegola) {
		return pbandiDatiGeneraliDAO.isProgettoAssociatoRegola(idProgetto,  codRegola) ;
	}

	public List getListaIdModalitaAgevolazioni(Long idProgetto) {
		return pbandiDatiGeneraliDAO.getListaIdModalitaAgevolazioni(idProgetto);
	}

	public String getCausaleErogazionePerContoEconomicoErogazione( Long idProgetto,
			Long idModalitaAgevolazione) {
		return pbandiDatiGeneraliDAO.getCausaleErogazionePerContoEconomicoErogazione(idProgetto, idModalitaAgevolazione);
	}

	public String getCausaleErogazionePerContoEconomicoLiquidazione(
			Long idProgetto, Long idModalitaAgevolazione) {
		return pbandiDatiGeneraliDAO.getCausaleErogazionePerContoEconomicoLiquidazione(idProgetto, idModalitaAgevolazione);
	}

	public Boolean checkErogazioneSaldoProgettoContributo(Long idProgetto) {
		return pbandiDatiGeneraliDAO.checkErogazioneSaldoProgettoContributo(idProgetto);
	}

	public Long getIdProgettoAContributo(Long idProgettoAContributo) {
		return pbandiDatiGeneraliDAO.getIdProgettoAContributo(idProgettoAContributo);
	}

	// La logica è giusta, come indicato nel cdu, ma il db no.
	public boolean isProgettoBandoCultura(Long idProgetto){

		BandoLineaProgettoVO bandoLinea = findBandoLineaForProgetto(idProgetto);
		BigDecimal idBandoLinea = bandoLinea.getProgrBandoLineaIntervento();

		PbandiRBandoLineaInterventVO voLineaIntervento = new PbandiRBandoLineaInterventVO();
		voLineaIntervento.setProgrBandoLineaIntervento(idBandoLinea);
		BigDecimal idLineaDiIntervento = genericDAO.findSingleWhere(voLineaIntervento).getIdLineaDiIntervento();

		PbandiDLineaDiInterventoVO vo = new PbandiDLineaDiInterventoVO();
		vo.setIdProcesso(BigDecimal.valueOf(3));
		vo.setIdLineaDiIntervento(idLineaDiIntervento);
		List<PbandiDLineaDiInterventoVO> results = genericDAO.findListWhere(vo);

		return !results.isEmpty();
	}
}
