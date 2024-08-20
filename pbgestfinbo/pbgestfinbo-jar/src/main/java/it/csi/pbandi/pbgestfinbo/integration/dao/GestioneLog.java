/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.dao;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.csi.pbandi.logging.business.api.impl.LogMonitoraggioServiceImpl;



@Service
public class GestioneLog extends  LogMonitoraggioServiceImpl {
	
	@Autowired
	public GestioneLog(DataSource dataSource) {
		super(dataSource);
	}
}
