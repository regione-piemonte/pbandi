/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo;

import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiRRegolaTipoAnagVO;



public class RegolaAssociataAnagraficaVO extends PbandiRRegolaTipoAnagVO {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8948306641528545853L;
	private String descRegolaComposta;
	private String anagraficaBeneficiario;
	private String tipoAssociazione;
	
	public String getDescRegolaComposta() {
		return descRegolaComposta;
	}
	public void setDescRegolaComposta(String descRegolaComposta) {
		this.descRegolaComposta = descRegolaComposta;
	}
	public String getAnagraficaBeneficiario() {
		return anagraficaBeneficiario;
	}
	public void setAnagraficaBeneficiario(String anagraficaBeneficiario) {
		this.anagraficaBeneficiario = anagraficaBeneficiario;
	}
	public String getTipoAssociazione() {
		return tipoAssociazione;
	}
	public void setTipoAssociazione(String tipoAssociazione) {
		this.tipoAssociazione = tipoAssociazione;
	}

}
