/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebrce.dto.quadroprevisionale;

public class QuadroPrevisionale implements java.io.Serializable {

	/// Field [isVociVisibili]
	private boolean isVociVisibili = false;

	public void setIsVociVisibili(boolean val) {
		isVociVisibili = val;
	}

	public boolean getIsVociVisibili() {
		return isVociVisibili;
	}

	/// Field [idQuadro]
	private Long idQuadro = null;

	public void setIdQuadro(Long val) {
		idQuadro = val;
	}

	public Long getIdQuadro() {
		return idQuadro;
	}

	/// Field [controlloNuovoImportoBloccante]
	private boolean controlloNuovoImportoBloccante = false;

	public void setControlloNuovoImportoBloccante(boolean val) {
		controlloNuovoImportoBloccante = val;
	}

	public boolean getControlloNuovoImportoBloccante() {
		return controlloNuovoImportoBloccante;
	}

	// il serial version uid e' fisso in quanto la classe in oggetto e' serializzabile
	// solo per la clusterizzazione della sessione web e non viene scambiata con altre
	// componenti.
	private static final long serialVersionUID = 1L;

	/**
	 * Costruttore vuoto del DTO.
	 */
	public QuadroPrevisionale() {
		super();

	}

	public String toString() {
		/*PROTECTED REGION ID(R1479867615) ENABLED START*/
		/// inserire qui la logica desiderata per la rappresenatazione a stringa
		return super.toString();
		/*PROTECTED REGION END*/
	}
}
