package it.csi.pbandi.pbweb.business.manager;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import it.csi.pbandi.pbweb.dto.IstanzaAttivitaCorrenteDTO;
import it.csi.pbandi.pbweb.util.Constants;

public class ActivityProcessManager {

	private Logger LOG = Logger.getLogger(Constants.COMPONENT_NAME);
	
	/**
	 * Memorizza in sessione l' attivita' di processo corrente
	 * 
	 * @param theSession
	 * @param i
	 */
	public void setCurrentActivity(HttpSession theSession, IstanzaAttivitaCorrenteDTO i) {
		theSession.setAttribute(Constants.SESSION_KEY_CURRENT_ACTIVITY, i);
	}
}
