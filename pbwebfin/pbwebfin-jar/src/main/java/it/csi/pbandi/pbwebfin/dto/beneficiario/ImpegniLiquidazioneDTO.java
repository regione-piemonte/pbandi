/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebfin.dto.beneficiario;

import java.io.Serializable;
import java.util.Arrays;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import it.csi.pbandi.pbservizit.pbandisrv.dto.liquidazionebilancio.LiquidazioneDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.liquidazionebilancio.RipartizioneImpegniLiquidazioneDTO;

public class ImpegniLiquidazioneDTO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private LiquidazioneDTO liquidazione;
	private RipartizioneImpegniLiquidazioneDTO[] ripartizioneImpegniLiquidazioneDTO;
	
	public LiquidazioneDTO getLiquidazione() {
		return liquidazione;
	}
	public void setLiquidazione(LiquidazioneDTO liquidazione) {
		this.liquidazione = liquidazione;
	}
	public RipartizioneImpegniLiquidazioneDTO[] getRipartizioneImpegniLiquidazioneDTO() {
		return ripartizioneImpegniLiquidazioneDTO;
	}
	public void setRipartizioneImpegniLiquidazioneDTO(RipartizioneImpegniLiquidazioneDTO[] ripartizioneImpegniLiquidazioneDTO) {
		this.ripartizioneImpegniLiquidazioneDTO = ripartizioneImpegniLiquidazioneDTO;
	}
	@Override
	public String toString() {
		ReflectionToStringBuilder builder = new ReflectionToStringBuilder(this);
		builder.append("liquidazione", liquidazione);
		builder.append("ripartizioneImpegniLiquidazioneDTO", Arrays.toString(ripartizioneImpegniLiquidazioneDTO));
		return builder.toString();
	}
	
	

}
