/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebrce.integration.vo.affidamenti;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;


public class Email implements RowMapper<Email> {
	
	private String mail;
	
	
	
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}



	@Override
	public Email mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		Email email = new Email();
		email.setMail(rs.getString("INDIRIZZO_MAIL"));
		return email;
	}

	
	
}
