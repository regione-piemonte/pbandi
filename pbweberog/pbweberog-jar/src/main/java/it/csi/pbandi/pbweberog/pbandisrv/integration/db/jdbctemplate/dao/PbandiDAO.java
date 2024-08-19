/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


package it.csi.pbandi.pbweberog.pbandisrv.integration.db.jdbctemplate.dao;

import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.SezioneAppaltoVO;
import it.csi.pbandi.pbservizit.pbandiutil.common.LoggerUtil;
import it.csi.pbandi.pbservizit.pbandiutil.common.ObjectUtil;
import it.csi.pbandi.pbweberog.pbandisrv.business.manager.tracciamento.TracciamentoSrv;
import it.csi.pbandi.pbweberog.pbandisrv.dto.neoflux.NotificaDTO;
import it.csi.pbandi.pbweberog.pbandisrv.dto.neoflux.ProgettoDTO;
import it.csi.pbandi.pbweberog.pbandisrv.dto.neoflux.TaskDTO;
import it.csi.pbandi.pbweberog.util.DateUtil;
import it.csi.pbandi.pbweberog.util.StringUtil;
//import it.csi.pbandi.pbwebrce.pbandisrv.business.manager.tracciamento.TracciamentoSrv;
//import it.csi.pbandi.pbwebrce.pbandisrv.dto.neoflux.NotificaDTO;
//import it.csi.pbandi.pbwebrce.pbandisrv.dto.neoflux.ProgettoDTO;
//import it.csi.pbandi.pbwebrce.pbandisrv.dto.neoflux.TaskDTO;
import it.csi.pbandi.pbweberog.pbandisrv.integration.db.jdbctemplate.vo.GenericVO;
//import it.csi.pbandi.pbwebrce.pbandisrv.integration.db.jdbctemplate.vo.SezioneAppaltoVO;
//import it.csi.pbandi.pbwebrce.pbandisrv.integration.db.jdbctemplate.vo.neoflux.MetaDataVO;
//import it.csi.pbandi.pbwebrce.pbandisrv.integration.util.BeanMapper;
//import it.csi.pbandi.pbwebrce.pbandisrv.util.Constants;
import it.csi.pbandi.pbweberog.pbandisrv.integration.db.jdbctemplate.vo.neoflux.MetaDataVO;
import it.csi.pbandi.pbweberog.pbandiutil.common.BeanUtil;

import java.io.InputStream;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.sql.DataSource;

import oracle.jdbc.OracleConnection;
import oracle.jdbc.OracleConnectionWrapper;
import oracle.sql.ARRAY;
import oracle.sql.ArrayDescriptor;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
//import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.AbstractLobCreatingPreparedStatementCallback;
import org.springframework.jdbc.object.StoredProcedure;
import org.springframework.jdbc.support.lob.LobCreator;
import org.springframework.jdbc.support.lob.LobHandler;

import it.csi.pbandi.pbweberog.pbandisrv.util.Constants;
import it.csi.pbandi.pbweberog.pbandisrv.integration.util.BeanMapper;


//PK eliminato abstract
public  class PbandiDAO {
	private BeanUtil beanUtil;
	private static final String TABLE_NAME_PREFIX = "pbandi_";

	private static final int DUMP_TRACE_LEVEL = 2;

	protected static final String SYSTEM_DATA_DI_INSERIMENTO = "ora_rowscn";

	protected LoggerUtil logger = new LoggerUtil();

	private java.util.Map<String, String> mapTabelleConCampoDataFineValidita = null;

	
	private NamedParameterJdbcTemplate namedJdbcTemplate;
	private NamedParameterJdbcTemplate namedJdbcTemplateRowLimit;

	private DataSource dataSource;

	private TracciamentoSrv traceManagerBusiness;

	public NamedParameterJdbcTemplate getNamedJdbcTemplate() {
		//System.out.println("\n PbandiDAO > getNamedJdbcTemplate");//PK
		return namedJdbcTemplate;
	}

	public NamedParameterJdbcTemplate getNamedJdbcTemplateRowLimit() {
		return namedJdbcTemplateRowLimit;
	}

	public void setDataSource(DataSource dataSource) {
		System.out.println("\n PbandiDAO > setDataSource ");
		this.dataSource = dataSource;
		this.namedJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
		this.namedJdbcTemplateRowLimit = new NamedParameterJdbcTemplate(dataSource);
	}

	@Autowired
	public PbandiDAO(DataSource dataSource) {
		super();
//		this.dataSource = dataSource; //PK
		setDataSource(dataSource);
		System.out.println("\n PbandiDAO > getNamedJdbcTemplate > DataSource =" + dataSource);
	}

	public DataSource getDataSource() {
		return dataSource;
	}

	public void setLogger(LoggerUtil logger) {
		this.logger = logger;
	}

	public LoggerUtil getLogger() {
		return logger;
	}

	public void setTraceManagerBusiness(TracciamentoSrv traceManagerBusiness) {
		this.traceManagerBusiness = traceManagerBusiness;
	}

	public TracciamentoSrv getTraceManagerBusiness() {
		return traceManagerBusiness;
	}

	/**
	 * Riceve la stringa sql con i placeholder,e la mappa dei parametri da
	 * sostituire Effettua la sostituzione e logga la query,eseguibile
	 * direttamente sul db
	 * 
	 * @param sql
	 * @param params
	 */
	private String logQuery(String sql, MapSqlParameterSource params) {
		try {
			StringBuilder sbParamValue = new StringBuilder();
			if (params != null) {
				Map<String, String> substitutionMap = new HashMap<String, String>();
				Map<?, ?> replacementMap = params.getValues();
				for (Object paramKey : replacementMap.keySet()) {
					Object object = replacementMap.get(paramKey);
					if (object != null) {
						String valueString = object.toString();

						if (object instanceof String) {
							valueString = "'" + valueString + "'";
						} else if (object instanceof java.sql.Timestamp) {
							SimpleDateFormat sdf = new SimpleDateFormat(
									Constants.TIME_FORMAT);
							valueString = sdf.format(object);
							valueString = "(to_date('" + valueString
									+ "','dd/MM/yyyy hh24:mi:ss'))";
						} else if (object instanceof java.util.Date) {
							SimpleDateFormat sdf = new SimpleDateFormat(
									Constants.DATE_FORMAT);
							valueString = sdf.format(object);
							valueString = "(to_date('" + valueString
									+ "','dd/MM/yyyy'))";
						}
						sbParamValue.append("<" + paramKey + ": " + object
								+ "> ");

						substitutionMap.put((String) paramKey, valueString);
					}
				}
				sql = substituteMarkers(sql, substitutionMap);

			}
			if(sql !=null && !sql.contains("PBANDI_T_TRACCIAMENTO"))
				getLogger().info(sbParamValue.toString() + "\n" + sql);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			logger.info("Errore logQuery: " + sql);
		}
		return sql;
	}

	private void dumpQuery(String sql, it.csi.util.performance.StopWatch watcher) {
		watcher.stop();
		String sqlSenzaVirgole = sql.replaceAll(",", "\\$VIRGOLA\\$")
				.replaceAll("\n", "\\$ACAPO\\$").replaceAll("\r", " ");

		StackTraceElement trace = new Throwable().getStackTrace()[DUMP_TRACE_LEVEL];
		String className = trace.getClassName();
		String methodName = trace.getMethodName();
		watcher.dumpElapsed(className, methodName, "[" + className + "::"
				+ methodName + "]", sqlSenzaVirgole);
	}

