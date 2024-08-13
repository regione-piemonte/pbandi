/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.pbandisrv.integration.db.dao;

import static it.csi.pbandi.pbweb.pbandiutil.common.DateUtil.getDataOdierna;
import it.csi.pbandi.pbweb.pbandisrv.business.manager.SequenceManager;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.exception.InsertFailedException;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.exception.RecordNotFoundException;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.exception.TooMuchRecordFoundException;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.dao.PbandiDAO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.OwnerVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.oracle.vo.AllTabColsVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.util.Condition;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.util.FilterCondition;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.GenericVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiTTracciamentoVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.util.BeanMapper;
import it.csi.pbandi.pbweb.pbandiutil.common.BeanUtil;
import it.csi.pbandi.pbweb.pbandiutil.common.LoggerUtil;
import it.csi.pbandi.pbweb.pbandiutil.common.NumberUtil;
import it.csi.pbandi.pbweb.pbandiutil.common.ObjectUtil;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import javax.sql.DataSource;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.RowMapper;

/**
 * Questa classe vorrebbe diventare un DAO generico che dipende solo dal VO che
 * gli viene passato
 * 
 * TODO trasformare l'estensione di pbandidao in una inclusione
 */

public final class GenericDAO extends PbandiDAO {
	
	public GenericDAO(DataSource dataSource) {
		super(dataSource);
		// TODO Auto-generated constructor stub
	}

	private static final Set<String> NON_UPDATABLE_PROPERTIES = new HashSet<String>();
	private static final Set<String> AUTOUPDATE_PROPERTIES = new HashSet<String>();

	static {
		NON_UPDATABLE_PROPERTIES.add("dtInserimento");
		NON_UPDATABLE_PROPERTIES.add("dtInizioValidita");
		NON_UPDATABLE_PROPERTIES.add("idUtenteIns");

		AUTOUPDATE_PROPERTIES.add("dtAggiornamento");
		AUTOUPDATE_PROPERTIES.add("idUtenteAgg");
	}

	@Autowired
	private BeanMapper beanMapperNew;

	private enum SelectModes {
		SELECT, SELECT_DISTINCT
	}

	private SelectModes selectMode = SelectModes.SELECT;

	public void setBeanMapperNew(BeanMapper beanMapperNew) {
		this.beanMapperNew = beanMapperNew;
	}

	public BeanMapper getBeanMapperNew() {
		return beanMapperNew;
	}

	private void setSelectMode(SelectModes selectMode) {
		this.selectMode = selectMode;
	}

	private void resetSelectMode() {
		this.selectMode = SelectModes.SELECT;
	}

	private static String reduceList(List<StringBuilder> aList, String separator) {
		if (aList.size() == 0) {
			return "";
		}

		StringBuilder stringBuilder = new StringBuilder();
		for (StringBuilder condition : aList) {
			stringBuilder.append(condition);
			stringBuilder.append(separator);
		}
		return stringBuilder.substring(0,
				stringBuilder.length() - separator.length());
	}

	private static <E extends GenericVO> String transformToColumnListString(
			final E valueObject) {
		final List<StringBuilder> fieldsList = new ArrayList<StringBuilder>();
		try {
			Condition.forEachVOProperty(valueObject,
					new Condition.Closure<E>() {
						public void execute(final GenericVO valueObject,
								final String aPropertyName)
								throws IntrospectionException,
								IllegalAccessException,
								InvocationTargetException {
							fieldsList.add(new StringBuilder(
									BeanMapper
											.getDBFieldNameByPropertyName(aPropertyName)));
						}

					});
		} catch (Exception e) {
			return null;
		}
		return getCommaSeparatedString(fieldsList);
	}

	public static String getAndClauses(List<StringBuilder> andConditionList) {
		return reduceList(andConditionList, " and ");
	}

	public static String getOrClauses(List<StringBuilder> andConditionList) {
		return reduceList(andConditionList, " or ");
	}

	public static String getCommaSeparatedString(List<StringBuilder> fieldsList) {
		return reduceList(fieldsList, " , ");
	}

	private static StringBuilder getEqualityCondition(String a, String b) {
		return new StringBuilder(a).append(" = ").append(b);
	}

	public static String equalityCondition(String aPropertyName) {
		return getEqualityCondition(
				BeanMapper.getDBFieldNameByPropertyName(aPropertyName),
				asParameter(aPropertyName)).toString();
	}

	public static String betweenCondition(String aPropertyName, boolean truncate) {
		return " " + (truncate?"trunc":"") + "("
				+ BeanMapper.getDBFieldNameByPropertyName(aPropertyName)
				+ ") between " + (truncate?"trunc":"") + "(" + asParameter("from_" + aPropertyName)
				+ ") and " + (truncate?"trunc":"") + " (" + asParameter("to_" + aPropertyName) + ")";
	}

	public static String likeStartsWithCondition(String aPropertyName) {
		return "upper(ltrim("
				+ BeanMapper.getDBFieldNameByPropertyName(aPropertyName)
				+ ")) like upper(" + asParameter(aPropertyName) + ")||'%'";
	}

	public static String likeEndsWithCondition(String aPropertyName) {
		return "upper("
				+ BeanMapper.getDBFieldNameByPropertyName(aPropertyName)
				+ ") like '%'||upper(" + asParameter(aPropertyName) + ")";
	}

	public static String likeContainsCondition(String aPropertyName) {
		return "upper("
				+ BeanMapper.getDBFieldNameByPropertyName(aPropertyName)
				+ ") like '%'||upper(" + asParameter(aPropertyName) + ")||'%'";
	}

	public static String equalsIgnoreCaseCondition(String aPropertyName) {
		return "upper("
				+ BeanMapper.getDBFieldNameByPropertyName(aPropertyName)
				+ ") = upper(" + asParameter(aPropertyName) + ")";
	}

	public static String isNullCondition(String aPropertyName) {
		return BeanMapper.getDBFieldNameByPropertyName(aPropertyName)
				+ " is null ";
	}

	public static String greaterThanCondition(String aPropertyName) {
		return BeanMapper.getDBFieldNameByPropertyName(aPropertyName) + " > "
				+ asParameter(aPropertyName);
	}

	public static String greaterThanOrEqualCondition(String aPropertyName, boolean truncate) {
		return " " + (truncate?"trunc":"") + "("
				+ BeanMapper.getDBFieldNameByPropertyName(aPropertyName)
				+ ") >= " + (truncate?"trunc":"") + "(" + asParameter(aPropertyName) + ") ";
	}

	public static String lessThanCondition(String aPropertyName) {
		return BeanMapper.getDBFieldNameByPropertyName(aPropertyName) + " < "
				+ asParameter(aPropertyName);
	}

	public static String lessThanOrEqualCondition(String aPropertyName, boolean truncate) {
		return " " + (truncate?"trunc":"") + "("
				+ BeanMapper.getDBFieldNameByPropertyName(aPropertyName)
				+ ") <= " + (truncate?"trunc":"") + "(" + asParameter(aPropertyName) + ") ";
	}

	/**
	 * Restituisce tutti gli attributi/propriet� dell'oggetto esclusi quelli
	 * esplicitamente rimossi
	 * 
	 * @param valueObject
	 * @return
	 */
	public static Set<String> extractMappableProperties(GenericVO valueObject) {
		return extractMappableProperties(valueObject.getClass());
	}

	public static Set<String> extractMappableProperties(
			Class<? extends GenericVO> clazz) {
		Set<String> propertyNames = null;
		try {
			// TODO si potrebbe cercare di differenziare considerando le
			// properties che arrivano direttamente dal genericVO che non
			// dovrebbero essere usate nelle query

			propertyNames = BeanUtil.getProperties(clazz);
			removeNonMappableProperties(propertyNames);
		} catch (IntrospectionException ex) {
			throwIntrospectionException(ex);
		}
		return propertyNames;
	}

	private static void removeNonMappableProperties(Set<String> propertyNames) {
		propertyNames.remove("orderPropertyNamesList");
		propertyNames.remove("ascendentOrder");
		propertyNames.remove("class");
		propertyNames.remove("hasSequence");
		propertyNames.remove("PK");
		propertyNames.remove("PKValid");
		propertyNames.remove("propertySequenced");
		propertyNames.remove("SEQUENCE");
		propertyNames.remove("sequence");
		propertyNames.remove("tableNameForValueObject");
		propertyNames.remove("valid");
	}

	public static Map<String, Class<?>> extractMappablePropertiesAndTypes(
			Class<? extends GenericVO> clazz) {
		Map<String, Class<?>> propertiesAndTypes = null;
		try {
			propertiesAndTypes = BeanUtil.getPropertiesAndTypes(clazz);
			removeNonMappableProperties(propertiesAndTypes.keySet());
		} catch (IntrospectionException ex) {
			throwIntrospectionException(ex);
		}
		return propertiesAndTypes;
	}

