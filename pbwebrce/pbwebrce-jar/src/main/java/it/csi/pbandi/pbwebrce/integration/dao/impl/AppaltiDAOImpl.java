/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebrce.integration.dao.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.collections.BidiMap;
import org.apache.commons.collections.bidimap.TreeBidiMap;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import it.csi.pbandi.filestorage.business.api.impl.FileApiServiceImpl;
import it.csi.pbandi.filestorage.exceptions.IncorrectUploadPathException;
import it.csi.pbandi.pbservizit.integration.dao.ProfilazioneDAO;
import it.csi.pbandi.pbservizit.pbandisrv.business.gestionedatididominio.GestioneDatiDiDominioBusinessImpl;
import it.csi.pbandi.pbservizit.pbandisrv.dto.gestioneappalti.AppaltoDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.gestioneappalti.EsitoGestioneAppalti;
import it.csi.pbandi.pbservizit.pbandisrv.dto.gestionedatididominio.Decodifica;
import it.csi.pbandi.pbservizit.pbandisrv.dto.gestionedocumentidispesa.MessaggioDTO;
import it.csi.pbandi.pbservizit.pbandisrv.exception.FormalParameterException;
import it.csi.pbandi.pbservizit.pbandisrv.interfacecsi.gestionedatididominio.GestioneDatiDiDominioSrv;
import it.csi.pbandi.pbservizit.pbandisrv.util.validation.ValidatorInput;
import it.csi.pbandi.pbservizit.security.UserInfoSec;
import it.csi.pbandi.pbservizit.util.UseCaseConstants;
import it.csi.pbandi.pbwebrce.dto.CodiceDescrizione;
import it.csi.pbandi.pbwebrce.dto.EsitoProceduraAggiudicazioneDTO;
import it.csi.pbandi.pbwebrce.dto.ProceduraAggiudicazione;
import it.csi.pbandi.pbwebrce.dto.ProceduraAggiudicazioneDTO;
import it.csi.pbandi.pbwebrce.dto.StepAggiudicazione;
import it.csi.pbandi.pbwebrce.exception.RecordNotFoundException;
import it.csi.pbandi.pbwebrce.integration.dao.AppaltiDAO;
import it.csi.pbandi.pbwebrce.pbandisrv.integration.db.dao.GenericDAO;
import it.csi.pbandi.pbwebrce.pbandisrv.integration.db.util.AndCondition;
import it.csi.pbandi.pbwebrce.pbandisrv.integration.db.util.Condition;
import it.csi.pbandi.pbwebrce.pbandisrv.integration.db.util.FilterCondition;
import it.csi.pbandi.pbwebrce.pbandisrv.integration.db.util.LikeContainsCondition;
import it.csi.pbandi.pbwebrce.pbandisrv.integration.db.vo.AppaltoVO;
import it.csi.pbandi.pbwebrce.pbandisrv.integration.db.vo.IterProcAggVO;
import it.csi.pbandi.pbwebrce.pbandisrv.integration.db.vo.PbandiRIterProcAggVO;
import it.csi.pbandi.pbwebrce.pbandisrv.integration.db.vo.PbandiTAppaltoVO;
import it.csi.pbandi.pbwebrce.pbandisrv.integration.db.vo.PbandiTProceduraAggiudicazVO;
import it.csi.pbandi.pbwebrce.pbandisrv.integration.db.vo.PbandiTProgettoVO;
import it.csi.pbandi.pbwebrce.pbandisrv.integration.db.vo.PbandiTRibassoAstaVO;
import it.csi.pbandi.pbwebrce.pbandisrv.integration.db.vo.ProceduraAggiudicazioneProgettoAppaltoVO;
import it.csi.pbandi.pbwebrce.util.BeanUtil;
import it.csi.pbandi.pbwebrce.util.Constants;
import it.csi.pbandi.pbwebrce.util.ErrorMessages;
import it.csi.pbandi.pbwebrce.util.MessageConstants;
import it.csi.pbandi.pbwebrce.util.NumberUtil;

@Component
public class AppaltiDAOImpl extends JdbcDaoSupport implements AppaltiDAO {
	
	private Logger LOG = Logger.getLogger(Constants.COMPONENT_NAME);
	@Autowired
	protected BeanUtil beanUtil;
	
	protected FileApiServiceImpl fileApiServiceImpl;
	@Autowired
	private GenericDAO genericDAO;
	
	@Autowired
	private GestioneDatiDiDominioBusinessImpl gestioneDatiDiDominioBusinessImpl;
	
	@Autowired
	ProfilazioneDAO profilazioneDao;

	public ProfilazioneDAO getProfilazioneDao() {
		return profilazioneDao;
	}

