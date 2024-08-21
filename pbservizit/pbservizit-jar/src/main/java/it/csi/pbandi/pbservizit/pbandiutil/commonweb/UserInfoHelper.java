/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandiutil.commonweb;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import static it.csi.pbandi.pbservizit.pbandiutil.common.BeanUtil.ACTIVITY_ASSIGNMENT_OPERATOR;
import static it.csi.pbandi.pbservizit.pbandiutil.common.BeanUtil.ACTIVITY_TOKEN_SEPARATOR;
import it.csi.pbandi.pbservizit.pbandiutil.common.BeanUtil;
import it.csi.pbandi.pbservizit.pbandiutil.common.LoggerUtil;
import it.csi.pbandi.pbservizit.pbandiutil.common.ProxyFactory;
import it.csi.pbandi.pbservizit.pbandiutil.common.dto.ContestoIdentificativoDTO;
import it.csi.pbandi.pbservizit.pbandiutil.commonweb.dto.IstanzaAttivitaCorrenteDTO;
import it.csi.pbandi.pbservizit.pbandiutil.commonweb.dto.UserInfoDTO;

import it.csi.iride2.policy.entity.Identita;
import it.csi.iride2.policy.exceptions.MalformedIdTokenException;

public class UserInfoHelper {

	private static final Map<String, String> MAP_USER_INFO_TO_CONTESTO_IDENTIFICATIVO = new HashMap<String, String>();

	static {
		MAP_USER_INFO_TO_CONTESTO_IDENTIFICATIVO.put("idSoggetto", "idSoggetto");
		MAP_USER_INFO_TO_CONTESTO_IDENTIFICATIVO.put("codiceRuolo",	"descBreveTipoAnagrafica");
	}

	 @Autowired
	 private BeanUtil beanUtil;
	 @Autowired
	 private LoggerUtil logger;
	 private ProxyFactory proxyFactory;
	 private Map<String, Object> session;
		
		public void setBeanUtil(BeanUtil beanUtil) {
			this.beanUtil = beanUtil;
		}

		public BeanUtil getBeanUtil() {
			return beanUtil;
		}

		public void setLoggerUtil(LoggerUtil loggerUtil) {
			this.logger = loggerUtil;
		}

		public LoggerUtil getLoggerUtil() {
			return logger;
		}

		public UserInfoDTO getUserInfoDTO() {
			return transformToUserInfoDTO(getSession().get(
					Constants.SESSION_KEY_APPDATA_CURRENTUSER));
		}

		public void setSession(Map<String, Object> session) {
			this.session = session;
		}

		public Map<String, Object> getSession() {
			return session;
		}

		public void setProxyFactory(ProxyFactory proxyFactory) {
			this.proxyFactory = proxyFactory;
		}

		public ProxyFactory getProxyFactory() {
			return proxyFactory;
		}

	/**
	 * la stringa di identitaIride cambia di significato, al suo interno non ci
	 * sarï¿½ piï¿½ l'identitï¿½ iride (perchï¿½ per il servizio ï¿½ inutilizzabile) ma
	 * tutte le informazioni di contesto che identificano chi fa una chiamata al
	 * servizio e il contesto in cui la fa
	 * 
	 * il contenuto della stringa ï¿½ una mappa codificata che utilizziamo per
	 * popolare un bean che potrï¿½ essere comune tra presentation e strato di
	 * servizio
	 * 
	 */
	@SuppressWarnings("static-access")
	public void creaContestoIdentificativo(Object userInfoMiniAppSpecific,
			IstanzaAttivitaCorrenteDTO istanzaAttivitaCorrenteDTO) {

		ContestoIdentificativoDTO contestoIdentificativo = beanUtil
				.transform(
						beanUtil.decodeMap(beanUtil.getPropertyValueByName(
								userInfoMiniAppSpecific, "idIride",
								String.class), ACTIVITY_TOKEN_SEPARATOR,
								ACTIVITY_ASSIGNMENT_OPERATOR),
						ContestoIdentificativoDTO.class);

		logger.info("contestoIdentificativo.getCodUtente()  :"+(contestoIdentificativo!=null?contestoIdentificativo.getCodUtenteFlux():null));

		if (istanzaAttivitaCorrenteDTO != null && istanzaAttivitaCorrenteDTO.getTaskIdentity() != null) {
			contestoIdentificativo
					.setIdIstanzaAttivitaProcesso(istanzaAttivitaCorrenteDTO.getTaskIdentity());
			contestoIdentificativo
				.setTaskName(istanzaAttivitaCorrenteDTO.getTaskName());
		}

		Object identita = getIdentitaIride();
		if (identita != null) {
			beanUtil.valueCopy(identita, contestoIdentificativo);
			contestoIdentificativo.setIdentitaIride(identita.toString());
		} else {
			logger.warn("Identita` digitale NON trovata in sessione.");
		}

		beanUtil.valueCopy(userInfoMiniAppSpecific, contestoIdentificativo,
				MAP_USER_INFO_TO_CONTESTO_IDENTIFICATIVO);

		try {
			contestoIdentificativo
					.setCodUtenteFlux(getCodUtenteFlux(userInfoMiniAppSpecific));
		} catch (Exception e) {
			logger.warn("Non riesco a creare il codice utente  da mettere nel contesto : "+e.getMessage());
		}

		logger.shallowDump(contestoIdentificativo,"info");

		try {
			beanUtil.setPropertyValueByName(userInfoMiniAppSpecific, "idIride",
					beanUtil.encodeMap(
							beanUtil.transformToMap(contestoIdentificativo),
							ACTIVITY_TOKEN_SEPARATOR,
							ACTIVITY_ASSIGNMENT_OPERATOR));
		} catch (Exception e) {
			logger.error(
					"Impossibile impostare il nuovo contesto identificativo nello userInfo: "
							+ e.getMessage(), e);
		}
	}

