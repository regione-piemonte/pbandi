/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.business.manager;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import it.csi.pbandi.pbservizit.pbandisrv.integration.db.dao.GenericDAO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.VoceDiSpesaAssociataVO;
import it.csi.pbandi.pbservizit.pbandiutil.common.BeanUtil;
import it.csi.pbandi.pbservizit.pbandiutil.common.LoggerUtil;

public class VoceDiSpesaManager {
	private LoggerUtil logger;
	private BeanUtil beanUtil;

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
	private GenericDAO genericDAO;
	
	
	public List<VoceDiSpesaAssociataVO> findVociDiSpesaAssociate(VoceDiSpesaAssociataVO filtro) {
		List<VoceDiSpesaAssociataVO> voci = getGenericDAO().findListWhere(filtro);
		return voci;
	}


	public void setGenericDAO(GenericDAO genericDAO) {
		this.genericDAO = genericDAO;
	}


	public GenericDAO getGenericDAO() {
		return genericDAO;
	}

}
