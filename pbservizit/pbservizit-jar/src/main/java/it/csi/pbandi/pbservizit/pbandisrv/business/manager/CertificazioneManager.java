/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.business.manager;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import it.csi.csi.wrapper.CSIException;
import it.csi.csi.wrapper.SystemException;
import it.csi.csi.wrapper.UnrecoverableException;
//import it.csi.pbandi.pbservizit.pbandisrv.dto.certificazione.PropostaCertificazioneDTO;
import it.csi.pbandi.pbservizit.pbandisrv.exception.certificazione.CertificazioneException;

import it.csi.pbandi.pbservizit.pbandisrv.integration.db.dao.GenericDAO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.DettPropostaCertifVO;
//import it.csi.pbandi.pbservizit.pbandisrv.interfacecsi.certificazione.CertificazioneSrv;
import it.csi.pbandi.pbservizit.pbandiutil.common.BeanUtil;
import it.csi.pbandi.pbservizit.pbandiutil.common.LoggerUtil;

public class CertificazioneManager {
	
	@Autowired
	private LoggerUtil logger;
	@Autowired
	private BeanUtil beanUtil;
	@Autowired
	private GenericDAO genericDAO;

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

	public GenericDAO getGenericDAO() {
		return genericDAO;
	}

	public void setGenericDAO(GenericDAO genericDAO) {
		this.genericDAO = genericDAO;
	}

	public java.lang.Double findImportoUltimaPropostaCertificazionePerProgetto(java.lang.Long idProgetto, java.lang.String statoProposta)
			throws CSIException, SystemException, UnrecoverableException,
			CertificazioneException {
		
		logger.begin();
		
		DettPropostaCertifVO filtro = new DettPropostaCertifVO();
		filtro.setDescBreveStatoPropostaCert(statoProposta);
		filtro.setIdProgetto(beanUtil.transform(idProgetto,
				BigDecimal.class));
		List<String> orderByList = new ArrayList<String>();
		orderByList.add("dtOraCreazione");
		filtro.setOrderPropertyNamesList(orderByList);

		List<DettPropostaCertifVO> proposteCertificazione = genericDAO.findListWhere(filtro);
		Double importo = null;
		if(proposteCertificazione!=null && proposteCertificazione.size()>0 && proposteCertificazione.get(0)!=null){
			importo = beanUtil.transform(proposteCertificazione.get(0)
					.getImportoCertificazioneNetto(), Double.class);
		}
		return importo;
	}	

}
