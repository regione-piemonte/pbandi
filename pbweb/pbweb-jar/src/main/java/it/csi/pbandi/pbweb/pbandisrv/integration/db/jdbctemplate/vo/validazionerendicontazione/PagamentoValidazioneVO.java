/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.validazionerendicontazione;

import java.util.List;

import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.GenericVO;

public class PagamentoValidazioneVO extends GenericVO{

	private static final long serialVersionUID = -91888291378092535L;
	

	private java.lang.String descModalitaPagamento = null;

	private java.lang.Long idModalitaPagamento = null;

	private java.lang.Long idPagamento = null;

	private java.lang.Double importoTotale = null;

	private java.util.Date dtPagamento = null;

	private java.util.Date dtValuta = null;

	private it.csi.pbandi.pbweb.pbandisrv.dto.validazionerendicontazione.VoceDiSpesaDTO[] vociDiSpesa = null;

	private java.lang.Long idDichiarazioneDiSpesa = null;
	
	//private java.lang.Long idStatoValidazione = null;


	
	//private java.lang.String descStatoPagamento = null;


	//private java.lang.String descBreveStatoPagamento = null;


	/*
	public java.lang.Long getIdStatoValidazione() {
		return idStatoValidazione;
	}

	public void setIdStatoValidazione(java.lang.Long idStatoValidazione) {
		this.idStatoValidazione = idStatoValidazione;
	}
	*/

	public void setDescModalitaPagamento(java.lang.String val) {
		descModalitaPagamento = val;
	}

	public java.lang.String getDescModalitaPagamento() {
		return descModalitaPagamento;
	}


	public void setIdModalitaPagamento(java.lang.Long val) {
		idModalitaPagamento = val;
	}

	public java.lang.Long getIdModalitaPagamento() {
		return idModalitaPagamento;
	}


	public void setIdPagamento(java.lang.Long val) {
		idPagamento = val;
	}

	public java.lang.Long getIdPagamento() {
		return idPagamento;
	}


	public void setImportoTotale(java.lang.Double val) {
		importoTotale = val;
	}

	public java.lang.Double getImportoTotale() {
		return importoTotale;
	}


	public void setDtPagamento(java.util.Date val) {
		dtPagamento = val;
	}

	public java.util.Date getDtPagamento() {
		return dtPagamento;
	}


	public void setDtValuta(java.util.Date val) {
		dtValuta = val;
	}

	public java.util.Date getDtValuta() {
		return dtValuta;
	}

	
	public void setVociDiSpesa(
			it.csi.pbandi.pbweb.pbandisrv.dto.validazionerendicontazione.VoceDiSpesaDTO[] val) {
		vociDiSpesa = val;
	}

	public it.csi.pbandi.pbweb.pbandisrv.dto.validazionerendicontazione.VoceDiSpesaDTO[] getVociDiSpesa() {
		return vociDiSpesa;
	}


	public void setIdDichiarazioneDiSpesa(java.lang.Long val) {
		idDichiarazioneDiSpesa = val;
	}

	public java.lang.Long getIdDichiarazioneDiSpesa() {
		return idDichiarazioneDiSpesa;
	}
	

	/*
	public void setDescStatoPagamento(java.lang.String val) {
		descStatoPagamento = val;
	}

	public java.lang.String getDescStatoPagamento() {
		return descStatoPagamento;
	}


	public void setDescBreveStatoPagamento(java.lang.String val) {
		descBreveStatoPagamento = val;
	}

	public java.lang.String getDescBreveStatoPagamento() {
		return descBreveStatoPagamento;
	}
	*/


	
}
