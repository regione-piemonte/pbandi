
package it.csi.pbandi.pbweb.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbweb.pbandisrv.integration.util.DataFilter;



public class PbandiTRettificaDiSpesaVO extends GenericVO {

  	
  	private BigDecimal importoRettifica;
  	
  	private BigDecimal idRettificaDiSpesa;
  	
  	private BigDecimal idUtenteAgg;
  	
  	private BigDecimal progrPagQuotParteDocSp;
  	
  	private BigDecimal idUtenteIns;
  	
  	private String riferimento;
  	
  	private Date dtRettifica;
  	
	public PbandiTRettificaDiSpesaVO() {}
  	
	public PbandiTRettificaDiSpesaVO (BigDecimal idRettificaDiSpesa) {
	
		this. idRettificaDiSpesa =  idRettificaDiSpesa;
	}
  	
	public PbandiTRettificaDiSpesaVO (BigDecimal importoRettifica, BigDecimal idRettificaDiSpesa, BigDecimal idUtenteAgg, BigDecimal progrPagQuotParteDocSp, BigDecimal idUtenteIns, String riferimento, Date dtRettifica) {
	
		this. importoRettifica =  importoRettifica;
		this. idRettificaDiSpesa =  idRettificaDiSpesa;
		this. idUtenteAgg =  idUtenteAgg;
		this. progrPagQuotParteDocSp =  progrPagQuotParteDocSp;
		this. idUtenteIns =  idUtenteIns;
		this. riferimento =  riferimento;
		this. dtRettifica =  dtRettifica;
	}
  	
  	
	public BigDecimal getImportoRettifica() {
		return importoRettifica;
	}
	
	public void setImportoRettifica(BigDecimal importoRettifica) {
		this.importoRettifica = importoRettifica;
	}
	
	public BigDecimal getIdRettificaDiSpesa() {
		return idRettificaDiSpesa;
	}
	
	public void setIdRettificaDiSpesa(BigDecimal idRettificaDiSpesa) {
		this.idRettificaDiSpesa = idRettificaDiSpesa;
	}
	
	public BigDecimal getIdUtenteAgg() {
		return idUtenteAgg;
	}
	
	public void setIdUtenteAgg(BigDecimal idUtenteAgg) {
		this.idUtenteAgg = idUtenteAgg;
	}
	
	public BigDecimal getProgrPagQuotParteDocSp() {
		return progrPagQuotParteDocSp;
	}
	
	public void setProgrPagQuotParteDocSp(BigDecimal progrPagQuotParteDocSp) {
		this.progrPagQuotParteDocSp = progrPagQuotParteDocSp;
	}
	
	public BigDecimal getIdUtenteIns() {
		return idUtenteIns;
	}
	
	public void setIdUtenteIns(BigDecimal idUtenteIns) {
		this.idUtenteIns = idUtenteIns;
	}
	
	public String getRiferimento() {
		return riferimento;
	}
	
	public void setRiferimento(String riferimento) {
		this.riferimento = riferimento;
	}
	
	public Date getDtRettifica() {
		return dtRettifica;
	}
	
	public void setDtRettifica(Date dtRettifica) {
		this.dtRettifica = dtRettifica;
	}
	
	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid() && importoRettifica != null && progrPagQuotParteDocSp != null && idUtenteIns != null && dtRettifica != null) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idRettificaDiSpesa != null ) {
   			isPkValid = true;
   		}

   		return isPkValid;
	}
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull( importoRettifica);
	    if (!DataFilter.isEmpty(temp)) sb.append(" importoRettifica: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idRettificaDiSpesa);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idRettificaDiSpesa: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idUtenteAgg);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idUtenteAgg: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( progrPagQuotParteDocSp);
	    if (!DataFilter.isEmpty(temp)) sb.append(" progrPagQuotParteDocSp: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idUtenteIns);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idUtenteIns: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( riferimento);
	    if (!DataFilter.isEmpty(temp)) sb.append(" riferimento: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtRettifica);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtRettifica: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List<String> getPK() {
		java.util.List<String> pk=new java.util.ArrayList<String>();
		
			pk.add("idRettificaDiSpesa");
		
	    return pk;
	}
	
	
}
