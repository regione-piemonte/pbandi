/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.contoeconomico;

public class ContoEconomicoVO {
	
	
	private Double _importoSpesaAmmessa = null;
	private Double _importoSpesaQuietanziata  = null;
	private Double _importoSpesaRendicontata  = null;
	private Double _importoSpesaValidataTotale  = null;
	private Double _percSpesaQuietanziataSuAmmessa  = null;
	private Double _percSpesaValidataSuAmmessa  = null;
	
	private String _label  = null;
	
	private ContoEconomicoVO [] _figli = null;
	
	private Long _idVoce = null;
	
	

	public Double getImportoSpesaAmmessa() {
		return _importoSpesaAmmessa;
	}

	public void setImportoSpesaAmmessa(Double importoSpesaAmmessa) {
		this._importoSpesaAmmessa = importoSpesaAmmessa;
	}

	public Double getImportoSpesaQuietanziata() {
		return _importoSpesaQuietanziata;
	}

	public void setImportoSpesaQuietanziata(Double importoSpesaQuietanziata) {
		this._importoSpesaQuietanziata = importoSpesaQuietanziata;
	}

	public Double getImportoSpesaRendicontata() {
		return _importoSpesaRendicontata;
	}

	public void setImportoSpesaRendicontata(Double importoSpesaRendicontata) {
		this._importoSpesaRendicontata = importoSpesaRendicontata;
	}

	public Double getImportoSpesaValidataTotale() {
		return _importoSpesaValidataTotale;
	}

	public void setImportoSpesaValidataTotale(Double importoSpesaValidataTotale) {
		this._importoSpesaValidataTotale = importoSpesaValidataTotale;
	}

	public Double getPercSpesaQuietanziataSuAmmessa() {
		return _percSpesaQuietanziataSuAmmessa;
	}

	public void setPercSpesaQuietanziataSuAmmessa(
			Double percentualeSpesaQuietanziataSuAmmessa) {
		this._percSpesaQuietanziataSuAmmessa = percentualeSpesaQuietanziataSuAmmessa;
	}

	public Double getPercSpesaValidataSuAmmessa() {
		return _percSpesaValidataSuAmmessa;
	}

	public void setPercSpesaValidataSuAmmessa(
			Double percentualeSpesaValidataSuAmmessa) {
		this._percSpesaValidataSuAmmessa = percentualeSpesaValidataSuAmmessa;
	}

	public String getLabel() {
		return _label;
	}

	public void setLabel(String label) {
		this._label = label;
	}

	public ContoEconomicoVO[] getFigli() {
		return _figli;
	}

	public void setFigli(ContoEconomicoVO[] figli) {
		this._figli = figli;
	}

	public Long getIdVoce() {
		return _idVoce;
	}

	public void setIdVoce(Long idVoce) {
		this._idVoce = idVoce;
	}
	
	
	
	

}
