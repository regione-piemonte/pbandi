/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.business.impegnobilancio;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import it.csi.csi.wrapper.CSIException;
import it.csi.csi.wrapper.SystemException;
import it.csi.csi.wrapper.UnrecoverableException;
import it.csi.pbandi.pbservizit.pbandisrv.business.BusinessImpl;
import it.csi.pbandi.pbservizit.pbandisrv.business.manager.BilancioManager;
import it.csi.pbandi.pbservizit.pbandisrv.dto.impegnobilancio.BandolineaImpegnoDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.impegnobilancio.EnteCompetenzaDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.impegnobilancio.EsitoOperazioneAggiornaImpegnoDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.impegnobilancio.EsitoOperazioneAssociaImpegnoDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.impegnobilancio.EsitoOperazioneConsultaImpegniDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.impegnobilancio.EsitoOperazioneDisassociaDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.impegnobilancio.ImpegnoDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.impegnobilancio.MessaggioDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.impegnobilancio.ProgettoImpegnoDTO;
import it.csi.pbandi.pbservizit.pbandisrv.exception.impegnobilancio.ImpegnoBilancioException;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.dao.GenericDAO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.exception.RecordNotFoundException;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.BeneficiarioProgettoVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.EnteDiCompetenzaImpegnoVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.EnteDiCompetenzaVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.RegolaAssociataBandoLineaVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.impegnobilancio.AnnoEsercizioBilancioVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.impegnobilancio.BandolineaImpegnoBilVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.impegnobilancio.BandolineaImpegnoSoggettoBilVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.impegnobilancio.ImpegnoBilancioVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.impegnobilancio.ProgettoBilancioPerBeneficiarioVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.impegnobilancio.ProgettoImpegnoBilVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.impegnobilancio.ProgettoImpegnoPerBeneficiarioVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.impegnobilancio.ProgettoImpegnoSoggettoBilVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.impegnobilancio.ProgettoVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.util.AndCondition;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.util.Condition;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.util.LikeContainsCondition;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiCBatchImpegniVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiRBandoLineaInterventVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiREnteCompetenzaSoggVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiRImpegnoBandoLineaVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiRImpegnoProgettoVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiTProgettoVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiWImpegniVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.services.bilancio.BilancioDAO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.services.bilancio.BilancioDAOImpl;
import it.csi.pbandi.pbservizit.pbandisrv.integration.services.bilancio.vo.ImpegnoKeyVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.services.bilancio.vo.ImpegnoVO;
import it.csi.pbandi.pbservizit.pbandisrv.interfacecsi.impegnobilancio.ImpegnoBilancioSrv;
import it.csi.pbandi.pbservizit.pbandisrv.util.Constants;
import it.csi.pbandi.pbservizit.pbandisrv.util.validation.ValidatorInput;
import it.csi.pbandi.pbservizit.pbandiutil.common.DateUtil;
import it.csi.pbandi.pbservizit.pbandiutil.common.NumberUtil;
import it.csi.pbandi.pbservizit.pbandiutil.common.ObjectUtil;
import it.csi.pbandi.pbservizit.pbandiutil.common.RegoleConstants;
import it.csi.pbandi.pbservizit.pbandiutil.common.StringUtil;
import it.csi.pbandi.pbservizit.pbandiutil.common.messages.ErrorConstants;
import it.csi.pbandi.pbservizit.pbandiutil.common.messages.MessaggiConstants;

public class ImpegnoBilancioBusinessImpl extends BusinessImpl implements ImpegnoBilancioSrv {
	
	@Autowired
	private GenericDAO genericDAO;
	@Autowired
	private BilancioManager bilancioManager;
	@Autowired
	private BilancioDAOImpl bilancioDAO;
	@Autowired
	private BilancioDAOImpl bilancioDAOImpl;

	
	public void setGenericDAO(GenericDAO genericDAO) {
		this.genericDAO = genericDAO;
	}

	public GenericDAO getGenericDAO() {
		return genericDAO;
	}

	public BilancioManager getBilancioManager() {
		return bilancioManager;
	}


	public void setBilancioManager(BilancioManager bilancioManager) {
		this.bilancioManager = bilancioManager;
	}


//	public void setBilancioDAO(BilancioDAO bilancioDAO) {
//		this.bilancioDAO = bilancioDAO;
//	}
//
//
//	public BilancioDAO getBilancioDAO() {
//		return bilancioDAO;
//	}

	public EnteCompetenzaDTO[] findDirezioniProvvedimento(Long idUtente,
			String identitaDigitale, Long idSoggetto)
			throws CSIException, SystemException, UnrecoverableException,
			ImpegnoBilancioException {
		logger.begin();
		try {
			String[] nameParameter = { "idUtente", "identitaDigitale",
					"idSoggetto"};

			ValidatorInput.verifyNullValue(nameParameter, idUtente,
					identitaDigitale, idSoggetto);
			/*
			 * 18/01/2012: La combo delle direzioni del provvedimento viene popolata con
			 * tutti gli enti di competenza validi, quindi non si popola piu' con gli enti di
			 * competenza associati all' utente. 
			 */
			//List<SoggettoEnteCompetenzaVO> entiCompetenzaSoggetto = bilancioManager.findEntiCompetenzaSoggetto(idSoggetto);
			
			
			EnteDiCompetenzaVO filterVO = new EnteDiCompetenzaVO();
			filterVO.setAscendentOrder("descEnte");
			List<EnteDiCompetenzaVO> entiCompetenzaVO = genericDAO.findListWhere(Condition.filterBy(filterVO));
			
			
			
			EnteCompetenzaDTO[] entiCompetenza = beanUtil.transform(entiCompetenzaVO, EnteCompetenzaDTO.class);
			return entiCompetenza;
		} finally {
			logger.end();
		}
	}
	
	public EnteCompetenzaDTO[] findDirezioniConRuoloBilancio (Long idUtente,String identitaDigitale)
			throws CSIException, SystemException, UnrecoverableException,
			ImpegnoBilancioException {
		logger.begin();
		try {
			String[] nameParameter = { "idUtente", "identitaDigitale"};
			ValidatorInput.verifyNullValue(nameParameter, idUtente,identitaDigitale);
						
			EnteDiCompetenzaImpegnoVO filterVO = new EnteDiCompetenzaImpegnoVO();
			filterVO.setAscendentOrder("descEnte");
			List<EnteDiCompetenzaImpegnoVO> entiCompetenzaVO = genericDAO.findListWhere(Condition.filterBy(filterVO));
			
			EnteCompetenzaDTO[] entiCompetenza = beanUtil.transform(entiCompetenzaVO, EnteCompetenzaDTO.class);
			return entiCompetenza;
		} finally {
			logger.end();
		}
	}


	public ImpegnoDTO[] findImpegni(Long idUtente, String identitaDigitale,
			ImpegnoDTO filtro, Long idSoggetto) throws CSIException, SystemException,
			UnrecoverableException, ImpegnoBilancioException {
		logger.begin();
		try {
			
			String[] nameParameter = { "idUtente", "identitaDigitale", "filtro","idSoggetto"};

			ValidatorInput.verifyNullValue(nameParameter, idUtente,
			identitaDigitale, filtro,idSoggetto);
			
			ValidatorInput.verifyAtLeastOneNotNullValue(filtro);
			
			/*
			 * Ricerco l'ente di competenza del soggetto
			 */
			List<PbandiREnteCompetenzaSoggVO> entiCompetenzaSoggetto = findEntiAssociatiSoggetto(idSoggetto);
			
			if (ObjectUtil.isEmpty(entiCompetenzaSoggetto)) {
				logger.warn("Attenzione. Per il soggetto "+idSoggetto+" non sono state trovate enti di competenza associate.");
				return null;
			}
			Map<String,String> mapEnteSoggettoToEnteCapitolo = new HashMap<String, String>();
			mapEnteSoggettoToEnteCapitolo.put("idEnteCompetenza", "idEnteCompetenzaCap");
			
			Map<String,String> mapEnteSoggettoToEnteProvvedimento = new HashMap<String, String>();
			mapEnteSoggettoToEnteProvvedimento.put("idEnteCompetenza", "idEnteCompetenzaProvv");
			
			List<ImpegnoBilancioVO> entiCapitoloFilterVO = beanUtil.transformList(entiCompetenzaSoggetto, ImpegnoBilancioVO.class, mapEnteSoggettoToEnteCapitolo);
			List<ImpegnoBilancioVO> entiProvvedimentoFilterVO = beanUtil.transformList(entiCompetenzaSoggetto, ImpegnoBilancioVO.class, mapEnteSoggettoToEnteProvvedimento);
			
			ImpegnoBilancioVO filtroVO = beanUtil.transform(filtro, ImpegnoBilancioVO.class, BilancioDAO.MAP_IMPEGNODTO_TO_IMPEGNOBILANCIO_VO);
			filtroVO.setAscendentOrder("annoPerente","numeroPerente","annoImpegno","numeroImpegno");			
			filtroVO.setRagsoc(null);	// Se valorizzato ragsoc va gestito con like e non case sensitive.
			
			List<ImpegnoBilancioVO> impegni = null;
			
			//TODO PK : filtro.getRagsoc() is nullo ... si rompe
			logger.debug("filtro="+filtro);
			if(filtro!=null)
				logger.debug("filtro.getRagsoc()="+filtro.getRagsoc());
			
			if (filtro == null || (filtro.getRagsoc()==null) || ("".equals(filtro.getRagsoc()) || filtro.getRagsoc().length() <= 0)  ) {
				logger.debug("findImpegni PRE query");
				impegni = genericDAO.findListWhere(Condition.filterBy(filtroVO).and(Condition.filterBy(entiCapitoloFilterVO).or(Condition.filterBy(entiProvvedimentoFilterVO))));	

			} else {
				ImpegnoBilancioVO filtroRagsoc = new ImpegnoBilancioVO();
				filtroRagsoc.setRagsoc(filtro.getRagsoc());
				logger.debug("likeRagSoc pre valorizzazione");
				LikeContainsCondition likeRagSoc = new LikeContainsCondition<ImpegnoBilancioVO>(filtroRagsoc);
	
				impegni = genericDAO.findListWhere(Condition.filterBy(filtroVO).and(likeRagSoc).and(Condition.filterBy(entiCapitoloFilterVO).or(Condition.filterBy(entiProvvedimentoFilterVO))));
			}
			logger.debug("impegni valorizzato="+impegni); 			
			return beanUtil.transform(impegni, ImpegnoDTO.class,BilancioDAO.MAP_IMPEGNOBILANCIOVO_TO_IMPEGNODTO);
			
//			ImpegnoDTO[] arr = null;
//			try {
//				System.out.println("findImpegni PRE TRASFORM");
//				arr = beanUtil.transform(impegni, ImpegnoDTO.class,BilancioDAO.MAP_IMPEGNOBILANCIOVO_TO_IMPEGNODTO);
//				System.out.println("findImpegni POST TRASFORM");
//				logger.debug(" post trasform impegni");
//			}catch(Exception e) {
//				logger.error(" ERROR post trasform impegni");
//				e.printStackTrace();
//			}
//			return arr;
		} finally {
			logger.end();
		}
		
	}

	private List<PbandiREnteCompetenzaSoggVO> findEntiAssociatiSoggetto(
			Long idSoggetto) {
		PbandiREnteCompetenzaSoggVO enteSoggettoVO = new PbandiREnteCompetenzaSoggVO();
		enteSoggettoVO.setIdSoggetto(NumberUtil.toBigDecimal(idSoggetto));
		List<PbandiREnteCompetenzaSoggVO> entiCompetenzaSoggetto = genericDAO.findListWhere(Condition.filterBy(enteSoggettoVO));
		return entiCompetenzaSoggetto;
	}
	
	public ImpegnoDTO dettaglioImpengo(Long idUtente, String identitaDigitale,
			Long idSoggetto, Long idImpegno) throws CSIException,
			SystemException, UnrecoverableException, ImpegnoBilancioException {
		logger.begin();
		try {
			String[] nameParameter = { "idUtente", "identitaDigitale",
					"idSoggetto","idImpegno"};

			ValidatorInput.verifyNullValue(nameParameter, idUtente,
					identitaDigitale, idSoggetto, idImpegno);
			
			ImpegnoBilancioVO filtroVO = new ImpegnoBilancioVO();
			filtroVO.setIdImpegno(NumberUtil.toBigDecimal(idImpegno));
			
			ImpegnoBilancioVO impegnoVO = genericDAO.findSingleWhere(filtroVO);
			
			return beanUtil.transform(impegnoVO, ImpegnoDTO.class, BilancioDAO.MAP_IMPEGNOBILANCIOVO_TO_IMPEGNODTO);
			
		} finally {
			logger.end();
		}
	}
	
	

