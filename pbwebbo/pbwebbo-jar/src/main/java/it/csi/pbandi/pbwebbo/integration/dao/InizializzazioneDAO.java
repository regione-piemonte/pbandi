/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebbo.integration.dao;

import javax.servlet.http.HttpServletRequest;

import it.csi.pbandi.pbservizit.exception.UtenteException;
import it.csi.pbandi.pbservizit.security.UserInfoSec;

public interface InizializzazioneDAO {
	
	UserInfoSec completaDatiUtente(Long idPrj, Long idSg, Long idSgBen, Long idU, String role, String taskIdentity, String taskName, String wkfAction, HttpServletRequest req)  throws UtenteException;
	
	
}