	public static void throwIntrospectionException(Exception ex) {
		RuntimeException exception = new RuntimeException(
				"Impossibile effettuare l'introspezione del VO");
		exception.initCause(ex);
		throw exception;
	}

	@SuppressWarnings("unchecked")
	public <T extends GenericVO> T getKeyObject(T valueObject) {
		T result = null;
		if (valueObject != null && valueObject.isPKValid()) {
			List<String> pk = (List<String>) valueObject.getPK();
			try {
				result = (T) valueObject.getClass().newInstance();
				for (String string : pk) {
					BeanUtil.setPropertyValueByName(result, string, BeanUtil
							.getPropertyValueByName(valueObject, string));
				}
			} catch (Exception e) {
				logger.error("Impossibile creare il VO chiave.", e);
				// non riusciamo a creare l'oggetto
			}
		}
		return result;
	}

	private interface Checker {
		public boolean check(Object s);
	}

	private interface InsertClosure {
		public boolean insert(final String sqlInsertStatement,
				final MapSqlParameterSource params);
	}

	private interface UpdateClosure {
		public boolean update(final String sqlUpdateStatement,
				final MapSqlParameterSource params);
	}

	@Autowired
	private SequenceManager sequenceManager;
	// @Deprecated
	// private TracciamentoDTO tracciamentoDTO;
	
	@Autowired
	private LoggerUtil logger;
	
	@Autowired
	private BeanUtil beanUtil;
	private Boolean javaModelAlignedToDbStructure = null;
	private final UpdateClosure updateClosureWithTrace;
	private String dbOwner = null;

	{
		updateClosureWithTrace = new UpdateClosure() {
			public boolean update(String sqlUpdateStatement,
					MapSqlParameterSource params) {
				return modifica(sqlUpdateStatement, params);
			}
		};
	}

	public void setLogger(LoggerUtil logger) {
		this.logger = logger;
	}

	public void setSequenceManager(SequenceManager sequenceManager) {
		this.sequenceManager = sequenceManager;
	}

	public SequenceManager getSequenceManager() {
		return sequenceManager;
	}

	public void setBeanUtil(BeanUtil beanUtil) {
		this.beanUtil = beanUtil;
	}

	public BeanUtil getBeanUtil() {
		return beanUtil;
	}

	/*
	 * public void setTracciamentoDTO(TracciamentoDTO tracciamentoDTO) {
	 * this.tracciamentoDTO = tracciamentoDTO; }
	 * 
	 * public TracciamentoDTO getTracciamentoDTO() { return tracciamentoDTO; }
	 */

	public boolean isJavaModelAlignedToDbStructure() {
		if (javaModelAlignedToDbStructure == null) {
			boolean checkAllVOSyntax = checkAllVOSyntax();
			boolean checkDbAlignementToJavaStructure = checkDbAlignementToJavaStructure();
			javaModelAlignedToDbStructure = checkAllVOSyntax
					&& checkDbAlignementToJavaStructure;
		}

		return javaModelAlignedToDbStructure;
	}

	private boolean checkDbAlignementToJavaStructure() {

		Boolean checkPassed = true;

		List<String> missingOnDB = new ArrayList<String>();
		List<String> missingOnJavaModel = new ArrayList<String>();

		AllTabColsVO f = new AllTabColsVO();
		f.setOwner(getOwner());

		Map<?, List<AllTabColsVO>> indexByPropertyAsList;
		try {
			indexByPropertyAsList = getBeanUtil().indexByPropertyAsList(
					this.findListWhere(f), "tableName");
			for (Object tableName : indexByPropertyAsList.keySet()) {
				logger.debug("Checking " + tableName);
				try {
					Set<String> mappableProperties = GenericDAO
							.extractMappableProperties(GenericVO
									.getVOClassByTableName((String) tableName));
					logger.debug("Table in use in Java model.");

					List<AllTabColsVO> list = indexByPropertyAsList
							.get(tableName);
					for (String propertyName : mappableProperties) {
						boolean propertyFound = false;
						Iterator<AllTabColsVO> iterator = list.iterator();
						while (iterator.hasNext()) {
							AllTabColsVO allTabColsVO = iterator.next();
							if (propertyName.equals(BeanMapper
									.getPropertyNameByDBFieldName(allTabColsVO
											.getColumnName()))) {
								propertyFound = true;
								iterator.remove();
								break;
							}
						}
						if (!propertyFound) {
							checkPassed = false;
							String dbFieldNameByPropertyName = BeanMapper
									.getDBFieldNameByPropertyName(propertyName);
							missingOnDB.add(tableName + "."
									+ dbFieldNameByPropertyName);
							logger.debug("Column " + dbFieldNameByPropertyName
									+ " missing on db.");
						}
					}
					if (!list.isEmpty()) {
						checkPassed = false;
						for (AllTabColsVO allTabColsVO : list) {
							missingOnJavaModel.add(tableName + "."
									+ allTabColsVO.getColumnName());
							logger.debug("Column "
									+ allTabColsVO.getColumnName()
									+ " missing on Java model.");
						}
					}
				} catch (ClassNotFoundException e) {
					// non c'� una classe che mappa la tabella, di sicuro
					// non la usiamo
					logger.debug("Unused table.");
				}
			}
		} catch (Exception e) {
			checkPassed = false;
			logger.error(
					"Impossibile verificare allineamento struttura db e modello Java: "
							+ e.getMessage(), e);
		}

		logCheckDbAlignementToJavaStructureResults(checkPassed, missingOnDB,
				missingOnJavaModel);

		logger.debug("checkAllVOSyntax passed: " + checkPassed);

		return checkPassed;
	}

	private void logCheckDbAlignementToJavaStructureResults(
			boolean checkPassed, List<String> missingOnDB,
			List<String> missingOnJavaModel) {
		if (!checkPassed) {
			logger.warn("Attenzione: sono presenti disallineamenti tra la struttura del codice e quella del DB!");
			if (!missingOnDB.isEmpty()) {
				logger.warn("Mancanti sulla struttura DB:");
				for (String string : missingOnDB) {
					logger.warn(string);
				}
			}
			if (!missingOnJavaModel.isEmpty()) {
				logger.warn("Mancanti sul modello Java:");
				for (String string : missingOnJavaModel) {
					logger.warn(string);
				}
			}
		}
	}

	@SuppressWarnings("unchecked")
	public <T extends GenericVO> boolean checkAllVOSyntax() {

		boolean syntaxCheckPassed = true;

		StringBuilder sbInstanceFailure = new StringBuilder();
		StringBuilder sbSyntaxError = new StringBuilder();
		StringBuilder sbSyntaxErrorWithQuery = new StringBuilder();

		for (String voClassName : iterateOnAllVOClassNames()) {
			logger.debug("Checking syntax for: " + voClassName);
			try {
				T vo = (T) ObjectUtil.createNewInstance(voClassName);
				try {
					this.where(crateNeverSatisfiedCondition(vo)).select();
				} catch (Exception e) {
					boolean hasQueryResource = vo.hasQueryResource();
					syntaxCheckPassed = !hasQueryResource && syntaxCheckPassed;

					if (hasQueryResource) {
						sbSyntaxErrorWithQuery.append(prepareLogMessage(
								voClassName, e));
						logger.error(voClassName + ": " + e.getMessage(), e);
					} else {
						sbSyntaxError.append(prepareLogMessage(voClassName, e));
						logger.error(voClassName + ": " + e.getMessage(), e);
					}
				}
			} catch (Exception e) {
				sbInstanceFailure.append(prepareLogMessage(voClassName, e));
				logger.error(voClassName + ": " + e.getMessage(), e);
			}
		}

		logCheckAllVOSyntaxResults(sbInstanceFailure, sbSyntaxError,
				sbSyntaxErrorWithQuery);

		logger.debug("checkAllVOSyntax passed: " + syntaxCheckPassed);

		return syntaxCheckPassed;
	}

	private StringBuilder prepareLogMessage(String voClassName, Exception e) {
		StringBuilder sb = new StringBuilder();
		sb.append(voClassName);
		sb.append(": ");

		String message = e.getMessage() == null ? "null pointer" : e
				.getMessage();

		int prefixLength = Math.min(50, message.length());
		int suffixLength = Math.min(50, message.length() - prefixLength);

		sb.append(message.subSequence(0, prefixLength));

		if (prefixLength + suffixLength < message.length()) {
			sb.append(" ... ");
			sb.append(message.subSequence(message.length() - suffixLength,
					message.length()));
		}

		sb.append("\n");
		return sb;
	}

