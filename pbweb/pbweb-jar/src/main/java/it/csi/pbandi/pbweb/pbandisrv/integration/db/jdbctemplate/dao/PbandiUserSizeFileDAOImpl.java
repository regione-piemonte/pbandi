/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.dao;

import java.sql.Types;

import javax.sql.DataSource;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

public class PbandiUserSizeFileDAOImpl extends PbandiDAO {

	public PbandiUserSizeFileDAOImpl(DataSource dataSource) {
		super(dataSource);
		// TODO Auto-generated constructor stub
	}

	public Long findUsedFileSpace(Long idSoggettoBeneficiario) {
		Long usedFileSpace = 0l;

		MapSqlParameterSource params = new MapSqlParameterSource();

		StringBuilder sqlSelect = new StringBuilder("SELECT SUM (SIZE_FILE) AS userSpaceUsed");
		sqlSelect.append(" FROM pbandi_t_file   JOIN") 
			     .append("  pbandi_t_folder") 
			     .append(" USING(id_folder)") 
    			 .append(" where ID_SOGGETTO_BEN =:idSoggettoBen");

		params.addValue("idSoggettoBen", idSoggettoBeneficiario, Types.BIGINT);
		
		usedFileSpace = queryForLong(sqlSelect.toString(), params);

		return usedFileSpace;
	}

}
