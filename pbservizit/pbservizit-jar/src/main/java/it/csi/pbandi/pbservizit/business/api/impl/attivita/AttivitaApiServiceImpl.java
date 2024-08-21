/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.business.api.impl.attivita;

import java.security.InvalidParameterException;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.csi.pbandi.pbservizit.security.UserInfoSec;
import it.csi.pbandi.pbservizit.business.api.attivita.AttivitaApi;
import it.csi.pbandi.pbservizit.integration.dao.AttivitaDAO;
import it.csi.pbandi.pbservizit.util.Constants;

@Service
public class AttivitaApiServiceImpl implements AttivitaApi {

	private Logger LOG = Logger.getLogger(Constants.COMPONENT_NAME);
	
	@Autowired
	AttivitaDAO attivitaDAO;
	
	@Override
	public Response chiudiAttivita(HttpServletRequest req, String descBreveTask, Long idProgetto)
			throws InvalidParameterException, Exception {
		String prf ="[AttivitaServiceImpl::chiudiAttivita] ";
		LOG.debug(prf+"BEGIN");
		LOG.info(prf + " descBreveTask="+descBreveTask+", idProgetto="+idProgetto);
		
		// PK vedi pbandiweb -> CallbackAction.chiudiAttivita
		// 
		// se currentActivityRettifica == TRUE bisogna fare cose differenti.... magari creiamo un servizio apposito
		//
		
		UserInfoSec userInfo = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);
		LOG.info(prf + " userInfo="+userInfo);
		
		if(userInfo==null) {
			throw new InvalidParameterException("utente in sessione non correttamente valorizzato");
		}
		
		if (userInfo.getIdUtente() == null || userInfo.getIdIride() == null || descBreveTask == null || idProgetto == null) {
			throw new InvalidParameterException(" parametri in input non correttamente valorizzati");
		}
		
		String ret = null;
		try {
			
			ret = attivitaDAO.chiudiAttivita( userInfo.getIdUtente(), userInfo.getIdIride(), descBreveTask, idProgetto);
			
		} catch (InvalidParameterException e) {
			LOG.error(prf + "InvalidParameterException :"+e.getMessage());
			throw e;
		} catch (Exception e) {
			LOG.error(prf + "Exception :"+e.getMessage());
			throw e;
		}
		
		LOG.info(prf + " END");
		return Response.ok().entity(ret).build();
		
	}

}
