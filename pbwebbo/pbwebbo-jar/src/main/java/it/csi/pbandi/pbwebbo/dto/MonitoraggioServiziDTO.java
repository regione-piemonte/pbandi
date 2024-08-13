/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebbo.dto;

import java.util.Date;

public class MonitoraggioServiziDTO {
	public String esito;
	public String codiceErrore;
	public String messaggioErrore;
	public Date dataInizioChiamata;
	public Date dataFineChiamata;
	public String nomeServizio;
	public String categoriaServizio;
	
	public String getEsito() {
		return esito;
	}
	public void setEsito(String esito) {
		this.esito = esito;
	}
	public String getCodiceErrore() {
		return codiceErrore;
	}
	public void setCodiceErrore(String codiceErrore) {
		this.codiceErrore = codiceErrore;
	}
	public String getMessaggioErrore() {
		return messaggioErrore;
	}
	public void setMessaggioErrore(String messaggioErrore) {
		this.messaggioErrore = messaggioErrore;
	}
	public Date getDataInizioChiamata() {
		return dataInizioChiamata;
	}
	public void setDataInizioChiamata(Date dataInizioChiamata) {
		this.dataInizioChiamata = dataInizioChiamata;
	}
	public Date getDataFineChiamata() {
		return dataFineChiamata;
	}
	public void setDataFineChiamata(Date dataFineChiamata) {
		this.dataFineChiamata = dataFineChiamata;
	}
	public String getNomeServizio() {
		return nomeServizio;
	}
	public void setNomeServizio(String nomeServizio) {
		this.nomeServizio = nomeServizio;
	}
	public String getCategoriaServizio() {
		return categoriaServizio;
	}
	public void setCategoriaServizio(String categoriaServizio) {
		this.categoriaServizio = categoriaServizio;
	}

	
	
	
	

}