	public BandolineaImpegnoDTO[] findBandolineaAssociati(Long idUtente,
			String identitaDigitale, Long idSoggetto, Long[] listaIdImpegno)
			throws CSIException, SystemException, UnrecoverableException,
			ImpegnoBilancioException {
		logger.begin();
		try {
			String[] nameParameter = { "idUtente", "identitaDigitale",
					"idSoggetto"};

			ValidatorInput.verifyNullValue(nameParameter, idUtente,
					identitaDigitale, idSoggetto);
			
			List<BandolineaImpegnoDTO> result = new ArrayList<BandolineaImpegnoDTO>();
			
			/*
			 * Ricerco l'ente di competenza del soggetto
			 */
			List<PbandiREnteCompetenzaSoggVO> entiCompetenzaSoggetto = findEntiAssociatiSoggetto(idSoggetto);
			
			if (ObjectUtil.isEmpty(entiCompetenzaSoggetto)) {
				logger.warn("Attenzione. Per il soggetto "+idSoggetto+" non sono state trovate enti di competenza associate.");
				return null;
			}
			Map<String,String> mapEnteSoggettoToEnteCapitolo = new HashMap<String, String>();
			mapEnteSoggettoToEnteCapitolo.put("idEnteCompetenza", "idEnteCap");
			
			Map<String,String> mapEnteSoggettoToEnteProvvedimento = new HashMap<String, String>();
			mapEnteSoggettoToEnteProvvedimento.put("idEnteCompetenza", "idEnteProvv");
			
			List<BandolineaImpegnoSoggettoBilVO> entiCapitoloFilterVO = beanUtil.transformList(entiCompetenzaSoggetto, BandolineaImpegnoSoggettoBilVO.class, mapEnteSoggettoToEnteCapitolo);
			List<BandolineaImpegnoSoggettoBilVO> entiProvvedimentoFilterVO = beanUtil.transformList(entiCompetenzaSoggetto, BandolineaImpegnoSoggettoBilVO.class, mapEnteSoggettoToEnteProvvedimento);
			
			List<PbandiRImpegnoBandoLineaVO> listBandolineaAssociatiImpegno = getBandolineaAssociati(listaIdImpegno);
			
			// Crea una lista di ProgrBandoLinea eliminando i doppioni.
			ArrayList<BigDecimal> listaProgrBandoLinea = new ArrayList<BigDecimal>();
			for (PbandiRImpegnoBandoLineaVO p : listBandolineaAssociatiImpegno) {
				listaProgrBandoLinea.add(p.getProgrBandoLineaIntervento());
			}
			List<BigDecimal> listalistaProgrBandoLineaUnici = new ArrayList<BigDecimal>(new LinkedHashSet<BigDecimal>(listaProgrBandoLinea));
	
			if (!ObjectUtil.isEmpty(listalistaProgrBandoLineaUnici)) {
				/*
				 * Per ogni bandolinea associato all' impegno ricerco tutti
				 * gli impegni associati al bandolinea.
				 */
				for (BigDecimal bando : listalistaProgrBandoLineaUnici){
					BandolineaImpegnoSoggettoBilVO bandolineaFilterVO = new BandolineaImpegnoSoggettoBilVO();
					bandolineaFilterVO.setProgrBandolineaIntervento(bando);
					bandolineaFilterVO.setAscendentOrder("nomeBandolinea");
					
					List<BandolineaImpegnoSoggettoBilVO> listImpegniBandolinea = genericDAO.findListWhereDistinct(Condition.filterBy(bandolineaFilterVO).and(Condition.filterBy(entiCapitoloFilterVO).or(Condition.filterBy(entiProvvedimentoFilterVO))), BandolineaImpegnoSoggettoBilVO.class);
					if (!ObjectUtil.isEmpty(listImpegniBandolinea)) {
						BigDecimal progressivoBandolinea = null;
						BandolineaImpegnoDTO bandolinea = null;
						long countImpegni = 0;
						List<ImpegnoDTO> impegniBandolinea = new ArrayList<ImpegnoDTO>();
						BigDecimal importoTotaleImpegniBandolinea = new BigDecimal(0);
						for (BandolineaImpegnoSoggettoBilVO vo : listImpegniBandolinea) {
							if (progressivoBandolinea == null)
								progressivoBandolinea = vo.getProgrBandolineaIntervento();
							if (progressivoBandolinea.compareTo(vo.getProgrBandolineaIntervento()) == 0) {
								if (bandolinea == null)
									bandolinea = beanUtil.transform(vo, BandolineaImpegnoDTO.class);
								countImpegni++;
								ImpegnoDTO impegno = beanUtil.transform(vo, ImpegnoDTO.class, BilancioDAO.MAP_BANDOLINEA_IMPEGNOVO_TO_IMPEGNODTO);
								impegniBandolinea.add(impegno);
								importoTotaleImpegniBandolinea = NumberUtil.sum(importoTotaleImpegniBandolinea, NumberUtil.createScaledBigDecimal(vo.getImportoAttualeImpegno()));
							} else {
								/*
								 * Cambio di bandolinea
								 */
								/*
								 * Aggiorno i dati del bandolinea precedente
								 */
								bandolinea.setNumTotImpegniBandolinea(countImpegni);
								bandolinea.setImpegni(beanUtil.transform(impegniBandolinea, ImpegnoDTO.class));
								bandolinea.setImpTotImpegniBandolinea(NumberUtil.toDouble(importoTotaleImpegniBandolinea));
								result.add(bandolinea);
								
								/*
								 * Reset delle variabili
								 */
								countImpegni = 1;
								bandolinea = beanUtil.transform(vo, BandolineaImpegnoDTO.class);
								progressivoBandolinea = vo.getProgrBandolineaIntervento();
								impegniBandolinea = new ArrayList<ImpegnoDTO>();
								importoTotaleImpegniBandolinea = new BigDecimal(0);
								
								ImpegnoDTO impegno = beanUtil.transform(vo, ImpegnoDTO.class, BilancioDAO.MAP_BANDOLINEA_IMPEGNOVO_TO_IMPEGNODTO);
								impegniBandolinea.add(impegno);
								importoTotaleImpegniBandolinea = NumberUtil.sum(importoTotaleImpegniBandolinea, NumberUtil.createScaledBigDecimal(vo.getImportoAttualeImpegno()));
							}
						}
						/*
						 * Inserisco l'ultimo bandolinea
						 */
						bandolinea.setNumTotImpegniBandolinea(countImpegni);
						bandolinea.setImpegni(beanUtil.transform(impegniBandolinea, ImpegnoDTO.class));
						bandolinea.setImpTotImpegniBandolinea(NumberUtil.toDouble(importoTotaleImpegniBandolinea));
						result.add(bandolinea);
					}
				}
			}

			return beanUtil.transform(result, BandolineaImpegnoDTO.class);
			
		} finally {
			logger.end();
		}
	}
	
	public BandolineaImpegnoDTO[] findBandolineaDaAssociare(Long idUtente,
			String identitaDigitale, Long[] idImpegno, Long idEnteCompetenza, Long progrBandolinea)
			throws CSIException, SystemException, UnrecoverableException,
			ImpegnoBilancioException {
		logger.begin();
		try {
			String[] nameParameter = { "idUtente", "identitaDigitale", "idEnteCompetenza"};

			ValidatorInput.verifyNullValue(nameParameter, idUtente,
					identitaDigitale, idEnteCompetenza);
			
			List<BandolineaImpegnoDTO> result = new ArrayList<BandolineaImpegnoDTO>();
			
			List<BandolineaImpegnoBilVO> listBandolineaImpegni = null;			
			if (idImpegno != null && idImpegno.length > 0) {
				listBandolineaImpegni = getBandolineaDaAssociare(idEnteCompetenza, idImpegno, progrBandolinea);
			} else {
				listBandolineaImpegni = getBandolineaDaAssociareSenzaImpegni(idEnteCompetenza, progrBandolinea);
			}
			
			if (!ObjectUtil.isEmpty(listBandolineaImpegni)) {
				BigDecimal progressivoBandolinea = null;
				BandolineaImpegnoDTO bandolinea = null;
				long countImpegni = 0;
				List<ImpegnoDTO> impegniBandolinea = new ArrayList<ImpegnoDTO>();
				BigDecimal importoTotaleImpegniBandolinea = new BigDecimal(0);
				for (BandolineaImpegnoBilVO bl : listBandolineaImpegni) {
					if (progressivoBandolinea == null)
						progressivoBandolinea = bl.getProgrBandolineaIntervento();
					
					if (progressivoBandolinea.compareTo(bl.getProgrBandolineaIntervento()) == 0) {
						if (bandolinea == null)
							bandolinea = beanUtil.transform(bl, BandolineaImpegnoDTO.class);
						/*
						 * idImpegno = -1 indica che non esiste impegno (nella query gli impegni sono in 
						 * outer join con i bandolinea)
						 */
						if (bl.getIdImpegno().compareTo(ImpegnoBilancioSrv.ID_IMPEGNO_FITTIZIO) != 0) {
							countImpegni++;
							ImpegnoDTO impegno = beanUtil.transform(bl, ImpegnoDTO.class, BilancioDAO.MAP_BANDOLINEA_IMPEGNOVO_TO_IMPEGNODTO);
							impegniBandolinea.add(impegno);
							importoTotaleImpegniBandolinea = NumberUtil.sum(importoTotaleImpegniBandolinea, NumberUtil.createScaledBigDecimal(bl.getImportoAttualeImpegno()));
						}
					} else {
						/*
						 * Cambio di bandolinea
						 */
						bandolinea.setNumTotImpegniBandolinea(countImpegni);
						bandolinea.setImpegni(beanUtil.transform(impegniBandolinea, ImpegnoDTO.class));
						bandolinea.setImpTotImpegniBandolinea(NumberUtil.toDouble(importoTotaleImpegniBandolinea));
						result.add(bandolinea);
						
						/*
						 * Reset delle variabili
						 */
						countImpegni = 0;
						bandolinea = beanUtil.transform(bl, BandolineaImpegnoDTO.class);
						progressivoBandolinea = bl.getProgrBandolineaIntervento();
						impegniBandolinea = new ArrayList<ImpegnoDTO>();
						importoTotaleImpegniBandolinea = new BigDecimal(0);
						/*
						 * idImpegno = -1 indica che non esiste impegno (nella query gli impegni sono in 
						 * outer join con i bandolinea)
						 */
						if (bl.getIdImpegno().compareTo(ImpegnoBilancioSrv.ID_IMPEGNO_FITTIZIO) != 0) {
							countImpegni++;
							ImpegnoDTO impegno = beanUtil.transform(bl, ImpegnoDTO.class, BilancioDAO.MAP_BANDOLINEA_IMPEGNOVO_TO_IMPEGNODTO);
							impegniBandolinea.add(impegno);
							importoTotaleImpegniBandolinea = NumberUtil.sum(importoTotaleImpegniBandolinea, NumberUtil.createScaledBigDecimal(bl.getImportoAttualeImpegno()));
						}
					}
				}
				/*
				 * Inserisco l'ultimo bandolinea
				 */
				bandolinea.setNumTotImpegniBandolinea(countImpegni);
				bandolinea.setImpegni(beanUtil.transform(impegniBandolinea, ImpegnoDTO.class));
				bandolinea.setImpTotImpegniBandolinea(NumberUtil.toDouble(importoTotaleImpegniBandolinea));
				result.add(bandolinea);
			}
					
					
			return beanUtil.transform(result, BandolineaImpegnoDTO.class);
			
		} finally {
			logger.end();
		}
	}

	private List<RegolaAssociataBandoLineaVO> getBandolineaAssociatiRegola(String codiceRegola) {
		List<RegolaAssociataBandoLineaVO> listBandolineaAssociatiRegola37 = null;
		try {
			listBandolineaAssociatiRegola37 = regolaManager.findBandolineaAssociatiRegola(codiceRegola);
		} catch (Exception e) {
			logger.error("Eccezione gestita: errore durante la ricerca dei bandolinea associati alla regola "+RegoleConstants.BR37_ATTIVITA_LIQUIDAZIONE_CONTRIBUTO,e);
		}
		return listBandolineaAssociatiRegola37;
	}
	
	public BandolineaImpegnoDTO[] findListBandolinea(Long idUtente,
			String identitaDigitale, Long[] idImpegno, Long idEnteCompetenza)
			throws CSIException, SystemException, UnrecoverableException,
			ImpegnoBilancioException {
		logger.begin();
		try {
			String[] nameParameter = { "idUtente", "identitaDigitale"};
			ValidatorInput.verifyNullValue(nameParameter, idUtente,
					identitaDigitale);
			
			List<BandolineaImpegnoDTO> result = new ArrayList<BandolineaImpegnoDTO>();
			
			List<BandolineaImpegnoBilVO> bandolinea = null;
			if (idImpegno != null && idImpegno.length > 0) {
				bandolinea = getBandolineaDaAssociare(idEnteCompetenza, idImpegno, null);
			} else {
				bandolinea = getBandolineaDaAssociareSenzaImpegni(idEnteCompetenza, null);
			}
			
			if (!ObjectUtil.isEmpty(bandolinea)) {
				BigDecimal progrBandolinea = new BigDecimal(0);
				for (BandolineaImpegnoBilVO bl : bandolinea) {
					if (progrBandolinea.compareTo(bl.getProgrBandolineaIntervento()) != 0) {
						progrBandolinea = bl.getProgrBandolineaIntervento();
						BandolineaImpegnoDTO dto = new BandolineaImpegnoDTO();
						dto.setNomeBandolinea(bl.getNomeBandolinea());
						dto.setProgrBandolineaIntervento(NumberUtil.toLong(bl.getProgrBandolineaIntervento()));
						result.add(dto);
					}
				}
			}
			
			return beanUtil.transform(result, BandolineaImpegnoDTO.class);
			
		} finally {
			logger.end();
		}
		
	}

