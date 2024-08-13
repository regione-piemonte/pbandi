/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.util;

import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import it.csi.pbandi.pbservizit.exception.UtenteException;
import it.csi.pbandi.pbservizit.security.UserInfoSec;

public class RequestUtil {
	
	static Logger logger = Logger.getLogger(DateUtil.class.getName());

	public static String idIrideInSessione(HttpServletRequest request) throws UtenteException {		
		
		if(request == null) {
			logger.error("RequestUtil.datiUtente(): request non valorizzata.");
			throw new UtenteException("Request non valorizzata.");
		}
		
		UserInfoSec userInfo = (UserInfoSec) request.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);
		
		if(userInfo == null) {
			logger.error("RequestUtil.datiUtente(): userInfo non valorizzato.");
			throw new UtenteException("UserInfo non valorizzato.");
		}
		
		return userInfo.getIdIride();
	}
}
