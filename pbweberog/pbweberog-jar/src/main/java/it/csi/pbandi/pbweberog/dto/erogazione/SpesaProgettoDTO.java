/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweberog.dto.erogazione;

public class SpesaProgettoDTO implements java.io.Serializable {

	static final long serialVersionUID = 1;

	private String tipoOperazione;

	private Double costoTotaleInvestimento;
	private Double importoAmmessoContributo;
	private Double totaleSpesaSostenuta;
	private Double avanzamentoSpesaSostenuta;
	private Double totaleSpesaValidata;
	private Double avanzamentoSpesaValidata;
	private Double totaleSpesaAmmessa;
	public String getTipoOperazione() {
		return tipoOperazione;
	}
	public void setTipoOperazione(String tipoOperazione) {
		this.tipoOperazione = tipoOperazione;
	}
	public Double getCostoTotaleInvestimento() {
		return costoTotaleInvestimento;
	}
	public void setCostoTotaleInvestimento(Double costoTotaleInvestimento) {
		this.costoTotaleInvestimento = costoTotaleInvestimento;
	}
	public Double getImportoAmmessoContributo() {
		return importoAmmessoContributo;
	}
	public void setImportoAmmessoContributo(Double importoAmmessoContributo) {
		this.importoAmmessoContributo = importoAmmessoContributo;
	}
	public Double getTotaleSpesaSostenuta() {
		return totaleSpesaSostenuta;
	}
	public void setTotaleSpesaSostenuta(Double totaleSpesaSostenuta) {
		this.totaleSpesaSostenuta = totaleSpesaSostenuta;
	}
	public Double getAvanzamentoSpesaSostenuta() {
		return avanzamentoSpesaSostenuta;
	}
	public void setAvanzamentoSpesaSostenuta(Double avanzamentoSpesaSostenuta) {
		this.avanzamentoSpesaSostenuta = avanzamentoSpesaSostenuta;
	}
	public Double getTotaleSpesaValidata() {
		return totaleSpesaValidata;
	}
	public void setTotaleSpesaValidata(Double totaleSpesaValidata) {
		this.totaleSpesaValidata = totaleSpesaValidata;
	}
	public Double getAvanzamentoSpesaValidata() {
		return avanzamentoSpesaValidata;
	}
	public void setAvanzamentoSpesaValidata(Double avanzamentoSpesaValidata) {
		this.avanzamentoSpesaValidata = avanzamentoSpesaValidata;
	}
	public Double getTotaleSpesaAmmessa() {
		return totaleSpesaAmmessa;
	}
	public void setTotaleSpesaAmmessa(Double totaleSpesaAmmessa) {
		this.totaleSpesaAmmessa = totaleSpesaAmmessa;
	}

	
	
}