	public void setProfilazioneDao(ProfilazioneDAO profilazioneDao) {
		this.profilazioneDao = profilazioneDao;
	}

	public void setGenericDAO(GenericDAO genericDAO) {
		this.genericDAO = genericDAO;
	}

	public GenericDAO getGenericDAO() {
		return genericDAO;
	}
	
	public AppaltiDAOImpl(DataSource dataSource) {
		setDataSource(dataSource);
		new NamedParameterJdbcTemplate(dataSource);
		try {
			fileApiServiceImpl = new FileApiServiceImpl(Constants.ROOT_FILE_SYSTEM);
		} catch (IncorrectUploadPathException e) {
			e.printStackTrace();
		}
		this.genericDAO = new GenericDAO(dataSource);
	}
	
	private static final BidiMap MAP_FROM_APPALTOVO_TO_APPALTODTO = new TreeBidiMap();
	private static final BidiMap MAP_FROM_APPALTODTO_TO_APPALTOVO;
	static {
		MAP_FROM_APPALTOVO_TO_APPALTODTO.put("idAppalto", "idAppalto");
		MAP_FROM_APPALTOVO_TO_APPALTODTO.put("dtInizioPrevista",
				"dtInizioPrevista");
		MAP_FROM_APPALTOVO_TO_APPALTODTO.put("dtConsegnaLavori",
				"dtConsegnaLavori");
		MAP_FROM_APPALTOVO_TO_APPALTODTO.put("dtFirmaContratto",
				"dtFirmaContratto");
		MAP_FROM_APPALTOVO_TO_APPALTODTO.put("importoContratto",
				"importoContratto");
		MAP_FROM_APPALTOVO_TO_APPALTODTO.put("bilancioPreventivo",
				"bilancioPreventivo");
		MAP_FROM_APPALTOVO_TO_APPALTODTO
				.put("oggettoAppalto", "oggettoAppalto");
		MAP_FROM_APPALTOVO_TO_APPALTODTO.put("idProceduraAggiudicaz",
				"idProceduraAggiudicaz");
		MAP_FROM_APPALTOVO_TO_APPALTODTO.put("codVisualizzatoProcAgg",
				"descProcAgg");
		MAP_FROM_APPALTOVO_TO_APPALTODTO.put("descProcAgg",
				"descrizioneProcAgg");
		MAP_FROM_APPALTOVO_TO_APPALTODTO.put("dtGUUE", "dtGuue");
		MAP_FROM_APPALTOVO_TO_APPALTODTO.put("dtGURI", "dtGuri");
		MAP_FROM_APPALTOVO_TO_APPALTODTO.put("dtQuotNazionali",
				"dtQuotNazionali");
		MAP_FROM_APPALTOVO_TO_APPALTODTO.put("dtWebStazAppaltante",
				"dtWebStazAppaltante");
		MAP_FROM_APPALTOVO_TO_APPALTODTO.put("dtWebOsservatorio",
				"dtWebOsservatorio");
		MAP_FROM_APPALTOVO_TO_APPALTODTO.put("idProgetto", "idProgetto");

		// }L{ PBANDI-1884
		MAP_FROM_APPALTOVO_TO_APPALTODTO
				.put("interventoPisu", "interventoPisu");
		MAP_FROM_APPALTOVO_TO_APPALTODTO.put("impresaAppaltatrice",
				"impresaAppaltatrice");
		MAP_FROM_APPALTOVO_TO_APPALTODTO.put("idTipologiaAppalto",
				"idTipologiaAppalto");

		MAP_FROM_APPALTOVO_TO_APPALTODTO.put("importoRibassoAsta",
				"importoRibassoAsta");
		MAP_FROM_APPALTOVO_TO_APPALTODTO.put("percentualeRibassoAsta",
				"percentualeRibassoAsta");
		
		MAP_FROM_APPALTOVO_TO_APPALTODTO.put("sopraSoglia",
		"sopraSoglia");

		MAP_FROM_APPALTODTO_TO_APPALTOVO = MAP_FROM_APPALTOVO_TO_APPALTODTO
				.inverseBidiMap();
	}
	