	private void logCheckAllVOSyntaxResults(StringBuilder sbInstanceFailure,
			StringBuilder sbSyntaxError, StringBuilder sbSyntaxErrorWithQuery) {
		if (sbInstanceFailure.length() > 1) {
			logger.debug("\n\n\n\nVO controllati ma non istanziabili:");
			logger.debug("\n" + sbInstanceFailure.toString());
		}

		if (sbSyntaxError.length() > 1) {
			logger.warn("\n\n\n\nVO con errori di sintassi, ma senza query associata:");
			logger.warn("\n" + sbSyntaxError.toString());
		}

		if (sbSyntaxErrorWithQuery.length() > 1) {
			logger.warn("\n\n\n\nVO con errori di sintassi E con query associata (CAUSANO SICURAMENTE ERRORI):");
			logger.warn("\n" + sbSyntaxErrorWithQuery.toString());
		}
	}

	@SuppressWarnings("unchecked")
	private <T extends GenericVO> Condition<T> crateNeverSatisfiedCondition(T vo) {
		String propName = GenericDAO.extractMappableProperties(vo).iterator()
				.next();
		Condition<T> fieldNull = Condition.isFieldNull(
				(Class<T>) vo.getClass(), propName);
		Condition<T> neverSatisfiedCondition = fieldNull
				.and(fieldNull.negate());
		return neverSatisfiedCondition;
	}

	private Iterable<String> iterateOnAllVOClassNames() {
		InputStream resourceAsStream = GenericDAO.class.getClassLoader()
				.getResourceAsStream("vo_list.properties");
		final Reader r = new BufferedReader(new InputStreamReader(
				resourceAsStream));

		return new Iterable<String>() {

			public Iterator<String> iterator() {
				return new Iterator<String>() {

					private boolean hasNext = true;

					public void remove() {
					}

					public String next() {
						int currentChar;
						StringBuilder sb = new StringBuilder("");
						boolean end = !hasNext;

						while (!end) {
							try {
								currentChar = r.read();
								if (currentChar == -1) {
									end = complete();
								} else if (currentChar == ';') {
									end = true;
								} else {
									if (currentChar == File.separatorChar
											|| currentChar == '/'
											|| currentChar == '\\') {
										currentChar = '.';
									}
									sb.append((char) currentChar);
								}
							} catch (IOException e) {
								logger.error(
										"Impossibile leggere la lista dei vo: "
												+ e.getMessage(), e);
								end = complete();
							}
						}

						String string = sb.toString();
						return string
								.substring(
										0,
										Math.max(
												string.length()
														- ".java".length(), 0));
					}

					private boolean complete() {
						hasNext = false;
						try {
							r.close();
						} catch (IOException e) {
						}
						return true;
					}

					public boolean hasNext() {
						return hasNext;
					}
				};
			}
		};
	}

	public String getOwner() {
		if (dbOwner == null) {
			dbOwner = findListAll(OwnerVO.class).get(0).getOwner();
		}

		return dbOwner;
	}

	/**
	 * delete PER CHIAVE. Restituisce false in caso ci sia qualche errore (PK
	 * non valida, clausola non valorizzata o errore durante il metodo di
	 * eliminazione)
	 * 
	 * @param valueObject
	 * @return
	 * @throws Exception
	 */
	public Boolean delete(GenericVO valueObject) throws Exception {
		Boolean result = false;

		if (valueObject.isPKValid()) {
			String tableName = valueObject.getTableNameForValueObject();

			MapSqlParameterSource params = new MapSqlParameterSource();

			List<StringBuilder> andConditionList = new ArrayList<StringBuilder>();

			// il delete viene eseguito solo sulla chiave primaria
			setConditionListWithOnlyKeys(valueObject, params, andConditionList);

			String clauses = getAndClauses(andConditionList);
			result = doDelete(tableName, params, clauses);
		}

		return result;
	}

	/**
	 * delete senza chiave (da usare con attenzione)
	 * 
	 * @param <T>
	 * @param c
	 * @return
	 * @throws Exception
	 */
	public <T extends GenericVO> Boolean deleteWhere(Condition<T> c)
			throws Exception {
		Boolean result = doDelete(c.getTableName(), c.getParameters(),
				c.getClauses());
		return result;
	}

	public void deleteWithoutTrace(GenericVO valueObject) throws Exception {
		if (valueObject.isPKValid()) {
			String tableName = valueObject.getTableNameForValueObject();

			MapSqlParameterSource params = new MapSqlParameterSource();

			List<StringBuilder> andConditionList = new ArrayList<StringBuilder>();

			// il delete viene eseguito solo sulla chiave primaria
			setConditionListWithOnlyKeys(valueObject, params, andConditionList);

			if (andConditionList.size() > 0) {

				String sqlDelete = "delete from " + tableName + " where "
						+ getAndClauses(andConditionList);
				this.eliminaSenzaTracciamento(sqlDelete, params);
			}
		}

	}

	private Boolean doDelete(String tableName, MapSqlParameterSource params,
			String clauses) {
		String sqlDelete = "delete from " + tableName;
		if (!isEmpty(clauses)) {
			sqlDelete = sqlDelete + " where " + clauses;

		}
		return this.elimina(sqlDelete, params);
	}

	@SuppressWarnings("unchecked")
	private void setConditionListWithOnlyKeys(GenericVO valueObject,
			MapSqlParameterSource params, List<StringBuilder> andConditionList)
			throws IntrospectionException, IllegalAccessException,
			InvocationTargetException {
		Set<String> propertyNames = extractMappableProperties(valueObject);
		List<String> pk = valueObject.getPK();
		for (String aPropertyName : propertyNames) {
			if (isEmpty(pk)) {
				Object aPropertyValue = (Object) BeanUtil
						.getPropertyValueByName(valueObject, aPropertyName);
				if (!isNull(aPropertyValue)) {
					andConditionList.add(new StringBuilder(
							equalityCondition(aPropertyName)));
					mapParamType(params, aPropertyName, aPropertyValue);
				}
			} else {
				for (int i = 0; i < pk.size(); i++) {
					if (aPropertyName.equals(pk.get(i))) {
						Object aPropertyValue = (Object) BeanUtil
								.getPropertyValueByName(valueObject,
										aPropertyName);
						if (!isNull(aPropertyValue)) {
							andConditionList.add(new StringBuilder(
									equalityCondition(aPropertyName)));
							mapParamType(params, aPropertyName, aPropertyValue);
						}
					}
				}
			}
		}
	}

	private <T extends GenericVO> void doPreInsertValueObjectEnrichment(
			T valueObject) {
		setValues(valueObject, NON_UPDATABLE_PROPERTIES);
	}

	private <T> void setValues(T valueObject, Set<String> nonUpdatableProperties) {
		for (String propName : nonUpdatableProperties) {
			BeanUtil.setPropertyIfValueIsNullByName(valueObject, propName,
					getDefaultValue(propName));
		}
	}

	private <T extends GenericVO> void doPreUpdateValueObjectEnrichment(
			T valueObject) {
		setValues(valueObject, AUTOUPDATE_PROPERTIES);
	}

	private Object getDefaultValue(String propName) {
		Object result = null;
		if (propName == null) {
			result = null;
		} else if (propName.startsWith("dt")) {
			result = getDataOdierna();
		}
		/*
		 * else if (propName.startsWith("idUtente")) { result =
		 * BeanUtil.getPropertyValueByName(getTracciamentoDTO(),
		 * "utente.idUtente"); }
		 */
		return result;
	}

	public <T extends GenericVO> T insert(T valueObject) throws Exception {
		T result = doInsert(valueObject, new InsertClosure() {
			public boolean insert(final String sqlInsertStatement,
					final MapSqlParameterSource params) {
				return inserisci(sqlInsertStatement, params);
			}
		});
		return result;
	}

	public <T extends GenericVO> void insert(List<T> valueObjects)
			throws InsertFailedException, Exception {
		for (T valueObject : valueObjects) {
			doInsert(valueObject, new InsertClosure() {
				public boolean insert(final String sqlInsertStatement,
						final MapSqlParameterSource params) {
					return inserisci(sqlInsertStatement, params);
				}
			});
		}
	}

	public <T extends GenericVO> T insertWithoutTrace(T valueObject)
			throws Exception {
		T result = doInsert(valueObject, new InsertClosure() {
			public boolean insert(final String sqlInsertStatement,
					final MapSqlParameterSource params) {
				return inserisci(sqlInsertStatement, params);
			}
		});
		return result;
	}

