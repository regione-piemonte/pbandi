/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.vo;


import java.math.BigDecimal;
import java.util.Date;

public class VisualizzaStatoCreditoVO {
	
	private String statoCredito;
	private String descStato;
	private Date dtModifica;
	private String nome;
	private String cognome;
	//i sottostanti solo per l'update/insert
	private int idSoggetto;
	private int idProgetto;
	private int idUtente;
	private Date dtInserimento;
	private int idStatoCredito; 
	private BigDecimal progrStatoCredito; 
	
	public VisualizzaStatoCreditoVO() {
		super();
		//TODO Auto-generated constructor stub
	}


	public VisualizzaStatoCreditoVO(String statoCredito, String descStato, Date dtModifica, String nome,
			String cognome, int idSoggetto, int idProgetto, int idUtente, Date dtInserimento) {
		super();
		this.statoCredito = statoCredito;
		this.descStato = descStato;
		this.dtModifica = dtModifica;
		this.nome = nome;
		this.cognome = cognome;
		this.idSoggetto = idSoggetto;
		this.idProgetto = idProgetto;
		this.idUtente = idUtente;
	}


	public Date getDtInserimento() {
		return dtInserimento;
	}


	public void setDtInserimento(Date dtInserimento) {
		this.dtInserimento = dtInserimento;
	}


	public int getIdStatoCredito() {
		return idStatoCredito;
	}


	public void setIdStatoCredito(int idStatoCredito) {
		this.idStatoCredito = idStatoCredito;
	}


	public BigDecimal getProgrStatoCredito() {
		return progrStatoCredito;
	}


	public void setProgrStatoCredito(BigDecimal progrStatoCredito) {
		this.progrStatoCredito = progrStatoCredito;
	}


	public String getStatoCredito() {
		return statoCredito;
	}


	public void setStatoCredito(String statoCredito) {
		this.statoCredito = statoCredito;
	}


	public String getDescStato() {
		return descStato;
	}


	public void setDescStato(String descStato) {
		this.descStato = descStato;
	}


	public Date getDtModifica() {
		return dtModifica;
	}


	public void setDtModifica(Date dtModifica) {
		this.dtModifica = dtModifica;
	}


	public String getNome() {
		return nome;
	}


	public void setNome(String nome) {
		this.nome = nome;
	}


	public String getCognome() {
		return cognome;
	}


	public void setCognome(String cognome) {
		this.cognome = cognome;
	}
	
	
	public int getIdSoggetto() {
		return idSoggetto;
	}


	public void setIdSoggetto(int idSoggetto) {
		this.idSoggetto = idSoggetto;
	}

	public int getIdProgetto() {
		return idProgetto;
	}


	public void setIdProgetto(int idProgetto) {
		this.idProgetto = idProgetto;
	}

	public int getIdUtente() {
		return idUtente;
	}


	public void setIdUtente(int idUtente) {
		this.idUtente = idUtente;
	}
	
}	