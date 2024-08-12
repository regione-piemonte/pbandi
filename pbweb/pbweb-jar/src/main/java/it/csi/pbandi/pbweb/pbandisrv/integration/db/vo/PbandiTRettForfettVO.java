
package it.csi.pbandi.pbweb.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbweb.pbandisrv.integration.util.DataFilter;

public class PbandiTRettForfettVO extends GenericVO {

	private BigDecimal idRettificaForfett;
  	
  	private BigDecimal idAppalto;
  	
  	private BigDecimal idAppaltoChecklist;
  	
  	private BigDecimal idCategAnagrafica;
  	
  	private BigDecimal idPropostaCertificaz;
  	
  	private Double percRett;
  	
  	private Date dataInserimento;
  	
	public PbandiTRettForfettVO() {}
  	
	public PbandiTRettForfettVO (BigDecimal idRettificaDiSpesa) {
	
		this. idRettificaForfett =  idRettificaForfett;
	}
  	
	public PbandiTRettForfettVO (BigDecimal idRettificaForfett, BigDecimal idAppalto, BigDecimal idAppaltoChecklist, BigDecimal idCategAnagrafica, BigDecimal idPropostaCertificaz, Double percRett, Date dataInserimento) {	
		this.idRettificaForfett =  idRettificaForfett;
		this.idAppalto =  idAppalto;
		this.idAppaltoChecklist =  idAppaltoChecklist;
		this.idCategAnagrafica =  idCategAnagrafica;
		this.idPropostaCertificaz =  idPropostaCertificaz;
		this.percRett =  percRett;
		this.dataInserimento =  dataInserimento;
	}
	
	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid()) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idRettificaForfett != null ) {
   			isPkValid = true;
   		}
   		return isPkValid;
	}
	
	public java.util.List<String> getPK() {
		java.util.List<String> pk=new java.util.ArrayList<String>();
			pk.add("idRettificaForfett");
	    return pk;
	}
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull( idRettificaForfett);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idRettificaForfett: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idAppalto);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idAppalto: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idAppaltoChecklist);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idAppaltoChecklist: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idCategAnagrafica);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idCategAnagrafica: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idPropostaCertificaz);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idPropostaCertificaz: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( percRett);
	    if (!DataFilter.isEmpty(temp)) sb.append(" percRett: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dataInserimento);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dataInserimento: " + temp + "\t\n");
	    
	    return sb.toString();
	}

	public BigDecimal getIdRettificaForfett() {
		return idRettificaForfett;
	}

	public void setIdRettificaForfett(BigDecimal idRettificaForfett) {
		this.idRettificaForfett = idRettificaForfett;
	}

	public BigDecimal getIdAppalto() {
		return idAppalto;
	}

	public void setIdAppalto(BigDecimal idAppalto) {
		this.idAppalto = idAppalto;
	}

	public BigDecimal getIdAppaltoChecklist() {
		return idAppaltoChecklist;
	}

	public void setIdAppaltoChecklist(BigDecimal idAppaltoChecklist) {
		this.idAppaltoChecklist = idAppaltoChecklist;
	}

	public BigDecimal getIdCategAnagrafica() {
		return idCategAnagrafica;
	}

	public void setIdCategAnagrafica(BigDecimal idCategAnagrafica) {
		this.idCategAnagrafica = idCategAnagrafica;
	}

	public BigDecimal getIdPropostaCertificaz() {
		return idPropostaCertificaz;
	}

	public void setIdPropostaCertificaz(BigDecimal idPropostaCertificaz) {
		this.idPropostaCertificaz = idPropostaCertificaz;
	}

	public Double getPercRett() {
		return percRett;
	}

	public void setPercRett(Double percRett) {
		this.percRett = percRett;
	}

	public Date getDataInserimento() {
		return dataInserimento;
	}

	public void setDataInserimento(Date dataInserimento) {
		this.dataInserimento = dataInserimento;
	}
	
}
