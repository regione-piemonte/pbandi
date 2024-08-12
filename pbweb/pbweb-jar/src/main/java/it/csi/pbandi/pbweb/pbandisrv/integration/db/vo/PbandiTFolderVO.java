
package it.csi.pbandi.pbweb.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbweb.pbandisrv.integration.util.DataFilter;



public class PbandiTFolderVO extends GenericVO {

	private Date dtInserimento;
  	
  	private Date dtAggiornamento;
  	
  	private BigDecimal idFolder;
  	
  	private BigDecimal idPadre;
  	
  	private BigDecimal idSoggettoBen;
  	
  	private BigDecimal idUtenteIns;
  	
 	private BigDecimal idUtenteAgg;
 	
 	private String nomeFolder;

  	public PbandiTFolderVO() {}
  	
	public PbandiTFolderVO (BigDecimal idFolder) {
	
		this. idFolder =  idFolder;
	}
  	
	public PbandiTFolderVO ( Date dtInserimento, Date dtAggiornamento ,BigDecimal idFolder, BigDecimal idPadre, BigDecimal idSoggettoBen, String nomeFolder, BigDecimal idUtenteIns , BigDecimal idUtenteAgg) {
	
		this. dtInserimento =  dtInserimento;
		this. dtAggiornamento =  dtAggiornamento;
		this. idFolder =  idFolder;
		this. idPadre =  idPadre;
		this. idSoggettoBen =  idSoggettoBen;
		this. idUtenteIns =  idUtenteIns;
		this. idUtenteAgg =  idUtenteAgg;
		this. nomeFolder  = nomeFolder;
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

	public BigDecimal getIdFolder() {
		return idFolder;
	}

	public void setIdFolder(BigDecimal idFolder) {
		this.idFolder = idFolder;
	}

	public BigDecimal getIdPadre() {
		return idPadre;
	}

	public void setIdPadre(BigDecimal idPadre) {
		this.idPadre = idPadre;
	}

	public BigDecimal getIdSoggettoBen() {
		return idSoggettoBen;
	}

	public void setIdSoggettoBen(BigDecimal idSoggettoBen) {
		this.idSoggettoBen = idSoggettoBen;
	}

	public BigDecimal getIdUtenteIns() {
		return idUtenteIns;
	}

	public void setIdUtenteIns(BigDecimal idUtenteIns) {
		this.idUtenteIns = idUtenteIns;
	}

	public BigDecimal getIdUtenteAgg() {
		return idUtenteAgg;
	}

	public void setIdUtenteAgg(BigDecimal idUtenteAgg) {
		this.idUtenteAgg = idUtenteAgg;
	}

	public String getNomeFolder() {
		return nomeFolder;
	}

	public void setNomeFolder(String nomeFolder) {
		this.nomeFolder = nomeFolder;
	}


	
	
	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid() && dtInserimento != null && idFolder != null && idUtenteIns != null && nomeFolder != null) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idFolder != null ) {
   			isPkValid = true;
   		}

   		return isPkValid;
	}
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull( dtInserimento);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtInserimento: " + temp + "\t\n");
		
	    temp = DataFilter.removeNull( dtAggiornamento);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtAggiornamento: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idFolder);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idFolder: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idPadre);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idPadre: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idSoggettoBen);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idSoggettoBen: " + temp + "\t\n");
	      
	    temp = DataFilter.removeNull( idUtenteIns);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idUtenteIns: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idUtenteAgg);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idUtenteAgg: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( nomeFolder);
	    if (!DataFilter.isEmpty(temp)) sb.append(" nomeFolder: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List getPK() {
		java.util.List pk=new java.util.ArrayList();
		
			pk.add("idFolder");
		
	    return pk;
	}
	
	
}
