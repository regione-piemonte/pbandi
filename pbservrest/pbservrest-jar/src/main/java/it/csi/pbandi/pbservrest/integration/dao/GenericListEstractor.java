/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservrest.integration.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

public class GenericListEstractor<T> implements ResultSetExtractor<List<T>>
{
  protected GenericDatabaseObjectReader<T> reader;
  public GenericListEstractor(Class<T> objClass)
  {
    reader=new GenericDatabaseObjectReader<T>(objClass);
  }

  @Override
  public List<T> extractData(ResultSet rs) throws SQLException, DataAccessException
  {
    return reader.extractList(rs);
  }
}