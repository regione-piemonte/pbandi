/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.integration.services.bilancio.vo;

import java.math.BigDecimal;


public class InserisciAttoLiquidazioneVO extends BaseVO {

	private Integer maxRec;
	
	AttoLiquidazioneVO attoLiquidazione;
	
	private ImpegnoVO[] impegni;
	
	private RitenutaAttoNewVO ritenutaAttoNew;
	
	// CDU-110-V03 inizio
	private String esitoContabilia;
	private String erroreContabilia;
	private String idOperazioneAsincrona;
	private Long idAttoLiquidazione;

	public Long getIdAttoLiquidazione() {
		return idAttoLiquidazione;
	}

	public void setIdAttoLiquidazione(Long idAttoLiquidazione) {
		this.idAttoLiquidazione = idAttoLiquidazione;
	}

	public String getEsitoContabilia() {
		return esitoContabilia;
	}

	public void setEsitoContabilia(String esitoContabilia) {
		this.esitoContabilia = esitoContabilia;
	}

	public String getErroreContabilia() {
		return erroreContabilia;
	}

	public void setErroreContabilia(String erroreContabilia) {
		this.erroreContabilia = erroreContabilia;
	}

	public String getIdOperazioneAsincrona() {
		return idOperazioneAsincrona;
	}

	public void setIdOperazioneAsincrona(String idOperazioneAsincrona) {
		this.idOperazioneAsincrona = idOperazioneAsincrona;
	}
	
	// CDU-110-V03 fine

	public AttoLiquidazioneVO getAttoLiquidazione() {
		return attoLiquidazione;
	}

	public void setAttoLiquidazione(AttoLiquidazioneVO attoLiquidazione) {
		this.attoLiquidazione = attoLiquidazione;
	}

	public ImpegnoVO[] getImpegni() {
		return impegni;
	}

	public void setImpegni(ImpegnoVO[] impegni) {
		this.impegni = impegni;
	}


	public Integer getMaxRec() {
		return maxRec;
	}

	public void setMaxRec(Integer maxRec) {
		this.maxRec = maxRec;
	}

	public RitenutaAttoNewVO getRitenutaAttoNew() {
		return ritenutaAttoNew;
	}

	public void setRitenutaAttoNew(RitenutaAttoNewVO ritenutaAttoNew) {
		this.ritenutaAttoNew = ritenutaAttoNew;
	}

}
