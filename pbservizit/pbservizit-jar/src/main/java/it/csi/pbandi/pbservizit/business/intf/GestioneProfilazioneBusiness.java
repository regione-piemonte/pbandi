/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.business.intf;

import java.util.List;
import javax.servlet.http.HttpSession;

import it.csi.pbandi.pbservizit.dto.profilazione.BeneficiarioCount;
import it.csi.pbandi.pbservizit.exception.DaoException;
import it.csi.pbandi.pbservizit.exception.UtenteException;
import it.csi.pbandi.pbservizit.security.BeneficiarioSec;
import it.csi.pbandi.pbservizit.security.UserInfoSec;

public interface GestioneProfilazioneBusiness {

	public UserInfoSec getInfoUtente(HttpSession httpSession) throws UtenteException;
	
	BeneficiarioCount countBeneficiari(UserInfoSec userInfo, String codiceRuolo) throws DaoException;

	List<BeneficiarioSec> findBeneficiariByDenominazione(UserInfoSec userInfo, String denominazione) throws DaoException;
}
