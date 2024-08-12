package it.csi.pbandi.pbweb.pbandisrv.business.manager;

import it.csi.pbandi.pbweb.pbandisrv.dto.dichiarazionedispesa.VoceDiCostoDTO;
import it.csi.pbandi.pbweb.pbandisrv.dto.manager.ProgettoDTO;
import it.csi.pbandi.pbweb.pbandisrv.dto.manager.BeneficiarioDTO;
import it.csi.pbandi.pbweb.pbandisrv.dto.manager.DecodificaDTO;
import it.csi.pbandi.pbweb.pbandisrv.dto.manager.RappresentanteLegaleDTO;
import it.csi.pbandi.pbweb.pbandisrv.dto.manager.report.DichiarazioneDiSpesaDTO;
import it.csi.pbandi.pbweb.pbandisrv.dto.manager.report.EnteAppartenenzaDTO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.dao.GenericDAO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.dao.GenericDAO.Pair;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.exception.RecordNotFoundException;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.dao.PbandiDichiarazioneDiSpesaDAOImpl;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.BeneficiarioProgettoVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.DichiarazioneDiSpesaConTipoVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.EnteDiAppartenenzaVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.VoceDiSpesaDichiarazioneVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.VoceDiSpesaImportiDichiarazioneVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.dichiarazionedispesa.DocumentoContabileVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.dichiarazionedispesa.ProgettoVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.util.AndCondition;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.util.Condition;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.util.FilterCondition;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.util.LessThanOrEqualCondition;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.util.NullCondition;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.util.OrCondition;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.GenericVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiDTipoDichiarazSpesaVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiRPagamentoDichSpesaVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiTDichiarazioneSpesaVO;
import it.csi.pbandi.pbweb.pbandisrv.interfacecsi.gestionedatididominio.GestioneDatiDiDominioSrv;
import it.csi.pbandi.pbweb.pbandiutil.common.BeanUtil;
import it.csi.pbandi.pbweb.pbandiutil.common.Constants;
import it.csi.pbandi.pbweb.pbandiutil.common.DateUtil;
import it.csi.pbandi.pbweb.pbandiutil.common.LoggerUtil;
import it.csi.pbandi.pbweb.pbandiutil.common.NumberUtil;
import it.csi.pbandi.pbweb.pbandiutil.common.ObjectUtil;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

public class DichiarazioneDiSpesaManager {
	private static final String ID_DICHIARAZIONE_SPESA = "idDichiarazioneSpesa";
	private static final String DT_CHIUSURA_VALIDAZIONE = "dtChiusuraValidazione";

	@Autowired
	private BeanUtil beanUtil;
	@Autowired
	private DecodificheManager decodificheManager;
	@Autowired
	private EnteManager enteManager;
	@Autowired
	private GenericDAO genericDAO;
	@Autowired
	private LoggerUtil logger;
	@Autowired
	private PbandiDichiarazioneDiSpesaDAOImpl pbandiDichiarazioneDiSpesaDAO;
	@Autowired
	private ProgettoManager progettoManager;
	@Autowired
	private RappresentanteLegaleManager rappresentanteLegaleManager;

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
	
	public void setEnteManager(EnteManager enteManager) {
		this.enteManager = enteManager;
	}

	public EnteManager getEnteManager() {
		return enteManager;
	}

	public void setRappresentanteLegaleManager(
			RappresentanteLegaleManager rappresentanteLegaleManager) {
		this.rappresentanteLegaleManager = rappresentanteLegaleManager;
	}

	public RappresentanteLegaleManager getRappresentanteLegaleManager() {
		return rappresentanteLegaleManager;
	}
	public void setPbandiDichiarazioneDiSpesaDAO(
			PbandiDichiarazioneDiSpesaDAOImpl pbandiDichiarazioneDiSpesaDAO) {
		this.pbandiDichiarazioneDiSpesaDAO = pbandiDichiarazioneDiSpesaDAO;
	}

	public PbandiDichiarazioneDiSpesaDAOImpl getPbandiDichiarazioneDiSpesaDAO() {
		return pbandiDichiarazioneDiSpesaDAO;
	}

	public void setProgettoManager(ProgettoManager progettoManager) {
		this.progettoManager = progettoManager;
	}