	private <T extends GenericVO> T doInsert(T valueObject,
			InsertClosure closure) throws InsertFailedException, Exception {
		if(valueObject.getClass()!=PbandiTTracciamentoVO.class){
			logger.debug("Inserimento di: ");
			logger.dump(valueObject);
		}
		boolean isValidForSequence = true;
		if (valueObject.isPKValid()) {
			BigDecimal zero = new BigDecimal(0);
			List pkNames = valueObject.getPK();
			for (Object pkName : pkNames) {
				BigDecimal pkVal = beanUtil.transform(
						BeanUtil.getPropertyValueByName(valueObject,
								pkName.toString()), BigDecimal.class);
				beanUtil.transform(pkName, BigDecimal.class);
				if (NumberUtil.compare(pkVal, zero) <= 0) {
					isValidForSequence = false;
					break;
				}
			}
		} else {
			isValidForSequence = false;
		}
		if (!isValidForSequence && sequenceManager.hasSequence(valueObject)) {
			sequenceManager.setKeyFromSequence(valueObject);
		}

		if (valueObject.isPKValid()) {
			doPreInsertValueObjectEnrichment(valueObject);

			String sqlInsert = "";
			MapSqlParameterSource params = new MapSqlParameterSource();
			String tableName = valueObject.getTableNameForValueObject();
			List<StringBuilder> fieldsList = new ArrayList<StringBuilder>();
			List<StringBuilder> valuesList = new ArrayList<StringBuilder>();

			// prepara oggetto MapSqlParameterSource per jdbcTemplate
			extractFieldsAndParametersValues(valueObject, params, fieldsList,
					valuesList);

			if (fieldsList.size() > 0 && valuesList.size() > 0) {
				sqlInsert = "insert into " + tableName + " ("
						+ getCommaSeparatedString(fieldsList) + ") values ("
						+ getCommaSeparatedString(valuesList) + ")";
			}
			if (isEmpty(sqlInsert)) {
				String message = "NON POSSO ESEGUIRE L'INSERT: non ci sono attributi valorizzati nell'oggetto "
						+ valueObject;
				logger.warn(message);
				throw new InsertFailedException(message);
			} else {
				Boolean insertDoneWithSuccess = closure.insert(sqlInsert,
						params);
				String message = "ESITO INSERIMENTO " + insertDoneWithSuccess;
				if(valueObject.getClass()!=PbandiTTracciamentoVO.class)
					logger.debug(message);
				if (!insertDoneWithSuccess) {
					throw new InsertFailedException(message);
				}
			}

		} else {
			String message = "NON POSSO ESEGUIRE L'INSERT: PK NON VALIDA,CONTROLLARE SE E' STATA SETTATA LA SEQUENCE";
			logger.warn(message);
			throw new InsertFailedException(message);
		}

		return valueObject;
	}

	public <T extends GenericVO> T insertOrUpdateExisting(T valueObject)
			throws Exception {
		T vo = doInsertOrUpdateExisting(valueObject);
		return vo;
	}

	public <T extends GenericVO> void insertOrUpdateExisting(
			List<T> valueObjects) throws Exception {
		for (T valueObject : valueObjects) {
			doInsertOrUpdateExisting(valueObject);
		}
	}

	private <T extends GenericVO> T doInsertOrUpdateExisting(T valueObject)
			throws Exception {
		T vo = valueObject;
		if (valueObject.isPKValid()) {
			updateNullables(valueObject);
		} else {
			vo = insert(valueObject);
		}
		return vo;
	}

	private void extractFieldsAndParametersValues(GenericVO valueObject,
			MapSqlParameterSource params, List<StringBuilder> fieldsList,
			List<StringBuilder> valuesList) throws IntrospectionException,
			IllegalAccessException, InvocationTargetException {
		Set<String> propertyNames = extractMappableProperties(valueObject);

		for (String aPropertyName : propertyNames) {
			Object objVal = BeanUtil.getPropertyValueByName(valueObject,
					aPropertyName);

			if (objVal != null) {
				fieldsList.add(new StringBuilder().append(BeanMapper
						.getDBFieldNameByPropertyName(aPropertyName)));
				valuesList.add(new StringBuilder().append(":").append(
						aPropertyName));
			}
			// rimappa i parametri col tipo corretto nell'oggetto
			// MapSqlParameterSource
			// usato da jdbcTemplate per le operazioni sul db
			mapParamType(params, aPropertyName, objVal);

		}
	}

	/**
	 * Rimappa gli attributi del valueObject nell'oggetto MapSqlParameterSource
	 * utilizzato da jdbctemplate per le operazioni sul db
	 * 
	 * @param params
	 * @param aPropertyName
	 * @param objVal
	 */

	private void mapParamType(MapSqlParameterSource params,
			String aPropertyName, Object objVal) {
		if (objVal == null) {
			params.addValue(aPropertyName, objVal);
		} else if (objVal instanceof BigDecimal)
			params.addValue(aPropertyName, (BigDecimal) objVal);
		else if (objVal instanceof BigInteger)
			params.addValue(aPropertyName, new BigDecimal((BigInteger) objVal));
		else if (objVal instanceof java.sql.Date)
			params.addValue(aPropertyName, (java.sql.Date) objVal,
					Types.TIMESTAMP);
		else if (objVal instanceof java.util.Date)
			params.addValue(aPropertyName, (java.util.Date) objVal);
		else if (objVal instanceof Double)
			params.addValue(aPropertyName,
					new BigDecimal(((Double) objVal).doubleValue()));
		else if (objVal instanceof Float)
			params.addValue(aPropertyName,
					new BigDecimal(((Float) objVal).floatValue()));
		else if (objVal instanceof Integer)
			params.addValue(aPropertyName,
					new BigDecimal(((Integer) objVal).intValue()));
		else if (objVal instanceof Long)
			params.addValue(aPropertyName,
					new BigDecimal(((Long) objVal).longValue()));
		else if (objVal instanceof String)
			params.addValue(aPropertyName, (String) objVal);
		else if (objVal instanceof byte[]) {
			params.addValue(aPropertyName, objVal);
			// try {
			// oracle.sql.BLOB b = oracle.sql.BLOB.getEmptyBLOB();
			// byte[]array=(byte[])objVal;
			// b.setBytes(array.length,array);
			// params.addValue(aPropertyName,b);
			// } catch (SQLException e) {
			// // TODO Auto-generated catch block
			// e.printStackTrace();
			// }
		} else
			logger.debug("tipo parametro non RICONOSCIUTO: "
					+ objVal.getClass().getSimpleName());
	}

	/**
	 * aggiorna tutti i campi impostando a null quelli nulli nel VO
	 * 
	 */
	public void updateNullables(GenericVO valueObject) throws Exception {
		logger.dump(valueObject);

		doUpdate(createKeyFilterForUpdate(valueObject), valueObject,
				new Checker() {
					public boolean check(Object s) {
						return true;
					}
				}, updateClosureWithTrace, true);
	}

	/**
	 * update PER CHIAVE: solo se la chiave � valorizzata viene eseguito
	 * aggiorna solo i campi non null del VO
	 * 
	 * @param valueObject
	 * @throws Exception
	 */
	public void update(GenericVO valueObject) throws Exception {
		// BeanUtil.

		doUpdate(createKeyFilterForUpdate(valueObject), valueObject,
				new Checker() {
					public boolean check(Object s) {
						return !isNull(s);
					}
				}, updateClosureWithTrace, true);
	}

	/**
	 * update PER CHIAVE: solo se la chiave � valorizzata viene eseguito SENZA
	 * TRACCIAMENTO
	 * 
	 * @param valueObject
	 * @throws Exception
	 */
	public void updateWithoutTrace(GenericVO valueObject) throws Exception {
		doUpdate(createKeyFilterForUpdate(valueObject), valueObject,
				new Checker() {
					public boolean check(Object s) {
						return !isNull(s);
					}
				}, new UpdateClosure() {
					public boolean update(String sqlUpdateStatement,
							MapSqlParameterSource params) {
						return modificaSenzaTracciamento(sqlUpdateStatement,
								params);
					}
				}, true);
	}

	private FilterCondition<GenericVO> createKeyFilterForUpdate(
			GenericVO valueObject) {
		return new FilterCondition<GenericVO>(getKeyObject(valueObject));
	}

	/**
	 * update PER CONDIZIONE: valorizza tutte le tuple che rispettano un dato VO
	 * 
	 * @param newValue
	 *            il VO contenente i nuovi valori da inserire
	 * @param filter
	 *            il VO contenente i valori di filtro per la ricerca
	 * @return
	 */

	public <T extends GenericVO> boolean update(T filter, T newValue)
			throws Exception {
		boolean result = doUpdate(new FilterCondition<T>(filter), newValue,
				new Checker() {
					public boolean check(Object s) {
						return !isNull(s);
					}
				}, updateClosureWithTrace, false);
		return result;
	}

	public <T extends GenericVO> void update(Condition<T> filter, T newValue)
			throws Exception {
		doUpdate(filter, newValue, new Checker() {
			public boolean check(Object s) {
				return !isNull(s);
			}
		}, updateClosureWithTrace, false);
	}

