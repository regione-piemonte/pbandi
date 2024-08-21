/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.business.manager;

import it.csi.pbandi.pbservizit.pbandisrv.dto.manager.RappresentanteLegaleDTO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.dao.GenericDAO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.dao.PbandiDichiarazioneDiSpesaDAOImpl;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.dichiarazionedispesa.RappresentanteLegaleDichiarazioneVO;
import it.csi.pbandi.pbservizit.pbandiutil.common.BeanUtil;
import it.csi.pbandi.pbservizit.pbandiutil.common.LoggerUtil;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

public class RappresentanteLegaleManager {
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

	public void setPbandiDichiarazioneDiSpesaDAO(
			PbandiDichiarazioneDiSpesaDAOImpl pbandiDichiarazioneDiSpesaDAO) {
		this.pbandiDichiarazioneDiSpesaDAO = pbandiDichiarazioneDiSpesaDAO;
	}

	public PbandiDichiarazioneDiSpesaDAOImpl getPbandiDichiarazioneDiSpesaDAO() {
		return pbandiDichiarazioneDiSpesaDAO;
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


	public RappresentanteLegaleDTO findRappresentanteLegale(
			Long idProgetto,Long idSoggettoRappresentante) {		 
			RappresentanteLegaleDichiarazioneVO rappLegaleVO = new RappresentanteLegaleDichiarazioneVO();
			rappLegaleVO.setIdProgetto(idProgetto);
			rappLegaleVO.setIdSoggetto(idSoggettoRappresentante);
			List<RappresentanteLegaleDichiarazioneVO> rappresentantiVO = genericDAO
					.findListWhere(rappLegaleVO);
			RappresentanteLegaleDichiarazioneVO rapprVO = new RappresentanteLegaleDichiarazioneVO();
			if (rappresentantiVO != null && rappresentantiVO.size() > 0) {
				logger.warn("********** Found "+rappresentantiVO.size()+" for idProgetto:"+idProgetto+" and idSoggettoRappresentante:"+idSoggettoRappresentante+" *****");
				rapprVO = rappresentantiVO.get(0);
			}

			return getBeanUtil().transform(rapprVO,RappresentanteLegaleDTO.class);
	}
	
	
	public List<RappresentanteLegaleDTO> findRappresentantiLegali(
			Long idProgetto,Long idSoggettoRappresentante) {
	 			RappresentanteLegaleDichiarazioneVO rappLegaleVO = new RappresentanteLegaleDichiarazioneVO();
			rappLegaleVO.setIdProgetto(idProgetto);
			rappLegaleVO.setIdSoggetto(idSoggettoRappresentante);
			List<RappresentanteLegaleDichiarazioneVO> rappresentantiVO = genericDAO.findListWhere(rappLegaleVO);
			return getBeanUtil().transformList(rappresentantiVO,RappresentanteLegaleDTO.class);	 
	}

	






}
