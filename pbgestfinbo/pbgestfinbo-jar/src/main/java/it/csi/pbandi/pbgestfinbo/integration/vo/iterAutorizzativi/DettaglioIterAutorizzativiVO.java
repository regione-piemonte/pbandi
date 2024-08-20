/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.vo.iterAutorizzativi;

import java.math.BigDecimal;
import java.sql.Date;

public class DettaglioIterAutorizzativiVO {
	
	private BigDecimal idWorkFlow; 
	private BigDecimal idDettWorkflow ; 
	private String descIncarico; 
	private String nomeUtente;
	private String cognomeUtente;
	private Date dataRespingimeneto; 
	private Date dataApprovazione; 
	private String Motivazione;
	
	public BigDecimal getIdWorkFlow() {
		return idWorkFlow;
	}
	public void setIdWorkFlow(BigDecimal idWorkFlow) {
		this.idWorkFlow = idWorkFlow;
	}
	public BigDecimal getIdDettWorkflow() {
		return idDettWorkflow;
	}
	public void setIdDettWorkflow(BigDecimal idDettWorkflow) {
		this.idDettWorkflow = idDettWorkflow;
	}
	public String getDescIncarico() {
		return descIncarico;
	}
	public void setDescIncarico(String descIncarico) {
		this.descIncarico = descIncarico;
	}
	public String getNomeUtente() {
		return nomeUtente;
	}
	public void setNomeUtente(String nomeUtente) {
		this.nomeUtente = nomeUtente;
	}
	public String getCognomeUtente() {
		return cognomeUtente;
	}
	public void setCognomeUtente(String cognomeUtente) {
		this.cognomeUtente = cognomeUtente;
	}
	public Date getDataRespingimeneto() {
		return dataRespingimeneto;
	}
	public void setDataRespingimeneto(Date dataRespingimeneto) {
		this.dataRespingimeneto = dataRespingimeneto;
	}
	public Date getDataApprovazione() {
		return dataApprovazione;
	}
	public void setDataApprovazione(Date dataApprovazione) {
		this.dataApprovazione = dataApprovazione;
	}
	public String getMotivazione() {
		return Motivazione;
	}
	public void setMotivazione(String motivazione) {
		Motivazione = motivazione;
	} 
	
	 
}
