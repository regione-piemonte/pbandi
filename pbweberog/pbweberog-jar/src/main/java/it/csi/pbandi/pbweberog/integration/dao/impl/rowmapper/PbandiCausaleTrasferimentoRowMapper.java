/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweberog.integration.dao.impl.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.CausaleTrasferimentoVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.util.BeanMapper;

public class PbandiCausaleTrasferimentoRowMapper implements RowMapper<CausaleTrasferimentoVO> {

	public CausaleTrasferimentoVO mapRow(ResultSet rs, int row) throws SQLException {
		BeanMapper beanMapper = new BeanMapper();
		CausaleTrasferimentoVO vo = (CausaleTrasferimentoVO) beanMapper.toBean(rs, CausaleTrasferimentoVO.class);
		return vo;
	}
}