	private static final BidiMap MAP_FROM_ITERPROCAGGVO_TO_STEPAGGIUDICAZIONE = new TreeBidiMap();
	private static final BidiMap MAP_FROM_STEPAGGIUDICAZIONE_TO_PBANDIRITERPROCAGGVO;
	static {
		MAP_FROM_ITERPROCAGGVO_TO_STEPAGGIUDICAZIONE.put("dtEffettiva", "dtEffettiva");
		MAP_FROM_ITERPROCAGGVO_TO_STEPAGGIUDICAZIONE.put("dtPrevista", "dtPrevista");
		MAP_FROM_ITERPROCAGGVO_TO_STEPAGGIUDICAZIONE.put("idMotivoScostamento", "idMotivoScostamento");
		MAP_FROM_ITERPROCAGGVO_TO_STEPAGGIUDICAZIONE.put("idStepAggiudicazione", "idStepAggiudicazione");
		MAP_FROM_ITERPROCAGGVO_TO_STEPAGGIUDICAZIONE.put("importoStep", "importo");
		MAP_FROM_ITERPROCAGGVO_TO_STEPAGGIUDICAZIONE.put("descStep", "descStepAggiudicazione");

		MAP_FROM_STEPAGGIUDICAZIONE_TO_PBANDIRITERPROCAGGVO = MAP_FROM_ITERPROCAGGVO_TO_STEPAGGIUDICAZIONE
				.inverseBidiMap();
	}
	
	private static final BidiMap MAP_FROM_STEPAGGIUDICAZIONE_TO_STEPAGGIUDICAZIONEDTO = new TreeBidiMap();
	private static final BidiMap MAP_FROM_STEPAGGIUDICAZIONEDTO_TO_STEPAGGIUDICAZIONE;
	static {
		MAP_FROM_STEPAGGIUDICAZIONE_TO_STEPAGGIUDICAZIONEDTO.put("dtEffettiva", "dtEffettiva");
		MAP_FROM_STEPAGGIUDICAZIONE_TO_STEPAGGIUDICAZIONEDTO.put("dtPrevista", "dtPrevista");
		MAP_FROM_STEPAGGIUDICAZIONE_TO_STEPAGGIUDICAZIONEDTO.put("id", "idStepAggiudicazione");
		MAP_FROM_STEPAGGIUDICAZIONE_TO_STEPAGGIUDICAZIONEDTO.put("idMotivoScostamento", "idMotivoScostamento");
		MAP_FROM_STEPAGGIUDICAZIONE_TO_STEPAGGIUDICAZIONEDTO.put("importoStep", "importo");
		MAP_FROM_STEPAGGIUDICAZIONE_TO_STEPAGGIUDICAZIONEDTO.put("label", "descStepAggiudicazione");
		MAP_FROM_STEPAGGIUDICAZIONEDTO_TO_STEPAGGIUDICAZIONE = MAP_FROM_STEPAGGIUDICAZIONE_TO_STEPAGGIUDICAZIONEDTO
				.inverseBidiMap();
	}
	
	@Override
	@Transactional
	public AppaltoDTO[] findAppalti(Long idUtente, String idIride, AppaltoDTO filtro) throws FormalParameterException {
		String prf = "[AppaltiDAOImpl::findAppalti]";
		LOG.info(prf + " BEGIN");
		try {
			AppaltoDTO[] appalti = null;
			String[] nameParameter = { "idUtente", "idIride", "filtro" };
			ValidatorInput.verifyNullValue(nameParameter, idUtente, idIride, filtro);
			AppaltoVO query = beanUtil.transform(filtro, AppaltoVO.class, MAP_FROM_APPALTODTO_TO_APPALTOVO);
			appalti = beanUtil.transform(genericDAO.where(Condition.filterBy(query)).select(), AppaltoDTO.class, MAP_FROM_APPALTOVO_TO_APPALTODTO);
			LOG.info(prf + " END");
			return appalti;
		} catch(Exception e) {
			LOG.error(e.getMessage());
			throw e;
		}

	}

	@Override
	@Transactional
	public String findCodiceProgetto(Long idProgetto) {
		String prf = "[AppaltiDAOImpl::findCodiceProgetto]";
		LOG.info(prf + " BEGIN");
		try {
			String sql = "SELECT CODICE_VISUALIZZATO FROM PBANDI_T_PROGETTO WHERE ID_PROGETTO = ?";
			Object[] param = new Object[] {idProgetto};
			String codiceProgetto = getJdbcTemplate().queryForObject(sql, param, String.class);
			LOG.info(prf + " END");
			return codiceProgetto;
		} catch (Exception e) {
			LOG.info(prf + " END");
			throw e;
		}
	}
	
	@Override
	@Transactional
	public List<CodiceDescrizione> findTipologieAppalti(UserInfoSec userInfo) throws Exception {
		String prf = "[AppaltiDAOImpl::findTipologieAppalti]";
		LOG.info(prf + " BEGIN");
		try {
			LOG.info(prf + " END");
			return decodificaToCodiceDescrizione(gestioneDatiDiDominioBusinessImpl.findDecodifiche(userInfo.getIdUtente(), userInfo.getIdIride(),
							GestioneDatiDiDominioSrv.TIPOLOGIA_APPALTO));
		} catch(Exception e) {
			LOG.error(e.getMessage());
			throw e;
		}
	}
	
