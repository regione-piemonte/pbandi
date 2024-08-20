/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebfin.dto.impegni;

import java.io.Serializable;
import java.util.Arrays;

public class GestioneImpegniDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long[] idImpegniSelizioanti;
	private Long[] bandoLineaSelezionati;
	private Long[] idProgettiSelezionati;
	
	public Long[] getIdImpegniSelizioanti() {
		return idImpegniSelizioanti;
	}
	public void setIdImpegniSelizioanti(Long[] idImpegniSelizioanti) {
		this.idImpegniSelizioanti = idImpegniSelizioanti;
	}
	public Long[] getBandoLineaSelezionati() {
		return bandoLineaSelezionati;
	}
	public void setBandoLineaSelezionati(Long[] bandoLineaSelezionati) {
		this.bandoLineaSelezionati = bandoLineaSelezionati;
	}
	public Long[] getIdProgettiSelezionati() {
		return idProgettiSelezionati;
	}
	public void setIdProgettiSelezionati(Long[] idProgettiSelezionati) {
		this.idProgettiSelezionati = idProgettiSelezionati;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("GestioneImpegniDTO [idImpegniSelizioanti=");
		builder.append(Arrays.toString(idImpegniSelizioanti));
		builder.append(", bandoLineaSelezionati=");
		builder.append(Arrays.toString(bandoLineaSelezionati));
		builder.append(", idProgettiSelezionati=");
		builder.append(Arrays.toString(idProgettiSelezionati));
		builder.append("]");
		return builder.toString();
	}

}
