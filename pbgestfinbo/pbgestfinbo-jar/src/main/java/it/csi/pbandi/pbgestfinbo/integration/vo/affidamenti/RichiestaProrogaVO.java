/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.vo.affidamenti;

import java.math.BigDecimal;
import java.sql.Date;

public class RichiestaProrogaVO {
	
	private BigDecimal idRichiestaProroga; 
	private int idStatoProroga; 
	private String descStatoProroga; 
	private Date dataRichiesta; 
	private int numGiorniRichiesta; 
	private Date dataInserimento; 
	private String motivazione ;
	private BigDecimal idTarget; 
	private boolean isApprovata; 
	private int numGiorniApprov; 
	
	
	public int getNumGiorniApprov() {
		return numGiorniApprov;
	}
	public void setNumGiorniApprov(int numGiorniApprov) {
		this.numGiorniApprov = numGiorniApprov;
	}
	public boolean isApprovata() {
		return isApprovata;
	}
	public void setApprovata(boolean isApprovata) {
		this.isApprovata = isApprovata;
	}
	public BigDecimal getIdTarget() {
		return idTarget;
	}
	public void setIdTarget(BigDecimal idTarget) {
		this.idTarget = idTarget;
	}
	public BigDecimal getIdRichiestaProroga() {
		return idRichiestaProroga;
	}
	public void setIdRichiestaProroga(BigDecimal idRichiestaProroga) {
		this.idRichiestaProroga = idRichiestaProroga;
	}
	public int getIdStatoProroga() {
		return idStatoProroga;
	}
	public void setIdStatoProroga(int idStatoProroga) {
		this.idStatoProroga = idStatoProroga;
	}
	public String getDescStatoProroga() {
		return descStatoProroga;
	}
	public void setDescStatoProroga(String descStatoProroga) {
		this.descStatoProroga = descStatoProroga;
	}
	public Date getDataRichiesta() {
		return dataRichiesta;
	}
	public void setDataRichiesta(Date dataRichiesta) {
		this.dataRichiesta = dataRichiesta;
	}
	public int getNumGiorniRichiesta() {
		return numGiorniRichiesta;
	}
	public void setNumGiorniRichiesta(int numGiorniRichiesta) {
		this.numGiorniRichiesta = numGiorniRichiesta;
	}
	public Date getDataInserimento() {
		return dataInserimento;
	}
	public void setDataInserimento(Date dataInserimento) {
		this.dataInserimento = dataInserimento;
	}
	public String getMotivazione() {
		return motivazione;
	}
	public void setMotivazione(String motivazione) {
		this.motivazione = motivazione;
	} 

	
	

}
