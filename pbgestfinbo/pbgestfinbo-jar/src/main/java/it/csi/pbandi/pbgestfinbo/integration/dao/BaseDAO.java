/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

//package it.csi.pbandi.pbgestfinbo.integration.dao;
//
//import java.math.BigDecimal;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Vector;
//
//import org.apache.log4j.Logger;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.ApplicationContext;
//import org.springframework.dao.DataAccessException;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.jdbc.core.ResultSetExtractor;
//import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
//import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
//import org.springframework.jdbc.core.namedparam.SqlParameterSource;
//
//import it.csi.pbandi.pbgestfinbo.exception.InternalUnexpectedException;
//import it.csi.pbandi.pbgestfinbo.integration.dto.internal.DecodificaDTO;
//import it.csi.pbandi.pbgestfinbo.integration.dto.internal.LogParameter;
//import it.csi.pbandi.pbgestfinbo.integration.dto.internal.LogVariable;
//import it.csi.pbandi.pbgestfinbo.util.Constants;
//import it.csi.pbandi.pbgestfinbo.util.DumpUtils;
//
//public class BaseDAO
//{
//  private static final String          THIS_CLASS = BaseDAO.class.getSimpleName();
//  protected static final Logger        logger     = Logger.getLogger(Constants.COMPONENT_NAME);
//  protected final int                  PASSO      = 900;
//  public static final int DEFAULT_QUERY_TIMEOUT = 290;
//
//  @Autowired
//  protected NamedParameterJdbcTemplate namedParameterJdbcTemplate;
//  @Autowired
//  protected ApplicationContext         appContext;
//
//  public BaseDAO()
//  {
//  }
//  
//  public void initDao()
//  {
//    setQueryTimeout(Constants.SQL.DEFAULT_QUERY_TIMEOUT);
//  }
//  
//  public void setQueryTimeout(int queryTimeOut)
//  {
//   ((JdbcTemplate) namedParameterJdbcTemplate.getJdbcOperations()).setQueryTimeout(queryTimeOut);
//  }
//
//  public <T> T queryForObject(String query, SqlParameterSource parameters, Class<T> objClass, ResultSetExtractor<T> re)
//  {
//    return namedParameterJdbcTemplate.query(query, parameters, re);
//  }
//
//  public <T> T queryForObject(String query, SqlParameterSource parameters, Class<T> objClass)
//  {
//    ResultSetExtractor<T> re = new GenericObjectExtractor<T>(objClass);
//    return namedParameterJdbcTemplate.query(query, parameters, re);
//  }
//
//  public String queryForString(String query, SqlParameterSource parameters, final String field)
//  {
//    return namedParameterJdbcTemplate.query(query, parameters, new ResultSetExtractor<String>()
//    {
//      @Override
//      public String extractData(ResultSet rs) throws SQLException, DataAccessException
//      {
//        String sql = "";
//        while (rs.next())
//        {
//          sql = rs.getString(field);
//        }
//        return sql;
//      }
//    });
//  }
//
//  /**
//   * Assicurarsi che i nomi dei campi del DTO siano UGUALI ai campi (o alias) richiesti nella query ma in camel case
//   * senza spazi e punteggiatura, <b>rispettando anche i tipi.</b> (es: "TIPO_RICHIESTA" sul db => "tipoRichiesta" sul
//   * dto)
//   */
//  public <T> List<T> queryForList(String query, SqlParameterSource parameters, Class<T> objClass)
//  {
//    ResultSetExtractor<List<T>> re = new GenericListEstractor<T>(objClass);
//    return namedParameterJdbcTemplate.query(query, parameters, re);
//  }
//
//  public int update(String query, MapSqlParameterSource parameters) throws InternalUnexpectedException
//  {
//    String THIS_METHOD = "update";
//    if (logger.isDebugEnabled())
//    {
//      logger.debug("[" + THIS_CLASS + ":: " + THIS_METHOD + "] BEGIN.");
//    }
//
//    try
//    {
//      return namedParameterJdbcTemplate.update(query, parameters);
//    }
//    catch (Throwable t)
//    {
//      InternalUnexpectedException e = new InternalUnexpectedException(t, query, parameters);
//      logInternalUnexpectedException(e, "[" + THIS_CLASS + ":: " + THIS_METHOD + "]");
//      throw e;
//    }
//    finally
//    {
//      if (logger.isDebugEnabled())
//      {
//        logger.debug("[" + THIS_CLASS + ":: " + THIS_METHOD + "] END.");
//      }
//    }
//
//  }
//
//  public String getInCondition(String campo, List<?> vId)
//  {
//    int cicli = vId.size() / PASSO;
//    if (vId.size() % PASSO != 0)
//      cicli++;
//
//    StringBuffer condition = new StringBuffer(" AND ( ");
//    for (int j = 0; j < cicli; j++)
//    {
//      if (j != 0)
//      {
//        condition.append(" OR ");
//      }
//      boolean primo = true;
//      for (int i = j * PASSO; i < ((j + 1) * PASSO) && i < vId.size(); i++)
//      {
//        if (primo)
//        {
//          condition.append(" " + campo + " IN (" + getIdFromvId(vId, i));
//          primo = false;
//        }
//        else
//        {
//          condition.append("," + getIdFromvId(vId, i));
//        }
//      }
//      condition.append(")");
//    }
//    condition.append(")");
//
//    return condition.toString();
//
//  }
//
//  public String getInCondition(String campo, Vector<?> vId, boolean andClause)
//  {
//    int cicli = vId.size() / PASSO;
//    if (vId.size() % PASSO != 0)
//      cicli++;
//    StringBuffer condition = new StringBuffer("  ");
//
//    if (andClause)
//      condition.append(" AND ( ");
//
//    for (int j = 0; j < cicli; j++)
//    {
//      if (j != 0)
//      {
//        condition.append(" OR ");
//      }
//      boolean primo = true;
//      for (int i = j * PASSO; i < ((j + 1) * PASSO) && i < vId.size(); i++)
//      {
//        if (primo)
//        {
//          condition.append(" " + campo + " IN (" + getIdFromvId(vId, i));
//          primo = false;
//        }
//        else
//        {
//          condition.append("," + getIdFromvId(vId, i));
//        }
//      }
//      condition.append(")");
//    }
//
//    if (andClause)
//      condition.append(")");
//
//    return condition.toString();
//
//  }
//
//  public String getNotInCondition(String campo, List<?> vId)
//  {
//    int cicli = vId.size() / PASSO;
//    if (vId.size() % PASSO != 0)
//      cicli++;
//
//    StringBuffer condition = new StringBuffer(" AND ( ");
//    for (int j = 0; j < cicli; j++)
//    {
//      if (j != 0)
//      {
//        condition.append(" OR ");
//      }
//      boolean primo = true;
//      for (int i = j * PASSO; i < ((j + 1) * PASSO) && i < vId.size(); i++)
//      {
//        if (primo)
//        {
//          condition.append(" " + campo + " NOT IN (" + getIdFromvId(vId, i));
//          primo = false;
//        }
//        else
//        {
//          condition.append("," + getIdFromvId(vId, i));
//        }
//      }
//      condition.append(")");
//    }
//    condition.append(")");
//    return condition.toString();
//  }
//
//  protected String getIdFromvId(List<?> vId, int idx)
//  {
//
//    Object o = vId.get(idx);
//
//    if (o instanceof String)
//    {
//      return "'" + (String) o + "'";
//    }
//    else
//      return o.toString();
//  }
//
//  public long getNextSequenceValue(String sequenceName)
//  {
//    String query = " SELECT " + sequenceName + ".NEXTVAL FROM DUAL";
//    return namedParameterJdbcTemplate.queryForLong(query, (SqlParameterSource) null);
//  }
//
//  protected void logInternalUnexpectedException(InternalUnexpectedException e, String logHeader)
//  {
//    DumpUtils.logInternalUnexpectedException(logger, e, logHeader);
//  }
//
//  public Long getLongNull(ResultSet rs, String name) throws SQLException
//  {
//    String value = rs.getString(name);
//    if (value != null)
//    {
//      return new Long(value);
//    }
//    return null;
//  }
//
//  public Long getLongNull(ResultSet rs, String name, Long defaultOnNull) throws SQLException
//  {
//    String value = rs.getString(name);
//    if (value != null)
//    {
//      return new Long(value);
//    }
//    return defaultOnNull;
//  }
//
//  public Integer getIntegerNull(ResultSet rs, String name) throws SQLException
//  {
//    String value = rs.getString(name);
//    if (value != null)
//    {
//      return new Integer(value);
//    }
//    return null;
//  }
//
//  public Long queryForLong(String query, MapSqlParameterSource mapParameterSource)
//  {
//    return namedParameterJdbcTemplate.query(query, mapParameterSource, new ResultSetExtractor<Long>()
//    {
//
//      @Override
//      public Long extractData(ResultSet rs) throws SQLException, DataAccessException
//      {
//        if (rs.next())
//        {
//          return rs.getLong(1);
//        }
//        else
//        {
//          return null;
//        }
//      }
//
//    });
//  }
//  
//  public Long queryForLongNull(String query, MapSqlParameterSource mapParameterSource)
//  {
//    return namedParameterJdbcTemplate.query(query, mapParameterSource, new ResultSetExtractor<Long>()
//    {
//      
//      @Override
//      public Long extractData(ResultSet rs) throws SQLException, DataAccessException
//      {
//        if (rs.next())
//        {
//          BigDecimal bd = rs.getBigDecimal(1);
//          if (bd!=null)
//          {
//            return bd.longValue();
//          }
//        }
//        return null;
//      }
//      
//    });
//  }
//
//  public String queryForString(String query, MapSqlParameterSource mapParameterSource)
//  {
//    return namedParameterJdbcTemplate.query(query, mapParameterSource, new ResultSetExtractor<String>()
//    {
//
//      @Override
//      public String extractData(ResultSet rs) throws SQLException, DataAccessException
//      {
//        if (rs.next())
//        {
//          return rs.getString(1);
//        }
//        else
//        {
//          return null;
//        }
//      }
//
//    });
//  }
//  
//  public BigDecimal queryForBigDecimal(String query, MapSqlParameterSource mapParameterSource)
//  {
//    return namedParameterJdbcTemplate.query(query, mapParameterSource, new ResultSetExtractor<BigDecimal>()
//    {
//
//      @Override
//      public BigDecimal extractData(ResultSet rs) throws SQLException, DataAccessException
//      {
//        if (rs.next())
//        {
//          return rs.getBigDecimal(1);
//        }
//        else
//        {
//          return null;
//        }
//      }
//
//    });
//  }
//
//  public List<DecodificaDTO<String>> queryForDecodificaString(String query, SqlParameterSource parameters)
//  {
//    return namedParameterJdbcTemplate.query(query, parameters, new ResultSetExtractor<List<DecodificaDTO<String>>>()
//    {
//      @Override
//      public List<DecodificaDTO<String>> extractData(ResultSet rs) throws SQLException, DataAccessException
//      {
//        List<DecodificaDTO<String>> list = new ArrayList<DecodificaDTO<String>>();
//        while (rs.next())
//        {
//          DecodificaDTO<String> d = new DecodificaDTO<String>();
//          d.setCodice(rs.getString("CODICE"));
//          d.setDescrizione(rs.getString("DESCRIZIONE"));
//          try
//          {
//            d.setCodiceDescrizione(rs.getString("CODICE_DESCRIZIONE"));
//          }
//          catch (Exception e)
//          {
//            /* NON TUTTE LE QUERY HANNO CODICE_DESCRIZIONE TRA LE COLONNE */}
//          d.setId(rs.getString("ID"));
//          list.add(d);
//        }
//        return list;
//      }
//    });
//  }
//
//  public List<DecodificaDTO<Long>> queryForDecodificaLong(String query, SqlParameterSource parameters)
//  {
//    return namedParameterJdbcTemplate.query(query, parameters, new ResultSetExtractor<List<DecodificaDTO<Long>>>()
//    {
//      @Override
//      public List<DecodificaDTO<Long>> extractData(ResultSet rs) throws SQLException, DataAccessException
//      {
//        List<DecodificaDTO<Long>> list = new ArrayList<DecodificaDTO<Long>>();
//        while (rs.next())
//        {
//          DecodificaDTO<Long> d = new DecodificaDTO<Long>();
//          d.setCodice(rs.getString("CODICE"));
//          d.setDescrizione(rs.getString("DESCRIZIONE"));
//          d.setId(rs.getLong("ID"));
//          list.add(d);
//        }
//        return list;
//      }
//    });
//  }
//  
//  public List<DecodificaDTO<BigDecimal>> queryForDecodificaBigDecimal(String query, SqlParameterSource parameters)
//  {
//    return namedParameterJdbcTemplate.query(query, parameters, new ResultSetExtractor<List<DecodificaDTO<BigDecimal>>>()
//    {
//      @Override
//      public List<DecodificaDTO<BigDecimal>> extractData(ResultSet rs) throws SQLException, DataAccessException
//      {
//        List<DecodificaDTO<BigDecimal>> list = new ArrayList<DecodificaDTO<BigDecimal>>();
//        while (rs.next())
//        {
//          DecodificaDTO<BigDecimal> d = new DecodificaDTO<BigDecimal>();
//          d.setCodice(rs.getString("CODICE"));
//          d.setDescrizione(rs.getString("DESCRIZIONE"));
//          d.setId(rs.getBigDecimal("ID"));
//          list.add(d);
//        }
//        return list;
//      }
//    });
//  }
//
//  public List<DecodificaDTO<Integer>> queryForDecodificaInteger(String query, SqlParameterSource parameters)
//  {
//    return namedParameterJdbcTemplate.query(query, parameters, new ResultSetExtractor<List<DecodificaDTO<Integer>>>()
//    {
//      @Override
//      public List<DecodificaDTO<Integer>> extractData(ResultSet rs) throws SQLException, DataAccessException
//      {
//        List<DecodificaDTO<Integer>> list = new ArrayList<DecodificaDTO<Integer>>();
//        while (rs.next())
//        {
//          DecodificaDTO<Integer> d = new DecodificaDTO<Integer>();
//          d.setCodice(rs.getString("CODICE"));
//          d.setDescrizione(rs.getString("DESCRIZIONE"));
//          d.setId(rs.getInt("ID"));
//          list.add(d);
//        }
//        return list;
//      }
//    });
//  }
//
//  public void delete(String tableName, String idName, long idValue) throws InternalUnexpectedException
//  {
//    String THIS_METHOD = "[" + THIS_CLASS + "::delete]";
//    if (logger.isDebugEnabled())
//    {
//      logger.debug(THIS_METHOD + " BEGIN.");
//    }
//    final String UPDATE = " DELETE FROM " + tableName + " WHERE " + idName + " = " + idValue;
//    ;
//    try
//    {
//      namedParameterJdbcTemplate.update(UPDATE, (MapSqlParameterSource) null);
//    }
//    catch (Throwable t)
//    {
//      InternalUnexpectedException e = new InternalUnexpectedException(t,
//          new LogParameter[]
//          {
//          },
//          new LogVariable[]
//          {}, UPDATE, (MapSqlParameterSource) null);
//      logInternalUnexpectedException(e, THIS_METHOD + "");
//      throw e;
//    }
//    finally
//    {
//      if (logger.isDebugEnabled())
//      {
//        logger.debug(THIS_METHOD + " END.");
//      }
//    }
//  }
//
//  public void delete(String tableName, String idName, long idValue, String idName2, long idValue2)
//      throws InternalUnexpectedException
//  {
//    String THIS_METHOD = "[" + THIS_CLASS + "::delete]";
//    if (logger.isDebugEnabled())
//    {
//      logger.debug(THIS_METHOD + " BEGIN.");
//    }
//    final String UPDATE = " DELETE FROM " + tableName + " WHERE " + idName + " = " + idValue + " AND " + idName2 + " = "
//        + idValue2;
//    try
//    {
//      namedParameterJdbcTemplate.update(UPDATE, (MapSqlParameterSource) null);
//    }
//    catch (Throwable t)
//    {
//      InternalUnexpectedException e = new InternalUnexpectedException(t,
//          new LogParameter[]
//          {
//          },
//          new LogVariable[]
//          {}, UPDATE, (MapSqlParameterSource) null);
//      logInternalUnexpectedException(e, THIS_METHOD + "");
//      throw e;
//    }
//    finally
//    {
//      if (logger.isDebugEnabled())
//      {
//        logger.debug(THIS_METHOD + " END.");
//      }
//    }
//  }
//  
//  public void deleteByDate(String tableName, String idName, long idValue, String idName2, String idValue2)
//      throws InternalUnexpectedException
//  {
//    String THIS_METHOD = "[" + THIS_CLASS + "::deleteByDate]";
//    if (logger.isDebugEnabled())
//    {
//      logger.debug(THIS_METHOD + " BEGIN.");
//    }
//    final String UPDATE = " DELETE FROM " + tableName + " WHERE " + idName + " = " + idValue + " AND " + idName2 + " is "
//        + idValue2;
//    try
//    {
//      namedParameterJdbcTemplate.update(UPDATE, (MapSqlParameterSource) null);
//    }
//    catch (Throwable t)
//    {
//      InternalUnexpectedException e = new InternalUnexpectedException(t,
//          new LogParameter[]
//          {
//          },
//          new LogVariable[]
//          {}, UPDATE, (MapSqlParameterSource) null);
//      logInternalUnexpectedException(e, THIS_METHOD + "");
//      throw e;
//    }
//    finally
//    {
//      if (logger.isDebugEnabled())
//      {
//        logger.debug(THIS_METHOD + " END.");
//      }
//    }
//  }
//
//  public List<DecodificaDTO<Integer>> getTabellaDecodifica(String nomeTabella, boolean orderByDesc)
//      throws InternalUnexpectedException
//  {
//    String THIS_METHOD = "[" + THIS_CLASS + "::getCompetenzeGal]";
//    if (logger.isDebugEnabled())
//    {
//      logger.debug(THIS_METHOD + " BEGIN.");
//    }
//    final String ID = nomeTabella.replace("FIN_D", "ID");
//    StringBuilder queryBuilder = new StringBuilder(" SELECT T.").append(ID).append(" AS ID,\n"
//        + " NULL AS CODICE, \n"
//        + " T.DESCRIZIONE \n"
//        + " FROM ").append(nomeTabella).append(" T ORDER BY ");
//    if (orderByDesc)
//    {
//      queryBuilder.append("T.DESCRIZIONE");
//    }
//    else
//    {
//      queryBuilder.append("T.").append(ID);
//    }
//    queryBuilder.append(" ASC");
//    final String QUERY = queryBuilder.toString();
//    try
//    {
//      return queryForDecodificaInteger(QUERY, (MapSqlParameterSource) null);
//    }
//    catch (Throwable t)
//    {
//      InternalUnexpectedException e = new InternalUnexpectedException(t,
//          (LogParameter[]) null,
//          new LogVariable[]
//          {}, QUERY, (MapSqlParameterSource) null);
//      logInternalUnexpectedException(e, THIS_METHOD + "");
//      throw e;
//    }
//    finally
//    {
//      if (logger.isDebugEnabled())
//      {
//        logger.debug(THIS_METHOD + " END.");
//      }
//    }
//  }
//
//  
//
//}