	private List<PbandiRImpegnoBandoLineaVO> getBandolineaAssociati(Long[] listaIdImpegno) {
		List<PbandiRImpegnoBandoLineaVO> listBandiAssociati = null;
		if (listaIdImpegno == null || listaIdImpegno.length == 0) {
			logger.info("getBandolineaAssociati(): nessun id impegno.");
			listBandiAssociati = genericDAO.findListAll(PbandiRImpegnoBandoLineaVO.class);
		} else {
			ArrayList<PbandiRImpegnoBandoLineaVO> filtro = new ArrayList<PbandiRImpegnoBandoLineaVO>(); 
			for (int i = 0; i < listaIdImpegno.length; i++) {
				PbandiRImpegnoBandoLineaVO vo = new PbandiRImpegnoBandoLineaVO();
				vo.setIdImpegno(new BigDecimal(listaIdImpegno[i]));
				filtro.add(vo);
			}
			listBandiAssociati = genericDAO.findListWhereDistinct(Condition.filterBy(filtro), PbandiRImpegnoBandoLineaVO.class);
		}
		return listBandiAssociati;
	}


	
	public ProgettoImpegnoDTO[] findProgettiAssociati(Long idUtente,
			String identitaDigitale, Long idSoggetto, Long[] listaIdImpegno, Long annoEsercizio)
			throws CSIException, SystemException, UnrecoverableException,
			ImpegnoBilancioException {
		logger.begin();
		try {
			String[] nameParameter = { "idUtente", "identitaDigitale",
					"idSoggetto"};

			ValidatorInput.verifyNullValue(nameParameter, idUtente,
					identitaDigitale, idSoggetto);
			
			List<ProgettoImpegnoDTO> result = new ArrayList<ProgettoImpegnoDTO>();
			
			/*
			 * Ricerco gli enti di competenza associati all' utente
			 */
			List<PbandiREnteCompetenzaSoggVO> entiCompetenzaSoggetto = findEntiAssociatiSoggetto(idSoggetto);
			if (ObjectUtil.isEmpty(entiCompetenzaSoggetto)) {
				logger.warn("Attenzione. Per il soggetto "+idSoggetto+" non sono state trovate enti di competenza associate.");
				return null;
			}
			Map<String,String> mapEnteSoggettoToEnteCapitolo = new HashMap<String, String>();
			mapEnteSoggettoToEnteCapitolo.put("idEnteCompetenza", "idEnteCap");
			
			Map<String,String> mapEnteSoggettoToEnteProvvedimento = new HashMap<String, String>();
			mapEnteSoggettoToEnteProvvedimento.put("idEnteCompetenza", "idEnteProvv");
			
			
			
			List<ProgettoImpegnoSoggettoBilVO> entiCapitoloFilterVO = beanUtil.transformList(entiCompetenzaSoggetto, ProgettoImpegnoSoggettoBilVO.class, mapEnteSoggettoToEnteCapitolo);
			List<ProgettoImpegnoSoggettoBilVO> entiProvvedimentoFilterVO = beanUtil.transformList(entiCompetenzaSoggetto, ProgettoImpegnoSoggettoBilVO.class, mapEnteSoggettoToEnteProvvedimento);
			
			/*
			 * Ricerco i progetti associati all' impegno
			 */
			List<PbandiRImpegnoProgettoVO> listProgettiImpegni = null;
			if (listaIdImpegno == null || listaIdImpegno.length == 0) {
				logger.info("findProgettiAssociati(): nessun id impegno.");
				listProgettiImpegni = genericDAO.findListAll(PbandiRImpegnoProgettoVO.class);
			} else {
				ArrayList<PbandiRImpegnoProgettoVO> filtro = new ArrayList<PbandiRImpegnoProgettoVO>(); 
				for (int i = 0; i < listaIdImpegno.length; i++) {
					PbandiRImpegnoProgettoVO vo = new PbandiRImpegnoProgettoVO();
					vo.setIdImpegno(new BigDecimal(listaIdImpegno[i]));
					filtro.add(vo);
				}
				listProgettiImpegni = genericDAO.findListWhereDistinct(Condition.filterBy(filtro), PbandiRImpegnoProgettoVO.class);
			}	
			
			// Crea una lista di idProgetto eliminando i doppioni.
			ArrayList<BigDecimal> listaIdProgetto = new ArrayList<BigDecimal>();
			for (PbandiRImpegnoProgettoVO p : listProgettiImpegni) {
				listaIdProgetto.add(p.getIdProgetto());
			}
			List<BigDecimal> listaIdProgettoUnici = new ArrayList<BigDecimal>(new LinkedHashSet<BigDecimal>(listaIdProgetto));
	
			
			 if (!ObjectUtil.isEmpty(listaIdProgettoUnici)){
				 for (BigDecimal p : listaIdProgettoUnici) {
					 /*
					  * per ogni progetto ricerco gli impegni associati
					  */
					 
					 ProgettoImpegnoSoggettoBilVO progettoFilterVO = new ProgettoImpegnoSoggettoBilVO();
					 progettoFilterVO.setIdProgetto(p);
					 if (annoEsercizio != null)
						 progettoFilterVO.setAnnoEsercizio(annoEsercizio.toString());
					 progettoFilterVO.setAscendentOrder("codiceVisualizzato");
					 List<ProgettoImpegnoSoggettoBilVO> listImpegniProgetto = genericDAO.findListWhereDistinct(Condition.filterBy(progettoFilterVO).and(Condition.filterBy(entiCapitoloFilterVO).or(Condition.filterBy(entiProvvedimentoFilterVO))), ProgettoImpegnoSoggettoBilVO.class);
					 					
					 
					 if (!ObjectUtil.isEmpty(listImpegniProgetto)){
						 long countImpegni = 0;
						 BigDecimal idPrj = null;
						 ProgettoImpegnoDTO progetto = null;
						 BigDecimal importoTotaleImpegniProgetto = new BigDecimal(0);
						 List<ImpegnoDTO> impegniProgetto = new ArrayList<ImpegnoDTO>();
						 for (ProgettoImpegnoSoggettoBilVO vo : listImpegniProgetto) {
							 if (idPrj == null)
								 idPrj = vo.getIdProgetto();
							 if (idPrj.compareTo(vo.getIdProgetto()) == 0) {
								 /*
								  * Abbiamo un impegno dello stesso
								  * progetto.
								  */
								 if (progetto == null)
									 progetto = beanUtil.transform(vo, ProgettoImpegnoDTO.class);
								 countImpegni++;
								 ImpegnoDTO impegnoProgetto =  beanUtil.transform(vo, ImpegnoDTO.class, BilancioDAO.MAP_PROGETTO_IMPEGNOVO_TO_IMPEGNODTO);
								 
								 impegniProgetto.add(impegnoProgetto);
								 importoTotaleImpegniProgetto = NumberUtil.sum(importoTotaleImpegniProgetto, NumberUtil.createScaledBigDecimal(vo.getImportoAttualeImpegno()));
							 } else {
								 /*
								  * Cambio il progetto
								  */
								 progetto.setNumTotImpegniProgetto(countImpegni);
								 progetto.setImpegni(beanUtil.transform(impegniProgetto, ImpegnoDTO.class));
								 progetto.setImpTotImpegniProgetto(NumberUtil.toDouble(importoTotaleImpegniProgetto));	 
								 
								 result.add(progetto);
								 
								 /*
								  * Reset delle variabili
								  */
								 countImpegni = 1;
								 progetto = beanUtil.transform(vo, ProgettoImpegnoDTO.class);
								 idPrj = vo.getIdProgetto();;
								 impegniProgetto = new ArrayList<ImpegnoDTO>();
								 importoTotaleImpegniProgetto = new BigDecimal(0);
								 ImpegnoDTO impegno = beanUtil.transform(vo, ImpegnoDTO.class, BilancioDAO.MAP_PROGETTO_IMPEGNOVO_TO_IMPEGNODTO);								 														
								 impegniProgetto.add(impegno);
								 importoTotaleImpegniProgetto = NumberUtil.sum(importoTotaleImpegniProgetto, NumberUtil.createScaledBigDecimal(vo.getImportoAttualeImpegno()));
							 }
						 }
						 /*
						  * Inserisco l'ultimo progetto
						  */						 
						 ImpegnoDTO[] lista = beanUtil.transform(impegniProgetto, ImpegnoDTO.class);
						 lista = ordinaNullInFondo(lista);
						 progetto.setImpegni(lista);
						 progetto.setImpTotImpegniProgetto(NumberUtil.toDouble(importoTotaleImpegniProgetto));
						 progetto.setNumTotImpegniProgetto(countImpegni);
						 result.add(progetto);
					 }
				 }
			 }
			 return beanUtil.transform(result, ProgettoImpegnoDTO.class);
		} finally {
			logger.end();
		}	
	}
	
	public ProgettoImpegnoDTO[] findProgettiAssociatiPerBeneficiario(Long idUtente,
			String identitaDigitale, Long idSoggetto, Long[] listaIdImpegno, Long annoEsercizio)
			throws CSIException, SystemException, UnrecoverableException,
			ImpegnoBilancioException {
		logger.begin();
		try {
			String[] nameParameter = { "idUtente", "identitaDigitale",
					"idSoggetto","listaIdImpegno"};

			ValidatorInput.verifyNullValue(nameParameter, idUtente,
					identitaDigitale, idSoggetto, listaIdImpegno);
			
			List<ProgettoImpegnoDTO> result = new ArrayList<ProgettoImpegnoDTO>();
			
			/*
			 * Ricerco gli enti di competenza associati all' utente
			 */
			List<PbandiREnteCompetenzaSoggVO> entiCompetenzaSoggetto = findEntiAssociatiSoggetto(idSoggetto);
			if (ObjectUtil.isEmpty(entiCompetenzaSoggetto)) {
				logger.warn("Attenzione. Per il soggetto "+idSoggetto+" non sono state trovate enti di competenza associate.");
				return null;
			}
			Map<String,String> mapEnteSoggettoToEnteCapitolo = new HashMap<String, String>();
			mapEnteSoggettoToEnteCapitolo.put("idEnteCompetenza", "idEnteCap");
			
			Map<String,String> mapEnteSoggettoToEnteProvvedimento = new HashMap<String, String>();
			mapEnteSoggettoToEnteProvvedimento.put("idEnteCompetenza", "idEnteProvv");
			
			
			List<ProgettoImpegnoSoggettoBilVO> entiCapitoloFilterVO = beanUtil.transformList(entiCompetenzaSoggetto, ProgettoImpegnoSoggettoBilVO.class, mapEnteSoggettoToEnteCapitolo);
			List<ProgettoImpegnoSoggettoBilVO> entiProvvedimentoFilterVO = beanUtil.transformList(entiCompetenzaSoggetto, ProgettoImpegnoSoggettoBilVO.class, mapEnteSoggettoToEnteProvvedimento);
			
			/*
			 * Ricerco i progetti associati all' impegno
			 */
			//ProgettoImpegnoPerBeneficiarioVO filtroCF = new ProgettoImpegnoPerBeneficiarioVO();
			ArrayList<ProgettoImpegnoPerBeneficiarioVO> filtroCF = new ArrayList<ProgettoImpegnoPerBeneficiarioVO>();
			for (Long idImpegno : listaIdImpegno) {
				ProgettoImpegnoPerBeneficiarioVO f = new ProgettoImpegnoPerBeneficiarioVO();
				f.setIdImpegno(new BigDecimal(idImpegno));
				filtroCF.add(f);
			}
			List<ProgettoImpegnoPerBeneficiarioVO> listProgettiImpegni = null;
			listProgettiImpegni = genericDAO.findListWhere(filtroCF);
			logger.info("findProgettiAssociatiPerBeneficiario(): num progetti associati = "+listProgettiImpegni.size());
			
			// Crea una lista di idProgetto eliminando i doppioni.
			ArrayList<BigDecimal> listaIdProgetto = new ArrayList<BigDecimal>();
			for (ProgettoImpegnoPerBeneficiarioVO p : listProgettiImpegni) {
				listaIdProgetto.add(p.getIdProgetto());
			}
			List<BigDecimal> listaIdProgettoUnici = new ArrayList<BigDecimal>(new LinkedHashSet<BigDecimal>(listaIdProgetto));
	
			
			 if (!ObjectUtil.isEmpty(listaIdProgettoUnici)){
				 for (BigDecimal p : listaIdProgettoUnici) {
					 /*
					  * per ogni progetto ricerco gli impegni associati
					  */
					 
					 ProgettoImpegnoSoggettoBilVO progettoFilterVO = new ProgettoImpegnoSoggettoBilVO();
					 progettoFilterVO.setIdProgetto(p);
					 if (annoEsercizio != null)
						 progettoFilterVO.setAnnoEsercizio(annoEsercizio.toString());
					 progettoFilterVO.setAscendentOrder("codiceVisualizzato");
					 List<ProgettoImpegnoSoggettoBilVO> listImpegniProgetto = genericDAO.findListWhereDistinct(Condition.filterBy(progettoFilterVO).and(Condition.filterBy(entiCapitoloFilterVO).or(Condition.filterBy(entiProvvedimentoFilterVO))), ProgettoImpegnoSoggettoBilVO.class);
					 					
					 
					 if (!ObjectUtil.isEmpty(listImpegniProgetto)){
						 long countImpegni = 0;
						 BigDecimal idPrj = null;
						 ProgettoImpegnoDTO progetto = null;
						 BigDecimal importoTotaleImpegniProgetto = new BigDecimal(0);
						 List<ImpegnoDTO> impegniProgetto = new ArrayList<ImpegnoDTO>();
						 for (ProgettoImpegnoSoggettoBilVO vo : listImpegniProgetto) {
							 if (idPrj == null)
								 idPrj = vo.getIdProgetto();
							 if (idPrj.compareTo(vo.getIdProgetto()) == 0) {
								 /*
								  * Abbiamo un impegno dello stesso
								  * progetto.
								  */
								 if (progetto == null)
									 progetto = beanUtil.transform(vo, ProgettoImpegnoDTO.class);
								 countImpegni++;
								 ImpegnoDTO impegnoProgetto =  beanUtil.transform(vo, ImpegnoDTO.class, BilancioDAO.MAP_PROGETTO_IMPEGNOVO_TO_IMPEGNODTO);
								 
								 impegniProgetto.add(impegnoProgetto);
								 importoTotaleImpegniProgetto = NumberUtil.sum(importoTotaleImpegniProgetto, NumberUtil.createScaledBigDecimal(vo.getImportoAttualeImpegno()));
							 } else {
								 /*
								  * Cambio il progetto
								  */
								 progetto.setNumTotImpegniProgetto(countImpegni);
								 progetto.setImpegni(beanUtil.transform(impegniProgetto, ImpegnoDTO.class));
								 progetto.setImpTotImpegniProgetto(NumberUtil.toDouble(importoTotaleImpegniProgetto));	 
								 
								 result.add(progetto);
								 
								 /*
								  * Reset delle variabili
								  */
								 countImpegni = 1;
								 progetto = beanUtil.transform(vo, ProgettoImpegnoDTO.class);
								 idPrj = vo.getIdProgetto();;
								 impegniProgetto = new ArrayList<ImpegnoDTO>();
								 importoTotaleImpegniProgetto = new BigDecimal(0);
								 ImpegnoDTO impegno = beanUtil.transform(vo, ImpegnoDTO.class, BilancioDAO.MAP_PROGETTO_IMPEGNOVO_TO_IMPEGNODTO);								 														
								 impegniProgetto.add(impegno);
								 importoTotaleImpegniProgetto = NumberUtil.sum(importoTotaleImpegniProgetto, NumberUtil.createScaledBigDecimal(vo.getImportoAttualeImpegno()));
							 }
						 }
						 /*
						  * Inserisco l'ultimo progetto
						  */						 
						 ImpegnoDTO[] lista = beanUtil.transform(impegniProgetto, ImpegnoDTO.class);
						 lista = ordinaNullInFondo(lista);
						 progetto.setImpegni(lista);
						 progetto.setImpTotImpegniProgetto(NumberUtil.toDouble(importoTotaleImpegniProgetto));
						 progetto.setNumTotImpegniProgetto(countImpegni);
						 result.add(progetto);
					 } else {
						 // Se il progetto non ha impegni, cerco i dati del solo progetto.
						 logger.info("findProgettiAssociatiPerBeneficiario(): nessun impegno trovato: cerco i dati del solo progetto.");
						 try {
							 ProgettoBilancioPerBeneficiarioVO filtroProg = new ProgettoBilancioPerBeneficiarioVO();
							 filtroProg.setIdProgetto(p);
							 ProgettoBilancioPerBeneficiarioVO progVO = genericDAO.findSingleOrNoneWhere(filtroProg);
							 if (progVO != null) {
								 ProgettoImpegnoDTO progetto = new ProgettoImpegnoDTO();
								 progetto.setIdProgetto(NumberUtil.toLong(progVO.getIdProgetto()));
								 progetto.setCodiceVisualizzato(progVO.getCodiceVisualizzato());
								 progetto.setTitoloProgetto(progVO.getTitoloProgetto());
								 progetto.setIdSoggetto(NumberUtil.toLong(progVO.getIdSoggetto()));
								 progetto.setDenominazioneBeneficiario(progVO.getDenominazioneBeneficiario());
								 progetto.setQuotaImportoAgevolato(NumberUtil.toDouble(progVO.getQuotaImportoAgevolato()));
								 progetto.setNumTotImpegniProgetto(new Long(0));
								 progetto.setImpTotImpegniProgetto(new Double(0));
								 progetto.setImpegni(null);
								 progetto.setNomeBandoLinea(progVO.getNomeBandoLinea());
								 result.add(progetto);
							 }
						 } catch (Exception e) {
							 logger.error("findProgettiAssociatiPerBeneficiario(): ERRORE nella ricerca del progetto senza impegni");
						 }
					 }
				 }
			 }
			 return beanUtil.transform(result, ProgettoImpegnoDTO.class);
		} finally {
			logger.end();
		}	
	}
	
