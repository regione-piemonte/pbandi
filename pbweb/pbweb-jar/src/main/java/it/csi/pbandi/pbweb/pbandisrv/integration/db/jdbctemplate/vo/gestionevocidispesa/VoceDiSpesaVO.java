/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.gestionevocidispesa;

import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.GenericVO;


public class VoceDiSpesaVO extends GenericVO {
	static final long serialVersionUID = 1;

	private Long _idVoceDiSpesa = null;
	private Long _idVoceDiSpesaPadre;
	private Long _idRigoContoEconomico;
	private Long _idProgetto;  
	private Long _idQuotaParteDocSpesa;
	private Long _idDocSpesa;
	private Double _importo = null;
	private Double _importoRichiesto = null;
	private Double _importoAgevolato = null;
	private Double _importoFinanziamento= null;
	private Double _oreLavorate= null;
	private Double _costoOrario= null;
	private java.lang.String _descVoceDiSpesa = null;
	private java.lang.String _descVoceDiSpesaCompleta = null;
	private java.lang.Long _idTipoDocumentoDiSpesa = null;
	
	
	
	public void setCostoOrario(java.lang.Double val) {
		_costoOrario = val;
	}

	public java.lang.Double getCostoOrario() {
		return _costoOrario;
	}
	
	public void setOreLavorate(java.lang.Double val) {
		_oreLavorate = val;
	}

	public java.lang.Double getOreLavorate() {
		return _oreLavorate;
	}
	
	public void setIdVoceDiSpesa(java.lang.Long val) {
		_idVoceDiSpesa = val;
	}

	public java.lang.Long getIdVoceDiSpesa() {
		return _idVoceDiSpesa;
	}

	public void setDescVoceDiSpesa(java.lang.String val) {
		_descVoceDiSpesa = val;
	}

	
	public java.lang.String getDescVoceDiSpesa() {
		return _descVoceDiSpesa;
	}


	public void setImporto(java.lang.Double val) {
		_importo = val;
	}

	
	public java.lang.Double getImporto() {
		return _importo;
	}

	
	
	
	//////////////////
	

	
	public Long getIdRigoContoEconomico() {
		return _idRigoContoEconomico;
	}
	public void setIdRigoContoEconomico(Long idRigoContoEconomico) {
		this._idRigoContoEconomico = idRigoContoEconomico;
	}
	public Long getIdProgetto() {
		return _idProgetto;
	}
	public void setIdProgetto(Long idProgetto) {
		this._idProgetto = idProgetto;
	}
	public Long getIdQuotaParteDocSpesa() {
		return _idQuotaParteDocSpesa;
	}
	public void setIdQuotaParteDocSpesa(Long idQuotaParteDocSpesa) {
		this._idQuotaParteDocSpesa = idQuotaParteDocSpesa;
	}
	public Long getIdDocSpesa() {
		return _idDocSpesa;
	}
	public void setIdDocSpesa(Long idDocSpesa) {
		this._idDocSpesa = idDocSpesa;
	}

	public Double getImportoRichiesto() {
		return _importoRichiesto;
	}
	
	public void setImportoRichiesto(Double importoRichiesto) {
		this._importoRichiesto = importoRichiesto;
	}
	
	public Double getImportoAgevolato() {
		return _importoAgevolato;
	}
	public void setImportoAgevolato(Double importoAgevolato) {
		this._importoAgevolato = importoAgevolato;
	}
	public Double getImportoFinanziamento() {
		return _importoFinanziamento;
	}
	public void setImportoFinanziamento(Double importoFinanziamento) {
		this._importoFinanziamento = importoFinanziamento;
	}


	public void setDescVoceDiSpesaCompleta(java.lang.String _descVoceDiSpesaCompleta) {
		this._descVoceDiSpesaCompleta = _descVoceDiSpesaCompleta;
	}


	public java.lang.String getDescVoceDiSpesaCompleta() {
		return _descVoceDiSpesaCompleta;
	}


	public void setIdVoceDiSpesaPadre(Long _idVoceDiSpesaPadre) {
		this._idVoceDiSpesaPadre = _idVoceDiSpesaPadre;
	}


	public Long getIdVoceDiSpesaPadre() {
		return _idVoceDiSpesaPadre;
	}
	
	public void setIdTipoDocumentoDiSpesa(java.lang.Long val) {
		_idTipoDocumentoDiSpesa = val;
	}
	public java.lang.Long getIdTipoDocumentoDiSpesa() {
		return _idTipoDocumentoDiSpesa;
	}

}
