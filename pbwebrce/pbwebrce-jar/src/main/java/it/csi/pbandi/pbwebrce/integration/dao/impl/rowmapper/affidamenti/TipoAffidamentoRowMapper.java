/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebrce.integration.dao.impl.rowmapper.affidamenti;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.csi.pbandi.pbwebrce.pbandisrv.integration.db.vo.PbandiDTipoAffidamentoVO;


public class TipoAffidamentoRowMapper implements RowMapper<PbandiDTipoAffidamentoVO> {

	@Override
	public PbandiDTipoAffidamentoVO mapRow(ResultSet rs, int rowNum) throws SQLException {
		PbandiDTipoAffidamentoVO cm = new PbandiDTipoAffidamentoVO();
		cm.setIdTipoAffidamento(rs.getBigDecimal("ID_TIPO_AFFIDAMENTO"));
		cm.setDescTipoAffidamento(rs.getString("DESC_TIPO_AFFIDAMENTO"));
		return cm;
	}


}
