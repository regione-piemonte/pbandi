/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebbo.dto.configuraBando;

import java.io.Serializable;

public class DataModalitaAgevolazioneDTO implements Serializable {
	
	
	private Long idBando;
	private Long idModalitaAgevolazione;
	private String descModalitaAgevolazione;
	private Integer percentualeImportoAgevolato;
	private Integer minimoImportoAgevolato;
	private Integer massimoImportoAgevolato;
	private Integer periodoStabilita;
	private Integer percentualeImportoDaErogare;
	private String flagLiquidazione;
	
	
	public Long getIdBando() {
		return idBando;
	}
	public void setIdBando(Long idBando) {
		this.idBando = idBando;
	}
	public Long getIdModalitaAgevolazione() {
		return idModalitaAgevolazione;
	}
	public void setIdModalitaAgevolazione(Long idModalitaAgevolazione) {
		this.idModalitaAgevolazione = idModalitaAgevolazione;
	}
	public String getDescModalitaAgevolazione() {
		return descModalitaAgevolazione;
	}
	public void setDescModalitaAgevolazione(String descModalitaAgevolazione) {
		this.descModalitaAgevolazione = descModalitaAgevolazione;
	}
	public Integer getPercentualeImportoAgevolato() {
		return percentualeImportoAgevolato;
	}
	public void setPercentualeImportoAgevolato(Integer percentualeImportoAgevolato) {
		this.percentualeImportoAgevolato = percentualeImportoAgevolato;
	}
	public Integer getMinimoImportoAgevolato() {
		return minimoImportoAgevolato;
	}
	public void setMinimoImportoAgevolato(Integer minimoImportoAgevolato) {
		this.minimoImportoAgevolato = minimoImportoAgevolato;
	}
	public Integer getMassimoImportoAgevolato() {
		return massimoImportoAgevolato;
	}
	public void setMassimoImportoAgevolato(Integer massimoImportoAgevolato) {
		this.massimoImportoAgevolato = massimoImportoAgevolato;
	}
	public Integer getPeriodoStabilita() {
		return periodoStabilita;
	}
	public void setPeriodoStabilita(Integer periodoStabilita) {
		this.periodoStabilita = periodoStabilita;
	}
	public Integer getPercentualeImportoDaErogare() {
		return percentualeImportoDaErogare;
	}
	public void setPercentualeImportoDaErogare(Integer percentualeImportoDaErogare) {
		this.percentualeImportoDaErogare = percentualeImportoDaErogare;
	}
	public String getFlagLiquidazione() {
		return flagLiquidazione;
	}
	public void setFlagLiquidazione(String flagLiquidazione) {
		this.flagLiquidazione = flagLiquidazione;
	}
	
	
	
	

}