	public ProgettoManager getProgettoManager() {
		return progettoManager;
	}


		
	public String[] getDichiarazioniDaValidare(ProgettoDTO progetto) {
		// }L{ 1.8 : le "dichiarazioni da validare" sono solo INTERMEDIE
		List<PbandiTDichiarazioneSpesaVO> pbandiTDichiarazioneSpesaVOs = findDichiarazioni(
				progetto, Constants.DESC_BREVE_TIPO_DICHIARAZIONE_INTERMEDIA);
		String[] s = beanUtil.extractValues(pbandiTDichiarazioneSpesaVOs,
				"idDichiarazioneSpesa", String.class).toArray(
				new String[pbandiTDichiarazioneSpesaVOs.size()]);

		return s;
	}

	private List<PbandiTDichiarazioneSpesaVO> findDichiarazioni(
			ProgettoDTO progetto, String... descBreveStatiProgetto) {
		List<PbandiTDichiarazioneSpesaVO> statiDichiarazione = new ArrayList<PbandiTDichiarazioneSpesaVO>();
		for (String descBreveStatoProgetto : descBreveStatiProgetto) {
			PbandiTDichiarazioneSpesaVO temp = new PbandiTDichiarazioneSpesaVO();
			DecodificaDTO decodifica = decodificheManager.findDecodifica(
					GestioneDatiDiDominioSrv.TIPO_DICHIARAZ_SPESA,
					descBreveStatoProgetto);
			if (decodifica.getId() != null) {
				temp
						.setIdTipoDichiarazSpesa(new BigDecimal(decodifica
								.getId()));
				statiDichiarazione.add(temp);
			}
		}
		PbandiTDichiarazioneSpesaVO dichiarazioneVO = beanUtil.transform(
				progetto, PbandiTDichiarazioneSpesaVO.class);

		List<PbandiTDichiarazioneSpesaVO> pbandiTDichiarazioneSpesaVOs = genericDAO
				.where(
						new AndCondition<PbandiTDichiarazioneSpesaVO>(
								new FilterCondition<PbandiTDichiarazioneSpesaVO>(
										dichiarazioneVO),
								new FilterCondition<PbandiTDichiarazioneSpesaVO>(
										statiDichiarazione),
								new NullCondition<PbandiTDichiarazioneSpesaVO>(
										PbandiTDichiarazioneSpesaVO.class,
										DT_CHIUSURA_VALIDAZIONE))).select();
		return pbandiTDichiarazioneSpesaVOs;
	}

	public Boolean isDichiarazioniDaValidare(ProgettoDTO progetto) {
		return new Boolean(getDichiarazioniDaValidare(progetto).length > 0);
	}

	public Boolean isDichiarazioniDaValidareById(Long  idProgetto) {
		ProgettoDTO progetto = new ProgettoDTO();
		progetto.setIdProgetto(idProgetto);
		return isDichiarazioniDaValidare(progetto);
	}

	public String[] getDichiarazioniFinaliDaValidare(ProgettoDTO progetto) {
		// }L{ 1.8 : le "ulteriori dichiarazioni da validare" sono solo
		// INTEGRATIVA e
		// FINALE CON COMUNICAZIONE
		List<PbandiTDichiarazioneSpesaVO> pbandiTDichiarazioneSpesaVOs = findDichiarazioni(
				progetto,
				Constants.DESC_BREVE_TIPO_DICHIARAZIONE_INTEGRATIVA,
				Constants.DESC_BREVE_TIPO_DICHIARAZIONE_FINALE_CON_COMUNICAZIONE);

		/*
		// e devono avere dei pagamenti associati
		List<PbandiRPagamentoDichSpesaVO> dichiarazioni = beanUtil
				.transformList(pbandiTDichiarazioneSpesaVOs,
						PbandiRPagamentoDichSpesaVO.class);
		dichiarazioni = genericDAO.findListWhere(dichiarazioni);

		String[] s = beanUtil.extractValues(dichiarazioni,
				"idDichiarazioneSpesa", String.class).toArray(
				new String[dichiarazioni.size()]);
		*/
		String[] s = beanUtil.extractValues(pbandiTDichiarazioneSpesaVOs,
				"idDichiarazioneSpesa", String.class).toArray(
				new String[pbandiTDichiarazioneSpesaVOs.size()]);
		
		return s;
	}

