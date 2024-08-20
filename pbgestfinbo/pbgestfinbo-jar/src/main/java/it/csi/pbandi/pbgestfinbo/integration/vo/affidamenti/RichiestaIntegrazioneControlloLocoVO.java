/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.vo.affidamenti;

import java.math.BigDecimal;
import java.sql.Date;

public class RichiestaIntegrazioneControlloLocoVO {
	
	  private Long idRichiestaIntegr;
	  private Long idTarget;// corrrisponde a idControllo
	  private Long idUtente;
	  private int numGiorniScadenza;
	  private String motivazione; 
	  private Date dataRichiesta; 
	  private Date dataNotifica;
	  private String descStatoRichiesta; 
	  private Long idEntita;
	  private Long idProgetto;
	  private  Long idStatoRichiesta;
	  private Date dataScadenza; 
	    
	  
	public Date getDataScadenza() {
		return dataScadenza;
	}
	public void setDataScadenza(Date dataScadenza) {
		this.dataScadenza = dataScadenza;
	}
	public Long getIdStatoRichiesta() {
		return idStatoRichiesta;
	}
	public void setIdStatoRichiesta(Long idStatoRichiesta) {
		this.idStatoRichiesta = idStatoRichiesta;
	}
	public Long getIdRichiestaIntegr() {
		return idRichiestaIntegr;
	}
	public void setIdRichiestaIntegr(Long idRichiestaIntegr) {
		this.idRichiestaIntegr = idRichiestaIntegr;
	}
	public Long getIdTarget() {
		return idTarget;
	}
	public void setIdTarget(Long idTarget) {
		this.idTarget = idTarget;
	}
	public Long getIdUtente() {
		return idUtente;
	}
	public void setIdUtente(Long idUtente) {
		this.idUtente = idUtente;
	}
	public int getNumGiorniScadenza() {
		return numGiorniScadenza;
	}
	public void setNumGiorniScadenza(int numGiorniScadenza) {
		this.numGiorniScadenza = numGiorniScadenza;
	}
	public String getMotivazione() {
		return motivazione;
	}
	public void setMotivazione(String motivazione) {
		this.motivazione = motivazione;
	}
	public Date getDataRichiesta() {
		return dataRichiesta;
	}
	public void setDataRichiesta(Date dataRichiesta) {
		this.dataRichiesta = dataRichiesta;
	}
	public Date getDataNotifica() {
		return dataNotifica;
	}
	public void setDataNotifica(Date dataNotifica) {
		this.dataNotifica = dataNotifica;
	}
	public String getDescStatoRichiesta() {
		return descStatoRichiesta;
	}
	public void setDescStatoRichiesta(String descStatoRichiesta) {
		this.descStatoRichiesta = descStatoRichiesta;
	}
	public Long getIdEntita() {
		return idEntita;
	}
	public void setIdEntita(Long idEntita) {
		this.idEntita = idEntita;
	}
	public Long getIdProgetto() {
		return idProgetto;
	}
	public void setIdProgetto(Long idProgetto) {
		this.idProgetto = idProgetto;
	}
	  
//	  private 
	  
//	  ID_RICHIESTA_INTEGRAZ
//	  DESCRIZIONE
//	  DT_RICHIESTA
//	  DT_NOTIFICA
//	  DT_SCADENZA
//	  DT_INVIO
//	  ID_STATO_RICHIESTA
//	  PBANDI_D_STATO_RICH_INTEGRAZ. DESC_STATO_RICHIESTA.

	  


	  
	  
	  

}