	@SuppressWarnings("unchecked")
	protected <T> T queryForObject(String sql, MapSqlParameterSource params,
			RowMapper<T> rowMapper) {
		Object obj = null;
		logQuery(sql, params);
		it.csi.util.performance.StopWatch watcher = new it.csi.util.performance.StopWatch(
				Constants.STOPWATCH_LOGGER);
		try {
			watcher.start();
			obj = namedJdbcTemplate.queryForObject(sql, params, rowMapper);
			return (T) obj;
		} catch (Exception e) {
			if (e instanceof EmptyResultDataAccessException) {
				getLogger().warn("Dato non trovato sul db");
			} else {
				getLogger().fatal(e.getMessage(), e);
				throw new RuntimeException(e);
			}
		} finally {
			dumpQuery(sql, watcher);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	protected <T> List<T> query(String sql, MapSqlParameterSource params,
			RowMapper<T> rowMapper) {

		//System.out.println("\n query sql="+sql); //PK
		
		List<T> elementList = null;

		String logQuery = logQuery(sql, params);

		it.csi.util.performance.StopWatch watcher = new it.csi.util.performance.StopWatch(
				Constants.STOPWATCH_LOGGER);
		try {
			watcher.start();
			//System.out.println("\n namedJdbcTemplate="+namedJdbcTemplate); //PK
			elementList = namedJdbcTemplate.query(sql, params, rowMapper);
			return elementList;
		} catch (Exception e) {
			if (e instanceof EmptyResultDataAccessException) {
				getLogger().warn("No data found!\n");
			} else {
				getLogger().fatal(e.getMessage(), e);
				throw new RuntimeException(e);
			}

		} finally {
			//dumpQuery(logQuery, watcher);

			getLogger().info(
					"records found: "
							+ (elementList != null ? elementList.size() : 0)+"\n");

		}
		return null;

	}

	protected Integer queryForInt(String sql, MapSqlParameterSource params) {
		String logQuery = logQuery(sql, params);
		it.csi.util.performance.StopWatch watcher = new it.csi.util.performance.StopWatch(
				Constants.STOPWATCH_LOGGER);
		try {
			watcher.start();
			Object obj = namedJdbcTemplate.queryForObject(sql, params, Integer.class);
//			Object obj = namedJdbcTemplate.queryForInt(sql, params);
			return (Integer) obj;
		} catch (Exception e) {
			if (e instanceof EmptyResultDataAccessException) {
				getLogger().warn("No data found!\n");
			} else {
				getLogger().fatal(e.getMessage(), e);
				throw new RuntimeException(e);
			}
		} finally {
			dumpQuery(logQuery, watcher);
		}
		return null;
	}

	protected Long queryForLong(String sql, MapSqlParameterSource params) {
		String logQuery = logQuery(sql, params);

		it.csi.util.performance.StopWatch watcher = new it.csi.util.performance.StopWatch(
				Constants.STOPWATCH_LOGGER);
		try {
			watcher.start();
			Object obj = namedJdbcTemplate.queryForObject(sql, params, Long.class);
//			Object obj = namedJdbcTemplate.queryForLong(sql, params);
			return (Long) obj;
		} catch (Exception e) {
			if (e instanceof EmptyResultDataAccessException) {
				getLogger().warn("No data found!\n");
			} else {
				getLogger().fatal(e.getMessage(), e);
				throw new RuntimeException(e);
			}
		} finally {
			dumpQuery(logQuery, watcher);
		}
		return null;
	}

	protected Double queryForDouble(String sql, MapSqlParameterSource params) {
		String logQuery = logQuery(sql, params);
		it.csi.util.performance.StopWatch watcher = new it.csi.util.performance.StopWatch(
				Constants.STOPWATCH_LOGGER);
		try {
			watcher.start();
			Object obj = namedJdbcTemplate.queryForObject(sql.toString(),
					params, Double.class);
			return (Double) obj;
		} catch (Exception e) {
			if (e instanceof EmptyResultDataAccessException) {
				getLogger().warn("No data found!\n");
			} else {
				getLogger().fatal(e.getMessage(), e);
				throw new RuntimeException(e);
			}
		} finally {
			dumpQuery(logQuery, watcher);
		}
		return null;
	}

	
	protected String queryForString(String sql, MapSqlParameterSource params) {
		String logQuery = logQuery(sql, params);
		it.csi.util.performance.StopWatch watcher = new it.csi.util.performance.StopWatch(
				Constants.STOPWATCH_LOGGER);
		try {
			watcher.start();
			Object obj = namedJdbcTemplate.queryForObject(sql.toString(),
					params, String.class);
			return (String) obj;
		} catch (Exception e) {
			if (e instanceof EmptyResultDataAccessException) {
				getLogger().warn("No data found!\n");
			} else {
				getLogger().fatal(e.getMessage(), e);
				throw new RuntimeException(e);
			}
		} finally {
			dumpQuery(logQuery, watcher);
		}
		return null;
	}
	
	
	@SuppressWarnings("unchecked")
	protected List<Map> queryForList(String sql, MapSqlParameterSource params) {
		it.csi.util.performance.StopWatch watcher = new it.csi.util.performance.StopWatch(
				Constants.STOPWATCH_LOGGER);
		try {
			watcher.start();
			logQuery(sql, params);
			List<Map> list = (List) namedJdbcTemplate.queryForList(sql, params);
			return (List<Map>) list;
		} catch (Exception e) {
			if (e instanceof EmptyResultDataAccessException) {
				 getLogger().warn("No data found!\n");
			} else {
				getLogger().fatal(e.getMessage(), e);
				throw new RuntimeException(e);
			}
		} finally {
			dumpQuery(sql, watcher);
		}
		return null;
	}

	/*
	 * Recupera tutti i record delle tabelle in join che ,se hanno un campo
	 * dt_fine_validita,dev'essere DIVERSA DA NULL
	 */
	protected void setWhereClause(List<StringBuilder> conditionList,
			StringBuilder sqlSelect, StringBuilder tables) {
		Set<String> tablesControlloData = findTableNamesWithAlias(tables
				.toString());

		addDataFine(tablesControlloData, conditionList);
		if (!conditionList.isEmpty()) {
			sqlSelect.append(" WHERE");
			for (int i = 0; i < conditionList.size(); i++) {
				if (i > 0) {
					sqlSelect.append(" AND");
				}
				sqlSelect.append(" ").append(conditionList.get(i));
			}
		}
	}

	/**
	 * Consente di escludere dalle tabelle (tables ) per le quali controllare la
	 * data fine validita' alcune tabelle (tablesDaEscludere)
	 * 
	 * @param conditionList
	 * @param sqlSelect
	 * @param tables
	 * @param tablesDaEscludere
	 */
	protected void setWhereClause(List<StringBuilder> conditionList,
			StringBuilder sqlSelect, StringBuilder tables,
			StringBuilder tablesDaEscludere) {
		Set<String> tablesControlloData = findTableNamesWithAlias(tables
				.toString());
		Set<String> tablesNonControlloData = findTableNamesWithAlias(tablesDaEscludere
				.toString());
		tablesControlloData.removeAll(tablesNonControlloData);

		addDataFine(tablesControlloData, conditionList);
		if (!conditionList.isEmpty()) {
			sqlSelect.append(" WHERE");
			for (int i = 0; i < conditionList.size(); i++) {
				if (i > 0) {
					sqlSelect.append(" AND");
				}
				sqlSelect.append(" ").append(conditionList.get(i));
			}
		}
	}

	@Deprecated
	protected Set<String> findTableNamesWithAlias(String sqlInsert) {
		Set<String> tableNames = new HashSet<String>();

		// non supporta le tabelle con nomi case sensitive
		sqlInsert = sqlInsert.toLowerCase().trim();

		Pattern p = Pattern.compile(TABLE_NAME_PREFIX + "[a-z_]*[ a-z_]*");
		Matcher matcher = p.matcher(sqlInsert);
		while (matcher.find()) {
			tableNames.add(matcher.group().trim());
		}

		return tableNames;
	}

	/*
	 * Usato in alternativa a setWhereClause(List<StringBuilder> conditionList,
	 * StringBuilder sqlSelect,StringBuilder tables) per rintracciare anche gli
	 * eventuali record di tabelle con campo DT_FINE_VALIDITA NULL
	 */
	protected void setWhereClause(List<StringBuilder> conditionList,
			StringBuilder sqlSelect) {
		if (!conditionList.isEmpty()) {
			sqlSelect.append(" WHERE");
			for (int i = 0; i < conditionList.size(); i++) {
				if (i > 0) {
					sqlSelect.append(" AND");
				}
				sqlSelect.append(" ").append(conditionList.get(i));
			}
		}

	}

	/**
	 * 
	 * @param sqlSelect
	 * @param sortColumn
	 * @param sortAsc
	 */
	protected void setOrderBy(StringBuilder sqlSelect, String sortColumn,
			boolean sortAsc) {
		sqlSelect.append(" ORDER BY ").append(sortColumn);
		sqlSelect.append(sortAsc ? " ASC" : " DESC");
	}

	protected Boolean modifica(String sqlUpdate, MapSqlParameterSource params) {
		try {
			// traccio l'operazione prima dell'update,
			// cos� traccio eventualmente i valori precedenti la modifica

			// traceManagerBusiness.tracciaEntita(sqlUpdate, params,
			// TracciamentoSrv.UPDATE);

			return modificaSenzaTracciamento(sqlUpdate, params);

		} finally {

		}

	}

	protected Boolean modificaSenzaTracciamento(String sqlUpdate,
			MapSqlParameterSource params) {
		String logQuery = logQuery(sqlUpdate, params);
		it.csi.util.performance.StopWatch watcher = new it.csi.util.performance.StopWatch(
				Constants.STOPWATCH_LOGGER);
		try {
			watcher.start();

			int record = namedJdbcTemplate.update(sqlUpdate, params);

			getLogger().info("records modified: " + record);

			if (record > 0)
				return Boolean.TRUE;
			else
				return Boolean.FALSE;
		} finally {
			dumpQuery(logQuery, watcher);
		}

	}

	protected boolean eliminaSenzaTracciamento(String sqlDelete,
			MapSqlParameterSource params) {
		String logQuery = logQuery(sqlDelete, params);
		it.csi.util.performance.StopWatch watcher = new it.csi.util.performance.StopWatch(
				Constants.STOPWATCH_LOGGER);
		try {
			watcher.start();

			int record = namedJdbcTemplate.update(sqlDelete, params);

			getLogger().info("record eliminati: " + record);

			if (record > 0)
				return Boolean.TRUE;
			else
				return Boolean.FALSE;
		} finally {
			dumpQuery(logQuery, watcher);
		}
	}

	protected Boolean elimina(String sqlDelete, MapSqlParameterSource params,
			GenericVO... vos) {
			return eliminaSenzaTracciamento(sqlDelete, params);
	}


	public Boolean inserisci(String sqlInsert,
			MapSqlParameterSource params) {

		String logQuery = logQuery(sqlInsert, params);
		it.csi.util.performance.StopWatch watcher = new it.csi.util.performance.StopWatch(
				Constants.STOPWATCH_LOGGER);
		try {
			watcher.start();
			int record = namedJdbcTemplate.update(sqlInsert, params);
			if(sqlInsert !=null && !sqlInsert.contains("PBANDI_T_TRACCIAMENTO ")) getLogger().info("record modificati: " + record);
			if (record > 0)
				return Boolean.TRUE;
			else
				return Boolean.FALSE;

		} finally {
			dumpQuery(logQuery, watcher);
		}
	}

	public String findValorePrecedente(String tabella, String nomeAttributo,
			Map attributiChiave, MapSqlParameterSource params) {
		String ret = null;
		StringBuilder tables = new StringBuilder(tabella);
		List<StringBuilder> conditionList = new ArrayList<StringBuilder>();
		StringBuilder sqlSelect = (new StringBuilder("SELECT "));
		sqlSelect.append(nomeAttributo);
		sqlSelect.append(" FROM ").append(tables);
		Set set = attributiChiave.entrySet();
		Iterator iter = set.iterator();
		while (iter.hasNext()) {
			Map.Entry entry = (Map.Entry) iter.next();
			conditionList.add((new StringBuilder()).append(entry.getKey()
					+ "=:" + entry.getValue()));

		}
		setWhereClause(conditionList, sqlSelect);
		try {
			Object obj = null;
			if (nomeAttributo.contains("DT_")) {
				obj = namedJdbcTemplate.queryForObject(sqlSelect.toString(),
						params, java.util.Date.class);
			} else {
				obj = namedJdbcTemplate.queryForObject(sqlSelect.toString(),
						params, String.class);
			}
			if (obj != null) {
				if (obj instanceof java.util.Date)
					ret = DateUtil.getTime((java.util.Date) obj);
				else
					ret = (String) obj;
			}
		} catch (Exception x) {
			getLogger().warn(" errore: " + x.getMessage());
		}
		return ret;
	}

	public static Set<String> findTableNames(String sqlInsert) {
		Set<String> tableNames = new HashSet<String>();

		// non supporta le tabelle con nomi case sensitive
		sqlInsert = sqlInsert.toLowerCase().trim();

		Pattern p = Pattern.compile(TABLE_NAME_PREFIX + "[a-z_]*");
		Matcher matcher = p.matcher(sqlInsert);
		while (matcher.find()) {
			tableNames.add(matcher.group().trim());
		}

		return tableNames;
	}

	private void addDataFine(Set<String> tables,
			List<StringBuilder> conditionList) {

		mapTabelleConCampoDataFineValidita = getTabelleConCampoDataFineValidita();
		if (tables.size() > 0) {
			for (String table : tables) {
				String nomeTabella = mapTabelleConCampoDataFineValidita
						.get(table.toUpperCase().trim());
				if (nomeTabella != null)
					conditionList
							.add((new StringBuilder())
									.append("nvl(trunc("
											+ nomeTabella
											+ ".DT_FINE_VALIDITA), trunc(sysdate+1)) > trunc(sysdate)"));
			}
		}
	}

	/**
	 * restituisce tutte le tabelle che hanno un campo data fine validita
	 * 
	 * @return
	 */
	private Map<String, String> getTabelleConCampoDataFineValidita() {
		mapTabelleConCampoDataFineValidita = getTabelleByCampo(
				"DT_FINE_VALIDITA", mapTabelleConCampoDataFineValidita);
		return mapTabelleConCampoDataFineValidita;
	}

	private Map<String, String> getTabelleByCampo(String nomeCampo, Map map) {
		if (map == null) {
			map = new java.util.HashMap<String, String>();
			MapSqlParameterSource params = new MapSqlParameterSource();
			StringBuilder tables = new StringBuilder(" sys.all_tab_columns ");
			List<StringBuilder> conditionList = new ArrayList<StringBuilder>();
			conditionList
					.add((new StringBuilder()).append("owner like :owner"));
			conditionList.add((new StringBuilder())
					.append("table_name like :tableName"));
			conditionList.add((new StringBuilder())
					.append("column_name = :columnName"));
			StringBuilder sqlSelect = (new StringBuilder("SELECT TABLE_NAME "
					+ " FROM ")).append(tables);
			params.addValue("owner", "PBANDI%", Types.VARCHAR);
			params.addValue("tableName", "PBANDI%", Types.VARCHAR);
			params.addValue("columnName", nomeCampo, Types.VARCHAR);

			setWhereClause(conditionList, sqlSelect);

			// logger.debug(" query: " + sqlSelect.toString());

			List<Map> listTabelle = queryForList(sqlSelect.toString(), params);
			StringBuilder sbTabelle = new StringBuilder();

			for (Map tabella : listTabelle) {
				java.util.Collection<String> c = tabella.values();
				String nomeTabella = "";
				if (c.iterator().hasNext()) {
					nomeTabella = c.iterator().next();
					sbTabelle.append("nomeTabella:" + nomeTabella + ",");
					map.put(nomeTabella, nomeTabella);
				}

			}
			getLogger().debug(
					"tabelle con " + nomeCampo + ": " + sbTabelle.toString());
		}
		return map;
	}

	protected int modificaConRitorno(String sqlUpdate,
			MapSqlParameterSource params) {
		it.csi.util.performance.StopWatch watcher = new it.csi.util.performance.StopWatch(
				Constants.STOPWATCH_LOGGER);

		String logQuery = logQuery(sqlUpdate, params);
		try {
			watcher.start();
			int record = namedJdbcTemplate.update(sqlUpdate, params);
			getLogger().info("records modified: " + record+"\n");

			return record;
		} finally {
			dumpQuery(logQuery, watcher);
		}

	}

	public class ProcedureSQL {
		private abstract class LoggingStoredProcedure extends StoredProcedure {
			public LoggingStoredProcedure(DataSource dataSource, String string) {
				super(dataSource, string);
			}

			@SuppressWarnings("unchecked")
			@Override
			public Map execute(Map inParams) throws DataAccessException {
				it.csi.util.performance.StopWatch watcher = new it.csi.util.performance.StopWatch(
						Constants.STOPWATCH_LOGGER);
				try {
					watcher.start();
					logger.info("Input procedure :  " + inParams);

					Map result = super.execute(inParams);
					logger.info("Output procedure: " + result  +"\n");
					return result;
				} finally {
					dumpQuery(this.getSql(), watcher);
				}
			}
		}

		
		
		
		
		
	private class PutNotificationMetadataStoredProcedure extends
		LoggingStoredProcedure {

	private static final String P_NOME_METADATA = "p_nome_metadata";
	private static final String P_VALORE_METADATA = "p_valore_metadata";

	public PutNotificationMetadataStoredProcedure() {
		super(getDataSource(), Constants.NOME_PACKAGE_PROC_PROCESSO + "."
				+ "PutNotificationMetadata");

		declareParameter(new SqlParameter(P_NOME_METADATA, Types.VARCHAR));
		declareParameter(new SqlParameter(P_VALORE_METADATA, Types.VARCHAR));

		compile();
	}

	public void execute(String p_nome_metadata, String p_valore_metadata ) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(P_NOME_METADATA, p_nome_metadata);
		map.put(P_VALORE_METADATA, p_valore_metadata);

		execute(map);
	}
};

                  
		



		private class RipartizionePagamentiStoredProcedure extends
				LoggingStoredProcedure {

			private static final String P_ID_UTENTE_INS = "pIdUtenteIns";
			private static final String P_ELENCO_DOC = "pElencoDoc";
			private static final String P_ID_PROGETTO = "pIdProgetto";
			private static final String P_ID_DICHIARAZIONE = "pIdDichiarazioneSpesa";

			public RipartizionePagamentiStoredProcedure() {
				super(getDataSource(), Constants.NOME_PACKAGE_UTILITY_ONLINE + "."
						+ "RipartizionePagamenti");

				declareParameter(new SqlParameter(P_ID_PROGETTO, Types.NUMERIC));
				declareParameter(new SqlParameter(P_ELENCO_DOC, Types.VARCHAR));
				declareParameter(new SqlParameter(P_ID_DICHIARAZIONE,
						Types.NUMERIC));
				declareParameter(new SqlParameter(P_ID_UTENTE_INS,
						Types.NUMERIC));

				compile();
			}

			public void execute(BigDecimal idProgetto, String listaId,
					Long idDichiarazione, BigDecimal idUtente) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put(P_ID_PROGETTO, idProgetto);
				map.put(P_ELENCO_DOC, listaId);
				map.put(P_ID_DICHIARAZIONE, idDichiarazione);
				map.put(P_ID_UTENTE_INS, idUtente);

				execute(map);
			}
		};

		private class AggiornaImpegnoStoredProcedure extends
				LoggingStoredProcedure {

			private static final String RESULT = "result";
			private static final String P_ID_IMPEGNO = "pIdImpegni";
			private static final String P_ON_LINE = "pOnLine";

			public AggiornaImpegnoStoredProcedure() {
				super(getDataSource(), Constants.NOME_PACKAGE_PROC_BILANCIO
						+ "." + "Impegni");
				declareParameter(new SqlOutParameter(RESULT, Types.NUMERIC));
				declareParameter(new SqlParameter(P_ID_IMPEGNO, Types.NUMERIC));
				declareParameter(new SqlParameter(P_ON_LINE, Types.VARCHAR));
				setFunction(true);

				compile();

			}

			public int execute(BigDecimal idImpegni, String flagOnLine) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put(P_ID_IMPEGNO, idImpegni);
				map.put(P_ON_LINE, flagOnLine);

				int res = 1;
				Map<String, Object> results = execute(map);
				if (results.containsKey(RESULT)) {
					res = ((BigDecimal) results.get(RESULT)).intValue();
				}

				return res;
			}

		}

