package it.csi.pbandi.pbweb.pbandisrv.business.manager;

import it.csi.pbandi.pbweb.pbandisrv.dto.manager.ConfigurationDTO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.dao.GenericDAO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.util.Condition;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiCCostantiVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.util.BeanMapper;
import it.csi.pbandi.pbweb.pbandiutil.common.BeanUtil;
import it.csi.pbandi.pbweb.pbandiutil.common.LoggerUtil;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;

public class ConfigurationManager {
	private static final String BUNDLE_SUFFIX = "Configuration.";
	private static final String CONFIGURATION_VARIABLE_PREFIX = "conf.";
	private Long lastInvocationTime = 0l;
	
	@Autowired
	private LoggerUtil logger;
	
	@Autowired
	private BeanUtil beanUtil;
	
	@Autowired
	private GenericDAO genericDAO;
	
	private ConfigurationDTO configurationDTO;

	public void setLogger(LoggerUtil logger) {
		this.logger = logger;
	}

	public LoggerUtil getLogger() {
		return logger;
	}

	public void setBeanUtil(BeanUtil beanUtil) {
		this.beanUtil = beanUtil;
	}

	public BeanUtil getBeanUtil() {
		return beanUtil;
	}

	public void setGenericDAO(GenericDAO genericDAO) {
		this.genericDAO = genericDAO;
	}

	public GenericDAO getGenericDAO() {
		return genericDAO;
	}

	public void init() {
		try {
			configurationDTO = loadStaticConfiguration();
			getCurrentConfiguration();
		} catch (Exception e) {
			logger.warn("Impossibile leggere la configurazione, ragione: "
					+ e.getMessage());
		}
			checkDbStructure();
	}

	@SuppressWarnings("static-access")
	private ConfigurationDTO loadStaticConfiguration() {
		ConfigurationDTO staticConf = new ConfigurationDTO();
		Map<String, ResourceBundle> bundleMap = new HashMap<String, ResourceBundle>();
		for (String propName : beanUtil
				.enumerateProperties(ConfigurationDTO.class)) {
			if (propName.contains(BUNDLE_SUFFIX)) {
				//logger.debug(propName);
				String bundleName = propName.substring(0, propName
						.indexOf(BUNDLE_SUFFIX));
				//logger.debug(bundleName);
				try {
					ResourceBundle bundle = bundleMap.get(bundleName);
					if (bundle == null) {
						bundle = ResourceBundle.getBundle(bundleName);
						bundleMap.put(bundleName, bundle);
					}
					// FIXME dipendenza errata
					String bundleKey = BeanMapper.getDBFieldNameByPropertyName(
							propName.substring((bundleName + BUNDLE_SUFFIX)
									.length())).toLowerCase();
					//logger.debug(bundleKey);
					beanUtil.setPropertyValueByName(staticConf, propName,
							bundle.getString(bundleKey));
				} catch (Exception e) {
					logger.warn("Configurazione statica non trovata per: "
							+ propName + ", ragione: " + e.getMessage());
				}
			}

		}
		return staticConf;
	}

	private void checkDbStructure() {
		try {
			boolean dbStructureCheckBlocking = false;
			dbStructureCheckBlocking = getConfiguration().isDbStructureCheckBlocking();
			logger.info("dbStructureCheckBlocking: "+ dbStructureCheckBlocking);
			if (dbStructureCheckBlocking
					&& !genericDAO.isJavaModelAlignedToDbStructure()) {
				throw new RuntimeException(
						"Inizializazione fallita: la struttura della base dati non � allineata alle classi Java, verificare il log.");
			}
		} catch (Throwable t) {
			logger.error("Check allineamento struttura DB fallito: "
					+ t.getMessage(), t);
			throw new RuntimeException(
					"Inizializazione fallita: il controllo ha ricevuto una eccezione non prevista.",
					t);
		}  
	}

	/**
	 * Legge SEMPRE la configurazione da DB
	 */
	public ConfigurationDTO getCurrentConfiguration() throws Exception {

			Set<PbandiCCostantiVO> values = new HashSet<PbandiCCostantiVO>();
			for (String propertyName : beanUtil
					.enumerateProperties(ConfigurationDTO.class)) {
				PbandiCCostantiVO value = new PbandiCCostantiVO();
				value
						.setAttributo(CONFIGURATION_VARIABLE_PREFIX
								+ propertyName);
				values.add(value);
			}

			 ConfigurationDTO runtimeConfiguration = beanUtil.transform(beanUtil
					.map(genericDAO.where(Condition.filterBy(values)).select(),
							"attributo", "valore"),
					CONFIGURATION_VARIABLE_PREFIX, ConfigurationDTO.class);

			if (configurationDTO == null) {
				configurationDTO = runtimeConfiguration;
			} else {
				beanUtil.deepValueCopy(runtimeConfiguration, configurationDTO);
			}
			
			
		return configurationDTO;
	}

	/**
	 * Legge la configurazione da DB se il cache time � stato superato.
	 */
	public ConfigurationDTO getConfiguration() {

		try {
			if (configurationDTO == null
					|| ((System.currentTimeMillis() - lastInvocationTime) > configurationDTO
							.getConfigurationCache())) {
			//	logger.debug("leggo da db");
				getCurrentConfiguration();
				lastInvocationTime = System.currentTimeMillis();
			} else {
				//logger.debug("cache ancora valida NON leggo da db");
			}
		} catch (Exception e) {
			logger.error("Impossibile aggiornare la configurazione: "
					+ e.getMessage(), e);
		} 

		return configurationDTO;
	}
}