	private ArrayList<CodiceDescrizione> decodificaToCodiceDescrizione(
			Decodifica[] srcArray) {
		String prf = "[AppaltiDAOImpl::decodificaToCodiceDescrizione]";
		LOG.info(prf + " START");
		HashMap<String, String> trsMap = new HashMap<String, String>() {
			{
				put("id", "codice");
				put("descrizione", "descrizione");
			}
		};
		ArrayList<CodiceDescrizione> result = new ArrayList<CodiceDescrizione>();
		for (Decodifica decodifica : srcArray) {
			CodiceDescrizione temp = beanUtil.transform(decodifica, CodiceDescrizione.class, trsMap);
			result.add(temp);
		}
		LOG.info(prf + " END");
		return result;
	}

	@Override
	@Transactional
	public List<CodiceDescrizione> findTipologieProcedureAggiudicazione(UserInfoSec userInfo, Long progBandoLinea) throws Exception {
		String prf = "[AppaltiDAOImpl::findTipologieProcedureAggiudicazione]";
		LOG.info(prf + " BEGIN");
		try {
			return decodificaToCodiceDescrizione(
					gestioneDatiDiDominioBusinessImpl.findTipologieProcedureAggiudicazione(userInfo.getIdUtente(),
					userInfo.getIdIride(), progBandoLinea));
		} catch(Exception e) {
			LOG.error(e.getMessage());
			throw e;
		}finally {
			LOG.info(prf + " END");
		}
	}

	@Override
	@Transactional
	public ProceduraAggiudicazioneDTO[] findAllProcedureAggiudicazione(Long idUtente, String idIride,
			ProceduraAggiudicazioneDTO filtro) throws FormalParameterException {
		String prf = "[AppaltiDAOImpl::findAllProcedureAggiudicazione]";
		LOG.info(prf + " BEGIN");
		try {
			String[] nameParameter = { "idUtente", "identitaDigitale", "filtro" };
			ValidatorInput.verifyNullValue(nameParameter, idUtente, idIride, filtro);
	
			ValidatorInput.verifyAtLeastOneNotNullValue(filtro);
			ProceduraAggiudicazioneProgettoAppaltoVO filtroVO = beanUtil.transform(
					filtro, ProceduraAggiudicazioneProgettoAppaltoVO.class,
					new HashMap<String, String>() {
						{
							put("idTipologiaAggiudicaz",
							"idTipologiaAggiudicaz");
							put("idProceduraAggiudicaz",
							"idProceduraAggiudicaz");
							put("idProgetto", "idProgetto");
						}
					});
	
			ProceduraAggiudicazioneProgettoAppaltoVO likeConditionVO = new ProceduraAggiudicazioneProgettoAppaltoVO();
			likeConditionVO.setCodProcAgg(filtro.getCodProcAgg());
			likeConditionVO.setCigProcAgg(filtro.getCigProcAgg());
			LikeContainsCondition<ProceduraAggiudicazioneProgettoAppaltoVO> likeCondition = new LikeContainsCondition<ProceduraAggiudicazioneProgettoAppaltoVO>(
					likeConditionVO);
	
			FilterCondition<ProceduraAggiudicazioneProgettoAppaltoVO> filterCondition = new FilterCondition<ProceduraAggiudicazioneProgettoAppaltoVO>(
					filtroVO);
			AndCondition<ProceduraAggiudicazioneProgettoAppaltoVO> andCondition = new AndCondition<ProceduraAggiudicazioneProgettoAppaltoVO>(
					filterCondition, likeCondition);
			List<ProceduraAggiudicazioneProgettoAppaltoVO> result = genericDAO
			.findListWhere(andCondition);
			LOG.info(prf + " END");
			return beanUtil.transform(result, ProceduraAggiudicazioneDTO.class);
		} catch(Exception e) {
			LOG.error(e.getMessage());
			throw e;
		}
	}

