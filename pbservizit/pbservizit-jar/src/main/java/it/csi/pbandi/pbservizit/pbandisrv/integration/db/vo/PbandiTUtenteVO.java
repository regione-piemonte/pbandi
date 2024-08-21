/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


package it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbservizit.pbandisrv.integration.util.DataFilter;



public class PbandiTUtenteVO extends GenericVO {

  	
  	private String login;
  	
  	private String codiceUtente;
  	
  	private BigDecimal idUtente;
  	
  	private BigDecimal idSoggetto;
  	
  	private BigDecimal idTipoAccesso;
  	
  	private Date dtAggiornamento;
  	
  	private Date dtInserimento;
  	
  	private String password;
  	
  	private String flagConsensoMail;
  	
  	private String emailConsenso;
  	
	public PbandiTUtenteVO() {}
  	
	public PbandiTUtenteVO (BigDecimal idUtente) {
	
		this. idUtente =  idUtente;
	}
  	
	public PbandiTUtenteVO (String login, String codiceUtente, BigDecimal idUtente, BigDecimal idSoggetto, BigDecimal idTipoAccesso, Date dtAggiornamento, Date dtInserimento, String password, String flagConsensoMail, String emailConsenso) {
	
		this. login =  login;
		this. codiceUtente =  codiceUtente;
		this. idUtente =  idUtente;
		this. idSoggetto =  idSoggetto;
		this. idTipoAccesso =  idTipoAccesso;
		this. dtAggiornamento =  dtAggiornamento;
		this. dtInserimento =  dtInserimento;
		this. password =  password;
		this. flagConsensoMail =  flagConsensoMail;
		this. emailConsenso =  emailConsenso;
	}
  	
  	
	public String getLogin() {
		return login;
	}
	
	public void setLogin(String login) {
		this.login = login;
	}
	
	public String getCodiceUtente() {
		return codiceUtente;
	}
	
	public void setCodiceUtente(String codiceUtente) {
		this.codiceUtente = codiceUtente;
	}
	
	public BigDecimal getIdUtente() {
		return idUtente;
	}
	
	public void setIdUtente(BigDecimal idUtente) {
		this.idUtente = idUtente;
	}
	
	public BigDecimal getIdSoggetto() {
		return idSoggetto;
	}
	
	public void setIdSoggetto(BigDecimal idSoggetto) {
		this.idSoggetto = idSoggetto;
	}
	
	public BigDecimal getIdTipoAccesso() {
		return idTipoAccesso;
	}
	
	public void setIdTipoAccesso(BigDecimal idTipoAccesso) {
		this.idTipoAccesso = idTipoAccesso;
	}
	
	public Date getDtAggiornamento() {
		return dtAggiornamento;
	}
	
	public void setDtAggiornamento(Date dtAggiornamento) {
		this.dtAggiornamento = dtAggiornamento;
	}
	
	public Date getDtInserimento() {
		return dtInserimento;
	}
	
	public void setDtInserimento(Date dtInserimento) {
		this.dtInserimento = dtInserimento;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getFlagConsensoMail() {
		return flagConsensoMail;
	}

	public void setFlagConsensoMail(String flagConsensoMail) {
		this.flagConsensoMail = flagConsensoMail;
	}

	public String getEmailConsenso() {
		return emailConsenso;
	}

	public void setEmailConsenso(String emailConsenso) {
		this.emailConsenso = emailConsenso;
	}

	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid() && codiceUtente != null && idTipoAccesso != null && dtInserimento != null) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idUtente != null ) {
   			isPkValid = true;
   		}

   		return isPkValid;
	}
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull( login);
	    if (!DataFilter.isEmpty(temp)) sb.append(" login: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( codiceUtente);
	    if (!DataFilter.isEmpty(temp)) sb.append(" codiceUtente: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idUtente);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idUtente: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idSoggetto);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idSoggetto: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idTipoAccesso);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idTipoAccesso: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtAggiornamento);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtAggiornamento: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtInserimento);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtInserimento: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( password);
	    if (!DataFilter.isEmpty(temp)) sb.append(" password: " + temp + "\t\n");
	    
	    
	    temp = DataFilter.removeNull( flagConsensoMail);
	    if (!DataFilter.isEmpty(temp)) sb.append(" flagConsensoMail: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( emailConsenso);
	    if (!DataFilter.isEmpty(temp)) sb.append(" emailConsenso: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List<String> getPK() {
		java.util.List<String> pk=new java.util.ArrayList<String>();
		
			pk.add("idUtente");
		
	    return pk;
	}
	
	
}
