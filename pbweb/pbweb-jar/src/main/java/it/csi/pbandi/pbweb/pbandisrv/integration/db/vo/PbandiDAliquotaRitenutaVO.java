
package it.csi.pbandi.pbweb.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbweb.pbandisrv.integration.util.DataFilter;



public class PbandiDAliquotaRitenutaVO extends GenericVO {

  	
  	private Date dtFineValidita;
  	
  	private Date dtInizioValidita;
  	
  	private BigDecimal idAliquotaRitenuta;
  	
  	private BigDecimal percAliquota;
  	
  	private String descAliquota;
  	
  	private BigDecimal idTipoSoggRitenuta;
  	
  	private BigDecimal idTipoRitenuta;
  	
  	private String codOnere;
  	private String codNaturaOnere;
  	private String descNaturaOnere;
  	private BigDecimal percCaricoEnte;
  	private BigDecimal percCaricoSoggetto;
  	
  	
	public PbandiDAliquotaRitenutaVO() {}
  	
	public PbandiDAliquotaRitenutaVO (BigDecimal idAliquotaRitenuta) {
	
		this. idAliquotaRitenuta =  idAliquotaRitenuta;
	}
  	
	public PbandiDAliquotaRitenutaVO (Date dtFineValidita, Date dtInizioValidita, BigDecimal idAliquotaRitenuta, BigDecimal percAliquota, String descAliquota, BigDecimal idTipoSoggRitenuta, BigDecimal idTipoRitenuta,
			String codOnere, String codNaturaOnere, String descNaturaOnere, BigDecimal percCaricoEnte, BigDecimal percCaricoSoggetto) {
	
		this. dtFineValidita =  dtFineValidita;
		this. dtInizioValidita =  dtInizioValidita;
		this. idAliquotaRitenuta =  idAliquotaRitenuta;
		this. percAliquota =  percAliquota;
		this. descAliquota =  descAliquota;
		this. idTipoSoggRitenuta =  idTipoSoggRitenuta;
		this. idTipoRitenuta =  idTipoRitenuta;
		this. codOnere = codOnere;
		this. codNaturaOnere = codNaturaOnere;
		this. descNaturaOnere = descNaturaOnere;
		this. percCaricoEnte = percCaricoEnte;
		this. percCaricoSoggetto = percCaricoSoggetto;
	}
  	
	public String getCodOnere() {
		return codOnere;
	}

	public void setCodOnere(String codOnere) {
		this.codOnere = codOnere;
	}

	public String getCodNaturaOnere() {
		return codNaturaOnere;
	}

	public void setCodNaturaOnere(String codNaturaOnere) {
		this.codNaturaOnere = codNaturaOnere;
	}

	public String getDescNaturaOnere() {
		return descNaturaOnere;
	}

	public void setDescNaturaOnere(String descNaturaOnere) {
		this.descNaturaOnere = descNaturaOnere;
	}

	public BigDecimal getPercCaricoEnte() {
		return percCaricoEnte;
	}

	public void setPercCaricoEnte(BigDecimal percCaricoEnte) {
		this.percCaricoEnte = percCaricoEnte;
	}

	public BigDecimal getPercCaricoSoggetto() {
		return percCaricoSoggetto;
	}

	public void setPercCaricoSoggetto(BigDecimal percCaricoSoggetto) {
		this.percCaricoSoggetto = percCaricoSoggetto;
	}

	public Date getDtFineValidita() {
		return dtFineValidita;
	}
	
	public void setDtFineValidita(Date dtFineValidita) {
		this.dtFineValidita = dtFineValidita;
	}
	
	public Date getDtInizioValidita() {
		return dtInizioValidita;
	}
	
	public void setDtInizioValidita(Date dtInizioValidita) {
		this.dtInizioValidita = dtInizioValidita;
	}
	
	public BigDecimal getIdAliquotaRitenuta() {
		return idAliquotaRitenuta;
	}
	
	public void setIdAliquotaRitenuta(BigDecimal idAliquotaRitenuta) {
		this.idAliquotaRitenuta = idAliquotaRitenuta;
	}
	
	public BigDecimal getPercAliquota() {
		return percAliquota;
	}
	
	public void setPercAliquota(BigDecimal percAliquota) {
		this.percAliquota = percAliquota;
	}
	
	public String getDescAliquota() {
		return descAliquota;
	}
	
	public void setDescAliquota(String descAliquota) {
		this.descAliquota = descAliquota;
	}
	
	public BigDecimal getIdTipoSoggRitenuta() {
		return idTipoSoggRitenuta;
	}
	
	public void setIdTipoSoggRitenuta(BigDecimal idTipoSoggRitenuta) {
		this.idTipoSoggRitenuta = idTipoSoggRitenuta;
	}
	
	public BigDecimal getIdTipoRitenuta() {
		return idTipoRitenuta;
	}
	
	public void setIdTipoRitenuta(BigDecimal idTipoRitenuta) {
		this.idTipoRitenuta = idTipoRitenuta;
	}
	
	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid() && dtInizioValidita != null && descAliquota != null && idTipoSoggRitenuta != null && idTipoRitenuta != null) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idAliquotaRitenuta != null ) {
   			isPkValid = true;
   		}

   		return isPkValid;
	}
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull( dtFineValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtFineValidita: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtInizioValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtInizioValidita: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idAliquotaRitenuta);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idAliquotaRitenuta: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( percAliquota);
	    if (!DataFilter.isEmpty(temp)) sb.append(" percAliquota: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( descAliquota);
	    if (!DataFilter.isEmpty(temp)) sb.append(" descAliquota: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idTipoSoggRitenuta);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idTipoSoggRitenuta: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idTipoRitenuta);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idTipoRitenuta: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( codOnere);
	    if (!DataFilter.isEmpty(temp)) sb.append(" codOnere: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( codNaturaOnere);
	    if (!DataFilter.isEmpty(temp)) sb.append(" codNaturaOnere: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( descNaturaOnere);
	    if (!DataFilter.isEmpty(temp)) sb.append(" descNaturaOnere: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( percCaricoEnte);
	    if (!DataFilter.isEmpty(temp)) sb.append(" percCaricoEnte: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( percCaricoSoggetto);
	    if (!DataFilter.isEmpty(temp)) sb.append(" percCaricoSoggetto: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List getPK() {
		java.util.List pk=new java.util.ArrayList();
		
			pk.add("idAliquotaRitenuta");
		
	    return pk;
	}
	
	
}
