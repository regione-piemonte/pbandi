
package it.csi.pbandi.pbweb.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbweb.pbandisrv.integration.util.DataFilter;



public class PbandiTProceduraAggiudicazVO extends GenericVO {

  	
  	private BigDecimal iva;
  	
  	private Date dtInizioValidita;
  	
  	private BigDecimal idUtenteAgg;
  	
  	private String descProcAgg;
  	
  	private BigDecimal idTipologiaAggiudicaz;
  	
  	private String cigProcAgg;
  	
  	private BigDecimal idProgetto;
  	
  	private BigDecimal idProceduraAggiudicaz;
  	
  	private Date dtFineValidita;
  	
  	private String codProcAgg;
  	
  	private BigDecimal importo;
  	
  	private BigDecimal idUtenteIns;
  	
  	private BigDecimal idMotivoAssenzaCig;
  	
  	private Date dtAggiudicazione;
  	
	public PbandiTProceduraAggiudicazVO() {}
  	
	public PbandiTProceduraAggiudicazVO (BigDecimal idProceduraAggiudicaz) {
	
		this. idProceduraAggiudicaz =  idProceduraAggiudicaz;
	}
  	
	public PbandiTProceduraAggiudicazVO (BigDecimal iva, Date dtInizioValidita, BigDecimal idUtenteAgg, String descProcAgg, BigDecimal idTipologiaAggiudicaz, String cigProcAgg, BigDecimal idProgetto, BigDecimal idProceduraAggiudicaz, 
			Date dtFineValidita, String codProcAgg, BigDecimal importo, 
			BigDecimal idUtenteIns, BigDecimal idMotivoAssenzaCig, Date dtAggiudicazione) {
	
		this. iva =  iva;
		this. dtInizioValidita =  dtInizioValidita;
		this. idUtenteAgg =  idUtenteAgg;
		this. descProcAgg =  descProcAgg;
		this. idTipologiaAggiudicaz =  idTipologiaAggiudicaz;
		this. cigProcAgg =  cigProcAgg;
		this. idProgetto =  idProgetto;
		this. idProceduraAggiudicaz =  idProceduraAggiudicaz;
		this. dtFineValidita =  dtFineValidita;
		this. codProcAgg =  codProcAgg;
		this. importo =  importo;
		this. idUtenteIns =  idUtenteIns;
		this.idMotivoAssenzaCig = idMotivoAssenzaCig;
		this.dtAggiudicazione = dtAggiudicazione;
	}
  	
  	
	public BigDecimal getIva() {
		return iva;
	}
	
	public void setIva(BigDecimal iva) {
		this.iva = iva;
	}
	
	public Date getDtInizioValidita() {
		return dtInizioValidita;
	}
	
	public void setDtInizioValidita(Date dtInizioValidita) {
		this.dtInizioValidita = dtInizioValidita;
	}
	
	public BigDecimal getIdUtenteAgg() {
		return idUtenteAgg;
	}
	
	public void setIdUtenteAgg(BigDecimal idUtenteAgg) {
		this.idUtenteAgg = idUtenteAgg;
	}
	
	public String getDescProcAgg() {
		return descProcAgg;
	}
	
	public void setDescProcAgg(String descProcAgg) {
		this.descProcAgg = descProcAgg;
	}
	
	public BigDecimal getIdTipologiaAggiudicaz() {
		return idTipologiaAggiudicaz;
	}
	
	public void setIdTipologiaAggiudicaz(BigDecimal idTipologiaAggiudicaz) {
		this.idTipologiaAggiudicaz = idTipologiaAggiudicaz;
	}
	
	public String getCigProcAgg() {
		return cigProcAgg;
	}
	
	public void setCigProcAgg(String cigProcAgg) {
		this.cigProcAgg = cigProcAgg;
	}
	
	public BigDecimal getIdProgetto() {
		return idProgetto;
	}
	
	public void setIdProgetto(BigDecimal idProgetto) {
		this.idProgetto = idProgetto;
	}
	
	public BigDecimal getIdProceduraAggiudicaz() {
		return idProceduraAggiudicaz;
	}
	
	public void setIdProceduraAggiudicaz(BigDecimal idProceduraAggiudicaz) {
		this.idProceduraAggiudicaz = idProceduraAggiudicaz;
	}
	
	public Date getDtFineValidita() {
		return dtFineValidita;
	}
	
	public void setDtFineValidita(Date dtFineValidita) {
		this.dtFineValidita = dtFineValidita;
	}
	
	public String getCodProcAgg() {
		return codProcAgg;
	}
	
	public void setCodProcAgg(String codProcAgg) {
		this.codProcAgg = codProcAgg;
	}
	
	public BigDecimal getImporto() {
		return importo;
	}
	
	public void setImporto(BigDecimal importo) {
		this.importo = importo;
	}
	
	public BigDecimal getIdUtenteIns() {
		return idUtenteIns;
	}
	
	public void setIdUtenteIns(BigDecimal idUtenteIns) {
		this.idUtenteIns = idUtenteIns;
	}
	
	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid() && dtInizioValidita != null && descProcAgg != null && idTipologiaAggiudicaz != null && idProgetto != null && importo != null && idUtenteIns != null) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idProceduraAggiudicaz != null ) {
   			isPkValid = true;
   		}

   		return isPkValid;
	}
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull( iva);
	    if (!DataFilter.isEmpty(temp)) sb.append(" iva: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtInizioValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtInizioValidita: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idUtenteAgg);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idUtenteAgg: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( descProcAgg);
	    if (!DataFilter.isEmpty(temp)) sb.append(" descProcAgg: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idTipologiaAggiudicaz);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idTipologiaAggiudicaz: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( cigProcAgg);
	    if (!DataFilter.isEmpty(temp)) sb.append(" cigProcAgg: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idProgetto);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idProgetto: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idProceduraAggiudicaz);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idProceduraAggiudicaz: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtFineValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtFineValidita: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( codProcAgg);
	    if (!DataFilter.isEmpty(temp)) sb.append(" codProcAgg: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( importo);
	    if (!DataFilter.isEmpty(temp)) sb.append(" importo: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idUtenteIns);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idUtenteIns: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idMotivoAssenzaCig);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idMotivoAssenzaCig: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtAggiudicazione);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtAggiudicazione: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public BigDecimal getIdMotivoAssenzaCig() {
		return idMotivoAssenzaCig;
	}

	public void setIdMotivoAssenzaCig(BigDecimal idMotivoAssenzaCig) {
		this.idMotivoAssenzaCig = idMotivoAssenzaCig;
	}

	public Date getDtAggiudicazione() {
		return dtAggiudicazione;
	}

	public void setDtAggiudicazione(Date dtAggiudicazione) {
		this.dtAggiudicazione = dtAggiudicazione;
	}

	public java.util.List getPK() {
		java.util.List pk=new java.util.ArrayList();
		
			pk.add("idProceduraAggiudicaz");
		
	    return pk;
	}
	
	
}
