/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.vo.search;

public class DettaglioImpresaVO {
	
	private String annoRiferimento;
	private String unitaLavorativeAnnue;
	private String fatturato;
	private String totaleBilancioAnnuale;
	
	
	public DettaglioImpresaVO(String annoRiferimento, String unitaLavorativeAnnue, String fatturato,
			String totaleBilancioAnnuale) {
		this.annoRiferimento = annoRiferimento;
		this.unitaLavorativeAnnue = unitaLavorativeAnnue;
		this.fatturato = fatturato;
		this.totaleBilancioAnnuale = totaleBilancioAnnuale;
	}


	public DettaglioImpresaVO() {
	}


	public String getAnnoRiferimento() {
		return annoRiferimento;
	}


	public void setAnnoRiferimento(String annoRiferimento) {
		this.annoRiferimento = annoRiferimento;
	}


	public String getUnitaLavorativeAnnue() {
		return unitaLavorativeAnnue;
	}


	public void setUnitaLavorativeAnnue(String unitaLavorativeAnnue) {
		this.unitaLavorativeAnnue = unitaLavorativeAnnue;
	}


	public String getFatturato() {
		return fatturato;
	}


	public void setFatturato(String fatturato) {
		this.fatturato = fatturato;
	}


	public String getTotaleBilancioAnnuale() {
		return totaleBilancioAnnuale;
	}


	public void setTotaleBilancioAnnuale(String totaleBilancioAnnuale) {
		this.totaleBilancioAnnuale = totaleBilancioAnnuale;
	}


	@Override
	public String toString() {
		return "DettaglioImpresaVO [annoRiferimento=" + annoRiferimento + ", unitaLavorativeAnnue="
				+ unitaLavorativeAnnue + ", fatturato=" + fatturato + ", totaleBilancioAnnuale=" + totaleBilancioAnnuale
				+ "]";
	}
	
	

}
