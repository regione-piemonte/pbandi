/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.pbandisrv.business.manager;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import it.csi.pbandi.pbweb.pbandisrv.dto.recupero.DettaglioRecuperoDTO;
import it.csi.pbandi.pbweb.pbandisrv.exception.FormalParameterException;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.dao.GenericDAO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.ErogazioneCausaleModalitaAgevolazioneVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.recupero.RecuperoProgettoVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.revoca.ModalitaAgevolazioneTotaleRevocheVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.revoca.RevocaProgettoVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.revoca.TotaleRevocaProgettoVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiDTipoRecuperoVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiTDettPropostaCertifVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiTRecuperoVO;
import it.csi.pbandi.pbweb.pbandisrv.util.Constants;
import it.csi.pbandi.pbweb.pbandisrv.util.validation.ValidatorInput;
import it.csi.pbandi.pbweb.pbandiutil.common.BeanUtil;
import it.csi.pbandi.pbweb.pbandiutil.common.LoggerUtil;
import it.csi.pbandi.pbweb.pbandiutil.common.NumberUtil;

public class RevocaRecuperoManager {
	
	@Autowired
	private LoggerUtil logger;
	@Autowired
	private BeanUtil beanUtil;
	@Autowired
	private GenericDAO genericDAO;
	@Autowired
	protected DecodificheManager decodificheManager;

	
	
	public DecodificheManager getDecodificheManager() {
		return decodificheManager;
	}
	public void setDecodificheManager(DecodificheManager decodificheManager) {
		this.decodificheManager = decodificheManager;
	}
	
	public DettaglioRecuperoDTO[] findSoppressioniManager(Long idProgetto) {
		PbandiTRecuperoVO query = new PbandiTRecuperoVO();
		query.setIdProgetto(beanUtil
				.transform(idProgetto, BigDecimal.class));
		query.setIdTipoRecupero(decodificheManager.decodeDescBreve(
				PbandiDTipoRecuperoVO.class,
				Constants.COD_FIDEIUSSIONI_TIPO_RECUPERO_SOPPRESSIONE));
		return beanUtil.transform(genericDAO.where(query).select(),
				DettaglioRecuperoDTO.class);
	}
	/**
	 * Ricerca tutte le revoche associate ad un progetto ( ed eventualmente anche ad una modalita' di agevolazione).
	 * Valorizzando l'idRevoca si esegue una ricerca puntuale della revoca
	 * @param idProgetto
	 * @param idModalitaAgevolazione
	 * @param idRevoca
	 * @return
	 * @throws FormalParameterException
	 */
	public List<RevocaProgettoVO> findRevocheProgetto (Long idProgetto, Long idModalitaAgevolazione, Long idRevoca) throws FormalParameterException {
		logger.begin();
		String[] nameParameter = { "idProgetto","idModalitaAgevolazione"};
		ValidatorInput.verifyNullValue(nameParameter, idProgetto, idModalitaAgevolazione);
		RevocaProgettoVO filtroVO = new RevocaProgettoVO();
		filtroVO.setIdProgetto(NumberUtil.toBigDecimal(idProgetto));
		filtroVO.setIdModalitaAgevolazione(NumberUtil.toBigDecimal(idModalitaAgevolazione));
		filtroVO.setIdRevoca(NumberUtil.toBigDecimal(idRevoca));
		filtroVO.setAscendentOrder("dtRevoca","descMotivoRevoca");
		logger.end();
		return genericDAO.findListWhere(filtroVO);
	}
	
	
	/**
	 * Ricerca tutti i recuperi associati ad un progetto ( ed eventualmente anche ad una modalita' di agevolazione).
	 * Valorizzando l' idRecupero si esegue una ricerca puntuale del recupero
	 * @param idProgetto
	 * @param idModalitaAgevolazione
	 * @param idRecupero
	 * @return
	 * @throws FormalParameterException
	 */
	public List<RecuperoProgettoVO> findRecuperiProgetto(Long idProgetto, Long idModalitaAgevolazione, Long idRecupero) throws FormalParameterException {
		logger.begin();
		String[] nameParameter = { "idProgetto","idModalitaAgevolazione"};
		ValidatorInput.verifyNullValue(nameParameter, idProgetto, idModalitaAgevolazione);
		RecuperoProgettoVO filtro = new RecuperoProgettoVO();
		filtro.setIdProgetto(NumberUtil.toBigDecimal(idProgetto));
		filtro.setIdModalitaAgevolazione(NumberUtil.toBigDecimal(idModalitaAgevolazione));
		filtro.setIdRecupero(NumberUtil.toBigDecimal(idRecupero));
		filtro.setAscendentOrder("dtRecupero","descTipoRecupero");
		logger.end();
		return genericDAO.findListWhere(filtro);
	}
	
	
	/**
	 * Ricerca tutte le causali delle modalita' di agevolazioni legate ad un progetto
	 * @param idProgetto
	 * @param idModalitaAgevolazione
	 * @return
	 * @throws FormalParameterException
	 */
	public List<ErogazioneCausaleModalitaAgevolazioneVO> findCausaliModalitaAgevolazione ( Long idProgetto, Long idModalitaAgevolazione) throws FormalParameterException {
		logger.begin();
		String[] nameParameter = { "idProgetto","idModalitaAgevolazione"};
		ValidatorInput.verifyNullValue(nameParameter, idProgetto, idModalitaAgevolazione);
		ErogazioneCausaleModalitaAgevolazioneVO causaleModalitaAgevolazioneVO = new ErogazioneCausaleModalitaAgevolazioneVO();
		causaleModalitaAgevolazioneVO.setIdModalitaAgevolazione(NumberUtil.toBigDecimal(idModalitaAgevolazione));
		causaleModalitaAgevolazioneVO.setIdProgetto(NumberUtil.toBigDecimal(idProgetto));
		causaleModalitaAgevolazioneVO.setAscendentOrder("dtContabile","descCausale");
		logger.end();
		return  genericDAO.findListWhere(causaleModalitaAgevolazioneVO);
	}
	
	
	/**
	 * Ricerca il totale delle revoche e dei recuperi legati alle modalita' del progetto.
	 * @param idProgetto
	 * @param idModalitaAgevolazione
	 * @return 
	 * @throws FormalParameterException
	 */
	public ModalitaAgevolazioneTotaleRevocheVO findTotaleRevocheRecuperi (Long idProgetto, Long idModalitaAgevolazione) throws FormalParameterException {
		logger.begin();
		String[] nameParameter = { "idProgetto","idModalitaAgevolazione"};
		ValidatorInput.verifyNullValue(nameParameter, idProgetto, idModalitaAgevolazione);
		ModalitaAgevolazioneTotaleRevocheVO totaleModalitaVO = new ModalitaAgevolazioneTotaleRevocheVO();
		totaleModalitaVO.setIdModalitaAgevolazione(NumberUtil.toBigDecimal(idModalitaAgevolazione));
		totaleModalitaVO.setIdProgetto(NumberUtil.toBigDecimal(idProgetto));
		logger.end();
		return genericDAO.findSingleWhere(totaleModalitaVO);
	}

