/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.business.service.impl;

import javax.sql.DataSource;

import org.jvnet.hk2.annotations.Service;
import org.springframework.beans.factory.annotation.Autowired;

import it.csi.pbandi.logging.business.api.impl.LogMonitoraggioServiceImpl;



@Service
public class LogMonitoraggio extends  LogMonitoraggioServiceImpl{	

	@Autowired
	public LogMonitoraggio(DataSource dataSource) {
		super(dataSource);
	}
	
	
}
