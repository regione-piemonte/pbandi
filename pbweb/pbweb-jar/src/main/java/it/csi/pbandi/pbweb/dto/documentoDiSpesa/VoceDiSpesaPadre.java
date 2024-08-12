package it.csi.pbandi.pbweb.dto.documentoDiSpesa;

import java.util.ArrayList;

public class VoceDiSpesaPadre implements java.io.Serializable {

	private Long idVoceDiSpesa = null;
	private Long idRigoContoEconomico = null;
	private String descVoceDiSpesa = null;
	private Double importoAmmessoFinanziamento = null;
	private Double importoResiduoAmmesso = null;
	private Long idTipologiaVoceDiSpesa = null;
	private ArrayList<VoceDiSpesaFiglia> vociDiSpesaFiglie = new ArrayList<>();

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

	public ArrayList<VoceDiSpesaFiglia> getVociDiSpesaFiglie() {
		return vociDiSpesaFiglie;
	}
	public void setVociDiSpesaFiglie(ArrayList<VoceDiSpesaFiglia> vociDiSpesaFiglie) {
		this.vociDiSpesaFiglie = vociDiSpesaFiglie;
	}

	public Long getIdTipologiaVoceDiSpesa() {
		return idTipologiaVoceDiSpesa;
	}

	public void setIdTipologiaVoceDiSpesa(Long idTipologiaVoceDiSpesa) {
		this.idTipologiaVoceDiSpesa = idTipologiaVoceDiSpesa;
	}

	@Override
	public String toString() {
		return "VoceDiSpesaPadre{" +
				"idVoceDiSpesa=" + idVoceDiSpesa +
				", idRigoContoEconomico=" + idRigoContoEconomico +
				", descVoceDiSpesa='" + descVoceDiSpesa + '\'' +
				", importoAmmessoFinanziamento=" + importoAmmessoFinanziamento +
				", importoResiduoAmmesso=" + importoResiduoAmmesso +
				", idTipologiaVoceDiSpesa=" + idTipologiaVoceDiSpesa +
				", vociDiSpesaFiglie=" + vociDiSpesaFiglie +
				'}';
	}
}