	private <T extends GenericVO> boolean doUpdate(Condition<T> filter,
			T newValue, Checker c, UpdateClosure closure,
			boolean removeKeyValues) throws Exception {

		PropertyDescriptor[] propertyDescriptors = Introspector.getBeanInfo(
				newValue.getClass()).getPropertyDescriptors();
		for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
			if (propertyDescriptor.getName().equalsIgnoreCase("idUtenteAgg")) {
				Object value = BeanUtil.getPropertyValueByName(newValue,
						"idUtenteAgg");
				if (value == null) {
					String message = "\n\n\n\nNON POSSO ESEGUIRE L'UPDATE: idUtenteAgg non valorizzato"
							+ newValue;
					logger.error(message + "\n\n\n\n\n");
					// throw new InsertFailedException(message);
				}
				break;
			}

		}
		String tableName = newValue.getTableNameForValueObject();

		MapSqlParameterSource params = new MapSqlParameterSource();
		List<StringBuilder> fieldValuesList = new ArrayList<StringBuilder>();

		doPreUpdateValueObjectEnrichment(newValue);

		// estraggo le proprieta dal newValue
		Set<String> newValuePropertyNames = extractMappableProperties(newValue);
		newValuePropertyNames.removeAll(NON_UPDATABLE_PROPERTIES);
		if (removeKeyValues) {
			newValuePropertyNames.removeAll(newValue.getPK());
		}

		// per ogni proprieta del newValue
		for (String aPropertyName : newValuePropertyNames) {

			// ottengo il valore corrispondente
			Object aPropertyValue = (Object) BeanUtil.getPropertyValueByName(
					newValue, aPropertyName);

			// se la proprieta � da includere nei campi di update
			if (c.check(aPropertyValue)) {
				String propertyParamPlaceHolder = "new_" + aPropertyName;

				// creo una condizione di uguaglianza con la proprieta
				StringBuilder condition = new StringBuilder(
						BeanMapper.getDBFieldNameByPropertyName(aPropertyName)
								+ " = :" + propertyParamPlaceHolder);

				// campi da aggiornare ,ES: NOME_CAMPO=:nomePropriet�
				fieldValuesList.add(condition);

				// mappa i parametri dinamici per jdbcTemplate
				mapParamType(params, propertyParamPlaceHolder, aPropertyValue);
			}
		} // filedValuesList � mappato con tutte le condizioni di uguaglianza

		String whereClauses = filter.getClauses();
		boolean result = false;
		if (fieldValuesList.size() > 0 && !isEmpty(whereClauses)) {
			String sqlUpdate = "update " + tableName + " set "
					+ getCommaSeparatedString(fieldValuesList) + " where "
					+ whereClauses;
			params.addValues(filter.getParameters().getValues());

			result = closure.update(sqlUpdate, params);
		} else {
			logger.warn("\n\n\nNON POSSO ESEGUIRE L'UPDATE :campi da modificare o condizione non validi, fieldValuesList: " + fieldValuesList.toString()
					+ " , andConditionList: " + whereClauses);
		}


