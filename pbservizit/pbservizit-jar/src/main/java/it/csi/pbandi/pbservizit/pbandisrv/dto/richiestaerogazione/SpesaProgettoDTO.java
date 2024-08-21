/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.dto.richiestaerogazione;

public class SpesaProgettoDTO implements java.io.Serializable {
	static final long serialVersionUID = 1;
	private Double importoAmmessoContributo;
	private Double totaleSpesaSostenuta;
	private Double avanzamentoSpesaSostenuta;
	private Double importoSpesaDaRaggiungere;
	private Double avanzamentoSpesaValidata;
	private Double totaleSpesaValidata;
	private Double avanzamentoSpesaPrevistaBando;
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
	public Double getImportoSpesaDaRaggiungere() {
		return importoSpesaDaRaggiungere;
	}
	public void setImportoSpesaDaRaggiungere(Double importoSpesaDaRaggiungere) {
		this.importoSpesaDaRaggiungere = importoSpesaDaRaggiungere;
	}
	public Double getAvanzamentoSpesaValidata() {
		return avanzamentoSpesaValidata;
	}
	public void setAvanzamentoSpesaValidata(Double avanzamentoSpesaValidata) {
		this.avanzamentoSpesaValidata = avanzamentoSpesaValidata;
	}
	public Double getTotaleSpesaValidata() {
		return totaleSpesaValidata;
	}
	public void setTotaleSpesaValidata(Double totaleSpesaValidata) {
		this.totaleSpesaValidata = totaleSpesaValidata;
	}
	public Double getAvanzamentoSpesaPrevistaBando() {
		return avanzamentoSpesaPrevistaBando;
	}
	public void setAvanzamentoSpesaPrevistaBando(Double avanzamentoSpesaPrevistaBando) {
		this.avanzamentoSpesaPrevistaBando = avanzamentoSpesaPrevistaBando;
	}
	
	

}
