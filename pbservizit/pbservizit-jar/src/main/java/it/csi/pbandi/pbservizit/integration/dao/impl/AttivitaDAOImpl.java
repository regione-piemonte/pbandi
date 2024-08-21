/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.integration.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import org.apache.log4j.Logger;
import it.csi.csi.wrapper.CSIException;
import it.csi.csi.wrapper.SystemException;
import it.csi.csi.wrapper.UnrecoverableException;
import it.csi.pbandi.pbservizit.pbandisrv.business.neoflux.NeofluxBusinessImpl;
import it.csi.pbandi.pbservizit.pbandisrv.exception.neoflux.NeoFluxException;
import it.csi.pbandi.pbservizit.integration.dao.AttivitaDAO;
import it.csi.pbandi.pbservizit.util.Constants;

@Component
public class AttivitaDAOImpl implements AttivitaDAO {

	private Logger LOG = Logger.getLogger(Constants.COMPONENT_NAME);
	
	@Autowired
	NeofluxBusinessImpl neofluxBusinessImpl;
	
	@Override
	public String chiudiAttivita(Long idUtente, String identitaDigitale, String descBreveTask, Long idProgetto) 
			throws NeoFluxException, SystemException, UnrecoverableException, CSIException {
		LOG.info("[AttivitaDAOImpl::chiudiAttivita] BEGIN");
		neofluxBusinessImpl.unlockAttivita(idUtente, identitaDigitale, idProgetto, descBreveTask);
		LOG.info("[AttivitaDAOImpl::chiudiAttivita] END");
		return null;
	}

}