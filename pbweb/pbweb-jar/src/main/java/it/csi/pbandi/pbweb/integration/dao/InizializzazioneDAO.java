/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.integration.dao;

import javax.servlet.http.HttpServletRequest;

import it.csi.pbandi.pbservizit.exception.UtenteException;
import it.csi.pbandi.pbservizit.security.UserInfoSec;
import it.csi.pbandi.pbweb.dto.InizializzaDTO;

public interface InizializzazioneDAO {
	InizializzaDTO inizializzaHome (Long idPrj, Long idSg, Long idSgBen, Long idU, String role, String taskIdentity, String taskName, String wkfAction, HttpServletRequest req) throws UtenteException;
	//UserInfoSec completaDatiUtente(Long idPrj, Long idSg, Long idSgBen, Long idU, String role, String taskIdentity, String taskName, String wkfAction, HttpServletRequest req)  throws UtenteException;
	//DatiProgettoInizializzazioneDTO completaDatiProgetto(Long idPrj);
	UserInfoSec inizializzaHome2(Long idSg, Long idSgBen, Long idU, String role, HttpServletRequest req) throws UtenteException;
}
