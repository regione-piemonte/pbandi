/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.vo.search;

import java.math.BigDecimal;
import java.sql.Date;

public class AltriDatiDsan {

	private Date dataEmissioneDsan; 
	private Date dataScadenza; 
	private String numeroProtocollo; 
	private String descEsitoRichieste;
	// dati del documento 
	private String nomeDocumento;
	private BigDecimal idDocumentoIndex; 
	private Long idTipoDocumentoIndex; 
	
	
	
	public String getNomeDocumento() {
		return nomeDocumento;
	}
	public void setNomeDocumento(String nomeDocumento) {
		this.nomeDocumento = nomeDocumento;
	}
	public BigDecimal getIdDocumentoIndex() {
		return idDocumentoIndex;
	}
	public void setIdDocumentoIndex(BigDecimal idDocumentoIndex) {
		this.idDocumentoIndex = idDocumentoIndex;
	}
	public Long getIdTipoDocumentoIndex() {
		return idTipoDocumentoIndex;
	}
	public void setIdTipoDocumentoIndex(Long idTipoDocumentoIndex) {
		this.idTipoDocumentoIndex = idTipoDocumentoIndex;
	}
	public Date getDataEmissioneDsan() {
		return dataEmissioneDsan;
	}
	public void setDataEmissioneDsan(Date dataEmissioneDsan) {
		this.dataEmissioneDsan = dataEmissioneDsan;
	}
	public Date getDataScadenza() {
		return dataScadenza;
	}
	public void setDataScadenza(Date dataScadenza) {
		this.dataScadenza = dataScadenza;
	}
	public String getNumeroProtocollo() {
		return numeroProtocollo;
	}
	public void setNumeroProtocollo(String numeroProtocollo) {
		this.numeroProtocollo = numeroProtocollo;
	}
	public String getDescEsitoRichieste() {
		return descEsitoRichieste;
	}
	public void setDescEsitoRichieste(String descEsitoRichieste) {
		this.descEsitoRichieste = descEsitoRichieste;
	} 
	
	
}