		private class CaricamentoSchedaProgettoStoredProcedure extends
				LoggingStoredProcedure {

			private static final String RESULT = "result";
			private static final String P_ID_CSP_PROGETTO = "pIdCspProgetto";

			public CaricamentoSchedaProgettoStoredProcedure() {
				super(getDataSource(), Constants.NOME_PACKAGE_UTILITY_ONLINE + "."
						+ "CaricamentoSchedaProgetto");

				declareParameter(new SqlOutParameter(RESULT, Types.NUMERIC));
				declareParameter(new SqlParameter(P_ID_CSP_PROGETTO,
						Types.NUMERIC));

				setFunction(true);
				compile();
			}

			@SuppressWarnings("unchecked")
			public int execute(BigDecimal idCspProgetto) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put(P_ID_CSP_PROGETTO, idCspProgetto);

				int res = 1;
				Map<String, Object> results = execute(map);
				if (results.containsKey(RESULT)) {
					res = ((BigDecimal) results.get(RESULT)).intValue();
				}

				return res;
			}
		};
		
		private class IsFESRStoredProcedure extends
		LoggingStoredProcedure {

			private static final String RESULT = "result";
			private static final String P_ID_PROGETTO = "pIdProgetto";
		
			public IsFESRStoredProcedure() {
				super(getDataSource(), Constants.NOME_PACKAGE_UTILITY_ONLINE + "."
						+ "IsFESR");
		
				declareParameter(new SqlOutParameter(RESULT, Types.VARCHAR));
				declareParameter(new SqlParameter(P_ID_PROGETTO, Types.NUMERIC));
		
				setFunction(true);
				compile();
			}
		
			@SuppressWarnings("unchecked")
			public String execute(BigDecimal idProgetto) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put(P_ID_PROGETTO, idProgetto);
		
				String res = "";
				Map<String, Object> results = execute(map);
				if (results.containsKey(RESULT)) {
					res = ((String) results.get(RESULT));
				}
		
				return res;
			}
		};

		private class CreaPropostaCertificazioneStoredProcedure extends
				LoggingStoredProcedure {

			private static final String RESULT = "result";
			private static final String P_ID_PROPOSTA_CERTIFICAZIONE = "pIdPropostaCertificazione";

			public CreaPropostaCertificazioneStoredProcedure() {
			//	super(getDataSource(), Constants.NOME_PACKAGE_UTILITY_ONLINE + "."+ "CreaPropostaCertificazione");
				super(getDataSource(), Constants.NOME_PACKAGE_PCK_PBANDI_CERTIFICAZIONE + "."+ "MainCreaPropostaCertificazione");
				declareParameter(new SqlOutParameter(RESULT, Types.NUMERIC));
				declareParameter(new SqlParameter(P_ID_PROPOSTA_CERTIFICAZIONE,
						Types.NUMERIC));

				setFunction(true);
				compile();
			}

			@SuppressWarnings("unchecked")
			public int execute(BigDecimal idPropostaCertificazione) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put(P_ID_PROPOSTA_CERTIFICAZIONE, idPropostaCertificazione);

				int res = 1;
				Map<String, Object> results = execute(map);
				if (results.containsKey(RESULT)) {
					res = ((BigDecimal) results.get(RESULT)).intValue();
				}

				return res;
			}
		};
		
		private class CreaPropostaCertificazioneRevStoredProcedure extends LoggingStoredProcedure {

			private static final String RESULT = "result";
			private static final String P_ID_PROPOSTA_CERTIFICAZIONE = "pIdPropostaCertificazione";

			public CreaPropostaCertificazioneRevStoredProcedure() {
				super(getDataSource(), Constants.NOME_PACKAGE_PCK_PBANDI_CERTIFICAZIONE_REV + "."+ "MainCreaPropostaCertificazione");
				declareParameter(new SqlOutParameter(RESULT, Types.NUMERIC));
				declareParameter(new SqlParameter(P_ID_PROPOSTA_CERTIFICAZIONE,
				Types.NUMERIC));
				setFunction(true);
				compile();
			}

			@SuppressWarnings("unchecked")
			public int execute(BigDecimal idPropostaCertificazione) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put(P_ID_PROPOSTA_CERTIFICAZIONE, idPropostaCertificazione);

				int res = 1;
				Map<String, Object> results = execute(map);
				if (results.containsKey(RESULT)) {
					res = ((BigDecimal) results.get(RESULT)).intValue();
				}

				return res;
			}
		};
		
		private class CambiaSinonimiCertificazioneRevStoredProcedure extends LoggingStoredProcedure {

			private static final String RESULT = "result";

			public CambiaSinonimiCertificazioneRevStoredProcedure() {
				super(getDataSource(), "FN_SW_SYNONYM_REV_CERTIF");
				declareParameter(new SqlOutParameter(RESULT, Types.NUMERIC));
				setFunction(true);
				compile();
			}

			@SuppressWarnings("unchecked")
			public int execute() {
				Map<String, Object> map = new HashMap<String, Object>();
				int res = 1;
				Map<String, Object> results = execute(map);
				if (results.containsKey(RESULT)) {
					res = ((BigDecimal) results.get(RESULT)).intValue();
				}
				return res;
			}
		};
		
		private class RipristinaSinonimiCertificazioneRevStoredProcedure extends LoggingStoredProcedure {

			private static final String RESULT = "result";

			public RipristinaSinonimiCertificazioneRevStoredProcedure() {
				super(getDataSource(), "FN_UNDO_SYNONYM_REV_CERTIF");
				declareParameter(new SqlOutParameter(RESULT, Types.NUMERIC));
				setFunction(true);
				compile();
			}

			@SuppressWarnings("unchecked")
			public int execute() {
				Map<String, Object> map = new HashMap<String, Object>();
				int res = 1;
				Map<String, Object> results = execute(map);
				if (results.containsKey(RESULT)) {
					res = ((BigDecimal) results.get(RESULT)).intValue();
				}
				return res;
			}
		};


		private class ControlliCertificazioneStoredProcedure extends
				LoggingStoredProcedure {

			private static final String RESULT = "result";
			private static final String P_ID_PROPOSTA_CERTIFICAZIONE = "pIdPropostaCertificazione";

			public ControlliCertificazioneStoredProcedure() {
				super(getDataSource(), Constants.NOME_PACKAGE_PCK_PBANDI_CERTIFICAZIONE + "."
						+ "MainControlliCertificazione");

				declareParameter(new SqlOutParameter(RESULT, Types.NUMERIC));
				declareParameter(new SqlParameter(P_ID_PROPOSTA_CERTIFICAZIONE,
						Types.NUMERIC));

				setFunction(true);
				compile();
			}

			@SuppressWarnings("unchecked")
			public int execute(BigDecimal idPropostaCertificazione) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put(P_ID_PROPOSTA_CERTIFICAZIONE, idPropostaCertificazione);

				int res = 1;
				Map<String, Object> results = execute(map);
				if (results.containsKey(RESULT)) {
					res = ((BigDecimal) results.get(RESULT)).intValue();
				}

				return res;
			}
		};

		private class CaricaAttoDiLiquidazioneStoredProcedure extends
				LoggingStoredProcedure {

			private static final String RESULT = "result";
			private static final String P_ID_ATTI_DT = "pIdAttiDt";
			private static final String P_ON_LINE = "pOnLine";

			public CaricaAttoDiLiquidazioneStoredProcedure() {
				super(getDataSource(), Constants.NOME_PACKAGE_PROC_BILANCIO
						+ "." + "atti");

				declareParameter(new SqlOutParameter(RESULT, Types.NUMERIC));
				declareParameter(new SqlParameter(P_ID_ATTI_DT, Types.NUMERIC));
				declareParameter(new SqlParameter(P_ON_LINE, Types.VARCHAR));

				setFunction(true);
				compile();
			}

			public int execute(BigDecimal idImpegni, String flagOnLine) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put(P_ID_ATTI_DT, idImpegni);
				map.put(P_ON_LINE, flagOnLine);

				int res = 1;
				Map<String, Object> results = execute(map);
				if (results.containsKey(RESULT)) {
					res = ((BigDecimal) results.get(RESULT)).intValue();
				}

				return res;
			}
		};

		private class CaricaAttivitaPregresseStoredProcedure extends
				LoggingStoredProcedure {

			private static final String RESULT = "result";
			private static final String P_ID_PROGETTO = "pIdProgetto";

			public CaricaAttivitaPregresseStoredProcedure() {
				super(getDataSource(), Constants.NOME_PACKAGE_UTILITY_ONLINE + "."
						+ "attivitaPregresse");

				declareParameter(new SqlOutParameter(RESULT, Types.NUMERIC));
				declareParameter(new SqlParameter(P_ID_PROGETTO, Types.NUMERIC));

				setFunction(true);
				compile();
			}

			@SuppressWarnings("unchecked")
			public int execute(BigDecimal idProgetto) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put(P_ID_PROGETTO, idProgetto);

				int res = -1;
				Map<String, Object> results = execute(map);
				if (results.containsKey(RESULT)) {
					res = ((BigDecimal) results.get(RESULT)).intValue();
				}

				return res;
			}
		};
		
		private class CreaIntermediaFinaleStoredProcedure extends LoggingStoredProcedure {


			private static final String P_ID_UTENTE = "pIdUtente";
			private static final String RESULT = "result";

			public CreaIntermediaFinaleStoredProcedure() {
				super(getDataSource(), Constants.NOME_PACKAGE_PCK_PBANDI_CERTIFICAZIONE + "." + "fnc_CreaPropCertificazFoto");
				
				declareParameter(new SqlOutParameter(RESULT, Types.NUMERIC));
				declareParameter(new SqlParameter(P_ID_UTENTE, Types.NUMERIC));

				setFunction(true);
				compile();
			}
			
			@SuppressWarnings("unchecked")
			public int execute(BigDecimal idUtente) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put(P_ID_UTENTE, idUtente);

				int res = -1;
				Map<String, Object> results = execute(map);
				if (results.containsKey(RESULT)) {
					res = ((BigDecimal) results.get(RESULT)).intValue();
				}

				return res;
			}

		};
		
		private class CreaNotifAnticipiStoredProcedure extends
		LoggingStoredProcedure {

			private static final String RESULT = "result";
			private static final String P_ID_PROPOSTA_CERTIFICAZ = "pIdPropostaCertificaz";

			public CreaNotifAnticipiStoredProcedure() {
				super(getDataSource(), Constants.NOME_PACKAGE_PCK_PBANDI_NOTIFICH + "."
						+ "FNC_CREA_NOTIF_ANTICIPI");
				declareParameter(new SqlOutParameter(RESULT, Types.NUMERIC));
				declareParameter(new SqlParameter(P_ID_PROPOSTA_CERTIFICAZ,Types.NUMERIC));
				setFunction(true);
				compile();
			}
		
			@SuppressWarnings("unchecked")
			public int execute(BigDecimal idPropostaCertificazione) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put(P_ID_PROPOSTA_CERTIFICAZ, idPropostaCertificazione);
		
				int res = 1;
				Map<String, Object> results = execute(map);
				if (results.containsKey(RESULT)) {
					res = ((BigDecimal) results.get(RESULT)).intValue();
				}
		
				return res;
			}
		};
		
		private class CreaNotifAnticipiMailStoredProcedure extends
		LoggingStoredProcedure {

			private static final String RESULT = "result";

			public CreaNotifAnticipiMailStoredProcedure() {
				super(getDataSource(), Constants.NOME_PACKAGE_PCK_PBANDI_NOTIFICH + "."
						+ "FNC_CREA_NOTIF_ANTICIPI_MAIL");
				declareParameter(new SqlOutParameter(RESULT, Types.NUMERIC));
				setFunction(true);
				compile();
			}
		
			@SuppressWarnings("unchecked")
			public int execute() {
				Map<String, Object> map = new HashMap<String, Object>();
		
				int res = 1;
				Map<String, Object> results = execute(map);
				if (results.containsKey(RESULT)) {
					res = ((BigDecimal) results.get(RESULT)).intValue();
				}
		
				return res;
			}
		};
		
		private class AggiornaPropostaIntermediaFinaleStoredProcedure extends LoggingStoredProcedure {


			private static final String P_ID_UTENTE = "pIdUtente";
			private static final String RESULT = "result";

			public AggiornaPropostaIntermediaFinaleStoredProcedure() {
				super(getDataSource(), Constants.NOME_PACKAGE_PCK_PBANDI_CERTIFICAZIONE + "." + "CreaPropostaCertificazAggInt");
				
				declareParameter(new SqlOutParameter(RESULT, Types.NUMERIC));
				declareParameter(new SqlParameter(P_ID_UTENTE, Types.NUMERIC));

				setFunction(true);
				compile();
			}
			
			
			@SuppressWarnings("unchecked")
			public int execute(BigDecimal idUtente) throws Exception {
				Map<String, Object> map = new HashMap<String, Object>();
				
				map.put(P_ID_UTENTE, idUtente);
				
				int res = -1;
				Map<String, Object> results = execute(map);
				if (results.containsKey(RESULT)) {
					res = ((BigDecimal) results.get(RESULT)).intValue();
				}

				return res;
			}
		};

