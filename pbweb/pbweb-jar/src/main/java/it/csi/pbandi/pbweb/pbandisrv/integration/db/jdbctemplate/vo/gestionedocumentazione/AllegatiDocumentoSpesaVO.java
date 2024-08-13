/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.gestionedocumentazione;

import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.GenericVO;

import java.math.BigDecimal;

public class AllegatiDocumentoSpesaVO extends GenericVO{
	
	private BigDecimal idProgetto;
	private BigDecimal idDichiarazioneSpesa;
	private String nomeFile;
	private BigDecimal idDocumentoIndex;
	private String uuidNodo;
	
	public BigDecimal getIdProgetto() {
		return idProgetto;
	}
	public void setIdProgetto(BigDecimal idProgetto) {
		this.idProgetto = idProgetto;
	}
	public BigDecimal getIdDichiarazioneSpesa() {
		return idDichiarazioneSpesa;
	}
	public void setIdDichiarazioneSpesa(BigDecimal idDichiarazioneSpesa) {
		this.idDichiarazioneSpesa = idDichiarazioneSpesa;
	}
	public String getNomeFile() {
		return nomeFile;
	}
	public void setNomeFile(String nomeFile) {
		this.nomeFile = nomeFile;
	}
	public BigDecimal getIdDocumentoIndex() {
		return idDocumentoIndex;
	}
	public void setIdDocumentoIndex(BigDecimal idDocumentoIndex) {
		this.idDocumentoIndex = idDocumentoIndex;
	}
	public String getUuidNodo() {
		return uuidNodo;
	}
	public void setUuidNodo(String uuidNodo) {
		this.uuidNodo = uuidNodo;
	}
}