	@Override
	@Transactional
	public ProceduraAggiudicazioneDTO findDettaglioProceduraAggiudicazione(Long idUtente, String idIride,
			Long idProcedura) throws Exception {
		String prf = "[AppaltiDAOImpl::findDettaglioProceduraAggiudicazione]";
		LOG.info(prf + " BEGIN");
		try {
			String[] nameParameter = { "idUtente", "identitaDigitale", "idProceduraAggiudicazione" };
			ValidatorInput.verifyNullValue(nameParameter, idUtente, idIride, idProcedura);
			
			ProceduraAggiudicazioneProgettoAppaltoVO filtroVO = new ProceduraAggiudicazioneProgettoAppaltoVO();
			filtroVO.setIdProceduraAggiudicaz(beanUtil.transform(idProcedura, BigDecimal.class));
			filtroVO = genericDAO.findSingleWhere(filtroVO);
			
			IterProcAggVO procAggVO = new IterProcAggVO();
			procAggVO.setIdProceduraAggiudicaz(filtroVO.getIdProceduraAggiudicaz());
			procAggVO.setAscendentOrder("codIgrueT48");
			List<IterProcAggVO> iter = genericDAO.findListWhere(procAggVO);
			LOG.debug(prf + " size" + iter.size());
			for(IterProcAggVO s: iter) {
				LOG.debug(prf + " step : " + s.getDescStep() + "DtEffettiva: " + s.getDtEffettiva());				
			}
			ProceduraAggiudicazioneDTO result = beanUtil.transform(filtroVO, ProceduraAggiudicazioneDTO.class);
			
			result.setIter(beanUtil.transform(iter, StepAggiudicazione.class, MAP_FROM_ITERPROCAGGVO_TO_STEPAGGIUDICAZIONE));
			
			for(StepAggiudicazione s: result.getIter()) {
				LOG.debug(prf + " step : " + s.getDescStepAggiudicazione() + "DtEffettiva: " + s.getDtEffettiva());
			}
			
			// Alex: non so perchè ci siano 2 identificativi, ma Angular usa "id", quindi lo valorizzo.
			for(StepAggiudicazione s: result.getIter()) {
				if (s.getIdStepAggiudicazione() != null)
					s.setId(s.getIdStepAggiudicazione().toString());
			}
			
			return result;
		} catch(Exception e) {
			LOG.error(e.getMessage());
			throw e;
		}
		
	}

	@Override
	@Transactional(rollbackFor = {Exception.class})
	public EsitoProceduraAggiudicazioneDTO modificaProceduraAggiudicazione(Long idUtente, String idIride,
			ProceduraAggiudicazioneDTO proceduraAggiudicazione) throws Exception {
			String prf = "[AppaltiDAOImpl::modificaProceduraAggiudicazione]";
			LOG.info(prf + " BEGIN");
			try {
				
				EsitoProceduraAggiudicazioneDTO result = new EsitoProceduraAggiudicazioneDTO();
				 
				String[] nameParameter = { "idUtente", "identitaDigitale",
						"proceduraAggiudicazione" };
				ValidatorInput.verifyNullValue(nameParameter, idUtente,
						idIride, proceduraAggiudicazione);

				result = insertUpdateProceduraAggiudicazione(idUtente,proceduraAggiudicazione);

				return result;
			} catch(Exception e) {
				LOG.error(e.getMessage());
				throw e;
			}
		
	}

