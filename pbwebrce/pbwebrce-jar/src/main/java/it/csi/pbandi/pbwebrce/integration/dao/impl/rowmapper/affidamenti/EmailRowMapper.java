/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebrce.integration.dao.impl.rowmapper.affidamenti;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.csi.pbandi.pbwebrce.integration.vo.affidamenti.AffidamentoVO;
import it.csi.pbandi.pbwebrce.integration.vo.affidamenti.Email;


public class EmailRowMapper implements RowMapper<Email> {

	@Override
	public Email mapRow(ResultSet rs, int rowNum) throws SQLException {
		Email cm = new Email();
		
		cm.setMail(rs.getString("INDIRIZZO_MAIL"));
		
		return cm;
	}



}