	// Crea una nuova lista con prima gli elementi con numeroPerente valorizzato e al fondo gli altri.
	private ImpegnoDTO[] ordinaNullInFondo(ImpegnoDTO[] lista) {
		if (lista == null)
			return null;
		try  {
			ArrayList<ImpegnoDTO> array = beanUtil.transformToArrayList(lista, ImpegnoDTO.class);
			
			ArrayList<ImpegnoDTO> arrayOrdinato = new ArrayList<ImpegnoDTO>();	
			int indice = 0;
			for (ImpegnoDTO dto : array) {				
				if (dto.getAnnoPerente() != null && dto.getNumeroPerente() != null) {
					boolean inserito = false;
					for (int i = 0; i < arrayOrdinato.size(); i++) {
						ImpegnoDTO dtoOrdinato = arrayOrdinato.get(i);
						if (dto.getAnnoPerente().intValue() > dtoOrdinato.getAnnoPerente().intValue() ||
							(dto.getAnnoPerente().intValue() == dtoOrdinato.getAnnoPerente().intValue() &&
							 dto.getNumeroPerente().intValue() > dtoOrdinato.getNumeroPerente().intValue())) {
							arrayOrdinato.add(i, dto);
							inserito = true;							
							break;
						}										
					}
					if (!inserito) {
						arrayOrdinato.add(dto);						
					}
				}
			}
			for (ImpegnoDTO dto : array) {
				if (dto.getAnnoPerente() == null || dto.getNumeroPerente() == null) {
						arrayOrdinato.add(dto);
				}
			}
		
			return arrayOrdinato.toArray(new ImpegnoDTO[] {});
			
		} catch (Exception e) {
			logger.error("Errore in ordinaNullInFondo(): "+e);
			return lista;
		}
	}
	
	
	public ProgettoImpegnoDTO[] findProgettiDaAssociare(Long idUtente,
			String identitaDigitale, Long[] idImpegno, Long progrBandolinea, Long idProgetto)
			throws CSIException, SystemException, UnrecoverableException,
			ImpegnoBilancioException {
		logger.begin();
		String[] nameParameter = { "idUtente", "identitaDigitale",
				"progrBandolinea"};

		ValidatorInput.verifyNullValue(nameParameter, idUtente,
				identitaDigitale,progrBandolinea);
		
		if (idImpegno != null && idImpegno.length > 0) {
			return findProgettiDaAssociareConImpegni(idImpegno, progrBandolinea, idProgetto);
		} else {
			return findProgettiDaAssociareSenzaImpegni(progrBandolinea, idProgetto);
		}
	}
	
	public ProgettoImpegnoDTO[] findProgettiDaAssociareConImpegni(Long[] idImpegno, Long progrBandolinea, Long idProgetto)
			throws CSIException, SystemException, UnrecoverableException,
			ImpegnoBilancioException {
		logger.begin();
		try {
			List<ProgettoImpegnoDTO> result = new ArrayList<ProgettoImpegnoDTO>();
			
			/*
			 * Ricerco i progetti gia' associati all' impegno.
			 */
			//PbandiRImpegnoProgettoVO progettoFilter = new PbandiRImpegnoProgettoVO();
			//progettoFilter.setIdImpegno(NumberUtil.toBigDecimal(idImpegno));
			//List<PbandiRImpegnoProgettoVO> listProgettiAssociati = genericDAO.findListWhere(progettoFilter);
			ArrayList<PbandiRImpegnoProgettoVO> progettoFilter = new ArrayList<PbandiRImpegnoProgettoVO>();
			for (int i = 0; i < idImpegno.length; i++) {
				PbandiRImpegnoProgettoVO vo = new PbandiRImpegnoProgettoVO();
				vo.setIdImpegno(new BigDecimal(idImpegno[i]));
				progettoFilter.add(vo);
			}
			List<PbandiRImpegnoProgettoVO> listProgettiAssociati = genericDAO.findListWhere(progettoFilter);
			
			ProgettoImpegnoBilVO filterVO = new ProgettoImpegnoBilVO();
			filterVO.setProgrBandolineaIntervento(NumberUtil.toBigDecimal(progrBandolinea));
			filterVO.setIdProgetto(NumberUtil.toBigDecimal(idProgetto));
			// Jira PBANDI-2848: la modalit� di agevolazione ora � gestita nella select.
			// filterVO.setDescBreveModalitaAgevolaz("Contributo");   
			filterVO.setAscendentOrder("codiceVisualizzato");
			
			//ProgettoImpegnoBilVO notFilter = new ProgettoImpegnoBilVO();
			//notFilter.setIdImpegno(NumberUtil.toBigDecimal(idImpegno));
			ArrayList<ProgettoImpegnoBilVO> notFilter = new ArrayList<ProgettoImpegnoBilVO>();
			for (int i = 0; i < idImpegno.length; i++) {
				ProgettoImpegnoBilVO vo = new ProgettoImpegnoBilVO();
				vo.setIdImpegno(new BigDecimal(idImpegno[i]));
				notFilter.add(vo);
			}
			
			AndCondition<ProgettoImpegnoBilVO> andCondition = null;
			
			if (!ObjectUtil.isEmpty(listProgettiAssociati)){
				List<ProgettoImpegnoBilVO> progettiAssociatiFilter = new ArrayList<ProgettoImpegnoBilVO>();
				for (PbandiRImpegnoProgettoVO vo : listProgettiAssociati) {
					ProgettoImpegnoBilVO progetto = new ProgettoImpegnoBilVO();
					progetto.setIdProgetto(vo.getIdProgetto());
					progettiAssociatiFilter.add(progetto);
				}
				andCondition = new AndCondition<ProgettoImpegnoBilVO>(Condition.filterBy(filterVO),Condition.not(Condition.filterBy(notFilter)),Condition.not(Condition.filterBy(progettiAssociatiFilter)));
			} else {
				andCondition = new AndCondition<ProgettoImpegnoBilVO>(Condition.filterBy(filterVO),Condition.not(Condition.filterBy(notFilter)));
			}
			
			List<ProgettoImpegnoBilVO> listImpegniProgetti = genericDAO.findListWhereDistinct(andCondition, ProgettoImpegnoBilVO.class);
			
			if (!ObjectUtil.isEmpty(listImpegniProgetti)) {
				long countImpegni = 0;
				BigDecimal idPrj = null;
				ProgettoImpegnoDTO progetto = null;
				BigDecimal importoTotaleImpegniProgetto = new BigDecimal(0);
				List<ImpegnoDTO> impegniProgetto = new ArrayList<ImpegnoDTO>();
				for (ProgettoImpegnoBilVO p : listImpegniProgetti) {
					if (idPrj == null)
						idPrj = p.getIdProgetto();
					if (idPrj.compareTo(p.getIdProgetto()) == 0) {
						/*
						 * Abbiamo un impegno dello stesso
						 * progetto.
						 */
						if (progetto == null)
							progetto = beanUtil.transform(p, ProgettoImpegnoDTO.class);
						
						
						/*
						 * idImpegno = -1 indica che non esiste impegno (nella query gli impegni sono in 
						 * outer join con i progetti)
						 */
						if (p.getIdImpegno().compareTo(ImpegnoBilancioSrv.ID_IMPEGNO_FITTIZIO) != 0) {
							countImpegni++;
							ImpegnoDTO impegnoProgetto =  beanUtil.transform(p, ImpegnoDTO.class, BilancioDAO.MAP_PROGETTO_IMPEGNOVO_TO_IMPEGNODTO);
							impegniProgetto.add(impegnoProgetto);
							importoTotaleImpegniProgetto = NumberUtil.sum(importoTotaleImpegniProgetto, NumberUtil.createScaledBigDecimal(p.getImportoAttualeImpegno()));
						}
						
					} else {
						/*
						 * Cambio il progetto
						 */
						progetto.setNumTotImpegniProgetto(countImpegni);
						progetto.setImpegni(beanUtil.transform(impegniProgetto, ImpegnoDTO.class));
						progetto.setImpTotImpegniProgetto(NumberUtil.toDouble(importoTotaleImpegniProgetto));
						result.add(progetto);
						
						/*
						 * Reset delle variabili
						 */
						countImpegni = 0;
						progetto = beanUtil.transform(p, ProgettoImpegnoDTO.class);
						idPrj = p.getIdProgetto();;
						impegniProgetto = new ArrayList<ImpegnoDTO>();
						importoTotaleImpegniProgetto = new BigDecimal(0);
						/*
						 * idImpegno = -1 indica che non esiste impegno (nella query gli impegni sono in 
						 * outer join con i progetti)
						 */
						if (p.getIdImpegno().compareTo(ImpegnoBilancioSrv.ID_IMPEGNO_FITTIZIO) != 0) {
							countImpegni++;
							ImpegnoDTO impegno = beanUtil.transform(p, ImpegnoDTO.class, BilancioDAO.MAP_PROGETTO_IMPEGNOVO_TO_IMPEGNODTO);
							impegniProgetto.add(impegno);
							importoTotaleImpegniProgetto = NumberUtil.sum(importoTotaleImpegniProgetto, NumberUtil.createScaledBigDecimal(p.getImportoAttualeImpegno()));
						}
						
						
					}
				}
				/*
				 * Inserisco l'ultimo progetto
				 */
				progetto.setNumTotImpegniProgetto(countImpegni);
				progetto.setImpegni(beanUtil.transform(impegniProgetto, ImpegnoDTO.class));
				progetto.setImpTotImpegniProgetto(NumberUtil.toDouble(importoTotaleImpegniProgetto));
				result.add(progetto);
			}
			
			return beanUtil.transform(result, ProgettoImpegnoDTO.class);
			
		} finally {
			logger.end();
		}
	}
	
	public ProgettoImpegnoDTO[] findProgettiDaAssociareSenzaImpegni(Long progrBandolinea, Long idProgetto)
			throws CSIException, SystemException, UnrecoverableException,
			ImpegnoBilancioException {
		logger.begin();
		try {
			List<ProgettoImpegnoDTO> result = new ArrayList<ProgettoImpegnoDTO>();
		
			ProgettoImpegnoBilVO filterVO = new ProgettoImpegnoBilVO();
			filterVO.setProgrBandolineaIntervento(NumberUtil.toBigDecimal(progrBandolinea));
			filterVO.setIdProgetto(NumberUtil.toBigDecimal(idProgetto)); 
			filterVO.setAscendentOrder("codiceVisualizzato");				
			List<ProgettoImpegnoBilVO> listImpegniProgetti = genericDAO.findListWhereDistinct(Condition.filterBy(filterVO), ProgettoImpegnoBilVO.class);
			
			if (!ObjectUtil.isEmpty(listImpegniProgetti)) {
				long countImpegni = 0;
				BigDecimal idPrj = null;
				ProgettoImpegnoDTO progetto = null;
				BigDecimal importoTotaleImpegniProgetto = new BigDecimal(0);
				List<ImpegnoDTO> impegniProgetto = new ArrayList<ImpegnoDTO>();
				for (ProgettoImpegnoBilVO p : listImpegniProgetti) {
					if (idPrj == null)
						idPrj = p.getIdProgetto();
					if (idPrj.compareTo(p.getIdProgetto()) == 0) {
						/*
						 * Abbiamo un impegno dello stesso
						 * progetto.
						 */
						if (progetto == null)
							progetto = beanUtil.transform(p, ProgettoImpegnoDTO.class);
						
						
						/*
						 * idImpegno = -1 indica che non esiste impegno (nella query gli impegni sono in 
						 * outer join con i progetti)
						 */
						if (p.getIdImpegno().compareTo(ImpegnoBilancioSrv.ID_IMPEGNO_FITTIZIO) != 0) {
							countImpegni++;
							ImpegnoDTO impegnoProgetto =  beanUtil.transform(p, ImpegnoDTO.class, BilancioDAO.MAP_PROGETTO_IMPEGNOVO_TO_IMPEGNODTO);
							impegniProgetto.add(impegnoProgetto);
							importoTotaleImpegniProgetto = NumberUtil.sum(importoTotaleImpegniProgetto, NumberUtil.createScaledBigDecimal(p.getImportoAttualeImpegno()));
						}
						
					} else {
						/*
						 * Cambio il progetto
						 */
						progetto.setNumTotImpegniProgetto(countImpegni);
						progetto.setImpegni(beanUtil.transform(impegniProgetto, ImpegnoDTO.class));
						progetto.setImpTotImpegniProgetto(NumberUtil.toDouble(importoTotaleImpegniProgetto));
						result.add(progetto);
						
						/*
						 * Reset delle variabili
						 */
						countImpegni = 0;
						progetto = beanUtil.transform(p, ProgettoImpegnoDTO.class);
						idPrj = p.getIdProgetto();;
						impegniProgetto = new ArrayList<ImpegnoDTO>();
						importoTotaleImpegniProgetto = new BigDecimal(0);
						/*
						 * idImpegno = -1 indica che non esiste impegno (nella query gli impegni sono in 
						 * outer join con i progetti)
						 */
						if (p.getIdImpegno().compareTo(ImpegnoBilancioSrv.ID_IMPEGNO_FITTIZIO) != 0) {
							countImpegni++;
							ImpegnoDTO impegno = beanUtil.transform(p, ImpegnoDTO.class, BilancioDAO.MAP_PROGETTO_IMPEGNOVO_TO_IMPEGNODTO);
							impegniProgetto.add(impegno);
							importoTotaleImpegniProgetto = NumberUtil.sum(importoTotaleImpegniProgetto, NumberUtil.createScaledBigDecimal(p.getImportoAttualeImpegno()));
						}
						
						
					}
				}
				/*
				 * Inserisco l'ultimo progetto
				 */
				progetto.setNumTotImpegniProgetto(countImpegni);
				progetto.setImpegni(beanUtil.transform(impegniProgetto, ImpegnoDTO.class));
				progetto.setImpTotImpegniProgetto(NumberUtil.toDouble(importoTotaleImpegniProgetto));
				result.add(progetto);
			}
			
			return beanUtil.transform(result, ProgettoImpegnoDTO.class);
			
		} finally {
			logger.end();
		}
	}
	