	public List<TotaleRevocaProgettoVO> findTotaleRevocheSulProgetto (Long idProgetto) throws FormalParameterException {
		String[] nameParameter = { "idProgetto"};
		ValidatorInput.verifyNullValue(nameParameter, idProgetto);
		TotaleRevocaProgettoVO totaleModalitaVO = new TotaleRevocaProgettoVO();
		totaleModalitaVO.setIdProgetto(NumberUtil.toBigDecimal(idProgetto));
		return genericDAO.findListWhere(totaleModalitaVO);
	}

	
	/**
	 * Verifica se per il progetto in oggetto esiste una proposta di certificazione
	 * @param idProgetto
	 * @return
	 * @throws FormalParameterException 
	 */
	public Boolean checkPropostaCertificazioneProgetto (Long idProgetto) throws FormalParameterException {
		String[] nameParameter = { "idProgetto" };
		ValidatorInput.verifyNullValue(nameParameter,idProgetto);
		/*
		 * Verifico se per il progetto esiste
		 * una proposta di certificazione
		 */
		PbandiTDettPropostaCertifVO filtroVO = new PbandiTDettPropostaCertifVO();
		filtroVO.setIdProgetto(NumberUtil.toBigDecimal(idProgetto));
		List<PbandiTDettPropostaCertifVO> listProposteCertificazione = genericDAO.findListWhere(filtroVO);
		if (listProposteCertificazione != null && !listProposteCertificazione.isEmpty()) 
			return Boolean.TRUE;
		else
			return Boolean.FALSE;
	}


	public LoggerUtil getLogger() {
		return logger;
	}


	public void setLogger(LoggerUtil logger) {
		this.logger = logger;
	}


	public BeanUtil getBeanUtil() {
		return beanUtil;
	}


	public void setBeanUtil(BeanUtil beanUtil) {
		this.beanUtil = beanUtil;
	}


	public GenericDAO getGenericDAO() {
		return genericDAO;
	}


	public void setGenericDAO(GenericDAO genericDAO) {
		this.genericDAO = genericDAO;
	}

}
