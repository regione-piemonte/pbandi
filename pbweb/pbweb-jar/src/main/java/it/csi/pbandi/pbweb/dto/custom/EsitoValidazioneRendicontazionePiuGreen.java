package it.csi.pbandi.pbweb.dto.custom;

import java.util.Arrays;

import it.csi.pbandi.pbweb.pbandisrv.dto.validazionerendicontazione.EsitoValidazioneRendicontazione;

public class EsitoValidazioneRendicontazionePiuGreen extends EsitoValidazioneRendicontazione {
	
	  private java.lang.Long idDocIndexDichiarazionePiuGreen;
	  
	  private java.lang.Long idReportDettaglioDocSpesaPiuGreen;

	public java.lang.Long getIdDocIndexDichiarazionePiuGreen() {
		return idDocIndexDichiarazionePiuGreen;
	}

	public void setIdDocIndexDichiarazionePiuGreen(
			java.lang.Long idDocIndexDichiarazionePiuGreen) {
		this.idDocIndexDichiarazionePiuGreen = idDocIndexDichiarazionePiuGreen;
	}

	public java.lang.Long getIdReportDettaglioDocSpesaPiuGreen() {
		return idReportDettaglioDocSpesaPiuGreen;
	}

	public void setIdReportDettaglioDocSpesaPiuGreen(
			java.lang.Long idReportDettaglioDocSpesaPiuGreen) {
		this.idReportDettaglioDocSpesaPiuGreen = idReportDettaglioDocSpesaPiuGreen;
	}

	@Override
	public String toString() {
		return "EsitoValidazioneRendicontazionePiuGreen [idDocIndexDichiarazionePiuGreen="
				+ idDocIndexDichiarazionePiuGreen + ", idReportDettaglioDocSpesaPiuGreen="
				+ idReportDettaglioDocSpesaPiuGreen + ", getMessage()=" + getMessage() + ", getParams()="
				+ Arrays.toString(getParams()) + ", getEsito()=" + getEsito() + ", getIdDocIndexDichiarazione()="
				+ getIdDocIndexDichiarazione() + ", getIdReportDettaglioDocSpesa()=" + getIdReportDettaglioDocSpesa()
				+ ", getIdDocIndexPiuGreen()=" + getIdDocIndexPiuGreen() + "]";
	}
	
}