	public ProgettoImpegnoDTO[] findListProgetti(Long idUtente,
			String identitaDigitale, Long[] idImpegno, Long progrBandolinea)
			throws CSIException, SystemException, UnrecoverableException,
			ImpegnoBilancioException {
		
			String[] nameParameter = { "idUtente", "identitaDigitale"};

			ValidatorInput.verifyNullValue(nameParameter, idUtente,
					identitaDigitale);
			
			if (idImpegno != null && idImpegno.length > 0) {
				logger.info("findListProgetti(): chiamo findListProgettiConImpegni()");
				return findListProgettiConImpegni(idImpegno, progrBandolinea);
			} else {
				logger.info("findListProgetti(): chiamo findListProgettiSenzaImpegni()");
				return findListProgettiSenzaImpegni(progrBandolinea);
			}
	}

	private ProgettoImpegnoDTO[] findListProgettiConImpegni(Long[] idImpegno, Long progrBandolinea)
			throws CSIException, SystemException, UnrecoverableException,
			ImpegnoBilancioException {
		logger.begin();
		try {
			List<ProgettoImpegnoDTO> result = new ArrayList<ProgettoImpegnoDTO>();
			
			/*
			 * Ricerco i progetti associati all' impegno
			 */
			//PbandiRImpegnoProgettoVO progettoFilter = new PbandiRImpegnoProgettoVO();
			//progettoFilter.setIdImpegno(NumberUtil.toBigDecimal(idImpegno));
			//List<PbandiRImpegnoProgettoVO> listProgettiAssociati = genericDAO.findListWhere(progettoFilter);
			ArrayList<PbandiRImpegnoProgettoVO> progettoFilter = new ArrayList<PbandiRImpegnoProgettoVO>(); 
			for (int i = 0; i < idImpegno.length; i++) {
				PbandiRImpegnoProgettoVO vo = new PbandiRImpegnoProgettoVO();
				vo.setIdImpegno(new BigDecimal(idImpegno[i]));
				progettoFilter.add(vo);
			}
			List<PbandiRImpegnoProgettoVO> listProgettiAssociati = genericDAO.findListWhere(progettoFilter);
			
				
			ProgettoImpegnoBilVO filterVO = new ProgettoImpegnoBilVO();
			filterVO.setProgrBandolineaIntervento(NumberUtil.toBigDecimal(progrBandolinea));
			// Jira PBANDI-2848: la modalit� di agevolazione ora � gestita nella select.
			//filterVO.setDescBreveModalitaAgevolaz("Contributo");
			filterVO.setAscendentOrder("codiceVisualizzato");
			
			//ProgettoImpegnoBilVO notFilter = new ProgettoImpegnoBilVO();
			//notFilter.setIdImpegno(NumberUtil.toBigDecimal(idImpegno[0]));			
			ArrayList<ProgettoImpegnoBilVO> notFilter = new ArrayList<ProgettoImpegnoBilVO>(); 
			for (int i = 0; i < idImpegno.length; i++) {
				ProgettoImpegnoBilVO vo = new ProgettoImpegnoBilVO();
				vo.setIdImpegno(new BigDecimal(idImpegno[i]));
				notFilter.add(vo);
			} 
			
			AndCondition<ProgettoImpegnoBilVO> andCondition = null;
			
			if (!ObjectUtil.isEmpty(listProgettiAssociati)){
				/*
				 * Non considero i progetti gia' associati all' impegno.
				 */
				List<ProgettoImpegnoBilVO> progettiAssociatiFilter = new ArrayList<ProgettoImpegnoBilVO>();
				for (PbandiRImpegnoProgettoVO progetto : listProgettiAssociati) {
					ProgettoImpegnoBilVO vo = new ProgettoImpegnoBilVO();
					vo.setIdProgetto(progetto.getIdProgetto());
					progettiAssociatiFilter.add(vo);
				}
				andCondition = new AndCondition<ProgettoImpegnoBilVO>(Condition.filterBy(filterVO),Condition.not(Condition.filterBy(notFilter)),Condition.not(Condition.filterBy(progettiAssociatiFilter)));
			} else {
				andCondition = new AndCondition<ProgettoImpegnoBilVO>(Condition.filterBy(filterVO),Condition.not(Condition.filterBy(notFilter)));
			}
			
			List<ProgettoImpegnoBilVO> progetti = genericDAO.findListWhereDistinct(andCondition, ProgettoImpegnoBilVO.class);
			
			if (!ObjectUtil.isEmpty(progetti)) {
				BigDecimal idProgetto = new BigDecimal(0);
				for (ProgettoImpegnoBilVO p : progetti) {
					if (idProgetto.compareTo(p.getIdProgetto()) != 0) {
						idProgetto = p.getIdProgetto();
						ProgettoImpegnoDTO dto = new ProgettoImpegnoDTO();
						dto.setIdProgetto(NumberUtil.toLong(p.getIdProgetto()));
						dto.setCodiceVisualizzato(p.getCodiceVisualizzato());
						dto.setDenominazioneBeneficiario(p.getDenominazioneBeneficiario());
						result.add(dto);
					}
				}
			}
			return beanUtil.transform(result, ProgettoImpegnoDTO.class);
			
		} finally {
			logger.end();
		}		
	}
	
	private ProgettoImpegnoDTO[] findListProgettiSenzaImpegni(Long progrBandolinea)
		throws CSIException, SystemException, UnrecoverableException,ImpegnoBilancioException {
		logger.begin();
		try {
			List<ProgettoImpegnoDTO> result = new ArrayList<ProgettoImpegnoDTO>();	
				
			ProgettoImpegnoBilVO filterVO = new ProgettoImpegnoBilVO();
			filterVO.setProgrBandolineaIntervento(NumberUtil.toBigDecimal(progrBandolinea));
			filterVO.setAscendentOrder("codiceVisualizzato");
			
			List<ProgettoImpegnoBilVO> progetti = genericDAO.findListWhereDistinct(Condition.filterBy(filterVO), ProgettoImpegnoBilVO.class);
			
			if (!ObjectUtil.isEmpty(progetti)) {
				BigDecimal idProgetto = new BigDecimal(0);
				for (ProgettoImpegnoBilVO p : progetti) {
					if (idProgetto.compareTo(p.getIdProgetto()) != 0) {
						idProgetto = p.getIdProgetto();
						ProgettoImpegnoDTO dto = new ProgettoImpegnoDTO();
						dto.setIdProgetto(NumberUtil.toLong(p.getIdProgetto()));
						dto.setCodiceVisualizzato(p.getCodiceVisualizzato());
						dto.setDenominazioneBeneficiario(p.getDenominazioneBeneficiario());
						result.add(dto);
					}
				}
			}
			return beanUtil.transform(result, ProgettoImpegnoDTO.class);
			
		} finally {
			logger.end();
		}		
	}
	
	public EsitoOperazioneDisassociaDTO disassociaBandolinea(Long idUtente,
			String identitaDigitale, Long idImpegno,
			Long[] progrBandolineaSelezionati) throws CSIException,
			SystemException, UnrecoverableException, ImpegnoBilancioException {
		logger.begin();
		try {
			String[] nameParameter = { "idUtente", "identitaDigitale",
					"idImpegno","progrBandolineaSelezionati"};

			ValidatorInput.verifyNullValue(nameParameter, idUtente,
					identitaDigitale, idImpegno, progrBandolineaSelezionati);
			
			ValidatorInput.verifyArrayNotEmpty("progrBandolineaSelezionati", progrBandolineaSelezionati);
			
			EsitoOperazioneDisassociaDTO result = new EsitoOperazioneDisassociaDTO();
			result.setEsito(Boolean.TRUE);
			StringBuilder bandolineaNonDisassociati = new StringBuilder ();
			for (Long progrBandolinea : progrBandolineaSelezionati) {
				PbandiRImpegnoBandoLineaVO filterVO = new PbandiRImpegnoBandoLineaVO();
				filterVO.setIdImpegno(NumberUtil.toBigDecimal(idImpegno));
				filterVO.setProgrBandoLineaIntervento(NumberUtil.toBigDecimal(progrBandolinea));
				
				try {
					genericDAO.delete(filterVO);
				} catch (Exception e) {
					logger.error("Errore in fase di disassociazione bandolinea impengo. Eccezione gestita.",e);
					result.setEsito(Boolean.FALSE);
					PbandiRBandoLineaInterventVO bandolineaVO = new PbandiRBandoLineaInterventVO();
					bandolineaVO.setProgrBandoLineaIntervento(NumberUtil.toBigDecimal(progrBandolinea));
					bandolineaVO = genericDAO.findSingleWhere(bandolineaVO);
					bandolineaNonDisassociati.append(bandolineaVO.getNomeBandoLinea()+",");
				}
			}
			MessaggioDTO msg = new MessaggioDTO();
			
			if (result.getEsito()) {
				msg.setKey(MessaggiConstants.KEY_MSG_IMPEGNO_DISASSOCIA_CONCLUSA);
			} else {
				msg.setKey(ErrorConstants.ERRORE_BILANCIO_BANDOLINEA_NON_DISASSOCIATI);
				msg.setParams(new String [] {bandolineaNonDisassociati.toString()});
			}
			
			result.setMsg(msg);
			
			return result;
			
		} finally {
			logger.end();
		}
		
	}

	public EsitoOperazioneDisassociaDTO disassociaProgetti(Long idUtente,
			String identitaDigitale, Long idImpegno, Long[] progetti)
			throws CSIException, SystemException, UnrecoverableException,
			ImpegnoBilancioException {
		logger.begin();
		try {
			String[] nameParameter = { "idUtente", "identitaDigitale",
					"idImpegno","progetti"};

			ValidatorInput.verifyNullValue(nameParameter, idUtente,
					identitaDigitale, idImpegno, progetti);
			
			ValidatorInput.verifyArrayNotEmpty("progrBandolineaSelezionati", progetti);
			
			EsitoOperazioneDisassociaDTO result = new EsitoOperazioneDisassociaDTO();
			result.setEsito(Boolean.TRUE);
			StringBuilder progettiNonDisassociati = new StringBuilder ();
			
			for (Long idProgetto : progetti) {
				PbandiRImpegnoProgettoVO filterVO = new PbandiRImpegnoProgettoVO();
				filterVO.setIdImpegno(NumberUtil.toBigDecimal(idImpegno));
				filterVO.setIdProgetto(NumberUtil.toBigDecimal(idProgetto));
				
				try {
					genericDAO.delete(filterVO);
				} catch (Exception e) {
					logger.error("Errore in fase di disassociazione progetto impegno. Eccezione gestita.",e);
					result.setEsito(Boolean.FALSE);
					PbandiTProgettoVO progettoVO = new PbandiTProgettoVO();
					progettoVO.setIdProgetto(NumberUtil.toBigDecimal(idProgetto));
					progettoVO = genericDAO.findSingleWhere(progettoVO);
					progettiNonDisassociati.append(progettoVO.getCodiceVisualizzato()+",");
				}
			}
			MessaggioDTO msg = new MessaggioDTO();
			
			if (result.getEsito()) {
				msg.setKey(MessaggiConstants.KEY_MSG_IMPEGNO_DISASSOCIA_CONCLUSA);
			} else {
				msg.setKey(ErrorConstants.ERRORE_BILANCIO_PROGETTI_NON_DISASSOCIATI);
				msg.setParams(new String [] {progettiNonDisassociati.toString()});
			}
			
			result.setMsg(msg);
			
			return result;
		} finally {
			logger.end();
		}
		
	}
	
