/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


package it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbservizit.pbandisrv.integration.util.DataFilter;



public class PbandiTFileVO extends GenericVO {

	private Date dtInserimento;

	private Date dtAggiornamento;
  	
	private BigDecimal idDocumentoIndex;

	private BigDecimal idFile;
	
	private BigDecimal idFolder;
	
  	private BigDecimal idUtenteAgg;

	private BigDecimal idUtenteIns;
  	
  	private String nomeFile;
 	
 	private BigDecimal sizeFile;
 	
	public Date getDtAggiornamento() {
		return dtAggiornamento;
	}

	public void setDtAggiornamento(Date dtAggiornamento) {
		this.dtAggiornamento = dtAggiornamento;
	}

	public BigDecimal getIdUtenteAgg() {
		return idUtenteAgg;
	}

	public void setIdUtenteAgg(BigDecimal idUtenteAgg) {
		this.idUtenteAgg = idUtenteAgg;
	}


 	

 	public PbandiTFileVO() {}
  	
	public PbandiTFileVO (BigDecimal idDocumentoIndex) {
	
		this. idDocumentoIndex =  idDocumentoIndex;
	}
  	
	public PbandiTFileVO ( Date dtInserimento, BigDecimal idDocumentoIndex , BigDecimal idFile, BigDecimal idFolder, BigDecimal idUtenteIns, String nomeFile, BigDecimal sizeFile ) {
		this. dtInserimento =  dtInserimento;
		this. idDocumentoIndex =  idDocumentoIndex;
		this. idFile=idFile;
		this. idFolder =  idFolder;
		this. idUtenteIns =  idUtenteIns;
		this. nomeFile  = nomeFile;
		this. sizeFile  = sizeFile;
	}
  	
	
  	public BigDecimal getIdDocumentoIndex() {
		return idDocumentoIndex;
	}

	public void setIdDocumentoIndex(BigDecimal idDocumentoIndex) {
		this.idDocumentoIndex = idDocumentoIndex;
	}

	public String getNomeFile() {
		return nomeFile;
	}

	public void setNomeFile(String nomeFile) {
		this.nomeFile = nomeFile;
	}

	public BigDecimal getSizeFile() {
		return sizeFile;
	}

	public void setSizeFile(BigDecimal sizeFile) {
		this.sizeFile = sizeFile;
	}

	public Date getDtInserimento() {
		return dtInserimento;
	}

	public void setDtInserimento(Date dtInserimento) {
		this.dtInserimento = dtInserimento;
	}

	public BigDecimal getIdFile() {
		return idFile;
	}

	public void setIdFile(BigDecimal idFile) {
		this.idFile = idFile;
	}
	
	public BigDecimal getIdFolder() {
		return idFolder;
	}

	public void setIdFolder(BigDecimal idFolder) {
		this.idFolder = idFolder;
	}

	 
	public BigDecimal getIdUtenteIns() {
		return idUtenteIns;
	}

	public void setIdUtenteIns(BigDecimal idUtenteIns) {
		this.idUtenteIns = idUtenteIns;
	}

	 
	
	
	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid() && dtInserimento != null  &&   idUtenteIns != null && nomeFile != null) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idFile != null ) {
   			isPkValid = true;
   		}

   		return isPkValid;
	}
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull( dtAggiornamento);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtAggiornamento: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtInserimento);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtInserimento: " + temp + "\t\n");
		
	    temp = DataFilter.removeNull( idDocumentoIndex);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idDocumentoIndex: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idFile);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idFile: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idFolder);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idFolder: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idUtenteAgg);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idUtenteAgg: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idUtenteIns);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idUtenteIns: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( nomeFile);
	    if (!DataFilter.isEmpty(temp)) sb.append(" nomeFile: " + temp + "\t\n");
	      
	    temp = DataFilter.removeNull( sizeFile);
	    if (!DataFilter.isEmpty(temp)) sb.append(" sizeFile: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List getPK() {
		java.util.List pk=new java.util.ArrayList();
		
		pk.add("idFile");
		
	    return pk;
	}

	
}