///////////////////
// NEOFLUX
		
		
		
	private class CountProgettiBENStoredProcedure extends
		LoggingStoredProcedure {

		private static final String P_PROGR_BANDO_LINEA_INTERVENTO = "p_progr_bando_linea_intervento";
		private static final String P_ID_SOGGETTO_BEN = "p_id_soggetto_ben";
		private static final String P_ID_UTENTE = "p_id_utente";
		
		private static final String V_RITORNO = "v_ritorno";

		public CountProgettiBENStoredProcedure() {
			super(getDataSource(), Constants.NOME_PACKAGE_PROC_PROCESSO + "."
					+ "CountProgettiBEN");

			declareParameter(new SqlOutParameter(V_RITORNO, Types.NUMERIC));
			declareParameter(new SqlParameter(P_PROGR_BANDO_LINEA_INTERVENTO, Types.NUMERIC));
			declareParameter(new SqlParameter(P_ID_SOGGETTO_BEN, Types.NUMERIC));
			declareParameter(new SqlParameter(P_ID_UTENTE, Types.NUMERIC));

			setFunction(true);
			compile();
		}

		@SuppressWarnings("unchecked")
		public int execute(BigDecimal progrBandoLineaIntervento,BigDecimal idSoggettoBen,BigDecimal idUtente) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put(P_PROGR_BANDO_LINEA_INTERVENTO, progrBandoLineaIntervento);
			map.put(P_ID_SOGGETTO_BEN, idSoggettoBen);
			map.put(P_ID_UTENTE, idUtente);
			int res =0;
			Map<String, Object> results = execute(map);
			if (!results.isEmpty( )) {
				if(results.get(V_RITORNO)!=null){
					res = ((BigDecimal) results.get(V_RITORNO)).intValue();
				}
			}

			return res;
		}
	};
		
	
	private class SendNotificationMessageStoredProcedure extends
	LoggingStoredProcedure {
		private static final String V_RITORNO = "v_ritorno";
		private static final String P_ID_PROGETTO = "p_id_progetto";
		private static final String P_DESCR_BREVE_TEMPLATE_NOTIF = "p_descr_breve_template_notif";
		private static final String P_CODICE_RUOLO = "p_codice_ruolo";
		private static final String P_ID_UTENTE = "p_id_utente";
		private static final String P_ID_TARGET = "p_id_target";
		
		public SendNotificationMessageStoredProcedure() {
			super(getDataSource(), Constants.NOME_PACKAGE_PROC_PROCESSO
					+ "." + "SendNotificationMessage");
			declareParameter(new SqlOutParameter(V_RITORNO, Types.NUMERIC));
			declareParameter(new SqlParameter(P_ID_PROGETTO, Types.NUMERIC));
			declareParameter(new SqlParameter(P_DESCR_BREVE_TEMPLATE_NOTIF, Types.VARCHAR));
			declareParameter(new SqlParameter(P_CODICE_RUOLO, Types.VARCHAR));
			declareParameter(new SqlParameter(P_ID_UTENTE, Types.NUMERIC));
			declareParameter(new SqlParameter(P_ID_TARGET, Types.NUMERIC));
			
			setFunction(true);
			compile();
		}
		
		public void execute(BigDecimal p_id_progetto,String p_descr_breve_template_notif, String p_codice_ruolo,Long p_id_utente,Long p_id_target) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put(P_ID_PROGETTO, p_id_progetto);
			map.put(P_DESCR_BREVE_TEMPLATE_NOTIF, p_descr_breve_template_notif);
			map.put(P_CODICE_RUOLO, p_codice_ruolo);
			map.put(P_ID_UTENTE, p_id_utente);
			map.put(P_ID_TARGET, p_id_target);
		
			execute(map);
		}
		};
	
	
		
		
		
		
		private class CancelNotificationMessageStoredProcedure extends
		LoggingStoredProcedure {

		private static final String V_RITORNO = "v_ritorno";
		private static final String ID_NOTIFICA= "idNotifica";
		private static final String ID_UTENTE = "idUtente";
		
	 
		public CancelNotificationMessageStoredProcedure() {
			super(getDataSource(), Constants.NOME_PACKAGE_PROC_PROCESSO + "."
					+ "CancelNotificationMessage");

			declareParameter(new SqlOutParameter(V_RITORNO, Types.NUMERIC));
			declareParameter(new SqlParameter(ID_NOTIFICA, Types.NUMERIC));
			declareParameter(new SqlParameter(ID_UTENTE, Types.NUMERIC));

			setFunction(true);
			compile();
		}

		@SuppressWarnings("unchecked")
		public int execute(BigDecimal idNotifica,BigDecimal idUtente) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put(ID_NOTIFICA, idNotifica);
			map.put(ID_UTENTE, idUtente);

			int res = -1;
			Map<String, Object> results = execute(map);
			if (!results.isEmpty( )) {
				if(results.get(V_RITORNO)!=null){
					res = ((BigDecimal) results.get(V_RITORNO)).intValue();
				}
			}

			return res;
		}
	};

		
		
		
		
	private class EndAttivitaStoredProcedure extends
	LoggingStoredProcedure {

	private static final String V_RITORNO = "v_ritorno";
	private static final String ID_PROGETTO = "idProgetto";
	private static final String DESCR_BREVE_TASK = "descBreveTask";
	private static final String ID_UTENTE = "idUtente";
	
 
	public EndAttivitaStoredProcedure() {
		super(getDataSource(), Constants.NOME_PACKAGE_PROC_PROCESSO + "."
				+ "EndAttivita");

		declareParameter(new SqlOutParameter(V_RITORNO, Types.NUMERIC));
		declareParameter(new SqlParameter(ID_PROGETTO, Types.NUMERIC));
		declareParameter(new SqlParameter(DESCR_BREVE_TASK, Types.VARCHAR));
		declareParameter(new SqlParameter(ID_UTENTE, Types.NUMERIC));

		setFunction(true);
		compile();
	}

	@SuppressWarnings("unchecked")
	public int execute(BigDecimal idProgetto,String descBreveTask,BigDecimal idUtente) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(ID_PROGETTO, idProgetto);
		map.put(DESCR_BREVE_TASK, descBreveTask);
		map.put(ID_UTENTE, idUtente);

		int res = -1;
		Map<String, Object> results = execute(map);
		if (!results.isEmpty( )) {
			if(results.get(V_RITORNO)!=null){
				res = ((BigDecimal) results.get(V_RITORNO)).intValue();
			}
		}

		return res;
	}
	};

	
	
	private class GetProcessoBLStoredProcedure extends
		LoggingStoredProcedure {

		private static final String ID_PROCESSO = "idProcesso";
		private static final String PROGR_BANDO_LINEA_INTERVENTO = "progrBandoLineaIntervento";
	
		public GetProcessoBLStoredProcedure() {
			super(getDataSource(), Constants.NOME_PACKAGE_PROC_PROCESSO + "."
					+ "GetProcessoBL");
	
			declareParameter(new SqlOutParameter(ID_PROCESSO, Types.NUMERIC));
			declareParameter(new SqlParameter(PROGR_BANDO_LINEA_INTERVENTO, Types.NUMERIC));
	
			setFunction(true);
			compile();
		}
	
		@SuppressWarnings("unchecked")
		public int execute(BigDecimal progrBandoLineaIntervento) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put(PROGR_BANDO_LINEA_INTERVENTO, progrBandoLineaIntervento);
	
			int res = -1;
			Map<String, Object> results = execute(map);
			if (!results.isEmpty( )) {
				if(results.get(ID_PROCESSO)!=null){
					res = ((BigDecimal) results.get(ID_PROCESSO)).intValue();
				}
			}
	
			return res;
		}
	};
	
	private class AvviaCampionamentoStoredProcedure extends
	LoggingStoredProcedure {

		private static final String P_DESC_BREVE_LINEA = "pDescBreveLinea";
		private static final String P_ID_UTENTE_INS = "pID_UTENTE_INS";
		private static final String ID_CAMPIONE = "v_ritorno";

		public AvviaCampionamentoStoredProcedure() {
			super(getDataSource(), Constants.NOME_PACKAGE_PCK_BANDI_CAMPIONAMENTO + "."
					+ "fnc_MainCampionamento");

			declareParameter(new SqlOutParameter(ID_CAMPIONE, Types.NUMERIC));
			declareParameter(new SqlParameter(P_DESC_BREVE_LINEA, Types.VARCHAR));
			declareParameter(new SqlParameter(P_ID_UTENTE_INS, Types.NUMERIC));
			
			setFunction(true);
			compile();
		}

		@SuppressWarnings("unchecked")
		public BigDecimal execute(String descBreveLinea, Long idUtente) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put(P_DESC_BREVE_LINEA, descBreveLinea);
			map.put(P_ID_UTENTE_INS, idUtente);

			BigDecimal res = new BigDecimal(-1);
			Map<String, Object> results = execute(map);
			if (!results.isEmpty( )) {
				if(results.get(ID_CAMPIONE)!=null){
					res = ((BigDecimal) results.get(ID_CAMPIONE));
				}
			}
			return res;
		}
	};
	
	
	private class GetProcessoStoredProcedure extends
	LoggingStoredProcedure {

	private static final String ID_PROCESSO = "idProcesso";
	private static final String ID_PROGETTO = "idProgetto";

	public GetProcessoStoredProcedure() {
		super(getDataSource(), Constants.NOME_PACKAGE_PROC_PROCESSO + "."
				+ "GetProcesso");

		declareParameter(new SqlOutParameter(ID_PROCESSO, Types.NUMERIC));
		declareParameter(new SqlParameter(ID_PROGETTO, Types.NUMERIC));

		setFunction(true);
		compile();
	}

	@SuppressWarnings("unchecked")
	public int execute(BigDecimal idProgetto) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(ID_PROGETTO, idProgetto);

		int res = -1;
		Map<String, Object> results = execute(map);
		if (!results.isEmpty( )) {
			if(results.get(ID_PROCESSO)!=null){
				res = ((BigDecimal) results.get(ID_PROCESSO)).intValue();
			}
		}

		return res;
	}
};




private class IsLockedStoredProcedure extends
LoggingStoredProcedure {

private static final String V_RITORNO = "v_ritorno";
private static final String ID_PROGETTO = "idProgetto";
private static final String DESCR_BREVE_TASK = "descBreveTask";
private static final String ID_UTENTE = "idUtente";


public IsLockedStoredProcedure() {
	super(getDataSource(), Constants.NOME_PACKAGE_PROC_PROCESSO + "."
			+ "IsLocked");

	declareParameter(new SqlOutParameter(V_RITORNO, Types.VARCHAR));
	declareParameter(new SqlParameter(ID_PROGETTO, Types.NUMERIC));
	declareParameter(new SqlParameter(DESCR_BREVE_TASK, Types.VARCHAR));
	declareParameter(new SqlParameter(ID_UTENTE, Types.NUMERIC));

	setFunction(true);
	compile();
}

@SuppressWarnings("unchecked")
public String execute(BigDecimal idProgetto,String descBreveTask,BigDecimal idUtente) {
	Map<String, Object> map = new HashMap<String, Object>();
	map.put(ID_PROGETTO, idProgetto);
	map.put(DESCR_BREVE_TASK, descBreveTask);
	map.put(ID_UTENTE, idUtente);

	String res = null;
	Map<String, Object> results = execute(map);
	if (!results.isEmpty( )) {
		if(results.get(V_RITORNO)!=null){
			res = ((String) results.get(V_RITORNO)) ;
		}
	}

	return res;
}
};


/*
 *  Nel database di test ho aggiunto, nel package PCK_PBANDI_CERTIFICAZIONE, una nuova procedura nominata "update_anticipi" che ricalcola l'IMPORTO_ANTICIPO della tabella PBANDI_R_DETT_PROP_CER_LIN_ANT.
	La procedura dovr� essere eseguita dall'applicativo on-line a fronte di una variazione dell'IMPORTO_CERTIFICAZIONE_NETTO.
	La procedura dovr� essere richiamata passando come primo parametro l'ID della proposta di certificazione, come secondo l'ID del progetto) .
	Es. PCK_PBANDI_CERTIFICAZIONE.update_anticipi(parametro 1, parametro2)
 */

private class UpdateAnticipiStoredProcedure extends LoggingStoredProcedure {
	
	private static final String ID_PROPOSTA_CERTIFICAZIONE = "pIdPropostaCertificaz";
	private static final String ID_PROGETTO = "pIdProgetto";
	
	public UpdateAnticipiStoredProcedure() {
		super(getDataSource(), Constants.NOME_PACKAGE_PCK_PBANDI_CERTIFICAZIONE + "." + "update_anticipi");
		declareParameter(new SqlParameter(ID_PROPOSTA_CERTIFICAZIONE, Types.NUMERIC));
		declareParameter(new SqlParameter(ID_PROGETTO, Types.NUMERIC));

		compile();
	}
	
	@SuppressWarnings("unchecked")
	public void execute(BigDecimal idPropostaCertificazione, BigDecimal idProgetto) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(ID_PROGETTO, idProgetto);
		map.put(ID_PROPOSTA_CERTIFICAZIONE, idPropostaCertificazione);

		execute(map);
	
	}
}


/*	String codiceFiscaleNuovoBen,
			Long idProgetto,
			Long idEnteGiuridicoNew,
			Long idIndirizzoNew,
			Long idSedeNew,
			Long idRecapitiNew,
			String caso*/

private class CambiaBeneficiarioStoredProcedure extends
LoggingStoredProcedure {

private static final String V_RITORNO = "v_ritorno";
private static final String CODICE_FISCALE_NUOVO_BEN="codiceFiscaleNuovoBen";
private static final String ID_PROGETTO = "idProgetto";
private static final String ID_ENTEGIURIDICO_NEW = "idEnteGiuridicoNew";
private static final String ID_INDIRIZZO_NEW = "idIndirizzoNew";
private static final String ID_SEDE_NEW = "idSedeNew";
private static final String ID_RECAPITI_NEW = "idRecapitiNew";
private static final String CASO = "caso";

public CambiaBeneficiarioStoredProcedure() {
	super(getDataSource(), Constants.NOME_PACKAGE_PROC_CAMBIO_BENEFICIARIO + "."
			+ "Main");

	declareParameter(new SqlOutParameter(V_RITORNO, Types.INTEGER));
	declareParameter(new SqlParameter(CODICE_FISCALE_NUOVO_BEN, Types.VARCHAR));
	declareParameter(new SqlParameter(ID_PROGETTO, Types.NUMERIC));
	declareParameter(new SqlParameter(ID_ENTEGIURIDICO_NEW, Types.NUMERIC));
	declareParameter(new SqlParameter(ID_INDIRIZZO_NEW, Types.NUMERIC));
	declareParameter(new SqlParameter(ID_SEDE_NEW, Types.NUMERIC));
	declareParameter(new SqlParameter(ID_RECAPITI_NEW, Types.NUMERIC));
	declareParameter(new SqlParameter(CASO, Types.VARCHAR));
	setFunction(true);
	compile();
}

@SuppressWarnings("unchecked")
public int execute(String codiceFiscaleNuovoBen,
		BigDecimal idProgetto,
		BigDecimal idEnteGiuridicoNew,
		BigDecimal idIndirizzoNew,
		BigDecimal idSedeNew,
		BigDecimal idRecapitiNew,
		String caso) {
	Map<String, Object> map = new HashMap<String, Object>();
	map.put(CODICE_FISCALE_NUOVO_BEN, codiceFiscaleNuovoBen);
	map.put(ID_PROGETTO, idProgetto);
	map.put(ID_ENTEGIURIDICO_NEW, idEnteGiuridicoNew);
	map.put(ID_INDIRIZZO_NEW, idIndirizzoNew);
	map.put(ID_SEDE_NEW, idSedeNew);
	map.put(ID_RECAPITI_NEW, idRecapitiNew);
	map.put(CASO, caso);
	//	map.put(ID_UTENTE, idUtente);

	int  res = -1;
	Map<String, Object> results = execute(map);
	if (!results.isEmpty( )) {
		if(results.get(V_RITORNO)!=null){
			res = ((Integer) results.get(V_RITORNO)) ;
		}
	}

	return res;
}
};