	public EsitoOperazioneDisassociaDTO disassociaBandolineaGestAss(Long idUtente,
			String identitaDigitale, Long[] idImpegno,
			Long[] progrBandolineaSelezionati) throws CSIException,
			SystemException, UnrecoverableException, ImpegnoBilancioException {
		logger.begin();
		try {
			String[] nameParameter = { "idUtente", "identitaDigitale",
					"idImpegno","progrBandolineaSelezionati"};
			ValidatorInput.verifyNullValue(nameParameter, idUtente,
					identitaDigitale, idImpegno, progrBandolineaSelezionati);			
			ValidatorInput.verifyArrayNotEmpty("progrBandolineaSelezionati", progrBandolineaSelezionati);
			ValidatorInput.verifyArrayNotEmpty("idImpegno", idImpegno);
			
			String s1 = "";
			for (Long im : idImpegno) {
				s1 += "  " + im;
			}
			String s2 = "";
			for (Long bl : progrBandolineaSelezionati) {
				s2 += "  " + bl;
			}
			logger.info("\n\ndisassociaBandolineaGestAss(): elenco idImpegno = "+s1+"; elenco progrBandolinea = "+s2+"\n\n");			
			
			EsitoOperazioneDisassociaDTO result = new EsitoOperazioneDisassociaDTO();
			result.setEsito(Boolean.TRUE);
			for (Long id : idImpegno) {
				EsitoOperazioneDisassociaDTO esito = this.disassociaBandolinea(idUtente, identitaDigitale, id, progrBandolineaSelezionati);				
				if (!esito.getEsito()) {
					result.setEsito(false);
					result.setMsg(esito.getMsg());
					return result;
				}
			}
			
			MessaggioDTO msg = new MessaggioDTO();
			msg.setKey(MessaggiConstants.KEY_MSG_IMPEGNO_DISASSOCIA_CONCLUSA);
			result.setMsg(msg);
			
			logger.info("\n\ndisassociaBandolineaGestAss(): FINE \n\n");
			
			return result;	
		} finally {
			logger.end();
		}		
	}

	public EsitoOperazioneDisassociaDTO disassociaProgettiGestAss(Long idUtente,
			String identitaDigitale, Long[] idImpegno, Long[] progetti)
			throws CSIException, SystemException, UnrecoverableException,
			ImpegnoBilancioException {
		logger.begin();
		try {
			String[] nameParameter = { "idUtente", "identitaDigitale",
					"idImpegno","progetti"};
			ValidatorInput.verifyNullValue(nameParameter, idUtente,
					identitaDigitale, idImpegno, progetti);			
			ValidatorInput.verifyArrayNotEmpty("progetti", progetti);
			ValidatorInput.verifyArrayNotEmpty("idImpegno", idImpegno);
			
			String s1 = "";
			for (Long im : idImpegno) {
				s1 += "  " + im;
			}
			String s2 = "";
			for (Long bl : progetti) {
				s2 += "  " + bl;
			}
			logger.info("\n\ndisassociaProgettiGestAss(): elenco idImpegno = "+s1+"; elenco progetti = "+s2+"\n\n");			
			
			EsitoOperazioneDisassociaDTO result = new EsitoOperazioneDisassociaDTO();
			result.setEsito(Boolean.TRUE);
			
			for (Long id : idImpegno) {
				EsitoOperazioneDisassociaDTO esito = this.disassociaProgetti(idUtente, identitaDigitale, id, progetti);				
				if (!esito.getEsito()) {
					result.setEsito(false);
					result.setMsg(esito.getMsg());
					return result;
				}
			}
			
			MessaggioDTO msg = new MessaggioDTO();
			msg.setKey(MessaggiConstants.KEY_MSG_IMPEGNO_DISASSOCIA_CONCLUSA);
			result.setMsg(msg);
			
			logger.info("\n\ndisassociaProgettiGestAss(): FINE \n\n");
			
			return result;
		} finally {
			logger.end();
		}		
	}
	
	public EsitoOperazioneAggiornaImpegnoDTO aggiornaImpegno(Long idUtente, String identitaDigitale,
			ImpegnoDTO impegno) throws CSIException, SystemException,
			UnrecoverableException, ImpegnoBilancioException {
		logger.begin();
		EsitoOperazioneAggiornaImpegnoDTO result = new EsitoOperazioneAggiornaImpegnoDTO();
		result.setEsito(Boolean.FALSE);
		try {
			String[] nameParameter = { "idUtente", "identitaDigitale",
					"impegno","annoEsercizio"};

			ValidatorInput.verifyNullValue(nameParameter, idUtente,
					identitaDigitale, impegno, impegno.getAnnoEsercizio());
			
			
			List<PbandiCBatchImpegniVO> listAnni = genericDAO.findListWhere(new PbandiCBatchImpegniVO());
			Long annoEsercizio = impegno.getAnnoEsercizio();
			String descBreveEnteCapitolo = new String();
			String descBreveEnteProvv = new String ();
			Long numeroCapitolo = null;
			
			/*
			 * Ente di competenza e numero capitolo relativi al capitolo al
			 * quale l'impegno e' associato
			 */
			if (impegno.getCapitolo() != null) {
				numeroCapitolo = impegno.getCapitolo().getNumeroCapitolo();
				if (impegno.getCapitolo().getEnteCompetenza() != null) {
					descBreveEnteCapitolo = impegno.getCapitolo().getEnteCompetenza().getDescBreveEnte();
				}
			}
			
			/*
			 * Ente di competenza del provvedimento associato al capitolo
			 */
			if (impegno.getProvvedimento()!= null && impegno.getProvvedimento().getEnteCompetenza()!= null ) {
				descBreveEnteProvv = impegno.getProvvedimento().getEnteCompetenza().getDescBreveEnte();
			}
			
			logger.info("aggiornaImpegno(): descBreveEnteCapitolo = "+descBreveEnteCapitolo);
			logger.info("aggiornaImpegno(): descBreveEnteProvv = "+descBreveEnteProvv);
			
			/*
			 * Possono essere gestiti solamenti gli impegni che
			 * rispettano anno-esercizio e direzione-provvedimento indicato nella tabella PBANDI_C_BATCH_IMPEGNI o anno-esercizio, numero_capitolo  e direzione del capitolo indicati
			 * nella tabella PBANDI_C_BATCH_IMPEGNI
			 */
			
			boolean isValid = false;
			/*
			 * Se e' valorizzato l' ente di provvedimento allora verifico prima
			 * che nella PBANDI_C_BATCH_IMPEGNI ci sia la direzione del provvedimento.
			 */
			if (!StringUtil.isEmpty(descBreveEnteProvv)) {
				PbandiCBatchImpegniVO filterEnteProvvedimentoVO = new PbandiCBatchImpegniVO();
				filterEnteProvvedimentoVO.setAnnoEsercizio(NumberUtil.toBigDecimal(annoEsercizio));
				filterEnteProvvedimentoVO.setDirezione(descBreveEnteProvv);
				List<PbandiCBatchImpegniVO> listGestiti = genericDAO.findListWhere(filterEnteProvvedimentoVO);
				if (!ObjectUtil.isEmpty(listGestiti))
					isValid = true;
			}
			
			if (!isValid) {
				if (!StringUtil.isEmpty(descBreveEnteCapitolo)){
					PbandiCBatchImpegniVO filterCapitoloVO = new PbandiCBatchImpegniVO();
					filterCapitoloVO.setAnnoEsercizio(NumberUtil.toBigDecimal(annoEsercizio));
					filterCapitoloVO.setDirezione(descBreveEnteCapitolo);
					filterCapitoloVO.setNumeroCapitolo(NumberUtil.toBigDecimal(numeroCapitolo));
					List<PbandiCBatchImpegniVO> listGestiti = genericDAO.findListWhere(filterCapitoloVO);
					if (!ObjectUtil.isEmpty(listGestiti))
						isValid = true;
				}
			}
			

			if (!isValid) {
				/*
				 * non aggiorno l' impegno poiche' non fa
				 * parte dei annoEsercizio/direzione(del capitolo o del provvedimento)/numero capitolo validi.
				 * Loggo l'eccezione
				 */
				logger.error("Impossibile aggiornare l'impegno poiche' annoEsercizio/direzione(capitolo o provvedimento)/capitolo non risultano sulla PBANDI_C_BATCH_IMPEGNI. ["+annoEsercizio+","+"("+descBreveEnteCapitolo+","+descBreveEnteProvv+"),"+numeroCapitolo+"]");
				result.setEsito(Boolean.FALSE);
				MessaggioDTO msg = new MessaggioDTO();
				msg.setKey(ErrorConstants.ERRORE_BILANCIO_IMPEGNO_NON_GESTITO);
				msg.setParams(new String[] {""+annoEsercizio, descBreveEnteCapitolo+","+descBreveEnteProvv, ""+numeroCapitolo});
				result.setMsg(msg);
				result.setImpegno(impegno);
				return result;
			}
			
			
			/*
			 * Aggiorno l' impegno
			 */
			PbandiWImpegniVO vo = beanUtil.transform(impegno, PbandiWImpegniVO.class, BilancioDAO.MAP_IMPEGNODTO_TO_PbandiWImpegniVO);
			vo.setFlagOnline("S");
			vo.setImportoattuale(NumberUtil.createScaledBigDecimal(impegno.getImportoAttualeImpegno()));
			if(impegno != null && impegno.getCodiceFiscale() != null) {
				vo.setCodFiscale( impegno.getCodiceFiscale() );
			}
			if(impegno != null && impegno.getRagsoc() != null) {
				vo.setRagsoc( impegno.getRagsoc() );
			}
//			if(impegno != null && impegno.getProvvedimento() != null && impegno.getProvvedimento().getEnteCompetenza() != null &&
//			   impegno.getProvvedimento().getEnteCompetenza().getIdEnteCompetenza() != null) {
//				vo.setco( impegno.getCodiceFiscale() );
//			}
			
			
// FORZATURA DA TOGLIERE - inizio
// al momento lasciata poich� non si � sicuri che il pl\sql sia stato adeguato.
vo.setDatains(this.mymock());
// FORZATURA DA TOGLIERe - fine	
			
			try {
				vo = genericDAO.insert(vo);
			
				logger.info("aggiornaImpegno(): eseguo PCK_PBANDI_BILANCIO.Impegni con idImpegni = "+vo.getIdImpegni()+"; flag = "+vo.getFlagOnline());
				logger.info("aggiornaImpegno(): importo = "+vo.getImportoattuale());
				int resultCallProcedure = genericDAO.callProcedure().aggiornaImpegno(vo.getIdImpegni(), true,idUtente);

				MessaggioDTO msg = new MessaggioDTO();
				if (resultCallProcedure == 0) {
					result.setEsito(Boolean.TRUE);
					msg.setKey(MessaggiConstants.SALVATAGGIO_AVVENUTO_CON_SUCCESSO);
				} else {
					result.setEsito(Boolean.FALSE);
					msg.setKey(ErrorConstants.ERRORE_SERVER);//it.csi.pbandi.pbandisrv.util.Constants;
					String msgException = "Stored procedure "+Constants.NOME_PACKAGE_PROC_BILANCIO+ "."
					+ "Impegni"+" non e' riuscita ad eseguire l'aggiornamento per idImpengo="+vo.getIdImpegni()+". Consultare la tabella PBANDI_L_LOG_BATCH.";
					ImpegnoBilancioException e = new ImpegnoBilancioException(msgException);
					logger.error("Errore gestito",e);
				}
				result.setMsg(msg);
				result.setImpegno(impegno);
			} catch (Exception e) {
				logger.error("aggiornaImpegno(): sollevata eccezione: "+e);
				result.setEsito(Boolean.FALSE);
				MessaggioDTO msg = new MessaggioDTO();
				msg.setKey(ErrorConstants.ERRORE_SERVER);
				result.setMsg(msg);
				result.setImpegno(impegno);
			}
		} finally {
			logger.end();
		}
		return result;
	}

	
	private java.sql.Date mymock() {
		return DateUtil.utilToSqlDate(DateUtil.getDataOdierna());
	}
	
	
	public EsitoOperazioneAggiornaImpegnoDTO consultaImpegno(Long idUtente,
			String identitaDigitale, ImpegnoDTO filtro) throws CSIException,
			SystemException, UnrecoverableException, ImpegnoBilancioException {
		logger.begin();
		EsitoOperazioneAggiornaImpegnoDTO result = new EsitoOperazioneAggiornaImpegnoDTO();
		result.setEsito(Boolean.FALSE);
		try {
			String[] nameParameter = { "idUtente", "identitaDigitale","filtro"};
			
			ValidatorInput.verifyNullValue(nameParameter, idUtente,
					identitaDigitale, filtro);
			
			
			List<ImpegnoKeyVO> filtroImpegni = new ArrayList<ImpegnoKeyVO>();
			Long numMaxRecord = new Long(1);
			
			ImpegnoKeyVO impegnoKey = new ImpegnoKeyVO();
			impegnoKey.setAnnoImpegno(filtro.getAnnoImpegno().toString());
			impegnoKey.setNumeroImpegno(new Integer(filtro.getNumeroImpegno().toString()));
			filtroImpegni.add(impegnoKey);
			List<ImpegnoVO> impegni = null;
			
			/*
			 * Ricerco l 'impegno dal bilancio
			 */
			try {
				impegni = bilancioDAOImpl.consultaImpegni(filtroImpegni, null, null, filtro.getAnnoEsercizio(),
						numMaxRecord,idUtente);
			} catch (Exception e) {
				logger.error("consultaImpegno(): Errore sollevato da bilancioDAO.consultaImpegni(): "+e);
				result.setEsito(Boolean.FALSE);
				MessaggioDTO msg = new MessaggioDTO();
				msg.setKey(ErrorConstants.ERRORE_IMPEGNI_IMPOSSIBILE_REPERIRE_DATI_IMPEGNO);
				result.setMsg(msg);
				return result;
			}finally {
				logger.info("impegni="+impegni);
			}
			
			if (!ObjectUtil.isEmpty(impegni)) {
				logger.info("consultaImpegno(): num impegni restituiti da bilancioDAO.consultaImpegni() = "+impegni.size());
				ImpegnoDTO impegno = beanUtil.transform(impegni.get(0), ImpegnoDTO.class, BilancioDAO.MAP_IMPEGNOVO_TO_IMPEGNODTO);
				impegno.setImportoAttualeImpegno(NumberUtil.toDouble(impegni.get(0).getImportoAttualeImpegno()));	
				logger.info("\n\n\nHHHHHHHHHHHHHHHH  "+impegni.get(0).getImportoAttualeImpegno()+"   -   "+impegno.getImportoAttualeImpegno()+"\n\n\n");			
				result.setEsito(Boolean.TRUE);
				result.setImpegno(impegno);
			} else {
				logger.info("consultaImpegno(): bilancioDAO.consultaImpegni() non ha restituito alcun impegno.");
				result.setEsito(Boolean.FALSE);
				MessaggioDTO msg = new MessaggioDTO();
				msg.setKey(ErrorConstants.WARNING_IMPEGNI_NESSUN_IMPEGNO_DA_AGGIORNARE);
				result.setMsg(msg);				
				return result;
			}
			
		} finally {
			logger.end();
		}
		return result;
	}

	
	
