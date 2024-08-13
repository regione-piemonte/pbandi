/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebbo.business.service.impl;


import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.csi.pbandi.pbwebbo.business.service.ConsoleMonitoriaggioServiziService;
import it.csi.pbandi.pbwebbo.dto.MonitoraggioServiziDTO;
import it.csi.pbandi.pbwebbo.dto.PbandiServiziContDTO;
import it.csi.pbandi.pbwebbo.dto.PbandiTMonServAmmvoContabVO;
import it.csi.pbandi.pbwebbo.dto.utils.ResponseCodeMessage;
import it.csi.pbandi.pbwebbo.integration.dao.ConsoleMonitoraggioServiziDAO;
import it.csi.pbandi.pbwebbo.util.Constants;
@Service
public class ConsoleMonitoriaggioServiziServiceImpl implements ConsoleMonitoriaggioServiziService {

	private Logger LOG = Logger.getLogger(Constants.COMPONENT_NAME);
	
	@Autowired
	private ConsoleMonitoraggioServiziDAO consoleMonitoraggioServiziDAO; 
	
	@Override
	public Response findErrorMessage(HttpServletRequest req) throws Exception {
		
		String prf = "[ConsoleMonitoriaggioServiziServiceImpl::findErrorMessage]";
		LOG.debug(prf + " BEGIN");
		List<PbandiTMonServAmmvoContabVO> listErrorMessage;
		try {
			 listErrorMessage = consoleMonitoraggioServiziDAO.findError();
		}catch (Exception e) {
			LOG.error(prf + e);
			ResponseCodeMessage resp = new ResponseCodeMessage();
			resp.setCode("ERRORE");
			resp.setMessage("Errore: " + e);
			return Response.ok().entity(resp).build();
		}
		return Response.ok().entity(listErrorMessage).build();
	}

	@Override
	public Response getEsitoChiamate(String dataInizio, String dataFine, String codiceErrore,Long idServizio, HttpServletRequest req)
			throws Exception {
		String prf = "[ConsoleMonitoriaggioServiziServiceImpl::getEsitoChiamate]";
		LOG.debug(prf + " BEGIN");
		List<MonitoraggioServiziDTO> esitoMonitoraggio ;
		try {
			
			
			String formato = "dd/MM/yyyy HH:mm:ss";
			SimpleDateFormat sdf = new SimpleDateFormat(formato);
			Date dataOraInizio = sdf.parse(dataInizio);
			Date dataOraFine = sdf.parse(dataFine);
			
			LOG.debug(dataOraInizio);
			LOG.debug(dataOraFine);
			
			esitoMonitoraggio = consoleMonitoraggioServiziDAO.getErrorMonitoraggio(dataOraInizio, dataOraFine, codiceErrore,idServizio);
		}catch (Exception e) {
			LOG.error(prf + e);
			ResponseCodeMessage resp = new ResponseCodeMessage();
			resp.setCode("ERRORE");
			resp.setMessage("Errore: " + e);
			return Response.ok().entity(resp).build();
		}
		return Response.ok().entity(esitoMonitoraggio).build();
	}

	@Override
	public Response getStatusServizio(String dataInizio, String dataFine, String codiceErrore, Long idServizio, HttpServletRequest req)
			throws Exception {
		String prf = "[ConsoleMonitoriaggioServiziServiceImpl::getStatusServizio]";
		LOG.debug(prf + " BEGIN");
		List<PbandiTMonServAmmvoContabVO> esitoMonitoraggio ;
		try {
			
			String formato = "dd/MM/yyyy HH:mm:ss";
			SimpleDateFormat sdf = new SimpleDateFormat(formato);
			Date dataOraInizio = sdf.parse(dataInizio);
			Date dataOraFine = sdf.parse(dataFine);
			
			
			 
			esitoMonitoraggio = consoleMonitoraggioServiziDAO.getStatusMonitoraggio(dataOraInizio, dataOraFine, codiceErrore,idServizio);
		}catch (Exception e) {
			LOG.error(prf + e);
			ResponseCodeMessage resp = new ResponseCodeMessage();
			resp.setCode("ERRORE");
			resp.setMessage("Errore: " + e);
			return Response.ok().entity(resp).build();
		}
		return Response.ok().entity(esitoMonitoraggio).build();
	}

	@Override
	public Response findServizi(HttpServletRequest req) throws Exception {
		String prf = "[ConsoleMonitoriaggioServiziServiceImpl::findErrorMessage]";
		LOG.debug(prf + " BEGIN");
		List<PbandiServiziContDTO> listServizi;
		try {
			listServizi = consoleMonitoraggioServiziDAO.findServizi();
		}catch (Exception e) {
			LOG.error(prf + e);
			ResponseCodeMessage resp = new ResponseCodeMessage();
			resp.setCode("ERRORE");
			resp.setMessage("Errore: " + e);
			return Response.ok().entity(resp).build();
		}
		return Response.ok().entity(listServizi).build();
	}

}
