/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.business.manager;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;

import it.csi.pbandi.pbservizit.pbandisrv.dto.manager.report.EnteAppartenenzaDTO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.dao.GenericDAO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.dao.PbandiDichiarazioneDiSpesaDAOImpl;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.EnteDiAppartenenzaVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.dichiarazionedispesa.EnteAppartenenzaVO;
import it.csi.pbandi.pbservizit.pbandisrv.interfacecsi.dichiarazionedispesa.DichiarazioneDiSpesaSrv;
import it.csi.pbandi.pbservizit.pbandiutil.common.BeanUtil;
import it.csi.pbandi.pbservizit.pbandiutil.common.LoggerUtil;

public class EnteManager {
	@Autowired
	private BeanUtil beanUtil;
	@Autowired
	private DecodificheManager decodificheManager;
	@Autowired
	private GenericDAO genericDAO;
	@Autowired
	private LoggerUtil logger;
	@Autowired
	private PbandiDichiarazioneDiSpesaDAOImpl pbandiDichiarazioneDiSpesaDAO;
	
	
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

	public void setPbandiDichiarazioneDiSpesaDAO(
			PbandiDichiarazioneDiSpesaDAOImpl pbandiDichiarazioneDiSpesaDAO) {
		this.pbandiDichiarazioneDiSpesaDAO = pbandiDichiarazioneDiSpesaDAO;
	}

	public PbandiDichiarazioneDiSpesaDAOImpl getPbandiDichiarazioneDiSpesaDAO() {
		return pbandiDichiarazioneDiSpesaDAO;
	}



	public EnteDiAppartenenzaVO findEnteDestinatario(Long idProgetto) {
		return findEnteAppartenenza(idProgetto, DichiarazioneDiSpesaSrv.CODICE_RUOLO_ENTE_DESTINATARIO);
	}

	
	private EnteDiAppartenenzaVO findEnteAppartenenza(Long idProgetto,
			String codiceTipoRuoloEnte) {
			EnteDiAppartenenzaVO enteVO = new EnteDiAppartenenzaVO();
			enteVO.setIdProgetto(beanUtil.transform(idProgetto, BigDecimal.class));
			enteVO.setDescBreveRuoloEnte(codiceTipoRuoloEnte);
			enteVO = genericDAO.findSingleWhere(enteVO);
			return enteVO;		 
	}





}
