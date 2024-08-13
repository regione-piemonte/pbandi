/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebbo.integration.dao;

import java.util.Date;
import java.util.List;
import java.sql.Timestamp;

import it.csi.pbandi.pbwebbo.dto.MonitoraggioServiziDTO;
import it.csi.pbandi.pbwebbo.dto.PbandiServiziContDTO;
import it.csi.pbandi.pbwebbo.dto.PbandiTMonServAmmvoContabVO;


public interface ConsoleMonitoraggioServiziDAO {
	
	
	List<PbandiTMonServAmmvoContabVO>	findError();
	List<PbandiServiziContDTO>	findServizi();
	List<PbandiTMonServAmmvoContabVO>   getStatusMonitoraggio(Date dataInizio, Date dataFine, String codiceErrore,Long idServizio);
	List<MonitoraggioServiziDTO>getErrorMonitoraggio(Date dataInizio, Date dataFine, String codiceErrore,Long idServizio);
}
