/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.vo;

import java.math.BigDecimal;
import java.sql.Date;

public class PassaggioPerditaVO {
	
	private	Long idPassaggioPerdita; 						//	ID_PASSAGGIO_PERDITA
	private Long idProgetto; 								//	ID_PROGETTO
	private Date dataPassaggioPerdita; 						//	DT_PASSAGGIO_PERDITA
	private BigDecimal impPerditaComplessiva;						//	IMP_PERDITA_COMPLESSIVA
	private BigDecimal impPerditaCapitale;						//	IMP_PERDITA_CAPITALE
	private BigDecimal impPerditaInterressi; 						//	IMP_PERDITA_INTERESSI
	private BigDecimal impPerditaAgevolaz; 						//	IMP_PERDITA_AGEVOLAZ
	private BigDecimal impPerditaMora;							//	IMP_PERDITA_MORA 
	private String note; 									//	NOTE
	private Date dataInizioValidita; 						//	DT_INIZIO_VALIDITA
	private Date dataFineValidita;							//	Dt_fine_validità
	private Long idUtenteIns;								//	Id_utente_ins
	private Long idUtenteAgg;								//	Id_utente_agg
	private Date dataInserimento; 							//	Dt_inserimento
	private Date dataAggiornamento; 						//	Dt_aggiornamento
	
	
	public Long getIdPassaggioPerdita() {
		return idPassaggioPerdita;
	}
	public void setIdPassaggioPerdita(Long idPassaggioPerdita) {
		this.idPassaggioPerdita = idPassaggioPerdita;
	}
	public Long getIdProgetto() {
		return idProgetto;
	}
	public void setIdProgetto(Long idProgetto) {
		this.idProgetto = idProgetto;
	}
	public Date getDataPassaggioPerdita() {
		return dataPassaggioPerdita;
	}
	public void setDataPassaggioPerdita(Date dataPassaggioPerdita) {
		this.dataPassaggioPerdita = dataPassaggioPerdita;
	}
	
	public BigDecimal getImpPerditaComplessiva() {
		return impPerditaComplessiva;
	}
	public void setImpPerditaComplessiva(BigDecimal impPerditaComplessiva) {
		this.impPerditaComplessiva = impPerditaComplessiva;
	}
	public BigDecimal getImpPerditaCapitale() {
		return impPerditaCapitale;
	}
	public void setImpPerditaCapitale(BigDecimal impPerditaCapitale) {
		this.impPerditaCapitale = impPerditaCapitale;
	}
	public BigDecimal getImpPerditaInterressi() {
		return impPerditaInterressi;
	}
	public void setImpPerditaInterressi(BigDecimal impPerditaInterressi) {
		this.impPerditaInterressi = impPerditaInterressi;
	}
	public BigDecimal getImpPerditaAgevolaz() {
		return impPerditaAgevolaz;
	}
	public void setImpPerditaAgevolaz(BigDecimal impPerditaAgevolaz) {
		this.impPerditaAgevolaz = impPerditaAgevolaz;
	}
	public BigDecimal getImpPerditaMora() {
		return impPerditaMora;
	}
	public void setImpPerditaMora(BigDecimal impPerditaMora) {
		this.impPerditaMora = impPerditaMora;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public Date getDataInizioValidita() {
		return dataInizioValidita;
	}
	public void setDataInizioValidita(Date dataInizioValidita) {
		this.dataInizioValidita = dataInizioValidita;
	}
	public Date getDataFineValidita() {
		return dataFineValidita;
	}
	public void setDataFineValidita(Date dataFineValidita) {
		this.dataFineValidita = dataFineValidita;
	}
	public Long getIdUtenteIns() {
		return idUtenteIns;
	}
	public void setIdUtenteIns(Long idUtenteIns) {
		this.idUtenteIns = idUtenteIns;
	}
	public Long getIdUtenteAgg() {
		return idUtenteAgg;
	}
	public void setIdUtenteAgg(Long idUtenteAgg) {
		this.idUtenteAgg = idUtenteAgg;
	}
	public Date getDataInserimento() {
		return dataInserimento;
	}
	public void setDataInserimento(Date dataInserimento) {
		this.dataInserimento = dataInserimento;
	}
	public Date getDataAggiornamento() {
		return dataAggiornamento;
	}
	public void setDataAggiornamento(Date dataAggiornamento) {
		this.dataAggiornamento = dataAggiornamento;
	}
	
	

}