	public Boolean isDichiarazioniFinaliDaValidare(ProgettoDTO progetto) {
		String[] dichiarazioniFinaliDaValidare = getDichiarazioniFinaliDaValidare(progetto);
		return new Boolean(dichiarazioniFinaliDaValidare.length > 0);
	}

	public Condition<PbandiTDichiarazioneSpesaVO> validazioneChiusaCondition(
			PbandiTDichiarazioneSpesaVO filtroVO) {
		return Condition.filterBy(filtroVO).and(
				Condition.not(Condition.isFieldNull(
						PbandiTDichiarazioneSpesaVO.class,
						DT_CHIUSURA_VALIDAZIONE)));
	}

	public boolean isDichiarazioneChiusa(Long idDichiarazione) {
		PbandiTDichiarazioneSpesaVO filtroVO = new PbandiTDichiarazioneSpesaVO();
		filtroVO.setIdDichiarazioneSpesa(beanUtil.transform(idDichiarazione,
				BigDecimal.class));
		return genericDAO.where(validazioneChiusaCondition(filtroVO)).count() == 1;
	}

	/**
	 * individua l'UNICO idDichiarazione relativo a questo idPagamento
	 * dell'ultima (temporalmente) dichiarazione di spesa chiusa legata al
	 * pagamento
	 * 
	 * @param idPagamento
	 * @return idDichiarazione relativo
	 */
	public BigDecimal getIdUltimaDichiarazioneValidataPerIlPagamento(
			BigDecimal idPagamento) {
		if (idPagamento == null) {
			logger.warn("idPagamento non valorizzato");
			return null;
		}

		PbandiRPagamentoDichSpesaVO pbandiRPagamentoDichSpesaVO = new PbandiRPagamentoDichSpesaVO();
		pbandiRPagamentoDichSpesaVO.setIdPagamento(idPagamento);

		PbandiTDichiarazioneSpesaVO pbandiTDichiarazioneSpesaVO = new PbandiTDichiarazioneSpesaVO();
		// FIXME ordinamento non supportato
		// pbandiTDichiarazioneSpesaVO.setDescendentOrder(DT_CHIUSURA_VALIDAZIONE,
		// ID_DICHIARAZIONE_SPESA);
		pbandiTDichiarazioneSpesaVO.setDescendentOrder(DT_CHIUSURA_VALIDAZIONE);

		List<Pair<GenericVO, PbandiRPagamentoDichSpesaVO, PbandiTDichiarazioneSpesaVO>> list = genericDAO
				.join(
						Condition.filterBy(pbandiRPagamentoDichSpesaVO),
						Condition.filterBy(pbandiTDichiarazioneSpesaVO).and(
								Condition.isFieldNull(
										PbandiTDichiarazioneSpesaVO.class,
										DT_CHIUSURA_VALIDAZIONE).negate())).by(
						ID_DICHIARAZIONE_SPESA).select();

		BigDecimal result = null;

		if (list.size() > 0) {
			result = list.get(0).getSecond().getIdDichiarazioneSpesa();
		} else {
			logger
					.warn("Nessuna dichiarazione di spesa trovata per il pagamento: "
							+ idPagamento);
		}

		logger.debug("idPagamento " + idPagamento + " -> idDichiarazione "
				+ result);

		return result;
	}

	public boolean hasDichiarazioneFinale(Long idUtente,
			String identitaDigitale, Long idProgetto) {
			PbandiTDichiarazioneSpesaVO pbandiTDichiarazioneSpesaVO = new PbandiTDichiarazioneSpesaVO();
			pbandiTDichiarazioneSpesaVO
					.setIdProgetto(new BigDecimal(idProgetto));
			BigDecimal idTipoDichFinale = getDecodificheManager()
					.decodeDescBreve(PbandiDTipoDichiarazSpesaVO.class,
							Constants.DESC_BREVE_TIPO_DICHIARAZIONE_FINALE);
			pbandiTDichiarazioneSpesaVO
					.setIdTipoDichiarazSpesa(idTipoDichFinale);
			List<PbandiTDichiarazioneSpesaVO> list = genericDAO
					.findListWhere(pbandiTDichiarazioneSpesaVO);
			if (!ObjectUtil.isEmpty(list))
				return true;

			return false;
	 
	}

