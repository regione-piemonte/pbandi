/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweberog.dto.disimpegni;

public class DsDettaglioRevocaIrregolarita implements java.io.Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long idDettRevocaIrreg;
	private Long idDichiarazioneSpesa;
	private Double importoIrregolareDs;

	
	
	public Long getIdDettRevocaIrreg() {
		return idDettRevocaIrreg;
	}

	public void setIdDettRevocaIrreg(Long idDettRevocaIrreg) {
		this.idDettRevocaIrreg = idDettRevocaIrreg;
	}

	public Long getIdDichiarazioneSpesa() {
		return idDichiarazioneSpesa;
	}

	public void setIdDichiarazioneSpesa(Long idDichiarazioneSpesa) {
		this.idDichiarazioneSpesa = idDichiarazioneSpesa;
	}

	public Double getImportoIrregolareDs() {
		return importoIrregolareDs;
	}

	public void setImportoIrregolareDs(Double importoIrregolareDs) {
		this.importoIrregolareDs = importoIrregolareDs;
	}

	public DsDettaglioRevocaIrregolarita() {
		super();

	}

	public String toString() {
		return super.toString();
	}
}
