/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweberog.integration.request;

import java.io.Serializable;

public class ControllaDatiRequest implements Serializable{
	
	 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private double importoCalcolato;  //Erogazione -> spesa progetto -> importoAmmessoAContributo
	 private double importoResiduoSpettante; // DatiCalcolati - > ImportoResiduoSpettante
	 private  double importoErogazioneEffettiva; // DatiCalcolati - > ImportoTotaleErogato
	 private String codCausaleErogSel;
	 
	 private Double percErogazione;
	 private Double percLimite;
	 
	public double getImportoCalcolato() {
		return importoCalcolato;
	}
	public void setImportoCalcolato(double importoCalcolato) {
		this.importoCalcolato = importoCalcolato;
	}
	public double getImportoResiduoSpettante() {
		return importoResiduoSpettante;
	}
	public void setImportoResiduoSpettante(double importoResiduoSpettante) {
		this.importoResiduoSpettante = importoResiduoSpettante;
	}
	public double getImportoErogazioneEffettiva() {
		return importoErogazioneEffettiva;
	}
	public void setImportoErogazioneEffettiva(double importoErogazioneEffettiva) {
		this.importoErogazioneEffettiva = importoErogazioneEffettiva;
	}
	public String getCodCausaleErogSel() {
		return codCausaleErogSel;
	}
	public void setCodCausaleErogSel(String codCausaleErogSel) {
		this.codCausaleErogSel = codCausaleErogSel;
	}
	public Double getPercErogazione() {
		return percErogazione;
	}
	public void setPercErogazione(Double percErogazione) {
		this.percErogazione = percErogazione;
	}
	public Double getPercLimite() {
		return percLimite;
	}
	public void setPercLimite(Double percLimite) {
		this.percLimite = percLimite;
	}
	 
	 
	 
}
