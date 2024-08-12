package it.csi.pbandi.pbweb.pbandisrv.dto.gestionedocumentidispesa;

import java.util.Arrays;

public class VoceDiSpesaPadreDTO implements java.io.Serializable {

	static final long serialVersionUID = 1;

	private Long idVoceDiSpesa = null;

	public void setIdVoceDiSpesa(Long val) {
		idVoceDiSpesa = val;
	}

	public Long getIdVoceDiSpesa() {
		return idVoceDiSpesa;
	}

	private String descVoceDiSpesa = null;

	public void setDescVoceDiSpesa(String val) {
		descVoceDiSpesa = val;
	}

	public String getDescVoceDiSpesa() {
		return descVoceDiSpesa;
	}

	private it.csi.pbandi.pbweb.pbandisrv.dto.gestionedocumentidispesa.CodiceDescrizioneDTO[] vociDiSpesaFiglie = null;

	public void setVociDiSpesaFiglie(
			it.csi.pbandi.pbweb.pbandisrv.dto.gestionedocumentidispesa.CodiceDescrizioneDTO[] val) {
		vociDiSpesaFiglie = val;
	}

	public it.csi.pbandi.pbweb.pbandisrv.dto.gestionedocumentidispesa.CodiceDescrizioneDTO[] getVociDiSpesaFiglie() {
		return vociDiSpesaFiglie;
	}

	@Override
	public String toString() {
		return "VoceDiSpesaPadreDTO{" +
				"idVoceDiSpesa=" + idVoceDiSpesa +
				", descVoceDiSpesa='" + descVoceDiSpesa + '\'' +
				", vociDiSpesaFiglie=" + Arrays.toString(vociDiSpesaFiglie) +
				'}';
	}
}