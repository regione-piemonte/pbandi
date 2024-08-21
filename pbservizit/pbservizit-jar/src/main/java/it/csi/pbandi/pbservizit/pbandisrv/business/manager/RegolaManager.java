/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.business.manager;

import it.csi.pbandi.pbservizit.pbandisrv.exception.FormalParameterException;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.dao.GenericDAO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.AttributoVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.BandoLineaVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.RegolaAssociataBandoLineaVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.util.Condition;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiCRegolaVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiRRegolaBandoLineaVO;
import it.csi.pbandi.pbservizit.pbandisrv.util.validation.ValidatorInput;
import it.csi.pbandi.pbservizit.pbandiutil.common.BeanUtil;
import it.csi.pbandi.pbservizit.pbandiutil.common.LoggerUtil;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

public class RegolaManager {
	
	@Autowired
	private LoggerUtil logger;
	
	@Autowired
	private BeanUtil beanUtil;
	
	@Autowired
	private GenericDAO genericDAO;

	@Autowired
	private ProgettoManager progettoManager;
	
	@Autowired
	private TimerManager timerManager;

	private Map cache = new HashMap();
	private long start = 0;
	private long timeOut = 60 * 5 * 1000;

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

	public void setProgettoManager(ProgettoManager progettoManager) {
		this.progettoManager = progettoManager;
	}

	public ProgettoManager getProgettoManager() {
		return progettoManager;
	}

	public TimerManager getTimerManager() {
		return timerManager;
	}

	public void setTimerManager(TimerManager timerManager) {
		this.timerManager = timerManager;
	}

	/**
	 * 
	 * @param idBandoLinea
	 * @param codiceRegola
	 * @return
	 * @throws FormalParameterException
	 */
	public boolean isRegolaApplicabileForBandoLinea(Long idBandoLinea,
			String codiceRegola) throws FormalParameterException {

		Boolean verifica = true;
		try {
			String[] nameParameter = { "idBando", "codiceRegola" };
			ValidatorInput.verifyNullValue(nameParameter, idBandoLinea,	codiceRegola);

			String key = "" + idBandoLinea + "-" + codiceRegola;

			if ((System.currentTimeMillis() - start > timeOut)) {
				// LA CACHE BISOGNA PULIRLA $%@�@*#@!!!!
				cache = new HashMap();
				start = System.currentTimeMillis();
			}

			if (cache.containsKey(key)) {
				verifica = (Boolean) cache.get(key);
				// FIXME rimuovere a regime i log
				logger.debug("cache --> " + key );
			} else {
				verifica = isRegolaApplicabile(idBandoLinea, codiceRegola);
				cache.put(key, verifica);
				// FIXME rimuovere a regime i log
				logger.debug("no cache --> " + key );
			}

		} catch (Exception e) {
			logger.error("Error " + e.getMessage(), e);
		}  

		return verifica;
	}

	private boolean isRegolaApplicabile(Long idBandoLinea, String codiceRegola) {
		PbandiCRegolaVO regolaVO = new PbandiCRegolaVO();
		regolaVO.setDescBreveRegola(codiceRegola);

		PbandiRRegolaBandoLineaVO regolaBandoLineaVO = new PbandiRRegolaBandoLineaVO();
		regolaBandoLineaVO.setProgrBandoLineaIntervento(BigDecimal.valueOf(idBandoLinea));

		return genericDAO
				.join(Condition.filterBy(regolaVO),
						Condition.filterBy(regolaBandoLineaVO)).by("idRegola")
				.count() > 0;
	}

	public boolean isRegolaApplicabileForProgetto(Long idProgetto,
			String codiceRegola) throws FormalParameterException {

		logger.debug("isRegolaApplicabileForProgetto idProgetto="+idProgetto + ", codiceRegola="+codiceRegola);
		
		boolean verifica = true;
		try {
			String[] nameParameter = { "idProgetto", "codiceRegola" };
			ValidatorInput.verifyNullValue(nameParameter, idProgetto, codiceRegola);

			verifica = isRegolaApplicabile(progettoManager.getIdBandoLinea(idProgetto), codiceRegola);

		} catch (Exception e) {
			logger.error("Error " + e.getMessage(), e);
			System.out.println("Error " + e.getMessage());
			e.printStackTrace();
		} 
		logger.debug("isRegolaApplicabileForProgetto verifica="+verifica);
		return verifica;
	}

	public List<RegolaAssociataBandoLineaVO> findBandolineaAssociatiRegola(
			String codiceRegola) throws Exception {
		List<RegolaAssociataBandoLineaVO> result = null;
		try {
			String[] nameParameter = { "codiceRegola" };
			ValidatorInput.verifyNullValue(nameParameter, codiceRegola);
			RegolaAssociataBandoLineaVO regolaBandoLineaVO = new RegolaAssociataBandoLineaVO();
			regolaBandoLineaVO.setDescBreveRegola(codiceRegola);
			result = genericDAO.findListWhere(Condition
					.filterBy(regolaBandoLineaVO));

		} catch (Exception e) {
			logger.error(
					"Errore nella ricerca dei bandolinea associati alla regola "
							+ codiceRegola, e);
			throw e;
		}  
		return result;
	}
}
