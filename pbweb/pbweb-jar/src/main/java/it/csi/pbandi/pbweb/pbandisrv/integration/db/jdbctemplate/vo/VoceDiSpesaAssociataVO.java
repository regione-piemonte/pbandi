/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo;

import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiRBandoVoceSpesaVO;

public class VoceDiSpesaAssociataVO extends PbandiRBandoVoceSpesaVO{

	private String codTipoVoceDiSpesa;
	private String descVoceDiSpesaComposta;
	private String livello;
	private String descTipologiaVoceDiSpesa;
	private String flagEdit;
	
	public String getDescTipologiaVoceDiSpesa() {
		return descTipologiaVoceDiSpesa;
	}
	public void setDescTipologiaVoceDiSpesa(String descTipologiaVoceDiSpesa) {
		this.descTipologiaVoceDiSpesa = descTipologiaVoceDiSpesa;
	}
	public String getFlagEdit() {
		return flagEdit;
	}
	public void setFlagEdit(String flagEdit) {
		this.flagEdit = flagEdit;
	}
	public String getLivello() {
		return livello;
	}
	public void setLivello(String livello) {
		this.livello = livello;
	}
	public void setCodTipoVoceDiSpesa(String codTipoVoceDiSpesa) {
		this.codTipoVoceDiSpesa = codTipoVoceDiSpesa;
	}
	public String getCodTipoVoceDiSpesa() {
		return codTipoVoceDiSpesa;
	}
	public String getDescVoceDiSpesaComposta() {
		return descVoceDiSpesaComposta;
	}
	public void setDescVoceDiSpesaComposta(String descVoceDiSpesaComposta) {
		this.descVoceDiSpesaComposta = descVoceDiSpesaComposta;
	}
}
