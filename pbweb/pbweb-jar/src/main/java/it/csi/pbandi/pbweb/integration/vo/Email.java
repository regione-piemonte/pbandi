package it.csi.pbandi.pbweb.integration.vo;

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
