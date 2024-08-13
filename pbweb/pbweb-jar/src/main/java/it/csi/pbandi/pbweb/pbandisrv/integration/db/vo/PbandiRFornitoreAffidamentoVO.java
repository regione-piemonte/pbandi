/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.pbandisrv.integration.db.vo;

import it.csi.pbandi.pbweb.pbandisrv.integration.util.DataFilter;
import java.math.BigDecimal;
import java.sql.Date;

public class PbandiRFornitoreAffidamentoVO extends GenericVO{
	
	private BigDecimal idFornitore;
	private BigDecimal idAppalto;
	private BigDecimal idTipoPercettore;
	private Date dtInvioVerificaAffidamento;
	private String flgInvioVerificaAffidamento;

	public PbandiRFornitoreAffidamentoVO(){}
	
	public PbandiRFornitoreAffidamentoVO(BigDecimal idFornitore, BigDecimal idAppalto, BigDecimal idTipoPercettore){
		this.idFornitore = idFornitore;
		this.idAppalto = idAppalto;
		this.idTipoPercettore = idTipoPercettore;
	}

	public PbandiRFornitoreAffidamentoVO(
			BigDecimal idFornitore,
			BigDecimal idAppalto,
			BigDecimal idTipoPercettore,
			Date dtInvioVerificaAffidamento,
			String flgInvioVerificaAffidamento	
	) {
		super();
		this.idFornitore = idFornitore;
		this.idAppalto = idAppalto;
		this.idTipoPercettore = idTipoPercettore;
		this.dtInvioVerificaAffidamento = dtInvioVerificaAffidamento;
		this.flgInvioVerificaAffidamento = flgInvioVerificaAffidamento;
	}

	public BigDecimal getIdFornitore() {
		return idFornitore;
	}

	public void setIdFornitore(BigDecimal idFornitore) {
		this.idFornitore = idFornitore;
	}

	public BigDecimal getIdAppalto() {
		return idAppalto;
	}

	public void setIdAppalto(BigDecimal idAppalto) {
		this.idAppalto = idAppalto;
	}

	public BigDecimal getIdTipoPercettore() {
		return idTipoPercettore;
	}

	public void setIdTipoPercettore(BigDecimal idTipoPercettore) {
		this.idTipoPercettore = idTipoPercettore;
	}

	public Date getDtInvioVerificaAffidamento() {
		return dtInvioVerificaAffidamento;
	}

	public void setDtInvioVerificaAffidamento(Date dtInvioVerificaAffidamento) {
		this.dtInvioVerificaAffidamento = dtInvioVerificaAffidamento;
	}

	public String getFlgInvioVerificaAffidamento() {
		return flgInvioVerificaAffidamento;
	}

	public void setFlgInvioVerificaAffidamento(String flgInvioVerificaAffidamento) {
		this.flgInvioVerificaAffidamento = flgInvioVerificaAffidamento;
	}

	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid()) {
   			isValid = true;
   		}
   		return isValid;
	}
	
	@Override
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idFornitore != null && idAppalto != null && idTipoPercettore != null) {
   			isPkValid = true;
   		}
   		return isPkValid;
	}
	
	public java.util.List<String> getPK() {
		java.util.List<String> pk=new java.util.ArrayList<String>();		
			pk.add("idFornitore");
			pk.add("idAppalto");
			pk.add("idTipoPercettore");
	    return pk;
	}

	public String toString() {		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull( idFornitore);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idFornitore: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idAppalto);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idAppalto: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idTipoPercettore);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idTipoPercettore: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtInvioVerificaAffidamento);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtInvioVerificaAffidamento: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( flgInvioVerificaAffidamento);
	    if (!DataFilter.isEmpty(temp)) sb.append(" flgInvioVerificaAffidamento: " + temp + "\t\n");

	    return sb.toString();
	}

}
