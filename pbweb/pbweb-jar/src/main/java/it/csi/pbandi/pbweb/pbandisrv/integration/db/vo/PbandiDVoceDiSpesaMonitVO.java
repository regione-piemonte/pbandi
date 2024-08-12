
package it.csi.pbandi.pbweb.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbweb.pbandisrv.integration.util.DataFilter;



public class PbandiDVoceDiSpesaMonitVO extends GenericVO {

	// CDU-13-V08 inizio
	private BigDecimal idNaturaCipe;
	
  	public BigDecimal getIdNaturaCipe() {
		return idNaturaCipe;
	}

	public void setIdNaturaCipe(BigDecimal idNaturaCipe) {
		this.idNaturaCipe = idNaturaCipe;
	}
	// CDU-13-V08 fine
	
	private BigDecimal idTipoOperazione;
  	
  	private Date dtFineValidita;
  	
  	private String descVoceSpesaMonit;
  	
  	private Date dtInizioValidita;
  	
  	private BigDecimal idVoceDiSpesaMonit;
  	
  	private String codIgrueT28;
  	
	public PbandiDVoceDiSpesaMonitVO() {}
  	
	public PbandiDVoceDiSpesaMonitVO (BigDecimal idVoceDiSpesaMonit) {
	
		this. idVoceDiSpesaMonit =  idVoceDiSpesaMonit;
	}
  	
	public PbandiDVoceDiSpesaMonitVO (BigDecimal idTipoOperazione, Date dtFineValidita, String descVoceSpesaMonit, Date dtInizioValidita, BigDecimal idVoceDiSpesaMonit, String codIgrueT28, BigDecimal idNaturaCipe) {
	
		this.idTipoOperazione =  idTipoOperazione;
		this.dtFineValidita =  dtFineValidita;
		this.descVoceSpesaMonit =  descVoceSpesaMonit;
		this.dtInizioValidita =  dtInizioValidita;
		this.idVoceDiSpesaMonit =  idVoceDiSpesaMonit;
		this.codIgrueT28 =  codIgrueT28;
		this.idNaturaCipe = idNaturaCipe;
	}
  	
  	
	public BigDecimal getIdTipoOperazione() {
		return idTipoOperazione;
	}
	
	public void setIdTipoOperazione(BigDecimal idTipoOperazione) {
		this.idTipoOperazione = idTipoOperazione;
	}
	
	public Date getDtFineValidita() {
		return dtFineValidita;
	}
	
	public void setDtFineValidita(Date dtFineValidita) {
		this.dtFineValidita = dtFineValidita;
	}
	
	public String getDescVoceSpesaMonit() {
		return descVoceSpesaMonit;
	}
	
	public void setDescVoceSpesaMonit(String descVoceSpesaMonit) {
		this.descVoceSpesaMonit = descVoceSpesaMonit;
	}
	
	public Date getDtInizioValidita() {
		return dtInizioValidita;
	}
	
	public void setDtInizioValidita(Date dtInizioValidita) {
		this.dtInizioValidita = dtInizioValidita;
	}
	
	public BigDecimal getIdVoceDiSpesaMonit() {
		return idVoceDiSpesaMonit;
	}
	
	public void setIdVoceDiSpesaMonit(BigDecimal idVoceDiSpesaMonit) {
		this.idVoceDiSpesaMonit = idVoceDiSpesaMonit;
	}
	
	public String getCodIgrueT28() {
		return codIgrueT28;
	}
	
	public void setCodIgrueT28(String codIgrueT28) {
		this.codIgrueT28 = codIgrueT28;
	}
	
	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid() && idTipoOperazione != null && descVoceSpesaMonit != null && dtInizioValidita != null && codIgrueT28 != null) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idVoceDiSpesaMonit != null ) {
   			isPkValid = true;
   		}

   		return isPkValid;
	}
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull( idTipoOperazione);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idTipoOperazione: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtFineValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtFineValidita: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( descVoceSpesaMonit);
	    if (!DataFilter.isEmpty(temp)) sb.append(" descVoceSpesaMonit: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtInizioValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtInizioValidita: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idVoceDiSpesaMonit);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idVoceDiSpesaMonit: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( codIgrueT28);
	    if (!DataFilter.isEmpty(temp)) sb.append(" codIgrueT28: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idNaturaCipe);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idNaturaCipe: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List<String> getPK() {
		java.util.List<String> pk=new java.util.ArrayList<String>();
		
			pk.add("idVoceDiSpesaMonit");
		
	    return pk;
	}
	
	
}
