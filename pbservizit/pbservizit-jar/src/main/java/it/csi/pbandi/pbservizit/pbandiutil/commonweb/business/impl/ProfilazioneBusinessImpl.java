/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandiutil.commonweb.business.impl;

import it.csi.pbandi.pbservizit.pbandiutil.commonweb.business.intf.ProfilazioneBusinessInterface;
import it.csi.pbandi.pbservizit.pbandiutil.common.LoggerUtil;
import it.csi.pbandi.pbservizit.pbandiutil.commonweb.UserInfoHelper;
import it.csi.pbandi.pbservizit.pbandiutil.commonweb.dto.UserInfoDTO;
import it.csi.pbandi.pbservizit.pbandiutil.commonweb.PermessoCache;
import it.csi.pbandi.pbservizit.pbandisrv.interfacecsi.profilazione.ProfilazioneSrv;
import it.csi.pbandi.pbservizit.pbandiutil.commonweb.Constants;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

public class ProfilazioneBusinessImpl implements ProfilazioneBusinessInterface {

	private static final long TTL = 60 * 60 * 1000; // TTL = 1h

	@Autowired
	private LoggerUtil logger;
	
	@Autowired
	private UserInfoHelper userInfoHelper;
	
	private ProfilazioneSrv profilazioneSrv;

	private Map<String, Object> session;

	public void setProfilazioneSrv(ProfilazioneSrv profilazioneSrv) {
		this.profilazioneSrv = profilazioneSrv;
	}

	public ProfilazioneSrv getProfilazioneSrv() {
		return profilazioneSrv;
	}

	public void setLogger(LoggerUtil logger) {
		this.logger = logger;
	}

	public LoggerUtil getLogger() {
		return logger;
	}
	
	@Override
	public boolean hasPermessoCurrentUser(String permesso) {
		boolean auth = false;
		logger.begin();
		try {
			logger.debug("userInfoHelper="+userInfoHelper);;
			UserInfoDTO userInfo = userInfoHelper.getUserInfoDTO();
			logger.dump(userInfo);
			logger.debug("permesso="+permesso);
			
			auth = getHasPermessoCache().get(userInfo.getIdUtente(),
					userInfo.getIdIride(), userInfo.getCodiceRuolo(), permesso);
		} catch (Throwable t) {
			logger.warn("Impossibile verificare l'abilitazione al permesso: "
					+ permesso + " (causa: " + t.getMessage() + ")");
			t.printStackTrace();
		}
		logger.end();
		return auth;
	}

	private PermessoCache<Boolean> getHasPermessoCache() {
		@SuppressWarnings("unchecked")
		PermessoCache<Boolean> hasPermessoCache = (PermessoCache<Boolean>) getSession().get(
				Constants.SESSION_KEY_HAS_PERMESSO_CACHE);

		if (hasPermessoCache == null) {
			logger.debug("cache non trovata in sessione, ne creo una nuova");
			hasPermessoCache = new PermessoCache<Boolean>(); 
			hasPermessoCache.setCacheTimeout(TTL);

			getSession().put(Constants.SESSION_KEY_HAS_PERMESSO_CACHE,
					hasPermessoCache);
		}

		return hasPermessoCache;
	}

	public void setUserInfoHelper(UserInfoHelper userInfoHelper) {
		this.userInfoHelper = userInfoHelper;
	}

	public UserInfoHelper getUserInfoHelper() {
		return userInfoHelper;
	}

	public void setSession(Map<String, Object> session) {
		this.session = session;
	}

	public Map<String, Object> getSession() {
		return session;
	}
}