	private EsitoProceduraAggiudicazioneDTO insertUpdateProceduraAggiudicazione(Long idUtente,
			ProceduraAggiudicazioneDTO proceduraAggiudicazione) throws Exception {
		String prf = "[AppaltiDAOImpl::insertUpdateProceduraAggiudicazione]";
		LOG.info(prf + " START");
				
		ValidatorInput.verifyNullValue(new String[] { "idProgetto",
				"idTipologiaAggiudicaz", "importo", "descProcAgg" },
				proceduraAggiudicazione.getIdProgetto(),
				proceduraAggiudicazione.getIdTipologiaAggiudicaz(),
				proceduraAggiudicazione.getImporto(),
				proceduraAggiudicazione.getDescProcAgg());
		ValidatorInput.verifyAtLeastOneNotNullValue(new String[] {
				"cigProcAgg", "codProcAgg" },
				proceduraAggiudicazione.getCigProcAgg(),
				proceduraAggiudicazione.getCodProcAgg());
		
		// check univocita' CIG e CPA
		PbandiTProceduraAggiudicazVO vo = new PbandiTProceduraAggiudicazVO();
		vo.setCigProcAgg(proceduraAggiudicazione.getCigProcAgg());
		if (vo.getCigProcAgg() == null)
			vo.setCigProcAgg("");		
		Condition<PbandiTProceduraAggiudicazVO> cigCondition = Condition.filterBy(vo);
		vo = new PbandiTProceduraAggiudicazVO();
		vo.setCodProcAgg(proceduraAggiudicazione.getCodProcAgg());
		if (vo.getCodProcAgg() == null)
			vo.setCodProcAgg("");
		vo.setIdProgetto(beanUtil.transform(proceduraAggiudicazione.getIdProgetto(), BigDecimal.class));
		Condition<PbandiTProceduraAggiudicazVO> cpaCondition = Condition.filterBy(vo);
		if(proceduraAggiudicazione.getIdProceduraAggiudicaz() != null) {
			// devo escludere la procedura stessa dal controllo
			vo = new PbandiTProceduraAggiudicazVO(beanUtil.transform(proceduraAggiudicazione.getIdProceduraAggiudicaz(), BigDecimal.class));
			cigCondition = cigCondition.and(Condition.filterBy(vo).negate());
			cpaCondition = cpaCondition.and(Condition.filterBy(vo).negate());
		}

		EsitoProceduraAggiudicazioneDTO result = new EsitoProceduraAggiudicazioneDTO();
		MessaggioDTO msg = new MessaggioDTO();
		try {
			vo = genericDAO.where(cigCondition).selectFirst();
		} catch (it.csi.pbandi.pbwebrce.pbandisrv.integration.db.exception.RecordNotFoundException e) {
			// tutto OK
			vo = null;
		}		
		
		if (vo != null) {
			result.setEsito(false);
			msg.setMsgKey(ErrorMessages.ERRORE_PROCEDURA_AGGIUDICAZIONE_CIG_GIA_ASSEGNATO + " "+ proceduraAggiudicazione.getIdProgetto());
			msg.setParams(new String[] { genericDAO.findSingleWhere(
					new PbandiTProgettoVO(vo.getIdProgetto()))
					.getCodiceVisualizzato() });
			result.setMsgs(new MessaggioDTO[] { msg });
		} else if (genericDAO.count(cpaCondition) != 0) {
			result.setEsito(false);
			msg.setMsgKey(ErrorMessages.ERRORE_PROCEDURA_AGGIUDICAZIONE_CPA_GIA_ASSEGNATO);
			result.setMsgs(new MessaggioDTO[] { msg });
		} else {
			result.setEsito(true);
			msg.setMsgKey(MessageConstants.MSG_SALVA_SUCCESSO);
			result.setMsgs(new MessaggioDTO[] { msg });
			PbandiTProceduraAggiudicazVO pbandiTProceduraAggiudicazVO = beanUtil.transform(
					proceduraAggiudicazione,
					PbandiTProceduraAggiudicazVO.class);
			pbandiTProceduraAggiudicazVO.setIdUtenteIns(new BigDecimal(idUtente));
			ProceduraAggiudicazioneDTO newProcedura = beanUtil.transform(genericDAO.insertOrUpdateExisting(pbandiTProceduraAggiudicazVO),
					ProceduraAggiudicazioneDTO.class);
			result.setProceduraAggiudicazione(newProcedura);
			
			// pulisci iter aggiudicazione se sono in modifica
			if (newProcedura.getIdProceduraAggiudicaz() != null) {
				PbandiRIterProcAggVO procAggVO = new PbandiRIterProcAggVO();
				procAggVO.setIdProceduraAggiudicaz(new BigDecimal(
						newProcedura.getIdProceduraAggiudicaz()));
				genericDAO.deleteWhere(Condition.filterBy(procAggVO));
			}
			
			// salva iter aggiudicazione se esistente
			final StepAggiudicazione[] newIter = proceduraAggiudicazione.getIter();
			
			// Alex: non so perchè ci siano 2 identificativi, 
			// ma Angular popola "id", mentre "IdStepAggiudicazione" è chiave primaria su db.
			if (newIter != null) {
				for(StepAggiudicazione s: newIter) {
					if (s.getId() != null && s.getId().length() > 0)
						s.setIdStepAggiudicazione(Long.valueOf(s.getId()));
				}
			}
					
			if(newIter != null && newIter.length>0) {
				ArrayList<PbandiRIterProcAggVO> iter = beanUtil.transformToArrayList(newIter, PbandiRIterProcAggVO.class, MAP_FROM_STEPAGGIUDICAZIONE_TO_PBANDIRITERPROCAGGVO);
				for (PbandiRIterProcAggVO iterItem : iter) {
					iterItem.setIdProceduraAggiudicaz(new BigDecimal(newProcedura.getIdProceduraAggiudicaz()));
					iterItem.setIdUtenteIns(new BigDecimal(idUtente));
				}
				
				genericDAO.insert(iter);
			}

		} 	
		LOG.info(prf + " END");
		return result;

	}

