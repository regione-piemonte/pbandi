/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandiutil.commonweb;

import java.util.Map;

public class ContentPanelUtil {

	/**
	 * Verifica se attualmente si sta effettuando il primo accesso al Content
	 * Panel attuale
	 * 
	 * @param session
	 *            la sessione
	 * @return true se � il primo accesso all'attuale Content Panel
	 */
	public boolean isInitContentPanel(Map session) {
		if (session.get(Constants.KEY_INIT_CONTENT_PANEL) != null
				&& ((Boolean) session.get(Constants.KEY_INIT_CONTENT_PANEL)))
			return true;
		else
			return false;
	}

	/**
	 * Fa si' che la prossima chiamata a isInitContentPanel restituisca true
	 * 
	 * @param session
	 *            la sessione
	 */
	public void resetInitContentPanel(Map session) {
		session.remove(Constants.KEY_CURRENT_CONTENT_PANEL);
	}

	/**
	 * Ottiene il nome dell'attuale Content Panel recuperato dalla Action di
	 * Struts e corretto per essere comparato con l'attributo statico CPNAME dei
	 * singoli Content Panel
	 * 
	 * @param session
	 *            la sessione
	 * @return il CPNAME dell'attuale Content Panel
	 */
	public String getCurrentCP(Map session) {
		return transformActionNameToCpName((String) session
				.get(Constants.KEY_CURRENT_CONTENT_PANEL));
	}

	/**
	 * Ottiene il nome del precedente Content Panel recuperato dalla Action di
	 * Struts e corretto per essere comparato con l'attributo statico CPNAME dei
	 * singoli Content Panel
	 * 
	 * @param session
	 *            la sessione
	 * @return il CPNAME del precedente Content Panel
	 */
	public String getPreviousCP(Map session) {
		return  transformActionNameToCpName((String) session.get(Constants.KEY_PREVIOUS_CONTENT_PANEL));
	}

	private String transformActionNameToCpName(String actionName) {
		return actionName.substring(0, 1).toLowerCase()
				+ actionName.substring(1);
	}
	
	

}
