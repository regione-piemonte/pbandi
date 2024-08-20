/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebfin.dto.beneficiario;

import java.io.Serializable;
import java.util.Arrays;

import it.csi.pbandi.pbservizit.pbandisrv.dto.liquidazionebilancio.AliquotaRitenutaAttoDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.liquidazionebilancio.EsitoRitenuteDTO;

public class AliquotaRienutaDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private AliquotaRitenutaAttoDTO[] aliquotaRitenutaAttoDTO;
	private EsitoRitenuteDTO esitoRitenuteDTO;
	
	public AliquotaRitenutaAttoDTO[] getAliquotaRitenutaAttoDTO() {
		return aliquotaRitenutaAttoDTO;
	}
	public void setAliquotaRitenutaAttoDTO(AliquotaRitenutaAttoDTO[] aliquotaRitenutaAttoDTO) {
		this.aliquotaRitenutaAttoDTO = aliquotaRitenutaAttoDTO;
	}
	public EsitoRitenuteDTO getEsitoRitenuteDTO() {
		return esitoRitenuteDTO;
	}
	public void setEsitoRitenuteDTO(EsitoRitenuteDTO esitoRitenuteDTO) {
		this.esitoRitenuteDTO = esitoRitenuteDTO;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("AliquotaRienutaDTO [aliquotaRitenutaAttoDTO=");
		builder.append(Arrays.toString(aliquotaRitenutaAttoDTO));
		builder.append(", esitoRitenuteDTO=");
		builder.append(esitoRitenuteDTO);
		builder.append("]");
		return builder.toString();
	}
	
}