	public EsitoOperazioneConsultaImpegniDTO consultaImpegni(

	java.lang.Long idUtente,

	java.lang.String identitaDigitale,

	it.csi.pbandi.pbservizit.pbandisrv.dto.impegnobilancio.ImpegnoDTO[] filtro

	)
			throws it.csi.csi.wrapper.CSIException,
			it.csi.csi.wrapper.SystemException,
			it.csi.csi.wrapper.UnrecoverableException

			,
			it.csi.pbandi.pbservizit.pbandisrv.exception.impegnobilancio.ImpegnoBilancioException

	{
		logger.begin();
		EsitoOperazioneConsultaImpegniDTO result = new EsitoOperazioneConsultaImpegniDTO();
		result.setEsito(Boolean.FALSE);
		try {
			String[] nameParameter = { "idUtente", "identitaDigitale", "filtro" };

			ValidatorInput.verifyNullValue(nameParameter, idUtente,
					identitaDigitale, filtro);

			List<ImpegnoKeyVO> filtroImpegni = new ArrayList<ImpegnoKeyVO>();
			Long numMaxRecord = new Long(10);

			Long annoEsercizio = filtro[0].getAnnoEsercizio();
			for (int i = 0; i < filtro.length; i++) {
				ImpegnoKeyVO impegnoKey = new ImpegnoKeyVO();
				impegnoKey
						.setAnnoImpegno(filtro[i].getAnnoImpegno().toString());
				impegnoKey.setNumeroImpegno(new Integer(filtro[i]
						.getNumeroImpegno().toString()));
				filtroImpegni.add(impegnoKey);
			}
			
			// un po' di log
			logger.info("consultaImpegni(): parametri input per bilancioDAO.consultaImpegni():");
			logger.info("consultaImpegni():    annoEsercizio = "+annoEsercizio);
			logger.info("consultaImpegni():    num filtri impegni = "+filtroImpegni.size());
			for (ImpegnoKeyVO key : filtroImpegni) {
				logger.info("consultaImpegni():    ImpegnoKeyVO: AnnoImpegno="+key.getAnnoImpegno()+"; NumeroImpegno="+key.getNumeroImpegno());
			}

			List<ImpegnoVO> impegni = null;
			/*
			 * Ricerco l 'impegno dal bilancio
			 */
			try {
				impegni = bilancioDAO.consultaImpegni(filtroImpegni, null,
						null, annoEsercizio, numMaxRecord,idUtente);
			} catch (Exception e) {
				logger.error("consultaImpegni(): errore nel richiamo di bilancioDAO.consultaImpegni(): "+e);
				result.setEsito(Boolean.FALSE);
				MessaggioDTO msg = new MessaggioDTO();
				msg.setKey(ErrorConstants.ERRORE_IMPEGNI_IMPOSSIBILE_REPERIRE_DATI_IMPEGNO);
				result.setMsg(msg);
				return result;
			}

			if (!ObjectUtil.isEmpty(impegni)) {
				ArrayList<ImpegnoDTO> listaImpegniDTO = new ArrayList<ImpegnoDTO>();
				for (ImpegnoVO impegno : impegni) {					
					ImpegnoDTO impegnoDTO = beanUtil.transform(impegno,
							ImpegnoDTO.class,
							BilancioDAO.MAP_IMPEGNOVO_TO_IMPEGNODTO);					
					listaImpegniDTO.add(impegnoDTO);
				}
				result.setImpegno(listaImpegniDTO
						.toArray(new ImpegnoDTO[listaImpegniDTO.size()]));
				result.setEsito(Boolean.TRUE);
			} else {
				logger.error("consultaImpegni(): lista impegni vuota: nessun impegno da aggiornare");
				result.setEsito(Boolean.FALSE);
				MessaggioDTO msg = new MessaggioDTO();
				msg.setKey(ErrorConstants.WARNING_IMPEGNI_NESSUN_IMPEGNO_DA_AGGIORNARE);
				result.setMsg(msg);
				return result;
			}
		} finally {
			logger.end();
		}
		return result;

	}
	
	public String[] findAnniEsercizioValidi(Long idUtente,
			String identitaDigitale) throws CSIException, SystemException,
			UnrecoverableException, ImpegnoBilancioException {
		logger.begin();
		try {
			List<String> result = new ArrayList<String>();
			List<AnnoEsercizioBilancioVO> anni = bilancioManager.findAnniEsercizioForImpegni();
			if (!ObjectUtil.isEmpty(anni)) {
				for (AnnoEsercizioBilancioVO vo : anni) {
					result.add(vo.getAnnoEsercizio());
				}
			}
			return beanUtil.transform(result,String.class);
		} finally {
			logger.end();
		}
		
	}
	
	
	public EsitoOperazioneAssociaImpegnoDTO associaBandolineaImpegno (Long idUtente,
			String identitaDigitale,Long idSoggetto, Long idImpegno, Long[] progrBandolinea) throws CSIException, SystemException,
			UnrecoverableException, ImpegnoBilancioException {
		logger.begin();
		try {
			EsitoOperazioneAssociaImpegnoDTO result = new EsitoOperazioneAssociaImpegnoDTO();
			result.setEsito(Boolean.FALSE);
			String[] nameParameter = { "idUtente", "identitaDigitale","idSoggetto","idImpegno","progrBandolinea" };
			
			ValidatorInput.verifyNullValue(nameParameter, idUtente,
					identitaDigitale,idSoggetto, idImpegno, progrBandolinea);
			
			ValidatorInput.verifyArrayNotEmpty("progrBandolinea", progrBandolinea);
						
			String s = "";
			for (Long bl : progrBandolinea) {
				s += "  " + bl;
			}
			logger.info("\n\nassociaBandolineaImpegno(): idSoggetto = "+idSoggetto+"; idImpegno = "+idImpegno+"; elenco progrBandolinea = "+s+"\n\n");
			
			/*
			 * Ricerco gli enti di competenza associati all' utente
			 */
			List<PbandiREnteCompetenzaSoggVO> entiCompetenzaSoggetto = findEntiAssociatiSoggetto(idSoggetto);
			if (ObjectUtil.isEmpty(entiCompetenzaSoggetto)) {
				logger.warn("Attenzione. Per il soggetto "+idSoggetto+" non sono state trovate enti di competenza associate.");
				MessaggioDTO msg = new MessaggioDTO();
				msg.setKey(ErrorConstants.ERRORE_SERVER);
				result.setMsg(msg);
				return result;
			}
			Map<String,String> mapEnteSoggettoToEnteCapitolo = new HashMap<String, String>();
			mapEnteSoggettoToEnteCapitolo.put("idEnteCompetenza", "idEnteCap");
			
			Map<String,String> mapEnteSoggettoToEnteProvvedimento = new HashMap<String, String>();
			mapEnteSoggettoToEnteProvvedimento.put("idEnteCompetenza", "idEnteProvv");
			
			List<ProgettoImpegnoSoggettoBilVO> entiCapitoloFilterVO = beanUtil.transformList(entiCompetenzaSoggetto, ProgettoImpegnoSoggettoBilVO.class, mapEnteSoggettoToEnteCapitolo);
			List<ProgettoImpegnoSoggettoBilVO> entiProvvedimentoFilterVO = beanUtil.transformList(entiCompetenzaSoggetto, ProgettoImpegnoSoggettoBilVO.class, mapEnteSoggettoToEnteProvvedimento);
						
			/*
			 * Ricerco i progetti legati al bandolinea ed all' impegno
			 */
			ProgettoImpegnoSoggettoBilVO filterVO = new ProgettoImpegnoSoggettoBilVO();
			filterVO.setIdImpegno(NumberUtil.toBigDecimal(idImpegno));
			
			
			List<ProgettoImpegnoSoggettoBilVO> listBandolineaFilterVO = null;
			if (!ObjectUtil.isEmpty(progrBandolinea)) {
				listBandolineaFilterVO = new ArrayList<ProgettoImpegnoSoggettoBilVO>();
				for (Long bl : progrBandolinea) {
					ProgettoImpegnoSoggettoBilVO bandolineaFilter = new ProgettoImpegnoSoggettoBilVO();
					bandolineaFilter.setProgrBandolineaIntervento(NumberUtil.toBigDecimal(bl));
					listBandolineaFilterVO.add(bandolineaFilter);
				}
			}
			List<ProgettoVO> progetti = null;
			if (!ObjectUtil.isEmpty(listBandolineaFilterVO)) {
				 progetti =  genericDAO.findListWhereDistinct(new AndCondition<ProgettoImpegnoSoggettoBilVO>(Condition.filterBy(filterVO),Condition.filterBy(listBandolineaFilterVO), Condition.filterBy(entiCapitoloFilterVO).or(Condition.filterBy(entiProvvedimentoFilterVO))), ProgettoVO.class);
			} else {
				progetti =  genericDAO.findListWhereDistinct(Condition.filterBy(filterVO), ProgettoVO.class);
			}
			
			/*
			 * Cancello i progetti legati all' impegno
			 */
			if (!ObjectUtil.isEmpty(progetti)) {
				for (ProgettoVO p : progetti) {
					PbandiRImpegnoProgettoVO toDeleteVO = new PbandiRImpegnoProgettoVO();
					toDeleteVO.setIdImpegno(NumberUtil.toBigDecimal(idImpegno));
					toDeleteVO.setIdProgetto(p.getIdProgetto());
					try {
						genericDAO.delete(toDeleteVO);
					} catch (Exception e) {
						logger.error("Errore durante la cancellazione del progetto dalla PBANDI_R_IMPEGNO_PROGETTO con idImpegno = "+idImpegno+" e IdProgetto = "+p.getIdProgetto(),e);
						MessaggioDTO msg = new MessaggioDTO();
						msg.setKey(ErrorConstants.ERRORE_SERVER);
						result.setMsg(msg);
						return result;
					}
				}
			}
			
			/*
			 * Inserisco la relazione tra i bandolinea e l'impegno
			 */
			if (!ObjectUtil.isEmpty(progrBandolinea)) {
				for (Long progr : progrBandolinea) {
					PbandiRImpegnoBandoLineaVO toInsertVO = new PbandiRImpegnoBandoLineaVO();
					toInsertVO.setIdImpegno(NumberUtil.toBigDecimal(idImpegno));					
					toInsertVO.setProgrBandoLineaIntervento(NumberUtil.toBigDecimal(progr));
					if (nonEsiste(toInsertVO)) {
						toInsertVO.setIdUtenteIns(NumberUtil.toBigDecimal(idUtente));
						try {
							genericDAO.insert(toInsertVO);
						} catch (Exception e) {
							logger.error("Errore durante l'inserimento del bandolinea nella PBANDI_R_IMPEGNO_BANDO_LINEA con idImpegno = "+idImpegno+" e ProgrBandoLinea = "+progr,e);
							MessaggioDTO msg = new MessaggioDTO();
							msg.setKey(ErrorConstants.ERRORE_SERVER);
							result.setMsg(msg);
							return result;
						}
					}
				}
			} else {
				logger.warn("Nessun bandolinea da associare all'impegno["+idImpegno+"].");
			}
			
			logger.info("\n\nassociaBandolineaImpegno(): FINE \n\n");
			
			result.setEsito(Boolean.TRUE);
			MessaggioDTO msg = new MessaggioDTO();
			msg.setKey(MessaggiConstants.SALVATAGGIO_AVVENUTO_CON_SUCCESSO);
			result.setMsg(msg);
			return result;
			
		} finally {
			logger.end();
		}
		
	}
	
	private boolean nonEsiste(PbandiRImpegnoBandoLineaVO vo) {
		List<PbandiRImpegnoBandoLineaVO> lista = genericDAO.findListWhere(vo);
		return (lista.size() == 0);
	}
	
	private boolean nonEsiste(PbandiRImpegnoProgettoVO vo) {
		List<PbandiRImpegnoProgettoVO> lista = genericDAO.findListWhere(vo);
		return (lista.size() == 0);
	}
	
	
	public EsitoOperazioneAssociaImpegnoDTO associaProgettiImpegno (Long idUtente,
			String identitaDigitale, Long idImpegno, Long [] idProgetti) throws CSIException, SystemException,
			UnrecoverableException, ImpegnoBilancioException {
		logger.begin();
		try {
			EsitoOperazioneAssociaImpegnoDTO result = new EsitoOperazioneAssociaImpegnoDTO();
			result.setEsito(Boolean.FALSE);
			String[] nameParameter = { "idUtente", "identitaDigitale","idImpegno","idProgetti" };
			
			ValidatorInput.verifyNullValue(nameParameter, idUtente,
					identitaDigitale, idImpegno, idProgetti);
			
			ValidatorInput.verifyArrayNotEmpty("idProgetti", idProgetti);
			
			String s = "";
			for (Long id : idProgetti) {
				s += "  " + id;
			}
			logger.info("\n\nassociaProgettiImpegno(): idImpegno = "+idImpegno+"; elenco idProgetti = "+s+"\n\n");	
			
			if (!ObjectUtil.isEmpty(idProgetti)) {
				for (Long idP : idProgetti) {
					PbandiRImpegnoProgettoVO toInsertVO = new PbandiRImpegnoProgettoVO();
					toInsertVO.setIdProgetto(NumberUtil.toBigDecimal(idP));
					toInsertVO.setIdImpegno(NumberUtil.toBigDecimal(idImpegno));
					if (nonEsiste(toInsertVO)) {
						toInsertVO.setIdUtenteIns(NumberUtil.toBigDecimal(idUtente));
						try {
							genericDAO.insert(toInsertVO);
						} catch (Exception e) {
							logger.error("Errore durante l'inserimento del progetto nella PBANDI_R_IMPEGNO_PROGETTO  con idImpegno = "+idImpegno+" e IdProgetto = "+idP,e);
							MessaggioDTO msg = new MessaggioDTO();
							msg.setKey(ErrorConstants.ERRORE_SERVER);
							result.setMsg(msg);
							return result;
						}
					}
				}
			} else {
				logger.warn("Nessun progetto da associare all'impegno["+idImpegno+"].");
			}
			
			logger.info("\n\nassociaProgettiImpegno(): FINE \n\n");
			
			result.setEsito(Boolean.TRUE);
			MessaggioDTO msg = new MessaggioDTO();
			msg.setKey(MessaggiConstants.SALVATAGGIO_AVVENUTO_CON_SUCCESSO);
			result.setMsg(msg);
			return result;
		} finally {
			logger.end();
		}
	}
	