	public boolean hasDichiarazioneFinaleOFinaleComunicazione(Long idUtente,
			String identitaDigitale, Long idProgetto) {
	 
			PbandiTDichiarazioneSpesaVO pbandiTDichiarazioneSpesaVOFinale = new PbandiTDichiarazioneSpesaVO();
			pbandiTDichiarazioneSpesaVOFinale.setIdProgetto(new BigDecimal(
					idProgetto));
			BigDecimal idTipoDichFinale = getDecodificheManager()
					.decodeDescBreveStorico(PbandiDTipoDichiarazSpesaVO.class,
							Constants.DESC_BREVE_TIPO_DICHIARAZIONE_FINALE);
			pbandiTDichiarazioneSpesaVOFinale
					.setIdTipoDichiarazSpesa(idTipoDichFinale);

			BigDecimal idTipoDichFinaleConComunicazione = getDecodificheManager()
					.decodeDescBreveStorico(
							PbandiDTipoDichiarazSpesaVO.class,
							Constants.DESC_BREVE_TIPO_DICHIARAZIONE_FINALE_CON_COMUNICAZIONE);
			PbandiTDichiarazioneSpesaVO pbandiTDichiarazioneSpesaVOFinaleCom = new PbandiTDichiarazioneSpesaVO();
			pbandiTDichiarazioneSpesaVOFinaleCom.setIdProgetto(new BigDecimal(
					idProgetto));
			pbandiTDichiarazioneSpesaVOFinaleCom
					.setIdTipoDichiarazSpesa(idTipoDichFinaleConComunicazione);

			OrCondition<PbandiTDichiarazioneSpesaVO> condizioneComplessiva = new OrCondition<PbandiTDichiarazioneSpesaVO>(
					new FilterCondition<PbandiTDichiarazioneSpesaVO>(
							pbandiTDichiarazioneSpesaVOFinale),
					new FilterCondition<PbandiTDichiarazioneSpesaVO>(
							pbandiTDichiarazioneSpesaVOFinaleCom));

			List<PbandiTDichiarazioneSpesaVO> list = genericDAO
					.findListWhere(condizioneComplessiva);
			if (!ObjectUtil.isEmpty(list))
				return true;

			return false;
	 
	}

	public DichiarazioneDiSpesaDTO findDichiarazioneForReport(
			java.lang.Long idProgetto, 
			List<Long> idDocumentiValidi,
			Long idRappresentanteLegale)
			throws Exception {
	 
			/**
			 * Gli stati dei pagamenti sono I (INSERITO) e T(RESPINTO)
			 */
			/**
			 * Carico i dati del legale rappresentante
			 */
			RappresentanteLegaleDTO rappresentante = rappresentanteLegaleManager.findRappresentanteLegale(idProgetto,idRappresentanteLegale);

			/**
			 * Carico i dati del progetto
			 */

			DichiarazioneDiSpesaConTipoVO dichiarazioneVO = getDichiarazioneFinale(idProgetto);
			/**
			 * Carico i dati dell' ente di appartenenza/competenza
			 */
			EnteDiAppartenenzaVO enteVO = enteManager.findEnteDestinatario(idProgetto);
			
			Map<String,String> propsEnteFromVOtoDTO = new HashMap<String, String>();
			propsEnteFromVOtoDTO.put("descEnte", "descEnte");
			propsEnteFromVOtoDTO.put("indirizzo", "indirizzo");
			propsEnteFromVOtoDTO.put("cap", "cap");
			propsEnteFromVOtoDTO.put("descComune", "comune");
			propsEnteFromVOtoDTO.put("siglaProvincia", "siglaProvincia");
			
			
			EnteAppartenenzaDTO ente = beanUtil.transform(enteVO, EnteAppartenenzaDTO.class, propsEnteFromVOtoDTO);
			
			
			/**
			 * Carico i dati del beneficiario
			 */

			BeneficiarioDTO beneficiario = findBeneficiario(idProgetto);

			it.csi.pbandi.pbweb.pbandisrv.dto.manager.report.ProgettoDTO progettoDTO =
				progettoManager.getProgetto(idProgetto,dichiarazioneVO.getDtDichiarazione(),
						idDocumentiValidi,NumberUtil.toLong(dichiarazioneVO.getIdDichiarazioneSpesa()));

		

			DichiarazioneDiSpesaDTO dichiarazione = new DichiarazioneDiSpesaDTO();

			dichiarazione.setBeneficiario(beneficiario);
			dichiarazione.setDataAl(dichiarazioneVO.getDtDichiarazione());
			dichiarazione.setEnte(ente);
			// JIRA 1874 : indirizzo corretto SEDE LEGALE
			//dichiarazione.setIndirizzo(rappresentante.getIndirizzoResidenza());
			//indirizzo ERRATO: RAPP LEGALE 
			String indirizzoSedeLegale=progettoManager.caricaSedeLegale(idProgetto);
			dichiarazione.setIndirizzo(indirizzoSedeLegale);
			if (dichiarazioneVO.getIdDichiarazioneSpesa() != null)
				dichiarazione.setIdDichiarazione(dichiarazioneVO
						.getIdDichiarazioneSpesa().longValue());
			else
				dichiarazione.setIdDichiarazione(0l);
			dichiarazione.setProgetto(progettoDTO);
			dichiarazione.setRappresentanteLegale(rappresentante);

			return dichiarazione;
	}

	

