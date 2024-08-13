/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.dto.documentoDiSpesa;

public class VoceDiSpesaFiglia implements java.io.Serializable {

	private Long idVoceDiSpesa = null;
	private Long idRigoContoEconomico = null;
	private String descVoceDiSpesa = null;
	private Long idTipologiaVoceDiSpesa = null;
	private Double importoAmmessoFinanziamento = null;
	private Double importoResiduoAmmesso = null;

	public Long getIdVoceDiSpesa() {
		return idVoceDiSpesa;
	}

	public void setIdVoceDiSpesa(Long idVoceDiSpesa) {
		this.idVoceDiSpesa = idVoceDiSpesa;
	}

	public Long getIdRigoContoEconomico() {
		return idRigoContoEconomico;
	}

	public void setIdRigoContoEconomico(Long idRigoContoEconomico) {
		this.idRigoContoEconomico = idRigoContoEconomico;
	}

	public String getDescVoceDiSpesa() {
		return descVoceDiSpesa;
	}

	public void setDescVoceDiSpesa(String descVoceDiSpesa) {
		this.descVoceDiSpesa = descVoceDiSpesa;
	}

	public Double getImportoAmmessoFinanziamento() {
		return importoAmmessoFinanziamento;
	}

	public void setImportoAmmessoFinanziamento(Double importoAmmessoFinanziamento) {
		this.importoAmmessoFinanziamento = importoAmmessoFinanziamento;
	}

	public Double getImportoResiduoAmmesso() {
		return importoResiduoAmmesso;
	}

	public void setImportoResiduoAmmesso(Double importoResiduoAmmesso) {
		this.importoResiduoAmmesso = importoResiduoAmmesso;
	}

	public Long getIdTipologiaVoceDiSpesa() {
		return idTipologiaVoceDiSpesa;
	}

	public void setIdTipologiaVoceDiSpesa(Long idTipologiaVoceDiSpesa) {
		this.idTipologiaVoceDiSpesa = idTipologiaVoceDiSpesa;
	}

	@Override
	public String toString() {
		return "VoceDiSpesaFiglia{" +
				"idVoceDiSpesa=" + idVoceDiSpesa +
				", idRigoContoEconomico=" + idRigoContoEconomico +
				", descVoceDiSpesa='" + descVoceDiSpesa + '\'' +
				", idTipologiaVoceDiSpesa=" + idTipologiaVoceDiSpesa +
				", importoAmmessoFinanziamento=" + importoAmmessoFinanziamento +
				", importoResiduoAmmesso=" + importoResiduoAmmesso +
				'}';
	}
}