	public void aggiungiIstanzaAttivitaProcessoAContestoIdentificativo(
			Object currentUserMiniAppSpecific, IstanzaAttivitaCorrenteDTO istanzaAttivitaCorrenteDTO)
			throws Exception {
		creaContestoIdentificativo(currentUserMiniAppSpecific, istanzaAttivitaCorrenteDTO);
	}

	public <T extends Object> T createWrappedUserInfo(final Class<T> clazz) {
		UserInfoProxySpecification<T> bean = new UserInfoProxySpecification<T>();
		bean.setContainedClass(clazz);
		T object = getProxyFactory().getObject(bean);
		return object;
	}

	private Object getIdentitaIride() {
		return getSession().get(Constants.SESSION_KEY_IDENTITA_IRIDE);
	}

	public String getCodUtenteFlux(Object userInfoMiniAppSpecific)
			throws Exception {
		UserInfoDTO userInfoDTO = transformToUserInfoDTO(userInfoMiniAppSpecific);

		String codFiscale;
		/*
		 * VN: se l' utente e' un incaricato, il codice fiscale per comporre il
		 * codiceUtenteFlux deve essere quello del delegante, per il quale
		 * abbiamo tutti i dati anagrafici nella identita iride
		 */
		if (userInfoDTO.getIsIncaricato() != null
				&& userInfoDTO.getIsIncaricato()) {
			try {
				Identita identitaDelegante = new Identita(
						userInfoDTO.getIdIride());
				codFiscale = identitaDelegante.getCodFiscale();
			} catch (MalformedIdTokenException e) {
				logger.error("Errore nel recupero dell' identita' iride.", e);
				throw e;
			}
		} else {
			codFiscale = userInfoDTO.getCodFisc();
		}
		String codUtente = userInfoDTO.getIdSoggetto() + "_" + codFiscale + "@"
				+ userInfoDTO.getCodiceRuolo();

		getLoggerUtil().info("codUtente : " + codUtente);

		return codUtente;
	}

	private UserInfoDTO transformToUserInfoDTO(Object userInfoMiniAppSpecific) {
		return getBeanUtil().transform(userInfoMiniAppSpecific,
				UserInfoDTO.class);
	}

	public boolean isLoginIncaricato(Object userInfoMiniAppSpecific) {
		UserInfoDTO userInfoDTO = transformToUserInfoDTO(userInfoMiniAppSpecific);
		return userInfoDTO.getIsIncaricato().booleanValue();
	}

	/**
	 * Restituisce il numero di ruoli Iride validi per l' utente
	 * 
	 * @param currentUser
	 * @return
	 */
	public int countRuoliValidi(Object userInfoMiniAppSpecific) {
		int result = 0;
		@SuppressWarnings("static-access")
		List<?> ruoli = (List<?>) beanUtil.getPropertyValueByName(
				userInfoMiniAppSpecific, "ruoli");
		if (ruoli != null) {
			result = ruoli.size();
		}
		return result;
	}

	@SuppressWarnings("static-access")
	public <T extends Object> T  createUserInfoProxyPostDeserialization (Object deserializableUserInfo, final Class<T> clazz) throws Exception {
		try {
			T userInfoProxy = createWrappedUserInfo(clazz);
			beanUtil.valueCopyNulls(deserializableUserInfo, userInfoProxy);
			ArrayList<?> beneficiari = (ArrayList<?>) beanUtil.getPropertyValueByName(
					deserializableUserInfo, "beneficiari");
			beanUtil.setPropertyIfValueIsNullByName(userInfoProxy, "beneficiari", beneficiari);
			try {
				Method m1 = userInfoProxy.getClass().getMethod("setBeneficiari", ArrayList.class);
				m1.invoke(userInfoProxy, beneficiari);
			} catch (NoSuchMethodException nsme) {
				getLoggerUtil().warn("Metodo non trovato nello userInfo:"+nsme.getMessage());
			}
			
			List<?> ruoli = (List<?>) beanUtil.getPropertyValueByName(
					deserializableUserInfo, "ruoli");
			beanUtil.setPropertyIfValueIsNullByName(userInfoProxy, "ruoli", ruoli);
			
			try {
				Method m2 = userInfoProxy.getClass().getMethod("setRuoli", ArrayList.class);
				m2.invoke(userInfoProxy, ruoli);
			} catch (NoSuchMethodException nsme) {
				getLoggerUtil().warn("Metodo non trovato nello userInfo:"+nsme.getMessage());
			}
			
			return userInfoProxy;
			
		} catch (Exception e) {
			logger.error("Errore durante la creazione dello userInfo post deseriliazazzione.", e);
			throw e;
		} 	
	}
}