		return result;
	}

	@SuppressWarnings("unchecked")
	public <T extends GenericVO> T[] findAll(Class<T> c) {
		List<T> list = findListAll(c);
		return list.toArray((T[]) Array.newInstance(c, list.size()));
	}

	public <T extends GenericVO> List<T> findListAll(Class<T> c) {
		List<T> list = null;
		try {
			list = findListWhere(createVOInstance(c));
		} finally {
		}
		return list;
	}

	private <T> T createVOInstance(Class<T> c) {
		T voInstance = null;
		try {
			voInstance = c.newInstance();
		} catch (InstantiationException ex) {
			throwIntrospectionException(ex);
		} catch (IllegalAccessException ex) {
			throwIntrospectionException(ex);
		}
		return voInstance;
	}

	/**
	 * @deprecated usa gli array, le liste sono preferibili
	 * 
	 * @param <T>
	 * @param valueObject
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Deprecated
	public <T extends GenericVO> T[] findWhere(T valueObject) {
		List<T> list = findListWhere(valueObject);
		return list.toArray((T[]) Array.newInstance(valueObject.getClass(),
				list.size()));
	}

	public <T extends GenericVO> List<T> findListWhere(T valueObject) {
		List<T> res = findListWhere(Condition.filterBy(valueObject));
		return res;
	}

	/**
	 * @deprecated meglio non usare gli array, usare la versione con la lista
	 *             invece
	 * @param <T>
	 * @param condition
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Deprecated
	public <T extends GenericVO> T[] findWhere(Condition<T> condition) {
		List<T> list = findListWhere(condition);
		return list.toArray((T[]) Array.newInstance(
				condition.getContainedClass(), list.size()));
	}

	public <T extends GenericVO> List<T> findListWhere(
			final Condition<T> condition) {
		
		//System.out.println("findListWhere"); //PK
		
		List<T> results = null;

		try {
			results = doQuery(createSelectStatement(condition),
					condition.getParameters(), condition.getContainedClass());
		} catch (IllegalArgumentException e) {
			throwIntrospectionException(e);
		} finally {
		}
		return results == null ? new ArrayList<T>() : results;
	}

	public <T extends GenericVO> List<T> findListWhereWithRowLimits(
			final Condition<T> condition, int rowsLimit) {
		List<T> results = null;

		try {
			results = doQueryWithRowsLimit(createSelectStatement(condition),
					condition.getParameters(), condition.getContainedClass(),
					rowsLimit);
		} catch (IllegalArgumentException e) {
			throwIntrospectionException(e);
		} finally {
		}
		return results == null ? new ArrayList<T>() : results;
	}

	public <T extends GenericVO> List<T> findListWhere(Iterable<T> valueObjects) {
		Condition<T> condition = new FilterCondition<T>(valueObjects);
		List<T> res = findListWhere(condition);
		return res;
	}

	public <T extends GenericVO> T findSingleWhere(T valueObject) {
		//System.out.println("findSingleWhere"); //PK
		return findSingleWhere(Condition.filterBy(valueObject));
	}

	public <T extends GenericVO, E extends GenericVO> E findSingleWhereDistinct(
			T valueObject, Class<E> aClass) {
		return findSingleWhereDistinct(Condition.filterBy(valueObject), aClass);
	}

	/**
	 * @param <T>
	 * @param c
	 *            is the value object, as findSingleWhere
	 * @return null if no record found, the record if found and an exception if
	 *         more that one record is found
	 */
	public <T extends GenericVO> T findSingleOrNoneWhere(T valueObject) {
		return findSingleOrNoneWhere(Condition.filterBy(valueObject));
	}

	/**
	 * @param <T>
	 * @param c
	 *            is the condition, as findSingleWhere
	 * @return null if no record found, the record if found and an exception if
	 *         more that one record is found
	 */
	public <T extends GenericVO> T findSingleOrNoneWhere(Condition<T> c) {
		T result;
		try {
			List<T> list = findListWhere(c);
			if (list == null || list.size() == 0) {
				result = null;
			} else if (list.size() == 1) {
				result = list.get(0);
			} else {
				TooMuchRecordFoundException exception = new TooMuchRecordFoundException(
						"Atteso risultato unico, trovate pi� righe");
				throw exception;
			}
		} finally {
		}
		return result;
	}

	public <T extends GenericVO> T findSingleWhere(Condition<T> c) {
		//System.out.println("T findSingleWhere"); //PK
		T result;
		try {
			List<T> list = findListWhere(c);
			if (list != null && list.size() == 1) {
				result = list.get(0);
			} else {
				RecordNotFoundException exception = new RecordNotFoundException(
						"Atteso risultato unico, trovati molti o nessuno.");
				throw exception;
			}
		} finally {
		}
		return result;
	}

	public <T extends GenericVO, E extends GenericVO> E findSingleWhereDistinct(
			Condition<T> c, Class<E> aClass) {
		E result;
		try {
			List<E> list = findListWhereDistinct(c, aClass);
			if (list != null && list.size() == 1) {
				result = list.get(0);
			} else {
				RecordNotFoundException exception = new RecordNotFoundException(
						"Atteso risultato unico, trovati molti o nessuno.");
				throw exception;
			}
		} finally {
		}
		return result;
	}

 
	public <T extends GenericVO> T findFirstWhere(Condition<T> c) {
		T result;
		try {
			List<T> list = findListWhere(c);
			if (list != null && list.size() > 0) {
				result = list.get(0);
			} else {
				RecordNotFoundException exception = new RecordNotFoundException(
						"Atteso risultato almeno un risultato, trovato nessuno.");
				throw exception;
			}
		} finally {
		}
		return result;
	}

	public <T extends GenericVO, E extends GenericVO> List<E> findListWhereGroupBy(
			final Condition<T> condition, Class<E> aclass) {
		List<E> results = null;

		try {
			FilterCondition<E> groupByCondition = new FilterCondition<E>(
					aclass.newInstance());
			results = doFindListWhereGroupBy(condition, groupByCondition);
		} catch (IllegalArgumentException e) {
			throwIntrospectionException(e);
		} catch (InstantiationException e) {
			throwIntrospectionException(e);
		} catch (IllegalAccessException e) {
			throwIntrospectionException(e);
		} finally {
		}
		return results;
	}
	
	public <T extends GenericVO, E extends GenericVO> List<E> findListWhereGroupBy(
			final Condition<T> condition, Class<E> aclass, String listaCampiGroupBy) {
		List<E> results = null;

		try {
			//FilterCondition<E> groupByCondition = new FilterCondition<E>(aclass.newInstance());
			FilterCondition<E> groupByCondition = new FilterCondition<E>(aclass.newInstance());
			results = doFindListWhereGroupBy(condition, groupByCondition, listaCampiGroupBy);
		} catch (IllegalArgumentException e) {
			throwIntrospectionException(e);
		} catch (InstantiationException e) {
			throwIntrospectionException(e);
		} catch (IllegalAccessException e) {
			throwIntrospectionException(e);
		} finally {
		}
		return results;
	}

	public <T extends GenericVO, E extends GenericVO> List<E> findListWhereGroupBy(
			final Condition<T> condition, E groupByVO) {
		List<E> results = null;

		try {
			FilterCondition<E> groupByCondition = new FilterCondition<E>(
					groupByVO);
			results = doFindListWhereGroupBy(condition, groupByCondition);
		} finally {
		}
		return results;
	}

	public <T extends GenericVO, E extends GenericVO> List<E> findListWhereDistinct(
			final Condition<T> condition, Class<E> aClass) {
		List<E> results = null;

		try {
			FilterCondition<E> distincColumns = new FilterCondition<E>(
					aClass.newInstance());
			results = doFindListWhereDistinct(condition, distincColumns);
		} catch (IllegalArgumentException e) {
			throwIntrospectionException(e);
		} catch (InstantiationException e) {
			throwIntrospectionException(e);
		} catch (IllegalAccessException e) {
			throwIntrospectionException(e);
		} finally {
		}
		return results;
	}

	public <T extends GenericVO, E extends GenericVO> List<E> findListWhereDistinct(
			final Condition<T> condition, E distinctVO) {
		List<E> results = null;

		try {
			FilterCondition<E> distinctCondition = new FilterCondition<E>(
					distinctVO);
			results = doFindListWhereDistinct(condition, distinctCondition);
		} finally {
		}
		return results;
	}

	public <T extends GenericVO, E extends GenericVO> E[] findAllDistinct(
			Class<T> c, Class<E> distinctVO) {
		List<E> list = findListAllDistinct(c, distinctVO);
		return list.toArray((E[]) Array.newInstance(distinctVO, list.size()));
	}

	public <T extends GenericVO, E extends GenericVO> List<E> findListAllDistinct(
			Class<T> c, Class<E> distinctVO) {
		List<E> list = null;
		try {
			list = findListWhereDistinct(
					Condition.filterBy(createVOInstance(c)),
					createVOInstance(distinctVO));
		} finally {
		}
		return list;
	}

	public static List<Map<String, String>> disambiguateParameters(
			Iterable<?> conditionsList,
			MapSqlParameterSource mapSqlParameterSource) {
		if (mapSqlParameterSource == null) {
			return null;
		}

		List<Map<String, String>> substitutionMapList = new ArrayList<Map<String, String>>();

		int counter = 0;
		for (Object cond : conditionsList) {
			Condition<?> c = (Condition<?>) cond;
			Map<String, String> substitutionMap = new HashMap<String, String>();
			for (Object paramName : c.getParameters().getValues().keySet()) {
				String newParamName = ((String) paramName) + "_" + counter;
				substitutionMap.put((String) paramName,
						PbandiDAO.asParameter(newParamName));
				mapSqlParameterSource.addValue(newParamName, c.getParameters()
						.getValue((String) paramName), c.getParameters()
						.getSqlType((String) paramName));
			}
			substitutionMapList.add(substitutionMap);
			counter++;
		}

		return substitutionMapList;
	}

	public <T extends GenericVO, E extends GenericVO> List<Pair<GenericVO, T, E>> findListJoin(
			final Pair<Condition<? extends GenericVO>, Condition<T>, Condition<E>> joinTypes,
			List<String> joinPropertiesNames, List<Order> orderList) {

		List<StringBuilder> tableNames = new ArrayList<StringBuilder>();
		List<StringBuilder> fieldsList = new ArrayList<StringBuilder>();
		List<String> tableAliases = new ArrayList<String>();

		MapSqlParameterSource params = new MapSqlParameterSource();
		List<Map<String, String>> substitutionMapList = GenericDAO
				.disambiguateParameters(joinTypes, params);

		final List<Condition<? extends GenericVO>> columnVOTypes = new ArrayList<Condition<? extends GenericVO>>();
		final List<String> propNames = new ArrayList<String>();
		final List<Class<?>> propTypes = new ArrayList<Class<?>>();

		int tableCount = 0;
		int columnsCount = 0;

		List<StringBuilder> orderByConditionList = new ArrayList<StringBuilder>();
		if (!isEmpty(orderList)) {
			for (Order order : orderList) {
				orderByConditionList.add(new StringBuilder(getAlias(order
						.getConditionPosition()))
						.append(".")
						.append(BeanMapper.getDBFieldNameByPropertyName(order
								.getPropName())).append(" ")
						.append(order.isAscendent() ? "asc" : "desc"));
			}
		}

		for (final Condition<? extends GenericVO> joinContition : joinTypes) {

			String tableAlias = getAlias(tableCount);

			Map<String, Class<?>> propsAndTypesMap = extractMappablePropertiesAndTypes(joinContition
					.getContainedClass());
			for (String propName : propsAndTypesMap.keySet()) {
				fieldsList.add(new StringBuilder(tableAlias)
						.append(".")
						.append(BeanMapper
								.getDBFieldNameByPropertyName(propName))
						.append(" as c").append(columnsCount));

				columnVOTypes.add(joinContition);
				propNames.add(propName);
				propTypes.add(propsAndTypesMap.get(propName));
				columnsCount++;
			}

			tableAliases.add(tableAlias);

			StringBuilder tableName = new StringBuilder();

			final String clauses = PbandiDAO.substituteMarkers(
					joinContition.getClauses(),
					substitutionMapList.get(tableCount));
			if (!GenericDAO.isEmpty(clauses)) {
				@SuppressWarnings("rawtypes")
				Condition<?> condition = new Condition() {

					@Override
					public Class<?> getContainedClass() {
						return joinContition.getContainedClass();
					}

					@Override
					public String getSortColumns() {
						return joinContition.getSortColumns();
					}

					@Override
					public String getTableName() {
						return joinContition.getTableName();
					}

					@Override
					public String getClauses() {
						return clauses;
					}

					@Override
					public boolean isAscendentOrder() {
						return joinContition.isAscendentOrder();
					}
				};

				tableName
						.append(" ( ")
						.append(createSelectAndGroupByStatement(condition,
								condition, getSelectedColumns(null)))
						.append(" ) ");
			} else {
				tableName.append(joinContition.getTableName()).append(" ");
			}

			tableNames.add(tableName.append(tableAlias));
			tableCount++;
		}

		final int totColumns = columnsCount;

		List<StringBuilder> andConditionList = new ArrayList<StringBuilder>();
		for (String propName : joinPropertiesNames) {
			// TODO join tra n tabelle
			String dbFieldName = BeanMapper
					.getDBFieldNameByPropertyName(propName);
			andConditionList.add(getEqualityCondition(tableAliases.get(0) + "."
					+ dbFieldName, tableAliases.get(1) + "." + dbFieldName));
		}

		StringBuilder sql = new StringBuilder();

		appendSelectByMode(sql);
		sql.append(getCommaSeparatedString(fieldsList));
		sql.append(" from ");
		sql.append(getCommaSeparatedString(tableNames));
		sql.append(" where ");
		sql.append(getAndClauses(andConditionList));
		if (!isEmpty(orderByConditionList)) {
			sql.append(" order by ");
			sql.append(getCommaSeparatedString(orderByConditionList));
		}
		
		List<Pair<GenericVO, T, E>> results = this.query(sql.toString(),
				params, new RowMapper<Pair<GenericVO, T, E>>() {

					public Pair<GenericVO, T, E> mapRow(ResultSet rs, int row)
							throws SQLException {
						List<GenericVO> voList = new ArrayList<GenericVO>();
						Map<Condition<? extends GenericVO>, GenericVO> voMap = new HashMap<Condition<? extends GenericVO>, GenericVO>();

						for (Condition<? extends GenericVO> voType : joinTypes) {
							GenericVO vo = createVOInstance(voType
									.getContainedClass());
							voMap.put(voType, vo);
							voList.add(vo);
						}

						for (int index = 0; index < totColumns; index++) {
							Condition<? extends GenericVO> voType = columnVOTypes
									.get(index);
							GenericVO vo = voMap.get(voType);
							try {
								BeanUtil.setPropertyValueByName(vo, propNames
										.get(index), new BeanMapper()
										.extractColumnValue(rs, index + 1,
												propTypes.get(index)));
							} catch (Exception e) {
								// pass
							}
						}

						Pair<GenericVO, T, E> pair = new Pair<GenericVO, T, E>();
						pair.setAll(voList);

						return pair;
					}
				});

		return results;
	}

	private void appendSelectByMode(StringBuilder sql) {
		//System.out.println("appendSelectByMode"); //PK
		
		switch (selectMode) {
		case SELECT:
			sql.append("select ");
			break;

		case SELECT_DISTINCT:
			sql.append("select distinct ");
			break;

		default:
			sql.append("select ");
			break;
		}
		resetSelectMode();
	}

	private String getAlias(int tableCount) {
		return "alias" + tableCount;
	}

	private <T extends GenericVO, E extends GenericVO> List<E> doFindListWhereGroupBy(
			final Condition<T> condition, FilterCondition<E> groupByCondition) {
		List<E> results = doQuery(
				createSelectAndGroupByStatement(condition, groupByCondition,
						getSelectedColumns(groupByCondition)),
				condition.getParameters(), groupByCondition.getContainedClass());
		return results;
	}
	
	private <T extends GenericVO, E extends GenericVO> List<E> doFindListWhereGroupBy(
			final Condition<T> condition, FilterCondition<E> groupByCondition, String listaCampiGroupBy) {
		List<E> results = doQuery(
				createSelectAndGroupByStatement(condition, groupByCondition,
						getSelectedColumns(groupByCondition), listaCampiGroupBy),
				condition.getParameters(), groupByCondition.getContainedClass());
		return results;
	}

	private <T extends GenericVO, E extends GenericVO> List<E> doFindListWhereDistinct(
			final Condition<T> condition, FilterCondition<E> distinctColumns) {
		setSelectMode(SelectModes.SELECT_DISTINCT);
		List<E> results = doQuery(
				createSelectAndGroupByStatement(condition, condition,
						getSelectedColumns(distinctColumns)),
				condition.getParameters(), distinctColumns.getContainedClass());
		return results;
	}

	private <T extends GenericVO> List<T> doQuery(String query,
			MapSqlParameterSource params, final Class<T> aclass) {
		
		//System.out.println("\ndoQuery query="); //PK
		
		List<T> results = null;
		if (!isEmpty(query)) {
			// long start = System.currentTimeMillis();
			query = cleanupQuery(query, params, true);
			// long deltaT = System.currentTimeMillis() - start;

			results = this.query(query, params,
					new RowMapper<T>() {
						// BeanMapper beanMapper = new BeanMapper();

						@SuppressWarnings("unchecked")
						public T mapRow(ResultSet rs, int row)
								throws SQLException {
							T vo = (T) beanMapperNew.toBean(rs, aclass);
							return vo;
						}
					});
		}

		return results == null ? new ArrayList<T>() : results;
	}

	/**
	 * Questo metodo permette di introdurre dei parametri all'interno di una
	 * query SQL gestita tramite GenericDAO. Introducendo un commento a singola
	 * linea '--' contenente una condizione con il parametro indicato in
	 * camel-case ':parametroIndicato', esso verra' incluso come parte della
	 * query finale nel caso che lo stesso parametro sia incluso nella lista dei
	 * parametri passati alla query originaria. In caso contrario, o in assenza
	 * di un parametro valido all'interno dell'intera linea, essa non verra'
	 * inclusa nella query restituita, se removeComments e' TRUE
	 * 
	 * 
	 * @param query
	 *            String in SQL contenente commenti e paramentri interni
	 * @param params
	 *            parametri utilizzati nella query
	 * @param removeComments
	 *            indica se i commenti non inclusi nella query finale devono
	 *            essere rimossi
	 * @return query eventualmente ripulita da commenti a singola linea e
	 *         arricchita con parametri interni
	 */
	private String cleanupQuery(String query, MapSqlParameterSource params,
			Boolean removeComments) {
		
		//System.out.println("cleanupQuery"); //PK
		
		if (query.contains("--")) {
			StringBuilder cleanQuery = new StringBuilder();
			Set<String> keySet = params.getValues().keySet();
			Map<String, String> usedParams = new HashMap<String, String>();
			// }L{ necessario in caso di Condition collegate (id_0, id_1...)
			for (String usedParam : keySet) {
				int underScore = usedParam.indexOf('_');
				if (underScore == -1) {
					underScore = usedParam.length();
				}
				usedParams.put(usedParam.substring(0, underScore), usedParam);
			}
			// java.util.Scanner ~80ms
			// query.split("\n|\r") ~10ms
			// java.io.BufferedReader ~5ms
			BufferedReader reader = new BufferedReader(new StringReader(query));
			String row = null;
			try {
				row = reader.readLine();
			} catch (IOException e) {
				logger.error("IOException in lettura della query");
			}
			while (row != null) {
				if (row.contains("--")) {
					String subRow = row.substring(row.indexOf("--") + 2);
					if (subRow.contains(":")) {
						for (String usedParam : usedParams.keySet()) {
							if (subRow.contains(":" + usedParam)) {
								subRow = subRow.replace(":" + usedParam, ":"
										+ usedParams.get(usedParam));
								cleanQuery.append(subRow + "\n");
							}
						}
					} else if (!removeComments) {
						cleanQuery.append(row);
					}
				} else {
					cleanQuery.append(row + "\n");
				}
				 
				try {
					row = reader.readLine();
				} catch (IOException e) {
					logger.error("IOException in lettura della query");
					row = null;
				}
			}
			query = cleanQuery.toString();
			try {
				reader.close();
			} catch (IOException e) {
				logger.error("IOException in chiusura BufferedReader");
			}
		}
		return query;
	}

	private <T extends GenericVO> List<T> doQueryWithRowsLimit(String query,
			MapSqlParameterSource params, final Class<T> aclass, int rowsLimit) {
		List<T> results = null;

		if (!isEmpty(query)) {
			results = this.queryWithRowsLimit(query, params,
					new RowMapper<T>() {
						// BeanMapper beanMapper = new BeanMapper();

						@SuppressWarnings("unchecked")
						public T mapRow(ResultSet rs, int row)
								throws SQLException {
							T vo = (T) beanMapperNew.toBean(rs, aclass);
							return vo;
						}
					}, rowsLimit);
		}

		return results == null ? new ArrayList<T>() : results;
	}

	private String createSelectStatement(final Condition<?> condition) {
		//System.out.println("createSelectStatement"); //PK
		return createSelectAndGroupByStatement(condition, condition,
				getSelectedColumns(condition));
	}

	private String getSelectedColumns(Condition<?> condition) {
		String result;
		if (condition == null || condition.getContainedClass() == null) {
			result = "*";
		} else {
			List<StringBuilder> selectedColumns = new ArrayList<StringBuilder>();
			for (String aPropertyName : extractMappableProperties(condition
					.getContainedClass())) {
				selectedColumns.add(new StringBuilder()
						.append(BeanMapper
								.getDBFieldNameByPropertyName(aPropertyName))
						.append(" as ").append(aPropertyName));
			}

			result = GenericDAO.getCommaSeparatedString(selectedColumns);
		}

		return result;
	}

	private String createSelectAndGroupByStatement(
			final Condition<?> whereCondition, Condition<?> groupByCondition,
			String selectedColumnsString) {

		//System.out.println("createSelectAndGroupByStatement"); //PK
		
		String clauseString = whereCondition.getClauses();
		
		StringBuilder sqlSelect = new StringBuilder();
		appendSelectByMode(sqlSelect);
		sqlSelect.append(selectedColumnsString);
		sqlSelect.append(" from ");
		sqlSelect.append(whereCondition.getTableName());
		if (!isEmpty(clauseString)) {
			sqlSelect.append(" where " + clauseString);
		}
		if (!whereCondition.equals(groupByCondition)) {
			try {
				sqlSelect.append(" group by "
						+ transformToColumnListString(groupByCondition
								.getContainedClass().newInstance()));
			} catch (InstantiationException e) {
				throwIntrospectionException(e);
			} catch (IllegalAccessException e) {
				throwIntrospectionException(e);
			}
		}

		String sortColumns = whereCondition.getSortColumns();
		if (!isEmpty(sortColumns)) {
			this.setOrderBy(sqlSelect, sortColumns,
					whereCondition.isAscendentOrder());
		}
		return isEmpty(selectedColumnsString) ? null : sqlSelect.toString();
	}
	
	private String createSelectAndGroupByStatement(
			final Condition<?> whereCondition, Condition<?> groupByCondition,
			String selectedColumnsString, String listaCampiGroupBy) {
		
		String clauseString = whereCondition.getClauses();
		
		StringBuilder sqlSelect = new StringBuilder();
		appendSelectByMode(sqlSelect);
		sqlSelect.append(selectedColumnsString);
		sqlSelect.append(" from ");
		sqlSelect.append(whereCondition.getTableName());
		if (!isEmpty(clauseString)) {
			sqlSelect.append(" where " + clauseString);
		}
		if (!whereCondition.equals(groupByCondition)) {
			//sqlSelect.append(" group by " + transformToColumnListString(groupByCondition.getContainedClass().newInstance()));
			sqlSelect.append(" group by " + listaCampiGroupBy);
		}

		String sortColumns = whereCondition.getSortColumns();
		if (!isEmpty(sortColumns)) {
			this.setOrderBy(sqlSelect, sortColumns,
					whereCondition.isAscendentOrder());
		}
		return isEmpty(selectedColumnsString) ? null : sqlSelect.toString();
	}

	public <T extends GenericVO> int count(T valueObject) {
		Condition<T> condition = new FilterCondition<T>(valueObject);
		return count(condition);
	}

	public <T extends GenericVO> int count(Condition<T> condition) {
		return doCount(condition);
	}

	public <T extends GenericVO> Double sum(T valueObject, String propertyName) {
		Condition<T> condition = new FilterCondition<T>(valueObject);
		return sum(condition, propertyName);
	}

	public <T extends GenericVO> Double sum(Condition<T> condition,
			String propertyName) {
		return doSum(condition, propertyName);
	}

	private <T extends GenericVO> int doCount(Condition<T> condition) {
		int count = 0;

		try {
			String query = createCountStatement(condition);

			if (!isEmpty(query)) {
				count = this.queryForInt(query, condition.getParameters());
				logger.debug("Count result: " + count);
			}
		} catch (IllegalArgumentException e) {
			throwIntrospectionException(e);
		} finally {
		}
		return count;
	}

	private String createCountStatement(final Condition<?> condition) {
		return createSelectAndGroupByStatement(condition, condition, "count(*)");
	}

	private <T extends GenericVO> Double doSum(Condition<T> condition,
			String propertyName) {
		Double sum = null;

		try {
			String query = createSumStatement(condition, propertyName);

			if (!isEmpty(query)) {
				sum = this.queryForDouble(query, condition.getParameters());
				logger.debug("Sum result: " + sum);
			}
		} catch (IllegalArgumentException e) {
			throwIntrospectionException(e);
		} finally {
		}
		return ObjectUtil.nvl(sum, 0D);
	}

	private String createSumStatement(Condition<?> condition,
			String propertyName) {
		return createSelectAndGroupByStatement(condition, condition, "sum("
				+ BeanMapper.getDBFieldNameByPropertyName(propertyName) + ")");
	}

	public class WhereClause<T extends GenericVO> {
		private Condition<T> condition;

		public WhereClause(Condition<T> condition) {
			this.condition = condition;
		}

		public <E extends GenericVO> GroupClause<T, E> groupBy(Class<E> aclass) {
			return new GroupClause<T, E>(condition, aclass);
		}

		public T selectSingle() {
			return findSingleWhere(condition);
		}

		public List<T> select() {
			return findListWhere(condition);
		}

		public int count() {
			return doCount(condition);
		}

		public Double sum(String propertyName) {
			return doSum(condition, propertyName);
		}

		public Boolean delete() throws Exception {
			return deleteWhere(condition);
		}

		public WhereClause<T> orderBy(String... propertyNames) {
			T orderFilter = createVOInstance(condition.getContainedClass());
			orderFilter.setAscendentOrder(propertyNames);
			return new WhereClause<T>(Condition.filterBy(orderFilter).and(
					condition));
		}

		public WhereClause<T> orderByDesc(String... propertyNames) {
			T orderFilter = createVOInstance(condition.getContainedClass());
			orderFilter.setDescendentOrder(propertyNames);
			return new WhereClause<T>(Condition.filterBy(orderFilter).and(
					condition));
		}

		public T selectFirst() {
			return findFirstWhere(condition);
		}
	}

	public class GroupClause<T extends GenericVO, E extends GenericVO> {

		private Condition<T> condition;
		private Class<E> aclass;

		public GroupClause(Condition<T> condition, Class<E> aclass) {
			this.condition = condition;
			this.aclass = aclass;
		}

		public List<E> select() {
			return findListWhereGroupBy(condition, aclass);
		}
	}

	public class JoinClause<T extends GenericVO, E extends GenericVO> {
		private Pair<Condition<? extends GenericVO>, Condition<T>, Condition<E>> joinTypes;

		public JoinClause(Condition<T> c1, Condition<E> c2) {
			joinTypes = new Pair<Condition<? extends GenericVO>, Condition<T>, Condition<E>>(
					c1, c2);
		}

		public SelectJoinClause<T, E> by(String propertyName) {
			List<String> joinPropertiesNames = new ArrayList<String>();
			joinPropertiesNames.add(propertyName);

			return new SelectJoinClause<T, E>(joinPropertiesNames, joinTypes);
		}
	}

	public class Pair<F, T extends F, E extends F> implements Iterable<F> {
		List<F> values = new ArrayList<F>();

		public Pair(T first, E second) {
			values.add(first);
			values.add(second);
		}

		public Pair() {
		}

		public Iterator<F> iterator() {
			return values.iterator();
		}

		public void setAll(Collection<F> values) {
			this.values.addAll(values);
		}

		@SuppressWarnings("unchecked")
		public T getFirst() {
			return (T) values.get(0);
		}

		@SuppressWarnings("unchecked")
		public E getSecond() {
			return (E) values.get(1);
		}

	}

	public class SelectJoinClause<T extends GenericVO, E extends GenericVO> {

		private Pair<Condition<? extends GenericVO>, Condition<T>, Condition<E>> joinTypes;
		private List<String> joinPropertiesNames;
		private List<Order> orderList = null;

		public SelectJoinClause(
				List<String> joinPropertiesNames,
				Pair<Condition<? extends GenericVO>, Condition<T>, Condition<E>> joinTypes) {
			this.joinPropertiesNames = joinPropertiesNames;
			this.joinTypes = joinTypes;
		}

		public SelectJoinClause(
				List<String> joinPropertiesNames,
				Pair<Condition<? extends GenericVO>, Condition<T>, Condition<E>> joinTypes,
				List<Order> orderList) {
			this.joinPropertiesNames = joinPropertiesNames;
			this.joinTypes = joinTypes;
			this.orderList = orderList;
		}

		public List<Pair<GenericVO, T, E>> select() {
			return findListJoin(joinTypes, joinPropertiesNames, orderList);
		}

		public SelectJoinClause<T, E> orderBy(Order... orders) {
			return new SelectJoinClause<T, E>(joinPropertiesNames, joinTypes,
					Arrays.asList(orders));
		}

		public int count() {
			return findListJoin(joinTypes, joinPropertiesNames, orderList)
					.size();
		}

	}

	public <T extends GenericVO> WhereClause<T> where(Condition<T> condition) {
		return new WhereClause<T>(condition);
	}

	public <T extends GenericVO> WhereClause<T> where(T valueObject) {
		return new WhereClause<T>(Condition.filterBy(valueObject));
	}

	public <T extends GenericVO> WhereClause<T> from(Class<T> clazz) {
		return new WhereClause<T>(Condition.filterBy(createVOInstance(clazz)));
	}

	public <T extends GenericVO, E extends GenericVO> JoinClause<T, E> join(
			Class<T> class1, Class<E> class2) {
		return new JoinClause<T, E>(
				Condition.filterBy(createVOInstance(class1)),
				Condition.filterBy(createVOInstance(class2)));
	}

	public <T extends GenericVO, E extends GenericVO> JoinClause<T, E> join(
			Condition<T> c1, Condition<E> c2) {
		return new JoinClause<T, E>(c1, c2);
	}

	public static class Order {
		private String propName;
		private boolean ascendent;
		private int conditionPosition;

		public Order(String propName, boolean ascendent, int conditionPosition) {
			this.propName = propName;
			this.ascendent = ascendent;
			this.conditionPosition = conditionPosition;
		}

		public static Order ascendent(String propName, int conditionPosition) {
			return new Order(propName, true, conditionPosition);
		}

		public static Order descendent(String propName, int conditionPosition) {
			return new Order(propName, false, conditionPosition);
		}

		public String getPropName() {
			return propName;
		}

		public void setPropName(String propName) {
			this.propName = propName;
		}

		public boolean isAscendent() {
			return ascendent;
		}

		public void setAscendent(boolean ascendent) {
			this.ascendent = ascendent;
		}

		public int getConditionPosition() {
			return conditionPosition;
		}

		public void setConditionPosition(int conditionPosition) {
			this.conditionPosition = conditionPosition;
		}
	}
}