	@Override
	@Transactional
	public EsitoGestioneAppalti creaAppalto(Long idUtente, String idIride, AppaltoDTO appaltoDTO) {
		String prf = "[AppaltiDAOImpl::creaAppalto]";
		LOG.info(prf + " BEGIN");
		EsitoGestioneAppalti esito = new EsitoGestioneAppalti();
		try {
			String[] nameParameter = { "idUtente", "idIride", "appaltoDTO" };
			ValidatorInput.verifyNullValue(nameParameter, idUtente,idIride, appaltoDTO);
			esito.setAppalto(insertUpdateAppalto(
					beanUtil.transform(appaltoDTO, PbandiTAppaltoVO.class),
					idUtente));
			esito.setEsito(aggiornaRibassoAstaAppalto(idUtente, esito.getAppalto()
					.getIdProceduraAggiudicaz(), appaltoDTO
					.getImportoRibassoAsta(), appaltoDTO
					.getPercentualeRibassoAsta()));
			if(!esito.getEsito()) {
				logger.warn("Appalto non creato: errore in aggiornaRibassoAstaAppalto");
				esito.setMessage(ErrorMessages.ERRORE_SERVER);
				//esito.setAppalto(appaltoDTO);
			} else {
				esito.setMessage(MessageConstants.MSG_SALVA_SUCCESSO);
				//esito.setAppalto(appaltoDTO);
			}
		} catch (FormalParameterException e) {
			logger.warn("Appalto non creato: " + e.getMessage());
			esito.setEsito(false);
			esito.setMessage(ErrorMessages.ERRORE_CAMPO_OBBLIGATORIO);
			esito.setAppalto(appaltoDTO);
		} catch (Exception e) {
			logger.warn("Appalto non creato: errore in insertUpdateAppalto");
			esito.setEsito(false);
			esito.setMessage(e.getMessage());
			esito.setAppalto(appaltoDTO);
		}
		LOG.info(prf + " END");
		return esito;
	}