	public EsitoOperazioneAssociaImpegnoDTO associaBandolineaImpegnoGestAss(Long idUtente,
			String identitaDigitale,Long idSoggetto, Long[] idImpegni, Long[] progrBandolinea) throws CSIException, SystemException,
			UnrecoverableException, ImpegnoBilancioException {
		logger.begin();
		try {
			EsitoOperazioneAssociaImpegnoDTO result = new EsitoOperazioneAssociaImpegnoDTO();
			result.setEsito(Boolean.FALSE);
			
			String[] nameParameter = { "idUtente", "identitaDigitale","idSoggetto","idImpegni","progrBandolinea" };			
			ValidatorInput.verifyNullValue(nameParameter, idUtente,
					identitaDigitale,idSoggetto, idImpegni, progrBandolinea);			
			ValidatorInput.verifyArrayNotEmpty("progrBandolinea", progrBandolinea);
			ValidatorInput.verifyArrayNotEmpty("idImpegni", idImpegni);
						
			String s1 = "";
			for (Long im : idImpegni) {
				s1 += "  " + im;
			}
			String s2 = "";
			for (Long bl : progrBandolinea) {
				s2 += "  " + bl;
			}
			logger.info("\n\nassociaBandolineaImpegnoGestAss(): idSoggetto = "+idSoggetto+"; elenco idImpegno = "+s1+"; elenco progrBandolinea = "+s2+"\n\n");
			
			for (Long idImpegno : idImpegni) {
				EsitoOperazioneAssociaImpegnoDTO esito = this.associaBandolineaImpegno(idUtente, identitaDigitale, idSoggetto, idImpegno, progrBandolinea);
				if (esito == null || !esito.getEsito())  {
					MessaggioDTO msg = new MessaggioDTO();
					msg.setKey(ErrorConstants.ERRORE_SERVER);
					result.setMsg(msg);
					logger.info("\n\nassociaBandolineaImpegnoGestAss(): FINE CON ERRORE \n\n");
					return result;
				}
			}
			
			logger.info("\n\nassociaBandolineaImpegnoGestAss(): FINE \n\n");
			
			result.setEsito(Boolean.TRUE);
			MessaggioDTO msg = new MessaggioDTO();
			msg.setKey(MessaggiConstants.SALVATAGGIO_AVVENUTO_CON_SUCCESSO);
			result.setMsg(msg);
			return result;
			
		} finally {
			logger.end();
		}
		
	}
	
	
	public EsitoOperazioneAssociaImpegnoDTO associaProgettiImpegnoGestAss(Long idUtente,
			String identitaDigitale, Long[] idImpegni, Long[] idProgetti) throws CSIException, SystemException,
			UnrecoverableException, ImpegnoBilancioException {
		logger.begin();
		try {
			EsitoOperazioneAssociaImpegnoDTO result = new EsitoOperazioneAssociaImpegnoDTO();
			result.setEsito(Boolean.FALSE);
			
			String[] nameParameter = { "idUtente", "identitaDigitale","idImpegni","idProgetti" };			
			ValidatorInput.verifyNullValue(nameParameter, idUtente,
					identitaDigitale, idImpegni, idProgetti);			
			ValidatorInput.verifyArrayNotEmpty("idProgetti", idProgetti);
			ValidatorInput.verifyArrayNotEmpty("idImpegni", idImpegni);
			
			String s1 = "";
			for (Long im : idImpegni) {
				s1 += "  " + im;
			}
			String s2 = "";
			for (Long id : idProgetti) {
				s2 += "  " + id;
			}
			logger.info("\n\nassociaProgettiImpegnoGestAss(): elenco idImpegno = "+s1+"; elenco idProgetti = "+s2+"\n\n");
	
			for (Long idImpegno : idImpegni) {
				EsitoOperazioneAssociaImpegnoDTO esito = this.associaProgettiImpegno(idUtente, identitaDigitale, idImpegno, idProgetti);
				if (esito == null || !esito.getEsito())  {
					MessaggioDTO msg = new MessaggioDTO();
					msg.setKey(ErrorConstants.ERRORE_SERVER);
					result.setMsg(msg);
					logger.info("\n\nassociaProgettiImpegnoGestAss(): FINE CON ERRORE \n\n");
					return result;
				}
			}

			
			logger.info("\n\nassociaProgettiImpegnoGestAss(): FINE \n\n");
			
			result.setEsito(Boolean.TRUE);
			MessaggioDTO msg = new MessaggioDTO();
			msg.setKey(MessaggiConstants.SALVATAGGIO_AVVENUTO_CON_SUCCESSO);
			result.setMsg(msg);
			return result;
		} finally {
			logger.end();
		}
	}
	
	public Boolean hasImpegniAssociatiProgetto (Long idUtente,
			String identitaDigitale,Long idProgetto) throws CSIException, SystemException,
			UnrecoverableException, ImpegnoBilancioException {
		logger.begin();
		boolean result = false;
		String[] nameParameter = { "idUtente", "identitaDigitale","idProgetto"};
		
		ValidatorInput.verifyNullValue(nameParameter, idUtente,
				identitaDigitale, idProgetto);
		try {
			result = bilancioManager.hasImpegniAssociatiProgetto(idProgetto);
		} finally {
			logger.end();
		}
		return result;
	}
	
	public Boolean hasImpegniAssociatiBandolinea (Long idUtente,
			String identitaDigitale,Long progrBandolinea) throws CSIException, SystemException,
			UnrecoverableException, ImpegnoBilancioException {
		logger.begin();
		boolean result = false;
		String[] nameParameter = { "idUtente", "identitaDigitale","progrBandolinea"};
		
		ValidatorInput.verifyNullValue(nameParameter, idUtente,
				identitaDigitale, progrBandolinea);
		try {
			result = bilancioManager.hasImpegniAssociatiBandolinea(progrBandolinea);
		} finally {
			logger.end();
		}
		return result;
	}
	
	
	private BeneficiarioProgettoVO findBeneficiarioProgetto (BigDecimal idPrj) {
		BeneficiarioProgettoVO result = null;
		 BeneficiarioProgettoVO beneficiarioFilterVO = new BeneficiarioProgettoVO();
		 beneficiarioFilterVO.setIdProgetto(idPrj);
		 AndCondition<BeneficiarioProgettoVO> andCondition = new AndCondition<BeneficiarioProgettoVO>(Condition.filterBy(beneficiarioFilterVO),Condition.validOnly(BeneficiarioProgettoVO.class));
		 try {
			 result =  genericDAO.findSingleWhere(andCondition);
			 
		 } catch (RecordNotFoundException rnfe) {
			 logger.warn("Beneficiario del progetto associato all' impegno non trovato."+rnfe.getMessage());
		 }	
		 return result;
	}
	
	
	
	private List<BandolineaImpegnoBilVO> getBandolineaDaAssociare(Long idEnteCompetenza, Long[] idImpegno, Long progrBandolinea) {
		
		/*
		 * Ricerco i bandolinea associati all'impegno. Questi bandolinea non
		 * dovranno comparire nella lista dei bandolinea
		 */
		List<PbandiRImpegnoBandoLineaVO> listBandiAssociati = getBandolineaAssociati(idImpegno);
		
		
		/*
		 * Ricerco i bandolinea associati alla regola BR37
		 */
		List<RegolaAssociataBandoLineaVO> listBandolineaAssociatiRegola37 = getBandolineaAssociatiRegola(RegoleConstants.BR37_ATTIVITA_LIQUIDAZIONE_CONTRIBUTO);
		
		
		
		
		List<BandolineaImpegnoDTO> result = new ArrayList<BandolineaImpegnoDTO>();
		BandolineaImpegnoBilVO filterVO = new BandolineaImpegnoBilVO();
		filterVO.setIdEnteBandol(NumberUtil.toBigDecimal(idEnteCompetenza));
		filterVO.setProgrBandolineaIntervento(NumberUtil.toBigDecimal(progrBandolinea));
		filterVO.setDescBreveRuoloEnteBandol("RESP. BILANCIO");
		filterVO.setAscendentOrder("nomeBandolinea");
		
		// codice vecchio.
		//BandolineaImpegnoBilVO notFilter = new BandolineaImpegnoBilVO();
		//notFilter.setIdImpegno(NumberUtil.toBigDecimal(idImpegno));
		// codice nuovo.
		ArrayList<BandolineaImpegnoBilVO> notFilter = new ArrayList<BandolineaImpegnoBilVO>(); 
		for (int i = 0; i < idImpegno.length; i++) {
			BandolineaImpegnoBilVO vo = new BandolineaImpegnoBilVO();
			vo.setIdImpegno(new BigDecimal(idImpegno[i]));
			notFilter.add(vo);
		}
		
		AndCondition<BandolineaImpegnoBilVO> andCondition = null;
		AndCondition<BandolineaImpegnoBilVO> andConditionBandi = null;
		if (!ObjectUtil.isEmpty(listBandiAssociati)) {
			List<BandolineaImpegnoBilVO> bandolineaAssociatiFilter = new ArrayList<BandolineaImpegnoBilVO>();
			for(PbandiRImpegnoBandoLineaVO bando : listBandiAssociati) {
				BandolineaImpegnoBilVO vo = new BandolineaImpegnoBilVO();
				vo.setProgrBandolineaIntervento(bando.getProgrBandoLineaIntervento());
				bandolineaAssociatiFilter.add(vo);
			}
			andConditionBandi = new AndCondition<BandolineaImpegnoBilVO>(Condition.filterBy(filterVO),Condition.not(Condition.filterBy(notFilter)), Condition.not(Condition.filterBy(bandolineaAssociatiFilter)));
		} else {
			andConditionBandi = new AndCondition<BandolineaImpegnoBilVO>(Condition.filterBy(filterVO),Condition.not(Condition.filterBy(notFilter)));
		}
		
		/*
		 * Sono visualizzati solo i bandolinea che hanno associati la regola BR37.
		 */
		if (!ObjectUtil.isEmpty(listBandolineaAssociatiRegola37)) {
			List<BandolineaImpegnoBilVO> bandolineaAssociatiBR37Filter = new ArrayList<BandolineaImpegnoBilVO>();
			for(RegolaAssociataBandoLineaVO bando : listBandolineaAssociatiRegola37) {
				BandolineaImpegnoBilVO vo = new BandolineaImpegnoBilVO();
				vo.setProgrBandolineaIntervento(bando.getProgrBandoLineaIntervento());
				bandolineaAssociatiBR37Filter.add(vo);
			}
			andCondition = new AndCondition<BandolineaImpegnoBilVO>(andConditionBandi,Condition.filterBy(bandolineaAssociatiBR37Filter));
		} else {
			andCondition = new AndCondition<BandolineaImpegnoBilVO>(andConditionBandi,null);
		}
		
		
		
		List<BandolineaImpegnoBilVO> bandolinea = genericDAO.findListWhereDistinct(andCondition, BandolineaImpegnoBilVO.class);
		
		return bandolinea;
	}

	private List<BandolineaImpegnoBilVO> getBandolineaDaAssociareSenzaImpegni(Long idEnteCompetenza, Long progrBandolinea) {

		/*
		 * Ricerco i bandolinea associati alla regola BR37
		 */
		List<RegolaAssociataBandoLineaVO> listBandolineaAssociatiRegola37 = getBandolineaAssociatiRegola(RegoleConstants.BR37_ATTIVITA_LIQUIDAZIONE_CONTRIBUTO);
				
		BandolineaImpegnoBilVO filterVO = new BandolineaImpegnoBilVO();
		filterVO.setIdEnteBandol(NumberUtil.toBigDecimal(idEnteCompetenza));
		filterVO.setProgrBandolineaIntervento(NumberUtil.toBigDecimal(progrBandolinea));
		filterVO.setDescBreveRuoloEnteBandol("RESP. BILANCIO");
		filterVO.setAscendentOrder("nomeBandolinea");
		
		//AndCondition<BandolineaImpegnoBilVO> andConditionBandi = null;
		//andConditionBandi = new AndCondition<BandolineaImpegnoBilVO>(Condition.filterBy(filterVO),null);
		
		/*
		 * Sono visualizzati solo i bandolinea che hanno associati la regola BR37.
		 */
		AndCondition<BandolineaImpegnoBilVO> andCondition = null;
		if (!ObjectUtil.isEmpty(listBandolineaAssociatiRegola37)) {
			List<BandolineaImpegnoBilVO> bandolineaAssociatiBR37Filter = new ArrayList<BandolineaImpegnoBilVO>();
			for(RegolaAssociataBandoLineaVO bando : listBandolineaAssociatiRegola37) {
				BandolineaImpegnoBilVO vo = new BandolineaImpegnoBilVO();
				vo.setProgrBandolineaIntervento(bando.getProgrBandoLineaIntervento());
				bandolineaAssociatiBR37Filter.add(vo);
			}
			andCondition = new AndCondition<BandolineaImpegnoBilVO>(Condition.filterBy(filterVO),Condition.filterBy(bandolineaAssociatiBR37Filter));
		} else {
			andCondition = new AndCondition<BandolineaImpegnoBilVO>(Condition.filterBy(filterVO),null);
		}
		
		List<BandolineaImpegnoBilVO> bandolinea = genericDAO.findListWhereDistinct(andCondition, BandolineaImpegnoBilVO.class);
		
		return bandolinea;
	}
	
}