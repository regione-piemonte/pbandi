/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.vo.search;

import java.math.BigDecimal;
import java.sql.Date;

public class RicercaCampionamentiVO {
	
	private String descrizione; 
	private Date dataInizio; 
	private Date dataFine; 
	private BigDecimal idUtenteCampionamento; 
	private Long idTipologiaSelezione; 
	private Long idStatoCampionamento;
	
	public String getDescrizione() {
		return descrizione;
	}
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	public Date getDataInizio() {
		return dataInizio;
	}
	public void setDataInizio(Date dataInizio) {
		this.dataInizio = dataInizio;
	}
	public Date getDataFine() {
		return dataFine;
	}
	public void setDataFine(Date dataFine) {
		this.dataFine = dataFine;
	}
	public void setIdUtenteCampionamento(BigDecimal idUtenteCampionamento) {
		this.idUtenteCampionamento = idUtenteCampionamento;
	}
	public BigDecimal getIdUtenteCampionamento() {
		return idUtenteCampionamento;
	}
	public Long getIdTipologiaSelezione() {
		return idTipologiaSelezione;
	}
	public void setIdTipologiaSelezione(Long idTipologiaSelezione) {
		this.idTipologiaSelezione = idTipologiaSelezione;
	}
	public Long getIdStatoCampionamento() {
		return idStatoCampionamento;
	}
	public void setIdStatoCampionamento(Long idStatoCampionamento) {
		this.idStatoCampionamento = idStatoCampionamento;
	} 
	
	

}