	public DichiarazioneDiSpesaConTipoVO getDichiarazioneFinale(
			java.lang.Long idProgetto) {
		DichiarazioneDiSpesaConTipoVO dichiarazioneVO = new DichiarazioneDiSpesaConTipoVO();
		dichiarazioneVO.setIdProgetto(new BigDecimal(idProgetto));
		dichiarazioneVO
				.setIdTipoDichiarazSpesa((new BigDecimal(
						decodificheManager
								.findDecodifica(
										GestioneDatiDiDominioSrv.TIPO_DICHIARAZ_SPESA,
										it.csi.pbandi.pbweb.pbandiutil.common.Constants.DESC_BREVE_TIPO_DICHIARAZIONE_FINALE)
								.getId())));
		dichiarazioneVO.setDescendentOrder("dtDichiarazione");
		try {
			dichiarazioneVO = genericDAO.where(dichiarazioneVO).selectFirst();
			return dichiarazioneVO;
		} catch (RecordNotFoundException e) {
			logger.warn("Nessuna dichiarazione di tipo FINALE trovata per il progetto "
					+ idProgetto);
		}

		return null;
	}

	public DichiarazioneDiSpesaConTipoVO getDichiarazioneFinaleConComunicazione(
			java.lang.Long idProgetto) {
		DichiarazioneDiSpesaConTipoVO dichiarazioneVO = new DichiarazioneDiSpesaConTipoVO();
		dichiarazioneVO.setIdProgetto(new BigDecimal(idProgetto));
		dichiarazioneVO
				.setIdTipoDichiarazSpesa((new BigDecimal(
						decodificheManager
								.findDecodifica(
										GestioneDatiDiDominioSrv.TIPO_DICHIARAZ_SPESA,
										it.csi.pbandi.pbweb.pbandiutil.common.Constants.DESC_BREVE_TIPO_DICHIARAZIONE_FINALE_CON_COMUNICAZIONE)
								.getId())));
		dichiarazioneVO.setDescendentOrder("dtDichiarazione");
		try {
			dichiarazioneVO = genericDAO.where(dichiarazioneVO).selectFirst();
			return dichiarazioneVO;
		} catch (RecordNotFoundException e) {
			logger.warn("Nessuna dichiarazione di tipo FINALE CON COMUNICAZIONE trovata per il progetto "
					+ idProgetto);
		}

		return null;
	}



	private BeneficiarioDTO findBeneficiario(Long idProgetto) {
		 
			BeneficiarioProgettoVO filtro = new BeneficiarioProgettoVO();
			filtro.setIdProgetto(beanUtil.transform(idProgetto, BigDecimal.class));
			return beanUtil.transform(getProgettoManager().findBeneficiario(
					filtro), BeneficiarioDTO.class);

		 
	}

	


