/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbworkspace.integration.dao;

import java.security.InvalidParameterException;
import java.util.List;

import it.csi.pbandi.pbworkspace.exception.ErroreGestitoException;
import it.csi.pbandi.pbworkspace.exception.UtenteException;
import it.csi.pbandi.pbworkspace.integration.vo.BandoProgettiVO;
import it.csi.pbandi.pbworkspace.integration.vo.NotificaAlertVO;
import it.csi.pbandi.pbworkspace.integration.vo.NotificheFrequenzeVO;

public interface NotificheViaMailDAO {

	String getMail(Long idUtente, String idIride, Long idSoggetto) throws UtenteException, ErroreGestitoException;

	void saveMail(Long idUtente, String idIride, Long idSoggetto, String mail)
			throws UtenteException, InvalidParameterException, ErroreGestitoException;

	NotificheFrequenzeVO getNotificheFrequenze(Long idUtente, String idIride, Long idSoggetto)
			throws UtenteException, ErroreGestitoException;

	void associateNotificheIstruttore(Long idUtente, String idIride, Long idSoggetto,
			List<NotificaAlertVO> notificheAlert) throws UtenteException, ErroreGestitoException;

	List<BandoProgettiVO> getBandiProgetti(Long idUtente, String idIride, Long idSoggetto, Long idSoggettoNotifica)
			throws ErroreGestitoException, UtenteException;

	void associateProgettiToNotifica(Long idUtente, String idIride, Long idSoggetto, NotificaAlertVO notificaAlert)
			throws UtenteException, ErroreGestitoException;

}
