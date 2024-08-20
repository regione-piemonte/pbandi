/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


package it.csi.pbandi.pbwebrce.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbwebrce.pbandisrv.integration.util.DataFilter;



public class PbandiTBeneficiarioBilancioVO extends GenericVO {

  	
  	private BigDecimal idRecapiti;
  	
  	private BigDecimal codiceBeneficiarioBilancio;
  	
  	private BigDecimal idUtenteAgg;
  	
  	private BigDecimal idIndirizzo;
  	
  	private Date dtInserimento;
  	
  	private Date dtAggiornamento;
  	
  	private String quietanzante;
  	
  	private BigDecimal idPersonaFisica;
  	
  	private BigDecimal idEnteGiuridico;
  	
  	private String codFiscQuietanzante;
  	
  	private BigDecimal idBeneficiarioBilancio;
  	
  	private BigDecimal idUtenteIns;
  	
  	private BigDecimal idSede;
  	
  	private BigDecimal idSoggetto;
  	
	public PbandiTBeneficiarioBilancioVO() {}
  	
	public PbandiTBeneficiarioBilancioVO (BigDecimal idBeneficiarioBilancio) {
	
		this. idBeneficiarioBilancio =  idBeneficiarioBilancio;
	}
  	
	public PbandiTBeneficiarioBilancioVO (BigDecimal idRecapiti, BigDecimal codiceBeneficiarioBilancio, BigDecimal idUtenteAgg, BigDecimal idIndirizzo, Date dtInserimento, Date dtAggiornamento, String quietanzante, BigDecimal idPersonaFisica, BigDecimal idEnteGiuridico, String codFiscQuietanzante, BigDecimal idBeneficiarioBilancio, BigDecimal idUtenteIns, BigDecimal idSede, BigDecimal idSoggetto) {
	
		this. idRecapiti =  idRecapiti;
		this. codiceBeneficiarioBilancio =  codiceBeneficiarioBilancio;
		this. idUtenteAgg =  idUtenteAgg;
		this. idIndirizzo =  idIndirizzo;
		this. dtInserimento =  dtInserimento;
		this. dtAggiornamento =  dtAggiornamento;
		this. quietanzante =  quietanzante;
		this. idPersonaFisica =  idPersonaFisica;
		this. idEnteGiuridico =  idEnteGiuridico;
		this. codFiscQuietanzante =  codFiscQuietanzante;
		this. idBeneficiarioBilancio =  idBeneficiarioBilancio;
		this. idUtenteIns =  idUtenteIns;
		this. idSede =  idSede;
		this. idSoggetto =  idSoggetto;
	}
  	
  	
	public BigDecimal getIdRecapiti() {
		return idRecapiti;
	}
	
	public void setIdRecapiti(BigDecimal idRecapiti) {
		this.idRecapiti = idRecapiti;
	}
	
	public BigDecimal getCodiceBeneficiarioBilancio() {
		return codiceBeneficiarioBilancio;
	}
	
	public void setCodiceBeneficiarioBilancio(BigDecimal codiceBeneficiarioBilancio) {
		this.codiceBeneficiarioBilancio = codiceBeneficiarioBilancio;
	}
	
	public BigDecimal getIdUtenteAgg() {
		return idUtenteAgg;
	}
	
	public void setIdUtenteAgg(BigDecimal idUtenteAgg) {
		this.idUtenteAgg = idUtenteAgg;
	}
	
	public BigDecimal getIdIndirizzo() {
		return idIndirizzo;
	}
	
	public void setIdIndirizzo(BigDecimal idIndirizzo) {
		this.idIndirizzo = idIndirizzo;
	}
	
	public Date getDtInserimento() {
		return dtInserimento;
	}
	
	public void setDtInserimento(Date dtInserimento) {
		this.dtInserimento = dtInserimento;
	}
	
	public Date getDtAggiornamento() {
		return dtAggiornamento;
	}
	
	public void setDtAggiornamento(Date dtAggiornamento) {
		this.dtAggiornamento = dtAggiornamento;
	}
	
	public String getQuietanzante() {
		return quietanzante;
	}
	
	public void setQuietanzante(String quietanzante) {
		this.quietanzante = quietanzante;
	}
	
	public BigDecimal getIdPersonaFisica() {
		return idPersonaFisica;
	}
	
	public void setIdPersonaFisica(BigDecimal idPersonaFisica) {
		this.idPersonaFisica = idPersonaFisica;
	}
	
	public BigDecimal getIdEnteGiuridico() {
		return idEnteGiuridico;
	}
	
	public void setIdEnteGiuridico(BigDecimal idEnteGiuridico) {
		this.idEnteGiuridico = idEnteGiuridico;
	}
	
	public String getCodFiscQuietanzante() {
		return codFiscQuietanzante;
	}
	
	public void setCodFiscQuietanzante(String codFiscQuietanzante) {
		this.codFiscQuietanzante = codFiscQuietanzante;
	}
	
	public BigDecimal getIdBeneficiarioBilancio() {
		return idBeneficiarioBilancio;
	}
	
	public void setIdBeneficiarioBilancio(BigDecimal idBeneficiarioBilancio) {
		this.idBeneficiarioBilancio = idBeneficiarioBilancio;
	}
	
	public BigDecimal getIdUtenteIns() {
		return idUtenteIns;
	}
	
	public void setIdUtenteIns(BigDecimal idUtenteIns) {
		this.idUtenteIns = idUtenteIns;
	}
	
	public BigDecimal getIdSede() {
		return idSede;
	}
	
	public void setIdSede(BigDecimal idSede) {
		this.idSede = idSede;
	}
	
	public BigDecimal getIdSoggetto() {
		return idSoggetto;
	}
	
	public void setIdSoggetto(BigDecimal idSoggetto) {
		this.idSoggetto = idSoggetto;
	}
	
	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid() && dtInserimento != null && idUtenteIns != null) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idBeneficiarioBilancio != null ) {
   			isPkValid = true;
   		}

   		return isPkValid;
	}
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull( idRecapiti);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idRecapiti: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( codiceBeneficiarioBilancio);
	    if (!DataFilter.isEmpty(temp)) sb.append(" codiceBeneficiarioBilancio: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idUtenteAgg);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idUtenteAgg: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idIndirizzo);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idIndirizzo: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtInserimento);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtInserimento: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtAggiornamento);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtAggiornamento: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( quietanzante);
	    if (!DataFilter.isEmpty(temp)) sb.append(" quietanzante: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idPersonaFisica);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idPersonaFisica: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idEnteGiuridico);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idEnteGiuridico: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( codFiscQuietanzante);
	    if (!DataFilter.isEmpty(temp)) sb.append(" codFiscQuietanzante: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idBeneficiarioBilancio);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idBeneficiarioBilancio: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idUtenteIns);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idUtenteIns: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idSede);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idSede: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idSoggetto);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idSoggetto: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List getPK() {
		java.util.List pk=new java.util.ArrayList();
		
			pk.add("idBeneficiarioBilancio");
		
	    return pk;
	}
	
	
}