	public List<DocumentoContabileVO> findDocumentiContabili(Long idProgetto,
			Date al, List<Long> idDocumentiValidi,
			 Long idDichiarazione) {
			//JIRA-1978
			List<DocumentoContabileVO> documentiContabiliVO = pbandiDichiarazioneDiSpesaDAO
					.findDocumentiContabili(idProgetto, al, idDocumentiValidi,
							idDichiarazione);
			return documentiContabiliVO;

		 
	}

	public ProgettoVO findDatiProgetto(Long idProgetto, Date al,
			List<Long> idDocumentiValidi,   Long idDichiarazione) {
		 
			ProgettoVO progettoVO = pbandiDichiarazioneDiSpesaDAO
					.findDatiProgetto(idProgetto, al, idDocumentiValidi,
							  idDichiarazione);
			return progettoVO;

		 
	}

	public Long getIdDichiarazionePerValidazioneFinale(Long idProgetto) {
		ProgettoDTO progetto = new ProgettoDTO();
		progetto.setIdProgetto(idProgetto);
		String[] dichiarazioni = getDichiarazioniFinaliDaValidare(progetto);
		if (dichiarazioni.length > 1) {
			List<PbandiTDichiarazioneSpesaVO> dichiarazioniVO = beanUtil.beanify(Arrays.asList(dichiarazioni), "idDichiarazioneSpesa",
					PbandiTDichiarazioneSpesaVO.class);
			BigDecimal idDichiarazione = genericDAO.where(Condition.filterByKeyOf(dichiarazioniVO)).orderBy("dtDichiarazione").selectFirst().getIdDichiarazioneSpesa();
			return beanUtil.transform(idDichiarazione, Long.class);
		}
		return dichiarazioni.length == 0 ? null : beanUtil.transform(
				dichiarazioni[0], Long.class);
	}



	public VoceDiCostoDTO[] findVociDiCosto(Long idDichiarazione, Long idProgetto) {

		VoceDiSpesaDichiarazioneVO filtro = new VoceDiSpesaDichiarazioneVO();
		filtro.setIdDichiarazioneSpesa(new BigDecimal( idDichiarazione));
//		filtro.setIdDichiarazioneRend(new BigDecimal( idDichiarazione));
		filtro.setIdProgetto(new BigDecimal( idProgetto));
		long start=System.currentTimeMillis();
		List<VoceDiSpesaDichiarazioneVO> vociVO = genericDAO.findListWhere(Condition.filterBy(filtro));
		logger.info("\n\n\n\n\n\n\n\n\nfindVociDiCosto, query executed in ms -----> "+(System.currentTimeMillis()-start)+"\n\n\n");

		VoceDiCostoDTO[] vociDTO = new VoceDiCostoDTO[vociVO.size()];
		Map<String, String> map = new HashMap<String, String>();

		map.put("descVoceDiSpesa", "descVoceDiSpesa");
		map.put("descVoceDiSpesaPadre", "descVoceDiSpesaPadre");
		map.put("importoAmmessoFinanziamento", "importoAmmessoAFinanziamento");
		map.put("totaleQuietanzato", "importoQuietanzato");
		map.put("totaleValidato", "importoValidato");
		map.put("totaleRendicontato", "importoRendicontato");

		getBeanUtil().valueCopy(vociVO.toArray(), vociDTO, map);

		return vociDTO;
	}
	
	public DichiarazioneDiSpesaConTipoVO getDichiarazione(
			java.lang.Long idProgetto) {
		DichiarazioneDiSpesaConTipoVO dichiarazioneVO = new DichiarazioneDiSpesaConTipoVO();
		dichiarazioneVO.setIdProgetto(new BigDecimal(idProgetto));
		
		dichiarazioneVO.setDescendentOrder("dtDichiarazione");
		try {
			dichiarazioneVO = genericDAO.where(dichiarazioneVO).selectFirst();
			return dichiarazioneVO;
		} catch (RecordNotFoundException e) {
			logger.warn("Nessuna dichiarazione di tipo FINALE trovata per il progetto "
					+ idProgetto);
		}

		return null;
	}

	public DocumentoContabileVO findRuoloFirmaDocumentiContabili(Long idDocSpesa) {
		DocumentoContabileVO documentiContabiliVO = pbandiDichiarazioneDiSpesaDAO.findRuoloFirmaDocumentiContabili(idDocSpesa);
		return documentiContabiliVO;
	}	
	
}