private class StartAttivitaStoredProcedure extends
	LoggingStoredProcedure {

	private static final String V_RITORNO = "v_ritorno";
	private static final String ID_PROGETTO = "idProgetto";
	private static final String DESCR_BREVE_TASK = "descBreveTask";
	private static final String ID_UTENTE = "idUtente";
	

	public StartAttivitaStoredProcedure() {
		super(getDataSource(), Constants.NOME_PACKAGE_PROC_PROCESSO + "."
				+ "StartAttivita");

		declareParameter(new SqlOutParameter(V_RITORNO, Types.NUMERIC));
		declareParameter(new SqlParameter(ID_PROGETTO, Types.NUMERIC));
		declareParameter(new SqlParameter(DESCR_BREVE_TASK, Types.VARCHAR));
		declareParameter(new SqlParameter(ID_UTENTE, Types.NUMERIC));

		setFunction(true);
		compile();
	}

	@SuppressWarnings("unchecked")
	public int execute(BigDecimal idProgetto,String descBreveTask,BigDecimal idUtente) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(ID_PROGETTO, idProgetto);
		map.put(DESCR_BREVE_TASK, descBreveTask);
		map.put(ID_UTENTE, idUtente);

		int res = -1;
		Map<String, Object> results = execute(map);
		if (!results.isEmpty( )) {
			if(results.get(V_RITORNO)!=null){
				res = ((BigDecimal) results.get(V_RITORNO)).intValue();
			}
		}

		return res;
	}
};



