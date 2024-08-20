/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebrce.dto.quadroprevisionale;

public class EsitoFindQuadroPrevisionaleDTO implements java.io.Serializable {

	static final long serialVersionUID = 1;
	private boolean isVociVisibili = true;
	private QuadroPrevisionaleDTO quadroPrevisionale = null;

	private java.util.Date dataUltimoPreventivo = null;
	private java.util.Date dataUltimaSpesaAmmessa = null;
	private QuadroPrevisionaleComplessivoDTO[] quadroPrevisionaleComplessivo = null;
	private boolean isControlloNuovoImportoBloccante = true;
	public boolean isVociVisibili() {
		return isVociVisibili;
	}
	public void setVociVisibili(boolean isVociVisibili) {
		this.isVociVisibili = isVociVisibili;
	}
	public QuadroPrevisionaleDTO getQuadroPrevisionale() {
		return quadroPrevisionale;
	}
	public void setQuadroPrevisionale(QuadroPrevisionaleDTO quadroPrevisionale) {
		this.quadroPrevisionale = quadroPrevisionale;
	}
	public java.util.Date getDataUltimoPreventivo() {
		return dataUltimoPreventivo;
	}
	public void setDataUltimoPreventivo(java.util.Date dataUltimoPreventivo) {
		this.dataUltimoPreventivo = dataUltimoPreventivo;
	}
	public java.util.Date getDataUltimaSpesaAmmessa() {
		return dataUltimaSpesaAmmessa;
	}
	public void setDataUltimaSpesaAmmessa(java.util.Date dataUltimaSpesaAmmessa) {
		this.dataUltimaSpesaAmmessa = dataUltimaSpesaAmmessa;
	}
	public QuadroPrevisionaleComplessivoDTO[] getQuadroPrevisionaleComplessivo() {
		return quadroPrevisionaleComplessivo;
	}
	public void setQuadroPrevisionaleComplessivo(QuadroPrevisionaleComplessivoDTO[] quadroPrevisionaleComplessivo) {
		this.quadroPrevisionaleComplessivo = quadroPrevisionaleComplessivo;
	}
	public boolean isControlloNuovoImportoBloccante() {
		return isControlloNuovoImportoBloccante;
	}
	public void setControlloNuovoImportoBloccante(boolean isControlloNuovoImportoBloccante) {
		this.isControlloNuovoImportoBloccante = isControlloNuovoImportoBloccante;
	}

	
}
