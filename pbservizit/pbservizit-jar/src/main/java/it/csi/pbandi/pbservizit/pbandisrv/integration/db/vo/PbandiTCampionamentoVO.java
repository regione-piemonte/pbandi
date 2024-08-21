/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo;

import it.csi.pbandi.pbservizit.pbandisrv.integration.util.DataFilter;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

public class PbandiTCampionamentoVO extends GenericVO{
	
	/**
	  	id_campione, 
		data_campionamento, 
		id_proposta_certificaz, 
		id_linea_di_intervento, 
		id_categ_anagrafica, 
		id_periodo, 
		tipo_controlli
	 * */
	
	private BigDecimal idCampione;
	private Date dataCampionamento;
	private BigDecimal idPropostaCertificaz;
	private BigDecimal idLineaDiIntervento;
	private BigDecimal idCategAnagrafica;
	private BigDecimal idPeriodo;
	private String tipoControlli;
	
	public PbandiTCampionamentoVO(){}
	
	public PbandiTCampionamentoVO(BigDecimal idCampione, Date dataCampionamento,         
			BigDecimal idPropostaCertificaz,
			BigDecimal idLineaDiIntervento, 
			BigDecimal idCategAnagrafica,   
			BigDecimal idPeriodo,           
			String tipoControlli){
		
		 this.idCampione = idCampione;          
		 this.dataCampionamento = dataCampionamento;         
		 this.idPropostaCertificaz = idPropostaCertificaz;
		 this.idLineaDiIntervento = idLineaDiIntervento; 
		 this.idCategAnagrafica = idCategAnagrafica;   
		 this.idPeriodo = idPeriodo;           
		 this.tipoControlli = tipoControlli;           

	}
	
	public PbandiTCampionamentoVO(BigDecimal idCampione){
		this.idCampione = idCampione;
	}
	

	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid() && idPeriodo != null && idCategAnagrafica != null) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idCampione != null ) {
   			isPkValid = true;
   		}

   		return isPkValid;
	}
	public BigDecimal getIdCampione() {
		return idCampione;
	}

	public void setIdCampione(BigDecimal idCampione) {
		this.idCampione = idCampione;
	}

	public Date getDataCampionamento() {
		return dataCampionamento;
	}

	public void setDataCampionamento(Date dataCampionamento) {
		this.dataCampionamento = dataCampionamento;
	}

	public BigDecimal getIdPropostaCertificaz() {
		return idPropostaCertificaz;
	}

	public void setIdPropostaCertificaz(BigDecimal idPropostaCertificaz) {
		this.idPropostaCertificaz = idPropostaCertificaz;
	}

	public BigDecimal getIdLineaDiIntervento() {
		return idLineaDiIntervento;
	}

	public void setIdLineaDiIntervento(BigDecimal idLineaDiIntervento) {
		this.idLineaDiIntervento = idLineaDiIntervento;
	}

	public BigDecimal getIdCategAnagrafica() {
		return idCategAnagrafica;
	}

	public void setIdCategAnagrafica(BigDecimal idCategAnagrafica) {
		this.idCategAnagrafica = idCategAnagrafica;
	}

	public BigDecimal getIdPeriodo() {
		return idPeriodo;
	}

	public void setIdPeriodo(BigDecimal idPeriodo) {
		this.idPeriodo = idPeriodo;
	}

	public String getTipoControlli() {
		return tipoControlli;
	}

	public void setTipoControlli(String tipoControlli) {
		this.tipoControlli = tipoControlli;
	}
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull( idCampione);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idCampione: " + temp + "\t\n");
	   
	    temp = DataFilter.removeNull( dataCampionamento);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dataCampionamento: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idPropostaCertificaz);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idPropostaCertificaz: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idLineaDiIntervento);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idLineaDiIntervento: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idCategAnagrafica);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idCategAnagrafica: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idPeriodo);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idPeriodo: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( tipoControlli);
	    if (!DataFilter.isEmpty(temp)) sb.append(" tipoControlli: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List getPK() {
		java.util.List pk=new java.util.ArrayList();
		
			pk.add("idCampione");
		
	    return pk;
	}

}