private class UnlockAttivitaStoredProcedure extends
LoggingStoredProcedure {

private static final String V_RITORNO = "v_ritorno";
private static final String ID_PROGETTO = "idProgetto";
private static final String DESCR_BREVE_TASK = "descBreveTask";
private static final String ID_UTENTE = "idUtente";


public UnlockAttivitaStoredProcedure() {
	super(getDataSource(), Constants.NOME_PACKAGE_PROC_PROCESSO + "."
			+ "UnlockAttivita");

	declareParameter(new SqlOutParameter(V_RITORNO, Types.NUMERIC));
	declareParameter(new SqlParameter(ID_PROGETTO, Types.NUMERIC));
	declareParameter(new SqlParameter(DESCR_BREVE_TASK, Types.VARCHAR));
	declareParameter(new SqlParameter(ID_UTENTE, Types.NUMERIC));

	setFunction(true);
	compile();
}

@SuppressWarnings("unchecked")
public int execute(BigDecimal idProgetto,String descBreveTask,BigDecimal idUtente) {
	Map<String, Object> map = new HashMap<String, Object>();
	map.put(ID_PROGETTO, idProgetto);
	map.put(DESCR_BREVE_TASK, descBreveTask);
	map.put(ID_UTENTE, idUtente);

	int res = -1;
	Map<String, Object> results = execute(map);
	if (!results.isEmpty( )) {
		if(results.get(V_RITORNO)!=null){
			res = ((BigDecimal) results.get(V_RITORNO)).intValue();
		}
	}

	return res;
}
};



	////////////////////////////////////////////////////////////////////
   // NEOFLUX
	


		private class CaricamentoMassivoDocSpesaStoredProcedure extends
				LoggingStoredProcedure {

			private static final String RESULT = "result";
			private static final String P_ID_CARICA_MASS_DOC_SPESA = "pIdCaricaMassDocSpesa";

			public CaricamentoMassivoDocSpesaStoredProcedure() {
				super(getDataSource(),
						Constants.NOME_PACKAGE_PROC_CAR_MASS_DOC_SPESA + "."
								+ "Main");

				declareParameter(new SqlOutParameter(RESULT, Types.NUMERIC));
				declareParameter(new SqlParameter(P_ID_CARICA_MASS_DOC_SPESA,
						Types.NUMERIC));

				setFunction(true);
				compile();
			}
			
			public int execute(Long idCaricaMassDocSpesa) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put(P_ID_CARICA_MASS_DOC_SPESA, idCaricaMassDocSpesa);
				int res = -1;
				Map<String, Object> results = execute(map);
				if (results.containsKey(RESULT)) {
					res = ((BigDecimal) results.get(RESULT)).intValue();
				}
				return res;
			}
		};

		private class RevertRipartizionePagamentiStoredProcedure extends
				LoggingStoredProcedure {
			private static final String P_ID_UTENTE_INS = "pIdUtenteIns";
			private static final String P_ELENCO_DOC = "pElencoDoc";
			private static final String P_ID_PROGETTO = "pIdProgetto";
			private static final String P_ID_DICHIARAZIONE = "pIdDichiarazioneSpesa";

			public RevertRipartizionePagamentiStoredProcedure() {
				super(getDataSource(), Constants.NOME_PACKAGE_UTILITY_ONLINE + "."
						+ "RevRipartizionePagamenti");
				declareParameter(new SqlParameter(P_ID_PROGETTO, Types.NUMERIC));
				declareParameter(new SqlParameter(P_ELENCO_DOC,
						oracle.jdbc.OracleTypes.ARRAY));
				declareParameter(new SqlParameter(P_ID_DICHIARAZIONE,
						Types.NUMERIC));
				declareParameter(new SqlParameter(P_ID_UTENTE_INS,
						Types.NUMERIC));

				compile();
			}

			public void execute(BigDecimal idProgetto, Long[] documenti,
					BigDecimal idDichiarazione, BigDecimal idUtente)
					throws Exception {
				Map<String, Object> map = new HashMap<String, Object>();
//				ArrayDescriptor arrayDescriptor = null;
				oracle.sql.ARRAY array = null;
//				org.jboss.resource.adapter.jdbc.WrappedConnection	vendorConnection =null;
				
				OracleConnection connection = null;
				OracleConnectionWrapper ocw = null;
				OracleConnection oc = null;
				try {
//					vendorConnection = (org.jboss.resource.adapter.jdbc.WrappedConnection) getDataSource().getConnection();
//					arrayDescriptor = ArrayDescriptor.createDescriptor("T_NUMBER_ARRAY", vendorConnection.getUnderlyingConnection());
//					array = new oracle.sql.ARRAY(arrayDescriptor, vendorConnection.getUnderlyingConnection(), documenti);
//TODO TODO
					
					logger.warn("\n\n TODO POCHETTINO ....... OracleConnectionWrapper funziona??????\n\n");
					//connection = (OracleConnection) DriverManager.getConnection( "url", "username", "password");
					connection = (OracleConnection) getDataSource().getConnection();
					ocw = new OracleConnectionWrapper((OracleConnection) connection);
					oc = ocw.unwrap();
					array = (ARRAY) oc.createArrayOf("T_NUMBER_ARRAY", documenti);
				
				} catch (SQLException e) {
					logger.error("error in getting vendorConnection for Array : "+e.getMessage(),e);
					throw e;
				}finally{
					try{
						logger.info("closing vendorConnection ...");
//						vendorConnection.close();
						oc.close();
						ocw.close();
						connection.close();
					}catch (Exception e) {
						 logger.error("error in closing vendorConnection : "+e.getMessage(),e);
					}
				}
				map.put(P_ID_PROGETTO, idProgetto);

				map.put(P_ELENCO_DOC, array);

				map.put(P_ID_DICHIARAZIONE, idDichiarazione);
				map.put(P_ID_UTENTE_INS, idUtente);

				execute(map);
			}

			
//			public void execute(BigDecimal idProgetto, Long[] documenti,
//					BigDecimal idDichiarazione, BigDecimal idUtente)
//					throws Exception {
//				Map<String, Object> map = new HashMap<String, Object>();
//				ArrayDescriptor arrayDescriptor = null;
//				oracle.sql.ARRAY array = null;
//				//org.jboss.resource.adapter.jdbc.WrappedConnection	vendorConnection =null;
//				DelegatingConnection vendorConnection = null;
//				
//				try {
//					vendorConnection = new DelegatingConnection(getDataSource().getConnection());
//					arrayDescriptor = ArrayDescriptor.createDescriptor("T_NUMBER_ARRAY", vendorConnection.getInnermostDelegate());
//					array = new oracle.sql.ARRAY(arrayDescriptor, vendorConnection.getInnermostDelegate(), documenti);
//
//				} catch (SQLException e) {
//					logger.error("error in getting vendorConnection for Array : "+e.getMessage(),e);
//					throw e;
//				}finally{
//					try{
//						logger.info("closing vendorConnection ...");
//						vendorConnection.close();
//					}catch (Exception e) {
//						 logger.error("error in closing vendorConnection : "+e.getMessage(),e);
//					}
//				}
//				map.put(P_ID_PROGETTO, idProgetto);
//
//				map.put(P_ELENCO_DOC, array);
//
//				map.put(P_ID_DICHIARAZIONE, idDichiarazione);
//				map.put(P_ID_UTENTE_INS, idUtente);
//
//				execute(map);
//			}
		}

		private AggiornaImpegnoStoredProcedure aggiornaImpengo;
		private CaricamentoSchedaProgettoStoredProcedure caricamentoSchedaProgetto;
		private IsFESRStoredProcedure isFESR;
		private CaricaAttoDiLiquidazioneStoredProcedure caricaAttoDiLiquidazione;
		private CaricaAttivitaPregresseStoredProcedure caricaAttivitaPregresse;
		private CaricamentoMassivoDocSpesaStoredProcedure caricamentoMassivoDocSpesa;
		private ControlliCertificazioneStoredProcedure controlliCertificazione;
		private CreaPropostaCertificazioneStoredProcedure creaPropostaCertificazione;
		private CreaIntermediaFinaleStoredProcedure creaIntermediaFinale;
		private AggiornaPropostaIntermediaFinaleStoredProcedure aggiornaPropostaIntermediaFinale;
		private CreaNotifAnticipiStoredProcedure creaNotifAnticipi;
		private CreaNotifAnticipiMailStoredProcedure creaNotifAnticipiMail;
		
		private CreaPropostaCertificazioneRevStoredProcedure creaPropostaCertificazioneRev;
		private CambiaSinonimiCertificazioneRevStoredProcedure cambiaSinonimiCertificazioneRev;
		private RipristinaSinonimiCertificazioneRevStoredProcedure ripristinaSinonimiCertificazioneRev;

		
		// NEOFLUX
		private CancelNotificationMessageStoredProcedure cancelNotificationMessage ;
		private CountProgettiBENStoredProcedure countProgettiBEN;
		private EndAttivitaStoredProcedure endAttivita;
		private GetProcessoStoredProcedure getProcesso;
		private GetProcessoBLStoredProcedure getProcessoBL;
		private IsLockedStoredProcedure isLocked;
		private PutNotificationMetadataStoredProcedure putNotificationMetadata;
		private SendNotificationMessageStoredProcedure sendNotificationMessage;
		private StartAttivitaStoredProcedure startAttivita;
		private UnlockAttivitaStoredProcedure unlockAttivita;
		// NEOFLUX
		
		//CAMPIONAMENTO 
		private AvviaCampionamentoStoredProcedure avviaProceduraCampionamento;
	
		private CambiaBeneficiarioStoredProcedure cambiaBeneficiario;
		private UpdateAnticipiStoredProcedure updateAnticipi;
		
		
		private RevertRipartizionePagamentiStoredProcedure revertRipartizionePagamenti;
		private RipartizionePagamentiStoredProcedure ripartizionePagamenti;
		
		
		public int aggiornaImpegno(BigDecimal idImpegno, boolean flagOnline,
				Long idUtente) throws Exception {
			Long idTracciamento = null;
			long start = 0L;
			try {
				idTracciamento = traceManagerBusiness.tracciaOperazione(
						"ImpegniStoredProcedure.aggiornaImpegno", idUtente);
				start = System.currentTimeMillis();
				if (aggiornaImpengo == null)
					aggiornaImpengo = new AggiornaImpegnoStoredProcedure();

				String flag = "N";
				if (flagOnline)
					flag = "S";
				int result = aggiornaImpengo.execute(idImpegno, flag);
				if (result == 0)
					traceManagerBusiness.tracciaEsitoPositivo(idTracciamento,
							start);
				else {
					String msg = "Stored procedure "
							+ Constants.NOME_PACKAGE_PROC_BILANCIO
							+ "."
							+ "Impegni"
							+ " non e' riuscita ad eseguire l'aggiornamento per idImpengo="
							+ idImpegno
							+ ". Consultare la tabella PBANDI_L_LOG_BATCH.";
					traceManagerBusiness.tracciaEsitoNegativo(idTracciamento,
							start, msg);
				}
				return result;

			} catch (Exception e) {
				logger.error(
						"Errore durante la chiamata della storedProcedure. ", e);
				traceManagerBusiness.tracciaEsitoNegativo(idTracciamento,
						start, e.getMessage());
				throw e;
			} finally {
			}
		}

		
		
		/////////////////////////////////////////////////////////////////////////////////////////
		// NEOFLUX
		
		public int cancelNotificationMessage(BigDecimal idNotifica,BigDecimal idUtente) {
			logger.warn("calling cancelNotificationMessage ...");
			long start=System.currentTimeMillis();
			if (cancelNotificationMessage == null) {
				cancelNotificationMessage = new CancelNotificationMessageStoredProcedure();
			}
			int result= cancelNotificationMessage.execute(idNotifica,idUtente);
			logger.warn("endAttivita executed in ms: "+(System.currentTimeMillis()-start));
			return result;
		};
		
		
		public int endAttivita(BigDecimal idProgetto,String descBreveTask,BigDecimal idUtente) {
			logger.warn("calling endAttivita ...");
			long start=System.currentTimeMillis();
			if (endAttivita == null) {
				endAttivita = new EndAttivitaStoredProcedure();
			}
			int result= endAttivita.execute(idProgetto,descBreveTask,idUtente);
			logger.warn("endAttivita executed in ms: "+(System.currentTimeMillis()-start));
			return result;
		};
		
		
		public int countProgettiBEN(BigDecimal progrBandoLineaIntervento,BigDecimal idSoggettoBen,BigDecimal idUtente) {
			logger.warn("calling countProgettiBEN ...");
			long start=System.currentTimeMillis();
			if (countProgettiBEN == null) {
				countProgettiBEN = new CountProgettiBENStoredProcedure();
			}
			int result= countProgettiBEN.execute(progrBandoLineaIntervento,idSoggettoBen,idUtente);
			logger.warn("countProgettiBEN executed in ms: "+(System.currentTimeMillis()-start));
			return result;
		};
 
		
		public int getProcesso(BigDecimal idProgetto) {
			logger.warn("calling getProcesso ...");
			long start=System.currentTimeMillis();
			if (getProcesso == null) {
				getProcesso = new GetProcessoStoredProcedure();
			}
			int result= getProcesso.execute(idProgetto);
			logger.warn("getProcesso executed in ms: "+(System.currentTimeMillis()-start));
			return result;
		};
		
		public int getProcessoBL(BigDecimal progrBandoLineaIntervento) {
			logger.warn("calling getProcessoBL ...");
			long start=System.currentTimeMillis();
			if (getProcessoBL == null) {
				getProcessoBL = new GetProcessoBLStoredProcedure();
			}
			int result= getProcessoBL.execute(progrBandoLineaIntervento);
			logger.warn("getProcessoBL executed in ms: "+(System.currentTimeMillis()-start));
			return result;
		};
		
		public int aggiornaPropostaIntermediaFinale(BigDecimal idUtente
				) throws Exception{
			if (aggiornaPropostaIntermediaFinale == null) {
				aggiornaPropostaIntermediaFinale = new AggiornaPropostaIntermediaFinaleStoredProcedure();
			}
			return aggiornaPropostaIntermediaFinale.execute(idUtente);
		}
 
	 
		public BigDecimal avviaProceduraCampionamento(String descBreveLinea, Long idUtente) throws Exception{
			if(avviaProceduraCampionamento == null)
				avviaProceduraCampionamento = new AvviaCampionamentoStoredProcedure();
			
			return avviaProceduraCampionamento.execute(descBreveLinea, idUtente);
		}
 

		public TaskDTO[] getAttivitaBL(BigDecimal progrBandoLineaIntervento,
				BigDecimal idUtente, String descBreveTipoAnagrafica,
				BigDecimal start) {
			long startTime = System.currentTimeMillis();
			List<TaskDTO> result = new ArrayList<TaskDTO>();
			final StringBuilder sql = new StringBuilder(
					"select  "
							+ Constants.NOME_PACKAGE_PROC_PROCESSO
							+ "."
							+ "GetAttivitaBL(:progrBandoLineaIntervento,:idUtente,:descBreveTipoAnagrafica,:start)");
			sql.append(" FROM dual");

			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("progrBandoLineaIntervento",progrBandoLineaIntervento);
			params.addValue("idUtente", idUtente);
			params.addValue("descBreveTipoAnagrafica",descBreveTipoAnagrafica);
			params.addValue("start", start);

			PreparedStatementCallback action = new PreparedStatementCallback() {

				public List<TaskDTO> doInPreparedStatement(
						PreparedStatement preparedStatement)
						throws SQLException, DataAccessException {
					List<TaskDTO> tasks = new ArrayList<TaskDTO>();
					ResultSet rs = preparedStatement.executeQuery();
					while (rs.next()) {
						java.sql.Array array = (java.sql.Array) rs.getArray(1);
						ResultSet arrayRs = array.getResultSet();
						while (arrayRs.next()) {
							BigDecimal row = (BigDecimal) arrayRs.getObject(1);
							oracle.sql.STRUCT struct = (oracle.sql.STRUCT) arrayRs
									.getObject(2);
							if (struct != null) {
								Object[] attributes = struct.getAttributes();
								if (attributes != null) {
									TaskDTO task = new TaskDTO();
									task.setIdProgetto(getLong(attributes[0]));
									task.setTitoloProgetto(getString(attributes[1]));
									task.setCodiceVisualizzato(getString(attributes[2]));
									task.setDescrBreveTask(getString(attributes[3]));
									task.setDescrTask(getString(attributes[4]));
									task.setProgrBandoLineaIntervento(getLong(attributes[5]));
									task.setNomeBandoLinea(getString(attributes[6]));
									task.setFlagOpt(getString(attributes[7]));
									task.setFlagLock(getString(attributes[8]));
									task.setAcronimoProgetto(getString(attributes[9]));
									task.setIdBusiness(getLong(attributes[10]));
									task.setIdNotifica(getLong(attributes[11]));
									task.setDenominazioneLock(getString(attributes[12]));
									if(!isEmpty(task.getDescrTask()))
										tasks.add(task);
								}
							}
						}
						arrayRs.close();
					}
					rs.close();

					return tasks;
				}
			};

			result = (List<TaskDTO>) namedJdbcTemplate.execute(sql.toString(),
					params, action);
			logger.warn("method executed in ms: "
					+ (System.currentTimeMillis() - startTime));
			return result.toArray(new TaskDTO[0]);
		}

	public TaskDTO[] getAttivitaBEN(BigDecimal progrBandoLineaIntervento,BigDecimal idSoggettoBen,BigDecimal idUtente,
									String descBreveTipoAnagrafica,
									BigDecimal start,
									BigDecimal idProgetto,
									String descrTask) {
		
		logger.begin();		
		long startTime=System.currentTimeMillis();
		List<TaskDTO> result = new ArrayList<TaskDTO>();
		final StringBuilder sql = new StringBuilder(
				"select  "+Constants.NOME_PACKAGE_PROC_PROCESSO + "."
					+ "GetAttivitaBEN(:progrBandoLineaIntervento,:idSoggettoBen,:idUtente,:descBreveTipoAnagrafica,:start,:idProgetto,:descrTask)");
		sql.append(" FROM dual");

		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("progrBandoLineaIntervento", progrBandoLineaIntervento);
		params.addValue("idSoggettoBen", idSoggettoBen);
		params.addValue("idUtente", idUtente);
		params.addValue("descBreveTipoAnagrafica", descBreveTipoAnagrafica);
		params.addValue("start", start);
		params.addValue("idProgetto", idProgetto);
		params.addValue("descrTask", descrTask);

		PreparedStatementCallback action = new PreparedStatementCallback() {

			public List<TaskDTO> doInPreparedStatement(
					PreparedStatement preparedStatement) throws SQLException,
					DataAccessException {
				List<TaskDTO> tasks = new ArrayList<TaskDTO>();
				ResultSet rs = preparedStatement.executeQuery();
				while (rs.next()) {
					java.sql.Array array = (java.sql.Array) rs.getArray(1);
					ResultSet arrayRs = array.getResultSet();
					while (arrayRs.next()) {
						//BigDecimal row = (BigDecimal) arrayRs.getObject(1);
						oracle.sql.STRUCT struct = (oracle.sql.STRUCT) arrayRs.getObject(2);
						if (struct != null) {
							Object[] attributes = struct.getAttributes();
							if(attributes!=null){
									TaskDTO task = new TaskDTO();
									task.setIdProgetto(getLong(attributes[0]));
									task.setTitoloProgetto(getString(attributes[1]));
									task.setCodiceVisualizzato(getString(attributes[2]));
									task.setDescrBreveTask(getString(attributes[3]));
									task.setDescrTask(getString(attributes[4]));
									task.setProgrBandoLineaIntervento(getLong(attributes[5]));
									task.setNomeBandoLinea(getString(attributes[6]));
									task.setFlagOpt(getString(attributes[7]));
									task.setFlagLock(getString(attributes[8]));
									task.setAcronimoProgetto(getString(attributes[9]));
									task.setIdBusiness(getLong(attributes[10]));
									task.setIdNotifica(getLong(attributes[11]));
									task.setDenominazioneLock(getString(attributes[12]));
									if(!isEmpty(task.getDescrTask()))
										tasks.add(task);
							}
						}
					}
					arrayRs.close();
				}
				rs.close();
				return tasks;
			}
		};
		
		result = (List<TaskDTO>) namedJdbcTemplate.execute(
				sql.toString(), params, action);
		logger.info("method executed in ms: "+(System.currentTimeMillis()-startTime));
		logger.end();	
		return result.toArray(new TaskDTO[0]);
	}
	
	
	
	
		public SezioneAppaltoVO[] getSezioniAppalto(String codiceModello,
				BigDecimal idProgetto, String codTipoDocIndex, BigDecimal idChecklist) {
			logger.info("\n\n\ncodiceModello: "+codiceModello+
					" ,idProgetto:"+idProgetto
					+" , codTipoDocIndex: "+codTipoDocIndex
					+" ,idChecklist: "+idChecklist);
			long startTime = System.currentTimeMillis();
			List<SezioneAppaltoVO> sezioniAppaltoVO = new ArrayList<SezioneAppaltoVO>();
			  StringBuilder sql = new StringBuilder();
			  sql.append("select ")
			  	 .append( Constants.NOME_PACKAGE_UTILITY_ONLINE)
				 .append( ".");
			  if(idChecklist!=null)
				  sql .append( "CaricaCheckList(:codiceModello,:idProgetto,:codTipoDocIndex,:idChecklist)");
			  else
				  sql .append( "CaricaCheckList(:codiceModello,:idProgetto,:codTipoDocIndex)");
			sql.append(" FROM dual");

			MapSqlParameterSource params = new MapSqlParameterSource();
	 
			params.addValue("codiceModello", codiceModello);
		    params.addValue("idProgetto", idProgetto);
		    params.addValue("codTipoDocIndex", codTipoDocIndex);
		    if(idChecklist!=null)
		    	params.addValue("idChecklist", idChecklist);

			PreparedStatementCallback action = new PreparedStatementCallback() {

				public List<SezioneAppaltoVO> doInPreparedStatement(
						PreparedStatement preparedStatement)
						throws SQLException, DataAccessException {
					List<SezioneAppaltoVO> sezioniAppaltoVO = new ArrayList<SezioneAppaltoVO>();
					
					ResultSet rs = preparedStatement.executeQuery();
					while (rs.next()) {
						java.sql.Array array = (java.sql.Array) rs.getArray(1);
						ResultSet arrayRs = array.getResultSet();
						while (arrayRs.next()) {
							
							oracle.sql.STRUCT struct = (oracle.sql.STRUCT) arrayRs
									.getObject(2);
							if (struct != null) {
								Object[] attributes = struct.getAttributes();
								int i=0;
								if (attributes != null) {
 
									SezioneAppaltoVO sezioneAppaltoVO = new SezioneAppaltoVO();
									sezioneAppaltoVO.setIdTipoModello(getString(attributes[i++]));
									sezioneAppaltoVO.setIdAppalto(getString(attributes[i++]));
									sezioneAppaltoVO.setOggettoAppalto(getString(attributes[i++]));
									sezioneAppaltoVO.setProgrOrdinamento(getString(attributes[i++]));
									sezioneAppaltoVO.setCodControllo(getString(attributes[i++]));
									sezioneAppaltoVO.setDescrRiga(getString(attributes[i++]));
									sezioneAppaltoVO.setImportoContratto(getString(attributes[i++]));
									sezioneAppaltoVO.setBilancioPreventivo(getString(attributes[i++]));
									sezioneAppaltoVO.setDtFirmaContratto(getString(attributes[i++]));
									sezioneAppaltoVO.setDtInizioPrevista(getString(attributes[i++]));
									sezioneAppaltoVO.setDtConsegnaLavori(getString(attributes[i++]));
									sezioneAppaltoVO.setDtGuue(getString(attributes[i++]));
									sezioneAppaltoVO.setDtGuri(getString(attributes[i++]));
									sezioneAppaltoVO.setDtQuotNazionali(getString(attributes[i++]));
									sezioneAppaltoVO.setDtWebStazAppaltante(getString(attributes[i++]));
									sezioneAppaltoVO.setDtWebOsservatorio(getString(attributes[i++]));
									sezioneAppaltoVO.setDescTipologiaAggiudicazione(getString(attributes[i++]));
									sezioneAppaltoVO.setFlagEsito(getString(attributes[i++]));
									sezioneAppaltoVO.setNote(getString(attributes[i++]));									
									sezioneAppaltoVO.setSpiegazioni(getString(attributes[i++]));	 
									sezioneAppaltoVO.setNorma(getString(attributes[i++]));		
									sezioneAppaltoVO.setNormativariferimento(getString(attributes[i++]));
									sezioneAppaltoVO.setCriteriselezione(getString(attributes[i++]));
									sezioneAppaltoVO.setSupplementarititolo(getString(attributes[i++]));
									sezioneAppaltoVO.setSupplementaridatafirma( getString(attributes[i++]));
									sezioneAppaltoVO.setSupplementariammontare( getString(attributes[i++]));
									sezioneAppaltoVO.setSupplementaridataconsegna(getString(attributes[i++]));
									sezioneAppaltoVO.setSupplementaridataeffettiva(getString(attributes[i++]));
									sezioneAppaltoVO.setSupplementarigiustificazione(getString(attributes[i++]));
									sezioneAppaltoVO.setDatanoncontrattuali(getString(attributes[i++]));
									sezioneAppaltoVO.setImportononcontrattuali(getString(attributes[i++]));
									sezioneAppaltoVO.setDatanoneseguiti(getString(attributes[i++]));
									sezioneAppaltoVO.setImportononeseguiti(getString(attributes[i++]));
									sezioniAppaltoVO.add(sezioneAppaltoVO);
								}
							}
						}
						arrayRs.close();
					}
					rs.close();
					return sezioniAppaltoVO;
				}
			};

			sezioniAppaltoVO = (List<SezioneAppaltoVO>) namedJdbcTemplate.execute(sql.toString(),
					params, action);
			logger.warn("method executed in ms: "
					+ (System.currentTimeMillis() - startTime));
			return sezioniAppaltoVO.toArray(new SezioneAppaltoVO[0]);
		}
	
	
		public NotificaDTO  getNotificationMessage(BigDecimal idNotifica) {
			long startTime = System.currentTimeMillis();
			NotificaDTO  result = null;
			final StringBuilder sql = new StringBuilder(
					"select  "
							+ Constants.NOME_PACKAGE_PROC_PROCESSO
							+ "."
							+ "GetNotificationMessage(:idNotifica)");
			sql.append(" FROM dual");

			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("idNotifica",
					idNotifica);

		/* WEBLOGIC
		 * 	PreparedStatementCallback action = new PreparedStatementCallback() {

				public  NotificaDTO  doInPreparedStatement(
						PreparedStatement preparedStatement)
						throws SQLException, DataAccessException {
					ResultSet rs = preparedStatement.executeQuery();
					NotificaDTO  notificaDTO = new   NotificaDTO ();
					while (rs.next()) {
						//oracle.sql.STRUCT struct = (oracle.sql.STRUCT) rs.getObject(1);
						weblogic.jdbc.wrapper.Struct wlstruct= (weblogic.jdbc.wrapper.Struct) rs.getObject(1);
						oracle.sql.STRUCT struct =(oracle.sql.STRUCT) wlstruct.getVendorObj();
						if (struct != null) {
							Object[] attributes = struct.getAttributes();
							if (attributes != null) {
								notificaDTO.setIdProgetto(getLong(attributes[0]));
								notificaDTO.setTitoloProgetto(getString(attributes[1]));
								notificaDTO.setCodiceVisualizzato(getString(attributes[2]));
								notificaDTO.setIdNotifica(getLong(attributes[3]));
								notificaDTO.setStatoNotifica(getString(attributes[4]));
								notificaDTO.setDataNotifica(getTimestamp(attributes[5]));
								notificaDTO.setSubjectNotifica(getString(attributes[6]));
								notificaDTO.setMessageNotifica(getString(attributes[7]));
							}
						}
					 
					}
					rs.close();
					return notificaDTO;
				}
			};*/

			PreparedStatementCallback action = new PreparedStatementCallback() {

				public  NotificaDTO  doInPreparedStatement(
						PreparedStatement preparedStatement)
						throws SQLException, DataAccessException {
					ResultSet rs = preparedStatement.executeQuery();
					NotificaDTO  notificaDTO = new   NotificaDTO ();
					while (rs.next()) {
						oracle.sql.STRUCT struct = (oracle.sql.STRUCT) rs.getObject(1);
						if (struct != null) {
							Object[] attributes = struct.getAttributes();
							if (attributes != null) {
								notificaDTO.setIdProgetto(getLong(attributes[0]));
								notificaDTO.setTitoloProgetto(getString(attributes[1]));
								notificaDTO.setCodiceVisualizzato(getString(attributes[2]));
								notificaDTO.setIdNotifica(getLong(attributes[3]));
								notificaDTO.setStatoNotifica(getString(attributes[4]));
								notificaDTO.setDataNotifica(getTimestamp(attributes[5]));
								notificaDTO.setSubjectNotifica(getString(attributes[6]));
								try {
									notificaDTO.setMessageNotifica(getStringFromClob(attributes[7]));
								} catch (Exception e) {
									logger.error("Errore durante il reperimento del messaggio notifica.", e);
								}
							}
						}
					 
					}
					rs.close();
					return notificaDTO;
				}
			};
			
			
			result = (NotificaDTO) namedJdbcTemplate.execute(sql.toString(),
					params, action);
			logger.warn("method executed in ms: "
					+ (System.currentTimeMillis() - startTime));
			return result ;
		}
	
	public ProgettoDTO[] getProgetti(BigDecimal progrBandoLineaIntervento,
			BigDecimal idSoggettoBen, BigDecimal idUtente) {
		long startTime = System.currentTimeMillis();
		List<ProgettoDTO> result = new ArrayList<ProgettoDTO>();
		final StringBuilder sql = new StringBuilder(
				"select  "
						+ Constants.NOME_PACKAGE_PROC_PROCESSO
						+ "."
						+ "GetProgetti(:progrBandoLineaIntervento,:idSoggettoBen,:idUtente)");
		sql.append(" FROM dual");

		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("progrBandoLineaIntervento",progrBandoLineaIntervento);
		params.addValue("idSoggettoBen", idSoggettoBen);
		params.addValue("idUtente", idUtente);
 
		PreparedStatementCallback action = new PreparedStatementCallback() {

			public List<ProgettoDTO> doInPreparedStatement(
					PreparedStatement preparedStatement)
					throws SQLException, DataAccessException {
				List<ProgettoDTO> progetti = new ArrayList<ProgettoDTO>();
				ResultSet rs = preparedStatement.executeQuery();
				while (rs.next()) {
					java.sql.Array array = (java.sql.Array) rs.getArray(1);
					ResultSet arrayRs = array.getResultSet();
					while (arrayRs.next()) {
						BigDecimal row = (BigDecimal) arrayRs.getObject(1);
						oracle.sql.STRUCT struct = (oracle.sql.STRUCT) arrayRs.getObject(2);
						if (struct != null) {
							Object[] attributes = struct.getAttributes();
							if (attributes != null) {
									ProgettoDTO progetto = new ProgettoDTO();
									progetto.setId(getLong(attributes[0]));
									progetto.setTitolo(getString(attributes[1]));
									progetto.setCodiceVisualizzato(getString(attributes[2]));
									progetto.setAcronimo(getString(attributes[3]));
									progetti.add(progetto);
							}
						}
					}
					arrayRs.close();
				}
				rs.close();

				return progetti;
			}
		};

		result = (List<ProgettoDTO>) namedJdbcTemplate.execute(sql.toString(),
				params, action);
		logger.warn("method executed in ms: "
				+ (System.currentTimeMillis() - startTime));
		return result.toArray(new ProgettoDTO[0]);
	}

	public String isLocked(BigDecimal idProgetto,String descBreveTask,BigDecimal idUtente) {
		logger.warn("calling isLocked ...");
		long start=System.currentTimeMillis();
		if (isLocked == null) {
			isLocked = new IsLockedStoredProcedure();
		}
		String result= isLocked.execute(idProgetto,descBreveTask,idUtente);
		logger.warn("isLocked executed in ms: "+(System.currentTimeMillis()-start));
		return result;
	};
	
	public int startAttivita(BigDecimal idProgetto,String descBreveTask,BigDecimal idUtente) {
		logger.warn("calling startAttivita ...");
		long start=System.currentTimeMillis();
		if (startAttivita == null) {
			startAttivita = new StartAttivitaStoredProcedure();
		}
		int result= startAttivita.execute(idProgetto,descBreveTask,idUtente);
		logger.warn("startAttivita executed in ms: "+(System.currentTimeMillis()-start));
		return result;
	};
	
	public int unlockAttivita(BigDecimal idProgetto,String descBreveTask,BigDecimal idUtente) {
		logger.warn("calling unlockAttivita ...");
		long start=System.currentTimeMillis();
		if (unlockAttivita == null) {
			unlockAttivita = new UnlockAttivitaStoredProcedure();
		}
		int result= unlockAttivita.execute(idProgetto,descBreveTask,idUtente);
		logger.warn("unlockAttivita executed in ms: "+(System.currentTimeMillis()-start));
		return result;
	};
	

	
	protected Long getLong(Object object) {
		if(object !=null && object instanceof BigDecimal)
			return ((BigDecimal)object).longValue();
		return null;
	}
	private String getString(Object object) {
		if(object !=null && object instanceof String)
			return (String)object ;
		return null;
	}
	private Date getDate(Object object) {
		if(object !=null && object instanceof Date )
			return (Date)object ;
		return null;
	}
	private Timestamp getTimestamp(Object object) {
		if(object !=null &&  object instanceof Timestamp)
			return (Timestamp)object ;
		return null;
	}
	
	private String getStringFromClob(Object object) throws Exception {
		if(object != null && object instanceof Clob){
			Clob clobObject = (Clob) object;
			InputStream in = clobObject.getAsciiStream();
			StringWriter w = new StringWriter();
			IOUtils.copy(in, w);
			String clobAsString = w.toString();
			return clobAsString;
		}
		return null;
	}
	
	
	
	public void putNotificationMetadata(String nomeMetadata,String valoreMetaData) {
		logger.info("calling PutNotificationMetadataStoredProcedure ...");
		if (putNotificationMetadata == null) {
			putNotificationMetadata = new PutNotificationMetadataStoredProcedure();
		}
		putNotificationMetadata.execute(nomeMetadata, valoreMetaData );
	};

	public void putNotificationMetadata(List <MetaDataVO> metadata ) {
		logger.info("calling PutNotificationMetadataStoredProcedure ...");
	
		if (putNotificationMetadata == null) {
			putNotificationMetadata = new PutNotificationMetadataStoredProcedure();
		}
		for (MetaDataVO metaDataVO : metadata) {
			logger.info("metaDataVO.getNome(): "+metaDataVO.getNome()+" --- metaDataVO.getValore(): "+metaDataVO.getValore());
			putNotificationMetadata.execute(metaDataVO.getNome(), metaDataVO.getValore() );
		}
	};
	/*			p_id_progetto                   IN  PBANDI_T_PROGETTO.ID_PROGETTO%TYPE,
                p_descr_breve_template_notif    IN  PBANDI_D_TEMPLATE_NOTIFICA.DESCR_BREVE_TEMPLATE_NOTIFICA%TYPE,
                p_codice_ruolo                  IN  PBANDI_C_RUOLO_DI_PROCESSO.CODICE%TYPE,
                p_id_utente						IN  PBANDI_T_UTENTE.ID_UTENTE%TYPE,                
                p_id_target                     IN  PBANDI_T_NOTIFICA_PROCESSO.ID_TARGET%TYPE
    */
	
	// Versione originale, chiamata in pi� punti da Bandi, che non salva l'idTarget.
	public void sendNotificationMessage(BigDecimal idProgetto,String descBreveTemplateNotifica,String ruoloProcesso,Long idUtente) {
		logger.info("calling sendNotificationMessage ...");
		if (sendNotificationMessage == null) {
			sendNotificationMessage = new SendNotificationMessageStoredProcedure();
		}
			Long idTarget = null;
			sendNotificationMessage.execute(idProgetto , descBreveTemplateNotifica, ruoloProcesso, idUtente, idTarget);
	};

	// Jira PBANDI-2773: nuova versione che invia una notifica, salvando l'idTarget in PBANDI_T_NOTIFICA_PROCESSO.
	public void sendNotificationMessage2(BigDecimal idProgetto,String descBreveTemplateNotifica,String ruoloProcesso,Long idUtente, Long idTarget) {
		logger.info("calling sendNotificationMessage ...");
		if (sendNotificationMessage == null) {
			sendNotificationMessage = new SendNotificationMessageStoredProcedure();
		}
			sendNotificationMessage.execute(idProgetto , descBreveTemplateNotifica, ruoloProcesso, idUtente, idTarget);
	};
		// NEOFLUX
		///////////////////////////////////////////////////////////////////////////////////////
		
	
			 
	public boolean cambiaBeneficiario(String codiceFiscaleNuovoBen,
			BigDecimal idProgetto,
			BigDecimal idEnteGiuridicoNew,
			BigDecimal idIndirizzoNew,
			BigDecimal idSedeNew,
			BigDecimal idRecapitiNew,
			String caso) {
		logger.warn("\n\ncalling cambiaBeneficiario ...");
		long start=System.currentTimeMillis();
		if (cambiaBeneficiario == null) {
			cambiaBeneficiario = new CambiaBeneficiarioStoredProcedure();
		}
		int result= cambiaBeneficiario.execute(codiceFiscaleNuovoBen,idProgetto,
				idEnteGiuridicoNew,idIndirizzoNew,
				idSedeNew,idRecapitiNew,caso);
		logger.warn("cambiaBeneficiario witch exit code "+result+" executed in ms: "+(System.currentTimeMillis()-start));
		if(result==0)
			return true;
		else return false;
	};
	
	
	public void aggiornaAnticipi(BigDecimal idPropostaCertificazione, BigDecimal idProgetto){
		logger.info("\nCalling aggiornaAnticipi procedure...");
		if(updateAnticipi == null){
			updateAnticipi = new UpdateAnticipiStoredProcedure();
		}
		updateAnticipi.execute(idPropostaCertificazione, idProgetto);
		
	}	
		
		
		public void ripartizionePagamenti(BigDecimal idProgetto,
				String listaId, Long idDichiarazione, BigDecimal idUtente) {
			logger.info("calling RipartizionePagamentiStoredProcedure ...");
			if (ripartizionePagamenti == null) {
				ripartizionePagamenti = new RipartizionePagamentiStoredProcedure();
			}
			ripartizionePagamenti.execute(idProgetto, listaId, idDichiarazione,
					idUtente);
		};

		
		
		
		
		public void revertRipartizionePagamenti(BigDecimal idProgetto,
				List<Long> documenti, BigDecimal idDichiarazione,
				BigDecimal idUtente) throws Exception {
			if (revertRipartizionePagamenti == null)
				revertRipartizionePagamenti = new RevertRipartizionePagamentiStoredProcedure();
			revertRipartizionePagamenti
					.execute(idProgetto, documenti.toArray(new Long[] {}),
							idDichiarazione, idUtente);
		}

		public boolean caricamentoSchedaProgetto(BigDecimal idCspProgetto) {
				if (caricamentoSchedaProgetto == null) {
					caricamentoSchedaProgetto = new CaricamentoSchedaProgettoStoredProcedure();
				}
				return caricamentoSchedaProgetto.execute(idCspProgetto) == 0;
		};
		
		public String isFESR(BigDecimal idProgetto) {
			if (isFESR == null) {
				isFESR = new IsFESRStoredProcedure();
			}
			return isFESR.execute(idProgetto);
	};

		public boolean creaPropostaCertificazione(
				BigDecimal idPropostaCertificazione) {
				if (creaPropostaCertificazione == null) {
					creaPropostaCertificazione = new CreaPropostaCertificazioneStoredProcedure();
				}
				return creaPropostaCertificazione
						.execute(idPropostaCertificazione) == 0;
		}
		
		public boolean creaPropostaCertificazioneRev(
				BigDecimal idPropostaCertificazione) {
				if (creaPropostaCertificazioneRev == null) {
					creaPropostaCertificazioneRev = new CreaPropostaCertificazioneRevStoredProcedure();
				}
				return creaPropostaCertificazioneRev
						.execute(idPropostaCertificazione) == 0;
		}
		
		public boolean cambiaSinonimiCertificazioneRev() {
				if (cambiaSinonimiCertificazioneRev == null) {
					cambiaSinonimiCertificazioneRev = new CambiaSinonimiCertificazioneRevStoredProcedure();
				}
				return cambiaSinonimiCertificazioneRev.execute() == 0;
		}
		
		public boolean ripristinaSinonimiCertificazioneRev() {
			if (ripristinaSinonimiCertificazioneRev == null) {
				ripristinaSinonimiCertificazioneRev = new RipristinaSinonimiCertificazioneRevStoredProcedure();
			}
			return ripristinaSinonimiCertificazioneRev.execute() == 0;
		}

		public boolean creaAnteprimaPropostaCertificazione(
				BigDecimal idPropostaCertificazione) {
				if (controlliCertificazione == null) {
					controlliCertificazione = new ControlliCertificazioneStoredProcedure();
				}
				return controlliCertificazione
						.execute(idPropostaCertificazione) == 0;
		};

		public boolean caricaAttoDiLiquidazione(
				BigDecimal idAttiLiquidazioneDt, boolean flagOnline) {
				if (caricaAttoDiLiquidazione == null) {
					caricaAttoDiLiquidazione = new CaricaAttoDiLiquidazioneStoredProcedure();
				}

				String flag = "N";
				if (flagOnline)
					flag = "S";

				return caricaAttoDiLiquidazione.execute(idAttiLiquidazioneDt,
						flag) == 0;
		};

		public void caricamentoMassivoDocSpesa(Long idCaricaMassDocSpesa) {
				long start = System.currentTimeMillis();
				if (caricamentoMassivoDocSpesa == null) {
					caricamentoMassivoDocSpesa = new CaricamentoMassivoDocSpesaStoredProcedure();
				}
				int result = caricamentoMassivoDocSpesa
						.execute(idCaricaMassDocSpesa);

				if (result == 0)
					logger.info("\n\n\n"
							+ Constants.NOME_PACKAGE_PROC_CAR_MASS_DOC_SPESA
							+ ".Main eseguito correttamente in "
							+ (System.currentTimeMillis() - start));
				else {
					String msg = "Stored procedure "
							+ Constants.NOME_PACKAGE_PROC_CAR_MASS_DOC_SPESA
							+ ".Main eseguita in "
							+ (System.currentTimeMillis() - start)
							+ " non ha avuto esito positivo. Consultare la tabella PBANDI_L_LOG_BATCH.";
					logger.error(msg);
				}

		}
		
		public int creaNotifAnticipi(
				BigDecimal idPropostaCertificazione) {
				if (creaNotifAnticipi == null) {
					creaNotifAnticipi = new CreaNotifAnticipiStoredProcedure();
				}
				return creaNotifAnticipi.execute(idPropostaCertificazione);
		}
		
		public int creaNotifAnticipiMail() {
				if (creaNotifAnticipiMail == null) {
					creaNotifAnticipiMail = new CreaNotifAnticipiMailStoredProcedure();
				}
				return creaNotifAnticipiMail.execute();
		}
		
		public int creaFotografiaIntermediaFinaleCertificazione(BigDecimal idUtente){
				if (creaIntermediaFinale == null) {
					creaIntermediaFinale = new CreaIntermediaFinaleStoredProcedure();
				}
				return creaIntermediaFinale.execute(idUtente);
		}
		
		public int aggiornaPropostaIntermediaFinaleCertificazione(BigDecimal idUtente){
			if (creaIntermediaFinale == null) {
				creaIntermediaFinale = new CreaIntermediaFinaleStoredProcedure();
			}
			return creaIntermediaFinale.execute(idUtente);	
		}

		public void setCaricamentoMassivoDocSpesa(
				CaricamentoMassivoDocSpesaStoredProcedure caricamentoMassivoDocSpesa) {
			this.caricamentoMassivoDocSpesa = caricamentoMassivoDocSpesa;
		}

		public CaricamentoMassivoDocSpesaStoredProcedure getCaricamentoMassivoDocSpesa() {
			return caricamentoMassivoDocSpesa;
		};

		
		
		
		public int caricaAttivitaPregresse(BigDecimal idProgetto) {
				if (caricaAttivitaPregresse == null) {
					caricaAttivitaPregresse = new CaricaAttivitaPregresseStoredProcedure();
				}
				return caricaAttivitaPregresse.execute(idProgetto);
		}

		public void setCaricaAttivitaPregresse(
				CaricaAttivitaPregresseStoredProcedure caricaAttivitaPregresse) {
			this.caricaAttivitaPregresse = caricaAttivitaPregresse;
		}

		public CaricaAttivitaPregresseStoredProcedure getCaricaAttivitaPregresse() {
			return caricaAttivitaPregresse;
		}

		public RevertRipartizionePagamentiStoredProcedure getRevertRipartizionePagamenti() {
			return revertRipartizionePagamenti;
		}

		public void setRevertRipartizionePagamenti(
				RevertRipartizionePagamentiStoredProcedure revertRipartizionePagamenti) {
			this.revertRipartizionePagamenti = revertRipartizionePagamenti;
		};

	}

	private ProcedureSQL procedureSQL = new ProcedureSQL();

	public final ProcedureSQL callProcedure() {
		return procedureSQL;
	}

	public static boolean isNull(Object o) {

		return ObjectUtil.isNull(o);
	}

	public static boolean isEmpty(String o) {

		return ObjectUtil.isEmpty(o);
	}

	public boolean isEmpty(Map map) {

		return ObjectUtil.isEmpty(map);
	}

	public boolean isEmpty(Collection o) {
		return ObjectUtil.isEmpty(o);
	}

	public String upper(String var) {
		return StringUtil.toUpperCase(var);
	}

	public boolean isTrue(Boolean bool) {
		return ObjectUtil.isTrue(bool);
	}

	public static String substituteMarkers(String sql,
			Map<String, String> substitutionMap) {
		String result = sql;

		if (substitutionMap != null) {
			for (String marker : substitutionMap.keySet()) {
				String newMarker = substitutionMap.get(marker);
				String param = asParameter(marker);
				 if(newMarker.indexOf("$")>0)
					 newMarker=newMarker.replaceAll("[\\$]", "X_X_X");
				 result = result.replaceAll(param + "\\b", newMarker);
				 if(result.indexOf("X_X_X")>0)
					 result=result.replaceAll("X_X_X","\\$");
			}
		}

		return result;
	}

	public static String asParameter(String string) {
		return ":" + string;
	}

	/**
	 * 
	 */
	public void updateBlob(final long idTemplate, final byte[] blobBytes)
			throws Exception {
		getJdbcTemplate()
				.execute(
						"UPDATE PBANDI_T_TEMPLATE set jasperblob = ? where id_template=? ",
						new AbstractLobCreatingPreparedStatementCallback(
								lobHandler) {

							@Override
							protected void setValues(PreparedStatement ps,
									LobCreator lobCreator) throws SQLException,
									DataAccessException {
								lobCreator.setBlobAsBytes(ps, 1, blobBytes);
								ps.setLong(2, idTemplate);
							}

						}

				);
	}

	public static <T> List<List<T>> a(List<T> list) {
		List<List<T>> result = new ArrayList<List<T>>();
		int inConditionLimit = 6;
		if (list.size() > inConditionLimit) {
			int n = list.size() / inConditionLimit;
			int mod = list.size() % inConditionLimit;
			for (int i = 1; i <= n; ++i) {
				List<T> sublist = list.subList((i - 1) * inConditionLimit, i
						* inConditionLimit);
				result.add(sublist);
			}
			if (mod > 0) {
				List<T> sublist = list.subList(n * inConditionLimit,
						list.size());
				result.add(sublist);
			}
		} else {
			result.add(list);
		}

		return result;
	}

	// OracleLobHandler, the Oracle-specific implementation of Spring's
	// LobHandler interface,
	// requires a NativeJdbcExtractor for obtaining the native OracleConnection:
	// it is an interface
	// for extracting native JDBC objects from wrapped objects coming from
	// connection pools.

	private LobHandler lobHandler; // di tipo OracleLobHandler
	private JdbcTemplate jdbcTemplate;

	public void setLobHandler(LobHandler lobHandler) {
		this.lobHandler = lobHandler;
	}

	public LobHandler getLobHandler() {
		return lobHandler;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	/*
	 * public void insertXMLType( final String clob,final long idUtente,final
	 * String validato, final String nomeFile) throws Exception {
	 * 
	 * long start=System.currentTimeMillis();
	 * 
	 * getJdbcTemplate() .execute(
	 * "INSERT INTO PBANDI_T_CARICA_MASS_DOC_SPESA (FLAG_CARICATO,FILE_XML, "+
	 * "ID_UTENTE ,"+ "FLAG_VALIDATO,"+ "DT_INSERIMENTO,"+ "NOME_FILE,"+
	 * "ID_CARICA_MASS_DOC_SPESA) values('S',XMLType(?),?,?,sysdate,?,SEQ_PBANDI_T_CARICA_MASS_DOC_S.NEXTVAL)"
	 * , new AbstractLobCreatingPreparedStatementCallback( lobHandler) {
	 * 
	 * @Override protected void setValues(PreparedStatement ps, LobCreator
	 * lobCreator) throws SQLException, DataAccessException { int c=0;
	 * lobCreator.setClobAsString(ps, ++c, clob); ps.setLong(++c, idUtente);
	 * ps.setString(++c, validato); ps.setString(++c, nomeFile); }
	 * 
	 * }
	 * 
	 * );
	 * logger.info("\n\n\nInsert xmltype in "+(System.currentTimeMillis()-start)
	 * +" ms\n\n\n"); }
	 */

	public void updateXMLType(final String clob, final long id,
			String nomeTabella, String nomeCampo, String nomeChiave)
			throws Exception {
		long start = System.currentTimeMillis();
		getJdbcTemplate().execute(
				"UPDATE " + nomeTabella + " set " + nomeCampo
						+ " =  XMLType(?) where " + nomeChiave + "=? ",
				new AbstractLobCreatingPreparedStatementCallback(lobHandler) {

					@Override
					protected void setValues(PreparedStatement ps,
							LobCreator lobCreator) throws SQLException,
							DataAccessException {
						lobCreator.setClobAsString(ps, 1, clob);
						ps.setLong(2, id);
					}

				}

		);
		logger.info("\n\n update  xmltype in "
				+ (System.currentTimeMillis() - start) + " ms\n\n");
	}

	/**
	 * 
	 * @param sql
	 * @param params
	 * @param rowMapper
	 * @param rowsLimit
	 *            limita i record trovati sul db da rimappare.
	 * @return
	 */
	@SuppressWarnings("unchecked")
	protected <T> List<T> queryWithRowsLimit(String sql,
			MapSqlParameterSource params, RowMapper<T> rowMapper,
			int rowsLimit) {

		List<T> elementList = null;

		String logQuery = logQuery(sql, params);

		it.csi.util.performance.StopWatch watcher = new it.csi.util.performance.StopWatch(
				Constants.STOPWATCH_LOGGER);
		try {
			watcher.start();
			int maxRows = ((JdbcTemplate) namedJdbcTemplateRowLimit
					.getJdbcOperations()).getMaxRows();
			((JdbcTemplate) namedJdbcTemplateRowLimit.getJdbcOperations())
					.setMaxRows(rowsLimit);
			elementList = namedJdbcTemplateRowLimit.query(sql, params,
					rowMapper);
			((JdbcTemplate) namedJdbcTemplateRowLimit.getJdbcOperations())
					.setMaxRows(maxRows);
			return elementList;
		} catch (Exception e) {
			if (e instanceof EmptyResultDataAccessException) {
				getLogger().warn("Dato non trovato sul db");
			} else {
				getLogger().fatal(e.getMessage(), e);
				throw new RuntimeException(e);
			}

		} finally {
			dumpQuery(logQuery, watcher);

			getLogger().info(
					"record found : "
							+ (elementList != null ? elementList.size() : 0)+"\n");

		}
		return null;

	}

	public <T extends it.csi.pbandi.pbweberog.pbandisrv.integration.db.vo.GenericVO> void updateClob(
			T vo, String clobProperty, final Object clob) {
		long start = System.currentTimeMillis();
		if (vo.isPKValid()) {
			String nomeTabella = vo.getTableNameForValueObject();
			List pks = vo.getPK();
			Map<String, String> keys = new HashMap<String, String>();
			try {
				for (Object pk : pks) {
					String dbFieldName = BeanMapper
							.getDBFieldNameByPropertyName(pk.toString());
					String value;
					value = BeanMapper
							.getBeanValueByDBFieldNameByIntrospection(vo,
									dbFieldName).toString();
					keys.put(dbFieldName, value);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			String condition = "(";
			for (String pk : keys.keySet()) {
				condition = condition.concat(pk + " = '" + keys.get(pk)
						+ "' AND");
			}
			condition = condition.substring(0, condition.length() - 4).concat(
					")");
			getJdbcTemplate()
					.execute(
							"UPDATE "
									+ nomeTabella
									+ " set "
									+ BeanMapper.getDBFieldNameByPropertyName(clobProperty)
									+ " = (?) where " + condition,
							new AbstractLobCreatingPreparedStatementCallback(
									lobHandler) {

								@Override
								protected void setValues(PreparedStatement ps,
										LobCreator lobCreator)
										throws SQLException,
										DataAccessException {
									lobCreator.setClobAsString(ps, 1,
											clob.toString());
								}

							}

					);
		} else {
			logger.error("VO non correttamente valorizzato");
		}
	//	logger.info("\n\n\n update CLOB in "
			//	+ (System.currentTimeMillis() - start) + " ms\n\n");
	}

	public BeanUtil getBeanUtil() {
		return beanUtil;
	}

	public void setBeanUtil(BeanUtil beanUtil) {
		this.beanUtil = beanUtil;
	}
	
 
}
