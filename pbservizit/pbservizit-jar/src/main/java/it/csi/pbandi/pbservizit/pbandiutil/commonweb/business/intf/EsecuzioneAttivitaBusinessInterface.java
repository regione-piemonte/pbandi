/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandiutil.commonweb.business.intf;

import java.util.Map;

import it.csi.pbandi.pbservizit.pbandiutil.commonweb.dto.IstanzaAttivitaCorrenteDTO;

public interface EsecuzioneAttivitaBusinessInterface {

	public abstract IstanzaAttivitaCorrenteDTO createAttivitaPreliminareAvvioProgetto();

	public abstract IstanzaAttivitaCorrenteDTO createAttivita(
			Object currentUserMiniAppSpecific, String taskIdentity)
			throws Exception;

	public abstract void chiudiAttivita(Object currentUserMiniAppSpecific,
			IstanzaAttivitaCorrenteDTO i) throws Exception;

	public abstract Long getProcessVariableForTask(
			Object currentUserMiniAppSpecific, String variableName,
			String taskIdentity) throws Exception;

	public abstract Long getProcessVariableForIstanzaAttivita(
			Object currentUserMiniAppSpecific, String idIstanza,
			String nomeVariabile) throws Exception;

	/**
	 * Restituisce, prelevandolo dalla sessione, il bean rappresentante l'
	 * attivita' di processo corrente.
	 * 
	 * @param theSession
	 * @return
	 */
	public abstract IstanzaAttivitaCorrenteDTO getCurrentActivity(Map theSession);

	/**
	 * Memorizza in sessione l' attivita' di processo corrente
	 * 
	 * @param theSession
	 * @param i
	 */
	public abstract void setCurrentActivity(Map theSession,
			IstanzaAttivitaCorrenteDTO i);

	public abstract void finishTask(Object userInfo,
			IstanzaAttivitaCorrenteDTO istanzaAttivita) throws Exception;

	public abstract void removeCurrentActivity(Map theSession);
	
	/**
	 * Verifica se l'attivita' nella sessione corrente e' quella indicata
	 * @param taskName recuperabile da it.csi.pbandi.pbandiutil.common.Constants.TASKNAME_*
	 * @return true se l'attivita' e' quella indicata, false altrimenti
	 */
	public boolean checkCurrentActivity(String taskName);

	public abstract boolean isCurrentActivityGestioneAppalti(Map session);

	public abstract boolean isCurrentActivityRimodulazione(Map session);

	public abstract boolean isCurrentActivityPropostaDiRimodulazione(Map session);
	

}