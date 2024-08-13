/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.gestionepagamenti;

import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.GenericVO;

public class PagamentoVoceDiSpesaVO extends GenericVO {
	static final long serialVersionUID = 1;
	
	private java.lang.Long idVoceDiSpesa = null;
	private java.lang.Long idQuotaParteDocSpesa = null;
	private java.lang.String voceDiSpesa = null;
	private java.lang.Long idRigoContoEconomico = null;
	private java.lang.Double importoRendicontato = null;
	private java.lang.Double totaleAltriPagamenti = null;
	private java.lang.Double importoVoceDiSpesaCorrente = null;
	private java.lang.Double importoQuietanzato = null;
	
	
	
	public java.lang.Double getImportoQuietanzato() {
		return importoQuietanzato;
	}
	public void setImportoQuietanzato(java.lang.Double importoQuietanzato) {
		this.importoQuietanzato = importoQuietanzato;
	}
	public void setIdVoceDiSpesa(java.lang.Long val) {
		idVoceDiSpesa = val;
	}
	public java.lang.Long getIdVoceDiSpesa() {
		return idVoceDiSpesa;
	}

	public void setIdQuotaParteDocSpesa(java.lang.Long val) {
		idQuotaParteDocSpesa = val;
	}
	public java.lang.Long getIdQuotaParteDocSpesa() {
		return idQuotaParteDocSpesa;
	}

	public void setVoceDiSpesa(java.lang.String val) {
		voceDiSpesa = val;
	}
	public java.lang.String getVoceDiSpesa() {
		return voceDiSpesa;
	}

	public void setIdRigoContoEconomico(java.lang.Long val) {
		idRigoContoEconomico = val;
	}
	public java.lang.Long getIdRigoContoEconomico() {
		return idRigoContoEconomico;
	}

	public void setImportoRendicontato(java.lang.Double val) {
		importoRendicontato = val;
	}
	public java.lang.Double getImportoRendicontato() {
		return importoRendicontato;
	}
	public java.lang.Double getTotaleAltriPagamenti() {
		return totaleAltriPagamenti;
	}
	public void setTotaleAltriPagamenti(java.lang.Double totaleAltriPagamenti) {
		this.totaleAltriPagamenti = totaleAltriPagamenti;
	}
	public java.lang.Double getImportoVoceDiSpesaCorrente() {
		return importoVoceDiSpesaCorrente;
	}
	public void setImportoVoceDiSpesaCorrente(
			java.lang.Double importoVoceDiSpesaCorrente) {
		this.importoVoceDiSpesaCorrente = importoVoceDiSpesaCorrente;
	}

	
}
