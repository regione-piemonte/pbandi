/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandiutil.commonweb.business.impl;

import it.csi.pbandi.pbservizit.pbandisrv.dto.esecuzioneattivita.IstanzaAttivitaDTO;
import it.csi.pbandi.pbservizit.pbandisrv.interfacecsi.esecuzioneattivita.EsecuzioneAttivitaSrv;
import it.csi.pbandi.pbservizit.pbandiutil.common.BeanUtil;
import it.csi.pbandi.pbservizit.pbandiutil.common.Constants;
import it.csi.pbandi.pbservizit.pbandiutil.common.LoggerUtil;
import it.csi.pbandi.pbservizit.pbandiutil.commonweb.ContentPanelUtil;
import it.csi.pbandi.pbservizit.pbandiutil.commonweb.UserInfoHelper;
import it.csi.pbandi.pbservizit.pbandiutil.commonweb.business.intf.EsecuzioneAttivitaBusinessInterface;
import it.csi.pbandi.pbservizit.pbandiutil.commonweb.dto.IstanzaAttivitaCorrenteDTO;
import it.csi.pbandi.pbservizit.pbandiutil.commonweb.dto.UserInfoDTO;

import java.util.Map;

public class EsecuzioneAttivitaBusinessImpl implements
		EsecuzioneAttivitaBusinessInterface {

	private LoggerUtil loggerUtil;
	private BeanUtil beanUtil;
	private EsecuzioneAttivitaSrv esecuzioneAttivitaSrv;
	private UserInfoHelper userInfoHelper;
	private Map<String, Object> session;
	private ContentPanelUtil contentPanelUtil;

	public LoggerUtil getLoggerUtil() {
		return loggerUtil;
	}

	public void setLoggerUtil(LoggerUtil loggerUtil) {
		this.loggerUtil = loggerUtil;
	}

	public EsecuzioneAttivitaSrv getEsecuzioneAttivitaSrv() {
		return esecuzioneAttivitaSrv;
	}

	public void setEsecuzioneAttivitaSrv(
			EsecuzioneAttivitaSrv esecuzioneAttivitaSrv) {
		this.esecuzioneAttivitaSrv = esecuzioneAttivitaSrv;
	}

	public void setBeanUtil(BeanUtil beanUtil) {
		this.beanUtil = beanUtil;
	}

	public BeanUtil getBeanUtil() {
		return beanUtil;
	}

	public void setUserInfoHelper(UserInfoHelper userInfoHelper) {
		this.userInfoHelper = userInfoHelper;
	}

	public UserInfoHelper getUserInfoHelper() {
		return userInfoHelper;
	}

	public Map<String, Object> getSession() {
		return session;
	}

	public void setSession(Map<String, Object> session) {
		this.session = session;
	}

	public void setContentPanelUtil(ContentPanelUtil contentPanelUtil) {
		this.contentPanelUtil = contentPanelUtil;
	}

	public ContentPanelUtil getContentPanelUtil() {
		return contentPanelUtil;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeit.csi.pbandi.pbandiutil.commonweb.business.impl.
	 * EsecuzioneAttivitaBusinessInterface
	 * #createAttivitaPreliminareAvvioProgetto()
	 */
	public IstanzaAttivitaCorrenteDTO createAttivitaPreliminareAvvioProgetto() {
		IstanzaAttivitaCorrenteDTO istanzaAttivitaCorrenteDTO = new IstanzaAttivitaCorrenteDTO();
		istanzaAttivitaCorrenteDTO.setUrlMiniApp("avviaProgetto.do");
		istanzaAttivitaCorrenteDTO.setTaskName(Constants.TASKNAME_ATTIVITA_PRELIMINARE);
		return istanzaAttivitaCorrenteDTO;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeit.csi.pbandi.pbandiutil.commonweb.business.impl.
	 * EsecuzioneAttivitaBusinessInterface#createAttivita(java.lang.Object,
	 * java.lang.String)
	 */
	public IstanzaAttivitaCorrenteDTO createAttivita(
			Object currentUserMiniAppSpecific, String taskIdentity)
			throws Exception {
		loggerUtil.warn("\n\n\n#########################\ncreateAttivita , taskIdentity:  "+taskIdentity);
		UserInfoDTO currentUser = beanUtil.transform(currentUserMiniAppSpecific, UserInfoDTO.class);

		IstanzaAttivitaCorrenteDTO istanzaAttivitaCorrenteDTO = new IstanzaAttivitaCorrenteDTO();

		istanzaAttivitaCorrenteDTO.setTaskIdentity(taskIdentity);

		IstanzaAttivitaDTO istanzaAttivitaDTO = new IstanzaAttivitaDTO();
		istanzaAttivitaDTO.setId(taskIdentity);
		istanzaAttivitaDTO = esecuzioneAttivitaSrv.avviaAttivita(
				currentUser.getIdUtente(), currentUser.getIdIride(),
				userInfoHelper.getCodUtenteFlux(currentUser),
				istanzaAttivitaDTO);

		BeanUtil.valueCopy(istanzaAttivitaDTO, istanzaAttivitaCorrenteDTO);

		Map<String, String> metadatiIstanza = beanUtil.map(
				istanzaAttivitaDTO.getVariabili(), "nome", "valore");
		istanzaAttivitaCorrenteDTO.setMetadati(metadatiIstanza);

		/*
		 * FIXME mantengo per compatibilit� le propriet� statiche
		 * valorizzate con variabili di processo, ma la mappa deve essere
		 * usata in modo preferenziale
		 */
		istanzaAttivitaCorrenteDTO.setIdProgetto(beanUtil
				.transform(metadatiIstanza
						.get(Constants.PROCESS_VARIABLE_ID_PROGETTO),
						Long.class));
		istanzaAttivitaCorrenteDTO.setIdDichiarazione(beanUtil.transform(
				metadatiIstanza
						.get(Constants.PROCESS_VARIABLE_ID_DICHIARAZIONE),
				Long.class));
		istanzaAttivitaCorrenteDTO
				.setCodCausaleErogazione(metadatiIstanza
						.get(Constants.PROCESS_VARIABLE_CAUSALE_RICHIESTA_EROGAZIONE));


		userInfoHelper
				.aggiungiIstanzaAttivitaProcessoAContestoIdentificativo(
						currentUserMiniAppSpecific,
						istanzaAttivitaCorrenteDTO);
		return istanzaAttivitaCorrenteDTO;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeit.csi.pbandi.pbandiutil.commonweb.business.impl.
	 * EsecuzioneAttivitaBusinessInterface#chiudiAttivita(java.lang.Object,
	 * it.csi.pbandi.pbandiutil.commonweb.dto.IstanzaAttivitaCorrenteDTO)
	 */
	public void chiudiAttivita(Object currentUserMiniAppSpecific,
			IstanzaAttivitaCorrenteDTO i) throws Exception {
		loggerUtil.error("\n\nchiudiAttivita MUST NOT BE EVER INVOKED , AttivitaCorrenteDTO.getTaskName:  "+i.getTaskName()+"\n\n\n");
	/*	UserInfoDTO currentUser = beanUtil.transform(currentUserMiniAppSpecific, UserInfoDTO.class);
		contentPanelUtil.resetInitContentPanel(session);
		IstanzaAttivitaDTO istanzaAttivitaDTO = getBeanUtil().transform(i,IstanzaAttivitaDTO.class);
		istanzaAttivitaDTO.setId(i.getTaskIdentity());
		esecuzioneAttivitaSrv.chiudiAttivita(currentUser.getIdUtente(),
										currentUser.getIdIride(),
										userInfoHelper.getCodUtenteFlux(currentUser), 
										istanzaAttivitaDTO);*/
		 
	}

	public void finishTask(Object currentUserMiniAppSpecific,
			IstanzaAttivitaCorrenteDTO istanzaAttivita) throws Exception {
		loggerUtil.warn("finishTask MUST NOT BE EVER INVOKED , istanzaAttivitaCorrenteDTO.getTaskName:  "+istanzaAttivita.getTaskName());
		/*UserInfoDTO currentUser = beanUtil.transform(
				currentUserMiniAppSpecific, UserInfoDTO.class);
		IstanzaAttivitaDTO istanzaAttivitaDTO = beanUtil.transform(
				istanzaAttivita, IstanzaAttivitaDTO.class);
		istanzaAttivitaDTO.setId(istanzaAttivita.getTaskIdentity());
		esecuzioneAttivitaSrv.concludiAttivita(currentUser.getIdUtente(),
					currentUser.getIdIride(), userInfoHelper
							.getCodUtenteFlux(currentUser), istanzaAttivitaDTO);*/
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeit.csi.pbandi.pbandiutil.commonweb.business.impl.
	 * EsecuzioneAttivitaBusinessInterface
	 * #getProcessVariableForTask(java.lang.Object, java.lang.String,
	 * java.lang.String)
	 */
	public Long getProcessVariableForTask(Object currentUserMiniAppSpecific,
			String variableName, String taskIdentity) throws Exception {
		loggerUtil.info("\n\n\ngetProcessVariableForTask MUST NOT BE EVER INVOKED  , taskIdentity:  "+taskIdentity+" , variableName: "+variableName);
		/*UserInfoDTO currentUser = beanUtil.transform(currentUserMiniAppSpecific, UserInfoDTO.class);
		Long result = null;
		IstanzaAttivitaDTO istanzaAttivitaDTO = new IstanzaAttivitaDTO();
		istanzaAttivitaDTO.setId(taskIdentity);
		result = esecuzioneAttivitaSrv
				.caricaLongProcessVariableForIstanzaAttivita(currentUser
						.getIdUtente(), currentUser.getIdIride(),
						userInfoHelper.getCodUtenteFlux(currentUser),
						variableName, istanzaAttivitaDTO);

		return result;
		*/
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeit.csi.pbandi.pbandiutil.commonweb.business.impl.
	 * EsecuzioneAttivitaBusinessInterface
	 * #getProcessVariableForIstanzaAttivita(java.lang.Object, java.lang.String,
	 * java.lang.String)
	 */
	public Long getProcessVariableForIstanzaAttivita(
			Object currentUserMiniAppSpecific, String idIstanza,
			String nomeVariabile) throws Exception {
		
		loggerUtil.info("getProcessVariableForIstanzaAttivita MUST NOT BE EVER INVOKED  ,idIstanza:  "+idIstanza+" , nomeVariabile: "+nomeVariabile);
		/*
		loggerUtil.info("getProcessVariableForTask , idIstanza:  "+idIstanza+" , nomeVariabile: "+nomeVariabile);
		IstanzaAttivitaDTO istanzaAttivita = new IstanzaAttivitaDTO();
		istanzaAttivita.setId(idIstanza);

		Long result;
		result = esecuzioneAttivitaSrv
					.caricaLongProcessVariableForIstanzaAttivita(user
							.getIdUtente(), user.getIdIride(), userInfoHelper
							.getCodUtenteFlux(user), nomeVariabile,
							istanzaAttivita);
		return result;
*/
		return null;
	}

	/**
	 * Restituisce, prelevandolo dalla sessione, il bean rappresentante l'
	 * attivita' di processo corrente.
	 * 
	 * @param theSession
	 *            ;
	 * @return
	 */
	public IstanzaAttivitaCorrenteDTO getCurrentActivity(Map theSession) {
		return (IstanzaAttivitaCorrenteDTO) theSession
				.get(it.csi.pbandi.pbservizit.pbandiutil.commonweb.Constants.SESSION_KEY_CURRENT_ACTIVITY);
	}

	/**
	 * Memorizza in sessione l' attivita' di processo corrente
	 * 
	 * @param theSession
	 * @param i
	 */
	public void setCurrentActivity(Map theSession, IstanzaAttivitaCorrenteDTO i) {
		theSession
				.put(
						it.csi.pbandi.pbservizit.pbandiutil.commonweb.Constants.SESSION_KEY_CURRENT_ACTIVITY,
						i);
	}

	public void removeCurrentActivity(Map theSession) {
		theSession
				.remove(it.csi.pbandi.pbservizit.pbandiutil.commonweb.Constants.SESSION_KEY_CURRENT_ACTIVITY);
	}

	private boolean checkCurrentActivity(Map theSession, String taskName) {
		IstanzaAttivitaCorrenteDTO i = getCurrentActivity(theSession);
		return (i != null) && (i.getTaskName().equals(taskName));
	}

	/**
	 * Verifica se l'attivita' nella sessione corrente e' quella indicata
	 * 
	 * @param taskName
	 *            recuperabile da
	 *            it.csi.pbandi.pbandiutil.common.Constants.TASKNAME_*
	 * @return true se l'attivita' e' quella indicata, false altrimenti
	 */
	public boolean checkCurrentActivity(String taskName) {
		return checkCurrentActivity(session, taskName);
	}

	/**
	 * Verifica se l'attivita' corrente e' quella di RIMODULAZIONE
	 * 
	 * @param theSession
	 * @return
	 */
	public boolean isCurrentActivityRimodulazione(Map theSession) {
		return checkCurrentActivity(
				theSession,
				it.csi.pbandi.pbservizit.pbandiutil.common.Constants.TASKNAME_RIMODULAZIONE_CONTO_ECONOMICO);
	}

	/**
	 * Verifica se l'attivita' corrente e' quella di PROPOSTA DI RIMODULAZIONE
	 * 
	 * @param theSession
	 * @return
	 */
	public boolean isCurrentActivityPropostaDiRimodulazione(Map theSession) {
		return checkCurrentActivity(
				theSession,
				it.csi.pbandi.pbservizit.pbandiutil.common.Constants.TASKNAME_PROPOSTA_RIMODULAZIONE);

	}

	/**
	 * Verifica se l'attivita' corrente e' quella di GESTIONE APPALTI
	 * 
	 * @param theSession
	 * @return
	 */
	public boolean isCurrentActivityGestioneAppalti(Map theSession) {
		return checkCurrentActivity(
				theSession,
				it.csi.pbandi.pbservizit.pbandiutil.common.Constants.TASKNAME_GESTIONE_APPALTI);

	}
	
}
