/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.vo.search;

import java.util.Date;

public class AnagBenAltriDati_DurcVO {
	
	private Date dataEmiss;
	private String esito;
	private Date dataScadenza;
	private String numProto;
	
	
	public AnagBenAltriDati_DurcVO(Date dataEmiss, String esito, Date dataScadenza, String numProto) {
		this.dataEmiss = dataEmiss;
		this.esito = esito;
		this.dataScadenza = dataScadenza;
		this.numProto = numProto;
	}


	public AnagBenAltriDati_DurcVO() {
	}


	public Date getDataEmiss() {
		return dataEmiss;
	}


	public void setDataEmiss(Date dataEmiss) {
		this.dataEmiss = dataEmiss;
	}


	public String getEsito() {
		return esito;
	}


	public void setEsito(String esito) {
		this.esito = esito;
	}


	public Date getDataScadenza() {
		return dataScadenza;
	}


	public void setDataScadenza(Date dataScadenza) {
		this.dataScadenza = dataScadenza;
	}


	public String getNumProto() {
		return numProto;
	}


	public void setNumProto(String numProto) {
		this.numProto = numProto;
	}


	@Override
	public String toString() {
		return "AnagBenAltriDati_DurcVO [dataEmiss=" + dataEmiss + ", esito=" + esito + ", dataScadenza=" + dataScadenza
				+ ", numProto=" + numProto + "]";
	}
	
	

}
