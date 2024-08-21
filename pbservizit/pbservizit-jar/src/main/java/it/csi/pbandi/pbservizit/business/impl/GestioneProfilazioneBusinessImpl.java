/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.business.impl;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.csi.pbandi.pbservizit.business.intf.GestioneProfilazioneBusiness;
import it.csi.pbandi.pbservizit.business.intf.MessageKey;
import it.csi.pbandi.pbservizit.dto.profilazione.BeneficiarioCount;
import it.csi.pbandi.pbservizit.dto.profilazione.Ruolo;
import it.csi.pbandi.pbservizit.dto.profilazione.UserInfoDTO;
import it.csi.pbandi.pbservizit.exception.DaoException;
import it.csi.pbandi.pbservizit.exception.UtenteException;
import it.csi.pbandi.pbservizit.integration.dao.ProfilazioneDAO;
import it.csi.pbandi.pbservizit.security.UserInfoSec;
import it.csi.pbandi.pbservizit.util.Constants;
import it.csi.pbandi.pbservizit.security.BeneficiarioSec;

@Component("gestioneProfilazioneBusiness")
public class GestioneProfilazioneBusinessImpl implements GestioneProfilazioneBusiness {

	private Logger LOG = Logger.getLogger(Constants.COMPONENT_NAME);

	@Autowired
	ProfilazioneDAO profilazioneSrv;
	
	@Override
	public UserInfoSec getInfoUtente(HttpSession session) throws UtenteException {

		String prf = "[GestioneProfilazioneBusinessImpl::getInfoUtente]";
		LOG.info(prf + " BEGIN");

		UserInfoSec userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
		LOG.info(prf + " userInfo=" + userInfo);

		UserInfoDTO dto = null;
//		UtenteGIDTO utenteIncaricato = (UtenteGIDTO) session.getAttribute(Constants.KEY_UTENTE_INCARICATO);
//		LOG.info(prf + " utenteIncaricato=" + utenteIncaricato);

//		if (utenteIncaricato != null) {
//
//			dto = profilazioneSrv.getMiniAppInfoUtente(utenteIncaricato.getIdUtenteDelegato(), userInfo.getIdIride(),
//					true, utenteIncaricato);
//
//			LOG.info(prf + " dto from getMiniAppInfoUtente=" + dto);
//
//		} else {
			dto = profilazioneSrv.getInfoUtente( userInfo.getCodFisc(), userInfo.getNome(), userInfo.getCognome());

			LOG.info(prf + " dto from getInfoUtente=" + dto);

//		}

		if (dto != null) {

			userInfo.setIsIncaricato(dto.getIsIncaricato());

			/**
			 * Verifico il ruoli/o dell' utente. Se l'utente ha dei ruoli allora accede,
			 * altrimenti no
			 */
			String descrRuolo = null;
			String codiceRuolo = null;

			if (dto.getRuoli() != null) {

				if (dto.getRuoli().length == 0) {
					/*
					 * Utente non accede perche' non ha ruoli iride validi
					 */
					UtenteException ue = new UtenteException(MessageKey.KEY_MSG_ACCESSO_NON_AUTORIZZATO);
					LOG.error(prf + "Utente non accede perche' non possiede ruoli IRIDE validi.", ue);
					throw ue;

				} else if (dto.getRuoli().length == 1) {

					LOG.info(prf + "Utente ha un solo ruolo");
					/*
					 * L' utente accede con quel ruolo
					 */
					Ruolo ruolo = dto.getRuoli()[0];
					descrRuolo = ruolo.getDescrizione();
					codiceRuolo = ruolo.getDescrizioneBreve();
					userInfo.setRuolo(descrRuolo);
					userInfo.setCodiceRuolo(codiceRuolo);

				}
				/*
				 * Carico nello userInfo i ruoli IRIDE dell' utente
				 */
				LOG.info(prf + "Carico nello userInfo i ruoli IRIDE dell' utente");
				ArrayList<Ruolo> ruoli = new ArrayList<Ruolo>();
				for (Ruolo ruoloIride : dto.getRuoli()) {
					Ruolo ruolo = new Ruolo();
					ruolo.setDescrizione(ruoloIride.getDescrizione());
					ruolo.setDescrizioneBreve(ruoloIride.getDescrizioneBreve());
					ruolo.setId(ruoloIride.getId());

					ruolo.setRuoloHelp(ruoloIride.getRuoloHelp());
					ruoli.add(ruolo);
				}

				userInfo.setRuoli(ruoli);

			} else {
				UtenteException ue = new UtenteException(MessageKey.KEY_MSG_ACCESSO_NON_AUTORIZZATO);
				LOG.error(prf + "Utente non accede perche' non possiede ruoli IRIDE validi.", ue);
				throw ue;
			}
			userInfo.setIdUtente(dto.getIdUtente());
			userInfo.setIdSoggetto(dto.getIdSoggetto());
			userInfo.setCodFisc(dto.getCodiceFiscale());
			userInfo.setCognome(dto.getCognome());
			userInfo.setNome(dto.getNome());
			userInfo.setRuoloHelp(dto.getRuoloHelp());

		} else {

			LOG.info(prf + " dto NULL");
			/**
			 * Se il dto e' nullo, l'utente non puo' accedere. E' un controllo in piu' che
			 * viene eseguito. Rilancio una eccezione Utenteexception
			 */
			UtenteException ue = new UtenteException(MessageKey.KEY_MSG_ACCESSO_NON_AUTORIZZATO);
			LOG.error(prf + " Utente non accede poiche' UserInfo e' null", ue);
			throw ue;

		}
		LOG.info(prf + " END");
		return userInfo;
	}

	@Override
	public BeneficiarioCount countBeneficiari(UserInfoSec currentUser, String codiceRuolo) throws DaoException {
		String prf = "[GestioneProfilazioneBusinessImpl::findBeneficiari]";
		LOG.info(prf + " BEGIN");

		BeneficiarioCount beneficiarioCount = profilazioneSrv.countBeneficiari(currentUser.getIdUtente(), currentUser.getCodFisc(), codiceRuolo);

		if (beneficiarioCount != null) {
			LOG.info(prf + " trovati " + beneficiarioCount.getCount() + " beneficiari");

		}
		LOG.info(prf + " END");
		return beneficiarioCount;
	}

	@Override
	public List<BeneficiarioSec> findBeneficiariByDenominazione(UserInfoSec userInfo, String denominazione) throws DaoException {
		String prf = "[GestioneProfilazioneBusinessImpl::findBeneficiariByDenominazione]";
		LOG.info(prf + " BEGIN");

		List<BeneficiarioSec> beneficiari = profilazioneSrv.findBeneficiariByDenominazione(userInfo.getIdUtente(), userInfo.getCodFisc(), userInfo.getCodiceRuolo(), denominazione);

		if (beneficiari != null) {
			LOG.info(prf + " trovati " + beneficiari.size() + " beneficiari");

		}
		LOG.info(prf + " END");
		return beneficiari;
	}
}