	private Boolean aggiornaRibassoAstaAppalto(Long idUtente, Long idProceduraAggiudicaz, Double importoRibassoAsta,
			Double percentualeRibassoAsta) {
		String prf = "[AppaltiDAOImpl::aggiornaRibassoAstaAppalto]";
		LOG.info(prf + " START");
		PbandiTRibassoAstaVO vo = new PbandiTRibassoAstaVO();
		vo.setIdProceduraAggiudicaz(new BigDecimal(idProceduraAggiudicaz));
		vo = genericDAO.findSingleOrNoneWhere(vo);
		if (vo == null) {
			vo = new PbandiTRibassoAstaVO();
			vo.setIdProceduraAggiudicaz(new BigDecimal(idProceduraAggiudicaz));
		}
		vo.setPercentuale(percentualeRibassoAsta != null && NumberUtil.compare(percentualeRibassoAsta, 0D) != 0 ? new BigDecimal(
				percentualeRibassoAsta) : null);
		vo.setImporto(importoRibassoAsta != null && NumberUtil.compare(importoRibassoAsta, 0D) != 0 ? new BigDecimal(
				importoRibassoAsta) : null);
		try {
			if (vo.getImporto() == null && vo.getPercentuale() == null) {
				if (vo.getIdRibassoAsta() != null) {
					LOG.info(prf + " END");
					return genericDAO.delete(vo);
				} else {
					LOG.info(prf + " END");
					return true;
				}
			} else {
				if (vo.getIdRibassoAsta() != null) {
					vo.setIdUtenteAgg(new BigDecimal(idUtente));
					genericDAO.updateNullables(vo);
				} else {
					vo.setIdUtenteIns(new BigDecimal(idUtente));
					genericDAO.insert(vo);
				}
				LOG.info(prf + " END");
				return true;
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			LOG.info(prf + " END");
			return false;
		}
	}

	private AppaltoDTO insertUpdateAppalto(PbandiTAppaltoVO appaltoVO, Long idUtente) throws Exception {
		String prf = "[AppaltiDAOImpl::insertUpdateAppalto]";
		LOG.info(prf + " START");
		String[] nameParameter = { "appalto.oggettoAppalto",
				"appalto.importoContratto", "appalto.dtFirmaContratto",
				"appalto.idProceduraAggiudicaz" };
		ValidatorInput.verifyNullValue(nameParameter,
				appaltoVO.getOggettoAppalto(), appaltoVO.getImportoContratto(),
				appaltoVO.getDtFirmaContratto(),
				appaltoVO.getIdProceduraAggiudicaz());
		try {
			appaltoVO.setIdUtenteIns(new BigDecimal(idUtente));
			appaltoVO = genericDAO.insertOrUpdateExisting(appaltoVO);
		} catch (Exception ex) {
			logger.error("Eccezione durante la insert/update di un appalto", ex);
			throw new Exception(ErrorMessages.ERRORE_SERVER);
		}
		LOG.info(prf + " END");
		return beanUtil.transform(appaltoVO, AppaltoDTO.class,
				MAP_FROM_APPALTOVO_TO_APPALTODTO);
	}

	@Override
	@Transactional(rollbackFor = {Exception.class})
	public EsitoProceduraAggiudicazioneDTO creaProceduraAggiudicazione(Long idUtente, String idIride, ProceduraAggiudicazione p, Long idProgetto) throws FormalParameterException, Exception {
		String prf = "[AppaltiDAOImpl::creaProceduraAggiudicazione]";
		LOG.info(prf + " BEGIN");
		ProceduraAggiudicazioneDTO dto = beanUtil.transform(p, ProceduraAggiudicazioneDTO.class);
				
		//StepAggiudicazione[] steps = beanUtil.transform(p.getIter(),StepAggiudicazione.class, MAP_FROM_STEPAGGIUDICAZIONE_TO_STEPAGGIUDICAZIONEDTO);
		StepAggiudicazione[] steps = p.getIter();
			
		dto.setIdProgetto(idProgetto);
		dto.setIter(steps);
		
		EsitoProceduraAggiudicazioneDTO result = new EsitoProceduraAggiudicazioneDTO();
		 
		String[] nameParameter = { "idUtente", "identitaDigitale",
				"proceduraAggiudicazione" };
		ValidatorInput.verifyNullValue(nameParameter, idUtente, idIride, dto);

		try {
			result = insertUpdateProceduraAggiudicazione(idUtente, dto);
		} catch (Exception e) {
			LOG.error(e.getMessage());
			throw e;
		}
		LOG.info(prf + " END");
		return result;

	}
	
	@Override
	@Transactional
	public EsitoGestioneAppalti eliminaAppalto(Long idAppalto) {
		String prf = "[AppaltiDAOImpl::eliminaAppalto]";
		LOG.info(prf + " BEGIN");
		LOG.info(prf + " idAppalto = "+idAppalto);
		
		EsitoGestioneAppalti esito = new EsitoGestioneAppalti();
		try {
			String[] nameParameter = { "idAppalto" };
			ValidatorInput.verifyNullValue(nameParameter, idAppalto);
			
			esito = rimuoviAppalto(idAppalto);
			
		} catch (FormalParameterException e) {
			logger.error("Appalto non creato: " + e.getMessage());
			esito.setEsito(false);
			esito.setMessage(ErrorMessages.ERRORE_CAMPO_OBBLIGATORIO);
		} catch (Exception e) {
			logger.error("Appalto non creato: errore in "+prf);
			esito.setEsito(false);
			esito.setMessage(e.getMessage());
		}
		LOG.info(prf + " END");
		return esito;
	}
	
	private EsitoGestioneAppalti rimuoviAppalto(Long idAppalto) throws Exception {
		String prf = "[AppaltiDAOImpl::rimuoviAppalto]";
		LOG.info(prf + " START");
		String[] nameParameter = { "idAppalto" };
		ValidatorInput.verifyNullValue(nameParameter, idAppalto);
		
		PbandiTAppaltoVO query = new PbandiTAppaltoVO();
		EsitoGestioneAppalti esito = new EsitoGestioneAppalti();
		try {
			query.setIdAppalto(beanUtil.transform(idAppalto, BigDecimal.class));
			esito.setEsito(genericDAO.where(Condition.filterBy(query)).delete());
			esito.setMessage("Cancellazione avvenuta con successo");
		} catch (Exception ex) {
			logger.error("Eccezione durante la cancellazione di un appalto", ex);
			throw new Exception(ErrorMessages.ERRORE_SERVER);
		}
		LOG.info(prf + " END");
		return esito;
	}

	@Override
	public Boolean eliminazioneAppaltoAbilitata(Long idSoggetto, Long idUtente, String codiceRuolo) {
		String prf = "[AppaltiDAOImpl::eliminazioneAppaltoAbilitata]";
		LOG.info(prf + " BEGIN");
		LOG.info(prf + " idSoggetto = "+idSoggetto+"; idUtente = "+idUtente+"; codiceRuolo = "+codiceRuolo);
		
		Boolean esito = new Boolean(false);
		try {
			String[] nameParameter = { "idSoggetto", "idUtente", "codiceRuolo" };
			ValidatorInput.verifyNullValue(nameParameter, idSoggetto, idUtente, codiceRuolo);
						
			esito = profilazioneDao.hasPermesso(idSoggetto, idUtente, codiceRuolo, "OPEAPP004");
			LOG.info(prf+" esito = "+esito);
			
		} catch (FormalParameterException e) {
			logger.error("Parametro mancante: " + e.getMessage());
		} catch (Exception e) {
			logger.error("Errore in "+prf);
		}
		LOG.info(prf + " END");
		return esito;
	}
	
}
